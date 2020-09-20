package raul.imashev.country;

import android.graphics.drawable.PictureDrawable;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.GenericRequestBuilder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.model.StreamEncoder;
import com.bumptech.glide.load.resource.file.FileToStreamDecoder;
import com.caverock.androidsvg.SVG;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import raul.imashev.country.SVG.SvgDecoder;
import raul.imashev.country.SVG.SvgDrawableTranscoder;
import raul.imashev.country.SVG.SvgSoftwareLayerSetter;
import raul.imashev.country.data.Country;


public class CountryAdapter extends RecyclerView.Adapter<CountryAdapter.CountryViewHolder> {

    List<Country> countries;
    private OnCountryClickListener onCountryClickListener;
    public CountryAdapter() {
        countries = new ArrayList<>();
    }

    interface OnCountryClickListener {
        void onCountryClick(int position);
    }

    @NonNull
    @Override
    public CountryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.country_item, parent, false);
        return new CountryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CountryViewHolder holder, int position) {
        Country country = countries.get(position);

        holder.textViewName.setText(country.getName());
        //Для загрузки изображений в формате SVG используются библиотеки androidsvg-aar И GLIDE
            GenericRequestBuilder<Uri, InputStream, SVG, PictureDrawable> requestBuilder = Glide.with(holder.imageViewFlag.getContext())
                    .using(Glide.buildStreamModelLoader(Uri.class, holder.imageViewFlag.getContext()), InputStream.class)
                    .from(Uri.class)
                    .as(SVG.class)
                    .transcode(new SvgDrawableTranscoder(), PictureDrawable.class)
                    .sourceEncoder(new StreamEncoder())
                    .cacheDecoder(new FileToStreamDecoder<SVG>(new SvgDecoder()))
                    .decoder(new SvgDecoder())
                    .listener(new SvgSoftwareLayerSetter<Uri>());
            requestBuilder.diskCacheStrategy(DiskCacheStrategy.NONE)
                    .load(Uri.parse(country.getFlagPath()))
                    .into(holder.imageViewFlag);
    }

    @Override
    public int getItemCount() {
        return countries.size();
    }

    class CountryViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageViewFlag;
        private TextView textViewName;

        public CountryViewHolder(@NonNull View itemView) {
            super(itemView);
            imageViewFlag = itemView.findViewById(R.id.imageViewFlag);
            textViewName = itemView.findViewById(R.id.textViewCountryName);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(onCountryClickListener != null) {
                        onCountryClickListener.onCountryClick(getAdapterPosition());
                    }
                }
            });
        }
    }

    public List<Country> getCountries() {
        return countries;
    }

    public void setCountries(List<Country> countries) {
        this.countries = countries;
        notifyDataSetChanged();
    }

    public void addCountries(List<Country> countries) {
        this.countries.addAll(countries);
        notifyDataSetChanged();
    }

    public void setOnCountryClickListener(OnCountryClickListener onCountryClickListener) {
        this.onCountryClickListener = onCountryClickListener;
    }
}
