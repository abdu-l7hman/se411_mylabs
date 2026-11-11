package psu.se411.lab14.exception;

/** Thrown by the service when the requested object does not exist. */
public class ObjectNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public ObjectNotFoundException(String message) {
		super(message);
	}
}
