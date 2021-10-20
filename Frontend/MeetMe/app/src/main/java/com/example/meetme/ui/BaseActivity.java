package com.example.meetme.ui;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.view.MenuItem;

import com.example.meetme.R;
import com.google.android.material.navigation.NavigationBarView;

public abstract class BaseActivity extends AppCompatActivity implements NavigationBarView.OnItemSelectedListener {

    protected NavigationBarView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentViewId());

        navigationView = (NavigationBarView) findViewById(R.id.navigation);
        navigationView.setOnItemSelectedListener(this);

    }

    protected abstract int getContentViewId();

    @Override
    protected void onStart() {
        super.onStart();
        updateNavigationBarState();
    }

    // Remove inter-activity transition to avoid screen tossing on tapping bottom navigation items
    @Override
    public void onPause() {
        super.onPause();
        overridePendingTransition(0, 0);
    }

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

    private void updateNavigationBarState() {
        int actionId = getNavigationMenuItemId();
        selectBottomNavigationBarItem(actionId);
    }

    void selectBottomNavigationBarItem(int itemId) {
        MenuItem item = navigationView.getMenu().findItem(itemId);
        item.setChecked(true);
    }

    abstract int getLayoutId(); // this is to return which layout(activity) needs to display when clicked on tabs.

    abstract int getNavigationMenuItemId();//Which menu item selected and change the state of that menu item
}
