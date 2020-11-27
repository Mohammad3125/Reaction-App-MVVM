package com.example.bluetoothtest.view.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.transition.AutoTransition;
import androidx.transition.TransitionManager;

import com.example.bluetoothtest.R;
import com.google.android.material.card.MaterialCardView;

public class FragmentMainPage extends Fragment {
    MaterialCardView cardView;
    TextView showMoreTextView;
    LinearLayout showMoreLayout;
    RelativeLayout relativeLayout;
    ImageView imageView;

    AutoCompleteTextView totalPlayerTextView;
    AutoCompleteTextView sportCategoryTextView;

    String[] sportCategories = new String[]{"General", "Tennis", "Football", "Volleyball"};
    String[] totalPlayers = new String[]{"1", "2", "3", "4", "5", "6"};

    ArrayAdapter<String> dropDownAdapterSportCategory;
    ArrayAdapter<String> dropDownAdapterTotalPlayer;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View viewToReturn = inflater.inflate(R.layout.fragment_mainpage, container, false);


        initViews(viewToReturn);



        // Layout Bounds Change Animator
/*        showMoreTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (showMoreLayout.getVisibility() == View.GONE) {
                    showMoreTextView.setText("Show Less");
                    //TransitionManager is for Animating Layouts which have Ending and Starting Layouts
                    // A layout is Invisible then we make it Visible then applying animation
                    // This way layout bounds expand as Ending layout appears :D
                    //imageView.setMaxHeight(cardView.getHeight());
                    imageView.setLayoutParams(new RelativeLayout.LayoutParams(imageView.getWidth(), imageView.getHeight() + 250));
                    showMoreLayout.setVisibility(View.VISIBLE);

                } else {
                    showMoreTextView.setText("Show More");
                    //imageView.setMaxHeight(cardView.getHeight());
                    imageView.setLayoutParams(new RelativeLayout.LayoutParams(imageView.getWidth(), imageView.getHeight() - 250));
                    showMoreLayout.setVisibility(View.GONE);
                }
                TransitionManager.beginDelayedTransition(cardView, new AutoTransition());

            }
        });*/
        return viewToReturn;
    }

    private void initViews(View viewToReturn) {
        sportCategoryTextView = viewToReturn.findViewById(R.id.auto_complete_textview_sports);
        totalPlayerTextView = viewToReturn.findViewById(R.id.auto_complete_textview_totalplayer);
    }

    @Override
    public void onResume() {
        super.onResume();

        dropDownAdapterSportCategory = new ArrayAdapter<>(getContext(), R.layout.dropdown_menu_custom_item, sportCategories);
        dropDownAdapterTotalPlayer = new ArrayAdapter<>(getContext(), R.layout.dropdown_menu_custom_item, totalPlayers);

        sportCategoryTextView.setAdapter(dropDownAdapterSportCategory);
        totalPlayerTextView.setAdapter(dropDownAdapterTotalPlayer);
    }
}
