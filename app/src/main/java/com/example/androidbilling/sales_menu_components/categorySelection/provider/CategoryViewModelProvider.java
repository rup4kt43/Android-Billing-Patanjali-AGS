package com.example.androidbilling.sales_menu_components.categorySelection.provider;

import androidx.lifecycle.ViewModelProvider;

import com.example.androidbilling.sales_menu_components.categorySelection.view.CategorySelectionView;
import com.example.androidbilling.sales_menu_components.categorySelection.viewModel.CategoryViewModel;
import com.example.androidbilling.sales_menu_components.categorySelection.viewModel.CategoryViewModelFactory;
import com.example.androidbilling.sales_menu_components.salesmenu.view.SalesMenuView;
import com.example.androidbilling.sales_menu_components.salesmenu.viewmodel.SalesViewModel;
import com.example.androidbilling.sales_menu_components.salesmenu.viewmodel.SalesViewModelFactory;

public class CategoryViewModelProvider {


    static CategoryViewModel instance;
    private static CategorySelectionView mainContext;



    public CategoryViewModelProvider(CategorySelectionView mainContext){
        this.mainContext = mainContext;
    }

    public static CategoryViewModel getInstance() {

        if (instance == null) {
            instance = new ViewModelProvider(mainContext, new CategoryViewModelFactory()).get(CategoryViewModel.class);
        }
        return instance;
    }

    public static void clearInstance(){
        instance = null;
    }


}
