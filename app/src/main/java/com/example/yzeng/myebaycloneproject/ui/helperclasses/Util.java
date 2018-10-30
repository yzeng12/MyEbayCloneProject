package com.example.yzeng.myebaycloneproject.ui.helperclasses;

public class Util {
    private static Util utilInstance;

    public static synchronized Util getUtilInstance(){
        utilInstance = new Util();
        return utilInstance;
    }

    public boolean isValidMobile(String phone) {
        return android.util.Patterns.PHONE.matcher(phone).matches();
    }

}
