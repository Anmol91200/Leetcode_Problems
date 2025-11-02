class Solution {
    public int lengthOfLongestSubstring(String s) {
         Map<Character, Integer> lastseen= new HashMap<>();
        int left=0;
        int maxlen=0;

        for(int right=0;right<s.length();right++){
            char ch=s.charAt(right);

            if(lastseen.containsKey(ch) && lastseen.get(ch) >= left){
                left=lastseen.get(ch)+1;
            }
            lastseen.put(ch,right);

            maxlen=Math.max(maxlen,right-left+1);
        }
        return maxlen;
        
    }
}
