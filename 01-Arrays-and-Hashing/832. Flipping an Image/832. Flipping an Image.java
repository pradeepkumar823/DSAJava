1class Solution 
2{
3    public static void flip(int[] arr) 
4    {
5        int i = 0;
6        int j = arr.length - 1;
7        while (i < j) 
8        {
9            int temp = arr[i];
10            arr[i] = arr[j];
11            arr[j] = temp;
12            i++;
13            j--;
14        }
15    }
16    public int[][] flipAndInvertImage(int[][] opposite) 
17    {
18        int n = opposite.length;
19        for(int i=0;i<n;i++)
20        {   
21            flip(opposite[i]); // Flip
22            for(int j=0;j<n;j++)
23                opposite[i][j] = (opposite[i][j] == 1) ? 0 : 1; // Invert
24        }   
25        return opposite;
26    }
27}