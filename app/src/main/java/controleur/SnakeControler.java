package controleur;

import javafx.scene.input.KeyEvent;
import model.SnakeInteger;


//Interface fonctionnelle
public interface SnakeControler{
    public void handle(KeyEvent ev, SnakeInteger snake);
}