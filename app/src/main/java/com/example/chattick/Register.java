package com.example.chattick;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Locale;

public class Register extends AppCompatActivity {

    private EditText username, email, mobile, password, conPassword;
    private Button btn_register;
    private TextView btn_login;

    DatabaseReference reference;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        init();
    }

    private void init(){

        username = findViewById(R.id.r_name);
        mobile = findViewById(R.id.r_mobile);
        email = findViewById(R.id.r_email);
        password = findViewById(R.id.r_password);
        conPassword = findViewById(R.id.r_passwordConfirmed);

        auth = FirebaseAuth.getInstance();
        btn_register = findViewById(R.id.r_registerBtn);
        btn_login = findViewById(R.id.r_loginBtn);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Register.this,Login.class);
                startActivity(i);
                finish();
            }
        });

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String fullNameTxt = username.getText().toString();
                final String emailTxt = email.getText().toString();
                final String phoneTxt = mobile.getText().toString();
                final String passwordTxt = password.getText().toString();
                final String conPasswordTxt = conPassword.getText().toString();

                if (fullNameTxt.isEmpty() || emailTxt.isEmpty() || phoneTxt.isEmpty() || passwordTxt.isEmpty()){
                    Toast.makeText(Register.this, "Please fill all fields", Toast.LENGTH_SHORT).show();

                } else if(passwordTxt.length()< 6){
                    Toast.makeText(Register.this, "password must at least 6 characters", Toast.LENGTH_SHORT).show();
                }else if (!passwordTxt.equals(conPasswordTxt)){
                    Toast.makeText(Register.this, "password must equals another password", Toast.LENGTH_SHORT).show();
                }else {
                    register(fullNameTxt,phoneTxt,emailTxt,passwordTxt);
                }
            }
        });

    }

    private void register(String username,String mobile,String email, String password){

        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    FirebaseUser firebaseUser = auth.getCurrentUser();
                    assert firebaseUser != null;
                    String userid = firebaseUser.getUid();

                    reference = FirebaseDatabase.getInstance().getReference("Users").child(userid);

                    HashMap<String,String> hashMap = new HashMap<>();
                    hashMap.put("id",userid);
                    hashMap.put("username",username);
                    hashMap.put("mobile",mobile);
                    hashMap.put("imageURL", "default");
                    hashMap.put("status", "offline");
                    hashMap.put("search", username.toLowerCase());

                    reference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                Intent intent = new Intent(Register.this, Home.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                                finish();
                            }
                        }
                    });


                } else {
                    Toast.makeText(Register.this, "email or password is wrong", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}