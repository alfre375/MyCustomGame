package me.luna.mycustomgame.api;

import me.luna.mycustomgame.ChatColors;
import me.luna.mycustomgame.Main;
import me.alfredo.mycustomgame.api.events.*;
import me.luna.mycustomgame.api.events.*;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class PluginManager {
    Main main = new Main();
    public static HashMap<Plugin, Boolean> pluginEnabledMap = new HashMap<Plugin, Boolean>();
    public static HashMap<Plugin, String> pluginNameMap = new HashMap<Plugin, String>();
    public static HashMap<CommandExecutor, Priority> priorityMap = new HashMap<CommandExecutor, Priority>();
    public static HashMap<String, CommandExecutor> commandMap = new HashMap<String, CommandExecutor>();
    public static HashMap<CommandExecutor, Plugin> executorPluginMap = new HashMap<CommandExecutor, Plugin>();
    public static HashMap<Listener, Plugin> listenerPluginMap = new HashMap<Listener, Plugin>();

    private boolean checkHas(Class[] inters, String name){
        for (Class c : inters) {
            if (c.getName() == name)
                return true;
        }
        return false;
    }
    private boolean checkHas(Plugin[] plugins, String name){
        for (Plugin plugin : plugins) {
            if (plugin.getName() == name)
                return true;
        }
        return false;
    }
    private Plugin loadFromJar(String filePath) throws ClassNotFoundException, IOException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        JarFile jarFile = new JarFile(filePath);
        Enumeration<JarEntry> e = jarFile.entries();

        URL[] urls = { new URL("jar:file:" + filePath+"!/") };
        URLClassLoader cl = URLClassLoader.newInstance(urls);

        while (e.hasMoreElements()) {
            JarEntry je = e.nextElement();
            if(je.isDirectory() || !je.getName().endsWith(".class")){
                continue;
            }
            // -6 because of .class
            String className = je.getName().substring(0,je.getName().length()-6);
            className = className.replace('/', '.');
            Class c = cl.loadClass(className);
            Class sup = c.getSuperclass();
            Class[] interfaces = c.getInterfaces();
            System.out.println(sup);
            if (interfaces.length > 0) {

                if (checkHas(interfaces, "me.alfredo.mycustomgame.api.Plugin")) {
                    System.out.println(c.getConstructor().newInstance() instanceof CommandSender);
                    return (Plugin) c.getConstructor().newInstance();
                }
                System.out.println((c.getConstructor().newInstance() instanceof CommandSender) + ";");
            }
        }
        return null;
    }
    public void loadPlugins() {
        String pluginDir = "";
        try {
            File jarFile = new File(Main.class.getProtectionDomain().getCodeSource().getLocation().toURI());
            pluginDir = jarFile.getParentFile().getAbsolutePath() + File.separator + "plugins";
            System.out.println(pluginDir);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(ChatColors.RED + "FATAL: " + e.getMessage() + ChatColors.getDefaultColorCode());
            Main.stopServer();
            return;
        }
        File dir = new File(pluginDir);
        File[] directoryListing = dir.listFiles();
        if (directoryListing != null) {
            System.out.println("\"plugins\" folder was found, contents: ");
            for (File child : directoryListing) {
                String[] parts = child.getName().split("\\.");
                if(parts.length>1 && parts[parts.length-1].equalsIgnoreCase("jar")){
                    System.out.println("JARFILE:"+child.getName() + " ");
                    Plugin plugin;
                    try {
                        plugin = loadFromJar(child.getPath());
                        plugin.onEnable();
                    } catch (ClassNotFoundException e) {
                        System.out.println("Erroneous JAR file: " + child.getName());
                        continue;
                    } catch (IOException | InvocationTargetException | IllegalAccessException | InstantiationException |
                             NoSuchMethodException e) {
                        System.out.println(e.getMessage());
                        continue;
                    }
                    if (!pluginEnabledMap.containsKey(plugin)) {
                        pluginEnabledMap.put(plugin, true);
                        pluginNameMap.put(plugin, plugin.getName());
                    } else {
                        pluginEnabledMap.replace(plugin, true);
                    }
                    System.out.println("Loaded plugin " + plugin.getName());
                }else{
                    System.out.println(child.getName() + " ");
                }
            }
            System.out.println("");
        } else {
            //give warning
            System.out.println(ChatColors.RED.getCode() + "No plugins directory" + ChatColors.getDefaultColorCode());
            //create a new "plugins" folder
            if (new File(pluginDir).mkdirs()) {
                System.out.println(ChatColors.GREEN.getCode() + "Successfully created the plugins folder" + ChatColors.getDefaultColorCode());
            } else {
                System.out.println(ChatColors.RED.getCode() + "ERR: Unable to create the plugins folder!" + ChatColors.getDefaultColorCode());
            }
        }
    }
    public void enablePlugin(Plugin plugin) {
        // Run code to enable the plugin here
        pluginEnabledMap.replace(plugin,true);
        plugin.onEnable();
    }

    public void disablePlugin(Plugin plugin) {
        // Run code to disable the plugin here
        pluginEnabledMap.replace(plugin,false);
        HashSet<CommandExecutor> executorsToRemove = new HashSet<CommandExecutor>();
        for (CommandExecutor exe : executorPluginMap.keySet()) {
            if (executorPluginMap.get(exe) == plugin) {
                executorsToRemove.add(exe);
                HashSet<String> toRemove = new HashSet<String>();
                for (String s : commandMap.keySet()) {
                    if (commandMap.get(s) == exe) {
                        toRemove.add(s);
                    }
                }
                for (String s : toRemove) {
                    commandMap.remove(s);
                }
            }
        }
        for (CommandExecutor exe : executorsToRemove) {
            executorPluginMap.remove(exe);
        }
        HashSet<Listener> listenersToRemove = new HashSet<Listener>();
        for (Listener listener : listenerPluginMap.keySet()) {
            if (listenerPluginMap.get(listener) == plugin) {
                listenersToRemove.add(listener);
            }
        }
        for (Listener listener : listenersToRemove) {
            listenerPluginMap.remove(listener);
        }
        plugin.onDisable();
    }

    public boolean isEnabled(Plugin plugin) {
        // Run code to check if the plugin is enabled
        return pluginEnabledMap.get(plugin);
    }

    public static boolean isEnabledStatic(Plugin plugin) {
        // Run code to check if the plugin is enabled
        return pluginEnabledMap.get(plugin);
    }

    public static void registerCommand(String commandMainLabel, CommandExecutor executor, Plugin plugin) {
        // Run code to register a plugin command
        commandMap.put(commandMainLabel,executor);
        executorPluginMap.put(executor, plugin);
        priorityMap.put(executor, Priority.DEFAULT);
    }

    public static void registerCommand(String commandMainLabel, CommandExecutor executor, Plugin plugin, Priority priority) {
        // Run code to register a plugin command
        commandMap.put(commandMainLabel,executor);
        executorPluginMap.put(executor, plugin);
        priorityMap.put(executor, priority);
    }

    public String getPluginName(Plugin plugin) {
        // Do some code to find the name
        String name = pluginNameMap.get(plugin);
        // Return the final value
        return name;
    }

    public String[] getPluginsNames() {
        List<String> pluginNames = new ArrayList<>();
        // Run code to get plugin names
        for (String name : pluginNameMap.values()) {
            pluginNames.add(name);
        }
        // Add the plugin's name to the list
        // Return the final list of strings
        return pluginNames.toArray(new String[0]);
    }

    public String[] getEnabledPluginsNames() {
        // Create a variable to hold the names of the plugins
        List<String> pluginNames = new ArrayList<>();
        // Run code to get the plugin classes
        Plugin[] plugins = pluginEnabledMap.keySet().toArray(new Plugin[0]);
        // Check and see if the plugin is enabled
        for (Plugin pluginClass : plugins) {
            if (isEnabled(pluginClass)) {
                pluginNames.add(getPluginName(pluginClass));
            }
        }
        return pluginNames.toArray(new String[0]);
    }
    public Plugin getPlugin(String name) {
        for (Plugin p : pluginNameMap.keySet()) {
            if (pluginNameMap.get(p).equals(name)) {
                return p;
            } /*else {
                System.out.println(pluginNameMap.get(p) + " != " + name);
            }*/
        }
        return null;
    }
    public void disableAll() {
        for (Plugin p : pluginEnabledMap.keySet()) {
            disablePlugin(p);
        }
    }
    public void enableAll() {
        for (Plugin p : pluginEnabledMap.keySet()) {
            enablePlugin(p);
        }
    }
    public void unrecognizePlugins() {
        disableAll();
        pluginEnabledMap = new HashMap<>();
        pluginNameMap = new HashMap<>();
        commandMap = new HashMap<>();
        listenerPluginMap = new HashMap<>();
    }
    public static void registerListener(Listener listener, Plugin plugin) {
        listenerPluginMap.put(listener,plugin);
    }
    public void handleEvents(Event event) {
        if (event.getEventType() == EventType.PLAYER_CHAT) {
            PlayerChatEvent pce = (PlayerChatEvent) event;
            if (pce.getMessageSent().startsWith("/")) {
                String inputNoFSlash = pce.getMessageSent().substring(1,pce.getMessageSent().length());
                Main.handleCommand(inputNoFSlash, pce.getSender());
                return;
            }
        }
        for (Listener listener : listenerPluginMap.keySet()) {
            Class<?> listenerClass = listener.getClass();
            List<Method> methods = List.of(listenerClass.getDeclaredMethods());

            for (Method method : methods) {
                if (method.isAnnotationPresent(EventHandler.class)) {
                    Class<?>[] parameterTypes = method.getParameterTypes();

                    if (parameterTypes.length == 1 && Event.class.isAssignableFrom(parameterTypes[0])) {
                        if (parameterTypes[0].isAssignableFrom(event.getClass())) {
                            try {
                                method.invoke(listener, event);
                                if (event.isCancelled()) {
                                    return;
                                }
                            } catch (IllegalAccessException | InvocationTargetException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }
        }
        if (event.getEventType() == EventType.PLAYER_JOIN) {
            PlayerJoinEvent pje = (PlayerJoinEvent) event;
            Player p = pje.getPlayer();
            // Broadcast Join Message
            Server server = Main.server;
            for (Player player : server.getOnlinePlayers()) {
                player.sendMessage(pje.getJoinMessage());
            }
            server.playerConnected(pje);
            server.getConsoleSender().sendMessage(pje.getJoinMessage());
        } else if (event.getEventType() == EventType.PLAYER_CHAT) {
            PlayerChatEvent pce = (PlayerChatEvent) event;
            if (pce.getSender() instanceof Player) {
                Player p = (Player) pce.getSender();
                String msg = pce.getMessageSent();
                // Broadcast Message
                Server server = Main.server;
                for (Player player : server.getOnlinePlayers()) {
                    player.sendMessage(p.getDisplayName() + ": " + msg);
                }
                server.getConsoleSender().sendMessage(p.getDisplayName() + ": " + msg);
            }
        }
    }
}
