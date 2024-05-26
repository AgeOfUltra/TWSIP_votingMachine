<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Online Voting System Project</title>
</head>
<body>

<h1>Online Voting System Project</h1>

<p>This project was completed during an internship at TopperWorld. It implements a secure and efficient online voting system using Java and JavaFX, integrated with a MySQL database.</p>

<h2>Project Overview</h2>
<ul>
    <li><strong>Design and Planning</strong>: Plan the overall architecture of the online voting system, including the database schema and user interface design. Define the functionalities and features required for user authentication, candidate registration, voting, and result computation.</li>
    <li><strong>User Authentication and Registration</strong>: Implement a user registration system with proper validation and security measures. Set up user authentication using secure login credentials.</li>
    <li><strong>Candidate Registration and Ballot Creation</strong>: Develop a candidate registration module where candidates can enter their details for each election. Create electronic ballots for each election, listing the registered candidates.</li>
    <li><strong>Vote Casting</strong>: Implement the vote casting mechanism, ensuring that each user can cast only one vote per election. Ensure vote privacy and security during transmission and storage.</li>
    <li><strong>Vote Counting and Result Computation</strong>: Implement a vote counting algorithm to tally the votes for each candidate in each election. Determine the election results based on the vote count.</li>
</ul>

<h2>Setup Instructions</h2>
<h3>Prerequisites</h3>
<ul>
    <li>JDK (Java Development Kit)</li>
    <li>JavaFX SDK</li>
    <li>MySQL Database</li>
    <li>Integrated Development Environment (IDE) or text editor</li>
</ul>

<h3>Database Setup</h3>
<p>I have provided the sql required file for the setup of the database(located in the resource folder) download and execute it in the MySql Software.</p>

<h3>Project Setup</h3>
<ol>
    <li>Set up a new Java project using an Integrated Development Environment (IDE) or a text editor and compile your Java code.</li>
    <li>Create User Interface: Develop a GUI for the online voting system that includes user registration, candidate registration, voting, and result computation functionalities.</li>
    <li>User Authentication: Implement a mechanism to prompt the user for their username and password to authenticate them before allowing access to the voting functionalities.</li>
    <li>Candidate Registration: Design a module for admin users to register candidates for each election.</li>
    <li>Vote Casting: Write code to allow users to cast their votes for candidates in different elections. Ensure that each user can cast only one vote per election.</li>
    <li>Vote Counting: Implement a vote counting algorithm to tally the votes and compute the results for each election.</li>
    <li>Security Measures: Implement code to validate user credentials and ensure secure transmission and storage of votes.</li>
</ol>

<h2>Features</h2>
<ul>
    <li>User Registration and Authentication</li>
    <li>Candidate Registration</li>
    <li>Electronic Ballot Creation</li>
    <li>Secure Vote Casting</li>
    <li>Vote Counting and Result Computation</li>
  <li>Admin Login</li>
  <li>Admin Managment like adding election, candidates, deleting candidates,status changing,..etc</li>
</ul>

<h2>Usage</h2>
<p>Run the project using your IDE. The online voting system GUI will prompt you to register or log in. Once authenticated, you can register candidates (if you are an admin), cast your vote, and view the election results.</p>

<hr>
<p>I have already created a the jar file of the application (located at OUTPUT folder ) download and setup the database in local machine and run it!</p>
</body>
</html>
