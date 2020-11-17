package com.example.mreminder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {
    EditText emailId,password,category;
    Button btnsignIn;
    TextView TvnotRegistered;
    FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mFirebaseAuth = FirebaseAuth.getInstance();
        emailId=findViewById(R.id.emailId);
        password=findViewById(R.id.password);
        category=findViewById(R.id.category);
        btnsignIn = findViewById(R.id.btnsignIn);
        TvnotRegistered = findViewById(R.id.TvnotRegistered);

        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser mFirebaseUser = mFirebaseAuth.getCurrentUser();
                String cat = category.getText().toString().toLowerCase();
                if(mFirebaseUser!=null){
                    Toast.makeText(LoginActivity.this,"You are logged in!",Toast.LENGTH_SHORT).show();
                    if(cat.equals("doctor")){ Intent i = new Intent(LoginActivity.this, DoctorDashboard.class);
                        startActivity(i);
                    } else if (cat.equals("caretaker")){ Intent i = new Intent(LoginActivity.this, CaretakerDashboard.class);
                        startActivity(i);
                    } else if(cat.equals("patient")){ Intent i = new Intent(LoginActivity.this, PatientDashboard.class);
                        startActivity(i);
                    }
                } else {
                    Toast.makeText(LoginActivity.this,"Please Login!",Toast.LENGTH_SHORT).show();
                }
                }
            };

        btnsignIn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String email = emailId.getText().toString();
                String pass = password.getText().toString();
                String cat = category.getText().toString().toLowerCase();
                if(email.isEmpty())
                {
//                    Toast.makeText(LoginActivity.this,email,Toast.LENGTH_SHORT).show();
//                    Toast.makeText(MainActivity.this, "Fill Email!", Toast.LENGTH_SHORT).show();
                    emailId.setError("Please Fill Your Email");
                    emailId.requestFocus();
                } else if(pass.isEmpty())
                {
//                    Toast.makeText(MainActivity.this, "Fill Password", Toast.LENGTH_SHORT).show();
                    password.setError("Please Fill Your Password");
                    password.requestFocus();
                } else if(cat.isEmpty())
                {
//                    Toast.makeText(MainActivity.this, "Fill Category as per the norms", Toast.LENGTH_SHORT).show();
                    category.setError("Please Fill Your Category");
                    category.requestFocus();
                } else if(!(email.isEmpty() && pass.isEmpty() && cat.isEmpty())) {

                        mFirebaseAuth.signInWithEmailAndPassword(email,pass).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                String cat = category.getText().toString().toLowerCase();
                                if(!task.isSuccessful()){

                                    Toast.makeText(LoginActivity.this, "Login Error Occured!", Toast.LENGTH_SHORT).show();
                                } else if(task.isSuccessful() && cat.equals("doctor")) {
                                    startActivity(new Intent(LoginActivity.this, DoctorDashboard.class));
                                } else if(task.isSuccessful() && cat.equals("caretaker")) {
                                    startActivity(new Intent(LoginActivity.this,CaretakerDashboard.class));
                                } else if(task.isSuccessful() && cat.equals("patient")) {
                                    startActivity(new Intent(LoginActivity.this,PatientDashboard.class));
                                }
                            }
                        });
                    }
            }

        });

        TvnotRegistered.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginActivity.this,MainActivity.class);
                startActivity(i);
            }
        });

    }
    @Override
    protected void onStart(){
        super.onStart();
        mFirebaseAuth.addAuthStateListener(mAuthStateListener);
    }
}
