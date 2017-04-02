package com.tumblrviewer.models;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.RealmResults;

/**
 * Created by sebastian on 11.12.2016.
 */

public class ProfilesDbModel extends RealmObject{
    public String profile;

    public ProfilesDbModel(){

    }

    public ProfilesDbModel(String profile) {
        this.profile = profile;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public static List<String> getAllRecords(){
        Realm realm = Realm.getDefaultInstance();
        RealmResults<ProfilesDbModel> result = realm.where(ProfilesDbModel.class).findAll();
        ArrayList<String> resultString = new ArrayList<>();
        for(int i=0; i<result.size(); i++){
            resultString.add(result.get(i).getProfile());
        }

        return resultString;
    }

    public static void addRecord(String profile){
        Realm realm = Realm.getDefaultInstance();

        RealmResults<ProfilesDbModel> result = realm.where(ProfilesDbModel.class)
                .equalTo("profile", profile)
                .findAll();
        if(result.size()==0) {
            ProfilesDbModel profilesDbModel = new ProfilesDbModel(profile);
            realm.beginTransaction();
            realm.copyToRealm(profilesDbModel);
            realm.commitTransaction();
        }
    }

    public static List<ProfilesDbModel> getFiltredRecords(String filter){
        Realm realm = Realm.getDefaultInstance();
        RealmResults<ProfilesDbModel> result = realm.where(ProfilesDbModel.class)
                .contains("profile", filter)
                .findAll();
        return result;
    }

    @Override
    public String toString() {
        return profile;
    }
}
