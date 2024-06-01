package org.example.licenta.exceptions;

public class NoDepartmentsInDatabaseException extends Exception{
    public NoDepartmentsInDatabaseException(String message) {
        super(message);
    }
}
