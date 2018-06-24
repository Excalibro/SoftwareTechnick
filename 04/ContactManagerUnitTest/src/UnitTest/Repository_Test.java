package UnitTest;
import ContactManager.*;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import ContactManager.Company;
import ContactManager.Person;

class Repository_Test {

	// Make Data

	
	private List<Company> _testCompanies;
	private List<Person> _testPersons;
	private Repository _repo;
	
	private Person _person_a;
	private Person _person_b;
	private Person _person_c;
	private Person _person_d;
	
	private Company _company_a;
	private Company _company_b;
	private Company _company_c;
	private Company _company_d;
	
	
	private void Initialize_EmptyTestData()
	{
		// Clear Old Data:
		_repo = new Repository();
		_testCompanies  = new ArrayList<>();
		_testPersons = new ArrayList<>();
		
		// Create New Data:
		_person_a = new Person("Adrian" , "Ziegler", "Samplesquare16", "0195238", "sample@email.com");
		_person_b = new Person("Bertram" , "Yacomelly", "Samplesquare16", "0195238", "sample@email.com");
		_person_c = new Person("Constanin" , "Xiximora", "Samplesquare16", "0195238", "sample@email.com");
		_person_d = new Person("Diana" , "Wacholda", "Samplesquare16", "0195238", "sample@email.com");
		
		_company_a = new Company("Autodesk", "Samplesquare16");
		_company_b = new Company("BorealFilms", "Samplesquare16");
		_company_c = new Company("CuttingEdgeTechnologyInc", "Samplesquare16");
		_company_d = new Company("Dreamworks", "Samplesquare16");
		
		_testCompanies.add(_company_a);
		_testCompanies.add(_company_b);
		_testCompanies.add(_company_c);
		_testCompanies.add(_company_d);

		_testPersons.add(_person_a);
		_testPersons.add(_person_b);
		_testPersons.add(_person_c);
		_testPersons.add(_person_d);


	}
	// Initializes repository with 4 persons and 4 companies
	private void Initialize_TestData()
	{
		// Clear Old Data:
		_repo = new Repository();
		_testCompanies  = new ArrayList<>();
		_testPersons = new ArrayList<>();
		
		// Create New Data:
		_person_a = new Person("Adrian" , "Ziegler", "Samplesquare16", "0195238", "sample@email.com");
		_person_b = new Person("Bertram" , "Yacomelly", "Samplesquare16", "0195238", "sample@email.com");
		_person_c = new Person("Constanin" , "Xiximora", "Samplesquare16", "0195238", "sample@email.com");
		_person_d = new Person("Diana" , "Wacholda", "Samplesquare16", "0195238", "sample@email.com");
		
		_company_a = new Company("Autodesk", "Samplesquare16");
		_company_b = new Company("BorealFilms", "Samplesquare16");
		_company_c = new Company("CuttingEdgeTechnologyInc", "Samplesquare16");
		_company_d = new Company("Dreamworks", "Samplesquare16");
		
		_testCompanies.add(_company_a);
		_testCompanies.add(_company_b);
		_testCompanies.add(_company_c);
		_testCompanies.add(_company_d);

		_testPersons.add(_person_a);
		_testPersons.add(_person_b);
		_testPersons.add(_person_c);
		_testPersons.add(_person_d);

		
		
		// Add data to repo
			_repo.Add_Person(_person_c);
			_repo.Add_Person(_person_a);
			_repo.Add_Person(_person_d);
			_repo.Add_Person(_person_b);

			_repo.Add_Company(_company_c);
			_repo.Add_Company(_company_a);
			_repo.Add_Company(_company_d);
			_repo.Add_Company(_company_b);
	}
	
	
	@Test
	void Test_Init() {
		Initialize_TestData();
		
		assertEquals(4, _repo.Get_Num_Persons());
		assertEquals(4, _repo.Get_Num_Companies());
	}

	@Test
	void Test_Add_Employe() {
		Initialize_EmptyTestData();
		
		_company_a.Add_Employee(_person_a);
		
		assertTrue(_company_a.Get_EmployeeIsInCompany(_person_a));
		assertEquals(1, _company_a.Get_Num_Employees());
	}
	
	@Test
	void Test_Remove_Company() {
		Initialize_EmptyTestData();
		
		// Assert That Repo is Empty:
		assertEquals(0, _repo.Get_Num_Companies());
		assertEquals(0, _repo.Get_Num_Persons());
		
		// Add Data
		_company_a.Add_Employee(_person_a);
		_company_a.Add_Employee(_person_b);
		_company_a.Add_Employee(_person_c);
		_company_a.Add_Employee(_person_d);
		
		assertEquals(4, _company_a.Get_Num_Employees());
		
		// add to repo:
		_repo.Add_Company(_company_a);
		_repo.Add_Person(_person_a);
		_repo.Add_Person(_person_b);
		_repo.Add_Person(_person_c);
		_repo.Add_Person(_person_d);
		
		assertEquals(1, _repo.Get_Num_Companies());
		assertEquals(4, _repo.Get_Num_Persons());
		
		// remove without removing employees
		_repo.Delete_Company(_company_a, false);
		
		assertEquals(0, _repo.Get_Num_Companies());
		assertEquals(4, _repo.Get_Num_Persons());
		
		// add company to repo
		_repo.Add_Company(_company_a);
		
		// remove with removing employees
		_repo.Delete_Company(_company_a, true);
		
		assertEquals(0, _repo.Get_Num_Companies());
		assertEquals(0, _repo.Get_Num_Persons());
		
	}
	
	@Test
	void Test_Sort_Companies() {
		Initialize_TestData();
		
		_repo.Sort_Companies_ByName(false);
		
		for(int i = 0; i < _repo.Get_Num_Companies() && i < _testCompanies.size(); i++)
		{
			assertEquals(_repo.Get_Company(i), _testCompanies.get(i));
		}
		
		
		Initialize_TestData();
		
		_repo.Sort_Companies_ByName(true);
		
		for(int i = 0; i < _repo.Get_Num_Companies() && i < _testCompanies.size(); i++)
		{
			assertEquals(_repo.Get_Company(_repo.Get_Num_Companies() - (i+1)), _testCompanies.get(i));
		}
	}
}
