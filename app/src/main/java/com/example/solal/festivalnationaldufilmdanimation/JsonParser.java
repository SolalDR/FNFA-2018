package com.example.solal.festivalnationaldufilmdanimation;

import android.app.Activity;
import android.content.Context;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by sdussoutrevel on 12/12/2017.
 */

public class JsonParser extends Activity {

    public static String getStringFromJson(Integer path, Context context) {
        StringBuffer sb = new StringBuffer();
        BufferedReader br = null;
        try {
            br = new BufferedReader(new InputStreamReader(context.getResources().openRawResource(path)));
            String temp;
            while ((temp = br.readLine()) != null)
                sb.append(temp);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if( br != null) {
                    br.close(); // stop reading
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return sb.toString();
    }

}


