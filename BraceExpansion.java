
// In this problem, using dfs and backtracking. Creating a list of list first from the given string, and then calling dfs on it.
// Time Complexity : O(k^n) if there are k blocks of avg n size each
// Space Complexity : O(nk)
// Did this code successfully run on Leetcode : yes
// Any problem you faced while coding this : no
import java.util.*;

class BraceExpansion {
    List<String> result;
    List<List<Character>> blocks;

    public String[] expand(String s) {
        // Base case
        if (s == null || s.length() == 0) {
            return new String[] {};
        }
        result = new ArrayList<>();
        blocks = new ArrayList<>();
        int i = 0;
        // Loop till s, and make a list of list
        while (i < s.length()) {
            // Take one char
            char c = s.charAt(i);
            // Declare list of char
            List<Character> block = new ArrayList<>();
            // Check if it is opening bracket, then each char in it will be in one list
            if (c == '{') {
                // Do i++
                i++;
                // Loop till you find closing
                while (s.charAt(i) != '}') {
                    // If it is not comma
                    if (s.charAt(i) != ',') {
                        // Then add char to the list
                        block.add(s.charAt(i));
                    }
                    // i++
                    i++;
                }
            } else {
                // Else if it is char, individual char will have its own list
                block.add(c);
            }
            // Sort this inner list
            Collections.sort(block);
            // Add this inner list to the blocks list
            blocks.add(block);
            i++;
        }
        // Call the backtracking method
        Backtrack(0, new StringBuilder());
        // We have result in list, but we have to return array so create an array and
        // put all values of list in array
        String[] st = new String[result.size()];
        for (int j = 0; j < result.size(); j++) {
            st[j] = result.get(j);
        }
        return st;
    }

    private void Backtrack(int index, StringBuilder sb) {
        // Base
        // Whenever index becomes equal to block size, add the string in sb to the
        // result
        if (index == blocks.size()) {
            result.add(sb.toString());
            return;
        }
        // Logic
        // Get the list present at index
        List<Character> l1 = blocks.get(index);
        // Go over each char
        for (char c1 : l1) {
            // Take the sb length before adding char
            int lengthsb = sb.length();
            // Append char
            sb.append(c1);
            // Now call the backtrack for next index
            Backtrack(index + 1, sb);
            // Once call finishes, set the length to what it was previously
            sb.setLength(lengthsb);
        }

    }

    public static void main(String[] args) {
        String s1 = "{a,b}c{d,e}f";
        BraceExpansion s = new BraceExpansion();
        String[] res = s.expand(s1);
        for (int x = 0; x < res.length; x++) {
            System.out.println(res[x] + " ");
        }

    }
}