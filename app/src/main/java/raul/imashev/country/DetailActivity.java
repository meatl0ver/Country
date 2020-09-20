package raul.imashev.country;

import android.content.Intent;
import android.graphics.drawable.PictureDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.GenericRequestBuilder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.model.StreamEncoder;
import com.bumptech.glide.load.resource.file.FileToStreamDecoder;
import com.caverock.androidsvg.SVG;

import java.io.InputStream;
import java.util.List;

import raul.imashev.country.SVG.SvgDecoder;
import raul.imashev.country.SVG.SvgDrawableTranscoder;
import raul.imashev.country.SVG.SvgSoftwareLayerSetter;
import raul.imashev.country.data.Country;
import raul.imashev.country.data.Currency;
import raul.imashev.country.data.Language;
import raul.imashev.country.data.MainViewModel;

public class DetailActivity extends AppCompatActivity {

    private TextView textViewCountryNameDetail;
    private TextView textViewTimezonesDetail;
    private ImageView imageViewFlagDetail;
    private MainViewModel viewModel;

    private RecyclerView recyclerViewLanguages;
    private RecyclerView recyclerViewCurrencies;

    private int iso;
    private Country country;
    private LanguageAdapter languageAdapter;
    private CurrencyAdapter currencyAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        //Подключение ViewModel для работы с данными
        viewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        textViewCountryNameDetail = findViewById(R.id.textViewCountryNameDetail);
        textViewTimezonesDetail = findViewById(R.id.textViewTimezonesDetailData);
        imageViewFlagDetail = findViewById(R.id.imageViewFlagDetail);
        recyclerViewLanguages = findViewById(R.id.recyclerViewLanguages);
        recyclerViewLanguages.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewCurrencies = findViewById(R.id.recyclerViewCurrencies);
        recyclerViewCurrencies.setLayoutManager(new LinearLayoutManager(this));

        //Получение интента с кодом страны
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("ISO")) {
            iso = intent.getIntExtra("ISO", -1);
        } else {
            finish();
        }
        country = viewModel.getCountryByNumericCode(iso);
        //Назначение значений с данными страны для TextView с именем и часовыми поясами
        textViewCountryNameDetail.setText(country.getName());
        textViewTimezonesDetail.setText(country.getTimezones());

        languageAdapter = new LanguageAdapter();
        recyclerViewLanguages.setAdapter(languageAdapter);
        currencyAdapter = new CurrencyAdapter();
        recyclerViewCurrencies.setAdapter(currencyAdapter);

        //Подключение значений из бд в адаптер
        LiveData<List<Language>> languagesFromLiveData = viewModel.getLanguageByNumericCode(iso);
        languagesFromLiveData.observe(this, new Observer<List<Language>>() {
            @Override
            public void onChanged(@Nullable List<Language> languages) {
                languageAdapter.setLanguages(languages);
            }
        });
        LiveData<List<Currency>> currenciesFromLiveData = viewModel.getCurrencyByNumericCode(iso);
        currenciesFromLiveData.observe(this, new Observer<List<Currency>>() {
            @Override
            public void onChanged(@Nullable List<Currency> currencies) {
                currencyAdapter.setCurrencies(currencies);
            }
        });
        //Для загрузки изображений в формате SVG используются библиотеки androidsvg-aar И GLIDE
        GenericRequestBuilder<Uri, InputStream, SVG, PictureDrawable> requestBuilder = Glide.with(this)
                .using(Glide.buildStreamModelLoader(Uri.class, this), InputStream.class)
                .from(Uri.class)
                .as(SVG.class)
                .transcode(new SvgDrawableTranscoder(), PictureDrawable.class)
                .sourceEncoder(new StreamEncoder())
                .cacheDecoder(new FileToStreamDecoder<SVG>(new SvgDecoder()))
                .decoder(new SvgDecoder())
                .listener(new SvgSoftwareLayerSetter<Uri>());

        requestBuilder.diskCacheStrategy(DiskCacheStrategy.NONE)
                .load(Uri.parse(country.getFlagPath()))
                .into(imageViewFlagDetail);
    }
}