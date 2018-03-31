package com.example.eslamfaisal.myinventory;


import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.eslamfaisal.myinventory.data.DataContract;
import com.example.eslamfaisal.myinventory.data.DataContract.ProductEntry;


public class DataCursorAdapter extends CursorAdapter {

    private CatalogActivity catalogActivity = new CatalogActivity();

    public DataCursorAdapter(CatalogActivity context, Cursor c) {
        super(context, c, 0);
        this.catalogActivity = context;
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.list_item, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        int nameColumnIndex = cursor.getColumnIndexOrThrow(ProductEntry.COLUMN_PRODUCT_NAME);
        String productName = cursor.getString(nameColumnIndex);
        int quantityColumnIndex = cursor.getColumnIndexOrThrow(ProductEntry.COLUMN_PRODUCT_QUANTITY);
        final int productQuantity = cursor.getInt(quantityColumnIndex);
        int priceColumnIndex = cursor.getColumnIndexOrThrow(ProductEntry.COLUMN_PRODUCT_PRICE);
        int productPrice = cursor.getInt(priceColumnIndex);

        TextView name = (TextView) view.findViewById(R.id.name);
        name.setText(productName);

        TextView quantity = (TextView) view.findViewById(R.id.quantity);
        quantity.setText(String.valueOf(productQuantity));

        TextView price = (TextView) view.findViewById(R.id.price);
        price.setText(String.valueOf(productPrice));

        ImageView productImage = (ImageView) view.findViewById(R.id.product_image);
        String image = cursor.getString(cursor.getColumnIndexOrThrow(ProductEntry.COLUMN_PRODUCT_IMAGE));
        productImage.setImageURI(Uri.parse(image));

        final Long id = cursor.getLong(cursor.getColumnIndex(DataContract.ProductEntry._ID));
        Button saleButton = (Button) view.findViewById(R.id.sale_button);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                catalogActivity.onListItemClick(id);
            }
        });
        saleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                catalogActivity.onSaleButtonClick(id, productQuantity);
                notifyDataSetChanged();
            }
        });
    }
}
