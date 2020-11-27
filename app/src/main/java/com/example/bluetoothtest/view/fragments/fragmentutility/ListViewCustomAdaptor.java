package com.example.bluetoothtest.view.fragments.fragmentutility;

import android.content.Context;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.bluetoothtest.model.entities.users.User;
import com.example.bluetoothtest.utility.DbHelper;
import com.example.bluetoothtest.utility.ProfileHelper;
import com.example.bluetoothtest.R;
import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;

public class ListViewCustomAdaptor extends ArrayAdapter<User> {

    ImageView userProfileImageView;
    MaterialCardView cardViewContainer;
    TextView username;
    TextView expand;
    ImageView moreButton;
    TextView isAdmin;
    Context context;


    TextView reactionTime;
    ProfileHelper profileHelper;
    //int[] colorArray = new int[]{Color.YELLOW,Color.CYAN , Color.GREEN , Color.MAGENTA, Color.RED, Color.BLUE};

    public ListViewCustomAdaptor(@NonNull Context context, ArrayList<User> users) {
        super(context, 0, users);
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.custom_listview_fragment_users, parent, false);

        }

        initViews(convertView);

        User item = getItem(position);
        if (item != null) {
            //colorfulDot.setColorFilter(colorArray[new Random().nextInt(colorArray.length)]);

       /*     if (item.parentName.equals(MainActivity.username)) {
                isAdmin.setVisibility(View.GONE);
                moreButton.setVisibility(View.VISIBLE);
            } else {
                isAdmin.setVisibility(View.VISIBLE);
                moreButton.setVisibility(View.GONE);
            }*/

            username.setText(item.name);

            String path = item.profilePath;

            if (path != null)
                ProfileHelper.getImage(path, userProfileImageView);
            else ProfileHelper.getDefaultImage(userProfileImageView);



            if (moreButton.getVisibility() == View.VISIBLE) {
                moreButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Context wrapper = new ContextThemeWrapper(context, R.style.PopupMenu);
                        PopupMenu menu = new PopupMenu(wrapper, view);

                        menu.inflate(R.menu.list_users_popup_menu);
                        menu.show();

                        menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                            @Override
                            public boolean onMenuItemClick(MenuItem menuItem) {
                                if (menuItem.getItemId() == R.id.popup_menu_delete) {
                                    remove(item);
                                    DbHelper database = new DbHelper(context);
                                    database.removeRecord(DbHelper.TABLE_USERS_NAME, item.name);
                                    ProfileHelper.delete(item.profilePath);
                                }
                                return false;
                            }
                        });


                    }
                });
            }

        }

        return convertView;
    }

    private void initViews(View view) {
        userProfileImageView = view.findViewById(R.id.user_profile_image_view_list_view);
        username = view.findViewById(R.id.Person_Name);
        moreButton = view.findViewById(R.id.listview_users_button_more);
        isAdmin = view.findViewById(R.id.IsAdmingTextView);

        cardViewContainer = view.findViewById(R.id.user_profile_container_list_view);
    }
}
