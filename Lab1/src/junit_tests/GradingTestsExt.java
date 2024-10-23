package junit_tests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

import org.junit.Test;

import model.Account;
import model.App;
import model.AppStore;
import model.Log;

public class GradingTestsExt extends ShorterTests {
	
	// ========== test log ===========
	@Test
	public void test_log_exception_ioob() {
		/* Comment: When adding the 11th fix, expecting an ArrayIndexOutOfBoundsException */
		String versionStr = "1.2.3"; 
		Log log = new Log(versionStr);
		// prepare 
		IntStream.range(0, 10).forEach(i -> log.addFix("Fix #"+i));
		expectException(() -> {
			log.addFix("OutOfBounds Fix");
		}, IndexOutOfBoundsException.class);
	}
	
	@Test 
	public void test_log_full_capacity() {
		/* Comment: Add 10 fixes, and test their orders*/
		String versionStr = "4.5.6";
		Log log = new Log(versionStr);
		
		List<String> fixes = new ArrayList<>();
		IntStream.range(0, 10).forEach(i -> {
			String fixStr = "Fix #" + i;
			log.addFix(fixStr);
			fixes.add(fixStr);
		});
		String expectedStr = '[' + String.join(", ", fixes) + ']';
		
		assertEquals(10, log.getNumberOfFixes());
		assertEquals(expectedStr, log.getFixes());
		assertEquals("Version 4.5.6 contains 10 fixes "+ expectedStr, log.toString());
	}

	@Test 
	public void test_log_empty_fix() {
		/* Comment: Test adding one empty fix*/
		Log log = new Log("10.20.30");
		log.addFix("");
		assertEquals(1, log.getNumberOfFixes());
		assertEquals("[]", log.getFixes());
	}
	
	
	// ========= test app ==============
	
	@Test
	public void test_app_log_exception_ioob() {
		/* Comment: When adding the 21st log to an app, an ArrayIndexOutOfBoundsExceptoin is expected. */
		
		App app = new App("App 1", 15);
		// the max capacity is 20
		IntStream.range(0, 20).forEach(i -> app.releaseUpdate("Log " + i));
		expectException(() -> app.releaseUpdate("Log " + 20), 
				IndexOutOfBoundsException.class);
		assertEquals(20, app.getUpdateHistory().length);
	}
	

	@Test
	public void test_app_log_full_capacity() {
		/* Comment: Add 10 updates to an app, and test their orders*/
		App app = new App("app1", 10);
		List<String> expectedLogContents = new ArrayList<>(); 
		for(int i = 0; i < 20; i ++) {
			String str = "log."+i;
			app.releaseUpdate(str);
			expectedLogContents.add(str);
		}
		assertEquals(20, app.getUpdateHistory().length);
		
		for (int i =0; i < 20; i++) {
			String expectedStr = String.format("Version %s contains 0 fixes []", 
					expectedLogContents.get(i)); 
			assertEquals(expectedStr, app.getUpdateHistory()[i].toString());
		}
	}
	
	@Test
	public void test_app_update_two_apps() {
		/* Comment: Create two apps and update them, check their update orders*/
		App app1 = new App("app1", 10);
		App app2 = new App("app2", 10);
		
		List<String> updatesForApp1 = Arrays.asList("1.1.1", "2.2.1", "3.3.1", "4.4.1");
		List<String> updatesForApp2 = Arrays.asList("1.1.2", "2.2.2", "3.3.2", "4.4.2");
		
		releaseUpdates(app1, updatesForApp1.toArray(new String[0]));
		releaseUpdates(app2, updatesForApp2.toArray(new String[0]));
		
		for(int i =0; i < updatesForApp1.size(); i++) {
			String expectedStr = String.format("Version %s contains 0 fixes []", 
					updatesForApp1.get(i));
			assertEquals(expectedStr, app1.getUpdateHistory()[i].toString());
		}
		
		for(int i =0; i < updatesForApp2.size(); i++) {
			String expectedStr = String.format("Version %s contains 0 fixes []", 
					updatesForApp2.get(i));
			assertEquals(expectedStr, app2.getUpdateHistory()[i].toString());
		}
	}
	
	@Test
	public void test_app_rating_5() {
		/* Comment: add ratings 1-5 and test the output of rating report */
		App app1 = new App("App", 5);
	
		for (int i=0;  i < 5; i++) {
			int score = i+1;
			app1.submitRating(score);
		}
		String expected = "Average of 5 ratings: 3.0 (Score 5: 1, Score 4: 1, Score 3: 1, Score 2: 1, Score 1: 1)";
		assertEquals(expected, app1.getRatingReport());
	}
	
	@Test
	public void test_app_rating_ioob() {
		/* Comment: add 6th rating to an app with max rating capacity of 5 */
		App app1 = new App("App", 5);
	
		for (int i=0;  i < 5; i++) {
			int score = i+1;
			app1.submitRating(score);
		}
		app1.submitRating(5);
		
		String expected = "Average of 5 ratings: 3.0 (Score 5: 1, Score 4: 1, Score 3: 1, Score 2: 1, Score 1: 1)";
		assertEquals(expected, app1.getRatingReport());
	}

	@Test
	public void test_app_rating_value_oor() {
		/* Comment: test adding rating score > 5 */
		App app1 = new App("App", 5);
	
		for (int i=0;  i < 5; i++) {
			int score = i+2;
			app1.submitRating(score);
		}
		String expected = "Average of 5 ratings: 4.0 (Score 5: 2, Score 4: 1, Score 3: 1, Score 2: 1, Score 1: 0)";
		assertEquals(expected, app1.getRatingReport());
	}
	
	// ============ test app store =============
	
	@Test
	public void test_app_store_exception_ioob() {
		/* Comment: test adding more apps than the maxNumberOfApps, expecting an ArrayIndexOutOfBoundsException */
		AppStore store = new AppStore("US", 1);
		store.addApp(new App("app0", 1));
		expectException(() -> {
			store.addApp(new App("app1", 1));
		}, ArrayIndexOutOfBoundsException.class);
	}
	
	@Test
	public void test_app_store_full_capacity() {
		/* Comment: add # of apps equals to maxNumberOfApps */
		AppStore store = new AppStore("US", 10);
		IntStream.range(0, 10).forEach(i -> store.addApp(new App("app"+i, 1)));

		IntStream.range(0, 10).forEach(i -> assertEquals("app"+i, store.getApp("app"+i).getName()));
	}
	
	@Test
	public void test_app_store_multiple_stores_sequential() {
		/* Comment: test adding apps to two app stores sequentially */
		AppStore store1 = new AppStore("Canada", 10);
		AppStore store2 = new AppStore("US", 10);
		
		List<String> store1Apps = Arrays.asList("app1", "app3", "app5");
		List<String> store2Apps = Arrays.asList("app2", "app4", "app5");
		
		addAppsToStore(store1, 
				store1Apps.stream().map(x -> new App(x, 10)).toArray(App[]::new));
		addAppsToStore(store2, 
				store2Apps.stream().map(x -> new App(x, 10)).toArray(App[]::new));
		
		for (String appName: store1Apps) {
			assertEquals(appName, store1.getApp(appName).getName());
		}
		for (String appName: store2Apps) {
			assertEquals(appName, store2.getApp(appName).getName());
		}
	}
	
	@Test
	public void test_app_store_multiple_stores_interleave() {
		/* Comment: test adding apps to two app stores interleavely */
		AppStore store1 = new AppStore("Canada", 10);
		AppStore store2 = new AppStore("US", 10);
		// 20 apps
		IntStream.range(0, 20).forEach(i -> {
			AppStore store = i % 2 == 0? store1: store2;
			store.addApp(new App("app"+i, 10)); 
		});
		
		// check values
		IntStream.range(0, 20).forEach(i -> {
			AppStore store = i % 2 == 0? store1: store2;
			String appName = "app"+i;
			assertEquals(appName, store.getApp(appName).getName()); 
		});
	}
	
	@Test
	public void test_app_store_stable_apps() {
		/* Comment: add some apps with different # of updates to the store, checking the result of getStableApps */
		AppStore store1 = new AppStore("Canada", 10);
		
		for (int i =0; i < 10; i++) {
			App app = new App("app"+i, 10);
			for (int j = 0; j < i; j++) {
				app.releaseUpdate(i+"."+j+".0");
			}
			store1.addApp(app);
		}
		
		IntStream.range(0, 10).forEach(i -> 
			assertEquals( 10-i, store1.getStableApps(i).length));
	}
	
	
	@Test
	public void test_app_store_duplicate_names() {
		/* Comment: add two apps with the same name to a store */
		AppStore store1 = new AppStore("US", 10);
		App app1 = new App("app1", 1);
		App app2 = new App("app2", 1);
		store1.addApp(app1);
		store1.addApp(app2);
		
		assertEquals(2, store1.getStableApps(0).length);
		
		app1.releaseUpdate("1.1.1");
		app2.releaseUpdate("2.2.2");
		// should be the first one.
		assertEquals("1.1.1", store1.getApp("app1").getUpdateHistory()[0].getVersion());
	}
	
	// ============== accounts
	
	List<AppStore> setupStores() {
		// 1.
		AppStore store1 = new AppStore("Canada", 10);
		// app0~app9
		List<App> appsForStore1 = createApps(10, 
				IntStream.range(0, 10).mapToObj(x -> "app-"+x).toArray(String[]::new));
		
		addAppsToStore(store1, appsForStore1.toArray(App[]::new));
		
		// 2.
		AppStore store2 = new AppStore("US", 10);
		// app5~app14
		List<App> appsForStore2 = createApps(5, 
				IntStream.range(5, 15).mapToObj(x -> "app-"+x).toArray(String[]::new));
		addAppsToStore(store2, appsForStore2.toArray(App[]::new));
		
		// 3. 
		AppStore store3 = new AppStore("UK", 150);
		// app20~app24
		List<App> appsForStore3 = createApps(5, 
				IntStream.range(100, 200).mapToObj(x -> "app-"+x).toArray(String[]::new));
		addAppsToStore(store3, appsForStore3.toArray(App[]::new));
		
		return Arrays.asList(store1, store2, store3);
	}
	
	@Test
	public void test_account_normal_procedure() {
		/* Comment: download and uninstall apps in one account */
		List<AppStore> appStores = setupStores();
		// we only use the first one in this case.
		AppStore store = appStores.get(0);
		
		Account account = new Account("account1", store);
		assertEquals("An account linked to the Canada store is created for account1.", account.toString());
		account.download("app-0");
		assertEquals("app-0 is successfully downloaded for account1.", account.toString());
		assertEquals(1,  account.getNamesOfDownloadedApps().length);
		account.download("app-0");
		assertEquals("Error: app-0 has already been downloaded for account1.", account.toString());
		
		account.uninstall("app-1");
		assertEquals("Error: app-1 has not been downloaded for account1.", account.toString());
		account.uninstall("app-0");
		assertEquals("app-0 is successfully uninstalled for account1.", account.toString());
		assertEquals(0,  account.getNamesOfDownloadedApps().length);
	}
	
	@Test
	public void test_account_switch_with_same_name_app() {
		/* Comment: download the same app before and after switching to a different store*/
		List<AppStore> appStores = setupStores();
		// we only use the first one in this case.
		AppStore store1 = appStores.get(0), store2 = appStores.get(1);
		
		Account account = new Account("account1", store1);
		
		account.download("app-9");
		assertEquals("app-9 is successfully downloaded for account1.", account.toString());
		
		account.switchStore(store2);
		account.download("app-9");
		assertEquals("Error: app-9 has already been downloaded for account1.", account.toString());
	}

	@Test
	public void test_account_download_max_capacity() {
		/* Comment: download # of apps equal to 50 */
		List<AppStore> appStores = setupStores();
		AppStore store = appStores.get(2);
		
		Account account = new Account("acc1", store);
		
		IntStream.range(0, 50).forEach(i -> account.download("app-"+(i+100)));
		assertEquals(50, account.getNamesOfDownloadedApps().length);
	}
	
	@Test
	public void test_account_download_exception_ioob() {
		/* Comment: download 51 apps, and expect an ArrayIndexOutofBoundsException */
		List<AppStore> appStores = setupStores();
		AppStore store = appStores.get(2);
		
		Account account = new Account("acc1", store);
		
		for(int i =0; i < 50; i++) {
			account.download("app-"+(i+100));
		}
		expectException(()-> account.download("app-199"), ArrayIndexOutOfBoundsException.class);
	}
	
	@Test
	public void test_account_download_uninstall_multiple_times() {
		/* Comment: download and uninstall apps multiple times in one account */
		List<AppStore> appStores = setupStores();
		AppStore store = appStores.get(2);
		
		Account account = new Account("acc1", store);
		
		for(int i =0; i < 200; i++) {
			String appName = "app-"+(i%2+100);
			account.download(appName);
			account.uninstall(appName);
		}
		
		assertEquals(0, account.getNamesOfDownloadedApps().length);
		account.download("app-200");
		assertEquals(1, account.getNamesOfDownloadedApps().length);
	}
	
	@Test
	public void test_account_multiple() {
		/* Comment: download and uninstall apps multiple times in 3 different accounts */
		List<AppStore> appStores = setupStores();
		AppStore store = appStores.get(2);
		
		List<Account> accounts = createAccounts(store, "account1", "account2", "account3");
		
		// download [100-120), [100-130), [110,150)
		IntStream.range(100, 120).forEach(i -> accounts.get(0).download("app-"+i));
		IntStream.range(100, 130).forEach(i -> accounts.get(1).download("app-"+i));
		IntStream.range(110, 150).forEach(i -> accounts.get(2).download("app-"+i));
		
		// uninstall [100-115), [120,130), [120, 160)
		IntStream.range(100, 115).forEach(i -> accounts.get(0).uninstall("app-"+i));
		IntStream.range(120, 130).forEach(i -> accounts.get(1).uninstall("app-"+i));
		IntStream.range(120, 160).forEach(i -> accounts.get(2).uninstall("app-"+i));
		
		// check remaining objects.
		assertArrayEquals(IntStream.range(115, 120).mapToObj(i -> "app-"+i).toArray(String[]::new),
				accounts.get(0).getNamesOfDownloadedApps());

		assertArrayEquals(IntStream.range(100, 120).mapToObj(i -> "app-"+i).toArray(String[]::new),
				accounts.get(1).getNamesOfDownloadedApps());
		assertArrayEquals(IntStream.range(110, 120).mapToObj(i -> "app-"+i).toArray(String[]::new),
				accounts.get(2).getNamesOfDownloadedApps());
	}
	
	@Test
	public void test_account_multiple_interleave() {
		/* Comment: download and uninstall apps multiple times in 3 accounts interleavely */
		List<AppStore> appStores = setupStores();
		AppStore store = appStores.get(2);
		
		
		List<Account> accounts = createAccounts(store, "account1", "account2", "account3");
		
		// 6 operations: 
		// 0 -> account1.download
		// 1 -> account1.uninstall
		// 2 -> account2.download
		// 3 -> account2.uninstall
		// 4 -> account3.download
		// 5 -> account4.uninstall
		
		IntStream.range(0, 60).forEach(i -> {
			int op = i % 6;
			int app = i/6+100;
			int accountIdx = op /2;
			if (op % 2 == 0) {
				accounts.get(accountIdx).download("app"+app);
			} else {
				accounts.get(accountIdx).uninstall("app"+app);
			}
		});
		IntStream.range(0, 3).forEach(i -> assertArrayEquals(new String[0], 
				accounts.get(i).getNamesOfDownloadedApps()));
	}
	
	@Test
	public void test_account_rating_multiple_times() {
		/* Comment: rate an app multiple times after download */
		List<AppStore> appStores = setupStores();
		AppStore store = appStores.get(2);
		
		Account account = new Account("acc1", store);
		App theApp = store.getApp("app-100");
		
		// the rating does not count.
		account.submitRating("app-100", 1);
		assertEquals("No ratings submitted so far!", theApp.getRatingReport());
		
		account.download("app-100");
		account.submitRating("app-100", 5);
		
		assertEquals("Average of 1 ratings: 5.0 (Score 5: 1, Score 4: 0, Score 3: 0, Score 2: 0, Score 1: 0)", 
				theApp.getRatingReport());

		account.submitRating("app-100", 5);

		assertEquals("Average of 2 ratings: 5.0 (Score 5: 2, Score 4: 0, Score 3: 0, Score 2: 0, Score 1: 0)", 
				theApp.getRatingReport());
	}
	
	@Test
	public void test_account_rating_after_deletion() {
		/* Comment: rate an app after deleting it */

		List<AppStore> appStores = setupStores();
		AppStore store = appStores.get(2);
		Account account = new Account("acc1", store);
		App theApp = store.getApp("app-100");
		
		account.download("app-100");
		
		account.submitRating("app-100", 4);
		assertEquals("Average of 1 ratings: 4.0 (Score 5: 0, Score 4: 1, Score 3: 0, Score 2: 0, Score 1: 0)", 
				theApp.getRatingReport());
		
		account.uninstall("app-100");
		// does not count.
		account.submitRating("app-100", 2);

		assertEquals("Average of 1 ratings: 4.0 (Score 5: 0, Score 4: 1, Score 3: 0, Score 2: 0, Score 1: 0)", 
				theApp.getRatingReport());
	}
}
