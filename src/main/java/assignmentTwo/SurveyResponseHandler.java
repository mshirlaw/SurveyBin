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
import java.util.Date;

/**
 * Servlet implementation class SurveyResponseHandler
 * When users submit their form, this Servlet is responsible for:
 * - recording the survey response
 * - recording the IP address from which the response was submitted
 * - recording the date and time of the response
 * - displaying a thank you message with a summary of the data submitted
 * @author mshirlaw
 */
@WebServlet("/SurveyResponseHandler")
public class SurveyResponseHandler extends HttpServlet {

    //get an instance of the DAO
    private assignmentTwo.DataStore dataStore;

    public SurveyResponseHandler() throws ReflectiveOperationException, SQLException
    {
        dataStore = assignmentTwo.DataStore.getInstance();
    }

    /**
	 * doGet
	 * Pass GET requests to the doPost method
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		doPost(request,response);
	}

	/**
	 * doPost
	 * Process a survey response and store the results in the DataStore as a
	 * new instance of the SurveyResponseItem class
	 * Display a summary page to the user of the information that was submitted 
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		//set content type
		response.setContentType("text/html");
		
		//get a PrintWriter object
		PrintWriter out = response.getWriter();
		
		//get the survey ID
		String surveyID = request.getParameter("surveyID");
		
		//create a survey response item and survey item
		assignmentTwo.SurveyResponseItem surveyResponse = createSurveyResponse(request,surveyID);
		assignmentTwo.SurveyItem surveyItem;
        try
        {
            surveyItem = dataStore.getSurveyForm(surveyID);
        }
        catch(SQLException ex)
        {
            throw new ServletException(ex);
        }
				
		//add survey response to the DataStore
		try
        {
            dataStore.addSurveyResponse(surveyResponse);
        }
        catch(SQLException ex)
        {
            throw new ServletException(ex);
        }
		
		//create thank you page
        String head = "<head>"
                + "<link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.2.0/css/bootstrap.min.css\">"
                + "<link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.2.0/css/bootstrap-theme.min.css\">"
                + "<link type=\"text/css\" rel=\"stylesheet\" href=\"main.css\">"
                + "<title>Survey Bin | Create a Survey</title>"
                + "</head>";
		String body  = "<body>"
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
					+ "<p>Thank you very much! You have submitted the following information: </p>"
                    + "<div class=\"table-responsive\">"
                    + "<table class=\"table table-bordered\">"
					+ "<th>ITEM</th><th>VALUE</th>"
					+ "<tr><td>Survey</td><td>"+surveyResponse.getTitle()+"</td></tr>"
					+ "<tr><td>Date</td><td>"+surveyResponse.getResponseDate()+"</td></tr>"
					+ "<tr><td>IP Address</td><td>"+surveyResponse.getIpAddress()+"</td></tr>"
                    //+ "<tr><td>Session ID</td><td>"+surveyResponse.getSessionID()+"</td></tr>"
                    + "<tr><td>"+surveyItem.getQuestionOne()+"</td><td>"+surveyResponse.getQuestionOne()+"</td></tr>"
					+ "<tr><td>"+surveyItem.getQuestionTwo()+"</td><td>"+surveyResponse.getQuestionTwo()+"</td></tr>"
					+ "<tr><td>"+surveyItem.getQuestionThree()+"</td><td>"+surveyResponse.getQuestionThree()+"</td></tr>"
					+ "</table>"
                    + "</div>"
                    + "</div>";

        //build the page footer
        String footer = "<div class=\"container\">"
                + "<br /><br /><hr /><address>Matthew Shirlaw<br />Assignment 2<br />COMP391 Advanced Web Technologies</address>"
                + "</div>"
                + "</body>";

		//display a thank you page
		out.write("<!DOCTYPE html>");
		out.write("<html>");
		out.write(head);
		out.write(body);
		out.write(footer);
		out.write("</html>");
	}
	
	/**
	 * createSurveyResponse
	 * Creates a new instance of the SurveyResponseItem class for 
	 * the survey with UUID of surveyID. If any data is incomplete, 
	 * the value N/A is used
	 * @param request A HttpServletRequest object 
	 * @param surveyID The unique UUID for the survey that was completed
	 * @return A SurveyResponseItem which contains all response data
	 */
	public assignmentTwo.SurveyResponseItem createSurveyResponse(HttpServletRequest request, String surveyID)
	{
		//create an instance of the SurveyReponseItem class
		assignmentTwo.SurveyResponseItem aResponse = new assignmentTwo.SurveyResponseItem();
		Date now = new Date();

		//survey ID
		aResponse.setSurveyID(surveyID);

        //session id of responder
        HttpSession session = request.getSession();
        if(session != null){
            aResponse.setSessionID(session.getId());
        }

		//add survey responses to the SurveyResponseItem
		if(request.getParameter("title").equals(""))
			aResponse.setTitle("N/A");
		else
			aResponse.setTitle(request.getParameter("title"));
		
		if(request.getParameter("intro").equals(""))
			aResponse.setOpeningText("N/A");
		else
			aResponse.setOpeningText(request.getParameter("intro"));
		
		if(request.getParameter("q1Response").equals(""))
			aResponse.setQuestionOne("N/A");
		else
			aResponse.setQuestionOne(request.getParameter("q1Response"));
		
		if(request.getParameter("q2Response").equals(""))
			aResponse.setQuestionTwo("N/A");
		else
			aResponse.setQuestionTwo(request.getParameter("q2Response"));
		
		if(request.getParameter("q3Response").equals(""))
			aResponse.setQuestionThree("N/A");
		else
			aResponse.setQuestionThree(request.getParameter("q3Response"));
		
		//add IP address and host to the SurveyResponseItem
		aResponse.setIpAddress(request.getRemoteAddr());
		aResponse.setHostAddress(request.getRemoteHost());
		
		//set date and time
		aResponse.setResponseDate(now.toString());
		
		return aResponse;
	}
	
}
