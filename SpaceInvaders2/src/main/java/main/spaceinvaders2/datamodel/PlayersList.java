package main.spaceinvaders2.datamodel;

import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Scanner;

public class PlayersList {

    private URL fileURL;

    //List<Player> players = new ArrayList<>();

    private ListProperty<Player> players = new SimpleListProperty<>(FXCollections.observableArrayList());

    private final PropertyChangeSupport listenerManager = new PropertyChangeSupport(this);

    public ObservableList<Player> getPlayers() {
        return players.get();
    }

    public ListProperty<Player> playersProperty() {
        return players;
    }

    public void readFromFile () throws IOException {
        try {
            fileURL = getClass().getClassLoader().getResource("data.txt");
            File playerFile = new File(fileURL.toURI());
            Scanner sc = new Scanner(playerFile);
            while (sc.hasNextLine()) {
                String[] data = sc.nextLine().split("/");
                if (data.length != 3) throw new IllegalArgumentException("Invalid data.txt string format");
                String nick = data[0];
                int scores = Integer.parseInt(data[1]);
                int pass = Integer.parseInt(data[2]);
                players.add(new Player(nick, scores, pass));
            }
            System.out.println(players.size());
        }catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    public void initTest(){
        for (int i = 0; i < 30; i++) {
            players.add(new Player(String.format("test%d",i),i*100,i));
        }
        players.sort(null);
    }

    public void addNewPlayer (String nick, int pass) throws IOException, URISyntaxException {
        players.add(new Player(nick, 0,pass));
        PrintWriter writer = new PrintWriter(new FileWriter(new File(fileURL.toURI()),true));
        writer.append(nick).append("/0/").append(String.valueOf(pass)).append("\n");
        writer.close();
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
