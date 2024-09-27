package model;

// By Abdulkadir Ahmed

/*
 * App: - stores logs and reviews */

public class App {
	private final int maxUpdates = 20;
	
	String appName;
	String latestUpdate;
	Log[] updateHistory;
	String RatingReport;
	int currentHistoryRatingIndex;
	int[] appRatings;
	int currentAppRatingsIndex;
	
	public App(String appName,int maxRatings) {
		this.appName = appName;
		this.latestUpdate = "n/a";
		this.updateHistory = new Log[maxUpdates];
		this.RatingReport = "No ratings submitted so far!";
		this.currentHistoryRatingIndex = 0;
		this.appRatings = new int[maxRatings];
		this.currentAppRatingsIndex = 0;
	}

	public String getName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}
	
	public String getWhatIsNew() {
		return this.latestUpdate;
	}

	public Log[] getUpdateHistory() {
		int Size = 0;
		Log[] ModifedUpdateHistory = new Log[0];
		boolean emptyUpdateHistory = true;
		int FoundValues = 0;
		
		for (int i = 0; i < this.updateHistory.length; i++) {
			if(this.updateHistory[i] != null)  {
				FoundValues ++;
				
			}
		}
		
		if (FoundValues > 0) {
			Size = FoundValues;
			emptyUpdateHistory = false;
			
			Log[] FoundValuesArray = new Log[Size];
			
			for (int i = 0; i < this.updateHistory.length; i++) {
				if(this.updateHistory[i] != null)  {
					FoundValuesArray[i] = this.updateHistory[i];			
				}
			}
			
			ModifedUpdateHistory = FoundValuesArray;
		}
		
		return ModifedUpdateHistory;
	}

	public void setUpdateHistory(Log[] updateHistory) {
		this.updateHistory = updateHistory;
	}

	public void setRatingReport(String ratingReport) {
		RatingReport = ratingReport;
	}

	public Log getVersionInfo(String version) {
		Log l = null;
		
		for (int i = 0; i < updateHistory.length; i++) {
			if (this.updateHistory[i] != null) {
				if (this.updateHistory[i].getVersion().equals(version)) {
					l = this.updateHistory[i];
				}
			}
		}
		
		return l;
	}
	
	public void updateLatestUpdate() {
		Log[] getLatestLog = getUpdateHistory();
		
		if (getLatestLog.length > 0) {
			Log currentLatestUpdate = getLatestLog[getLatestLog.length - 1];
			
			if (getLatestLog[getLatestLog.length - 1] != null) {
				this.latestUpdate = currentLatestUpdate.toString();
			}
		}
	}
	
	public void releaseUpdate(String version) {;	
	    Log update = this.updateHistory[currentHistoryRatingIndex];
		
	    if (update != null) {
	    	this.latestUpdate = update.toString();
		} else {
			update = new Log(version);
					
			this.latestUpdate = update.toString();
		}
		
		this.updateHistory[currentHistoryRatingIndex] = update;
		
		this.currentHistoryRatingIndex++;
	}
	
	public double ratingAverage() {	
		double sum = 0;
		double values = 0;
		
		for (int i = 0; i < appRatings.length; i++) {
			if (appRatings[i] != 0) {
				sum += appRatings[i];
				values++;
			}
		}
		
		double average = sum/values;
		
		return average;
	}
	
	public void submitRating(int Rating) {
		this.appRatings[currentAppRatingsIndex] = Rating;
		
		currentAppRatingsIndex++;
	}
	
	public boolean anyRatings() {
		boolean foundRatings = false;
		
		for (int i = 0; i < this.appRatings.length; i++) {
			if (appRatings[i] != 0) {
				foundRatings = true;
			}
		}
		
		return foundRatings;
	}
	
	public String getRatingReport() {
		if (anyRatings() == true) {
			int[] ratingCases = new int[5]; // Side note for me: This has array like this [3,2,3,4,5] 
			// where index 0 corresponds to ratings amount for 1 rating  
			// and index 1 corresponds to rating amounts for 2 ratings  
			//and etc.... up till rating 5

			for (int i = 0; i < this.appRatings.length; i++) {
				if (this.appRatings[i] == 1) {
					ratingCases[0]++;
				} else if(this.appRatings[i] == 2) {
					ratingCases[1]++;
				}  else if(this.appRatings[i] == 3) {
					ratingCases[2]++;
				} else if(this.appRatings[i] == 4) {
					ratingCases[3]++;
				} else if(this.appRatings[i] == 5) {
					ratingCases[4]++;
				}
			}

			int ratingsTillNow = 0;

			for (int i = 0; i < this.appRatings.length; i++) {
				if (this.appRatings[i] != 0) {
					ratingsTillNow++;
				}
			}

			double average = ratingAverage();

			String ratingReportOutput = "Average of " + ratingsTillNow 
					+ " ratings: " + String.format("%.1f", average) 
					+ " (Score 5: " + ratingCases[4]
							+ ", Score 4: " + ratingCases[3]	
									+ ", Score 3: " + ratingCases[2]
											+ ", Score 2: " + ratingCases[1]
													+ ", Score 1: " + ratingCases[0] + ")"

													;

			this.RatingReport = ratingReportOutput;
			
			return ratingReportOutput;
		} else {
			return this.RatingReport;
		}
	}
	
 	public String toString() {
 		updateLatestUpdate();
 		
		if (this.RatingReport == "No ratings submitted so far!") {
			return this.appName + " (Current Version: " + this.latestUpdate + "; Average Rating: " + "n/a)";
		} else {
			return this.appName + " (Current Version: " + this.latestUpdate + "; Average Rating: " + String.format("%.1f", ratingAverage()) + ")";
		}
	}
}
