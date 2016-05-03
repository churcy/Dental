package com.tinytree.dental.test;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by 重 on 2016/3/29.
 */
public class TestController {

    public static void main(String[] args) {

        List<Integer> total = new ArrayList<>(Arrays.asList(0, 0, 0, 0, 0));
        initData(3, total);
        boolean flag = true;
        boolean orderFlag = false;
        boolean changeFlag = false;
        int position = 0;
        List<Integer> record;


        record = total;
        do{
            for (int i = 0; i < total.size() - 1; i++) {
             
                changeFirst(changeFlag, orderFlag, total);


            }

        } while(false);



        position = position + 1;


    }

    public static void changeFirst(boolean changeFlag, boolean orderFlag, List<Integer> total) {
        boolean flag;
        for (int i = 0; i < total.size() - 1; i++) {

            changeFlag = changePosition(i, total);
            orderFlag = orderOverOrNotFirst(2,total);

            if (changeFlag) {
                changeFirst(changeFlag, orderFlag, total);
            }
            if(orderFlag){
                break;
            }


        }



    }

    public static boolean changePosition(int i, List<Integer> total) {
        boolean flag;
        if (total.get(i) == 1 && total.get(i + 1) == 0) {
            total.set(i, 0);
            total.set(i + 1, 1);
            // boolean compareFlag = compareData(total);
            System.out.println(total);
            //System.out.println(compareFlag);
            flag = true;
        } else {
            flag = false;
        }
        return flag;
    }

    private static boolean orderOverOrNotFirst(int number, List<Integer> total) {
        int sum = 0;
        boolean flag;
        for (int i = 0; i < number; i++) {
            sum += total.get(i);
        }
        if (sum == 0) {
            flag = true;
        } else {
            flag = false;
        }
        return flag;
    }

    private static boolean orderOverOrNotSecond(List<Integer> total) {
        int sum = 0;
        boolean flag;

        if (total.get(0) == 0 && total.get(1) == 1) {
            flag = true;
        } else {
            flag = false;
        }

        return flag;
    }

    private static boolean compareData(List<Integer> total) {
        List<Integer> numbers = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8));
        int length = total.size();
        int sumSelected = 0;
        int sumUnSelected = 0;
        boolean flag;
        List<Integer> selected = new ArrayList<>();
        List<Integer> unSelected = new ArrayList<>();
        for (int i = 0; i < length; i++) {
            if (total.get(i) == 1) {
                selected.add(numbers.get(i));
            } else {
                unSelected.add(numbers.get(i));
            }
        }
        for (int i = 0; i < selected.size(); i++) {
            sumSelected += selected.get(i);
        }
        for (int i = 0; i < unSelected.size(); i++) {
            sumUnSelected += unSelected.get(i);
        }
        if (sumSelected == sumUnSelected) {
            flag = true;
        } else {
            flag = false;
        }
        return flag;
    }

    public static List<Integer> initData(int number, List<Integer> total) {
        for (int i = 0; i < total.size(); i++) {
            total.set(i, 0);
        }
        for (int i = 0; i < number; i++) {
            total.set(i, 1);
        }
        return total;
    }

}
