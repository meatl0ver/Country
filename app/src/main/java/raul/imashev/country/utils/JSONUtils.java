package raul.imashev.country.utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import raul.imashev.country.data.Country;
import raul.imashev.country.data.Currency;
import raul.imashev.country.data.Language;

public class JSONUtils {


    private static final String KEY_NAME = "name";
    private static final String KEY_FLAG = "flag";
    private static final String KEY_NUMERIC_CODE = "numericCode";
    private static final String KEY_TIMEZONES = "timezones";
    private static final String KEY_LANGUAGES = "languages";
    private static final String KEY_ISO639 = "iso639_1";
    private static final String KEY_CURRENCIES = "currencies";
    private static final String KEY_CODE = "code";
    private static final String KEY_SYMBOL = "symbol";


    public static ArrayList<Country> getCountriesFromJSON(JSONArray jsonArray) {
        ArrayList<Country> countries = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String flag = jsonObject.getString(KEY_FLAG);
                String name = jsonObject.getString(KEY_NAME);
                int numericCode = jsonObject.getInt(KEY_NUMERIC_CODE);
                JSONArray timezonesJson = jsonObject.getJSONArray(KEY_TIMEZONES);
                StringBuilder timezonesBuilder = new StringBuilder();
                for (int j = 0; j < timezonesJson.length(); j++) {
                    timezonesBuilder.append(timezonesJson.get(j)).append("\n");
                }
                String timezones = timezonesBuilder.toString();
                countries.add(new Country(numericCode, name, flag, timezones));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return countries;
    }

    public static ArrayList<Language> getLanguagesFromJSON(JSONArray jsonArray) {
        ArrayList<Language> languages = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                JSONArray languagesJSON = jsonObject.getJSONArray(KEY_LANGUAGES);
                for (int j = 0; j < languagesJSON.length(); j++) {
                    JSONObject lang = languagesJSON.getJSONObject(j);
                    String iso639 = lang.getString(KEY_ISO639);
                    String langName = lang.getString(KEY_NAME);
                    int numericCode = jsonObject.getInt(KEY_NUMERIC_CODE);
                    languages.add(new Language(null, numericCode, iso639, langName));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return languages;
    }

    public static ArrayList<Currency> getCurrenciesFromJSON(JSONArray jsonArray) {
        ArrayList<Currency> currencies = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                JSONArray CurrenciesJSON = jsonObject.getJSONArray(KEY_CURRENCIES);
                for (int j = 0; j < CurrenciesJSON.length(); j++) {
                    JSONObject cur = CurrenciesJSON.getJSONObject(j);
                    String code = cur.getString(KEY_CODE);
                    String name = cur.getString(KEY_NAME);
                    String symbol = cur.getString(KEY_SYMBOL);
                    int numericCode = jsonObject.getInt(KEY_NUMERIC_CODE);
                    currencies.add(new Currency(null, numericCode,code,name,symbol));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return currencies;
    }
}
