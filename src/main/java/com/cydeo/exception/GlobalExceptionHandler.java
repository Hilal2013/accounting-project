package com.cydeo.exception;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler({Exception.class, RuntimeException.class, Throwable.class})
    public String handleGenericException(Exception exception,Model model) {
        String message = exception.getMessage();
        if (message == null || message.isEmpty()) {

            model.addAttribute("message", "Something went wrong!");

        }
        return "error";
    }
    @ExceptionHandler(InvoiceNotFoundException.class)
    public String handleInvoiceNotFoundException(InvoiceNotFoundException ex, Model model) {
        model.addAttribute("message", ex.getMessage());
        return "error";
    }





}

