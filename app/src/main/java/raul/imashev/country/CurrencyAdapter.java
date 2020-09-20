package raul.imashev.country;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import raul.imashev.country.data.Currency;

public class CurrencyAdapter extends RecyclerView.Adapter<CurrencyAdapter.CurrencyViewHolder> {

    List<Currency> currencies;
    public CurrencyAdapter() {
        currencies = new ArrayList<>();
    }

    @NonNull
    @Override
    public CurrencyAdapter.CurrencyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.currency_item, parent, false);
        return new CurrencyAdapter.CurrencyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CurrencyAdapter.CurrencyViewHolder holder, int position) {
        Currency currency = currencies.get(position);
        holder.textViewCurrencyCodeDetail.setText(currency.getCode());
        holder.textViewCurrencyNameDetail.setText(currency.getName());
        holder.textViewCurrencySymbolDetail.setText(currency.getSymbol());

    }
    @Override
    public int getItemCount() {
        return currencies.size();
    }

    public class CurrencyViewHolder extends RecyclerView.ViewHolder {
        private TextView textViewCurrencyCodeDetail;
        private TextView textViewCurrencyNameDetail;
        private TextView textViewCurrencySymbolDetail;

        public CurrencyViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewCurrencyCodeDetail = itemView.findViewById(R.id.textViewCurrencyCodeDetail);
            textViewCurrencyNameDetail = itemView.findViewById(R.id.textViewCurrencyNameDetail);
            textViewCurrencySymbolDetail = itemView.findViewById(R.id.textViewCurrencySymbolDetail);
        }
    }

    public void setCurrencies(List<Currency> currencies) {
        this.currencies = currencies;
        notifyDataSetChanged();
    }
}
