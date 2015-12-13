# Setup #

  1. Download [Apache Software Foundation](http://tree-buildingsurvey.googlecode.com/files/TBS%20Apache%20Software%20Foundation.zip) to your C:\ drive
  1. Open Eclipse
  1. Window > Preferences
  1. Server > Runtime Environments
  1. Click 'Add'
  1. Apache > Apache Tomcat v6.0
  1. Enter "C:\Apache Software Foundation\Tomcat 6.0" for "Tomcat Installation directory"
  1. Click Finish
  1. Window > Show View > Servers
  1. Right-Click inside the Servers view and select "New"
  1. Apache > Apache Tomcat v6.0
  1. Click Finish

> Note: This .zip file that I have loaded on to the "Downloads" tab has everything already setup for deploying Servlets & Perl CGI Scripts (see ..\Apache Software Foundation\Tomcat 6.0\conf\web.xml) as well as ODBC connections for Oracle, MySQL, & HSQL databases (see ..\Apache Software Foundation\Tomcat 6.0\conf\context.xml)