import java.util.*;

class Solution {
    public List<List<String>> buildPortfolio(List<List<String>> trades) {
        
        int cash = 1000;
        
        Map<String, Integer> portfolio = new TreeMap<>();

        for(List<String> stock : trades){

            String  stockName   = stock.get(1);
            String stockType    = stock.get(2);
            int quantity        = Integer.parseInt(stock.get(3));
            int price           = Integer.parseInt(stock.get(4));

            if(stockType.equals("B")){
                
                cash = cash - (quantity * price);

                if(portfolio.containsKey(stockName)){
                    portfolio.put(stockName, portfolio.get(stockName) + quantity);
                }else{
                    portfolio.put(stockName, quantity);
                }
            }else{
                cash = cash + (quantity * price);

                int remainingStock = portfolio.get(stockName) - quantity;

                if(remainingStock > 0 ){
                    portfolio.put(stockName, remainingStock);
                }else{
                    portfolio.remove(stockName);
                }
                
            }
        }

        List<List<String>> result = new LinkedList<>(); 
        result.add(Arrays.asList("CASH", Integer.toString(cash)));

        portfolio.entrySet().forEach(entry -> {
            result.add(Arrays.asList(entry.getKey(), entry.getValue().toString()));
        });


        return result;
     }
}