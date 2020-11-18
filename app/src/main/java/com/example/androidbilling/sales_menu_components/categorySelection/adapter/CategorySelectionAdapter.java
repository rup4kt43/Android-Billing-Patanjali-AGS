package com.example.androidbilling.sales_menu_components.categorySelection.adapter;

import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidbilling.R;
import com.example.androidbilling.databinding.CategoryRvContainerBinding;
import com.example.androidbilling.sales_menu_components.categorySelection.dto.VerticalDTO;
import com.example.androidbilling.sales_menu_components.categorySelection.provider.CategoryViewModelProvider;
import com.example.androidbilling.sales_menu_components.categorySelection.view.CategorySelectionView;
import com.example.androidbilling.sales_menu_components.categorySelection.viewModel.CategoryViewModel;
import com.example.androidbilling.sales_menu_components.salesmenu.provider.SalesMenuViewModelProvider;
import com.example.androidbilling.sales_menu_components.salesmenu.view.SalesMenuView;
import com.example.androidbilling.utilities.global.GlobalValues;

import java.util.ArrayList;
import java.util.List;

public class CategorySelectionAdapter extends RecyclerView.Adapter<CategorySelectionAdapter.CustomCategoryListViewHolder> {
    private CategorySelectionView context;
    private List<VerticalDTO> categoryList;
    boolean flag ;


    private int checkCount = 0;


    public CategorySelectionAdapter(CategorySelectionView categorySelectionView, List<VerticalDTO> strings) {
        this.context = categorySelectionView;
        this.categoryList = strings;
    }

    @NonNull
    @Override
    public CustomCategoryListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CategoryRvContainerBinding categoryRvContainerBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.category_rv_container, parent, false);
        return new CustomCategoryListViewHolder(categoryRvContainerBinding);


    }

    @Override
    public void onBindViewHolder(@NonNull CustomCategoryListViewHolder holder, int position) {
        flag=false;



        holder.categoryBinding.tvCategory.setText(categoryList.get(position).getVerticalName());
        holder.categoryBinding.checkbox.setChecked(categoryList.get(position).getVerticalCheckedStatus()==1);

 /*       holder.categoryBinding.llCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!holder.categoryBinding.checkbox.isChecked()){
                    holder.categoryBinding.checkbox.setChecked(true);

                    SalesMenuViewModelProvider.getInstance().updateCategorySelection(categoryList.get(position).getVerticalName(),1);
                    SalesMenuViewModelProvider.getInstance().retriveVertical();
                    notifyDataSetChanged();
                }else{
                    holder.categoryBinding.checkbox.setChecked(false);

                    SalesMenuViewModelProvider.getInstance().updateCategorySelection(categoryList.get(position).getVerticalName(),0);
                    SalesMenuViewModelProvider.getInstance().retriveVertical();
                    notifyDataSetChanged();
                }

            }
        });*/

        holder.categoryBinding.checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {



                CheckBoxAsyncTask checkBoxAsyncTask = new CheckBoxAsyncTask();


                if(isChecked){


                    checkBoxAsyncTask.doInBackground(categoryList.get(position).getVerticalName(),"1");

                    /*SalesMenuViewModelProvider.getInstance().updateCategorySelection(categoryList.get(position).getVerticalName(),1);
                    SalesMenuViewModelProvider.getInstance().retriveVertical();
                    notifyDataSetChanged();*/
                }else{
                    checkBoxAsyncTask.doInBackground(categoryList.get(position).getVerticalName(),"0");
               /*     holder.categoryBinding.checkbox.setChecked(false);

                    SalesMenuViewModelProvider.getInstance().updateCategorySelection(categoryList.get(position).getVerticalName(),0);
                    SalesMenuViewModelProvider.getInstance().retriveVertical();
                    notifyDataSetChanged();*/
                }
            }
        });











    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    public static class CustomCategoryListViewHolder extends RecyclerView.ViewHolder {
        CategoryRvContainerBinding categoryBinding;

        public CustomCategoryListViewHolder(@NonNull CategoryRvContainerBinding itemView) {
            super(itemView.getRoot());
            this.categoryBinding = itemView;

        }
    }

    public boolean isAllChecked(){
        if(checkCount==categoryList.size()){
            return true;
        }else{
            return false;
        }
    }


    public class CheckBoxAsyncTask extends AsyncTask<String,String,String>{

        @Override
        protected String doInBackground(String... strings) {

            String categoryName = strings[0];
            String status = strings[1];
            CategoryViewModelProvider.getInstance().updateCategorySelection(categoryName,Integer.parseInt(status));

            return null;
        }


    }
}
