package com.example.admin.opass;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

public class ViewDb extends AppCompatActivity {
    ListView listView;
    SQLiteDatabase sqLiteDatabase;
    DatabaseHelper databaseHelper;
    Cursor cursor;
    ListAdapter listAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_db);
        listView= (ListView) findViewById(R.id.list_view);
        listAdapter=new ListAdapter(getApplicationContext(),R.layout.row_layout);
        listView.setAdapter(listAdapter);
        databaseHelper=new DatabaseHelper(getApplicationContext());
        sqLiteDatabase=databaseHelper.getReadableDatabase();
        cursor=databaseHelper.getInfo(sqLiteDatabase);
        if(cursor.moveToFirst()){
            do{
                String name,email,password;
                name=cursor.getString(0);
                email=cursor.getString(1);
                password=cursor.getString(2);
                DataProvider dataProvider=new DataProvider(name,email,password);
                listAdapter.add(dataProvider);

            }while (cursor.moveToNext());
        }
    }
}
