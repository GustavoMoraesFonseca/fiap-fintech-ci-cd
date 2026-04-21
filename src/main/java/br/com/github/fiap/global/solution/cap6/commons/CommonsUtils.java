package br.com.github.fiap.global.solution.cap6.commons;

import java.time.Duration;
import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import br.com.github.fiap.global.solution.cap6.constants.Constants;
import br.com.github.fiap.global.solution.cap6.dto.ResponseDto;
import br.com.github.fiap.global.solution.cap6.exception.NotFoundException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CommonsUtils {

	public static void registerTrace(LocalDateTime dtHrInicio, int HttpStatus, String method) {
		LocalDateTime dtHrFinal = LocalDateTime.now();
		long duracao = Duration.between(dtHrInicio, dtHrFinal).getSeconds();
		long duracaoEmMilissegundos = Duration.ofSeconds(duracao).toMillis();
		log.info(String.format("Duration in ms: %s | Method: %s | HttpStatus: %s", 
					  		   duracaoEmMilissegundos, method, HttpStatus)
		);
	}
	
	public static void notFoundChecker(int paramForCheck) throws NotFoundException {
		if (paramForCheck == 0)
			throw new NotFoundException();
	}
	
	public static ResponseEntity ok(Object obj) {
		return ResponseEntity.ok(new ResponseDto(obj, null));
	}
	
	public static ResponseEntity created(int id) {
		return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseDto(id, null));
	}
	
	public static ResponseEntity notFound() {
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
				new ResponseDto(Constants.NOT_FOUND_MSG, null)
			);
	}
	
	public static ResponseEntity conflict(String msg) {
		return ResponseEntity.status(HttpStatus.CONFLICT).body(
				new ResponseDto(null, msg)
			);
	}
	
	public static ResponseEntity badRequest(String msg) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
				new ResponseDto(null, msg)
			);
	}
	
	public static ResponseEntity serverError(String msg) {
		return ResponseEntity.internalServerError().body(
				new ResponseDto(null, msg)
			);
	}
}