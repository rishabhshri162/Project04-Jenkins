package in.co.rays.proj4.exception;

/**
 * ApplicationException is used to handle application-level exceptions.
 * It represents errors that occur during business logic execution.
 *
 * Author: Rishabh Shrivastava
 */
public class ApplicationException extends Exception {

    /**
     * Creates an ApplicationException with a specific error message.
     *
     * @param msg error message
     */
    public ApplicationException(String msg) {
        super(msg);
    }
}
