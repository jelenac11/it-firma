package tim13.webshop.shop.exceptions;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class BaseException extends Exception {

	private static final long serialVersionUID = 3623903129060302702L;

	private HttpStatus status;

	private String message;

}
