import java.util.*;

class Solution {
    public List<String> empRefProgram(List<String> referrers, List<String> referrals) {

        Map<String, List<String>> map = new HashMap<>();
        Set<String> set = new HashSet<>();

        for (int i = 0; i < referrals.size(); i++) {

            String person = referrers.get(i);
            String referredPerson = referrals.get(i);
            set.add(person);
            if (map.containsKey(person)) {
                map.get(person).add(referredPerson);
            } else {
                List<String> list = new ArrayList<>();
                list.add(referredPerson);
                map.put(person, list);
            }
        }

        PriorityQueue<Person> maxHeap = new PriorityQueue<>( (a,b) -> a.count != b.count ? b.count - a.count : a.name.compareTo(b.name) );

        Map<String, Integer> memo = new HashMap<>();
        for(String name: set){
            int count = dfs(name, map,  memo);
            maxHeap.offer(new Person(name, count));
        }

        List<String> result = new ArrayList<>();
        for(int i = 0; i < 3 && !maxHeap.isEmpty(); i++){
            Person person = maxHeap.poll();
            result.add(person.name + " " + person.count);
        }
        return result;
    }

    public int dfs(String source, Map<String, List<String>> map, Map<String, Integer> memo){

        if(memo.containsKey(source)) return memo.get(source);

        int total = 0;

        if(map.containsKey(source)){
            for(String neighbor : map.get(source)){
                total = total + 1 + dfs(neighbor, map, memo);
            }
        }
        memo.put(source, total);
        return total;
    }

    public static class Person{
        String name;
        int count;

        public Person(String name, int count){
            this.name = name;
            this.count = count;
        }
    }
}