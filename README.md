Class Management App

This application is designed to help teachers in attendances record, assessments records, report generation, student performance statistics, AI integrations etc.

Tools you need:

- Database: MySQL, MariaDB

- IDE: Any java IDE such as intellij, netbeans, eclipse ...

- Postman

- Internet connection


    Running Client Project
---------------------------------------

-> Client project, the client is developed using legacy Java Swing library.

In order to start the client here are the steps to make sure it runs smoothly:

1. Open the "ClassManagementAppClient/" folder

2. Open the tool that helps you manage MySQL, such as MySQL Workbench, or any other tool because the next steps requires to run the scripts for database initialization. 

3. Navigate to -> SQL Stuffs/SQL Scripts/

4. Run these 2 SQL Scripts in your preffered mysql tool, as these scripts are essential in database initialization. ["Create Table.sql","User Teacher Permissions.sql" and "classmanagementappclient_seed.sql"]

5. You can start the project. (it's preffered but not mandatory to start the client project in netbeans IDE, beacuse of UI forms or pallete).

6. You may need to activate KGradientPanel in your Swing Containers, if u don't have it.
As you add this Swing Container component, u may encounter the issue where to find KGradientPanel jar file, so don't worry about it as u can find it right in "Application Files/lib/", so u can use that jar file as the reference.



Understanding the necessary files in the client project
-----------------------------------------------------------

From the root folder, we have "Application Files/" folder, inside the folder there's many folders that has some essential properties that are used to drive the project's UI.

1. Application Files/App Audios, contains the project's audios.

2. Application Files/App Icons, contains all the icons used in the project.

3. Application Files/Properties, contains the paths of the app's icons, audios and other files, contains the DbProperties, contains the security properties, contains the teacherInformation etc.

4. Application Files/Serializable Objects, contains the LoggedInTeacherObject that is serializable. So that the app keeps tracks of the logged in teacher even if he closes it entirely.

5. Application Files/UI Properties, contains properties that helps the app to keep track of the UI state.

6.  Application Files/Web Properties, contains properties that helps the client app to know where to find it's backend ex it's URL and other properties such as request timeout duration.



    Running Server-Side Project
---------------------------------------


-> Server-Side project, the server is developed Spring Framework, for better information u can read the 'pom.xml' file inside "ClassManagementAppServer/".

In order to start the server here are the steps to make sure it runs smoothly:

1. Open the ClassManagementAppServer folder

2. Open the tool that helps you manage MySQL, such as MySQL Workbench, or any other tool because the next steps requires to run the scripts for database initialization. 

3. Navigate to -> sql/

4. Run these 3 SQL Scripts in your preffered mysql tool, as these scripts are essential in database initialization. ["create.sql", "user permission class management app server.sql" and "classmanagementserver_seed.sql"]

5. Create a ".env" file, in the root folder.

6. You will need to create the email sender using google, so that it manages sending emails via Gmail for the users. 

Here are the steps to follow:

- Go to manage your account (Gmail)

- Go to Security

- Enable 2 step verification (normally it's 'off' make it 'on')

- Open your browser and follow link: myaccount.google.com/apppasswords

- Enter the app's name (It's preffered to enter a name similar to the app's purpose example classManagementAppServer)

- Click create

- Then u'll get the app's password

- Keep the password somewhere we gonna need it in the steps below.

7. Go to https://aistudio.google.com/api-keys, here u'll create an API Key. [Keep this API key somewhere we gonna need it in the steps below.]

8. Create a ".env" file inside the root of the project. [ClassManagementAppServer/]

9. Open the file and add the following key-value pairs inside the file:

spring.application.admin.phone-number=[Put here the phone number u use in money transfering, note that the model for monet transffering isn't yet developed]
# example spring.application.admin.phone-number=+257788888888


spring.application.admin.gmail=[Put here the Gmail u used on step 6]
# example, spring.application.admin.gmail=ben1000@gmail.com

spring.ai.google.genai.api-key=[Put here the api key from google that u kept on step 7]
# example, spring.ai.google.genai.api-key=dghjkjd.5%A.oijnx

spring.mail.password=[Put here the password u got on step 6]
# example, spring.mail.password=dghjkjd.5%A.oijnx

10. You can start the project.

11. As an admin, u can access the web application part by opening the browser and type: [http://localhost:8081/].

- 8081, is default port running on the server.

- The credentials that u'll use must be associated to the user who has the ADMIN ROLE



    How can I add my custom data to the AI model ?
----------------------------------------------------------

1. Navigate inside ClassManagementAppServer/src/main/resources/templates/spring_ai/vector_source

2. Inside the folder u'll find the files that has examples on how u'll write your own custom files, so u can take them as reference and write your own files.

3. Navigate to ClassManagementAppServer/src/main/java/com/example/classManagementAppServer/service/rest/ai/VectorStoreService.java

- U'll find this line [Number 21] -> @Value("classpath:templates/spring_ai/vector_source/intro.st")

- Instead of "intro.st" u'll put there the file name of the created on step 2, so it'll be like @Value("classpath:templates/spring_ai/vector_source/my-created-file-name.st")

- Check on line 35, u'll find like -> document.getMetadata().put("id","intro" + id++);

- Instead of "intro",  find another custom easy to remember unique word to replace with, this word can help u if u want to delete this data u inputted.

4. Now let's open postman application.

- Choose the method as POST

- On the authorization tab select "no auth"

- On the body tab, navigate to "raw". Then enter the following JSON:

{
    "secretKey":"2002"
}

- Note that "2002" is the default password that is set via "application.yml", so u can still change it no problem.

- Enter the URL as: http://localhost:8081/developer/ai/data

- Then click send.

- And boom, u can now ask the AI model what u've actually populated in your "custom-data.st" file and it'll respond as an expert.


    How can I delete data from the AI model ?
---------------------------------------------------

- There's no endpoint to do this. But by navigating to MariDB CLI u can do it easily.

- Select the database of the app.

- Then run -> "SELECT id, metadata FROM vector_store;" -- [Don't incude the quotstion marks please]

- There you will see your custom "id" that u set during the data insertion, u'll find it inside the metadata JSON.

- Then copy the associated id [on the left side].

- Then run -> "DELETE FROM vector_store WHERE id='your-copied-id-here';" -- [Don't incude the quotstion marks please]

- And boom, u can now ask the AI model what u've actually deleted, and it'll be lost at all.

N.B
------
- This project was for learning purposes, hence some implementetion may not be production ready, as I'm still a junior developer who still seek for real world expirence.

- The methods used in this project maybe outdated in future as we know that tech evolves so much, as the project is done 2025-2026.

You can contact the developer:
* Gmail: shimwabenjamin1@gmail.com
* whatsapp: +250780270994

Job status: I'm currently unemployed and I'm open for job.