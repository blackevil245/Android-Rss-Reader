package jamesnguyen.newzyv2.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
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
import jamesnguyen.newzyv2.Model.ItemCache;
import jamesnguyen.newzyv2.Model.RssItem;
import jamesnguyen.newzyv2.Model.SubscriptionManager;
import jamesnguyen.newzyv2.R;
import jamesnguyen.newzyv2.RSS_Service.RssService;

public class Main extends AppCompatActivity {

    private final ResultReceiver resultReceiver = new ResultReceiver(new Handler()) {
        @SuppressWarnings("unchecked")
        @Override
        protected void onReceiveResult(int resultCode, Bundle resultData) {
            ArrayList<ArrayList<RssItem>> receivedPackage = (ArrayList<ArrayList<RssItem>>) resultData.getSerializable(RssService.ITEMS);
            assert receivedPackage != null;
            ItemCache.getInstance().setTempCache(receivedPackage);
        }
    };
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;
    private boolean backPressedOnce = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // SUBSCRIPTION LIST
        SubscriptionManager.getInstance().initList();

        // INIT DATA CACHE
        startService();

        // INIT VIEWS
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        mDrawerList = (ListView) findViewById(android.R.id.list);

        // INIT TOOLBAR
        final EditText app_name = (EditText) findViewById(R.id.search_bar);
        final TextView app_title = (TextView) findViewById(R.id.app_title);

        setSupportActionBar(toolbar);
        assert getSupportActionBar() != null;

        // SETUP DRAWER
        String[] mDrawerListItems = getResources().getStringArray(R.array.drawer_list);
        mDrawerList.setAdapter(new ArrayAdapter<>(this, R.layout.drawer_item, R.id.menu_item_title, mDrawerListItems));
        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        replaceFragment(ItemCache._ALL_LINK, false);
                        app_title.setText("All Newzy");
                        break;
                    case 1:
                        replaceFragment(ItemCache._1_LINK, false);
                        app_title.setText("Tech");
                        break;
                    case 2:
                        replaceFragment(ItemCache._2_LINK, false);
                        app_title.setText("Science");
                        break;
                    case 3:
                        replaceFragment(ItemCache._3_LINK, false);
                        app_title.setText("Design");
                        break;
                    case 4:
                        replaceFragment(ItemCache._4_LINK, false);
                        app_title.setText("PC World");
                        break;
                    case 5:
                        replaceFragment(ItemCache._5_LINK, false);
                        app_title.setText("Dota 2");
                        break;
                    case 6:
                        replaceFragment(ItemCache._6_LINK, false);
                        app_title.setText("CS:GO");
                        break;
                    case 7:
                        replaceFragment(ItemCache._7_LINK, false);
                        app_title.setText("onGamers");
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

        // SETUP TOOLBAR
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        mDrawerToggle.syncState();

        // START RSS FRAGMENTS
        if (savedInstanceState == null) {
            replaceFragment(ItemCache._ALL_LINK, true);
            app_title.setText("All Newzy");
        }
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    private void startService() {
        Intent intent = new Intent(Main.this, RssService.class);
        intent.putExtra(RssService.RECEIVER, resultReceiver);
        Main.this.startService(intent);
    }

    private void replaceFragment(int request_id, boolean add) {
        Bundle bundle = new Bundle();
        bundle.putInt(RssFragment.ID, request_id);

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