package com.example.project_final_ver_01.adapters.ui_components;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project_final_ver_01.R;
import com.example.project_final_ver_01.database.entities.User;
import com.example.project_final_ver_01.interfaces.IClickItemListener;

import java.util.List;

public class UserViewHolderAdapter extends RecyclerView.Adapter<UserViewHolderAdapter.UserViewHolder> {
    private List<User> mListUser;
    private IClickItemListener mIClickItemListener;

    public UserViewHolderAdapter(List<User> mListUser, IClickItemListener iClickItemListener) {
        this.mListUser = mListUser;
        this.mIClickItemListener = iClickItemListener;
    }
    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user, parent, false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        User user = mListUser.get(position);
        if(user == null) return;
        holder.card_item_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mIClickItemListener.onClickItem(user);
            }
        });
        holder.tv_user.setText(user.getEmail());
    }

    @Override
    public int getItemCount() {
        if(mListUser != null) {
            return mListUser.size();
        }
        return 0;
    }

    public static class UserViewHolder extends RecyclerView.ViewHolder {
        private CardView card_item_user;
        private TextView tv_user;
        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            card_item_user = itemView.findViewById(R.id.card_item_user);
            tv_user = itemView.findViewById(R.id.tv_user);
        }
    }
}
