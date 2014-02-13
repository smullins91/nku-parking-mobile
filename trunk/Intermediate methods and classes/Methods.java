
import java.util.Vector;


public class Methods {
	//PSEUDOCODE OF METHODS THAT WE NEED
	
	/* Input: User ID and the parking lot selected by user.
	 * Output to server: Reserves the spot for the user.
	 * Output to user: If input is valid, return a vector holding the directions to the space.
	 * Function also determines if the user already reserved a space or has parked,
	 * and returns prompts to handle those cases.
	 */
	public Object reserveSpot(int userID, ParkingLot inLot)
	{
		if (userHasReservation(userID) == true)
		{
			//prompt user to determine if they want to keep or cancel existing reservation.
			//If they do not want a new reservation, return null. Otherwise, continue
		}
		if (userIsParked(userID) == true)
		{
			//prompt user to determine if they want to release the space they are already using
			//If they do not want a new space, return null. Otherwise, continue
		}
		
		//Return directions to the parking space
		ParkingSpace foundSpace = getNearestSpace(inLot);
		reserveSpace(userID, inLot, foundSpace);
		Object userLocation = getLocation(userID);
		return getDirections(userLocation, inLot.location, foundSpace);
	}
	
	public void reserveSpace(int userID, ParkingLot inLot, ParkingSpace inSpace) {
		//TO DO: tell server to reserve this space in this lot for this user.
	}
	
	public boolean userHasReservation(int userID){
		/*
		 * TO DO: query server to determine if this user already has a reservation.
		 * If so, return true. If not, return false.
		 */
		return false;
	}
	
	public boolean userIsParked(int userID){
		/*
		 * TO DO: query server to determine if this user is already in a parking spot.
		 * If so, return true. If not, return false.
		 */
		return false;
	}
	
	/* Input: the parking lot selected by the user.
	 * Output: the closest available parking space to the lot's entrance
	 */
	public ParkingSpace getNearestSpace(ParkingLot inLot){
		/*
		 * TO DO: query server to get the array [column, row] of the parking spaces in the lot.
		 * Preferably download all the spaces at once, so we will not need to query server to check
		 * spaces individually
		 */
		ParkingSpace[][] spaces = new ParkingSpace[inLot.getRows()][inLot.getColumns()];
		
		//find and return the nearest space in the lot - the empty space with the lowest column value
		for (int i = 0; i < inLot.getColumns(); i++)
		{
			for (int j = 0; j < inLot.getRows(); j++)
			{
				if ((String) spaces[j][i].getStatus() == "empty"){
					return spaces[j][i];
				}
			}
		}
		return null; //if no available space is found
	}
	
	public Vector<String> getDirections(Object userLocation, Object lotLocation, ParkingSpace inSpace)
	{
		Vector<String> directions = new Vector<String>();
		/*
		 * Use google maps (or any other application) to get the directions,
		 * insert them into the vector, and then insert instructions to reach
		 * this particular parking space based on it's row and column
		 */
		return directions;
	}
	
	public Object getLocation(int userID)
	{
		// get the user's location, either from the database (if it's there), or from the app
		Object location = null;
		
		return location;
	}
	
}
	
	

