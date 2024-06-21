/*    */ package net.optifine.shaders.config;
/*    */ 
/*    */ import java.util.Map;
/*    */ import net.minecraft.src.Config;
/*    */ import net.optifine.expr.ConstantFloat;
/*    */ import net.optifine.expr.FunctionBool;
/*    */ import net.optifine.expr.FunctionType;
/*    */ import net.optifine.expr.IExpression;
/*    */ import net.optifine.expr.IExpressionResolver;
/*    */ 
/*    */ public class MacroExpressionResolver
/*    */   implements IExpressionResolver {
/* 13 */   private Map<String, String> mapMacroValues = null;
/*    */ 
/*    */   
/*    */   public MacroExpressionResolver(Map<String, String> mapMacroValues) {
/* 17 */     this.mapMacroValues = mapMacroValues;
/*    */   }
/*    */ 
/*    */   
/*    */   public IExpression getExpression(String name) {
/* 22 */     String s = "defined_";
/*    */     
/* 24 */     if (name.startsWith(s)) {
/*    */       
/* 26 */       String s2 = name.substring(s.length());
/* 27 */       return this.mapMacroValues.containsKey(s2) ? (IExpression)new FunctionBool(FunctionType.TRUE, null) : (IExpression)new FunctionBool(FunctionType.FALSE, null);
/*    */     } 
/*    */ 
/*    */     
/* 31 */     while (this.mapMacroValues.containsKey(name)) {
/*    */       
/* 33 */       String s1 = this.mapMacroValues.get(name);
/*    */       
/* 35 */       if (s1 == null || s1.equals(name)) {
/*    */         break;
/*    */       }
/*    */ 
/*    */       
/* 40 */       name = s1;
/*    */     } 
/*    */     
/* 43 */     int i = Config.parseInt(name, -2147483648);
/*    */     
/* 45 */     if (i == Integer.MIN_VALUE) {
/*    */       
/* 47 */       Config.warn("Unknown macro value: " + name);
/* 48 */       return (IExpression)new ConstantFloat(0.0F);
/*    */     } 
/*    */ 
/*    */     
/* 52 */     return (IExpression)new ConstantFloat(i);
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\optifine\shaders\config\MacroExpressionResolver.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */