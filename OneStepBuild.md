## One Step Build ##

_The exact information that follows may also be found in `/branches/rewrite/README.txt` in the SVN_<br>

<h3>Deployment Notes:</h3>
<b>Prerequisites:</b><br>
<ol><li>You must go to the google code site, <a href='http://code.google.com/p/tree-buildingsurvey/'>http://code.google.com/p/tree-buildingsurvey/</a>, and download the existing project one of two ways.<br>
<ul><li>A) Download Tortoise SVN and use the directions listed on the "Source" tab of the google code page to checkout the current project<br>
</li><li>B) Go to the "Downloads" tab and download the latest .zip file of the project<br>
</li></ul></li><li>You must have Java 6(or most recent version) installed on your computer<br>
</li><li>You must have Ant 1.7(or latest version) installed on your computer</li></ol>

<h3>Steps for Deployment:</h3>

<ol><li>go to the directory of your local copy of the project in the command line<br>
</li><li>run the command "ant jarfile", this creates your jarfile that executes the applet<br>
</li><li>run the command "ant javadoc", this creates the javadoc to be viewed on the web<br>
</li><li>update the file applet.html with the current version notes (new functions, future functions, removed functions, etc.)<br>
<ul><li>You can do this by adding a some simple text within a <code>&lt;p&gt;</code> tag above the <code>&lt;applet&gt;</code> tag<br>
</li></ul></li><li>run the command,<br>
<ul><li><code>"ant -Dversion=[version number] -Dusername=[server username] -Dpassw=[server password] -Dhostname=[server name] -Dserver_dir= [server directory] deploy"</code>,<br>
<ul><li>this will deploy the application on whichever server you would like to house it<br>
</li></ul></li></ul></li><li>update the wiki links to the updated version on the google code wiki(this step only required for TBS Dev Group members)<br>
<ul><li>"Test Current Tree-Building Applet" link on home page<br>
</li><li>"Test Applets", wiki page (<a href='http://code.google.com/p/tree-buildingsurvey/wiki/Applets'>http://code.google.com/p/tree-buildingsurvey/wiki/Applets</a>)<br>
</li><li>wiki table of contents (<a href='http://code.google.com/p/tree-buildingsurvey/w/edit/TableOfContents'>http://code.google.com/p/tree-buildingsurvey/w/edit/TableOfContents</a>)<br>
</li><li>any changes to README.TXT should be reflected in the wiki page OneStepBuild