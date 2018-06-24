package ContactManager;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Company{

	
	private String _name;
	private String _adress;
	private List<Person> _employees = new ArrayList<>();
	
	public Company(){}
	
	public Company(String name, String adress)
	{
		_name = name;
		_adress = adress;
	}
	
	public void Add_Employee(Person employee)
	{
		_employees.add(employee);
	}
	
	public void Remove_Employee(Person employee)
	{
		_employees.remove(employee);	
	}
	
	
	public String Get_Name()
	{
		return _name;
	}
	public void Set_Name(String name)
	{
		_name = name;
	}
	public String Get_Adress()
	{
		return _adress;
	}
	public void Set_Adress(String adress)
	{
		_adress = adress;
	}
	
	public int Get_Num_Employees()
	{
		return _employees.size();
	}
	
	public Person Get_Employee(int id)
	{
		if (id >= 0 && id < _employees.size())
		{
			return _employees.get(id);
		}
		else
		{
			return null;
		}
	}
	
	public boolean Get_EmployeeIsInCompany(Person employee)
	{
		if (_employees.contains(employee))
		{
			return true;
		}
		else
		{
			return false;
		}
	}
}
