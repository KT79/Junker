package uk.co.mcclure_solicitors.wwjmcclure;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.internal.widget.AdapterViewCompat;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity  {
    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
  //  private NavigationDrawerFragment mNavigationDrawerFragment;

    /**
     * Used to store the last screen title. .
     */
    private CharSequence mTitle;
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();

        factFinds.remove(info.position);
        populateList();

        return true;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.context_menu, menu);

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == RESULT_CANCELED)
        {
            return;
        }

        if(resultCode == EditFactFind.RESULT_DELETE)
        {
            factFinds.remove(editingFactFindId);
            editingFactFindId = -1;
            populateList();
        }

        Serializable extra = data.getSerializableExtra("FactFind");
        if(extra != null)
        {
            FactFind newFactFind = (FactFind)extra;
            if(editingFactFindId > -1)
            {
                factFinds.set(editingFactFindId, newFactFind);
                editingFactFindId = -1;
            }
            else
            {
                factFinds.add(newFactFind);
            }
            populateList();
        }
    }
    private List<FactFind> factFinds = new ArrayList<FactFind>();
    private ListView factFindListView;
    private int editingFactFindId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));
        */
        // Set up the List view
        factFindListView = (ListView)findViewById(R.id.ListFactFinds);

        factFindListView.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapter, View view, int itemNumber,
                                    long id) {
                Intent editFactFindIntent = new Intent(view.getContext(), EditFactFind.class);
                editFactFindIntent.putExtra("FactFind", factFinds.get(itemNumber));
                editingFactFindId = itemNumber;
                startActivityForResult(editFactFindIntent, 1);

            }
        });

        registerForContextMenu(factFindListView);
        //TODO This will need to be replaced with an iteration to go through the DB(?) to find all local fact finds
        factFinds.add(new FactFind("Mr Smith", new Date()));
        factFinds.add(new FactFind("Miss Jones", new Date()));
        factFinds.add(new FactFind("Mrs McDonald", new Date()));
        factFinds.add(new FactFind("Mr Todd", new Date()));
        factFinds.add(new FactFind("Mr and Mrs Walker", new Date()));

        populateList();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
      // if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
        //    getMenuInflater().inflate(R.menu.nav_drawer, menu);
          //  restoreActionBar();
          //  return true;
       // }
       //else {
          //  return super.onCreateOptionsMenu(menu);
            getMenuInflater().inflate(R.menu.action_menu, menu);
            return true;
        //}
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        //TODO this should be replaced with a method at class level which can be called


        Intent editFactFindIntent = new Intent(this, EditFactFind.class);
        startActivityForResult(editFactFindIntent, 1);

        switch (item.getItemId()) {
            case R.id.action_search:
                openSearch();
                return true;
            case R.id.action_settings:
                openSettings();
                return true;
            case R.id.new_factfind:
                addFactFind();
                return true;
            case R.id.navigation_drawer:


            default:
                return super.onOptionsItemSelected(item);

        }

    }

    public void openSearch() {
        //TODO this needs to be added at a later date, non-essential
    }

    private void openSettings() {
        //TODO this needs some thought as to what will go in here
    }

    private boolean addFactFind(){

        Intent factFindIntent = new Intent(this, EditFactFind.class);
        startActivity(factFindIntent);
        return true;

    }

    private void populateList() {
        List<String> values = new ArrayList<String>();

        for(FactFind factFind : factFinds)
        {
            values.add(factFind.getTitle());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1,
                values);

        factFindListView.setAdapter(adapter);
    }


    public void onSectionAttached(int number) {
        switch (number) {
            case 1:
                mTitle = getString(R.string.title_section1);
                break;
            case 2:
                mTitle = getString(R.string.title_section2);
                break;
            case 3:
                mTitle = getString(R.string.title_section3);
                break;
            case 4:
                mTitle = getString(R.string.title_section4);
                break;
            case 5:
                mTitle = getString(R.string.title_section5);
                break;
        }
    }

    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
       // actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }

}
