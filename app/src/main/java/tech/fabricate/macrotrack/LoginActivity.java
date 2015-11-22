package tech.fabricate.macrotrack;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.IOException;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;
import tech.fabricate.macrotrack.rest.ServiceGenerator;
import tech.fabricate.macrotrack.rest.model.LoginRequest;
import tech.fabricate.macrotrack.rest.model.User;
import tech.fabricate.macrotrack.rest.service.LoginService;

public class LoginActivity extends AppCompatActivity {

    private static final int REQUEST_SIGNUP = 0;

    private EditText emailInput;
    private EditText passwordInput;
    private Button loginButton;
    private TextView signUpButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        emailInput = (EditText) findViewById(R.id.loginEmail);
        passwordInput = (EditText) findViewById(R.id.loginPassword);
        loginButton = (Button) findViewById(R.id.loginButton);
        signUpButton = (TextView) findViewById(R.id.signUpButton);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login(emailInput.getText().toString(), passwordInput.getText().toString());
            }
        });

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SignupActivity.class);
                startActivityForResult(intent, REQUEST_SIGNUP);
            }
        });

    }

    public void login(String username, String password) {
        final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Authenticating...");
        progressDialog.show();

        LoginService service = ServiceGenerator.createService(LoginService.class);
        Call<User> callService = service.loginUser(new LoginRequest(username, password));
        callService.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Response<User> response, Retrofit retrofit) {
                if (response.isSuccess()) {
                        Log.d("Retrofit", response.body().getMessage());
                } else {
                    // error response, no access to resource?
                }
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Throwable t) {
                //something went completely south (like no internet connection)
                Log.d("Error", t.getMessage());
                progressDialog.dismiss();
            }
        });
    }

}
