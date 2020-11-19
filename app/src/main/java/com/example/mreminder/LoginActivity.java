package com.example.mreminder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;


public class LoginActivity extends AppCompatActivity /*implements AdapterView.OnItemSelectedListener*/{
    EditText emailId,password;
    Button btnsignIn;
    TextView TvnotRegistered;
    FirebaseAuth mFirebaseAuth;
    FirebaseFirestore fStore;
    String cat="";
    String uid;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);

        mFirebaseAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        emailId=findViewById(R.id.emailId);
        password=findViewById(R.id.password);
        btnsignIn = findViewById(R.id.btnsignIn);
        TvnotRegistered = findViewById(R.id.TvnotRegistered);








        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser mFirebaseUser = mFirebaseAuth.getCurrentUser();
                if(mFirebaseUser!=null){
                    uid = mFirebaseAuth.getCurrentUser().getUid();
                    DocumentReference docRef = fStore.collection("users").document(uid);
                    docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                            if(documentSnapshot.exists())
                            {
                                cat = documentSnapshot.getString("category");
//                                Log.i("Cat",cat);
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(LoginActivity.this, "Some Error Occured! Please Try Again", Toast.LENGTH_SHORT).show();
                        }
                    });
//                    Toast.makeText(LoginActivity.this,"You are logged in!",Toast.LENGTH_SHORT).show();
                    if(cat.equals("doctor")){ Intent i = new Intent(LoginActivity.this, DoctorDashboard.class);
                        startActivity(i);
                    } else if (cat.equals("caretaker")){ Intent i = new Intent(LoginActivity.this, CaretakerDashboard.class);
                        startActivity(i);
                    } else if(cat.equals("patient")){ Intent i = new Intent(LoginActivity.this, PatientDashboard.class);
                        startActivity(i);
                    }
                }
                }
            };

        btnsignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailId.getText().toString();
                String pass = password.getText().toString();

                if(email.isEmpty())
                {
                    emailId.setError("Please Fill Your Email");
                    emailId.requestFocus();
                } else if(pass.isEmpty())
                {
                    password.setError("Please Fill Your Password");
                    password.requestFocus();
                }  else if(!(email.isEmpty() && pass.isEmpty())) {

                        mFirebaseAuth.signInWithEmailAndPassword(email,pass).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                uid = mFirebaseAuth.getCurrentUser().getUid();
//                                uid = "8UqkP5Be0eUhldSMYDGmKYwQFUX2";
                                DocumentReference docRef = fStore.collection("users").document(uid);
                                docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                    @Override
                                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                                        if(documentSnapshot.exists())
                                        {
                                            cat = documentSnapshot.getString("category");
                                        }
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(LoginActivity.this, "Some Error Occured! Please Try Again", Toast.LENGTH_SHORT).show();
                                    }
                                });

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

//    @Override
//    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//        String text = parent.getItemAtPosition(position).toString();
//        Toast.makeText(parent.getContext(), text, Toast.LENGTH_SHORT).show();
//    }

//    @Override
//    public void onNothingSelected(AdapterView<?> parent) {
//
//    }
}
