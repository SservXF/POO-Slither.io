package GUI;

import GUI.optionView.PageMainOptionOffline;
import externData.ImageBank;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class OfflinePage extends VBox {


    Stage primaryStage;
    Scene homeScene;
    

    public OfflinePage(Stage primaryStage,Scene homeScene, int WITDH, int HEIGHT) {
        this.primaryStage = primaryStage;

        ButtonPixelFont title = new ButtonPixelFont("OFFLINE",60);

        ButtonPixelFont playButtonSlither = new ButtonPixelFont("SLITHER.IO",40);
        ButtonPixelFont playButtonSnake = new ButtonPixelFont("SNAKE",40);
        ButtonPixelFont exitButton = new ButtonPixelFont("EXIT",40);

        Border border = new Border(new javafx.scene.layout.BorderStroke(
                javafx.scene.paint.Color.BLACK,
                BorderStrokeStyle.NONE, // Type de la bordure, ici aucun
                new CornerRadii(10), // Marge arrondie du rectangle
                new BorderWidths(20) // Épaisseur de la bordure
        ));

        // Application de la bordure au VBox
        setBorder(border);
        setAlignment(Pos.CENTER);
        setSpacing(50);


        

        BackgroundSize backgroundSize = new BackgroundSize(WITDH, HEIGHT, false, false, false, true);
        BackgroundImage background = new BackgroundImage(
                ImageBank.homePageBackground,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                backgroundSize
        );

        this.setBackground(new Background(background));

        playButtonSlither.setOnAction(e -> {
            PageMainOptionOffline pageMainOption = new PageMainOptionOffline(primaryStage,homeScene,WITDH,HEIGHT,false);
            Scene optionScene = new Scene(pageMainOption,WITDH,HEIGHT);
            primaryStage.setScene(optionScene);
            primaryStage.show();
        });

        playButtonSnake.setOnAction(e -> {
            PageMainOptionOffline pageMainOption = new PageMainOptionOffline(primaryStage,homeScene,WITDH,HEIGHT,true);
            Scene optionScene = new Scene(pageMainOption,WITDH,HEIGHT);
            primaryStage.setScene(optionScene);
            primaryStage.show();
        });

        exitButton.setOnAction(e -> {
            primaryStage.close();
        });


        getChildren().addAll(title,playButtonSlither,playButtonSnake,exitButton);
        VBox.setMargin(playButtonSlither, new javafx.geometry.Insets(50, 0, 0, 0));
    }
    
}
