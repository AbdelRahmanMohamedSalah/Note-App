package com.example.abdelrahman.note;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class Activity_signup extends AppCompatActivity {


    EditText et_first_name, et_email, et_last_name, et_password, et_re_password;
    Button btn_save;
    DatabaseReference databaseReference;
    FirebaseAuth firebaseAuth;
    FirebaseAuth.AuthStateListener authStateListener;
    //    ImageButton ibtn_image;
    Uri imageUri;
    private static final int RUSLET_LOAD_IMAGE = 1;
    private StorageReference mStorageReference;

    boolean checkClick = false;

    @Override
    protected void onStart() {
        super.onStart();
        firebaseAuth.addAuthStateListener(authStateListener);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

//        ibtn_image = (ImageButton) findViewById(R.id.ibtn_image);
        et_first_name = (EditText) findViewById(R.id.et_first_name);
        et_last_name = (EditText) findViewById(R.id.et_last_name);
        et_email = (EditText) findViewById(R.id.et_email);
        et_password = (EditText) findViewById(R.id.et_password);
        et_re_password = (EditText) findViewById(R.id.et_re_password);
        btn_save = (Button) findViewById(R.id.btn_save);


        mStorageReference = FirebaseStorage.getInstance().getReference();


//        ibtn_image.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent open = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//                startActivityForResult(open, RUSLET_LOAD_IMAGE);
//
//
//            }
//        });

        databaseReference = FirebaseDatabase.getInstance().getReference("users");


        firebaseAuth = FirebaseAuth.getInstance();

        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

            }
        };


        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                final String f_name = et_first_name.getText().toString();
                final String l_name = et_last_name.getText().toString();
                String email = et_email.getText().toString();
                String password1 = et_password.getText().toString();
                String password2 = et_re_password.getText().toString();

                if (!f_name.isEmpty()) {
                    if (!l_name.isEmpty()) {
                        if (!email.isEmpty()) {
                            if (email.contains("@") && email.contains(".com")) {
                                if (!password1.isEmpty()) {
                                    if (password1.length() >= 6) {
                                        if (!password2.isEmpty()) {


                                            if (password1.equals(password2)) {

                                                firebaseAuth.createUserWithEmailAndPassword(email, password1).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<AuthResult> task) {

                                                        if (!task.isSuccessful()) {


                                                            Toast.makeText(Activity_signup.this, "Erorr Connection ", Toast.LENGTH_SHORT).show();
                                                        } else {

                                                            String fullname = f_name + " " + l_name;
                                                            String uid = firebaseAuth.getCurrentUser().getUid();


                                                            String email = et_email.getText().toString();

                                                            databaseReference.child(uid).child("name").setValue(fullname);
                                                            databaseReference.child(uid).child("email").setValue(email);
//                                                    databaseReference.child(uid).child("image").setValue(imageUri.toString());


//                                                    try {
//                                                        if (checkClick == true) {
//
//                                                            mStorageReference.child(firebaseAuth.getCurrentUser().getUid()).child("profile.jpg").putFile(imageUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
//                                                                @Override
//                                                                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
//
//                                                                    if (task.isSuccessful()) {
//
//                                                                        databaseReference.child(firebaseAuth.getCurrentUser().getUid()).child("image").setValue(task.getResult().getDownloadUrl().toString());

//                                                 Toast.makeText(Edit_Ads.this, "تم التعديل", Toast.LENGTH_SHORT).show();
////
//                                                                        finish();
//
//                                                                    }
//                                                                }
//                                                            });
//                                                        }
//
//
//                                                    } catch (Exception e) {
//
//                                                    }


                                                            Toast.makeText(Activity_signup.this, "welcome", Toast.LENGTH_SHORT).show();
                                                            Intent intent = new Intent(Activity_signup.this, MainActivity.class);
                                                            //    intent.putExtra("uid_reg", uid);
                                                            //  intent.putExtra("name", fullname);
                                                            startActivity(intent);
                                                            finish();


                                                        }


                                                    }
                                                });

                                            } else {

                                                Toast.makeText(Activity_signup.this, "password not Matched", Toast.LENGTH_SHORT).show();
                                            }

                                        } else {

                                            et_re_password.setError("RePassword Empty");
                                        }


                                    } else {
                                        et_password.setError("password lengh to short ");
                                    }
                                } else {

                                    et_password.setError("password Empty");
                                }
                            }else {
                            et_email.setError("error email type");
                            }
                        } else {

                            et_email.setError("Email Empty");

                        }


                    } else {
                        et_last_name.setError("Last Name Empty");
                    }
                } else {

                    et_first_name.setError("First Name Empty");
                }


            }
        });


    }


//    @Override
//    protected void onActivityResult(int reqCode, int resultCode, Intent data) {
//        super.onActivityResult(reqCode, resultCode, data);
//
//
//        if (resultCode == RESULT_OK) {
//            try {
//                imageUri = data.getData();
//                checkClick = true;
//                final InputStream imageStream = getContentResolver().openInputStream(imageUri);
//
//                final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
//                Picasso.with(this).load(imageUri).into(ibtn_image);
//
//
//            } catch (FileNotFoundException e) {
//              e.printStackTrace();
//                Toast.makeText(Activity_signup.this, "Something went wrong", Toast.LENGTH_LONG).show();
//            }
//
//        } else {
//            Toast.makeText(Activity_signup.this, "You haven't picked Image", Toast.LENGTH_LONG).show();
//        }
//    }
}


