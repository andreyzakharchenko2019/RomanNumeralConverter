package com.andreyzakharchenko.romannumeralconverter.view;

public interface ConvertRomanNumeralToIntViewContract {
    void convertRomanNumeralToInt(String romanNumeral);
    void showMessage(String message);
    void showArabicNumeral(int arabicNumeral);
}
