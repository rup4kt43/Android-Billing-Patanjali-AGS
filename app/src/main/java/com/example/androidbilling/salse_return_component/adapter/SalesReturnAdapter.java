package com.example.androidbilling.salse_return_component.adapter;

import android.app.Dialog;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidbilling.R;
import com.example.androidbilling.bill_load.pojo.GetBillResult;
import com.example.androidbilling.bill_load.pojo.ProdListItem;
import com.example.androidbilling.databinding.BillLoadRvContainerBinding;
import com.example.androidbilling.salse_return_component.view.SalesReturnView;
import com.example.androidbilling.utilities.global.DecimalRoundOffClass;
import com.example.androidbilling.utilities.global.GlobalFunctions;

public class SalesReturnAdapter extends RecyclerView.Adapter<SalesReturnAdapter.CustomSalesReturnHolder> {

    private final SalesReturnView context;
    private final GetBillResult itemList;

    public SalesReturnAdapter(SalesReturnView salesReturnView, GetBillResult getBillResult) {
        this.context = salesReturnView;
        this.itemList = getBillResult;

    }

    @NonNull
    @Override
    public CustomSalesReturnHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        BillLoadRvContainerBinding binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.bill_load_rv_container, parent, false);
        return new CustomSalesReturnHolder(binding);


    }

    @Override
    public void onBindViewHolder(@NonNull CustomSalesReturnHolder holder, int position) {
        ProdListItem prodListItem = itemList.getProdList().get(position);
        holder.itemView.setProduct(prodListItem);
        holder.itemView.tvSn.setText(String.valueOf(position + 1));



        double gstForDiscount = prodListItem.getInddiscount()*prodListItem.getGstrate()/100;
        double disWithGst = prodListItem.getInddiscount() + gstForDiscount;
        Log.e("Status",String.valueOf(disWithGst));

        holder.itemView.tvAmount.setText(DecimalRoundOffClass.roundOff(prodListItem.getMrp()*prodListItem.getQuantity()-disWithGst));
        holder.itemView.tvDesca.setSelected(true);

        holder.itemView.llItemLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showItemEditDialog(holder.getAdapterPosition());
            }
        });


    }

    private void showItemEditDialog(int position) {
        ProdListItem prodListItem = itemList.getProdList().get(position);
        Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.item_edit_dialog);
        TextView tv_qty = dialog.findViewById(R.id.tv_qty);
        ImageView iv_minus = dialog.findViewById(R.id.iv_minus);
        ImageView iv_add = dialog.findViewById(R.id.iv_plus);
        Button btn_apply = dialog.findViewById(R.id.btn_apply);
        Button btn_remove = dialog.findViewById(R.id.btn_remove);

        tv_qty.setText(String.valueOf(prodListItem.getQuantity()));


        iv_minus.setOnClickListener(v1 -> {
            double qty = Double.parseDouble(tv_qty.getText().toString());
            Log.e("Qty", qty + "");
            if (qty > 1) {
                tv_qty.setText(String.valueOf(qty - 1));
            } else {
                GlobalFunctions.showToast("Item cannot be decrease more");
            }
        });
        iv_add.setOnClickListener(v12 -> {
            double qty = Double.parseDouble(tv_qty.getText().toString());
            if(qty==prodListItem.getMainQuantity()){
                GlobalFunctions.showToast("Sorry you have reached the maximum quantity for this product!");
            }else{
                tv_qty.setText(String.valueOf(qty+1));
            }

        });

        btn_apply.setOnClickListener(v13 -> {
            double qty = Double.parseDouble(tv_qty.getText().toString());
            itemList.getProdList().get(position).setQuantity(qty);
            notifyDataSetChanged();
            dialog.dismiss();


        });

        btn_remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemList.getProdList().remove(position);
                notifyDataSetChanged();
                dialog.dismiss();
            }
        });


        Window dialogWindow = dialog.getWindow();
        WindowManager m = context.getWindowManager();
        Display d = m.getDefaultDisplay();
        assert dialogWindow != null;
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.width = d.getWidth();

        dialog.show();
    }

    @Override
    public int getItemCount() {
        return itemList.getProdList().size();
    }

    public static class CustomSalesReturnHolder extends RecyclerView.ViewHolder {
        BillLoadRvContainerBinding itemView;

        public CustomSalesReturnHolder(@NonNull BillLoadRvContainerBinding itemView) {
            super(itemView.getRoot());
            this.itemView = itemView;
        }
    }
}
