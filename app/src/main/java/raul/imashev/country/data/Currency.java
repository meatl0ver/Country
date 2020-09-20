package raul.imashev.country.data;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "currencies")
public class Currency {

    @PrimaryKey(autoGenerate = true)
    private Long id;

    private int numericCode;
    private String code;
    private String name;
    private String symbol;


    public Currency(Long id, int numericCode, String code, String name, String symbol) {
        this.id = id;
        this.numericCode = numericCode;
        this.code = code;
        this.name = name;
        this.symbol = symbol;
    }

    public int getNumericCode() {
        return numericCode;
    }

    public void setNumericCode(int numericCode) {
        this.numericCode = numericCode;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
