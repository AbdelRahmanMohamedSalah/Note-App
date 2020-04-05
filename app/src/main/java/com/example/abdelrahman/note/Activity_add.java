package com.example.abdelrahman.note;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Activity_add extends AppCompatActivity {


    ImageButton btn_back, btn_save;
    TextInputEditText et_title, et_note;


    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        btn_back = (ImageButton) findViewById(R.id.btn_add_back);
        btn_save = (ImageButton) findViewById(R.id.btn_add_note);
        et_title = (TextInputEditText) findViewById(R.id.et_add_title);
        et_note = (TextInputEditText) findViewById(R.id.et_add_note);

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!et_title.getText().toString().isEmpty()) {
                    if (!et_note.getText().toString().isEmpty()) {

                        String title = et_title.getText().toString();
                        String note = et_note.getText().toString();
                        String date = date();

                        String id = FirebaseAuth.getInstance().getCurrentUser().getUid();

                        databaseReference = FirebaseDatabase.getInstance().getReference("Note").child(id).push();

                        String key = databaseReference.getKey();
                        databaseReference.child("title").setValue(title);
                        databaseReference.child("note").setValue(note);
                        databaseReference.child("date").setValue(date);
                        databaseReference.child("key").setValue(key);

                        Toast.makeText(Activity_add.this, "Added", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Activity_add.this, MainActivity.class);
                startActivity(intent);
                finish();
                       // onBackPressed();
                    }else {
                     et_note.setError("field Empty");
                    }
                }else {
                    et_title.setError("field Empty");
                }

            }
        });

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Activity_add.this, MainActivity.class);
                startActivity(intent);
                finish();
//                onBackPressed();
            }
        });
    }
    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        startActivity(new Intent(Activity_add.this, MainActivity.class));
        finish();

    }

    public String date() {

        Date date = new Date();//"dd-MM-yyyy HH:mm"
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

        return dateFormat.format(date);

    }
}
