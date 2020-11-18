package com.example.androidbilling.bill_load.view;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.androidbilling.R;
import com.example.androidbilling.bill_load.adapter.BillLoadAdapter;
import com.example.androidbilling.bill_load.provider.BillLoadViewModelProvider;
import com.example.androidbilling.bill_load.repository.BillLoadRepository;
import com.example.androidbilling.bill_load.viewModel.BillLoadViewModel;
import com.example.androidbilling.billing_components.billing_preview.dto.BillingCalculationDTO;
import com.example.androidbilling.databinding.ActivityBillLoadViewBinding;
import com.example.androidbilling.utilities.global.DecimalRoundOffClass;
import com.example.androidbilling.utilities.global.GlobalFunctions;

public class BillLoadView extends AppCompatActivity {
    ActivityBillLoadViewBinding binding;
    BillLoadViewModel provider;
    String[] billType = new String[]{"TI", "CN"};
    ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new BillLoadViewModelProvider(this);
        provider = BillLoadViewModelProvider.getInstance();


        setUpBinding();
        initListener();
        initObserver();

        getSupportActionBar().setElevation(0);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getSupportActionBar().setTitle("Bill Preview");


        ArrayAdapter<String> spn_billTypeAdapter = new ArrayAdapter<>(this, R.layout.spinner_dropdown, billType);
        spn_billTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spnBillType.setAdapter(spn_billTypeAdapter);


    }

    private void initObserver() {

        provider.fetchMessage().observe(this, GlobalFunctions::showToast);

        provider.fetchBillResult().observe(this, getBillResult -> {
            if (getBillResult != null) {
                binding.llBillDescription.setVisibility(View.VISIBLE);
                if (getBillResult.getBillto() != null) {
                    if (getBillResult.getBillto().matches("")) {
                        binding.llBillTo.setVisibility(View.GONE);
                    } else {
                        binding.llBillTo.setVisibility(View.VISIBLE);
                        binding.tvBillTo.setText(getBillResult.getBillto());
                    }

                }

                if (getBillResult.getBilltoadd() != null) {
                    if (getBillResult.getBilltoadd().matches("")) {
                        binding.llBillToAdd.setVisibility(View.GONE);
                    } else {
                        binding.llBillToAdd.setVisibility(View.VISIBLE);
                        binding.billToAdd.setText(getBillResult.getBilltoadd());
                    }
                }

                double totalDiscount = 0.00;
                for(int i = 0;i<getBillResult.getProdList().size();i++){
                    double discountAmount = getBillResult.getProdList().get(i).getDiscount();
                    double totalDiscountWithGst = (discountAmount+discountAmount*getBillResult.getProdList().get(i).getGstrate()/100);
                    totalDiscount+=totalDiscountWithGst;
                }

                BillingCalculationDTO billingCalculationDTO = new BillingCalculationDTO(


                        Double.parseDouble(DecimalRoundOffClass.roundOff(getBillResult.getNetamnt())),
                        Double.parseDouble(DecimalRoundOffClass.roundOff(totalDiscount)),
                        Double.parseDouble(DecimalRoundOffClass.roundOff(getBillResult.getVatamnt()))

                );


                binding.setBillingCalculation(billingCalculationDTO);
                binding.llContentLoaded.setVisibility(View.VISIBLE);
                BillLoadAdapter adapter = new BillLoadAdapter(BillLoadView.this, getBillResult);

                binding.recyclerView.setAdapter(adapter);

                if (getBillResult.getStatus() == 1) {
                    binding.status.setText("CANCELLED");
                    binding.status.setTextColor(Color.parseColor("#A64452"));
                } else {
                    binding.status.setText("ACTIVE");
                    binding.status.setTextColor(Color.parseColor("#219C5F"));
                }

            }
            GlobalFunctions.hideKeyboard(BillLoadView.this);
        });


    }

    private void initListener() {
        binding.btnLoad.setOnClickListener(v -> {

            String et_billType = (String) binding.spnBillType.getSelectedItem();
            String bNo = binding.etBillNo.getText().toString();
            String division = binding.etDivision.getText().toString();
            String phiscalId = binding.etPhiscal.getText().toString();


            BillLoadViewModelProvider.getInstance().getBill(et_billType, bNo, division, phiscalId);

        });
    }


    private void setUpBinding() {
        binding = com.example.androidbilling.databinding.ActivityBillLoadViewBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        //recycler view
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }


    @SuppressLint("RtlHardcoded")
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            clearSingletonInstances();
            BillLoadView.this.finish();

        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        clearSingletonInstances();
        super.onBackPressed();
    }

    private void clearSingletonInstances() {
        BillLoadViewModelProvider.clearInstance();
        BillLoadRepository.clearInstance();
    }

    @Override
    protected void onResume() {
        super.onResume();
        binding.etBillNo.requestFocus();
        GlobalFunctions.showKeyboard(this);
    }
}