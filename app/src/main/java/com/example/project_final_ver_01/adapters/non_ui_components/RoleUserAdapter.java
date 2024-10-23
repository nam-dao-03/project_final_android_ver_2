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

public class RoleUserAdapter extends ArrayAdapter<RoleUserAdapter.RoleUser> {

    public RoleUserAdapter(@NonNull Context context, int resource, @NonNull List<RoleUser> objects) {
        super(context, resource, objects);
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_dropdown_to_select, parent, false);
        TextView tv_user_role = convertView.findViewById(R.id.tv_item_to_select);

        RoleUser roleUser = this.getItem(position);
        if(roleUser == null) return convertView;
        tv_user_role.setText(roleUser.getName());

        return convertView;
    }

    @SuppressLint("ViewHolder")
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_dropdown_selected, parent, false);
        TextView tv_role_selected = convertView.findViewById(R.id.tv_item_selected);

        RoleUser roleUser = this.getItem(position);
        if(roleUser == null) return convertView;
        tv_role_selected.setText(roleUser.getName());

        return convertView;
    }

    public static class RoleUser {
        private String name;

        public RoleUser(String name) {
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
