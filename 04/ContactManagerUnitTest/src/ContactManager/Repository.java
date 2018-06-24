package ContactManager;

import java.util.ArrayList;
import java.util.List;

public class Repository {

	private List<Company> _companies = new ArrayList<>();
	private List<Person> _persons  = new ArrayList<>();
	
	public void Print()
	{
		for (int  i = 0; i < _companies.size(); i++)
		{
			System.out.println(_companies.get(i).Get_Name());
		}
	}
	
	public void Add_Person(Person person)
	{
		_persons.add(person);
	}
	
	public void Add_Company(Company company)
	{
		_companies.add(company);
	}
	
	public void Sort_Companies_ByName(boolean inverse)
	{
		_companies.sort(new Company_Comparator(ECompanySortType.name, inverse));
	}
	
	public void Sort_Companies_ByAdress(boolean inverse)
	{
		_companies.sort(new Company_Comparator(ECompanySortType.adress, inverse));
	}
	
	public void Delete_Company(int companyId, boolean deleteEmployees)
	{
		
	}
	
	public void Delete_Company(Company company, boolean deleteEmployees)
	{
		if(deleteEmployees)
		{
			for (int i = 0; i < company.Get_Num_Employees(); i++)
			{
				_persons.remove(company.Get_Employee(i));
			}
			
			_companies.remove(company);
		}
		else
		{
			_companies.remove(company);
		}
	}
	
	public Company Get_Company(int id)
	{
		if(id >= 0 && id < _companies.size())
		{
			return _companies.get(id);
		}
		else
		{
			return null;
		}
	}
	
	public int Get_Num_Companies()
	{
		return _companies.size();
	}
	
	public int Get_Num_Persons()
	{
		return _persons.size();
	}
}
