package project.hotelsync.com.hotelsync;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * Created by Joyli on 2018-06-03.
 */

public class UserSignUp extends AppCompatActivity {
    EditText email, roomNumber, password;
    Button signUp;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;


    private void registerUser() {
        email = (EditText) findViewById(R.id.signUpEmail);
        String inputEmail = email.toString();

        roomNumber = (EditText) findViewById(R.id.signUpRoomNumber);
        String inputRoomNumber = roomNumber.toString();

        password = (EditText) findViewById(R.id.signUpPassword);
        String inputPassword = password.toString();

        if (inputEmail.isEmpty()) {
            email.setError("Email is required");
            email.requestFocus();
            return;
        }
        //checking for valid email address

        if (!Patterns.EMAIL_ADDRESS.matcher(inputEmail).matches()) {
            email.setError("Enter a Valid Email");
            email.requestFocus();
            return;
        }

        //check if password has less than 6 characters
        if (password.length() < 6) {
            password.setError("Minimum length of password is 6");
            password.requestFocus();
            return;
        }

        if (inputPassword.isEmpty()) {
            password.setError("Password is required");
            password.requestFocus();
        }

        mAuth.createUserWithEmailAndPassword(inputEmail, inputPassword)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d("signing up user", "createUserWithEmail:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Toast.makeText(UserSignUp.this, "Sign-in Failed",
                                    Toast.LENGTH_SHORT).show();
                        }

                        // ...
                    }
                });


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up);
        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d("logging ", "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    // User is signed out
                    Log.d("logging", "onAuthStateChanged:signed_out");
                }
                // ...
            }
        };

        signUp = (Button)findViewById(R.id.signUpButton);
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerUser();
            }
        });


    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }

    }
}
