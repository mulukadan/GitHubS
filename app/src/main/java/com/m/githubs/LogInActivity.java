package com.m.githubs;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.m.githubs.controller.SearchActivity;

public class LogInActivity extends AppCompatActivity implements View.OnClickListener{
    private FirebaseAuth mAuth;
    private EditText emailEV, passwordEv;
    private Button logInBtn;
    private TextView titleTV, actionTV;
    ProgressDialog pd;
    public int Action = 1;//1 to creare new account, 0 for back to login
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        mAuth = FirebaseAuth.getInstance();
        emailEV = (EditText)findViewById(R.id.emailET);
        passwordEv = (EditText)findViewById(R.id.passwordET);

        logInBtn = (Button)findViewById(R.id.login_btn);
        logInBtn.setOnClickListener(this);

        titleTV = (TextView)findViewById(R.id.title);

        actionTV = (TextView)findViewById(R.id.toCreateAcc);
        actionTV.setOnClickListener(this);

        pd = new ProgressDialog(this);
        pd.setCancelable(false);
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            finish();
            Intent intent = new Intent(LogInActivity.this, SearchTabActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.login_btn:
                getUserDetails();
                break;
            case R.id.toCreateAcc:
                if(Action == 1){
                    titleTV.setText("Create GitHubS Account");
                    actionTV.setText("You have Account? login here");
                    logInBtn.setText("Create");
                    Action = 0;
                }
                else{
                    titleTV.setText("LogIn to GitHubS");
                    actionTV.setText("Don't have Account? Create here");
                    logInBtn.setText("LogIn");
                    Action = 1;
                }
                break;
        }
    }

    public void getUserDetails(){
        String Email = emailEV.getText().toString().trim();
        String password = passwordEv.getText().toString().trim();
        if(Email.isEmpty()){
            emailEV.setError("Email is required");
            emailEV.requestFocus();

            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(Email).matches()){
            emailEV.setError("Please enter a valid Email");
            emailEV.requestFocus();
            return;
        }
        if(password.isEmpty()){
            passwordEv.setError("Password is required");
            passwordEv.requestFocus();
            return;
        }
        if(password.length()<6){
            passwordEv.setError("Password should be atleast 6 characters");
            passwordEv.requestFocus();
            return;
        }
        if(Action == 1){
            pd.setMessage("Logging in ..");
            pd.show();
            LoginUser(Email, password);

        }else{
            pd.setMessage("Creating Account...");
            pd.show();
            registerUser(Email, password);
        }


    }

    public void registerUser(final String Email, String password){
        mAuth.createUserWithEmailAndPassword(Email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            pd.hide();
                            finish();
                            Toast.makeText(getApplicationContext(),"User registered", Toast.LENGTH_SHORT ).show();
                            FirebaseUser user = mAuth.getCurrentUser();
                            Intent intent = new Intent(LogInActivity.this, SearchTabActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
//                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
//                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            pd.hide();
                            if(task.getException() instanceof FirebaseAuthUserCollisionException){
                                Toast.makeText(LogInActivity.this, Email+": is already registered!!",
                                        Toast.LENGTH_SHORT).show();
                            }else {
                                Toast.makeText(LogInActivity.this, task.getException().getMessage(),
                                        Toast.LENGTH_SHORT).show();
                            }
//                            updateUI(null);
                        }

                        // ...
                    }
                });
    }

    public void LoginUser(String Email, String password){
        mAuth.signInWithEmailAndPassword(Email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            pd.hide();
                            finish();
                            Toast.makeText(getApplicationContext(),"Login successful", Toast.LENGTH_SHORT ).show();
                            FirebaseUser user = mAuth.getCurrentUser();
                            Intent intent = new Intent(LogInActivity.this, SearchTabActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
//                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            pd.hide();
                            Toast.makeText(getApplicationContext(),"Wrong Email or Password", Toast.LENGTH_SHORT ).show();
//                            updateUI(null);
                        }

                        // ...
                    }
                });
    }
}
