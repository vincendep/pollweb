/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.univaq.f4i.iw.pollweb.business.controller;

import it.univaq.f4i.iw.framework.security.SecurityLayer;
import it.univaq.f4i.iw.pollweb.business.model.Participant;
import it.univaq.f4i.iw.pollweb.business.model.ReservedSurvey;
import it.univaq.f4i.iw.pollweb.business.model.Survey;
import it.univaq.f4i.iw.pollweb.business.model.User;
import it.univaq.f4i.iw.pollweb.data.dao.DataLayer;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

/**
 *
 * @author vince
 */
@WebServlet("/account/survey-details?survey=")
@MultipartConfig
public class ParticipantsController extends BaseController {

    private void action_add(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        int idSurvey = SecurityLayer.checkNumeric(request.getParameter("survey"));
        Survey survey = ((DataLayer) request.getAttribute("datalayer")).getSurveyDAO().findById(idSurvey);
        if (survey == null || !(survey instanceof ReservedSurvey)) {
            throw new ServletException("Parametro survey non valido");
        }
        User user = (User) request.getAttribute("logged_user");
        if (!survey.getManager().equals(user)) {
            throw new ServletException("Non sei il responsabile di questo sondaggio");
        }
        Participant participant = new Participant();
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        if (email == null || password == null) {
            throw new ServletException("Parametro mancante");
        }
        participant.setEmail(email);
        participant.setPassword(password);
        participant.setReservedSurvey((ReservedSurvey) survey);
        // TODO manage duplicate email?
        ((DataLayer) request.getAttribute("datalayer")).getParticipantDAO().saveOrUpdate(participant);
        response.sendRedirect("account/survey-details?survey=" + idSurvey);
    }

    private void action_add_from_file(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        int idSurvey = SecurityLayer.checkNumeric(request.getParameter("survey"));
        Survey survey = ((DataLayer) request.getAttribute("datalayer")).getSurveyDAO().findById(idSurvey);
        if (survey == null || !(survey instanceof ReservedSurvey)) {
            throw new ServletException("Parametro survey non valido");
        }
        User user = (User) request.getAttribute("logged_user");
        if (!survey.getManager().equals(user)) {
            throw new ServletException("Non sei il responsabile di questo sondaggio");
        }

        Part filePart = request.getPart("csv"); // Retrieves <input type="file" name="csv">
        String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString(); // MSIE fix.
        InputStream is = filePart.getInputStream();

        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        String line = null;
        String regex = "^([_a-zA-Z0-9-]+(\\.[_a-zA-Z0-9-]+)*@[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)*(\\.[a-zA-Z]{1,6}))?$";
        Pattern pattern = Pattern.compile(regex);
        while ((line = br.readLine()) != null) {
            try {
                String[] columns = line.split(";");
                if (!(columns[0].equals(""))) {
                    Matcher matcher = pattern.matcher(columns[0]);
                    if (matcher.matches()) {
                        if (!(columns[1].equals(""))) {
                            Participant participant = new Participant();
                            participant.setEmail(columns[0]);
                            participant.setPassword(columns[1]);
                            participant.setReservedSurvey((ReservedSurvey) survey);
                            ((DataLayer) request.getAttribute("datalayer")).getParticipantDAO().saveOrUpdate(participant);
                        }
                    }
                } else {
                    System.out.println("La riga " + line + "non è nel formato corretto");
                }
            } catch (Exception e) {
                throw new ServletException("Caricamento non riuscito");
            }
        }
        response.sendRedirect("account/survey-details?survey=" + idSurvey);
    }

    private void action_delete(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        int idSurvey = SecurityLayer.checkNumeric(request.getParameter("survey"));
        Survey survey = ((DataLayer) request.getAttribute("datalayer")).getSurveyDAO().findById(idSurvey);
        if (survey == null || !(survey instanceof ReservedSurvey)) {
            throw new ServletException("Parametro survey non valido");
        }
        User user = (User) request.getAttribute("logged_user");
        if (!survey.getManager().equals(user)) {
            throw new ServletException("Non sei il responsabile di questo sondaggio");
        }
        int participantId = SecurityLayer.checkNumeric(request.getParameter("participant"));
        Participant participant = ((DataLayer) request.getAttribute("datalayer")).getParticipantDAO().findById(participantId);
        if (!participant.getReservedSurvey().equals(survey)) {
            throw new ServletException("Non puoi eliminare questo partecipante");
        }
        ((DataLayer) request.getAttribute("datalayer")).getParticipantDAO().delete(participant);
        response.sendRedirect("account/survey-details?survey=" + idSurvey);
    }

    private void action_authenticate(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession session = request.getSession();
        Integer surveyId = SecurityLayer.checkNumeric(request.getParameter("survey"));
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        DataLayer dataLayer = (DataLayer) request.getAttribute("datalayer");
        Participant participant = dataLayer.getParticipantDAO().findByEmailAndPasswordAndSurveyId(email, password, surveyId);
        if (participant != null) {
            session.setAttribute("participantid", participant.getId());
            response.sendRedirect("compile-survey?survey=" + surveyId);
        } else {
            throw new ServletException("Credenziali errate");
        }
    }

    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        try {
            if (request.getParameter("add") != null) {
                action_add(request, response);
            } else if (request.getParameter("add-from-file") != null) {
                action_add_from_file(request, response);
            } else if (request.getParameter("delete") != null) {
                action_delete(request, response);
            } else if (request.getParameter("authenticate") != null) {
                action_authenticate(request, response);
            } else {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            }
        } catch (IOException ex) {
            Logger.getLogger(ParticipantsController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
