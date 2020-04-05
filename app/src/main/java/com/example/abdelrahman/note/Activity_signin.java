package com.example.abdelrahman.note;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Activity_signin extends AppCompatActivity {


    String name, password;
    ProgressBar progressBar;
    EditText et_name, et_password;
    Button btn_login;
    TextView btn_signup;


    FirebaseAuth firebaseAuth;
    FirebaseAuth.AuthStateListener authStateListener;

    @Override
    protected void onStart() {
        super.onStart();
        firebaseAuth.addAuthStateListener(authStateListener);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
        this.getSupportActionBar().hide();

        et_name = (EditText) findViewById(R.id.name);
        et_password = (EditText) findViewById(R.id.password);
        btn_login = (Button) findViewById(R.id.login);
        btn_signup = (TextView) findViewById(R.id.signup);
        progressBar = findViewById(R.id.progressSignUpActivity);

        progressBar.setVisibility(View.INVISIBLE);
        firebaseAuth = FirebaseAuth.getInstance();


        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {

                    Intent intent = new Intent(Activity_signin.this, MainActivity.class);
                    startActivity(intent);
                    finish();

                }
//                else {
//                    Toast.makeText(Activity_signin.this, "Login please", Toast.LENGTH_SHORT).show();
//                }
            }
        };


        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = et_name.getText().toString();
                password = et_password.getText().toString();

                if (!name.isEmpty()) {
                    if (!password.isEmpty()) {
                        btn_login.setEnabled(false);
                        progressBar.setVisibility(View.VISIBLE);
                        firebaseAuth.signInWithEmailAndPassword(name, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {

                                if (!task.isSuccessful()) {

                                    Toast.makeText(Activity_signin.this, "Error", Toast.LENGTH_SHORT).show();
                                    progressBar.setVisibility(View.INVISIBLE);
                                    btn_login.setEnabled(true);
                                } else {

                                    Toast.makeText(Activity_signin.this, "Hello", Toast.LENGTH_SHORT).show();

                                    String uid = firebaseAuth.getCurrentUser().getUid();
                                    Intent intent = new Intent(Activity_signin.this, MainActivity.class);
                                    intent.putExtra("uid_log", uid);
                                    startActivity(intent);
                                }
                            }
                        });

                    } else {
                       et_password.setError("password Empty");
                    }
                } else {
                    et_name.setError("Email Empty");
                }
            }
        });

        btn_signup.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Activity_signin.this, Activity_signup.class);
                startActivity(intent);


            }
        });


    }

}

