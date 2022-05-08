package com.andreyzakharchenko.romannumeralconverter.presenter;

import static com.andreyzakharchenko.romannumeralconverter.util.Constants.LOG_NAME;
import static com.andreyzakharchenko.romannumeralconverter.util.Constants.REGEX_FOR_ROMAN_NUMERAL;
import static com.andreyzakharchenko.romannumeralconverter.util.Constants.SPACE;

import android.app.Activity;
import android.util.Log;

import com.andreyzakharchenko.romannumeralconverter.R;
import com.andreyzakharchenko.romannumeralconverter.util.RomanNumeral;
import com.andreyzakharchenko.romannumeralconverter.view.ConvertRomanNumeralToIntViewContract;

import java.nio.charset.StandardCharsets;

public class ConvertRomanNumeralToIntPresenter implements ConvertRomanNumeralToIntPresenterContract {

    private ConvertRomanNumeralToIntViewContract convertRomanNumeralToIntViewContract;
    private Activity activity;

    public ConvertRomanNumeralToIntPresenter(ConvertRomanNumeralToIntViewContract convertRomanNumeralToIntViewContract) {
        this.convertRomanNumeralToIntViewContract = convertRomanNumeralToIntViewContract;
        activity = (Activity) this.convertRomanNumeralToIntViewContract;
    }

    @Override
    public void convertRomanNumeralToInt(String romanNumeral) {
        if (validateRomanNumeral(romanNumeral)) {
            convertBytesToInt(romanNumeral.getBytes(StandardCharsets.UTF_8));
        }
    }

    private boolean validateRomanNumeral(String romanNumeral) {
        if (romanNumeral == null || romanNumeral.length() == 0) {
            showMessage(activity.getResources().getString(R.string.error_empty_string));
            return false;
        }
        if (romanNumeral.length() > 15) {
            showMessage(activity.getResources().getString(R.string.error_too_big_numeral));
            return false;
        }
        if (romanNumeral.contains(SPACE)) {
            showMessage(activity.getResources().getString(R.string.error_numeral_has_space));
            return false;
        }
        if (!romanNumeral.matches(REGEX_FOR_ROMAN_NUMERAL)) {
            showMessage(activity.getResources().getString(R.string.error_roman_numeral));
            return false;
        }
        byte[] romanNumeralBytes = romanNumeral.getBytes(StandardCharsets.UTF_8);
        if (!checkEqualSuccessively(romanNumeralBytes)) {
            return false;
        }
        return checkOrderNumbers(romanNumeralBytes);
    }

    private void showMessage(String message) {
        convertRomanNumeralToIntViewContract.showMessage(message);
    }

    private boolean checkEqualSuccessively(byte[] romanNumeral) {
        try {
            for (int i = 0; i < romanNumeral.length; i++) {
                byte currentCharacter = romanNumeral[i];
                if (currentCharacter == romanNumeral[i + 1]) {
                    // Арабские цифры соответствуют байтовому значению букв, обозначающих римские цифры
                    // 86 - V, 76 - L, 68 - D
                    // По правилу формирования римских цифр, они не могут повторятся друг за другом
                    // Например: VV - Недопустимая запись
                    if (currentCharacter == 86 || currentCharacter == 76 || currentCharacter == 68) {
                        showMessage(activity.getResources().getString(R.string.error_roman_numeral));
                        return false;
                    }
                }
                if (currentCharacter == romanNumeral[i + 1] && currentCharacter == romanNumeral[i + 2] &&
                        currentCharacter == romanNumeral[i + 3]) {
                    showMessage(activity.getResources().getString(R.string.error_roman_numeral));
                    return false;
                }
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            Log.d(LOG_NAME, "Ignore Error in checkEqualSuccessively - " + e);
        }
        return true;
    }

    private boolean checkOrderNumbers(byte[] romanNumeral) {
        try {
            for (int i = 0; i < romanNumeral.length; i++) {
                int currentNumber = RomanNumeral.romanNumeral.get(romanNumeral[i]);
                int nextNumber = RomanNumeral.romanNumeral.get(romanNumeral[i + 1]);
                if (currentNumber < nextNumber) {
                    if (currentNumber == 1000 || currentNumber == 500 ||
                            currentNumber == 50 || currentNumber == 5) {
                        showMessage(activity.getResources().getString(R.string.error_roman_numeral_order));
                        return false;
                    }
                    if (!RomanNumeral.rulesRomanNumeral.get(currentNumber).contains(nextNumber)) {
                        showMessage(activity.getResources().getString(R.string.error_roman_numeral_order));
                        return false;
                    }
                }
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            Log.d(LOG_NAME, "Ignore Error in checkOrderNumbers - " + e);
        }
        return true;
    }

    private void convertBytesToInt(byte[] romanNumeral) {
        Log.d(LOG_NAME, new String(romanNumeral));
        int arabicNumeral = 0;
        int size = romanNumeral.length;
        try {
            for (int i = 0; i < romanNumeral.length; i++) {
                int currentNumber = RomanNumeral.romanNumeral.get(romanNumeral[i]);
                int nextNumber;
                if (i + 1 < size) {
                    nextNumber = RomanNumeral.romanNumeral.get(romanNumeral[i + 1]);
                } else {
                    nextNumber = 0;
                }
                if (currentNumber >= nextNumber) {
                    arabicNumeral += currentNumber;
                } else {
                    arabicNumeral += nextNumber - currentNumber;
                    i++;
                }
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            Log.d(LOG_NAME, "Ignore Error in convertBytesToInt - " + e);
        }
        Log.d(LOG_NAME, "arabicNumeral - " + arabicNumeral);
        convertRomanNumeralToIntViewContract.showArabicNumeral(arabicNumeral);
    }
}
