package com.cornerstone.buildingbrands;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    
    private BottomNavigationView bottomNavigation;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        // Initialize bottom navigation
        bottomNavigation = findViewById(R.id.bottom_navigation);
        setupBottomNavigation();
        
        // Check if user is logged in
        if (!isUserLoggedIn()) {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        }
    }
    
    private void setupBottomNavigation() {
        bottomNavigation.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.nav_daily_prod:
                    loadFragment(new DailyProductionFragment());
                    return true;
                case R.id.nav_history:
                    loadFragment(new HistoryFragment());
                    return true;
                case R.id.nav_scan:
                    startActivity(new Intent(this, ScannerActivity.class));
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    return true;
                case R.id.nav_repairs:
                    loadFragment(new RepairsFragment());
                    return true;
                case R.id.nav_units:
                    loadFragment(new UnitsFragment());
                    return true;
            }
            return false;
        });
    }
    
    private void loadFragment(Fragment fragment) {
        getSupportFragmentManager()
            .beginTransaction()
            .setCustomAnimations(R.anim.fade_in, R.anim.fade_out)
            .replace(R.id.fragment_container, fragment)
            .commit();
    }
    
    private boolean isUserLoggedIn() {
        // Check SharedPreferences for login status
        return getSharedPreferences("UserPrefs", MODE_PRIVATE)
            .getBoolean("isLoggedIn", false);
    }
}