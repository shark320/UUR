package main.spaceinvaders2.datamodel;

import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import main.spaceinvaders2.SpaceInvaders2App;

import java.io.*;
import java.util.Scanner;

/**
 * Class represents list of players accounts  in the game
 *
 * @author Volodymyr Pavlov
 * @version 30.07.2022
 */
public class PlayersList {
    //--------------------------------------------------------------------------//
    //                                                                          //
    //                           Static constants                               //
    //                                                                          //
    //--------------------------------------------------------------------------//

    /**
     * Default player account
     */
    private static final Player defaultPlayer = new Player("Default player", 0, 0);

    //--------------------------------------------------------------------------//
    //                                                                          //
    //                           Instance variables                             //
    //                                                                          //
    //--------------------------------------------------------------------------//

    /**
     * File with players data
     */
    private File file;


    /**
     * List of the players
     */
    private final ListProperty<Player> players = new SimpleListProperty<>(FXCollections.observableArrayList());

    //--------------------------------------------------------------------------//
    //                                                                          //
    //                              Constructors                                //
    //                                                                          //
    //--------------------------------------------------------------------------//

    /**
     * Default class constructor
     * Adding default player to the list
     */
    public PlayersList() {
        players.add(defaultPlayer);
    }

    //--------------------------------------------------------------------------//
    //                                                                          //
    //                           Getters and Setters                            //
    //                                                                          //
    //--------------------------------------------------------------------------//

    /**
     * Players list getter
     *
     * @return player list
     */
    public ObservableList<Player> getPlayers() {
        return players.get();
    }

    /**
     * Players list property getter
     *
     * @return players list property
     */
    public ListProperty<Player> playersProperty() {
        return players;
    }

    /**
     * Default player getter
     *
     * @return default player instance
     */
    public Player getDefaultPlayer() {
        return defaultPlayer;
    }


    //--------------------------------------------------------------------------//
    //                                                                          //
    //                           Instance methods                               //
    //                                                                          //
    //--------------------------------------------------------------------------//

    /**
     * Read players info from the data file
     */
    public void readFromFile() {
        try {
            //fileURL = getClass().getClassLoader().getResource("data.txt");
            file = new File("data.txt");
            Scanner sc = new Scanner(file);
            while (sc.hasNextLine()) {
                String[] data = sc.nextLine().split("/");
                /*                System.out.println(Arrays.toString(data));*/
                if (data.length != 3) throw new IllegalArgumentException("Invalid data.txt string format");
                String nick = data[0];
                int scores = Integer.parseInt(data[1]);
                int pass = Integer.parseInt(data[2]);
                players.add(new Player(nick, scores, pass));
            }
        } catch (FileNotFoundException fnfe) {
            file = new File("data.txt");
        }
    }

    /**
     * Update player info
     *
     * @param player -player to update
     */
    public void update(Player player) {
        players.removeIf(p -> p.getNickName().equals(player.getNickName()));
        players.add(player);
        players.sort(null);
    }


    /**
     * Save all players to the file
     *
     * @throws IOException -if there are problems with file IO
     */
    public void savePlayers() throws IOException {
        players.remove(defaultPlayer);
        PrintWriter writer = new PrintWriter(new FileWriter(file, false), true);
        for (Player player : players) {
            writer.append(player.getNickName()).append("/").append(String.valueOf(player.getMaxScores())).append("/").append(String.valueOf(player.getPassHash()));
            writer.append('\n');
        }
        writer.close();
    }

    /**
     * Adding new player to the list
     *
     * @param player -player to add
     */
    public void addNewPlayer(Player player) {
        for (Player p : players) {
            if (p.getNickName().equals(player.getNickName())) {
                SpaceInvaders2App.showError("There is already player with the same name");
                return;
            }
        }
        players.add(new Player(player.getNickName(), 0, player.getPassHash()));
        players.sort(null);
    }

    //--------------------------------------------------------------------------//
    //                                                                          //
    //                       Override instance methods                          //
    //                                                                          //
    //--------------------------------------------------------------------------//
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Player p : players) {
            sb.append(p.toString()).append("\n");
        }
        return sb.toString();
    }

}
