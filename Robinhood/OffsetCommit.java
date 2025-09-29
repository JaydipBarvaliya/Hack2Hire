import java.util.*;

class Solution {
    public List<Integer> commitOffset(List<Integer> offsets) {

        int nextCommit = 0;
        Set<Integer> processed = new HashSet<>(); 
        List<Integer> result = new ArrayList<>();
        
        for(int offset : offsets) {

            processed.add(offset);

            if(processed.contains(nextCommit)){
                while (processed.contains(nextCommit)) {
                    nextCommit++;
                }
                result.add(nextCommit - 1);
            }else{
                result.add(-1);
            }
        }
        return result;

     }
}