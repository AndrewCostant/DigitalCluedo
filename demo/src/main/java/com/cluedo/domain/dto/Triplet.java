package com.cluedo.domain.dto;

import com.cluedo.domain.*;

public record Triplet(SuspectC suspectPerson, WeaponC weapon, RoomC room) {
    public boolean isInHand(Card card) {
        if (this.suspectPerson.equals(card)) {
            return true;
        } 
        else if (this.weapon.equals(card)) {
            return true;
        }
        else if (this.room.equals(card)) {
            return true;
        }
        return false;
    }

    public boolean equals(Triplet other) {
        return this.suspectPerson.getName().equals(other.suspectPerson.getName()) && this.weapon.getName().equals(other.weapon.getName()) && this.room.getName().equals(other.room.getName());
    }
    
}
