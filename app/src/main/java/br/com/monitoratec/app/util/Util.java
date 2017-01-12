package br.com.monitoratec.app.util;

import android.content.Context;
import android.support.design.widget.TextInputLayout;
import android.text.TextUtils;
import android.widget.EditText;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import br.com.monitoratec.app.R;

/**
 * Created by elder-dell on 2017-01-11.
 */

public final class Util {

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

    public static  boolean validateRequiredFields(Context context,TextInputLayout... fields){

        boolean isValid = true;

        for (TextInputLayout field  : fields){
            EditText editText = field.getEditText();
            if(editText != null){

                if(TextUtils.isEmpty(editText.getText())){

                    isValid = false;
                    field.setErrorEnabled(true);
                    field.setError(context.getString(R.string.txt_required));
                } else {
                    field.setErrorEnabled(false);
                    field.setError(null);
                }

            } else {
                throw new RuntimeException("The TextInputLayout must have an EditText");
            }

        }
        return isValid;
    }


}
