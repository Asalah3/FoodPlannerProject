package com.example.foodplanner.view;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.viewpager.widget.ViewPager;

import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.foodplanner.R;


public class OnboardingFragment extends Fragment {

    ViewPager viewPager;
    LinearLayout dotLayout;
    AppCompatButton skipButton;
    TextView[] dots;
    ViewPadgerAdapter viewPagerAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_onboarding, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();

        skipButton = view.findViewById(R.id.skip_btn);
        viewPager = view.findViewById(R.id.slideViewPager);
        dotLayout = view.findViewById(R.id.indicator_layout);

        skipButton.setOnClickListener(v -> Navigation.findNavController(view)
                .navigate(OnboardingFragmentDirections.actionOnboardingFragmentToChooserFragment()));

        viewPagerAdapter = new ViewPadgerAdapter(requireContext());

        viewPager.setAdapter(viewPagerAdapter);

        setUpIndicator(0);
        viewPager.addOnPageChangeListener(viewListener);

    }

    public void setUpIndicator(int position) {

        dots = new TextView[3];
        dotLayout.removeAllViews();

        for (int i = 0; i < dots.length; i++) {

            dots[i] = new TextView(requireContext());
            dots[i].setText(Html.fromHtml("&#8226"));
            dots[i].setTextSize(35);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                dots[i].setTextColor(getResources().getColor(R.color.grey_dark, requireContext().getTheme()));
            }
            dotLayout.addView(dots[i]);

        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            dots[position].setTextColor(getResources().getColor(R.color.green_dark, requireContext().getTheme()));
        }

    }

    ViewPager.OnPageChangeListener viewListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }


        @Override
        public void onPageSelected(int position) {

            setUpIndicator(position);
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };
}