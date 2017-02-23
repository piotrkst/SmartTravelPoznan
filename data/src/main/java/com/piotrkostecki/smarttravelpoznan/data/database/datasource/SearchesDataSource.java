package com.piotrkostecki.smarttravelpoznan.data.database.datasource;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.piotrkostecki.smarttravelpoznan.data.database.MySQLiteHelper;
import com.piotrkostecki.smarttravelpoznan.data.entity.SearchEntity;
import com.piotrkostecki.smarttravelpoznan.domain.executor.ThreadExecutor;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscriber;
import rx.schedulers.Schedulers;


public class SearchesDataSource {

    private ThreadExecutor threadExecutor;

    private SQLiteDatabase database;
    private MySQLiteHelper dbHelper;
    private String[] allColumns = {
            MySQLiteHelper.COLUMN_ID,
            MySQLiteHelper.COLUMN_SEARCH,
            MySQLiteHelper.COLUMN_QUANTITY
    };

    @Inject
    public SearchesDataSource(Context context, ThreadExecutor threadExecutor) {
        dbHelper = new MySQLiteHelper(context);
        this.threadExecutor = threadExecutor;
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        database.close();
    }

    public void createSearch(String search) {
        this.executeAsynchronously(() -> {
            SearchEntity searchEntity = checkIfSearchExists(search);
            ContentValues values = new ContentValues();
            values.put(MySQLiteHelper.COLUMN_SEARCH, search);
            if (searchEntity == null) {
                values.put(MySQLiteHelper.COLUMN_QUANTITY, 1);
                database.insert(MySQLiteHelper.TABLE_SEARCHES, null,
                        values);
            } else {
                values.put(MySQLiteHelper.COLUMN_QUANTITY, searchEntity.getQuantity() + 1);
                database.update(MySQLiteHelper.TABLE_SEARCHES, values, "_id =" + searchEntity.getId(), null);
            }
            this.close();
        });
    }

    private Callable<List<SearchEntity>> getAllSearches() {
        return () -> {
            List<SearchEntity> searches = new ArrayList<>();

            Cursor cursor = database.query(MySQLiteHelper.TABLE_SEARCHES,
                    allColumns, null, null, null, null, MySQLiteHelper.COLUMN_QUANTITY + " DESC");

            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                SearchEntity search = cursorToSearch(cursor);
                searches.add(search);
                Log.i("TEST", "getAllSearches: " + search.getName() + search.getQuantity());
                cursor.moveToNext();
            }

            cursor.close();
            return searches;
        };
    }

    public SearchEntity checkIfSearchExists(String fieldValue) {
        String query = "SELECT * FROM " + MySQLiteHelper.TABLE_SEARCHES + " WHERE " + MySQLiteHelper.COLUMN_SEARCH + "= '" + fieldValue + "'";
        Cursor cursor = database.rawQuery(query, null);

        if (cursor.getCount() <= 0) {
            cursor.close();
            return null;
        }
        cursor.moveToFirst();
        SearchEntity search = cursorToSearch(cursor);

        cursor.close();
        return search;
    }

    private SearchEntity cursorToSearch(Cursor cursor) {
        SearchEntity search = new SearchEntity();
        search.setId(cursor.getLong(0));
        search.setName(cursor.getString(1));
        search.setQuantity(cursor.getInt(2));
        return search;
    }

    public Observable<List<SearchEntity>> getSearches() {
        return makeObservable(getAllSearches())
                .subscribeOn(Schedulers.computation());
    }

    private static <T> Observable<T> makeObservable(final Callable<T> func) {
        return Observable.create(
                new Observable.OnSubscribe<T>() {
                    @Override
                    public void call(Subscriber<? super T> subscriber) {
                        try {
                            subscriber.onNext(func.call());
                        } catch(Exception ex) {
                            Log.e("Error", "Error reading from the database", ex);
                        }
                    }
                });
    }

    private void executeAsynchronously(Runnable runnable) {
        this.threadExecutor.execute(runnable);
    }
}
