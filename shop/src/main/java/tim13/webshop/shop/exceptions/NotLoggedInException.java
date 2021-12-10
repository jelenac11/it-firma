package tim13.webshop.shop.exceptions;

public class NotLoggedInException extends Exception {

	private static final long serialVersionUID = 1L;

	public NotLoggedInException(String message) {
        super(message);
    }
}
