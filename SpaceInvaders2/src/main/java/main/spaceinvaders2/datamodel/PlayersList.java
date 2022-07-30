package main.spaceinvaders2.datamodel;

import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import main.spaceinvaders2.SpaceInvaders2App;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Arrays;
import java.util.Scanner;

public class PlayersList {

    private File file;

    private static final Player defaultPlayer = new Player("Default player", 0, 0);

    private ListProperty<Player> players = new SimpleListProperty<>(FXCollections.observableArrayList());

    private final PropertyChangeSupport listenerManager = new PropertyChangeSupport(this);

    public ObservableList<Player> getPlayers() {
        return players.get();
    }

    public ListProperty<Player> playersProperty() {
        return players;
    }

    public PlayersList(){
        players.add(defaultPlayer);
    }

    public Player getDefaultPlayer(){
        return defaultPlayer;
    }

    public void readFromFile () throws IOException {
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
        }catch(FileNotFoundException fnfe){
            file = new File("data.txt");
        }
    }

    public void update (Player player){
        players.removeIf(p->{
            return p.getNickName().equals(player.getNickName());
        });
        players.add(player);
        players.sort(null);
    }
/*            System.out.println(players.size());*/


    public void initTest(){
        for (int i = 0; i < 30; i++) {
            players.add(new Player(String.format("test%d",i),i*100,i));
        }
        players.sort(null);
    }

    public void addNewPlayer (String nick, int pass) throws IOException, URISyntaxException {
        players.add(new Player(nick, 0,pass));
        players.sort(null);
    }

    public void savePlayers() throws IOException {
        players.remove(defaultPlayer);
        PrintWriter writer = new PrintWriter(new FileWriter(file, false), true);
        for (Player player: players) {
            writer.append(player.getNickName()).append("/").append(String.valueOf(player.getMaxScores())).append("/").append(String.valueOf(player.getPassHash()));
            writer.append('\n');
        }
        writer.close();
    }

    public void addNewPlayer (Player player)  {
        for (Player p:players){
            if (p.getNickName().equals(player.getNickName())){
                SpaceInvaders2App.showError("There is already player with the same name");
                return;
            }
        }
        players.add(new Player(player.getNickName(), 0,player.getPassHash()));
        players.sort(null);
    }

    /**
     * adding property change listener
     *
     * @param listener property change listener
     */
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        System.out.println("Registering " + listener);
        listenerManager.addPropertyChangeListener(listener);
    }



    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Player p: players){
            sb.append(p.toString()+"\n");
        }
        return sb.toString();
    }

}
