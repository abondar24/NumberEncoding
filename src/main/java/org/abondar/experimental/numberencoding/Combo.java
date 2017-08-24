package org.abondar.experimental.numberencoding;

import java.util.ArrayList;
import java.util.List;

public class Combo {

    private String combo;
    private Integer pos;
    private List<String> cities;

    public Combo(String combo, int pos) {
        this.combo = combo;
        this.pos = pos;

        cities = new ArrayList<>();
    }


    public List<String> getCities() {
        return cities;
    }

    public void setCities(List<String> cities) {
        this.cities = cities;
    }

    public String getCombo() {
        return combo;
    }

    public void setCombo(String combo) {
        this.combo = combo;
    }

    public Integer getPos() {
        return pos;
    }

    public void setPos(Integer pos) {
        this.pos = pos;
    }

    @Override
    public String toString() {
        return "Combo{" +
                "combo='" + combo + '\'' +
                ", pos=" + pos +
                ", cities=" + cities +
                '}';
    }
}
