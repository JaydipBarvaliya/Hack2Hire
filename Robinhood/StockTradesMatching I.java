import java.util.*;

class Solution {
    public List<String> exactTradeMatch(List<String> houses, List<String> streets) {

        Map<String, Integer> houseMap = new HashMap<>();

        for (String house : houses) {
            houseMap.put(house, houseMap.getOrDefault(house, 0) + 1);
        }

        List<String> result = new ArrayList<>();

        for (String street : streets) {
            if (houseMap.containsKey(street)) {

                int freq = houseMap.get(street) - 1;
                if (freq == 0) {
                    houseMap.remove(street);
                } else {
                    houseMap.put(street, freq);
                }
            } else {
                result.add(street);
            }
        }

        houseMap.entrySet().forEach(entry -> {

            for (int i = 0; i < entry.getValue(); i++) {
                result.add(entry.getKey());
            }
        });

        Collections.sort(result);

        return result;

    }
}


 house=["AAPL,B,0100,ABC123", "AAPL,B,0100,ABC123", "AAPL,B,0100,ABC123", "GOOG,S,0050,CDC333", "AAPL,S,0100,ABC456"],
street=["  FB,B,0100,GBGGGG", "AAPL,B,0100,ABC123", "AAPL,B,0100,ABC123", "GOOG,S,0050,CDC444"]
