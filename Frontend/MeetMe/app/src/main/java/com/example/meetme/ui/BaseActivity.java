package com.example.meetme.ui;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.view.MenuItem;

import com.example.meetme.R;
import com.google.android.material.navigation.NavigationBarView;

public abstract class BaseActivity extends AppCompatActivity implements NavigationBarView.OnItemSelectedListener {

    /**
     * The standard bottom navigation bar to be displayed on each page.
     */
    protected NavigationBarView navigationView;

    /**
     * Creates and initializes the navigation bar.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentViewId());

        navigationView = (NavigationBarView) findViewById(R.id.navigation);
        navigationView.setOnItemSelectedListener(this);

    }

    /**
     *
     * @return the id of the layout
     */
    protected abstract int getContentViewId();

    /**
     * Updates the navigation bar
     */
    @Override
    protected void onStart() {
        super.onStart();
        updateNavigationBarState();
    }

    /**
     * Remove inter-activity transition to avoid screen tossing on tapping bottom navigation items
     */
     @Override
    public void onPause() {
        super.onPause();
        overridePendingTransition(0, 0);
    }

    /**
     * Determines which page to display based on what menuItem was selected
     * @param item the item selected
     * @return true on success
     */
    @Override
    public boolean onNavigationItemSelected (@NonNull MenuItem item) {
        navigationView.postDelayed(() -> {
            int itemId = item.getItemId();
            if (itemId == R.id.action_dashboard) {
                startActivity(new Intent(this, DashboardPage.class));
            } else if (itemId == R.id.action_register) {
                startActivity(new Intent(this, RegisterPage.class));
            } else if (itemId == R.id.action_createMeeting) {
                startActivity(new Intent(this, CreateMeetingPage.class));
            } else if (itemId == R.id.action_search){
                startActivity(new Intent(this, SearchPage.class));
            }
            //finish();
        }, 300);
        return true;
    }

    /**
     * Updates the navigation bar's display
     */
    private void updateNavigationBarState() {
        int actionId = getNavigationMenuItemId();
        selectBottomNavigationBarItem(actionId);
    }

    /**
     * Determines which item to display as selected
     * @param itemId
     */
    void selectBottomNavigationBarItem(int itemId) {
        MenuItem item = navigationView.getMenu().findItem(itemId);
        item.setChecked(true);
    }

    /**
     *
     * @return which layout needs to display when clicked
     */
    abstract int getLayoutId();

    /**
     * Which menu item selected and change the state of that menu item
     * @return
     */
    abstract int getNavigationMenuItemId();
}
