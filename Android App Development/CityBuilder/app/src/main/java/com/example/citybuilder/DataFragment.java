package com.example.citybuilder;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.text.DecimalFormat;


public class DataFragment extends Fragment {

    private TextView month;
    private TextView year;
    private TextView money;
    private TextView income;
    private TextView pop;
    private TextView employ;
    private TextView cityName;
    private TextView temp;
    private GameData gameData;
    DataFragListener parentActivity;

    public interface DataFragListener{
        GameData getGameData();

    }

    public DataFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_data, container, false);
        parentActivity = (DataFragListener) this.getActivity();
        gameData = parentActivity.getGameData();
        month = v.findViewById(R.id.monthData);
        year = v.findViewById(R.id.yearData);
        money = v.findViewById(R.id.moneyData);
        income = v.findViewById(R.id.incomeData);
        pop = v.findViewById(R.id.populationData);
        employ = v.findViewById(R.id.employmentData);
        cityName = v.findViewById(R.id.cityData);
        temp = v.findViewById(R.id.temperatureData);

        updateAll(gameData);

        return v;
    }

    public void updateAll(GameData gameData){
        String txt = Integer.toString(gameData.getGameMonth());
        month.setText(txt);
        txt =  Integer.toString(gameData.getGameYear());
        year.setText(txt);
        txt =  Integer.toString(gameData.getCurrentMoney());
        money.setText(txt);
        txt =  Integer.toString(gameData.getIncome());
        income.setText(txt);
        txt =  Integer.toString(gameData.getPop());
        pop.setText(txt);

        // format employment to 2dp percentage
        float percentage = gameData.getEmploy()*100;
        txt = String.format("%.02f", percentage) + "%";
        employ.setText(txt);
        txt =  gameData.getCityName();
        cityName.setText(txt);
        DecimalFormat tempFormat = new DecimalFormat("##.##");
        txt =  tempFormat.format(gameData.getTemperature());
        temp.setText(txt);
    }

    public void updateMonth(){
        String txt = Integer.toString(gameData.getGameMonth());
        month.setText(txt);
    }

    public void updateYear(){
        String txt = Integer.toString(gameData.getGameYear());
        year.setText(txt);
    }

    public void updateMoneyText(){
        String txt =  Integer.toString(parentActivity.getGameData().getCurrentMoney());
        money.setText(txt);
    }
}