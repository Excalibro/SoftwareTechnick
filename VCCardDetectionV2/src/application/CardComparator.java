package application;

import org.opencv.core.Mat;

public class CardComparator {
	private Mat _mat_num_7; //0
	private Mat _mat_num_8; //1
	private Mat _mat_num_9; //2
	private Mat _mat_num_10; //3
	private Mat _mat_num_B; //4
	private Mat _mat_num_D; //5
	private Mat _mat_num_K; //6
	private Mat _mat_num_A; //7
	
	private Mat _mat_sym_club; //0
	private Mat _mat_sym_diamond;  //1
	private Mat _mat_sym_heart;//2
	private Mat _mat_sym_spade;//3


	
	CardComparator()
	{
		
	}
	
	public Mat Get_Symbol(int id)
	{
		if(id == 0 && _mat_sym_club!= null) {return  _mat_sym_club;}
		else if(id == 1 && _mat_sym_diamond!= null) {return  _mat_sym_diamond;}
		else if(id == 2 && _mat_sym_heart!= null) {return  _mat_sym_heart;}
		else if(id == 3 && _mat_sym_spade != null) {return  _mat_sym_spade;}
		else {return null; }
	}
	public Mat Get_Number(int id)
	{
		if(id == 0) {return  _mat_num_7;}
		else if(id == 1) {return  _mat_num_8;}
		else if(id == 2) {return  _mat_num_9;}
		else if(id == 3) {return  _mat_num_10;}
		else if(id == 4) {return  _mat_num_B;}
		else if(id == 5) {return  _mat_num_D;}
		else if(id == 6) {return  _mat_num_K;}
		else if(id == 7) {return  _mat_num_A;}
		else {return null; }
	}
	
	public CardComparatorResult Calculate_MostSimilarNumber(Mat matToCompare)
	{
		double max_value = -1;
		int max_id = -1;
		
		for (int i = 0 ; i < 8; i++)
		{
			double value = -1;
			
			Mat numberMat = Get_Number(i);
			
			if(numberMat != null)
			{
				if(numberMat.width() == matToCompare.width() && numberMat.height() == matToCompare.height())
				{
					value = Calculate_Mat_Similarity(numberMat, matToCompare);
				}
			}
			
			
			if(max_value < 0 && value >= 0)
			{
				max_value = value;
				max_id = i;
			}
			else if(value >= 0 && value < max_value)
			{
				max_value = value;
				max_id = i;
			}
		}
		
		// Return
		CardComparatorResult result = new CardComparatorResult(ECardNumbers.num_none, ECardSymbols.sym_none ,-1);
		
		if(max_id == 0) 	 {result.number = ECardNumbers.num_7;}
		else if(max_id == 1) {result.number = ECardNumbers.num_8;}
		else if(max_id == 2) {result.number = ECardNumbers.num_9;}
		else if(max_id == 3) {result.number = ECardNumbers.num_10;}
		else if(max_id == 4) {result.number = ECardNumbers.num_B;}
		else if(max_id == 5) {result.number = ECardNumbers.num_D;}
		else if(max_id == 6) {result.number = ECardNumbers.num_K;}
		else if(max_id == 7) {result.number = ECardNumbers.num_A;}
		
		if(max_id != -1) {result.similarity = max_value;}
		
		return result;
	}
	
	public CardComparatorResult Calculate_MostSimilarSymbol(Mat matToCompare)
	{
		double max_value = -1;
		int max_id = -1;
		
		for (int i = 0 ; i < 4; i++)
		{
			double value = -1;
			
			Mat symbolMat = Get_Symbol(i);
			
			if(symbolMat != null)
			{
				if(symbolMat.width() == matToCompare.width() && symbolMat.height() == matToCompare.height())
				{
					value = Calculate_Mat_Similarity(symbolMat, matToCompare);
				}
			}
			
			
			if(max_value < 0 && value >= 0)
			{
				max_value = value;
				max_id = i;
			}
			else if(value >= 0 && value < max_value)
			{
				max_value = value;
				max_id = i;
			}
		}
		
		// Return
		CardComparatorResult result = new CardComparatorResult(ECardNumbers.num_none, ECardSymbols.sym_none ,-1);
		
		if(max_id == 0) {result.symbol = ECardSymbols.sym_club;}
		else if(max_id == 1) {result.symbol = ECardSymbols.sym_diamond;}
		else if(max_id == 2) {result.symbol = ECardSymbols.sym_heart;}
		else if(max_id == 3) {result.symbol = ECardSymbols.sym_spade;}
		if(max_id != -1) {result.similarity = max_value;}
		
		return result;
	}
	
	public double Calculate_Mat_Similarity(Mat a, Mat b)
	{
		double value_final = 0;
		
		for(int x = 0; x < a.size().width; x++)
		{
			for(int y = 0; y < a.size().height; y++)
			{
				double value_current = a.get(y, x)[0] - b.get(y, x)[0];
				value_current = Math.abs(value_current);
				value_final += value_current;
			}
		}
		
		return value_final;
	}
	
	public void Set_NumberMatrix(Mat numberMat, ECardNumbers cardNumber)
	{
		switch(cardNumber)
		{
		case num_7:
			_mat_num_7 = numberMat;
			break;
		case num_8:
			_mat_num_8 = numberMat;
			break;
		case num_9:
			_mat_num_9 = numberMat;
			break;
		case num_10:
			_mat_num_10 = numberMat;
			break;
		case num_B:
			_mat_num_B = numberMat;
			break;
		case num_D:
			_mat_num_D = numberMat;
			break;
		case num_K:
			_mat_num_K = numberMat;
			break;
		case num_A:
			_mat_num_A = numberMat;
			break;
		default:
			break;
		}
	}
	
	public void Set_SymbolMatrix(Mat symbolMat, ECardSymbols cardSymbol)
	{
		switch(cardSymbol)
		{
		case sym_club:
			_mat_sym_club = symbolMat;
			break;
		case sym_diamond:
			_mat_sym_diamond = symbolMat;
			break;
		case sym_heart:
			_mat_sym_heart = symbolMat;
			break;
		case sym_spade:
			_mat_sym_spade = symbolMat;
			break;
		default:
			break;
		}
	}
}
