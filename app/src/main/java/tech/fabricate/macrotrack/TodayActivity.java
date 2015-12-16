package tech.fabricate.macrotrack;

import android.content.Intent;
import android.content.res.Configuration;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import tech.fabricate.macrotrack.rest.model.Ingredient;

public class TodayActivity extends AppCompatActivity {

    private TextView firstNameHeader;
    private TextView proteinAmount;
    private TextView carbAmount;
    private TextView fatAmount;

    private ListView mDrawerList;
    private DrawerLayout mDrawerLayout;
    private ArrayAdapter<String> mAdapter;
    private ActionBarDrawerToggle mDrawerToggle;
    private String mActivityTitle;

    private Button addMacroButton;
    private Button addRecipeButton;

    static final int CREATE_RECIPE = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_today);

        mDrawerList = (ListView)findViewById(R.id.navList);
        mDrawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        mActivityTitle = getTitle().toString();

        addDrawerItems();
        setupDrawer();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        firstNameHeader = (TextView) findViewById(R.id.firstNameHeader);
        proteinAmount = (TextView) findViewById(R.id.proteinAmount);
        carbAmount = (TextView) findViewById(R.id.carbAmount);
        fatAmount = (TextView) findViewById(R.id.fatAmount);

        firstNameHeader.setText("Kuba");
        proteinAmount.setText("32");
        carbAmount.setText("50");
        fatAmount.setText("9");


        setupMacroButtons();
    }

    private void setupMacroButtons() {
        addMacroButton = (Button) findViewById(R.id.addMacroButton);
        addRecipeButton = (Button) findViewById(R.id.addRecipeButton);

        addMacroButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TodayActivity.this, RecipeActivity.class);
                startActivityForResult(intent, CREATE_RECIPE);
            }
        });

        addRecipeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TodayActivity.this, RecipeActivity.class);
                startActivityForResult(intent, CREATE_RECIPE);
            }
        });
    }

    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent data) {
        if (requestCode == CREATE_RECIPE) {
            if (resultCode == RESULT_OK) {
                Bundle extras = data.getExtras();
                if (extras != null) {
                    Ingredient ingredient = extras.getParcelable("ingredient");
                    Log.d("activity result", ingredient.getFood_description());
                } else {
                    Log.d("activity result", "Extras was null");
                }
            }
        }
    }

    private void addDrawerItems() {
        String[] navigationArray = {"Nav 1", "Nav 2", "Nav 3", "Nav 4"};
        mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, navigationArray);
        mDrawerList.setAdapter(mAdapter);

        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(TodayActivity.this, "Time for an upgrade! " + id, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setupDrawer() {
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.drawer_open, R.string.drawer_close) {

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getSupportActionBar().setTitle("Navigation");
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                getSupportActionBar().setTitle(mActivityTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };

        mDrawerToggle.setDrawerIndicatorEnabled(true);
        mDrawerLayout.setDrawerListener(mDrawerToggle);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        // Activate the navigation drawer toggle
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
