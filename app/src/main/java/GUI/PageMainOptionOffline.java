package GUI;


import GUI.PlayPage.PlayPageSlither;
import GUI.PlayPage.PlayPageSnake;
import GUI.customButton.ButtonNotClickeablePixelFont;
import GUI.customButton.ButtonPixelFont;
import GUI.optionView.AddPlayerBox;
import GUI.optionView.OptionConfigPane;
import GUI.optionView.PlayerChoosePane;
import GUI.optionView.SetOfConfiguration;
import configuration.TouchControler;
import controleur.ControlerSlither;
import controleur.ControlerSnake;
import controleur.KeyboardControler;
import externData.ImageBank;
import interfaces.HumanPlayer;
import interfaces.Orientation.Angle;
import interfaces.Orientation.Direction;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import model.engine.EngineSlither;
import model.engine.EngineSnake;

public class PageMainOptionOffline extends Page {

    public static final int SOLO_SLITHER_MAP_RADIUS = 2000;
    public static final int SOLO_SNAKE_MAP_WIDTH = 4000;

    private boolean isSnake;
    public static boolean mouseActivated;

    OptionConfigPane optionConfigPane;

    public PageMainOptionOffline (Window window, boolean isSnake) {
        super(window);
        this.isSnake = isSnake;
        
    }

    public void valideConfig(){
        if(isSnake){
            if(optionConfigPane.isReachableWallActivated()){
                window.getConfigSnakeSnake().setTraversableWall(true);
            }
            if(optionConfigPane.isCollitionWithMeActivated()){
                window.getConfigSnakeSnake().setCollidingWithHimself(true);
            }
            if(optionConfigPane.greedyDeathActivated()){
                window.getConfigSnakeSnake().setDeathFood(true);
            }
            if(optionConfigPane.growingSnakeActivated()){
                window.getConfigSnakeSnake().setRadiusGrowing(true);
            }
        }
        else{
            if(optionConfigPane.isReachableWallActivated()){
                window.getConfigSnakeSlither().setTraversableWall(true);
            }
            if(optionConfigPane.isCollitionWithMeActivated()){
                window.getConfigSnakeSlither().setCollidingWithHimself(true);
            }
            if(optionConfigPane.greedyDeathActivated()){
                window.getConfigSnakeSlither().setDeathFood(true);
            }
            if(optionConfigPane.growingSnakeActivated()){
                window.getConfigSnakeSlither().setRadiusGrowing(true);
            }
        }
    }

    public void launchSlither(){
        clear();
        Scene scene = window.getScene();

        PlayPageSlither playPageSlither = new PlayPageSlither();
        if(SetOfConfiguration.getNumberOfHuman() <= 1){
            window.setOfflineSlither(EngineSlither.createGame(SOLO_SLITHER_MAP_RADIUS, window.getConfigFoodDouble(), window.getConfigSnakeSlither()));
        }
        else{
            window.setOfflineSlither(EngineSlither.createGame(Math.min((int) scene.getWidth(),(int)scene.getHeight())/2, window.getConfigFoodDouble(), window.getConfigSnakeSlither()));
        }
        

        if (mouseActivated) {
            
            scene.setOnMouseMoved(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    double x = event.getX()-Window.WITDH/2;
                    double y = event.getY()-Window.HEIGHT/2;
                    window.getOfflineSlither().getMousePlayer().mouseMoved(x, y,SetOfConfiguration.getNumberOfHuman() == 1);
                }
            });
            scene.setOnMouseReleased(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    window.getOfflineSlither().getMousePlayer().mouseReleased(isSnake);
                }
            });

            scene.setOnMousePressed(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    window.getOfflineSlither().getMousePlayer().mousePressed(isSnake);
                }
            });

            scene.setOnMouseDragged(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    double x = event.getX()-Window.WITDH/2;
                    double y = event.getY()-Window.HEIGHT/2;
                    window.getOfflineSlither().getMousePlayer().mouseDragged(x, y,SetOfConfiguration.getNumberOfHuman() == 1);
                }
            });

            window.getOfflineSlither().addPlayerMouse();


        }
        if(!(SetOfConfiguration.getNumberOfHuman() == 1 && mouseActivated)){
            for(int i = 0;i<SetOfConfiguration.getNumberOfHuman();i++){
                if(i != SetOfConfiguration.getIndexOfPlayerMouse()){
                    
                    KeyboardControler<Double,Angle> controler = new ControlerSlither(SetOfConfiguration.commandMapingPanes.get(i));
                    window.getOfflineSlither().addPlayer(controler);
                }
            }
        }

        for(int i = 0;i<SetOfConfiguration.getNumberOfBot();i++){
            window.getOfflineSlither().addBot();
        }
        
        scene.setOnKeyPressed( ev -> {
            if(ev.getCode() == KeyCode.ESCAPE){
                scene.setOnMouseClicked(null);
                scene.setOnMouseMoved(null);
                scene.setOnMousePressed(null);
                scene.setOnMouseReleased(null);
                scene.setOnMouseDragged(null);
                TouchControler.resetNumber();
                AddPlayerBox.resetNumero();
                mouseActivated=false;
                SetOfConfiguration.resetConfiguration();
                window.getOfflineSlither().stop();
                PageMainOptionOffline.mouseActivated = false;
                
                window.switchToMenuPage();
            }
            for (HumanPlayer p : window.getOfflineSlither().getPlayers()) {
                p.keyPressed(ev);
            }
        });

        scene.setOnKeyReleased( ev -> {
            for (HumanPlayer p : window.getOfflineSlither().getPlayers()) {
                p.keyReleased(ev);
            }
        });
        
        window.getOfflineSlither().addObserver(playPageSlither);
        window.getOfflineSlither().notifyObservers();

        window.getLayout().getChildren().add(playPageSlither);
        
        window.getOfflineSlither().run();
    }

    public void lanchSnake(){
        clear();
        Scene scene = window.getScene();

        PlayPageSnake playPageSnake = new PlayPageSnake();
        if(SetOfConfiguration.getNumberOfHuman() <= 1){
            window.setOfflineSnake(EngineSnake.createGame(SOLO_SNAKE_MAP_WIDTH, SOLO_SNAKE_MAP_WIDTH, window.getConfigFoodSnake(), window.getConfigSnakeSnake()));
        }
        else{
            window.setOfflineSnake(EngineSnake.createGame((int) scene.getWidth(),(int)scene.getHeight(), window.getConfigFoodSnake(), window.getConfigSnakeSnake()));
        }
        
        for(int i = 0;i<SetOfConfiguration.getNumberOfHuman();i++){
            
            KeyboardControler<Integer,Direction> controler = new ControlerSnake (SetOfConfiguration.commandMapingPanes.get(i));
            window.getOfflineSnake().addPlayer(controler);
        
            
        }
        for(int i = 0;i<SetOfConfiguration.getNumberOfBot();i++){
            window.getOfflineSnake().addBot();
        }

        scene.setOnKeyPressed( ev -> {
            if(ev.getCode() == KeyCode.ESCAPE){
                window.getOfflineSnake().stop();
                window.switchToMenuPage();
            }
            for (HumanPlayer p : window.getOfflineSnake().getPlayers()) {
                p.keyPressed(ev);
            }
        });

        scene.setOnKeyReleased( ev -> {
            for (HumanPlayer p : window.getOfflineSnake().getPlayers()) {
                p.keyReleased(ev);
            }
        });
        

        window.getOfflineSnake().addObserver(playPageSnake);
        window.getOfflineSnake().notifyObservers();  playPageSnake.requestFocus();

        window.getLayout().getChildren().add(playPageSnake);

        TouchControler.resetNumber();
        AddPlayerBox.resetNumero();
        SetOfConfiguration.resetConfiguration();
        window.getOfflineSnake().run();
    }

    @Override
    public void createPage() {
        ButtonNotClickeablePixelFont title = new ButtonNotClickeablePixelFont("SETTING OF LAUNCH",50);
        PlayerChoosePane playerChoosePane = new PlayerChoosePane(isSnake);
        ButtonPixelFont launchButton = new ButtonPixelFont("LAUNCH GAME",40, true);
        
        VBox layout = window.getLayout();
        layout.getChildren().clear();
        layout.setSpacing(50);
        layout.setPadding(new Insets(20)); // Marge de 10 pixels à tous les côtés du Pane
        layout.setAlignment(Pos.CENTER);
        setBackground(ImageBank.wallpaper_settings);

        final boolean isSnakeFinal = isSnake;
        launchButton.setOnAction(e -> {
            valideConfig();
            if(isSnakeFinal){
                lanchSnake();
            }else{
                launchSlither();
            }
        });
        optionConfigPane = new OptionConfigPane();

        layout.getChildren().addAll(title, playerChoosePane, optionConfigPane, launchButton);

        VBox.setMargin(playerChoosePane, new javafx.geometry.Insets(100, 0, 0, 0));
        VBox.setMargin(launchButton, new javafx.geometry.Insets(100, 0, 0, 0));
    }

    @Override
    public void sceneKeyConfiguration() {
        window.getScene().setOnKeyPressed( ev -> {
            if(ev.getCode() == KeyCode.ESCAPE){
                window.switchToMenuPage();
            }
        });
    }
}
