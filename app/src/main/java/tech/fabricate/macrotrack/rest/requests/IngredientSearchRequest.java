package tech.fabricate.macrotrack.rest.requests;

/**
 * Created by y4ku on 11/27/15.
 */
public class IngredientSearchRequest {
    private String ingredientString;

    public IngredientSearchRequest(String ingredientString) {
        this.ingredientString = ingredientString;
    }

    public String getIngredientString() {
        return ingredientString;
    }
}
