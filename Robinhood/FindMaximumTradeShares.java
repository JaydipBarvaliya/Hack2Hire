import java.util.*;


class Solution {
    public int findMaxTradeShares(List<List<String>> orders) {

        PriorityQueue<Node> buyOrdersMaxHeap = new PriorityQueue<>((nodeA, nodeB) -> nodeB.price - nodeA.price);
        PriorityQueue<Node> sellOrdersMinHeap = new PriorityQueue<>((nodeA, nodeB) -> nodeA.price - nodeB.price); 
        int count = 0;
        for(List<String> order : orders){

            if(order.get(2).equals("buy")){
            
                Node buyOrder = new Node(Integer.parseInt(order.get(0)), Integer.parseInt(order.get(1)), "buy");
                
                if(sellOrdersMinHeap.isEmpty() || buyOrder.price < sellOrdersMinHeap.peek().price){
                    buyOrdersMaxHeap.offer(buyOrder);
                    continue;
                }


                if (buyOrder.quantity == sellOrdersMinHeap.peek().quantity) {
                    count = count + buyOrder.quantity;
                    sellOrdersMinHeap.poll();
                } else if(buyOrder.quantity < sellOrdersMinHeap.peek().quantity) {

                    count = count + buyOrder.quantity;

                    Node sellOrder = sellOrdersMinHeap.poll();
                    sellOrder.quantity = sellOrder.quantity - buyOrder.quantity;
                    sellOrdersMinHeap.offer(sellOrder);
                }else{
                    while(!sellOrdersMinHeap.isEmpty() && buyOrder.quantity != 0 && buyOrder.price >= sellOrdersMinHeap.peek().price){
                        Node sellOrder = sellOrdersMinHeap.poll();
                        int trade = Math.min(buyOrder.quantity, sellOrder.quantity);
                        count = count + trade;
                        buyOrder.quantity = buyOrder.quantity - trade;
                        sellOrder.quantity = sellOrder.quantity - trade;
                        if(sellOrder.quantity > 0){
                            sellOrdersMinHeap.offer(sellOrder);
                        }
                    }
                    if(buyOrder.quantity > 0){
                        buyOrdersMaxHeap.offer(buyOrder);
                    }
                }
                    
            }else{

                Node sellOrder = new Node(Integer.parseInt(order.get(0)), Integer.parseInt(order.get(1)), "sell");

                if(buyOrdersMaxHeap.isEmpty() || sellOrder.price > buyOrdersMaxHeap.peek().price){
                    sellOrdersMinHeap.offer(sellOrder);
                    continue;
                }

                if(sellOrder.quantity == buyOrdersMaxHeap.peek().quantity){
                    count = count + sellOrder.quantity;
                    buyOrdersMaxHeap.poll();
                } else if(sellOrder.quantity < buyOrdersMaxHeap.peek().quantity) {
                    count = count + sellOrder.quantity;

                    Node buyOrder = buyOrdersMaxHeap.poll();
                    buyOrder.quantity = buyOrder.quantity - sellOrder.quantity;
                    buyOrdersMaxHeap.offer(buyOrder);
                }else{

                    while (!buyOrdersMaxHeap.isEmpty() && sellOrder.quantity != 0 && sellOrder.price <= buyOrdersMaxHeap.peek().price) {
                        Node buyOrder = buyOrdersMaxHeap.poll();
                        int trade = Math.min(sellOrder.quantity, buyOrder.quantity);
                        count = count + trade;
                        sellOrder.quantity = sellOrder.quantity - trade;
                        buyOrder.quantity = buyOrder.quantity - trade;
                        if (buyOrder.quantity > 0) {
                            buyOrdersMaxHeap.offer(buyOrder);
                        }
                    }
                    if (sellOrder.quantity > 0) {
                        sellOrdersMinHeap.offer(sellOrder);
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