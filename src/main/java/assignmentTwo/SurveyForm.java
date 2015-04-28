package assignmentTwo;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class SurveyForm
 * This Servlet presents an HTML form which allows
 * a user to create a custom SurveyBin survey. POST
 * data is sent to the SurveyBuilder Servlet 
 * @author mshirlaw
 */
@WebServlet("/SurveyForm")
public class SurveyForm extends HttpServlet {
    
	/**
	 * doGet 
	 * Displays an HTML form for the user to 
	 * use when creating a new SurveyBin survey 
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{		
		//set content type
		response.setContentType("text/html");
				
		//get a PrintWriter object
		PrintWriter out = response.getWriter();
						
		//create page
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
						+ "<p>Here you can create your own custom SurveyBin<sup>&reg;</sup> survey. "
						+ "Simply complete the form below and "
						+ "hit the \"Create Your Survey\" button when you are ready to go live!"
						+ "</p> "
						+ "<p>In no time at all you will be presented with two custom links. One that you can share with your clients"
						+ "and another to view a detailed breakup of your survey results. "
						+ "</p>"
						+ "<h2>Customise your survey</h2>"
						+ "<form id=\"survery_creator\" action=\"SurveyBuilder\" method=\"POST\">"
						+ "<label>What is the title of your survey? "+ missingTitle(request)+"</label><br />"
						+ "<textarea class=\"form-control\" rows=\"2\" name=\"title\"></textarea>"
						+ "<br />"
						+ "<label>Enter some introductory text for your survey:"+ missingIntro(request)+"</label><br />"
						+ "<textarea class=\"form-control\" rows=\"3\" name=\"intro\"></textarea>"
						+ "<br />"
						+ "<label>Enter the text for the first question that you want "
						+ "to ask (uses a likert scale):"+ missingQuestionOne(request)+"</label><br />"
						+ "<textarea class=\"form-control\" rows=\"3\" name=\"question1\"></textarea>"
						+ "<br />"
						+ "<label>Enter the text for the second question that you want "
						+ "to ask (a numeric answer):"+ missingQuestionTwo(request)+"</label><br />"
						+ "<textarea class=\"form-control\" rows=\"3\" name=\"question2\"></textarea>"
						+ "<br />"
						+ "<label for=\"choices\">How many options do you want to display for question 2?</label>"
						+ "<input type=\"text\" class=\"form-control\" name=\"choices\" />"
						+ "<br />"
						+ "<label>Enter the text for the third question that you want "
						+ "to ask (a long response question):"+ missingQuestionThree(request)+"</label><br />"
						+ "<textarea class=\"form-control\" rows=\"3\" name=\"question3\"></textarea>"
						+ "<br />"
						+ "<button type=\"submit\" class=\"btn btn-default\">Create Your Survey</button>"
						+ "</form>"
						+ "</div>"
						+ "</div>";
		
		//build the page footer
        String footer = "<div class=\"container\">"
                        + "<br /><br /><hr /><address>Matthew Shirlaw<br />Assignment 2<br />COMP391 Advanced Web Technologies</address>"
                        + "</div>"
                        + "</body>";
		
		//display the HTML form
		out.write("<!DOCTYPE html>");
		out.write("<html>");
		out.write(head);
		out.write(body);
		out.write(footer);
		out.write("</html>");
	}

	/**
	 * doPost 
	 * Simply passes any POST request to the doGet method
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		doGet(request,response);
	}	

	/**
	 * missingTitle
	 * Used to determine if the user failed to compete the title 
	 * section of the form and display an error message when the 
	 * form is re-displayed
	 * @param request The HttpServletRequest object
	 * @return An error message that indicates that the user failed to complete the form or an empty string
	 */
	public String missingTitle(HttpServletRequest request)
	{		
		if(Boolean.parseBoolean(request.getParameter("missingTitle")) == true)
		{
			return "<span style=\"color:red;\"> * Error! Title must not be blank<span>";
		}
		else 
		{
			return "";
		}
	}

	/**
	 * missingIntro
	 * Used to determine if the user failed to compete the introductory text 
	 * section of the form and display an error message when the 
	 * form is re-displayed
	 * @param request The HttpServletRequest object
	 * @return An error message that indicates that the user failed to complete the form or an empty string
	 */
	public String missingIntro(HttpServletRequest request)
	{		
		if(Boolean.parseBoolean(request.getParameter("missingIntro")) == true)
		{
			return "<span style=\"color:red;\"> * Error! Intro must not be blank<span>";
		}
		else 
		{
			return "";
		}
	}
	
	/**
	 * missingQuestionOne
	 * Used to determine if the user failed to compete the first question 
	 * section of the form and display an error message when the 
	 * form is re-displayed
	 * @param request The HttpServletRequest object
	 * @return An error message that indicates that the user failed to complete the form or an empty string
	 */
	public String missingQuestionOne(HttpServletRequest request)
	{		
		if(Boolean.parseBoolean(request.getParameter("missingQuestionOne")) == true)
		{
			return "<span style=\"color:red;\"> * Error! Q1 must not be blank<span>";
		}
		else 
		{
			return "";
		}
	}
	
	/**
	 * missingQuestionTwo
	 * Used to determine if the user failed to compete the second question 
	 * section of the form and display an error message when the 
	 * form is re-displayed
	 * @param request The HttpServletRequest object
	 * @return An error message that indicates that the user failed to complete the form or an empty string
	 */
	public String missingQuestionTwo(HttpServletRequest request)
	{		
		if(Boolean.parseBoolean(request.getParameter("missingQuestionTwo")) == true)
		{
			return "<span style=\"color:red;\"> * Error! Q2 must not be blank<span>";
		}
		else 
		{
			return "";
		}
	}
	
	/**
	 * missingQuestionThree
	 * Used to determine if the user failed to compete the third question 
	 * section of the form and display an error message when the 
	 * form is re-displayed
	 * @param request The HttpServletRequest object
	 * @return An error message that indicates that the user failed to complete the form or an empty string
	 */
	public String missingQuestionThree(HttpServletRequest request)
	{		
		if(Boolean.parseBoolean(request.getParameter("missingQuestionThree")) == true)
		{
			return "<span style=\"color:red;\"> * Error! Q3 must not be blank<span>";
		}
		else 
		{
			return "";
		}
	}

}
