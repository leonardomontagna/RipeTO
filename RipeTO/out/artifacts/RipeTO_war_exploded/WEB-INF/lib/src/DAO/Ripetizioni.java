package DAO;

public class Ripetizioni {

    private String nome;
    private String titolo;
    private String data;
    private String ora;
    private String stato;
    private String id;
    private String email;


    public Ripetizioni(String nome,String titolo,String data,String ora,String id,String stato,String email) {
        this.nome = nome;
        this.titolo = titolo;
        this.data = data;
        this.ora = ora;
        this.id = id;
        this.stato = stato;
        this.email = email;
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

    public String getStato() { return stato; }

    public void setStato(String stato) { this.stato = stato; }

    public String getId() { return id; }

    public void setId(String id) { this.id = id; }

    public String getEmail() { return email; }

    public void setEmail(String email) { this.email = email; }
}
