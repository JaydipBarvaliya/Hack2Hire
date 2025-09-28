import java.util.*;

class Solution {
    public List<List<String>> marginCall(List<List<String>> trades) {

        int cash = 1000;

        Map<String, Integer> portfolio = new TreeMap<>();
        Map<String, Integer> lastReportedPrice = new TreeMap<>();

        for (List<String> stock : trades) {

            String stockName = stock.get(1);
            String stockType = stock.get(2);
            int quantity = Integer.parseInt(stock.get(3));
            int price = Integer.parseInt(stock.get(4));

            if (stockType.equals("B")) {

                int requiredCash = quantity * price;

                portfolio.put(stockName, portfolio.getOrDefault(stockName, 0) + quantity); // put the stock in the
                                                                                           // portfolio immediately
                cash = cash - requiredCash;
                lastReportedPrice.put(stockName, price);

                if (cash < 0) {

                    int deficit = -cash; // cash was nagative but we are making it positive to understand logically

                    PriorityQueue<Stock> maxHeap = new PriorityQueue<>((a,
                            b) -> b.price != a.price ? Integer.compare(b.price, a.price) : a.name.compareTo(b.name));

                    portfolio.entrySet().stream().forEach(entry -> {
                        maxHeap.offer(
                                new Stock(entry.getKey(), entry.getValue(), lastReportedPrice.get(entry.getKey())));
                    });

                    while (deficit > 0 && !maxHeap.isEmpty()) { // deficit > 0 means I have still some deficit to fill
                                                                // up

                        Stock top = maxHeap.poll();

                        int sellQty = Math.min(top.quantity, (int) Math.ceil((double) deficit / top.price));
                        int sellCash = sellQty * top.price;

                        cash = cash + sellCash;
                        deficit = deficit - sellCash;

                        // Update portfolio using sellQty
                        int remainingStock = portfolio.get(top.name) - sellQty;
                        if (remainingStock == 0) {
                            portfolio.remove(top.name);
                        } else {
                            portfolio.put(top.name, remainingStock);
                        }
                    }
                }

            } else {
                cash = cash + (quantity * price);

                int remainingStock = portfolio.get(stockName) - quantity;

                if (remainingStock == 0) {
                    portfolio.remove(stockName);
                } else {
                    portfolio.put(stockName, remainingStock);

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

    public static class Stock {
        String name;
        int quantity;
        int price;

        public Stock(String name, int quantity, int price) {
            this.name = name;
            this.quantity = quantity;
            this.price = price;
        }
    }
}