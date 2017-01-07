package mobilecourse.sapp;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Vector;

import org.json.*;


/**
 * Created by tord on 2017-01-06.
 */

public class jsonHandler {

    public void setGeneralSettings(File file, int notifications, int timer, int mute) throws Exception{
        String list = "{\"reservations\":[";
        BufferedReader br = new BufferedReader(new FileReader(file));
        String line;
        String jsonLine = "";
        while((line = br.readLine()) != null){
            jsonLine += line;
        }
        br.close();
        JSONObject jsonObject = new JSONObject(jsonLine);
        JSONArray jsonArray =  jsonObject.getJSONArray("reservations");
        String obj;

        for(int i=0; i < jsonArray.length(); i++){
            if(((JSONObject)jsonArray.get(i)).getString("followGenSett").equals("0")){
                obj = jsonArray.get(i).toString();
            }else{
                obj = jsonArray.get(i).toString();
                obj = obj.substring(0,obj.indexOf("\"notifications\""));
                obj += "\"notifications\":" + "\""+notifications+"\",";
                obj += "\"noificationTimer\":" + "\""+timer+"\",";
                obj += "\"mute\":" + "\""+mute+"\"}";
            }
            list += obj;
            if(i != jsonArray.length()-1){
                list += ",";
            }

        }
        list += "]}";
        BufferedWriter bw = new BufferedWriter(new FileWriter(file));
        bw.write(list);
        bw.close();
    }

    public Vector<String> getGeneralSettings(File file) throws Exception{
        Vector<String> returnValue = new Vector<>();
        BufferedReader br = new BufferedReader(new FileReader(file));
        String line;
        String jsonLine = "";
        while((line = br.readLine()) != null){
            jsonLine += line;
        }
        br.close();
        JSONObject jsonObject = new JSONObject(jsonLine);
        JSONArray jsonArray = jsonObject.getJSONArray("reservations");
        for(int i =0; i < jsonArray.length(); i++){
            if(((JSONObject)jsonArray.get(i)).getString("followGenSett").equals("1")){
                returnValue.add(((JSONObject)jsonArray.get(i)).getString("notifications"));
                returnValue.add(((JSONObject)jsonArray.get(i)).getString("noificationTimer"));
                returnValue.add(((JSONObject)jsonArray.get(i)).getString("mute"));
            }
        }

        return returnValue;

    }

    public void changeSpecificObject(scheduleInformation sch,File file) throws Exception{
        String list = "{\"reservations\":[";
        BufferedReader br = new BufferedReader(new FileReader(file));
        String line;
        String jsonLine = "";
        while((line = br.readLine()) != null){
            jsonLine += line;
        }
        br.close();
        JSONObject jsonObject = new JSONObject(jsonLine);
        JSONArray jsonArray =  jsonObject.getJSONArray("reservations");
        String obj;

        for(int i=0; i < jsonArray.length(); i++){
            if(((JSONObject)jsonArray.get(i)).getString("course").equals(sch.getCourseFull())&&((JSONObject)jsonArray.get(i)).getString("person").equals(sch.getPerson())&&((JSONObject)jsonArray.get(i)).getString("room").equals(sch.getRoomFull())
                    &&((JSONObject)jsonArray.get(i)).getString("startdate").equals(sch.getDate())&&((JSONObject)jsonArray.get(i)).get("starttime").equals(sch.getStartTime()) &&((JSONObject)jsonArray.get(i)).get("endtime").equals(sch.getEndTime())){
                obj = jsonArray.get(i).toString();
            }else{
                obj = jsonArray.get(i).toString();
                obj = obj.substring(0,obj.indexOf("\"followGenSett\""));
                obj += "\"followGenSett\":" + "\""+ sch.getFollowGenSett() +"\",";
                obj += "\"notifications\":" + "\""+sch.getNotifications()+"\",";
                obj += "\"noificationTimer\":" + "\""+sch.getNotificationTimer()+"\",";
                obj += "\"mute\":" + "\""+sch.getMuted()+"\"}";
            }
            list += obj;
            if(i != jsonArray.length()-1){
                list += ",";
            }

        }
        list += "]}";
        BufferedWriter bw = new BufferedWriter(new FileWriter(file));
        bw.write(list);
        bw.close();

    }

    public Vector<scheduleInformation> getSchedule(File file) throws Exception{
        Vector<scheduleInformation> schedule = new Vector<>();
        BufferedReader br = new BufferedReader(new FileReader(file));
        String line;
        String jsonLine = "";
        while((line = br.readLine()) != null){
            jsonLine += line;
        }
        br.close();
        JSONObject jsonObject = new JSONObject(jsonLine);
        JSONArray arr = jsonObject.getJSONArray("reservations");
        JSONObject obj;
        for(int i = 0; i < arr.length(); i ++){
            obj = ((JSONObject)arr.get(i));
            schedule.add(new scheduleInformation(obj.getString("course"),obj.getString("person"),obj.getString("room"),obj.getString("text"),obj.getString("startdate")
                    ,obj.getString("starttime"),obj.getString("endtime"),obj.getInt("followGenSett"),obj.getInt("notifications"),obj.getInt("noificationTimer")
                    ,obj.getInt("mute")));
        }

        return schedule;


    }

    public void getJsonFromTimeEdit(String url,File file) throws Exception{
        String newUrl = url.substring(0,url.length()-4)+ "json";
        JSONObject json = readJsonFromUrl(newUrl);
        JSONArray array = json.getJSONArray("reservations");
        String niceJson = "{\"reservations\":[";
        String columns[];
        for(int i = 0; i < array.length(); i++){
            niceJson += "{";
            columns = ((JSONObject)array.get(i)).get("columns").toString().split(",");

            niceJson += "\"course\":" + columns[0].substring(1) + ",";
            niceJson += "\"person\":" + columns[3] + ",";
            niceJson += "\"room\":" + columns[4] + ",";
            niceJson += "\"text\":" + columns[5] + ",";
            niceJson += "\"startdate\":\"" + ((JSONObject)array.get(i)).get("startdate") + "\",";
            niceJson += "\"starttime\":\"" + ((JSONObject)array.get(i)).get("starttime") + "\",";
            niceJson += "\"endtime\":\"" + ((JSONObject)array.get(i)).get("endtime") + "\",";

            niceJson += "\"followGenSett\":" + "\"1\",";
            niceJson += "\"notifications\":" + "\"1\",";
            niceJson += "\"noificationTimer\":" + "\"15\",";
            niceJson += "\"mute\":" + "\"1\"}";
            if(i != array.length()-1){
                niceJson += ",";
            }
        }
        niceJson += "]}";
        //System.out.println(niceJson);
        JSONObject obj = new JSONObject(niceJson);
        BufferedWriter writer = new BufferedWriter(new FileWriter(file));
        writer.write(niceJson);
        writer.close();

    }

    //Parse JSON
    public static JSONObject readJsonFromUrl(String url) throws IOException, JSONException
    {
        InputStream is = new URL(url).openStream();
        try
        {
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            String jsonText = readAll(rd);
            JSONObject json = new JSONObject(jsonText);
            return json;
        }

        finally
        {
            is.close();
        }
    }

    //Reader
    private static String readAll(Reader rd) throws IOException
    {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1)
        {
            sb.append((char) cp);
        }
        return sb.toString();
    }
}
