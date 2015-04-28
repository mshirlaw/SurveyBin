#SurveyBin
#####Created by: Matthew Shirlaw [220019815]

This project is a web application that allows users to create and administer anonymous surveys.

The application employs a derby database using JDBC to store all survey data so that if the server is
restarted all of the data will be retained between sessions.

The home page for the application lists links to the survey pages and results pages for surveys that
were created in the current session. The results page for each survey contains a summary of the results in HTML format
as well as:

1. A URL from which the survey results data can be downloaded in JSON format.
2. A URL from which the survey results data can be downloaded in CSV format.
3. A URL which will produce the survey results data in a format that depends on the value of the Accept header.

###Dependencies

This project has the following dependencies:

1. The JSON simple library - for creating JSON formatted data
2. Open CSV - for creating CSV formatted data
3. Apache Derby - for JDBC database
4. JUNIT - for unit tests
5. Bootstrap - for CSS styling

Each of these dependencies are all handled by gradle.

###Building the application

To build the application:

```
cd SurveyBin
```

```
gradle appRun
```

###Structure of source code

Java code for the application is included in:

```
/SurveyBin/src/main/java/assignmentTwo
```

JSP code is included in:

```
/SurveyBin/src/main/webapp/WEB-INF
```

UML style class diagrams for the application are included in:

```
/SurveyBin/docs
```

###Navigation

The home page for the application can be accessed by visiting:

```
localhost:8080/SurveyBin
```