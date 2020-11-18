package com.example.androidbilling.sales_menu_components.categorySelection.viewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.androidbilling.sales_menu_components.categorySelection.dto.VerticalDTO;
import com.example.androidbilling.sales_menu_components.salesmenu.repository.SalesDatabaseRepository;

import java.util.List;

public class CategoryViewModel extends ViewModel {
    private LiveData<List<VerticalDTO>> verticalData;
    SalesDatabaseRepository salesDatabaseRepository;


    public CategoryViewModel(){
        salesDatabaseRepository = SalesDatabaseRepository.getInstance();

        verticalData = salesDatabaseRepository.fetchVerticalList();
    }


    //------------------------------method for returning data or message---------------------//
    public LiveData<List<VerticalDTO>> fetchVertical() {
        return verticalData;
    }


    public void retriveVertical() {
        salesDatabaseRepository.getVertical();
    }

    public void updateCategorySelection(String categoryName, int status) {
        salesDatabaseRepository.updateCategorySataus(categoryName, status);
    }

}
