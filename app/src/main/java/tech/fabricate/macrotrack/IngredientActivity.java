package tech.fabricate.macrotrack;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
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
import tech.fabricate.macrotrack.rest.service.IngredientSearchService;

public class IngredientActivity extends AppCompatActivity {

    ArrayList<String> recipeItems=new ArrayList<String>();
    ArrayAdapter<String> adapter;

    ListView recipeList;
    ArrayList<Ingredient> ingredientsArrayList = new ArrayList<Ingredient>();

    private EditText searchText;
    private Button searchButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingredient);

        searchText = (EditText) findViewById(R.id.searchText);
        searchButton = (Button) findViewById(R.id.searchButton);

        recipeList = (ListView)findViewById(R.id.recipeList);

        adapter=new ArrayAdapter<String>(IngredientActivity.this,
                android.R.layout.simple_list_item_1,
                recipeItems);
        recipeList.setAdapter(adapter);

        addListViewOnClickListeners();

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String searchTerm = searchText.getText().toString();

                final ProgressDialog progressDialog = new ProgressDialog(IngredientActivity.this,
                        R.style.AppTheme_Dark_Dialog);
                progressDialog.setIndeterminate(true);
                progressDialog.setMessage("Search...");
                progressDialog.show();

                SharedPreferences sharedPreferences = PreferenceManager
                        .getDefaultSharedPreferences(IngredientActivity.this);

                Log.d("AUTHORIZATION", sharedPreferences.getString("jwt", "Not Authenticated"));

                IngredientSearchService service = ServiceGenerator.createService(IngredientSearchService.class, sharedPreferences.getString("jwt", "Not Authenticated"));
                Call<List<Ingredient>> callService = service.searchIngredients(searchTerm);
                callService.enqueue(new Callback<List<Ingredient>>() {
                    @Override
                    public void onResponse(Response<List<Ingredient>> response, Retrofit retrofit) {
                        if (response.isSuccess()) {
                            List<Ingredient> ingredients = response.body();
                            recipeItems.clear();
                            ingredientsArrayList.clear();
                            for(Ingredient ingredient : ingredients) {
                                recipeItems.add(ingredient.getFood_description());
                                ingredientsArrayList.add(ingredient);
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

    protected void addListViewOnClickListeners() {
        recipeList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();
                intent.putExtra("ingredient", ingredientsArrayList.get(position));
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }

}
