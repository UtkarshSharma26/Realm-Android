package com.example.realmdata.Model;

import io.realm.RealmObject;

public class Database extends RealmObject {
    String name;
    int marks;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMarks() {
        return marks;
    }

    public void setMarks(int marks) {
        this.marks = marks;
    }

    @Override
    public String toString() {
        return "Database{" +
                "name='" + name + '\'' +
                ", marks=" + marks +
                '}';
    }
}
