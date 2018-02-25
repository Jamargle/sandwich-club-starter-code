package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public final class JsonUtils {

    private static final String KEY_NAME = "name";
    private static final String KEY_MAIN_NAME = "mainName";
    private static final String KEY_ALSO_KNOWN_AS = "alsoKnownAs";
    private static final String KEY_ORIGIN = "placeOfOrigin";
    private static final String KEY_DESCRIPTION = "description";
    private static final String KEY_IMAGE = "image";
    private static final String KEY_INGREDIENTS = "ingredients";


    public static Sandwich parseSandwichJson(final String json) {
        try {
            final JSONObject jsonSandwich = new JSONObject(json);

            final Sandwich sandwich = new Sandwich();
            if (jsonSandwich.has(KEY_NAME)) {
                final JSONObject name = jsonSandwich.getJSONObject(KEY_NAME);
                sandwich.setMainName(name.optString(KEY_MAIN_NAME));

                final JSONArray alsoKnownJson = name.optJSONArray(KEY_ALSO_KNOWN_AS);
                final List<String> alsoKnownList = new ArrayList<>(alsoKnownJson.length());
                for (int i = 0; i < alsoKnownJson.length(); i++) {
                    alsoKnownList.add(alsoKnownJson.optString(i));
                }
                sandwich.setAlsoKnownAs(alsoKnownList);
            }
            sandwich.setPlaceOfOrigin(jsonSandwich.optString(KEY_ORIGIN));
            sandwich.setDescription(jsonSandwich.optString(KEY_DESCRIPTION));
            sandwich.setImage(jsonSandwich.optString(KEY_IMAGE));
            if (jsonSandwich.has(KEY_INGREDIENTS)) {
                final JSONArray ingredientsJson = jsonSandwich.getJSONArray(KEY_INGREDIENTS);
                final List<String> ingredientList = new ArrayList<>(ingredientsJson.length());
                for (int i = 0; i < ingredientsJson.length(); i++) {
                    ingredientList.add(ingredientsJson.optString(i));
                }
                sandwich.setIngredients(ingredientList);
            }

            return sandwich;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
}
