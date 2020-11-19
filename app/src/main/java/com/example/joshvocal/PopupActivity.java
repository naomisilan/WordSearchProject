package com.example.joshvocal;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


import android.content.SharedPreferences;
import android.widget.Toast;

import com.example.joshvocal.view.MenuActivity;
import com.example.joshvocal.view.WordSearchActivity;



public class PopupActivity extends Activity  implements View.OnClickListener {

    EditText pName;
    String custName = "n.a";
    Button but1,but2;
    final int mode = Activity.MODE_PRIVATE;
    final String MYPREFS ="MyPreferences_001";
    SharedPreferences mySharedPreferences;
    SharedPreferences.Editor myEditor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popup);

        pName = (EditText)findViewById(R.id.playerName);
        but1 = (Button)findViewById(R.id.playerOk);
        but2 = (Button)findViewById(R.id.playerCancel);

        mySharedPreferences = getSharedPreferences(MYPREFS,0);
        myEditor = mySharedPreferences.edit();
        if(mySharedPreferences != null && mySharedPreferences.contains("custName")){
            showSavedPreferences();
        }else{
            pName.setText("");
        }

        but1 = (Button)findViewById(R.id.playerOk);
        but1.setOnClickListener(this);
        but2 = (Button)findViewById(R.id.playerCancel);
        but2.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        myEditor.clear();
        if (v.getId() ==but1.getId()){
            myEditor.putString("playerName", pName.getText().toString());
            startActivity(new Intent(PopupActivity.this, WordSearchActivity.class));
        } else {
            startActivity(new Intent(PopupActivity.this, MenuActivity.class));
        }

        myEditor.commit();
        applySavedPreferences();

    }

    public void applySavedPreferences(){
        SharedPreferences mySharedPreferences = getSharedPreferences(MYPREFS, mode);
        custName = mySharedPreferences.getString("playerName", "");
        String msg = "Goodluck, "+ custName + "!";
        Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_LONG).show();
    }

    public void showSavedPreferences() {
        SharedPreferences mySharedPreferences = getSharedPreferences(MYPREFS, mode);
        custName = mySharedPreferences.getString("playerName", "");
        String msg = custName;
        pName.setText(msg);
    }

    @Override
    protected void onPause (){
        savePreferences();
        super.onPause();
    }
    protected void savePreferences(){
        SharedPreferences mySharedPreferences = getSharedPreferences(MYPREFS,mode);
        SharedPreferences.Editor myEditor =  mySharedPreferences.edit();
        myEditor.putString("custName",pName.getText().toString());
        myEditor.commit();
    }




}