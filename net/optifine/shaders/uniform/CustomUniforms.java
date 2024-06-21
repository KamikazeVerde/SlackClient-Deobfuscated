/*    */ package net.optifine.shaders.uniform;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ import net.optifine.expr.IExpression;
/*    */ import net.optifine.expr.IExpressionCached;
/*    */ 
/*    */ 
/*    */ public class CustomUniforms
/*    */ {
/*    */   private CustomUniform[] uniforms;
/*    */   private IExpressionCached[] expressionsCached;
/*    */   
/*    */   public CustomUniforms(CustomUniform[] uniforms, Map<String, IExpression> mapExpressions) {
/* 16 */     this.uniforms = uniforms;
/* 17 */     List<IExpressionCached> list = new ArrayList<>();
/*    */     
/* 19 */     for (String s : mapExpressions.keySet()) {
/*    */       
/* 21 */       IExpression iexpression = mapExpressions.get(s);
/*    */       
/* 23 */       if (iexpression instanceof IExpressionCached) {
/*    */         
/* 25 */         IExpressionCached iexpressioncached = (IExpressionCached)iexpression;
/* 26 */         list.add(iexpressioncached);
/*    */       } 
/*    */     } 
/*    */     
/* 30 */     this.expressionsCached = list.<IExpressionCached>toArray(new IExpressionCached[list.size()]);
/*    */   }
/*    */ 
/*    */   
/*    */   public void setProgram(int program) {
/* 35 */     for (int i = 0; i < this.uniforms.length; i++) {
/*    */       
/* 37 */       CustomUniform customuniform = this.uniforms[i];
/* 38 */       customuniform.setProgram(program);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public void update() {
/* 44 */     resetCache();
/*    */     
/* 46 */     for (int i = 0; i < this.uniforms.length; i++) {
/*    */       
/* 48 */       CustomUniform customuniform = this.uniforms[i];
/* 49 */       customuniform.update();
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   private void resetCache() {
/* 55 */     for (int i = 0; i < this.expressionsCached.length; i++) {
/*    */       
/* 57 */       IExpressionCached iexpressioncached = this.expressionsCached[i];
/* 58 */       iexpressioncached.reset();
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public void reset() {
/* 64 */     for (int i = 0; i < this.uniforms.length; i++) {
/*    */       
/* 66 */       CustomUniform customuniform = this.uniforms[i];
/* 67 */       customuniform.reset();
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\optifine\shader\\uniform\CustomUniforms.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */