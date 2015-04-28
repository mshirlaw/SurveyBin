package assignmentTwo;

import au.com.bytecode.opencsv.CSVWriter;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.RequestDispatcher;

import java.io.*;
import java.sql.SQLException;
import java.util.ArrayList;


/**
 * This Servlet will produce a page which is
 * dependent upon the value of the Accept header.
 * If the browser accepts text/html or * then HTML will be returned
 * If the browser accepts application/json then JSON formatted data will be returned
 * If the browser accepts text/csv then CSV formatted data will be returned
 */
@WebServlet(urlPatterns={"APIPageGenerator"})
public class APIPageGenerator extends HttpServlet
{
    //get an instance of the DAO
    private DataStore dataStore;

    public APIPageGenerator() throws ReflectiveOperationException, SQLException
    {
        dataStore = DataStore.getInstance();
    }

    /**
	 * doGet
     * Determines the value of the request header and returns the
     * data in the appropriate format
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
        //get the correct surveyID
        String surveyID = request.getParameter("surveyID");

        //get a PrintWriter object
        PrintWriter out = response.getWriter();

        //check the request header
        if(request.getHeader("Accept").contains("text/html") || request.getHeader("Accept").contains("*"))
        {
            outputHTMLPage(surveyID, request, response);
        }
        else if(request.getHeader("Accept").contains("application/json") &&
                !(request.getHeader("Accept").contains("text/html")))
        {
            response.setContentType("application/json");
            outputJSONPage(surveyID, out);
        }
        else if(request.getHeader("Accept").contains("text/csv") &&
                !(request.getHeader("Accept").contains("application/json")) &&
                !(request.getHeader("Accept").contains("text/html")))
        {
            response.setContentType("text/csv");
            outputCSVPage(surveyID, out);
        }

    }

	/**
	 * doPost
     * Passes POST requests to the doGet method
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
        doGet(request,response);
    }

    /**
     * Outputs a results page in CSV format
     * @param surveyID The UUID of the survey for which results should be displayed
     */
    private void outputCSVPage(String surveyID, PrintWriter out) throws ServletException
    {
        //the returned list of responses
        ArrayList<SurveyResponseItem> responses;

        //turn each response into a string
        String line;

        try
        {
            responses = dataStore.getSurveyResponses(surveyID);

            //header row
            line = "Survey Name" + ","
                    + "IP addresses" + ","
                    + "Time completed" + ","
                    + "Question 1" + ","
                    + "Question 2" + ","
                    + "Question 3" + "\n";

            out.write(line);

            //all responses
            for(int i=0; i<responses.size();i++)
            {
                line = responses.get(i).getTitle() + ","
                        + responses.get(i).getIpAddress() + ","
                        + responses.get(i).getResponseDate() + ","
                        + responses.get(i).getQuestionOne() + ","
                        + responses.get(i).getQuestionTwo() + ","
                        + responses.get(i).getQuestionThree() + "\n";

                out.write(line);
            }

            out.close();

        }
        catch(SQLException ex) {
            throw new ServletException(ex);
        }

    }

    /**
     * Outputs a results page in JSON format
     * @param surveyID The UUID of the survey for which results should be displayed
     */
    private void outputJSONPage(String surveyID, PrintWriter out) throws ServletException
    {

        //create the JSON object
        JSONObject jsonObject = new JSONObject();

        //get all survey responses for this survey
        ArrayList<SurveyResponseItem> allResponses;

        //get the survey form data
        SurveyItem survey;

        try
        {
            allResponses = dataStore.getSurveyResponses(surveyID);
            survey = dataStore.getSurveyForm(surveyID);
        }
        catch(SQLException ex) {
            throw new ServletException(ex);
        }

        //set survey name
        jsonObject.put("Survey Name", survey.getTitle());

        //populate all ip addresses
        JSONArray ipValues = new JSONArray();
        for(int i=0; i<allResponses.size();i++)
        {
            ipValues.add(allResponses.get(i).getIpAddress());
        }
        jsonObject.put("IP addresses", ipValues);

        //populate all date values
        JSONArray dateValues = new JSONArray();
        for(int i=0; i<allResponses.size();i++)
        {
            dateValues.add(allResponses.get(i).getResponseDate());
        }
        jsonObject.put("Time completed", ipValues);

        //populate all Q1 answers
        JSONArray questionOneValues = new JSONArray();
        for(int i=0; i<allResponses.size();i++)
        {
            questionOneValues.add(allResponses.get(i).getQuestionOne());
        }
        jsonObject.put("Question 1", questionOneValues);

        //populate all Q2 answers
        JSONArray questionTwoValues = new JSONArray();
        for(int i=0; i<allResponses.size();i++)
        {
            questionTwoValues.add(allResponses.get(i).getQuestionTwo());
        }
        jsonObject.put("Question 2", questionTwoValues);

        //populate all Q3 answers
        JSONArray questionThreeValues = new JSONArray();
        for(int i=0; i<allResponses.size();i++)
        {
            questionThreeValues.add(allResponses.get(i).getQuestionThree());
        }
        jsonObject.put("Question 3", questionThreeValues);

        out.write(jsonObject.toString());
    }

    /**
     * Outputs a retults page in HTML format
     * @param surveyID The UUID of the survey for which results should be displayed
     */
    private void outputHTMLPage(String surveyID, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
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
