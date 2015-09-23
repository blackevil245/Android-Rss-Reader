package jamesnguyen.newzyv2;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;

public class Main extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /////// SETUP TOOLBAR ///////
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        EditText app_name = (EditText) findViewById(R.id.search_bar);

        setSupportActionBar(toolbar);
        toolbar.setTitle("Newzy");

        /////// SETUP RECYCLER VIEW /////
        RecyclerView rv = (RecyclerView) findViewById(R.id.feed);
        rv.setHasFixedSize(true);
        LinearLayoutManager rvManager = new LinearLayoutManager(this);
        rv.setLayoutManager(rvManager);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.action_search:
//                openSearch();
                return true;
            case R.id.action_settings:
//                openSettings();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}