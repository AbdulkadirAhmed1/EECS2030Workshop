package junit_tests;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import model.*;

public class ShorterTests {
	
	// constants
	static List<String> recognizedCodes = Arrays.asList(
		"mRNA-1273", "BNT162b2", "Ad26.COV2.S", "AZD1222"
	);
	
	static List<String> vaccineTypes = Arrays.asList("RNA", 
			"Non Replicating Viral Vector", "Inactivated");
	
	static class VaccineBean{
		String codename;
		String manufacturer;
		String type;
		String status;
		boolean recognized;
		
		public String toString() {
			if (recognized) {
				return String.format("Recognized vaccine: %s (%s; %s)", codename, type, manufacturer);
			} else {
				return String.format("Unrecognized vaccine: %s (%s; %s)", codename, type, manufacturer);
			}
		}
	}
	
	static class VaccineDistBean{
		VaccineBean vaccine;
		int numOfDoses;
		public VaccineDistBean(VaccineBean vb, int limit) {
			this.vaccine = vb;
			this.numOfDoses = limit;
		}
		public String toString() {
			return String.format("%d doses of %s by %s", numOfDoses, vaccine.codename, this.vaccine.manufacturer);
		}
	}
	
	static class HealthRecordUnitBean{
		VaccineBean vaccine;
		String date;
		String site;
		public HealthRecordUnitBean(VaccineBean vaccine, String site, String date) {
			super();
			this.vaccine = vaccine;
			this.date = date;
			this.site = site;
		}
	}
	
	static class HealthRecordBean{
		String name;
		List<HealthRecordUnitBean> list = new ArrayList<>();
		String appointmentStatus;
		int limit;
		
		public HealthRecordBean(String name, int limit) {
			this.name = name;
			this.limit = limit;
			this.appointmentStatus = String.format("No vaccination appointment for %s yet", name);
		}
		
		public String getVaccineReceipt() {
			if (list.size() == 0) {
				return String.format("%s has not yet received any doses.", name);
			} else {
				String begin = String.format("Number of doses %s has received: %d [", name, list.size());
				String end = "]";
				return begin + String.join("; ", list.stream().map(i ->
					String.format("%s in %s on %s", i.vaccine, i.site, i.date)
				).collect(Collectors.toList())) + end;
			}
		}
		
	}
	
	static class VaccinationSiteBean {
		String name;
		List<VaccineDistBean> distributions = new ArrayList<>();
		public VaccinationSiteBean(String name) {
			super();
			this.name = name;
		}
		
		public int getNumberOfAvailableDoses() {
			return distributions.stream().mapToInt(i -> i.numOfDoses).sum();
		}
		
		public String toString(){
			String res = String.format("%s has %d available doses: <%s>", 
					this.name, this.getNumberOfAvailableDoses(), 
					String.join(", ", distributions.stream()
						.map(i -> String.format("%s doses of %s", i.numOfDoses, i.vaccine.manufacturer))
						.collect(Collectors.toList())));
			return res;
		}
				
	}
	
	static Vaccine createVaccine(VaccineBean bean) {
		return new Vaccine(bean.codename, bean.type, bean.manufacturer);
	}
	
	static VaccineBean createVaccineBean(int i, boolean recognized) {
		VaccineBean bean = new VaccineBean();
		if (recognized) {
			bean.codename = pickOne(recognizedCodes, i);
		} else {
			bean.codename = "code name:" + i;
		}
		bean.recognized = recognized;
		bean.manufacturer = "manufacturer:" +i;
		bean.type = pickOne(vaccineTypes, i);
		return bean;
	}
	
	static <T> T pickOne(List<T> list, int i){
		return list.get(i % list.size());
	}
	
	void assertVaccine(VaccineBean expected, Vaccine vaccine){
		assertEquals(expected.toString(), vaccine.toString());
	}
	
	void assertHealthRecordReceipt(HealthRecordBean expected, HealthRecord actual) {
		assertEquals(expected.getVaccineReceipt(), actual.getVaccinationReceipt());
	}
	
	void assertAppointmentStatus(HealthRecordBean expected, HealthRecord actual) {
		assertEquals(expected.appointmentStatus, actual.getAppointmentStatus());
	}
	
	void assertVaccineDistribution(VaccineDistBean expected, VaccineDistribution actual) {
		assertEquals(expected.toString(), actual.toString());
	}
	
	static <T, F> void compareList(List<T> expected, List<F> actual, BiConsumer<T, F> consumer) {
		assertEquals(expected.size(), actual.size());
		IntStream.range(0, expected.size()).forEach(i -> consumer.accept(expected.get(i), actual.get(i)));
	}

	static <T> void expectException(RunnableWithException runner, Class<? extends Exception> expClass) {
		expectException(runner, expClass, () -> {});
	}
	
	static <T> void expectException(RunnableWithException runner, Class<? extends Exception> expClass, Runnable runnerAfterException) {
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
	
	static void failOnException(RunnableWithException runnable) {
		try {
			runnable.run();
		} catch (Exception e) {
			fail("unexpected exception: "+e.toString());
		}
	}
	
	static interface RunnableWithException{
		public void run() throws Exception;
	}
}
