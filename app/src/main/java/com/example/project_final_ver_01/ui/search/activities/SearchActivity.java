package com.example.project_final_ver_01.ui.search.activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project_final_ver_01.R;
import com.example.project_final_ver_01.adapters.non_ui_components.FilterSearchAdapter;
import com.example.project_final_ver_01.adapters.ui_components.YogaClassInstanceViewHolderAdapter;
import com.example.project_final_ver_01.database.DatabaseHelper;
import com.example.project_final_ver_01.database.entities.User;
import com.example.project_final_ver_01.database.entities.UserYogaClassInstance;
import com.example.project_final_ver_01.database.entities.YogaClassInstance;
import com.example.project_final_ver_01.database.entities.YogaCourse;
import com.example.project_final_ver_01.interfaces.IClickItemListener;
import com.example.project_final_ver_01.ui.login.activities.AdminHomeActivity;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity {
    //Define widgets in activity
    private Spinner spn_filter;
    private EditText et_search_input;
    private ImageView btn_back;
    private FilterSearchAdapter filterSearchAdapter;
    private RecyclerView rcv_search_result;
    private YogaClassInstanceViewHolderAdapter yogaClassInstanceViewHolderAdapter;
    private SearchView sv_search_view;

    //Define object
    private LinearLayout rootLayout, ll_view_empty;
    private DatabaseHelper databaseHelper = new DatabaseHelper(SearchActivity.this);
    private List<FilterSearchAdapter.FilterSearch> filterSearchList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_search);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        // Signing variable
        rootLayout = findViewById(R.id.main);
        ll_view_empty = findViewById(R.id.ll_view_empty);
        btn_back = findViewById(R.id.btn_back);
        spn_filter = findViewById(R.id.spn_filter);
        rcv_search_result = findViewById(R.id.rcv_search_result);
        sv_search_view = findViewById(R.id.sv_search_view);
        et_search_input = sv_search_view.findViewById(androidx.appcompat.R.id.search_src_text);
        et_search_input.setHintTextColor(ContextCompat.getColor(this, R.color.white_hint));
        et_search_input.setTextColor(Color.WHITE);
        et_search_input.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
        showFilterListDropdown();
        showSearchResultList();
        setListenerForWidgets();
    }

    private void showFilterListDropdown(){
        filterSearchList.add(new FilterSearchAdapter.FilterSearch("Course"));
        filterSearchList.add(new FilterSearchAdapter.FilterSearch("Teacher"));
        filterSearchList.add(new FilterSearchAdapter.FilterSearch("Schedule"));
        filterSearchList.add(new FilterSearchAdapter.FilterSearch("Day of the week"));
        filterSearchAdapter = new FilterSearchAdapter(SearchActivity.this, R.layout.item_dropdown_selected, filterSearchList);
        spn_filter.setAdapter(filterSearchAdapter);
    }
    private void setListenerForWidgets(){
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent admin_home_activity = new Intent(SearchActivity.this, AdminHomeActivity.class);
                startActivity(admin_home_activity);
            }
        });
        sv_search_view.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                yogaClassInstanceViewHolderAdapter.getFilter().filter(query, new Filter.FilterListener() {
                    @Override
                    public void onFilterComplete(int count) {
                        if(count > 0) {
                            ll_view_empty.setVisibility(View.GONE);
                        }
                        if(count == 0) {
                            ll_view_empty.setVisibility(View.VISIBLE);
                        }
                    }
                });
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                yogaClassInstanceViewHolderAdapter.getFilter().filter(newText, new Filter.FilterListener() {
                    @Override
                    public void onFilterComplete(int count) {
                        if(count > 0) {
                            ll_view_empty.setVisibility(View.GONE);
                        }
                        if(count == 0) {
                            ll_view_empty.setVisibility(View.VISIBLE);
                        }
                    }
                });
                return false;
            }
        });
        spn_filter.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int selectedPosition = spn_filter.getSelectedItemPosition();
                FilterSearchAdapter.FilterSearch itemAtPosition = (FilterSearchAdapter.FilterSearch) spn_filter.getItemAtPosition(selectedPosition);
                String filterOption = itemAtPosition.getName().replaceAll(" ", "_");
                yogaClassInstanceViewHolderAdapter.setFilterOption(filterOption);
                String searchStr = et_search_input.getText().toString();
                sv_search_view.setQuery(searchStr, true);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        rootLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sv_search_view.clearFocus();
            }
        });
        ll_view_empty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sv_search_view.clearFocus();
            }
        });
    }
    private void showSearchResultList(){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(SearchActivity.this);
        linearLayoutManager.setReverseLayout(true);
        rcv_search_result.setLayoutManager(linearLayoutManager);
        yogaClassInstanceViewHolderAdapter = new YogaClassInstanceViewHolderAdapter(getClassInstanceList(), getYogaCourseList(), getUserList("Teacher"), getUserYogaClassInstanceList(), new IClickItemListener() {
            @Override
            public void onClickItem(Object object) {
                Intent intent = new Intent(SearchActivity.this, AdminHomeActivity.class);
                intent.putExtra("object_yoga_class_instance", ((YogaClassInstance) object));
                startActivity(intent);
                finish();
            }
        });
        yogaClassInstanceViewHolderAdapter.setYogaClassInstanceList(new ArrayList<>());
        rcv_search_result.setAdapter(yogaClassInstanceViewHolderAdapter);
    }



    //Database
    private List<YogaClassInstance> getClassInstanceList() {
        return databaseHelper.getAllYogaClassInstance();
    }
    private List<YogaCourse> getYogaCourseList(){
        return databaseHelper.getALlYogaCourse();
    }
    private List<UserYogaClassInstance> getUserYogaClassInstanceList(){
        return databaseHelper.getAllUserYogaClassInstance();
    }
    private List<User> getUserList(String role){
        return databaseHelper.getAllUser(role);
    }
}