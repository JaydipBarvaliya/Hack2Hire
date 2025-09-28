import java.util.*;

class Solution {
    public List<List<Integer>> getCandlesticks(List<List<Integer>> pairs) {


        int endIntervalTrack = Integer.MIN_VALUE;

        for(List<Integer> pair : pairs){
            endIntervalTrack = Math.max(endIntervalTrack, pair.get(0));
        }

        int starting  = 0;
        int ending    =  endIntervalTrack - (endIntervalTrack%10);
        Map<Integer, List<List<Integer>>> map = new TreeMap<>();
        
        while(starting <= ending ){
            map.put(starting, new ArrayList<>());
            starting = starting + 10;
        }

        for (List<Integer> pair : pairs) {

            int time = pair.get(0);
            int startInterval = time - (time % 10);
            map.get(startInterval).add(pair);
        }

        List<List<Integer>> result = new ArrayList<>();

        int previousClosingPrice = 0;

        for (Map.Entry<Integer, List<List<Integer>>> entry : map.entrySet()) {

            int startInterval = entry.getKey();

            if (entry.getValue().isEmpty()) {

                List<Integer> sublist = new ArrayList<>();
                sublist.add(startInterval);
                sublist.add(previousClosingPrice);
                sublist.add(previousClosingPrice);
                sublist.add(previousClosingPrice);
                sublist.add(previousClosingPrice);
                result.add(sublist);

            } else {

                int minPrice = Integer.MAX_VALUE;
                int maxPrice = Integer.MIN_VALUE;
                int minTime = Integer.MAX_VALUE;
                int maxTime = Integer.MIN_VALUE;
                int openingPrice = 0;
                int closingPrice = 0;
                for (List<Integer> pair : entry.getValue()) {

                    int time = pair.get(0);
                    int price = pair.get(1);

                    minPrice = Math.min(minPrice, price);
                    maxPrice = Math.max(maxPrice, price);

                    if (time < minTime) {
                        minTime = time;
                        openingPrice = price;

                    }
                    if (time > maxTime) {
                        maxTime = time;
                        closingPrice = price;
                    }
                }
                List<Integer> sublist = Arrays.asList(startInterval, maxPrice, minPrice, openingPrice, closingPrice);
                result.add(sublist);

                previousClosingPrice = closingPrice;
            }
        };

        return result;
    }
}