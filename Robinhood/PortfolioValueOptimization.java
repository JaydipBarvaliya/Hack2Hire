import java.util.*;
class Solution {
    public double portValOptimization(int totalBudget, List<List<String>> securities) {

        PriorityQueue<Node> maxHeap = new PriorityQueue<>((a,b) -> Double.compare(b.rate, a.rate));

        for(List<String> sec : securities){


            String stockName  = sec.get(0);
            int currvalue     = Integer.parseInt(sec.get(1));
            int futureValue   = Integer.parseInt(sec.get(2));
            int totalShares   = Integer.parseInt(sec.get(3));
            double rate =  (double) futureValue/currvalue;

            if(currvalue > totalBudget || rate < 1) continue;

            maxHeap.offer(new Node(stockName, currvalue, futureValue, totalShares, rate));
        }

        double profit = 0.0;

        while(!maxHeap.isEmpty()  && totalBudget > 0){
            
            Node currStock = maxHeap.poll();
            
            int maxBuy = currStock.totalShares * currStock.currPrice;
            
            if( totalBudget >=  maxBuy){
                totalBudget = totalBudget - (currStock.currPrice * currStock.totalShares);
                profit = profit + (currStock.futurePrice * currStock.totalShares);
            }else{
                double totalStockToAfford =  (double) totalBudget/currStock.currPrice;
                profit  = profit +  ( totalStockToAfford * currStock.futurePrice);
                totalBudget = 0;
            }
        }

        return Math.max(profit, totalBudget);
        
     }


    static class Node{
         String stockName;
         int currPrice;
         int futurePrice;
         int totalShares;
         double rate;

         public Node(String stockName, int currPrice, int futurePrice, int totalShares, double rate){
             this.stockName = stockName;
             this.currPrice = currPrice;
             this.futurePrice = futurePrice;
             this.totalShares = totalShares;
             this.rate = rate;
         }
     }
}