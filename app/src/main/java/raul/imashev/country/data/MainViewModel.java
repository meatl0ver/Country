package raul.imashev.country.data;

import android.app.Application;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class MainViewModel extends AndroidViewModel {

    private static CountryDatabase database;
    private LiveData<List<Country>> counties;
    private LiveData<List<Language>> languages;
    private LiveData<List<Currency>> currencies;

    public MainViewModel(@NonNull Application application) {
        super(application);
        database = CountryDatabase.getInstance(getApplication());
        counties = database.countryDao().getAllCountries();
        languages = database.languageDao().getAllLanguages();
        currencies = database.currencyDao().getAllCurrencies();;
    }

    public Country getCountryByNumericCode(int numericCode) {
        try {
            return new GetCountryTask().execute(numericCode).get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static class GetCountryTask extends AsyncTask<Integer, Void, Country> {
        @Override
        protected Country doInBackground(Integer... integers) {
            if (integers != null && integers.length > 0) {
                return database.countryDao().getCountryByNumericCode(integers[0]);
            }
            return null;
        }
    }

    public LiveData<List<Language>> getLanguageByNumericCode(int numericCode) {
        try {
            return new GetLanguageTask().execute(numericCode).get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }
    private static class GetLanguageTask extends AsyncTask<Integer, Void, LiveData<List<Language>>> {
        @Override
        protected LiveData<List<Language>> doInBackground(Integer... integers) {
            if (integers != null && integers.length > 0) {
                return database.languageDao().getLanguageByNumericCode(integers[0]);
            }
            return null;
        }
    }

    public LiveData<List<Currency>> getCurrencyByNumericCode(int numericCode) {
        try {
            return new GetCurrencyTask().execute(numericCode).get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }
    private static class GetCurrencyTask extends AsyncTask<Integer, Void, LiveData<List<Currency>>> {
        @Override
        protected LiveData<List<Currency>> doInBackground(Integer... integers) {
            if (integers != null && integers.length > 0) {
                return database.currencyDao().getCurrencyByNumericCode(integers[0]);
            }
            return null;
        }
    }

    public void deleteAllCountries() {
        new DeleteCountriesTask().execute();
    }

    private static class DeleteCountriesTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            database.countryDao().deleteAllCountries();
            return null;
        }
    }

    public void deleteAllLanguages() {
        new DeleteLanguagesTask().execute();
    }

    private static class DeleteLanguagesTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            database.languageDao().deleteAllLanguages();
            return null;
        }
    }

    public void deleteAllCurrencies() {
        new DeleteCurrenciesTask().execute();
    }

    private static class DeleteCurrenciesTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            database.currencyDao().deleteAllCurrencies();
            return null;
        }
    }





    public void insertCountry(Country country) {
        new InsertTask().execute(country);
    }

    private static class InsertTask extends AsyncTask<Country, Void, Void> {
        @Override
        protected Void doInBackground(Country... countries) {
            if (countries != null && countries.length > 0) {
                database.countryDao().insertCountry(countries[0]);
            }
            return null;
        }
    }

    public void insertLanguage(Language language) {
        new InsertLanguageTask().execute(language);
    }

    private static class InsertLanguageTask extends AsyncTask<Language, Void, Void> {
        @Override
        protected Void doInBackground(Language... languages) {
            if (languages != null && languages.length > 0) {
                database.languageDao().insertLanguage(languages[0]);
            }
            return null;
        }
    }

    public void insertCurrency(Currency currency) {
        new InsertCurrencyTask().execute(currency);
    }

    private static class InsertCurrencyTask extends AsyncTask<Currency, Void, Void> {
        @Override
        protected Void doInBackground(Currency... currencies) {
            if (currencies != null && currencies.length > 0) {
                database.currencyDao().insertCurrency(currencies[0]);
            }
            return null;
        }
    }



    public LiveData<List<Country>> getCountries() {
        return counties;
    }

    public LiveData<List<Language>> getLanguages() {
        return languages;
    }

    public LiveData<List<Currency>> getCurrencies() { return currencies; }


}
