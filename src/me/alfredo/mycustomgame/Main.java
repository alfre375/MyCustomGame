package me.alfredo.mycustomgame;

import me.alfredo.mycustomgame.api.*;
import me.alfredo.mycustomgame.api.events.PlayerChatEvent;
import me.alfredo.mycustomgame.commands.PluginsCommand;

import java.io.File;
import java.io.FileInputStream;
import java.lang.reflect.Method;
import java.util.*;

public class Main {
    static PluginsCommand pluginsCommand = new PluginsCommand();
    static Util util = new Util();
    public static Server server = Server.generateInstance(new ConsoleSender() {
        @Override
        public void sendMessage(String message) {
            System.out.println(message + ChatColor.RESET);
        }

        @Override
        public void executeCommand(String label, String[] args) {
            List<String> tokens = Arrays.stream(args).toList();
            tokens.add(0,label);
            handleCommand(util.mergeStringList((String[]) tokens.stream().toArray(), " "), this);
        }
    });

    private static volatile boolean isServerRunning = true;

    public static void main(String[] args) {
        PluginManager pm = new PluginManager();
        pm.loadPlugins();
        Properties properties = new Properties();
        try {
            File jarFile = new File(Main.class.getProtectionDomain().getCodeSource().getLocation().toURI());
            String propertiesFilePath = jarFile.getParentFile().getAbsolutePath() + File.separator + "server.properties";
            FileInputStream fileInputStream = new FileInputStream(propertiesFilePath);
            properties.load(fileInputStream);
        } catch (Exception e) {
            e.printStackTrace();
        }

        String internetProtocolAddress = properties.getProperty("server-ip").split("#")[0];
        internetProtocolAddress = util.mergeStringList(internetProtocolAddress.split(" "), "");
        int port = Integer.parseInt(util.mergeStringList(properties.getProperty("server-port").split("#")[0].split(" "),""));
        String motd = properties.getProperty("motd").split("#")[0];
        String passphrase = properties.getProperty("entry-passphrase");
        passphrase = passphrase.split("#")[0];
        passphrase = util.mergeStringList(passphrase.split(" "),"");
        boolean whitelist = Boolean.parseBoolean(util.mergeStringList(properties.getProperty("whitelist").split("#")[0].split(" "),""));

        System.out.println("This is working so far. The IP is " + internetProtocolAddress + ":" + port);
        System.out.println("MOTD registered as " + motd);

        if (passphrase == null || passphrase.isEmpty()) {
            System.out.println("Passphrase blank. Passphrase will not be required for entry.");
        } else {
            System.out.println("Passphrase will be required. Post-encryption is " + passphrase);
        }

        System.out.println("Whitelist is set to " + whitelist);

        Scanner scanner = new Scanner(System.in);
        System.out.print("> ");
        while (isServerRunning) {
            String input = scanner.nextLine();
            handleCommand(input, server.getConsoleSender());
            System.out.print("> ");
        }
    }
    public static String[] removeElement(String[] array, int index) {
        if (index < 0 || index >= array.length) {
            // Invalid index, return the original array
            System.out.println("Invalid index");
            return array;
        }

        String[] newArray = new String[array.length - 1];
        int newArrayIndex = 0;

        for (int i = 0; i < array.length; i++) {
            if (i != index) {
                newArray[newArrayIndex] = array[i];
                newArrayIndex++;
            }
        }

        return newArray;
    }
    public static void handleCommand(String input, CommandSender sender) {
        String[] tokens = input.split(" ");
        String commandLabel = tokens[0];
        String args = tokens.length > 1 ? tokens[1] : "";
        String[] arg = tokens;
        arg = removeElement(arg, 0);
        final String[] argf = arg;

        if (PluginManager.commandMap.containsKey(commandLabel)) {
            CommandExecutor executor = PluginManager.commandMap.get(commandLabel);
            executor.onPluginCommand(sender,commandLabel,arg);
            return;
        }

        if (commandLabel == "") {
            return;
        }

        switch (commandLabel) {
            case "stop":
                stopServer();
                break;
            case "plugins", "pl":
                pluginsCommand.onPluginCommand(sender, commandLabel, arg);
                break;
            case "handydandydebug":
                System.out.println("ARGS: "+args);
                System.out.println("Tokens: " + util.mergeStringList(tokens, ", "));
                System.out.println("ARG: " + util.mergeStringList(arg, ", "));
                System.out.println("commandLabel: " + commandLabel);
                System.out.println(sender instanceof ConsoleSender);
                System.out.println(sender instanceof Player);
                break;
            case "reload", "rl":
                PluginManager pm = new PluginManager();
                pm.unrecognizePlugins();
                pm.loadPlugins();
                break;
            case "handlenewevent":
                if (argf.length == 0) {
                    sender.sendMessage(ChatColor.RED + "yeah, no");
                    return;
                }
                HandlePlayerJoining hpj = new HandlePlayerJoining(new Main(), argf[0]);
                break;
            case "uuidgen":
                sender.sendMessage(UUID.randomUUID().toString());
                break;
            case "dupeuuid":
                List<UUID> uuids = new ArrayList<>();
                while (true) {
                    UUID currentUUID = UUID.randomUUID();
                    if (uuids.contains(currentUUID)) {
                        sender.sendMessage(currentUUID + " is doubled up, took " + (uuids.size()+1) + " tries");
                        break;
                    }
                    sender.sendMessage(String.valueOf(uuids.size()));
                    uuids.add(currentUUID);
                    if (uuids.size() > 1000000) {
                        sender.sendMessage("Unable to find duplicate UUIDs (1000000 tries)");
                    }
                }
                break;
            case "list":
                sender.sendMessage(ChatColor.CYAN + "There are " + server.getOnlinePlayers().size() + " online players: ");
                List<String> names = new ArrayList<>();
                for (Player p : server.getOnlinePlayers()) {
                    names.add(p.getName());
                }
                sender.sendMessage(ChatColor.YELLOW + util.mergeStringList(names.toArray(),", "));
                break;
            case "handlemessageevent":
                String msg = tokens.length > 2 ? tokens[2] : "";
                String[] msgl = removeElement(tokens, 0);
                msgl = removeElement(msgl, 0);
                msg = util.mergeStringList(msgl, " ");
                String playerName = arg[0];
                Player p = null;
                for (Player p1 :
                        server.getOnlinePlayers()) {
                    if (p1.getName().equals(playerName)) {
                        p = p1;
                    }
                }
                if (p == null) {
                    sender.sendMessage("You need to first create the player with handlenewevent " + playerName);
                }
                PlayerChatEvent e = new PlayerChatEvent(msg, msg.startsWith("/"), p);
                pm = new PluginManager();
                pm.handleEvents(e);
                break;
            default:
                ChatColor.println("&4Command not found: /" + input, "&");
                break;
        }
    }

    public static void stopServer() {
        System.out.println("Stopping server...");
        PluginManager pm = new PluginManager();
        pm.disableAll();
        pm.unrecognizePlugins();
        isServerRunning = false;
    }

    public static void sendMessage(String message) {
        System.out.println(message + ChatColor.RESET);
    }
}
