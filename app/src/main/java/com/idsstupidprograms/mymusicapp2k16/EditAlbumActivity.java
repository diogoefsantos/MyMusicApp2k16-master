package com.idsstupidprograms.mymusicapp2k16;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

public class EditAlbumActivity extends AppCompatActivity implements View.OnClickListener{

    TextView album;
    TextView artista;
    TextView ano;
    RatingBar evaluation;

    Button button_guardar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Intent intent = getIntent();


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_album);

        album = (TextView) findViewById(R.id.textView_album);
        artista = (TextView) findViewById(R.id.textView_artista);
        ano = (TextView) findViewById(R.id.textView_ano);
        evaluation = (RatingBar) findViewById(R.id.mRatingBar);

        button_guardar = (Button) findViewById(R.id.button_guardar);

        album.setText(intent.getStringExtra("album"));
        artista.setText(intent.getStringExtra("artist"));
        ano.setText(intent.getStringExtra("ano"));
        evaluation.setRating(Integer.parseInt(intent.getStringExtra("evaluation")));

        button_guardar.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_guardar:
                if(validate()) {
                    Intent intent = new Intent();
                    intent.putExtra("type", "edit_album");
                    intent.putExtra("position", getIntent().getStringExtra("position"));
                    intent.putExtra("album", album.getText().toString());
                    intent.putExtra("artista", artista.getText().toString());
                    intent.putExtra("ano", ano.getText().toString());
                    intent.putExtra("evaluation", new Integer(Math.round(evaluation.getRating())).toString());
                    setResult(RESULT_OK, intent);
                    finish();
                }
                break;
        }
    }

    public boolean validate () {
        boolean Balbum = false;
        boolean Bartist = false;
        boolean Bano = false;
        boolean Bevaluation = false;

        if(album.getText().toString().equals("")) {
            Toast.makeText(getApplicationContext(), "Parámetro de Album incorreto", Toast.LENGTH_SHORT).show();
            Balbum = false;
        }else {
            Balbum = true;
        }
        if(artista.getText().toString().equals("")) {
            if (Balbum)
                Toast.makeText(getApplicationContext(), "Parámetro de Artista incorreto", Toast.LENGTH_SHORT).show();
            Bartist = false;
        }else {
            Bartist = true;
        }

        if(ano.getText().toString().equals("") ||
                ano.getText().toString().length() < 4 ||
                new Integer(ano.getText().toString()) < 1950 ||
                new Integer(ano.getText().toString()) > 2100) {
            if(Balbum && Bartist)
                Toast.makeText(getApplicationContext(), "Parámetro de Ano incorreto", Toast.LENGTH_SHORT).show();
            Bano = false;
        }else {
            Bano = true;
        }

        if (!Balbum || !Bartist || !Bano) {
            return false;
        }else {
            return true;
        }
    }
}
