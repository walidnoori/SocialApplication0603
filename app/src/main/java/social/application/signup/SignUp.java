package social.application.signup;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
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


import social.application.R;
import social.application.login.Login;
import social.application.login.Login;
import social.application.main.MainActivity;

public class SignUp extends AppCompatActivity implements View.OnClickListener{

    private FirebaseAuth mAuth;
    private EditText emailTxt, pwdTxt;
    private Button signupBtn;
    private TextView loginText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mAuth = FirebaseAuth.getInstance();

        //email and pass word fields
        emailTxt = (EditText) findViewById(R.id.emailTxt);
        pwdTxt = (EditText) findViewById(R.id.pwdTxt);
        loginText = (TextView) findViewById(R.id.loginText);

        //sign up button
        findViewById(R.id.signupBtn).setOnClickListener(this);
        loginText.setOnClickListener(this);
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        //updateUI(currentUser);
    }

    public void goToLoginPage(){
        startActivity(new Intent(SignUp.this, Login.class));
        finish();
    }

    public void registerUser(String email, String password){

        if(!validate()){
            return;
        }

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            //go to another page
                            startActivity(new Intent(SignUp.this, MainActivity.class));
                            finish();
                        }else{
                            Toast.makeText(SignUp.this, "User sign up failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    @Override
    public void onClick(View view) {
        int i = view.getId();
        if (i == R.id.signupBtn) {
            registerUser(emailTxt.getText().toString(), pwdTxt.getText().toString());
        }else if(i == R.id.loginText){
            goToLoginPage();
        }


    }

    public boolean validate(){
        boolean valid = true;

        String emailField = emailTxt.getText().toString();
        if(TextUtils.isEmpty(emailField)){
            emailTxt.setError("Required.");
            valid = false;
        }else {
            emailTxt.setError(null);
        }

        String pwd = pwdTxt.getText().toString();
        if(TextUtils.isEmpty(pwd)){
            pwdTxt.setError("Required.");
            valid = false;
        }else {
            pwdTxt.setError(null);
        }

        return valid;
    }
}
