package mobilecourse.sapp;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by tord on 2016-12-29.
 */

public class scheduleInformation implements Parcelable{

    private String course;
    private String person;
    private String room;
    private String text;
    private String date;
    private String startTime;
    private String endTime;
    private int notificationTimer;
    private int muted;

    //Constructors
    public scheduleInformation(){
        this.course = "";
        this.person = "";
        this.room = "";
        this.text = "";
        this.date = "";
        this.startTime = "";
        this.endTime = "";
        this.notificationTimer=0;
        this.muted=1;

    }

    public scheduleInformation(String course, String person, String room, String text, String date, String startTime, String endTime){
        this.course = course;
        this.person = person;
        this.room = room;
        this.text = text;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.notificationTimer = 15;
        this.muted = 1;
    }



    //Get-Functions
    public String getCourse(){
        String returnValue = this.course;
        if(this.course.indexOf(",")!= -1){
            returnValue = this.course.substring(0,this.course.indexOf(","));
        }
        return returnValue;
    }
    public String getPerson(){
        return this.person;
    }
    public String getRoom(){
        return this.room;
    }
    public String getText(){
        return this.text;
    }
    public String getDate(){
        return this.date;
    }
    public String getStartTime(){
        return this.startTime;
    }
    public String getEndTime(){
        return this.endTime;
    }
    public int getNotificationTimer(){return this.notificationTimer;}
    public int getMuted(){return this.muted;}

    //Set-Functions
    public void setCourse(String course){
        this.course = course;
    }
    public void setPerson(String person){
        this.person = person;
    }
    public void setRoom(String room){
        this.room = room;
    }
    public void setText(String text){
        this.text = text;
    }
    public void setDate(String date){
        this.date = date;
    }
    public void setStartTime(String startTime){
        this.startTime = startTime;
    }
    public void setEndTime(String endTime){
        this.endTime = endTime;
    }
    public void setNotificationTimer(int timer){this.notificationTimer = timer;}
    public void setMuted(int muted){this.muted = muted;}

    public String toString(){
        String returnString = "";
        returnString += "Course: "+this.course+"\nPerson: "+this.person+"\nRoom: "+this.room;
        returnString += "\nText: "+this.text+"\nDate: "+this.date+"\nStartTime: "+this.startTime+"\nEndtime: "+this.endTime;
        return returnString;
    }

    /*
    * Parcel-Functions
    * Needed to send objects between different classes/fragments
    */

    public scheduleInformation(Parcel parcel){
        this.course = parcel.readString();
        this.person = parcel.readString();
        this.room = parcel.readString();
        this.text = parcel.readString();
        this.date = parcel.readString();
        this.startTime = parcel.readString();
        this.endTime = parcel.readString();
        this.notificationTimer = parcel.readInt();
        this.muted = parcel.readInt();
    }

    @Override
    public int describeContents(){
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags){
        parcel.writeString(this.course);
        parcel.writeString(this.person);
        parcel.writeString(this.room);
        parcel.writeString(this.text);
        parcel.writeString(this.date);
        parcel.writeString(this.startTime);
        parcel.writeString(this.endTime);
        parcel.writeInt(this.notificationTimer);
        parcel.writeInt(this.muted);
    }

    public static final Parcelable.Creator<scheduleInformation> CREATOR = new Parcelable.Creator<scheduleInformation>(){
        public scheduleInformation createFromParcel(Parcel in){
            return new scheduleInformation(in);
        }

        public scheduleInformation[] newArray(int size){
            return new scheduleInformation[size];
        }
    };




}
