package com.example.project_final_ver_01.adapters.non_ui_components;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.project_final_ver_01.R;

import java.util.List;

public class FilterSearchAdapter extends ArrayAdapter<FilterSearchAdapter.FilterSearch> {


    public FilterSearchAdapter(@NonNull Context context, int resource, @NonNull List<FilterSearch> objects) {
        super(context, resource, objects);
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_dropdown_to_select, parent, false);
        TextView tv_filter = convertView.findViewById(R.id.tv_item_to_select);

        FilterSearch filterSearch = this.getItem(position);
        if(filterSearch == null) return convertView;

        tv_filter.setText(filterSearch.getName());
        return convertView;
    }

    @SuppressLint("ViewHolder")
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView =LayoutInflater.from(parent.getContext()).inflate(R.layout.item_dropdown_selected, parent, false);
        TextView tv_filter = convertView.findViewById(R.id.tv_item_selected);

        FilterSearch filterSearch = this.getItem(position);
        if(filterSearch == null) return convertView;

        tv_filter.setText(filterSearch.getName());
        return convertView;
    }

    public static class FilterSearch{
        private String name;

        public FilterSearch(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
