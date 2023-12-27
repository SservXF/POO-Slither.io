package model.foods;

import java.util.ArrayList;
import java.util.Random;

import interfaces.Orientation;
import javafx.scene.paint.Color;
import model.coordinate.Coordinate;
import model.plateau.Snake;

public abstract class FoodFactory<Type extends Number & Comparable<Type>, O extends Orientation<O>> {

    public static enum FoodType {
        GROWING_FOOD,
        GROWING_BIG_FOOD,
        KILLER_FOOD,
        POISON_FOOD,
        DEATH_FOOD;


        public Color getColor() {
            switch (this) {
                case GROWING_FOOD:
                    return Color.GREEN;
                case GROWING_BIG_FOOD:
                    return Color.BLUE;
                case KILLER_FOOD:
                    return Color.RED;
                case DEATH_FOOD:
                    return Color.BLACK;
                case POISON_FOOD:
                    return Color.PURPLE;
                default:
                    throw new IllegalArgumentException("FoodType not recognized");
            }
        }
    }

    public static abstract class FoodBuilder<Type extends Number & Comparable<Type>, O extends Orientation<O>> {
        public abstract Food<Type,O> createFood(FoodType foodType, Coordinate<Type,O> coordinate);
    }

    protected final FoodBuilder<Type,O> foodBuilder;

    public FoodFactory(FoodBuilder<Type,O> foodBuilder) {
        this.foodBuilder = foodBuilder;
    }

    public abstract ArrayList<DeathFood<Type,O>> getDeathFoods(Snake<Type,O> snake);

    public Food<Type,O> getRandomFood(Coordinate<Type,O> coordinate){
        ArrayList<Food<Type,O>> allFoods = new ArrayList<Food<Type,O>>();
        for (FoodType foodType : FoodType.values()) {
            allFoods.add(foodBuilder.createFood(foodType, coordinate));
        }

        int totalProbability = 0;
        for (Food<Type,O> food : allFoods) {
            totalProbability += food.getProbability();
        }

        Random rand = new Random();
        int random = rand.nextInt(totalProbability);
        int currentProbability = 0;
        for (Food<Type,O> food : allFoods) {
            currentProbability += food.getProbability();
            if (random < currentProbability) {
                return food;
            }
        }

        throw new RuntimeException("Food not found");
    }
    
}