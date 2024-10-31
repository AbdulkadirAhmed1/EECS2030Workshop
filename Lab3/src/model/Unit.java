package model;

import java.util.Objects;

public class Unit {
	String roomName;
	final String defaultMeasurment = "feet";
	
	final double conversionfeetToMeters = 0.3048;
		
	//for feet
	int width;
	int length;
	
	double width2;
	double length2;
	
	int areaInFeet;
	double areaInMeters;
	
	double feetToMeters = conversionfeetToMeters;
	
	String measurment = defaultMeasurment;
	String status;
	
	public Unit(String roomName,int width,int length) {
		this.roomName = roomName;
		this.width = width;
		this.length = length;
		
		updateStatus();
	}
	
	public void getArea() {
		this.areaInFeet = this.width * this.length;
		this.areaInMeters = this.width2 * this.length2;
	}
	
	public void updateStatus() {
		getArea();
		
		if (measurment == "feet") {
			this.status =  String.format("A unit of %d square %s (%d' wide and %d' long) functioning as %s"
					, this.areaInFeet,this.measurment,this.width,this.length,this.roomName);
		} else if(measurment == "meters") {
			this.status =  String.format("A unit of %.2f square %s (%.2f m wide and %.2f m long) functioning as %s"
					, this.areaInMeters,this.measurment,this.width2,this.length2,this.roomName);
		}
	}
	

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Unit other = (Unit) obj;
		
		if (this.areaInMeters  == 0 && other.areaInMeters == 0) {
			 //so if area in meters is not initialized for both
			//compare area in feet only

			return this.roomName == other.roomName && 
					   this.areaInFeet == other.areaInFeet
						;
						
		} else {
			//else just compare area in meters
			//if we ever come to this block we know 
			//this.areaInMeters != 0 and other.areaInMeters != 0
			
			//so then we can just compare areas
			
			return this.roomName == other.roomName && 
					   this.areaInMeters == other.areaInMeters
						;
						
		}
	}

	public void toogleMeasurement() {
		if (measurment == "feet") {
			this.width2 = this.width * feetToMeters;
			this.length2 = this.length * feetToMeters;
			this.areaInMeters = width * length;
			
			this.measurment = "meters";
			
			updateStatus();
		} else if(measurment == "meters") {
			this.measurment = "feet";
			
			updateStatus();
		}
	}
	

	public String toString() {
		return this.status;
	}
}
