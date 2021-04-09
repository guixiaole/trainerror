package com.gxl.trainerror;

public class test {
    public static void main(String[] args) {
        String  f1 = "1";
        Integer i1;
        if (f1.equals(""))
            i1 = null;
        else
            i1 = Integer.valueOf(f1);

        System.out.println(i1);
    }
}
