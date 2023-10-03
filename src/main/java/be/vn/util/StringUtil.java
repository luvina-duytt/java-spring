package be.vn.util;

/**
 * StringUtil class
 *
 * @author TuanDV
 */
public class StringUtil {
    /**
     * Check String is null or empty
     *
     * @param str String
     * @return true String input is null or length = 0
     */
    public static boolean isEmpty(String str) {
        return (str == null || str.trim().length() <= 0);
    }
}
