package raul.imashev.country.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface CurrencyDao {
    @Query("SELECT * FROM currencies")
    LiveData<List<Currency>> getAllCurrencies();

    @Query("SELECT * FROM currencies WHERE numericCode=:numericCode")
    LiveData<List<Currency>> getCurrencyByNumericCode(int numericCode);

    @Query("DELETE FROM currencies")
    void deleteAllCurrencies();

    @Insert
    void insertCurrency(Currency currency);
}
