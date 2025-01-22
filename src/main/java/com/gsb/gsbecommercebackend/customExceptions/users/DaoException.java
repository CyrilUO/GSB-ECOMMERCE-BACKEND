
package com.gsb.gsbecommercebackend.customExceptions.users;
/**
 * Exception personnalisée pour les erreurs liées aux opérations DAO.
 */
public class DaoException extends RuntimeException {

    public DaoException(String message) {
        super(message);
    }

    public DaoException(String message, Throwable cause) {
        super(message, cause);
    }
}
