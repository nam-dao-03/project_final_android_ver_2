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
import com.example.project_final_ver_01.database.entities.User;
import com.example.project_final_ver_01.database.entities.UserYogaClassInstance;
import com.example.project_final_ver_01.database.entities.YogaClassInstance;
import com.example.project_final_ver_01.database.entities.YogaCourse;
import com.example.project_final_ver_01.interfaces.IClickItemListener;

import java.util.List;

public class YogaClassInstanceViewHolderAdapter extends RecyclerView.Adapter<YogaClassInstanceViewHolderAdapter.YogaClassInstanceViewHolder> {
    private List<User> mUserList;
    private List<UserYogaClassInstance> mUserYogaClassInstanceList;
    private List<YogaClassInstance> mListYogaClassInstance;
    private List<YogaCourse> mListYogaCourse;
    private IClickItemListener mIClickItemListener;
    public YogaClassInstanceViewHolderAdapter(List<YogaClassInstance> yogaClassInstances, List<YogaCourse> yogaCourses, List<User> mUserList, List<UserYogaClassInstance> mUserYogaClassInstanceList, IClickItemListener iClickItemListener) {
        this.mListYogaClassInstance = yogaClassInstances;
        this.mListYogaCourse = yogaCourses;
        this.mUserList = mUserList;
        this.mUserYogaClassInstanceList = mUserYogaClassInstanceList;
        this.mIClickItemListener = iClickItemListener;
    }
    @NonNull
    @Override
    public YogaClassInstanceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_class_instance, parent, false);
        return new YogaClassInstanceViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull YogaClassInstanceViewHolder holder, int position) {
        YogaClassInstance yogaClassInstance = mListYogaClassInstance.get(position);
        if(yogaClassInstance == null) return;
        //Get name course from YogaCourse
        YogaCourse yogaCourse = getYogaCourseByID(yogaClassInstance.getYoga_course_id());
        if(yogaCourse == null) return;

        if(yogaCourse.getType_of_class().equals("Flow Yoga"))
            holder.img_type_of_class.setImageResource(R.drawable.img_yoga_class_01);
        if(yogaCourse.getType_of_class().equals("Aerial Yoga"))
            holder.img_type_of_class.setImageResource(R.drawable.img_yoga_class_02);
        if(yogaCourse.getType_of_class().equals("Family Yoga"))
            holder.img_type_of_class.setImageResource(R.drawable.img_yoga_class_03);


        UserYogaClassInstance userYogaClassInstance = getUserYogaClassInstanceById(yogaClassInstance.getYoga_class_instance_id());
        if(userYogaClassInstance == null) return;
        User user = getUserByID(userYogaClassInstance.getUser_id());
        if(user == null) return;
        holder.card_item_class_instance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mIClickItemListener.onClickItem(yogaClassInstance);
            }
        });
        holder.tv_name_course.setText("Course: " + yogaCourse.getCourse_name());
        holder.tv_schedule.setText("Schedule: " + yogaClassInstance.getSchedule());
        holder.tv_teacher.setText("Teacher: " + user.getUser_name());
        holder.tv_day_of_the_week.setText("Start on: " + yogaCourse.getDay_of_the_week());

    }

    @Override
    public int getItemCount() {
        if(mListYogaClassInstance != null) {
            return mListYogaClassInstance.size();
        }
        return 0;
    }

    public static class YogaClassInstanceViewHolder extends RecyclerView.ViewHolder {
        private CardView card_item_class_instance;
        private TextView tv_name_course, tv_teacher, tv_schedule, tv_day_of_the_week;
        private ImageView img_type_of_class;
        public YogaClassInstanceViewHolder(@NonNull View itemView) {
            super(itemView);
            card_item_class_instance = itemView.findViewById(R.id.card_item_class_instance);
            tv_name_course = itemView.findViewById(R.id.tv_name_course);
            tv_teacher = itemView.findViewById(R.id.tv_teacher);
            tv_schedule = itemView.findViewById(R.id.tv_schedule);
            tv_day_of_the_week = itemView.findViewById(R.id.tv_day_of_the_week);
            img_type_of_class = itemView.findViewById(R.id.img_type_of_class);
        }
    }
    private YogaCourse getYogaCourseByID(int id) {
        for(YogaCourse yogaCourse : mListYogaCourse) {
            if(yogaCourse.getId() == id) {
                return yogaCourse;
            }
        }
        return null;
    }
    private UserYogaClassInstance getUserYogaClassInstanceById(int id){
        for(UserYogaClassInstance userYogaClassInstance: mUserYogaClassInstanceList) {
            if(userYogaClassInstance.getYoga_class_instance_id() == id) {
                return userYogaClassInstance;
            }
        }
        return null;
    }
    private User getUserByID(int id) {
        for(User user: mUserList) {
            if(user.getId() == id) {
                return user;
            }
        }
        return null;
    }
}
