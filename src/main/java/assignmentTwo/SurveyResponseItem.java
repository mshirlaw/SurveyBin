package assignmentTwo;

public class SurveyResponseItem extends SurveyItem
{
	private String ipAddress;
	private String hostAddress;
	private String responseDate;
	
	/**
	 * 
	 */
	public SurveyResponseItem()
	{
		super();
	}
	
	/**
	 * 
	 * @param ip
	 */
	public void setIpAddress(String ip)
	{
		this.ipAddress = ip;
	}
	
	/**
	 * 
	 */
	public String getIpAddress()
	{
		return this.ipAddress;
	}
	
	/**
	 * 
	 * @param host
	 */
	public void setHostAddress(String host)
	{
		this.hostAddress = host;
	}
	
	/**
	 * 
	 * @return
	 */
	public String getHostAddress()
	{
		return this.hostAddress;
	}
	
	/**
	 * 
	 * @param d
	 */
	public void setResponseDate(String d)
	{
		this.responseDate=d;
	}

    /**
     *
     * @return
     */
	public String getResponseDate()
	{
		return this.responseDate;
	}
}
