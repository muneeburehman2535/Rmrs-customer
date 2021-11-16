package com.comcept.rmrscustomer.db;


import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

public class DatabaseMigrations {

    public static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            database.execSQL("ALTER TABLE cart  ADD COLUMN is_deal INTEGER NOT NULL DEFAULT 0");

        }
    };

}
