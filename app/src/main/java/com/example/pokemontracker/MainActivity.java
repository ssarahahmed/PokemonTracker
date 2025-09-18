package com.example.pokemontracker;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

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

        Button resetButton = findViewById(R.id.button);
        Button saveButton = findViewById(R.id.button2);
        radioGroup = findViewById(R.id.radioGroup);


        resetButton.setOnClickListener(v -> resetAllFields());
        saveButton.setOnClickListener(v -> submit());

        nameCol = findViewById(R.id.N);
        speciesCol = findViewById(R.id.S);
        heightCol = findViewById(R.id.H);
        weightCol = findViewById(R.id.W);
        hpCol = findViewById(R.id.Hpt);
        attackCol = findViewById(R.id.ATT);
        defenseCol = findViewById(R.id.Def);
        genderCol = findViewById(R.id.G);

        weight = findViewById(R.id.Weight);

        height = findViewById(R.id.Height);

        weight.addTextChangedListener(new TextWatcher() {
            boolean isEditing = false;
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }
            @Override
            public void afterTextChanged(Editable s) {
                if (isEditing) return;
                isEditing = true;
                String text = s.toString();

                // Check if the text contains a number or decimal
                if (!text.isEmpty()) {
                        weight.removeTextChangedListener(this);
                        weight.setText(text + " kg");
                        weight.setSelection(text.length()); // Keep cursor before " kg"
                        weight.addTextChangedListener(this);
                    }
                }
        });
        height.addTextChangedListener(new TextWatcher() {
            boolean isEditing = false;
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }
            @Override
            public void afterTextChanged(Editable s) {
                if (isEditing) return;
                isEditing = true;
                String text = s.toString();

                // Check if the text contains a number or decimal
                if (!text.isEmpty()) {
                    height.removeTextChangedListener(this);
                    height.setText(text + " m");
                    height.setSelection(text.length()); // Keep cursor before " kg"
                    height.addTextChangedListener(this);
                }
            }
        });
    }

    private void resetAllFields() {
        // Reset all EditText fields to their defaults
        nationalNumber.setHint("896");
        nationalNumber.setText("");
        name.setHint("Glastrier");
        name.setText("");
        species.setHint("Wild Horse Pokémon");
        species.setText("");
        height.setHint("2.2");
        height.setText("");
        weight.setHint("800.0");
        weight.setText("");
        hp.setHint("0");
        hp.setText("");
        attack.setHint("0");
        attack.setText("");
        defense.setHint("0");
        defense.setText("");
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


        if (nationalNumber.getText().toString().trim().isEmpty()) {
            errors.append("National Number is required");
            allValid = false;
        }
        if (name.getText().toString().trim().isEmpty()) {
            nameCol.setTextColor(Color.RED);
            errors.append("Name is required");
            allValid = false;
        }
        if (species.getText().toString().trim().isEmpty()) {
            speciesCol.setTextColor(Color.RED);
            errors.append("Species is required");
            allValid = false;
        }
        if (hp.getText().toString().trim().isEmpty()) {
            hpCol.setTextColor(Color.RED);
            errors.append("HP is required");
            allValid = false;
        }
        if (attack.getText().toString().trim().isEmpty()) {
            attackCol.setTextColor(Color.RED);
            errors.append("Attack is required");
            allValid = false;
        }
        if (defense.getText().toString().trim().isEmpty()) {
            defenseCol.setTextColor(Color.RED);
            errors.append("Defense is required");
            allValid = false;
        }
        if (height.getText().toString().trim().isEmpty()) {
            heightCol.setTextColor(Color.RED);
            errors.append("Height is required");
            allValid = false;
        }
        if (weight.getText().toString().trim().isEmpty()) {
            weightCol.setTextColor(Color.RED);
            errors.append("Weight is required");
            allValid = false;
        }

        if (radioGroup.getCheckedRadioButtonId() == -1) {
            genderCol.setTextColor(Color.RED);
            errors.append("Select a gender");
            allValid = false;
        }


        if (!allValid) {
            Toast.makeText(this, errors.toString(), Toast.LENGTH_LONG).show();
            return;
        }


        // Name 3–12 letters
        String nameLeng = name.getText().toString().trim();
        if (nameLeng.length() < 3 || nameLeng.length() > 12 || !nameLeng.matches("[a-zA-Z]+")) {
            nameCol.setTextColor(Color.RED);
            errors.append("Name must be 3–12 letters");
            allValid = false;
        }

        int hpVal = Integer.parseInt(hp.getText().toString());
        if (hpVal < 1 || hpVal > 362) {
            hpCol.setTextColor(Color.RED);
            errors.append("HP must be 1–362");
            allValid = false;
        }

        int attkVal = Integer.parseInt(attack.getText().toString());
        if (attkVal < 0 || attkVal > 526) {
            attackCol.setTextColor(Color.RED);
            errors.append("Attack must be 0–526");
            allValid = false;
        }

        int defVal = Integer.parseInt(defense.getText().toString());
        if (defVal < 10 || defVal > 614) {
            defenseCol.setTextColor(Color.RED);
            errors.append("Defense must be 10–614");
            allValid = false;
        }

        double hVal = Double.parseDouble(height.getText().toString());
        if (hVal < 0.2 || hVal > 169.99) {
            heightCol.setTextColor(Color.RED);
            errors.append("Height must be 0.2–169.99");
            allValid = false;
        }

        double wVal = Double.parseDouble(weight.getText().toString());
        if (wVal < 0.1 || wVal > 992.7) {
            weightCol.setTextColor(Color.RED);
            errors.append("Weight must be 0.1–992.7");
            allValid = false;
        }

        // Final result
        if (allValid) {
            Toast.makeText(this, "Information stored!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, errors.toString(), Toast.LENGTH_LONG).show();
        }
    }
    private void switchLayout() {
        // Move to the next layout
        currentLayout = (currentLayout + 1) % layouts.length;

        // Set the new layout
        setContentView(layouts[currentLayout]);

        // EdgeToEdge and window insets
        EdgeToEdge.enable(this);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Re-bind all UI elements for the new layout
        nationalNumber = findViewById(R.id.NationalNumber);
        name = findViewById(R.id.Name);
        species = findViewById(R.id.Species);
        height = findViewById(R.id.Height);
        weight = findViewById(R.id.Weight);
        hp = findViewById(R.id.HP);
        attack = findViewById(R.id.Attack);
        defense = findViewById(R.id.Defense);
        levelSpinner = findViewById(R.id.spinner);
        radioGroup = findViewById(R.id.radioGroup);

        Button resetButton = findViewById(R.id.button);
        Button saveButton = findViewById(R.id.button2);

        resetButton.setOnClickListener(v -> resetAllFields());
        saveButton.setOnClickListener(v -> submit());

        nameCol = findViewById(R.id.N);
        speciesCol = findViewById(R.id.S);
        heightCol = findViewById(R.id.H);
        weightCol = findViewById(R.id.W);
        hpCol = findViewById(R.id.Hpt);
        attackCol = findViewById(R.id.ATT);
        defenseCol = findViewById(R.id.Def);
        genderCol = findViewById(R.id.G);
    }

}
