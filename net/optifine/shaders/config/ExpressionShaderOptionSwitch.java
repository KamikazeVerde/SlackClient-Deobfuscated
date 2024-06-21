/*    */ package net.optifine.shaders.config;
/*    */ 
/*    */ import net.optifine.expr.ExpressionType;
/*    */ import net.optifine.expr.IExpressionBool;
/*    */ 
/*    */ public class ExpressionShaderOptionSwitch
/*    */   implements IExpressionBool
/*    */ {
/*    */   private ShaderOptionSwitch shaderOption;
/*    */   
/*    */   public ExpressionShaderOptionSwitch(ShaderOptionSwitch shaderOption) {
/* 12 */     this.shaderOption = shaderOption;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean eval() {
/* 17 */     return ShaderOptionSwitch.isTrue(this.shaderOption.getValue());
/*    */   }
/*    */ 
/*    */   
/*    */   public ExpressionType getExpressionType() {
/* 22 */     return ExpressionType.BOOL;
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 27 */     return "" + this.shaderOption;
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\optifine\shaders\config\ExpressionShaderOptionSwitch.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */