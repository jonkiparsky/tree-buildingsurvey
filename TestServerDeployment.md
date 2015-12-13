# Important: cluster.bio.whe.umb.edu is no longer operational #

**Replace any references to cluster.bio.whe.umb.edu with a working URL**

> Before doing anything make sure that the follwing lines of code have been updated in the perl script that you will be deploying or else errors will occur.
  1. `$script_url = "http://cluster.bio.whe.umb.edu/cgi-bin/Test/TreeSurvey.pl"`
  1. `print "archive=\"http://cluster.bio.whe.umb.edu/Test/phylogenySurvey.jar\" \n";`

# Steps #
  1. Download [pscp](http://www.chiark.greenend.org.uk/~sgtatham/putty/download.html) to `C:\`
  1. Open up the command-line and goto `C:\`
  1. To send your .pl file to the server, use the following: `pscp [location of perl script]\TreeSruvey.pl root@cluster.bio.whe.umb.edu:/var/www/cgi-bin/Test/`
  1. Enter the password.
  1. Do the same for the .jar file: `pscp [location of .jar]\phylogenySurvey.jar root@cluster.bio.whe.umb.edu:/var/www/html/Test/`
  1. Download [putty](http://www.chiark.greenend.org.uk/~sgtatham/putty/download.html)
  1. Go to server, cluster.bio.whe.umb.edu
  1. use (User:root, Password:)
  1. Type the following commands:
  * `cd /var/www/cgi-bin/Test/`
  * `chmod +x TreeSurvey.pl`
  * `cd /var/www/html/Test/`
  * `chown nobody:nobody phylogenySurvey.jar`
  * `chmod +x phylogenySurvey.jar`

(password available from Jon or Andrew)

> Now You should be able to access your updated code from the following link http://cluster.bio.whe.umb.edu/cgi-bin/Test/TreeSurvey.pl

_Note: These steps could be subject to change if we move from using a perl CGI script to using servlets or JSF_