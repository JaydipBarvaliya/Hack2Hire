
import java.util.*;

class Solution {


    public List<String> empRefProgram(List<String> referrers, List<String> referrals) {


        Map<String, List<String>> map = getStringListMap(referrers, referrals);
        Map<String, Integer> visited = new HashMap<>();
        PriorityQueue<Node> maxPQ = new PriorityQueue<>((a, b) -> {

            if(a.count == b.count){
                return a.name.compareTo(b.name);
            }else{
                return b.count - a.count;
            }

        });

        List<String> result = new ArrayList<>();
        map.entrySet().forEach(entry -> {

            int count = dfs(entry.getKey(),  map, visited);

            Node node = new Node(count, entry.getKey());
            maxPQ.add(node);
        });

        int k = 3;
        while(k-- > 0 && !maxPQ.isEmpty()){
            Node node = maxPQ.poll();
            String ans = node.name + " " + node.count;
            result.add(ans);
        }

        return result;
    }

    public int dfs(String key,  Map<String, List<String>> map, Map<String, Integer> visited){

        if(!map.containsKey(key)) return 0;

        if(visited.containsKey(key)) return visited.get(key);

        int count = 0;
        for(String neighbor : map.get(key)){
            count = count + 1 + dfs(neighbor, map, visited);
        }

        visited.put(key, count);
        return count;
    }


    private static Map<String, List<String>> getStringListMap(List<String> referrers, List<String> referrals) {

        Map<String, List<String>> map = new HashMap<>();
        int size = referrers.size();

        for(int i = 0; i < size; i++){

            String key = referrers.get(i);
            String value = referrals.get(i);

            if(map.containsKey(key)){
                List<String> existingList = map.get(key);
                existingList.add(value);
                map.put(key, existingList);
            }else{
                List<String> newList = new ArrayList<>();
                newList.add(value);
                map.put(key, newList);
            }
        }
        return map;
    }

    static class Node{
        int count;
        String name;
        public Node(int count, String name){
            this.count = count;
            this.name = name;
        }
    }
}