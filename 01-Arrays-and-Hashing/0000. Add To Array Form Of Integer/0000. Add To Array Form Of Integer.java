  class Solution {
     public:
    vector<int> addToArrayForm(vector<int>& num, int k) {
       int n=num.size();
       vector<int>ans;
       string str=to_string(k);
       int strsize=str.size();
       string j;
      if(strsize>n){
          for(auto val: num){
              j+=to_string(val);
         } 
      int s=0;
      stringstream ss;
      ss<<j;
      ss>>s;
      int l=(s)+k;
      while(l>0){
          ans.push_back(l%10);
          l=l/10;
      }
      reverse(ans.begin(),ans.end());
      return ans;
    }
    int size=n;
    int count=0;
    vector<int>temp(n,0);
    while(k>0){
       temp[size-1]=k%10;
       k=k/10;
       size--;
    } 

    for(int i=n-1;i>=0;--i){
        int tempo=temp[i]+num[i];
        if(count!=0){
            tempo+=1;
            count=0;
        }
        if(tempo>9){
            ans.push_back(tempo%10);
            count=1;
        }else{
            ans.push_back(tempo);
        }
       
    }  
     reverse(ans.begin(),ans.end());
     if(count!=0){
            ans.insert(ans.begin(),1);
        }
 
    return ans;
}
};