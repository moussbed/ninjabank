package com.mb.ninjabank.httpclients.exceptions;

public record ExceptionMessage(String timestamp,
                               int status,
                               String error,
                               String message,
                               String path
                               ) {
}
