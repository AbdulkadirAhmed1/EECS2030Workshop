package junit_tests;

import static org.junit.Assert.*;

import org.junit.Test;

import model.Account;
import model.App;
import model.AppStore;
import model.Log;

/*
 * Requirement: Any classes you create must reside in the `model` package and be imported properly.
 * For example, creating a new class `Foo` in the `model` package should result in:
 * 	import model.Foo;
 */
public class StarterTestsMod {
	
	/* 
	 * Programming Requirements:
	 * 
	 * 	- You are only allowed to use primitive arrays (e.g., int[], String[], Product[]) 
	 * 		for declaring attributes and implementing the idea of collections.
	 * 	- Any use of a Java library class or method is forbidden 
	 * 		(that is, use selections and loops to build your solution from scratch instead):
	 * 	- Here are some examples of forbidden classes/methods: 
	 * 		- Arrays class (e.g., Arrays.copyOf)
	 * 		- System class (e.g., System.arrayCopy)
	 * 		- ArrayList class
	 * 		- String class (e.g., substring).
	 * 	- The use of some library classes does not require an import statement, 
	 * 		but these classes are also forbidden to be used.
	 * 	- Here are the exceptions (library methods which you are allowed to use if needed):
	 * 		- String class (equals, format)
	 * 
	 * Violating the above programming requirements will result in a penalty (see lab instructions for details). 
	 * 
	 * Tests included in this class serve as documentation on how instances of an Apple AppStore operates.
	 * 
	 * Before attempting this lab, it is expected that you already completed background study materials:
	 * 	1. Review Tutorial Series on OOP in Java (Part 1 and Part 2): 
	 * 		https://www.eecs.yorku.ca/~jackie/teaching/tutorials/index.html#refurbished_store
	 * 	2. Written Notes on Reference-Typed, Multi-Valued Attributes:
	 * 		https://www.eecs.yorku.ca/~jackie/teaching/lectures/2021/F/EECS2030/notes/EECS2030_F21_Tracing_PointCollectorTester.pdf
	 * 	3. Written Notes on Inferring Classes from JUnit Tests:
	 * 		https://www.eecs.yorku.ca/~jackie/teaching/lectures/2021/F/EECS2030/notes/EECS2030_F21_Inferring_Classes_from_JUnit.pdf 
	 * 
	 * Be sure to also read the following sections from your Lab1 instructions PDF:
	 * 	- The `Requirements of this Lab` section (page 3) 
	 * 	- Section 2.3 The Apple AppStore Problem
	 * 	- Section 2.4 Hints and Requirements
	 * 
	 * Programming IDEs such as Eclipse are able to fix some compilation errors for you. 
	 * However, you are advised to follow the guidance as specified in the written notes above
	 * to fix these compilation errors manually, because: 
	 * 	1) it helps you better understand how the intended classes and methods work together; and 
	 * 	2) you may be tested in a written test or exam without the assistance of IDEs.
	 * 
	 */
	
	/*
	 * Tests related to the Log class.
	 */
	
	/*
	 * Recommended exercises: 
	 * 	Visualizing and tracing (on both debugger and paper) on how objects are created and manipulated
	 * 	in each test would be extremely valuable for reinforcing your understanding.
	 */ 
	@Test
	public void test_log_01() {
		/* Create a new app update log with a version number */
		Log appUpdate = new Log("10.21.31");
		
		/* Retrieve the set version value. */
		String v = appUpdate.getVersion();
		assertEquals("10.21.31", v);
		
		/* Initially, no fixes have been added to the update log. */
		int n = appUpdate.getNumberOfFixes();
		assertEquals(0, n);
		
		/* The list of fixes (appearing within a pair of square brackets) is empty */
		String s1 = appUpdate.getFixes();
		assertEquals("[]", s1);
		
		/* The string representation of an update log object includes:
		 * 	- its set version
		 * 	- the number of fixes so far
		 * 	- a comma-separated list of fixes, enclosed within a pair of square brackets
		 * Note: Always spell the word "fixes" even when the number of fixes is 0 or 1.
		 */
		String s2 = appUpdate.toString();
		assertEquals("Version 10.21.31 contains 0 fixes []", s2); 
	}
	
	/*
	 * Recommended exercises: 
	 * 	Visualizing and tracing (on both debugger and paper) on how objects are created and manipulated
	 * 	in each test would be extremely valuable for reinforcing your understanding.
	 */
	@Test
	public void test_log_02() {
		/* Create a new app update log with a version number */
		Log appUpdate = new Log("21.31.10");
		
		/* Add two fixes to the update log.
		 * See Section 2.3 of lab instructions for more details 
		 * 	(e.g., maximum number of fixes in an update log). 
		 * No error handling is needed when the max limit is exceeded.
		 */
		appUpdate.addFix("#0 Addressed writing lag issues");
		appUpdate.addFix("#3 Fixed a bug about dismissing menus");
		
		assertEquals("21.31.10", appUpdate.getVersion());
		/* So far, two fixes have been added to the app update log. */
		assertEquals(2, appUpdate.getNumberOfFixes());
		/* The list of fixes (appearing within the pair of square brackets) is comma-separated.
		 * Note the white spaces appearing after commas (,).  
		 */
		assertEquals("[#0 Addressed writing lag issues, #3 Fixed a bug about dismissing menus]", appUpdate.getFixes());
		assertEquals("Version 21.31.10 contains 2 fixes [#0 Addressed writing lag issues, #3 Fixed a bug about dismissing menus]", appUpdate.toString()); 
	}
	
	/*
	 * Tests related to the App class.
	 */
	
	/*
	 * Recommended exercises: 
	 * 	Visualizing and tracing (on both debugger and paper) on how objects are created and manipulated
	 * 	in each test would be extremely valuable for reinforcing your understanding.
	 */
	@Test
	public void test_app_01() {
		/* Create a new app with a name and the maximum number of ratings allowed to be submitted. */
		App app = new App("BadNotes 6", 15);
		
		/* Retrieve the set name of the app. */
		String s1 = app.getName();
		assertEquals("BadNotes 6", s1);
		
		/* Retrieve the latest update log of the app.
		 * Initially, no updates have been released for the new app. 
		 */
		String s2 = app.getWhatIsNew();
		assertEquals("n/a", s2);
		
		/* Retrieve the update history of the app (as an array of log objects).
		 */
		Log[] history = app.getUpdateHistory();
		assertTrue(history.length == 0);
		
		/* Initially, no log object is associated with any version number. */
		Log log = app.getVersionInfo("2.0.1");
		assertNull(log);
		
		/* Initially, no ratings have been submitted by registered accounts. */
		String s3 = app.getRatingReport();
		assertEquals("No ratings submitted so far!", s3);
		
		/*
		 * The string representation of an app object includes:
		 * 	- its name
		 * 	- log information of current/latest version/release (n/a when none have been released)
		 * 	- an average of rating scores received so far (n/a when none have been submitted)
		 */
		String s4 = app.toString();
		assertEquals("BadNotes 6 (Current Version: n/a; Average Rating: n/a)", s4);
	}
	
	/*
	 * Recommended exercises: 
	 * 	Visualizing and tracing (on both debugger and paper) on how objects are created and manipulated
	 * 	in each test would be extremely valuable for reinforcing your understanding.
	 */
	@Test
	public void test_app_02() {
		/* Create a new app with a name and the maximum number of ratings allowed to be submitted. */
		App app = new App("GoodNotes 5", 15);
		
		/* Release three updates (by adding their version numbers).
		 * 
		 * See Section 2.3 of lab instructions for more details 
		 * 	(e.g., maximum number of updates allowed for an app). 
		 * No error handling is needed when the max limit is exceeded. 
		 */
		app.releaseUpdate("15.27.27");
		app.releaseUpdate("15.27.29");
		app.releaseUpdate("15.27.31");
		
		/* Retrieve the set name of the app. */
		assertEquals("GoodNotes 5", app.getName());
		
		/* Get information about the last-released version. */
		assertEquals("Version 15.27.31 contains 0 fixes []", app.getWhatIsNew());
		
		assertTrue(app.getUpdateHistory().length == 3);
		/* Retrieve the list of update log objects and compare their toString return values. */
		assertTrue(app.getUpdateHistory()[0].toString().equals("Version 15.27.27 contains 0 fixes []"));
		assertTrue(app.getUpdateHistory()[1].toString().equals("Version 15.27.29 contains 0 fixes []"));
		assertTrue(app.getUpdateHistory()[2].toString().equals("Version 15.27.31 contains 0 fixes []"));
		
		/* Add fixes to each stored update log. */
		app.getUpdateHistory()[0].addFix("@1 Support for new M1 ipad Pro");
		app.getUpdateHistory()[1].addFix("@2 Fixed a launch crash");
		app.getUpdateHistory()[1].addFix("@3 Improved scroll animations");
		app.getUpdateHistory()[2].addFix("@4 Better logging");
		app.getUpdateHistory()[2].addFix("@5 Improved performance");
		app.getUpdateHistory()[2].addFix("@6 Stability improvements");
		
		assertTrue(app.getUpdateHistory().length == 3);
		/* Retrieve the list of update log objects and compare their toString return values. */
		assertEquals("Version 15.27.27 contains 1 fixes [@1 Support for new M1 ipad Pro]", app.getUpdateHistory()[0].toString());
		assertEquals("Version 15.27.29 contains 2 fixes [@2 Fixed a launch crash, @3 Improved scroll animations]", app.getUpdateHistory()[1].toString());
		assertEquals("Version 15.27.31 contains 3 fixes [@4 Better logging, @5 Improved performance, @6 Stability improvements]", app.getUpdateHistory()[2].toString());
		
		/* Given a version number, if valid, retrieve the corresponding log object and compare its toString return value. */
		assertEquals("Version 15.27.27 contains 1 fixes [@1 Support for new M1 ipad Pro]", app.getVersionInfo("15.27.27").toString());
		assertEquals("Version 15.27.29 contains 2 fixes [@2 Fixed a launch crash, @3 Improved scroll animations]", app.getVersionInfo("15.27.29").toString());
		assertEquals("Version 15.27.31 contains 3 fixes [@4 Better logging, @5 Improved performance, @6 Stability improvements]", app.getVersionInfo("15.27.31").toString());
		/* No log object is associated with this non-existing version number. */
		assertNull(app.getVersionInfo("15.27.33"));  
	}
	
	/*
	 * Recommended exercises: 
	 * 	Visualizing and tracing (on both debugger and paper) on how objects are created and manipulated
	 * 	in each test would be extremely valuable for reinforcing your understanding.
	 */
	@Test
	public void test_app_03() {
		/* Create a new app with a name and the maximum number of ratings allowed to be submitted. */
		App app = new App("BadNotes 5", 15);
		
		/* Release three updates (by adding their version numbers).
		 * 
		 * See Section 2.3 of lab instructions for more details 
		 * 	(e.g., maximum number of updates allowed for an app). 
		 * No error handling is needed when the max limit is exceeded. 
		 */
		app.releaseUpdate("45.57.127");
		app.releaseUpdate("45.57.129");
		app.releaseUpdate("45.57.131");
		
		/* Add fixes to each stored update log. */
		app.getUpdateHistory()[0].addFix("@11 Support for new M1 ipad Pro");
		app.getUpdateHistory()[1].addFix("@12 Fixed a launch crash");
		app.getUpdateHistory()[1].addFix("@13 Improved scroll animations");
		app.getUpdateHistory()[2].addFix("@14 Better logging");
		app.getUpdateHistory()[2].addFix("@15 Improved performance");
		app.getUpdateHistory()[2].addFix("@16 Stability improvements");
	
		/*
		 * Submit rating scores to the app.
		 * 
		 * You can assume that each rating score is between 1 and 5.
		 * 
		 * The maximum number of submitted ratings allowed for an app has been set to 15 
		 * 	(see the constructor call in the first line).
		 * No error handling is needed when the limit is exceeded 
		 * (See Section 2.3 of lab instructions for more details). 
		 */
		app.submitRating(3);
		
		/*
		 * Hints on obtaining the rating report:
		 * 	- Always spell `ratings` even when there is only zero or one.
		 * 	- The average is always displayed with a digit after the decimal point.
		 * 	- Within the round parentheses (...), display a comma-separated breakdown of the number of submissions for each rating score.
		 * 	- Refer to your Review Tutorial to see how to format a floating-number.   
		 */
		assertEquals("Average of 1 ratings: 3.0 (Score 5: 0, Score 4: 0, Score 3: 1, Score 2: 0, Score 1: 0)", app.getRatingReport());
		
		app.submitRating(4);
		assertEquals("Average of 2 ratings: 3.5 (Score 5: 0, Score 4: 1, Score 3: 1, Score 2: 0, Score 1: 0)", app.getRatingReport());
		
		app.submitRating(4);
		assertEquals("Average of 3 ratings: 3.7 (Score 5: 0, Score 4: 2, Score 3: 1, Score 2: 0, Score 1: 0)", app.getRatingReport());
				
		assertEquals("BadNotes 5 (Current Version: Version 45.57.131 contains 3 fixes [@14 Better logging, @15 Improved performance, @16 Stability improvements]; Average Rating: 3.7)", app.toString());
	}
	
	/*
	 * Tests related to the AppStore class.
	 */
	
	/*
	 * Recommended exercises: 
	 * 	Visualizing and tracing (on both debugger and paper) on how objects are created and manipulated
	 * 	in each test would be extremely valuable for reinforcing your understanding.
	 */
	@Test
	public void test_app_store_01() {
		/* Create a new app store with a branch name and the maximum number of apps allowed to be available. */
		AppStore store = new AppStore("UK", 100);
		
		/* Retrieve the set branch of the app store. */
		String branch = store.getBranch();
		assertEquals("UK", branch);
		
		/* Retrieve the app object of the given name. 
		 * Initially, no apps are available in the app store. 
		 */
		App app = store.getApp("VeryGoodNotes 5");
		assertNull(app);
		
		/* Retrieve the information of all available apps in the app store that are stable, i.e., contain more than 2 updates.
		 * Initially, there are no apps in the app store and thus no apps are stable.
		 * Note that the return value of getStableApps is a string array.  
		 */
		String[] stableApps = store.getStableApps(2);
		assertTrue(stableApps.length == 0);
	}
	
	/*
	 * Recommended exercises: 
	 * 	Visualizing and tracing (on both debugger and paper) on how objects are created and manipulated
	 * 	in each test would be extremely valuable for reinforcing your understanding.
	 */
	@Test
	public void test_app_store_02() {
		/* Create a new app store with a branch name and the maximum number of apps allowed to be available. */
		AppStore store = new AppStore("US", 100);
		
		/* Create three apps and add them to the app store */
		App app1 = new App("GoodNotes 5", 15);
		app1.releaseUpdate("15.17.127");
		app1.releaseUpdate("15.17.129");
		app1.releaseUpdate("15.17.131");
		app1.getUpdateHistory()[0].addFix("@x1 Support for new M1 ipad Pro");
		app1.getUpdateHistory()[1].addFix("@x2 Improved scroll animations");
		app1.getUpdateHistory()[2].addFix("@x3 Better logging");
		
		App app2 = new App("Things 4 for iPad", 25); /* Note that the maximum number of allowed ratings is different from app1 */
		app2.releaseUpdate("13.115.11"); 
		app2.getUpdateHistory()[0].addFix("@x4 Improved data migration");
		
		App app3 = new App("Notability", 20); /* Note that the maximum number of allowed ratings is different from app1 */
		app3.releaseUpdate("110.14.15");
		app3.releaseUpdate("110.15.12");
		app3.getUpdateHistory()[0].addFix("@x5 Fixed a multi-window issue");
		app3.getUpdateHistory()[1].addFix("@x6 Added line spacing options for text");
		
		store.addApp(app1);
		store.addApp(app2);
		store.addApp(app3);
		
		String branch = store.getBranch();
		assertEquals("US", branch);
		
		/* So far, 3 apps are available in the app store. */
		assertSame(app1, store.getApp("GoodNotes 5"));
		assertSame(app2, store.getApp("Things 4 for iPad"));
		assertSame(app3, store.getApp("Notability"));
		
		/* 2 apps are stable, i.e., contain at least (i.e., larger than or equal to) 2 updates.
		 * Note that the return value of getStableApps is a string array. 
		 */
		assertTrue(store.getStableApps(2).length == 2);
		assertEquals("GoodNotes 5 (3 versions; Current Version: Version 15.17.131 contains 1 fixes [@x3 Better logging])", store.getStableApps(2)[0]);
		assertEquals("Notability (2 versions; Current Version: Version 110.15.12 contains 1 fixes [@x6 Added line spacing options for text])", store.getStableApps(2)[1]);
	}
	
	/*
	 * Tests related to the Account class.
	 */
	
	/*
	 * Recommended exercises: 
	 * 	Visualizing and tracing (on both debugger and paper) on how objects are created and manipulated
	 * 	in each test would be extremely valuable for reinforcing your understanding.
	 */
	@Test
	public void test_account_01() {
		/* Set up an app store with 3 available apps. */
		AppStore canadianStore = new AppStore("Canada", 100);
		
		App app1 = new App("GoodNotes 6", 15);
		app1.releaseUpdate("5.7.27");
		app1.releaseUpdate("5.7.29");
		app1.releaseUpdate("5.7.31");
		app1.getUpdateHistory()[0].addFix("Support for new M1 ipad Pro");
		app1.getUpdateHistory()[1].addFix("Improved scroll animations");
		app1.getUpdateHistory()[2].addFix("Better logging");
		
		App app2 = new App("Things 3 for iPad", 25); /* Note that the maximum number of allowed ratings is different from app1 */
		app2.releaseUpdate("3.15.1"); 
		app2.getUpdateHistory()[0].addFix("Improved data migration");
		
		App app3 = new App("Notability", 20); /* Note that the maximum number of allowed ratings is different from app1 */
		app3.releaseUpdate("10.4.5");
		app3.releaseUpdate("10.5.2");
		app3.getUpdateHistory()[0].addFix("Fixed a multi-window issue");
		app3.getUpdateHistory()[1].addFix("Added line spacing options for text");
		
		canadianStore.addApp(app1);
		canadianStore.addApp(app2);
		canadianStore.addApp(app3);
		
		/* Create a new account with a name and its registered/linked store. */
		Account acc1 = new Account("P1@Suyeon", canadianStore);
		
		/* Get the status of account acc1. See Section 2.3 of the lab instructions. */
		String status = acc1.toString(); 
		assertEquals("An account linked to the Canada store is created for P1@Suyeon.", status);
		
		/* No apps have been downloaded for the new account. */
		String[] namesOfDownloadedApps = acc1.getNamesOfDownloadedApps();
		assertTrue(namesOfDownloadedApps.length == 0);
		
		App[] objectsOfDownloadedApps = acc1.getObjectsOfDownloadedApps();
		assertTrue(objectsOfDownloadedApps.length == 0);
		
		/* Since the account has no downloaded apps, nothing can be uninstalled. */
		acc1.uninstall("GoodNotes 6");
		
		status = acc1.toString();
		assertEquals("Error: GoodNotes 6 has not been downloaded for P1@Suyeon.", status);
		
		/* Since the account has no downloaded apps, the account owner cannot submit any ratings. */
		acc1.submitRating("GoodNotes 6", 4);
		
		status = acc1.toString();
		assertEquals("Error: GoodNotes 6 is not a downloaded app for P1@Suyeon.", status); 
		
		/* Switch to a different store for the account. */
		acc1.switchStore(new AppStore("UK", 125));
		
		status = acc1.toString(); 
		assertEquals("Account for P1@Suyeon is now linked to the UK store.", status);
	}
	
	/*
	 * Recommended exercises: 
	 * 	Visualizing and tracing (on both debugger and paper) on how objects are created and manipulated
	 * 	in each test would be extremely valuable for reinforcing your understanding.
	 */
	@Test
	public void test_account_02() {
		/* Set up an app store with 3 available apps. */
		AppStore canadianStore = new AppStore("Canada", 100);
		
		App app1 = new App("GoodNotes 5", 15);
		app1.releaseUpdate("5.7.27");
		app1.releaseUpdate("5.7.29");
		app1.releaseUpdate("5.7.31");
		app1.getUpdateHistory()[0].addFix("Support for new M1 ipad Pro");
		app1.getUpdateHistory()[1].addFix("Improved scroll animations");
		app1.getUpdateHistory()[2].addFix("Better logging");
		
		App app2 = new App("Things 3 for iPad", 25); /* Note that the maximum number of allowed ratings is different from app1 */
		app2.releaseUpdate("3.15.1"); 
		app2.getUpdateHistory()[0].addFix("Improved data migration");
		
		App app3 = new App("Notability", 20); /* Note that the maximum number of allowed ratings is different from app1 */
		app3.releaseUpdate("10.4.5");
		app3.releaseUpdate("10.5.2");
		app3.getUpdateHistory()[0].addFix("Fixed a multi-window issue");
		app3.getUpdateHistory()[1].addFix("Added line spacing options for text");
		
		canadianStore.addApp(app1);
		canadianStore.addApp(app2);
		canadianStore.addApp(app3);
		
		/* Create three new accounts with names and a common registered store. */
		Account acc1 = new Account("Suyeon~P1", canadianStore);
		Account acc2 = new Account("Yuna~P2", canadianStore);
		Account acc3 = new Account("Heeyeon~P3", canadianStore);
		
		/* Download apps for registered accounts and check their statues.
		 * Note that the `download` method takes as input a value matching the type of app1.getName(). 
		 */
		acc1.download(app1.getName());
		assertEquals("GoodNotes 5 is successfully downloaded for Suyeon~P1.", acc1.toString());
		
		acc2.download(app1.getName());
		assertEquals("GoodNotes 5 is successfully downloaded for Yuna~P2.", acc2.toString());
		acc2.download(app2.getName());		
		assertEquals("Things 3 for iPad is successfully downloaded for Yuna~P2.", acc2.toString());
		
		acc3.download(app1.getName());
		assertEquals("GoodNotes 5 is successfully downloaded for Heeyeon~P3.", acc3.toString());
		acc3.download(app2.getName());
		assertEquals("Things 3 for iPad is successfully downloaded for Heeyeon~P3.", acc3.toString());		
		acc3.download(app3.getName());
		assertEquals("Notability is successfully downloaded for Heeyeon~P3.", acc3.toString());
		
		/* Attempt to download an app that's already downloaded: no change and signal an error in status. */
		acc3.download(app2.getName());
		assertTrue(acc3.getNamesOfDownloadedApps().length == 3);
		assertTrue(acc3.getObjectsOfDownloadedApps().length == 3);
		assertEquals("Error: Things 3 for iPad has already been downloaded for Heeyeon~P3.", acc3.toString());
	}
	
	/*
	 * Recommended exercises: 
	 * 	Visualizing and tracing (on both debugger and paper) on how objects are created and manipulated
	 * 	in each test would be extremely valuable for reinforcing your understanding.
	 */
	@Test
	public void test_account_03() {
		/* Set up an app store with 3 available apps. */
		AppStore canadianStore = new AppStore("Canada", 100);
		
		App app1 = new App("GoodNotes 15", 15);
		app1.releaseUpdate("5.7.27");
		app1.releaseUpdate("5.7.29");
		app1.releaseUpdate("5.7.31");
		app1.getUpdateHistory()[0].addFix("Support for new M1 ipad Pro");
		app1.getUpdateHistory()[1].addFix("Improved scroll animations");
		app1.getUpdateHistory()[2].addFix("Better logging");
		
		App app2 = new App("Things 13 for iPad", 25); /* Note that the maximum number of allowed ratings is different from app1 */
		app2.releaseUpdate("3.15.1"); 
		app2.getUpdateHistory()[0].addFix("Improved data migration");
		
		App app3 = new App("Notability1", 20); /* Note that the maximum number of allowed ratings is different from app1 */
		app3.releaseUpdate("10.4.5");
		app3.releaseUpdate("10.5.2");
		app3.getUpdateHistory()[0].addFix("Fixed a multi-window issue");
		app3.getUpdateHistory()[1].addFix("Added line spacing options for text");
		
		canadianStore.addApp(app1);
		canadianStore.addApp(app2);
		canadianStore.addApp(app3);
		
		/* Create three new accounts with names and a common registered store. */
		Account acc1 = new Account("P1#Suyeon", canadianStore);
		Account acc2 = new Account("P2#Yuna", canadianStore);
		Account acc3 = new Account("P3#Heeyeon", canadianStore);
		
		acc1.download(app1.getName());
		acc2.download(app1.getName());
		acc2.download(app2.getName());		
		acc3.download(app1.getName());
		acc3.download(app2.getName());		
		acc3.download(app3.getName());
		
		/* So far, each account has downloaded different numbers of apps. */
		assertTrue(acc1.getNamesOfDownloadedApps().length == 1);
		assertTrue(acc2.getNamesOfDownloadedApps().length == 2);
		assertTrue(acc3.getNamesOfDownloadedApps().length == 3);
		
		/* Check the names of the downloaded apps for each account. */
		assertEquals("GoodNotes 15", acc1.getObjectsOfDownloadedApps()[0].getName());
		
		assertEquals("GoodNotes 15", acc2.getObjectsOfDownloadedApps()[0].getName());
		assertEquals("Things 13 for iPad", acc2.getObjectsOfDownloadedApps()[1].getName());
		
		assertEquals("GoodNotes 15", acc3.getObjectsOfDownloadedApps()[0].getName());
		assertEquals("Things 13 for iPad", acc3.getObjectsOfDownloadedApps()[1].getName());
		assertEquals("Notability1", acc3.getObjectsOfDownloadedApps()[2].getName());
	}
	
	/*
	 * Recommended exercises: 
	 * 	Visualizing and tracing (on both debugger and paper) on how objects are created and manipulated
	 * 	in each test would be extremely valuable for reinforcing your understanding.
	 */
	@Test
	public void test_account_04() {
		/* Set up an app store with 3 available apps. */
		AppStore canadianStore = new AppStore("Canada", 100);
		
		App app1 = new App("GoodNotes x5", 15);
		app1.releaseUpdate("5.7.27");
		app1.releaseUpdate("5.7.29");
		app1.releaseUpdate("5.7.31");
		app1.getUpdateHistory()[0].addFix("Support for new M1 ipad Pro");
		app1.getUpdateHistory()[1].addFix("Improved scroll animations");
		app1.getUpdateHistory()[2].addFix("Better logging");
		
		App app2 = new App("Things x3 for iPad", 25); /* Note that the maximum number of allowed ratings is different from app1 */
		app2.releaseUpdate("3.15.1"); 
		app2.getUpdateHistory()[0].addFix("Improved data migration");
		
		App app3 = new App("Notability", 20); /* Note that the maximum number of allowed ratings is different from app1 */
		app3.releaseUpdate("10.4.5");
		app3.releaseUpdate("10.5.2");
		app3.getUpdateHistory()[0].addFix("Fixed a multi-window issue");
		app3.getUpdateHistory()[1].addFix("Added line spacing options for text");
		
		canadianStore.addApp(app1);
		canadianStore.addApp(app2);
		canadianStore.addApp(app3);
		
		/* Create three new accounts with names and a common registered store. */
		Account acc1 = new Account("Suyeon", canadianStore);
		Account acc2 = new Account("Yuna", canadianStore);
		Account acc3 = new Account("Heeyeon", canadianStore);
		
		acc1.download(app1.getName());
		acc2.download(app1.getName());
		acc2.download(app2.getName());		
		acc3.download(app1.getName());
		acc3.download(app2.getName());		
		acc3.download(app3.getName()); 
		
		/* So far, each account has downloaded different numbers of apps. */
		assertTrue(acc1.getObjectsOfDownloadedApps().length == 1);
		assertTrue(acc2.getObjectsOfDownloadedApps().length == 2);
		assertTrue(acc3.getObjectsOfDownloadedApps().length == 3);
		
		/* Important Hint:
		 * 	For names of each account's downloaded apps, 
		 * 		the returned app objects from method `getObjectsOfDownloadedApps` 
		 * 		should match those in the linked store.  
		 */
		assertSame(canadianStore.getApp("GoodNotes x5"), acc1.getObjectsOfDownloadedApps()[0]);
		
		assertSame(canadianStore.getApp("GoodNotes x5"), acc2.getObjectsOfDownloadedApps()[0]);
		assertSame(canadianStore.getApp("Things x3 for iPad"), acc2.getObjectsOfDownloadedApps()[1]);
		
		assertSame(canadianStore.getApp("GoodNotes x5"), acc3.getObjectsOfDownloadedApps()[0]);
		assertSame(canadianStore.getApp("Things x3 for iPad"), acc3.getObjectsOfDownloadedApps()[1]);
		assertSame(canadianStore.getApp("Notability"), acc3.getObjectsOfDownloadedApps()[2]);
	}
	
	/*
	 * Recommended exercises: 
	 * 	Visualizing and tracing (on both debugger and paper) on how objects are created and manipulated
	 * 	in each test would be extremely valuable for reinforcing your understanding.
	 */
	@Test
	public void test_account_05() {
		/* Set up an app store with 3 available apps. */
		AppStore canadianStore = new AppStore("Canada", 100);
		
		App app1 = new App("1GoodNotes 5", 15);
		app1.releaseUpdate("5.7.27");
		app1.releaseUpdate("5.7.29");
		app1.releaseUpdate("5.7.31");
		app1.getUpdateHistory()[0].addFix("Support for new M1 ipad Pro");
		app1.getUpdateHistory()[1].addFix("Improved scroll animations");
		app1.getUpdateHistory()[2].addFix("Better logging");
		
		App app2 = new App("2Things 3 for iPad", 25); /* Note that the maximum number of allowed ratings is different from app1 */
		app2.releaseUpdate("3.15.1"); 
		app2.getUpdateHistory()[0].addFix("Improved data migration");
		
		App app3 = new App("3Notability", 20); /* Note that the maximum number of allowed ratings is different from app1 */
		app3.releaseUpdate("10.4.5");
		app3.releaseUpdate("10.5.2");
		app3.getUpdateHistory()[0].addFix("Fixed a multi-window issue");
		app3.getUpdateHistory()[1].addFix("Added line spacing options for text");
		
		canadianStore.addApp(app1);
		canadianStore.addApp(app2);
		canadianStore.addApp(app3);
		
		/* Create three new accounts with names and a common registered store. */
		Account acc1 = new Account("1Suyeon", canadianStore);
		Account acc2 = new Account("2Yuna", canadianStore);
		Account acc3 = new Account("3Heeyeon", canadianStore);
		
		acc1.download(app1.getName());
		acc2.download(app1.getName());
		acc2.download(app2.getName());		
		acc3.download(app1.getName());
		acc3.download(app2.getName());		
		acc3.download(app3.getName());
		
		/* No ratings have been submitted so far for all apps. */
		assertEquals("No ratings submitted so far!", app1.getRatingReport());
		assertEquals("No ratings submitted so far!", app2.getRatingReport());
		assertEquals("No ratings submitted so far!", app3.getRatingReport());
		
		/* Attempt to submit the rating score for an app not already downloaded. */
		acc1.submitRating("2Things 3 for iPad", 4); 
		assertEquals("Error: 2Things 3 for iPad is not a downloaded app for 1Suyeon.", acc1.toString());
		acc1.submitRating("3Notability", 4); 
		assertEquals("Error: 3Notability is not a downloaded app for 1Suyeon.", acc1.toString());
		
		acc2.submitRating("3Notability", 4); 
		assertEquals("Error: 3Notability is not a downloaded app for 2Yuna.", acc2.toString());
		
		/* Submit the rating score for an app already downloaded.
		 * Hint: Submit the rating score to the corresponding app object existing in the app store. 
		 */
		acc1.submitRating("1GoodNotes 5", 5);
		assertEquals("Rating score 5 of 1Suyeon is successfully submitted for 1GoodNotes 5.", acc1.toString());
		acc2.submitRating("1GoodNotes 5", 1);
		assertEquals("Rating score 1 of 2Yuna is successfully submitted for 1GoodNotes 5.", acc2.toString());
		acc3.submitRating("1GoodNotes 5", 2);
		assertEquals("Rating score 2 of 3Heeyeon is successfully submitted for 1GoodNotes 5.", acc3.toString());
		
		/* Verify that the app existing in the app store has its rating scores updated. */
		assertEquals("Average of 3 ratings: 2.7 (Score 5: 1, Score 4: 0, Score 3: 0, Score 2: 1, Score 1: 1)", app1.getRatingReport());
	}
	
	/*
	 * Recommended exercises: 
	 * 	Visualizing and tracing (on both debugger and paper) on how objects are created and manipulated
	 * 	in each test would be extremely valuable for reinforcing your understanding.
	 */
	@Test
	public void test_account_06() {
		/* Set up an app store with 3 available apps. */
		AppStore canadianStore = new AppStore("Canada", 100);
		
		App app1 = new App("GoodNotes 5", 15);
		app1.releaseUpdate("5.7.27");
		app1.releaseUpdate("5.7.29");
		app1.releaseUpdate("5.7.31");
		app1.getUpdateHistory()[0].addFix("Support for new M1 ipad Pro");
		app1.getUpdateHistory()[1].addFix("Improved scroll animations");
		app1.getUpdateHistory()[2].addFix("Better logging");
		
		App app2 = new App("Things 3 for iPad", 25); /* Note that the maximum number of allowed ratings is different from app1 */
		app2.releaseUpdate("3.15.1"); 
		app2.getUpdateHistory()[0].addFix("Improved data migration");
		
		App app3 = new App("Notability", 20); /* Note that the maximum number of allowed ratings is different from app1 */
		app3.releaseUpdate("10.4.5");
		app3.releaseUpdate("10.5.2");
		app3.getUpdateHistory()[0].addFix("Fixed a multi-window issue");
		app3.getUpdateHistory()[1].addFix("Added line spacing options for text");
		
		canadianStore.addApp(app1);
		canadianStore.addApp(app2);
		canadianStore.addApp(app3);
		
		/* Create three new accounts with names and a common registered store. */
		Account acc1 = new Account("1Suyeon", canadianStore);
		Account acc2 = new Account("2Yuna", canadianStore);
		Account acc3 = new Account("3Heeyeon", canadianStore);
		
		acc1.download(app1.getName());
		acc2.download(app1.getName());
		acc2.download(app2.getName());		
		acc3.download(app1.getName());
		acc3.download(app2.getName());		
		acc3.download(app3.getName());
		
		/* Attempt to uninstall an app not already downloaded */
		acc1.uninstall("Things 3 for iPad");
		assertEquals("Error: Things 3 for iPad has not been downloaded for 1Suyeon.", acc1.toString());
		acc1.uninstall("Notability");
		assertEquals("Error: Notability has not been downloaded for 1Suyeon.", acc1.toString());
		acc2.uninstall("Notability");
		assertEquals("Error: Notability has not been downloaded for 2Yuna.", acc2.toString());
		
		/* Uninstall some apps already downloaded */
		acc1.uninstall("GoodNotes 5");
		assertEquals("GoodNotes 5 is successfully uninstalled for 1Suyeon.", acc1.toString());
		assertTrue(acc1.getNamesOfDownloadedApps().length == 0);
		assertTrue(acc1.getObjectsOfDownloadedApps().length == 0);
		
		acc2.uninstall("GoodNotes 5");
		assertEquals("GoodNotes 5 is successfully uninstalled for 2Yuna.", acc2.toString());
		assertTrue(acc2.getNamesOfDownloadedApps().length == 1);
		assertEquals("Things 3 for iPad", acc2.getNamesOfDownloadedApps()[0]);
		assertTrue(acc2.getObjectsOfDownloadedApps().length == 1);
		assertSame(canadianStore.getApp("Things 3 for iPad"), acc2.getObjectsOfDownloadedApps()[0]);
		
		acc3.uninstall("Things 3 for iPad");
		assertEquals("Things 3 for iPad is successfully uninstalled for 3Heeyeon.", acc3.toString());
		assertTrue(acc3.getNamesOfDownloadedApps().length == 2);
		assertEquals("GoodNotes 5", acc3.getNamesOfDownloadedApps()[0]);
		assertEquals("Notability", acc3.getNamesOfDownloadedApps()[1]);
		assertTrue(acc3.getObjectsOfDownloadedApps().length == 2);
		assertSame(canadianStore.getApp("GoodNotes 5"), acc3.getObjectsOfDownloadedApps()[0]);
		assertSame(canadianStore.getApp("Notability"), acc3.getObjectsOfDownloadedApps()[1]);
	}
}
