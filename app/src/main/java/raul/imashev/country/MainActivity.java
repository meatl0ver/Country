package raul.imashev.country;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Toast;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

import raul.imashev.country.data.Country;
import raul.imashev.country.data.Currency;
import raul.imashev.country.data.Language;
import raul.imashev.country.data.MainViewModel;
import raul.imashev.country.utils.JSONUtils;
import raul.imashev.country.utils.NetworkUtils;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private CountryAdapter countryAdapter;
    private MainViewModel viewModel;

    SharedPreferences prefs = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //SharedPrefferences для проверки на первый запуск
        prefs = getSharedPreferences("firstTimeRun", MODE_PRIVATE);

        //Подключение ViewModel для работы с данными
        viewModel = ViewModelProviders.of(this).get(MainViewModel.class);

        //Для recycler используется метод getColumnCount() который определяет количество столбцов изходя из размеров экрана
        recyclerView = findViewById(R.id.recyclerViewCountries);
        recyclerView.setLayoutManager(new GridLayoutManager(this, getColumnCount()));
        countryAdapter = new CountryAdapter();
        recyclerView.setAdapter(countryAdapter);

        countryAdapter.setOnCountryClickListener(new CountryAdapter.OnCountryClickListener() {
            @Override
            public void onCountryClick(int position) {
                Country country = countryAdapter.getCountries().get(position);
                //Во вторую активность отправлется код страны, из которого позже определяются другие данные для отображения
                Intent intent = new Intent(getApplicationContext(), DetailActivity.class);
                intent.putExtra("ISO", country.getNumericCode());
                startActivity(intent);
            }
        });

        //Подключение значений из бд в адаптер
        LiveData<List<Country>> countriesFromLiveData = viewModel.getCountries();
        countriesFromLiveData.observe(this, new Observer<List<Country>>() {
            @Override
            public void onChanged(@Nullable List<Country> countries) {
                countryAdapter.setCountries(countries);
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        //Если приложение запущено в первый раз, происходит загружка данных
        if (prefs.getBoolean("firstrun", true)) {
            downloadData();
            prefs.edit().putBoolean("firstrun", false).apply();
        }
    }

    //Метод для загрузки данных и обновления данных
    private void downloadData() {
        //Проверка на подключение к интернету
        if(!isOnline()) {
            Toast.makeText(this, "Internet connection required", Toast.LENGTH_LONG).show();
        } else {
            //Метод для загрузки JSON массива
            JSONArray jsonArray = NetworkUtils.getJSONFromNetwork();
            //Методы создают объекты классов из JSON массива
            ArrayList<Country> countries = JSONUtils.getCountriesFromJSON(jsonArray);
            ArrayList<Language> languages = JSONUtils.getLanguagesFromJSON(jsonArray);
            ArrayList<Currency> currencies = JSONUtils.getCurrenciesFromJSON(jsonArray);
            //В случае если база уже создана, происходит удаление существующих элементов и затем, добавление новых
            if (!countries.isEmpty()) {
                viewModel.deleteAllCountries();
                for (Country country : countries) {
                    viewModel.insertCountry(country);
                }
            }
            if (!languages.isEmpty()) {
                viewModel.deleteAllLanguages();
                for (Language language : languages) {
                    viewModel.insertLanguage(language);
                }
            }
            if (!currencies.isEmpty()) {
                viewModel.deleteAllCurrencies();
                for (Currency currency : currencies) {
                    viewModel.insertCurrency(currency);
                }
            }
            Toast.makeText(this, "Data downloaded successfully", Toast.LENGTH_LONG).show();
        }
    }

    //При нажатии на кнопку происходит обновление данных
    public void onClickFloatingActionButton(View view) {
        downloadData();
    }

    //Метод для проверки подключения к сети
    protected boolean isOnline() {
        boolean result;
        ConnectivityManager cm = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        result = cm.getActiveNetworkInfo() != null;
        return result;
    }

    //Метод для подсчета количества столбцов в recycler
    private int getColumnCount() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int width = (int) (displayMetrics.widthPixels / displayMetrics.density);
        return Math.max(width / 185, 2);
    }
}