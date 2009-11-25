package utils;

import models.Message;
import play.jobs.Job;
import play.jobs.OnApplicationStart;
import siena.PersistenceManagerFactory;
import siena.gae.GaePersistenceManager;

@OnApplicationStart
public class ConfigureDatabase extends Job {

        @Override
        public void doJob() {
                GaePersistenceManager pm = new GaePersistenceManager();
                try {
                        PersistenceManagerFactory.install(pm, Message.class);
                } catch(Exception e) {
                        e.printStackTrace();
                }
        }

}
