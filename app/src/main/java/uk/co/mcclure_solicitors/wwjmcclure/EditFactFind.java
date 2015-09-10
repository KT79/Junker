package uk.co.mcclure_solicitors.wwjmcclure;


import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.FragmentManager;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.DrawerLayout;
import android.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class EditFactFind extends FragmentActivity implements NavigationDrawerFragment.NavigationDrawerCallbacks {

    public static final int RESULT_DELETE = -500;
    private boolean isInEditMode = true;
    private boolean isAddingFactFind = true;
    private NavigationDrawerFragment mNavigationDrawerFragment;
    private CharSequence mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_factfind);

        final Button saveButton = (Button)findViewById(R.id.saveButton);
        final Button cancelButton = (Button)findViewById(R.id.cancelButton);
        final EditText titleEditText = (EditText)findViewById(R.id.titleEditText);
        //final EditText factFindEditText = (EditText)findViewById(R.id.factFindEditText);
        final TextView dateTextView = (TextView)findViewById(R.id.dateTextView);
        final Button nextButton =(Button) findViewById(R.id.nextButton);

        //Create fragment and give it an argument for the selected article
        secA_pg1 iniSecFrag = new secA_pg1();
        Bundle args = new Bundle();
        args.putInt(secA_pg1.ARG_INDEX, 1);
        iniSecFrag.setArguments(args);

        FragmentTransaction initialTransaction = getFragmentManager().beginTransaction();

        // Replace whatever is in the fragment_container view with this fragment,
        // and add the transaction to the back stack so the user can navigate back
        initialTransaction.replace(R.id.fragment_container, iniSecFrag);
        initialTransaction.addToBackStack(null);

        //Commit the transaction
        initialTransaction.commit();

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));

        Serializable extra = getIntent().getSerializableExtra("FactFind");
        if(extra != null)
        {
            FactFind factFind = (FactFind) extra;
            titleEditText.setText(factFind.getTitle());
          //  factFindEditText.setText(factFind.getFactFindTitle());

            DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
            String date = dateFormat.format(factFind.getDate());

            dateTextView.setText(date);

            isInEditMode = false;
            titleEditText.setEnabled(false);
          //  factFindEditText.setEnabled(false);
            saveButton.setText("Edit");

            isAddingFactFind = false;

        }

        cancelButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                setResult(RESULT_CANCELED, new Intent());
                finish();
            }
        });

        nextButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                //secA_pg1 secFrag = (secA_pg1) getFragmentManager().findFragmentById(R.id.Sec_A_pg1_fragment);
                //Create fragment and give it an argument for the selected article
                secA_pg2 newSecFrag = new secA_pg2();
                Bundle args = new Bundle();
                args.putInt(secA_pg2.ARG_INDEX, 2);
                newSecFrag.setArguments(args);

                FragmentTransaction transaction = getFragmentManager().beginTransaction();

                // Replace whatever is in the fragment_container view with this fragment,
                // and add the transaction to the back stack so the user can navigate back
                transaction.replace(R.id.fragment_container, newSecFrag);
                transaction.addToBackStack(null);

                //Commit the transaction
                transaction.commit();
            }
        });

        saveButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {


                if(isInEditMode)
                {
                    Intent returnIntent = new Intent();
                    FactFind factFind = new FactFind(titleEditText.getText().toString(),Calendar.getInstance().getTime());
                    returnIntent.putExtra("FactFind", factFind);
                    setResult(RESULT_OK, returnIntent);
                    finish();

                }
                else
                {
                    isInEditMode = true;
                    saveButton.setText("Save");
                    titleEditText.setEnabled(true);
                   // factFindEditText.setEnabled(true);
                }

            }
        });
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.are_you_sure_you_want_to_delete_this_fact_find_it_can_t_be_undone_);
        builder.setTitle("Confirm Delete");
        builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent returnIntent = new Intent();

                setResult(RESULT_DELETE, returnIntent);
                finish();

            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub

            }
        });

        builder.create().show();
        return true;

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.nav_drawer, menu);
            restoreActionBar();
            return true;
        }
        else {
            //return super.onCreateOptionsMenu(menu);
            getMenuInflater().inflate(R.menu.action_menu, menu);
            return true;
        }
    }
    //@Override
    public void onSelectedFragChanged(int index) {
        FragmentManager fragmentManager = getFragmentManager();
        secA_pg1 secA_pg1 = (secA_pg1) fragmentManager.findFragmentById(R.id.Sec_A_pg1_fragment);

        secA_pg1.setSectionTitle(index);
    }

    public void restoreActionBar() {
        ActionBar actionBar = getActionBar();
        //actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {

    }
}
