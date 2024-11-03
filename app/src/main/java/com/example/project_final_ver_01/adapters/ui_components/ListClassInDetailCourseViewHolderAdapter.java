package com.example.project_final_ver_01.adapters.ui_components;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import java.util.Calendar;
import java.util.List;
import java.util.Objects;

public class ListClassInDetailCourseViewHolderAdapter extends RecyclerView.Adapter<ListClassInDetailCourseViewHolderAdapter.ListClassInDetailCourseViewHolder> {

    private List<YogaClassInstance> mYogaClassInstanceList;
    private List<UserYogaClassInstance> mUserYogaClassInstanceList;
    private List<YogaCourse> mYogaCourseList;
    private List<User> mUserList;
    private IClickItemListener mIClickItemListener;

    //Object
    private User user;
    private YogaClassInstance yogaClassInstance;
    private YogaCourse yogaCourse;
    private UserYogaClassInstance userYogaClassInstance;
    public ListClassInDetailCourseViewHolderAdapter(List<YogaClassInstance> yogaClassInstanceList, List<UserYogaClassInstance> userYogaClassInstanceList, List<User> userList, List<YogaCourse> yogaCourseList, IClickItemListener iClickItemListener) {
        this.mYogaClassInstanceList = yogaClassInstanceList;
        this.mUserYogaClassInstanceList = userYogaClassInstanceList;
        this.mYogaCourseList = yogaCourseList;
        this.mUserList = userList;
        this.mIClickItemListener = iClickItemListener;
    }
    @NonNull
    @Override
    public ListClassInDetailCourseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_class_instance_in_detail_course, parent, false);
        return new ListClassInDetailCourseViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ListClassInDetailCourseViewHolder holder, int position) {
        yogaClassInstance = mYogaClassInstanceList.get(position);
        if(yogaClassInstance == null) return;
        userYogaClassInstance = getUserYogaClassInstanceById(yogaClassInstance.getYoga_class_instance_id());
        if(userYogaClassInstance == null) return;
        user = getUserById(userYogaClassInstance.getUser_id());
        if(user == null) return;
        yogaCourse = getYogaCourseById(yogaClassInstance.getYoga_course_id());
        if(yogaCourse == null) return;
        holder.card_item_class_instance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mIClickItemListener.onClickItem(yogaClassInstance);
            }
        });
        holder.tv_teacher.setText("Teacher: " + user.getUser_name());
        holder.tv_schedule.setText("Schedule: " + yogaClassInstance.getSchedule());
        holder.tv_day_of_the_week.setText(showDayOfTheWeek(yogaClassInstance.getSchedule()));

        if(!showDayOfTheWeek(yogaClassInstance.getSchedule()).equals(yogaCourse.getDay_of_the_week())) {
            holder.tv_day_of_the_week.setPaintFlags(holder.tv_day_of_the_week.getPaintFlags()| Paint.STRIKE_THRU_TEXT_FLAG);
            holder.tv_day_of_the_week.setTextColor(Color.RED);
            holder.tv_new_day_of_the_week.setText(yogaCourse.getDay_of_the_week());
        } else {
            holder.tv_new_day_of_the_week.setText("");
        }
    }

    @Override
    public int getItemCount() {
        if(mYogaClassInstanceList != null) {
            return mYogaClassInstanceList.size();
        }
        return 0;
    }
    public static class ListClassInDetailCourseViewHolder extends RecyclerView.ViewHolder {
        private CardView card_item_class_instance;
        private TextView tv_teacher, tv_schedule, tv_day_of_the_week, tv_new_day_of_the_week;
        public ListClassInDetailCourseViewHolder(@NonNull View itemView){
            super(itemView);
            card_item_class_instance = itemView.findViewById(R.id.card_item_class_instance);
            tv_teacher = itemView.findViewById(R.id.tv_teacher);
            tv_schedule = itemView.findViewById(R.id.tv_schedule);
            tv_day_of_the_week = itemView.findViewById(R.id.tv_day_of_the_week);
            tv_new_day_of_the_week = itemView.findViewById(R.id.tv_new_day_of_the_week);
        }
    }

    private UserYogaClassInstance getUserYogaClassInstanceById(int id){
        for(UserYogaClassInstance userYogaClassInstance: mUserYogaClassInstanceList) {
            if(userYogaClassInstance.getYoga_class_instance_id() == id) {
                return userYogaClassInstance;
            }
        }
        return null;
    }
    private User getUserById(int id) {
        for(User user: mUserList) {
            if(user.getId() == id) {
                return user;
            }
        }
        return null;
    }
    private YogaCourse getYogaCourseById(int id) {
        for(YogaCourse yogaCourse: mYogaCourseList) {
            if(yogaCourse.getId() == id) {
                return yogaCourse;
            }
        }
        return null;
    }
    private String showDayOfTheWeek(String dateString){
        String[] parts = dateString.split("/");
        int day = Integer.parseInt(parts[0]);
        int month = Integer.parseInt(parts[1]);
        int year = Integer.parseInt(parts[2]);
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month - 1, day);
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        switch (dayOfWeek) {
            case Calendar.SUNDAY:
                return "Sunday";
            case Calendar.MONDAY:
                return "Monday";
            case Calendar.TUESDAY:
                return "Tuesday";
            case Calendar.WEDNESDAY:
                return "Wednesday";
            case Calendar.THURSDAY:
                return "Thursday";
            case Calendar.FRIDAY:
                return "Friday";
            case Calendar.SATURDAY:
                return "Saturday";
            default:
                return "Unknown";
        }
    }

}
