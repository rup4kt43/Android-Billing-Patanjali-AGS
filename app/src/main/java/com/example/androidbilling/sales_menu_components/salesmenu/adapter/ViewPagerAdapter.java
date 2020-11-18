package com.example.androidbilling.sales_menu_components.salesmenu.adapter;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.androidbilling.sales_menu_components.salesmenu.fragments.ItemCartFragment;
import com.example.androidbilling.sales_menu_components.salesmenu.fragments.ItemListFragment;
import com.example.androidbilling.sales_menu_components.salesmenu.model.pojo.ItemListItem;
import com.example.androidbilling.sales_menu_components.salesmenu.view.SalesMenuView;

import java.util.List;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {




    private final int tabCount;
    private final List<ItemListItem> itemListItems;
    private Fragment fragment;
    private SalesMenuView context;



    public ViewPagerAdapter(SalesMenuView context,FragmentManager supportFragmentManager, int num, List<ItemListItem> itemListItems) {
        super(supportFragmentManager);
        this.context = context;
        this.tabCount = num;
        this.itemListItems = itemListItems;
    }



    @NonNull
    @Override
    public Fragment getItem(int i) {
        switch (i) {
            case 0:
                fragment = new ItemListFragment(itemListItems,context);
                return fragment;


            case 1:
                fragment = new ItemCartFragment();
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
