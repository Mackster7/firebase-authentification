package com.example.reecle;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class refridgerator extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private RadioGroup type,capacity;
    private RadioButton typeRadio,capacityRadio;
    private Button submit,accept;
    DatabaseReference rootRef,demoRef;
    Spinner spinner;
    TextView content;
    int price;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_refridgerator);
        getSupportActionBar().setTitle("Refridgerator details");

        Spinner spinner = findViewById(R.id.spinner1);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.fridge, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);


        addListenerOnButton();


    }
    public void addListenerOnButton() {

        type = (RadioGroup) findViewById(R.id.radioGroup);
        capacity = (RadioGroup) findViewById(R.id.radioGroup1);
        submit = (Button) findViewById(R.id.button);
        spinner=(Spinner) findViewById(R.id.spinner1);


        rootRef = FirebaseDatabase.getInstance().getReference();


        submit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {


                demoRef = rootRef.child("OTHER APPLIANCES/REFRIDGERATOR/");
                demoRef.child("price").addListenerForSingleValueEvent(new ValueEventListener() {

                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        //progressBar.setVisibility(View.VISIBLE);
                        int value = Integer.valueOf(String.valueOf(dataSnapshot.getValue()));
                        price = value;
                        int selectedId = type.getCheckedRadioButtonId();
                        int selectedId1 = capacity.getCheckedRadioButtonId();
                        switch (selectedId) {
                            case R.id.radioButton:
                                price = price - 1000;
                                break;
                            case R.id.radioButton1:
                                price = price - 800;

                                switch (selectedId1) {
                                    case R.id.radioButton4:
                                        price = price - 100;
                                        break;
                                    case R.id.radioButton5:
                                        price = price + 1;
                                        break;
                                    case R.id.radioButton6:
                                        price = price + 200;
                                        break;
                                }
                                break;
                            case R.id.radioButton2:
                                price = price - 600;

                                switch (selectedId1) {
                                    case R.id.radioButton4:
                                        Toast.makeText(refridgerator.this , "Configuration does not Exist." , Toast.LENGTH_SHORT).show();
                                        return;
                                    case R.id.radioButton5:
                                        price = price + 1;
                                        break;
                                    case R.id.radioButton6:
                                        price = price + 200;
                                        break;
                                }
                                break;
                            case R.id.radioButton3:
                                price = price - 200;

                                switch (selectedId1) {
                                    case R.id.radioButton4:
                                        Toast.makeText(refridgerator.this , "Configuration does not Exist." , Toast.LENGTH_SHORT).show();
                                        return;
                                    case R.id.radioButton5:
                                        price = price + 1;
                                        break;
                                    case R.id.radioButton6:
                                        price = price + 300;
                                        break;
                                }
                                break;
                        }

                        String brand=spinner.getSelectedItem().toString();
                        RadioButton mini=(RadioButton)findViewById(R.id.radioButton);

                        if(brand.equals("Select Brand")){
                            Toast.makeText(refridgerator.this , "Select a Brand." , Toast.LENGTH_SHORT).show();
                            return;
                        }
                        else if(brand.equals("Other") || mini.isChecked()){
                            price=price+1;
                        }
                        else
                        {
                            price=price+200;
                        }

                        if (selectedId == -1 || selectedId1 == -1 ) {
                            Toast.makeText(refridgerator.this , "Please Select All The Conditions." , Toast.LENGTH_SHORT).show();
                        }

                        else {
                            setContentView(R.layout.layout2);
                            getSupportActionBar().setTitle("Refridgerator Price");
                            content = (TextView) findViewById(R.id.content);
                            content.setText(String.valueOf("Rs" + price + "/-"));
                            accept = (Button) findViewById(R.id.Accept);

                            accept.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    String brand = spinner.getSelectedItem().toString().trim();
                                    String category = "Refridgerator";
                                    String price=content.getText().toString().trim();
                                    Intent intent = new Intent(getApplicationContext(), location.class);
                                    intent.putExtra("brand",brand);
                                    intent.putExtra("category",category);
                                    intent.putExtra("price",price);
                                    startActivity(intent);                                }
                            });

                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Toast.makeText(refridgerator.this , "" , Toast.LENGTH_SHORT).show();
                    }
                });


            }


        });


    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView , View view , int i , long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
