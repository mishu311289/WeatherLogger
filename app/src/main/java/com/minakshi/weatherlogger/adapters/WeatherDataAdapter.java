package com.minakshi.weatherlogger.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.minakshi.weatherlogger.R;
import com.minakshi.weatherlogger.Utils;
import com.minakshi.weatherlogger.model.WeatherReading;

import java.util.ArrayList;

public class WeatherDataAdapter extends RecyclerView.Adapter<WeatherDataAdapter.WeatherDataViewHolder> {

    Context context;
    ArrayList<WeatherReading> weatherDataList;

    public WeatherDataAdapter(Context context, ArrayList<WeatherReading> weatherData) {
        this.context = context;
        this.weatherDataList = weatherData;
    }

    @NonNull
    @Override
    public WeatherDataViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.weather_data_item, parent, false);
        return new WeatherDataViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WeatherDataViewHolder holder, int position) {
        WeatherReading weatherData = weatherDataList.get(position);

        holder.location.setText(weatherData.getLocation());
        holder.temperature.setText(String.valueOf(weatherData.getTemperature()));
        holder.timeStamp.setText(Utils.Companion.getDate(weatherData.getTimeStamp()));
        holder.humidity.setText("Humidity: "+ weatherData.getHumidity());
        holder.description.setText(weatherData.getDescription());
        holder.moreDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.details.getVisibility() == View.VISIBLE) {
                    holder.details.setVisibility(View.GONE);
                    holder.moreDetails.setText("More details");
                } else {
                    holder.details.setVisibility(View.VISIBLE);
                    holder.moreDetails.setText("Hide details");
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return weatherDataList.size();
    }

    public class WeatherDataViewHolder extends RecyclerView.ViewHolder{

        TextView location;
        TextView temperature;
        TextView timeStamp;
        RelativeLayout details;
        TextView description;
        TextView humidity;
        TextView moreDetails;

        public WeatherDataViewHolder(@NonNull View itemView) {
            super(itemView);

            location = itemView.findViewById(R.id.tvLocation);
            temperature = itemView.findViewById(R.id.tvTemp);
            timeStamp = itemView.findViewById(R.id.tvDate);
            details = itemView.findViewById(R.id.details);
            description = itemView.findViewById(R.id.tvDescription);
            humidity = itemView.findViewById(R.id.tvHumidity);
            moreDetails = itemView.findViewById(R.id.tvMoreDetails);
        }
    }
}
