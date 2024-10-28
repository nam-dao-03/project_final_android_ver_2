package com.example.project_final_ver_01.adapters.ui_components;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project_final_ver_01.R;
import com.example.project_final_ver_01.database.entities.YogaCourse;
import com.example.project_final_ver_01.interfaces.IClickItemListener;

import java.util.List;

public class YogaCourseViewHolderAdapter extends RecyclerView.Adapter<YogaCourseViewHolderAdapter.YogaCourseViewHolder> {

    private List<YogaCourse> mListYogaCourse;
    private IClickItemListener mIClickItemListener;


    public YogaCourseViewHolderAdapter(List<YogaCourse> mListYogaCourse, IClickItemListener miClickItemListener) {
        this.mListYogaCourse = mListYogaCourse;
        this.mIClickItemListener = miClickItemListener;
    }

    @NonNull
    @Override
    public YogaCourseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_course, parent, false);
        return new YogaCourseViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull YogaCourseViewHolder holder, int position) {
        YogaCourse yogaCourse = mListYogaCourse.get(position);
        if(yogaCourse == null) return;

        if(yogaCourse.getType_of_class().equals("Flow Yoga"))
            holder.img_type_of_class.setImageResource(R.drawable.img_yoga_class_01);
        if(yogaCourse.getType_of_class().equals("Aerial Yoga"))
            holder.img_type_of_class.setImageResource(R.drawable.img_yoga_class_02);
        if(yogaCourse.getType_of_class().equals("Family Yoga"))
            holder.img_type_of_class.setImageResource(R.drawable.img_yoga_class_03);
        holder.card_item_yoga_course.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mIClickItemListener.onClickItem(yogaCourse);
            }
        });
        holder.tv_name_course.setText("Course: " + yogaCourse.getCourse_name());
        holder.tv_type_of_class.setText("Type: " + yogaCourse.getType_of_class());
        holder.tv_teacher_name.setText("Start on: " + yogaCourse.getDay_of_the_week());
    }

    @Override
    public int getItemCount() {
        if (mListYogaCourse != null) {
            return mListYogaCourse.size();
        }
        return 0;
    }

    public static class YogaCourseViewHolder extends RecyclerView.ViewHolder {
        private CardView card_item_yoga_course;
        private TextView tv_name_course,tv_type_of_class, tv_teacher_name;
        private ImageView img_type_of_class;
        public YogaCourseViewHolder(@NonNull View itemView) {
            super(itemView);
            card_item_yoga_course = itemView.findViewById(R.id.card_item_yoga_course);
            tv_name_course = itemView.findViewById(R.id.tv_name_course);
            tv_type_of_class = itemView.findViewById(R.id.tv_type_of_class);
            tv_teacher_name = itemView.findViewById(R.id.tv_teacher_name);
            img_type_of_class = itemView.findViewById(R.id.img_type_of_class);
        }
    }

}
