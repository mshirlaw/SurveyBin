package assignmentTwo;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.RequestDispatcher;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Servlet implementation class SurveyHome
 * This Servlet forwards the user to a Home Page for the SurveyBin
 * web application which either includes a welcome message
 * or summary data for all surveys created in the current session.
 * SurveyHome includes a button for the user
 * to create a custom SurveyBin survey and return to the home page.
 * @author mshirlaw
 */
@WebServlet(urlPatterns={"","SurveyHome"})
public class SurveyHome extends HttpServlet {

    //get an instance of the DAO
    private assignmentTwo.DataStore dataStore;

    public SurveyHome() throws ReflectiveOperationException, SQLException
    {
        dataStore = assignmentTwo.DataStore.getInstance();
    }

    /**
	 * doGet
	 * Pass any GET requests to the doPost method
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		doPost(request,response);
	}

	/**
	 * doPost
	 * Determines whether there has been any surveys created
     * during the current session and forwards the request to
     * the appropriate homepage
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
        //get sessionID
        HttpSession session = request.getSession();
        String sessionID = session.getId();

        //forwarding address depends upon whether we have created any surveys yet
        String address = "WEB-INF/welcome.jsp";

        try
        {
            //check if we have created any surveys in this session, if so display summary data
            if (dataStore.getSurveysInSession(sessionID).size() > 0) {
                address = "WEB-INF/welcomeSummary.jsp";
            }

            //forward to jsp view pages
            RequestDispatcher dispatcher = request.getRequestDispatcher(address);
            dispatcher.forward(request, response);

        }catch(SQLException ex)
        {
            throw new ServletException(ex);
        }
    }

}
