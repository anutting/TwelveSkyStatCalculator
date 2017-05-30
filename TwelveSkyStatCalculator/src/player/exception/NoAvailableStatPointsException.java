package player.exception;

public class NoAvailableStatPointsException extends Exception {

	private static final long serialVersionUID = -738593550718317821L;

	//is thrown when a user tries to spend a stat point after they are already spent.
	public NoAvailableStatPointsException(String message) {
        super(message);
    }

}
