package br.com.ifba.exception;

import lombok.Data;

@Data
public class ErrorResponse {
    private int status;
    private String message;
    private String stacktrace;

    public ErrorResponse(int status, String message) {
        this.status = status;
        this.message = message;
    }

}
