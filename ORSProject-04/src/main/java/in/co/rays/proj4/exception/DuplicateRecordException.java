package in.co.rays.proj4.exception;

/**
 * DuplicateRecordException is thrown when an attempt is made
 * to add a record that already exists in the database.
 *
 * This typically happens when unique fields such as email,
 * course name, subject name, roll number, etc. are duplicated.
 *
 * Author: Rishabh Shrivastava
 */
public class DuplicateRecordException extends Exception {

    /**
     * Creates a DuplicateRecordException with a specific message.
     *
     * @param msg detailed error message
     */
    public DuplicateRecordException(String msg) {
        super(msg);
    }
}
