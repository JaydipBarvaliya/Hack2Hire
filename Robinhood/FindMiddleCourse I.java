import java.util.*;

class Solution {
    public String findMiddleCourse(List<List<String>> pairs) {

        Set<String> set = new HashSet<>();
        Map<String, List<String>> map = new HashMap<>();

        for(List<String> pair : pairs){
            set.add(pair.get(0));
            set.add(pair.get(1));
        }

        int middle = set.size()/2;

        for(List<String> pair : pairs){

            String course = pair.get(0);
            String depend = pair.get(1);

            if(map.containsKey(course)){
                map.get(course).add(depend);
            }else{
                map.put(course, List.of(depend));
            }
            set.remove(pair.get(1));
        }


        String startCourse = set.iterator().next();

        return dfs(map, startCourse, middle, 0);

     }

    public String dfs(Map<String, List<String>> map, String startCourse, int middle, int count){

        if(count == middle) return startCourse;
        
        for(String neigh : map.get(startCourse)){
            return dfs(map, neigh, middle, count + 1);
        }

        return "";
     }
}