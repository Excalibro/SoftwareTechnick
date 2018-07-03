package application;

enum ECardSymbols
{
	sym_none,
	sym_club,
	sym_diamond,
	sym_heart,
	sym_spade
}

enum ECardNumbers
{
	num_none,
   num_7,
   num_8,
   num_9,
   num_10,
   num_B,
   num_D,
   num_K,
   num_A
}


public class CardComparatorResult {

	public ECardNumbers number;
	public ECardSymbols symbol;
	
	public double similarity;
	
	CardComparatorResult(ECardNumbers num, ECardSymbols sym, double sim)
	{
		number = num;
		symbol = sym;
		
		similarity = sim;
	}
	
}
