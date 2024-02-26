package sample;

public class Card {

    private String value;
    private String type;

    public Card(String value, int type) {
        valueLoad(value);
        this.type = addType(type);
    }

    private String addType(int type){
        String typ;
        if (type == 0){
            typ = "C";
        }
        else if (type == 1){
            typ = "D";
        }
        else if (type == 2){
            typ = "H";
        }
        else typ = "S";
        return typ;
    }

    private void valueLoad(String value){
        if (value.equals("11")){
            value = "A";
        }
        if (value.equals("12")){
            value = "J";
        }
        if (value.equals("13")){
            value = "Q";
        }
        if (value.equals("14")){
            value= "K";
        }
        setValue(value);
    }

    private void setValue(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }

    public String getType() {
        return this.type;
    }

    private void setType(String type) {
        this.type = type;
    }
}