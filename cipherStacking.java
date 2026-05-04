/*
import java.util.ArrayList;
public class cipherStacking{
    public static void main(String[]args){
        // The purpose of the linear cipher is to encode the save system so it is
        // unreadable to the player. The text will be quite simple, actually.
        System.out.print(vigenereCipher(linearCipher("goodmorningfriendsthisisacomplexcipheranditworks")));
    }
    // This linear cipher has a decimation of 7 and a shift of 18
    public static String linearCipher(String s){
        ArrayList<Integer> nums = new ArrayList<Integer>();
        for(int i=0; i<s.length(); i++){
            // does some operations to get a new int x corresponding to a character
            int x = s.charAt(i);
            x-=97;
            x*=7;
            x+=18;
            nums.add(x);
        }
        // total variable that adds each character
        String total = "";
        for(int i=0; i<nums.size(); i++){
            // does some operations to get that character to a lowercase letter
            int y = nums.get(i);
            y%=26;
            y+=97;
            total = total + (char)y;
        }
        return total;
    }
    public static String vigenereCipher(String s){
        // declares a total
        String total = "";
        // this key will be used as a shift
        String key = "landonvanberkum";
        for(int i=0; i<s.length(); i++){
            // declares various different int variables
            int x = s.charAt(i);
            int keyNum = key.charAt(i%15);
            int finalNum;
            // does some operations so we can properly shift the characters
            x-=97;
            keyNum-=97;
            finalNum = x+keyNum;
            finalNum%=26;
            finalNum+=97;
            // adds to the total
            total += (char)finalNum;
        }
        return total;
    }
}
*/