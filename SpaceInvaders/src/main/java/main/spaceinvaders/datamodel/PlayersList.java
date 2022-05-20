package main.spaceinvaders.datamodel;

import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class PlayersList {

    private URL fileURL;

    List<Player> players = new ArrayList<>();

    public void readFromFile () throws IOException {
        try {
            fileURL = getClass().getClassLoader().getResource("players_data/data");
            System.out.println(fileURL);
            File playerFile = new File(fileURL.toURI());
            Scanner sc = new Scanner(playerFile);
            while (sc.hasNextLine()) {
                System.out.println("fff");
                String[] data = sc.nextLine().split("/");
                if (data.length != 3) throw new IllegalArgumentException("Invalid data string format");
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

    public void addNewPlayer (String nick, int pass) throws IOException, URISyntaxException {
        players.add(new Player(nick, 0,pass));
        PrintWriter writer = new PrintWriter(new FileWriter(new File(fileURL.toURI()),true));
        writer.append(nick).append("/0/").append(String.valueOf(pass)).append("\n");
        writer.close();
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
