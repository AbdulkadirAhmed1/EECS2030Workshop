package junit_tests;
import static org.junit.Assert.assertEquals;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.junit.Test;
import org.junit.Rule;
import org.junit.rules.Timeout;

import model.HealthRecord;
import model.InsufficientVaccineDosesException;
import model.TooMuchDistributionException;
import model.UnrecognizedVaccineCodeNameException;
import model.VaccinationSite;
import model.Vaccine;
import model.VaccineDistribution;

public class GradingTestExt extends ShorterTests{
	
	@Rule
    public Timeout globalTimeout = Timeout.seconds(2);
	
	@Test
	public void test_vaccine_recognized_and_unrecognized() {
		// Comment: add 100 recognized and unrecognized vaccines and test their toString() output
		List<VaccineBean> expectedList = IntStream.range(0, 100)
				.mapToObj(i -> createVaccineBean(i, i % 2==0)).collect(Collectors.toList());
		List<Vaccine> actualList = expectedList.stream()
				.map(i -> createVaccine(i)).collect(Collectors.toList());
		compareList(expectedList, actualList, this::assertVaccine);
	}
	
	@Test
	public void test_vaccine_distribution() {
		// Comment: test toString() method for vaccine distribution 
		List<VaccineBean> baseVaccines = IntStream.range(0, 100)
				.mapToObj(i -> createVaccineBean(i, i % 2==0))
				.collect(Collectors.toList());
		
		List<VaccineDistBean> expectedList = IntStream.range(0, baseVaccines.size())
				.mapToObj(i -> new VaccineDistBean(baseVaccines.get(i), i*100))
				.collect(Collectors.toList());
		
		List<VaccineDistribution> actualList = expectedList.stream()
				.map(i -> new VaccineDistribution(createVaccine(i.vaccine), i.numOfDoses))
				.collect(Collectors.toList());
		compareList(expectedList, actualList, this::assertVaccineDistribution);
	}
	
	@Test
	public void test_health_record_multiple_init() {
		// Comment: initialize 100 HealthRecord and check healthRecordReceipt & appointment status
		List<HealthRecordBean> expectedList = IntStream.range(0, 100)
				.mapToObj(i -> new HealthRecordBean("name"+i, i))
				.collect(Collectors.toList());
		
		List<HealthRecord> actualList = IntStream.range(0, expectedList.size())
				.mapToObj(i -> new HealthRecord("name"+i, i))
				.collect(Collectors.toList());
		
		compareList(expectedList, actualList, (e, a) -> {
			assertHealthRecordReceipt(e, a);
			assertAppointmentStatus(e, a);
		});
	}
	
	@Test
	public void test_health_record_full_capacity() {
		// Comment: call addRecord multiple times until full capacity is reached.
		int limit = 3;
		HealthRecordBean hrb = new HealthRecordBean("Tom", limit);
		HealthRecord hr = new HealthRecord(hrb.name, hrb.limit);
		VaccineBean vaccineBean = createVaccineBean(1, true);
		Vaccine vaccine = createVaccine(vaccineBean);
		IntStream.range(0, limit).forEach(i -> {
			String site = "site"+i;
			String date = "Sep-0"+i+"-2021";
			hr.addRecord(vaccine, site, date);
			hrb.list.add(new HealthRecordUnitBean(vaccineBean, site, date));
		});
		
		assertHealthRecordReceipt(hrb, hr);
	}
	

	@Test
	public void test_health_record_exception_ioob() {
		// Comment: call addRecord multiple times and expect an ArrayIndexOutOfBoundsException
		int limit = 5;
		HealthRecordBean hrb = new HealthRecordBean("Tom", limit);
		HealthRecord hr = new HealthRecord(hrb.name, hrb.limit);
		VaccineBean vaccineBean = createVaccineBean(1, true);
		Vaccine vaccine = createVaccine(vaccineBean);
		IntStream.range(0, limit).forEach(i -> {
			String site = "site"+i;
			String date = "Sep-0"+i+"-2021";
			hr.addRecord(vaccine, site, date);
			hrb.list.add(new HealthRecordUnitBean(vaccineBean, site, date));
		});
		
		assertHealthRecordReceipt(hrb, hr);
		
		// add one more
		expectException(()-> {
			IntStream.range(limit, limit+1).forEach(i -> {
				String site = "site"+i;
				String date = "Sep-0"+i+"-2021";
				hr.addRecord(vaccine, site, date);
				hrb.list.add(new HealthRecordUnitBean(vaccineBean, site, date));
			});			
		}, ArrayIndexOutOfBoundsException.class);
	}
	
	@Test
	public void test_health_record_multiple_adds_sequential() {
		// Comment: call addRecord multiple times for multiple healthRecords sequentially and test the receipt output
		int healthRecordNum = 10;
		int perRecordNum = 5;
		
		List<HealthRecordBean> healthRecordBeans = IntStream.range(0, healthRecordNum)
				.mapToObj(i -> new HealthRecordBean("name"+i, 10)).collect(Collectors.toList());
		List<HealthRecord> healthRecords = IntStream.range(0, healthRecordNum)
				.mapToObj(i -> new HealthRecord("name"+i, 10)).collect(Collectors.toList());

		VaccineBean vaccineBean = createVaccineBean(1, true);
		Vaccine vaccine = createVaccine(vaccineBean);
		
		// add
		IntStream.range(0, healthRecordNum * perRecordNum).forEach(i -> {
			int idx = i / perRecordNum;
			HealthRecordBean hrb = healthRecordBeans.get(idx);
			HealthRecord hr = healthRecords.get(idx);
			String site = "site"+i;
			String date = "Oct-0"+i+"-2021";
			hr.addRecord(vaccine, site, date);
			hrb.list.add(new HealthRecordUnitBean(vaccineBean, site, date));
		});
		
		// assert
		compareList(healthRecordBeans, healthRecords, this::assertHealthRecordReceipt);
	}
	
	@Test
	public void test_health_record_multiple_adds_interleave() {
		// Comment: call addRecord multiple times for multiple healthRecords interleaved and test the receipt output

		int healthRecordNum = 10;
		int perRecordNum = 10;
		
		List<HealthRecordBean> healthRecordBeans = IntStream.range(0, healthRecordNum)
				.mapToObj(i -> new HealthRecordBean("name"+i, 10)).collect(Collectors.toList());
		List<HealthRecord> healthRecords = IntStream.range(0, healthRecordNum)
				.mapToObj(i -> new HealthRecord("name"+i, 10)).collect(Collectors.toList());

		VaccineBean vaccineBean = createVaccineBean(1, true);
		Vaccine vaccine = createVaccine(vaccineBean);
		
		// add
		IntStream.range(0, healthRecordNum * perRecordNum).forEach(i -> {
			int idx = i % perRecordNum;
			HealthRecordBean hrb = healthRecordBeans.get(idx);
			HealthRecord hr = healthRecords.get(idx);
			String site = "site"+i;
			String date = "Oct-0"+i+"-2021";
			hr.addRecord(vaccine, site, date);
			hrb.list.add(new HealthRecordUnitBean(vaccineBean, site, date));
		});
		
		// assert
		compareList(healthRecordBeans, healthRecords, this::assertHealthRecordReceipt);
	}
	
	@Test
	public void test_vaccination_site_add_distribution_multiple_sequential() {
		// Comment: add distribution to multiple vaccination sites sequentially. 
		int numOfSites = 5;
		int numOfVaccines = 4;
		List<VaccinationSiteBean> vsbs = IntStream.range(0, numOfSites)
				.mapToObj(i -> new VaccinationSiteBean("site"+i)).collect(Collectors.toList());
		List<VaccinationSite> vss = IntStream.range(0, numOfSites)
				.mapToObj(i -> new VaccinationSite("site"+i, 10)).collect(Collectors.toList());

		// 4 different vaccines.
		List<VaccineBean> vaccineBeans = IntStream.range(0, numOfVaccines)
				.mapToObj(i -> createVaccineBean(i, true)).collect(Collectors.toList());
		List<Vaccine> vaccines = vaccineBeans.stream()
				.map(i -> createVaccine(i)).collect(Collectors.toList());
		
		// add.
		IntStream.range(0, numOfSites * numOfVaccines).forEach(i -> {
			int idx = i / numOfVaccines;
			int vIdx = i % numOfVaccines;
			
			VaccinationSiteBean vsb = vsbs.get(idx);
			VaccinationSite vs = vss.get(idx);
			
			vsb.distributions.add(new VaccineDistBean(vaccineBeans.get(vIdx), 1));
			failOnException(() -> {
				vs.addDistribution(vaccines.get(vIdx), 1);
			});
		});
		
		// assert
		compareList(vsbs, vss, (vsb, vs) -> {
			assertEquals(vsb.toString(), vs.toString());
		});
	}

	@Test
	public void test_vaccination_site_add_distribution_multiple_interleave() {
		// Comment: add distribution to multiple vaccination sites interleaved
		int numOfSites = 5;
		int numOfVaccines = 4;
		List<VaccinationSiteBean> vsbs = IntStream.range(0, numOfSites)
				.mapToObj(i -> new VaccinationSiteBean("site"+i)).collect(Collectors.toList());
		List<VaccinationSite> vss = IntStream.range(0, numOfSites)
				.mapToObj(i -> new VaccinationSite("site"+i, 10)).collect(Collectors.toList());

		// 4 different vaccines.
		List<VaccineBean> vaccineBeans = IntStream.range(0, numOfVaccines)
				.mapToObj(i -> createVaccineBean(i, true)).collect(Collectors.toList());
		List<Vaccine> vaccines = vaccineBeans.stream()
				.map(i -> createVaccine(i)).collect(Collectors.toList());
		
		// add.
		IntStream.range(0, numOfSites * numOfVaccines).forEach(i -> {
			int idx = i % numOfSites;
			int vIdx = i / numOfSites;
			
			VaccinationSiteBean vsb = vsbs.get(idx);
			VaccinationSite vs = vss.get(idx);
			
			vsb.distributions.add(new VaccineDistBean(vaccineBeans.get(vIdx), 1));
			failOnException(() -> {
				vs.addDistribution(vaccines.get(vIdx), 1);
			});
		});
		
		// assert
		compareList(vsbs, vss, (vsb, vs) -> {
			assertEquals(vsb.toString(), vs.toString());
		});
	}
	
	@Test
	public void test_vaccination_site_exception_unrecognized_vaccine() {
		// Comment: add unrecognized vaccines to vaccination sites, expect an unrecognized vaccine expception.
		int numOfSites = 5;
		int numOfVaccines = 4;
		List<VaccinationSiteBean> vsbs = IntStream.range(0, numOfSites)
				.mapToObj(i -> new VaccinationSiteBean("site"+i)).collect(Collectors.toList());
		List<VaccinationSite> vss = IntStream.range(0, numOfSites)
				.mapToObj(i -> new VaccinationSite("site"+i, 10)).collect(Collectors.toList());

		// 4 different vaccines.
		List<VaccineBean> vaccineBeans = IntStream.range(0, numOfVaccines)
				.mapToObj(i -> createVaccineBean(i, true)).collect(Collectors.toList());
		List<Vaccine> vaccines = vaccineBeans.stream()
				.map(i -> createVaccine(i)).collect(Collectors.toList());
		
		// add.
		IntStream.range(0, numOfSites * numOfVaccines).forEach(i -> {
			int idx = i / numOfVaccines;
			int vIdx = i % numOfVaccines;
			
			VaccinationSiteBean vsb = vsbs.get(idx);
			VaccinationSite vs = vss.get(idx);
			
			vsb.distributions.add(new VaccineDistBean(vaccineBeans.get(vIdx), 1));
			failOnException(() -> {
				vs.addDistribution(vaccines.get(vIdx), 1);
			});
		});
		
		// add unrecognized vaccine
		Vaccine unrecognizedVaccine = createVaccine(createVaccineBean(100, false));
		vss.forEach(i -> {
			expectException(() -> {
				i.addDistribution(unrecognizedVaccine, 1);	
			}, UnrecognizedVaccineCodeNameException.class);
		});
		
		// assert
		compareList(vsbs, vss, (vsb, vs) -> {
			assertEquals(vsb.toString(), vs.toString());
		});
	}
	
	@Test
	public void test_vaccination_site_exception_too_much_distribution() {
		// Comment: add # of vaccines > the max # of doses for vaccination sites, expect a TooMuchDistributionException exception.
		int numOfSites = 5;
		int numOfVaccines = 4;
		List<VaccinationSiteBean> vsbs = IntStream.range(0, numOfSites)
				.mapToObj(i -> new VaccinationSiteBean("site"+i)).collect(Collectors.toList());
		List<VaccinationSite> vss = IntStream.range(0, numOfSites)
				.mapToObj(i -> new VaccinationSite("site"+i, 10)).collect(Collectors.toList());

		// 4 different vaccines.
		List<VaccineBean> vaccineBeans = IntStream.range(0, numOfVaccines)
				.mapToObj(i -> createVaccineBean(i, true)).collect(Collectors.toList());
		List<Vaccine> vaccines = vaccineBeans.stream()
				.map(i -> createVaccine(i)).collect(Collectors.toList());
		
		// add.
		IntStream.range(0, numOfSites * numOfVaccines).forEach(i -> {
			int idx = i / numOfVaccines;
			int vIdx = i % numOfVaccines;
			
			VaccinationSiteBean vsb = vsbs.get(idx);
			VaccinationSite vs = vss.get(idx);
			
			vsb.distributions.add(new VaccineDistBean(vaccineBeans.get(vIdx), 1));
			failOnException(() -> {
				vs.addDistribution(vaccines.get(vIdx), 1);
			});
		});
		
		// add unrecognized vaccine
		Vaccine unrecognizedVaccine = createVaccine(createVaccineBean(100, true));
		vss.forEach(i -> {
			expectException(() -> {
				i.addDistribution(unrecognizedVaccine, 10);	
			}, TooMuchDistributionException.class);
		});
		
		// assert
		compareList(vsbs, vss, (vsb, vs) -> {
			assertEquals(vsb.toString(), vs.toString());
		});
	}
	
	@Test
	public void test_vaccination_site_exception_unrecognized_too_much_distribution() {
		// Comment: test the order of unrecognized vaccine exception and too much distribution exception.
		int numOfSites = 5;
		int numOfVaccines = 4;
		List<VaccinationSiteBean> vsbs = IntStream.range(0, numOfSites)
				.mapToObj(i -> new VaccinationSiteBean("site"+i)).collect(Collectors.toList());
		List<VaccinationSite> vss = IntStream.range(0, numOfSites)
				.mapToObj(i -> new VaccinationSite("site"+i, 10)).collect(Collectors.toList());

		// 4 different vaccines.
		List<VaccineBean> vaccineBeans = IntStream.range(0, numOfVaccines)
				.mapToObj(i -> createVaccineBean(i, true)).collect(Collectors.toList());
		List<Vaccine> vaccines = vaccineBeans.stream()
				.map(i -> createVaccine(i)).collect(Collectors.toList());
		
		// add.
		IntStream.range(0, numOfSites * numOfVaccines).forEach(i -> {
			int idx = i / numOfVaccines;
			int vIdx = i % numOfVaccines;
			
			VaccinationSiteBean vsb = vsbs.get(idx);
			VaccinationSite vs = vss.get(idx);
			
			vsb.distributions.add(new VaccineDistBean(vaccineBeans.get(vIdx), 1));
			failOnException(() -> {
				vs.addDistribution(vaccines.get(vIdx), 1);
			});
		});
		
		// add unrecognized vaccine
		Vaccine unrecognizedVaccine = createVaccine(createVaccineBean(100, false));
		vss.forEach(i -> {
			expectException(() -> {
				i.addDistribution(unrecognizedVaccine, 10);	
			}, UnrecognizedVaccineCodeNameException.class);
		});
		
		// assert
		compareList(vsbs, vss, (vsb, vs) -> {
			assertEquals(vsb.toString(), vs.toString());
		});
		
	}
	
	
	@Test
	public void test_vaccination_site_book_appointment_sequential() {
		// Comment: test book appointments on different vaccination sites sequentially.  
		int numOfSites = 5;
		int numOfVaccines = 4;
		List<VaccinationSiteBean> vsbs = IntStream.range(0, numOfSites)
				.mapToObj(i -> new VaccinationSiteBean("site"+i)).collect(Collectors.toList());
		List<VaccinationSite> vss = IntStream.range(0, numOfSites)
				.mapToObj(i -> new VaccinationSite("site"+i, 10)).collect(Collectors.toList());
		

		// 4 different vaccines.
		List<VaccineBean> vaccineBeans = IntStream.range(0, numOfVaccines)
				.mapToObj(i -> createVaccineBean(i, true)).collect(Collectors.toList());
		List<Vaccine> vaccines = vaccineBeans.stream()
				.map(i -> createVaccine(i)).collect(Collectors.toList());

		// add.
		IntStream.range(0, numOfSites * numOfVaccines).forEach(i -> {
			int idx = i / numOfVaccines;
			int vIdx = i % numOfVaccines;
			
			VaccinationSiteBean vsb = vsbs.get(idx);
			VaccinationSite vs = vss.get(idx);
			
			vsb.distributions.add(new VaccineDistBean(vaccineBeans.get(vIdx), 1));
			failOnException(() -> {
				vs.addDistribution(vaccines.get(vIdx), 1);
			});
		});
		
		List<HealthRecord> actual = IntStream.range(0, 10)
				.mapToObj(i -> new HealthRecord("Person"+i, 10)).collect(Collectors.toList()); 
		
		// book.
		IntStream.range(0, 10).forEach(i -> {
			int idx = i / numOfVaccines;
			failOnException(() -> vss.get(idx).bookAppointment(actual.get(i / 2)));
		});
		
		// assert
		IntStream.range(0, 10).forEach(i -> {
			if (i < 5) {
				String expected = String.format("Last vaccination appointment for Person%s with site%s succeeded", i, i*2/numOfVaccines);
				assertEquals(expected, actual.get(i).getAppointmentStatus());
			} else {
				assertEquals(String.format("No vaccination appointment for Person%s yet", i), actual.get(i).getAppointmentStatus());
			}
		});
	}
	
	@Test
	public void test_vaccination_site_book_appointment_interleave() {
		// Comment: test book appointments on different vaccination sites interleaved.
		int numOfSites = 5;
		int numOfVaccines = 4;
		List<VaccinationSiteBean> vsbs = IntStream.range(0, numOfSites)
				.mapToObj(i -> new VaccinationSiteBean("site"+i)).collect(Collectors.toList());
		List<VaccinationSite> vss = IntStream.range(0, numOfSites)
				.mapToObj(i -> new VaccinationSite("site"+i, 10)).collect(Collectors.toList());
		

		// 4 different vaccines.
		List<VaccineBean> vaccineBeans = IntStream.range(0, numOfVaccines)
				.mapToObj(i -> createVaccineBean(i, true)).collect(Collectors.toList());
		List<Vaccine> vaccines = vaccineBeans.stream()
				.map(i -> createVaccine(i)).collect(Collectors.toList());

		// add.
		IntStream.range(0, numOfSites * numOfVaccines).forEach(i -> {
			int idx = i / numOfVaccines;
			int vIdx = i % numOfVaccines;
			
			VaccinationSiteBean vsb = vsbs.get(idx);
			VaccinationSite vs = vss.get(idx);
			
			vsb.distributions.add(new VaccineDistBean(vaccineBeans.get(vIdx), 1));
			failOnException(() -> {
				vs.addDistribution(vaccines.get(vIdx), 1);
			});
		});
		
		List<HealthRecord> actual = IntStream.range(0, 10)
				.mapToObj(i -> new HealthRecord("Person"+i, 10)).collect(Collectors.toList()); 
		
		// book.
		IntStream.range(0, 10).forEach(i -> {
			int idx = i % numOfVaccines;
			failOnException(() -> vss.get(idx).bookAppointment(actual.get(i)));
		});
		
		// assert
		IntStream.range(0, 10).forEach(i -> {
			String expected = String.format("Last vaccination appointment for Person%s with site%s succeeded", i, i%numOfVaccines);
			assertEquals(expected, actual.get(i).getAppointmentStatus());
		});
	}
	
	@Test
	public void test_vaccination_site_book_appointment_exception_insufficient() {
		// Comment: test book appointments multiple times and expect an InsufficientVaccineDosesException. 
		int numOfSites = 5;
		int numOfVaccines = 4;
		List<VaccinationSiteBean> vsbs = IntStream.range(0, numOfSites)
				.mapToObj(i -> new VaccinationSiteBean("site"+i)).collect(Collectors.toList());
		List<VaccinationSite> vss = IntStream.range(0, numOfSites)
				.mapToObj(i -> new VaccinationSite("site"+i, 10)).collect(Collectors.toList());
		

		// 4 different vaccines.
		List<VaccineBean> vaccineBeans = IntStream.range(0, numOfVaccines)
				.mapToObj(i -> createVaccineBean(i, true)).collect(Collectors.toList());
		List<Vaccine> vaccines = vaccineBeans.stream()
				.map(i -> createVaccine(i)).collect(Collectors.toList());

		// add.
		IntStream.range(0, numOfSites * numOfVaccines).forEach(i -> {
			int idx = i / numOfVaccines;
			int vIdx = i % numOfVaccines;
			
			VaccinationSiteBean vsb = vsbs.get(idx);
			VaccinationSite vs = vss.get(idx);
			
			vsb.distributions.add(new VaccineDistBean(vaccineBeans.get(vIdx), 1));
			failOnException(() -> {
				vs.addDistribution(vaccines.get(vIdx), 1);
			});
		});
		
		List<HealthRecord> actual = IntStream.range(0, 10)
				.mapToObj(i -> new HealthRecord("Person"+i, 10)).collect(Collectors.toList()); 
		
		// book.
		IntStream.range(0, 30).forEach(i -> {
			int idx = i % numOfSites;
			int hrIdx = i % 10;
			if (i < 20) {
				failOnException(() -> vss.get(idx).bookAppointment(actual.get(hrIdx)));
				String expected = String.format("Last vaccination appointment for Person%s with site%s succeeded", hrIdx, 
						idx);
				assertEquals(expected, actual.get(hrIdx).getAppointmentStatus());
			} else {
				expectException(() -> vss.get(idx).bookAppointment(actual.get(hrIdx)), InsufficientVaccineDosesException.class);
				String expected = String.format("Last vaccination appointment for Person%s with site%s failed", hrIdx, 
						idx);
				assertEquals(expected, actual.get(hrIdx).getAppointmentStatus());
			}
		});
		
	}
	
}
