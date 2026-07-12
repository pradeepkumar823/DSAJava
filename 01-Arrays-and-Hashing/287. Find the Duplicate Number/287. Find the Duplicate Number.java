1class Solution {
2    public int findDuplicate(int[] nums) {
3        HashSet<Integer> hs=new HashSet<>();
4        for(int num:nums){
5            if(hs.contains(num)){
6                return num;
7            }
8            hs.add(num);
9        }
10        return -1;
11    }
12}