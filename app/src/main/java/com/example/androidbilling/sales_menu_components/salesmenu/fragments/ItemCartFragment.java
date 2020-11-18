package com.example.androidbilling.sales_menu_components.salesmenu.fragments;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.androidbilling.R;
import com.example.androidbilling.bill_load.pojo.GetBillResult;
import com.example.androidbilling.billing_components.billing_preview.view.BillingView;
import com.example.androidbilling.databinding.FragmentItemCartBinding;
import com.example.androidbilling.sales_menu_components.salesmenu.adapter.ItemCartAdapter;
import com.example.androidbilling.sales_menu_components.salesmenu.adapter.ItemListAdapter;
import com.example.androidbilling.sales_menu_components.salesmenu.model.pojo.items_details_pojo.BatchesItem;
import com.example.androidbilling.sales_menu_components.salesmenu.model.pojo.items_details_pojo.ItemDetailsResult;
import com.example.androidbilling.sales_menu_components.salesmenu.provider.SalesMenuViewModelProvider;
import com.example.androidbilling.sales_menu_components.salesmenu.view.SalesMenuView;
import com.example.androidbilling.utilities.global.GlobalFunctions;
import com.example.androidbilling.utilities.global.GlobalValues;
import com.example.androidbilling.utilities.sharedpreference.PreferenceUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ItemCartFragment extends Fragment {

    FragmentItemCartBinding itemCartBinding;
   public static List<BatchesItem> itemList;
    ItemCartAdapter adapter;

    ItemDetailsResult itemDetailsResult;
    GetBillResult getBillResult;

    String[] billType = new String[]{"TI"};;


    Intent i;


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        i = new Intent(context, BillingView.class);

    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        itemList = new ArrayList<>();
        itemCartBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_item_cart, container, false);
        return itemCartBinding.getRoot();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        itemCartBinding.etBillNo.requestFocus();
        itemDetailsResult = new ItemDetailsResult();
        getBillResult = new GetBillResult();


        if (PreferenceUtils.retriveBillEditOption(Objects.requireNonNull(getActivity()))) {
            itemCartBinding.llLoadBill.setVisibility(View.VISIBLE);
            itemCartBinding.btnCancel.setVisibility(View.VISIBLE);
        } else {
            itemCartBinding.llLoadBill.setVisibility(View.GONE);
        }





        itemCartBinding.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        super.onViewCreated(view, savedInstanceState);

        SalesMenuViewModelProvider.getInstance().fetchBatchSelectedItemToCart().observe(Objects.requireNonNull(getActivity()), batchesItems -> {
            itemList = batchesItems;
            adapter = new ItemCartAdapter(getActivity(), batchesItems);
            itemCartBinding.recyclerView.setAdapter(adapter);
        });



        initListener();

        ArrayAdapter<String> spn_billTypeAdapter = new ArrayAdapter<>(getActivity(), R.layout.spinner_dropdown, billType);
        spn_billTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        itemCartBinding.spnBillType.setAdapter(spn_billTypeAdapter);
    }

    private void initListener() {
        itemCartBinding.btnProceed.setOnClickListener(v -> {

            if(itemList.isEmpty()){
                GlobalFunctions.showToast("Sorry there are no items. Please add some items and proceed!");
            }else{
                i =  new Intent(getActivity(),BillingView.class);
                i.putExtra("getBillResult", SalesMenuView.getBillResult);
                i.putExtra("billingData", SalesMenuView.itemDetailsResult);
                startActivity(i);
                GlobalValues.isOrderProceed = true;
                if(GlobalFunctions.progressDialog.isShowing()){
                    GlobalFunctions.dismissProgressDialog();
                }
            }



        });
        itemCartBinding.btnLoad.setOnClickListener(v -> {

            String et_billType = (String) itemCartBinding.spnBillType.getSelectedItem();
            String bNo = itemCartBinding.etBillNo.getText().toString();
            String division = itemCartBinding.etDivision.getText().toString();
            String phiscalId = itemCartBinding.etPhiscal.getText().toString();



            SalesMenuViewModelProvider.getInstance().loadBill(et_billType,bNo,division,phiscalId);
            PreferenceUtils.lockEditMode(getActivity(), true);
            PreferenceUtils.lockEditMode(getActivity(), true);
            PreferenceUtils.isBillLoaded(getActivity(),true);

        });

        itemCartBinding.btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder  builder = new AlertDialog.Builder(getActivity());
                builder.setMessage("Are you sure you want to clear all items?")
                        .setCancelable(false)
                        .setNegativeButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                GlobalValues.isEditBillLoaded = false;
                                ItemListAdapter.isItemClicked =false;
                                PreferenceUtils.lockEditMode(getActivity(),false);
                                SalesMenuViewModelProvider.getInstance().cancelEdit();
                            }
                        }).setPositiveButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.show();
            }
        });
    }




}
