# NateCodingChallenge
1. Create a git repository on GitHub/BitBucket/GitLab with your project including README file. 
2. "Include at least 1 meaningful unit-test (and explain why) on both frontend and backend applications. "
	For my backend application, I created two unit tests. The first one givenNoParam_ProcessedUrlShouldReturnNoContent is to confirm that things work as expected when no url is passed in.
	When no url is passed in, it defaults to trying to access https://www.google.com/foo which will return a 404 error, and therefore no words are parsed and the wordCountMap will be empty;
	
	The second unit test I created for the backend application is bigTextParam_ProcessedUrlShouldReturnWordCountMap. For this unit test, it passes the url https://norvig.com/big.txt for parsing.
	I had been testing with this url so I was able to identify a string that does not appear on the site ("abcdefghijklmnopqrstuvwxyz", so it checks that there is no count for that string.
	Additionally, it checks that the words "the" appears 80030. And more importantly, that the word "isaiah" only appears once (which is easily verifiable with a browser).

3. Make sure the your solution is reproducible: specify dependencies, versions, runtime 

	dependencies should be listed in the Pom files. But I used Maven 3.1.8, Spring boot 2.6.7, Hamcrest, Jsoup 1.14.3, junit, the spring-boot-maven-plugin.
	SDK : openjdk-18 Oracle OpenJDK version 18.0.1

	I used Intellij for my IDE, running on Windows 10. 


4. Describe why you chose your tech stack. 
	I'm familiar with Java, and had some exposure to Spring in the past, but not spring boot or spring 
	I choose to use Spring Boot to help create the web server as it seems to be one of the most popular, usefuly, and flexible framework.
	I used Maven as it makes handling dependencies and building across different environments pretty hassle free. 
	
	
Bonus 
1. Provide test coverage report for your unit tests 
	Test coverage files can be found in the NateClient/coverage-reports and NateServer/coverage-reports folders. 

How to Build
	To build from the command line, make sure java and maven are setup correctly. Navigate to the NateCodingChallenge folder.

	run "mvn clean install" in both the NateServer and NateClient folders.

How to Run
	Then while in the NateServer directory, start the server with the following command "mvn spring-boot:run"
	Then in a separate terminal, navigate to the NateClient directory and run the same command to start the client ("mvn spring-boot:run").
	To have the client read URLs from a file and pass them to the server, run the following command instead : mvn spring-boot:run -Dspring-boot.run.arguments="{FILENAME}"
	
I dealt with a lot of firsts while working on this project; first time using Intellij, spring-boot, and all my code being deleted while attempting to get into gitHub.
Additionally, I kept noticing a problem with certain characters being displayed in the console; it seemed to be a character encoding issue. If the word count map is written to a file, and then opened
with notepad all the letters appear correctly. I spent a good deal of time trying to resolve the issue but ultimately gave up due to time constraints (and that they were fine when written to a file).
I had even tried changing the encoding of the Intellij console output but to no avail.


