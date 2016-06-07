package com.idsstupidprograms.mymusicapp2k16;

import android.app.Activity;
import android.content.Intent;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    // implements View.OnClickListener -> permite criar event listeners
    private ArrayList<String> albuns;

    //Variáveis:
    public Button button_add_album;
    public Button button_start_search;

    public ListView lista_abuns;

    public AlbumListAdapter adapter;
    public List<Album> mAlbumList = new ArrayList<>();
    public TextView number_list;

    //Variável do botão do menu (fechado)
    boolean menuSearchItem = false;



    //Commandos executados quando o programa é inicializado;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        number_list = (TextView) findViewById(R.id.number_list);

        /******************************
         * L I S T A
         ******************************/

        addListItem("Album", "Artista", 2015, 3);
        addListItem("test", "test Artista", 2014, 5);


        /******************************
         * FIM -    L I S T A
         ******************************/


        /******************************
         * B O T Ã O   A D I C I O N A R   N O V O   A L B U M
         ******************************/
        button_add_album = (Button) findViewById(R.id.button_add_album);
        button_add_album.setOnClickListener(this);

        /******************************
         * F I M -   B O T Ã O   A D I C I O N A R   N O V O   A L B U M
         ******************************/


        /******************************
         * B O T Ã O   P R O C U R A R   A L B U M
         ******************************/
        button_start_search = (Button) findViewById(R.id.button_start_search);
        button_start_search.setOnClickListener(this);
        /******************************
         * F I M -   B O T Ã O   P R O C U R A R   A L B U M
         ******************************/

        /******************************
         * L I S T A   A L B U N S
         ******************************/
        lista_abuns.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            //Basicamente foi criado um eventListener de cada item da lista, que depois de clickado
            //abre uma nova activity
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                openNew(position);
            }
        });
        /******************************
         * F I M -   L I S T A   A L B U N S
         ******************************/

        final TextView edit_text_search = (TextView) findViewById(R.id.edit_text_search);
        final Spinner spinner_search = (Spinner) findViewById(R.id.spinner_search);
        //Alteração do tipo de campo conforme o item escolhido no spinner de procura
        spinner_search.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                String selectedItem = (String) spinner_search.getSelectedItem().toString();
                switch (selectedItem) {
                    case "Tudo" :
                        edit_text_search.setInputType(InputType.TYPE_CLASS_TEXT);
                        break;
                    case "Album" :
                        edit_text_search.setInputType(InputType.TYPE_CLASS_TEXT);
                        break;
                    case "Artísta" :
                        edit_text_search.setInputType(InputType.TYPE_CLASS_TEXT);
                        break;
                    case "Ano de lançamento" :
                        edit_text_search.setInputType(InputType.TYPE_CLASS_NUMBER);
                        break;
                    case "Avaliação" :
                        edit_text_search.setInputType(InputType.TYPE_CLASS_NUMBER);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {}
        });
    };

    //Abertura duma nova view para pré-visualização do album escolhido
    public void openNew (int position){

        startActivityForResult(new Intent(this, ViewAlbumActivity.class)
                        .putExtra("position", new Integer(position).toString())
                        .putExtra("album", mAlbumList.get(position).getAlbum())
                        .putExtra("artist", mAlbumList.get(position).getArtist())
                        .putExtra("ano", new Integer(mAlbumList.get(position).getAno()).toString())
                        .putExtra("evaluation", new Integer(mAlbumList.get(position).getEvaluation()).toString())
                , 1);
    }

    //Event listener:
    @Override
    public void onClick(View v){
        switch (v.getId()) {
            case R.id.button_add_album :
                //Evento do botão de adição de um novo album
                startActivityForResult(new Intent(this, NewAlbumActivity.class), 1);
                break;
            case R.id.button_start_search :
                startSearch();
                hideKeyboard(v);
                break;
        }
    };

    //Event results listener
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //Prevenção de erros:
        if(data == null)
            return;
        if(data.getStringExtra("type").equals("new_album")) {
            Toast.makeText(getApplicationContext(), "Foi adicionado um novo album" , Toast.LENGTH_LONG).show();
            addListItem(data.getStringExtra("album"),
                    data.getStringExtra("artista"),
                    Integer.parseInt(data.getStringExtra("ano")),
                    Integer.parseInt(data.getStringExtra("evaluation")));
        }else if(data.getStringExtra("type").equals("view_album")) {
            if(data.getStringExtra("delete").equals("true")) {
                mAlbumList.remove(mAlbumList.get(new Integer(data.getStringExtra("position"))));

                Toast.makeText(getApplicationContext(), "Foi removido um album" , Toast.LENGTH_SHORT).show();

                number_list.setText(new Integer(mAlbumList.size()).toString());

                lista_abuns = (ListView) findViewById(R.id.lista_abuns);
                adapter = new AlbumListAdapter(getApplicationContext(), mAlbumList);
                lista_abuns.setAdapter(adapter);
            }
        }else if(data.getStringExtra("type").equals("edit_album")) {
            int pos = new Integer(data.getStringExtra("position"));

            mAlbumList.get(pos).setAlbum(data.getStringExtra("album"));
            mAlbumList.get(pos).setArtist(data.getStringExtra("artista"));
            mAlbumList.get(pos).setAno(new Integer(data.getStringExtra("ano")));
            mAlbumList.get(pos).setEvaluation(new Integer(data.getStringExtra("evaluation")));

            Toast.makeText(getApplicationContext(), "Foi editado um album" , Toast.LENGTH_SHORT).show();

            lista_abuns = (ListView) findViewById(R.id.lista_abuns);
            adapter = new AlbumListAdapter(getApplicationContext(), mAlbumList);
            lista_abuns.setAdapter(adapter);
        }

    };

    //Criação do menu:
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        //AQUI:
        menuInflater.inflate(R.menu.search_activity, menu);
        return super.onCreateOptionsMenu(menu);
    };

    //Eventos do menu:
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.search_button :
                //Botão de procura foi clicado:
                if (!menuSearchItem) {
                    startSearchBox();
                    item.setIcon(R.drawable.ic_search_close);
                    menuSearchItem = true;
                }else {
                    endSearchBox();
                    item.setIcon(R.drawable.ic_search);
                    menuSearchItem = false;
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    };

    public void addListItem(String album, String artista, int ano, int evaluation){

        lista_abuns = (ListView) findViewById(R.id.lista_abuns);

        mAlbumList.add(new Album(album, artista, ano, evaluation));

        number_list.setText(new Integer(mAlbumList.size()).toString());

        adapter = new AlbumListAdapter(getApplicationContext(), mAlbumList);
        lista_abuns.setAdapter(adapter);
    };

    public void startSearchBox(){
        LinearLayout searchBox = (LinearLayout) findViewById(R.id.search_Box);

        searchBox.setVisibility(View.VISIBLE);
    };

    public void endSearchBox(){
        TextView main_text = (TextView) findViewById(R.id.main_text);
        main_text.setText("Albuns existentes:");
        LinearLayout searchBox = (LinearLayout) findViewById(R.id.search_Box);
        searchBox.setVisibility(View.GONE);
        lista_abuns = (ListView) findViewById(R.id.lista_abuns);
        number_list.setText(new Integer(mAlbumList.size()).toString());
        adapter = new AlbumListAdapter(getApplicationContext(), mAlbumList);
        lista_abuns.setAdapter(adapter);
    }

    public void startSearch (){
        //Variáveis dos campo, botão e spinner
        TextView main_text = (TextView) findViewById(R.id.main_text);
        main_text.setText("Resultados de pesquisa:");

        EditText edit_text_search = (EditText) findViewById(R.id.edit_text_search);
        Button button_start_search = (Button) findViewById(R.id.button_start_search);
        Spinner spinner_search = (Spinner) findViewById(R.id.spinner_search);


        //Variáveis de informação contida nas variáveis
        String termo = edit_text_search.getText().toString();
        String selectedItem = (String) spinner_search.getSelectedItem().toString();


        //Procura conforme o parametro no spinner
        switch (selectedItem) {
            case "Tudo" :
                showSearchResults("tudo", termo);
                break;
            case "Album" :
                showSearchResults("album", termo);
                break;
            case "Artísta" :
                showSearchResults("artist", termo);
                break;
            case "Ano de lançamento" :
                showSearchResults("ano", termo);
                break;
            case "Avaliação" :
                showSearchResults("evaluation", termo);
                break;
            case "Artista ou album" :
                showSearchResults("artistaOualbum", termo);
                break;
        }
    };

    public void showSearchResults(String type, String value) {

        //Variável da lista dos resultados
        List<Album> searchResult = new ArrayList<>();

        //Variável da lista
        lista_abuns = (ListView) findViewById(R.id.lista_abuns);

        for(int i=0; i < mAlbumList.size(); i++) {
            if(type.equals("tudo")){
                if (mAlbumList.get(i).getAlbum().contains((value)) ||
                        mAlbumList.get(i).getArtist().contains((value)) ||
                        new Integer(mAlbumList.get(i).getAno()).toString().equals(value) ||
                        new Integer(mAlbumList.get(i).getEvaluation()).toString().equals(value)
                        ){
                    searchResult.add(mAlbumList.get(i));
                }
            }else if (type.equals("album")) {
                if (mAlbumList.get(i).getAlbum().contains((value))) {
                    searchResult.add(mAlbumList.get(i));
                }
            }else if (type.equals("artist")) {
                if(mAlbumList.get(i).getArtist().contains((value))){
                    searchResult.add(mAlbumList.get(i));
                }
            }else if (type.equals("ano")) {
                if(new Integer(mAlbumList.get(i).getAno()).toString().equals(value)){
                    searchResult.add(mAlbumList.get(i));
                }
            }else if (type.equals("evaluation")) {
                if(new Integer(mAlbumList.get(i).getEvaluation()).toString().equals(value)){
                    searchResult.add(mAlbumList.get(i));
                }
            }else if (type.equals("artistaOualbum")) {
                if(mAlbumList.get(i).getAlbum().contains((value)) ||
                        mAlbumList.get(i).getArtist().contains((value))){
                    searchResult.add(mAlbumList.get(i));
                }
            }
            number_list.setText(new Integer(searchResult.size()).toString());
        };

        adapter = new AlbumListAdapter(getApplicationContext(), searchResult);
        lista_abuns.setAdapter(adapter);
    };


    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager =(InputMethodManager)getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

}