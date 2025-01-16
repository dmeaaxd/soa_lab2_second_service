package ru.danmax.soa_lab2_second_service.exceptions;

public class TeamSizeErrorException extends Exception {
    public TeamSizeErrorException(String message) {
        super(message);
    }

    public TeamSizeErrorException(String message, Throwable cause) {
        super(message, cause);
    }

    public TeamSizeErrorException(Throwable cause) {
        super(cause);
    }
}
