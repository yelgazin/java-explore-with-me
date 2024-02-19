package ru.practicum.ewm.stat.client.exception;

public class StatClientException extends RuntimeException {
    public StatClientException(String message, Object... parameters) {
        super(String.format(message, parameters));
    }

    public StatClientException(String message, Throwable cause) {
        super(message, cause);
    }
}
