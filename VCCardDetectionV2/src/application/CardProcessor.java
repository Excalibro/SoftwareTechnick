package application;

import java.io.File;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.MatOfPoint2f;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.videoio.VideoCapture;
import javafx.application.Platform;
import javafx.scene.image.Image;

/*
 * This Class Handels Comunication Between ImageProcessor and CardUi
 * */

enum ECardProcessorMode
{
	paused,
	imageFile,
	videoFile,
	liveCam
}

public class CardProcessor implements Runnable {

	private ScheduledExecutorService _execService;
	private CardGui _cardGui;
	private ECardProcessorMode _processorMode = ECardProcessorMode.paused;
	
	
	// Symbol / Number Update
	private ECardSymbols _cardSymbol_toUpdate;
	private ECardNumbers _cardNumber_toUpdate;
	private boolean _update_symbol;
	private boolean _update_number;
	
	// Input
	private VideoCapture _videoCapture;
	private Mat _cardImage;
	private File _cardVideoFile;
	
	// Parameter
	private int _blurSize = 1;
	private double _threshold = 0;
	private int _cardBlurSize = 1;
	private double _cardThreshold = 0;
	private double _card_side_length = 0.5;
	private double _card_side_length_deviation = 0.2;
	
	private double _cardName_drawSize = 0.5;
	
	// Rects
	private Rect _symbolRect = new Rect (12,85,60,70);
	private Rect _numberRect = new Rect (12,5,60,80);
	
	// Card Comparator
	private CardComparator _cardComparator = new CardComparator();
	
	CardProcessor()
	{
	}
	
	public void Close()
	{
		if(_videoCapture != null)
		{
		if (_videoCapture.isOpened())
			{
			// release the camera
			_videoCapture.release();
			}
		}
	}
	
	public void Start()
	{
		// Setup Vars:

		
		switch(_processorMode)
		{
		case paused:
			
			break;
		case imageFile:
			
			break;
		case videoFile:
			
			
			if(_cardVideoFile != null)
			{

				_videoCapture = new VideoCapture();
				_videoCapture.open(_cardVideoFile.getPath());
			}
			break;
		case liveCam:
			_videoCapture = new VideoCapture();
			_videoCapture.open(0);
			break;
		}

		//Exec Runnable:
		_execService = Executors.newScheduledThreadPool(5);
		_execService.scheduleAtFixedRate (this, 0, 33, TimeUnit.MILLISECONDS);
	}
	
	public void Stop()
	{
		if(_execService != null)
		{
			_execService.shutdown();
			_execService = null;
		}

		
		if(_videoCapture != null)
		{
		if (_videoCapture.isOpened())
			{
			// release the camera
			_videoCapture.release();
			}
		}
		
		_videoCapture = null;
	}
	
	@Override
	public void run() {
		
		/*Get a frame from Input source:
		 * */
		boolean inputValid = false;
		Mat frame = new Mat();
		
		switch(_processorMode)
		{
			case videoFile:
				if(_videoCapture != null)
				{
				if(_videoCapture.isOpened())
				{
				_videoCapture.read(frame);
				inputValid = true;
				}
				}
			break;
			case imageFile:
				if(_cardImage != null)
				{
					frame = _cardImage.clone();
					inputValid = true;
				}
			break;
			case liveCam:
				if(_videoCapture != null)
				{
				if(_videoCapture.isOpened())
				{	
				_videoCapture.read(frame);
				inputValid = true;
				}
				}
			break;
		default:
			break;
		}
		


		if(inputValid)
		{			
			// Copy Frame:
			Mat defaultFrame = frame.clone();
			
			// BlackWhiteFrame
			// Conv To Grey:
			Mat greyFrame = ImageProcessor.Make_BluredGreyFrame(frame, _blurSize);
			
			
				// Make Display Image:
			final Image greyImage = ImageProcessor.Convert_Mat_To_Image(greyFrame);
			Platform.runLater(() ->  _cardGui.Set_ImageView_BlackWhite_Image(greyImage));
			
			
			
			// Convert Threshold
			Mat thresholdFrame = ImageProcessor.Make_TresholdFrame(greyFrame, _threshold);
			
				// Make Display Image:
			final Image threshImage = ImageProcessor.Convert_Mat_To_Image(thresholdFrame);
			Platform.runLater(() ->  _cardGui.Set_ImageView_Treshold_Image(threshImage));
			 
			
			// Get Contours:
			List<MatOfPoint> contours =	ImageProcessor.Find_Contours(thresholdFrame);
			
			// Find Rectangles:
			List<MatOfPoint2f> rectangles = ImageProcessor.Approx_Rectangle(contours);
			
			// Check if Rectangles have the correct size:
				// Calculate Correct side length
			double frame_avg_sideLength = (frame.height() + frame.width())/2;
			double CardSideLength_desired = frame_avg_sideLength * _card_side_length;
			double cardSideLength_allowdDeviation = frame_avg_sideLength * _card_side_length_deviation;
			
			List<MatOfPoint2f> rectangles_of_cardSize = ImageProcessor.Get_Rectangles_Of_AverageSideLenght(rectangles, CardSideLength_desired, cardSideLength_allowdDeviation);
			
			// Update Label for card numbers:
			final String cardNumberString = "Number of cards: " + rectangles_of_cardSize.size();
			Platform.runLater(() ->  _cardGui.Set_Label_CardNumber_Text(cardNumberString));
			
			// Convert Rectangles to Matrices (Which means extracting the images)
			List<Mat> rectangleMatrices = ImageProcessor.Make_Matrices_From_Rectangles(frame, rectangles_of_cardSize);
			
			// Extract symbol and number
			List<Mat> symbolMats = ImageProcessor.Get_Submats_From_Mat(rectangleMatrices, _symbolRect);
			List<Mat> numberMats = ImageProcessor.Get_Submats_From_Mat(rectangleMatrices, _numberRect);
			
			// Make Grey Matrices
			List<Mat> symbolMats_grey = ImageProcessor.Make_BluredGreyFrames(symbolMats, _cardBlurSize);
			List<Mat> numberMats_grey  = ImageProcessor.Make_BluredGreyFrames(numberMats, _cardBlurSize);
			
			// Normalize Frames
			symbolMats_grey = ImageProcessor.Normalize_Frame(symbolMats_grey);
			numberMats_grey = ImageProcessor.Normalize_Frame(numberMats_grey);
			
			// Make Threshold Matrices
			List<Mat> symbolMats_thresh = ImageProcessor.Make_TresholdFrames(symbolMats_grey, _cardThreshold);
			List<Mat> numberMats_thresh = ImageProcessor.Make_TresholdFrames(numberMats_grey, _cardThreshold);
			
			// Crop Greatest Contours
			List<Mat> symbolMats_cropped = ImageProcessor.Crop_GreatestContours(symbolMats_thresh);
			List<Mat> numberMats_cropped = ImageProcessor.Crop_GreatestContours(numberMats_thresh);
			
			// Resize Cropped Frames
			List<Mat> symbolMats_resized = ImageProcessor.Resize_Frames(symbolMats_cropped,new Size(100,100));
			List<Mat> numberMats_resized = ImageProcessor.Resize_Frames(numberMats_cropped, new Size(100,100));
			
			// Update Card Images:
			if(rectangleMatrices.size() > 0 && symbolMats_thresh.size() > 0 && numberMats_thresh.size() > 0)
			{
				// Update Symbol
				if(_update_symbol)
				{
					// Make Display Image:
					final Image symbolImage = ImageProcessor.Convert_Mat_To_Image(symbolMats_resized.get(0));
					Platform.runLater(() ->  _cardGui.Set_CardPanel_Symbol(_cardSymbol_toUpdate, symbolImage));
					
					_cardComparator.Set_SymbolMatrix(symbolMats_resized.get(0), _cardSymbol_toUpdate);
					_update_symbol = false;
				}
				// Update Symbol
				if(_update_number)
				{
					
					final Image numberImage = ImageProcessor.Convert_Mat_To_Image(numberMats_resized.get(0));
					Platform.runLater(() ->  _cardGui.Set_CardPanel_Number(_cardNumber_toUpdate, numberImage));
					
					_cardComparator.Set_NumberMatrix(numberMats_resized.get(0), _cardNumber_toUpdate);
					_update_number = false;
				}
				
				Mat displayCard = rectangleMatrices.get(0).clone();
				
				Imgproc.rectangle(displayCard, _symbolRect.tl(), _symbolRect.br(), new Scalar(255,0,0));
				Imgproc.rectangle(displayCard, _numberRect.tl(), _numberRect.br(), new Scalar(0,0,255));
				
				// Update Image View
				final Image cardImage_default = ImageProcessor.Convert_Mat_To_Image(displayCard);
				Platform.runLater(() ->  _cardGui.Set_ImageView_Card_Image(cardImage_default));
				
				final Image numberImage_default = ImageProcessor.Convert_Mat_To_Image(numberMats.get(0));
				Platform.runLater(() ->  _cardGui.Set_ImageView_Number_Image(numberImage_default));
				final Image symbolImage_default = ImageProcessor.Convert_Mat_To_Image(symbolMats.get(0));
				Platform.runLater(() ->  _cardGui.Set_ImageView_Symbol_Image(symbolImage_default));
				
				final Image numberImage_grey = ImageProcessor.Convert_Mat_To_Image(numberMats_grey.get(0));
				Platform.runLater(() ->  _cardGui.Set_ImageView_Number_Grey_Image(numberImage_grey));
				final Image symbolImage_grey = ImageProcessor.Convert_Mat_To_Image(symbolMats_grey.get(0));
				Platform.runLater(() ->  _cardGui.Set_ImageView_Symbol_Grey_Image(symbolImage_grey));
				
				final Image numberImage_thresh = ImageProcessor.Convert_Mat_To_Image(numberMats_thresh.get(0));
				Platform.runLater(() ->  _cardGui.Set_ImageView_Number_Thresh_Image(numberImage_thresh));
				final Image symbolImage_thresh = ImageProcessor.Convert_Mat_To_Image(symbolMats_thresh.get(0));
				Platform.runLater(() ->  _cardGui.Set_ImageView_Symbol_Thresh_Image(symbolImage_thresh));
				
			}
			
			// Calculate Similar
			for (int i = 0; i < symbolMats_resized.size() && i < numberMats_resized.size() && i < rectangles_of_cardSize.size(); i++)
			{
				CardComparatorResult numberResult = _cardComparator.Calculate_MostSimilarNumber(numberMats_resized.get(i));
				CardComparatorResult symbolResult = _cardComparator.Calculate_MostSimilarSymbol(symbolMats_resized.get(i));
				
				
				
				// Write Result to Card Rectangle:
				String cardName = new String();
				
				switch(symbolResult.symbol)
				{
				case sym_none:
					cardName += "unknown symbol ";
					break;
				case sym_heart:
					cardName += "heart ";
					break;
				case sym_spade:
					cardName += "spade ";
					break;
				case sym_diamond:
					cardName += "diamond ";
					break;
				case sym_club:
					cardName += "club ";
					break;
				}
				
				switch(numberResult.number)
				{
				case num_none:
					cardName += "and unknown number";
					break;
				case num_7:
					cardName += "7";
					break;
				case num_8:
					cardName += "8";
					break;
				case num_9:
					cardName += "9";
					break;
				case num_10:
					cardName += "10";
					break;
				case num_B:
					cardName += "Jack";
					break;
				case num_D:
					cardName += "Queen";
					break;
				case num_K:
					cardName += "King";
					break;
				case num_A:
					cardName += "Ace";
					break;
				}
				
					// Make Card MiddlePoint
					Point cardCenter = Calculate_Poly_CenterPoint(rectangles_of_cardSize.get(i));
					
					 Imgproc.putText (
			    	         defaultFrame,
			    	         cardName,
			    	         cardCenter,
			    	         Core.FONT_HERSHEY_SIMPLEX ,
			    	         _cardName_drawSize,
			    	         new Scalar(0, 0, 255)
			    	                                
			    	      );
			    	      
				}
			
			// Update GUIs default Frame:
			ImageProcessor.Draw_AllContours(defaultFrame, contours);
			ImageProcessor.Draw_AllContours2f(defaultFrame, rectangles_of_cardSize);
			
			Image defaultImage = ImageProcessor.Convert_Mat_To_Image(defaultFrame);
			Platform.runLater(() -> _cardGui.Set_ImageView_Default_Image(defaultImage));
			}
			

			
	}
		

		


	public void Set_CardGui(CardGui cardGui)
	{
		_cardGui = cardGui;
	}
	
	public void Set_CardImage(Mat image)
	{
		_cardImage = image;
	}
	
	public void Set_ProcessorMode(ECardProcessorMode mode)
	{
		_processorMode = mode;
	}
	
	public void Load_ImageFile(File file)
	{
		_cardImage = Imgcodecs.imread(file.getPath());
	}
	
	public void Set_VideoFile(File file)
	{
		_cardVideoFile = file;
	}
	
	// Set Parameters: ---------------------------------------------------------------------------------
	public void Set_Threshold(double threshold )
	{
		_threshold = threshold ;
	}
	public void Set_BlurSize(int blurSize)
	{
		_blurSize = blurSize;
	}
	public void Set_CardThreshold(double threshold )
	{
		_cardThreshold = threshold ;
	}
	public void Set_CardBlurSize(int blurSize)
	{
		_cardBlurSize = blurSize;
	}
	public void Set_CardName_DrawSize(double drawSize)
	{
		_cardName_drawSize = drawSize;
	}
	public void Set_Card_Side_Length(double card_side_length)
	{
		_card_side_length = card_side_length;
	}
	public void Set_Card_Side_Length_Deviation(double card_side_length_deviation)
	{
		_card_side_length_deviation = card_side_length_deviation;
	}
	
	// Request Update
	void Request_Symbol_Update(ECardSymbols symbol)
	{
		_update_symbol = true;
		_cardSymbol_toUpdate = symbol;
	}
	void Request_Number_Update(ECardNumbers number)
	{
		_update_number = true;
		_cardNumber_toUpdate = number;
	}

	// Set Rect
	void Set_SymbolRect_x(int value)
	{
		_symbolRect.x = value;
	}
	void Set_SymbolRect_y(int value)
	{
		_symbolRect.y = value;
	}
	void Set_SymbolRect_w(int value)
	{
		_symbolRect.width = value;
	}
	void Set_SymbolRect_h(int value)
	{
		_symbolRect.height = value;
	}
	void Set_NumberRect_x(int value)
	{
		_numberRect.x = value;
	}
	void Set_NumberRect_y(int value)
	{
		_numberRect.y = value;
	}
	void Set_NumberRect_w(int value)
	{
		_numberRect.width = value;
	}
	public void Set_NumberRect_h(int value)
	{
		_numberRect.height = value;
	}
	
	// Save Cards:
	public void Save_CardImages(File dir)
	{
		CardIO.Save_Cards(dir, _cardComparator);
	}
	// Load Cards:
	public void Load_CardImages(File dir)
	{
		CardIO.Load_Cards(dir, _cardGui, _cardComparator);
	}
	
	Point Calculate_Poly_CenterPoint(MatOfPoint2f poly)
	{
		if(poly.height() != 0)
		{
			 double polyCenter_x = 0;
	    	 double polyCenter_y = 0;
	    	 
	    	 for (int i = 0; i < poly.height() ; i++)
	    	 {
	    		 polyCenter_x += poly.get(i, 0)[0];
	    		 polyCenter_y += poly.get(i, 0)[1];
	    		 
	    	 }
	    	 
	    	 polyCenter_x /= poly.height();
	    	 polyCenter_y /= poly.height();
	 
	    	 return new Point(polyCenter_x,polyCenter_y);
		}
		else
		{
			return new Point(0,0);
		}	
	}
}
