package com.pro.pojo;

import org.springframework.beans.factory.annotation.Autowired;

public class Human {
    @Autowired
    private Cat cat;
    private String name;
    private Dog dog;
    @Autowired
    public void setDog(Dog dog) {
        this.dog = dog;
    }


    @Override
    public String toString() {
        return "Human{" +
                "cat=" + cat +
                ", dog=" + dog +
                ", name='" + name + '\'' +
                '}';
    }

    public Cat getCat() {
        return cat;
    }

    public void setCat(Cat cat) {
        this.cat = cat;
    }

    public Dog getDog() {
        return dog;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
