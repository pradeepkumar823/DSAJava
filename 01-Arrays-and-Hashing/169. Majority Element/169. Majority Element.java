1class Solution {
2    public int majorityElement(int[] nums) {
3        int count=0;
4        int result=0;
5        
6
7        for(int num:nums){
8          if(count==0){
9            result=num;
10          }
11        if(result==num){
12            count++;
13        }else{
14            count--;
15        }
16        }
17        return result;
18    }
19}