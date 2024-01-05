package GUI;

import client.Client;
import client.GUI.PlayPageSnakeOnline;
import javafx.concurrent.Task;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;
import model.engine.EngineSlither;
import model.engine.EngineSnake;
import model.skins.Skin;
import server.Server;

public class Window {

    private Server serverSnake = new Server();
    private Thread serverSnakeThread = new Thread(serverSnake);
    public void startServerSnake(){serverSnakeThread.start();}
    public void stopServerSnake(){
        serverSnake.shutdown();
        serverSnakeThread = new Thread(serverSnake);
    }
    public boolean isServerSnakeDone(){
        return !serverSnakeThread.isAlive();
    }
    public String getServerSnakeIp(){return serverSnake.getIp();}

    private Server serverSlither = new Server();
    private Thread serverSlitherThread = new Thread(serverSlither);
    public void startServerSlither(){serverSlitherThread.start();}
    public void stopServerSlither(){
        serverSlither.shutdown();
        serverSlitherThread = new Thread(serverSlither);
    }
    public boolean isServerSlitherDone(){return !serverSlitherThread.isAlive();}
    public String getServerSlitherIp(){return serverSlither.getIp();}

    private Client client = new Client();
    public void setClientPseudo(String pseudo){client.setPseudo(pseudo);}
    public void setClientIp(String ip){client.setIp(ip);}
    public void setClientSkin(Skin skin){client.setSkin(skin);}
    public PlayPageSnakeOnline getClientPlayPageSnakeOnline() {return client.getPlayPageSnakeOnline();}

    private Task<Void> clientTask = new Task<Void>() {
        @Override
        protected Void call() throws Exception {
            // long-running task
            client.run();
            return null;
        }
    };
    private Thread clientThread = new Thread(clientTask);
    public void startClient(){clientThread.start();}
    public void stopClient(){
        client.shutdown();
        clientThread = new Thread(clientTask);
    }

    private EngineSlither offlineSlither;
    private EngineSnake offlineSnake;

    public static final Rectangle2D screen = Screen.getPrimary().getBounds();
    public static final int WITDH = (int) screen.getWidth();
    public static final int HEIGHT = (int) screen.getHeight();

    private Stage primaryStage;
    private Scene scene;
    private VBox layout;

    public Stage getPrimaryStage() {return primaryStage;}
    public Scene getScene() {return scene;}
    public VBox getLayout() {return layout;}
    public void resetLayout() {
        layout = new VBox();
        scene.setRoot(layout);
    }

    public EngineSlither getOfflineSlither() {return offlineSlither;}
    public EngineSnake getOfflineSnake() {return offlineSnake;}
    public void setOfflineSlither(EngineSlither offlineSlither) {this.offlineSlither = offlineSlither;}
    public void setOfflineSnake(EngineSnake offlineSnake) {this.offlineSnake = offlineSnake;}

    public Window(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.layout = new VBox();
        this.scene = new Scene(layout, WITDH, HEIGHT);
        // setScene is already called in App.java

        switchToMenuPage();
    }

    public void switchToMenuPage(){
        new MenuPage(this).show();
    }

    public void switchToGameSelector(boolean isOnline){
        GameSelectorPage.createGameSelectorPage(this, isOnline).show();
    }

    public void switchToPageMainOptionOffline(boolean isSnake){
        new PageMainOptionOffline(this,isSnake).show();
    }

    public void switchToPseudoSelectorPage(boolean isSnake){
        new PseudoSelectorPage(this, isSnake).show();
    }

    public void switchToSkinSelectorPage(boolean isSnake){
        new SkinSelectorPage(this, isSnake).show();
    }

    public void switchToHostOrJoinPage(boolean isSnake){
        new HostOrJoinPage(this, isSnake).show();
    }

    public void switchToServerHostPage(boolean isSnake){
        new ServerHostPage(this, isSnake).show();
    }

    public void switchToServerJoinPage(boolean isSnake){
        new ServerJoinPage(this, isSnake).show();
    }
    
}
