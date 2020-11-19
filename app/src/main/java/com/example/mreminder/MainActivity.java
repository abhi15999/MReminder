package com.example.mreminder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.Console;
import java.util.HashMap;
import java.util.Map;


public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    EditText emailId,password;
    Button btnsignUp;
    TextView TvalreadyRegistered;
    FirebaseAuth mFirebaseAuth;
    FirebaseFirestore fStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        final Spinner category = findViewById(R.id.spinner_main);
        mFirebaseAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        emailId=findViewById(R.id.emailId);
        password=findViewById(R.id.password);
        btnsignUp = findViewById(R.id.btnsignUp);
        TvalreadyRegistered = findViewById(R.id.TvalreadyRegistered);


        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.Job_Array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        category.setOnItemSelectedListener(this);
        category.setAdapter(adapter);

        btnsignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailId.getText().toString();
                String pass = password.getText().toString();
                final String cat = category.getSelectedItem().toString().toLowerCase();
                if(email.isEmpty())
                {
//                    Toast.makeText(MainActivity.this, "Fill Email!", Toast.LENGTH_SHORT).show();
                    emailId.setError("Please Fill Your Email");
                    emailId.requestFocus();
                } else if(pass.isEmpty())
                {
                    password.setError("Please Fill Your Password");
                    password.requestFocus();
                } else if(cat.isEmpty()){

                }else if(!(email.isEmpty() && pass.isEmpty() && cat.isEmpty())) {
                    mFirebaseAuth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if(!(task.isSuccessful())){
                                Toast.makeText(MainActivity.this, "Error Occured!", Toast.LENGTH_SHORT).show();
                            } else {

                                String uId= mFirebaseAuth.getCurrentUser().getUid();
                                String cat = category.getSelectedItem().toString().toLowerCase();
                                Map<String,Object>user = new HashMap<>();
                                user.put("category",cat);
                                fStore.collection("users").document(uId)
                                        .set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    String uId= mFirebaseAuth.getCurrentUser().getUid();
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Log.d("TAG","Stored Succesfully for"+uId);
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Log.w("TAG", "Error writing document", e);
                                    }
                                });
//                                Toast.makeText(MainActivity.this,uId,Toast.LENGTH_SHORT).show();

//
                                Intent intent = new Intent(MainActivity.this,LoginActivity.class);
//                                intent.putExtra("CATEGORY", cat);
                                startActivity(intent);
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

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String text = parent.getItemAtPosition(position).toString();
        Toast.makeText(parent.getContext(), text, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}