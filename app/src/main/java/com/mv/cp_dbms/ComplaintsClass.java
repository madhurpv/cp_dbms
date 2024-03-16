package com.mv.cp_dbms;

public class ComplaintsClass {

    static int COMPLAINT_NOT_RESOLVED = 0;
    static int COMPLAINT_RESOLVED = 1;

    String title = "";
    String description = "";
    Long time = -1L;
    int complaint_state = COMPLAINT_NOT_RESOLVED;
    String complaint_maker_number = "-1";
    String complaint_maker_name = "-1";

}
