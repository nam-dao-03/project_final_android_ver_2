package com.example.project_final_ver_01.adapters.ui_components;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
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
import com.example.project_final_ver_01.ui.login.activities.AdminHomeActivity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class YogaClassInstanceViewHolderAdapter extends RecyclerView.Adapter<YogaClassInstanceViewHolderAdapter.YogaClassInstanceViewHolder> implements Filterable {
    private List<User> mUserList;
    private List<UserYogaClassInstance> mUserYogaClassInstanceList;
    private List<YogaClassInstance> mYogaClassInstanceList;
    private List<YogaClassInstance> mYogaClassInstancesSearchList;
    private List<YogaCourse> mListYogaCourse;
    private IClickItemListener mIClickItemListener;
    private String filterOption;
    public YogaClassInstanceViewHolderAdapter(List<YogaClassInstance> mYogaClassInstancesList, List<YogaCourse> yogaCourses, List<User> mUserList, List<UserYogaClassInstance> mUserYogaClassInstanceList, IClickItemListener iClickItemListener) {
        this.mYogaClassInstanceList = mYogaClassInstancesList;
        this.mYogaClassInstancesSearchList = mYogaClassInstancesList;
        this.mListYogaCourse = yogaCourses;
        this.mUserList = mUserList;
        this.mUserYogaClassInstanceList = mUserYogaClassInstanceList;
        this.mIClickItemListener = iClickItemListener;
    }

    public void setFilterOption(String filterOption) {
        this.filterOption = filterOption;
    }

    public void setYogaClassInstanceList(List<YogaClassInstance> mYogaClassInstanceList) {
        this.mYogaClassInstanceList = mYogaClassInstanceList;
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
        YogaClassInstance yogaClassInstance = mYogaClassInstanceList.get(position);
        if(yogaClassInstance == null) return;
        //Get name course from YogaCourse
        YogaCourse yogaCourse = getYogaCourseById(yogaClassInstance.getYoga_course_id());
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
        if(mYogaClassInstanceList != null) {
            return mYogaClassInstanceList.size();
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
    private YogaCourse getYogaCourseById(int id) {
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
    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String strSearch = constraint.toString();
                FilterResults filterResults = new FilterResults();
                if(strSearch.isEmpty()) {
                    mYogaClassInstanceList.clear();
                } else {
                    List<YogaClassInstance> list = new ArrayList<>();
                    for(YogaClassInstance yogaClassInstance: mYogaClassInstancesSearchList) {
                        if (filterOption.equals("Course")) {
                            YogaCourse yogaCourse = getYogaCourseById(yogaClassInstance.getYoga_course_id());
                            if(yogaCourse.getCourse_name().toLowerCase().contains(strSearch.toLowerCase())) list.add(yogaClassInstance);
                        }
                        if(filterOption.equals("Schedule")) {
                          if(yogaClassInstance.getSchedule().toLowerCase().contains(strSearch.toLowerCase())) list.add(yogaClassInstance);
                        }
                        if(filterOption.equals("Day_of_the_week")) {
                            if(showDayOfTheWeek(yogaClassInstance.getSchedule()).toLowerCase().contains(strSearch.toLowerCase())) list.add(yogaClassInstance);
                        }
                        if(filterOption.equals("Teacher")) {
                          UserYogaClassInstance userYogaClassInstance = getUserYogaClassInstanceById(yogaClassInstance.getYoga_class_instance_id());
                          User user = getUserByID(userYogaClassInstance.getUser_id());
                          if(user.getUser_name().toLowerCase().contains(strSearch.toLowerCase())) list.add(yogaClassInstance);
                        }
                    }
                    mYogaClassInstanceList = list;
                }
                filterResults.values = mYogaClassInstanceList;
                filterResults.count = mYogaClassInstanceList.size();
                return filterResults;
            }

            @SuppressLint("NotifyDataSetChanged")
            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                mYogaClassInstanceList = (List<YogaClassInstance>) results.values;
                notifyDataSetChanged();
            }
        };
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
