class Solution:
    def largestEven(self, s: str) -> str:
        n = len(s)
        last = len(s)-1
        #return last
        #s_list=list(s)
        if s[last]=='2':
            return s
        

        while (s[last] =='1' ):
            last-=1
            if last==-1:
                break

        return s[0:last+1]    
            