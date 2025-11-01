package com.example.pokemontracker;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private int currentLayout = 0; // 0 = Linear, 1 = Constraint, 2 = Table
    private final int[] layouts = {R.layout.linear_layout, R.layout.activity_main, R.layout.table_layout};
    private EditText nationalNumber, name, species, height, weight, hp, attack, defense;
    private Spinner levelSpinner;
    private RadioGroup radioGroup;

    private TextView nameCol, speciesCol, heightCol, weightCol,
            hpCol, attackCol, defenseCol, genderCol;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bindViews();
        setupSpinner();
        setButtonListeners();

        }
    private void bindViews() {
        nationalNumber = findViewById(R.id.nationalNumber);
        name = findViewById(R.id.name);
        species = findViewById(R.id.species);
        height = findViewById(R.id.height);
        weight = findViewById(R.id.weight);
        hp = findViewById(R.id.hp);
        attack = findViewById(R.id.attack);
        defense = findViewById(R.id.defense);
        levelSpinner = findViewById(R.id.levelSpinner);
        radioGroup = findViewById(R.id.genderGroup);

        nameCol = findViewById(R.id.nameLabel);
        speciesCol = findViewById(R.id.speciesLabel);
        heightCol = findViewById(R.id.heightLabel);
        weightCol = findViewById(R.id.weightLabel);
        hpCol = findViewById(R.id.hpLabel);
        attackCol = findViewById(R.id.attackLabel);
        defenseCol = findViewById(R.id.defenseLabel);
        genderCol = findViewById(R.id.genderLabel);
    }
    private void setupSpinner() {
        ArrayList<String> levels = new ArrayList<>();
        for (int i = 1; i <= 50; i++) levels.add(String.valueOf(i));
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item, levels);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        levelSpinner.setAdapter(adapter);
    }
    private void setButtonListeners() {
        Button resetButton = findViewById(R.id.resetButton);
        Button saveButton = findViewById(R.id.saveButton);
        Button toggleButton = findViewById(R.id.toggleLayoutButton);

        resetButton.setOnClickListener(v -> resetAllFields());
        saveButton.setOnClickListener(v -> submit());
        toggleButton.setOnClickListener(v -> switchLayout());
    }
    private void resetAllFields() {
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
        resetColors();
    }

    private void resetColors() {
        int defaultColor = Color.BLACK;
        nameCol.setTextColor(defaultColor);
        speciesCol.setTextColor(defaultColor);
        heightCol.setTextColor(defaultColor);
        weightCol.setTextColor(defaultColor);
        genderCol.setTextColor(defaultColor);
        hpCol.setTextColor(defaultColor);
        attackCol.setTextColor(defaultColor);
        defenseCol.setTextColor(defaultColor);
    }

    private void submit() {
        resetColors();
        boolean allValid = true;
        StringBuilder errors = new StringBuilder("Errors:\n");

        if (nationalNumber.getText().toString().trim().isEmpty() ||
                name.getText().toString().trim().isEmpty() ||
                species.getText().toString().trim().isEmpty() ||
                height.getText().toString().trim().isEmpty() ||
                weight.getText().toString().trim().isEmpty() ||
                hp.getText().toString().trim().isEmpty() ||
                attack.getText().toString().trim().isEmpty() ||
                defense.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, "All fields must be filled!", Toast.LENGTH_SHORT).show();
            allValid = false;
        }

        try {
            int natNum = Integer.parseInt(nationalNumber.getText().toString());
            if (natNum < 0 || natNum > 1010) {
                errors.append("National Number must be between 0–1010\n");
                allValid = false;
            }
        } catch (NumberFormatException e) {
            errors.append("Invalid National Number\n");
            allValid = false;
        }

        String nameVal = name.getText().toString().trim();
        if (!nameVal.matches("[A-Za-z. ]{3,12}")) {
            nameCol.setTextColor(Color.RED);
            errors.append("Name must be 3–12 letters (letters, dots, spaces only)\n");
            allValid = false;
        }

        String speciesVal = species.getText().toString().trim();
        if (!speciesVal.matches("[A-Za-z ]+")) {
            speciesCol.setTextColor(Color.RED);
            errors.append("Species may contain only letters and spaces\n");
            allValid = false;
        }

        int selectedGenderId = radioGroup.getCheckedRadioButtonId();
        if (selectedGenderId == -1) {
            genderCol.setTextColor(Color.RED);
            errors.append("Gender must be selected\n");
            allValid = false;
        } else {
            RadioButton rb = findViewById(selectedGenderId);
            String gender = rb.getText().toString();
            if (!(gender.equalsIgnoreCase("Male") || gender.equalsIgnoreCase("Female"))) {
                genderCol.setTextColor(Color.RED);
                errors.append("Gender must be Male or Female\n");
                allValid = false;
            }
        }

        try {
            double hVal = Double.parseDouble(height.getText().toString());
            if (hVal < 0.2 || hVal > 169.99) {
                heightCol.setTextColor(Color.RED);
                errors.append("Height must be between 0.2–169.99 m\n");
                allValid = false;
            }
        } catch (NumberFormatException e) {
            errors.append("Invalid Height format\n");
            allValid = false;
        }

        try {
            double wVal = Double.parseDouble(weight.getText().toString());
            if (wVal < 0.1 || wVal > 992.7) {
                weightCol.setTextColor(Color.RED);
                errors.append("Weight must be between 0.1–992.7 kg\n");
                allValid = false;
            }
        } catch (NumberFormatException e) {
            errors.append("Invalid Weight format\n");
            allValid = false;
        }

        try {
            int hpVal = Integer.parseInt(hp.getText().toString());
            if (hpVal < 1 || hpVal > 362) {
                hpCol.setTextColor(Color.RED);
                errors.append("HP must be between 1–362\n");
                allValid = false;
            }
        } catch (NumberFormatException e) {
            hpCol.setTextColor(Color.RED);
            errors.append("Invalid HP format\n");
            allValid = false;
        }

        try {
            int atkVal = Integer.parseInt(attack.getText().toString());
            if (atkVal < 0 || atkVal > 526) {
                attackCol.setTextColor(Color.RED);
                errors.append("Attack must be between 0–526\n");
                allValid = false;
            }
        } catch (NumberFormatException e) {
            attackCol.setTextColor(Color.RED);
            errors.append("Invalid Attack format\n");
            allValid = false;
        }

        try {
            int defVal = Integer.parseInt(defense.getText().toString());
            if (defVal < 10 || defVal > 614) {
                defenseCol.setTextColor(Color.RED);
                errors.append("Defense must be between 10–614\n");
                allValid = false;
            }
        } catch (NumberFormatException e) {
            defenseCol.setTextColor(Color.RED);
            errors.append("Invalid Defense format\n");
            allValid = false;
        }

        int levelVal = Integer.parseInt(levelSpinner.getSelectedItem().toString());
        if (levelVal < 1 || levelVal > 50) {
            errors.append("Level must be between 1–50\n");
            allValid = false;
        }

        if (allValid) {
            Toast.makeText(this, "Information stored in database!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, errors.toString(), Toast.LENGTH_LONG).show();
        }
    }

    private void switchLayout() {
        currentLayout = (currentLayout + 1) % layouts.length;
        setContentView(layouts[currentLayout]);
        bindViews();
        setupSpinner();
        setButtonListeners();
    }
}