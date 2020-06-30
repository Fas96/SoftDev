package com.fas.smash_k;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.UriMatcher;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.fas.smash_k.ui.RequestHandler;
import com.fas.smash_k.ui.helpers.Constants;
import com.fas.smash_k.ui.models.app.UserLocal;
import com.fas.smash_k.ui.sharedPrefManager.SharedPrefManager;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends Activity implements View.OnClickListener {

    private EditText emailEditTxt,passwordEditTxt;

    private TextView newUserRegister;

    private Button loginUserBtn;
    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login );

        if(SharedPrefManager.getInstance(this).isLoggedIn()){
            finish();
            startActivity(new Intent(this, MainActivity.class));
            return;
        }


        emailEditTxt=(EditText) findViewById(R.id.editTextEmail);
        passwordEditTxt= (EditText) findViewById(R.id.editTextPassword);


        newUserRegister= (TextView) findViewById(R.id.dont_have_register_tv);
        loginUserBtn=(Button) findViewById(R.id.cirLoginButton);

        //progress dialog
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setMessage("Please wait...");

        //set listeners
        loginUserBtn.setOnClickListener(this);
        newUserRegister.setOnClickListener(this);



        System.out.println("FAS lOGIN");
    }
    private void userLogin(){
        final String username = emailEditTxt.getText().toString().trim();
        final String password = passwordEditTxt.getText().toString().trim();



        mProgressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.URL_LOGIN, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                mProgressDialog.dismiss();


                Gson gson = new Gson();


                Toast.makeText(getApplicationContext(), response.toString(), Toast.LENGTH_LONG).show();
                UserLocal b = gson.fromJson(response.toString(), UserLocal.class);
                //UserLocal b = new UserLocal(false,1,"trek","trek");
                if (b.getError().toString().equals("false")) {

                    SharedPrefManager.getInstance(getApplicationContext())
                            .userLogin(
                                    b.getId(),
                                    b.getUsername(),
                                    b.getEmail()
                            );
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    finish();
                } else {
                    Toast.makeText(
                            getApplicationContext(),
                            b.getUsername() + "fas",
                            Toast.LENGTH_LONG
                    ).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                mProgressDialog.dismiss();

                Toast.makeText(
                        getApplicationContext(),
                        error.getMessage()+"login error",
                        Toast.LENGTH_LONG
                ).show();
            }
        }
        )
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("username", username);
                params.put("password", password);
                return params;
            }
        };

        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);

    }


    @Override
    public void onClick(View v) {
        long id =v.getId();
        if(id==R.id.cirLoginButton){
          userLogin();
        }else if(id==R.id.dont_have_register_tv){
            //send user to register
            startActivity(new Intent(this, RegisterActivity.class));
            finish();

        }
    }
}

