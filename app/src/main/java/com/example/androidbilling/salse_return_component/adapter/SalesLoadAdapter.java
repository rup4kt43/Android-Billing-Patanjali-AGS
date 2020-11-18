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
import com.example.androidbilling.bill_load.pojo.ProdListItem;
import com.example.androidbilling.databinding.SalesReturnBillLoadRvContainerBindingImpl;
import com.example.androidbilling.salse_return_component.pojo.SalesReturnBillLoadResultItem;
import com.example.androidbilling.salse_return_component.provider.SalesReturnViewModelProvider;
import com.example.androidbilling.salse_return_component.view.SalesReturnView;
import com.example.androidbilling.utilities.global.DecimalRoundOffClass;
import com.example.androidbilling.utilities.global.GlobalFunctions;

import java.util.List;

public class SalesLoadAdapter extends RecyclerView.Adapter<SalesLoadAdapter.CustomSalesLoadHolder> {


    private final SalesReturnView context;
    private final List<SalesReturnBillLoadResultItem> itemList;


    public SalesLoadAdapter(SalesReturnView salesReturnView, List<SalesReturnBillLoadResultItem> getBillResult) {
        this.context = salesReturnView;
        this.itemList = getBillResult;

    }

    @NonNull
    @Override
    public CustomSalesLoadHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        SalesReturnBillLoadRvContainerBindingImpl binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.sales_return_bill_load_rv_container, parent, false);
        return new CustomSalesLoadHolder(binding);


    }

    @Override
    public void onBindViewHolder(@NonNull CustomSalesLoadHolder holder, int position) {


        SalesReturnBillLoadResultItem prodListItem = itemList.get(position);
        holder.itemView.setProduct(prodListItem);
        holder.itemView.tvSn.setText(String.valueOf(position + 1));


        double gstForDiscount = prodListItem.getAltIntDiscount() * prodListItem.getGSTRATE() / 100;
        double disWithGst = prodListItem.getAltIntDiscount() + gstForDiscount;
        Log.e("Status", String.valueOf(disWithGst));

        double totalDiscountInReturn = prodListItem.getTotalDiscountInReturn();
        double totalDiscountInReturnWithGst = totalDiscountInReturn + (totalDiscountInReturn * prodListItem.getGSTRATE() / 100);

        holder.itemView.tvAmount.setText(DecimalRoundOffClass.roundOff(prodListItem.getMRP() * prodListItem.getRemainingQuantity() - totalDiscountInReturnWithGst));
        holder.itemView.tvDesca.setSelected(true);

        holder.itemView.llItemLayout.setOnClickListener(v -> {
            boolean checkIfItemExistOnReturn = SalesReturnViewModelProvider.getInstance().checkIfItemExist(prodListItem.getMCODE(), prodListItem.getBATCH(), prodListItem.getMFGDATE(), prodListItem.getEXPDATE(), prodListItem.getPrate());

            if (!checkIfItemExistOnReturn) {


                Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.sales_return_qty_edit_box);
                TextView tv_qty = dialog.findViewById(R.id.tv_qty);
                ImageView iv_minus = dialog.findViewById(R.id.iv_minus);
                ImageView iv_add = dialog.findViewById(R.id.iv_plus);
                ImageView iv_close = dialog.findViewById(R.id.iv_close);
                Button btn_apply = dialog.findViewById(R.id.btn_apply);
                Button btn_remove = dialog.findViewById(R.id.btn_remove);
                btn_remove.setVisibility(View.GONE);

                tv_qty.setText(String.valueOf(prodListItem.getRemainingQuantity()));
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
                    if (qty < prodListItem.getRemainingQuantity()) {
                        tv_qty.setText(String.valueOf(qty + 1));
                    } else {
                        GlobalFunctions.showToast("Item cannot be increased more");
                    }
                });

                iv_close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                btn_apply.setOnClickListener(v13 -> {
                    double qty = Double.parseDouble(tv_qty.getText().toString());
                    double disPerItem = prodListItem.getTotalDiscountInReturn() / prodListItem.getRealQty();

                    Log.e("DisPerItem", String.valueOf(disPerItem));
                    Log.e("DisPerItem", String.valueOf(disPerItem));

                    double returnItemDiscount = disPerItem * qty;
                    double returnItemDiscountWithGst = returnItemDiscount + (returnItemDiscount * prodListItem.getGSTRATE() / 100);


                    SalesReturnBillLoadResultItem salesReturnItem = new SalesReturnBillLoadResultItem();
                    salesReturnItem.setbILLTO(itemList.get(holder.getAdapterPosition()).getBILLTO());
                    salesReturnItem.setbILLTOADD(itemList.get(holder.getAdapterPosition()).getBILLTOADD());
                    salesReturnItem.setbILLTOMOB(itemList.get(holder.getAdapterPosition()).getBILLTOMOB());
                    salesReturnItem.settRNAC(itemList.get(holder.getAdapterPosition()).getTRNAC());


                    ProdListItem prodForReturn = new ProdListItem();
                    prodForReturn.setTotalIntDiscount(prodListItem.getTotalDiscountInReturn());
                    prodForReturn.setOriginalQty(prodListItem.getRealQty());
                    prodForReturn.setMainQuantity(prodListItem.getSTOCK());
                    prodForReturn.setQuantity(qty);
                    prodForReturn.setAltIndDiscount(returnItemDiscountWithGst);
                    prodForReturn.setTrnMode(prodListItem.getTrnMode());
                    prodForReturn.setParAc(prodListItem.getPARAC());
                    prodForReturn.setBatch(prodListItem.getBATCH());
                    prodForReturn.setBc(prodListItem.getBC());
                    prodForReturn.setExpdate(prodListItem.getEXPDATE());
                    prodForReturn.setInddiscount(returnItemDiscount);
                    prodForReturn.setInddiscountrate(prodListItem.getINDDISCOUNTRATE());
                    prodForReturn.setItemdesc(prodListItem.getITEMDESC());
                    prodForReturn.setMcode(prodListItem.getMCODE());
                    prodForReturn.setMfgdate(prodListItem.getMFGDATE());
                    prodForReturn.setMrp(prodListItem.getMRP());
                    prodForReturn.setRate(prodListItem.getRATE());
                    prodForReturn.setUnit(prodListItem.getUNIT());
                    prodForReturn.setWarehouse(prodListItem.getWAREHOUSE());
                    prodForReturn.setPrate(prodListItem.getPrate());
                    prodForReturn.setGstrate(prodListItem.getGSTRATE());
                    prodForReturn.setBillTo(prodListItem.getBILLTO());
                    prodForReturn.setBillToAdd(prodListItem.getBILLTOADD());
                    prodForReturn.setBillToMob(prodListItem.getBILLTOMOB());
                    prodForReturn.setTrnac(prodListItem.getTRNAC());
                    prodForReturn.setTrnMode(prodListItem.getTrnMode());


                    SalesReturnViewModelProvider.getInstance().addItemToResultList(prodForReturn);


                    dialog.dismiss();

                });
                Window dialogWindow = dialog.getWindow();
                WindowManager m = context.getWindowManager();
                Display d = m.getDefaultDisplay();
                assert dialogWindow != null;
                WindowManager.LayoutParams lp = dialogWindow.getAttributes();
                lp.width = d.getWidth();

                dialog.show();
            } else {
                GlobalFunctions.showToast("Item has already been added to return list. Edit the item in the return list.");
            }


        });

    }


    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public static class CustomSalesLoadHolder extends RecyclerView.ViewHolder {
        SalesReturnBillLoadRvContainerBindingImpl itemView;

        public CustomSalesLoadHolder(@NonNull SalesReturnBillLoadRvContainerBindingImpl itemView) {
            super(itemView.getRoot());
            this.itemView = itemView;
        }
    }
}
