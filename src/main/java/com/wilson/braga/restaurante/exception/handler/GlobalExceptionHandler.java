package com.wilson.braga.restaurante.exception.handler;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.server.ResponseStatusException;

import com.wilson.braga.restaurante.exception.ErrorResponse;
import com.wilson.braga.restaurante.exception.OrdenacaoInvalidaException;
import com.wilson.braga.restaurante.exception.TamanhoPaginaInvalidoException;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;

@RestControllerAdvice
public class GlobalExceptionHandler {

	// TRATA EXCEÇÕES QUANDO NAO ENCONTRAR INFORMACAO NO BANCO
	@ExceptionHandler(ResponseStatusException.class)
	public ResponseEntity<ErrorResponse> handleResponseStatusException(ResponseStatusException ex, WebRequest request) {

		// Obtém o HttpStatus da exceção
		HttpStatus status = HttpStatus.valueOf(ex.getStatusCode().value());

		ErrorResponse errorResponse = new ErrorResponse(status,
				ex.getReason() != null ? ex.getReason() : ex.getMessage(), // Mensagem de erro
				request.getDescription(false));

		return new ResponseEntity<>(errorResponse, status);
	}

	// TRATA EXCEÇÕES DO TIPO: ANOTAÇÕES COMO @NOTNULL, @SIZE, @MIN DO JAKARTA BEAN
	// VALIDATION

	@ExceptionHandler(ConstraintViolationException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ResponseEntity<Map<String, String>> handleConstraintViolationException(ConstraintViolationException ex) {

		Map<String, String> errors = new HashMap<>();

		for (ConstraintViolation<?> violation : ex.getConstraintViolations()) {
			errors.put(violation.getPropertyPath().toString(), violation.getMessage());
		}

		return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(TamanhoPaginaInvalidoException.class)
	public ResponseEntity<ErrorResponse> handleTamanhoPaginaInvalidoException(TamanhoPaginaInvalidoException ex,
			WebRequest request) {

		HttpStatus status = HttpStatus.BAD_REQUEST;

		ErrorResponse errorResponse = new ErrorResponse(status, ex.getMessage(), request.getDescription(false));
		return new ResponseEntity<>(errorResponse, status);
	}

	@ExceptionHandler(OrdenacaoInvalidaException.class)
	public ResponseEntity<ErrorResponse> handleOrdenacaoInvalidaException(OrdenacaoInvalidaException ex,
			WebRequest request) {

		HttpStatus status = HttpStatus.BAD_REQUEST;

		ErrorResponse errorResponse = new ErrorResponse(status, ex.getMessage(), request.getDescription(false));
		return new ResponseEntity<>(errorResponse, status);
	}

	// Trata erros de parâmetros incorretos (ex.: URL com tipo errado)
	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	public ResponseEntity<String> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException ex,
			HttpServletRequest request) {

		String message = String.format(
				"Erro na URL para o método '%s': O parâmetro '%s' deve ser do tipo '%s'. Valor fornecido: '%s'.",
				request.getMethod(), // Verbo HTTP
				ex.getName(), // Nome do parâmetro
				ex.getRequiredType().getSimpleName(), // Tipo esperado
				ex.getValue()); // Valor recebido
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
	}
	
	// TRATA EXCEÇÕES DE ARGUMENTOS INVÁLIDOS
		@ExceptionHandler(MethodArgumentNotValidException.class)
		public ResponseEntity<Map<String, String>> handleMethodArgumentNotValidException(
				MethodArgumentNotValidException ex) {
			Map<String, String> errors = new HashMap<>();
			ex.getBindingResult().getFieldErrors()
					.forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));
			return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
			// RETORNA 400 COM MAPA DE CAMPOS INVÁLIDOS E MENSAGENS DE ERRO
		}

}
