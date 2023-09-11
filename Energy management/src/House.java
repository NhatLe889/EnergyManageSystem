import java.util.*;

public class House {

    //units in kWh
    private int energy_usage_monthly;

    private int num_room;
    private int num_people;

    private int num_electronic = 0;

    private ExternalChainingHashMap<String, electronic> devices;

    private ArrayList<String> itemList;

    //units in square feet
    private int house_size;

    public House() {
        devices = new ExternalChainingHashMap<>();
        itemList = new ArrayList<>();
    }


    House(int energy_usage_monthly, int num_room, int num_people, int num_electronic, int house_size) {
        this.energy_usage_monthly = energy_usage_monthly;
        this.num_room = num_room;
        this.num_people = num_people;
        this.num_electronic = num_electronic;
        this.house_size = house_size;
    }

    public int[] most_used_devices() {
        int[] valueArr = new int[num_electronic];
        String[] keyArr = new String[num_electronic];
        for (int m = 0; m < num_electronic; m++) {
            valueArr[m] = (devices.get(itemList.get(m))).getHourly_use();
            keyArr[m] = itemList.get(m);
        }
        boolean checker = true;
        while (checker) {
            checker = false;
            for (int j = 0; j < valueArr.length - 1; j++) {
                if (valueArr[j] < valueArr[j+1]) {
                    checker = true;
                    int temp = valueArr[j];
                    valueArr[j] = valueArr[j+1];
                    valueArr[j+1] = temp;

                    String tempString = keyArr[j];
                    keyArr[j] = keyArr[j+1];
                    keyArr[j+1] = tempString;
                }
            }
        }
        int[] highestValue = new int[num_electronic];
        if (valueArr[0] > valueArr[1]) {
            System.out.println("The item with highest hourly used is " + keyArr[0]);
            highestValue[0] = valueArr[0];
        } else {
            // start with 0.
            int bigRange;
            int u = 1;
            while (valueArr[0] == valueArr[u]) {
                u++;
                if (u >= valueArr.length) {
                    break;
                }
            }
            bigRange = u;
            String[] highHourList = new String[bigRange];
            for (int i = 0; i < bigRange; i++) {
                highHourList[i] = keyArr[i];
                highestValue[i] = valueArr[i];
            }
            System.out.println("The items with highest hourly used is: ");
            for (int i = 0; i < highHourList.length; i++) {
                System.out.println(highHourList[i]);
            }
        }
        return highestValue;
    }

    public int[] highest_kilowatt_hour() {
        int[] kilowattHourArr = new int[num_electronic];
        String[] keyArr = new String[num_electronic];
        for (int m = 0; m < num_electronic; m++) {
            kilowattHourArr[m] = devices.get(itemList.get(m)).getKilowatt_hour();
            keyArr[m] = itemList.get(m);
        }
        boolean checker = true;
        while (checker) {
            checker = false;
            for (int j = 0; j < kilowattHourArr.length - 1; j++) {
                if (kilowattHourArr[j] < kilowattHourArr[j+1]) {
                    checker = true;
                    int temp = kilowattHourArr[j];
                    kilowattHourArr[j] = kilowattHourArr[j+1];
                    kilowattHourArr[j+1] = temp;

                    String tempString = keyArr[j];
                    keyArr[j] = keyArr[j+1];
                    keyArr[j+1] = tempString;
                }
            }
        }
        int[] highestKilowattHour = new int[num_electronic];
        if (kilowattHourArr[0] > kilowattHourArr[1]) {
            System.out.println("The item with highest kilowatt hour used is " + keyArr[0]);
            highestKilowattHour[0] = kilowattHourArr[0];
        } else {
            // start with 0.
            int bigRange;
            int u = 1;
            while (kilowattHourArr[0] == kilowattHourArr[u]) {
                u++;
            }
            bigRange = u;
            String[] highWattList = new String[bigRange];
            for (int i = 0; i <= bigRange; i++) {
                highWattList[i] = keyArr[i];
                highestKilowattHour[i] = kilowattHourArr[i];
            }
            System.out.println("The items with highest kilowatt hour used is: ");
            for (int i = 0; i < highWattList.length; i++) {
                System.out.println(highWattList[i]);
            }
        }
        return highestKilowattHour;
    }

    public void addDevices(electronic device) {
        if (devices.containsKey(device.getElectronicName())) {
            Scanner input = new Scanner(System.in);
            System.out.println(device.getElectronicName() + " already exist, would you like add duplicate?");
            String answer = input.nextLine();
            if (answer.equals("Yes") || answer.equals("yes")) {
                addAdditional(device);
            }
        } else {
            devices.put(device.getElectronicName(), device);
            itemList.add(device.getElectronicName());
            num_electronic++;
        }
    }

    public electronic getDevice(String name) {
        return devices.get(name);
    }

    public void addAdditional(electronic device) {
        if (devices.containsKey(device.getElectronicName())) {
            int current_duplicate_value = devices.get(device.getElectronicName()).getObject_duplicate();
            devices.get(device.getElectronicName()).setObject_duplicate(current_duplicate_value + 1);
        } else {
            devices.put(device.getElectronicName(), device);
            itemList.add(device.getElectronicName());
            num_electronic++;
        }
    }

    public void addKiloRate(String name, int rate) {
        if (devices.containsKey(name)) {
            devices.get(name).setKilowatt_rate(rate);
            devices.get(name).updateKwh();
        } else {
            System.out.println(name + " does not exist");
        }
    }

    public void addHourUse(String name, int hour) {
        if (devices.containsKey(name)) {
            devices.get(name).setHourly_use(hour);
            devices.get(name).updateKwh();
        } else {
            System.out.println(name + " does not exist");
        }
    }

    public void generalData() {
        int kWh_per_person = energy_usage_monthly / num_people;
        int average_kWh_per_device = energy_usage_monthly / num_electronic;
        int average_kWh_per_square_feet = energy_usage_monthly / house_size;
        int kWh_per_day = energy_usage_monthly / 365;

        System.out.println("####################################################################");
        System.out.println("kWh used per person: " + kWh_per_person);
        System.out.println("####################################################################");
        System.out.println("average kWh used per device: " + average_kWh_per_device);
        System.out.println("####################################################################");
        System.out.println("average kWh per square feet: " + average_kWh_per_square_feet);
        System.out.println("The U.S average per square feet is 0.45 kWh");
        System.out.println("####################################################################");
        System.out.println("kWh used per day: " + kWh_per_day);
        System.out.println("The U.S average per day is 28.9 kWh");
        System.out.println("####################################################################");
    }


    void setEnergy_usage_monthly(int energy_usage_monthly) {
        this.energy_usage_monthly = energy_usage_monthly;
    }

    void setNum_room(int num_room) {
        this.num_room = num_room;
    }

    void setNum_people(int num_people) {
        this.num_people = num_people;
    }

    void setNum_electronic(int num_electronic) {
        this.num_electronic = num_electronic;
    }

    void setHouse_size(int house_size) {
        this.house_size = house_size;
    }

    public int getEnergy_usage_monthly() {
        return energy_usage_monthly;
    }

    public int getNum_room() {
        return num_room;
    }

    public int getNum_people() {
        return num_people;
    }

    public int getNum_electronic() {
        return num_electronic;
    }

    public int getHouse_size() {
        return house_size;
    }
}
