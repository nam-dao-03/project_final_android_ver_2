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

public class TypeOfClassCourseAdapter extends ArrayAdapter<TypeOfClassCourseAdapter.TypeOfClassCourse> {

    public TypeOfClassCourseAdapter(@NonNull Context context, int resource, @NonNull List<TypeOfClassCourse> objects) {
        super(context, resource, objects);
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_dropdown_to_select, parent, false);
        TextView tv_type_of_class_course = convertView.findViewById(R.id.tv_item_to_select);

        TypeOfClassCourse typeOfClassCourse = this.getItem(position);
        if(typeOfClassCourse == null) return convertView;
        tv_type_of_class_course.setText(typeOfClassCourse.getName());
        return convertView;
    }

    @SuppressLint("ViewHolder")
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_dropdown_selected, parent, false);
        TextView tv_type_of_class_course = convertView.findViewById(R.id.tv_item_selected);
        TypeOfClassCourse typeOfClassCourse = this.getItem(position);
        if(typeOfClassCourse == null) return convertView;
        tv_type_of_class_course.setText(typeOfClassCourse.getName());
        return convertView;
    }

    public static class TypeOfClassCourse {
        private String name;

        public TypeOfClassCourse(String name) {
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
