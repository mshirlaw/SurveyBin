package assignmentTwo;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.UUID;

/**
 * Servlet implementation class SurveyBuilder
 * This Servlet builds a SurveyItem with a unique UUID
 * and stores it in the DataStore. The user is then presented 
 * with two links
 * - one link to complete the survey
 * - one link to view the survey results
 * @author mshirlaw
 */
@WebServlet("/SurveyBuilder")
public class SurveyBuilder extends HttpServlet{

    //get an instance of the DAO
    private assignmentTwo.DataStore dataStore;

    public SurveyBuilder() throws ReflectiveOperationException, SQLException
    {
        dataStore = assignmentTwo.DataStore.getInstance();
    }

    /**
	 * doGet 
	 * Passes GET requests to the doPost method
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		doPost(request,response);
	}

	/**
	 * doPost 
	 * Processes POST requests to create and store a SurveyItem in
	 * the DataStore and presents the user with a link to complete the 
	 * survey and a link to view the survey results
	 * If the user does not provide the necessary data, the SurveyForm is re-displayed
	 * with an appropriate error message
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		//set content type
		response.setContentType("text/html");
		
		//get PrintWriter
		PrintWriter out = response.getWriter();
		
		//generate random UUID for this survey
		String surveyID = UUID.randomUUID().toString();
		
		//build the head and body of the returned page
        String head = "<head>"
                + "<link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.2.0/css/bootstrap.min.css\">"
                + "<link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.2.0/css/bootstrap-theme.min.css\">"
                + "<link type=\"text/css\" rel=\"stylesheet\" href=\"main.css\">"
                + "<title>Survey Bin | Create a Survey</title>"
                + "</head>";
		String body = "<body>"
                + "<div class=\"jumbotron\">"
                + "<div class=\"container\">"
                + "<h1><span>Survey</span>Bin</h1>"
                + "<h2>Dynamic surveys at the click of a button.</h2>"
                + "<p>A simple poll? In-depth market research? We've got you covered.</p>"
                + "<div class=\"btn-group\">"
                + "<div class=\"btn btn-primary btn-lg\">"
                + "<a href=\"SurveyHome\">Home</a>"
                + "</div>"
                + "<div class=\"btn btn-primary btn-lg\" role=\"button\">"
                + "<a href=\"SurveyForm\">Create a Survey</a>"
                + "</div>"
                + "</div>"
                + "</div>"
                + "</div>"
				+ "<div class=\"container\">"
                + "<h2>Survey Link</h2>"
				+ "<p>You can find your customised SurveyBin<sup>&reg;</sup> survey at the address below. "
				+ "Use this link when asking your customers to complete your survey!</p>"
				+ "<a class=\"btn btn-default\" href=\"SurveyPageBuilder?surveyID="
				+ surveyID
				+ "\">SurveyPageBuilder?surveyID="
				+ surveyID
				+ "</a>"
                + "<h2>Results Link</h2>"
				+ "<p>To view the results of your survey you can use the address below: </p>"
				+ "<a class=\"btn btn-default\" href=\"SurveyResultsGenerator?surveyID="
				+ surveyID
				+ "\">"
                //+ "http://" + request.getServerName() + ":" + request.getServerPort() + "/SurveyBin/"
                + "SurveyResultsGenerator?surveyID="
				+ surveyID
				+ "</a>"
				+ "</div>";

        //build the page footer
        String footer = "<div class=\"container\">"
                + "<br /><br /><hr /><address>Matthew Shirlaw<br />Assignment 2<br />COMP391 Advanced Web Technologies</address>"
                + "</div>"
                + "</body>";
		
		
		//if there are missing items send a redirect to the SurveyForm Servlet
		if((request.getParameter("title").equals("")) ||
				(request.getParameter("intro").equals("")) ||
				(request.getParameter("question1").equals("")) ||
				(request.getParameter("question2").equals("")) ||
				(request.getParameter("question3").equals("")))
		{
			boolean missingTitle = false, missingIntro = false, 
					missingQuestionOne = false, missingQuestionTwo = false, 
					missingQuestionThree = false;
		
			if(request.getParameter("title").equals(""))
				missingTitle=true;
			if(request.getParameter("intro").equals(""))
				missingIntro=true;
			if(request.getParameter("question1").equals(""))
				missingQuestionOne=true;
			if(request.getParameter("question2").equals(""))
				missingQuestionTwo=true;
			if(request.getParameter("question3").equals(""))
				missingQuestionThree=true;
			
			//send re-direct with an appropriate error message
			response.sendRedirect("SurveyForm?missingTitle="+missingTitle
															+ "&missingIntro="+missingIntro
															+ "&missingQuestionOne="+missingQuestionOne
															+ "&missingQuestionTwo="+missingQuestionTwo
															+ "&missingQuestionThree="+missingQuestionThree);
		}
		else
		{
			//try to add a survey object to the DataStore
			try {
                dataStore.addSurveyForm(createSurvey(request, surveyID));
            }catch(SQLException ex)
            {
                throw new ServletException(ex);
            }
		}
		
		//display the links page
		out.write("<!DOCTYPE html>");
		out.write("<html>");
		out.write(head);
		out.write(body);
		out.write(footer);
		out.write("</html>");
	}

	/**
	 * createSurvey uses POST data to build a SurveyItem with 
	 * a unique UUID so that it can be stored in the DataStore for
	 * use at a later time
	 * @param request The request which contains the POST data for this survey
	 * @param surveyID A unique UUID for this survey
	 * @return A SurveyItem that can be stored in the DataStore for future use
	 */
	public assignmentTwo.SurveyItem createSurvey(HttpServletRequest request, String surveyID)
	{
		//create a new SurveyItem
		assignmentTwo.SurveyItem aSurvey = new assignmentTwo.SurveyItem();

        //get the session id
        HttpSession session = request.getSession();
        if(session != null){
            aSurvey.setSessionID(session.getId());
        }

		//Add POST data
		aSurvey.setSurveyID(surveyID);
		aSurvey.setTitle(request.getParameter("title"));
		aSurvey.setOpeningText(request.getParameter("intro"));
		aSurvey.setQuestionOne(request.getParameter("question1"));
		aSurvey.setQuestionTwo(request.getParameter("question2"));
		try
		{
			int num = Integer.parseInt(request.getParameter("choices"));
			aSurvey.setChoices(num);
		}
		catch(NumberFormatException ne)
		{
			//invalid data entered, use default value 0
			aSurvey.setChoices(0);
		}
		aSurvey.setQuestionThree(request.getParameter("question3"));
		return aSurvey;
	}
}
