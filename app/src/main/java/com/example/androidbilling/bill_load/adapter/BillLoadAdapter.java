package com.example.androidbilling.bill_load.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidbilling.R;
import com.example.androidbilling.bill_load.pojo.GetBillResult;
import com.example.androidbilling.bill_load.pojo.ProdListItem;
import com.example.androidbilling.bill_load.view.BillLoadView;
import com.example.androidbilling.databinding.BillLoadRvContainerBinding;
import com.example.androidbilling.utilities.global.DecimalRoundOffClass;

public class BillLoadAdapter extends RecyclerView.Adapter<BillLoadAdapter.CustomBillLoadViewHolder> {
    private final BillLoadView context;
        private final GetBillResult productList;

        public BillLoadAdapter(BillLoadView billLoadView, GetBillResult getBillResult) {
            this.context = billLoadView;
            this.productList = getBillResult;
        }

        @NonNull
        @Override
        public CustomBillLoadViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            BillLoadRvContainerBinding binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.bill_load_rv_container, parent, false);
            return new CustomBillLoadViewHolder(binding);




        }

        @Override
        public void onBindViewHolder(@NonNull CustomBillLoadViewHolder holder, int position) {
            ProdListItem prodListItem = productList.getProdList().get(position);
            double gstForDiscount = prodListItem.getInddiscount()*prodListItem.getGstrate()/100;
            double disWithGst = prodListItem.getInddiscount() + gstForDiscount;
            Log.e("Status",String.valueOf(disWithGst));
            holder.itemView.tvAmount.setText(DecimalRoundOffClass.roundOff(prodListItem.getNetAmount()));







            holder.itemView.setProduct(prodListItem);

            holder.itemView.tvSn.setText(String.valueOf(position+1));

            holder.itemView.tvDesca.setSelected(true);

        }

        @Override
        public int getItemCount() {
            return productList.getProdList().size();
        }

        public static class CustomBillLoadViewHolder extends RecyclerView.ViewHolder {
            BillLoadRvContainerBinding itemView;

            public CustomBillLoadViewHolder(@NonNull BillLoadRvContainerBinding itemView) {


                super(itemView.getRoot());
                this.itemView = itemView;
            }
    }
}
