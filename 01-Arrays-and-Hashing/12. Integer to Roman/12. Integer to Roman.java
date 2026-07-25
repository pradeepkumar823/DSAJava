class Solution {
    public String intToRoman(int num) {
        char[] c = new char[15];
        int i = 0;
        
        while (num >= 1000) { c[i++] = 'M'; num -= 1000; }
        if (num >= 900) { c[i++] = 'C'; c[i++] = 'M'; num -= 900; }
        if (num >= 500) { c[i++] = 'D'; num -= 500; }
        if (num >= 400) { c[i++] = 'C'; c[i++] = 'D'; num -= 400; }
        
        while (num >= 100) { c[i++] = 'C'; num -= 100; }
        if (num >= 90) { c[i++] = 'X'; c[i++] = 'C'; num -= 90; }
        if (num >= 50) { c[i++] = 'L'; num -= 50; }
        if (num >= 40) { c[i++] = 'X'; c[i++] = 'L'; num -= 40; }
        
        while (num >= 10) { c[i++] = 'X'; num -= 10; }
        if (num >= 9) { c[i++] = 'I'; c[i++] = 'X'; num -= 9; }
        if (num >= 5) { c[i++] = 'V'; num -= 5; }
        if (num >= 4) { c[i++] = 'I'; c[i++] = 'V'; num -= 4; }
        
        while (num >= 1) { c[i++] = 'I'; num -= 1; }
        
        return new String(c, 0, i);
    }
}