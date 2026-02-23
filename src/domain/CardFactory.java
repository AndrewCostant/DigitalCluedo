package domain;

public class CardFactory {

    public static Card createCard(String type, String name) {
        switch(type.toLowerCase()) {
            case "weapon":
                return new WeaponC(name);

            case "room":
                return new RoomC(name);
            
            case "suspect":
                return new SuspectC(name);

            case "chance":
                try {
                    Class<?> clazz = Class.forName("domain." + name);
                    EffectStrategy effectStrategy = (EffectStrategy) clazz.getDeclaredConstructor().newInstance();
                    return new ChanceC(name, effectStrategy);
                } catch (Exception e) {
                    throw new RuntimeException("Invalid effect class: " + name, e);
                }
                
            default:
                throw new IllegalArgumentException("Illegal card type");
        }
    }
}
