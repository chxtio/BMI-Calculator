package com.bmicalculator.Models;

import java.util.Random;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class bmi {
    double b;
    String risk;
//    String[] more;  = {"https://www.cdc.gov/healthyweight/assessing/bmi/index.html", "https://www.nhlbi.nih.gov/health/educational/lose_wt/index.htm", "https://www.ucsfhealth.org/education/body_mass_index_tool/"};
    JSONArray more;

    public bmi(JSONObject jsonObject) throws JSONException {
        b = jsonObject.getDouble("bmi");
        risk = jsonObject.getString("risk");
        more = jsonObject.getJSONArray("more");
    }

    public double getBMI() {
        return b;
    }

    public String getRisk() {
        return risk;
    }

    public String getMore() {
        Random rand = new Random();

        try {
            return (String) more.get(rand.nextInt(3));
        } catch (JSONException e) {
            e.printStackTrace();
            return "";
        }
//        return more[rand.nextInt(3)];
    }
}
