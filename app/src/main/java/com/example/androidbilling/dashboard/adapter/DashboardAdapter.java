package com.example.androidbilling.dashboard.adapter;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidbilling.R;
import com.example.androidbilling.bill_load.adapter.BillLoadAdapter;
import com.example.androidbilling.dashboard.core.DashboardImage;
import com.example.androidbilling.dashboard.model.pojo.DashboardResultItem;
import com.example.androidbilling.dashboard.view.DashboardView;
import com.example.androidbilling.databinding.BillLoadRvContainerBinding;
import com.example.androidbilling.databinding.DahboardRvContainerBinding;

import java.util.List;

public class DashboardAdapter  extends RecyclerView.Adapter<DashboardAdapter.DashboardViewHolder> {
    private final DashboardView context;
    private final List<DashboardResultItem> list;

    public DashboardAdapter(DashboardView dashboardView, List<DashboardResultItem> dashboardResultItems) {
        this.context = dashboardView;
        this.list = dashboardResultItems;

    }

    @NonNull
    @Override
    public DashboardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        DahboardRvContainerBinding binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.dahboard_rv_container, parent, false);
        return new DashboardViewHolder(binding);


    }

    @Override
    public void onBindViewHolder(@NonNull DashboardViewHolder holder, int position) {
        DashboardResultItem dashboardResultItem  = list.get(position);
        holder.binding.setDashboardResult(dashboardResultItem);



        String dashboardImage = DashboardImage.imageHashMap.get(dashboardResultItem.getVOUCHERNAME());

        Resources res = context.getResources();
        String mDrawableName = dashboardImage;
        int resID = res.getIdentifier(mDrawableName , "drawable", context.getPackageName());
        Drawable drawable = res.getDrawable(resID );
        holder.binding.ivDashboardIcons.setImageDrawable(drawable);




    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class DashboardViewHolder extends RecyclerView.ViewHolder {
        DahboardRvContainerBinding binding;

        public DashboardViewHolder(@NonNull DahboardRvContainerBinding itemView) {
            super(itemView.getRoot());
            this.binding = itemView;
        }
    }
}
