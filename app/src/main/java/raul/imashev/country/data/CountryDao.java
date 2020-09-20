package raul.imashev.country.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface CountryDao {
    @Query("SELECT * FROM countries")
    LiveData<List<Country>> getAllCountries();

    @Query("SELECT * FROM countries WHERE numericCode == :countryNumericCode")
    Country getCountryByNumericCode(int countryNumericCode);

    @Query("DELETE FROM countries")
    void deleteAllCountries();

    @Insert
    void insertCountry(Country country);
}
