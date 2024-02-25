package edu.hogwarts.studentadmin.models;

public enum SchoolYear {
    ZERO_YEAR,
    FIRST_YEAR,
    SECOND_YEAR,
    THIRD_YEAR,
    FOURTH_YEAR,
    FIFTH_YEAR,
    SIXTH_YEAR,
    SEVENTH_YEAR;

    public SchoolYear getNextYear() {
        int nextOrdinal = this.ordinal() + 1;
        if (nextOrdinal < SchoolYear.values().length) {
            return SchoolYear.values()[nextOrdinal];
        } else {
            return null;
        }
    }
}

