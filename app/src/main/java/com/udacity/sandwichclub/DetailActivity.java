package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;
import com.udacity.sandwichclub.utils.StringUtil;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView ingredientsIv = findViewById(R.id.sandwich_picture);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = DEFAULT_POSITION;
        if (intent != null) {
            position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        }
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Sandwich sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI(sandwich);
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(final Sandwich sandwich) {
        if (sandwich.getAlsoKnownAs() != null && sandwich.getAlsoKnownAs().size() > 0) {
            final TextView alsoKnownAsTitle = findViewById(R.id.also_known_as_title);
            alsoKnownAsTitle.setVisibility(View.VISIBLE);
            final TextView alsoKnownAs = findViewById(R.id.also_known_as);
            alsoKnownAs.setVisibility(View.VISIBLE);
            alsoKnownAs.setText(StringUtil.toStringSeparatedByCommas(sandwich.getAlsoKnownAs()));
        }

        if (sandwich.getPlaceOfOrigin() != null && !sandwich.getPlaceOfOrigin().isEmpty()) {
            final TextView originTitle = findViewById(R.id.origin_title);
            originTitle.setVisibility(View.VISIBLE);
            final TextView origin = findViewById(R.id.origin);
            origin.setVisibility(View.VISIBLE);
            origin.setText(sandwich.getPlaceOfOrigin());
        }

        if (sandwich.getDescription() != null && !sandwich.getDescription().isEmpty()) {
            final TextView descriptionTitle = findViewById(R.id.description_title);
            descriptionTitle.setVisibility(View.VISIBLE);
            final TextView description = findViewById(R.id.description);
            description.setVisibility(View.VISIBLE);
            description.setText(sandwich.getDescription());
        }

        if (sandwich.getIngredients() != null && sandwich.getIngredients().size() > 0) {
            final TextView ingredientsTitle = findViewById(R.id.ingredients_title);
            ingredientsTitle.setVisibility(View.VISIBLE);
            final TextView ingredients = findViewById(R.id.ingredients);
            ingredients.setVisibility(View.VISIBLE);
            ingredients.setText(StringUtil.toStringSeparatedByCommas(sandwich.getIngredients()));
        }
    }
}
