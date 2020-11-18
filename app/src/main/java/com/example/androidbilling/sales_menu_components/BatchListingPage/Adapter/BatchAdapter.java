package com.example.androidbilling.sales_menu_components.BatchListingPage.Adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidbilling.R;
import com.example.androidbilling.databinding.BatchRvContainerBinding;
import com.example.androidbilling.sales_menu_components.BatchListingPage.BatchList;
import com.example.androidbilling.sales_menu_components.salesmenu.model.pojo.items_details_pojo.BatchesItem;
import com.example.androidbilling.sales_menu_components.salesmenu.model.pojo.items_details_pojo.ItemDetailsResult;
import com.example.androidbilling.utilities.sharedpreference.PreferenceUtils;

public class BatchAdapter extends RecyclerView.Adapter<BatchAdapter.CustomBatchHolder> {
    private BatchList context;
    private ItemDetailsResult itemDetailsResult;

    public BatchAdapter(BatchList batchList, ItemDetailsResult itemDetailsResult) {
        this.context = batchList;
        this.itemDetailsResult = itemDetailsResult;
    }


    @NonNull
    @Override
    public CustomBatchHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        BatchRvContainerBinding binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.batch_rv_container, parent, false);

        return new CustomBatchHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomBatchHolder holder, int position) {
        BatchesItem result = itemDetailsResult.getBatches().get(position);
        holder.batchContainerBinding.setBatchDetails(result);
        holder.batchContainerBinding.cardView.setOnClickListener(v -> context.updateItemCart(holder.getAdapterPosition()));
        PreferenceUtils.lockEditMode(context,true);

    }

    @Override
    public int getItemCount() {
        return itemDetailsResult.getBatches().size();
    }

    public static class CustomBatchHolder extends RecyclerView.ViewHolder {

        private final BatchRvContainerBinding batchContainerBinding;

        public CustomBatchHolder(@NonNull BatchRvContainerBinding itemView) {
            super(itemView.getRoot());
            this.batchContainerBinding = itemView;
        }
    }
}
