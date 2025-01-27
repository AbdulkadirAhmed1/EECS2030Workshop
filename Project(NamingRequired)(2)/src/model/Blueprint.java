package model;

public class Blueprint {
	int maxFloors;
	int currentFloors;
	
	String status;
	
	Floor[] floors;
	
	public Blueprint(int maxFloors) {
		this.maxFloors = maxFloors;
		this.floors = new Floor[maxFloors];
		
		this.status = String.format("%.1f percents of building blueprint completed (%d out of %d floors)", 
				getPercant(),currentFloors,maxFloors);
	}
	
	//Deep copy Constructor
	
	public Blueprint(Blueprint other) {
		this.maxFloors = other.maxFloors;
		this.currentFloors = other.currentFloors;
		this.status = other.status;
		this.floors = other.floors;
	}
	
	public double getPercant() {
		double percant = ((double) this.currentFloors/(double) this.maxFloors) * 100;
		
		return percant;
	}
	
	public void addFloorPlan(Floor f) {
		this.floors[this.currentFloors++] = f;
		
		this.status = String.format("%.1f percents of building blueprint completed (%d out of %d floors)", 
				getPercant(),currentFloors,maxFloors);
	}
	
	public Floor[] getFloors() {
		Floor[] floorsCopy = new Floor[this.currentFloors];

		for (int i = 0; i < this.currentFloors; i++) {
			if (i <= floors.length && this.floors[i] != null) {
				Floor floorCopy = new Floor(this.floors[i]);
				
				floorsCopy[i] = floorCopy;
			}
		}
		
		return floorsCopy;
	}
	
	public String toString() {
		return this.status;
	}
}
