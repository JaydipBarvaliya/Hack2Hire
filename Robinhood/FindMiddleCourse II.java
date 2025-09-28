import java.util.*;

class Solution {
    public String findLongestPathMiddleCourse(List<List<String>> pairs) {

        Set<String> set = new HashSet<>();
        Map<String, List<String>> map = new HashMap<>();

        for (List<String> pair : pairs) {
            String course = pair.get(0);
            String depend = pair.get(1);
            set.add(course);
            set.add(depend);
        }

        for (List<String> pair : pairs) {

            String course = pair.get(0);
            String depend = pair.get(1);

            if (map.containsKey(course)) {
                map.get(course).add(depend);
            } else {
                map.put(course, new ArrayList<>(List.of(depend)));
            }
            set.remove(depend);
        }

        List<String> longestPath = new ArrayList<>();
        Map<String, List<String>> memo = new HashMap<>();
        for (String sourceNode : set) {
            List<String> longestChain = dfs(sourceNode, map, memo);

            if (longestChain.size() > longestPath.size()) {
                longestPath = longestChain;
            }
        }

        if (longestPath.size() % 2 == 0) {
            int mid = (longestPath.size() / 2) - 1;
            return longestPath.get(mid);
        } else {
            int mid = longestPath.size() / 2;
            return longestPath.get(mid);
        }

    }

    public List<String> dfs(String curr, Map<String, List<String>> map, Map<String, List<String>> memo) {

        if (memo.containsKey(curr)) return memo.get(curr);

        List<String> longestPath = new ArrayList<>();

        if (map.containsKey(curr)) {

            for (String neigh : map.get(curr)) {
                List<String> newPath = dfs(neigh, map, memo);

                if (newPath.size() > longestPath.size()) {
                    longestPath = newPath;
                }
            }
        }

        List<String> result = new ArrayList<>();
        result.add(curr);
        result.addAll(longestPath);

        memo.put(curr, result);
        return result;
    }
}