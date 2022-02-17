package com.example.connect.Utils;

public class timePassed {
    Long seconds = 59_000L;
    Long minutes = seconds*60L;

    Long hours = minutes*60L;
    Long days = hours*24L;
    public String getTimePassed(Long start){
        Long cur = System.currentTimeMillis();
        Long res = cur-start;
        if(res<seconds){
            Long sec = res/1000L;
            return sec+" sec ago";
        }else if(res<minutes){
            return res/seconds+" min ago";
        }else if(res<hours){
            return res/minutes+" hours ago";
        }else if(res<days){
            return res/hours+" days ago";
        }else{
            return "Posted long long back";
        }
    }
}
