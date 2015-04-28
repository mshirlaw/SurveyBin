package assignmentTwo;

/**
 * The SurveyItem class is used to represent
 * an individual survey. Each instance of the 
 * SurveyItem class contains information about the 
 * survey including the unique UUID that has been assigned to
 * the survey as well as the title, opening text and text of
 * each of the three questions
 * @author mshirlaw
 */
public class SurveyItem 
{
	//instance variables
	private String surveyID;
    private String sessionID;
	private String title;
	private String openingText;
	private int choices;
	private String questionOne;
	private String questionTwo;
	private String questionThree;
	
	/**
	 * Constructor
	 * Creates an empty SurveyItem
	 */
	public SurveyItem()
	{
		setSurveyID("");
		setQuestionOne("");
		setQuestionTwo("");
		setQuestionThree("");
	}

    /**
     *
     * @return
     */
    public String getSessionID() {
        return sessionID;
    }

    /**
     *
     * @param id
     */
    public void setSessionID(String id) {
        this.sessionID = id;
    }

	/**
	 * setTitle
	 * Sets the title for the survey
	 * @param t A title string for the survey
	 */
	public void setTitle(String t)
	{
		this.title=t;
	}
	
	/**
	 * setSurveyID
	 * Sets the unique UUID of the survey
	 * @param id The unique UUID for the survey
	 */
	public void setSurveyID(String id)
	{
		this.surveyID=id;
	}
	
	/**
	 * setOpeningText
	 * Sets the customised introductory text for the
	 * survey
	 * @param txt Customised introductory text for the survey
	 */
	public void setOpeningText(String txt)
	{
		this.openingText=txt;
	}

	/**
	 * setChoices
	 * Sets the number of options that can be chosen
	 * for the numeric question
	 * @param c The number of choices for the numeric question
	 */
	public void setChoices(int c)
	{
		this.choices=c;
	}
	
	/**
	 * setQuestionOne
	 * Sets the text of the first survey question
	 * @param one The text of the first survey question
	 */
	public void setQuestionOne(String one)
	{
		this.questionOne=one;
	}
	
	/**
	 * setQuestionTwo
	 * Sets the text of the second survey question
	 * @param two The text of the second survey question
	 */
	public void setQuestionTwo(String two)
	{
		this.questionTwo=two;
	}
	
	/**
	 * setQuestionThree
	 * Sets the text of the third survey question
	 * @param three The text of the third survey question
	 */
	public void setQuestionThree(String three)
	{
		this.questionThree=three;
	}
	
	/**
	 * getTitle
	 * Returns the title of the survey
	 * @return The title of the survey
	 */
	public String getTitle()
	{
		return this.title;
	}

	/**
	 * getSurveyID
	 * Returns the unique UUID of the survey
	 * @return The UUID of the survey
	 */
	public String getSurveyID()
	{
		return this.surveyID;
	}
	
	/**
	 * getOpeningText
	 * Returns the introductory text for the survey
	 * @return The opening text of the survey
	 */
	public String getOpeningText()
	{
		return this.openingText;
	}
	
	/**
	 * getChoices
	 * Returns the number of options for the numeric
	 * question for the survey
	 * @return The number of choices used in the numeric question of the survey
	 */
	public int getChoices()
	{
		return this.choices;
	}
	
	/**
	 * getQuestionOne
	 * Returns the text of the first question
	 * @return The text of the first question of the survey
	 */
	public String getQuestionOne()
	{
		return this.questionOne;
	}
	
	/**
	 * getQuestionTwo
	 * Returns the text of the second question
	 * @return The text of the second question of the survey
	 */	
	public String getQuestionTwo()
	{
		return this.questionTwo;
	}
	
	/**
	 * getQuestionThree
	 * Returns the text of the third question
	 * @return The text of the third question of the survey
	 */
	public String getQuestionThree()
	{
		return this.questionThree;
	}
}
