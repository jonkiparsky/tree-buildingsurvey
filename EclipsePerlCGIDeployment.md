# Setup #

  1. Open Eclipse
  1. File > New > Project
  1. Web > Dynamic Web Project
  1. Create a name for the project and click "Finish"
  1. Open `WebContent\WEB-INF\web.xml` inside the project just created
  1. Add the following:
```
  <servlet>
       <servlet-name>cgi</servlet-name>
       <servlet-class>org.apache.catalina.servlets.CGIServlet</servlet-class>
       <init-param>
         <param-name>debug</param-name>
         <param-value>6</param-value>
       </init-param>
       <init-param>
         <param-name>cgiPathPrefix</param-name>
         <param-value>WEB-INF/cgi</param-value>
       </init-param>
        <init-param>
          <param-name>passShellEnvironment</param-name>
          <param-value>true</param-value>
        </init-param>
       <load-on-startup>5</load-on-startup>
   </servlet>
   <servlet-mapping>
       <servlet-name>cgi</servlet-name>
       <url-pattern>/cgi-bin/*</url-pattern>
   </servlet-mapping>
```
  1. Save `web.xml`
  1. Go to `WebContent\META-INF` and create a new file `context.xml`
  1. Add the following:
```
  <Context privileged="true">
  </Context>
```
  1. Create the folder `WebContent\WEB-INF\cgi` and place a copy `TBSTestSurvey.pl` inside that folder
    * This file will be located within the `\documents\Perl_Scripts` folder in the Applet project you checked out from SVN earlier in our [Subclipse](Subclipse_Setup.md) setup
  1. Update `TBSTestSurvey.pl'
    1. put a `#` at the beginning of line 11 (`$jar_loc = "http://$ENV{'HTTP_HOST'}/Test/TBSRun.jar";`)
    1. remove the '#' at the beginning of line 12 (`#$jar_loc = "http://localhost:8080/PhylogenySurveyWeb/TBSRun.jar";`)
    1. update `PhylogenySurveyWeb` in line 12 with whatever the name of your web project is that you are deploying on your local server
  1. Use the [Command Line Instructions](BuildInstructions.md) to build a JAR file
  1. Copy and paste that JAR file from the `\documents\Applet_Testing` folder of the Applet project and into the `WebContent` folder
  1. Right-click the server that we created earlier in our [Apache Setup](ApacheEclipseSetup.md) and add this project to it
  1. Right-click that server again and click 'Start'
  1. You should be able to view the script at the url `http://localhost:9080/[Project Name]/cgi-bin/TBSTestSurvey.pl`

