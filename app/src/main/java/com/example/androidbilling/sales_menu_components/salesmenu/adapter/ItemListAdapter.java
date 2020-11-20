package com.example.androidbilling.sales_menu_components.salesmenu.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidbilling.R;
import com.example.androidbilling.databinding.FragmentItemlistRvContainerBinding;
import com.example.androidbilling.sales_menu_components.salesmenu.fragments.ItemListFragment;
import com.example.androidbilling.sales_menu_components.salesmenu.model.pojo.ItemListItem;
import com.example.androidbilling.sales_menu_components.salesmenu.provider.SalesMenuViewModelProvider;
import com.example.androidbilling.utilities.global.GlobalFunctions;
import com.example.androidbilling.utilities.sharedpreference.PreferenceUtils;
import com.futuremind.recyclerviewfastscroll.SectionTitleProvider;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ItemListAdapter extends RecyclerView.Adapter<ItemListAdapter.CustomItemListHolder> implements Filterable, SectionTitleProvider {
    private final FragmentActivity context;
    private final List<ItemListItem> list;
    public static boolean isItemClicked = false;



    private List<ItemListItem> itemListFiltered;


    public ItemListAdapter(FragmentActivity activity, List<ItemListItem> list) {
        this.itemListFiltered = list;
        this.context = activity;
        this.list = list;





    }

    @NonNull
    @Override
    public CustomItemListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        FragmentItemlistRvContainerBinding binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.fragment_itemlist_rv_container, parent, false);
        return new CustomItemListHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomItemListHolder holder, int position) {


        ItemListItem itemListItem = itemListFiltered.get(position);
        holder.itemListBinding.setItem(itemListItem);
        holder.itemListBinding.cardView.setOnClickListener(v -> {
            if (!isItemClicked) {
                isItemClicked = true;
                GlobalFunctions.hideKeyboard(context);
                int pos = holder.getAdapterPosition();
                if (PreferenceUtils.retriveBillEditOption(context)) {
                    if (PreferenceUtils.isBillLoadedStatus(context)) {

                        GlobalFunctions.showProgressDialog("Downloading Batch Details. Please Wait....", context);
                        SalesMenuViewModelProvider.getInstance().retriveItemDetails(itemListFiltered.get(pos).getMCODE());
                    } else {
                        GlobalFunctions.showToast("Please Load to Bill to add the item in the bill!");
                    }
                } else {
                    PreferenceUtils.lockEditMode(context, true);
                    GlobalFunctions.showProgressDialog("Downloading Batch Details. Please Wait....", context);
                    SalesMenuViewModelProvider.getInstance().retriveItemDetails(itemListFiltered.get(pos).getMCODE());
                }


            }


        });


    }

    @Override
    public int getItemCount() {
        return itemListFiltered.size();

    }

    @Override
    public String getSectionTitle(int position) {
        return itemListFiltered.get(position).getDESCA().substring(0,1);
    }

    public static class CustomItemListHolder extends RecyclerView.ViewHolder {


        public final FragmentItemlistRvContainerBinding itemListBinding;

        public CustomItemListHolder(@NonNull FragmentItemlistRvContainerBinding itemView) {
            super(itemView.getRoot());
            this.itemListBinding = itemView;

        }
    }


    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {

                String charString = constraint.toString().trim();
                if (charString.isEmpty() || charString.matches("")) {
                    itemListFiltered = list;
                } else {
                    ArrayList<ItemListItem> filteredLIst = new ArrayList<>();
                    for (ItemListItem row : list) {
                        if (ItemListFragment.checkedButton.matches("mcode")) {
                            if (row.getMCODE().toLowerCase().contains(charString.toLowerCase())) {
                                filteredLIst.add(row);
                            }
                        } else if (ItemListFragment.checkedButton.matches("name")) {
                            if (row.getDESCA().toLowerCase().contains(charString.toLowerCase())) {
                                filteredLIst.add(row);
                            }
                        } else if(ItemListFragment.checkedButton.matches("barcode")){
                            if(row.getBARCODE().toLowerCase().matches(charString.toLowerCase())){
                                int itemCount = 0;
                                for (int i=0;i<list.size();i++){
                                    if(list.get(i).getBARCODE().equalsIgnoreCase(charString.toLowerCase())){
                                        itemCount++;
                                    }
                                }

                                if(itemCount>1){

                                    filteredLIst.add(row);
                                }
                                else if(itemCount==1){


                                    SalesMenuViewModelProvider.getInstance().retriveItemDetails(row.getMCODE());
                                    SalesMenuViewModelProvider.getInstance().updateSearchStatus(true);



                                }else{
                                    filteredLIst.add(row);
                                }



                            }
                        }



                    }
                    itemListFiltered = filteredLIst;
                    Collections.sort(filteredLIst, new Comparator<ItemListItem>() {
                        @Override
                        public int compare(ItemListItem itemListItem, ItemListItem t1) {
                            return Double.compare(Double.parseDouble(itemListItem.getnWeight()), Double.parseDouble(t1.getnWeight()));
                        }
                    });
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = itemListFiltered;

                return filterResults;
            }

            @SuppressWarnings("unchecked")
            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {


                itemListFiltered = (ArrayList<ItemListItem>) results.values;
                //  ItemListFragment.setItemSize(itemListFiltered.size());

                notifyDataSetChanged();
                GlobalFunctions.dismissProgressDialog();

            }
        };
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }



}
