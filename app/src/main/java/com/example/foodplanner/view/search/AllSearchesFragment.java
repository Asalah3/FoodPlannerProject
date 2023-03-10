package com.example.foodplanner.view.search;

import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.foodplanner.R;


public class AllSearchesFragment extends Fragment {

    CardView areaCardView;
    CardView categoryCardView;
    CardView ingredientCardView;
    CardView mealsCardView;
    View view;

    public AllSearchesFragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_all_searches, container, false);

        ingredientCardView = view.findViewById(R.id.ingredient_cardView);
        ingredientCardView.setOnClickListener(view -> Navigation.findNavController(view)
                .navigate(AllSearchesFragmentDirections.actionAllSearchesFragmentToSearchByIngredientFragment()));

        categoryCardView = view.findViewById(R.id.category_cardView);
        categoryCardView.setOnClickListener(view -> Navigation.findNavController(view)
                .navigate(AllSearchesFragmentDirections.actionAllSearchesFragmentToSearchByCategoryFragment()));

        areaCardView = view.findViewById(R.id.area_cardView);
        areaCardView.setOnClickListener(view -> Navigation.findNavController(view)
                .navigate(AllSearchesFragmentDirections.actionAllSearchesFragmentToSearchByAreaFragment()));

        areaCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(AllSearchesFragmentDirections.actionAllSearchesFragmentToSearchByAreaFragment());
            }
        });
        mealsCardView = view.findViewById(R.id.meals_cardView);
        mealsCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(AllSearchesFragmentDirections.actionAllSearchesFragmentToMealsSearchFragment());
            }
        });
        return view;
    }
}