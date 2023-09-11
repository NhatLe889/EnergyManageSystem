public class electronic {
    private int kilowatt_hour;

    //hourly use per month
    private int hourly_use;

    private int watts;

    private String electronicName;

    private int object_duplicate = 1;

    public electronic(String name){
        electronicName = name;
    }

    public electronic(int watt_rate, int hourly_use, String name) {
        this.electronicName = name;
        this.watts = watt_rate;
        this.hourly_use = hourly_use;
        kilowatt_hour = (watts * hourly_use) / 1000;
    }

    public void updateKwh()  {
        kilowatt_hour = (watts * hourly_use) / 1000;
    }

    void setKilowatt_rate(int rate) {
        this.watts = rate;
    }

    void setHourly_use(int hourly_use) {
        this.hourly_use = hourly_use;
    }

    public void setObject_duplicate(int object_duplicate) {
        this.object_duplicate = object_duplicate;
    }

    public int getHourly_use() {
        return this.hourly_use;
    }

    public int getKilowatt_hour() {
        return this.kilowatt_hour;
    }

    public String getElectronicName() {
        return this.electronicName;
    }

    public int getObject_duplicate() {
        return this.object_duplicate;
    }
}
