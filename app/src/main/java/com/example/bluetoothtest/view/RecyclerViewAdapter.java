package com.example.bluetoothtest.view;

import android.content.Context;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bluetoothtest.R;
import com.example.bluetoothtest.model.entities.users.User;
import com.example.bluetoothtest.utility.ProfileHelper;

public class RecyclerViewAdapter extends ListAdapter<User, RecyclerViewAdapter.ViewHolder> {

    ViewHolder viewHolder;

    OnUserItemClickListener onUserItemClickListener;
    OnRemoveClickedListener onRemoveClickedListener;

    public RecyclerViewAdapter(@NonNull DiffUtil.ItemCallback<User> diffCallback) {
        super(diffCallback);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        viewHolder = new ViewHolder(LayoutInflater.
                from(parent.getContext()).
                inflate(R.layout.custom_listview_fragment_users, parent, false));

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(getItem(position), onRemoveClickedListener, onUserItemClickListener);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView userProfileImageView;
        TextView username;
        ImageView moreButton;
        TextView isAdmin;
        CardView cardViewContainer;
        View view;
        OnRemoveClickedListener onRemoveClickedListener;
        OnUserItemClickListener onUserItemClickListener;
        User user;

        public ViewHolder(@NonNull View view) {
            super(view);
            userProfileImageView = view.findViewById(R.id.user_profile_image_view_list_view);
            username = view.findViewById(R.id.Person_Name);
            moreButton = view.findViewById(R.id.listview_users_button_more);
            isAdmin = view.findViewById(R.id.IsAdmingTextView);
            cardViewContainer = view.findViewById(R.id.user_profile_container_list_view);
            this.view = view;


        }


        public void bind(User user, OnRemoveClickedListener onRemoveClickedListener,
                         OnUserItemClickListener onUserItemClickListener) {

            this.user = user;
            String userProfilePath = user.profilePath;

            if (userProfilePath != null && !userProfilePath.isEmpty())
                ProfileHelper.getImage(userProfilePath, userProfileImageView/*ImageView Profile*/);

            username.setText(user.name);

            moreButton.setOnClickListener(view -> {
                Context wrapper = new ContextThemeWrapper(view.getContext(), R.style.PopupMenu);
                PopupMenu menu = new PopupMenu(wrapper, view);
                menu.inflate(R.menu.list_users_popup_menu);
                menu.show();

                menu.setOnMenuItemClickListener(menuItem -> {
                    if (menuItem.getItemId() == R.id.popup_menu_delete)
                        onRemoveClickedListener.onClick(user);

                    return false;
                });
            });

            view.setOnClickListener(view -> {
                onUserItemClickListener.onClick(getAdapterPosition(), user.name);
            });

        }


    }

    public interface OnRemoveClickedListener {
        void onClick(User user);
    }

    public interface OnUserItemClickListener {
        void onClick(int position, String personName);
    }

    public ViewHolder getHolder() {
        return viewHolder;
    }

    public void setRemoveListener(OnRemoveClickedListener onRemoveClickedListener) {
        this.onRemoveClickedListener = onRemoveClickedListener;
    }

    public void setItemListener(OnUserItemClickListener onUserItemClickListener) {
        this.onUserItemClickListener = onUserItemClickListener;
    }


    public static class wordDiff extends DiffUtil.ItemCallback<User> {


        @Override
        public boolean areItemsTheSame(@NonNull User oldItem, @NonNull User newItem) {
            return oldItem == newItem;
        }

        @Override
        public boolean areContentsTheSame(@NonNull User oldItem, @NonNull User newItem) {
            return oldItem.name.equals(newItem.name);
        }
    }

}
