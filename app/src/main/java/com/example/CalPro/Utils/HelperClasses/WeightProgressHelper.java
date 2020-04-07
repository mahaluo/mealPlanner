package com.example.CalPro.Utils.HelperClasses;

public class WeightProgressHelper {

    private Double weight;
    private Integer year;
    private Integer month;
    private Integer day;


    public WeightProgressHelper() { }

    public static class Year extends WeightProgressHelper {
        public Year() { }
        public static class Month extends WeightProgressHelper {
            public Month() { }
        }
    }
}
