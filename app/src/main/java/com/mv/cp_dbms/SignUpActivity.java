package com.mv.cp_dbms;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SignUpActivity extends AppCompatActivity {


    Button buttonSubmit;
    EditText editTextName, editTextFlatNo, editTextNumberOfFamilyMembers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);


        buttonSubmit = findViewById(R.id.buttonSubmit);
        editTextName = findViewById(R.id.editTextName);
        editTextFlatNo = findViewById(R.id.editTextFlatNo);
        editTextNumberOfFamilyMembers = findViewById(R.id.editTextNumberOfFamilyMembers);

        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!editTextName.getText().toString().equals("") && !editTextFlatNo.getText().toString().equals("") && !editTextNumberOfFamilyMembers.getText().toString().equals("")) {
                    SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE);
                    SharedPreferences.Editor myEdit = sharedPreferences.edit();
                    myEdit.putString("name", editTextName.getText().toString());
                    myEdit.putInt("flatNo", Integer.parseInt(editTextFlatNo.getText().toString()));
                    myEdit.putInt("numberOfFamilyMembers", Integer.parseInt(editTextNumberOfFamilyMembers.getText().toString()));
                    myEdit.apply();

                    Intent i = new Intent(SignUpActivity.this, HomeActivity.class);
                    startActivity(i);
                    finish();
                }
                else{
                    Toast.makeText(SignUpActivity.this, "Fill up all details!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}