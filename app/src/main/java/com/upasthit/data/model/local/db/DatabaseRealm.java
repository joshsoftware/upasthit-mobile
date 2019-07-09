package com.upasthit.data.model.local.db;


import com.upasthit.ApplicationClass;

import java.util.List;


import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmObject;

/**
 * Class contains Realm Database configuration for project
 */

public class DatabaseRealm {

    RealmConfiguration realmConfiguration;

    private Realm mRealm;

    /**
     * Realm Setup
     *
     * @param app App instance
     */
    public void setup(ApplicationClass app) {
        if (realmConfiguration == null) {
            Realm.init(app);
            realmConfiguration = new RealmConfiguration.Builder()
                    .schemaVersion(0) // Must be bumped when the schema changes
                    .modules(new DataBaseModule())
                    .migration(new DataBaseMigration()) // DataBaseMigration to run instead of throwing an exception
                    .build();
            Realm.setDefaultConfiguration(realmConfiguration);
        } else {
            throw new IllegalStateException("database already configured");
        }
    }

    /**
     * @return Realm instance
     */
    public Realm getRealmInstance() {
        return Realm.getDefaultInstance();
    }


    /**
     * Generalised method to add entry in database
     *
     * @param model
     * @param <T>
     * @return
     */
    public <T extends RealmObject> T add(T model) {
        Realm realm = getRealmInstance();
        realm.beginTransaction();
        T obj = realm.copyToRealm(model);
        realm.commitTransaction();
        return obj;
    }

    /**
     * Generalised method to addOrUpdate entry in database
     *
     * @param model
     * @param <T>
     * @return
     */
    public <T extends RealmObject> T addOrUpdate(T model) {
        Realm realm = getRealmInstance();
        realm.beginTransaction();
        T obj = realm.copyToRealmOrUpdate(model);
        realm.commitTransaction();
        return obj;
    }


    /**
     * Generalised method to get all entries
     *
     * @param clazz
     * @param <T>
     * @return
     */
    public <T extends RealmObject> List<T> findAll(Class<T> clazz) {
        return getRealmInstance().where(clazz).findAll();
    }

    /**
     * Generalised method to delete entry
     *
     * @param model
     * @param <T>
     * @return
     */
    public <T extends RealmObject> T delete(T model) {
        Realm realm = getRealmInstance();
        realm.beginTransaction();
        model.deleteFromRealm();
        realm.commitTransaction();
        return model;
    }

}
