package com.kh.helloffice.advisor;

import com.kh.helloffice.NoAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.NoHandlerFoundException;

import lombok.extern.slf4j.Slf4j;

@ControllerAdvice
@Slf4j
public class ErrorProcessor {
	
	@ExceptionHandler(NoHandlerFoundException.class)
	@ResponseStatus(value = HttpStatus.NOT_FOUND)
	public String handle404(Exception e) {
		log.info(e.toString());
		return "error/exception404";
	}

	@ExceptionHandler(Exception.class)
	public void printException(Exception e){
		e.printStackTrace();
	}

	@ExceptionHandler(NoAccessException.class)
	@ResponseStatus(value = HttpStatus.UNAUTHORIZED)
	public String noAccess(Exception e) {
		return "error/no-access";
	}

}
