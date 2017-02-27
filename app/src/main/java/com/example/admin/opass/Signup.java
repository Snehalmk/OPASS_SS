package com.example.admin.opass;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;


public class Signup extends AppCompatActivity {
    Context context=this;
    DatabaseHelper databaseHelper;
    SQLiteDatabase sqLiteDatabase;

    private static final String REGISTER_URL = "http://opass.tk/signup.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        final EditText Name = (EditText) findViewById(R.id.Name);
        final EditText Email = (EditText) findViewById(R.id.Email);
        final EditText Pass = (EditText) findViewById(R.id.Pass);
        final EditText PassC = (EditText) findViewById(R.id.PassC);
        final Button Login = (Button) findViewById(R.id.Finish);
        final SQLiteDatabase db = openOrCreateDatabase("OpassDB", Context.MODE_PRIVATE, null);
        db.execSQL("CREATE TABLE IF NOT EXISTS opass(Name VARCHAR,Email VARCHAR,Password VARCHAR);");
        Login.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String name = Name.getText().toString();
                String email = Email.getText().toString();
                String password = Pass.getText().toString();
                String passwordc = PassC.getText().toString();
                View focusview = null;
                boolean exit = false;
                if (name.isEmpty()) {
                    Name.setError("Field Required");
                    focusview = Name;
                    exit = true;
                }
                if (!isValidEmail(email)) {
                    Email.setError("Invalid Email");
                    focusview = Email;
                    exit = true;
                }
                if (password.length() < 4) {
                    Pass.setError("Password too short");
                    focusview = Pass;
                    exit = true;
                }
                if (!password.equals(passwordc)) {
                    PassC.setError("Re-enter");
                    focusview = PassC;
                    exit = true;
                }
                if (exit) {
                    focusview.requestFocus();
                }
                else {
                    if (password.equals(passwordc))
                    {
                        register(name,email,password);
                        databaseHelper=new DatabaseHelper(context);
                        sqLiteDatabase=databaseHelper.getWritableDatabase();
                        databaseHelper.addInfo(name,email,password,sqLiteDatabase);
                        databaseHelper.close();
                        AlertDialog signup = new AlertDialog.Builder(Signup.this).create();
                        signup.setMessage("Sign-up Successful");
                        signup.setButton(DialogInterface.BUTTON_POSITIVE, "CONTINUE", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface d, int id) {
                                Intent intent = new Intent(Signup.this, Login.class);
                                startActivity(intent);
                            }
                        });
                        signup.show();
                    }
                }

            }
            public boolean isValidEmail(CharSequence target) {
                if (TextUtils.isEmpty(target)) {
                    return false;
                } else {
                    return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
                }
            }
        });

    }
    private void register(String name, String email, String password) {
        class RegisterUser extends AsyncTask<String, Void, String>{
            ProgressDialog loading;
            RegisterUserClass ruc = new RegisterUserClass();


            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(Signup.this, "Please Wait",null, true, true);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();
            }

            @Override
            protected String doInBackground(String... params) {

                HashMap<String, String> data = new HashMap<String,String>();
                data.put("name",params[0]);
                data.put("email",params[1]);
                data.put("password",params[2]);

                String result = ruc.sendPostRequest(REGISTER_URL,data);

                return  result;
            }
        }

        RegisterUser ru = new RegisterUser();
        ru.execute(name,email,password);
    }
}
