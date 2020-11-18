package com.example.androidbilling.sales_menu_components.categorySelection.view;

import android.annotation.SuppressLint;
import android.content.ComponentCallbacks2;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.androidbilling.databinding.ActivityCategorySelectionBinding;
import com.example.androidbilling.sales_menu_components.categorySelection.adapter.CategorySelectionAdapter;
import com.example.androidbilling.sales_menu_components.categorySelection.dto.VerticalDTO;
import com.example.androidbilling.sales_menu_components.categorySelection.provider.CategoryViewModelProvider;
import com.example.androidbilling.sales_menu_components.salesmenu.provider.SalesMenuViewModelProvider;

import java.util.List;
import java.util.Objects;

public class CategorySelectionView extends AppCompatActivity implements ComponentCallbacks2 {
    private ActivityCategorySelectionBinding binding;
    private CategorySelectionAdapter categorySelectionAdapter;
    int checkCount = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setupViewBinding();

        setUpViewModelProvider();

        getSupportActionBar().setElevation(0);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Category");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initObserver();


        initListener();
        binding.cbAll.setOnCheckedChangeListener(mListener);


    }

    private void setUpViewModelProvider() {

        new CategoryViewModelProvider(this);

        CategoryViewModelProvider.getInstance().retriveVertical();

    }


    private CompoundButton.OnCheckedChangeListener mListener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if (isChecked) {
                CategoryViewModelProvider.getInstance().updateCategorySelection("", 1);
                CategoryViewModelProvider.getInstance().retriveVertical();
                categorySelectionAdapter.notifyDataSetChanged();
            } else {
                binding.cbAll.setChecked(false);
                CategoryViewModelProvider.getInstance().updateCategorySelection("", 0);
                CategoryViewModelProvider.getInstance().retriveVertical();
                categorySelectionAdapter.notifyDataSetChanged();
            }
        }
    };

    private void initListener() {



    /*    binding.llSelectAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(binding.cbAll.isChecked()){
                    binding.cbAll.setChecked(false);
                    SalesMenuViewModelProvider.getInstance().updateCategorySelection("",0);
                    SalesMenuViewModelProvider.getInstance().retriveVertical();
                    categorySelectionAdapter.notifyDataSetChanged();
                }else{
                    binding.cbAll.setChecked(true);
                    SalesMenuViewModelProvider.getInstance().updateCategorySelection("",1);
                    SalesMenuViewModelProvider.getInstance().retriveVertical();
                    categorySelectionAdapter.notifyDataSetChanged();
                }

            }
        });*/


    }


    private void initObserver() {
        CategoryViewModelProvider.getInstance().fetchVertical().observe(this, new Observer<List<VerticalDTO>>() {
            @Override
            public void onChanged(List<VerticalDTO> strings) {
                checkCount = 0;

                for (int i = 0; i < strings.size(); i++) {
                    if (strings.get(i).getVerticalCheckedStatus() == 1) {
                        checkCount += 1;
                    }
                }
                binding.cbAll.setOnCheckedChangeListener(null);
                binding.cbAll.setChecked(checkCount == strings.size());
                binding.cbAll.setOnCheckedChangeListener(mListener);


                categorySelectionAdapter = new CategorySelectionAdapter(CategorySelectionView.this, strings);
                binding.recyclerView.setLayoutManager(new LinearLayoutManager(CategorySelectionView.this));
                binding.recyclerView.setAdapter(categorySelectionAdapter);


            }
        });
    }


    private void setupViewBinding() {
        binding = com.example.androidbilling.databinding.ActivityCategorySelectionBinding.inflate(getLayoutInflater());

        View view = binding.getRoot();
        setContentView(view);


    }


    @SuppressLint("RtlHardcoded")
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            CategorySelectionView.this.finish();
            clearData();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        clearData();
        super.onBackPressed();
    }

    private void clearData() {
        CategoryViewModelProvider.clearInstance();
    }

    public void removeAllCheck() {

        binding.cbAll.setChecked(false);
    }

    public void setAllCheck() {
        binding.cbAll.setChecked(true);
    }


    @Override
    protected void onResume() {

        super.onResume();
    }
}