import java.util.*;

class Solution {
    public List<List<Integer>> getCandlesticks(List<List<Integer>> timedPrices) {


    //  timePrices = [[1, 2], [3, 4], [9, 8], [5, 10], [13, 18], [34, 32], [55, 44]]
    PriorityQueue<int[]> minHeap = new PriorityQueue<>((a,b) -> a[0]-b[0]);

    for(List<Integer> timedPrice : timedPrices){
        minHeap.offer(new int[]{timedPrice.get(0), timedPrice.get(1)});
    }


    //  timePrices = [[1, 2], [3, 4], [5, 10], [9, 8], [13, 18], [34, 32], [55, 44]]  --> Sorted

    int intervalStart = 0;
    int intervalEnd = 9;



    //[1, 2], [3, 4], [5, 10], [9, 8]
    //[0, 10, 2, 2, 8]

    while(!minHeap.isEmpty()){

        int[] timeAndPrice = minHeap.peek(); // [1(time), 2(price)]
        int currStart = timeAndPrice[0]; // 1
        int currPrice = timeAndPrice[1]; // 2


        if(currStart >= intervalStart && currStart <= intervalEnd){
            
            // calculate [<start_time>, <max_price>, <min_price>, <open_price>, <close_price>].
            //             0,                  10,           2,             2,         8

        }




    }

    return null;
     }
}