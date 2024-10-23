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

public class TeacherAdapter extends ArrayAdapter<TeacherAdapter.Teacher> {

    public TeacherAdapter(@NonNull Context context, int resource, @NonNull List<Teacher> objects) {
        super(context, resource, objects);
    }

    @SuppressLint("ViewHolder")
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_dropdown_selected, parent, false);
        TextView tv_teacher_selected = convertView.findViewById(R.id.tv_item_selected);
        Teacher teacher = this.getItem(position);
        if(teacher == null) return convertView;
        tv_teacher_selected.setText(teacher.getName());
        return convertView;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_dropdown_to_select, parent, false);
        TextView tv_teacher = convertView.findViewById(R.id.tv_item_to_select);
        Teacher teacher = this.getItem(position);
        if(teacher == null) return convertView;
        tv_teacher.setText(teacher.getName());
        return convertView;
    }

    public static class Teacher{
        private String name;

        public Teacher(String name) {
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
