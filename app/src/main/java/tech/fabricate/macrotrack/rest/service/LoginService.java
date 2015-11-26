package tech.fabricate.macrotrack.rest.service;

import retrofit.Call;
import retrofit.http.Body;
import retrofit.http.POST;
import tech.fabricate.macrotrack.rest.requests.LoginRequest;
import tech.fabricate.macrotrack.rest.model.User;

/**
 * Created by y4ku on 11/22/15.
 */
public interface LoginService {
    @POST("login")
    Call<User> loginUser(@Body LoginRequest request);
}
