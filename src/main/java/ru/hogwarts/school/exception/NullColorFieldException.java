package ru.hogwarts.school.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

// исключение для выбрасывания в случае если параметр "color" не передан
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class NullColorFieldException extends RuntimeException {
    public NullColorFieldException(String message) {
    }
}
