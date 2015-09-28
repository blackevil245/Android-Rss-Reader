package jamesnguyen.newzyv2.Activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import jamesnguyen.newzyv2.Fragments.RssFragment;
import jamesnguyen.newzyv2.R;

public class Main extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;
    private String[] mDrawerListItems;
    private RssFragment currentRssFragment;
    private boolean backPressedOnce = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /////// INIT VIEWS ///////
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        mDrawerList = (ListView) findViewById(android.R.id.list);

        /////// INIT TOOLBAR ///////
        EditText app_name = (EditText) findViewById(R.id.search_bar);

        setSupportActionBar(toolbar);
        assert getSupportActionBar() != null;

        /////// SETUP DRAWER ////////
        mDrawerListItems = getResources().getStringArray(R.array.drawer_list);
        mDrawerList.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, mDrawerListItems));
        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        replaceFragment("http://www.wired.com/category/gear/feed/");
                        Toast.makeText(Main.this, "Tech fragment", Toast.LENGTH_SHORT).show();
                        break;
                    case 1:
                        replaceFragment("http://www.wired.com/category/science/feed/");
                        Toast.makeText(Main.this, "Science fragment", Toast.LENGTH_SHORT).show();
                        break;
                    case 2:
                        replaceFragment("http://www.wired.com/category/design/feed/");
                        Toast.makeText(Main.this, "Design fragment", Toast.LENGTH_SHORT).show();
                        break;
                }

                mDrawerLayout.closeDrawer(mDrawerList);
            }
        });

        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close) {
            public void onDrawerOpened(View v) {
                super.onDrawerOpened(v);
                invalidateOptionsMenu();
                syncState();
            }

            public void onDrawerClosed(View v) {
                super.onDrawerClosed(v);
                invalidateOptionsMenu();
                syncState();
            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);

        /////// SETUP TOOLBAR /////////
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        mDrawerToggle.syncState();

        /////// START RSS FRAGMENTS //////
        if (savedInstanceState == null) {
            addWelcomeFragment();
        }
    }

    private void replaceFragment(String link) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        RssFragment newFragment = new RssFragment(link);
        transaction.remove(currentRssFragment);
        transaction.add(R.id.fragment_container, newFragment);
        currentRssFragment = newFragment;
        transaction.commit();
    }

    private void addWelcomeFragment() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        RssFragment fragmentWelcome = new RssFragment("http://www.wired.com/category/photo/feed/");
        transaction.add(R.id.fragment_container, fragmentWelcome);
        currentRssFragment = fragmentWelcome;
        transaction.commit();
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
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
            case android.R.id.home:
                if (mDrawerLayout.isDrawerOpen(mDrawerList)) {
                    mDrawerLayout.closeDrawer(mDrawerList);
                } else {
                    mDrawerLayout.openDrawer(mDrawerList);
                }
                return true;
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

    @Override
    public void onBackPressed() {
        if (backPressedOnce) {
            super.onBackPressed();
            return;
        }

        backPressedOnce = true;

        Toast.makeText(Main.this, "Press BACK again to exit!", Toast.LENGTH_SHORT).show();
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                backPressedOnce = false;
            }
        }, 2000);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean("fragment_added", true);
    }
}