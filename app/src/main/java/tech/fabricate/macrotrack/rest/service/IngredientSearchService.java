package tech.fabricate.macrotrack.rest.service;

import java.util.List;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Path;
import tech.fabricate.macrotrack.rest.model.Ingredient;

/**
 * Created by y4ku on 11/27/15.
 */
public interface IngredientSearchService {
    @GET("search/ingredients/{ingredient}")
    Call<List<Ingredient>> searchIngredients(@Path("ingredient") String ingredient);
}
