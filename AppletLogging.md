# Server-side Logging in a Java Applet #

## Objective ##
We have an [applet JAR file](http://cluster.bio.whe.umb.edu/Test/TBSRun.jar) that is accessible from a server, `cluster.bio.whe.umb.edu`.  Clients are able to access this applet through the use of an html `<applet>` tag nested within an html file.  We would like the user's order of operations in creation of their tree to be sent to a `.log` file stored on the server that is hosting the applet.

## Shakedown ##
After much research I have found that it is most certainly possibly to have this type of logging take place with our applet.  Unfortunately, due to the software configuration we have inherited & the lack of flexability we have to alter this configuration, the setup involved in this type of logging is unworkable.

Log4j allows you to create something called a "Socket Server".  This server is simply an open port on your server that will connect directly to your `.log` file for appending information about your application.  This seems easy enough, but unfortunately we would need to get permission to open up a port on the server which would open up the server to possible attacks.  Here is a full description of the setup involved in setting a socket server on unix(ignore the setup relating to SMTP):
  * [Log4j Socket Server Setup](http://timarcher.com/node/10)

Once you have a socket server up and running you should be able to connect to it by adding a `log4j.properties` file to the root directory of the source(specifying the server & the port along with the appender pattern) and using Log4j's `SocketAppender` API. EX:

```
static Logger logger = Logger.getRootLogger();
public static void main(String argv[]) {
  // Try sending logging events through the SocketAppender in the log4j_remote.properties file
  System.out.println("Try sending logs through the SocketAppender in the log4j.properties file");
  PropertyConfigurator.configure("log4j_remote.properties"); //Logs to a SocketAppender; host 127.0.0.1, port 4445
  logger.debug("Hello world");
  logger.info("What a beatiful day.");
  // And try sending logging events through SocketAppender instance
  System.out.println("Try sending logs through a SocketAppender instance");
  SocketAppender sa = new SocketAppender("171.21.241.122", 4445);
  System.out.println("Created SocketAppender instance");
  String logMsg = "Writing log event through SocketAppender instance";
  LoggingEvent le = new LoggingEvent("fox.teststuff.TestLog4J",Logger.getRootLogger(),Level.DEBUG,logMsg, new Throwable());
  sa.append(le);
  sa.close();
}
```

Now this seems easy enough, but unfortunately since we are dealing with applets there may also be security setup involving signing applets or having the user "allow access to trusted website".  I have not yet seen any details about this in my research, but after all the key mashing related to applet security issues with this project I am going to assume it is needed.

In the end, maybe with the blessing of our client as well as our professor we may be able to persue this as an option, but with a huge question mark along side it.

## Other Possible Options ##

One other option that I thought while researching this problem would be as follows:
  1. Create either one long StringBuffer or a Stack of Strings at startup of the applet
  1. Append to this object throughout the lifecycle of the applet
  1. Upon saving of the tree, dump the contents of this object into a hidden `<input>` tag by talking to the `<applet>` tag with JavaScript and saving that huge string to either a database or a file on the server with Perl.

Now in theory this should work.  One thing that will be a problem is if the user decides to put a lot of thought into their tree construction and the StringBuffer/Stack grows so large that the JVM might run out of memory.  Another problem is that a `<hidden>` input tag may not be able to function with an obscenely large string.

So in the end this might be an interesting test, but is defintely not the most elegant of solutions to this problem.