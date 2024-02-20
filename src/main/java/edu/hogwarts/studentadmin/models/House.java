package edu.hogwarts.studentadmin.models;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity(name = "house")
public class House {
    @Id
    private String name;
    private String founder;
    @ElementCollection
    private List<String> colors = new ArrayList<>();

    public House(){}


    public House(String name, String founder, List<String> colors) {
        this.name = name;
        this.founder = founder;
        this.colors = colors;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFounder() {
        return founder;
    }

    public void setFounder(String founder) {
        this.founder = founder;
    }

    public List<String> getColors() {
        return colors;
    }

    public void setColors(List<String> colors) {
        this.colors = colors;
    }

    @Override
    public String toString() {
        return "House{" +
                "name='" + name + '\'' +
                ", founder='" + founder + '\'' +
                ", colors=" + colors +
                '}';
    }
}
