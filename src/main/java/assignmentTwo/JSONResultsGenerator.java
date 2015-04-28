package assignmentTwo;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;

import java.io.*;
import java.sql.SQLException;
import java.io.DataInputStream;
import java.util.ArrayList;

/**
 * This Servlet builds a summary of the results for
 * a given survey as a JSON formatted file which can be saved to disk
 */
@WebServlet(urlPatterns={"JSONResultsGenerator"})
public class JSONResultsGenerator extends HttpServlet
{

    //get an instance of the DAO
    private assignmentTwo.DataStore dataStore;

    public JSONResultsGenerator() throws ReflectiveOperationException, SQLException
    {
        dataStore = assignmentTwo.DataStore.getInstance();
    }

    /**
     * Creates a JSON formatted file and uses the Content-Disposition header to
     * force download of the file rather than display results in the browser
	 * doGet
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
        //get the file path of the servlet
        String id = request.getParameter("surveyID");
        String filePath = getServletContext().getRealPath("") + File.separator + "surveyID_" + id + ".json";

        //set the size of the output buffer in bytes
        int bufferSize = 4096;

        //create the JSON File
        createJSONFile(filePath, id);

        //create a link to the file just created
        File file = new File(filePath);
        int length = 0;
        ServletOutputStream outStream = response.getOutputStream();
        ServletContext context  = getServletConfig().getServletContext();
        String mimeType = context.getMimeType(filePath);

        //set response content type
        if (mimeType == null)
        {
            mimeType = "application/octet-stream";
        }
        response.setContentType(mimeType);
        response.setContentLength((int)file.length());
        String fileName = (new File(filePath)).getName();

        //set HTTP header
        response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");

        //create a byte array to store the file
        byte[] byteBuffer = new byte[bufferSize];
        DataInputStream inputStream = new DataInputStream(new FileInputStream(file));

        //read the file's bytes and writes them to the response
        while ((inputStream != null) && ((length = inputStream.read(byteBuffer)) != -1))
        {
            outStream.write(byteBuffer,0,length);
        }

        //close both streams
        inputStream.close();
        outStream.close();
    }

	/**
     * Passes POST requests to the doGet method
	 * doPost
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
        doGet(request,response);
    }

    /**
     * Creates a JSON formatted file of results
     * @param filePath The path to the JSON file that is created
     * @param surveyID The UUID of the survey for which results should be displayed
     */
    private void createJSONFile(String filePath, String surveyID) throws ServletException
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

        //write the JSON data to file
        try
        {
            FileWriter file = new FileWriter(filePath);
            file.write(jsonObject.toJSONString());
            file.flush();
            file.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

}
