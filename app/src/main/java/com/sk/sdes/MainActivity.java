package com.sk.sdes;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button buttonEncrypt,buttonDecrypt,buttonChangeText;
    EditText editKeyText,editBeginerText,editFinishText ;

    Key key;
    SDES sdes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editBeginerText=(EditText)findViewById(R.id.editBeginerText);
        editFinishText=(EditText)findViewById(R.id.editFinishText);
        editKeyText=(EditText)findViewById(R.id.editKeyText);

        buttonChangeText=(Button)findViewById(R.id.ChangeText);
        buttonChangeText.setText(">><<");
        buttonEncrypt=(Button)findViewById(R.id.buttonEncrypt);
        buttonDecrypt=(Button)findViewById(R.id.buttonDecrypt);

        buttonChangeText.setOnClickListener(this);
        buttonEncrypt.setOnClickListener(this);
        buttonDecrypt.setOnClickListener(this);

        sdes=new SDES();

        key=new Key();
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.ChangeText:
             //   String s= String.valueOf(editBeginerText.getText());
                editBeginerText.setText(editFinishText.getText());
                editFinishText.setText("");
                break;
            case R.id.buttonEncrypt:
                key.setKey(editKeyText.getText().toString());
                sdes.setKey(key);
                editFinishText.setText(sdes.encrupt(String.valueOf(editBeginerText.getText())));
                break;
            case R.id.buttonDecrypt:
                key.setKey(editKeyText.getText().toString());
                sdes.setKey(key);
                editFinishText.setText(sdes.decrupt(String.valueOf(editBeginerText.getText())));
                break;
        }
    }
}
