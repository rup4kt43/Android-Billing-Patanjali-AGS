package com.example.androidbilling.sales_menu_components.salesmenu.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.androidbilling.R;

import java.util.List;

public class DrawerAdapter extends BaseAdapter {
    Context context;
    List<String> categoryList;

    public DrawerAdapter(Context context, List<String> strings) {
        this.context = context;
        this.categoryList = strings;
    }

    @Override
    public int getCount() {
        return categoryList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = inflater.inflate(R.layout.drawer_list_layout,parent,false);
        TextView  item = row.findViewById(R.id.textView);
        item.setText(categoryList.get(position));
        return row;
    }
}
