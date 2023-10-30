package ru.hogwarts.school.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

// исключение для выбрасывания в случае, если передан некорректный возраст
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class StudentAgeException extends RuntimeException {
    public StudentAgeException(String message) {
    }
}
