package assignmentTwo;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.RequestDispatcher;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Servlet implementation class SurveyResultsGenerator
 * This Servlet determines if there are any responses to a given survey
 * If so, it forwards the request to a jsp summary page that provides
 * summary data for the given survey (including dates and IP addresses).
 * If there are no survey responses yet the servlet forwards the response
 * to a jsp error page
 * @author mshirlaw
 */
@WebServlet("/SurveyResultsGenerator")
public class SurveyResultsGenerator extends HttpServlet {

    //get an instance of the DAO
    private assignmentTwo.DataStore dataStore;

    public SurveyResultsGenerator() throws ReflectiveOperationException, SQLException
    {
        dataStore = assignmentTwo.DataStore.getInstance();
    }

    /**
     * doGet
     * Pass GET requests directly to doPost method
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //pass GET request directly to doPost
        doPost(request, response);
    }

    /**
     * doPost
     * Displays a summary page of all responses to a given survey
     * of a given UUID
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //get the correct surveyID
        String surveyID = request.getParameter("surveyID");

        try
        {
            //if we have no responses, forward to jsp error page
            if (dataStore.getSurveyResponses(surveyID) == null) {
                //forward to jsp error page
                RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/noResponses.jsp");
                dispatcher.forward(request, response);
            } else {
                //forward to jsp responses page
                RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/responses.jsp");
                dispatcher.forward(request, response);
            }
        }
        catch (SQLException ex)
        {
            throw new ServletException(ex);
        }
    }
}