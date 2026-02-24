package com.cluedo.domain;

public class RoomC extends Card {

    public RoomC() {
        super("Unnamed Room");
    }
    public RoomC(String name) {
        super(name);
    }

    @Override
    public String toString(){
        return "Room[" + this.getName() + "]";
    }
}