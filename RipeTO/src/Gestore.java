import DAO.*;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

import java.lang.reflect.Type;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "Gestore",  urlPatterns = {"/Gestore"})
public class Gestore extends HttpServlet {
    private  Gson gson = new Gson();
    HttpSession session;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void processRequest (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException,SQLException {

        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        String action = request.getParameter("action");
        if(action==null){
            out.println("action is null");
            System.out.println("action is null");
        }
        else
            System.out.println(action);

        assert action != null;
        switch (action){ // this switch perform all actions requested by the client
            case "checksession":
                if (checkSession(request)) {
                    out.println(1);
                } else {
                    out.println(0);
                }
                break;

            case "checkadmin":
                if(checkSession(request)) {
                    out.println((String) session.getAttribute("ruolo"));
                }
                break;



            case "getid"://android
                String ema = request.getParameter("email");

                out.println(gson.toJson(DAO.getid(ema)));
                break;

            case "getmaterie":
                /* the first line of code is needed to transfer a json string to the client
                 * this way i return the entire arraylist casted as a json*/
                response.setContentType("application/json");
                //System.out.println(gson.toJson(DAO.getMaterie()));
                out.print(gson.toJson(DAO.getMaterie()));
                break;

            case "getDocenti":
                /* the first line of code is needed to transfer a json string to the client
                 * this way i return the entire arraylist casted as a json*/
                response.setContentType("application/json");
                //System.out.println(gson.toJson(DAO.getMaterie()));
                out.print(gson.toJson(DAO.getDocenti()));
                break;


            case "getprofnames":
                response.setContentType("application/json");
                String materia = request.getParameter("materia");
                String giorno = request.getParameter("giorno");
                String ora = request.getParameter("ora");
                out.print(gson.toJson(DAO.getProfNames(materia,giorno,ora)));
                break;
            case "login":
                String email = request.getParameter("email");
                System.out.println(email);
                String psw = request.getParameter("psw");
                System.out.println("sono DAO "+ DAO.userRole(email,psw));
                System.out.println(psw);
                String ruolo = DAO.userRole(email,psw);
                String ID = DAO.userID(email,psw);
                switch (DAO.userRole(email,psw))
                {
                    case "amministratore":
                        session = request.getSession();
                        session.setAttribute("email",email);
                        session.setAttribute("ruolo",ruolo);
                        session.setAttribute("ID",ID);
                        out.println(1); //admin
                        break;
                    case "utente":
                        session = request.getSession();
                        session.setAttribute("email",email);
                        session.setAttribute("ruolo",ruolo);
                        session.setAttribute("ID",ID);
                        out.println(0); //simple user
                        System.out.println("utente loggato");
                        break;
                    case "-1":
                        out.println(-1); //invalid email or password
                        break;
                    default:
                        out.println(999); //something went wrong
                        break;
                }
                break;

            case "getLezioni":
                response.setContentType("text/html;charset=UTF-8");
                List<Docenze> lezioni = DAO.lezioni_queryDB();
                String x = gson.toJson(lezioni);
                out.print(x);
                break;
            case "getripetizioni":
                response.setContentType("text/html;charset=UTF-8");
                String id;
                session = request.getSession(false);
                if(session!=null)
                    id = (String)session.getAttribute("ID");
                else if(request.getParameter("ID")!=null)//per android
                    id = request.getParameter("ID");
                else
                    id = null;
                System.out.println(gson.toJson(DAO.getRipetizioni(id)));
                out.println(gson.toJson(DAO.getRipetizioni(id)));
                break;
            case "getAllRipe":
                response.setContentType("text/html;charset=UTF-8");
                out.println(gson.toJson(DAO.getAllRipetizioni()));
                break;
            case "getusername":
                session = request.getSession(false);
                if(session != null)
                    out.println((String) session.getAttribute("email"));
                break;
            case "effettuaripetizioni":
                try {
                    //this string will contain all the data we need in a json format
                    String effripeJson = request.getParameter("id");

                    //gson need this in order to understand what type of
                    // * data will recive, in this case just an array of integer
                    Type effripeListType = new TypeToken<ArrayList<Integer>>(){}.getType();

                    //now we can finally create the arraylist with the id id in the json string,
                    // * giving the string and the type of the data to fromJson function
                    ArrayList<Integer> effidripe = gson.fromJson(effripeJson, effripeListType);
                    String effettuata = "effettuata";
                    for (int id_rip : effidripe) {
                        DAO.manRipetizione(id_rip, effettuata);
                    }
                    out.println(1);
                }
                catch (Exception e){
                    out.println(0);
                    System.out.println(e.getMessage());
                }
                break;
            case "disdiciripetizioni":
                try{
                    /*to better understand this code fragment see line 109 and above, they are almost the same*/
                    String disripeJson =  request.getParameter("id");
                    System.out.println(disripeJson);
                    Type disripeListType = new TypeToken<ArrayList<Integer>>(){}.getType();
                    ArrayList<Integer> disidripe = gson.fromJson(disripeJson,disripeListType);
                    String disdetta = "disdetta";
                    for (int id_dis: disidripe) {
                        DAO.manRipetizione(id_dis, disdetta);
                    }
                    out.println(1);
                }
                catch (Exception e){
                    System.out.println(e.getMessage());
                    out.println(0);
                }
                break;


            case "logout":
                try {
                    session = request.getSession();
                    session.invalidate();
                    out.println(1);
                }
                catch (Exception e){
                    System.out.println(e.getMessage());
                    out.println(e.getMessage());
                }
                break;

            case "booklesson":
                try {
                    session = request.getSession(false);
                    String email_u;
                    String id_utente;
                    String nome_insegnate = request.getParameter("nome_insegnate");

                    String nome_materia = request.getParameter("nome_materia");

                    String giorno_ = request.getParameter("giorno");

                    String ora_ = request.getParameter("ora");

                    if(session != null ) //if session == null user is not logged but tried to book a lesson anyway or book by app
                    {

                        email_u = (String)session.getAttribute("email");
                        id_utente = (String)session.getAttribute("ID");


                        int success = DAO.bookLesson(nome_insegnate,nome_materia,email_u,giorno_,ora_,id_utente);
                        if(success == 1)
                            out.println(1);
                        else if(success == 0)
                            out.println(0);
                        else
                            out.println(-1);
                    }
                    else if (request.getParameter("email")!=null)
                    {
                        email_u = request.getParameter("email");


                         id_utente = request.getParameter("ID");



                        int success = DAO.bookLesson(nome_insegnate,nome_materia,email_u,giorno_,ora_,id_utente);

                        if(success == 1)
                            out.println(1);
                        else if(success == 0)
                            out.println(0);
                        else
                            out.println(-1);
                    }
                    else
                        out.println(-2);

                }
                catch (Exception e){
                    System.out.println(e.getMessage());
                    System.out.println("4");
                    out.println(0);
                }
                break;



            case "delDocente":
                try {
                    String id_p = request.getParameter("idprofs");

                            DAO.deleteprof(id_p);
                          out.println(1);

                }
                catch (Exception e)
                {
                    System.out.println(e.getMessage());
                    out.println(0);
                }
                break;
            case "addDocente":
                try {
                    String nome_prof = request.getParameter("nome");
                    String id_prof = request.getParameter("id");
                    if(nome_prof.length()>0){
                        if(DAO.addDocente(nome_prof,id_prof)==1)
                          out.println(1);
                        else
                            out.println(2);

                    }
                    else
                        out.println(0);
                }
                catch (Exception e){
                    System.out.println(e.getMessage());
                    out.println(0);
                }
                break;

            case "delmat":
                try {
                    String idmat = request.getParameter("idcorso");

                            DAO.deletecorso(idmat);

                        out.println(1);

                }
                catch (Exception e)
                {
                    System.out.println(e.getMessage());
                    out.println(0);
                }
                break;
            case "addmat":
                try {
                    String nome_mat = request.getParameter("nome");
                    String id_mat = request.getParameter("id");
                    if(nome_mat.length()>0){
                        if(DAO.addMateria(nome_mat,id_mat)==1)
                            out.println(1);
                        else
                            out.println(2);

                    }
                    else
                        out.println(0);
                }
                catch (Exception e){
                    System.out.println(e.getMessage());
                    out.println(0);
                }
                break;
            case "addLesson":
                try {
                    String nome_insegnate = request.getParameter("nome_insegnate");
                    System.out.println(nome_insegnate);
                    String nome_materia = request.getParameter("nome_materia");
                    System.out.println(nome_materia);
                    String giorno_ = request.getParameter("giorno");
                    System.out.println(giorno_);
                    String ora_ = request.getParameter("ora");
                    System.out.println(ora_);
                    int success = DAO.addLezione(nome_insegnate,nome_materia,giorno_,ora_);
                    System.out.println(success);
                    //out.println(gson.toJson(DAO.addLezione(nome_insegnate,nome_materia,giorno_,ora_)));
                    if(success == 1)
                        out.println(1);
                    else if(success == 0)
                        out.println(0);
                    else
                        out.println(-1);
                }
                catch (Exception e)
                {
                    System.out.println(e.getMessage());
                    out.println(0);
                }
                break;


                case "remLesson":
                try {
                    String nome_insegnate = request.getParameter("nome_insegnate");
                    System.out.println(nome_insegnate);
                    String nome_materia = request.getParameter("nome_materia");
                    System.out.println(nome_materia);
                    String giorno_ = request.getParameter("giorno");
                    System.out.println(giorno_);
                    String ora_ = request.getParameter("ora");
                    System.out.println(ora_);
                    int success = DAO.remLezione(nome_insegnate,nome_materia,giorno_,ora_);
                    System.out.println(success);
                    //out.println(gson.toJson(DAO.addLezione(nome_insegnate,nome_materia,giorno_,ora_)));
                    if(success == 1)
                        out.println(1);
                    else if(success == 0)
                        out.println(0);
                    else
                        out.println(-1);
                }
                catch (Exception e)
                {
                    System.out.println(e.getMessage());
                    out.println(0);
                }
                break;




      }
        out.close();
    }
    public boolean checkSession(HttpServletRequest request){
        //store the session data, do not create if doesn't exist
        session = request.getSession(false);
        System.out.println("sono la session"+session);
        System.out.println("Ruolo--->"+session.getAttribute("ruolo"));
        return session != null;
    }
}


