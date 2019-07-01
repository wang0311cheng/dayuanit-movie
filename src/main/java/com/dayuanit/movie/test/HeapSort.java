package com.dayuanit.movie.test;

import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Comparator;

public class HeapSort<T> {

    public Object[] heapArray;
    // 当前节点所在的下标
    public int size;

    private Comparator<T> comparator;

    public HeapSort(Comparator comparator){

        this.comparator = comparator;
        this.heapArray = new Object[10];
    }


    public static void main(String[] args) {

        int[] nums = {2, 9, 5, 3, 1, 6, 7, 0};
        String[] names = {"c" , "g", "b", "a"};
        Dog[] dogs = {new Dog(100), new Dog(500), new Dog(300), new Dog(800), new Dog(600)};

        // 匿名内部类
//        Comparator<Dog> dogComparator = new Comparator<Dog>(){
//            @Override
//            public int compare(Dog dog1, Dog dog2) {
//                return dog1.getPrice() - dog2.getPrice();
//            }
//        };

        // Lambda表达式
        Comparator<Dog> comparator1 = (dog1,dog2) -> dog1.getPrice() - dog2.getPrice();

        HeapSort<Dog> dogHeapSort = new HeapSort(comparator1);

        for (Dog num : dogs){
            dogHeapSort.add(num);
        }

        System.out.println(Arrays.toString(dogHeapSort.heapArray));

        for (int i = 0;i < 5; i++){
            System.out.print(dogHeapSort.remove());
        }
    }

    public void swap(int index1,int index2){
        Object tmp = heapArray[index1];
        heapArray[index1] = heapArray[index2];
        heapArray[index2] = tmp;
    }

    public synchronized void add(T element){

        if (size+1 >= heapArray.length){
            heapArray = Arrays.copyOf(heapArray,heapArray.length * 2);
        }
        heapArray[++size] = element;
        // 父节点
        int currentParent = size/2;
        // 当前节点
        int currentNode = size;

        while (currentParent > 0){
            if (comparator.compare((T)heapArray[currentNode],(T)heapArray[currentParent]) < 0){
                swap(currentNode,currentParent);
//                swap(currentParent,currentNode);
                currentNode = currentParent;
                currentParent = currentNode / 2;
                continue;
            }
            break;
        }

        System.out.println("size = " + this.size);
    }

    public synchronized T peek(){
        if (size <= 0) {
            return null;
        }
        return (T) heapArray[1];
    }

    public synchronized T remove(){
        T targetNum = (T)heapArray[1];
        swap(1,size--);
        int currentNode = 1;
        while (true){
            int left = currentNode * 2;
            int right = currentNode * 2 + 1;
            int min = currentNode;
            if (left <= size && comparator.compare((T)heapArray[min],(T)heapArray[left]) > 0){
                min = left;
            }
            if (right <= size && comparator.compare((T) heapArray[min],(T)heapArray[right]) > 0){
                min = right;
            }
            if (min != currentNode){
                swap(min,currentNode);
                currentNode = min;
                continue;
            }
            break;
        }
        return targetNum;
    }

    public int getSize() {
        return size;
    }
}
