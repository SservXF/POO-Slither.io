/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package slither;

import GUI.Window;
import externData.ImageBank;
import javafx.application.Application;
import javafx.scene.input.KeyCombination;
import javafx.stage.Stage;

public class App extends Application{

    @Override
    public void start(Stage primaryStage){

        try {
            new ImageBank().loadImages();
        } catch (NullPointerException e) {
            System.out.println("Error while loading images");
        }

        Window window = new Window(primaryStage);
        
        primaryStage.setTitle("Slither");
        primaryStage.setScene(window.getScene());
        primaryStage.show();
       
        primaryStage.setFullScreenExitHint("");
        primaryStage.setFullScreenExitKeyCombination(KeyCombination.valueOf("Ctrl+C"));
        primaryStage.setOnCloseRequest(e -> {
            window.stopServer();
            window.stopClient();
            primaryStage.close();
        });
        primaryStage.setFullScreen(true);
    }

    public static void main(String[] args) {
        launch(args);
    }
}