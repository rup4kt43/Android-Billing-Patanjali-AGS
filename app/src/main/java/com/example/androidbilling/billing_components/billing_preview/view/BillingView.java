package com.example.androidbilling.billing_components.billing_preview.view;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidbilling.R;
import com.example.androidbilling.bill_load.pojo.GetBillResult;
import com.example.androidbilling.billing_components.bill_save.dto.ProductDetails;
import com.example.androidbilling.billing_components.bill_save.dto.SaveRequest;
import com.example.androidbilling.billing_components.bill_save.view.BillSaveView;
import com.example.androidbilling.billing_components.billing_preview.adapter.BillingPreviewAdapter;
import com.example.androidbilling.billing_components.billing_preview.dto.BillingCalculationDTO;
import com.example.androidbilling.billing_components.billing_preview.pojo.GetCustomerResultItem;
import com.example.androidbilling.billing_components.billing_preview.pojo.calculation_pojo.CalculationResponse;
import com.example.androidbilling.billing_components.billing_preview.provider.BillingViewModelProvider;
import com.example.androidbilling.databinding.ActivityBillingViewBinding;
import com.example.androidbilling.sales_menu_components.salesmenu.model.pojo.items_details_pojo.ItemDetailsResult;
import com.example.androidbilling.utilities.global.DecimalRoundOffClass;
import com.example.androidbilling.utilities.global.GlobalFunctions;
import com.example.androidbilling.utilities.global.GlobalValues;
import com.example.androidbilling.utilities.global.SweetToast;

import com.example.androidbilling.utilities.scanner.MyApplication;
import com.example.androidbilling.utilities.sharedpreference.PreferenceUtils;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.example.androidbilling.billing_components.billing_preview.provider.BillingViewModelProvider.getInstance;


public class BillingView extends AppCompatActivity {
    ActivityBillingViewBinding billingViewBinding;
    ItemDetailsResult itemDetailsResult;
    GetBillResult getBillResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Initiating the billingViewModelProviderInstance
        new BillingViewModelProvider(this);
        //getInstance().getCustomerList();


        setUpBinding();

        Intent i = getIntent();
        itemDetailsResult = (ItemDetailsResult) Objects.requireNonNull(i.getExtras()).get("billingData");
        SaveRequest saveRequest = new SaveRequest();
        List<ProductDetails> productDetailsList = new ArrayList<>();
        saveRequest.setCompanyId(GlobalValues.company.getCOMPANYID());
        for (int j = 0; j < itemDetailsResult.getBatches().size(); j++) {
            ProductDetails productDetails = new ProductDetails();

            productDetails.setBatch(itemDetailsResult.getBatches().get(j).getBatch());
            productDetails.setWarehouse(itemDetailsResult.getBatches().get(j).getWarehouse());
            productDetails.setExpDate(itemDetailsResult.getBatches().get(j).getExpiry());
            productDetails.setAltIndDiscount(itemDetailsResult.getBatches().get(j).getAltIndDiscount());
            productDetails.setIndDiscount(itemDetailsResult.getBatches().get(j).getAltIndDiscount());
            productDetails.setIndDiscountRate(itemDetailsResult.getBatches().get(j).getIndividualDiscountRate());
            productDetails.setMcode(itemDetailsResult.getBatches().get(j).getMcode());
            productDetails.setMfgDate(itemDetailsResult.getBatches().get(j).getMfgDate());
            productDetails.setMrp(itemDetailsResult.getBatches().get(j).getMrp());
            productDetails.setpRate(itemDetailsResult.getBatches().get(j).getPrate());
            productDetails.setQuantity(Double.parseDouble(String.valueOf(itemDetailsResult.getBatches().get(j).getQuantity())));
            productDetails.setRate(Double.parseDouble(String.valueOf(itemDetailsResult.getBatches().get(j).getsRate())));
            productDetails.setGstRate(itemDetailsResult.getBatches().get(j).getGst());
            productDetailsList.add(productDetails);
        }
        saveRequest.setProductDetailsList(productDetailsList);
        saveRequest.setVoucherAbbName("Sales");
        //Saving the mode for new or edit
        if (PreferenceUtils.retriveBillEditOption(MyApplication.getContext())) {
            saveRequest.setMode("Edit");
            saveRequest.setVchrno(GlobalValues.loadedBill);
            saveRequest.setChalanNo(GlobalValues.chalanNo);

        } else {
            saveRequest.setMode("New");
        }

        getInstance().recalculateData(saveRequest);


        initObjects();
        initObserver();

        initListener();
        getSupportActionBar().setTitle("Bill Preview");


        //Retriving detail if its the edit
        if (PreferenceUtils.retriveBillEditOption(this)) {
            getBillResult = (GetBillResult) i.getExtras().get("getBillResult");
            assert getBillResult != null;

            billingViewBinding.actCustomerName.setText(getBillResult.getBillto());
            billingViewBinding.etCustomerAddress.setText(getBillResult.getBilltoadd());
            billingViewBinding.etCustomerPhoneNumber.setText(getBillResult.getBilltomob());
        }


    }

    private void initListener() {
        billingViewBinding.actCustomerName.setOnItemClickListener((parent, view, position, id) -> {
            GetCustomerResultItem customerResultItem = (GetCustomerResultItem) parent.getItemAtPosition(position);
            billingViewBinding.etCustomerAddress.setText(customerResultItem.getADDRESS());

        });


        billingViewBinding.etCustomerPhoneNumber.setOnFocusChangeListener((v, hasFocus) -> {

            if (Objects.requireNonNull(billingViewBinding.etCustomerPhoneNumber.getText()).toString().trim().length() < 10)
                billingViewBinding.etCustomerPhoneNumber.setError("Sorry! Phone number must be of 10 characters!");
            else
                billingViewBinding.etCustomerPhoneNumber.setError(null);

        });

    }

    private void initObserver() {


        getInstance().fetchCustomerDetails().observe(this, getCustomerResultItems -> {
            ArrayAdapter<GetCustomerResultItem> adapter = new ArrayAdapter<>(BillingView.this, android.R.layout.select_dialog_item, getCustomerResultItems);
            billingViewBinding.actCustomerName.setAdapter(adapter);
            billingViewBinding.actCustomerName.setThreshold(1);

        });

        getInstance().fetchRecalculatedData().observe(this, new Observer<CalculationResponse>() {
            @Override
            public void onChanged(CalculationResponse calculationResponse) {
                double totalDiscount = 0.00;
                for (int i = 0; i < calculationResponse.getRecalculationResult().getProdList().size(); i++) {
                    double discountAmount = calculationResponse.getRecalculationResult().getProdList().get(i).getDiscount();
                    double totalDiscountWithGst = (discountAmount + discountAmount * calculationResponse.getRecalculationResult().getProdList().get(i).getGstrate() / 100);
                    totalDiscount += totalDiscountWithGst;
                }


                BillingCalculationDTO billingCalculationDTO = new BillingCalculationDTO(
                        Double.parseDouble(DecimalRoundOffClass.roundOff(calculationResponse.getRecalculationResult().getNetamnt())),
                        Double.parseDouble(DecimalRoundOffClass.roundOff(totalDiscount)),
                        Double.parseDouble(DecimalRoundOffClass.roundOff(calculationResponse.getRecalculationResult().getVatamnt()))

                );
                Log.e("ZZZ", new Gson().toJson(billingCalculationDTO));
                Log.e("ZZZ", new Gson().toJson(billingCalculationDTO));

                itemDetailsResult.setBillingCalculationDTO(billingCalculationDTO);
                billingViewBinding.setBillingDetails(billingCalculationDTO);
            }
        });


    }

    private void initObjects() {
        BillingPreviewAdapter adapter = new BillingPreviewAdapter(this, itemDetailsResult);
        billingViewBinding.recyclerView.setAdapter(adapter);
        getInstance().calculateBill(itemDetailsResult);


    }

    private void setUpBinding() {
        billingViewBinding = com.example.androidbilling.databinding.ActivityBillingViewBinding.inflate(getLayoutInflater());
        View view = billingViewBinding.getRoot();
        setContentView(view);

        billingViewBinding.recyclerView.setLayoutManager(new LinearLayoutManager(this));

        billingViewBinding.recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                GlobalFunctions.hideKeyboard(BillingView.this);
                billingViewBinding.etCustomerAddress.clearFocus();
                billingViewBinding.etCustomerPhoneNumber.clearFocus();
                billingViewBinding.actCustomerName.clearFocus();
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.billing_preview_menu, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.menu_proceed) {
            if (!billingViewBinding.actCustomerName.getText().toString().isEmpty()) {
                if (billingViewBinding.etCustomerPhoneNumber.getText().toString().isEmpty()) {
                    GlobalFunctions.hideKeyboard(BillingView.this);
                    SweetToast.info(BillingView.this, "Sorry you must enter the contact number!");
                    billingViewBinding.etCustomerPhoneNumber.requestFocus();

                } else {
                    proceed();
                }
            } else {
                proceed();
            }


        }
        return super.onOptionsItemSelected(item);
    }

    private void proceed() {
        if (Objects.requireNonNull(billingViewBinding.etCustomerPhoneNumber.getText()).toString().isEmpty() || billingViewBinding.etCustomerPhoneNumber.getText().toString().length() == 10) {
            String customerName = billingViewBinding.actCustomerName.getText().toString();
            String customerAddress = Objects.requireNonNull(billingViewBinding.etCustomerAddress.getText()).toString();
            String customerPhone = billingViewBinding.etCustomerPhoneNumber.getText().toString();
            Intent i = new Intent(BillingView.this, BillSaveView.class);
            i.putExtra("saveData", itemDetailsResult);
            i.putExtra("customerName", customerName);
            i.putExtra("customerAddress", customerAddress);
            i.putExtra("customerPhone", customerPhone);
            startActivity(i);
            //Navigate to another page

        } else {
            GlobalFunctions.showToast("Please provide a correct phone number or discard the field!");
        }
    }


}