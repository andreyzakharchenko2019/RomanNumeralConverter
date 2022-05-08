package com.andreyzakharchenko.romannumeralconverter.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.andreyzakharchenko.romannumeralconverter.R;
import com.andreyzakharchenko.romannumeralconverter.presenter.ConvertRomanNumeralToIntPresenter;
import com.andreyzakharchenko.romannumeralconverter.presenter.ConvertRomanNumeralToIntPresenterContract;

public class MainActivity extends AppCompatActivity implements ConvertRomanNumeralToIntViewContract {

    private ConvertRomanNumeralToIntPresenterContract convertRomanNumeralToIntPresenterContract;

    private TextView numericTextView;
    private EditText romanNumericEditText;
    private Button convertButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initialLayers();

        initialViews();

        setOnClickListeners();
    }

    private void initialLayers() {
        convertRomanNumeralToIntPresenterContract = new ConvertRomanNumeralToIntPresenter(this);
    }

    private void initialViews() {
        numericTextView = findViewById(R.id.numericTextView);
        romanNumericEditText = findViewById(R.id.romanNumericEditText);
        convertButton = findViewById(R.id.convertButton);
    }

    private void setOnClickListeners() {
        convertButton.setOnClickListener(view -> {
            convertRomanNumeralToInt(romanNumericEditText.getText().toString());
        });
    }

    @Override
    public void convertRomanNumeralToInt(String romanNumeral) {
        convertRomanNumeralToIntPresenterContract.convertRomanNumeralToInt(romanNumeral.toUpperCase());
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void showArabicNumeral(int arabicNumeral) {
        numericTextView.setText(String.valueOf(arabicNumeral));
    }
}