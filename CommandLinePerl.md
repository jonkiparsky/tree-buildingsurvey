# Introduction #
  1. Open Eclipse
  1. Run > External Tools > External Tools...
  1. Select "Program" from the Configurations box on the left
  1. Click "New"
  1. Enter `perl -w` in the name field, unless you have a more catchy name
  1. Enter the location of your command line executable(ex: `C:\WINDOWS\system32\cmd.exe` in XP) in the Location box
  1. Enter the following in the Arguments box:
```
  /C "cd ${container_loc} && perl -w ${resource_name}" 
```
  1. Click Apply
  1. Go to the "Common" tab and for the `perl -w` configuration and click the `External Tools` checkbox  in the `Display in favorites menu`
  1. Click Apply & hit OK
  1. Now select your Perl script (`TreeSurvey.pl`) and go to Run > External Tools > `perl -w`

> After doing this you should see the output from your Perl script in the Console view.  In This case since ours is a CGI script our output will be mostly HTML code.  This is good from running quickly with debug statements to see where possible errors are occuring.
