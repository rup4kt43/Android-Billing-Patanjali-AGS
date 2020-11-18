package com.example.androidbilling.salse_return_component.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.androidbilling.salse_return_component.fragments.SalesLoadFragment;
import com.example.androidbilling.salse_return_component.fragments.SalesReturnFragment;
import com.example.androidbilling.salse_return_component.pojo.SalesReturnBillLoadResultItem;
import com.example.androidbilling.salse_return_component.view.SalesReturnView;

import java.util.List;

public class SalesReturnViewPagerAdapter extends FragmentStatePagerAdapter {


    private final int tabCount;
    private final List<SalesReturnBillLoadResultItem> itemList;

    private Fragment fragment;
    private SalesReturnView context;


    public SalesReturnViewPagerAdapter(SalesReturnView salesReturnView, FragmentManager supportFragmentManager, List<SalesReturnBillLoadResultItem> getBillResult, int num) {
        super(supportFragmentManager);
        this.context = salesReturnView;
        this.tabCount = num;
        this.itemList = getBillResult;
    }


    @NonNull
    @Override
    public Fragment getItem(int i) {
        switch (i) {
            case 0:
                fragment = new SalesLoadFragment(context, itemList);
                return fragment;


            case 1:
                fragment = new SalesReturnFragment();
                return fragment;

            default:
                return fragment;

        }
    }

    @Override
    public int getCount() {
        return tabCount;
    }
}
