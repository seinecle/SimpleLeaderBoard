/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Beans;

import Model.Player;
import javax.inject.Named;
import javax.enterprise.context.Dependent;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.Query;

/**
 *
 * @author LEVALLOIS
 */
@ManagedBean
@ViewScoped
public class PlayersBean implements Serializable {

    @Inject
    SingletonDB singleton;

    private List<Player> players;

    public PlayersBean() {
    }

    @PostConstruct
    public void init() {
        Datastore ds = singleton.getDatastore();
        Query q = ds.createQuery(Player.class);
        players = q.asList();
        
    }

    public List<Player> getPlayers() {
        return players;
    }

}
