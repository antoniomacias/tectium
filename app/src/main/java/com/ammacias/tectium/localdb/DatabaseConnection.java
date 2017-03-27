package com.ammacias.tectium.localdb;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by ammacias on 23/03/2017.
 */

public class DatabaseConnection {
    private static SQLiteDatabase db;
    private static DaoMaster daoMaster;
    private static DaoSession daoSession;

    public static DaoSession getConnection(Context ctx) {
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(ctx,"marca-db", null);
        db = helper.getWritableDatabase();
        daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();

        return daoSession;
    }

    public static CategoriaDBDao getCategoriaDBDao(Context ctx) {
        DaoSession daoSession = DatabaseConnection.getConnection(ctx);
        return daoSession.getCategoriaDBDao();
    }


    public static void closeConnection(){
        daoMaster.getDatabase().close();
    }
}
