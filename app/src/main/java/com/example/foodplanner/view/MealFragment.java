package com.example.foodplanner.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.foodplanner.R;
import com.example.foodplanner.database.MealDataBase;
import com.example.foodplanner.model.FavouriteMeal;
import com.example.foodplanner.model.MealsItem;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;


public class MealFragment extends Fragment {

    private static final String TAG = "MealFragment";
    private MealDataBase mealDataBase;

    String[] days = {"Saturday", "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday"};
    AutoCompleteTextView autoCompleteTextView;
    ArrayAdapter<String> adapterDays;

    List<String> list=new ArrayList<String>();
    SingleMealAdapter singleMealAdapter;
    YouTubePlayerView mealVideo;
    RecyclerView mealRecyclerView;
    View view;
    LinearLayoutManager linearLayoutManager;
    ImageView imageViewMealImage;
    TextView mealName , textViewStepsDetails , textViewArea;
    Button buttonFavourite;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, "onCreate: iteeeem");
        super.onCreate(savedInstanceState);
        linearLayoutManager = new LinearLayoutManager(requireContext());
        singleMealAdapter = new SingleMealAdapter(requireContext());
       mealDataBase = MealDataBase.getInstance(requireContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_meal, container, false);
        mealName = view.findViewById(R.id.textViewMealName);
        buttonFavourite = view.findViewById(R.id.buttonAddToFavourite);
        imageViewMealImage = view.findViewById(R.id.imageViewMealImage);
        textViewStepsDetails = view.findViewById(R.id.textViewStepsDetails);
        mealVideo = view.findViewById(R.id.videoViewMeal);
        textViewArea = view.findViewById(R.id.textViewArea);
        autoCompleteTextView = view.findViewById(R.id.autoCompleteTextView);

        adapterDays = new ArrayAdapter<>(getContext(), R.layout.dropdown_menu_list_item, days);
        autoCompleteTextView.setAdapter(adapterDays);

        autoCompleteTextView.setOnItemClickListener((parent, view, position, id) -> {
            String day = parent.getItemAtPosition(position).toString();
            Toast.makeText(getContext(), "Day: " + day, Toast.LENGTH_SHORT).show();
        });

        getLifecycle().addObserver(mealVideo);
        MealsItem mealsItem = MealFragmentArgs.fromBundle(getArguments()).getSingleMealItem();
        Glide.with(requireContext()).load(mealsItem.getStrMealThumb())
                .placeholder(R.drawable.ic_launcher_foreground)
                .apply(new RequestOptions().override(100,100)).into(imageViewMealImage);
        mealName.setText(mealsItem.getStrMeal());
        Log.i(TAG,
                "onCreateView: "+mealsItem.getStrYoutube());
        String [] split = mealsItem.getStrYoutube().split("=");
        Log.i(TAG, "onCreateView: "+split[0]);
        mealVideo.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
            @Override
            public void onReady(@NonNull YouTubePlayer youTubePlayer) {
                String videoId = split[0];
                youTubePlayer.loadVideo(videoId, 0);
            }
        });
        textViewStepsDetails.setText(mealsItem.getStrInstructions());
        textViewArea.setText(mealsItem.getStrArea());
//        singleMealAdapter.setList(mealsItem.getStrIngredient1());
//        list.add(mealsItem.getStrIngredient1());
//        list.add(mealsItem.getStrIngredient2());
//        list.add(mealsItem.getStrIngredient3());
//        list.add(mealsItem.getStrIngredient4());
//        list.add(mealsItem.getStrIngredient5());
//        list.add(mealsItem.getStrIngredient6());
//        list.add(mealsItem.getStrIngredient7());
//        list.add(mealsItem.getStrIngredient8());
//        list.add(mealsItem.getStrIngredient9());
        buttonFavourite.setOnClickListener(view -> mealDataBase.mealDao().insertFavMeal(new FavouriteMeal(Long.parseLong(mealsItem.getIdMeal()),
                mealsItem.getStrMeal(), mealsItem.getStrMealThumb(), mealsItem.getStrArea(), new ArrayList<>(), new ArrayList<>(),
                mealsItem.getStrInstructions(), mealsItem.getStrYoutube()))

                .subscribeOn(Schedulers.io())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {

                    }

                    @Override
                    public void onComplete() {

                    }

                    @Override
                    public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {

                    }
                }));

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mealRecyclerView = view.findViewById(R.id.recyclerViewIngredients);
//        allRecyclerView.setLayoutManager(linearLayoutManager);
//        singleMealAdapter.setList(list);
//        allRecyclerView.setAdapter(singleMealAdapter);
    }
}