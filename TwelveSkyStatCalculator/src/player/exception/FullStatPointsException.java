package player.exception;

public class FullStatPointsException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4365715745868462031L;
	
	//is thrown when a user tries to subtract a stat point from a stat, but hasn't spent any yet.
	public FullStatPointsException(String message) {
        super(message);
    }
}
