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
import com.example.project_final_ver_01.database.entities.YogaCourse;

import java.util.List;

public class NameCourseAdapter extends ArrayAdapter<NameCourseAdapter.NameCourse> {
    public NameCourseAdapter(@NonNull Context context, int resource, @NonNull List<NameCourseAdapter.NameCourse> objects) {
        super(context, resource, objects);
    }

    @SuppressLint("ViewHolder")
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_dropdown_selected, parent, false);
        TextView tv_course_selected = convertView.findViewById(R.id.tv_item_selected);
        NameCourse nameCourse = this.getItem(position);
        if(nameCourse == null) return convertView;
        tv_course_selected.setText(nameCourse.getName());
        return convertView;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_dropdown_to_select, parent, false);
        TextView tv_course = convertView.findViewById(R.id.tv_item_to_select);
        NameCourse nameCourse = this.getItem(position);
        if(nameCourse == null) return convertView;
        tv_course.setText(nameCourse.getName());
        return convertView;
    }

    public static class NameCourse{
        private String name;

        public NameCourse(String name) {
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
