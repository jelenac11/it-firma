package tim13.bank2.exceptions;

public class RequestException extends Exception{

	private static final long serialVersionUID = 1L;

	public RequestException(String errorMessage) {
        super(errorMessage);
    }
}
