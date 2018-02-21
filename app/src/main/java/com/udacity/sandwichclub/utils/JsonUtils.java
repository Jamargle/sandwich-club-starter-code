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
                if (name.has(KEY_MAIN_NAME)) {
                    sandwich.setMainName(name.getString(KEY_MAIN_NAME));
                }
                if (name.has(KEY_ALSO_KNOWN_AS)) {
                    final JSONArray alsoKnownJson = name.getJSONArray(KEY_ALSO_KNOWN_AS);
                    final List<String> alsoKnownList = new ArrayList<>(alsoKnownJson.length());
                    for (int i = 0; i < alsoKnownJson.length(); i++) {
                        alsoKnownList.add(alsoKnownJson.getString(i));
                    }
                    sandwich.setAlsoKnownAs(alsoKnownList);
                }
            }
            if (jsonSandwich.has(KEY_ORIGIN)) {
                sandwich.setPlaceOfOrigin(jsonSandwich.getString(KEY_ORIGIN));
            }
            if (jsonSandwich.has(KEY_DESCRIPTION)) {
                sandwich.setDescription(jsonSandwich.getString(KEY_DESCRIPTION));
            }
            if (jsonSandwich.has(KEY_IMAGE)) {
                sandwich.setImage(jsonSandwich.getString(KEY_IMAGE));
            }
            if (jsonSandwich.has(KEY_INGREDIENTS)) {
                final JSONArray ingredientsJson = jsonSandwich.getJSONArray(KEY_INGREDIENTS);
                final List<String> ingredientList = new ArrayList<>(ingredientsJson.length());
                for (int i = 0; i < ingredientsJson.length(); i++) {
                    ingredientList.add(ingredientsJson.getString(i));
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
