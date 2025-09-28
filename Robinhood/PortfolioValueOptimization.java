import java.util.*;
class Solution {
    public double portValOptimization(int cash, List<List<String>> securities) {

        double balance = (double) cash;
        PriorityQueue<Stock> maxHeap = new PriorityQueue<>((a,b) -> (b.futurePrice/b.currPrice) - (a.futurePrice/a.currPrice));

        for(List<String> security : securities){

            int currPrice = Integer.parseInt(security.get(1));
            int futurePrice = Integer.parseInt(security.get(2));
            int totalStocks = Integer.parseInt(security.get(3));
            if( futurePrice > currPrice ){
                maxHeap.add(new Stock(currPrice, futurePrice, totalStocks));
            }            
        }

        if(maxHeap.isEmpty()) return balance;

        double futureProfit = 0.0;
        while(!maxHeap.isEmpty() && balance > 0){

            Stock stock = maxHeap.poll();
            double affordableShares = Math.min(stock.totalStocks, balance/stock.currPrice);
            balance = balance - (stock.currPrice * affordableShares); 
            futureProfit = futureProfit + (stock.futurePrice * affordableShares);
        }

        return futureProfit;
     }

     public class Stock{
        int currPrice;
        int futurePrice;
        int totalStocks;

        public Stock(int currPrice, int futurePrice, int totalStocks){
            this.currPrice = currPrice;
            this.futurePrice = futurePrice;
            this.totalStocks = totalStocks;
        }
     }
}