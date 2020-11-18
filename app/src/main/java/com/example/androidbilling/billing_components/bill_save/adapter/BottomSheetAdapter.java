package com.example.androidbilling.billing_components.bill_save.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidbilling.R;
import com.example.androidbilling.billing_components.bill_save.view.BillSaveView;
import com.example.androidbilling.databinding.BottomSheetRvContainerBinding;
import com.example.androidbilling.utilities.global.GlobalValues;

public class BottomSheetAdapter extends RecyclerView.Adapter<BottomSheetAdapter.BottomSheetItemHolder> {
    private final String[] list;
    private BillSaveView context;

    public BottomSheetAdapter(BillSaveView billSaveView, String[] paymentList) {

        this.context = billSaveView;
        this.list = paymentList;
        context.showPaymentOptions(paymentList[GlobalValues.mode_selected_position]);

    }

    @NonNull
    @Override
    public BottomSheetItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        BottomSheetRvContainerBinding binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.bottom_sheet_rv_container, parent, false);

        return new BottomSheetItemHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull BottomSheetItemHolder holder, int position) {
        holder.binding.tvMode.setText(list[position]);
        holder.binding.checkbox.setChecked(GlobalValues.mode_selected_position == position);


        holder.binding.llMode.setOnClickListener(v -> {
            if (!holder.binding.checkbox.isChecked()) {
                holder.binding.checkbox.setChecked(true);


            }
        });

        holder.binding.checkbox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                if (GlobalValues.mode_validation == GlobalValues.DEFAULT_MODE) {

                    if (list[position].equalsIgnoreCase("SamridhiCard")) {

                        showAlertDialog("This will clear all the data of other modes. Are you Sure?", position);

                    } else {

                        GlobalValues.mode_selected_position = position;
                        context.showPaymentOptions(list[position]);
                        notifyDataSetChanged();
                    }

                } else if (GlobalValues.mode_validation == GlobalValues.SAMRIDHI_MODE) {
                    if (!list[position].equalsIgnoreCase("SamridhiCard")) {

                        showAlertDialog("This will clear the data of Samridhi Card Mode. Are you Sure?", holder.getAdapterPosition());
                    } else {

                        GlobalValues.mode_selected_position = position;
                        context.showPaymentOptions(list[position]);
                        notifyDataSetChanged();
                    }
                }
                // GlobalValues.selected_payment_mode = list.get(holder.getAdapterPosition()).getPAYMENTMODENAME();
                // context.showPaymentOptions(list.get(holder.getAdapterPosition()).getMODE(),list.get(holder.getAdapterPosition()).getACID());


            }
        });


    }

    private void showAlertDialog(String msg, int pos) {

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(msg)
                .setCancelable(false)
                .setNegativeButton("Yes", (dialog, which) -> {
                    GlobalValues.mode_selected_position = pos;

                    if (GlobalValues.mode_validation == GlobalValues.SAMRIDHI_MODE) {
                        //clear samriddhi amount data
                        context.clearSamridhiData();
                        context.showPaymentOptions(list[pos]);


                    } else {
                        //clear all other data
                        context.clearOtherModeData();
                        context.showPaymentOptions(list[pos]);

                        GlobalValues.mode_validation = GlobalValues.SAMRIDHI_MODE;


                    }
                    notifyDataSetChanged();
                }).setPositiveButton("No", (dialog, which) -> {
                    notifyDataSetChanged();

                    dialog.dismiss();
                });
        builder.show();

    }

    @Override
    public int getItemCount() {
        return list.length;
    }

    public static class BottomSheetItemHolder extends RecyclerView.ViewHolder {
        BottomSheetRvContainerBinding binding;

        public BottomSheetItemHolder(@NonNull BottomSheetRvContainerBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;
        }
    }
}
