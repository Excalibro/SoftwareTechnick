package application;

import java.io.File;

import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;

public class CardIO {

	// Save Cards:
	public static void Save_Cards(File dir, CardComparator cardComparator)
	{
			Mat mat_num_7 = cardComparator.Get_Number(0);
			// Save Numbers
			if(mat_num_7 != null)
			{
				String file = dir.getAbsolutePath();
				file += "/num_7.png";
				Imgcodecs.imwrite(file, mat_num_7);
			}
			Mat mat_num_8 = cardComparator.Get_Number(1);
			if(mat_num_8 != null)
			{
				String file = dir.getAbsolutePath();
				file += "/num_8.png";
				Imgcodecs.imwrite(file, mat_num_8);
			}
			Mat mat_num_9 = cardComparator.Get_Number(2);
			if(mat_num_9 != null)
			{
				String file = dir.getAbsolutePath();
				file += "/num_9.png";
				Imgcodecs.imwrite(file, mat_num_9);
			}
			Mat mat_num_10 = cardComparator.Get_Number(3);
			if(mat_num_10 != null)
			{
				String file = dir.getAbsolutePath();
				file += "/num_10.png";
				Imgcodecs.imwrite(file, mat_num_10);
			}
			Mat mat_num_B = cardComparator.Get_Number(4);
			if(mat_num_B != null)
			{
				String file = dir.getAbsolutePath();
				file += "/num_B.png";
				Imgcodecs.imwrite(file, mat_num_B);
			}
			Mat mat_num_D = cardComparator.Get_Number(5);
			if(mat_num_D != null)
			{
				String file = dir.getAbsolutePath();
				file += "/num_D.png";
				Imgcodecs.imwrite(file, mat_num_D);
			}
			Mat mat_num_K = cardComparator.Get_Number(6);
			if(mat_num_K != null)
			{
				String file = dir.getAbsolutePath();
				file += "/num_K.png";
				Imgcodecs.imwrite(file, mat_num_K);
			}
			Mat mat_num_A = cardComparator.Get_Number(7);
			if(mat_num_A != null)
			{
				String file = dir.getAbsolutePath();
				file += "/num_A.png";
				Imgcodecs.imwrite(file, mat_num_A);
			}
			
			// Save Symbols
			Mat mat_sym_club = cardComparator.Get_Symbol(0);
			if(mat_sym_club != null)
			{
				String file = dir.getAbsolutePath();
				file += "/sym_club.png";
				Imgcodecs.imwrite(file, mat_sym_club);
			}
			Mat mat_sym_diamond = cardComparator.Get_Symbol(1);
			if(mat_sym_diamond != null)
			{
				String file = dir.getAbsolutePath();
				file += "/sym_diamond.png";
				Imgcodecs.imwrite(file, mat_sym_diamond);
			}
			Mat mat_sym_heart = cardComparator.Get_Symbol(2);
			if(mat_sym_heart != null)
			{
				String file = dir.getAbsolutePath();
				file += "/sym_heart.png";
				Imgcodecs.imwrite(file, mat_sym_heart);
			}
			Mat mat_sym_spade = cardComparator.Get_Symbol(3);
			if(mat_sym_spade != null)
			{
				String file = dir.getAbsolutePath();
				file += "/sym_spade.png";
				Imgcodecs.imwrite(file, mat_sym_spade);
			}
	}
	
	// Load Cards
	public static void Load_Cards(File dir, CardGui cardGui, CardComparator cardComparator)
	{
		//7
	{
		String filePath = dir.getAbsolutePath();
		filePath += "/num_7.png";
		File file = new File(filePath);
		if(file.exists())
		{
			Mat mat = Imgcodecs.imread(filePath);
			cardComparator.Set_NumberMatrix(mat, ECardNumbers.num_7);
			cardGui.Set_CardPanel_Number(ECardNumbers.num_7, ImageProcessor.Convert_Mat_To_Image(mat));
		}
	}
	//8
	{
		String filePath = dir.getAbsolutePath();
		filePath += "/num_8.png";
		File file = new File(filePath);
		if(file.exists())
		{
			Mat mat = Imgcodecs.imread(filePath);
			cardComparator.Set_NumberMatrix(mat, ECardNumbers.num_8);
			cardGui.Set_CardPanel_Number(ECardNumbers.num_8, ImageProcessor.Convert_Mat_To_Image(mat));
		}
	}
	//9
	{
		String filePath = dir.getAbsolutePath();
		filePath += "/num_9.png";
		File file = new File(filePath);
		if(file.exists())
		{
			Mat mat = Imgcodecs.imread(filePath);
			cardComparator.Set_NumberMatrix(mat, ECardNumbers.num_9);
			cardGui.Set_CardPanel_Number(ECardNumbers.num_9, ImageProcessor.Convert_Mat_To_Image(mat));
		}
	}
	//10
	{
		String filePath = dir.getAbsolutePath();
		filePath += "/num_10.png";
		File file = new File(filePath);
		if(file.exists())
		{
			Mat mat = Imgcodecs.imread(filePath);
			cardComparator.Set_NumberMatrix(mat, ECardNumbers.num_10);
			cardGui.Set_CardPanel_Number(ECardNumbers.num_10, ImageProcessor.Convert_Mat_To_Image(mat));
		}
	}
	//B
	{
		String filePath = dir.getAbsolutePath();
		filePath += "/num_B.png";
		File file = new File(filePath);
		if(file.exists())
		{
			Mat mat = Imgcodecs.imread(filePath);
			cardComparator.Set_NumberMatrix(mat, ECardNumbers.num_B);
			cardGui.Set_CardPanel_Number(ECardNumbers.num_B, ImageProcessor.Convert_Mat_To_Image(mat));
		}
	}
	//D
	{
		String filePath = dir.getAbsolutePath();
		filePath += "/num_D.png";
		File file = new File(filePath);
		if(file.exists())
		{
			Mat mat = Imgcodecs.imread(filePath);
			cardComparator.Set_NumberMatrix(mat, ECardNumbers.num_D);
			cardGui.Set_CardPanel_Number(ECardNumbers.num_D, ImageProcessor.Convert_Mat_To_Image(mat));
		}
	}
	//K
	{
		String filePath = dir.getAbsolutePath();
		filePath += "/num_K.png";
		File file = new File(filePath);
		if(file.exists())
		{
			Mat mat = Imgcodecs.imread(filePath);
			cardComparator.Set_NumberMatrix(mat, ECardNumbers.num_K);
			cardGui.Set_CardPanel_Number(ECardNumbers.num_K, ImageProcessor.Convert_Mat_To_Image(mat));
		}
	}	

	//Symbols:
		//Club
	{
		String filePath = dir.getAbsolutePath();
		filePath += "/sym_club.png";
		File file = new File(filePath);
		if(file.exists())
		{
			Mat mat = Imgcodecs.imread(filePath);
			cardComparator.Set_SymbolMatrix(mat, ECardSymbols.sym_club);
			cardGui.Set_CardPanel_Symbol( ECardSymbols.sym_club, ImageProcessor.Convert_Mat_To_Image(mat));
		}
	}	
		//Diamond
	{
		String filePath = dir.getAbsolutePath();
		filePath += "/sym_diamond.png";
		File file = new File(filePath);
		if(file.exists())
		{
			Mat mat = Imgcodecs.imread(filePath);
			cardComparator.Set_SymbolMatrix(mat, ECardSymbols.sym_diamond);
			cardGui.Set_CardPanel_Symbol( ECardSymbols.sym_diamond, ImageProcessor.Convert_Mat_To_Image(mat));
		}
	}	
		//Heart
	{
		String filePath = dir.getAbsolutePath();
		filePath += "/sym_heart.png";
		File file = new File(filePath);
		if(file.exists())
		{
			Mat mat = Imgcodecs.imread(filePath);
			cardComparator.Set_SymbolMatrix(mat, ECardSymbols.sym_heart);
			cardGui.Set_CardPanel_Symbol( ECardSymbols.sym_heart, ImageProcessor.Convert_Mat_To_Image(mat));
		}
	}	
		//Spade
	{
		String filePath = dir.getAbsolutePath();
		filePath += "/sym_spade.png";
		File file = new File(filePath);
		if(file.exists())
			{
			Mat mat = Imgcodecs.imread(filePath);
			cardComparator.Set_SymbolMatrix(mat, ECardSymbols.sym_spade);
			cardGui.Set_CardPanel_Symbol( ECardSymbols.sym_spade, ImageProcessor.Convert_Mat_To_Image(mat));
		}
	}	
	}

	
}
