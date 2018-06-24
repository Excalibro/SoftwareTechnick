package ContactManager;

public class Person {

	private String _firstName;
	private String _lastName;
	private String _adress;
	private String _number;
	private String _email;
	private Company _company;
	
	public Person(){}
	
	public Person(	String firstName,
	String lastName,
	String adress,
	String number,
	String email)
	{
		_firstName = firstName;
		_lastName = lastName;
		_adress = adress;
		_number = number;
		_email = email;	
	}
	
	public String Get_LastName()
	{
		return _lastName;
	}
	public void Set_LastName(String lastName)
	{
		_lastName = lastName;
	}
	
	public String Get_FirstName()
	{
		return _firstName;
	}
	public void Set_FirstName(String firstname)
	{
		_firstName = firstname;
	}
	
	public String Get_Adress()
	{
		return _adress;
	}
	public void Set_Adress(String adress)
	{
		_adress = adress;
	}
	
	public String Get_Number()
	{
		return _number;
	}
	public void Set_Number(String number)
	{
		_number = number;
	}
	
	public String Get_Email()
	{
		return _email;
	}
	public void Set_Email(String email)
	{
		_email = email;
	}
	
	public Company Get_Company()
	{
		return _company;
	}
	public void Set_Email(Company company)
	{
		_company = company;
	}
}
