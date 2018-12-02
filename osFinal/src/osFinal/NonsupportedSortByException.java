package osFinal;

public class NonsupportedSortByException extends Exception{
	
	
	//some formatting guidance from https://stackoverflow.com/questions/8423700/how-to-create-a-custom-exception-type-in-java
	private static final long serialVersionUID = 1L;
	public NonsupportedSortByException() {
		super("Error: could not sort by sortType given");
	}
	public NonsupportedSortByException(String message) {
		super(message);
	}
}
