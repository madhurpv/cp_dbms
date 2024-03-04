package com.mv.cp_dbms;

import java.util.ArrayList;
import java.util.List;

public class VotingClass {

    static boolean VOTED = true;
    static boolean NOT_VOTED = false;


    String title = "";
    String details = "";
    long startTime = -1;
    long endTime = -1;
    List<String> options = new ArrayList<>();
    int selection;
    boolean voted;

    public VotingClass(String title, long startTime, long endTime, String details, List<String> options, int selection, boolean voted){
        this.title = title;
        this.startTime = startTime;
        this.endTime = endTime;
        this.details = details;
        this.options = options;
        this.selection = selection;
        this.voted = voted;
    }


}
