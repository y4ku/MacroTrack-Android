package tech.fabricate.macrotrack.models;

/**
 * Created by y4ku on 12/15/15.
 */
public class Macro {
    private String name;
    private double protein;
    private double carbs;
    private double fats;

    public Macro(String name, double protein, double carbs, double fats) {
        this.name = name;
        this.protein = protein;
        this.carbs = carbs;
        this.fats = fats;
    }

    public String getName() {
        return name;
    }

    public double getProtein() {
        return protein;
    }

    public double getCarbs() {
        return carbs;
    }

    public double getFats() {
        return fats;
    }
}
