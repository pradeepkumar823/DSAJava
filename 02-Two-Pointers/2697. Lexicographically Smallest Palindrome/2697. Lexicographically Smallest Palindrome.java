1class Solution {
2    public String makeSmallestPalindrome(String s) {
3        char[] arr=s.toCharArray();
4        int left=0;
5        int right=s.length()-1;
6        while(left<right){
7            if(arr[left]<arr[right]){
8                arr[right]=arr[left];
9               
10            }
11            else{
12                arr[left]=arr[right];
13            }
14            left++;
15            right--;
16        }
17return new String(arr);
18        
19    }
20}