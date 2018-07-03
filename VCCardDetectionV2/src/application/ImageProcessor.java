package application;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.core.MatOfPoint;
import org.opencv.core.MatOfPoint2f;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
/*
 * This Class contains several image processing functions
 * */

import javafx.scene.image.Image;

public class ImageProcessor {

	// Converts Matrix to Image
	public static Image Convert_Mat_To_Image(Mat mat)
	{
		MatOfByte matOfByte = new MatOfByte();
		Imgcodecs.imencode(".png", mat, matOfByte);
		return new Image(new ByteArrayInputStream(matOfByte.toArray()));
	}
	// Normalize Frame
	public static Mat Normalize_Frame(Mat frame)
	{
		Mat normalizedFrame = new Mat();
		Core.normalize(frame, normalizedFrame, 0, 255, frame.type());
		return normalizedFrame;
	}
	// Normalize Frames
	public static List<Mat> Normalize_Frame(List<Mat> frames)
	{
		List<Mat> normalizedFrames = new ArrayList<>();
		for(int i = 0; i <frames.size(); i++)
		{
			normalizedFrames.add(frames.get(i));
		}
		return normalizedFrames;
	}
	// Resize Frame
	public static List<Mat> Resize_Frames(List<Mat> frames, Size size)
	{
		List<Mat> resizedFrames = new ArrayList<>();
		for (int i = 0; i < frames.size(); i++)
		{
			Mat resizedFrame = new Mat();
			
			Imgproc.resize(frames.get(i), resizedFrame, size);
			resizedFrames.add(resizedFrame);
		}
		return resizedFrames;
	}
	
	// Create Blured GreyFrame
	public static Mat Make_BluredGreyFrame(Mat frame, int blurSize)
	{
		Mat greyFrame = new Mat();
		
		Imgproc.cvtColor(frame, greyFrame, Imgproc.COLOR_BGR2GRAY);
		if(blurSize % 2 != 0)
		{
			Imgproc.GaussianBlur(greyFrame, greyFrame,new Size(blurSize,blurSize), 0);
			
		}
		
		return greyFrame;
	}
	// Make Blured Greyframe List
	public static List<Mat> Make_BluredGreyFrames(List<Mat> frames, int blurSize)
	{
		List<Mat> greyFrames = new ArrayList<>();
		
		for (int i = 0; i < frames.size(); i++)
		{
			greyFrames.add(Make_BluredGreyFrame(frames.get(i), blurSize));
		}
		
		return greyFrames;
	}

	
	// Threshold Image
	public static Mat Make_TresholdFrame(Mat frame, double treshold)
	{
		Mat tresholdFrame = new Mat();
		Imgproc.threshold(frame, tresholdFrame, treshold, 255, Imgproc.THRESH_BINARY);
		return tresholdFrame;
	}
	// Threshold Frame List
	public static List<Mat> Make_TresholdFrames(List<Mat> frames, double treshold)
	{
		List<Mat> tresholdFrames = new ArrayList<Mat>();
		for (int i = 0; i < frames.size(); i++)
		{
			tresholdFrames.add(Make_TresholdFrame(frames.get(i), treshold));
		}

		return tresholdFrames;
	}
	// Find Contours
	public static List<MatOfPoint> Find_Contours (Mat frame)
	{
		 List<MatOfPoint> points = new ArrayList<>();
	     final Mat hierarchy = new Mat();
	     //Imgproc.findContours(frame, points, hierarchy, Imgproc.RETR_TREE, Imgproc.CHAIN_APPROX_SIMPLE);
	     Imgproc.findContours(frame, points, hierarchy, Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_SIMPLE);
	     return points;
	     
	     
	}
	// Draw All Contours:
	public static void Draw_AllContours (Mat destFrame, List<MatOfPoint> contours)
	{	     
		for(int i = 0; i < contours.size(); i++)
		{
			Imgproc.drawContours(destFrame, contours, i, new Scalar(255,0,0));
		}
	}
	// Draw All Contours2f:
	public static void Draw_AllContours2f (Mat destFrame, List<MatOfPoint2f> contours)
	{	     
		for(int i = 0; i < contours.size(); i++)
		{
			MatOfPoint contour = new MatOfPoint(contours.get(i).toArray());
			List<MatOfPoint> drawContours = new ArrayList<>();
			drawContours.add(contour);
			
			Imgproc.drawContours(destFrame, drawContours, 0, new Scalar(0,0,255));
		}
	}
	
	// Contours To Rectangles:
	public static List<MatOfPoint2f> Approx_Rectangle(List<MatOfPoint> contours)
	{
		List<MatOfPoint2f> rectangles = new ArrayList<>();
		
		for (int i = 0 ; i < contours.size() ; i++)
		{
			 MatOfPoint2f contour2f = new MatOfPoint2f(contours.get(i).toArray());
	    	 MatOfPoint2f approxPoly = new MatOfPoint2f();
			
	    	 // Calculate ApproxDistance
	    	 double approxDistance = Imgproc.arcLength(contour2f, true) * 0.02;
	    	 
			 //Approximate Polygon
			 Imgproc.approxPolyDP(contour2f, approxPoly, approxDistance, true);
			 
			 // Check for Rectangle:
	    	 if(approxPoly.size().height == 4)
	    	 {	    		 
	    		 // Now Check for area:
	    		 double contourArea = Imgproc.contourArea(contours.get(i));
	    		 double rectArea = Imgproc.contourArea(approxPoly);
	    		 
	    		 double areaDeviation = Math.abs(rectArea - contourArea);
	    		 double avgArea = (rectArea + contourArea) /2;
	    		 
	    		 if(areaDeviation / avgArea < 0.1)
	    		 {
		    		 rectangles.add(approxPoly);	
	    		 }
	    	 }
		}
		
		return rectangles;
	}
	// Crop to greatest Contour:
	public static Mat Crop_GreatestContour(Mat frame)
	{
		// Find Contours:
		Imgproc.threshold(frame, frame, 127, 255, Imgproc.THRESH_BINARY_INV);
		
		List<MatOfPoint> contours = Find_Contours(frame);
		// Calculate Max Area
		List<Double> areas = new ArrayList<>();
		for (int i = 0 ; i < contours.size(); i++)
		{
			areas.add(Imgproc.contourArea(contours.get(i)));
		}
		// find greates area
		int greatesContourArea_id = -1;
		for (int i = 0 ; i < contours.size(); i++)
		{
			if(greatesContourArea_id < 0)
			{
				greatesContourArea_id = i;
			}
			else
			{
				if(areas.get(i) > areas.get(greatesContourArea_id))
				{
					greatesContourArea_id = i;
				}
			}
		}
		// Min Bounding rect
		if(greatesContourArea_id >= 0)
		{	
			Rect boundingRect = Imgproc.boundingRect(contours.get(greatesContourArea_id));
			Mat croppedFrame = frame.submat(boundingRect);
			Imgproc.rectangle(frame, boundingRect.tl(),  boundingRect.br(), new Scalar(255,255,255));
			return croppedFrame;
		}
		else
		{
			return frame;
		}

	}
	public static List<Mat>Crop_GreatestContours(List<Mat> frames)
	{
		List<Mat> croppedFrames = new ArrayList<>();
		
		for(int i = 0; i < frames.size(); i++)
		{
			croppedFrames.add(Crop_GreatestContour(frames.get(i)));
		}
		return croppedFrames;
	}
	
	// Rectangles to Matrices:
	public static List<Mat> Make_Matrices_From_Rectangles(Mat srcFrame, List<MatOfPoint2f> rectangles)
	{
		List<Mat> rectMats = new ArrayList<>();
		
		for (int i = 0; i < rectangles.size() ; i++)
		{
			// Sort Points of rectangle:
			 MatOfPoint2f currentRect = rectangles.get(i); 
	    	 
	    	// Convert to Point array
	    	Point rectPoints[] = new Point[] {
	    			new Point(currentRect.get(0, 0)[0], (int)currentRect.get(0,0)[1]),
	    			new Point(currentRect.get(3, 0)[0], (int)currentRect.get(3,0)[1]),
	    			new Point(currentRect.get(2, 0)[0], (int)currentRect.get(2,0)[1]),
	    			new Point(currentRect.get(1, 0)[0], (int)currentRect.get(1,0)[1])};
	    	    	 
	    	 // Check if rect is rotated
	    	 Mat srcRect;
	    	 if(Get_CV_Point_Distance(rectPoints[0] , rectPoints[1]) < Get_CV_Point_Distance(rectPoints[0] , rectPoints[3]))
	    	 {
	    		 srcRect = new MatOfPoint2f(rectPoints[0], rectPoints[1], rectPoints[2], rectPoints[3]);
	    	 }
	    	 else
	    	 {
	    		 srcRect = new MatOfPoint2f(rectPoints[1], rectPoints[2], rectPoints[3], rectPoints[0]);
	    	 }
	    	 
	    	// Perspective Transform: 
	    	Mat destImage = new Mat(809, 500, srcFrame.type());
	        Mat dstRect = new MatOfPoint2f(new Point(0, 0), new Point(destImage.width() - 1, 0), new Point(destImage.width() - 1, destImage.height() - 1), new Point(0, destImage.height() - 1));
	    	
	    	Mat transform = Imgproc.getPerspectiveTransform(srcRect, dstRect);
	    	Imgproc.warpPerspective(srcFrame, destImage, transform, destImage.size());
    	 
	     rectMats.add(destImage);
		}
		
		return rectMats;
	}
	// Point Distance:
	static double Get_CV_Point_Distance(Point p1, Point p2)
	{
		double distance_x = p1.x - p2.x;
		double distance_y = p1.y - p2.y;
		
		return Math.sqrt(distance_x*distance_x + distance_y*distance_y);
	}
	
	// Get Sub Mat for symbol and number:
	static List<Mat> Get_Submats_From_Mat(List<Mat> matrices, Rect roi)
	{
		List<Mat> subMats = new ArrayList<>();
		
		for (int i = 0 ; i < matrices.size(); i++)
		{
			Mat mat = matrices.get(i).submat(roi);
			subMats.add(mat);
		}
		
		return subMats;
	}
	
	// Get Rectangles of size
	public static List<MatOfPoint2f> Get_Rectangles_Of_AverageSideLenght(List<MatOfPoint2f> rects, double length, double allowedDeviation)
	{
		List<MatOfPoint2f> correctRects = new ArrayList<>();
		
		for (int i = 0; i < rects.size(); i++)
		{
			// Get Points:
			MatOfPoint2f currentRect= rects.get(i);
			Point p1 = new Point(currentRect.get(0, 0)[0], currentRect.get(0, 0)[1]);
			Point p2 = new Point(currentRect.get(1, 0)[0], currentRect.get(1, 0)[1]);
			Point p3 = new Point(currentRect.get(2, 0)[0], currentRect.get(2, 0)[1]);
			Point p4 = new Point(currentRect.get(3, 0)[0], currentRect.get(3, 0)[1]);
			
			// Calculate side lengths:
			double length1 = Get_CV_Point_Distance(p1,p2);
			double length2 = Get_CV_Point_Distance(p2,p3);
			double length3 = Get_CV_Point_Distance(p3,p4);
			double length4 = Get_CV_Point_Distance(p4,p1);
			
			// Calc average length:
			double avgLength = (length1 + length2 + length3 + length4) / 4;
			
			// check if length is in range:
			if(avgLength >= length - allowedDeviation && avgLength <= length + allowedDeviation)
			{
				correctRects.add(rects.get(i));
			}
		}
		
		return correctRects;
	}
}
