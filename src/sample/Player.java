package sample;

import java.util.ArrayList;


public class Player {
    protected int eso;
    protected int value;
    protected ArrayList<Card> cards;

    public Player(int eso, int value) {
        this.eso = eso;
        this.value = value;
        this.cards = new ArrayList<>();
    }

    public int getEso() {
        return this.eso;
    }

    public void setEso(int eso) {
        this.eso = eso;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public void clearCards() {
        cards.clear();
        this.value = 0;
        this.eso = 0;
    }

    public void testEso() {
        while (this.value > 21 && this.eso > 0){
            this.value = this.value -10;
            setEso(getEso()-1);
        }
    }

    public ArrayList<Card> getCards() {
        return cards;
    }

    public void addCard(Card card){
        cards.add(card);
    }
}
