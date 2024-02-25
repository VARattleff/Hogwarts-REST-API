package edu.hogwarts.studentadmin.dto;

import java.util.ArrayList;
import java.util.List;

public class HouseResponseDto {
    private String name;
    private String founder;

    private List<String> colors = new ArrayList<>();

    public String getName() {
        return name;
    }

    public String getFounder() {
        return founder;
    }

    public List<String> getColors() {
        return colors;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setFounder(String founder) {
        this.founder = founder;
    }

    public void setColors(List<String> colors) {
        this.colors = colors;
    }
}
