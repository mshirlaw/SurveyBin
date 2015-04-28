package org.apache.jsp.WEB_002dINF;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;

public final class welcomeSummary_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static final JspFactory _jspxFactory = JspFactory.getDefaultFactory();

  private static java.util.List<String> _jspx_dependants;

  static {
    _jspx_dependants = new java.util.ArrayList<String>(3);
    _jspx_dependants.add("/WEB-INF/header.jsp");
    _jspx_dependants.add("/WEB-INF/jumbotron.jsp");
    _jspx_dependants.add("/WEB-INF/footer.jsp");
  }

  private org.glassfish.jsp.api.ResourceInjector _jspx_resourceInjector;

  public java.util.List<String> getDependants() {
    return _jspx_dependants;
  }

  public void _jspService(HttpServletRequest request, HttpServletResponse response)
        throws java.io.IOException, ServletException {

    PageContext pageContext = null;
    HttpSession session = null;
    ServletContext application = null;
    ServletConfig config = null;
    JspWriter out = null;
    Object page = this;
    JspWriter _jspx_out = null;
    PageContext _jspx_page_context = null;

    try {
      response.setContentType("text/html");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;
      _jspx_resourceInjector = (org.glassfish.jsp.api.ResourceInjector) application.getAttribute("com.sun.appserv.jsp.resource.injector");

      out.write("<!DOCTYPE html>\n");
      out.write("<html>\n");
      out.write("\n");
      out.write("    ");
      out.write("<head>\n");
      out.write("    <link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.2.0/css/bootstrap.min.css\">\n");
      out.write("    <link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.2.0/css/bootstrap-theme.min.css\">\n");
      out.write("    <link type=\"text/css\" rel=\"stylesheet\" href=\"main.css\">\n");
      out.write("    <title>Survey Bin | Create a Survey</title>\n");
      out.write("</head>");
      out.write("\n");
      out.write("\n");
      out.write("    <body>\n");
      out.write("\n");
      out.write("        ");
      out.write("<div class=\"jumbotron\">\n");
      out.write("    <div class=\"container\">\n");
      out.write("        <h1><span>Survey</span>Bin</h1>\n");
      out.write("        <h2>Dynamic surveys at the click of a button.</h2>\n");
      out.write("        <p>A simple poll? In-depth market research? We've got you covered.</p>\n");
      out.write("        <div class=\"btn-group\">\n");
      out.write("            <div class=\"btn btn-primary btn-lg\">\n");
      out.write("                <a href=\"SurveyHome\">Home</a>\n");
      out.write("            </div>\n");
      out.write("            <div class=\"btn btn-primary btn-lg\" role=\"button\">\n");
      out.write("                <a href=\"SurveyForm\">Create a Survey</a>\n");
      out.write("            </div>\n");
      out.write("        </div>\n");
      out.write("    </div>\n");
      out.write("</div>");
      out.write("\n");
      out.write("\n");
      out.write("        <div class=\"container\">\n");
      out.write("            <h2>Your survey, your questions</h2>\n");
      out.write("            <p>SurveyBin<sup>&reg;</sup> allows you and your team to quickly and\n");
      out.write("            easily create and administer simple, customised, annonymous surveys\n");
      out.write("            on any topic of your choosing.</p>\n");
      out.write("            <p>Surveys created using SurveyBin have a simple,\n");
      out.write("            customisable format consisting of an introductory text section,\n");
      out.write("            a \"likert scale\" style question with five selectable options\n");
      out.write("            as well as numerical and open response style questions.</p>\n");
      out.write("            <h2>SurveyBin Summary Data</h2>\n");
      out.write("            <p>The following surveys have been created during this session:</p>\n");
      out.write("            <div class=\"table-responsive\">\n");
      out.write("                <table class=\"table table-bordered\">\n");
      out.write("                    <th>ITEM</th><th>VALUE</th>\n");
      out.write("            \t\t<tr>\n");
      out.write("            \t\t    <td>Total # of surveys that were created in this session</td>\n");
      out.write("            \t\t    <td>\n");
      out.write("            \t\t        ");
      out.print( assignmentTwo.DataStore.getInstance().getSurveysInSession(session.getId()).size() );
      out.write("\n");
      out.write("            \t\t    </td>\n");
      out.write("            \t\t</tr>\n");
      out.write("            \t\t<tr>\n");
      out.write("            \t\t    <td>Total # of responses to surveys submitted this session</td>\n");
      out.write("            \t\t    <td>");
      out.print( assignmentTwo.DataStore.getInstance().getResponsesInSession(session.getId()).size() );
      out.write("</td>\n");
      out.write("            \t\t</tr>\n");
      out.write("            \t\t<tr>\n");
      out.write("            \t\t    <td>Survey pages</td>\n");
      out.write("            \t\t    <td>");
      out.print( assignmentTwo.SurveyUtilities.buildSurveyList(session.getId()) );
      out.write("</td>\n");
      out.write("            \t\t</tr>\n");
      out.write("            \t\t<tr>\n");
      out.write("            \t\t    <td>Survey results pages</td>\n");
      out.write("            \t\t    <td>");
      out.print( assignmentTwo.SurveyUtilities.buildResultsList(session.getId()) );
      out.write("</td>\n");
      out.write("            \t\t</tr>\n");
      out.write("            \t</table>\n");
      out.write("            </div>\n");
      out.write("        </div>\n");
      out.write("\n");
      out.write("        ");
      out.write("<div class=\"container\">\n");
      out.write("    <br /><hr />\n");
      out.write("    <address>\n");
      out.write("        Matthew Shirlaw<br />\n");
      out.write("        Assignment 2<br />\n");
      out.write("        COMP391 Advanced Web Technologies\n");
      out.write("    </address>\n");
      out.write("</div>");
      out.write("\n");
      out.write("\n");
      out.write("    </body>\n");
      out.write("</html>\n");
    } catch (Throwable t) {
      if (!(t instanceof SkipPageException)){
        out = _jspx_out;
        if (out != null && out.getBufferSize() != 0)
          out.clearBuffer();
        if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
        else throw new ServletException(t);
      }
    } finally {
      _jspxFactory.releasePageContext(_jspx_page_context);
    }
  }
}
