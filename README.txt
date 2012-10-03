Hello, and welcome to VehicleCalculator! Please take a few moments to read this document to better understand the program.

VehicleCalculator is a simple program that reads in formatted JSON data from a fixed location and uses that data to present vehicle design options to the user. Based on the parts chosen and the operation selected, vehicle calculator determines the distance each vehicle will move. It is written in Java and compatible with JREs of version 1.6 and above.

---Setup---
1. Check the java version on the system you're using. To do this, open a terminal or command prompt and type "java -version". If the command prompt reports that it doesn't understand 'java', then you may need to install a runtime environment from http://www.java.com/getjava/ . If you have java installed, but still can't get a version number to come up, check your PATH variable and ensure than the java/bin folder is on the path.
2. Download the VehicleCalculator.jar file from https://github.com/mrmcduff/VehicleCalculator/downloads . In your command window / terminal, navigate to the folder containing VehicleCalculator.jar and type "java -jar VehicleCalculator.jar", or simply double-click the file in explorer/finder.
3. A window will open and the program is now running.

---Operation---
VehicleCalculator is a simple gui that lets you select from a range of parts determined by the contents of the file at www.atlanticbt.com/mobiletest/json.txt. If that file is improperly formatted, there will be missing parts and you may not be able to do any calculations. If that file is missing or there is a connection error, you will be asked to check your connection and restart the program.

To use VehicleCalculator, select the options for the vehicle you'd like to design and press the "Calculate" button.

VehicleCalculator has five selectable fields in comboboxes:
VehicleType, Frame, Front Wheel, Rear Wheel, Power Plant, and Rider. When all comboboxes have selections, the "Calculate" button is enabled. Pressing this button will calculate the distance traveled.

Each part has attributes (not visible in the gui) that affect how much they impact the amount of time a vehicle can be ridden or how fast it goes. By default, all percentages for parts affecting the vehicle stack their modifiers additively. For instance, if you have two fast wheels, each of which increases the speed of the vehicle by 10%, then your vehicle will move 20% faster.

If you would prefer modifiers to stack multiplicatively (so that the two wheels would make you move 1.10 * 1.10 = 1.21, or 21% faster), select Options from the menu and set the stacking operation to Multiply.

---Source Files---
All files can be found at https://github.com/mrmcduff/VehicleCalculator. VehicleCalculator was built using Eclipse in standard package format using com.mishmash.alpha as the package. You can clone the repository and import the project into your workspace if you'd like to build, provided you have the required libraries in your path (see below). You will need JDK 1.6 or above and JUnit 4 if you'd like to run the tests. The 'doc' folder contains the javadoc html files documenting all public methods of all classes. The 'src' folder contains the source code, and the 'test' folder contains the JUnit tests.

VehicleCalculator was written using abbreviated TDD, where most tests were written prior to development and most methods are tested. A few regression tests for particularly tricky bugs were created and left in the test code. Some methods were left without automated tests due to time constraints. 

VehicleCalculator depends on the following external libraries:
guava-13.0.1
gson-2.2.2
commons-io-2.4

These must be in your Java build path in order to build the project. To add these in Eclipse, open the project folder, right click and go to Properties -> Java Build Path -> Libraries (tab), click Add External Jar, and locate the libraries on your machine.


