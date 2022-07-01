package com;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Tester{

    public class kvk{
        private String last;
        private Integer value;
        private String next;

        public kvk(){
            last = new String();
            next = new String();
        }
    }
    
    public static void add(String aHead,String bHead,kvk[] kvks,List<kvk> aList,List<kvk> bList,int n){
        for (int i = 0; i < n; i++) {
            if(aHead.equals(kvks[i].last)) {
                aList.add(kvks[i]);
            }
            if(bHead.equals(kvks[i].last)) {
                bList.add(kvks[i]);
            }
        }
    }
    
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String aHead = sc.next();
        String bHead = sc.next();
        int n = sc.nextInt();

        List<kvk> aList = new ArrayList<>();
        List<kvk> bList = new ArrayList<>();

        String[] last = new String[n];
        String[] next = new String[n];
        Integer[] value = new Integer[n];

        kvk[] kvks = new kvk[n];
        for (int i = 0; i < n; i++) {
            String Last = sc.next();
            kvks[i].last=Last;
            Integer Value = sc.nextInt();
            kvks[i].value=Value;
            String Next = sc.next();
            kvks[i].next=Next;
        }

        add(aHead,bHead,kvks,aList,bList,n);
        
        sc.close();
    }
}