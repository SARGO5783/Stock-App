/*
 * Use this utility class to format numbers
 */
package utilities.formatters;

import java.text.DecimalFormat;
import java.text.NumberFormat;

public class CurrencyFormatter {

   public static String formatCurrency(Double value) {
      NumberFormat formatter = NumberFormat.getCurrencyInstance();
      return formatter.format(value);
   }

    public static float formatCurrencyNoDollarSign(Double value) {
      DecimalFormat df = new DecimalFormat("#.00");
      float number = Float.valueOf(df.format(value));
      return number;
   }
    
}
