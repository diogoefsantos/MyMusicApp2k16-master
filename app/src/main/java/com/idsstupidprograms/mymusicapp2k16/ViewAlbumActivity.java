package com.idsstupidprograms.mymusicapp2k16;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

public class ViewAlbumActivity extends AppCompatActivity implements View.OnClickListener {

    TextView album;
    TextView artista;
    TextView ano;
    RatingBar evaluation;

    Button button_delete;
    Button button_edit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Intent intent = getIntent();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_album);


        album = (TextView) findViewById(R.id.textNome_Album);
        artista = (TextView) findViewById(R.id.textArtista);
        ano = (TextView) findViewById(R.id.textAno);
        evaluation = (RatingBar) findViewById(R.id.ratingBar);

        button_delete = (Button) findViewById(R.id.button_delete);
        button_edit = (Button) findViewById(R.id.button_edit);


        album.setText(intent.getStringExtra("album"));
        artista.setText(intent.getStringExtra("artist"));
        ano.setText(intent.getStringExtra("ano"));
        evaluation.setRating(Integer.parseInt(intent.getStringExtra("evaluation")));

        button_delete.setOnClickListener(this);
        button_edit.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_delete :
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                // Get the layout inflater
                LayoutInflater inflater = this.getLayoutInflater();

                // Add the buttons
                builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User clicked OK button

                        AlertDialog al = (AlertDialog) dialog;
                        Intent intent = new Intent();
                        intent.putExtra("type", "view_album");
                        intent.putExtra("delete", "true");
                        intent.putExtra("position", getIntent().getStringExtra("position"));
                        setResult(RESULT_OK, intent);
                        finish();

                    }
                });
                builder.setNegativeButton("Não", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                    }
                });
                // Set other dialog properties
                builder.setTitle("ATENÇÃO!");
                builder.setMessage("Tem a certeza que deseja eliminar o album?");


                // Create the AlertDialog
                AlertDialog dialog = builder.create();
                dialog.show();
                break;
            case R.id.button_edit :
                startActivityForResult(new Intent(this, EditAlbumActivity.class)
                                .putExtra("position", getIntent().getStringExtra("position"))
                                .putExtra("album", getIntent().getStringExtra("album"))
                                .putExtra("artist", getIntent().getStringExtra("artist"))
                                .putExtra("ano", getIntent().getStringExtra("ano"))
                                .putExtra("evaluation", getIntent().getStringExtra("evaluation"))
                        , 1);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //Prevenção de erros:
        if(data == null)
            return;
        Intent intent = new Intent();
        intent.putExtra("type", "edit_album");
        intent.putExtra("position", data.getStringExtra("position"));
        intent.putExtra("album", data.getStringExtra("album"));
        intent.putExtra("artista", data.getStringExtra("artista"));
        intent.putExtra("ano", data.getStringExtra("ano"));
        intent.putExtra("evaluation", data.getStringExtra("evaluation"));
        setResult(RESULT_OK, intent);
        finish();
    };
}
