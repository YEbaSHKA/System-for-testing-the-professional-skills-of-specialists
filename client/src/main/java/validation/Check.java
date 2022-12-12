package validation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Check {
    // public static boolean checkInt(String str) {
    //     Pattern r = Pattern.compile("[\\\\d]+");
    //     Matcher m = r.matcher(str);
    //     return m.matches();
    // }

    // public static boolean checkDouble(String str) {
    //     Pattern r = Pattern.compile("[+-]?([0-9]*[.])?[0-9]+");
    //     Matcher m = r.matcher(str);
    //     return m.matches();
    // }

    public static boolean checkLoginAndPass(String login, String password ) {
        Pattern regex = Pattern.compile("\\w+");
        Matcher loginMatcher = regex.matcher(login);
        Matcher passwordMatcher = regex.matcher(password);
        return loginMatcher.matches() && passwordMatcher.matches();
    }

    public static boolean checkFullName(String full_name) {
        Pattern regex = Pattern.compile("[А-Яа-я]+\\s[А-Яа-я]+\\s[А-Яа-я]+");
        Matcher fullNameMatcher = regex.matcher(full_name);
        return fullNameMatcher.matches();
    }
    // public static boolean checkLogin(String login)
    // {
    //     Pattern r = Pattern.compile()
    // }
}
