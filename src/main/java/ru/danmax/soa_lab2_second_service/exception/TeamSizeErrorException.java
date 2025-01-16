package ru.danmax.soa_lab2_second_service.exception;

public class TeamSizeErrorException extends Exception {
    public TeamSizeErrorException(String message) {
        super(message);
    }
}