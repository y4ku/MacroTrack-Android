package tech.fabricate.macrotrack;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import tech.fabricate.macrotrack.rest.ServiceGenerator;
import tech.fabricate.macrotrack.rest.model.User;
import tech.fabricate.macrotrack.rest.service.LoginService;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void login(View view) {
        // Create a very simple REST adapter which points the GitHub API endpoint.
        LoginService service = ServiceGenerator.createService(LoginService.class);

        String username = findViewById(R.id.loginEmail).toString();
        String password = findViewById(R.id.loginPassword).toString();


        User user = service.loginUser(username, password);

        System.out.println(user);
        //Log.i("Service Response", user);
    }

}
