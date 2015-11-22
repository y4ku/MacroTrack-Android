package tech.fabricate.macrotrack.rest.service;

import retrofit.Callback;
import retrofit.http.POST;
import retrofit.http.Path;
import tech.fabricate.macrotrack.rest.model.User;

/**
 * Created by y4ku on 11/22/15.
 */
public interface LoginService {
    @POST("/users")
    User loginUser(
        @Path("username") String username,
        @Path("password") String password
    );
}
