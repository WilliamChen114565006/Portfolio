public class FloatDecompose {
    public static int getSign(float value) {
        //converts the float value to the int representation of the binary value then shifts it so that it can see what the 32nd bit is, being 1 if negative and 0 if positive
        int convertedValue=Float.floatToIntBits(value);
        convertedValue>>>=31;

        return convertedValue;
    } 

    public static int getExponent(float value) {
        //shifts the value by 1 to the left to get rid of the signed bit (both if 0 or 1) and then shifts back 24 bits to obtain the 8 bits for the exponent part
        int convertedValue=Float.floatToIntBits(value);
        convertedValue<<=1;
        convertedValue>>>=24;

        return convertedValue;
    } 

    public static int getFraction(float value) {
        //converts the float value to the int value before clearing the leftmost 9 bits, leaving just the fraction part
        int convertedValue=Float.floatToIntBits(value);
        convertedValue &= ~0xff800000;

        return convertedValue;
    } 

    public static float makeFloat(int sign, int exp, int frac) {
        //shifts each value (the sign and exp) to the their position in IEEE 754, with the sign being the 32nd and so forth. Then, add all the shifted values together and converted to float
        int signShift=sign<<31;
        int expShift=exp<<23;
        int total=(signShift | expShift) | frac;
        float totalToFloat=Float.intBitsToFloat(total);
        
        return totalToFloat;
    }
}