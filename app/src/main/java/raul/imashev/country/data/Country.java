package raul.imashev.country.data;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "countries")
public class Country {
    @PrimaryKey
    private int numericCode;
    private String name;
    private String flagPath;
    private String timezones;


    public Country(int numericCode, String name, String flagPath, String timezones) {
        this.numericCode = numericCode;
        this.name = name;
        this.flagPath = flagPath;
        this.timezones = timezones;
    }


    public int getNumericCode() {
        return numericCode;
    }

    public void setNumericCode(int numericCode) {
        this.numericCode = numericCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFlagPath() {
        return flagPath;
    }

    public void setFlagPath(String flagPath) {
        this.flagPath = flagPath;
    }

    public String getTimezones() {
        return timezones;
    }

    public void setTimezones(String timezones) {
        this.timezones = timezones;
    }
}
