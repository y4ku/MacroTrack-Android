package tech.fabricate.macrotrack.rest.model;

/**
 * Created by y4ku on 11/22/15.
 */
public class User extends BaseModel{
    private String email;
    private String firstName;
    private String lastName;
    private String token;

    public String getEmail() {
        return email;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getToken() {
        return token;
    }

}
