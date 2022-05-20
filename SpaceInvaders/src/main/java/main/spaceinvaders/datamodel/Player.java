package main.spaceinvaders.datamodel;

public class Player {

    private int passHash;

    private String nickName;

    private int maxScores;

    public Player( String nickName, int scores,int passHash) {
        this.passHash = passHash;
        this.nickName = nickName;
        this.maxScores = scores;
    }

    public int getPassHash() {
        return passHash;
    }

    public void setPassHash(int passHash) {
        this.passHash = passHash;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public int getMaxScores() {
        return maxScores;
    }

    public void setMaxScores(int maxScores) {
        this.maxScores = maxScores;
    }

    @Override
    public String toString() {
        return "[ "+nickName+" , "+maxScores + " , " + passHash+" ]";
    }
}
