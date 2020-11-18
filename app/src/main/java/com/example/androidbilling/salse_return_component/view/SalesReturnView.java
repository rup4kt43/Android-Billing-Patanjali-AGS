package com.example.androidbilling.salse_return_component.view;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.androidbilling.R;
import com.example.androidbilling.bill_load.view.BillLoadView;
import com.example.androidbilling.databinding.ActivitySalesReturnViewBinding;
import com.example.androidbilling.salse_return_component.adapter.SalesReturnViewPagerAdapter;
import com.example.androidbilling.salse_return_component.fragments.SalesReturnFragment;
import com.example.androidbilling.salse_return_component.provider.SalesReturnViewModelProvider;
import com.example.androidbilling.salse_return_component.repository.SalesReturnRepository;
import com.example.androidbilling.salse_return_component.viewModel.SalesReturnViewModel;
import com.example.androidbilling.utilities.global.GlobalFunctions;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;

import java.util.Objects;

public class SalesReturnView extends AppCompatActivity {
    ActivitySalesReturnViewBinding binding;
    int numOfPage = 2;
    public static String billNumber;
    SalesReturnViewModel provider;
    private String trnMode;
    private SalesReturnViewPagerAdapter viewPagerAdapter;
    String[] billType = new String[]{"TI", "CN"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new SalesReturnViewModelProvider(this);
        GlobalFunctions.getGUID();
        provider = SalesReturnViewModelProvider.getInstance();
        setupBinding();
        initListener();
        setupViewPager();
        initObserver();

        Objects.requireNonNull(getSupportActionBar()).setElevation(0);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Sales Return");

        ArrayAdapter<String> spn_billTypeAdapter = new ArrayAdapter<>(this, R.layout.spinner_dropdown, billType);
        spn_billTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spnBillType.setAdapter(spn_billTypeAdapter);

        binding.etBillNo.requestFocus();
        GlobalFunctions.showKeyboard(this);


    }

    private void initObserver() {
        provider.fetchErrorMessage().observe(this, GlobalFunctions::showToast);

        provider.fetchBillResult().observe(this, getBillResult -> {
            if (getBillResult.size() > 0) {
                if(getBillResult.get(0).getTrnMode().equalsIgnoreCase("SamridhiCard")){
                    AlertDialog.Builder builder = new AlertDialog.Builder(SalesReturnView.this);
                    builder.setMessage("Cannot return the bill with payment mode Samriddhi Card. Would you like to preview the bill!!").setCancelable(false).

                            setPositiveButton("No", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            }).setNegativeButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            SalesReturnView.this.finish();
                            resetData();
                            startActivity(new Intent(SalesReturnView.this, BillLoadView.class));
                        }
                    }).show();

                }else{
                    viewPagerAdapter = new SalesReturnViewPagerAdapter(SalesReturnView.this, getSupportFragmentManager(), getBillResult, numOfPage);
                    binding.viewPager.setVisibility(View.VISIBLE);
                    binding.viewPager.setAdapter(viewPagerAdapter);
                    binding.etBillNo.setText("");
                    binding.tabLayout.setVisibility(View.VISIBLE);
                    Objects.requireNonNull(getSupportActionBar()).setTitle("Bill No : " + billNumber);
                    GlobalFunctions.hideKeyboard(SalesReturnView.this);
                }





            } else {
                binding.tabLayout.setVisibility(View.GONE);
                binding.viewPager.setVisibility(View.GONE);
                binding.etBillNo.setText("");
                Objects.requireNonNull(getSupportActionBar()).setTitle("Sales Return");
            }
        });

        provider.fetchResponseMessage().observe(this, s -> {

            Toast.makeText(SalesReturnView.this, s, Toast.LENGTH_SHORT).show();
            binding.tabLayout.setVisibility(View.GONE);
            binding.viewPager.setVisibility(View.GONE);
            binding.etBillNo.setText("");

            GlobalFunctions.getGUID();
            Objects.requireNonNull(getSupportActionBar()).setTitle("Sales Return");


        });


    }

    private void initListener() {
        binding.btnLoad.setOnClickListener(v -> {
            String et_billType = (String) binding.spnBillType.getSelectedItem();
            String bNo = binding.etBillNo.getText().toString();
            String division = binding.etDivision.getText().toString();
            String phiscalId = binding.etPhiscal.getText().toString();


            provider.loadBill(et_billType, bNo, division, phiscalId);

        });
    }

    private void setupViewPager() {
        provider.clearList();

        binding.tabLayout.removeAllTabs();


        binding.viewPager.setOffscreenPageLimit(numOfPage);

        binding.tabLayout.addTab(binding.tabLayout.newTab().setText("Sales List"));
        binding.tabLayout.addTab(binding.tabLayout.newTab().setText("Sales Return List"));
        binding.tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);


        binding.viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(binding.tabLayout));// view page change huda tab change garaune
        binding.tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                binding.viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }


    private void setupBinding() {
        binding = com.example.androidbilling.databinding.ActivitySalesReturnViewBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);


        //recycler view

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        resetData();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.bill_save_menu, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.menu_save) {
            if (SalesReturnFragment.billResult.getProdList() == null) {
                GlobalFunctions.showToast("Please load some item to return and proceed.");
            } else {

                saveBill();

            }
            //Navigate to another page
            return true;
        } else if (item.getItemId() == android.R.id.home) {
            SalesReturnView.this.finish();
            resetData();
        }
        return super.onOptionsItemSelected(item);
    }

    private void resetData() {
        SalesReturnViewModelProvider.clearInstance();
        SalesReturnRepository.clearInstance();
    }

    private void saveBill() {
        Log.e("SalesReturnFragment", new Gson().toJson(SalesReturnFragment.billResult));
        Log.e("SalesReturnFragment", new Gson().toJson(SalesReturnFragment.billResult));
        provider.saveBill(SalesReturnFragment.billResult, trnMode);
    }


}