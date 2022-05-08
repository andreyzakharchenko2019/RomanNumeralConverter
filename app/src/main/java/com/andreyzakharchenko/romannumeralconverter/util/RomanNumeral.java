package com.andreyzakharchenko.romannumeralconverter.util;

import static com.andreyzakharchenko.romannumeralconverter.util.Constants.LOG_NAME;

import android.hardware.lights.LightState;
import android.util.Log;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RomanNumeral {

    public static Map<Byte, Integer> romanNumeral;
    public static Map<Integer, List<Integer>> rulesRomanNumeral;

    static {
        romanNumeral = new HashMap<>();
        romanNumeral.put("I".getBytes(StandardCharsets.UTF_8)[0], 1);
        romanNumeral.put("V".getBytes(StandardCharsets.UTF_8)[0], 5);
        romanNumeral.put("X".getBytes(StandardCharsets.UTF_8)[0], 10);
        romanNumeral.put("L".getBytes(StandardCharsets.UTF_8)[0], 50);
        romanNumeral.put("C".getBytes(StandardCharsets.UTF_8)[0], 100);
        romanNumeral.put("D".getBytes(StandardCharsets.UTF_8)[0], 500);
        romanNumeral.put("M".getBytes(StandardCharsets.UTF_8)[0], 1000);
        Log.d(LOG_NAME, "romanNumeral - " + romanNumeral);

        rulesRomanNumeral = new HashMap<>();
        rulesRomanNumeral.put(1, Arrays.asList(5, 10));
        rulesRomanNumeral.put(10, Arrays.asList(50, 100));
        rulesRomanNumeral.put(100, Arrays.asList(500, 1000));
    }
}
