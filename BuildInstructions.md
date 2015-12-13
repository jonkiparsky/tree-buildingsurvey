## Any Questions\Problems Please e-mail: andrew.schonfeld@bbh.com ##
# Instructions For Builder #

Follow these steps to build our applications:
  1. Download Java's [JDK 6](http://java.sun.com/javase/downloads/index.jsp)
  1. add environment variable 'JAVA\_HOME' with location of folder that begins with 'jdk'
    * Mine on windows is 'C:\Program Files\Sun\jdk1.6.0\_16'
  1. add the following entry to environment variable, 'Path':
    * Windows: ;%JAVA\_HOME%\bin
    * Unix: ;%JAVA\_HOME%/bin
  1. Download [Ant](http://ant.apache.org/bindownload.cgi)
  1. add environment variable 'ANT\_HOME' with location of folder that begins with 'ant'
    * Mine on windows is 'C:\apache-ant-1.8.0'
  1. add the following entry to environment variable, 'Path':
    * Windows: ;%ANT\_HOME%\bin
    * Unix: ;%ANT\_HOME%/bin
  1. Download [Subversion](http://subversion.apache.org/packages.html)
    * If your downloading for Windows I prefer the `tigris.org` version, this is an .msi file so it should do all the installation automatically
  1. add environment variable 'SUBVERSION\_HOME' with location of folder that begins with 'Subversion'
    * Mine on windows is 'C:\Program Files\Subversion'
  1. add the following entry to environment variable, 'Path':
    * Windows: ;%SUBVERSION\_HOME%\bin
    * Unix: ;%SUBVERSION\_HOME%/bin
  1. Download [Eclipse](http://www.eclipse.org/downloads/download.php?file=/eclipse/downloads/drops/R-3.5.2-201002111343/eclipse-SDK-3.5.2-win32.zip)
  1. Install [subclipse](Subclipse_Setup.md)(the subversion plugin for eclipse)
  1. [Check out our projects from SVN](CheckingOutCode.md)(Applet & Admin Application)

# Compiling and Running Your Eclipse Projects From Command-Line #
  1. from the command line `cd` into the folder that contains you eclipse workspace
    * mine is `C:\Workspace`
  1. `cd` into the folder for either of your two projects
  1. run `ant jarfile`
    * The resulting jarfile will be located in this current location that you are running ant from
  1. here are the commands to run the new JAR files
    * the two versions of the Applet
      * Student Applet: `ant run_student`
      * Administrator Applet: `ant run_admin`
    * the Admin Application
      * `ant run`

# Creating A Local Server and Running Applet From It w/ Eclipse & Apache #
Follow the instructions in these two Wiki pages:
  1. [Apache Tomcat Eclipse Setup Instructions](ApacheEclipseSetup.md)
  1. [Eclipse Perl CGI Deployment Instructions](EclipsePerlCGIDeployment.md)

# Installing MySQL & Creating Local Database #
  1. Download and install [MySQL](http://dev.mysql.com/downloads/mysql/)
  1. Once the download is complete, open up MySQL Command Line Client
  1. You will need to enter the root password that you created during the installation
  1. Locate the file 'phylogenySurvey.sql' in the documents folder of the checked out Applet project
    * This will be in the workspace folder of your eclipse projects, mine is C:\workspace
  1. In the command line run the command:
    * `source [location of checked out Applet project]\documents\DB_Setup\phylogenySurvey.sql`
  1. Do the following [Perl MySQL DBI](PerlMySQLDBI.md) setup
  1. There will be two other files within the documents folder from two steps earlier of the extension `.pm`, drop these two files into the `WebContent\WEB-INF\cgi\' for the Local Server project you created earlier
  * **Now your database should be all set for running a local version of the Applet complete with saving!**