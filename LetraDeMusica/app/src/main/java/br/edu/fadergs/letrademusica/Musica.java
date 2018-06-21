package br.edu.fadergs.letrademusica;

public class Musica {
    private String id, nome, artista, letra, letraTraduzida;

    public Musica (String id, String nome, String artista){
        this.setId(id);
        this.setNome(nome);
        this.setArtista(artista);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getArtista() {
        return artista;
    }

    public void setArtista(String artista) {
        this.artista = artista;
    }

    public String getLetra() {
        return letra;
    }

    public void setLetra(String letra) {
        this.letra = letra;
    }

    public String getLetraTraduzida() {
        return letraTraduzida;
    }

    public void setLetraTraduzida(String letraTraduzida) {
        this.letraTraduzida = letraTraduzida;
    }
}
