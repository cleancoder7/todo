package com.dreampany.frame.ui.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.dreampany.frame.R;

public abstract class BaseNavigationActivity extends BaseMenuActivity implements NavigationView.OnNavigationItemSelectedListener {

    private int currentNavId;
    private ActionBarDrawerToggle toggle;
    private DrawerLayout drawerLayout;

    protected int getDrawerLayoutId() {
        return 0;
    }

    protected int getNavigationViewId() {
        return 0;
    }

    protected int getNavigationHeaderId() {
        return 0;
    }

    protected int getOpenDrawerDescRes() {
        return R.string.navigation_drawer_open;
    }

    protected int getCloseDrawerDescRes() {
        return R.string.navigation_drawer_close;
    }

    protected int getDefaultSelectedNavItemId() {
        return 0;
    }

    protected String getNavigationTitle(int navItemId) {
        return null;
    }

    protected abstract void onNavigationItem(int navItemId);

    protected abstract void onDrawerOpening();

    protected abstract void onDrawerClosing();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        fireOnStartUi = false;
        super.onCreate(savedInstanceState);
        if (toggle == null) {
            drawerLayout = findViewById(getDrawerLayoutId());
            Toolbar toolbar = findViewById(getToolbarId());

            toggle = new ActionBarDrawerToggle(
                    this,
                    drawerLayout,
                    toolbar,
                    getOpenDrawerDescRes(),
                    getCloseDrawerDescRes()) {

                @Override
                public void onDrawerStateChanged(int newState) {
                    if (newState == DrawerLayout.STATE_SETTLING) {
                        if (!isDrawerOpen()) {
                            onDrawerOpening();
                        } else {
                            onDrawerClosing();
                        }
                    }
                }
            };

            drawerLayout.addDrawerListener(toggle);
            toggle.syncState();

            NavigationView navigationView = findViewById(getNavigationViewId());
            if (navigationView != null) {
                navigationView.setNavigationItemSelectedListener(this);
                navigationView.setCheckedItem(getDefaultSelectedNavItemId());

                Menu navMenu = navigationView.getMenu();
                if (navMenu != null) {
                    navMenu.performIdentifierAction(getDefaultSelectedNavItemId(), 0);
                }
            }
            onStartUi(savedInstanceState);
        }
    }

    @Override
    public void onBackPressed() {
        if (!closeDrawer()) {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        closeDrawer();
        int targetNavId = item.getItemId();
        if (targetNavId != currentNavId) {
            onNavigationItem(targetNavId);
            currentNavId = targetNavId;
            return true;
        }
        return false;
    }

    protected boolean closeDrawer() {
        if (isDrawerOpen()) {
            drawerLayout.closeDrawer(GravityCompat.START);
            return true;
        }

        return false;
    }

    private boolean isDrawerOpen() {
        if (drawerLayout != null && drawerLayout.isDrawerOpen(GravityCompat.START)) {
            return true;
        }
        return false;
    }
}
