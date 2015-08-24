/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Beans;

import Model.Player;
import com.mongodb.MongoClient;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;

/**
 *
 * @author LEVALLOIS
 */
@Singleton
@Startup
public class SingletonDB implements Serializable {

    private Datastore datastore;
    private Set<String> correctCodes;

    @PostConstruct
    void loadConfiguration() {

        final Morphia morphia = new Morphia();
        morphia.map(Player.class);

        datastore = morphia.createDatastore(new MongoClient(), "players");
        datastore.ensureIndexes();
        
        correctCodes = new HashSet();
        correctCodes.add("1872");
        correctCodes.add("codename");
        correctCodes.add("milan");
        correctCodes.add("0");
        correctCodes.add("zero");
    }    

    public Datastore getDatastore() {
        return datastore;
    }

    public Set<String> getCorrectCodes() {
        return correctCodes;
    }

    

}
