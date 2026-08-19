1class Solution {
2    public List<List<Integer>> permute(int[] nums) {
3        List<List<Integer>> res = new ArrayList<>();
4        backtrack(nums, 0, res);
5        return res;    
6    }
7
8    private void backtrack(int[] nums, int start, List<List<Integer>> res) {
9        if (start == nums.length) {
10            res.add(arrayToList(nums));
11            return;
12        }
13
14        for (int i = start; i < nums.length; i++) {
15            swap(nums, start, i);
16            backtrack(nums, start + 1, res);
17            swap(nums, start, i);
18        }
19    }
20    
21    private List<Integer> arrayToList(int[] arr) {
22        List<Integer> list = new ArrayList<>();
23        for (int num : arr) {
24            list.add(num);
25        }
26        return list;
27    }
28    
29    private void swap(int[] nums, int i, int j) {
30        int temp = nums[i];
31        nums[i] = nums[j];
32        nums[j] = temp;
33    }    
34}