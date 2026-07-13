1class Solution {
2    public List<Integer> sequentialDigits(int low, int high) {
3         List<Integer>ans=new ArrayList<>();
4        for(int i=1;i<=9;i++){
5            int num=i;
6            for(int j=i+1;j<=9;j++){
7                 num=num*10+j;
8                 if(num>=low&&num<=high) ans.add(num);
9            }
10        }
11      Collections.sort(ans);
12      return ans;
13    }
14
15}