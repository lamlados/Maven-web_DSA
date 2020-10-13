package com.pro.pojo;

import java.util.*;

public class Student {
    private String name;
    private Address addr;
    private String[] books;
    private List<String> hobbies;
    private Map<String ,String> cards;
    private Set<String> games;
    private String values;
    private Properties info;

    public Student() {
    }

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", addr=" + addr.toString() +
                ", books=" + Arrays.toString(books) +
                ", hobbies=" + hobbies +
                ", cards=" + cards +
                ", games=" + games +
                ", value='" + values + '\'' +
                ", info=" + info +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Address getAddr() {
        return addr;
    }

    public void setAddr(Address addr) {
        this.addr = addr;
    }

    public String[] getBooks() {
        return books;
    }

    public void setBooks(String[] books) {
        this.books = books;
    }

    public List<String> getHobbies() {
        return hobbies;
    }

    public void setHobbies(List<String> hobbies) {
        this.hobbies = hobbies;
    }

    public Map<String, String> getCards() {
        return cards;
    }

    public void setCards(Map<String, String> cards) {
        this.cards = cards;
    }

    public Set<String> getGames() {
        return games;
    }

    public void setGames(Set<String> games) {
        this.games = games;
    }

    public String getValues() {
        return values;
    }

    public void setValues(String value) {
        this.values = value;
    }

    public Properties getInfo() {
        return info;
    }

    public void setInfo(Properties info) {
        this.info = info;
    }
}
