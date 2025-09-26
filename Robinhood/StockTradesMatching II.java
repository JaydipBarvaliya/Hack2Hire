import java.util.*;

class Solution {
    public List<String> getUnmatchTrades(List<String> houses, List<String> streets) {

       Map<String, Integer> houseMap =  countTrades(houses);
       Map<String, Integer> streetMap =  countTrades(streets);


       exactMatch(houseMap, streetMap);
       Map<String, List<String>> groupedHouses  = attributeMatch(houseMap);
       Map<String, List<String>> groupedStreets  = attributeMatch(streetMap);

        for(String newHouse : new ArrayList<>(groupedHouses.keySet())){

            if(groupedStreets.containsKey(newHouse)){
                List<String> listOfhouse = groupedHouses.get(newHouse);
                List<String> listOfStreets = groupedStreets.get(newHouse);

                int matches = Math.min(listOfhouse.size(), listOfStreets.size());

                for(int i=0; i<matches; i++){
                    listOfhouse.remove(0);
                    listOfStreets.remove(0);
                }

                if(listOfhouse.size() == 0) groupedHouses.remove(newHouse);
                if(listOfStreets.size() == 0) groupedStreets.remove(newHouse);
            }
        }


       Map<String, List<String>> offsetHouses = groupForOffset(groupedHouses);
       Map<String, List<String>> offsetStreets = groupForOffset(groupedStreets);

       List<String> uniqueHouses =  offSetGrouping(offsetHouses);
       List<String> uniqueStreets = offSetGrouping(offsetStreets);

        List<String> finale = new ArrayList<>();

        finale.addAll(uniqueHouses);
        finale.addAll(uniqueStreets);

        Collections.sort(finale);

        return finale;
    }

    private Map<String, List<String>> groupForOffset(Map<String, List<String>> trades) {
    Map<String, List<String>> newMap = new HashMap<>();

    for (String key : trades.keySet()) {
        List<String> orders = trades.get(key);
        for (String order : orders) {
            String[] parts = order.split(",");
            String newKey = parts[0] + "," + parts[2]; // symbol + qty only
            newMap.putIfAbsent(newKey, new ArrayList<>());
            newMap.get(newKey).add(order);
        }
    }

    return newMap;
}


    
    public List<String> offSetGrouping(Map<String, List<String>> trades){

        List<String> result = new ArrayList<>();

        for(String trade : trades.keySet()){

            List<String> group = trades.get(trade);
            List<String> buys = new ArrayList<>();
            List<String> sells = new ArrayList<>();

            for(String order: group){
                if(order.contains(",B,")) buys.add(order);
                if(order.contains(",S,")) sells.add(order);
            }

            Collections.sort(buys);
            Collections.sort(sells);

            int matches = Math.min(buys.size(), sells.size());

            for(int i=0; i<matches; i++){
                buys.remove(0);
                sells.remove(0);
            }
            result.addAll(buys);
            result.addAll(sells);
        }

        return result;
    }



    private Map<String, List<String>> attributeMatch(Map<String, Integer> tradesMap){

        Map<String, List<String>> newMap = new HashMap<>();

        for(String trade : tradesMap.keySet()){
           String[] splitted = trade.split(",");
           String newKey = splitted[0] + "," + splitted[1] + "," + splitted[2];
           newMap.put(newKey, new ArrayList<>());

           int freq = tradesMap.get(trade);

           for(int i=0; i<freq; i++){
                newMap.get(newKey).add(trade);
           }
        }
        return newMap;
    }

    private void exactMatch(Map<String, Integer> houseMap, Map<String, Integer> streetMap) {

        for(String house : new ArrayList<>(houseMap.keySet())){ // Collect keys into a temporary list before removal otherwise it will ConcurrentModificationException because You’re removing entries from a map while iterating its keySet
            if(streetMap.containsKey(house)){

                int matches = Math.min(houseMap.get(house), streetMap.get(house));
                
                houseMap.put(house, houseMap.get(house) - matches);
                streetMap.put(house, streetMap.get(house) - matches);

                if(houseMap.get(house) == 0) houseMap.remove(house);
                if(streetMap.get(house) == 0) streetMap.remove(house);
            }
        }
    }

    public Map<String, Integer> countTrades(List<String> trades){

        Map<String, Integer> tradeMap = new HashMap<>();

        for(String trade : trades){
            if(tradeMap.containsKey(trade)){
                tradeMap.put(trade, tradeMap.get(trade) + 1);
            }else{
                tradeMap.put(trade, 1);
            }
        }

        return tradeMap;

    }

}