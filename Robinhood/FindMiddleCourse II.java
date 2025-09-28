
import java.util.*;

class Solution {
    public String findLongestPathMiddleCourse(List<List<String>> pairs) {

        Map<String, List<String>> map = new HashMap<>();
        Set<String> courses = new HashSet<>();

        for (List<String> pair : pairs) {

            String key = pair.get(0);
            String value = pair.get(1);
            
            if(!map.containsKey(key)){
                map.put(key, new ArrayList<>(Arrays.asList(value)));
            }else{
                map.get(key).add(value);
            }

            courses.add(pair.get(0));
            courses.add(pair.get(1));
        }

        map.values().forEach(list -> list.forEach( val -> courses.remove(val)) );


        List<String> longestPath = new ArrayList<>();
        Map<String, List<String>> memo = new HashMap<>();
        for(String sourceNode : courses){ 
            List<String> path  = dfs(sourceNode, map, new ArrayList<>(), memo);

            if(path.size() > longestPath.size()){
                longestPath = path;
            }
        }
        
        if(longestPath.size() % 2 == 0){
            int mid = (longestPath.size()/2)-1;
            return longestPath.get(mid);
        }else{
            int mid = longestPath.size()/2;
            return longestPath.get(mid);
        }

    }

    public List<String> dfs(String node, Map<String, List<String>> map, List<String> chain, Map<String, List<String>> memo){
        
        if(memo.containsKey(node)) return memo.get(node);

        chain.add(node);

        if(!map.containsKey(node)) return chain;
    
        List<String> longest = new ArrayList<>();

        for(String neigh : map.get(node)){
            
            List<String> newPath = dfs(neigh, map, new ArrayList<>(chain), memo);

            if(newPath.size() > longest.size()){
                longest = newPath;
            }
        }

        longest.add(0, node); // add current node at the beginning of the longest path

        memo.put(node, longest);
        return longest;
    }
}