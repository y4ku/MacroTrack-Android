package tech.fabricate.macrotrack;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import tech.fabricate.macrotrack.rest.model.Ingredient;

public class RecipeActivity extends AppCompatActivity {

    ArrayList<String> ingredientDescriptions=new ArrayList<String>();
    ArrayAdapter<String> adapter;
    ListView ingredientList;

    private Button addIngredientsButton;
    private ArrayList<Ingredient> ingredients = new ArrayList<>();

    private TextView proteinAmount;
    private TextView carbAmount;
    private TextView fatAmount;

    static final int ADD_INGREDIENT = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);

        proteinAmount = (TextView) findViewById(R.id.proteinAmount);
        carbAmount = (TextView) findViewById(R.id.carbAmount);
        fatAmount = (TextView) findViewById(R.id.fatAmount);

        ingredientList = (ListView)findViewById(R.id.addedIngredientList);
        adapter = new ArrayAdapter<String>(RecipeActivity.this,
                        android.R.layout.simple_list_item_1,
                        ingredientDescriptions);
        ingredientList.setAdapter(adapter);


        addIngredientsButton = (Button) findViewById(R.id.addIngredientsButton);
        addIngredientsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RecipeActivity.this, IngredientActivity.class);
                startActivityForResult(intent, ADD_INGREDIENT);
            }
        });
        updateMacroCount();
    }

    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent data) {
        if (requestCode == ADD_INGREDIENT) {
            if (resultCode == RESULT_OK) {
                Bundle extras = data.getExtras();
                if (extras != null) {
                    Ingredient ingredient = extras.getParcelable("ingredient");
                    ingredients.add(ingredient);
                    Toast.makeText(RecipeActivity.this, "You added an ingredient:" + ingredient, Toast.LENGTH_LONG).show();
                    Log.d("activity result", ingredient.getFood_description());
                    ingredientDescriptions.add(ingredient.getFood_description());
                    adapter.notifyDataSetChanged();
                    updateMacroCount();
                } else {
                    Log.d("activity result", "Extras was null");
                }
            }
        }
    }

    protected void updateMacroCount() {
        double protein = 0, carbs = 0, fats = 0;
        for(Ingredient ingredient : ingredients) {
            protein += ingredient.getProtein();
            carbs += ingredient.getCarbs();
            fats += ingredient.getFats();
        }
        proteinAmount.setText(String.valueOf(protein));
        carbAmount.setText(String.valueOf(carbs));
        fatAmount.setText(String.valueOf(fats));
    }
}
