import java.util.*;

class Solution {
    public List<List<String>> fractionInvent(List<List<String>> orders, List<List<String>> inventory) {


        Map<String, Double> map = new TreeMap<>();

        for(List<String> order: inventory){

            String stock = order.get(0);
            double quantity = Double.parseDouble(order.get(1))/100.0;
            map.put(stock, quantity);
        }


        for(List<String> order: orders){

            String stockName = order.get(0);
            String orderType = order.get(1);
            String quantityOrPrice = order.get(2);
            double unit_price = Double.parseDouble(order.get(3))/100.0;


            double totalShares = 0.0;

            if(quantityOrPrice.startsWith("$")){
                double price = Double.parseDouble(quantityOrPrice.substring(1));
                totalShares = (price/unit_price)/100.0;
            }else{
                totalShares = Double.parseDouble(quantityOrPrice)/100.0;
            }

             double existingInventory = map.get(stockName);

            if(orderType.equals("B")){

                if(existingInventory < totalShares){
                    existingInventory = existingInventory + 1;
                }

                existingInventory = existingInventory - totalShares;
                existingInventory = existingInventory % 1.00; // this will remove any fractional part greater than 1 and even works for less then 1
                map.put(stockName, existingInventory);

            }else{

                existingInventory = existingInventory + totalShares;
                existingInventory = existingInventory % 1; // this will remove any fractional part greater than 1 and even works for less then 1
                map.put(stockName, existingInventory);
            }
        }


        List<List<String>> result = new ArrayList<>();

        map.entrySet().forEach(entry -> {

            String stock = entry.getKey();
            double quantity = entry.getValue();
            int fractionalQuantity = (int)Math.round(quantity * 100);
            result.add(Arrays.asList(stock, String.valueOf(fractionalQuantity)));
        });
        return result;
     }
}
