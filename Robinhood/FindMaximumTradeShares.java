import java.util.*;

class Solution {
    public int findMaxTradeShares(List<List<String>> orders) {


        PriorityQueue<Stock> maxBuyHeap = new PriorityQueue<>((a,b) -> b.price - a.price);
        PriorityQueue<Stock> minSellHeap = new PriorityQueue<>((a,b) -> a.price - b.price);
        int count = 0;

        for(List<String> order: orders){
            
            String orderType = order.get(2);

            if(orderType.equals("buy")){

                Stock buyOrder = new Stock(Integer.parseInt(order.get(0)), Integer.parseInt(order.get(1)), "buy");

                if(minSellHeap.isEmpty()){
                    maxBuyHeap.offer(buyOrder);
                    continue;
                }


                while ( !minSellHeap.isEmpty() && buyOrder.price >= minSellHeap.peek().price && buyOrder.quantity > 0) {

                    Stock minHeapStock = minSellHeap.poll();

                    int affordableStocks = Math.min(minHeapStock.quantity, buyOrder.quantity);
                    count = count + affordableStocks;
                    
                    minHeapStock.quantity = minHeapStock.quantity - affordableStocks;
                    buyOrder.quantity = buyOrder.quantity - affordableStocks;

                    if(minHeapStock.quantity > 0){
                        minSellHeap.offer(minHeapStock);
                    }   
                }
                if(buyOrder.quantity > 0){
                    maxBuyHeap.offer(buyOrder);
                }

            }else{

                Stock sellOrder = new Stock(Integer.parseInt(order.get(0)), Integer.parseInt(order.get(1)), "sell");

                if(maxBuyHeap.isEmpty()){
                    minSellHeap.offer(sellOrder);
                    continue;
                }

                while( !maxBuyHeap.isEmpty() && sellOrder.price <= maxBuyHeap.peek().price && sellOrder.quantity > 0 ){
                    
                    Stock maxHeapStock = maxBuyHeap.poll();

                    int affordableStocks = Math.min(maxHeapStock.quantity, sellOrder.quantity);
                    count = count + affordableStocks;
                    
                    maxHeapStock.quantity = maxHeapStock.quantity - affordableStocks;
                    sellOrder.quantity = sellOrder.quantity - affordableStocks;

                    if(maxHeapStock.quantity > 0){
                        maxBuyHeap.offer(maxHeapStock);
                    }   
                }

                if(sellOrder.quantity > 0){
                    minSellHeap.offer(sellOrder);
                }
            }
        }
        return count;
     }

     static class Stock{
        int price;
        int quantity;
        String orderType;

        public Stock(int price, int quantity, String orderType){
            this.price = price;
            this.quantity = quantity;
            this.orderType = orderType;
        }
        
     }
}