package com.example.androidbilling.dashboard.view;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.androidbilling.R;
import com.example.androidbilling.Settings.view.Settings;
import com.example.androidbilling.bill_load.view.BillLoadView;
import com.example.androidbilling.dashboard.adapter.DashboardAdapter;
import com.example.androidbilling.dashboard.core.DashboardImage;
import com.example.androidbilling.dashboard.model.pojo.DashboardResultItem;
import com.example.androidbilling.dashboard.provider.DashboardViewModelProvider;
import com.example.androidbilling.dashboard.viewModel.DashboardViewModel;
import com.example.androidbilling.dashboard.viewModel.DashboardViewModelFactory;
import com.example.androidbilling.databinding.ActivityDashboardViewBinding;
import com.example.androidbilling.databinding.NavHeaderMainBinding;
import com.example.androidbilling.login.view.LoginView;
import com.example.androidbilling.sales_menu_components.salesmenu.view.SalesMenuView;
import com.example.androidbilling.salse_return_component.view.SalesReturnView;
import com.example.androidbilling.utilities.global.GlobalFunctions;
import com.example.androidbilling.utilities.global.GlobalValues;
import com.example.androidbilling.utilities.sharedpreference.PreferenceUtils;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class DashboardView extends AppCompatActivity {
    private ActivityDashboardViewBinding binding;
    private DashboardViewModelProvider provider;
    private DashboardAdapter dashboardAdapter;
    private String title;
    List<DashboardResultItem> dashboardItems;
    DashboardResultItem dashboardResultItem;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Retriving login credentials
        PreferenceUtils.retriveLoginCredentials(this);
        setUpViewBinding();

        DashboardImage.setImageHashMap();
        setupViewModel();
        setUpViewModelProvider();

        initListener();
        retriveDashboardData();
        initObserver();
        setupDrawerAction();
        getSupportActionBar().setTitle("Dashboard");


        title = GlobalValues.company.getCOMPANYNAME();

        //navigation header section
        NavHeaderMainBinding navHeaderMainBinding = DataBindingUtil.bind(binding.navigationView.getHeaderView(0));
        navHeaderMainBinding.setUserDetails(GlobalValues.userInfo);
        navHeaderMainBinding.setCompanyDetails(GlobalValues.company);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);


    }

    private void setupViewModel() {
        new ViewModelProvider(this, new DashboardViewModelFactory()).get(DashboardViewModel.class);
    }

    private void initObserver() {

        DashboardViewModelProvider.getInstance().fetchDashboardData().observe(this, dashboardResultItems -> {
            if (dashboardResultItems.size() == 0) {
                dashboardItems = new ArrayList<>();

                dashboardResultItem = new DashboardResultItem();
                dashboardResultItem.setbILLCOUNT(0);
                dashboardResultItem.settOTALAMOUNT(0);
                dashboardResultItem.setvOUCHERNAME("SALES INVOICE");
                dashboardItems.add(dashboardResultItem);

                dashboardResultItem = new DashboardResultItem();
                dashboardResultItem.setbILLCOUNT(0);
                dashboardResultItem.settOTALAMOUNT(0);
                dashboardResultItem.setvOUCHERNAME("SALES RETURN");
                dashboardItems.add(dashboardResultItem);



            } else {
                dashboardItems = dashboardResultItems;
            }
            dashboardAdapter = new DashboardAdapter(DashboardView.this, dashboardItems);
            binding.recyclerView.setAdapter(dashboardAdapter);
            binding.progressBar.setVisibility(View.GONE);
        });
    }

    private void retriveDashboardData() {
        DashboardViewModelProvider.getInstance().retriveDashboardData();
        binding.progressBar.setVisibility(View.VISIBLE);
    }

    private void setUpViewBinding() {
        binding = com.example.androidbilling.databinding.ActivityDashboardViewBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void setUpViewModelProvider() {
        new DashboardViewModelProvider(this);

    }

    private void initListener() {


        binding.navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                int id = menuItem.getItemId();

                switch (id) {

                    case R.id.bill_load:


                        startActivity(new Intent(DashboardView.this, BillLoadView.class));
                        break;
                    case R.id.menu_logout:

                        showLogoutAlertDialog();
                        //Navigate to another page
                        break;
                    case R.id.salesReturn:

                        startActivity(new Intent(DashboardView.this, SalesReturnView.class));
                        break;


                    case R.id.salesInvoice:

                        startActivity(new Intent(DashboardView.this, SalesMenuView.class));

                        break;


                }

                return true;
            }
        });
    }

    private void setupDrawerAction() {
        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(this, binding.drawerLayout, R.string.drawer_open, R.string.drawer_close);
        binding.drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();

        binding.drawerLayout.addDrawerListener(new DrawerLayout.DrawerListener() {

            private float last = 0;

            @Override
            public void onDrawerSlide(@NonNull View drawerView, float slideOffset) {
                boolean opening = slideOffset > last;
                boolean closing = slideOffset < last;

                if (opening) {
                    Objects.requireNonNull(getSupportActionBar()).setTitle("Menu");
                } else if (closing) {
                    Objects.requireNonNull(getSupportActionBar()).setTitle("Dashboard");
                }

                last = slideOffset;
            }

            @Override
            public void onDrawerOpened(@NonNull View drawerView) {

            }

            @Override
            public void onDrawerClosed(@NonNull View drawerView) {

            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });
    }

    @SuppressLint("RtlHardcoded")
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();



        if (id == R.id.menu_setting) {
            startActivity(new Intent(DashboardView.this, Settings.class));
        } else if (id == android.R.id.home) {
            if (binding.drawerLayout.isDrawerOpen(Gravity.LEFT)) {

                // getSupportActionBar().setTitle(GlobalValues.company.getCOMPANYNAME());
                binding.drawerLayout.closeDrawer(Gravity.LEFT);
                Objects.requireNonNull(getSupportActionBar()).setTitle(GlobalValues.company.getCOMPANYNAME());

            } else {
                binding.drawerLayout.openDrawer(Gravity.LEFT);
                Objects.requireNonNull(getSupportActionBar()).setTitle("Category List");

                //getSupportActionBar().setTitle("Category List");
            }
        }

        return super.onOptionsItemSelected(item);
    }


    private void showLogoutAlertDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setMessage("Are you sure you want to Logout?")
                .setPositiveButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).setNegativeButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                PreferenceUtils.updateKeepMeLoggedInStatus(DashboardView.this, false);
                GlobalFunctions.clearAllSingleTonInstances();
                Intent i = new Intent(DashboardView.this, LoginView.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }
        });

        builder.show();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.home_page_menu, menu);
        invalidateOptionsMenu();
        MenuItem refreshMenu = menu.findItem(R.id.menu_refresh);
        refreshMenu.setVisible(false);
        return true;
    }


    @Override
    protected void onResume() {

        super.onResume();
        retriveDashboardData();
        binding.drawerLayout.closeDrawer(Gravity.LEFT, false);

    }

    @Override
    public void onBackPressed() {

        minimizeApp();
    }

    public void minimizeApp() {
        Intent startMain = new Intent(Intent.ACTION_MAIN);
        startMain.addCategory(Intent.CATEGORY_HOME);
        startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(startMain);
    }
}