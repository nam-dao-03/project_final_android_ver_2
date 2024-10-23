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

public class DayOfTheWeekAdapter extends ArrayAdapter<DayOfTheWeekAdapter.DayOfTheWeek> {

    public DayOfTheWeekAdapter(@NonNull Context context, int resource, @NonNull List<DayOfTheWeek> objects) {
        super(context, resource, objects);
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_dropdown_to_select, parent, false);
        TextView tv_day_of_the_week = convertView.findViewById(R.id.tv_item_to_select);

        DayOfTheWeek dayOfTheWeek = this.getItem(position);
        if(dayOfTheWeek == null) return convertView;
        tv_day_of_the_week.setText(dayOfTheWeek.getName());

        return convertView;
    }

    @SuppressLint("ViewHolder")
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_dropdown_selected, parent, false);
        TextView tv_day_of_week = convertView.findViewById(R.id.tv_item_selected);

        DayOfTheWeek dayOfTheWeek = this.getItem(position);
        if(dayOfTheWeek == null) return convertView;
        tv_day_of_week.setText(dayOfTheWeek.getName());

        return convertView;
    }

    public static class DayOfTheWeek {
        private String name;

        public DayOfTheWeek(String name) {
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
