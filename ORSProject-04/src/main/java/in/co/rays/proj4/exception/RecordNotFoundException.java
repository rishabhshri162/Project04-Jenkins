package in.co.rays.proj4.exception;

/**
 * RecordNotFoundException is thrown when a requested record
 * does not exist in the database.
 *
 * It is commonly used when searching by primary key, email,
 * roll number, or any unique field but no matching entry is found.
 *
 * Author: Rishabh Shrivastava
 */
public class RecordNotFoundException extends Exception {

    /**
     * Creates a RecordNotFoundException with a specific message.
     *
     * @param msg detailed error message
     */
    public RecordNotFoundException(String msg) {
        super(msg);
    }
}
