import java.util.ArrayList;
import java.util.List;

public class Player {
    private List<Card> hand;
    private String name;
    private List<Integer> book;

    public Player(String name) {
        this.name = name;
        hand = new ArrayList<>();
        book = new ArrayList<>();
    }

    public List<Card> getHand() {
        return hand;
    }

    public String getName() {
        return name;
    }

    public List<Integer> getBook() {
        return book;
    }
}
