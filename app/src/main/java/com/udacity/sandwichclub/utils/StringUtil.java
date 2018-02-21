package com.udacity.sandwichclub.utils;

import android.support.annotation.NonNull;

import java.util.List;

public final class StringUtil {

    public static String toStringSeparatedByCommas(@NonNull final List<String> list) {
        final String string = list.toString();
        // Remove the [ and ] returned by toString
        return string.substring(1, string.length() - 1);
    }
    
}
