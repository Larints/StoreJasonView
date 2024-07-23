package org.larin.springmvcjv.service;

import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class UserValidation {

    private static final String PHONE_VERIFICATION = "^(\\(\\d{3}\\)|\\d{3})[- .]?\\d{3}[- .]?\\d{4}$";

    private static final String EMAIL_VERIFICATION = "^([\\w-\\.]+){1,64}@([\\w&&[^_]]+){2,255}.[a-z]{2,}$";

    private static final Pattern phonePattern = Pattern.compile(PHONE_VERIFICATION);

    private static final Pattern emailPattern = Pattern.compile(EMAIL_VERIFICATION);


    public static boolean validateEmail(String email) {
        Matcher matcher = emailPattern.matcher(email);
        return matcher.matches();
    }

    public static boolean validatePhone(String phoneNumber) {
        Matcher matcher = phonePattern.matcher(phoneNumber);
        return matcher.matches();
    }


}
