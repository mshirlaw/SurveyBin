<!DOCTYPE html>
<html>

  <%@include file="header.jsp" %>

    <body>

        <%@include file="jumbotron.jsp" %>

        <div class="container">
            <div class="table-responsive">
                <table class="table table-bordered">
                    <th>ITEM</th><th>VALUE</th>
                    <tr>
                        <td>Survey Name</td>
                        <td>
                            <%= assignmentTwo.DataStore.getInstance().getSurveyForm(request.getParameter("surveyID")).getTitle() %>
                        </td>
                    </tr>
                    <tr>
                        <td>IPs Addresses</td>
                        <td>
                            <%= assignmentTwo.SurveyUtilities.createIpValues(request.getParameter("surveyID")) %>
                        </td>
                    </tr>
                    <tr>
                        <td>Time completed</td>
                        <td>
                            <%= assignmentTwo.SurveyUtilities.createDateValues(request.getParameter("surveyID")) %>
                        </td>
                    </tr>
                    <tr>
                        <td>Question 1</td>
                        <td>
                            <%= assignmentTwo.SurveyUtilities.createQuestionOneValues(request.getParameter("surveyID")) %>
                        </td>
                    </tr>
                    <tr>
                        <td>Question 2</td>
                        <td>
                            <%= assignmentTwo.SurveyUtilities.createQuestionTwoValues(request.getParameter("surveyID")) %>
                        </td>
                    </tr>
                    <tr>
                        <td>Question 3</td>
                        <td>
                            <%= assignmentTwo.SurveyUtilities.createQuestionThreeValues(request.getParameter("surveyID")) %>
                        </td>
                    </tr>
                </table>
            </div>
            <div id="links">
                <%= assignmentTwo.SurveyUtilities.buildAPILinks(request.getParameter("surveyID")) %>
            </div>
        </div>

        <%@include file="footer.jsp" %>

    </body>
</html>







