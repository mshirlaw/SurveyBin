package assignmentTwo;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

/**
 * Servlet implementation class SurveyPageBuilder
 * This Servlet builds the survey page for a survey with a given UUID
 * and passes the the user's response via POST to the SurveyResponseHandler.
 * @author mshirlaw
 */
@WebServlet("/SurveyPageBuilder")
public class SurveyPageBuilder extends HttpServlet {

    //get an instance of the DAO
    private assignmentTwo.DataStore dataStore;

    public SurveyPageBuilder() throws ReflectiveOperationException, SQLException
    {
        dataStore = assignmentTwo.DataStore.getInstance();
    }

	/**
	 * doGet
	 * pass GET request directly to doPost
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		//pass GET request directly to doPost
		doPost(request, response);
	}

	/**
	 * doPost
	 * Build a page for a survey with a given UUID
	 * Pass the user's response to the survey to the SurveyResponseHandler Servlet. 
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		//set content type
		response.setContentType("text/html");
		
		//get a PrintWriter object
		PrintWriter out = response.getWriter();
		
		//the action for the form
		String formAction = "SurveyResponseHandler";
		
		//get the survey object from the list
		String surveyID = request.getParameter("surveyID");

		//retrieve the survey
        assignmentTwo.SurveyItem theSurvey;
        try
        {
            theSurvey = dataStore.getSurveyForm(surveyID);
        }
        catch(SQLException ex)
        {
            throw new ServletException(ex);
        }

		
		//get the survey title
		String title = theSurvey.getTitle();;
		if (title == "")
			title="No title provided";
		
		//get the opening text
		String openingText = theSurvey.getOpeningText();;
		if (openingText == "")
			openingText="No opening text provided";
		
		//get the text of the first question
		String questionOne = theSurvey.getQuestionOne();
		if (questionOne == "")
			questionOne="Not provided";
		
		//get the text of the second question
		String questionTwo = theSurvey.getQuestionTwo();
		if (questionTwo == "")
			questionTwo ="Not provided";
		
		//get the text of the third question
		String questionThree = theSurvey.getQuestionThree();
		if (questionThree == "")
			questionThree="Not provided";
		
		//build the textarea for question 3
		String textArea = "<textarea class=\"form-control\" rows=\"5\" name=\"q3Response\"></textarea><br />";
		
		//build the submit button
		String submitButton = "<button class=\"btn btn-default\" type=\"submit\">Submit Survey</button>";
		
		//calculate how many options to use in the drop down menu
		int numberOfOptions = theSurvey.getChoices();
		
		//build the body of the HTML document
        String head = "<head>"
                + "<link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.2.0/css/bootstrap.min.css\">"
                + "<link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.2.0/css/bootstrap-theme.min.css\">"
                + "<link type=\"text/css\" rel=\"stylesheet\" href=\"main.css\">"
                + "<title>Survey Bin | Create a Survey</title>"
                + "</head>";
        String body =  "<body>"
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
                + "<div class=\"page-header\">"
                + "<h1>"+title+"</h1>"
	    		+ "<h4>"+openingText+"</h4>"
                + "</div>"
	    		+ "<form action=\"" + formAction + "\" method=\"POST\">"
                + "<div class=\"panel panel-default\">"
                + "<div class=\"panel-heading\">"
                + "<h3 class=\"panel-title\">Question 1: " + questionOne + "</h3>"
                + "</div>"
                + "<div class=\"panel-body\">"
                + "<p>Please choose an answer using the scale below:</p>"
	    		+ buildLikertScale()
                + "</div>"
                + "</div>"
                + "<div class=\"panel panel-default\">"
                + "<div class=\"panel-heading\">"
                + "<h3 class=\"panel-title\">Question 2: " + questionTwo + "</h3>"
                + "</div>"
                + "<div class=\"panel-body\">"
	   			+ "<p>Please pick a number between 0 and " + numberOfOptions + ":</p>"
	   			+ buildSelectBox(numberOfOptions)
                + "</div>"
                + "</div>"
                + "<div class=\"panel panel-default\">"
                + "<div class=\"panel-heading\">"
                + "<h3 class=\"panel-title\">Question 3: " + questionThree + "</h3>"
                + "</div>"
                + "<div class=\"panel-body\">"
                + "<p>Please type your answer in the space provided below:</p>"
	    		+ textArea
                + "</div>"
                + "</div>"
                + "<input type=\"hidden\" name=\"title\" value=\"" + title + "\">"
	        	+ "<input type=\"hidden\" name=\"intro\" value=\"" + openingText + "\">"
	    		+ "<input type=\"hidden\" name=\"surveyID\" value=\"" + surveyID + "\">"
	    		+ submitButton
	    		+ "</form>"
                + "</div>";

        //build the page footer
        String footer = "<div class=\"container\">"
                + "<br /><br /><hr /><address>Matthew Shirlaw<br />Assignment 2<br />COMP391 Advanced Web Technologies</address>"
                + "</div>"
                + "</body>";

		//display the HTML survey page
		out.write("<!DOCTYPE html>");
		out.println("<html>");
		out.println(head);
		out.println(body);
		out.println(footer);
		out.println("</html>");
	}	
	
	/**
	 * buildLikertScale
	 * Builds a series of checkboxes to form a likert scale
	 * @return A String representation of the HTML checkboxes
	 */
	public String buildLikertScale()
	{
		return "<label class=\"radio-inline\">"
                +"<input type=\"radio\" name=\"q1Response\" "
				+ "value=\"strongly disagree\"><i>Strongly Disagree</i>"
                + "</label>"
                + "<label class=\"radio-inline\">"
				+ "<input type=\"radio\" name=\"q1Response\""
				+ "value=\"slightly disagree\"><i>Slightly Disagree</i>"
                + "</label>"
                + "<label class=\"radio-inline\">"
                + "<input type=\"radio\" name=\"q1Response\""
				+ "value=\"neutral\"><i>Neutral</i>"
                + "</label>"
                + "<label class=\"radio-inline\">"
                + "<input type=\"radio\" name=\"q1Response\""
				+ "value=\"slightly agree\"><i>Slightly Agree</i>"
                + "</label>"
                + "<label class=\"radio-inline\">"
                + "<input type=\"radio\" name=\"q1Response\""
				+ "value=\"agree\"><i>Agree</i>"
                + "</label>";
	}
	
	/**
	 * buildSelectBox
	 * Builds a select box with number of choices specified
	 * @param boxes The number of options to display
	 * @return A string representation of the HTML code for a select box
	 */
	public String buildSelectBox(int boxes)
	{
		StringBuffer selectBox = new StringBuffer("<select class=\"form-control\" name=\"q2Response\">");
		
		for(int i=boxes; i>=0; i--)
		{
			String line = "<option value=\""+i+"\">"+i+"</option>";
			selectBox.append(line);
		}
		
		selectBox.append("</select>");
		
		return selectBox.toString();
	}
	
}
