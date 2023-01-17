
package fastfood.util;

import java.text.DecimalFormat;

public class FormatNumber {
    public static String withLargeIntegers(double value,int sl) {
        String x = "VND ###,###";
        if(sl>0){
            x += ".";
            for (int i = 0; i < sl; i++) {
                x += "0";
            }
        }
        DecimalFormat df = new DecimalFormat(x);
        return df.format(value);
    }
}
