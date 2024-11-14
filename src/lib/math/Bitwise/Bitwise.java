package src.lib.math.Bitwise;

import java.lang.NumberFormatException;

public class Bitwise {
    public static int pow(int x, int n) {
        if((n|x)==0){
            return 1;
        }
        if(n<0){
            throw new NumberFormatException("n must be a value greater than or equal to 0.");
        }
        int res = 1;
        while(n > 0){
            if((n & 0b1)==0b1){
                res *= x;
            }
            x*=x;
            n>>=1;
        }
        return res;

    }
}
