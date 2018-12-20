package me.fabricionogueira.treasyapi.resource;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;

import static org.springframework.http.HttpStatus.*;

/**
 * Respostas para as requisições
 */
@Component
public class ApiResponse {

	private HttpHeaders responseHeaders;

	/**
	 * Definindo o cabeçalho padrão para as requisições
	 */
	private ApiResponse() {
		this.responseHeaders = new HttpHeaders();
		this.responseHeaders.set("API", "Treasy");
	}

	/**
	 * Generic response for list.
	 *
	 * @param E       <Element>
	 * @param List<E> List values
	 */
	public <E> ResponseEntity<List<E>> list(List<E> entyties) {
		return new ResponseEntity<List<E>>(entyties, this.responseHeaders, OK);
	}

	/**
	 * Generic response for Object.
	 *
	 * @param <T> Type
	 * @param <T> Body values of type
	 */
	public <T> ResponseEntity<T> ok(T body) {
		return new ResponseEntity<T>((T) body, this.responseHeaders, OK);
	}

	/**
	 * Generic response for Object.
	 *
	 * @param <T> Type
	 * @param <T> Body values of type
	 */
	public <T> ResponseEntity<T> created(T body) {
		return new ResponseEntity<T>((T) body, this.responseHeaders, CREATED);
	}

	/**
	 * Not found
	 *
	 * @param <T> Type
	 */
	public <T> ResponseEntity<T> notFound() {
		return new ResponseEntity<T>((T) null, this.responseHeaders, NO_CONTENT);
	}

	/**
	 * When request fail
	 *
	 * @param <T> Type
	 */
	public <T> ResponseEntity<T> requestFail() {
		return new ResponseEntity<T>((T) null, this.responseHeaders, SERVICE_UNAVAILABLE);
	}
}
