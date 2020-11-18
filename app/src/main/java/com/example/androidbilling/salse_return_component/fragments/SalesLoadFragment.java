package com.example.androidbilling.salse_return_component.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.androidbilling.R;
import com.example.androidbilling.databinding.FragmentSalesLoadBinding;
import com.example.androidbilling.salse_return_component.adapter.SalesLoadAdapter;
import com.example.androidbilling.salse_return_component.pojo.SalesReturnBillLoadResultItem;
import com.example.androidbilling.salse_return_component.view.SalesReturnView;

import java.util.List;

public class SalesLoadFragment extends Fragment {
    private final SalesReturnView context;
    private final List<SalesReturnBillLoadResultItem> itemList;
    private FragmentSalesLoadBinding binding;

    public SalesLoadFragment(SalesReturnView context, List<SalesReturnBillLoadResultItem> itemList) {
        this.context = context;
        this.itemList= itemList;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_sales_load, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));


        SalesLoadAdapter adapter = new SalesLoadAdapter(context, itemList);
        binding.recyclerView.setAdapter(adapter);

    }
}
