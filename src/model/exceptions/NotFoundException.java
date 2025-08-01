package model.exceptions;

public class NotFoundException extends RuntimeException{
	private static final long serialVersionUID = 1L;

	public NotFoundException() {
		super("Id not found!");
	}
}
