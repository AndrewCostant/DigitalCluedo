package domain;

public class CellFactory {
    public static Cell createCell(int x, int y, String type, String name) {
        switch(type.toLowerCase()) {
            case "normal":
                return new NormalCell(x, y);

            case "chance":
                return new ChanceCell(x, y);
            
            case "room":
                return new NormalRoom(x, y, name);

            case "secret_room":
                return new SecretPassageRoom(x, y, name);

            case "gambling":
                return new GamblingRoom(x, y, name);
 
            default:
                throw new IllegalArgumentException("Illegal card type");
        }
    }
}
