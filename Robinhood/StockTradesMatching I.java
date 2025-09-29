import java.util.*;


// TreeMap operations are O(log n) instead of O(1) for HashMap.
// If your dataset is huge and sorting at the end was cheap compared to insertions, HashMap + Collections.sort() can sometimes still be faster.
class Solution {
    public List<String> exactTradeMatch(List<String> houses, List<String> streets) {

        Map<String, Integer> houseMap = new HashMap<>();

        for(String house: houses){
            houseMap.put(house, houseMap.getOrDefault(house, 0) + 1 );
        }

        List<String> result = new ArrayList<>();

        for(String street: streets){
            if(houseMap.containsKey(street)){

                int freq = houseMap.get(street) - 1;
                if(freq == 0){
                    houseMap.remove(street);
                }else{
                    houseMap.put(street, freq);
                }
            }else{
                result.add(street);
            }
        }

        houseMap.entrySet().forEach(entry -> {

            for(int i=0; i<entry.getValue(); i++){
                result.add(entry.getKey());
            }
        });

        Collections.sort(result);

        return result;

     }
}