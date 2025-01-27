package model;

//By Abdulkadir Ahmed

/*
* Logs: - stores version and fixes for the version */

public class Log {
	private final int maxFixes = 10;
	
	String version;
	int numberOfFixes;
	String fixes;
	String[] fixesArrayList = new String[maxFixes]; // fixes String array
	

	public Log(String version) {
		this.version = version;
		this.numberOfFixes = 0;
		this.fixes = "[]";
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}
	
	public int getNumberOfFixes() {
		return numberOfFixes;
	}

	public void setNumberOfFixes(int numberOfFixes) {
		this.numberOfFixes = numberOfFixes;
	}

	public String getFixes() {
		return fixes;
	}

	public void setFixes(String fixes) {
		this.fixes = fixes;
	}
	
	public String[] getFixesList() {
		return fixesArrayList;
	}

	public void setFixesList(String[] fixesList) {
		this.fixesArrayList = fixesList;
	}

	public void addFix(String msg) {
		fixesArrayList[numberOfFixes] = msg;
		
		String tempFixes = "[" + fixesArrayList[0];
		
		for (int i = 1; i < fixesArrayList.length; i++) {
			if (fixesArrayList[i] != null) {
				tempFixes += ", " + fixesArrayList[i];
			}
		}
		
		tempFixes += "]";
		
		setFixes(tempFixes);
		
		numberOfFixes += 1;
	}
	
	public String toString() {
		return "Version " + this.version + " contains " 
	+ this.numberOfFixes + " fixes " + this.fixes;
	}
}
