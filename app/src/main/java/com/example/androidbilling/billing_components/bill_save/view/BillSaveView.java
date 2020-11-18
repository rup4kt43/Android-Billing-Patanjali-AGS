package com.example.androidbilling.billing_components.bill_save.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.androidbilling.R;
import com.example.androidbilling.billing_components.bill_save.adapter.BottomSheetAdapter;
import com.example.androidbilling.billing_components.bill_save.calculation_part.DataModel;
import com.example.androidbilling.billing_components.bill_save.dto.trnmode.PaymentDetails;
import com.example.androidbilling.billing_components.bill_save.pojo.paymentmode.PaymentModeResultItem;
import com.example.androidbilling.billing_components.bill_save.provider.BillSaveViewModelProvider;
import com.example.androidbilling.databinding.ActivityBillSaveViewBinding;
import com.example.androidbilling.sales_menu_components.salesmenu.model.pojo.items_details_pojo.ItemDetailsResult;
import com.example.androidbilling.sales_menu_components.salesmenu.view.SalesMenuView;
import com.example.androidbilling.utilities.Printing.PrintActivity;
import com.example.androidbilling.utilities.global.GlobalFunctions;
import com.example.androidbilling.utilities.global.GlobalValues;
import com.example.androidbilling.utilities.global.SweetToast;
import com.example.androidbilling.utilities.sharedpreference.PreferenceUtils;
import com.google.android.material.bottomsheet.BottomSheetBehavior;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class BillSaveView extends AppCompatActivity {
    boolean isSaveButtonClicked = false;
    ActivityBillSaveViewBinding billSaveViewBinding;
    BottomSheetBehavior sheetBehavior;
    ItemDetailsResult itemDetailsResult;
    String mode = "Cash";
    private String customerName, customerAddress, customerPhone;
    PaymentDetails cashPaymentDetails, couponPaymentDetails, cardPaymentDetails, walletPaymentDetails, samridhiCardPaymentDetails;

    double netAmount;


    private DataModel dataModel;
    ArrayAdapter<PaymentModeResultItem> paymentModeResultItemArrayAdapter;
    BottomSheetAdapter bottomSheetAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setUpBinding();
        //initializing object
        cashPaymentDetails = new PaymentDetails();
        couponPaymentDetails = new PaymentDetails();
        cardPaymentDetails = new PaymentDetails();
        walletPaymentDetails = new PaymentDetails();
        samridhiCardPaymentDetails = new PaymentDetails();


        initViewModelProvider();
        retriveBillData();

        initListener();
        initObserver();
        initTextWatcher();
        sheetBehavior = BottomSheetBehavior.from(billSaveViewBinding.bslPaymentModes.bottomSheet);

        netAmount = itemDetailsResult.getBillingCalculationDTO().getNetAmount();
        billSaveViewBinding.etTender.setText(String.valueOf(netAmount));

        dataModel = new DataModel(netAmount);

        billSaveViewBinding.setDataModel(dataModel);
        billSaveViewBinding.etCashTender.requestFocus();


    }

    private void initTextWatcher() {
        billSaveViewBinding.etCashTender.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                // If the event is a key-down event on the "enter" button
                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER)) {

                    dataModel.setCashAmount("0");
                    dataModel.calculateBalance();
                    dataModel.CashCalculationFucntion();
                    GlobalFunctions.hideKeyboard(BillSaveView.this);

                    return true;
                }
                return false;
            }
        });

    }


    private void initViewModelProvider() {
        new BillSaveViewModelProvider(this);
        BillSaveViewModelProvider.getInstance().getPaymentModes();
    }

    private void initObserver() {
        BillSaveViewModelProvider.getInstance().fetchErrorMessage().observe(this, GlobalFunctions::showToast);

        BillSaveViewModelProvider.getInstance().fetchErrorMessage().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                GlobalFunctions.showToast(s);
                completeBilling();

            }
        });

        BillSaveViewModelProvider.getInstance().fetchPrintResponse().observe(this, saveBillResponse -> {

            if (saveBillResponse.getStatusCode() == 200) {
                if (!saveBillResponse.getResult().isEmpty()) {
                    GlobalFunctions.showToast("Bill Saved Successfully!!");

                    GlobalValues.mode_validation = GlobalValues.DEFAULT_MODE;
                    GlobalValues.mode_selected_position = 0;
                    SweetToast.info(BillSaveView.this,saveBillResponse.getResult2());
                    if (PreferenceUtils.retrivePrintintDetails(BillSaveView.this)) {

                        PrintActivity printActivity = new PrintActivity(BillSaveView.this, saveBillResponse.getResult());
                        printActivity.printData();


                        GlobalFunctions.getGUID();


                    }
                    completeBilling();
                    PreferenceUtils.saveBillEditDetails(BillSaveView.this, false);
                    GlobalValues.isOrderProceed = false;
                    isSaveButtonClicked = false;
                    GlobalValues.isEditBillLoaded = false;

                }

            }


        });


        BillSaveViewModelProvider.getInstance().fetchPaymentModes().observe(this, paymentModeResultItems -> {
            bottomSheetAdapter = new BottomSheetAdapter(BillSaveView.this, GlobalValues.paymentList);
            billSaveViewBinding.bslPaymentModes.recyclerView.setLayoutManager(new LinearLayoutManager(BillSaveView.this));

            billSaveViewBinding.bslPaymentModes.recyclerView.setAdapter(bottomSheetAdapter);

        });

        BillSaveViewModelProvider.getInstance().fetchPaymentModeName().observe(this, paymentModeResultItems -> {
            if (paymentModeResultItems.isEmpty()) {
                GlobalFunctions.showToast("Sorry currently there are no payment mode!!");
                if (GlobalValues.selected_payment_mode.equalsIgnoreCase("Cash")) {
                    billSaveViewBinding.etCashTender.setEnabled(false);
                } else if (GlobalValues.selected_payment_mode.equalsIgnoreCase("Card")) {
                    billSaveViewBinding.etCouponAmount.setEnabled(false);
                } else if (GlobalValues.selected_payment_mode.equalsIgnoreCase("Wallet")) {
                    billSaveViewBinding.etWalletAmount.setEnabled(false);
                } else {
                    billSaveViewBinding.etSamridhiCardAmoun.setEnabled(false);

                }
            } else {

                switch (GlobalValues.selected_payment_mode) {
                    case "Cash":
                        cashPaymentDetails.setTrnac(paymentModeResultItems.get(0).getACID());
                        break;
                    case "Card":

                        paymentModeResultItemArrayAdapter = new ArrayAdapter<>(BillSaveView.this, R.layout.bill_save_spinner, paymentModeResultItems);
                        paymentModeResultItemArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        billSaveViewBinding.spnCouponMode.setAdapter(paymentModeResultItemArrayAdapter);

                        cardPaymentDetails.setTrnac(paymentModeResultItems.get(billSaveViewBinding.spnCouponMode.getSelectedItemPosition()).getACID());
                        cardPaymentDetails.setType(billSaveViewBinding.spnCouponMode.getSelectedItem().toString());
                        cardPaymentDetails.setName(billSaveViewBinding.etCardHolder.getText().toString());


                        break;
                    case "SamridhiCard":
                        samridhiCardPaymentDetails.setTrnac(paymentModeResultItems.get(0).getACID());
                        samridhiCardPaymentDetails.setPaymentMode(paymentModeResultItems.get(0).getMODE());

                        break;

                    case "Wallet":
                        walletPaymentDetails.setPaymentMode(paymentModeResultItems.get(0).getPAYMENTMODENAME());
                        paymentModeResultItemArrayAdapter = new ArrayAdapter<>(BillSaveView.this, R.layout.bill_save_spinner, paymentModeResultItems);
                        paymentModeResultItemArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        billSaveViewBinding.spnWallet.setAdapter(paymentModeResultItemArrayAdapter);
                        walletPaymentDetails.setType(billSaveViewBinding.spnWallet.getSelectedItem().toString());
                        walletPaymentDetails.setTrnac(paymentModeResultItems.get(billSaveViewBinding.spnWallet.getSelectedItemPosition()).getACID());
                        break;

                }


            }

        });
    }


    private void completeBilling() {


        GlobalValues.chalanNo = "";
        Intent intent = new Intent(BillSaveView.this, SalesMenuView.class);
        GlobalFunctions.clearAllSingleTonInstances();

        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PreferenceUtils.lockEditMode(this, false);
        startActivity(intent);
    }

    private void initListener() {

        if (PreferenceUtils.retriveBillEditOption(this)) {
            mode = "Edit";
        }

       /* billSaveViewBinding.btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Double.parseDouble(dataModel.getBalanceAmount()) > 0) {
                    GlobalFunctions.showToast("Sorry please clear the balance amount and proceed");
                } else {
                    saveData();

                }

            }
        });*/
        // billSaveViewBinding.btnSave.setOnClickListener(v -> BillSaveViewModelProvider.getInstance().saveData(itemDetailsResult, billSaveViewBinding.etTenderAmount.getText().toString(),customerName,customerAddress,mode));

        billSaveViewBinding.bslPaymentModes.tvTitle.setOnClickListener(v -> {
            if (sheetBehavior.getState() != BottomSheetBehavior.STATE_EXPANDED) {
                sheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
            } else {

                sheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            }
        });


    }

    private void saveData() {
        List<PaymentDetails> paymentDetailsList = new ArrayList<>();
        String cashAmount = billSaveViewBinding.etCash.getText().toString();
        String cardAmount = billSaveViewBinding.etCard.getText().toString();
        String samridhiCardAmount = billSaveViewBinding.etSamridhiCard.getText().toString();
        String walletAmount = billSaveViewBinding.etWallet.getText().toString();

        String returnAmount = billSaveViewBinding.etCashReturn.getText().toString();


        if (itemDetailsResult.getBillingCalculationDTO().getNetAmount() == 0) {
            cashPaymentDetails.setAmount(Double.parseDouble("0"));

            cashPaymentDetails.setPaymentMode("CASH");
            cashPaymentDetails.setReturnAmount(Double.parseDouble(returnAmount));
            paymentDetailsList.add(cashPaymentDetails);
        }


        if (!cashAmount.isEmpty()) {


            if (Double.parseDouble(cashAmount) > 0) {

                cashPaymentDetails.setAmount(Double.parseDouble(cashAmount));

                cashPaymentDetails.setPaymentMode("CASH");
                cashPaymentDetails.setReturnAmount(Double.parseDouble(returnAmount));
                paymentDetailsList.add(cashPaymentDetails);


            }

        }
        if (!cardAmount.isEmpty()) {
            if (Double.parseDouble(cardAmount) > 0) {

                cardPaymentDetails.setPaymentMode("CARD");
                cardPaymentDetails.setAmount(Double.parseDouble(cardAmount));
                cardPaymentDetails.setName(billSaveViewBinding.spnCouponMode.getSelectedItem().toString());
                cardPaymentDetails.setApprovalCode(billSaveViewBinding.etApprovalCode.getText().toString());
                cardPaymentDetails.setNumber(billSaveViewBinding.etCardNumber.getText().toString());
                paymentDetailsList.add(cardPaymentDetails);


            }
        }
        if (!walletAmount.isEmpty()) {
            if (Double.parseDouble(walletAmount) > 0) {
                walletPaymentDetails.setAmount(Double.parseDouble(walletAmount));
                walletPaymentDetails.setNumber(billSaveViewBinding.etWalletCardNumber.getText().toString());
                paymentDetailsList.add(walletPaymentDetails);
            }
        }
        if (!samridhiCardAmount.isEmpty()) {
            if (Double.parseDouble(samridhiCardAmount) > 0) {
                samridhiCardPaymentDetails.setAmount(Double.parseDouble(samridhiCardAmount));
                samridhiCardPaymentDetails.setNumber(billSaveViewBinding.etSamridhiCardNumber.getText().toString());
                samridhiCardPaymentDetails.setName(billSaveViewBinding.etSamridhiCardHolderName.getText().toString());


                paymentDetailsList.add(samridhiCardPaymentDetails);
            }
        }


        BillSaveViewModelProvider.getInstance().saveData(itemDetailsResult, customerName, customerAddress, customerPhone, paymentDetailsList);
    }


    private void retriveBillData() {
        Intent i = getIntent();
        itemDetailsResult = (ItemDetailsResult) Objects.requireNonNull(i.getExtras()).get("saveData");


        customerName = i.getStringExtra("customerName");
        customerAddress = i.getStringExtra("customerAddress");
        customerPhone = i.getStringExtra("customerPhone");
    }


    private void setUpBinding() {
        billSaveViewBinding = com.example.androidbilling.databinding.ActivityBillSaveViewBinding.inflate(getLayoutInflater());
        View view = billSaveViewBinding.getRoot();
        setContentView(view);


    }


    public void showPaymentOptions(String mode) {

        GlobalValues.selected_payment_mode = mode;
        setActionBarTitle(mode + " Mode");
        sheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        BillSaveViewModelProvider.getInstance().filterPaymentMode();

        switch (mode) {
            case "Cash":
                GlobalValues.mode_validation = GlobalValues.DEFAULT_MODE;
                //Cash Inteface visible
                billSaveViewBinding.llCashModeInterface.setVisibility(View.VISIBLE);
                billSaveViewBinding.etCashTender.requestFocus();


                //Other interface gone
                billSaveViewBinding.llCoupenModeInterface.setVisibility(View.GONE);
                billSaveViewBinding.llSamriddhiCardModeInterface.setVisibility(View.GONE);
                billSaveViewBinding.llWalletInterface.setVisibility(View.GONE);


                break;
            case "Card":
                GlobalValues.mode_validation = GlobalValues.DEFAULT_MODE;
                //Cash Inteface visible
                billSaveViewBinding.llCoupenModeInterface.setVisibility(View.VISIBLE);
                billSaveViewBinding.etCashTender.requestFocus();


                //Other interface gone
                billSaveViewBinding.llCashModeInterface.setVisibility(View.GONE);
                billSaveViewBinding.llSamriddhiCardModeInterface.setVisibility(View.GONE);

                billSaveViewBinding.llWalletInterface.setVisibility(View.GONE);
                break;


            case "SamridhiCard":
                billSaveViewBinding.etSamridhiCardAmoun.requestFocus();
                GlobalValues.mode_validation = GlobalValues.SAMRIDHI_MODE;
                //Coupon Inteface visible
                billSaveViewBinding.llSamriddhiCardModeInterface.setVisibility(View.VISIBLE);

                //Other interface gone
                billSaveViewBinding.llCashModeInterface.setVisibility(View.GONE);
                billSaveViewBinding.llCoupenModeInterface.setVisibility(View.GONE);
                billSaveViewBinding.llWalletInterface.setVisibility(View.GONE);
                break;


            case "Wallet":

                billSaveViewBinding.etWalletAmount.requestFocus();
                GlobalValues.mode_validation = GlobalValues.DEFAULT_MODE;
                //Coupon Inteface visible
                billSaveViewBinding.llWalletInterface.setVisibility(View.VISIBLE);

                //Other interface gone
                billSaveViewBinding.llCashModeInterface.setVisibility(View.GONE);
                billSaveViewBinding.llCoupenModeInterface.setVisibility(View.GONE);
                billSaveViewBinding.llSamriddhiCardModeInterface.setVisibility(View.GONE);

                break;

        }


    }


    private void setActionBarTitle(String mode) {
        Objects.requireNonNull(getSupportActionBar()).setTitle(mode);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.bill_save_menu, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.menu_save) {
            if (!isSaveButtonClicked) {
                if (Double.parseDouble(billSaveViewBinding.etBalance.getText().toString()) > 0) {
                    GlobalFunctions.showToast("Please clear the balance before so you can proceed.");


                } else {
                    isSaveButtonClicked = true;
                    saveData();
                }
                //Navigate to another page
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    public void clearSamridhiData() {
        dataModel.setSamriddhiAmount("0");
    }

    public void clearOtherModeData() {

        dataModel.setCashAmount("0");
        dataModel.setCardAmount("0");
        dataModel.setCashReturn("0");
        dataModel.setCashTender("0");
        dataModel.setWalletAmount("0");
    }
}