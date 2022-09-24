import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;

public class Companies {

    private int id;
    private String name;
    private String address;
    private String phoneNumber;
    private String inn;
    private LocalDate founded;
    private ArrayList <Securities> securities;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getInn() {
        return inn;
    }


    public LocalDate getFounded() {
        return founded;
    }



    public ArrayList<Securities> getSecurities() {
        return securities;
    }

    public void setSecurities(ArrayList<Securities> securities) {
        this.securities = securities;
    }
}
