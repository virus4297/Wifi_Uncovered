package com.example.wifiuncovered;

import android.database.sqlite.SQLiteDatabase;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.text.format.Formatter;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.wifiuncovered.ui.home.HomeViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;

    public String ip,host,ipAddress;
    public SQLiteDatabase database;
    public MyHelper helper;
    public ProgressBar pb;
    public getDataNetwork asyncTask;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        final DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        //code to make menu items selectable

        navigationView.bringToFront();

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                NavController navController = Navigation.findNavController(MainActivity.this, R.id.nav_host_fragment);
                drawer.closeDrawers();
                return NavigationUI.onNavDestinationSelected(menuItem, navController)
                        || MainActivity.super.onOptionsItemSelected(menuItem);

            }
        });


        //*********************************************************************************************************************
//
//        helper=new MyHelper(this);
//        database = helper.getWritableDatabase();
        //***********************Get Current IP****************************
        WifiManager wm = (WifiManager) this.getSystemService(WIFI_SERVICE);
        if(wm!=null)
            ipAddress = Formatter.formatIpAddress(wm.getConnectionInfo().getIpAddress());
        else
            ipAddress="Wifi not Available!";
        if(ipAddress.equals("0.0.0.0"))
        {
            Toast.makeText(this,"Turn ON the Wifi beach!\nand\n Restart Application", Toast.LENGTH_LONG).show();
            ipAddress="Wifi not Available!";
            //return;
        }

        //*******************************Async call to getData
        final MainActivity mainActivity = this;
        asyncTask=new getDataNetwork(ipAddress,pb,this,mainActivity,helper,database);
        asyncTask.execute();

    }//OnCreate ends here

    public void setpbvisible(int visible){
        pb=findViewById(R.id.pbmain);
        pb.setVisibility(visible);
    }
    public String getDataNetworkStatus(){
        return this.asyncTask.getStatus().toString();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}
