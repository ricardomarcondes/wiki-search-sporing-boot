# wiki-search-sporing-boot

Problem:
1. Write a tool which would ask user to input a query, take command line input from user, then search Wikipedia for this query and present user with titles and links to 3 most relevant articles.

2. To the same tool, add something to clean / normalize query – i.e., deal with case, punctuation, etc.

3. Make the same tool multi-threaded. I.e., ask user to input 5 queries, and then process them all at once and in parallel.

4. Add http interface to the tool. I.e., instead of command line make it listen for REST http calls.

Instructions:
1. Download and unzip the file
2. Open terminal and change to dir where pom.xml is located
3. Run the command: mvn clean install
4. Run the command: mvn spring-boot:run
5. In the browser paste this url: http://localhost:8080/wikisearch/multi?query=Toronto&query=Trump&query=Brazil&query=EU&query=USA&query=Apple&query=Microsoft&query=Ontario
6. Verify the wiki search results.



