package csulb.edu.pickup;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.res.Configuration;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.ActionBar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class MainActivity extends ActionBarActivity {

    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;

    private CharSequence mDrawerTitle;
    private CharSequence mTitle;
    CustomDrawerAdapter adapter;

    private User thisUser;

    List<DrawerItem> dataList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initializing
        dataList = new ArrayList<DrawerItem>();
        mTitle = mDrawerTitle = getTitle();
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);

        //mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow,
          //      GravityCompat.START);

        // Add Drawer Item to dataList
        dataList.add(new DrawerItem("Map", R.drawable.map_icon));
        dataList.add(new DrawerItem("Create Event", R.drawable.plus_icon));
        dataList.add(new DrawerItem("View Profile", R.drawable.profile_icon));
        dataList.add(new DrawerItem("Calendar", R.drawable.calendar_icon));
        dataList.add(new DrawerItem("Friends", R.drawable.friends_icon));
        dataList.add(new DrawerItem("Edit Settings", R.drawable.settings_icon));
        dataList.add(new DrawerItem("Logout", R.drawable.logout_icon));


        adapter = new CustomDrawerAdapter(this, R.layout.custom_drawer_item,
                dataList);

        mDrawerList.setAdapter(adapter);

        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.drawer_open,  R.string.drawer_close) {
            public void onDrawerClosed(View view) {

                getSupportActionBar().setTitle(mTitle);
                invalidateOptionsMenu(); // creates call to
                // onPrepareOptionsMenu()
            }

            public void onDrawerOpened(View drawerView) {
                getSupportActionBar().setTitle(mDrawerTitle);
                invalidateOptionsMenu(); // creates call to
                // onPrepareOptionsMenu()
            }
        };

        mDrawerLayout.addDrawerListener(mDrawerToggle);

        if (savedInstanceState == null) {
            SelectItem(0);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public void SelectItem(int possition) {

        Fragment fragment = null;
        Bundle args = new Bundle();
        switch (possition) {
            case 0:
                fragment = new MapsFragment();
                fragmentSetUp(fragment);

                break;
            case 1:
                fragment = new CreateEventFragment();
                fragmentSetUp(fragment);

                break;
            case 2:
                fragment = new UserProfileFragment();
                fragmentSetUp(fragment);

                break;
            case 3:
                fragment = new CalendarFragment();
                fragmentSetUp(fragment);

                break;
            case 4://friends
                fragment = new FriendsFragment();
                fragmentSetUp(fragment);

                break;
            case 5:
                fragment = new EditSettingsFragment();
                fragmentSetUp(fragment);

                break;
            case 6://logout
<<<<<<< HEAD
                Bundle b = new Bundle();
                b.putParcelable("USER", thisUser);
                Intent myIntent = new Intent(getApplicationContext(), LoginActivity.class);
                myIntent.putExtras(b);
=======
                // DO NOT SEND BUNDLE WITH USER SINCE LOGGING OUT
                Intent myIntent = new Intent(getApplicationContext(), LoginActivity.class);
>>>>>>> origin/master
                startActivityForResult(myIntent, 0);

                break;

        }

<<<<<<< HEAD
//        fragment.setArguments(args);
//        FragmentManager frgManager = getFragmentManager();
//        frgManager.beginTransaction().replace(R.id.content_frame, fragment)
//                .addToBackStack( "tag" )
//                .commit();

        mDrawerList.setItemChecked(possition, true);
        setTitle(dataList.get(possition).getItemName());
        mDrawerLayout.closeDrawer(mDrawerList);

=======
        mDrawerList.setItemChecked(possition, true);
        setTitle(dataList.get(possition).getItemName());
        mDrawerLayout.closeDrawer(mDrawerList);
    }

    /**
     * Erwin added this for the Case 0 through 5 to call because Case 6 doesn't need this inorder to logout.
     * @param fragment
     */
    public void fragmentSetUp(Fragment fragment) {
        Bundle args = new Bundle();
        fragment.setArguments(args);
        FragmentManager frgManager = getFragmentManager();
        frgManager.beginTransaction().replace(R.id.content_frame, fragment)
                .addToBackStack( "tag" )
                .commit();
>>>>>>> origin/master
    }

    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        getSupportActionBar().setTitle(title);
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
        // Pass any configuration change to the drawer toggles
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // The action bar home/up action should open or close the drawer.
        // ActionBarDrawerToggle will take care of this.
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return false;
    }

    private class DrawerItemClickListener implements
            ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position,
                                long id) {
            SelectItem(position);

        }
    }

    @Override
    public void onBackPressed() {

        int count = getFragmentManager().getBackStackEntryCount();

        if (count == 1) {
            super.onBackPressed();
            //additional code
        } else {
            getFragmentManager().popBackStack();
        }
    }

    /**
     * Erwin added this for the Case 0 through 5 to call because Case 6 doesn't need this inorder to logout.
     * @param fragment
     */
    public void fragmentSetUp(Fragment fragment) {
        Bundle args = new Bundle();
        fragment.setArguments(args);
        FragmentManager frgManager = getFragmentManager();
        frgManager.beginTransaction().replace(R.id.content_frame, fragment)
                .addToBackStack( "tag" )
                .commit();
    }

}