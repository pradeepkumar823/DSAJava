class Solution {
    public int[] nextGreaterElement(int[] nums1, int[] nums2) {
        int freq[] = new int[10001];
        int stack[] = new int[nums2.length];
        int top=-1;
        for(int i=nums2.length-1;i>=0;i--){
               int num = nums2[i];
               while(top>=0&&stack[top]<=num){
                   top--;
               }
               freq[num] = (top == -1)? -1: stack[top];
               top++;
               stack[top] = num;
        }
        int ans[] = new int[nums1.length];
        for(int i=0;i<nums1.length;i++){
            ans[i] = freq[nums1[i]];
        }
        return ans;
    }
}