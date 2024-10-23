package junit_tests;


import static org.junit.Assert.fail;

import java.util.Arrays;
import java.util.List;

import model.Account;
import model.App;
import model.AppStore;

public class ShorterTests {
	
	static List<Account> createAccounts(AppStore store, String...accNames){
		return Arrays.stream(accNames).map(i -> new Account(i, store)).toList();
	}
	
	static List<App> createApps(int maxRatings, String...appNames) {
		return Arrays.stream(appNames).map(x -> new App(x, maxRatings)).toList();
	}
	
	static void releaseUpdates(App app, String... updates) {
		for (String update: updates) {
			app.releaseUpdate(update);
		}
	}
	
	static void addFix(App app, int updateIdx, String fixStr) {
		app.getUpdateHistory()[updateIdx].addFix(fixStr);
	}
	
	static void addAppsToStore(AppStore store, App... apps) {
		for (App app : apps) {
			store.addApp(app);
		}
	}
	
	static <T> void expectException(Runnable runner, Class<? extends Exception> expClass) {
		try {
			runner.run();
			fail(String.format("Expected Exception %s did not occur", expClass.getName()));
		} catch (Exception e) {
			if (!expClass.isInstance(e)) {
				// also fail
				fail(String.format("Expecting Exception [%s], got [%s]", 
						expClass.getName(), e.getClass().getName()));
			}	
		}
	}
}
