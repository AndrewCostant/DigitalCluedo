package domain.dto;

import domain.*;

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
    
}
