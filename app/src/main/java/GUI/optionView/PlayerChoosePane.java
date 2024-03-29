package GUI.optionView;

import GUI.customButton.ButtonNotClickeablePixelFont;
import GUI.customButton.ButtonPixelFont;
import javafx.geometry.Insets;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class PlayerChoosePane extends VBox {


    

    private HBox botPlayerSpinnerBox;
    private HBox botPlayerSpinner;

    private HBox humanPlayerSpinnerBox;

    private static int textSize = 25;

    
    public PlayerChoosePane(boolean isSnake) {
        botPlayerSpinnerBox = new HBox();
        humanPlayerSpinnerBox = new HBox();
        ButtonNotClickeablePixelFont titleBot = new ButtonNotClickeablePixelFont("NUMBER OF IA : ",textSize);
        ButtonNotClickeablePixelFont ViewNumber = new ButtonNotClickeablePixelFont(String.valueOf(SetOfConfiguration.getNumberOfBot()),textSize);
        botPlayerSpinner = new HBox();
        ButtonPixelFont plus = new ButtonPixelFont("+",textSize, false);
        ButtonPixelFont minus = new ButtonPixelFont("-",textSize, false);
        plus.setOnMouseClicked(event -> {
            SetOfConfiguration.incrementNumberOfBot();
            ViewNumber.setButtonText(String.valueOf(SetOfConfiguration.getNumberOfBot()));
        });

        minus.setOnMouseClicked(event -> {
            SetOfConfiguration.decrementNumberOfBot();
            ViewNumber.setButtonText(String.valueOf(SetOfConfiguration.getNumberOfBot()));
        });

        botPlayerSpinner.setSpacing(10);
        botPlayerSpinnerBox.setSpacing(50);
        botPlayerSpinner.getChildren().addAll(minus,ViewNumber,plus);
        botPlayerSpinnerBox.getChildren().addAll(titleBot,botPlayerSpinner);

        
        humanPlayerSpinnerBox.setSpacing(50);
        AddPlayerBox addPlayerBoxSnakePlayer1 = new AddPlayerBox(isSnake);
        AddPlayerBox addPlayerBoxSnakePlayer2 = new AddPlayerBox(isSnake);
        humanPlayerSpinnerBox.getChildren().addAll(addPlayerBoxSnakePlayer1,addPlayerBoxSnakePlayer2);
        
        
        setPadding(new Insets(10)); // Marge de 10 pixels à tous les côtés du Pane
        getChildren().addAll(botPlayerSpinnerBox);
        getChildren().addAll(humanPlayerSpinnerBox);
        setAlignment(javafx.geometry.Pos.CENTER);

    }
    
}
