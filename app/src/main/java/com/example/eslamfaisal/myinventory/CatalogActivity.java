package com.example.eslamfaisal.myinventory;

import android.app.LoaderManager;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.eslamfaisal.myinventory.data.DataContract.ProductEntry;
import com.example.eslamfaisal.myinventory.data.DataContract;

public class CatalogActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final int PRODUCT_LOADER = 0;

    private DataCursorAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catalog);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CatalogActivity.this, EditActivity.class);
                startActivity(intent);
            }
        });

        ListView productListView = (ListView) findViewById(R.id.list);

        View emptyView = findViewById(R.id.empty_catalog);
        productListView.setEmptyView(emptyView);

        adapter = new DataCursorAdapter(this, null);
        productListView.setAdapter(adapter);

        getLoaderManager().initLoader(PRODUCT_LOADER, null, this);

        productListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> pAdapterView, View pView, int position, long id) {
                Intent intent = new Intent(CatalogActivity.this, EditActivity.class);
                Uri currentItemUri = ContentUris.withAppendedId(ProductEntry.CONTENT_URI, id);
                intent.setData(currentItemUri);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_catalog, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_add_dummy_data) {
            insertProduct();
            return true;
        }

        if (id == R.id.delete_all_items) {
            showDeleteConfirmationDialog();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void showDeleteConfirmationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.delete_all_items);
        builder.setPositiveButton(R.string.action_continue, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                deleteAllProducts();
            }
        });
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

                if (dialog != null) {
                    dialog.dismiss();
                }
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void deleteAllProducts() {
        int rowsDeleted = getContentResolver().delete(ProductEntry.CONTENT_URI, null, null);
        Log.v("CatalogActivity", rowsDeleted + " rows deleted from products database");
    }

    private void insertProduct() {
        ContentValues values = new ContentValues();
        values.put(ProductEntry.COLUMN_PRODUCT_NAME, "Trousers");
        values.put(ProductEntry.COLUMN_PRODUCT_PRICE, 150);
        values.put(ProductEntry.COLUMN_PRODUCT_QUANTITY, 6);

        Uri imgUri = Uri.parse("android.resource://" + DataContract.CONTENT_AUTHORITY + "/" + R.drawable.ic_add_photo);
        values.put(ProductEntry.COLUMN_PRODUCT_IMAGE, imgUri.toString());
        Uri newUri = getContentResolver().insert(ProductEntry.CONTENT_URI, values);
        Log.v("Main", newUri.toString());
    }

    public void onListItemClick(long id) {
        Intent intent = new Intent(CatalogActivity.this, EditActivity.class);

        Uri currentItemUri = ContentUris.withAppendedId(ProductEntry.CONTENT_URI, id);
        intent.setData(currentItemUri);

        startActivity(intent);
    }

    public void onSaleButtonClick(long id, int quantity) {
        Uri currentItemUri = ContentUris.withAppendedId(ProductEntry.CONTENT_URI, id);

        int newQuantity = quantity > 0 ? quantity - 1 : 0;

        ContentValues values = new ContentValues();
        values.put(ProductEntry.COLUMN_PRODUCT_QUANTITY, newQuantity);

        getContentResolver().update(currentItemUri, values, null, null);

        if (quantity == newQuantity) {

            Toast.makeText(this, getString(R.string.out_of_stock), Toast.LENGTH_SHORT).show();
        } else if (quantity != newQuantity) {

            Toast.makeText(this, getString(R.string.updated_item),
                    Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String[] projection = {
                ProductEntry._ID,
                ProductEntry.COLUMN_PRODUCT_NAME,
                ProductEntry.COLUMN_PRODUCT_PRICE,
                ProductEntry.COLUMN_PRODUCT_QUANTITY,
                ProductEntry.COLUMN_PRODUCT_IMAGE};

        return new CursorLoader(this, ProductEntry.CONTENT_URI,
                projection, null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        adapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        adapter.swapCursor(null);
    }
}