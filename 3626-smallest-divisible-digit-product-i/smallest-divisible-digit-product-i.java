class Solution {
    public int smallestNumber(int n, int t) {

    for(int i = n ; ;i++){
        int pdct=1;
        int num=i;
        while(num>0){
            pdct *= num%10;
            num /=10;
        }
        if(pdct%t==0)
        return i;
    }
        
    }
}