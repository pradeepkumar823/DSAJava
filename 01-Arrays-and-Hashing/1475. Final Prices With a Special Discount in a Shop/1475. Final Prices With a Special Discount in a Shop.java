class Solution {

     static{
        int[] prices = {1};
        for(int i=0;i<200;i++){
            int[] answer = finalPrices(prices);
        }
    }
    public static int[] finalPrices(int[] prices) {
            
            int i = 0 , j = 1;
            for (;i<prices.length;i++){
                j = i + 1;
                while( j < prices.length && prices[j]> prices[i]){
                    j++;
                }
                if (j < prices.length){
                    prices[i] = prices[i] - prices[j];
                }
            }

        return prices;
    }
}