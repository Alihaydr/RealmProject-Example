package com.example.realmproject;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class QuotesModuleRealm extends RealmObject {


    @PrimaryKey
    private long id;
    private String firstName;
    private String lastName;

    public QuotesModuleRealm() {

    }

    public QuotesModuleRealm(String firstName, String lastName) {

        this.firstName=firstName;
        this.lastName=lastName;
    }

    public QuotesModuleRealm(int id, String firstName, String lastName) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public String toString() {
        return "QuotesModuleRealm{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }
}
