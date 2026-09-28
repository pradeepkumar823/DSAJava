    char[] str = sentence.toCharArray();
    int n = str.length;
    int count = 1;
    int i = 0, j = 0;
    while (i < n) {
        if (str[i] == ' ') {
            j = 0;
            count++;
        } else if (str[i] != searchWord.charAt(j)) {
            while (i + 1 < n && str[i + 1] != ' ')
                i++;
            j = 0;
            i++;
            count++;
        } else {
            j++;
        }

        if (j == searchWord.length())
            return count;
        i++;
    }

    return -1;

}