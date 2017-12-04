package com.example.mihovil.digitalnomad.files;

import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

/**
 * Created by Mihovil on 29.11.2017..
 */

public class UserToJsonFile extends JSONObject {
    private String name;
    private String email;
    private int id;
    private String url;
    private JSONObject object;
    private Context context;

    public JSONObject getObject() {
        return object;
    }

    public UserToJsonFile(String name, String email, int id, String url, Context c) {
        object = new JSONObject();
        this.name = name;
        this.email = email;
        this.id = id;
        this.url = url;
        this.context = c;
    }

    public void makeJSONObject() throws JSONException {
        object.put("name", name);
        object.put("email", email);
        object.put("id", id);
        object.put("url", url);
    }

    public void SaveToFile() throws IOException {
        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput("userJson.json", Context.MODE_PRIVATE));
        outputStreamWriter.write(object.toString());
        outputStreamWriter.close();
    }

    public static String ReadFromFile(Context c) {

        String ret = "";

        try {
            InputStream inputStream = c.openFileInput("userJson.json");

            if (inputStream != null) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";
                StringBuilder stringBuilder = new StringBuilder();

                while ((receiveString = bufferedReader.readLine()) != null) {
                    stringBuilder.append(receiveString);
                }

                inputStream.close();
                ret = stringBuilder.toString();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ret;
    }
}
