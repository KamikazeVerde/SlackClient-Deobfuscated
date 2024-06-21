/*    */ package javax.vecmath;
/*    */ 
/*    */ import java.util.MissingResourceException;
/*    */ import java.util.ResourceBundle;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ class VecMathI18N
/*    */ {
/*    */   static String getString(String paramString) {
/*    */     String str;
/*    */     try {
/* 42 */       str = ResourceBundle.getBundle("javax.vecmath.ExceptionStrings").getString(paramString);
/*    */     }
/* 44 */     catch (MissingResourceException missingResourceException) {
/* 45 */       System.err.println("VecMathI18N: Error looking up: " + paramString);
/* 46 */       str = paramString;
/*    */     } 
/* 48 */     return str;
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\javax\vecmath\VecMathI18N.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */