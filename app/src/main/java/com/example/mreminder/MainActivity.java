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

public class MainActivity extends AppCompatActivity {
    EditText emailId,password,category;
    Button btnsignUp;
    TextView TvalreadyRegistered;
    FirebaseAuth mFirebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mFirebaseAuth = FirebaseAuth.getInstance();
        emailId=findViewById(R.id.emailId);
        password=findViewById(R.id.password);
        category=findViewById(R.id.category);
        btnsignUp = findViewById(R.id.btnsignUp);
        TvalreadyRegistered = findViewById(R.id.TvalreadyRegistered);
        btnsignUp.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                String email = emailId.getText().toString();
                String pass = password.getText().toString();
                String cat = category.getText().toString().toLowerCase();
                if(email.isEmpty())
                {
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
                }else if(!(email.isEmpty() && pass.isEmpty() && cat.isEmpty())) {
                    mFirebaseAuth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
//                            String cat = category.getText().toString().toLowerCase();
                            if(!(task.isSuccessful())){
//                                Log.w("signInWithEmail:failure", task.getException());
                                Toast.makeText(MainActivity.this, "Error Occured!", Toast.LENGTH_SHORT).show();
                            } else {
                                startActivity(new Intent(MainActivity.this,LoginActivity.class));
                            }
                        }
                    });
                }

            }
        });

        TvalreadyRegistered.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this,LoginActivity.class);
                startActivity(i);

            }
        });

    }
}