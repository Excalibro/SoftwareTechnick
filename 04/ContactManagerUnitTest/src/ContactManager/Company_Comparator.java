package ContactManager;

import java.util.Comparator;

enum ECompanySortType
{
	name,
	adress
}

public class Company_Comparator implements Comparator<Company>{

	private ECompanySortType _type;
	private boolean _inverse;
	
	Company_Comparator(ECompanySortType type, boolean inverse)
	{
		_type = type;
		_inverse = inverse;
	}
	
	@Override
	public int compare(Company o1, Company o2) {
		
		int returnValue = 0;
		int compareValue = 0;
		switch(_type)
		{
		case name:
			compareValue = o1.Get_Name().compareTo( o2.Get_Name());
			
			if(compareValue > 0)
			{
				if(_inverse)
				{
					returnValue = -1;
				}
				else
				{
					returnValue = 1;
				}
			}
			else if(compareValue < 0)
			{
				if(_inverse)
				{
					returnValue = 1;
				}
				else
				{
					returnValue = -1;
				}
			}
			break;
		case adress:
			compareValue = o1.Get_Adress().compareTo( o2.Get_Adress());
			
			if(compareValue > 0)
			{
				if(_inverse)
				{
					returnValue = -1;
				}
				else
				{
					returnValue = 1;
				}
			}
			else if(compareValue < 0)
			{
				if(_inverse)
				{
					returnValue = 1;
				}
				else
				{
					returnValue = -1;
				}
			}
			break;
		}
		
		return returnValue;

	}

	

}
