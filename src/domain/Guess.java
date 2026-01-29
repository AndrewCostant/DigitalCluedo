package domain;

public class Guess {
    private SuspectC suspect;
    private WeaponC weapon;
    private RoomC room;

    public Guess(SuspectC suspect, WeaponC weapon, RoomC room) {
        this.suspect = suspect;
        this.weapon = weapon;
        this.room = room;
    }

    // GETTERS AND SETTERS
    public SuspectC getSuspect() {
        return suspect;
    }

    public void setSuspect(SuspectC suspect) {
        this.suspect = suspect;
    }

    public WeaponC getWeapon() {
        return weapon;
    }

    public void setWeapon(WeaponC weapon) {
        this.weapon = weapon;
    }

    public RoomC getRoom() {
        return room;
    }

    public void setRoom(RoomC room) {
        this.room = room;
    }

    public boolean isInHand(Card card) {
        if (this.suspect.equals(card)) {
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