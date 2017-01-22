package visionary.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * Utility class to provide method that return a response code and a message
 */
public class ResponseUtils {

	/**
	 * @param exc : the exception catching
	 * @param message : the message to add with error
	 * @return A bad request with an error message
	 */
	public static ResponseEntity<String> catchBadRequest (Exception exc, String message) {
        String errorMessage = message + exc.getMessage().toString();
		return new ResponseEntity<String>(errorMessage, HttpStatus.BAD_REQUEST);
	}
	/**
	 * @param object : the object that call this method (user...)
	 * @return A Not Found 404 Error with an error message
	 */
	public static ResponseEntity<String> catchNotFound (String object) {
		String errorMessage = "The " + object + " doesn't exist.";
		return new ResponseEntity<String>(errorMessage, HttpStatus.NOT_FOUND);
	}
	
}
