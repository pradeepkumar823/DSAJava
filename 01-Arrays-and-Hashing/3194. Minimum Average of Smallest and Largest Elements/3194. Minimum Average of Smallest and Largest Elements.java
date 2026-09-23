class Solution {
    public double minimumAverage(int[] nums) {
        int n = nums.length;
        int temp = n;
        double minAverage = Integer.MAX_VALUE;
        while(temp >= 2){
            int minElement = Integer.MAX_VALUE;
            int maxElement = Integer.MIN_VALUE;
            for(int i = 0; i < n; i++){
                if(nums[i] > 0){
                    minElement = Math.min(minElement, nums[i]);
                    maxElement = Math.max(maxElement, nums[i]);
                }
            }
            for(int i = 0; i < n; i++){
                if(nums[i] == minElement){
                    nums[i] = 0;
                    break;
                }
            }
            for(int i = 0; i < n; i++){
                if(nums[i] == maxElement){
                    nums[i] = 0;
                    break;
                }
            }
            double average = (minElement + maxElement) / 2.0;
            minAverage = Math.min(minAverage, average);
            temp -= 2;
        }
        return minAverage;

    }
}