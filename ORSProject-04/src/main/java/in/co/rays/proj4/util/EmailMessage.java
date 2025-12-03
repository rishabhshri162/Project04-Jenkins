package in.co.rays.proj4.util;

/**
 * EmailMessage holds the structure of an email.
 * It contains recipient address, subject, message body,
 * and the message type (HTML or TEXT).
 * 
 * Used by EmailUtility to send formatted mail messages.
 */
public class EmailMessage {

    /** Recipient email address */
    private String to;

    /** Email subject line */
    private String subject;

    /** Email message body */
    private String message;

    /** Email message type (HTML_MSG or TEXT_MSG) */
    private int messageType = TEXT_MSG;

    /** Constant for HTML message type */
    public static final int HTML_MSG = 1;

    /** Constant for plain text message type */
    public static final int TEXT_MSG = 2;

    /** Default constructor */
    public EmailMessage() {
    }

    /**
     * Parameterized constructor to create EmailMessage object.
     * 
     * @param to      Recipient email address
     * @param subject Email subject line
     * @param message Email message content
     */
    public EmailMessage(String to, String subject, String message) {
        this.to = to;
        this.subject = subject;
        this.message = message;
    }

    /** Sets recipient email address */
    public void setTo(String to) {
        this.to = to;
    }

    /** Returns recipient email address */
    public String getTo() {
        return to;
    }

    /** Sets subject */
    public void setSubject(String subject) {
        this.subject = subject;
    }

    /** Returns subject */
    public String getSubject() {
        return subject;
    }

    /** Sets message content */
    public void setMessage(String message) {
        this.message = message;
    }

    /** Returns message content */
    public String getMessage() {
        return message;
    }

    /** Sets type of message (HTML or TEXT) */
    public void setMessageType(int messageType) {
        this.messageType = messageType;
    }

    /** Returns message type */
    public int getMessageType() {
        return messageType;
    }
}
