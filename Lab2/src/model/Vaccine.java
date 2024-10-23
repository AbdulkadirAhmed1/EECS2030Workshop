package model;

public class Vaccine {
	String codeName;
	String type;
	String manufacturer;
	boolean validVaccine;
	
	private final String[] validVaccinecodeNames = {"mRNA-1273","BNT162b2","Ad26.COV2.S","AZD1222"};
	
	
	public Vaccine(String codeName, String type, String manufacturer) {
		this.codeName = codeName;
		this.type = type;
		this.manufacturer = manufacturer;
		checkIfValidVaccine();
	}

	public void checkIfValidVaccine() { // here we check if it is indeed a valid vaccine 
		this.validVaccine = false; // we assume it isn't a valid vaccine at first
		
		for (int i = 0; i < validVaccinecodeNames.length; i++) {
			if (this.codeName == validVaccinecodeNames[i]) {
				this.validVaccine = true;
			}
		}
	}
	
	public String toString() {
		if (this.validVaccine == true) {
			String vaccineString = String.format("Recognized vaccine: %s (%s; %s)", this.codeName,this.type,this.manufacturer);
			
			return vaccineString;
		} else {
			String vaccineString = String.format("Unrecognized vaccine: %s (%s; %s)", this.codeName,this.type,this.manufacturer);
			
			return vaccineString;
		}
	}
}
