package model;

public class HealthRecord {
	String patientName;
	String bookedAppointmentStatus;
	String vaccinationReceipt;

	int maxDoses;
	int currentDose;

	String[] vaccinationSite;
	String[] vaccinationDate;
	Vaccine[] vacineReference;

	public HealthRecord(String patientName,int maxDoses) {
		this.patientName = patientName;
		this.maxDoses = maxDoses;

		this.currentDose = 0; // they start of with no doses 

		this.vaccinationReceipt = String.format("%s has not yet received any doses.", 
				this.patientName);
		this.bookedAppointmentStatus = "No vaccination appointment for " + patientName + " yet";

		this.vaccinationSite = new String[maxDoses];
		this.vaccinationDate = new String[maxDoses];
		this.vacineReference = new Vaccine[maxDoses];
	}

	public void updateVaccinationReceipt() {
		//"Number of doses Alan has received: 1 [Recognized vaccine: mRNA-1273 (RNA; Moderna) in North York General Hospital on April-20-2021]"

		if (this.currentDose <= 0) {
			return;
		}
		
		String tempReceiptHeader = "Number of doses " + this.patientName + " has received: " + this.currentDose; 

		String tempReceiptInfo = "[";
		
		int tempIndex = 0;
		
		for (int i = 0; i < this.maxDoses; i++) {
			Vaccine vacineReferenceValue = vacineReference[i];
			String vaccinationSiteValue = vaccinationSite[i];
			String vaccinationDateValue = vaccinationDate[i];
			
			if (vacineReference[i] != null 
					&& vaccinationSite[i] != null 
					&& vaccinationDate[i] != null) 
			{
				if (tempIndex == 0) {
					tempReceiptInfo += "" + vacineReferenceValue.toString() + " in " + vaccinationSiteValue + " on " + vaccinationDateValue;
				} else {
					tempReceiptInfo += "; " + vacineReferenceValue.toString() + " in " + vaccinationSiteValue + " on " + vaccinationDateValue;
				}
				tempIndex++;
			}
		}

		tempReceiptInfo += "]";

		vaccinationReceipt = tempReceiptHeader + " " + tempReceiptInfo;
	}

	public String getVaccinationReceipt() {
		updateVaccinationReceipt();

		return vaccinationReceipt;
	}

	public void incrementcurrentDose() {	
		if (this.currentDose < this.maxDoses) {
			this.currentDose++;
		}
	}

	public void addRecord(Vaccine vaccine, String vaccinationSite, String vaccinationDate) {
		this.vacineReference[this.currentDose] = vaccine;
		this.vaccinationSite[this.currentDose] = vaccinationSite;
		this.vaccinationDate[this.currentDose] = vaccinationDate;

		incrementcurrentDose();
	}
	
	public void setAppointmentStatus(String bookedAppointmentStatus) {
		this.bookedAppointmentStatus = bookedAppointmentStatus;
	}

	public String getAppointmentStatus() {
		return bookedAppointmentStatus;
	}
}
