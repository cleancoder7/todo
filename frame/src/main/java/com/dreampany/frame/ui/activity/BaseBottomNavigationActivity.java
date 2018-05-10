package com.dreampany.frame.ui.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.view.MenuItem;

public abstract class BaseBottomNavigationActivity extends BaseMenuActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    private int currentNavId;

    protected int getNavigationViewId() {
        return 0;
    }

    protected int getDefaultSelectedNavItemId() {
        return 0;
    }

    protected abstract void onNavigationItem(int navItemId);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        fireOnStartUi = false;
        super.onCreate(savedInstanceState);

        final BottomNavigationView navigationView = findViewById(getNavigationViewId());
        if (navigationView != null) {
            navigationView.setOnNavigationItemSelectedListener(this);
        }
        setSelectedItem(getDefaultSelectedNavItemId());
        onStartUi(savedInstanceState);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int targetNavId = item.getItemId();
        if (targetNavId != currentNavId) {
            onNavigationItem(targetNavId);
            currentNavId = targetNavId;
            return true;
        }
        return false;
    }

    public void setSelectedItem(final int navItemId) {
        if (navItemId != 0) {
            final BottomNavigationView navigationView = findViewById(getNavigationViewId());
            if (navigationView != null) {
                navigationView.post(new Runnable() {
                    @Override
                    public void run() {
                        navigationView.setSelectedItemId(navItemId);
                    }
                });
            }
        }
    }
}
