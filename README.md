# MyCustomGame
A "simple" game with an API for writing plugins

# How to write a plugin
1. Download and install the IntelliJ IDE
2. Create a new Java workspace using the IntelliJ compiler
3. Set the src folder to not be the sources folder (right click, mark directory as, unmark as sources root)
4. In the src folder, add a new folder called main
5. In the main folder, add a folder called java
6. Set the java folder as a sources root
7. In the java folder, make a new package under "me.your name.the name of the plugin". This will be where your code goes. This is what I mean by "Project folder" from here on
8. Go to File, Project Structure, Libraries, and add the jar file of MyCustomGame.jar as a dependency
9. Click apply, then OK
10. In your project folder, create a Java class called main, then make it implement Plugin from api.me.luna.mycustomgame.Plugin
11. Import the Plugin class
12. Implement the methods
13. In the getName() method, make it return the name of the plugin.
14. Make a new static method like this: public static void main(String[] args) {};
15. Go to file, project structure, artifacts, and click the plus (+), then click JAR, then from modules with dependencies
16. For main class, select the class with the main() method, not the method
17. Click OK, then Apply, then OK again
18. Write the rest of the code as required to fit your needs and remember that the onEnable() method is called when the plugin is enabled, and thee onDisable method is called when the plugin is disabled

# How to register a command
1. Follow steps on how to write a plugin
2. Go to your Project folder, then inside, create a new package called "commands", then add a class with the appropriate name
3. Inside the new command class, make it implement CommandExecutor
4. Implement the methods
5. In the new method, add any code for your command
6. Go to your main class's onEnable method
7. In the onEnable method, write PluginManager.registerCommand(any label, the executor, this);
8. Import any methods that you have used
9. Go to Build, Build Artifacts, and then, if you have not compiled the project before, build, otherwise, rebuild
10. Place the jar file inside of your plugins folder
11. Start the server
