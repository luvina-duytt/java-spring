package be.vn.exception;
import lombok.Getter;

@Getter
public class ValidationException extends RuntimeException {
    private Object data;

    public ValidationException(String message) {
        super(message);
    }

    public ValidationException(Object data) {
        this.data = data;
    }

    public ValidationException(String message, Throwable cause) {
        super(message, cause);
    }
}
