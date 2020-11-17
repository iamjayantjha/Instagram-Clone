package com.instagramclone;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
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

public class RegisterActivity extends AppCompatActivity {

    EditText username, fullname, email, password;
    Button register;
    TextView txt_login;
    FirebaseAuth auth;
    DatabaseReference reference;
    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        username = findViewById(R.id.username);
        fullname = findViewById(R.id.fullname);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        register = findViewById(R.id.register);
        txt_login = findViewById(R.id.txt_login);
        auth = FirebaseAuth.getInstance();

        txt_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent login = new Intent(RegisterActivity.this,LoginActivity.class);
                login.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                login.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(login);
            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pd = new ProgressDialog(RegisterActivity.this);
                pd.setMessage("Please Wait..");
                pd.show();

                String str_username = username.getText().toString().trim();
                String str_fullname = fullname.getText().toString().trim();
                String str_email = email.getText().toString().trim();
                String str_password = password.getText().toString().trim();

                if (TextUtils.isEmpty(str_username)||TextUtils.isEmpty(str_fullname)|| TextUtils.isEmpty(str_email)||TextUtils.isEmpty(str_password)){
                    pd.dismiss();
                    Toast.makeText(RegisterActivity.this, "All fields are required!", Toast.LENGTH_SHORT).show();
                }else if (str_password.length() < 6){
                    pd.dismiss();
                    Toast.makeText(RegisterActivity.this, "Password must be more than 6 characters", Toast.LENGTH_LONG).show();
                }else {
                    register(str_username,str_fullname,str_email,str_password);
                }
            }
        });
    }

    private void register(final String username, final String fullname, String email, String password){
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    FirebaseUser firebaseUser = auth.getCurrentUser();
                    String userUid = firebaseUser.getUid();
                    reference = FirebaseDatabase.getInstance().getReference().child("Users").child(userUid);
                    HashMap<String, Object> hashMap = new HashMap<>();
                    hashMap.put("id",firebaseUser.getUid());
                    hashMap.put("name",fullname);
                    hashMap.put("username",username.toLowerCase());
                    hashMap.put("bio", "");
                    hashMap.put("imageURL","https://firebasestorage.googleapis.com/v0/b/vani-chat-9b86a.appspot.com/o/profile.jpg?alt=media&token=9f42e809-127c-49e2-9a53-351cd8d38104");
                    reference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                pd.dismiss();
                                Intent main = new Intent(RegisterActivity.this, MainActivity.class);
                                main.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                main.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(main);
                                finish();
                            }else {
                                pd.dismiss();
                                Toast.makeText(RegisterActivity.this, "Something went wrong! Please try later", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }
            }
        });
    }
}