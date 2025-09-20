import java.util.*;


import java.math.*;

class Solution {
    public int findMaxTradeShares(List<List<String>> orders) {

        PriorityQueue<Node> buyOrdersHeap = new PriorityQueue<>((nodeA, nodeB) -> nodeB.price - nodeA.price);
        PriorityQueue<Node> sellOrdersHeap = new PriorityQueue<>((nodeA, nodeB) -> nodeA.price - nodeB.price); 
        int count = 0;
        for(List<String> order : orders){

            if(order.get(2).equals("buy")){
            
                Node buyOrder = new Node(Integer.parseInt(order.get(0)), Integer.parseInt(order.get(1)), "buy");
                
                if(sellOrdersHeap.isEmpty() || buyOrder.price < sellOrdersHeap.peek().price){
                    buyOrdersHeap.offer(buyOrder);
                    continue;
                }


                if (buyOrder.quantity == sellOrdersHeap.peek().quantity) {
                    count = count + buyOrder.quantity;
                    sellOrdersHeap.poll();
                } else if(buyOrder.quantity < sellOrdersHeap.peek().quantity) {

                    count = count + buyOrder.quantity;

                    Node sellOrder = sellOrdersHeap.poll();
                    sellOrder.quantity = sellOrder.quantity - buyOrder.quantity;
                    sellOrdersHeap.offer(sellOrder);
                }else{
                    while(!sellOrdersHeap.isEmpty() && buyOrder.quantity != 0 && buyOrder.price >= sellOrdersHeap.peek().price){
                        Node sellOrder = sellOrdersHeap.poll();
                        int trade = Math.min(buyOrder.quantity, sellOrder.quantity);
                        count = count + trade;
                        buyOrder.quantity = buyOrder.quantity - trade;
                        sellOrder.quantity = sellOrder.quantity - trade;
                        if(sellOrder.quantity > 0){
                            sellOrdersHeap.offer(sellOrder);
                        }
                    }
                    if(buyOrder.quantity > 0){
                        buyOrdersHeap.offer(buyOrder);
                    }
                }
                    
            }else{

                Node sellOrder = new Node(Integer.parseInt(order.get(0)), Integer.parseInt(order.get(1)), "sell");

                if(buyOrdersHeap.isEmpty() || sellOrder.price > buyOrdersHeap.peek().price){
                    sellOrdersHeap.offer(sellOrder);
                    continue;
                }

                if(sellOrder.quantity == buyOrdersHeap.peek().quantity){
                    count = count + sellOrder.quantity;
                    buyOrdersHeap.poll();
                } else if(sellOrder.quantity < buyOrdersHeap.peek().quantity) {
                    count = count + sellOrder.quantity;

                    Node buyOrder = buyOrdersHeap.poll();
                    buyOrder.quantity = buyOrder.quantity - sellOrder.quantity;
                    buyOrdersHeap.offer(buyOrder);
                }else{

                    while (!buyOrdersHeap.isEmpty() && sellOrder.quantity != 0 && sellOrder.price <= buyOrdersHeap.peek().price) {
                        Node buyOrder = buyOrdersHeap.poll();
                        int trade = Math.min(sellOrder.quantity, buyOrder.quantity);
                        count = count + trade;
                        sellOrder.quantity = sellOrder.quantity - trade;
                        buyOrder.quantity = buyOrder.quantity - trade;
                        if (buyOrder.quantity > 0) {
                            buyOrdersHeap.offer(buyOrder);
                        }
                    }
                    if (sellOrder.quantity > 0) {
                        sellOrdersHeap.offer(sellOrder);
                }
            }
        }
     }

    return count;
}
static class Node{
         int price;
         int quantity;
         String type;

            Node(int price, int quantity, String type){
                this.price = price;
                this.quantity = quantity;
                this.type = type;
            }

}
}