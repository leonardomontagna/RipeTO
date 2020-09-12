package DAO;

public class Docenze{



    private String nome;
    private String titolo;
    private String data;
    private String ora;





    public Docenze(String nome,String titolo,String data,String ora) {
        this.nome=nome;
        this.titolo=titolo;
        this.data = data;
        this.ora=ora;
    }


    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTitolo() {
        return titolo;
    }

    public void setTitolo(String titolo) {
        this.titolo = titolo;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getOra() {
        return ora;
    }

    public void setOra(String ora) {
        this.ora = ora;
    }


}



