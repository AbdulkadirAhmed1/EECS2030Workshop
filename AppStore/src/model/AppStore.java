package model;

//By Abdulkadir Ahmed

/*
* App Store: - stores apps in given branch */

public class AppStore {
	String branchName;
	App[] appStore;
	int currentAppStoreIndex;

	public AppStore(String branchName,int maxApps) {
		this.branchName = branchName;
		this.appStore = new App[maxApps];
		this.currentAppStoreIndex = 0;
	}
	
	public int getAppStoreSize() {
		int Size = 0;
		
		for (int i = 0; i < appStore.length; i++) {
			if (appStore[i] != null) {
				Size++;
			}
		}
		
		return Size;
	}
	
	public App[] getAvailableApps() {
		App[] availableApps = new App[getAppStoreSize()];
		
		for (int i = 0; i < appStore.length; i++) {
			if (appStore[i] != null) {
				availableApps[i] = appStore[i];
			}
		}
		
		return availableApps;
	}

	public String getBranch() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}
	
	public void addApp(App app) {
		this.appStore[this.currentAppStoreIndex] = app;
		
		this.currentAppStoreIndex++;
	}
	
	public App getApp(String appName) {
		App getApp = null;
		
		for (int i = 0; i < appStore.length; i++) {
			if (appStore[i] != null) {
				if (appStore[i].getName().equals(appName)) {
					getApp = appStore[i];
				}
			}
		}
		
		return getApp;
	}
	
	public String[] getStableApps(int updateAmount) {
		App[] stableApps = new App[getAppStoreSize()];
		int stableAppsSize = 0;
		
		for (int i = 0; i < appStore.length; i++) {
			if (appStore[i] != null) {
				if (appStore[i].getUpdateHistory().length >= updateAmount) {
					stableAppsSize++;
					stableApps[i] = appStore[i];
				}
			}
		}
		
		String[] stringStableApps = new String[stableAppsSize];
		
		int sIndex = 0; // this only adds for non-null index
		// to prevent "IndexOutOfBounds" exception
		
		for (int i = 0; i < stableApps.length; i++) {
			if (stableApps[i] != null) {
				App currentApp = stableApps[i];
				
				currentApp.updateLatestUpdate();
				
				stringStableApps[sIndex++] = 
						currentApp.getName() + " (" + currentApp.getUpdateHistory().length + " versions; " 
						+ "Current Version: " + currentApp.getWhatIsNew() + ")";
			}
		}
		
		return stringStableApps;
	}
}
