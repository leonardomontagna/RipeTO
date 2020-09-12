package DAO;

import java.sql.*;
import java.util.ArrayList;

public class DAO {

    private static final String url1 = "jdbc:mysql://localhost:3306/ripetizioni";
    private static final String user = "root";
    private static final String password = "";

    public static void registerDriver() {
        try {
            DriverManager.registerDriver(new com.mysql.jdbc.Driver());
            System.out.println("Driver correttamente registrato");
        } catch (SQLException e) {
            System.out.println("Errore: " + e.getMessage());
        }
    }


    public static ArrayList<Docenze> lezioni_queryDB() {
        registerDriver();
        Connection conn1 = null;
        ArrayList<Docenze> out = new ArrayList<>();
        try {
            conn1 = DriverManager.getConnection(url1, user, password);
            if (conn1 != null) {
                System.out.println("Connected to the database test");
            }

            Statement st = conn1.createStatement();
            ResultSet rs = st.executeQuery("SELECT nome ,titolo,data,ora FROM (lezioni NATURAL JOIN docenti) NATURAL JOIN corsi WHERE stato = 'disponibile' OR stato = 'disdetta'");
            while (rs.next()) {
                Docenze l = new Docenze(rs.getString("nome"), rs.getString("titolo"), rs.getString("data"), rs.getString("ora"));
                out.add(l);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return out;
    }


    public static ArrayList<Ripetizioni> getRipetizioni(String id) {
        registerDriver();
        Connection conn1 = null;
        ArrayList<Ripetizioni> out = new ArrayList<>();
        try {
            conn1 = DriverManager.getConnection(url1, user, password);
            if (conn1 != null) {
                System.out.println("Connected to the database test");
            }

            Statement st = conn1.createStatement();
            ResultSet rs = st.executeQuery("SELECT nome, titolo, data, ora, stato, id_prenotazione FROM ripetizioni WHERE id_utente = '"+ id +"'");
            while (rs.next()) {
                Ripetizioni l = new Ripetizioni(rs.getString("nome"), rs.getString("titolo"), rs.getString("data"), rs.getString("ora"), rs.getString("id_prenotazione"), rs.getString("stato"), null);
                out.add(l);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return out;
    }


    public static int addDocente(String nome_cognome,String id_p) throws SQLException {
        Connection conn2 = null;
        conn2 = DriverManager.getConnection(url1, user, password);
        Statement st = conn2.createStatement();
        try {
            st.executeUpdate("INSERT INTO docenti (id_docente, nome) VALUES ('"+ id_p +"', '"+nome_cognome+"');");
            return 1;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return 10;
        } finally {
            conn2.close();
        }
    }

    public static int addMateria(String nome,String id_m) throws SQLException {
        Connection conn2 = null;
        conn2 = DriverManager.getConnection(url1, user, password);
        Statement st = conn2.createStatement();
        try {
            st.executeUpdate("INSERT INTO corsi (id_corso, titolo) VALUES ('"+ id_m +"', '"+nome+"')");
            return 1;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return 10;
        } finally {
            conn2.close();
        }
    }

    public static void deleteprof(String id) throws SQLException {
        Connection conn2 = null;
        conn2 = DriverManager.getConnection(url1, user, password);
        Statement st = conn2.createStatement();
        try {
            st.executeUpdate("DELETE FROM  lezioni WHERE id_docente='"+id+"'");
            st.executeUpdate("DELETE FROM  docenti WHERE id_docente='"+id+"'");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            conn2.close();
        }
    }

    public static void deletecorso(String id) throws SQLException {
        Connection conn2 = null;
        conn2 = DriverManager.getConnection(url1, user, password);
        Statement st = conn2.createStatement();
        try {
            st.executeUpdate("DELETE FROM  lezioni WHERE id_corso='"+id+"'");
            st.executeUpdate("DELETE FROM  corsi WHERE id_corso='"+id+"'");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            conn2.close();
        }
    }


    public static ArrayList<Ripetizioni> getAllRipetizioni() {
        registerDriver();
        Connection conn1 = null;
        ArrayList<Ripetizioni> out = new ArrayList<>();
        try {
            conn1 = DriverManager.getConnection(url1, user, password);
            if (conn1 != null) {
                System.out.println("Connected to the database test");
            }

            Statement st = conn1.createStatement();
            ResultSet rs = st.executeQuery("SELECT r.nome, titolo, data, ora, stato, id_prenotazione, email FROM ripetizioni r JOIN utenti u ON r.id_utente = u.id_utente");
            while (rs.next()) {
                Ripetizioni l = new Ripetizioni(rs.getString("nome"), rs.getString("titolo"), rs.getString("data"), rs.getString("ora"), rs.getString("id_prenotazione"), rs.getString("stato"), rs.getString("email"));
                out.add(l);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return out;
    }



    public static ArrayList<String> getid(String em) {
        registerDriver();
        Connection conn1 = null;
        ArrayList<String> out = new ArrayList<>();
        try {
            conn1 = DriverManager.getConnection(url1, user, password);
            if (conn1 != null) {
                System.out.println("Connected to the database test");
            }

            Statement st = conn1.createStatement();
            ResultSet rs = st.executeQuery("SELECT id_utente FROM utenti  WHERE email = '" + em +"'");
            while (rs.next()) {
                //Ripetizioni l = new Ripetizioni(rs.getString("nome"), rs.getString("titolo"), rs.getString("data"), rs.getString("ora"), rs.getString("id_prenotazione"), rs.getString("stato"), rs.getString("email"));
                out.add(rs.getString("id_utente"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return out;
    }




    public static ArrayList<Corsi> getMaterie() throws SQLException {
        registerDriver();
        Connection conn2 = null;

        conn2 = DriverManager.getConnection(url1, user, password);
        Statement st = conn2.createStatement();
        ArrayList<Corsi> materie = new ArrayList<>();
        ResultSet rs = st.executeQuery("SELECT * from corsi");
        try {
            assert rs != null;
            while (rs.next())
                materie.add(new Corsi(rs.getString("id_corso"), rs.getString("titolo")));
            return materie;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        } finally {
            conn2.close();
        }
    }

    public static ArrayList<Docenti> getDocenti() throws SQLException {
        registerDriver();
        Connection conn2 = null;

        conn2 = DriverManager.getConnection(url1, user, password);
        Statement st = conn2.createStatement();
        ArrayList<Docenti> docenti = new ArrayList<>();
        ResultSet rs = st.executeQuery("SELECT * from docenti");
        try {
            assert rs != null;
            while (rs.next())
                docenti.add(new Docenti(rs.getString("id_docente"), rs.getString("nome")));
            return docenti;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        } finally {
            conn2.close();
        }
    }

    public static ArrayList<String> getProfNames(String nome_materia, String giorno, String ora) throws SQLException {
        Connection conn2 = null;

        conn2 = DriverManager.getConnection(url1, user, password);
        Statement st = conn2.createStatement();

        ResultSet rs = st.executeQuery("SELECT nome FROM (lezioni NATURAL JOIN docenti) NATURAL JOIN corsi WHERE titolo = '" + nome_materia + "' AND data = '" + giorno + "' AND ora = '" + ora + "' AND (stato = 'disponibile' OR stato = 'disdetta') ");
        ArrayList<String> profs = new ArrayList<>();

        try {
            if (rs != null) {
                while (rs.next())
                    profs.add(rs.getString("nome"));
                return profs;
            } else
                return null; //there is no prof that can satisfy the user request
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        } finally {
            conn2.close();
        }
    }







    public static int bookLesson(String nome_insegnante, String nome_materia, String email, String giorno, String ora, String id_utente) throws SQLException {

        Connection conn2 = null;
        String id_lezione;
        String docente_name;
        String corso_name;
        String id_docente;
        String id_corso;
        conn2 = DriverManager.getConnection(url1, user, password);
        Statement st = conn2.createStatement();
        try {

            ResultSet rs = st.executeQuery("SELECT id_lezione, docenti.nome, titolo, id_docente, id_corso FROM (lezioni NATURAL JOIN docenti) NATURAL JOIN corsi WHERE nome = '"+nome_insegnante+"' AND data = '"+giorno+"' AND ora = '"+ora+"' AND titolo = '"+nome_materia+"'");//salvo parametri per prenotare

            if (rs != null ) {

                rs.next();

                id_lezione = rs.getString("id_lezione");

                docente_name = rs.getString("docenti.nome");

                corso_name = rs.getString("titolo");

                id_docente = rs.getString("id_docente");

                id_corso = rs.getString("id_corso");


                st.executeUpdate("INSERT INTO ripetizioni (id_prenotazione, id_utente, nome, titolo, data, ora, stato) VALUES (0 , '"+id_utente+"', '"+docente_name+"', '"+corso_name+"', '"+giorno+"', '"+ora+"', 'attiva');");
                st.executeUpdate("UPDATE lezioni SET stato = 'attiva' WHERE lezioni.id_lezione = '"+id_lezione+"' AND lezioni.id_docente = '"+id_docente+"' AND lezioni.id_corso = '"+id_corso+"' AND lezioni.data = '"+giorno+"' AND lezioni.ora = '"+ora+"'");
                return 1;
            } else
                return 0;
        } catch (Exception e) {
            System.out.println("Errore-->"+e.getMessage());
            return -1;
        } finally {
            conn2.close();
        }
    }




    public static int addLezione(String nome_insegnante, String nome_materia, String giorno, String ora) throws SQLException {
        Connection conn2 = null;
        String id_lezione;
        String id_docente;
        String id_corso;
        conn2 = DriverManager.getConnection(url1, user, password);
        Statement st = conn2.createStatement();
        Statement sta = conn2.createStatement();
        Statement stb = conn2.createStatement();
        try {
            ResultSet rsa = sta.executeQuery("SELECT id_corso FROM corsi WHERE titolo = '"+nome_materia+"'");
            ResultSet rsb = stb.executeQuery("SELECT id_docente FROM docenti WHERE nome = '"+nome_insegnante+"'");
            ResultSet rs = st.executeQuery("SELECT id_lezione, id_docente, id_corso FROM (lezioni NATURAL JOIN docenti) NATURAL JOIN corsi WHERE nome = '"+nome_insegnante+"' AND data = '"+giorno+"' AND ora = '"+ora+"'");// AND titolo = '"+nome_materia+"'");

            rsa.next();
             rsb.next();

            if (!rs.next()) {


                id_docente = rsb.getString("id_docente");
                id_corso = rsa.getString("id_corso");



                st.executeUpdate("INSERT INTO lezioni (id_lezione,id_docente, id_corso,data,ora,stato) VALUES (0 ,'"+ id_docente +"', '"+id_corso+"','"+giorno+"','"+ora+"','disponibile')");
                return 1;
            } else
                return 0;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return -1;
        } finally {
            conn2.close();
        }
    }





    public static int remLezione(String nome_insegnante, String nome_materia, String giorno, String ora) throws SQLException {
        Connection conn2 = null;
        String id_lezione;
        conn2 = DriverManager.getConnection(url1, user, password);
        Statement st = conn2.createStatement();
        Statement sta = conn2.createStatement();
        Statement stb = conn2.createStatement();
        try {
            ResultSet rsa = sta.executeQuery("SELECT id_corso FROM corsi WHERE titolo = '"+nome_materia+"'");
            ResultSet rsb = stb.executeQuery("SELECT id_docente FROM docenti WHERE nome = '"+nome_insegnante+"'");
            ResultSet rs = st.executeQuery("SELECT id_lezione, id_docente, id_corso FROM (lezioni NATURAL JOIN docenti) NATURAL JOIN corsi WHERE nome = '"+nome_insegnante+"' AND data = '"+giorno+"' AND ora = '"+ora+"' AND titolo = '"+nome_materia+"'");
            rsa.next();
            rsb.next();

            if (rs.next()) {


                id_lezione=rs.getString("id_lezione");

                st.executeUpdate("DELETE FROM lezioni WHERE id_lezione='"+ id_lezione +"'");
                return 1;
            } else
                return 0;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return -1;
        } finally {
            conn2.close();
        }
    }

    public static String userRole(String email, String psw) throws SQLException {
        registerDriver();
        String ruolo;
        Connection conn2 = null;
        conn2 = DriverManager.getConnection(url1, user, password);
        try {
            if(extraFunctions.emailIsValid(email) && extraFunctions.passwordIsValid(psw)) {

                if (conn2 != null) {
                    System.out.println("Connected to the database test");
                }
                Statement st = conn2.createStatement();
                ResultSet rs = st.executeQuery("SELECT ruolo FROM utenti WHERE email = '"+email+"' AND password = '"+psw+"'");
                if(rs != null) { //if resultset is null something went wrong
                    if (rs.next()) { //if there is a value in resultset this make it available
                        ruolo = rs.getString("ruolo");
                        System.out.println(ruolo);
                        return ruolo;
                    }else //email or password don't exist in the database
                        return "error";
                }
                else
                    return "999";
            }
            else { //email or password were not valid
                return "-1";
            }
        }
        catch (SQLException e) { //error, something went wrong
            System.out.println("Errore: " + e.getMessage());
            return "999";
        }
        finally {
            conn2.close();
        }
    }

    public static String userID(String email, String psw) throws SQLException {
        registerDriver();
        String ID;
        Connection conn2 = null;
        conn2 = DriverManager.getConnection(url1, user, password);

        try {
            if(extraFunctions.emailIsValid(email) && extraFunctions.passwordIsValid(psw)) {

                if (conn2 != null) {
                    System.out.println("Connected to the database test");
                }
                Statement st = conn2.createStatement();
                ResultSet rs = st.executeQuery("SELECT id_utente FROM utenti WHERE email = '"+email+"' AND password = '"+psw+"'");
                if(rs != null) { //if resultset is null something went wrong
                    if (rs.next()) { //if there is a value in resultset this make it available
                        ID = rs.getString("id_utente");
                        System.out.println(ID);
                        return ID;
                    }else //email or password don't exist in the database
                        return "error";
                }
                else
                    return "999";
            }
            else { //email or password were not valid
                return "-1";
            }
        }
        catch (SQLException e) { //error, something went wrong
            System.out.println("Errore: " + e.getMessage());
            return "999";
        }
        finally {
            conn2.close();
        }
    }

    public static void manRipetizione(int id, String new_state) throws SQLException {
        registerDriver();
        Connection conn2 = null;
        String nome;
        String titolo;
        String data;
        String ora;
        String id_docente;
        String id_corso;
        conn2 = DriverManager.getConnection(url1, user, password);

        try {

            Statement st = conn2.createStatement();
            st.executeUpdate("UPDATE ripetizioni SET stato = '"+new_state+"' WHERE id_prenotazione = '"+ id +"'");

            ResultSet rs = st.executeQuery("SELECT nome, titolo, data, ora FROM ripetizioni WHERE id_prenotazione = '"+ id +"'");
            rs.next();
            nome = rs.getString("nome");
            titolo = rs.getString("titolo");
            data = rs.getString("data");
            ora = rs.getString("ora");
            ResultSet rd = st.executeQuery("SELECT id_docente FROM docenti WHERE nome = '"+nome+"'");
            rd.next();
            id_docente = rd.getString("id_docente");
            ResultSet rc = st.executeQuery("SELECT id_corso FROM corsi WHERE titolo = '"+titolo+"'");
            rc.next();
            id_corso = rc.getString("id_corso");
            if(new_state.equals("disdetta")){//per rendere disponibile tra le lezioni
                st.executeUpdate("UPDATE lezioni SET stato = 'disdetta' WHERE id_docente = '" + id_docente + "' AND id_corso = '" + id_corso + "' AND data = '" + data + "' AND ora = '" + ora + "'");
            }
            else
                st.executeUpdate("DELETE FROM  lezioni WHERE id_docente = '" + id_docente + "' AND id_corso = '" + id_corso + "' AND data = '" + data + "' AND ora = '" + ora + "'");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            conn2.close();
        }
    }



}

