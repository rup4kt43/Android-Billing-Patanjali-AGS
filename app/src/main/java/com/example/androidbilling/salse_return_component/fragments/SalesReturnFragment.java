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
import com.example.androidbilling.bill_load.pojo.GetBillResult;
import com.example.androidbilling.databinding.FragmentSalesLoadBinding;
import com.example.androidbilling.salse_return_component.adapter.SalesReturnAdapter;
import com.example.androidbilling.salse_return_component.provider.SalesReturnViewModelProvider;
import com.example.androidbilling.salse_return_component.view.SalesReturnView;

import java.util.Objects;

public class SalesReturnFragment extends Fragment {
    private FragmentSalesLoadBinding binding;
    private SalesReturnAdapter adapter;
    public static GetBillResult billResult = new GetBillResult();


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

        initObserver();
    }

    private void initObserver() {
        SalesReturnViewModelProvider.getInstance().fetchReturnItemList().observe(Objects.requireNonNull(getActivity()), getBillResult -> {
            if (getBillResult != null) {
                billResult = getBillResult;
                adapter = new SalesReturnAdapter((SalesReturnView) getActivity(), getBillResult);
                binding.recyclerView.setAdapter(adapter);
            }
        });
    }
}
