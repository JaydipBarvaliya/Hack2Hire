import java.util.*;

class Solution {
    public String findMiddleCourse(List<List<String>> pairs) {

        Map<String, String> map = new HashMap<>();
        Set<String> setOfCourses = new HashSet<>();

        for (List<String> pair : pairs) {
            map.put(pair.get(0), pair.get(1));
            setOfCourses.add(pair.get(0));
            setOfCourses.add(pair.get(1));
        }

        int totalCourses = setOfCourses.size();
        map.entrySet().forEach(entry -> setOfCourses.remove(entry.getValue()));
        String firstCourse = setOfCourses.iterator().next();

        int mid = totalCourses / 2;
        for (int i = 0; i < mid; i++) {
            firstCourse = map.get(firstCourse);
        }
        return firstCourse;

    }
}