package raul.imashev.country.data;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "languages")
public class Language {

    @PrimaryKey(autoGenerate = true)
    private Long id;

    private int numericCode;
    private String iso639;
    private String name;

    public Language(Long id, int numericCode, String iso639, String name) {
        this.id = id;
        this.numericCode = numericCode;
        this.iso639 = iso639;
        this.name = name;
    }

    public int getNumericCode() {
        return numericCode;
    }

    public void setNumericCode(int numericCode) {
        this.numericCode = numericCode;
    }

    public String getIso639() {
        return iso639;
    }

    public void setIso639(String iso639) {
        this.iso639 = iso639;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
