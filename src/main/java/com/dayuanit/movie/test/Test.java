package com.dayuanit.movie.test;

import java.util.Arrays;

public class Test {

    public static int[] nums = {2, 9, 5, 3, 1, 6, 7, 0};

    public static int[] heapArray = new int[nums.length + 1];
    // 当前节点所在的下标
    public static int size;

    public static void main(String[] args) {

        for (int num : nums){
            add(num);
        }
        System.out.println(Arrays.toString(heapArray));

        for (int i=0; i<8; i++) {
            System.out.print(remove());
        }
    }

    public static void swqp(int index1,int index2){
        int tmp = heapArray[index1];
        heapArray[index1] = heapArray[index2];
        heapArray[index2] = tmp;
    }

    public static void add(int ele){
        heapArray[++size] = ele;
        int currentParent = size/2;
        int currentNode = size;

        while (currentParent > 0){

            if (heapArray[currentNode] < heapArray[currentParent]){
                swqp(currentNode,currentParent);
                currentNode = currentParent;
                currentParent = currentParent / 2;
                continue;
            }
            break;
        }
    }

    public static int remove(){
        int targetNode = heapArray[1];
        swqp(1,size--);
        int currentNode = 1;
        while (true){
            int left = currentNode * 2;
            int right = currentNode * 2 + 1;
            int min = currentNode;
            if (left < size && heapArray[min] > heapArray[left]){
                min = left;
            }
            if (right < size && heapArray[min] > heapArray[right]){
                min = right;
            }
            if (min != currentNode){
                swqp(min,currentNode);
                currentNode = min;
                continue;
            }
            break;
        }
        return targetNode;
    }
}
