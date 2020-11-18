package com.example.androidbilling.sales_menu_components.salesmenu.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.androidbilling.R;
import com.example.androidbilling.databinding.FragmentItemListBinding;
import com.example.androidbilling.sales_menu_components.categorySelection.view.CategorySelectionView;
import com.example.androidbilling.sales_menu_components.salesmenu.adapter.ItemListAdapter;
import com.example.androidbilling.sales_menu_components.salesmenu.model.pojo.ItemListItem;
import com.example.androidbilling.sales_menu_components.salesmenu.provider.SalesMenuViewModelProvider;
import com.example.androidbilling.sales_menu_components.salesmenu.view.SalesMenuView;
import com.example.androidbilling.utilities.global.GlobalFunctions;
import com.example.androidbilling.utilities.global.GlobalValues;
import com.example.androidbilling.utilities.global.SweetToast;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ItemListFragment extends Fragment {

    private final SalesMenuView context;
    FragmentItemListBinding itemListBinding;
    static CoordinatorLayout coordinatorLayout;
    ItemListAdapter itemListAdapter;
    public final List<ItemListItem> list;
    public static String checkedButton = "mcode";


    public static RadioButton rb_mcode, rb_barcode;
    public List<String> descaList = new ArrayList<>();


    public ItemListFragment(List<ItemListItem> itemListItems, SalesMenuView context) {
        this.context = context;
        this.list = itemListItems;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        itemListBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_item_list, container, false);
        coordinatorLayout = itemListBinding.coordinatorLayout;
        return itemListBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        // rb_mcode = itemListBinding.rbMcode;
        rb_barcode = itemListBinding.rbBarcode;
        checkedButton = "name";


        itemListBinding.searchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkedButton = "name";
            }
        });

        itemListBinding.searchView.setQueryHint("Search Item");
        itemListBinding.recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),3
        ));

        Collections.sort(list, new Comparator<ItemListItem>() {
            @Override
            public int compare(ItemListItem itemListItem, ItemListItem t1) {
                return itemListItem.getDESCA().compareTo(t1.getDESCA());
            }
        });




        itemListAdapter = new ItemListAdapter(getActivity(), list);
        itemListBinding.recyclerView.setAdapter(itemListAdapter);
        //setItemSize(list.size());

        searchItemListener();

        itemListBinding.rgMode.setOnCheckedChangeListener((group, checkedId) -> {
            switch (checkedId) {
            /*    case R.id.rb_mcode:
                    checkedButton = "mcode";
                    itemListBinding.searchView.setQuery(itemListBinding.searchView.getQuery().toString(), true);
                    break;*/
               /* case R.id.rb_name:
                    itemListBinding.searchView.setQuery(itemListBinding.searchView.getQuery().toString(), true);
                    checkedButton = "name";
                    break;*/

                case R.id.rb_barcode:
                    itemListAdapter.getFilter().filter(GlobalValues.scannedBarcode);
                    checkedButton = "barcode";

                    break;
            }
        });

        itemListBinding.fabCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getActivity(), CategorySelectionView.class));
            }
        });

        itemListBinding.ivBarcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //   startActivity(new Intent(getActivity(), MainActivity.class));
                /*IntentIntegrator integrator = new IntentIntegrator(getActivity());
                integrator.setDesiredBarcodeFormats(IntentIntegrator.EAN_13);
                integrator.setCaptureActivity(CaptureAct.class);
                integrator.setBeepEnabled(true);
                integrator.setBarcodeImageEnabled(true);
                integrator.forSupportFragment(ItemListFragment.this).initiateScan(); // `this` is the current Fragment*/

            }
        });

        itemListBinding.fastscroll.setRecyclerView(itemListBinding.recyclerView);
        initObserver();


    }

    private void initObserver() {

    }


    private void searchItemListener() {
        itemListBinding.searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (!query.isEmpty()) {
                    GlobalFunctions.hideKeyboard(getActivity());
                    itemListAdapter.getFilter().filter(query);
                    return true;
                } else {
                    GlobalFunctions.hideKeyboard(getActivity());
                    itemListAdapter.getFilter().filter("");
                    return false;
                }
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (!newText.isEmpty()) {

                    itemListAdapter.getFilter().filter(newText);
                    return true;
                } else {
                    itemListAdapter.getFilter().filter("");
                    return false;
                }
            }
        });

        itemListBinding.searchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemListBinding.searchView.onActionViewExpanded();
            }
        });
    }

/*    public static void setItemSize(int size) {
        GlobalFunctions.showSnackBar(coordinatorLayout, "Total Items : " + size + " Items", 0, -2);
    }*/


    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {

            if (result.getContents() == null) {
                Toast.makeText(getActivity(), "Cancelled", Toast.LENGTH_LONG).show();
            } else {
                //zGlobalFunctions.showProgressDialog("Filtering List. Please wait...",context);
                GlobalValues.scannedBarcode = result.getContents();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        SalesMenuViewModelProvider.getInstance().searchItemWithBarcode(result.getContents());
                    }
                }, 800);

            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    /* access modifiers changed from: protected */

    private String barcode = "";


    public static void  loadBarcode(String barcode){
        GlobalValues.scannedBarcode = barcode;
        rb_barcode.setChecked(true);

    }




}
