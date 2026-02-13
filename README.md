# Wikipedia Revision Reporter

## Project Summary
This application is an interactive software tool designed for investigative journalists to track Wikipedia article edit
histories. It allows users to input a Wikipedia article title and retrieves the 15 most recent revisions, displaying
them in reverse-chronological order . The reporter also identifies and displays article redirections (e.g., searching
for "Obama" redirects to "Barack Obama") and handles various error states like missing pages or network failures .

## Team Members

- Daniel Zhang
- Fei Ye

## Build Instructions
This project uses the Gradle build system and requires Java 11 or newer.
1. Prerequisites
   Java Development Kit (JDK) 11+

IntelliJ IDEA Community Edition (recommended)

2. How to Build and Run
   Clone the repository to your local machine.

Open the project in IntelliJ IDEA.

- To run the application:

- Locate the Main class in src/main/java/org/example/Main.java.

- Run the main method.

## Technical Details

- Architecture: Follows Model-View separation, ensuring that the domain model and user interface remain independent.
- Data Parsing: Utilizes JsonPath for robust extraction of revision data and redirect targets from the Wikipedia API.
- TDD: Developed using Test-Driven Development, with unit tests covering the parser and model logic.
