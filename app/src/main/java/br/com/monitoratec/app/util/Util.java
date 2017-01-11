package br.com.monitoratec.app.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by elder-dell on 2017-01-11.
 */

public class Util {

    private static final String EMAIL_PATTERN = "^[a-zA-Z0-9#_~!$&'()*+,;=:.\"(),:;<>@\\[\\]\\\\]+@[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)*$";
    private static Pattern pattern = Pattern.compile(EMAIL_PATTERN);
    private static Matcher matcher;

    public static boolean validateEmail(String email) {
        matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public static boolean validatePassword(String password){
        return (password.length() > 6) && (password.length() < 15);
    }
}
