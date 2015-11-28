package tech.fabricate.macrotrack;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;
import tech.fabricate.macrotrack.rest.ServiceGenerator;
import tech.fabricate.macrotrack.rest.model.Ingredient;
import tech.fabricate.macrotrack.rest.requests.LoginRequest;
import tech.fabricate.macrotrack.rest.service.IngredientSearchService;

public class RecipeActivity extends AppCompatActivity {

    ArrayList<String> recipeItems=new ArrayList<String>();
    ArrayAdapter<String> adapter;

    ListView recipeList;

    private EditText searchText;
    private Button searchButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);

        searchText = (EditText) findViewById(R.id.searchText);
        searchButton = (Button) findViewById(R.id.searchButton);

        recipeList = (ListView)findViewById(R.id.recipeList);

        adapter=new ArrayAdapter<String>(RecipeActivity.this,
                android.R.layout.simple_list_item_1,
                recipeItems);
        recipeList.setAdapter(adapter);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String searchTerm = searchText.getText().toString();

                final ProgressDialog progressDialog = new ProgressDialog(RecipeActivity.this,
                        R.style.AppTheme_Dark_Dialog);
                progressDialog.setIndeterminate(true);
                progressDialog.setMessage("Search...");
                progressDialog.show();

                SharedPreferences sharedPreferences = PreferenceManager
                        .getDefaultSharedPreferences(RecipeActivity.this);

                Log.d("AUTHORIZATION", sharedPreferences.getString("jwt", "Not Authenticated"));

                IngredientSearchService service = ServiceGenerator.createService(IngredientSearchService.class, sharedPreferences.getString("jwt", "Not Authenticated"));
                Call<List<Ingredient>> callService = service.searchIngredients(searchTerm);
                callService.enqueue(new Callback<List<Ingredient>>() {
                    @Override
                    public void onResponse(Response<List<Ingredient>> response, Retrofit retrofit) {
                        if (response.isSuccess()) {
                            List<Ingredient> ingredients = response.body();
                            recipeItems.clear();
                            for(Ingredient ingredient : ingredients) {
                                recipeItems.add(ingredient.getFood_description());
                            }
                            adapter.notifyDataSetChanged();
                        } else {
                            // error response, no access to resource?
                        }
                        progressDialog.dismiss();
                    }

                    @Override
                    public void onFailure(Throwable t) {
                        //something went completely south (like no internet connection)
                        Log.d("Error", t.getMessage());
                        progressDialog.dismiss();
                    }
                });
            }
        });

    }
}
