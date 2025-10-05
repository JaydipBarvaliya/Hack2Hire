package Amazon;


// Using Queue and HashMap but here we have to use while loop to find the oldest one-time visitor which may be take O(k) time in worst case
// import java.util.*;
// class UserTracker {
//     private Map<String, Integer> freqMap;
//     private Queue<String> queue;

//     public UserTracker() {
//         freqMap = new HashMap<>();
//         queue = new LinkedList<>();
//     }

//     public void userLogin(String username) {
//         freqMap.put(username, freqMap.getOrDefault(username, 0) + 1);

//         // If it's their first login, add to queue
//         if (freqMap.get(username) == 1) {
//             queue.add(username);
//         }
//     }

//     public String getOldestOneTimeVisitingUser() {

//         for(String user: queue) {
//             if (freqMap.get(user) == 1) {
//                 return user;
//             }
//         }
//         return "";
//     }
// }




// using LinkedHashSet to get rid of while loop in the above solution
import java.util.*;
class UserTracker {
    private Map<String, Integer> freqMap;
    private LinkedHashSet<String> queue;

    public UserTracker() {
        freqMap = new HashMap<>();
        queue = new LinkedHashSet<>();
    }

    public void userLogin(String username) {
        freqMap.put(username, freqMap.getOrDefault(username, 0) + 1);

        // If it's their first login, add to queue
        if (freqMap.get(username) == 1) {
            queue.add(username);
        }else{
            queue.remove(username);
        }
    }

    public String getOldestOneTimeVisitingUser() {

        return queue.isEmpty() ? "" : queue.iterator().next();
    }
}
