package model;

public class VaccineDistribution {
	Vaccine vaccine;
	int doses;
	
	public VaccineDistribution(Vaccine vaccine,int doses) {
		this.vaccine = vaccine;
		this.doses = doses;
	}
	
	public void incrementDoses(int dose) {
		this.doses += dose;
	}
	
	public void decrementDoses(int doses) {
		this.doses -= doses;
	}
	
	public String toString() {
		String VaccineDistributionString = String.format("%d doses of %s by %s", 
				this.doses,
				this.vaccine.codeName,
				this.vaccine.manufacturer);
		
		return VaccineDistributionString;
	}
}
