package com.xebia.tennistest.util;

import javax.persistence.Embeddable;


@Embeddable
public class BracketPosition {

    private Integer round;

    private Integer position;

    public BracketPosition() {
    }

    public BracketPosition(Integer round, Integer position) {
        this.round = round;
        this.position = position;
    }

    public Integer getRound() {
        return round;
    }

    public Integer getPosition() {
        return position;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BracketPosition that = (BracketPosition) o;

        if (!round.equals(that.round)) return false;
        return position.equals(that.position);
    }

    @Override
    public int hashCode() {
        int result = round.hashCode();
        result = 31 * result + position.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "[" + round + "/" + position + "]";
    }
}
