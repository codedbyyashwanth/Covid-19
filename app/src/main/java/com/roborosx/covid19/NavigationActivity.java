package com.roborosx.covid19;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.nfc.cardemulation.HostNfcFService;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class NavigationActivity extends AppCompatActivity {

    BottomNavigationView navigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation2);

        navigationView = findViewById(R.id.navigation);
        navigationView.setOnNavigationItemSelectedListener(listener);
        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout,new Home()).commit();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener listener=new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            Fragment fragment=null;
            switch (menuItem.getItemId())
            {
                case R.id.navigation_home:
                    fragment=new Home();
                    break;
                case R.id.navigation_notifications:
                    fragment=new Notification();
                    break;
                case R.id.navigation_dashboard:
                    fragment=new Dashboard();
                    break;
                case R.id.navigation_account:
                    fragment=new Contact();
                    break;
            }

            assert fragment != null;
            getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout,fragment).commit();

            return true;
        }
    };
}