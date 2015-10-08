package jamesnguyen.newzyv2.Activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
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
import android.widget.ListView;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;

import jamesnguyen.newzyv2.Application.MyApplication;
import jamesnguyen.newzyv2.Fragments.RssFragment;
import jamesnguyen.newzyv2.Fragments.SettingsFragment;
import jamesnguyen.newzyv2.R;
import jamesnguyen.newzyv2.RSS_Service.SubscriptionManager;
import jamesnguyen.newzyv2.Utilities.ConnectionManager;
import jamesnguyen.newzyv2.Utilities.SettingsManager;

public class Main extends AppCompatActivity {

    public static Activity mainActivity;
    private static DrawerLayout mDrawerLayout;
    private static ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;
    private boolean backPressedOnce = false;

    public static DrawerLayout getDrawerLayout() {
        return mDrawerLayout;
    }

    public static ListView getDrawerList() {
        return mDrawerList;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getApplicationContext();
        mainActivity = this;

        // LOAD SETTINGS
        final File file_setting = new File(getFilesDir() + File.separator + "Newzy_config");
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (file_setting.exists()) {
                    SettingsManager.getInstance().readSettingsFile();
                }
            }
        }).start();


        // INIT SUBSCRIPTION LIST
        SubscriptionManager.getInstance().initList();

        // INIT VIEWS
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        mDrawerList = (ListView) findViewById(android.R.id.list);

        // INIT TOOLBAR
        final TextView app_title = (TextView) findViewById(R.id.app_title);

        setSupportActionBar(toolbar);
        assert getSupportActionBar() != null;

        // SETUP DRAWER
        String[] mDrawerListItems = getResources().getStringArray(R.array.drawer_list);
        mDrawerList.setAdapter(new ArrayAdapter<>(this, R.layout.drawer_item, R.id.menu_item_title, mDrawerListItems));
        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ArrayList<Integer> requestID;
                switch (position) {
                    case 0: //All
                        requestID = new ArrayList<>();
                        for (int i = 0; i <= (SubscriptionManager.getInstance().getAll().size() - 1); i++) {
                            requestID.add(i);
                        }
                        replaceFragment(requestID, false);
                        app_title.setText(SubscriptionManager.getInstance().getTitle(position));
                        break;
                    case 1: //Business and Finance
                        requestID = new ArrayList<>();
                        requestID.add(0);
                        replaceFragment(requestID, false);
                        app_title.setText(SubscriptionManager.getInstance().getTitle(position));
                        break;
                    case 2: //Startup and Funding
                        requestID = new ArrayList<>();
                        requestID.add(1);
                        requestID.add(2);
                        replaceFragment(requestID, false);
                        app_title.setText(SubscriptionManager.getInstance().getTitle(position));
                        break;
                    case 3: //Tech
                        requestID = new ArrayList<>();
                        requestID.add(3);
                        requestID.add(4);
                        requestID.add(5);
                        requestID.add(6);
                        replaceFragment(requestID, false);
                        app_title.setText(SubscriptionManager.getInstance().getTitle(position));
                        break;
                    case 4: //Science
                        requestID = new ArrayList<>();
                        requestID.add(7);
                        requestID.add(8);
                        requestID.add(9);
                        replaceFragment(requestID, false);
                        app_title.setText(SubscriptionManager.getInstance().getTitle(position));
                        break;
                    case 5: //Entertainment
                        requestID = new ArrayList<>();
                        requestID.add(10);
                        requestID.add(11);
                        requestID.add(12);
                        requestID.add(13);
                        replaceFragment(requestID, false);
                        app_title.setText(SubscriptionManager.getInstance().getTitle(position));
                        break;
                    case 6: //Game
                        requestID = new ArrayList<>();
                        requestID.add(14);
                        requestID.add(15);
                        replaceFragment(requestID, false);
                        app_title.setText(SubscriptionManager.getInstance().getTitle(position));
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

        // SETUP TOOLBAR
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        mDrawerToggle.syncState();

        // START RSS READER + show ALL Fragment
        if (savedInstanceState == null) {
            final ArrayList<Integer> requestID = new ArrayList<>();
            for (int i = 0; i <= (SubscriptionManager.getInstance().getAll().size() - 1); i++) {
                requestID.add(i);
            }
            final File file_storage = new File(MyApplication.getAppContext().getFilesDir() + File.separator + "NewzyData.ser");

            // Get data normally
            if (ConnectionManager.getInstance().isNetworkAvailable()) {
                SubscriptionManager.getInstance().startService();
                new Handler().postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        replaceFragment(requestID, true); // TODO fix bug on sudden exit
                    }
                }, 10000);
                app_title.setText(SubscriptionManager.getInstance().getTitle(0));
            } else {
                // Read from file which contains previously data if exists when have no internet
                if (file_storage.exists()) {
                    SubscriptionManager.getInstance().startService();
                    new Handler().postDelayed(new Runnable() {

                        @Override
                        public void run() {
                            replaceFragment(requestID, true); // TODO fix bug on sudden exit
                        }
                    }, 10000);
                    app_title.setText(SubscriptionManager.getInstance().getTitle(0));

                    // Prevent using app when there's no connection and no previously downloaded data file
                } else {
                    new AlertDialog.Builder(this)
                            .setMessage("Please open data connection for first time data loading")
                            .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    mainActivity.finish();
                                }
                            }).show();
                }
            }
        }
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SettingsManager.getInstance().writeSettingsFile();
    }

    private void replaceFragment(ArrayList<Integer> request_id, boolean add) {
        Bundle bundle = new Bundle();
        bundle.putIntegerArrayList(RssFragment.ID, request_id);

        RssFragment newFragment = new RssFragment();
        newFragment.setArguments(bundle);

        if (add) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.add(R.id.fragment_container, newFragment);
            transaction.commit();
        } else {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_container, newFragment);
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
            case R.id.action_settings:
                openSettings();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    protected void openSettings() {
        SettingsFragment newFragment = SettingsFragment.newInstance();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, newFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean("fragment_added", true);
    }

    //    @Override
//    public void onBackPressed() {
//        if (backPressedOnce) {
//            super.onBackPressed();
//            return;
//        }
//
//        backPressedOnce = true;
//
//        Toast.makeText(Main.this, "Press BACK again to exit!", Toast.LENGTH_SHORT).show();
//        new Handler().postDelayed(new Runnable() {
//
//            @Override
//            public void run() {
//                backPressedOnce = false;
//            }
//        }, 300);
//    }
}