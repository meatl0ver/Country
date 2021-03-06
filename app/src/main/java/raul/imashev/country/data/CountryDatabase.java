package raul.imashev.country.data;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Country.class, Language.class, Currency.class}, version = 1, exportSchema = false)
public abstract class CountryDatabase extends RoomDatabase {

    private static final String DB_NAME = "countries.db";
    private static CountryDatabase database;
    private static final Object LOCK = new Object();

    public static CountryDatabase getInstance(Context context) {
        synchronized (LOCK) {
            if (database == null) {
                database = Room.databaseBuilder(context, CountryDatabase.class, DB_NAME).build();

            }
        }
        return database;
    }

    public abstract CountryDao countryDao();
    public abstract LanguageDao languageDao();
    public abstract CurrencyDao currencyDao();
}

