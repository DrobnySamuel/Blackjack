package sample;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class Money {

    private Double sum;
    private SimpleDateFormat format;
    private String date;

    public Money(){
        format =  new SimpleDateFormat("dd.MM.yyyy");
        date = format.format(new Date());
    }

    public void loadMoney(){
        try {
            Scanner sc = new Scanner(new File("money.dat"));
            if (sc.hasNext()){
                this.sum = Double.parseDouble(sc.nextLine());
                sc.close();
            }
            else setSum(0.0);
        } catch (FileNotFoundException e) {
            System.out.println("file peniaze.txt does not exist\ncreating new file");
            setSum(0.0);
            write();
        }
    }

    public Double getSum() {
        return sum;
    }

    public void setSum(Double sum) {
        this.sum = sum;
    }

    public void youLost(Double s)  {
        try {
            BufferedWriter wr = new BufferedWriter(new FileWriter("database.dat",true));
            wr.write(date + "-Prehra-"+s+"\n");
            wr.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void youWon(Double s)  {
        try {
            BufferedWriter wr = new BufferedWriter(new FileWriter("database.dat",true));
            wr.write(date + "-Výhra-"+s+"\n");
            wr.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void win(Double strike) {
        setSum(getSum()+strike);
        youWon(strike);
        write();
    }

    public void loss(Double strike) {
        setSum(getSum()-strike);
        youLost(strike);
        write();
    }

    public void giveUp(Double strike){
        setSum(getSum()-(strike/2));
        youLost(strike/2);
        write();
    }

    public String getDate() {
        return date;
    }

    private void write() {
        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("money.dat"));
            bufferedWriter.write(getSum().toString());
            bufferedWriter.close();
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
}
