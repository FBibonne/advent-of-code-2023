package bib.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class StringUtils {

    private StringUtils(){}

    public static List<String> findAllWithOverLap(String string, Pattern search) {
        List<String> retour = new ArrayList<>(string.length());
        var matcher = search.matcher(string);
        var start = 0;
        while (start < string.length() && matcher.find(start)) {
            retour.add(matcher.group());
            start = matcher.start() + 1;
        }
        return retour;
    }

}
