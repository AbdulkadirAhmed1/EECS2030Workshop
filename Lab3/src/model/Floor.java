package model;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Objects;

public class Floor {
	int maxFloorUnits = 20;
	
	int floorInSquareFeet;
	int utilizedSpace;
	
	Unit[] unitsStorage;
	int storageCount;
	
	String status;
	String units;
	
	public Floor(int floorInSquareFeet) {
		this.floorInSquareFeet = floorInSquareFeet;
		this.units = "[]";
		this.unitsStorage = new Unit[this.maxFloorUnits];
		
		this.status = String.format("Floor's utilized space is %d sq ft (%d sq ft remaining): %s", 
				this.utilizedSpace,
				this.floorInSquareFeet,
				this.units
				);
	}
	
	//Deep copy Constructor 
	
	public Floor(Floor other) {
		this.floorInSquareFeet = other.floorInSquareFeet;
		this.utilizedSpace = other.utilizedSpace;
		this.unitsStorage = other.unitsStorage;
		this.storageCount = other.storageCount;
		this.status = other.status;
		this.units = other.units;
	}
	
	public void updateStatus() {
		int remaining = this.floorInSquareFeet - this.utilizedSpace;
		
		String header = "Floor's utilized space is " + this.utilizedSpace + " sq ft (" + remaining + " sq ft remaining):";
		
		this.units = "[";
		
		for (int i = 0; i < storageCount; i++) {
			if (unitsStorage[i] != null) {
				String prefix = "sq ft";
				
				//if(unitsStorage[i].measurment == "meters") {
					//prefix = "sq meters";
				//}
				
				if (i == 0) {
					this.units += unitsStorage[i].roomName + ": " + unitsStorage[i].areaInFeet 
							+ " " + prefix + " (" + unitsStorage[i].width  + "' by " + unitsStorage[i].length + "')";
				} else {
					this.units += ", " + unitsStorage[i].roomName + ": " + unitsStorage[i].areaInFeet 
							+ " " + prefix + " (" + unitsStorage[i].width  + "' by " + unitsStorage[i].length + "')";
				}
			}
		}
		
		this.units += "]";
		
		this.status = String.format("%s %s", header,this.units);
	}
	
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Floor other = (Floor) obj;
		
		boolean FinalIsEqual = true;
		
		if (this.storageCount == other.storageCount) {
			//String foundEqual[] = new String[this.storageCount];
			int foundIndex[] = new int[this.storageCount];
			int counter = 0;
			
			for (int i = 0; i < foundIndex.length; i++) {
				foundIndex[i] = -1;
			}
			
			for (int i = 0; i < this.unitsStorage.length; i++) {
				if (this.unitsStorage[i] != null) {
					boolean EqualsChecker = false;
					
					for (int j = 0; j < other.unitsStorage.length; j++) {
						if (other.unitsStorage[j] != null) {
							boolean found = false;
							
							Unit u1 = this.unitsStorage[i];
							Unit u2 = other.unitsStorage[j];

							for (int k = 0; k < foundIndex.length; k++) {
								if (foundIndex[k] != -1 && foundIndex[k] == j) {
									found = true;
								}
							}

							if (found == false) {
								boolean IsEqual = true;
								
								IsEqual = u1.equals(u2);

								if (IsEqual == true) {
									foundIndex[counter++] = j;
									
									EqualsChecker = true;
									
									break;
								} 
							} 
						}
					}
					
					if (EqualsChecker == false) {
						FinalIsEqual = false;
						break;
					}
				}
			}
		} else {
			FinalIsEqual = false;
		}
		
		return this.floorInSquareFeet == other.floorInSquareFeet && FinalIsEqual;
		
		//now 
		
		/*
		 * Floors f1 and f2 are utilized in the same way (despite the added orders of units):
		 * 	+ 2 master bedrooms, each of 126'
		 *  + 1 office of 96'
		 *  + 1 kitchen of 90' 
		 */
		
		//here we are gonna check the unitsStorage for other
		//by using a for loop. then within we will check if unit from
		//other.unitsStorage[i].equals(this.floorInSquareFeet[i])
		//if they are equal then we have found 1 case where its equal
		
		//now how many cases do we need
		//well first of all we can compare the sizes by doing other.unitsStorage.storageCount
		//and this.floorInSquareFeet.storageCount 
		//if they are not equal we don't even bother going into a for loop 
		//in this case we just return equal as false
		
		//if they are equal then we will wanna check the inside of both arrays
		
		//this is the algorithm 
		
		//imagine size = 3
		//we will go from 0,1,2,3
	}

	public void addUnit(String roomName,int width,int length) throws InsufficientFloorSpaceException {
		//utilizedSpace should never exceed utilizedSpace
		int addingArea = width * length; //in square feet
		
		//(utilizedSpace + addingArea) should never exceed this.floorInSquareFeet
		
		if ((this.utilizedSpace + addingArea) >= this.floorInSquareFeet) {
			throw new InsufficientFloorSpaceException("Insufficient Floor Space: Max space is: " + this.floorInSquareFeet 
					+ "ft. utilizedSpace space is: " + this.utilizedSpace + "ft. unit that wants to be added has space of: " + addingArea + "ft"
					);
		} else {
			//means we are < so we are fine
			
			Unit u1 = new Unit(roomName, width, length);
			
			//add new unit u1 to our array
			
			unitsStorage[storageCount++] = u1;
			
			//add our addingArea to our utilizedSpace
			
			this.utilizedSpace += addingArea;
			
			updateStatus();
		}
	}
	
	public String toString() {
		return this.status;
	}
}
