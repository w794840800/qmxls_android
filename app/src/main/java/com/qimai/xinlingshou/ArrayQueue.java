package com.qimai.xinlingshou;

public class ArrayQueue {


    private String[]items;
    private int n;
    private int head = 0;
    private int tail = 0;


    public ArrayQueue(int n){

        items = new String[n];
        this.n = n;
    }


    public boolean enqueue(String item){

        if (tail==n){

            return false;
        }
        items[n] = item;

        tail++;
        return true;
    }

    public String dequeue(){


        if (head==tail){


            return  null;
        }
        String rec = items[head];
        head++;
        return rec;

    }


}
