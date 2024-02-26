package sample;

public class Row {

    private String date, event, sum;

    public Row(String date, String event, String sum) {
        this.date = date;
        this.event = event;
        this.sum = sum;
    }

    public String getDate() {
        return date;
    }

    public String getEvent() {
        return event;
    }

    public String getSum() {
        return sum;
    }
}
