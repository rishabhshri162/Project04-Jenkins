package in.co.rays.proj4.util;

import java.util.HashMap;

/**
 * Utility class used to build HTML-based email message bodies for 
 * different user actions like registration, password recovery, 
 * and password change notifications.
 *
 * Author: Rishabh Shrivastava
 */
public class EmailBuilder {

    /**
     * Builds the email message for a new user registration.
     *
     * @param map A HashMap containing user details (login, password)
     * @return HTML string for the registration email
     */
    public static String getUserRegistrationMessage(HashMap<String, String> map) {
        StringBuilder msg = new StringBuilder();
        msg.append("<HTML><BODY>");
        msg.append("<H1>Welcome to ORS, ").append(map.get("login")).append("!</H1>");
        msg.append("<P>Your registration is successful. You can now log in and manage your account.</P>");
        msg.append("<P><B>Login Id: ").append(map.get("login")).append("<BR>Password: ").append(map.get("password"))
                .append("</B></P>");
        msg.append("<P>Change your password after logging in for security reasons.</P>");
        msg.append("<P>For support, contact +91 98273 60504 or hrd@rays.co.in.</P>");
        msg.append("</BODY></HTML>");
        return msg.toString();
    }

    /**
     * Builds the email message for password recovery (forgot password).
     *
     * @param map A HashMap with keys: firstName, lastName, login, password
     * @return HTML string for the forgot-password email
     */
    public static String getForgetPasswordMessage(HashMap<String, String> map) {
        StringBuilder msg = new StringBuilder();
        msg.append("<HTML><BODY>");
        msg.append("<H1>Password Recovery</H1>");
        msg.append("<P>Hello, ").append(map.get("firstName")).append(" ").append(map.get("lastName")).append(".</P>");
        msg.append("<P>Your login details are:</P>");
        msg.append("<P><B>Login Id: ").append(map.get("login")).append("<BR>Password: ").append(map.get("password"))
                .append("</B></P>");
        msg.append("</BODY></HTML>");
        return msg.toString();
    }

    /**
     * Builds the email message for notifying a user after password change.
     *
     * @param map A HashMap with keys: firstName, lastName, login, password
     * @return HTML string for the change-password confirmation email
     */
    public static String getChangePasswordMessage(HashMap<String, String> map) {
        StringBuilder msg = new StringBuilder();
        msg.append("<HTML><BODY>");
        msg.append("<H1>Password Changed Successfully</H1>");
        msg.append("<P>Dear ").append(map.get("firstName")).append(" ").append(map.get("lastName"))
                .append(", your password has been updated.</P>");
        msg.append("<P>Your updated login details are:</P>");
        msg.append("<P><B>Login Id: ").append(map.get("login")).append("<BR>New Password: ").append(map.get("password"))
                .append("</B></P>");
        msg.append("</BODY></HTML>");
        return msg.toString();
    }
}
