package raul.imashev.country.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface LanguageDao {
    @Query("SELECT * FROM languages")
    LiveData<List<Language>> getAllLanguages();

    @Query("SELECT * FROM languages WHERE numericCode=:numericCode")
    LiveData<List<Language>> getLanguageByNumericCode(int numericCode);

    @Query("DELETE FROM languages")
    void deleteAllLanguages();

    @Insert
    void insertLanguage(Language language);
}
