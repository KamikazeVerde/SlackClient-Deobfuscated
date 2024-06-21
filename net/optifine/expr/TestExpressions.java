/*    */ package net.optifine.expr;
/*    */ 
/*    */ import java.io.BufferedReader;
/*    */ import java.io.InputStreamReader;
/*    */ 
/*    */ 
/*    */ public class TestExpressions
/*    */ {
/*    */   public static void main(String[] args) throws Exception {
/* 10 */     ExpressionParser expressionparser = new ExpressionParser(null);
/*    */ 
/*    */ 
/*    */     
/*    */     while (true) {
/*    */       try {
/* 16 */         InputStreamReader inputstreamreader = new InputStreamReader(System.in);
/* 17 */         BufferedReader bufferedreader = new BufferedReader(inputstreamreader);
/* 18 */         String s = bufferedreader.readLine();
/*    */         
/* 20 */         if (s.length() <= 0) {
/*    */           return;
/*    */         }
/*    */ 
/*    */         
/* 25 */         IExpression iexpression = expressionparser.parse(s);
/*    */         
/* 27 */         if (iexpression instanceof IExpressionFloat) {
/*    */           
/* 29 */           IExpressionFloat iexpressionfloat = (IExpressionFloat)iexpression;
/* 30 */           float f = iexpressionfloat.eval();
/* 31 */           System.out.println("" + f);
/*    */         } 
/*    */         
/* 34 */         if (iexpression instanceof IExpressionBool)
/*    */         {
/* 36 */           IExpressionBool iexpressionbool = (IExpressionBool)iexpression;
/* 37 */           boolean flag = iexpressionbool.eval();
/* 38 */           System.out.println("" + flag);
/*    */         }
/*    */       
/* 41 */       } catch (Exception exception) {
/*    */         
/* 43 */         exception.printStackTrace();
/*    */       } 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\optifine\expr\TestExpressions.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */