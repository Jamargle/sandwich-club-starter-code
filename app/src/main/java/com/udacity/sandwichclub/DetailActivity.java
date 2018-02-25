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

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    @BindView(R.id.also_known_as_title) TextView alsoKnownAsTitle;
    @BindView(R.id.also_known_as) TextView alsoKnownAs;
    @BindView(R.id.origin_title) TextView originTitle;
    @BindView(R.id.origin) TextView origin;
    @BindView(R.id.description_title) TextView descriptionTitle;
    @BindView(R.id.description) TextView description;
    @BindView(R.id.ingredients_title) TextView ingredientsTitle;
    @BindView(R.id.ingredients) TextView ingredients;
    @BindView(R.id.sandwich_picture) ImageView ingredientsIv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);

        final Intent intent = getIntent();
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

        final String[] jsonSandwiches = getResources().getStringArray(R.array.sandwich_details);
        final Sandwich sandwich = JsonUtils.parseSandwichJson(jsonSandwiches[position]);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI(sandwich);
        Picasso.with(this)
                .load(sandwich.getImage())
                .placeholder(R.drawable.ic_placeholder).fit()
                .error(R.drawable.error_placeholder_image)
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(final Sandwich sandwich) {
        if (sandwich.getAlsoKnownAs() != null && sandwich.getAlsoKnownAs().size() > 0) {
            alsoKnownAsTitle.setVisibility(View.VISIBLE);
            alsoKnownAs.setVisibility(View.VISIBLE);
            alsoKnownAs.setText(StringUtil.toStringSeparatedByCommas(sandwich.getAlsoKnownAs()));
        }

        if (sandwich.getPlaceOfOrigin() != null && !sandwich.getPlaceOfOrigin().isEmpty()) {
            originTitle.setVisibility(View.VISIBLE);
            origin.setVisibility(View.VISIBLE);
            origin.setText(sandwich.getPlaceOfOrigin());
        }

        if (sandwich.getDescription() != null && !sandwich.getDescription().isEmpty()) {
            descriptionTitle.setVisibility(View.VISIBLE);
            description.setVisibility(View.VISIBLE);
            description.setText(sandwich.getDescription());
        }

        if (sandwich.getIngredients() != null && sandwich.getIngredients().size() > 0) {
            ingredientsTitle.setVisibility(View.VISIBLE);
            ingredients.setVisibility(View.VISIBLE);
            ingredients.setText(StringUtil.toStringSeparatedByCommas(sandwich.getIngredients()));
        }
    }

}
