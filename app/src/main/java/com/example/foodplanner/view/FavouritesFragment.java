package com.example.foodplanner.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodplanner.R;
import com.example.foodplanner.database.MealDataBase;
import com.example.foodplanner.model.FavouriteMeal;
import com.example.foodplanner.model.MealsItem;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class FavouritesFragment extends Fragment {

    private MealDataBase mealDataBase;
    private FirebaseFirestore firebaseFirestore;
    private FirebaseAuth firebaseAuth;
    private MealsItem mealsItem;
    private ImageView imageViewMealThumb;
    private TextView textViewMealName;
    private View view;
    private FavouritesListAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        mealsItem = new MealsItem();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_favourites, container, false);
        mealDataBase = MealDataBase.getInstance(getContext());


        imageViewMealThumb = view.findViewById(R.id.imageViewFavMealThumb);
        textViewMealName = view.findViewById(R.id.textViewFavMealName);

        RecyclerView recyclerViewFavMeal = view.findViewById(R.id.recyclerViewFavMeal);
        adapter = new FavouritesListAdapter();
        recyclerViewFavMeal.setAdapter(adapter);


        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mealDataBase.mealDao().getFavMeals()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<List<FavouriteMeal>>() {
                    @Override
                    public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@io.reactivex.rxjava3.annotations.NonNull List<FavouriteMeal> favouriteMeals) {
                        adapter.setList(favouriteMeals);
                    }

                    @Override
                    public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {

                    }
                });
    }
}