import java.util.*;
import java.math.*;

class Solution {
    public List<List<String>> fractionInvent(List<List<String>> orders, List<List<String>> inventories) {

       // we have used the treemap to preserver the order
        Map<String, Double> map = new TreeMap<>();

        for(List<String> inventory : inventories){
            String companyName = inventory.get(0);
            double existingInventory = Double.parseDouble(inventory.get(1))/100.0;
            map.put(companyName, existingInventory);
        }


        for(List<String> order: orders){
            String companyName = order.get(0);
            String orderType = order.get(1);
            String cashOrTotalShares = order.get(2);
            String currentStockPrice = order.get(3);
            double existingInventory = map.get(companyName);

            if(orderType.equals("B")){
                
                double totalShareUWB = 0.0;
                
                if(cashOrTotalShares.startsWith("$")){
                    totalShareUWB = Double.parseDouble(cashOrTotalShares.substring(1))/
                                    Double.parseDouble(currentStockPrice);
                }else{
                    totalShareUWB = Double.parseDouble(cashOrTotalShares)/100.0;
                }

                if(existingInventory < totalShareUWB){
                    existingInventory = existingInventory + 1;  
                }

                existingInventory = existingInventory - totalShareUWB;
                existingInventory = existingInventory % 1.00;
                map.put(companyName, existingInventory);

            }else if(orderType.equals("S")){
                
                double totalShareUWS = 0.0;
                
                if(cashOrTotalShares.startsWith("$")){
                    totalShareUWS = Double.parseDouble(cashOrTotalShares.substring(1))/
                                    Double.parseDouble(currentStockPrice);
                }else{
                    totalShareUWS = Double.parseDouble(cashOrTotalShares)/100.0;
                }

                double totalNewInventory = existingInventory + totalShareUWS;
                totalNewInventory = totalNewInventory % 1.00;
                map.put(companyName, totalNewInventory);
            }
        }

        List<List<String>> result = new ArrayList<>(); 

        for (Map.Entry<String, Double> entry : map.entrySet()) {
            List<String> list = new ArrayList<>();
            list.add(entry.getKey());
            list.add(Integer.toString((int)Math.round(entry.getValue() * 100))); 
            result.add(list);
        }

        return result;

     }
}