1class Solution {
2    public String shortestCompletingWord(String licensePlate, String[] words) {
3
4        int[] expectedLetters = countLetters(licensePlate);
5
6        String best = null;
7        for (String word : words) {
8            int[] wordLetters = countLetters(word);
9
10            if (checkWord(expectedLetters, wordLetters) && (best == null || best.length() > word.length())) {
11                best = word;
12            }
13        }
14
15        return best;
16    }
17
18    private int[] countLetters(String s) {
19        int[] arr = new int[26];
20        for (char ch : s.toCharArray()) {
21            if (Character.isLetter(ch)) {
22                arr[Character.toLowerCase(ch) - 'a']++;
23            }
24        }
25        return arr;
26    }
27
28    private boolean checkWord(int[] expected, int[] current) {
29        for (int i = 0; i < 26; i++) {
30            if (current[i] < expected[i]) {
31                return false;
32            }
33        }
34        return true;
35    }
36}