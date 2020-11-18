package com.example.androidbilling.sales_menu_components.salesmenu.adapter;

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
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidbilling.R;
import com.example.androidbilling.databinding.FragmentItemcartRvContainerBinding;
import com.example.androidbilling.sales_menu_components.salesmenu.model.pojo.items_details_pojo.BatchesItem;
import com.example.androidbilling.sales_menu_components.salesmenu.provider.SalesMenuViewModelProvider;
import com.example.androidbilling.utilities.global.DecimalRoundOffClass;
import com.example.androidbilling.utilities.global.GlobalFunctions;
import com.example.androidbilling.utilities.global.SweetToast;
import com.example.androidbilling.utilities.sharedpreference.PreferenceUtils;

import java.util.List;
import java.util.Objects;

public class ItemCartAdapter extends RecyclerView.Adapter<ItemCartAdapter.CustomItemCartHolder> {
    FragmentItemcartRvContainerBinding itemcartRvContainerBinding;
    FragmentActivity context;
    List<BatchesItem> itemList;
    boolean isCardClicked = false;


    //----Modes for discount-------------//
    public int PERCENTAGE_MODE = 0;
    public int RUPEES_MODE = 1;
    public int mode = RUPEES_MODE;
    //--------------------------------//

    public ItemCartAdapter(FragmentActivity activity, List<BatchesItem> itemList) {
        this.context = activity;
        this.itemList = itemList;

        SalesMenuViewModelProvider.getInstance().createFinalDataForBillPreview(itemList);
    }


    @NonNull
    @Override
    public CustomItemCartHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        itemcartRvContainerBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.fragment_itemcart_rv_container, parent, false);
        return new CustomItemCartHolder(itemcartRvContainerBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomItemCartHolder holder, int position) {
        BatchesItem batchesItem = itemList.get(position);
        holder.itemCartContainerBinding.setBatchDetails(batchesItem);

        setVisibility(holder);

        //double flatDiscountWithGst  = batchesItem.getFlatDiscount() + batchesItem.getFlatDiscount()*batchesItem.getGs
        double flatDiscountWithGst  = batchesItem.getFlatDiscount() + (batchesItem.getFlatDiscount()*batchesItem.getGstRate()/100);
        holder.itemCartContainerBinding.tvDiscount.setText("Discount : " +DecimalRoundOffClass.roundOff(batchesItem.getAltIndDiscount()+flatDiscountWithGst));


        //listener

        holder.itemCartContainerBinding.cardView.setOnClickListener(v -> {
            int id = holder.itemCartContainerBinding.rgDisMode.getCheckedRadioButtonId();
            switch (id){
                case R.id.rb_rupees:
                    mode = RUPEES_MODE;
                    break;
                case R.id.rb_percentage:
                    mode = PERCENTAGE_MODE;
                    break;
            }

            if (isCardClicked) {
                isCardClicked = false;
                holder.itemCartContainerBinding.llEdit.setVisibility(View.GONE);

            } else {
                isCardClicked = true;
                holder.itemCartContainerBinding.llEdit.setVisibility(View.VISIBLE);

            }
        });

        holder.itemCartContainerBinding.cardView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                int qty = holder.itemCartContainerBinding.getBatchDetails().getQuantity();
                Dialog dialog = new Dialog(context);
                dialog.setCancelable(false);
                dialog.setContentView(R.layout.item_edit_dialog);

                TextView tv_quantity = dialog.findViewById(R.id.tv_qty);
                ImageView iv_add = dialog.findViewById(R.id.iv_plus);
                ImageView iv_minus = dialog.findViewById(R.id.iv_minus);
                ImageView iv_close = dialog.findViewById(R.id.iv_close);
                Button btn_apply = dialog.findViewById(R.id.btn_apply);
                Button btn_remove = dialog.findViewById(R.id.btn_remove);


                tv_quantity.setText(String.valueOf(qty));
                iv_add.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        btn_apply.setText("APPLY");
                        int old_qty = Integer.parseInt(tv_quantity.getText().toString());
                        tv_quantity.setText(String.valueOf(old_qty + 1));
                    }
                });

                iv_minus.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (Integer.parseInt(tv_quantity.getText().toString()) == 1) {


                            GlobalFunctions.showToast("Items cannot be less than one !!");

                        } else {
                            int old_qty = Integer.parseInt(tv_quantity.getText().toString());
                            tv_quantity.setText(String.valueOf(old_qty - 1));
                        }
                    }
                });

                btn_apply.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if(tv_quantity.getText().toString().matches("0")){
                            SweetToast.info(context,"Quantity must be greater than 0 !!");
                            dialog.dismiss();
                        }else{
                            holder.itemCartContainerBinding.getBatchDetails().setQuantity(Integer.parseInt(tv_quantity.getText().toString()));

                            notifyDataSetChanged();
                            dialog.dismiss();
                        }





                    }
                });

                btn_remove.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(itemList.size()==1){
                            PreferenceUtils.lockEditMode(context,false);
                        }
                        itemList.remove(holder.getAdapterPosition());
                        dialog.dismiss();
                        notifyDataSetChanged();
                    }
                });

                iv_close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });


                Window dialogWindow = dialog.getWindow();
                WindowManager m = context.getWindowManager();
                Display d = m.getDefaultDisplay();
                WindowManager.LayoutParams lp = dialogWindow.getAttributes();
                lp.width = d.getWidth() * 1;


                dialog.show();
                return true;
            }
        });


        holder.itemCartContainerBinding.btnApply.setOnClickListener(v -> {
            GlobalFunctions.hideKeyboard(context);
            String disAmount = Objects.requireNonNull(holder.itemCartContainerBinding.etDiscount.getText()).toString();
            String quantity = Objects.requireNonNull(holder.itemCartContainerBinding.etQuantity.getText()).toString();

            if (disAmount.isEmpty() && quantity.isEmpty()) {
                GlobalFunctions.showToast("Please enter the quantity or discount");

            } else {
                double Mrp = holder.itemCartContainerBinding.getBatchDetails().getMrp();
                if (!quantity.matches("")) {

                    if(quantity.matches("0")){
                        holder.itemCartContainerBinding.etQuantity.setText("");
                        SweetToast.info(context,"Quantity must be greater than 0 !!");

                    }
                    else if (Integer.parseInt(quantity) != holder.itemCartContainerBinding.getBatchDetails().getQuantity()) {

                        holder.itemCartContainerBinding.getBatchDetails().setQuantity(Integer.parseInt(quantity));


                        int qty = holder.itemCartContainerBinding.getBatchDetails().getQuantity();
                        Log.e("QTY", qty+"");
                        double grossAmount = holder.itemCartContainerBinding.getBatchDetails().getMrp() * holder.itemCartContainerBinding.getBatchDetails().getQuantity();
                        String discountAmount = DecimalRoundOffClass.roundOff((grossAmount * holder.itemCartContainerBinding.getBatchDetails().getIndividualDiscountRate() / 100));
                        holder.itemCartContainerBinding.getBatchDetails().setIndividualDiscount(Double.parseDouble(discountAmount));


                        notifyDataSetChanged();

                    }
                }

                if (!disAmount.matches("")) {
                    if (mode == RUPEES_MODE) {
                        if (Double.parseDouble(disAmount) > Mrp * holder.itemCartContainerBinding.getBatchDetails().getQuantity()) {
                            GlobalFunctions.showToast("Sorry please enter a valid discount");
                            holder.itemCartContainerBinding.etDiscount.setText("");
                            return;
                        } else {
                            holder.itemCartContainerBinding.getBatchDetails().setAltIndDiscount(Double.parseDouble(disAmount));


                        }
                    } else if (mode == PERCENTAGE_MODE) {
                        if(Double.parseDouble(disAmount)>100){
                            GlobalFunctions.showToast("Sorry please enter a valid discount!");
                            holder.itemCartContainerBinding.etDiscount.setText("");
                            return;
                        }else{
                            double grossAmount = holder.itemCartContainerBinding.getBatchDetails().getMrp() * holder.itemCartContainerBinding.getBatchDetails().getQuantity();
                            String discountAmount = DecimalRoundOffClass.roundOff((grossAmount * Double.parseDouble(disAmount)) / 100);
                            holder.itemCartContainerBinding.getBatchDetails().setAltIndDiscount(Double.parseDouble(discountAmount));
                        }
                        holder.itemCartContainerBinding.getBatchDetails().setIndividualDiscountRate(Double.parseDouble(disAmount));

                    }
                }


                notifyDataSetChanged();


            }
        });

        holder.itemCartContainerBinding.rgDisMode.setOnCheckedChangeListener((group, checkedId) -> {
            switch (checkedId) {
                case R.id.rb_rupees:
                    mode = RUPEES_MODE;
                    //calculate discount amount here
                    break;
                case R.id.rb_percentage:
                    //calculate discount amount here
                    mode = PERCENTAGE_MODE;
                    break;
            }
        });

     /*   holder.itemCartContainerBinding.cardView.setOnLongClickListener(v -> {

            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder
                    .setMessage("Are you sure you want to remove the item?")
                    .setPositiveButton("No", (dialog, which) -> dialog.dismiss())
                    .setNegativeButton("Yes", (dialog, which) -> {
                        int pos = holder.getAdapterPosition();
                        itemList.remove(pos);
                        notifyDataSetChanged();
                    });
            builder.setCancelable(false);
            builder.show();

            return true;
        });*/


    }



    private void setVisibility(CustomItemCartHolder holder) {
        isCardClicked = false;
        holder.itemCartContainerBinding.llEdit.setVisibility(View.GONE);
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public static class CustomItemCartHolder extends RecyclerView.ViewHolder {

        public final FragmentItemcartRvContainerBinding itemCartContainerBinding;

        public CustomItemCartHolder(@NonNull FragmentItemcartRvContainerBinding itemView) {
            super(itemView.getRoot());
            this.itemCartContainerBinding = itemView;
        }

    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }
}
