package model;

public class VaccinationSite {
	String vaccinationSiteName;
	String status;
	
	int maxSupply;
	int currentSupply;
	int currentAppointments;
	int bookedSupply;
	final int maxDistrubtionSupply = 4;
	final int maxSiteAppointments = 200;
	final int DosePerPatient = 1;
	
	VaccineDistribution[] supply;
	
	HealthRecord[] vaccinationAppointments;
	
	public VaccinationSite(String vaccinationSiteName,int maxSupply) {
		this.vaccinationSiteName = vaccinationSiteName;
		this.maxSupply = maxSupply;
		this.currentSupply = 0;
		this.currentAppointments = 0;
		this.bookedSupply = this.currentSupply;
		this.status = String.format("%s has %d available doses: <>", 
				this.vaccinationSiteName,
				this.currentSupply);
				
		supply = new VaccineDistribution[this.maxDistrubtionSupply];
		vaccinationAppointments = new HealthRecord[maxSiteAppointments];
	}
	
	public void bookAppointment(HealthRecord patient) throws InsufficientVaccineDosesException {
		boolean TooMuchAppointments = (this.currentAppointments >= this.maxSiteAppointments);
		
		int availableDoses = getNumberOfAvailableDoses();
		
		boolean EnoughSupply = (this.bookedSupply < availableDoses);
		// Enough Supply is true when (booked supply < AvailableDoses)
		// imagine we have 3 booked supply's and available doses is 3 
		// we shouldn't allow the program to add any more since 3 < 3 (false)
		// imagine booked supply was 2 and the available dose is the same then
		// we should allow it because 2 < 3 ....
		
		if (EnoughSupply == false) {
			patient.setAppointmentStatus("Last vaccination appointment for "
					+ patient.patientName + " with " + this.vaccinationSiteName + " failed"
					);
			throw new InsufficientVaccineDosesException(
					"Not enough supply currently booked supply is " + this.bookedSupply + " and " +
			"current supply is " + availableDoses
					);
		} else if (TooMuchAppointments == true) {
			// ignore 
		} else if (TooMuchAppointments == false && EnoughSupply == true) {
			vaccinationAppointments[this.currentAppointments++] = patient;
			patient.setAppointmentStatus("Last vaccination appointment for "
					+ patient.patientName + " with " + this.vaccinationSiteName + " succeeded"
					);
			this.bookedSupply += this.DosePerPatient;
		}
	}
	
	public int getNumberOfAvailableDoses() {
		int totalDosesforVaccine = 0;

		for (int i = 0; i < supply.length; i++) {
			if (supply[i] != null) {
				if (supply[i].vaccine.validVaccine == true) {
					totalDosesforVaccine += supply[i].doses;
				}
			}
		}

		return totalDosesforVaccine;
	}
	
	public int getTotalDoses() {
		int totalDoses = 0;
		
		for (int i = 0; i < supply.length; i++) {
			if (supply[i] != null) {
				totalDoses += getNumberOfAvailableDoses(supply[i].vaccine.codeName);
			}
		}
		
		return totalDoses;
	}
	
	public int getNumberOfAvailableDoses(String codeName) {
		int totalDosesforVaccine = 0;
		
		for (int i = 0; i < supply.length; i++) {
			if (supply[i] != null) {
				if (supply[i].vaccine.codeName.equals(codeName) && supply[i].vaccine.validVaccine == true) {
					totalDosesforVaccine += supply[i].doses;
				}
			}
		}
		
		return totalDosesforVaccine;
	}
	
	public void updateStatus(int totalDoses) {
		String tempStatusHeader = this.vaccinationSiteName + " has " + totalDoses + " available doses: "; 
		String tempStatusInfo = "<";
		
		int tempIndex = 0;
		
		for (int i = 0; i < this.maxDistrubtionSupply; i++) {
			if (supply[i] != null) {
				int currentDoses = getNumberOfAvailableDoses(supply[i].vaccine.codeName);
				
				if (tempIndex == 0) {
					tempStatusInfo += "" + currentDoses + " doses of " + supply[i].vaccine.manufacturer;
				} else {
					tempStatusInfo += ", " + currentDoses + " doses of " + supply[i].vaccine.manufacturer;
				}
				
				tempIndex++;
			}
		}
		
		tempStatusInfo += ">";
		
		this.status = tempStatusHeader + tempStatusInfo;
	}
	
	public void addDistribution(Vaccine vaccine,int doses) throws UnrecognizedVaccineCodeNameException,TooMuchDistributionException {
		boolean RecognizedVaccine = vaccine.validVaccine;
		boolean TooMuchDistribution = ((getNumberOfAvailableDoses() + doses)  > this.maxSupply);
		// 3 + 2 > 10  5 > 10 (false) 
		// 3 + 7 > 10  10 > 10 (false)
		
		if (RecognizedVaccine == false) {
			throw new UnrecognizedVaccineCodeNameException(vaccine.codeName + " is a Unrecognized Vaccine Code Name");
		}
		else if (TooMuchDistribution == true) {
	    	 throw new TooMuchDistributionException("Current supply " + getNumberOfAvailableDoses() + " exceeds max supply of " + this.maxSupply);
		} else if (RecognizedVaccine == true && TooMuchDistribution == false) {
			boolean vaccineDistrbutionExists = false;
			boolean novaccineDistrbutionExists = true;
			
			int totalDoses = getTotalDoses();
			
			for (int i = 0; i < supply.length; i++) {
				if (supply[i] != null) {
					//totalDoses += getNumberOfAvailableDoses(supply[i].vaccine.codeName);
					novaccineDistrbutionExists = false;
					
					if (supply[i].vaccine.codeName.equals(vaccine.codeName)) { // here we check if there is vaccinedistrbution that exists
						vaccineDistrbutionExists = true;
						supply[i].incrementDoses(doses);
					}
				}
			}
			
			if (vaccineDistrbutionExists == false) {
				VaccineDistribution vd = new VaccineDistribution(vaccine,doses);
				supply[currentSupply++] = vd;
			}
			
			if (novaccineDistrbutionExists == true) {
				totalDoses = doses;
			} else {
				totalDoses += doses;
			}
			
			updateStatus(totalDoses);
			
			//"North York General Hospital has 10 available doses: <4 doses of Moderna, 5 doses of Pfizer/BioNTech, 1 doses of Oxford/AstraZeneca>"
		}	
	}
	
	public void updatevaccinationAppointments(HealthRecord removepatient) {
		//now in here will remove a patient from the vaccinationAppointments list 
		//criteria: 
		//[patient0,patient1,patient2,patient3,null,.....] let say we remove patient0(first element) then we want the final array to be
		//[patient1,patient2,patient3,null,.....] (im using null here to just show its empty it would be 0 in syntax)
		//all elements move up by 1
		
		//we assume removepatient is always the first element 
		//also we don't care about any null values in the table
		
		//assume our array looks as such (not accruate)
		//[patient1,patient2,patient3,null,.....]
		
		//first we set vaccinationAppointments[0] = null that way its deleted
		
		vaccinationAppointments[0] = null;
		//[null,patient2,patient3,null,.....]
		
		for (int i = 0; i < vaccinationAppointments.length; i++) {
			if (vaccinationAppointments[i] != null & i != 0) {
				vaccinationAppointments[i-1] = vaccinationAppointments[i];
				vaccinationAppointments[i] = null;
			}
		}
		//iteration 0:
		//nothing happens (i == 0 and vaccinationAppointments[0] == null)
		//iteration 1:
		//[patient2,null,patient3,null,.....]
		//iteration 2:
		//nothing happens (vaccinationAppointments[2] == null)
		///iteration 3:
		//[patient2,patient3,null,null,.....]
		//lopp continues but won't do anything else because all other values or null
	}
	
	public void updatevaccinationSupply(VaccineDistribution currentvd) {
		//condition to remove vaccine from vaccine distrubution we must have emptied out the supply of a given vaccine
		
		//supply array would look as such 
	    
		//[vd1,vd2,vd3,null] //size of supply is 4
		//in order to remove currentvd from an the array 
		//then currentvd.doses <= 0 (then only then do we remove it)
		
		//we assume that currentvd is always the first element
		//of supply 
		
		if (currentvd.doses <= 0) {
			//removing algorithm is the same as in updatevaccinationAppointments
			
			supply[0] = null;
			//[null,vd2,vd3,null]
			
			for (int i = 0; i < supply.length; i++) {
				if (supply[i] != null & i != 0) {
					supply[i-1] = supply[i];
					supply[i] = null;
				}
			}
		}
	}
	
	public void administer(String administerDate) {
		//so here we have a bunch of Appointments and we will give each patient a vaccine 
		
		//here is order of my logic:
		
		//(1) get the information of:
		//first patient in vaccinationAppointments list
		//first vaccine in supply list
		//(2) we will then want to update the (STATUS) of
		//current vaccine distribution(look at addDistribution method). 
		//we also wanna update the information under the patient by using the (addRecord method)
		//this will automatically update the VaccinationReceipt(already setup from HealthRecord class)
		//(3)we will then update these lists by removing the patient and vaccine within the current context
		//and move the list up i.e all the elements move up by 1
		//e.g [0,1,2,3] let say we remove 0(first element) then we want the final array to be
		//[1,2,3,null] (im using null here to just show its empty it would be 0 in syntax)
		//all elements move up by 1
		//How would we do this. well of course by using a for loop
		//we also updated booksupply and currentAppointments to 0
		
		
		//loop n times where n is the amount of appointments
		
		int totalDoses = getTotalDoses();
		int startingSupply = 0;
		
		for (int i = 0; i < this.currentAppointments; i++) {
			if (supply[startingSupply].doses <= 0) {
				startingSupply++;
			}
			
			VaccineDistribution currentvd = supply[startingSupply];
			HealthRecord currentpatient = vaccinationAppointments[0];
			
			//we will take the first vaccine in the vaccine distribution and give
			//the supply(of 1) to the current patient
			
			currentpatient.addRecord(currentvd.vaccine, vaccinationSiteName, administerDate);
			currentvd.decrementDoses(1);
			totalDoses -= 1;
			
			updateStatus(totalDoses); 
			
			updatevaccinationAppointments(currentpatient);
			//updatevaccinationSupply(currentvd);
		}
		
		this.bookedSupply = 0;
		this.currentAppointments = 0;
		
		//update supply and vaccinationAppointments arrays here
	}
	
	public String toString() {
		return this.status;
	}
}
