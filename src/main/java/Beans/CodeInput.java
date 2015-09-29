/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Beans;

import BusinessLogic.CodesImporter;
import Model.CodeAward;
import Model.Player;
import java.io.IOException;
import java.io.Serializable;
import java.util.Set;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
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
        code = !code.startsWith("#") ? "#" + code : code;
        this.code = code.toLowerCase();
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
        this.twitter = twitter.replaceAll("@", "");
    }

    public void saveCode() throws IOException {

        Set<CodeAward> codeAwards = CodesImporter.importCodes();
        if (codeAwards == null || codeAwards.isEmpty()) {
            return;
        }

        String correctAnswer = null;

        int nbCorrectAnswer = 0;
        for (CodeAward codeAward : codeAwards) {
            if (this.code.equals(codeAward.getCode())) {
                nbCorrectAnswer++;
                correctAnswer = codeAward.getCode();
            }
        }
        if (nbCorrectAnswer == 0) {
            addMessage("Code is invalid");
            return;
        } else {
            Datastore ds = singleton.getDatastore();
            Query<Player> updateQueryPlayer = ds.createQuery(Player.class).field("twitter").equal(this.twitter);
            UpdateOperations<Player> opsPlayer;
            opsPlayer = ds.createUpdateOperations(Player.class).add("codes", correctAnswer, false);
            ds.updateFirst(updateQueryPlayer, opsPlayer, true);
            addMessage("Code is valid!\n Check your position in the leaderboard!");
        }
    }

    public void addMessage(String summary) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, summary, null);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

}
//
