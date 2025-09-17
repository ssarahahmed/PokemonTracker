package com.example.pokemontracker;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {


    private EditText nationalNumber, name, species, height, weight, hp, attack, defense;
    private Spinner levelSpinner;
    private Button resetButton, saveButton;
    private RadioGroup radioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        nationalNumber = findViewById(R.id.NationalNumber);
        name = findViewById(R.id.Name);
        species = findViewById(R.id.Species);
        height = findViewById(R.id.Height);
        weight = findViewById(R.id.Weight);
        hp = findViewById(R.id.HP);
        attack = findViewById(R.id.Attack);
        defense = findViewById(R.id.Defense);

        levelSpinner = findViewById(R.id.spinner);

        resetButton = findViewById(R.id.button);
        saveButton = findViewById(R.id.button2);
        radioGroup = findViewById(R.id.radioGroup);


        resetButton.setOnClickListener(v -> resetAllFields());
    }
    private void resetAllFields() {
        // Reset all EditText fields to their defaults
        nationalNumber.setText("896");
        name.setText("Glastrier");
        species.setText("Wild Horse Pokémon");
        height.setText("2.2");
        weight.setText("800.0");
        hp.setText("0");
        attack.setText("0");
        defense.setText("0");
        radioGroup.clearCheck();
        levelSpinner.setSelection(0);
}
}