import java.util.*;

class Solution {
    long maxProduct = -1; // store the best answer

    public int maxMultiplierProduct(int n, List<List<Integer>> edges, int src, int dest) {
    
        List<List<int[]>> adj  = new ArrayList<>();
        for(int i=0; i<n; i++){
            adj.add(new ArrayList<>());
        }

        for(List<Integer> edge : edges){
            adj.get(edge.get(0)).add(new int[]{edge.get(1), edge.get(2)});
        }

        boolean[] visited = new boolean[n];
        visited[src] = true;

        dfs(adj, src, dest, visited, 1);

        return (int)maxProduct;
    }

    public void dfs(List<List<int[]>> adj, int curr,  int dest, boolean[] visited, int totalProduct){
       
        if(curr == dest){
            maxProduct = Math.max(maxProduct, totalProduct);
            return;
        }

        visited[curr] = true;
        
        for(int[] edge : adj.get(curr)){
            
            int currNode = edge[0];
            int currProduct = edge[1];

            if(!visited[currNode]){
                dfs(adj, currNode, dest, visited, totalProduct * currProduct);
            }
        }
        visited[curr] = false;
    } 
}














//Below solution is kinda Dijkstra's Algorithm variation but it will not work because in dijistra algo we automatically get rid of cycles because we want to shortest path and when we visit something which is already
//visited we can ignore it because it will be longer path but here we want maximum product path so we cannot ignore cycles because it might give us better product path.
// class Solution {
//     public int maxMultiplierProduct(int n, List<List<Integer>> edges, int src, int dest) {

//         List<List<long[]>> adj = new ArrayList<>();

//         for(int i=0; i<n; i++){
//             adj.add(new ArrayList<>());
//         }

//         for(List<Integer> edge : edges){
//             int source = edge.get(0);
//             int target = edge.get(1);
//             int weight = edge.get(2);
//             adj.get(source).add(new long[]{target, weight});
//         }


//         PriorityQueue<long[]> maxHeap = new PriorityQueue<>((a,b) ->  Long.compare(b[0], a[0]));
//         maxHeap.offer(new long[]{1L, src});


//         long[] dist = new long[n];
//         Arrays.fill(dist, Long.MIN_VALUE);
//         dist[src] = 1L;


//         while(!maxHeap.isEmpty()){

//             long[] node = maxHeap.poll();

//             long currDist = node[0];
//             int currNode = (int) node[1];

//              if (currDist < dist[currNode]) continue;

//             for(long[] neigh : adj.get(currNode)){

//                 int neighNode =  (int) neigh[0];
//                 long neighDist =  neigh[1];

//                 if( currDist * neighDist > dist[neighNode]){
//                     dist[neighNode] = currDist * neighDist;
//                     maxHeap.offer(new long[]{dist[neighNode], neighNode});
//                 }
//             }

//         }

//         return dist[dest] == Long.MIN_VALUE ? -1 : (int)dist[dest];

//      }
// }