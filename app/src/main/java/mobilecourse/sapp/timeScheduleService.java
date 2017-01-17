package mobilecourse.sapp;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.app.TaskStackBuilder;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.provider.Settings;
import android.support.v7.app.NotificationCompat;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.text.SimpleDateFormat;
import java.util.Date;

public class timeScheduleService extends JobService {
    public timeScheduleService() {
    }

    private Handler jobHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage( Message msg ) {
            SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd|kk:mm");
            Date now = new Date();

            String strDate = date.format(now);
            File file = new File(getApplicationContext().getFilesDir(), "save.json");
            try{
                now = date.parse(strDate);
                BufferedReader br = new BufferedReader(new FileReader(file));
                String line,jsonLine = "";
                while((line = br.readLine()) != null){
                    jsonLine += line;
                }
                br.close();
                JSONObject jsonObject = new JSONObject(jsonLine);
                JSONArray arr = jsonObject.getJSONArray("reservations");
                JSONObject obj;
                Date lectureStart,lectureEnd;
                AudioManager audio = (AudioManager) getBaseContext().getSystemService(Context.AUDIO_SERVICE);
                for (int i = 0; i < arr.length(); i++){
                    obj = ((JSONObject)arr.get(i));

                    if(audio.getRingerMode() == AudioManager.RINGER_MODE_SILENT || audio.getRingerMode() == AudioManager.RINGER_MODE_VIBRATE){
                        lectureEnd = date.parse(obj.getString("startdate")+"|"+obj.getString("endtime"));
                        if(now.compareTo(lectureEnd) == 0){
                            audio.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
                        }

                    }

                    lectureStart = date.parse(obj.getString("startdate")+"|"+obj.getString("starttime"));
                    if(audio.getRingerMode() == AudioManager.RINGER_MODE_NORMAL){
                        if(now.compareTo(lectureStart) == 0){
                            audio.setRingerMode(AudioManager.RINGER_MODE_VIBRATE);
                        }
                    }

                    lectureStart.setMinutes(lectureStart.getMinutes() - obj.getInt("noificationTimer"));
                    if(now.compareTo(lectureStart) == 0 && obj.getInt("notifications") == 1){
                        sendNotification(obj);
                    }

                }

            }catch(Exception e){
                System.out.println(e.toString());
            }
            return true;
        }

        public void sendNotification(JSONObject obj) throws Exception{
            NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext());
            builder.setSmallIcon(R.mipmap.ic_launcher);
            builder.setContentTitle(obj.getString("course"));
            builder.setContentText("Starts in "+obj.getString("noificationTimer")+"min, at "+obj.getString("room"));
            builder.setVibrate(new long[]{1000,1000,1000});
            builder.setSound(Settings.System.DEFAULT_NOTIFICATION_URI);
            builder.setAutoCancel(true);
            Intent intent = new Intent(getApplicationContext(),mainActivity.class);
            intent.putExtra("fromNote","showFragment");
            TaskStackBuilder stackBuilder = TaskStackBuilder.create(getApplicationContext());
            stackBuilder.addParentStack(mainActivity.class);
            stackBuilder.addNextIntent(intent);
            PendingIntent pendingIntent = stackBuilder.getPendingIntent(0,PendingIntent.FLAG_UPDATE_CURRENT);
            builder.setContentIntent(pendingIntent);
            NotificationManager nm = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            nm.notify(0,builder.build());
        }
    });



    @Override
    public boolean onStartJob(JobParameters jobParameters) {
        jobHandler.sendMessage( Message.obtain( jobHandler, 1, jobParameters ));
        return false;
    }

    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        jobHandler.removeMessages(1);
        return false;
    }

}
