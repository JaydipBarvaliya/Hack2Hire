import java.util.*;
import java.math.*;

class Solution {
    public List<String> loadFactorCalc(List<String> serviceList, String entryPoint) {

        Map<String, List<String>> adj = new HashMap<>();
        Map<String, Integer> indegreeMap = new HashMap<>();
        Map<String, Integer> loadMap = new HashMap<>();
        
        for(String service : serviceList){
            String[] parts = service.split("=");
            String name = parts[0];
            adj.put(name, new ArrayList<>());
            indegreeMap.put(name, 0);

        }
       
        for(String service : serviceList){
            String[] parts = service.split("=");
            String name = parts[0];
            if(parts.length > 1 && !parts[1].isEmpty()){
                for(String dep : parts[1].split(",")){
                    if(adj.containsKey(dep)){
                        adj.get(name).add(dep);
                        indegreeMap.put(dep, indegreeMap.getOrDefault(dep, 0) + 1);
                    }
                }
            }
        }

        Queue<String> queue = new LinkedList<>();
        queue.add(entryPoint);
        loadMap.put(entryPoint, 1);


        while (!queue.isEmpty()) {

            String curr = queue.poll();

            for(String neigh : adj.get(curr)){
                loadMap.put(neigh, loadMap.getOrDefault(neigh, 0) + loadMap.get(curr));
                indegreeMap.put(neigh, indegreeMap.get(neigh) - 1);
                if(indegreeMap.get(neigh) == 0){
                    queue.add(neigh);
                }
            }
        }

        List<String> result = new ArrayList<>();
        
        for(String service : adj.keySet()){

            int load = loadMap.getOrDefault(service, 0);
            
            if (load > 0){
                result.add(service + "*" + load);
            }
        }

        Collections.sort(result);
        return result;
     }
}