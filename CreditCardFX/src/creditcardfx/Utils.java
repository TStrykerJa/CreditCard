package creditcardfx;

import java.text.DecimalFormat;

public class Utils {

    static String fileLocation = "C:\\Users\\\\Tree\\Desktop\\CreditCard\\list.csv";

    public static String capitalizedFirstChar(String s) {
        return s.substring(0, 1).toUpperCase() + s.substring(1).toLowerCase();
    }

    public static String removeAllNonNumeric(String s) {
        return s.replaceAll("[\\D]", "");
    }

    public static String moneyFormatCommaAndDecimal(String in) {
        double amount = Double.parseDouble(in);
        DecimalFormat formatter = new DecimalFormat("#,###.00");
        return formatter.format(amount);
    }

    public static String moneyFormatGetRidOfComma(String in) {
        String out = in.replaceAll("[$,]", "");
        return out;
    }

    public static boolean isAlpha(String name) {
        char[] chars = name.toCharArray();

        for (char c : chars) {
            if (!Character.isLetter(c)) {
                return false;
            }
        }

        return true;
    }
    

}
