package com.example.project_final_ver_01.adapters.ui_components;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
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

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ListClassInDetailCourseViewHolderAdapter extends RecyclerView.Adapter<ListClassInDetailCourseViewHolderAdapter.ListClassInDetailCourseViewHolder> {

    private List<YogaClassInstance> mYogaClassInstanceList;
    private List<UserYogaClassInstance> mUserYogaClassInstanceList;
    private List<User> mUserList;
    private IClickItemListener mIClickItemListener;

    //Object
    private User user;
    private YogaClassInstance yogaClassInstance;
    private UserYogaClassInstance userYogaClassInstance;
    public ListClassInDetailCourseViewHolderAdapter(List<YogaClassInstance> yogaClassInstanceList, List<UserYogaClassInstance> userYogaClassInstanceList, List<User> userList, IClickItemListener iClickItemListener) {
        this.mYogaClassInstanceList = yogaClassInstanceList;
        this.mUserYogaClassInstanceList = userYogaClassInstanceList;
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
        user = getUserByID(userYogaClassInstance.getUser_id());
        if(user == null) return;
        holder.card_item_class_instance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mIClickItemListener.onClickItem(yogaClassInstance);
            }
        });
        holder.tv_teacher.setText("Teacher: " + user.getUser_name());
        holder.tv_schedule.setText("Schedule: " + yogaClassInstance.getSchedule());
        holder.tv_day_of_the_week.setText("Start on: " + showDayOfTheWeek(yogaClassInstance.getSchedule()));
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
        private TextView tv_teacher, tv_schedule, tv_day_of_the_week;
        public ListClassInDetailCourseViewHolder(@NonNull View itemView){
            super(itemView);
            card_item_class_instance = itemView.findViewById(R.id.card_item_class_instance);
            tv_teacher = itemView.findViewById(R.id.tv_teacher);
            tv_schedule = itemView.findViewById(R.id.tv_schedule);
            tv_day_of_the_week = itemView.findViewById(R.id.tv_day_of_the_week);
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
    private User getUserByID(int id) {
        for(User user: mUserList) {
            if(user.getId() == id) {
                return user;
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
