/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

/**
 *
 * @author LEVALLOIS
 */
@Entity
public class Player implements Comparable<Player> {

    @Id
    private ObjectId id;
    String email;
    String[] codes;
    String twitter;
    int correctCodes;

    public String getEmailAddress() {
        return email;
    }

    public void setEmailAddress(String emailAddress) {
        this.email = emailAddress;
    }

    public String[] getCodes() {
        return codes;
    }

    public void setCodes(String[] codes) {
        this.codes = codes;
    }

    public String getTwitter() {
        return twitter.startsWith("@") ? twitter : "@" + twitter;
    }

    public void setTwitter(String twitter) {
        this.twitter = twitter;
    }

    public int getCorrectCodes() {
        return codes.length;
    }

    public void setCorrectCodes(int correctCodes) {
        this.correctCodes = correctCodes;
    }

    @Override
    public int compareTo(Player another) {
        if (this.codes.length < another.codes.length) {
            return -1;
        } else {
            return 1;
        }
    }

}
