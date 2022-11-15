package com.example.dragonsushi.Objects;

import java.io.Serializable;

public class Client implements Serializable {
    User user;
    Person person;

    public Client(User user, Person person){
        this.user = user;
        this.person = person;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

}
