# AppAML

- Backend functions tested;
- Frontend CLI connected but may be buggy particular with consideration to use case 3, as I no longer have access to the API to test it
- Documentation to be delivered

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
  
   - You can now run the project by pressing the play icon at the top right hand corner of the VsCode UI `Play > Run Java` or `Play > Debug Java`

   
