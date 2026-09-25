class Solution {

    public List<String> braceExpansionII(String expression) {
        Set<String> result = dfs(expression, 0, expression.length() - 1);
        return new ArrayList<>(result);
    }

    private Set<String> dfs(String s, int l, int r) {

        Set<String> result = new TreeSet<>();
        int balance = 0;

        // Check for top-level comma
        for (int i = l; i <= r; i++) {

            if (s.charAt(i) == '{') {
                balance++;
            } 
            else if (s.charAt(i) == '}') {
                balance--;
            } 
            else if (s.charAt(i) == ',' && balance == 0) {

                result.addAll(dfs(s, l, i - 1));
                result.addAll(dfs(s, i + 1, r));

                return result;
            }
        }

        // If entire expression is inside braces
        if (s.charAt(l) == '{' && matchingBrace(s, l) == r) {
            return dfs(s, l + 1, r - 1);
        }

        // Concatenation
        result.add("");

        int i = l;

        while (i <= r) {

            Set<String> part;

            if (s.charAt(i) == '{') {

                int j = matchingBrace(s, i);

                part = dfs(s, i + 1, j - 1);

                i = j + 1;

            } else {

                part = new TreeSet<>();
                part.add(String.valueOf(s.charAt(i)));

                i++;
            }

            // Combine current results with this part
            Set<String> newResult = new TreeSet<>();

            for (String a : result) {
                for (String b : part) {
                    newResult.add(a + b);
                }
            }

            result = newResult;
        }

        return result;
    }

    private int matchingBrace(String s, int start) {

        int balance = 0;

        for (int i = start; i < s.length(); i++) {

            if (s.charAt(i) == '{') {
                balance++;
            } 
            else if (s.charAt(i) == '}') {
                balance--;

                if (balance == 0) {
                    return i;
                }
            }
        }

        return -1;
    }
}