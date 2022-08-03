package main.spaceinvaders2.datamodel;

/**
 * Class represents player's account
 * Includes password hash, nickname and max scores
 *
 * @author Volodymyr Pavlov
 * @version 30.07.2022
 */
public class Player implements Comparable<Player> {

    //--------------------------------------------------------------------------//
    //                                                                          //
    //                           Instance variables                             //
    //                                                                          //
    //--------------------------------------------------------------------------//

    /**
     * Hashcode of the password
     */
    private int passHash;

    /**
     * Players nickname
     */
    private String nickName;

    /**
     * Players max scores
     */
    private int maxScores;

    //--------------------------------------------------------------------------//
    //                                                                          //
    //                              Constructors                                //
    //                                                                          //
    //--------------------------------------------------------------------------//

    /**
     * Class constructor
     *
     * @param nickName -players nickname
     * @param scores   -players max scores
     * @param passHash -password hashcode
     */
    public Player(String nickName, int scores, int passHash) {
        this.passHash = passHash;
        this.nickName = nickName;
        this.maxScores = scores;
    }

    //--------------------------------------------------------------------------//
    //                                                                          //
    //                           Getters and Setters                            //
    //                                                                          //
    //--------------------------------------------------------------------------//

    /**
     * Password hash getter
     *
     * @return password hashcode
     */

    public int getPassHash() {
        return passHash;
    }

    /**
     * Password hash setter
     *
     * @param passHash -new password hashcode
     */
    public void setPassHash(int passHash) {
        this.passHash = passHash;
    }

    /**
     * Nickname getter
     *
     * @return player's nickname
     */
    public String getNickName() {
        return nickName;
    }

    /**
     * Nickname setter
     *
     * @param nickName -player's nickname
     */
    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    /**
     * Max scores getter
     *
     * @return player's max scores
     */
    public int getMaxScores() {
        return maxScores;
    }

    /**
     * Max scores setter
     *
     * @param maxScores -player's max scores
     */
    public void setMaxScores(int maxScores) {
        this.maxScores = Math.max(this.maxScores, maxScores);
    }


    //--------------------------------------------------------------------------//
    //                                                                          //
    //                       Override instance methods                          //
    //                                                                          //
    //--------------------------------------------------------------------------//

    @Override
    public int compareTo(Player p) {
        return p.maxScores - this.maxScores;
    }

    @Override
    public String toString() {
        return "[ " + nickName + " , " + maxScores + " , " + passHash + " ]";
    }
}
