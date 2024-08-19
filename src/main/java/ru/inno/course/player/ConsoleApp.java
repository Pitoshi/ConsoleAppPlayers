package ru.inno.course.player;

import ru.inno.course.player.data.DataProvider;
import ru.inno.course.player.model.Player;
import ru.inno.course.player.service.PlayerService;
import ru.inno.course.player.service.PlayerServiceImpl;

import java.util.Collection;
import java.util.Scanner;

public class ConsoleApp {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        DataProvider DataProvider = null;
        PlayerService service = new PlayerServiceImpl(DataProvider);

        printHelp();

        while (true) {
            try {
                String command = scanner.nextLine();
                if (!isKnownCommand(command)) {
                    System.out.println("Unknown command!");
                    printHelp();
                }

                if (command.equalsIgnoreCase("list")) {
                    Collection<Player> players = service.getPlayers();
                    if (players.size() == 0) {
                        System.out.println("Empty player list");
                    }

                    for (Player player : players) {
                        System.out.println(player);
                    }
                }

                // add Max
                if (command.toLowerCase().startsWith("add ")) {
                    String nick = command.substring(4);
                    int newPlayerId = service.createPlayer(nick);
                    System.out.println(newPlayerId);
                }

                // get 1
                if (command.toLowerCase().startsWith("get ")) {
                    String idAsString = command.substring(4);
                    int id = Integer.parseInt(idAsString);
                    Player player = service.getPlayerById(id);
                    System.out.println(player);
                }

                // delete 1
                if (command.toLowerCase().startsWith("delete ")) {
                    String idAsString = command.substring(7);
                    int id = Integer.parseInt(idAsString);
                    Player player = service.deletePlayer(id);
                    System.out.println(player);
                }

                // points 1 20 => [ "1", "20" ]
                if (command.toLowerCase().startsWith("points ")) {
                    String argsString = command.substring(7);
                    String[] arguments = argsString.split(" ");
                    int id = Integer.parseInt(arguments[0]);
                    int points = Integer.parseInt(arguments[1]);

                    int newScore = service.addPoints(id, points);
                    System.out.println(newScore);
                }

                if (command.equalsIgnoreCase("quit")) {
                    System.exit(0);
                }

                if (command.equalsIgnoreCase("help")) {
                    printHelp();
                }
            } catch (Exception ex) {
                System.err.println(ex.getMessage());
            }
        }

    }

    private static boolean isKnownCommand(String command) {
        String lowerCase = command.toLowerCase();
        return lowerCase.equals("help")
                || lowerCase.equals("quit")
                || lowerCase.equals("list")
                || lowerCase.startsWith("add ")
                || lowerCase.startsWith("get ")
                || lowerCase.startsWith("delete ")
                || lowerCase.startsWith("points ");
    }

    private static void printHelp() {
        System.out.println("help – print this message");
        System.out.println("add <nickname> – add new Player with NICKNAME");
        System.out.println("get <id> – get Player by id");
        System.out.println("list – get all users");
        System.out.println("delete <id> – delete Player by id");
        System.out.println("points <playerId> <pointsToAdd> – add points to Player");
        System.out.println("quit – exit");
    }
}
