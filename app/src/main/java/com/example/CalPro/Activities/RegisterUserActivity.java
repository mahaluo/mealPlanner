package com.example.CalPro.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.CalPro.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterUserActivity extends AppCompatActivity {
    private static final String TAG = "RegisterUserActivity";
    public EditText emailEditText, passwordEditText, confirmPasswordEditText;
    Button registerBtn;
    TextView titleTextView;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: created register user activity");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);

        mAuth = FirebaseAuth.getInstance();

        emailEditText = findViewById(R.id.registerEmailEditText);
        passwordEditText = findViewById(R.id.registerPasswordEditText);
        confirmPasswordEditText = findViewById(R.id.confirmRegisterPasswordEditText);
        registerBtn = findViewById(R.id.registerAccountBtn);
        titleTextView = findViewById(R.id.registerUserTitleTextView);

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = emailEditText.getText().toString();
                String password = passwordEditText.getText().toString();
                String confirmPassword = confirmPasswordEditText.getText().toString();

                if (email.isEmpty()){
                    emailEditText.setError("Please enter an email address");
                } else if (password.isEmpty()){
                    passwordEditText.setError("Please enter a password");
                } else if (!password.equals(confirmPassword)){
                    confirmPasswordEditText.setText("");
                    confirmPasswordEditText.setError("Password does not match!");
                }
                else {
                    mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(RegisterUserActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()) {
                                Log.d(TAG, "onComplete: registered new user");
                                toastMessage("Registration successful!");
                                loginUser();
                            } else {
                                toastMessage("Registration failed, please try again");
                                Log.d(TAG, "onComplete: user authentication failed");
                            }
                        }
                    });
                }
            }
        });
    }

    public void loginUser(){
        Intent login = new Intent(this, LoginActivity.class);
        startActivity(login);
    }

    public void toastMessage(String message){
        Toast.makeText(RegisterUserActivity.this, message, Toast.LENGTH_SHORT).show();

    }
}
