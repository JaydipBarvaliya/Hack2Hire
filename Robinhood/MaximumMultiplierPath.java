import java.util.*;

class Solution {
    public int maxMultiplierProduct(int n, List<List<Integer>> edges, int src, int dest) {

        List<List<long[]>> adj = new ArrayList<>();

        for(int i=0; i<n; i++){
            adj.add(new ArrayList<>());
        }

        for(List<Integer> edge : edges){
            int source = edge.get(0);
            int target = edge.get(1);
            int weight = edge.get(2);
            adj.get(source).add(new long[]{target, weight});
        }


        PriorityQueue<long[]> maxHeap = new PriorityQueue<>((a,b) ->  Long.compare(b[0], a[0]));
        maxHeap.offer(new long[]{1L, src});


        long[] dist = new long[n];
        Arrays.fill(dist, Long.MIN_VALUE);
        dist[src] = 1L;


        while(!maxHeap.isEmpty()){

            long[] node = maxHeap.poll();

            long currDist = node[0];
            int currNode = (int) node[1];

             if (currDist < dist[currNode]) continue;

            for(long[] neigh : adj.get(currNode)){

                int neighNode =  (int) neigh[0];
                long neighDist =  neigh[1];

                if( currDist * neighDist > dist[neighNode]){
                    dist[neighNode] = currDist * neighDist;
                    maxHeap.offer(new long[]{dist[neighNode], neighNode});
                }
            }

        }

        return dist[dest] == Long.MIN_VALUE ? -1 : (int)dist[dest];

     }
}