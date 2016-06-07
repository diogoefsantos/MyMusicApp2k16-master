package com.idsstupidprograms.mymusicapp2k16;

public class Album {
    private String album;
    private String artist;
    private int ano;
    private int evaluation;



    public Album(String album, String artist, int ano, int evaluation) {
        this.album = album;
        this.artist = artist;
        this.ano = ano;
        this.evaluation = evaluation;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public int getAno() {
        return ano;
    }

    public void setAno(int ano) {
        this.ano = ano;
    }

    public int getEvaluation() {
        return evaluation;
    }

    public void setEvaluation(int evaluation) {
        this.evaluation = evaluation;
    }
}
