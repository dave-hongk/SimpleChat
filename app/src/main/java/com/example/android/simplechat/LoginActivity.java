package com.example.android.simplechat;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final EditText emailEditText = findViewById(R.id.edit_text_email);
        final EditText passwordEditText = findViewById(R.id.edit_text_password);
        Button logInButton = findViewById(R.id.button_login);

        logInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean valid = true;

                String emailStr = emailEditText.getText().toString();
                if (!validateEmail(emailStr)) {
                    valid = false;
                    emailEditText.setError("Invalid email address.");
                }

                String passwordStr = passwordEditText.getText().toString();
                if (!validatePassword(passwordStr)) {
                    valid = false;
                    passwordEditText.setError("Invalid password.");
                }

                if (valid) {
                    logIn(emailStr, passwordStr);
                }
            }
        });

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
    }

    private void logIn(String emailStr, String passwordStr) {
        // Start sign_in_with_email
        mAuth.signInWithEmailAndPassword(emailStr, passwordStr)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success
                            finish();
                        } else {
                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    // TODO: update password validation thoroughly
    private boolean validatePassword(String passwordStr) {
        return !TextUtils.isEmpty(passwordStr);
    }

    // TODO: validate email thoroughly
    private boolean validateEmail(String emailStr) {
        emailStr = emailStr.trim();
        return (!TextUtils.isEmpty(emailStr) &&
                Patterns.EMAIL_ADDRESS.matcher(emailStr).matches());
    }
}
