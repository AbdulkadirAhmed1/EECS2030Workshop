package model;

import java.util.Iterator;

//By Abdulkadir Ahmed

/*
* Account: - makes an account that runs a certain app store given the branch(region) */

public class Account {
	private final int maxDownloads = 20;
	
	String accountName;
	AppStore accountAppStore;
	App[] downloadedApps;
	String status;
	int currentDownloadedAppsIndex;
	
	public Account(String accountName,AppStore accountAppStore) {
		this.accountName = accountName;
		this.accountAppStore = accountAppStore;
		this.downloadedApps = new App[maxDownloads];
		this.status = "An account linked to the " 
				+ this.accountAppStore.getBranch() 
				+ " store is created for " + this.accountName + ".";
		this.currentDownloadedAppsIndex = 0;
	}
	
	public void switchStore(AppStore accountAppStore) {
		this.accountAppStore = accountAppStore;
		this.status = "Account for " 
				+ this.accountName
				+ " is now linked to the " + this.accountAppStore.getBranch() + " store.";
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public AppStore getAccountAppStore() {
		return accountAppStore;
	}

	public void setAccountAppStore(AppStore accountAppStore) {
		this.accountAppStore = accountAppStore;
	}
	
	public String[] getNamesOfDownloadedApps() {
		int Size = 0;
		
		for (int i = 0; i < downloadedApps.length; i++) {
			if (downloadedApps[i] != null) {
				Size++;
			}
		}
		
		String[] downloadedAppsNames = new String[Size];
		
		int tI = 0;
		
		for (int i = 0; i < downloadedApps.length; i++) {
			if (downloadedApps[i] != null) {
				downloadedAppsNames[tI++] = downloadedApps[i].getName();
			}
		}
		
		return downloadedAppsNames;
	}
	
	public App[] getObjectsOfDownloadedApps() {
		int Size = 0;
		
		for (int i = 0; i < downloadedApps.length; i++) {
			if (downloadedApps[i] != null) {
				Size++;
			}
		}
		
		App[] downloadedAppsObject = new App[Size];
		
		int tI = 0;
		
		for (int i = 0; i < downloadedApps.length; i++) {
			if (downloadedApps[i] != null) {
				downloadedAppsObject[tI++] = downloadedApps[i];
			}
		}
		
		return downloadedAppsObject;
	}
	
	public void download(String appName) {
		App[] availableApps = this.accountAppStore.getAvailableApps();

		App foundApp = null;
		
		for (int i = 0; i < availableApps.length; i++) {
			if (availableApps[i].getName().equals(appName)) {
				foundApp = availableApps[i];
				break; // we add break because this 1 in case scenario 
				// meaning we only need 1 case to be true for the whole
				// case to be true
			}
		}
		
		if (foundApp != null) {
			boolean AppExistsInDownloads = false;
				
			for (int i = 0; i < this.downloadedApps.length; i++) {
				if (this.downloadedApps[i] != null) {
					if (this.downloadedApps[i].getName().equals(appName)) {
						AppExistsInDownloads = true;
						break; // we add break because this 1 in case scenario 
						// meaning we only need 1 case to be true for the whole
						// case to be true
					}
				}
			}
			
			if (AppExistsInDownloads == false)	 {
				this.downloadedApps[currentDownloadedAppsIndex] = foundApp;
				
				currentDownloadedAppsIndex++;
				
				this.status = appName + " is successfully downloaded for " + this.accountName + ".";
			} else {
				this.status = "Error: " + appName + " has already been downloaded for " + this.accountName + ".";
			}
		} else {
			this.status = "Error: attempting to download an app that doesn't exist";
		}
	}
	
	public void uninstall(String appName) {
		App[] downloadedApps = getObjectsOfDownloadedApps();
		
		if (downloadedApps.length > 0) {
			boolean appExistsInDownloads = false;
			
			for (int j = 0; j < this.downloadedApps.length; j++) {
				if (this.downloadedApps[j] != null) {
					if (this.downloadedApps[j].getName().equals(appName)) {
						appExistsInDownloads = true;
						this.downloadedApps[j] = null;
					}
				}
			}
			
			if (appExistsInDownloads == false) {
				this.status = "Error: " + appName + " has not been downloaded for " + this.accountName + ".";
			} else {
				this.status = appName + " is successfully uninstalled for " + this.accountName + ".";
			}
		} else { 
			this.status = "Error: " + appName + " has not been downloaded for " + this.accountName + ".";
		}
	}
	
	public void submitRating(String appName,int rating) {
		App[] downloadedApps = getObjectsOfDownloadedApps();
		
		if (downloadedApps.length > 0) {
			App getApp = null;
			
			for (int j = 0; j < this.downloadedApps.length; j++) {
				if (this.downloadedApps[j] != null) {
					if (this.downloadedApps[j].getName().equals(appName)) {
						getApp = this.downloadedApps[j];
					}
				}
			}
			
			if (getApp == null) {
				this.status = "Error: " + appName + " is not a downloaded app for " + this.accountName + ".";
			} else {
				getApp.submitRating(rating);
				this.status = "Rating score " + rating + " of " + this.accountName + " is successfully submitted for " + appName + ".";
			}
		} else {
			this.status = "Error: " + appName + " is not a downloaded app for " + this.accountName + ".";
		}
	}
	
	public String toString() {
		return this.status;
	}
}
