package assignmentTwo;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * A static utility class that assists in
 * producing summary data for SurveyBin surveys
 */

public class SurveyUtilities
{
    /**
     * buildSurveyList
     * Builds an ordered list of links to all surveys that have been
     * created during the current session
     * @return An ordered list of links to all surveys that have been created in this session
     */
    public static String buildSurveyList(String sessionID) throws ReflectiveOperationException, SQLException
    {
        //get all surveys created in this session
        ArrayList<assignmentTwo.SurveyItem> surveysInSession = assignmentTwo.DataStore.getInstance().getSurveysInSession(sessionID);

        //create a string to hold the list of responses to these surveys
        String surveyList = "<ol>";

        //get all addresses
        for(int i=0; i<surveysInSession.size();i++)
        {
            surveyList += "<li><a class=\"s-links\" href=\"SurveyPageBuilder?surveyID="
                    + surveysInSession.get(i).getSurveyID()+"\">"
                    + surveysInSession.get(i).getSurveyID()+"<a>"
                    + "</li>";
        }

        return surveyList + "</ol>";
    }

    /**
     * buildResultsList
     * Builds an ordered list of links to the results pages for all surveys that have been
     * created during the current session
     * @return An ordered list of links to the results pages for all surveys that have been created in this session
     */
    public static String buildResultsList(String sessionID) throws ReflectiveOperationException, SQLException
    {
        //get all surveys created in this session
        ArrayList<assignmentTwo.SurveyItem> surveysInSession = assignmentTwo.DataStore.getInstance().getSurveysInSession(sessionID);

        //create a string to hold the list of results pages
        String resultsList = "<ol>";

        //get all addresses
        for(int i=0; i<surveysInSession.size();i++)
        {
            resultsList += "<li><a class=\"s-links\" href=\"SurveyResultsGenerator?surveyID="
                    + surveysInSession.get(i).getSurveyID()+"\">"
                    + surveysInSession.get(i).getSurveyID()+"<a>"
                    + "</li>";
        }

        return resultsList + "</ol>";
    }

    /**
     * createIpValues
     * Returns a string representation of an unordered list of
     * IP address values of users who have submitted a response to a given
     * survey
     * @param id The UUID of the survey for which IP addresses should be displayed
     * @return A string representation of a HTML list of IP addresses
     */
    public static String createIpValues(String id) throws ReflectiveOperationException, SQLException
    {
        //get responses
        assignmentTwo.DataStore dataStore = assignmentTwo.DataStore.getInstance();
        ArrayList<assignmentTwo.SurveyResponseItem> allResponses = dataStore.getSurveyResponses(id);

        //create a string to hold the list of IP addresses
        String addresses = "<ul>";

        //get all addresses
        for(int i=0; i<allResponses.size();i++)
        {
            addresses += "<li>"+allResponses.get(i).getIpAddress()+"</li>";
        }

        //finish list
        addresses += "</ul>";

        return addresses;
    }

    /**
     * createDateValues
     * Returns a string representation of an unordered list of
     * date and times that surveys were submitted for a given
     * survey
     * @param id The UUID of the survey for which dates and times should be displayed
     * @return A string representation of a HTML list of dates and times
     */
    public static String createDateValues(String id) throws ReflectiveOperationException, SQLException
    {
        //get responses
        assignmentTwo.DataStore dataStore = assignmentTwo.DataStore.getInstance();
        ArrayList<assignmentTwo.SurveyResponseItem> allResponses = dataStore.getSurveyResponses(id);

        //create a string to hold the list of IP addresses
        String dates = "<ul>";

        //get all addresses
        for(int i=0; i<allResponses.size();i++)
        {
            dates += "<li>"+allResponses.get(i).getResponseDate() +"</li>";
        }

        //finish list
        dates += "</ul>";

        return dates;
    }

    /**
     * createQuestionOneValues
     * Returns a string representation of an unordered list of
     * responses to Q1 that were submitted for a given
     * survey
     * @param id The UUID of the survey for which Q1 responses should be displayed
     * @return A string representation of a HTML list of responses to Q1
     */
    public static String createQuestionOneValues(String id) throws ReflectiveOperationException, SQLException
    {
        //get responses
        assignmentTwo.DataStore dataStore = assignmentTwo.DataStore.getInstance();
        ArrayList<assignmentTwo.SurveyResponseItem> allResponses = dataStore.getSurveyResponses(id);

        //create a string to hold the list of Q1 values
        String questionOneValues = "<ul>";

        //get all addresses
        for(int i=0; i<allResponses.size();i++)
        {
            questionOneValues += "<li>"+allResponses.get(i).getQuestionOne() +"</li>";
        }

        //finish list
        questionOneValues += "</ul>";

        return questionOneValues;
    }

    /**
     * createQuestionTwoValues
     * Returns a string representation of an unordered list of
     * responses to Q2 that were submitted for a given
     * survey
     * @param id The UUID of the survey for which Q2 responses should be displayed
     * @return A string representation of a HTML list of responses to Q2
     */
    public static String createQuestionTwoValues(String id) throws ReflectiveOperationException, SQLException
    {
        //get responses
        assignmentTwo.DataStore dataStore = assignmentTwo.DataStore.getInstance();
        ArrayList<assignmentTwo.SurveyResponseItem> allResponses = dataStore.getSurveyResponses(id);

        //create a string to hold the list of Q2 values
        String questionTwoValues = "<ul>";

        //get all addresses
        for(int i=0; i<allResponses.size();i++)
        {
            questionTwoValues += "<li>"+allResponses.get(i).getQuestionTwo() +"</li>";
        }

        //finish list
        questionTwoValues += "</ul>";

        return questionTwoValues;
    }

    /**
     * createQuestionThreeValues
     * Returns a string representation of an unordered list of
     * responses to Q3 that were submitted for a given
     * survey
     * @param id The UUID of the survey for which Q3 responses should be displayed
     * @return A string representation of a HTML list of responses to Q3
     */
    public static String createQuestionThreeValues(String id) throws ReflectiveOperationException, SQLException
    {
        //get responses
        assignmentTwo.DataStore dataStore = assignmentTwo.DataStore.getInstance();
        ArrayList<assignmentTwo.SurveyResponseItem> allResponses = dataStore.getSurveyResponses(id);

        //create a string to hold the list of IP addresses
        String questionThreeValues = "<ul>";

        //get all addresses
        for(int i=0; i<allResponses.size();i++)
        {
            questionThreeValues += "<li>"+allResponses.get(i).getQuestionThree() +"</li>";
        }

        //finish list
        questionThreeValues += "</ul>";

        return questionThreeValues;
    }

    /**
     * Returns a string representation of a list of download links
     * for survey data in JSON and CSV format
     * @param id The UUID of the survey
     * @return A string representation of HTML links to API data
     */
    public static String buildAPILinks(String id)
    {
        return "<a href=\"JSONResultsGenerator?surveyID="
                + id
                + "\">Download JSON Data</a><br />"
                + "<a href=\"CSVResultsGenerator?surveyID="
                + id
                + "\">Download CSV Data</a><br />"
                + "<a href=\"APIPageGenerator?surveyID="
                + id
                + "\">View JSON or CSV Data in a Browser</a>";
    }
}
