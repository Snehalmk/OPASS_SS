package com.example.admin.opass;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ScrollView;

import java.util.ArrayList;

import static com.example.admin.opass.UserContract.NewUserInfo.TABLE_NAME;
import static com.example.admin.opass.UserContract.NewUserInfo.USER_EMAIL;
import static com.example.admin.opass.UserContract.NewUserInfo.USER_NAME;
import static com.example.admin.opass.UserContract.NewUserInfo.USER_PASS;

public class Login extends AppCompatActivity {
    Context context=this;
    DatabaseHelper databaseHelper;
    SQLiteDatabase sqLiteDatabase;
    Cursor cursor;
    ArrayList<String> names=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        databaseHelper=new DatabaseHelper(context);
        sqLiteDatabase=databaseHelper.getReadableDatabase();
        cursor=databaseHelper.getName(sqLiteDatabase);
        while (cursor.moveToNext())
        {
            names.add(cursor.getString(0));
        }
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,android.R.layout.simple_dropdown_item_1line,names);
        final EditText passw= (EditText) findViewById(R.id.Password);
        final AutoCompleteTextView user= (AutoCompleteTextView) findViewById(R.id.User);
        user.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        user.setAdapter(adapter);
        user.setThreshold(1);
        final CheckBox show= (CheckBox) findViewById(R.id.checkBox);
        show.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(!isChecked)
                {
                    passw.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
                else
                {
                    passw.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
            }
        });

        Button confirm= (Button) findViewById(R.id.confirm);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String password = passw.getText().toString();
                String usern = user.getText().toString();
                View focusview1 = null;
                boolean exit = false;
                if (usern.isEmpty()) {
                    user.setError("Field required!");
                    focusview1 = user;
                    exit = true;
                }
                if (exit) {
                    focusview1.requestFocus();
                } else
                {
                    boolean status=false;

                    cursor= databaseHelper.getInfo(sqLiteDatabase);
                    if (cursor.moveToFirst())
                    {
                        do{
                            if(usern.equals(cursor.getString(0)) && password.equals(cursor.getString(2)) )
                            {
                                status=true;
                            }
                        }while (cursor.moveToNext());
                        if(status)
                        {
                            AlertDialog login = new AlertDialog.Builder(Login.this).create();
                            login.setMessage("Login Successful");
                            login.setButton(DialogInterface.BUTTON_POSITIVE, "CONTINUE", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface d, int id) {
                                    Intent intent = new Intent(Login.this, Home.class);
                                    intent.putExtra("Username", user.getText().toString());
                                    startActivity(intent);
                                }
                            });
                            login.show();
                        }
                        else
                        {
                            AlertDialog wrong = new AlertDialog.Builder(Login.this).create();
                            wrong.setMessage("Invalid User");
                            wrong.setButton(DialogInterface.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface d, int id) {

                                }
                            });
                            wrong.show();
                        }
                    }

                }
            }

        });



    }
}
