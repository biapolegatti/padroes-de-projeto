package one.digitalinovation.gof.exception.handler;

import one.digitalinovation.gof.exception.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestControllerAdvice
public class RestExceptionHandler {

	@ExceptionHandler(NotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public ApiError handlerException(NotFoundException e, WebRequest request) {
 		return new ApiError.ApiErrorBuilder()
				.status(HttpStatus.NOT_FOUND.value())
				.message("Cliente não encontrado")
				.timestamp(new Date()).build();

	}
	
	@ExceptionHandler(Exception.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public ApiError handlerDefaultException(Exception e, WebRequest request) {
		return new ApiError.ApiErrorBuilder()
				.status(HttpStatus.INTERNAL_SERVER_ERROR.value())
				.message("Um erro inesperado aconteceu")
				.timestamp(new Date()).build();

	}

}