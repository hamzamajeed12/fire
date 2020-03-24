package com.example.fire;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    FirebaseFirestore objectFirebaseFirestore;
    EditText studentNoET,studentNameET,studentRollET;

    TextView valuesTV;
    DocumentReference objectDocumentReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try
        {
            objectFirebaseFirestore = FirebaseFirestore.getInstance();
            studentNoET = findViewById(R.id.studentNoET);
            studentNameET = findViewById(R.id.studentNameET);
            studentRollET = findViewById(R.id.studentRollET);

            valuesTV = findViewById(R.id.valueTV);
            if(!studentNoET.getText().toString().isEmpty())
            {
                objectDocumentReference=objectFirebaseFirestore.collection("BSCS_6A").document(studentNoET.getText().toString());
            }

        }
        catch (Exception e)
        {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public void addVluesToFirebase(View View)

    {
        try
        {
            if (!studentNoET.getText().toString().isEmpty()
            && !studentNameET.getText().toString().isEmpty()
            && !studentRollET.getText().toString().isEmpty())
            {
                Map<String, String> objectMap = new HashMap<>();
                objectMap.put("Name", studentNameET.getText().toString());
                objectMap.put("Roll_No",studentRollET.getText().toString());

                objectFirebaseFirestore
                        .collection("BSCS_6A")
                        .document(studentNoET.getText().toString())
                        .set(objectMap)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(MainActivity.this, "Data is Stored", Toast.LENGTH_SHORT).show();
                            }
                        }) 
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(MainActivity.this, "Fails to Add Data", Toast.LENGTH_SHORT).show();
                            }
                        });
            }

        }
        catch (Exception e)
        {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
    public void getValuesFromFirebase(View view)
    {
        try
        {
            if(!studentNoET.getText().toString().isEmpty())
            {
                objectDocumentReference=objectFirebaseFirestore.collection("BSCS_6A").document(studentNoET.getText().toString());
                
                objectDocumentReference.get()
                        .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                            @Override
                            public void onSuccess(DocumentSnapshot documentSnapshot) {
                                String studentNo=documentSnapshot.getId();
                                String studentName=documentSnapshot.getString("Name");
                                String studentRollNO=documentSnapshot.getString("Roll_No");
                                valuesTV.setText(
                                        "Student No: "+studentNo+"\n"+
                                                "Name: "+studentName+"\n" +
                                                "Roll No: "+studentRollNO
                                );
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(MainActivity.this, "Fails to Get Values Back", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        }
        catch(Exception e)
        {
            Toast.makeText(this,e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}
