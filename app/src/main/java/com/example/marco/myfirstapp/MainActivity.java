package com.example.marco.myfirstapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.SQLException;
import android.database.sqlite.SQLiteException;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import static android.provider.AlarmClock.EXTRA_MESSAGE;


public class MainActivity extends AppCompatActivity {

    public static final String EXTRA_MESSAGE = "extra_message";

    EditText firstName, lastName;
    TextView textView;
    DB_Controller controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firstName = (EditText) findViewById(R.id.firstanme_input);
        lastName = (EditText) findViewById(R.id.lastname_input);
        textView = (TextView) findViewById(R.id.textView);

        controller = new DB_Controller(this, "", null, 1);

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
