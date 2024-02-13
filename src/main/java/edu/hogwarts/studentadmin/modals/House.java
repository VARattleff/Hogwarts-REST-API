package edu.hogwarts.studentadmin.modals;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity(name = "house")
public class House {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private String founder;
    @ElementCollection
    private List<String> colors = new ArrayList<>();

    public House(){}

    public House(long id, String name, String founder, List<String> colors) {
        this.id = id;
        this.name = name;
        this.founder = founder;
        this.colors = colors;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name != null) {
            this.name = name;
        }
    }

    public String getFounder() {
        return founder;
    }

    public void setFounder(String founder) {
        if (founder != null) {
            this.founder = founder;
        }
    }

    public List<String> getColors() {
        return colors;
    }

    public void setColors(List<String> colors) {
        if (colors != null) {
            this.colors = colors;
        }
    }

    @Override
    public String toString() {
        return "House{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", founder='" + founder + '\'' +
                ", colors=" + colors +
                '}';
    }
}
