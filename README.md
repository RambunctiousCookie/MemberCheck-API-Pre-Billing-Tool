# AppAML
   - **Goals**
      - To produce a re-scoped application within an accelerated timeframe which can offer pre-billing insights.
      - To reduce as much friction as possible given the accelerated timeframe (do not require billing staff to learn to use a new database).
      - Implement two functions which will assist in the pre-billing process by computing data fetched from the MemberCheck API.
   - **Current Progress**
      - Backend functions tested.
      - Frontend CLI connected.
      - Database not implemented.
   - **Caveats**
      - Use Case 3 may contain bugs, as I no longer have access to the API to test it with
      - Use Case 4 could not be implemented as the data is parsed as a `List<JsonElement>`, and no longer have credential access to retrieve the structure from the API endpoint

## Requirements

Before you can run the AppAML application, make sure you have the following requirements installed on your system:

0. **Clone this Repository**
   - Click on the green button in the top right-hand corner called `code` and then `download zip`.
   - You may also clone using HTTPS or SSH depending on your technical preference.
   - Place the extracted zip somewhere in your directory where you will remember.
     - For me, this is `D:\WS\VsCode\RTTbilling`.

2. **Visual Studio Code (VSCode):**
   - You can download and install VSCode from the official website: [VSCode Download](https://code.visualstudio.com/).
   - Follow the installation instructions for your specific operating system.

3. **Java:**
   - You'll need Java to run the application. Ensure that you have Java installed on your system.
   - If not, you can download it from the official Oracle website: [Java Downloads](https://www.oracle.com/java/technologies/javase-downloads.html).
   - Please download `JDK-17`
   - Make sure to add Java to your system's PATH or classpath so that the application can locate it.
     - Go to search and type in "Env" or "Environment Variables".
       <div style="text-align: center;"><img src="https://media.geeksforgeeks.org/wp-content/uploads/20201116195922/Screenshot20201116at71805PM.png" alt="image_tutorial" width="300"></div>
     - Click on New under System Variables (NOT User Variables).
       <div style="text-align: center;"><img src="https://media.geeksforgeeks.org/wp-content/uploads/20201116195936/Screenshot20201116at71849PM.png" alt="image_tutorial" width="300"></div>
     - Add CLASSPATH as the variable name and the path of files as the variable value.
       <div style="text-align: center;"><img src="https://media.geeksforgeeks.org/wp-content/uploads/20201116200006/Screenshot20201116at74509PM.png" alt="image_tutorial" width="300"></div>
     - Click [OK] to confirm.

4. **Maven:**
   - Maven is used for building and managing the project.
   - You need to have Maven installed on your system. You can download Maven from the official website: [Maven Download](https://maven.apache.org/download.cgi).
   - Similar to Java, add Maven to your system's PATH or classpath for easy access.
   - You can confirm whether this worked or not by opening `cmd.exe` and typing `mvn -version` to check if it has been successfully added to path.
     - If installation is successful, your command-line output will look similar to the following:
     - `Apache Maven 3.9.5 (57804ffe001d7215b5e7bcb531cf83df38f93546) ...`

## Installation and Running

Follow these steps to install and run the AppAML application:

1. **Rebuild the Project with Maven:**

   - Open the project in VScode.
   - Click on the top tooltip `Terminal > New Terminal`.
   - Verify that you are in the location where you have cloned the project during the Requirements stage above.
     - VsCode should automatically change the directory for you.
       - For me, this is `D:\WS\VsCode\RTTbilling`. It may differ for you.
     - If you are not in the correct directory, simply pass the command `cd ...` where `...` is the location of the cloned project.
       - For example, `cd D:\WS\VsCode\RTTbilling`
   - Type `mvn clean install` and execute by pressing enter.
   - If successful, you should see something similar to the following output:
      ```shell
        ...
       [INFO] Installing D:\WS\VsCode\RTTbilling\target\AppAML-1.0-SNAPSHOT.jar to C:\Users\...\.m2\repository\org\example\AppAML\1.0-SNAPSHOT\AppAML-1.0-SNAPSHOT.jar
       [INFO] ------------------------------------------------------------------------
       [INFO] BUILD SUCCESS
       [INFO] ------------------------------------------------------------------------
       [INFO] Total time:  5.470 s
       [INFO] Finished at: 2023-11-03T12:36:26+08:00
       [INFO] ------------------------------------------------------------------------
      ```
   
  2. **Running the Project:**
     
      - You can now run the project by pressing the play icon at the top right hand corner of the VsCode UI `Play > Run Java` or `Play > Debug Java`
      - This will bring you to the first screen in the Command Line (see below).
   
## Proper Usage of the Project:

### Enter API Key Menu
   ```shell
   ===Welcome===
   This is a preliminary Command-Line Interface for the Pre-Billing Application. To Use, Please Enter Your API Key.
           - Your API key can be obtained from the MemberCheck portal, under your profile.
           - Depending on your Command-Line Interface, you might need to press CTRL+V or RIGHT-CLICK to paste your key in.
   Enter your key:
   ```
   - The API Key entered has no cloud interaction.
   - It exists in volatile memory to reduce security breach vectors.
   - Implementation might need to be changed upon adoption of a web frameworks (see design documents in Lupl).
     
### Choose RTT Org Node Menu
   ```shell
   ===Choose RTT Org Node===
   In order to access billing data, the program will need guidance on which Org_Id is directly above the client organizations.
   Please enter the number that corresponds to HOW FAR FROM THE ROOT the nearest RTT organization occupies above the client organizations. Here are some examples:
   
   - RTT Org -> [0]
      - Client1
      - Client1
   //[0] represents the root. Here, you would enter [0] since RTT Org is at the root AND closest to client-level.
   
   - RTT Org -> [0]
      - RTT SubOrg -> [1]
         - Client1
         - Client2
   //[0] represents the root, and [1] represents 1 level away from root. Here, you would enter [1] since RTT SubOrg is 1 level away from the root AND is closest to client-level.
   
   - RTT Org -> [0]
      - RTT SubOrg -> [1]
         - RTT Sub-SubOrg -> [2]
            - Client1
            - Client2
   //[0] represents the root, and [2] represents 2 levels away from root. Here, you would enter [2] since RTT Sub-SubOrg is 2 levels away from the root AND is closest to client-level.
   
   I will now print the tree so that you can choose which level to enter.
   ```
   - Required for the program to correctly choose the Top-Level (TL) Organization IDs for the clients.
   - Implementation will always return the first node at the selected level implemented.
      - Based on current structure for `Retail` and `Wholesale` accounts, this will work
      - Will need to be updated in the future to parse a specific Org_Id if there are to be multiple RTT SubOrgs- for example something like this **would not work** with the current setup:
         ```shell
         - RTT Org 
            - RTT SubOrgA
               - Client1
               - Client2
            - RTT SubOrgB
               - Client3
               - Client4
         ```
### Main Menu
   ```shell
   ===Main Menu===
   To proceed, please type the number representing your choice and press enter.
   [1] Review Organizational Tree (Top-Level Nodes, RTT Node)
   [2] Get Quarterly Billing Statistics (Top-Level Nodes, Respective Scan Usage (Incl. Sub-Orgs))
   [3] Get Contract Renewal Statistics (Top-Level Nodes, Respective Monitoring Scans which are CURRENTLY TURNED ON (Incl. Sub-Orgs)))
   [99] Quit
   Enter your choice: 
   ```
   - User will only be admitted to Main Menu once `Api Key` and `RTT Org Node Selection` are complete.
   - This is because both fields are necessary in order to carry out each of the use cases.
   - [1] returns user to update the `RTT Org Node Selection` in case they made any mistakes.

### Get Quarterly Billing Statistics Menu
   ```shell
   ===Menu 2: Get Quarterly Billing Statistics (Top-Level Nodes, Respective Scan Usage (Incl. Sub-Orgs))===
   If you wish to exit this menu and return to the main menu, please enter [99] twice.
   Otherwise, to retrieve the quarterly billing statistics, follow these instructions:
      - Check [config.yaml] in [src/main/resources] and ensure that the various start and end dates for each quarter are correct. Otherwise, data retrieval would not be over the correct period.
      - First enter your desired YEAR (YYYY format), followed by your desired QUARTER (1-4)
   Enter desired YEAR (YYYY format): 2023
   Enter desired QUARTER (1-4): 1
   ```
   - Enter the required year and quarter.
   - Use `src/main/resources/config.yaml` to configure the start and end dates for each quarter.
      - This is left to the due diligence of the user. 
   - Outputs the billing details
      - Prints to the Command Line
      - Updates a file `src/main/resources/2_QuarterlyBilling/Output.csv`

### Get Contract Renewal Statistics Menu
   ```shell
   ===Menu 3: Get Contract Renewal Statistics (Top-Level Nodes, Respective Monitoring Scans which are CURRENTLY TURNED ON (Incl. Sub-Orgs))===
   BEFORE beginning, please check the following path:
   src/main/resources/3_ContractRenewal/Input.csv
       You must FIRST update this CSV file with all ongoing contracts for this function to work.
           - The format is as follows: Org_Id, Contract Start Date and Contract End Date for this function to work.
           - Contract Start Date and End Date MUST be in DD-MM-YYYY format.
       The system will automatically filter out which contracts end in 2 months or less.
    After entering the details following the format in the given file, you may proceed to do the following inputs:
        [1] to receive the statistics.
        [99] to return to the main menu.
   ```
   - Configuring the `src/main/resources/3_ContractRenewal/Input.csv` is essential, as the program would have no context for the contract start/end dates otherwise.
   - The system will automatically filter out which contracts end in 2 months or less.
   - Outputs the monitoring usage details where the status is turned **ON**:
      - Prints to the Command Line
      - Updates a file `src/main/resources/3_ContractRenewal/Output.csv`
        
### Get Contract Renewal Statistics Menu (Individual Company Monitoring Details)
   - Backend logic has already been written and works as of `31 October 2023`.
   - I am unable to implement the mapping logic because I no longer have access to the API as of `3 November 2023.`
   - Outputs the monitoring usage details where the monitoring scans' status is either turned **ON** or **OFF**.
      - In essence, it goes through **all scans** and returns a list of the granular details of each scan (the individuals/corporations that each organization was scanning at the time).
   - Logic can be found in `private static void menu4() ` of `src\main\java\RTT\billing\CommandLineApp.java`.

## Technologies Incorporated
### Java
   - Original API calls were done in Python.
   - Ported over to Java to facilitate easier Spring-Boot compatibility (Important for future implementation).
     
### HTTP Connection Pooling Manager
   - The `ApiService` class contains a field reference to a `PoolingHttpClientConnectionManager` object.
   - This is done intentionally to re-use the Connection Manager as many times as possible.
   - This significantly reduces the delay associated with re-creating and destroying the Connection Manager upon every HTTP call.
     
### YamlBeans
   - For user experience and updating of yearly interquartile billing start/end dates.
