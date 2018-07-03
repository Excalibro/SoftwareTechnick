package application;

import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

public class CardPanel extends VBox implements EventHandler<MouseEvent> {

	private CardGui _cardGui;
	
	private Button _button_num_7;
	private Button _button_num_8;
	private Button _button_num_9;
	private Button _button_num_10;
	private Button _button_num_K;
	private Button _button_num_D;
	private Button _button_num_B;
	private Button _button_num_A;
	
	private Button _button_sym_heart;
	private Button _button_sym_club;
	private Button _button_sym_diamond;
	private Button _button_sym_spade;
	
	public CardPanel (CardGui cardGui)
	{
		_cardGui = cardGui;
		
		GridPane gridPane_number = new GridPane();
		GridPane gridPane_symbol = new GridPane();
		
		getChildren().add(gridPane_number);
		getChildren().add(gridPane_symbol);
		
		// Create Number Buttons:
						//7
					
					_button_num_7 = new Button();
					_button_num_7.setPrefWidth(40);
					_button_num_7.setPrefHeight(40);
					gridPane_number.add(_button_num_7,0,1);
					_button_num_7.setOnMousePressed(this);

					
					Label label_num_7 = new Label("7");
					gridPane_number.add(label_num_7,0,0);
					
						//8
					_button_num_8 = new Button();
					_button_num_8.setPrefWidth(40);
					_button_num_8.setPrefHeight(40);
					gridPane_number.add(_button_num_8, 1, 1);
					_button_num_8.setOnMousePressed(this);
					
					Label label_num_8 = new Label("8");
					gridPane_number.add(label_num_8,1,0);
					
						//9
					_button_num_9 = new Button();
					_button_num_9.setPrefWidth(40);
					_button_num_9.setPrefHeight(40);
					gridPane_number.add(_button_num_9, 2, 1);
					_button_num_9.setOnMousePressed(this);
					
					Label label_num_9 = new Label("9");
					gridPane_number.add(label_num_9,2,0);
					
						//10
					_button_num_10 = new Button();
					_button_num_10.setPrefWidth(40);
					_button_num_10.setPrefHeight(40);
					gridPane_number.add(_button_num_10, 3, 1);
					_button_num_10.setOnMousePressed(this);
			
					Label label_num_10 = new Label("10");
					gridPane_number.add(label_num_10,3,0);
					
						//B
					_button_num_B = new Button();
					_button_num_B.setPrefWidth(40);
					_button_num_B.setPrefHeight(40);
					gridPane_number.add(_button_num_B, 4, 1);
					_button_num_B.setOnMousePressed(this);

					Label label_num_B = new Label("B");
					gridPane_number.add(label_num_B,4,0);
					
						//D
					_button_num_D = new Button();
					_button_num_D.setPrefWidth(40);
					_button_num_D.setPrefHeight(40);
					gridPane_number.add(_button_num_D, 5, 1);
					_button_num_D.setOnMousePressed(this);

					Label label_num_D = new Label("D");
					gridPane_number.add(label_num_D,5,0);
					
						//K
					_button_num_K = new Button();
					_button_num_K.setPrefWidth(40);
					_button_num_K.setPrefHeight(40);
					gridPane_number.add(_button_num_K, 6, 1);
					_button_num_K.setOnMousePressed(this);

					Label label_num_K = new Label("K");
					gridPane_number.add(label_num_K,6,0);
					
						//A
					_button_num_A = new Button();
					_button_num_A.setPrefWidth(40);
					_button_num_A.setPrefHeight(40);
					gridPane_number.add(_button_num_A, 7, 1);
					_button_num_A.setOnMousePressed(this);

					Label _label_num_A = new Label("A");
					gridPane_number.add(_label_num_A,7,0);
					
					
					// Symbols
						//Heart
					_button_sym_heart = new Button();
					_button_sym_heart.setPrefWidth(40);
					_button_sym_heart.setPrefHeight(40);
					gridPane_symbol.add(_button_sym_heart, 0, 1);
					_button_sym_heart.setOnMousePressed(this);
					
					Label label_sym_heart = new Label("Heart");
					gridPane_symbol.add(label_sym_heart,0,0);
					
						//Spade
					_button_sym_spade = new Button();
					_button_sym_spade.setPrefWidth(40);
					_button_sym_spade.setPrefHeight(40);
					gridPane_symbol.add(_button_sym_spade, 1, 1);
					_button_sym_spade.setOnMousePressed(this);
					
					Label label_sym_spade = new Label("Spade");
					gridPane_symbol.add(label_sym_spade,1,0);
					
						//Diamond
					_button_sym_diamond = new Button();
					_button_sym_diamond.setPrefWidth(40);
					_button_sym_diamond.setPrefHeight(40);
					gridPane_symbol.add(_button_sym_diamond, 2, 1);
					_button_sym_diamond.setOnMousePressed(this);
					
					Label label_sym_diamond = new Label("Diamond");
					gridPane_symbol.add(label_sym_diamond,2,0);
					
						//Club
					_button_sym_club = new Button();
					_button_sym_club.setPrefWidth(40);
					_button_sym_club.setPrefHeight(40);
					gridPane_symbol.add(_button_sym_club, 3, 1);
					_button_sym_club.setOnMousePressed(this);
				
					Label label_sym_club = new Label("Club");
					gridPane_symbol.add(label_sym_club,3,0);
	}
	
	void Set_Symbol(ECardSymbols symbol, Image image)
	{
		switch(symbol)
		{
		case sym_club:
			_button_sym_club.setGraphic(new ImageView(image));
			break;
		case sym_diamond:
			_button_sym_diamond.setGraphic(new ImageView(image));
			break;
		case sym_heart:
			_button_sym_heart.setGraphic(new ImageView(image));
			break;
		case sym_spade:
			_button_sym_spade.setGraphic(new ImageView(image));
			break;
		default:
			break;
		}
	}
	
	void Set_Number(ECardNumbers number, Image image)
	{
		switch(number)
		{
		case num_7:
			_button_num_7.setGraphic(new ImageView(image));
			break;
		case num_8:
			_button_num_8.setGraphic(new ImageView(image));
			break;
		case num_9:
			_button_num_9.setGraphic(new ImageView(image));
			break;
		case num_10:
			_button_num_10.setGraphic(new ImageView(image));
			break;
		case num_B:
			_button_num_B.setGraphic(new ImageView(image));
			break;
		case num_D:
			_button_num_D.setGraphic(new ImageView(image));
			break;
		case num_K:
			_button_num_K.setGraphic(new ImageView(image));
			break;
		case num_A:
			_button_num_A.setGraphic(new ImageView(image));
			break;
		default:
			break;
		}
	}

	@Override
	public void handle(MouseEvent event) {
		
		if(event.getSource() == _button_num_7)
		{
			_cardGui.Request_Number_Update(ECardNumbers.num_7);
		}
		else if(event.getSource() == _button_num_8)
		{
			_cardGui.Request_Number_Update(ECardNumbers.num_8);
		}
		else if(event.getSource() == _button_num_9)
		{
			_cardGui.Request_Number_Update(ECardNumbers.num_9);
		}
		else if(event.getSource() == _button_num_10)
		{
			_cardGui.Request_Number_Update(ECardNumbers.num_10);	
		}
		else if(event.getSource() == _button_num_B)
		{
			_cardGui.Request_Number_Update(ECardNumbers.num_B);
		}
		else if(event.getSource() == _button_num_D)
		{
			_cardGui.Request_Number_Update(ECardNumbers.num_D);
		}
		else if(event.getSource() == _button_num_K)
		{
			_cardGui.Request_Number_Update(ECardNumbers.num_K);
		}
		else if(event.getSource() == _button_num_A)
		{
			_cardGui.Request_Number_Update(ECardNumbers.num_A);
		}
		if(event.getSource() == _button_sym_heart)
		{
			_cardGui.Request_Symbol_Update(ECardSymbols.sym_heart);
		}
		else if(event.getSource() == _button_sym_club)
		{
			_cardGui.Request_Symbol_Update(ECardSymbols.sym_club);
		}
		else if(event.getSource() == _button_sym_diamond)
		{
			_cardGui.Request_Symbol_Update(ECardSymbols.sym_diamond);
		}
		else if(event.getSource() == _button_sym_spade)
		{
			_cardGui.Request_Symbol_Update(ECardSymbols.sym_spade);
		}
	}
	
	
}
