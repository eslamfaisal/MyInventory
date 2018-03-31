package com.example.eslamfaisal.myinventory.data;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

public class DataContract {

    public static final String CONTENT_AUTHORITY = "com.example.eslamfaisal.myinventory";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    public static final String PRODUCTS_PATH = "products";

    private DataContract() {
    }

    public static final class ProductEntry implements BaseColumns {

        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PRODUCTS_PATH);
        public static final String TABLE_NAME = "products";

        public static final String CONTENT_LIST_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PRODUCTS_PATH;
        public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PRODUCTS_PATH;

        public static final String _ID = BaseColumns._ID;
        public static final String COLUMN_PRODUCT_NAME = "product";
        public static final String COLUMN_PRODUCT_QUANTITY = "quantity";
        public static final String COLUMN_PRODUCT_PRICE = "price";
        public static final String COLUMN_PRODUCT_IMAGE = "image";

    }
}
