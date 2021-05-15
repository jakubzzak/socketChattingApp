local chatting app
===

### Run

Once you have cloned the repository into your local machine, run this command within the root of the folder:

```mvn clean package```

This builds the executable jar file with name "chattingApp-{version}.jar", which can be found in the generated target folder.
Navigate to the target folder and run the application with the following command:

```java -jar chattingApp-{version}.jar```

### Test

To test the app, you can make it run multiple times from different tabs in your terminal.

### So how does it work? 

Well, first we need a machine which is going to host the whole conversation, thus
first person who runs the app can enter arbitrary port. Any other user who wants to connect to this server
needs to provide ip address and port of the server. If there is nobody listening on the entered port,
the app will start new server. If the server exists, anyone from local network can join.

Have a nice chat!