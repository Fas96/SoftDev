package com.fas.smash_k;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class LoginActivity extends Activity implements View.OnClickListener {

    private EditText emailEditTxt;
    private EditText passwordEditTxt;
    private TextView forgotPassword;
    private TextView newUserRegister;
    private Button loginUserBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        emailEditTxt=(EditText) findViewById(R.id.editTextEmail);
        passwordEditTxt= (EditText) findViewById(R.id.editTextPassword);
        forgotPassword= (TextView) findViewById(R.id.forgot_pass_textV);
        newUserRegister= (TextView) findViewById(R.id.dont_have_register_tv);
        loginUserBtn=(Button) findViewById(R.id.cirLoginButton);

        //set listeners
        loginUserBtn.setOnClickListener(this);
        newUserRegister.setOnClickListener(this);

        System.out.println("FAS lOGIN");
    }

    @Override
    public void onClick(View v) {
        long id =v.getId();
        if(id==R.id.cirLoginButton){
            //check email and password then if it exit go to MainActivity
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }else if(id==R.id.dont_have_register_tv){
            //send user to register
            startActivity(new Intent(this, RegisterActivity.class));
            finish();

        }
    }
}
