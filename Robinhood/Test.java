import java.util.*;

class Solution {

    public List<List<Integer>> getCandlesticks(List<List<Integer>> timedPrices) {

        Map<Integer, List<List<Integer>>> map = new TreeMap<>();

        List<List<Integer>> result = new ArrayList<>();

        int intervalEnd = 0;
        int previousClosingPrice = 0;

        for(List<Integer> pair : timedPrices){
            intervalEnd = Math.max(intervalEnd, pair.get(0));
        }

        intervalEnd = intervalEnd - (intervalEnd % 10);
        int intervalStart = 0;

        while(intervalStart <= intervalEnd){
            map.put(intervalStart, new ArrayList<>());
            intervalStart = intervalStart + 10;
        }

        
        for(List<Integer> pair : timedPrices ){
            int startTime = pair.get(0);
            int fallInterval = startTime - (startTime%10);
            map.get(fallInterval).add(pair);
        }

        for (Map.Entry<Integer, List<List<Integer>>> entry : map.entrySet()) {

            int startInterval = entry.getKey();
            
            if(entry.getValue().isEmpty()){
                result.add(Arrays.asList(startInterval, previousClosingPrice, previousClosingPrice, previousClosingPrice, previousClosingPrice));
            }else{

            int maxPrice = Integer.MIN_VALUE;
            int minPrice = Integer.MAX_VALUE;
            int openPrice = 0;
            int closePrice = 0;
            int maxTime = Integer.MIN_VALUE;
            int minTime = Integer.MAX_VALUE;

            for(List<Integer> pair : entry.getValue()){
            
                minPrice = Math.min(minPrice, pair.get(1));
                maxPrice = Math.max(maxPrice, pair.get(1));
                
                if(pair.get(0) < minTime){
                    minTime = pair.get(0);
                    openPrice = pair.get(1);
                }
                
                if(pair.get(0) >= maxTime){
                    maxTime = pair.get(0);
                    closePrice = pair.get(1);
                }
            }

            previousClosingPrice = closePrice;
            result.add(Arrays.asList(startInterval, maxPrice, minPrice, openPrice, closePrice));
        }
            
    }
        return result;
     }
}