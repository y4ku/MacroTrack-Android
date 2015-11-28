package tech.fabricate.macrotrack.rest.model;

/**
 * Created by y4ku on 11/27/15.
 */
public class Ingredient extends BaseModel {
    private String food_description;
    private double protein;
    private double carbs;
    private double fats;
    private double weight;
    private String unit;

    public String getFood_description() {
        return food_description;
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

    public double getWeight() {
        return weight;
    }

    public String getUnit() {
        return unit;
    }
}
