package in.co.rays.proj4.util;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * The {@code DataUtility} class provides utility methods for data type
 * conversions, validations, and formatting operations commonly used
 * throughout the application.
 * <p>
 * It handles conversions between {@link String}, {@link Integer},
 * {@link Long}, {@link Date}, and {@link Timestamp}, as well as date
 * formatting and parsing operations.
 * </p>
 *
 * @author Rishabh Shrivastava
 * @version 1.0
 * @since 2025
 */
public class DataUtility {

    /** Application default date format (dd-MM-yyyy). */
    public static final String APP_DATE_FORMAT = "dd-MM-yyyy";

    /** Application date-time format (dd-MM-yyyy HH:mm:ss). */
    public static final String APP_TIME_FORMAT = "dd-MM-yyyy HH:mm:ss";

    private static final SimpleDateFormat formatter = new SimpleDateFormat(APP_DATE_FORMAT);
    private static final SimpleDateFormat timeFormatter = new SimpleDateFormat(APP_TIME_FORMAT);

    /**
     * Returns a trimmed version of the input string.
     *
     * @param val the input string
     * @return trimmed string if not null, otherwise returns null
     */
    public static String getString(String val) {
        if (DataValidator.isNotNull(val)) {
            return val.trim();
        } else {
            return val;
        }
    }

    /**
     * Converts an {@link Object} to its {@link String} representation.
     *
     * @param val the object to convert
     * @return the string value of the object, or an empty string if null
     */
    public static String getStringData(Object val) {
        if (val != null) {
            return val.toString();
        } else {
            return "";
        }
    }

    /**
     * Converts a {@link String} to an {@code int}.
     *
     * @param val the string to convert
     * @return integer value if valid, otherwise returns 0
     */
    public static int getInt(String val) {
        if (DataValidator.isInteger(val)) {
            return Integer.parseInt(val);
        } else {
            return 0;
        }
    }

    /**
     * Converts a {@link String} to a {@code long}.
     *
     * @param val the string to convert
     * @return long value if valid, otherwise returns 0
     */
    public static long getLong(String val) {
        if (DataValidator.isLong(val)) {
            return Long.parseLong(val);
        } else {
            return 0;
        }
    }

    /**
     * Parses a {@link String} into a {@link Date} object using
     * the default date format (dd-MM-yyyy).
     *
     * @param val the string representation of the date
     * @return parsed {@link Date} object, or null if parsing fails
     */
    public static Date getDate(String val) {
        Date date = null;
        try {
            date = formatter.parse(val);
        } catch (Exception e) {
        }
        return date;
    }

    /**
     * Converts a {@link Date} to a formatted string.
     *
     * @param date the date to format
     * @return formatted date string, or an empty string if null
     */
    public static String getDateString(Date date) {
        try {
            return formatter.format(date);
        } catch (Exception e) {
        }
        return "";
    }

    /**
     * Converts a {@link String} to a {@link Timestamp} using
     * the default date-time format (dd-MM-yyyy HH:mm:ss).
     *
     * @param val the string to convert
     * @return timestamp object, or null if parsing fails
     */
    public static Timestamp getTimestamp(String val) {
        Timestamp timeStamp = null;
        try {
            timeStamp = new Timestamp((timeFormatter.parse(val)).getTime());
        } catch (Exception e) {
            return null;
        }
        return timeStamp;
    }

    /**
     * Converts a time in milliseconds to a {@link Timestamp}.
     *
     * @param l the long value representing milliseconds
     * @return timestamp object, or null if conversion fails
     */
    public static Timestamp getTimestamp(long l) {
        Timestamp timeStamp = null;
        try {
            timeStamp = new Timestamp(l);
        } catch (Exception e) {
            return null;
        }
        return timeStamp;
    }

    /**
     * Returns the current system time as a {@link Timestamp}.
     *
     * @return current timestamp
     */
    public static Timestamp getCurrentTimestamp() {
        Timestamp timeStamp = null;
        try {
            timeStamp = new Timestamp(new Date().getTime());
        } catch (Exception e) {
        }
        return timeStamp;
    }

    /**
     * Converts a {@link Timestamp} to milliseconds.
     *
     * @param tm the timestamp
     * @return time in milliseconds, or 0 if null
     */
    public static long getTimestamp(Timestamp tm) {
        try {
            return tm.getTime();
        } catch (Exception e) {
            return 0;
        }
    }

    /**
     * Main method to test all utility functions.
     *
     * @param args command line arguments
     */
    public static void main(String[] args) {

        System.out.println("getString Test:");
        System.out.println("Original: '  Hello World  ' -> Trimmed: '" + getString("  Hello World  ") + "'");
        System.out.println("Null input: " + getString(null));

        System.out.println("\ngetStringData Test:");
        System.out.println("Object to String: " + getStringData(1234));
        System.out.println("Null Object: '" + getStringData(null) + "'");

        System.out.println("\ngetInt Test:");
        System.out.println("Valid Integer String: '124' -> " + getInt("124"));
        System.out.println("Invalid Integer String: 'abc' -> " + getInt("abc"));
        System.out.println("Null String: -> " + getInt(null));

        System.out.println("\ngetLong Test:");
        System.out.println("Valid Long String: '123456789' -> " + getLong("123456789"));
        System.out.println("Invalid Long String: 'abc' -> " + getLong("abc"));

        System.out.println("\ngetDate Test:");
        String dateStr = "10-10-2024";
        Date date = getDate(dateStr);
        System.out.println("String to Date: '" + dateStr + "' -> " + date);

        System.out.println("\ngetDateString Test:");
        System.out.println("Date to String: '" + getDateString(new Date()) + "'");

        System.out.println("\ngetTimestamp(String) Test:");
        String timestampStr = "10-10-2024 10:30:45";
        Timestamp timestamp = getTimestamp(timestampStr);
        System.out.println("String to Timestamp: '" + timestampStr + "' -> " + timestamp);

        System.out.println("\ngetTimestamp(long) Test:");
        long currentTimeMillis = System.currentTimeMillis();
        Timestamp ts = getTimestamp(currentTimeMillis);
        System.out.println("Current Time Millis to Timestamp: '" + currentTimeMillis + "' -> " + ts);

        System.out.println("\ngetCurrentTimestamp Test:");
        Timestamp currentTimestamp = getCurrentTimestamp();
        System.out.println("Current Timestamp: " + currentTimestamp);

        System.out.println("\ngetTimestamp(Timestamp) Test:");
        System.out.println("Timestamp to long: " + getTimestamp(currentTimestamp));
    }
}
