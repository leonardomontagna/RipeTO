package DAO;

public class Docenti {
    private String id_docente;
    private String nome;

    public Docenti(String id_docente, String nome) {
        this.id_docente = id_docente;
        this.nome = nome;
    }


    public String getId_docente() {
        return id_docente;
    }

    public void setId_docente(String id_docente) {
        this.id_docente = id_docente;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
