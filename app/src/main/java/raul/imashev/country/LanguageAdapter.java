package raul.imashev.country;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import raul.imashev.country.data.Language;

public class LanguageAdapter extends RecyclerView.Adapter<LanguageAdapter.LanguageViewHolder> {

    List<Language> languages;
    public LanguageAdapter() {
        languages = new ArrayList<>();
    }

    @NonNull
    @Override
    public LanguageAdapter.LanguageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.language_item, parent, false);
        return new LanguageAdapter.LanguageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LanguageAdapter.LanguageViewHolder holder, int position) {
        Language language = languages.get(position);
        holder.textViewLanguageISODetail.setText(language.getIso639());
        holder.textViewLanguageNameDetail.setText(language.getName());

    }
    @Override
    public int getItemCount() {
        return languages.size();
    }


    public class LanguageViewHolder extends RecyclerView.ViewHolder {
        private TextView textViewLanguageISODetail;
        private TextView textViewLanguageNameDetail;


        public LanguageViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewLanguageISODetail = itemView.findViewById(R.id.textViewLanguageISODetail);
            textViewLanguageNameDetail = itemView.findViewById(R.id.textViewLanguageNameDetail);
        }
    }

    public List<Language> getLanguages() {
        return languages;
    }


    public void setLanguages(List<Language> languages) {
        this.languages = languages;
        notifyDataSetChanged();
    }
}
