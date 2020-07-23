package br.com.nivlabs.gp.util;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * Classe StringUtils.java
 * 
 * @author <a href="mailto:viniciosarodrigues@gmail.com">Vinícios Rodrigues</a>
 * 
 * @since 7 de set de 2019
 */
public abstract class StringUtils {

    public static final String BRASIL_DATE = "dd/MM/yyyy";
    public static final String BRASIL_DATE_TIME = "dd/MM/yyyy HH:mm";

    private StringUtils() {
    }

    public static boolean isNullOrEmpty(String value) {
        return value == null || value.trim().isEmpty();
    }

    public static String getDigits(String str) {
        if (StringUtils.isNullOrEmpty(str)) {
            return str;
        }
        final int sz = str.length();
        final StringBuilder strDigits = new StringBuilder(sz);
        for (int i = 0; i < sz; i++) {
            final char tempChar = str.charAt(i);
            if (Character.isDigit(tempChar)) {
                strDigits.append(tempChar);
            }
        }
        return strDigits.toString();
    }

    /**
     * Checa que a String é numérica para gecagem de identificadores
     * 
     * @param str
     * @return
     */
    public static boolean isNumeric(String str) {
        try {
            Long.parseLong(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * Formata as datas em String
     * 
     * @param date
     * @param format
     * @return
     */
    public static String dateFormat(Date date, String format) {
        return new SimpleDateFormat(format).format(date);
    }
    
	public static LocalDateTime convertStringToDate(String stringDate) {
		DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S");
        return LocalDateTime.parse(stringDate, format);
	}

}