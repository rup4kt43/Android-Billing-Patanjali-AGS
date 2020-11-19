package com.example.androidbilling.entries.customer.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.example.androidbilling.R;
import com.example.androidbilling.databinding.ActivityCustomEntryViewBinding;
import com.example.androidbilling.entries.customer.model.StateResultList;
import com.example.androidbilling.entries.customer.repository.CustomerEntryRepository;
import com.example.androidbilling.entries.customer.viewmodel.CustomerEntryViewModel;
import com.example.androidbilling.entries.customer.viewmodel.CustomerEntryViewModelFactory;
import com.example.androidbilling.utilities.global.GlobalFunctions;
import com.example.androidbilling.utilities.global.SweetToast;

import java.util.List;

import static android.text.InputType.TYPE_NULL;

public class CustomEntryView extends AppCompatActivity {

    private CustomerEntryViewModel customerEntryViewModel;
    private ActivityCustomEntryViewBinding customEntryViewBinding;

    private static String[] paymentMethodList, gstList, salesList, integationList;

    private String payMethodValue, stateValue, districtValue, categoryValue, gstTypeValue, salesTypeValue, integationTypeValue;
    private List<String> districtList;
    private List<String> stateList;
    private List<String> categoryList;

    private List<StateResultList> allStateListData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar().setElevation(0);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getSupportActionBar().setTitle("Customer Entry");

        setupViewBinding();
        loadStringArray();
        initViewModel();
        initObserver();

        initCompenent();
        setupOnClick();

        // fetch from api
        customerEntryViewModel.getCategoryList();
        customerEntryViewModel.getStateList();

    }

    private void setupViewBinding() {
        customEntryViewBinding = ActivityCustomEntryViewBinding.inflate(getLayoutInflater());
        View view = customEntryViewBinding.getRoot();
        setContentView(view);
    }

    private void loadStringArray() {
        paymentMethodList = getResources().getStringArray(R.array.payment_methods);
        gstList = getResources().getStringArray(R.array.gst_type);
        salesList = getResources().getStringArray(R.array.sales_type);
        integationList = getResources().getStringArray(R.array.integation_type);
    }

    private void initViewModel() {
        customerEntryViewModel = new ViewModelProvider(this, new CustomerEntryViewModelFactory()).get(CustomerEntryViewModel.class);
    }

    private void initObserver() {

        customerEntryViewModel.getErrorMessageLiveData().observe(this, s -> {
            SweetToast.info(this, s);
        });

        customerEntryViewModel.getCategoryListLiveData().observe(this, categoryResultLists -> {
            categoryList = categoryResultLists;
            customEntryViewBinding.acCategory.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, categoryResultLists));
        });

        customerEntryViewModel.getStateListLiveData().observe(this, stateResultLists -> {
            allStateListData = stateResultLists;
            stateList = customerEntryViewModel.getStateName(stateResultLists);
            customEntryViewBinding.acState.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, stateList));
        });

        customerEntryViewModel.getDistrictListLiveData().observe(this, districtResultLists -> {
            districtList = districtResultLists;
            customEntryViewBinding.acDistrict.setText(null);
            customEntryViewBinding.acDistrict.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, districtResultLists));
        });

        customerEntryViewModel.getCustomerEntryResponseLiveData().observe(this, customerEntryResponse -> {
            SweetToast.info(this, customerEntryResponse.getResult());
            GlobalFunctions.dismissProgressDialog();
            CustomerEntryRepository.clearInstance();
            finish();
        });

    }


    private void initCompenent() {

        customEntryViewBinding.acPayMethod.setInputType(TYPE_NULL);
        customEntryViewBinding.acGstType.setInputType(TYPE_NULL);
        customEntryViewBinding.acSalesType.setInputType(TYPE_NULL);
        customEntryViewBinding.acIntegationType.setInputType(TYPE_NULL);
        customEntryViewBinding.acState.setInputType(TYPE_NULL);
        customEntryViewBinding.acDistrict.setInputType(TYPE_NULL);
        customEntryViewBinding.acCategory.setInputType(TYPE_NULL);

        customEntryViewBinding.acPayMethod.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, paymentMethodList));
        customEntryViewBinding.acGstType.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, gstList));
        customEntryViewBinding.acSalesType.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, salesList));
        customEntryViewBinding.acIntegationType.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, integationList));

    }

    private void setupOnClick() {

        customEntryViewBinding.acState.setOnItemClickListener((parent, view, position, id) -> {
            customEntryViewBinding.acState.setError(null);
            stateValue = customerEntryViewModel.getStateAreaCode(allStateListData, stateList.get(position));
            customerEntryViewModel.getDistrictList(stateList.get(position));
        });
        customEntryViewBinding.acPayMethod.setOnItemClickListener((parent, view, position, id) -> {
            customEntryViewBinding.acPayMethod.setError(null);
            payMethodValue = paymentMethodList[position];
        });
        customEntryViewBinding.acSalesType.setOnItemClickListener((parent, view, position, id) -> {
            customEntryViewBinding.acSalesType.setError(null);
            salesTypeValue = salesList[position];
        });
        customEntryViewBinding.acGstType.setOnItemClickListener((parent, view, position, id) -> {
            customEntryViewBinding.acGstType.setError(null);
            gstTypeValue = gstList[position];
        });
        customEntryViewBinding.acCategory.setOnItemClickListener((parent, view, position, id) -> {
            customEntryViewBinding.acCategory.setError(null);
            categoryValue = categoryList.get(position);
        });
        customEntryViewBinding.acIntegationType.setOnItemClickListener((parent, view, position, id) -> {
            customEntryViewBinding.acIntegationType.setError(null);
            integationTypeValue = integationList[position];
        });
        customEntryViewBinding.acDistrict.setOnItemClickListener((parent, view, position, id) -> {
            customEntryViewBinding.acDistrict.setError(null);
            districtValue = districtList.get(position);
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.bill_save_menu, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();

        if (id == android.R.id.home) {
            CustomerEntryRepository.clearInstance();
            finish();

        } else if (id == R.id.menu_save) {
            submit();
        }
        return super.onOptionsItemSelected(item);
    }

    private void submit() {

        String email = customEntryViewBinding.etEmail.getText().toString();
        String mobile = customEntryViewBinding.etMobile.getText().toString();
        String customerId = customEntryViewBinding.etCustomerId.getText().toString();
        String customerName = customEntryViewBinding.etCustomerName.getText().toString();
        String gstNo = customEntryViewBinding.etGst.getText().toString();
        String panNo = customEntryViewBinding.etPan.getText().toString();
        String address = customEntryViewBinding.etAddress.getText().toString();
        String city = customEntryViewBinding.etCity.getText().toString();
        String postalCode = customEntryViewBinding.etPostalCode.getText().toString();

//        String gstType = customEntryViewBinding.acGstType.getText().toString();
//        String district = customEntryViewBinding.acDistrict.getText().toString();
//        String state = customEntryViewBinding.acState.getText().toString();
//        String category = customEntryViewBinding.acCategory.getText().toString();
//        String salesType = customEntryViewBinding.acSalesType.getText().toString();
//        String integation = customEntryViewBinding.acIntegationType.getText().toString();
//        String payMode = customEntryViewBinding.acPayMethod.getText().toString();

        String mode = "NEW";
        String acid = "PA95";

        if (customerId.isEmpty()) {
            customEntryViewBinding.etCustomerId.setError("ID is required.");
            return;
        }

        if (customerName.isEmpty()) {
            customEntryViewBinding.etCustomerName.setError("Name is required.");
            return;
        }

        if (payMethodValue == null || payMethodValue.isEmpty()) {
            customEntryViewBinding.acPayMethod.setError("Pay method is required.");
            return;
        }

        if (categoryValue == null || categoryValue.isEmpty()) {
            customEntryViewBinding.acCategory.setError("Category is required.");
            return;
        }

        if (gstTypeValue == null || gstTypeValue.isEmpty()) {
            customEntryViewBinding.acGstType.setError("GST Type is required.");
            return;
        }

//        if (salesTypeValue == null || salesTypeValue.isEmpty()) {
//            customEntryViewBinding.acSalesType.setError("Sales Type is required.");
//            return;
//        }

        if (integationTypeValue == null || integationTypeValue.isEmpty()) {
            customEntryViewBinding.acIntegationType.setError("Integation is required.");
            return;
        }

        if (mobile.isEmpty()) {
            customEntryViewBinding.etMobile.setError("Mobile is required.");
            return;
        }

        if (address.isEmpty()) {
            customEntryViewBinding.etAddress.setError("Address is required.");
            return;
        }

        if (city.isEmpty()) {
            customEntryViewBinding.etCity.setError("City is required.");
            return;
        }

        if (stateValue == null || stateValue.isEmpty()) {
            customEntryViewBinding.acState.setError("State is required.");
            return;
        }

        if (districtValue == null || districtValue.isEmpty()) {
            customEntryViewBinding.acDistrict.setError("District is required.");
            return;
        }

        if (panNo.isEmpty()) {
            customEntryViewBinding.etPan.setError("PAN No. is required.");
            return;
        }

        if (gstTypeValue.equals(gstList[0]) || gstTypeValue.equals(gstList[1])) {

            if (gstNo.isEmpty()) {
                customEntryViewBinding.etGst.setError("GST No. is required.");
                return;
            }

        }

        GlobalFunctions.showProgressDialog("Please wait...", this);
        customerEntryViewModel.saveCustomerEntry(mode, email, customerName, address, panNo, gstNo, gstTypeValue, stateValue, districtValue, mobile, city, postalCode, customerId, categoryValue, integationTypeValue, payMethodValue, salesTypeValue, acid);
    }


}