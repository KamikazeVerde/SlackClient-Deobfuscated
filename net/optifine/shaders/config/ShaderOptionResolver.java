/*    */ package net.optifine.shaders.config;
/*    */ 
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ import net.optifine.expr.IExpression;
/*    */ import net.optifine.expr.IExpressionResolver;
/*    */ 
/*    */ public class ShaderOptionResolver
/*    */   implements IExpressionResolver {
/* 10 */   private Map<String, ExpressionShaderOptionSwitch> mapOptions = new HashMap<>();
/*    */ 
/*    */   
/*    */   public ShaderOptionResolver(ShaderOption[] options) {
/* 14 */     for (int i = 0; i < options.length; i++) {
/*    */       
/* 16 */       ShaderOption shaderoption = options[i];
/*    */       
/* 18 */       if (shaderoption instanceof ShaderOptionSwitch) {
/*    */         
/* 20 */         ShaderOptionSwitch shaderoptionswitch = (ShaderOptionSwitch)shaderoption;
/* 21 */         ExpressionShaderOptionSwitch expressionshaderoptionswitch = new ExpressionShaderOptionSwitch(shaderoptionswitch);
/* 22 */         this.mapOptions.put(shaderoption.getName(), expressionshaderoptionswitch);
/*    */       } 
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public IExpression getExpression(String name) {
/* 29 */     ExpressionShaderOptionSwitch expressionshaderoptionswitch = this.mapOptions.get(name);
/* 30 */     return (IExpression)expressionshaderoptionswitch;
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\optifine\shaders\config\ShaderOptionResolver.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */