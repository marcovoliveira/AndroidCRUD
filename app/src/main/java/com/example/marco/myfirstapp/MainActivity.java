package com.example.marco.myfirstapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.SQLException;
import android.database.sqlite.SQLiteException;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static android.provider.AlarmClock.EXTRA_MESSAGE;


public class MainActivity extends AppCompatActivity {

    public static final String EXTRA_MESSAGE = "extra_message";

    EditText firstName, lastName;
    TextView textView, test;
    DB_Controller controller;
    Button start;
    RequestQueue requestQueue;           //Class que permite os Json Request e URL request usando a library volley


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firstName = (EditText) findViewById(R.id.firstanme_input);
        lastName = (EditText) findViewById(R.id.lastname_input);
        textView = (TextView) findViewById(R.id.textView);

        controller = new DB_Controller(this, "", null, 1);

        start = (Button) findViewById(R.id.request);
        test = (TextView) findViewById(R.id.test);
        requestQueue = Volley.newRequestQueue(this);

        start.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                test.setText("");
                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest("https://raw.githubusercontent.com/rezken1/AndroidCRUD/master/colors.json",
                        null,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {

                                try {
                                    JSONArray jsonArray = response.getJSONArray("colors");

                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        JSONObject address = jsonArray.getJSONObject(i);

                                        String title = address.getString("color");
                                        String body = address.getString("category");

                                        test.append(title+ " " + body+ "\n");
                                    }



                                } catch (JSONException e){
                                    e.printStackTrace();
                                }

                            }
                        },

                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.e("VOLLEY", "ERROR");
                            }
                        }
                );
                requestQueue.add(jsonObjectRequest);
            }
        });

    }


    public void btn_click(View view) {
        switch (view.getId()){
            case R.id.btn_add:
                try {
                    controller.insert_student(firstName.getText().toString(), lastName.getText().toString());
                }
                catch (SQLiteException e){
                    Toast.makeText(MainActivity.this, "ALREDY EXISTS", Toast.LENGTH_SHORT).show();
                }

                break;
            case R.id.btn_delete:
                controller.delete(firstName.getText().toString());
                break;
            case R.id.btn_update:
                AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
                dialog.setTitle("Enter new firstname ");

                final EditText newFirstName = new EditText(this);
                dialog.setView(newFirstName);

                dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        controller.update_student(firstName.getText().toString(), newFirstName.getText().toString());
                    }
                });

                dialog.show();

                break;
            case R.id.btn_list:

                controller.list_all_studentds(textView);

                break;

        }
    }
}
