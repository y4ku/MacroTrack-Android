package tech.fabricate.macrotrack.rest.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by y4ku on 11/27/15.
 */
public class Ingredient extends BaseModel implements Parcelable {
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

    // Parcelling part
    public Ingredient(Parcel in) {
        this.food_description = in.readString();
        this.protein = in.readDouble();
        this.carbs = in.readDouble();
        this.fats = in.readDouble();
        this.weight = in.readDouble();
        this.unit = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.food_description);
        dest.writeDouble(this.protein);
        dest.writeDouble(this.carbs);
        dest.writeDouble(this.fats);
        dest.writeDouble(this.weight);
        dest.writeString(this.unit);
    }
    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Ingredient createFromParcel(Parcel in) {
            return new Ingredient(in);
        }

        public Ingredient[] newArray(int size) {
            return new Ingredient[size];
        }
    };
}
