/*    */ package net.optifine.expr;
/*    */ 
/*    */ public class ConstantFloat
/*    */   implements IExpressionFloat
/*    */ {
/*    */   private float value;
/*    */   
/*    */   public ConstantFloat(float value) {
/*  9 */     this.value = value;
/*    */   }
/*    */ 
/*    */   
/*    */   public float eval() {
/* 14 */     return this.value;
/*    */   }
/*    */ 
/*    */   
/*    */   public ExpressionType getExpressionType() {
/* 19 */     return ExpressionType.FLOAT;
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 24 */     return "" + this.value;
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\optifine\expr\ConstantFloat.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */