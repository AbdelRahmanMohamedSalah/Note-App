package com.example.abdelrahman.note;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Activity_show extends AppCompatActivity {

    TextView tv_title, tv_note,all,delete,share,edit;
    ImageButton btn_all, btn_delete, btn_share, btn_edit;

    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);
        String u_id = FirebaseAuth.getInstance().getCurrentUser().getUid();
        databaseReference = FirebaseDatabase.getInstance().getReference("Note").child(u_id);
        tv_note = (TextView) findViewById(R.id.tv_activity_show_note);
        tv_title = (TextView) findViewById(R.id.tv_activity_show_title);
        btn_all = (ImageButton) findViewById(R.id.ibtn_activity_show_allnote);
        btn_delete = (ImageButton) findViewById(R.id.ibtn_activity_show_delete);
        btn_share = (ImageButton) findViewById(R.id.ibtn_activity_show_share);
        btn_edit = (ImageButton) findViewById(R.id.ibtn_activity_show_edit);

        all = (TextView) findViewById(R.id.tv_all);
        delete = (TextView) findViewById(R.id.tv_delete);
        share = (TextView) findViewById(R.id.tv_share);
        edit = (TextView) findViewById(R.id.tv_edit);




        final Intent intent = getIntent();
        final String key = intent.getStringExtra("intent_id_key");
        final String note = intent.getStringExtra("intent_id_note");
        final String title = intent.getStringExtra("intent_id_title");

//        System.out.println(key);
        tv_title.setText(title);
        tv_note.setText(note);

        final String title_note = ("Title-> " + title + "  :  " + "Note->  " + note);

        all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(Activity_show.this, MainActivity.class);
                startActivity(intent1);
                finish();
            }
        });
        btn_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(Activity_show.this, MainActivity.class);
                startActivity(intent1);
                finish();
            }
        });

        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(Intent.ACTION_SEND);
                intent2.setType("text/plain");

                intent2.putExtra(Intent.EXTRA_TEXT, title_note);
                //intent2.putExtra(Intent.EXTRA_TEXT,note);
                startActivity(Intent.createChooser(intent2, "choose"));
                finish();
            }
        });
        btn_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(Intent.ACTION_SEND);
                intent2.setType("text/plain");

                intent2.putExtra(Intent.EXTRA_TEXT, title_note);
                //intent2.putExtra(Intent.EXTRA_TEXT,note);
                startActivity(Intent.createChooser(intent2, "choose"));
finish();
                //  startActivity(intent2);
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder alertDialog = new AlertDialog.Builder(Activity_show.this);
                alertDialog.setTitle("Delete note");
                alertDialog.setMessage("Do you want to delete note");
                alertDialog.setIcon(R.drawable.ic_delete_black_24dp);
                alertDialog.setNegativeButton("yes", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent1 = new Intent(Activity_show.this, MainActivity.class);
                        startActivity(intent1);
                        databaseReference.child(key).removeValue();
                        finish();

                    }
                });

                alertDialog.setPositiveButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {


                    }
                });


                alertDialog.show();        //      System.out.println(key);
            }
        });
        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                AlertDialog.Builder alertDialog = new AlertDialog.Builder(Activity_show.this);
                alertDialog.setTitle("Delete note");
                alertDialog.setMessage("Do you want to delete note");
                alertDialog.setIcon(R.drawable.ic_delete_black_24dp);
                alertDialog.setNegativeButton("yes", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent1 = new Intent(Activity_show.this, MainActivity.class);
                        startActivity(intent1);
                        databaseReference.child(key).removeValue();
finish();
                    }
                });

                alertDialog.setPositiveButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {


                    }
                });


                alertDialog.show();        //      System.out.println(key);
            }

        });

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(Activity_show.this, Activity_edit.class);

                intent2.putExtra("intent_id_key", key);
                intent2.putExtra("intent_id_note", note);
                intent2.putExtra("intent_id_title", title);

                startActivity(intent2);
                finish();
            }
        });
        btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent2 = new Intent(Activity_show.this, Activity_edit.class);

                intent2.putExtra("intent_id_key", key);
                intent2.putExtra("intent_id_note", note);
                intent2.putExtra("intent_id_title", title);

                startActivity(intent2);
finish();
            }
        });
    }
}
