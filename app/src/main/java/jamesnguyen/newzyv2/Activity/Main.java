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
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import jamesnguyen.newzyv2.Fragments.RssFragment;
import jamesnguyen.newzyv2.Model.SubscriptionManager;
import jamesnguyen.newzyv2.R;

public class Main extends AppCompatActivity {

    private final SubscriptionManager subscriptionManager = SubscriptionManager.getInstance();
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;
    private boolean backPressedOnce = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /////// INIT VIEWS ///////
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        mDrawerList = (ListView) findViewById(android.R.id.list);

        /////// SUBSCRIPTION LIST ///////
        subscriptionManager.initList();

        /////// INIT TOOLBAR ///////
        final EditText app_name = (EditText) findViewById(R.id.search_bar);
        final TextView app_title = (TextView) findViewById(R.id.app_title);

        setSupportActionBar(toolbar);
        assert getSupportActionBar() != null;

        /////// SETUP DRAWER ////////
        String[] mDrawerListItems = getResources().getStringArray(R.array.drawer_list);
        mDrawerList.setAdapter(new ArrayAdapter<>(this, R.layout.drawer_item, R.id.menu_item_title, mDrawerListItems));
        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        replaceFragment(SubscriptionManager.getAll(), true, false);
                        app_title.setText("All Newzy");
                        break;
                    case 1:
                        replaceFragment(subscriptionManager.getLink(0), false, false);
                        app_title.setText("Tech");
                        break;
                    case 2:
                        replaceFragment(subscriptionManager.getLink(1), false, false);
                        app_title.setText("Science");
                        break;
                    case 3:
                        replaceFragment(subscriptionManager.getLink(2), false, false);
                        app_title.setText("Design");
                        break;
                    case 4:
                        replaceFragment(subscriptionManager.getLink(3), false, false);
                        app_title.setText("PC World");
                        break;
                    case 5:
                        replaceFragment(subscriptionManager.getLink(4), false, false);
                        app_title.setText("Dota 2");
                        break;
                    case 6:
                        replaceFragment(subscriptionManager.getLink(5), false, false);
                        app_title.setText("CS:GO");
                        break;
                    case 7:
                        replaceFragment(subscriptionManager.getLink(6), false, false);
                        app_title.setText("onGamers");
                        break;
                    default:
                        replaceFragment(subscriptionManager.getLink(position - 1), false, false);
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
            replaceFragment(SubscriptionManager.getAll(), true, true);
            app_title.setText("All Newzy");
        }
    }

    private void replaceFragment(ArrayList<String> links, boolean showAll, boolean add) {
        Bundle bundle = new Bundle();
        bundle.putStringArrayList(RssFragment.LINKS, links);
        bundle.putBoolean(RssFragment.ALL, showAll);

        RssFragment newFragment = new RssFragment();
        newFragment.setArguments(bundle);

        if (add) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.add(R.id.fragment_container, newFragment);
            transaction.commit();
        } else {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_container, newFragment);
            transaction.addToBackStack(null);
            transaction.commit();
        }
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