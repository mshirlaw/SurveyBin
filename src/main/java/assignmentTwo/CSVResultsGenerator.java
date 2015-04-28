package assignmentTwo;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.*;
import java.util.ArrayList;

import java.sql.ResultSet;
import java.sql.SQLException;

import au.com.bytecode.opencsv.CSVWriter;


/**
 * This Servlet builds a summary of the results for
 * a given survey as a CSV file which can be saved to disk
 */
@WebServlet(urlPatterns={"CSVResultsGenerator"})
public class CSVResultsGenerator extends HttpServlet
{

    //get an instance of the DAO
    private DataStore dataStore;

    public CSVResultsGenerator() throws ReflectiveOperationException, SQLException
    {
        dataStore = DataStore.getInstance();
    }

    /**
     * Creates a CSV file and uses the Content-Disposition header to
     * force download of the file rather than display results in the browser
	 * doGet
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
        //get the file path of the servlet
        String id = request.getParameter("surveyID");
        String filePath = getServletContext().getRealPath("") + File.separator + "surveyID_" + id + ".csv";

        //set the size of the output buffer in bytes
        int bufferSize = 4096;

        //create the JSON File
        createCSVFile(filePath, id);

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
     * Creates a CSV file of results
     * @param filePath The path to the CSV file that is created
     * @param surveyID The UUID of the survey for which results should be displayed
     */
    private void createCSVFile(String filePath, String surveyID) throws ServletException
    {
        //create a csv writer
        CSVWriter writer;

        //the returned list of responses
        ArrayList<SurveyResponseItem> responses;

        //turn each response into a string
        String line;

        try
        {
            writer = new CSVWriter(new FileWriter(filePath), ',');
            responses = dataStore.getSurveyResponses(surveyID);

            //header row
            line = "Survey Name" + "#"
                    + "IP addresses" + "#"
                    + "Time completed" + "#"
                    + "Question 1" + "#"
                    + "Question 2" + "#"
                    + "Question 3";

            writer.writeNext(line.split("#"));

            //all responses
            for(int i=0; i<responses.size();i++)
            {
                line = responses.get(i).getTitle() + "#"
                        + responses.get(i).getIpAddress() + "#"
                        + responses.get(i).getResponseDate() + "#"
                        + responses.get(i).getQuestionOne() + "#"
                        + responses.get(i).getQuestionTwo() + "#"
                        + responses.get(i).getQuestionThree();

                writer.writeNext(line.split("#"));
            }

            writer.close();

        }
        catch(SQLException | IOException ex) {
            throw new ServletException(ex);
        }
    }
}
