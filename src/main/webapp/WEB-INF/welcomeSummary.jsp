<!DOCTYPE html>
<html>

    <%@include file="header.jsp" %>

    <body>

        <%@include file="jumbotron.jsp" %>

        <div class="container">
            <h2>Your survey, your questions</h2>
            <p>SurveyBin<sup>&reg;</sup> allows you and your team to quickly and
            easily create and administer simple, customised, annonymous surveys
            on any topic of your choosing.</p>
            <p>Surveys created using SurveyBin have a simple,
            customisable format consisting of an introductory text section,
            a "likert scale" style question with five selectable options
            as well as numerical and open response style questions.</p>
            <h2>SurveyBin Summary Data</h2>
            <p>The following surveys have been created during this session:</p>
            <div class="table-responsive">
                <table class="table table-bordered">
                    <th>ITEM</th><th>VALUE</th>
            		<tr>
            		    <td>Total # of surveys that were created in this session</td>
            		    <td>
            		        <%= assignmentTwo.DataStore.getInstance().getSurveysInSession(session.getId()).size() %>
            		    </td>
            		</tr>
            		<tr>
            		    <td>Total # of responses to surveys submitted this session</td>
            		    <td><%= assignmentTwo.DataStore.getInstance().getResponsesInSession(session.getId()).size() %></td>
            		</tr>
            		<tr>
            		    <td>Survey pages</td>
            		    <td><%= assignmentTwo.SurveyUtilities.buildSurveyList(session.getId()) %></td>
            		</tr>
            		<tr>
            		    <td>Survey results pages</td>
            		    <td><%= assignmentTwo.SurveyUtilities.buildResultsList(session.getId()) %></td>
            		</tr>
            	</table>
            </div>
        </div>

        <%@include file="footer.jsp" %>

    </body>
</html>
