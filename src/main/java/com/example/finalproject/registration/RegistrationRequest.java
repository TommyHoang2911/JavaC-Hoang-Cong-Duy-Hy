package com.example.finalproject.registration;

import lombok.*;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Setter
@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class RegistrationRequest {

    private final static String REGEX_EMAIL = "^[a-zA-Z0-9_+&*-]+(?:\\."+ "[a-zA-Z0-9_+&*-]+)*@" + "(?:[a-zA-Z0-9-]+\\.)+[a-z" + "A-Z]{2,7}$";
    private final static String REGEX_PASSWORD = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{8,}$";

    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String rePassword;

    public RegistrationRequest() {
    }

    public Boolean checkEmailPattern(String email){
        Pattern pattern = Pattern.compile(REGEX_EMAIL);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public Boolean checkPassword(String password){
        Pattern pattern = Pattern.compile(REGEX_PASSWORD);
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }
}
