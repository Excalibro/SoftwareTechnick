package application;

import java.io.File;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Slider;
import javafx.scene.control.Spinner;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/*
 * This Class Constructs the GUI
 * */

public class CardGui  implements EventHandler<Event>  {
	
	// CardProcessor(Used to update card images)
	private CardProcessor _cardProcessor;
	
	// Stage 
	private Stage _stage;
	
	// File Chooser
	private FileChooser _fileChooser = new FileChooser();
	
	// ImgprocSliders:
	private Slider _slider_blur;
	private Slider _slider_threshold;
	private Slider _slider_card_blur;
	private Slider _slider_card_threshold;
	private Slider _slider_cardName_drawSize;
	private Slider _slider_card_size;
	private Slider _slider_card_size_deviation;
	
	// Spinners:
	private Spinner<Integer> _spinner_symbolFrame_x;
	private Spinner<Integer> _spinner_symbolFrame_y;
	private Spinner<Integer> _spinner_symbolFrame_width;
	private Spinner<Integer> _spinner_symbolFrame_height;
	
	private Spinner<Integer> _spinner_numberFrame_x;
	private Spinner<Integer> _spinner_numberFrame_y;
	private Spinner<Integer> _spinner_numberFrame_width;
	private Spinner<Integer> _spinner_numberFrame_height;
	
	// Initial Spibox Values:
	int _numberRect_initial_x = 12;
	int _numberRect_initial_y = 5;
	int _numberRect_initial_w = 60;
	int _numberRect_initial_h = 80;
	
	int _symbolRect_initial_x = 12;
	int _symbolRect_initial_y = 85;
	int _symbolRect_initial_w = 60;
	int _symbolRect_initial_h = 70;
	
	// InputSource Options:
	private ObservableList<String> _inputSourceOptions = 
			FXCollections.observableArrayList(
			    "None",
			    "ImageFile",
			    "VideoFile",
			    "LiveCam"
			);
	
	private ComboBox<String> _comboBox_inputSource;
	
	// ImageViewer:
	private ImageView _imageView_default;
	private ImageView _imageView_blackWhite;
	private ImageView _imageView_treshold;
	private ImageView _imageView_card;
	private ImageView _imageView_symbol;
	private ImageView _imageView_number;
	private ImageView _imageView_symbol_grey;
	private ImageView _imageView_number_grey;
	private ImageView _imageView_symbol_thresh;
	private ImageView _imageView_number_thresh;
	
	// Card Number Label:
	private Label _label_numberOfCards;
	
	// MenuItems:
	MenuItem _menuItem_loadImage;
	MenuItem _menuItem_loadVideo;
	MenuItem _menuItem_loadCards;
	MenuItem _menuItem_saveCards;
	
	// Event Handler:
	private CardGui_MouseEventHandler _mouseEventHandler = new CardGui_MouseEventHandler(this);
	private CardGui_ActionEventHandler _actionEventHandler = new CardGui_ActionEventHandler(this);
	
	// CardPanel:
	CardPanel _cardPanel;
	
	// Constructor : SetupGui ------------------------------------------------------------
	CardGui(Stage primaryStage)
	{
		// Save Stage
		_stage = primaryStage;

		// Create Root:
		VBox root = new VBox();

		// Create Menu Bar:
		MenuBar menubar = new MenuBar();
		Menu fileMenu = new Menu("File");
		menubar.getMenus().add(fileMenu);
		
		// Menu Items:
		_menuItem_loadImage = new MenuItem("load image");
		fileMenu.getItems().addAll(_menuItem_loadImage);
		_menuItem_loadImage.setOnAction(_actionEventHandler);
		
		_menuItem_loadVideo = new MenuItem("load video");
		fileMenu.getItems().addAll(_menuItem_loadVideo);
		_menuItem_loadVideo.setOnAction(_actionEventHandler);
		
		_menuItem_loadCards = new MenuItem("load cards");
		fileMenu.getItems().addAll(_menuItem_loadCards);
		_menuItem_loadCards.setOnAction(_actionEventHandler);
		
		_menuItem_saveCards = new MenuItem("save cards");
		fileMenu.getItems().addAll(_menuItem_saveCards);
		_menuItem_saveCards.setOnAction(_actionEventHandler);
		
		
		root.getChildren().add(menubar);
		
		
		
		// Create ScrollPane:
		ScrollPane scrollPane = new ScrollPane();
		root.getChildren().add(scrollPane);
		
		// Create VBox:
		VBox vBox = new VBox();
		scrollPane.setContent(vBox);
		
		// Create Input source comboBox
		HBox hBox_input_drawSize = new HBox();
		vBox.getChildren().add(hBox_input_drawSize);
		
			//Label:
		Add_Label_To_Pane(hBox_input_drawSize, "InputSource");
			//ComboBox:
		_comboBox_inputSource = new ComboBox<>(_inputSourceOptions);
		hBox_input_drawSize.getChildren().add(_comboBox_inputSource);
		_comboBox_inputSource.setOnAction(_actionEventHandler);
		
		//Label for Draw Size:
		Add_Label_To_Pane(hBox_input_drawSize, "DrawSize:");
		//Draw Size Slider:
		_slider_cardName_drawSize = new Slider(0.1, 5, 0.5);
		_slider_cardName_drawSize.setOnMouseDragged(_mouseEventHandler);
		hBox_input_drawSize.getChildren().add(_slider_cardName_drawSize);
		
		// Create Default ImageView:  ----------------------------------------------------------------------------
		_imageView_default = new ImageView();
		_imageView_default.setFitWidth(600);
		_imageView_default.setFitHeight(400);
		vBox.getChildren().add(_imageView_default);
		
		// Create Card Number Label
		_label_numberOfCards = new Label("Nothing to see...");
		vBox.getChildren().add(_label_numberOfCards);
		
		// Create Sliders ----------------------------------------------------------------------------
		Add_Label_To_Pane(vBox,"Blur:");
		_slider_blur = new Slider(0,100,10);
		_slider_blur.setOnMouseDragged(_mouseEventHandler);
		vBox.getChildren().add(_slider_blur);
		
		Add_Label_To_Pane(vBox, "Treshold:");
		_slider_threshold = new Slider(0, 255, 100);
		_slider_threshold.setOnMouseDragged(_mouseEventHandler);
		vBox.getChildren().add(_slider_threshold);
		
		
		// Card Size and Size deviation
		HBox hBox_cardSize = new HBox();
		vBox.getChildren().add(hBox_cardSize);
		
		Add_Label_To_Pane(hBox_cardSize, "CardSize:");
		_slider_card_size = new Slider(0, 1, 0.5);
		_slider_card_size.setMaxSize(400, 50);
		_slider_card_size.setOnMouseDragged(_mouseEventHandler);
		hBox_cardSize.getChildren().add(_slider_card_size);
		
		Add_Label_To_Pane(hBox_cardSize, "CardSizeDeviation:");
		_slider_card_size_deviation = new Slider(0, 1, 0.2);
		_slider_card_size_deviation.setMaxSize(400, 50);
		_slider_card_size_deviation.setOnMouseDragged(_mouseEventHandler);
		hBox_cardSize.getChildren().add(_slider_card_size_deviation);
		
		
		// Create BW/TRSH ImageView:  ----------------------------------------------------------------------------
		HBox hBox_bw_trsh = new HBox();
		vBox.getChildren().add(hBox_bw_trsh);
		_imageView_blackWhite = new ImageView();
		_imageView_blackWhite.setFitWidth(400);
		_imageView_blackWhite.setFitHeight(300);
		hBox_bw_trsh.getChildren().add(_imageView_blackWhite);
		
		_imageView_treshold = new ImageView();
		_imageView_treshold.setFitWidth(400);
		_imageView_treshold.setFitHeight(300);
		hBox_bw_trsh.getChildren().add(_imageView_treshold);
		
		// Create Spinners for Symbol: ----------------------------------------------------------------------------
			// Label
		Add_Label_To_Pane(vBox, "SymbolRect (bounds):");
		
		HBox hBox_symbolRect_xywh = new HBox();
		vBox.getChildren().add(hBox_symbolRect_xywh);
		
		// X:
	    Add_Label_To_Pane(hBox_symbolRect_xywh, "X:");
		
			// SpinboxX
		_spinner_symbolFrame_x  = Add_Spinner_To_Pane(hBox_symbolRect_xywh, 0, 1000, _symbolRect_initial_x);
		
		// Y:
	    Add_Label_To_Pane(hBox_symbolRect_xywh, "Y:");
		
		// SpinboxY
		_spinner_symbolFrame_y = Add_Spinner_To_Pane(hBox_symbolRect_xywh, 0, 1000, _symbolRect_initial_y);
		
		// Width:
		Add_Label_To_Pane(hBox_symbolRect_xywh, "W:");
		
		// SpinboxWidth
		_spinner_symbolFrame_width = Add_Spinner_To_Pane(hBox_symbolRect_xywh, 0, 1000, _symbolRect_initial_w);
		
		// Height:
		Add_Label_To_Pane(hBox_symbolRect_xywh, "H:");
		
		// Spinbox_Height
		_spinner_symbolFrame_height = Add_Spinner_To_Pane(hBox_symbolRect_xywh, 0, 1000, _symbolRect_initial_h);
		
		
		// Slider for card blur:
		Add_Label_To_Pane(hBox_symbolRect_xywh, "Card Blur:");
		_slider_card_blur = new Slider();
		_slider_card_blur.setMin(0);
		_slider_card_blur.setMax(255);
		_slider_card_blur.setOnMouseDragged(_mouseEventHandler);
		hBox_symbolRect_xywh.getChildren().add(_slider_card_blur);
		
		
		// Create Spinners for Number:----------------------------------------------------------------------------
		// Label
	Add_Label_To_Pane(vBox, "NumberRect (bounds):");
	
	HBox hBox_numberRect_xywh = new HBox();
	vBox.getChildren().add(hBox_numberRect_xywh);
	
		// X:
    Add_Label_To_Pane(hBox_numberRect_xywh, "X:");
	
		// SpinboxX
	_spinner_numberFrame_x  = Add_Spinner_To_Pane(hBox_numberRect_xywh, 0, 1000, _numberRect_initial_x);
	
	// Y:
    Add_Label_To_Pane(hBox_numberRect_xywh, "Y:");
	
	// SpinboxY
	_spinner_numberFrame_y = Add_Spinner_To_Pane(hBox_numberRect_xywh, 0, 1000, _numberRect_initial_y);
	
	// Width:
	Add_Label_To_Pane(hBox_numberRect_xywh, "W:");
	
	// SpinboxWidth
	_spinner_numberFrame_width = Add_Spinner_To_Pane(hBox_numberRect_xywh, 0, 1000, _numberRect_initial_w);
	
	// Height:
	Add_Label_To_Pane(hBox_numberRect_xywh, "H:");
	
	// Spinbox_Height
	_spinner_numberFrame_height = Add_Spinner_To_Pane(hBox_numberRect_xywh, 0, 1000, _numberRect_initial_h);
	
	// Slider for card threshold:
	Add_Label_To_Pane(hBox_numberRect_xywh, "Card Threshold:");
	_slider_card_threshold = new Slider();
	_slider_card_threshold.setMin(0);
	_slider_card_threshold.setMax(255);
	_slider_card_threshold.setOnMouseDragged(_mouseEventHandler);
	hBox_numberRect_xywh.getChildren().add(_slider_card_threshold);
	
	
	// Create CardView: ---------------------------------------------------------------------------------------
	HBox hBox_card = new HBox();
	vBox.getChildren().add(hBox_card);
	
	_imageView_card = new ImageView();
	_imageView_card.setFitWidth(200);
	_imageView_card.setFitHeight(300);
	hBox_card.getChildren().add(_imageView_card);
	
	// Add CardCoundLabel:
	
	
	// Create Symbol View:
	_imageView_symbol = new ImageView();
	_imageView_symbol.setFitWidth(100);
	_imageView_symbol.setFitHeight(150);
	hBox_card.getChildren().add(_imageView_symbol);
	
	// Create Number View:
	_imageView_number = new ImageView();
	_imageView_number.setFitWidth(100);
	_imageView_number.setFitHeight(150);
	hBox_card.getChildren().add(_imageView_number);
	
	// Create Symbol_Grey View:
	_imageView_symbol_grey = new ImageView();
	_imageView_symbol_grey.setFitWidth(100);
	_imageView_symbol_grey.setFitHeight(150);
	hBox_card.getChildren().add(_imageView_symbol_grey);
	
	// Create Number_Grey View:
	_imageView_number_grey = new ImageView();
	_imageView_number_grey.setFitWidth(100);
	_imageView_number_grey.setFitHeight(150);
	hBox_card.getChildren().add(_imageView_number_grey);
	
	// Create Symbol_Threshold View:
	_imageView_symbol_thresh = new ImageView();
	_imageView_symbol_thresh.setFitWidth(100);
	_imageView_symbol_thresh.setFitHeight(150);
	hBox_card.getChildren().add(_imageView_symbol_thresh);
	
	// Create Number_Threshold View:
	_imageView_number_thresh = new ImageView();
	_imageView_number_thresh.setFitWidth(100);
	_imageView_number_thresh.setFitHeight(150);
	hBox_card.getChildren().add(_imageView_number_thresh);
	
	//Create CardPanel -------------------------------------------------------------------------
	_cardPanel = new CardPanel(this);
	vBox.getChildren().add(_cardPanel);
	
	
		// Create Scene ----------------------------------------------------------------------------
		Scene scene = new Scene(root,1000,600);

			primaryStage.setScene(scene);
			primaryStage.show();
	}
	
	
	
	private Spinner<Integer> Add_Spinner_To_Pane(Pane pane, int min, int max, int value)
	{
		Spinner<Integer> spinner = new Spinner<Integer>(min,max,value);
		spinner.setPrefWidth(70);
		pane.getChildren().add(spinner);
		spinner.setOnMouseClicked(_mouseEventHandler);
		return spinner;
	}
	
	private void Add_Label_To_Pane(Pane pane,String text)
	{
		Label label = new Label(text);
		pane.getChildren().add(label);
	}
	
	
	public void Set_CardProcessor(CardProcessor cardProcessor)
	{
		_cardProcessor = cardProcessor;
	}
	
	
	// Image View Setter: ----------------------------------------------------------------------------
	public void Set_ImageView_Default_Image(Image image)
	{
		_imageView_default.setImage(image);
	}
	public void Set_ImageView_BlackWhite_Image(Image image)
	{
		_imageView_blackWhite.setImage(image);
	}
	public void Set_ImageView_Treshold_Image(Image image)
	{
		_imageView_treshold.setImage(image);
	}
	public void Set_ImageView_Card_Image(Image image)
	{
		_imageView_card.setImage(image);
	}
	public void Set_ImageView_Symbol_Image(Image image)
	{
		_imageView_symbol.setImage(image);
	}
	public void Set_ImageView_Number_Image(Image image)
	{
		_imageView_number.setImage(image);
	}
	public void Set_ImageView_Symbol_Grey_Image(Image image)
	{
		_imageView_symbol_grey.setImage(image);
	}
	public void Set_ImageView_Number_Grey_Image(Image image)
	{
		_imageView_number_grey.setImage(image);
	}
	public void Set_ImageView_Symbol_Thresh_Image(Image image)
	{
		_imageView_symbol_thresh.setImage(image);
	}
	public void Set_ImageView_Number_Thresh_Image(Image image)
	{
		_imageView_number_thresh.setImage(image);
	}
	CardProcessor Get_CardProcessor()
	{
		return _cardProcessor;
	}
	
	// Set CardNumber Label:
	public void Set_Label_CardNumber_Text(String text)
	{
		_label_numberOfCards.setText(text);
	}
	
	// Comunication ----------------------------------------------------------------------------------
	void Set_CardPanel_Symbol(ECardSymbols symbol, Image image)
	{
		_cardPanel.Set_Symbol(symbol, image);
	}
	void Set_CardPanel_Number(ECardNumbers number, Image image)
	{
		_cardPanel.Set_Number(number, image);
	}
	void Request_Symbol_Update(ECardSymbols symbol)
	{
		_cardProcessor.Request_Symbol_Update(symbol);
	}
	void Request_Number_Update(ECardNumbers number)
	{
		_cardProcessor.Request_Number_Update(number);
	}


	// Event Handeling -------------------------------------------------------------------------------------
	@Override
	public void handle(Event event) {
		// Input Source Changed:
		if(event.getSource() == _comboBox_inputSource)
		{
			if(_comboBox_inputSource.getValue().toString().compareTo("None") == 0)
			{
				_cardProcessor.Stop();
				_cardProcessor.Set_ProcessorMode(ECardProcessorMode.paused);
			}
			else if(_comboBox_inputSource.getValue().toString().compareTo("LiveCam") == 0)
			{
				if(_cardProcessor != null)
				{
					
					_cardProcessor.Stop();
					_cardProcessor.Set_ProcessorMode(ECardProcessorMode.liveCam);
					_cardProcessor.Start();
				}
			}
			else if(_comboBox_inputSource.getValue().toString().compareTo("ImageFile") == 0)
			{
				if(_cardProcessor != null)
				{
					_cardProcessor.Stop();
					_cardProcessor.Set_ProcessorMode(ECardProcessorMode.imageFile);
					_cardProcessor.Start();
				}
			}
			else if(_comboBox_inputSource.getValue().toString().compareTo("VideoFile") == 0)
			{
				if(_cardProcessor != null)
				{	
					_cardProcessor.Stop();
					_cardProcessor.Set_ProcessorMode(ECardProcessorMode.videoFile);
					_cardProcessor.Start();
				}
			}
		}
		// Load Image
		else if(event.getSource() == _menuItem_loadImage)
		{
			File file = _fileChooser.showOpenDialog(_stage);
			_cardProcessor.Load_ImageFile(file);
		}
		else if(event.getSource() == _menuItem_loadVideo)
		{
			File file = _fileChooser.showOpenDialog(_stage);
			_cardProcessor.Set_VideoFile(file);
		}
		else if(event.getSource() == _menuItem_loadCards)
		{
			DirectoryChooser chooser = new DirectoryChooser();
			chooser.setTitle("load card images");
			File selectedDirectory = chooser.showDialog(_stage);
			_cardProcessor.Load_CardImages(selectedDirectory);
		}
		else if(event.getSource() == _menuItem_saveCards)
		{
			DirectoryChooser chooser = new DirectoryChooser();
			chooser.setTitle("save card images");
			File selectedDirectory = chooser.showDialog(_stage);
			_cardProcessor.Save_CardImages(selectedDirectory);
		}
		// Sliders
		else if(event.getSource() == _slider_blur)
		{
			_cardProcessor.Set_BlurSize((int)(1+ 2*Math.floor(_slider_blur.getValue())));
		}
		else if(event.getSource() == _slider_threshold)
		{
			_cardProcessor.Set_Threshold(_slider_threshold.getValue());
		}
		else if(event.getSource() == _slider_card_blur)
		{
			_cardProcessor.Set_CardBlurSize((int)(1+ 2*Math.floor(_slider_card_blur.getValue())));
		}
		else if(event.getSource() == _slider_card_threshold)
		{
			_cardProcessor.Set_CardThreshold(_slider_card_threshold.getValue());
		}
		else if(event.getSource() == _slider_cardName_drawSize)
		{
			_cardProcessor.Set_CardName_DrawSize(_slider_cardName_drawSize.getValue());
		}
		else if(event.getSource() == _slider_card_size)
		{
			_cardProcessor.Set_Card_Side_Length(_slider_card_size.getValue());
		}
		else if(event.getSource() == _slider_card_size_deviation)
		{
			_cardProcessor.Set_Card_Side_Length_Deviation(_slider_card_size_deviation.getValue());
		}
		// Spinner --------------------------------
		else if(event.getSource() == _spinner_symbolFrame_x)
		{
			_cardProcessor.Set_SymbolRect_x(_spinner_symbolFrame_x.getValue());
		}
		else if(event.getSource() == _spinner_symbolFrame_y)
		{
			_cardProcessor.Set_SymbolRect_y(_spinner_symbolFrame_y.getValue());
		}
		else if(event.getSource() == _spinner_symbolFrame_width)
		{
			_cardProcessor.Set_SymbolRect_w(_spinner_symbolFrame_width.getValue());
		}
		else if(event.getSource() == _spinner_symbolFrame_height)
		{
			_cardProcessor.Set_SymbolRect_h(_spinner_symbolFrame_height.getValue());
		}
		else if(event.getSource() == _spinner_numberFrame_x)
		{
			_cardProcessor.Set_NumberRect_x(_spinner_numberFrame_x.getValue());
		}
		else if(event.getSource() == _spinner_numberFrame_y)
		{
			_cardProcessor.Set_NumberRect_y(_spinner_numberFrame_y.getValue());
		}
		else if(event.getSource() == _spinner_numberFrame_width)
		{
			_cardProcessor.Set_NumberRect_w(_spinner_numberFrame_width.getValue());
		}
		else if(event.getSource() == _spinner_numberFrame_height)
		{
			_cardProcessor.Set_NumberRect_h(_spinner_numberFrame_height.getValue());
		}

	}
	
	
	// EVENT HANDLER:
	
	private class CardGui_MouseEventHandler implements EventHandler<MouseEvent>
	{

		CardGui _cardGui;
		
		CardGui_MouseEventHandler(CardGui cardGui)
		{
			_cardGui = cardGui;
		}
		
		@Override
		public void handle(MouseEvent event) {
			_cardGui.handle((Event)event);
		}
		
	}
	
	private class CardGui_ActionEventHandler implements EventHandler<ActionEvent>
	{

		CardGui _cardGui;
		
		CardGui_ActionEventHandler(CardGui cardGui)
		{
			_cardGui = cardGui;
		}
		
		@Override
		public void handle(ActionEvent event) {
			_cardGui.handle((Event)event);
		}
		
	}
}
