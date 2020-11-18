package com.example.androidbilling.sales_menu_components.BatchListingPage;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.androidbilling.databinding.ActivityBatchListBinding;
import com.example.androidbilling.sales_menu_components.BatchListingPage.Adapter.BatchAdapter;
import com.example.androidbilling.sales_menu_components.salesmenu.model.pojo.items_details_pojo.ItemDetailsResult;
import com.example.androidbilling.sales_menu_components.salesmenu.provider.SalesMenuViewModelProvider;

import java.util.Objects;

public class BatchList extends AppCompatActivity {
    private ActivityBatchListBinding batchListBinding;
    ItemDetailsResult itemDetailsResult;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setUpBinding();

        Intent i = getIntent();
        itemDetailsResult = (ItemDetailsResult) Objects.requireNonNull(i.getExtras()).get("batchList");

        Objects.requireNonNull(getSupportActionBar()).setTitle(itemDetailsResult.getBatches().get(0).getDesca() + " BATCH LIST");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initObject();


    }


    private void initObject() {
        BatchAdapter batchAdapter = new BatchAdapter(this, itemDetailsResult);
        batchListBinding.recyclerView.setAdapter(batchAdapter);
    }

    private void setUpBinding() {
        batchListBinding = ActivityBatchListBinding.inflate(getLayoutInflater());
        View view = batchListBinding.getRoot();
        setContentView(view);

        batchListBinding.recyclerView.setLayoutManager(new LinearLayoutManager(this));


    }

    public void updateItemCart(int adapterPosition) {
        SalesMenuViewModelProvider.getInstance().addItemToCart(itemDetailsResult.getBatches().get(adapterPosition));
        this.finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }

    @SuppressLint("RtlHardcoded")
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            BatchList.this.finish();
        }

        return super.onOptionsItemSelected(item);
    }
}