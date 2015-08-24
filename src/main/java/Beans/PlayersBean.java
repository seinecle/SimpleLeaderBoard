/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Beans;

import BusinessLogic.CodesImporter;
import Model.CodeAward;
import Model.MappingCodeAwards;
import Model.Player;
import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multiset;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.Query;

/**
 *
 * @author LEVALLOIS
 */
@ManagedBean
@RequestScoped
public class PlayersBean implements Serializable {

    @Inject
    SingletonDB singleton;

    private List<Player> players;

    public PlayersBean() {
    }

    @PostConstruct
    public void init() {

        try {
            Set<CodeAward> codeAwards = CodesImporter.importCodes();
            MappingCodeAwards.initializeMapping();

            Datastore ds = singleton.getDatastore();
            Query q = ds.createQuery(Player.class);
            players = q.asList();

            if (players == null) {
                players = new ArrayList();
            }

            for (Player player : players) {

//            Query<Player> qUNiquePlayer = ds.createQuery(Player.class).filter("twitter", player.getTwitter());
//            Player foundPlayer = (Player) qUNiquePlayer.get();
                StringBuilder sb = new StringBuilder();

                Multiset<String> categoryCodeAwards = HashMultiset.create();

                for (String code : player.getCodes()) {
                    for (CodeAward codeAward : codeAwards) {
                        if (code.equals(codeAward.getCode())) {
                            categoryCodeAwards.add(codeAward.getCategory());
                            player.setPoints(player.getPoints() + codeAward.getPoints());
                            break;
                        }
                    }
                }

                for (String categoryCodeAward : categoryCodeAwards.elementSet()) {
                    if (MappingCodeAwards.getMapCategoryToFontIcon().get(categoryCodeAward) != null) {
                        sb.append("<i style=\"font-size:0.8em\" class=\"fa ").append(MappingCodeAwards.getMapCategoryToFontIcon().get(categoryCodeAward)).append("\"></i> x ").append(categoryCodeAwards.count(categoryCodeAward));
                        sb.append(", ");
                    }
                }
                if (sb.lastIndexOf(", ") > 0) {
                    sb.delete(sb.lastIndexOf(","), sb.length() - 1);
                }
                player.setHtmlListOfCodeAwards(sb.toString());

            }
        } catch (IOException ex) {
            Logger.getLogger(PlayersBean.class.getName()).log(Level.SEVERE, null, ex);
        }

        Collections.sort(players);
        Collections.reverse(players);

        //find rank
        Player previous = null;
        int counterPlayers = 0;

        for (Player player : players) {
            counterPlayers++;
            if (previous != null) {
                if (player.getPoints() == previous.getPoints()) {
                    player.setRank(previous.getRank());
                } else {
                    player.setRank(counterPlayers);
                }
            } else {
                player.setRank(counterPlayers);
            }
            previous = player;

        }
    }

    public List<Player> getPlayers() {
        return players;
    }

}
