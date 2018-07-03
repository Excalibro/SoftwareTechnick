package application;
	
import org.opencv.core.Core;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/*
 * This Class is used for setting up the Program
 * */


public class Main extends Application {
	
	static {System.loadLibrary(Core.NATIVE_LIBRARY_NAME);}
	
	private CardGui _cardGui;
	private CardProcessor _cardProcessor;
	
	@Override
	public void start(Stage primaryStage) {
		
		// Setup Processor and Ui
		_cardGui = new CardGui(primaryStage);
		_cardProcessor = new CardProcessor();
		
		_cardGui.Set_CardProcessor(_cardProcessor);
		_cardProcessor.Set_CardGui(_cardGui);
		
		// ShutDown CardProcessor
		primaryStage.setOnCloseRequest((new EventHandler<WindowEvent>() {
			public void handle(WindowEvent we)
			{
				_cardProcessor.Close();
				System.exit(0);
			}
		}));
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
