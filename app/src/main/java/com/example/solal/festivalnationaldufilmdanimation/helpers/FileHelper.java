package com.example.solal.festivalnationaldufilmdanimation.helpers;

import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by sdussoutrevel on 15/12/2017.
 */

public class FileHelper {
    public static String readFile(String path, Context context ){
        try {
            FileInputStream in = context.openFileInput(path);
            InputStreamReader inputStreamReader = new InputStreamReader(in);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            StringBuilder sb = new StringBuilder();
            String line;
            try {
                while ((line = bufferedReader.readLine()) != null) {
                    sb.append(line);
                }
                return sb.toString();
            } catch(IOException e) {
                Log.d("Error", e.toString());
            }
        } catch(FileNotFoundException e) {
            Log.d("Error", e.toString());
        }
        return "";
    }

}
