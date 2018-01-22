package me.fabricionogueira.treasyapi.resource;

import java.util.List;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * Respostas para as requisições
 *
 */
public class ApiResponse {

	private static final ApiResponse INSTANCE = new ApiResponse();

	private HttpHeaders responseHeaders;

	/**
	 * Definindo o cabeçalho padrão para as requisições
	 */
	private ApiResponse() {
		this.responseHeaders = new HttpHeaders();
		this.responseHeaders.set("API", "Treasy");
	}

	/**
	 * Acesso único
	 */
	public static ApiResponse getInstance() {
		return INSTANCE;
	}

	/**
	 * Generic response for list.
	 *
	 * @param <E> Element
	 * @param List<E> List values
	 */
	public <E> ResponseEntity<List<E>> list(List<E> entyties) {
		return new ResponseEntity<List<E>>(entyties, this.responseHeaders, HttpStatus.OK);
	}

	/**
	 * Generic response for Object.
	 *
	 * @param <T> Type
	 * @param <T> Body values of type
	 */
	public <T> ResponseEntity<T> ok(T body) {
		return new ResponseEntity<T>((T) body, this.responseHeaders, HttpStatus.CREATED);
	}

	/**
	 * Not found
	 *
	 * @param <T> Type
	 *
	 */
	public <T> ResponseEntity<T> notFound() {
		return new ResponseEntity<T>((T) null, this.responseHeaders, HttpStatus.NO_CONTENT);
	}

	/**
	 * When request fail
	 *
	 * @param <T> Type
	 *
	 */
	public <T> ResponseEntity<T> requestFail() {
		return new ResponseEntity<T>((T) null, this.responseHeaders, HttpStatus.SERVICE_UNAVAILABLE);
	}
}
