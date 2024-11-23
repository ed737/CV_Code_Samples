package com.example.citybuilder;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import java.util.ArrayList;
import static com.example.citybuilder.Constants.PLAYER_NAME;


public class SettingsFragment extends Fragment implements View.OnClickListener, AdapterView.OnItemSelectedListener{


    // widget fields
    private TextView outcomeText;
    private Button startNewGame;
    private ArrayList<EditText> editTexts;
    private EditText playerName, mapWidth, mapHeight, startMoney, familySize, shopSize, salary, taxRate,
                     serviceCost, houseCost, commercialCost, roadCost;
    private Spinner citySelect;
    // city selection for Australia
    private String[] cities = {"Perth", "Darwin", "Adelaide", "Melbourne", "Sydney", "Canberra", "Brisbane", "Hobart"};
    SettingsFragmentListener parentActivity;

    public interface SettingsFragmentListener{
        Settings getSettings();
        void startNewGame();
    }

    public SettingsFragment() {
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
        View view = inflater.inflate(R.layout.fragment_settings, container, false);


        // this arrayList to hold all edit text to save on code repetition in onClick
        editTexts = new ArrayList<>();

        // assign parent activity
        parentActivity = (SettingsFragmentListener) this.getActivity();

        // setup widgets
        outcomeText = view.findViewById(R.id.txt_input_error_settings);

        // reset the text in outcome Text
        outcomeText.setText("");

        // setup all edit text views
        playerName = view.findViewById(R.id.txtPlayerName);
        mapWidth = view.findViewById(R.id.txtMapWidth);
        mapHeight = view.findViewById(R.id.txtMapHeight);
        startMoney = view.findViewById(R.id.txtStartMoney);
        familySize = view.findViewById(R.id.txtFamilySize);
        shopSize = view.findViewById(R.id.txtShopSize);
        salary = view.findViewById(R.id.txtSalary);
        taxRate = view.findViewById(R.id.txtTaxRate);
        serviceCost = view.findViewById(R.id.txtServiceCost);
        houseCost = view.findViewById(R.id.txtHouseBuildingCost);
        commercialCost = view.findViewById(R.id.txtCommBuildingCost);
        roadCost = view.findViewById(R.id.txtRoadCost);

        // setup the city select spinner
        citySelect = view.findViewById(R.id.citySelect);
        citySelect.setOnItemSelectedListener(this);

        ArrayAdapter adapter = new ArrayAdapter(this.getContext(),android.R.layout.simple_spinner_item ,cities);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        citySelect.setAdapter(adapter);


        // add these edit texts to list for validation.
        editTexts.add(playerName);
        editTexts.add(mapWidth);
        editTexts.add(mapHeight);
        editTexts.add(startMoney);
        editTexts.add(familySize);
        editTexts.add(shopSize);
        editTexts.add(salary);
        editTexts.add(taxRate);
        editTexts.add(serviceCost);
        editTexts.add(houseCost);
        editTexts.add(commercialCost);
        editTexts.add(roadCost);

        // setup done button
        startNewGame = view.findViewById(R.id.button_settings_done);

        // set onClickListener for done button
        startNewGame.setOnClickListener(this);

        // disregard playerName for now.
        playerName.setText(PLAYER_NAME);
        playerName.setVisibility(View.GONE);

        return view;
    }

    /**********************************************************
     * switch to validate user input and display error messages
     * Called from onClick. Will update settings in parent activity
     * if validation passes
     **********************************************************/

    private boolean validateSettingsInput(EditText inEditText){
        boolean outcome = true;
        int id = inEditText.getId();
        String outcomeStr;

        int num;

        // check player name has been entered
        // TODO ensure player name does not already exist in db, duplicate will be overwitten. Not implemented in this version.
        if(id == R.id.txtPlayerName && inEditText.getText().equals("")){
            outcome = false;
            outcomeStr = "Please enter a name";
            outcomeText.setText(outcomeStr);
            playerName.setText("");

        }

        // check if current text has been changed.
        if(!inEditText.getText().toString().equals("")) {

            switch (id) {
                case R.id.txtMapWidth:
                    num = Integer.parseInt(mapWidth.getText().toString());
                    if (num > 50 || num < 30) {
                        outcome = false;
                        outcomeStr = "Map Width must be between 30 and 50";
                        outcomeText.setText(outcomeStr);
                        mapWidth.setText("");
                    }
                    else{
                        parentActivity.getSettings().setMapWidth(num);
                    }
                    break;

                case R.id.txtMapHeight:
                    num = Integer.parseInt(mapHeight.getText().toString());
                    if (num > 20 || num < 2) {
                        outcome = false;
                        outcomeStr = "Map Height must be between 2 and 20";
                        outcomeText.setText(outcomeStr);
                        mapHeight.setText("");
                    }
                    else{
                        parentActivity.getSettings().setMapHeight(num);
                    }
                    break;

                case R.id.txtStartMoney:
                    num = Integer.parseInt(startMoney.getText().toString());
                    if (num > 1000000 || num < 100) {
                        outcome = false;
                        outcomeStr = "Start Money must be between 100 and 1000000";
                        outcomeText.setText(outcomeStr);
                        startMoney.setText("");
                    }
                    else{
                        parentActivity.getSettings().setStartMoney(num);
                    }
                    break;

                case R.id.txtFamilySize:
                    num = Integer.parseInt(familySize.getText().toString());
                    if (num > 12 || num < 1) {
                        outcome = false;
                        outcomeStr = "Family Size must be between 1 and 12";
                        outcomeText.setText(outcomeStr);
                        familySize.setText("");
                    }
                    else{
                        parentActivity.getSettings().setFamilySize(num);
                    }
                    break;

                case R.id.txtShopSize:
                    num = Integer.parseInt(shopSize.getText().toString());
                    if (num > 10 || num < 5) {
                        outcome = false;
                        outcomeStr = "Shop Size must be between 5 and 10";
                        outcomeText.setText(outcomeStr);
                        shopSize.setText("");
                    }
                    else{
                        parentActivity.getSettings().setShopSize(num);
                    }
                    break;

                case R.id.txtSalary:
                    num = Integer.parseInt(salary.getText().toString());
                    if (num > 20 || num < 5) {
                        outcome = false;
                        outcomeStr = "Salary must be between 5 and 20";
                        outcomeText.setText(outcomeStr);
                        salary.setText("");
                    }
                    else{
                        parentActivity.getSettings().setSalary(num);
                    }
                    break;

                case R.id.txtTaxRate:
                    float f;
                    f = Float.parseFloat(taxRate.getText().toString());
                    if (f > 0.5f || f < 0.05f) {
                        outcome = false;
                        outcomeStr = "Tax rate must be between 0.05 and 0.5";
                        outcomeText.setText(outcomeStr);
                        taxRate.setText("");
                    }
                    else{
                        parentActivity.getSettings().setTaxRate(f);
                    }
                    break;

                case R.id.txtServiceCost:
                    num = Integer.parseInt(serviceCost.getText().toString());
                    if (num > 10 || num < 1) {
                        outcome = false;
                        outcomeStr = "Service cost must be between 1 and 10";
                        outcomeText.setText(outcomeStr);
                        serviceCost.setText("");
                    }
                    else{
                        parentActivity.getSettings().setServiceCost(num);
                    }
                    break;

                case R.id.txtHouseBuildingCost:
                    num = Integer.parseInt(houseCost.getText().toString());
                    if (num > 300 || num < 50) {
                        outcome = false;
                        outcomeStr = "House cost must be between 50 and 300";
                        outcomeText.setText(outcomeStr);
                        houseCost.setText("");
                    }
                    else{
                        parentActivity.getSettings().setHouseCost(num);
                    }
                    break;

                case R.id.txtCommBuildingCost:
                    num = Integer.parseInt(commercialCost.getText().toString());
                    if (num > 1000 || num < 250) {
                        outcome = false;
                        outcomeStr = "Commercial cost must be between 250 and 1000";
                        outcomeText.setText(outcomeStr);
                        commercialCost.setText("");
                    }
                    else{
                        parentActivity.getSettings().setCommercialCost(num);
                    }
                    break;

                case R.id.txtRoadCost:
                    num = Integer.parseInt(roadCost.getText().toString());
                    if (num > 50 || num < 10) {
                        outcome = false;
                        outcomeStr = "Road cost must be between 10 and 50";
                        outcomeText.setText(outcomeStr);
                        roadCost.setText("");
                    }
                    else{
                        parentActivity.getSettings().setRoadCost(num);
                    }
                    break;
            }
        }

        return outcome;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        parentActivity.getSettings().setCityName(cities[position]);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        parentActivity.getSettings().setCityName(cities[0]);
    }

    @Override
    public void onClick(View v) {

        boolean passFail = true;
        int ii = 0;

        // run all EditTexts through validation
        // if they pass they will be updated in mainActivities instance of settings
        while( ii < editTexts.size() && passFail){
            passFail = validateSettingsInput(editTexts.get(ii));
            ii++;
        }

        // if all pass update outcomeText to empty string
        if(passFail){
            outcomeText.setText("");
            parentActivity.startNewGame();
        }

    }
}