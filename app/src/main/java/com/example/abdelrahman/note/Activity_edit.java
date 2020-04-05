package com.example.abdelrahman.note;

import android.content.Intent;
import android.media.Image;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Activity_edit extends AppCompatActivity {
    TextInputEditText  et_note;
    TextInputEditText et_title;
    ImageButton btn_save, btn_back;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        this.getSupportActionBar().hide();


        final Intent intent = getIntent();
        final String key = intent.getStringExtra("intent_id_key");
        String title = intent.getStringExtra("intent_id_title");
        String note = intent.getStringExtra("intent_id_note");
        final String user = FirebaseAuth.getInstance().getCurrentUser().getUid();

        et_title = (TextInputEditText) findViewById(R.id.et_edit_title);
        et_note = (TextInputEditText) findViewById(R.id.et_edit_note);
        btn_save = (ImageButton) findViewById(R.id.btn_edit_note);
        btn_back = (ImageButton) findViewById(R.id.btn_edit_back);

        et_title.setText(title);
        et_note.setText(note);

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String title2=et_title.getText().toString();
                String note2=et_note.getText().toString();

                databaseReference = FirebaseDatabase.getInstance().getReference("Note").child(user);
                databaseReference.child(key).child("title").setValue(title2);
                databaseReference.child(key).child("note").setValue(note2);


                Intent intent=new Intent(Activity_edit.this,MainActivity.class);
                startActivity(intent);

finish();
            }
        });

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1=new Intent(Activity_edit.this,MainActivity.class);
                startActivity(intent1);

            finish();}
        });


    }

}
