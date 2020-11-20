package com.example.androidbilling.sales_menu_components.salesmenu.view;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.androidbilling.R;
import com.example.androidbilling.Settings.view.Settings;
import com.example.androidbilling.bill_load.pojo.GetBillResult;
import com.example.androidbilling.bill_load.pojo.ProdListItem;
import com.example.androidbilling.bill_load.view.BillLoadView;
import com.example.androidbilling.databinding.ActivityHomepageBinding;
import com.example.androidbilling.sales_menu_components.BatchListingPage.BatchList;
import com.example.androidbilling.sales_menu_components.salesmenu.adapter.ViewPagerAdapter;
import com.example.androidbilling.sales_menu_components.salesmenu.fragments.ItemCartFragment;
import com.example.androidbilling.sales_menu_components.salesmenu.fragments.ItemListFragment;
import com.example.androidbilling.sales_menu_components.salesmenu.model.pojo.items_details_pojo.BatchesItem;
import com.example.androidbilling.sales_menu_components.salesmenu.model.pojo.items_details_pojo.ItemDetailsResult;
import com.example.androidbilling.sales_menu_components.salesmenu.provider.SalesMenuViewModelProvider;
import com.example.androidbilling.sales_menu_components.salesmenu.viewmodel.SalesViewModel;
import com.example.androidbilling.sales_menu_components.salesmenu.viewmodel.SalesViewModelFactory;
import com.example.androidbilling.utilities.global.DecimalRoundOffClass;
import com.example.androidbilling.utilities.global.GlobalFunctions;
import com.example.androidbilling.utilities.global.GlobalValues;
import com.example.androidbilling.utilities.global.SweetToast;
import com.example.androidbilling.utilities.sharedpreference.PreferenceUtils;
import com.google.android.material.tabs.TabLayout;

import java.util.List;
import java.util.Objects;

import static com.example.androidbilling.utilities.scanner.MyApplication.context;

public class SalesMenuView extends AppCompatActivity {

    ActivityHomepageBinding homePageBinding;
    String title;
    ProgressDialog progressDialog;

    public static ItemDetailsResult itemDetailsResult;

    private SalesViewModel viewModel;
    int numOfPage = 2;
    private ViewPagerAdapter viewPagerAdapter;
    public static GetBillResult getBillResult;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        itemDetailsResult = new ItemDetailsResult();
        getBillResult = new GetBillResult();
        progressDialog = new ProgressDialog(this);


        PreferenceUtils.lockEditMode(this, false);


        setUpViewModelProvider();
        //SalesMenuViewModelProvider.getInstance().updateCategorySelection("", 1);


        setUpBinding();

        getSupportActionBar().setElevation(0);
        getSupportActionBar().setTitle("Sales Invoice");


        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);


        initViewModel();
        initObserver();


    }


    private void setUpViewModelProvider() {
        new SalesMenuViewModelProvider(this);
    }





    private void initObserver() {

        viewModel.fetchErrorMessage().observe(this, s -> {
            if (s.matches("1")) {
                AlertDialog.Builder builder = new AlertDialog.Builder(SalesMenuView.this);
                builder.setCancelable(false)
                        .setMessage("The bill has already been cancelled and this bill cannot be loaded in the edit mode. Would you like to redirect to bill load page?")
                        .setPositiveButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).setNegativeButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SalesMenuView.this.finish();
                        startActivity(new Intent(SalesMenuView.this, BillLoadView.class));
                    }
                });
                builder.show();

            } else {
                GlobalFunctions.dismissProgressDialog();
                GlobalFunctions.showToast(s);
            }

        });


        viewModel.fetchDownloadResponse().observe(this, s -> {

            viewModel.retriveVertical();
            GlobalFunctions.showToast(s);


        });


        viewModel.fetchVertical().observe(this, strings -> {

            viewModel.getAllItemFromDatabase();
            progressDialog.dismiss();
            // categoryList = strings;
            //DrawerAdapter drawerAdapter = new DrawerAdapter(SalesMenuView.this,strings);
            //  homePageBinding.listView.setAdapter(drawerAdapter);


            //set the data to recycler adapter and add action on click to it
        });


        viewModel.fetchItemDetails().observe(this, itemDetailsResult -> {
            GlobalFunctions.dismissProgressDialog();
            if (itemDetailsResult.getBatches().size() > 1) {

                Intent i = new Intent(SalesMenuView.this, BatchList.class);
                i.putExtra("batchList", itemDetailsResult);
                startActivity(i);

            } else {
                SalesMenuViewModelProvider.getInstance().
                        addItemToCart(itemDetailsResult.getBatches().get(0));
                PreferenceUtils.allowBillSettingChange(SalesMenuView.this, false);


            }

        });

        viewModel.fetchItemForBillPreview().removeObservers(this);
        viewModel.fetchItemForBillPreview().observe(Objects.requireNonNull(this), batchesItems -> {

            itemDetailsResult.setBatches(batchesItems);


        });


        viewModel.fetchBillItemList().removeObservers(this);
        viewModel.fetchBillItemList().observe(this, getBillResult -> {
            if (getBillResult != null) {
                //trigger to identify bill loaded and ready to add item in bill edit option
                GlobalValues.isEditBillLoaded = true;

                if (getBillResult.getTrnmode().matches("SamridhiCard")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(SalesMenuView.this);
                    builder.setMessage("Cannot edit the bill with payment mode Samriddhi Card. Would you like to preview the bill!!").setCancelable(false).

                            setPositiveButton("No", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            }).setNegativeButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            SalesMenuView.this.finish();
                            startActivity(new Intent(SalesMenuView.this, BillLoadView.class));
                        }
                    }).show();

                } else {
                    List<ProdListItem> prodListItem = getBillResult.getProdList();
                    this.getBillResult = getBillResult;
                    for (int i = 0; i < prodListItem.size(); i++) {
                        BatchesItem batchesItem = new BatchesItem();
                        batchesItem.setBcode(prodListItem.get(i).getBc());
                        batchesItem.setIndividualDiscount(Double.parseDouble(DecimalRoundOffClass.roundOff(prodListItem.get(i).getInddiscount())));
                        batchesItem.setDesca(prodListItem.get(i).getItemdesc());
                        batchesItem.setMcode(prodListItem.get(i).getMcode());
                        batchesItem.setQuantity((int) prodListItem.get(i).getQuantity());
                        batchesItem.setWarehouse(prodListItem.get(i).getWarehouse());
                        batchesItem.setsRate(prodListItem.get(i).getRate());
                        batchesItem.setMrp(prodListItem.get(i).getMrp());
                        batchesItem.setIndividualDiscountRate(prodListItem.get(i).getInddiscountrate());
                        batchesItem.setMfgDate(prodListItem.get(i).getMfgdate());
                        batchesItem.setExpiry(prodListItem.get(i).getExpdate());
                        batchesItem.setPrate(prodListItem.get(i).getPrate());
                        batchesItem.setUnit(prodListItem.get(i).getUnit());
                        batchesItem.setBatch(prodListItem.get(i).getBatch());
                        batchesItem.setAltIndDiscount(prodListItem.get(i).getAltIndDiscount());
                        batchesItem.setGstRate(prodListItem.get(i).getGstrate());
                        batchesItem.setFlatDiscount(prodListItem.get(i).getFlatdiscount());
                        batchesItem.setIndividualDiscountRate(prodListItem.get(i).getInddiscountrate());
                        batchesItem.setGst(Double.parseDouble(DecimalRoundOffClass.roundOff(prodListItem.get(i).getGst())));
                        SalesMenuViewModelProvider.getInstance().addItemToCart(batchesItem);


                    }

                }
            }
        });

        viewModel.fetchItemScannedMcodeList().observe(this, new Observer<List<String>>() {
            @Override
            public void onChanged(List<String> strings) {
                if (strings.isEmpty()) {
                    GlobalFunctions.showToast("Cannot find the matching barcode");
                } else {
                    if (strings.size() > 1) {
                        GlobalFunctions.showProgressDialog("Filtering Item List. Please Wait...", SalesMenuView.this);
                        ItemListFragment.rb_barcode.setChecked(true);


                    } else {
                        ItemListFragment.rb_mcode.setChecked(true);
                        PreferenceUtils.lockEditMode(context, true);
                        GlobalFunctions.showProgressDialog("Downloading Batch Details. Please Wait....", SalesMenuView.this);
                        SalesMenuViewModelProvider.getInstance().retriveItemDetails(strings.get(0));
                    }
                }

            }
        });


    }


    private void initViewModel() {
        viewModel = new ViewModelProvider(this, new SalesViewModelFactory()).get(SalesViewModel.class);
        if (PreferenceUtils.retriveDataDownloadStatus(this)) {
            viewModel.retriveVertical();

        } else {

            progressDialog.setMessage("Downloading the data. Please Wait...");
            progressDialog.setCancelable(false);
            progressDialog.show();
            viewModel.getItemList();
        }


    }


    private void setUpBinding() {
        homePageBinding = com.example.androidbilling.databinding.ActivityHomepageBinding.inflate(getLayoutInflater());
        View view = homePageBinding.getRoot();
        setContentView(view);


        homePageBinding.viewPager.setOffscreenPageLimit(numOfPage);

        homePageBinding.tabLayout.addTab(homePageBinding.tabLayout.newTab().setText("Item List"));
        homePageBinding.tabLayout.addTab(homePageBinding.tabLayout.newTab().setText("Order List"));
        homePageBinding.tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);


        homePageBinding.viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(homePageBinding.tabLayout));// view page change huda tab change garaune
        homePageBinding.tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                homePageBinding.viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                homePageBinding.viewPager.setCurrentItem(tab.getPosition());
            }
        });


    }


    @Override
    public void onBackPressed() {
        if (homePageBinding.viewPager.getCurrentItem() == 0) {
            // If the user is currently looking at the first step, allow the system to handle the
            // Back button. This calls finish() on this activity and pops the back stack.
           showAlertDialog();


        } else {
            // Otherwise, select the previous step.
            homePageBinding.viewPager.setCurrentItem(homePageBinding.viewPager.getCurrentItem() - 1);
        }


    }

    private void showAlertDialog() {
        if(ItemCartFragment.itemList.size()>0){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("This will clear all the ordered item. Are you sure?")
                    .setPositiveButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).setNegativeButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    SalesMenuView.this.finish();
                }
            }).setCancelable(false).show();
        }else{
            SalesMenuView.this.finish();
        }



    }


    @Override
    protected void onResume() {
        super.onResume();


        if (GlobalValues.categorySelectedPosition >= 0) {
            viewModel.retriveItemFromCategory(GlobalValues.selectedCategory);

        } else {

            viewModel.getAllItemFromDatabase();
        }

        viewModel.fetchAllItemList().observe(this, itemListItems -> {
            viewPagerAdapter = new ViewPagerAdapter(SalesMenuView.this, getSupportFragmentManager(), numOfPage, itemListItems);
            homePageBinding.viewPager.setAdapter(viewPagerAdapter);

        });

        if (GlobalValues.isOrderProceed) {
            homePageBinding.viewPager.setCurrentItem(1);
            GlobalValues.isOrderProceed = false;
        } else {
            homePageBinding.viewPager.setCurrentItem(0);

        }

        GlobalFunctions.clearAllSingleTonInstances();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home_page_menu, menu);
        return true;
    }


    @SuppressLint("RtlHardcoded")
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            showAlertDialog();


        } else if (id == R.id.menu_setting) {
            startActivity(new Intent(SalesMenuView.this, Settings.class));
        } else if (id == R.id.menu_refresh) {

            progressDialog.setMessage("Downloading the data. Please Wait...");
            progressDialog.setCancelable(false);
            progressDialog.show();
            viewModel.getItemList();
        }


        return super.onOptionsItemSelected(item);
    }

    private String barcode = "";

    @Override
    public boolean dispatchKeyEvent(KeyEvent e) {

        if (homePageBinding.viewPager.getCurrentItem() == 0) {
            if(e.getAction()==KeyEvent.ACTION_DOWN){

                char pressedKey = (char) e.getUnicodeChar();
                barcode += pressedKey;

            }
            if (e.getAction()==KeyEvent.ACTION_DOWN && e.getKeyCode() == KeyEvent.KEYCODE_ENTER) {

                ItemListFragment.loadBarcode(barcode.trim());

                SweetToast.info(this,barcode.trim());
                barcode = "";

            }







        }
        return super.dispatchKeyEvent(e);




    }


}