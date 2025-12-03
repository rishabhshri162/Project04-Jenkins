package in.co.rays.proj4.exception;

/**
 * DatabaseException is thrown when any database-related error occurs,
 * such as connection failure, SQL issues, or transaction problems.
 *
 * Author: Rishabh Shrivastava
 */
public class DatabaseException extends Exception {

    /**
     * Creates a DatabaseException with a specific error message.
     *
     * @param msg detailed error message
     */
    public DatabaseException(String msg) {
        super(msg);
    }
}
