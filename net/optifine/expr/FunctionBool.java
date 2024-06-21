/*    */ package net.optifine.expr;
/*    */ 
/*    */ public class FunctionBool
/*    */   implements IExpressionBool
/*    */ {
/*    */   private FunctionType type;
/*    */   private IExpression[] arguments;
/*    */   
/*    */   public FunctionBool(FunctionType type, IExpression[] arguments) {
/* 10 */     this.type = type;
/* 11 */     this.arguments = arguments;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean eval() {
/* 16 */     return this.type.evalBool(this.arguments);
/*    */   }
/*    */ 
/*    */   
/*    */   public ExpressionType getExpressionType() {
/* 21 */     return ExpressionType.BOOL;
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 26 */     return "" + this.type + "()";
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\optifine\expr\FunctionBool.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */