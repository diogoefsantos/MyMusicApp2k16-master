package com.idsstupidprograms.mymusicapp2k16;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

public class NewAlbumActivity extends AppCompatActivity implements View.OnClickListener {

    EditText textView_album;
    EditText textView_artista;
    EditText textView_ano;

    RatingBar ratingBar;


    Button button_new_c_add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_album);

        textView_album = (EditText) findViewById(R.id.textView_album);
        textView_artista = (EditText) findViewById(R.id.textView_artista);
        textView_ano = (EditText) findViewById(R.id.textView_ano);

        ratingBar = (RatingBar) findViewById(R.id.mRatingBar);

        button_new_c_add = (Button) findViewById(R.id.button_new_c_add);
        button_new_c_add.setOnClickListener(this);

    }


    //Event listener:
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_new_c_add:
                hideKeyboard(v);
                if(validate()) {
                    Intent intent = new Intent();
                    intent.putExtra("type", "new_album");
                    intent.putExtra("album", textView_album.getText().toString());
                    intent.putExtra("artista", textView_artista.getText().toString());
                    intent.putExtra("ano", textView_ano.getText().toString());
                    intent.putExtra("evaluation", new Integer(Math.round(ratingBar.getRating())).toString());
                    setResult(RESULT_OK, intent);
                    finish();
                }
                break;
        }
    }

    public boolean validate () {
        boolean album = false;
        boolean artist = false;
        boolean ano = false;
        boolean evaluation = false;

        if(textView_album.getText().toString().equals("")) {
            Toast.makeText(getApplicationContext(), "Parámetro de Album incorreto", Toast.LENGTH_SHORT).show();
            album = false;
        }else {
            album = true;
        }
        if(textView_artista.getText().toString().equals("")) {
            if(album)
                Toast.makeText(getApplicationContext(), "Parámetro de Artista incorreto", Toast.LENGTH_SHORT).show();
            artist = false;
        }else {
            artist = true;
        }

        if(textView_ano.getText().toString().equals("") ||
                textView_ano.getText().toString().length() < 4 ||
                new Integer(textView_ano.getText().toString()) < 1950 ||
                new Integer(textView_ano.getText().toString()) > 2100) {
            if (album && artist)
                Toast.makeText(getApplicationContext(), "Parámetro de Ano incorreto", Toast.LENGTH_SHORT).show();
            ano = false;
        }else {
            ano = true;
        }

        if (!album || !artist || !ano) {
            return false;
        }else {
            return true;
        }
    }

    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager =(InputMethodManager)getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}
