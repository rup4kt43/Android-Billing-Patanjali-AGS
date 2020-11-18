package com.example.androidbilling.billing_components.billing_preview.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidbilling.R;
import com.example.androidbilling.billing_components.billing_preview.view.BillingView;
import com.example.androidbilling.databinding.BillPreviewRvContainerBinding;
import com.example.androidbilling.sales_menu_components.salesmenu.model.pojo.items_details_pojo.BatchesItem;
import com.example.androidbilling.sales_menu_components.salesmenu.model.pojo.items_details_pojo.ItemDetailsResult;

public class BillingPreviewAdapter extends RecyclerView.Adapter<BillingPreviewAdapter.CustomBillPreviewHolder> {
    private final ItemDetailsResult itemList;
    private Context context;

    public BillingPreviewAdapter(BillingView billingView, ItemDetailsResult itemDetailsResult) {
        this.context = billingView;
        this.itemList = itemDetailsResult;
    }


    @NonNull
    @Override
    public CustomBillPreviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        BillPreviewRvContainerBinding binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.bill_preview_rv_container, parent, false);

        return new CustomBillPreviewHolder(binding);


    }

    @Override
    public void onBindViewHolder(@NonNull CustomBillPreviewHolder holder, int position) {
        BatchesItem batchesItem = itemList.getBatches().get(position);
        holder.itemView.setBatchDetails(batchesItem);
        holder.itemView.tvSn.setText(String.valueOf(position+1));
        holder.itemView.tvDesca.setSelected(true);
        holder.itemView.tvAmount.setText(String.valueOf(batchesItem.getQuantity()*batchesItem.getMrp()));
    }

    @Override
    public int getItemCount() {
        return itemList.getBatches().size();
    }

    public static class CustomBillPreviewHolder extends RecyclerView.ViewHolder {
        public BillPreviewRvContainerBinding itemView;

        public CustomBillPreviewHolder(@NonNull BillPreviewRvContainerBinding itemView) {
            super(itemView.getRoot());
            this.itemView = itemView;
        }
    }
}
