package com.example.expense.ExpenseManagement.ExceptionController;

public class ApplicationException extends RuntimeException{
    
    private String message;

    public ApplicationException() {}

    public ApplicationException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }

}
