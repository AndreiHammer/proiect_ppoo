package ro.ase.service;

/**
 * Thrown when authentication fails due to invalid or non-matching credentials.
 */
public class AuthenticationException extends RuntimeException {
    public AuthenticationException(String message) {
        super(message);
    }
}
