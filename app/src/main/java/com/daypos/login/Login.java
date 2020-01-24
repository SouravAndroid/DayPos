package com.daypos.login;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.daypos.R;
import com.daypos.container.Container;
import com.daypos.forgotpassword.ForgotPassword;
import com.daypos.network.ApiConstant;
import com.daypos.network.GetDataParser;
import com.daypos.network.PostDataParser;
import com.daypos.registration.Registration;

import org.json.JSONObject;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import es.dmoral.toasty.Toasty;

public class Login extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.tv_forgot_password) TextView tv_forgot_password;
    @BindView(R.id.edt_username) EditText edt_username;
    @BindView(R.id.edt_password) EditText edt_password;
    @BindView(R.id.btn_login) Button btn_login;
    @BindView(R.id.btn_register) TextView btn_register;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);


        btn_login.setOnClickListener(this);
        btn_register.setOnClickListener(this);
        tv_forgot_password.setOnClickListener(this);


        String email = "admin@loyverse.com";
        String pass = "123456";

        edt_username.setText(email);
        edt_password.setText(pass);

    }

    @Override
    public void onClick(View v) {

        Intent intent = null;

        switch (v.getId()){

            case R.id.btn_login:

                checkValidate();

                break;

            case R.id.btn_register:

                intent = new Intent(Login.this, Registration.class);
                startActivity(intent);

                break;


            case R.id.tv_forgot_password:

                intent = new Intent(Login.this, ForgotPassword.class);
                startActivity(intent);

                break;
        }

    }

    private static boolean isValidEmail(String email) {
        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }
    private boolean validateEmail() {
        String email = edt_username.getText().toString().trim();

        if (email.isEmpty()) {
            Toasty.info(getApplicationContext(),
                    "Enter your email id",
                    Toast.LENGTH_SHORT, true).show();
            requestFocus(edt_username);
            return false;
        }

        if (!isValidEmail(email)){
            Toasty.info(getApplicationContext(),
                    "Enter your valid email id",
                    Toast.LENGTH_SHORT, true).show();
            requestFocus(edt_username);
            return false;
        }

        return true;
    }



    private void checkValidate(){

        if (!validateEmail()){
            return;
        }

        if (edt_password.getText().toString().isEmpty()){
            Toasty.info(getApplicationContext(),
                    "Enter password",
                    Toast.LENGTH_SHORT, true).show();
            return;
        }


        userlogin(edt_username.getText().toString(), edt_password.getText().toString());
    }



    public void userlogin(String email, String pass) {


        String device_id = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);

        String url = ApiConstant.login;

        HashMap<String, String> params = new HashMap<>();
        params.put("email", email);
        params.put("password", pass);
        params.put("device_type", "android");
        params.put("fcm_token", "funky");
        params.put("device_id", device_id);


        new PostDataParser(Login.this, url, params, true,
                new PostDataParser.OnGetResponseListner() {
            @Override
            public void onGetResponse(JSONObject response) {
                if (response != null) {

                    try {
                        int status = response.optInt("status");
                        String message = response.optString("message");
                        if (status == 1) {


                        } else {

                            Toasty.info(getApplicationContext(),
                                    message,
                                    Toast.LENGTH_SHORT, true).show();
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            }
        });
    }
}
