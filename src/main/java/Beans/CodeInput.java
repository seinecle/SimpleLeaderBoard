/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Beans;

import Model.Player;
import java.io.Serializable;
import java.util.Set;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.Query;
import org.mongodb.morphia.query.UpdateOperations;

/**
 *
 * @author LEVALLOIS
 */
@ManagedBean
@ViewScoped
public class CodeInput implements Serializable {

    @Inject
    SingletonDB singleton;

    private String code;
    private String email;
    private String twitter;

    public CodeInput() {
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTwitter() {
        return twitter;
    }

    public void setTwitter(String twitter) {
        this.twitter = twitter;
    }

    public String saveCode() {
        Set<String> correctCodes = singleton.getCorrectCodes();
        int correctAnswer = 0;
        for (String correctCode : correctCodes) {
            if (this.code.contains(correctCode)) {
                correctAnswer++;
            }
        }
        if (correctAnswer == 0) {
            return null;
        } else {
            Datastore ds = singleton.getDatastore();
            Query<Player> updateQueryPlayer = ds.createQuery(Player.class).field("twitter").equal(this.twitter);
            UpdateOperations<Player> opsPlayer;
            opsPlayer = ds.createUpdateOperations(Player.class).add("codes", this.code, false);
            ds.updateFirst(updateQueryPlayer, opsPlayer, true);

            return null;
        }
    }

}
