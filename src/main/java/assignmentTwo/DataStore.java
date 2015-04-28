package assignmentTwo;

import java.sql.*;
import java.util.ArrayList;


/**
 * DataStore is a singleton class used to store information about
 * surveys created using the SurveyBin web application. 
 * DataStore maintains a listOfSurveys and listOfResponses
 * and is used by the other servlets in the application.
 * The static methods in the DataStore class are thread safe and therefore 
 * can serve concurrent requests  
 * @author mshirlaw
 */
public class DataStore 
{
	private static DataStore INSTANCE;

    //private ArrayList<assignmentTwo.SurveyItem> listOfSurveys;
	//private ArrayList<assignmentTwo.SurveyResponseItem> listOfResponses;

    private static String driver = "org.apache.derby.jdbc.EmbeddedDriver";
    private static String dbName = "SURVEYS_DB";
    private static String connectionUrl = "jdbc:derby:" + dbName + ";create=true";

    //constructor initialises database and creates two lists - may not need the lists
    public DataStore() throws ReflectiveOperationException, SQLException  {
        initDatabase();
        //listOfSurveys = new ArrayList<assignmentTwo.SurveyItem>();
        //listOfResponses = new ArrayList<assignmentTwo.SurveyResponseItem>();
    }

    //get an instance of the DAO
    public synchronized static DataStore getInstance() throws ReflectiveOperationException, SQLException {
        if (INSTANCE == null) {
            INSTANCE = new DataStore();
        }
        return INSTANCE;
    }


    /**
     * initDatabase
     * Initialises a derby database by creating two
     * tables
     * 1. SURVEY_ITEMS and
     * 2. SURVEY_RESPONSES
     * The structure of these database tables is outlined in attached documentation
     */

    Connection connection;

    private void initDatabase() throws ReflectiveOperationException, SQLException {
        if (connection == null)
        {

            Class.forName(driver).newInstance();
            connection = DriverManager.getConnection(connectionUrl);

            //try with resources
            try
            (
                ResultSet questionSet = connection.getMetaData().getTables(null, null, "SURVEY_ITEMS", null);
                ResultSet responsesSet = connection.getMetaData().getTables(null, null, "SURVEY_RESPONSES", null);
                Statement s = connection.createStatement();
            )
            {
                //create questions table
                if (!questionSet.next())
                {
                    s.executeUpdate("CREATE TABLE SURVEY_ITEMS ("
                            + "SESSION_ID VARCHAR(255) NOT NULL,"
                            + "SURVEY_ID VARCHAR(255) NOT NULL,"
                            + "TITLE VARCHAR(255),"
                            + "OPENING_TEXT VARCHAR(255),"
                            + "CHOICES INT,"
                            + "QUESTION_ONE VARCHAR(255),"
                            + "QUESTION_TWO VARCHAR(255),"
                            + "QUESTION_THREE VARCHAR(255)"
                            + ")");
                }

                //create responses table
                if (!responsesSet.next())
                {
                    s.executeUpdate("CREATE TABLE SURVEY_RESPONSES ("
                            + "SESSION_ID VARCHAR(255) NOT NULL,"
                            + "SURVEY_ID VARCHAR(255) NOT NULL,"
                            + "IP_ADDRESS varchar(255),"
                            + "HOST_ADDRESS VARCHAR(255),"
                            + "RESPONSE_DATE VARCHAR(255),"
                            + "TITLE VARCHAR(255),"
                            + "OPENING_TEXT VARCHAR(255),"
                            + "CHOICES INT,"
                            + "QUESTION_ONE VARCHAR(255),"
                            + "QUESTION_TWO VARCHAR(255),"
                            + "QUESTION_THREE VARCHAR(255)"
                            + ")");
                }
            }
        }
    }

    /**
     * Close the database connection
     * @throws SQLException
     */
    public void shutdown() throws SQLException {
        connection.close();
    }


    /**
     * getSurveysInSession
     * Returns an Array List of all surveys
     * that have been created in a given session
     * @return surveysInSession A list of all surveys created during the current session
     */
    public synchronized ArrayList<assignmentTwo.SurveyItem> getSurveysInSession(String sessionID) throws SQLException
    {
        //an array list to store all surveys created in a given session
        ArrayList<assignmentTwo.SurveyItem> surveysInSession = new ArrayList<assignmentTwo.SurveyItem>();

        try
        (
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM SURVEY_ITEMS WHERE SESSION_ID = ?");
        )
        {
            statement.setString(1, sessionID);
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {

                assignmentTwo.SurveyItem survey = new assignmentTwo.SurveyItem();
                survey.setSessionID(rs.getString(1));
                survey.setSurveyID(rs.getString(2));
                survey.setTitle(rs.getString(3));
                survey.setOpeningText(rs.getString(4));
                survey.setChoices(rs.getInt(5));
                survey.setQuestionOne(rs.getString(6));
                survey.setQuestionTwo(rs.getString(7));
                survey.setQuestionThree(rs.getString(8));

                //add survey to array list
                surveysInSession.add(survey);
            }
        }

        return surveysInSession;

    }

    /**
     * getResponsesInSession
     * Returns an Array List of
     * responses to surveys that have been created during the current session
     * @return responses A list of all responses to surveys created during the current session
     */
    public synchronized ArrayList<assignmentTwo.SurveyResponseItem> getResponsesInSession(String sessionID) throws SQLException
    {
        //an array list to store all surveys created in this session
        ArrayList<assignmentTwo.SurveyResponseItem> responsesInSession = new ArrayList<assignmentTwo.SurveyResponseItem>();

        try
        (
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM SURVEY_RESPONSES WHERE SESSION_ID = ?");
        )
        {
            statement.setString(1, sessionID);
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {

                assignmentTwo.SurveyResponseItem response = new assignmentTwo.SurveyResponseItem();
                response.setSessionID(rs.getString(1));
                response.setSurveyID(rs.getString(2));
                response.setIpAddress(rs.getString(3));
                response.setHostAddress(rs.getString(4));
                response.setResponseDate(rs.getString(5));
                response.setTitle(rs.getString(6));
                response.setOpeningText(rs.getString(7));
                response.setChoices(rs.getInt(8));
                response.setQuestionOne(rs.getString(9));
                response.setQuestionTwo(rs.getString(10));
                response.setQuestionThree(rs.getString(11));

                //add survey to array list
                responsesInSession.add(response);
            }
        }

        return responsesInSession;

    }

    /**
	 * addSurveyForm
     * Adds a new survey to SURVEY_ITEMS table of the
     * database.
	 * @param s The new SurveyItem to add to the database
	 */
	public synchronized void addSurveyForm(assignmentTwo.SurveyItem si) throws SQLException
	{
        //TODO - will we need this arraylist?
        //add to the arraylist of surveys
		//listOfSurveys.add(si);

        try
        (
            //prepare an insert statement
            PreparedStatement insert = connection.prepareStatement(
                    "INSERT INTO SURVEY_ITEMS"
                  + "(SESSION_ID, SURVEY_ID, TITLE, OPENING_TEXT, CHOICES, QUESTION_ONE, QUESTION_TWO, QUESTION_THREE)"
                  +  "VALUES (?, ?, ?, ?, ?, ?, ?, ?)"
            );
        )
        {
            //insert survey values into database
            insert.setString(1, si.getSessionID());
            insert.setString(2, si.getSurveyID());
            insert.setString(3, si.getTitle());
            insert.setString(4, si.getOpeningText());
            insert.setInt(5, si.getChoices());
            insert.setString(6, si.getQuestionOne());
            insert.setString(7, si.getQuestionTwo());
            insert.setString(8, si.getQuestionThree());

            //execute the update
            insert.executeUpdate();

            //debug
            //System.out.println("Inserted survey into database");
        }
    }
	
	/**
	 * getSurveyForm
     * Returns a given SurveyItem from the database
	 * @param id The unique UUID of the survey
	 * @return A survey with the UUID equal to id or null if not found
	 */
	public synchronized assignmentTwo.SurveyItem getSurveyForm(String id) throws SQLException
	{
        try
        (
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM SURVEY_ITEMS WHERE SURVEY_ID = ?");
        ) {
            statement.setString(1, id);
            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                assignmentTwo.SurveyItem survey = new assignmentTwo.SurveyItem();
                survey.setSurveyID(rs.getString(1));
                survey.setSessionID(rs.getString(2));
                survey.setTitle(rs.getString(3));
                survey.setOpeningText(rs.getString(4));
                survey.setChoices(rs.getInt(5));
                survey.setQuestionOne(rs.getString(6));
                survey.setQuestionTwo(rs.getString(7));
                survey.setQuestionThree(rs.getString(8));
                return survey;

            } else {
                return null;
            }
        }
	}

	/**
	 * addSurveyResponse
     * Adds a new survey response to the
	 * database in the SURVEY_RESPONSES table
	 * @param s The new SurveyResponseItem to add to the listOfSurveys
	 */
	public synchronized void addSurveyResponse(assignmentTwo.SurveyResponseItem sri) throws SQLException
	{
        //add to the array list - TODO remove this entirely
        //listOfResponses.add(sri);

        try
        (
            //prepare an insert statement
            PreparedStatement insert = connection.prepareStatement(
                  "INSERT INTO SURVEY_RESPONSES"
                + "(SESSION_ID, SURVEY_ID, IP_ADDRESS, HOST_ADDRESS, RESPONSE_DATE,"
                + "TITLE, OPENING_TEXT, CHOICES, QUESTION_ONE, QUESTION_TWO, QUESTION_THREE)"
                +  "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)"
            );
        )
        {
            //insert survey values into database
            insert.setString(1, sri.getSessionID());
            insert.setString(2, sri.getSurveyID());
            insert.setString(3, sri.getIpAddress());
            insert.setString(4, sri.getHostAddress());
            insert.setString(5, sri.getResponseDate());
            insert.setString(6, sri.getTitle());
            insert.setString(7, sri.getOpeningText());
            insert.setInt(8, sri.getChoices());
            insert.setString(9, sri.getQuestionOne());
            insert.setString(10, sri.getQuestionTwo());
            insert.setString(11, sri.getQuestionThree());

            //execute the update
            insert.executeUpdate();

            //debug
            //System.out.println("Inserted response into database");
        }

    }

	/**
	 * getSurveyResponses
     * Returns an array list of all survey responses for a given survey
	 * @param id The unique UUID of the survey response
	 * @return An ArrayList of all survey responses with UUID equal to id or null
	 */
	public synchronized ArrayList<assignmentTwo.SurveyResponseItem> getSurveyResponses(String id) throws SQLException
	{
		//create an array list to store all responses for a give survey
		ArrayList<assignmentTwo.SurveyResponseItem> responsesForSurvey = new ArrayList<assignmentTwo.SurveyResponseItem>();

        try
        (
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM SURVEY_RESPONSES WHERE SURVEY_ID = ?");
        )
        {
            statement.setString(1, id);
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {

                assignmentTwo.SurveyResponseItem surveyResponse = new assignmentTwo.SurveyResponseItem();
                surveyResponse.setSessionID(rs.getString(1));
                surveyResponse.setSurveyID(rs.getString(2));
                surveyResponse.setIpAddress(rs.getString(3));
                surveyResponse.setHostAddress(rs.getString(4));
                surveyResponse.setResponseDate(rs.getString(5));
                surveyResponse.setTitle(rs.getString(6));
                surveyResponse.setOpeningText(rs.getString(7));
                surveyResponse.setChoices(rs.getInt(8));
                surveyResponse.setQuestionOne(rs.getString(9));
                surveyResponse.setQuestionTwo(rs.getString(10));
                surveyResponse.setQuestionThree(rs.getString(11));

                //add survey to array list
                responsesForSurvey.add(surveyResponse);
            }
        }

        if(responsesForSurvey.size()==0)
            return null;
        else
            return responsesForSurvey;
	}
}
