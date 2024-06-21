/*     */ package net.optifine.shaders.uniform;
/*     */ 
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import net.minecraft.world.biome.BiomeGenBase;
/*     */ import net.optifine.expr.ConstantFloat;
/*     */ import net.optifine.expr.IExpression;
/*     */ import net.optifine.expr.IExpressionResolver;
/*     */ import net.optifine.shaders.SMCLog;
/*     */ 
/*     */ public class ShaderExpressionResolver
/*     */   implements IExpressionResolver {
/*  13 */   private Map<String, IExpression> mapExpressions = new HashMap<>();
/*     */ 
/*     */   
/*     */   public ShaderExpressionResolver(Map<String, IExpression> map) {
/*  17 */     registerExpressions();
/*     */     
/*  19 */     for (String s : map.keySet()) {
/*     */       
/*  21 */       IExpression iexpression = map.get(s);
/*  22 */       registerExpression(s, iexpression);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void registerExpressions() {
/*  28 */     ShaderParameterFloat[] ashaderparameterfloat = ShaderParameterFloat.values();
/*     */     
/*  30 */     for (int i = 0; i < ashaderparameterfloat.length; i++) {
/*     */       
/*  32 */       ShaderParameterFloat shaderparameterfloat = ashaderparameterfloat[i];
/*  33 */       addParameterFloat(this.mapExpressions, shaderparameterfloat);
/*     */     } 
/*     */     
/*  36 */     ShaderParameterBool[] ashaderparameterbool = ShaderParameterBool.values();
/*     */     
/*  38 */     for (int k = 0; k < ashaderparameterbool.length; k++) {
/*     */       
/*  40 */       ShaderParameterBool shaderparameterbool = ashaderparameterbool[k];
/*  41 */       this.mapExpressions.put(shaderparameterbool.getName(), shaderparameterbool);
/*     */     } 
/*     */     
/*  44 */     for (BiomeGenBase biomegenbase : BiomeGenBase.BIOME_ID_MAP.values()) {
/*     */       
/*  46 */       String s = biomegenbase.biomeName.trim();
/*  47 */       s = "BIOME_" + s.toUpperCase().replace(' ', '_');
/*  48 */       int j = biomegenbase.biomeID;
/*  49 */       ConstantFloat constantFloat = new ConstantFloat(j);
/*  50 */       registerExpression(s, (IExpression)constantFloat);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void addParameterFloat(Map<String, IExpression> map, ShaderParameterFloat spf) {
/*  56 */     String[] astring = spf.getIndexNames1();
/*     */     
/*  58 */     if (astring == null) {
/*     */       
/*  60 */       map.put(spf.getName(), new ShaderParameterIndexed(spf));
/*     */     }
/*     */     else {
/*     */       
/*  64 */       for (int i = 0; i < astring.length; i++) {
/*     */         
/*  66 */         String s = astring[i];
/*  67 */         String[] astring1 = spf.getIndexNames2();
/*     */         
/*  69 */         if (astring1 == null) {
/*     */           
/*  71 */           map.put(spf.getName() + "." + s, new ShaderParameterIndexed(spf, i));
/*     */         }
/*     */         else {
/*     */           
/*  75 */           for (int j = 0; j < astring1.length; j++) {
/*     */             
/*  77 */             String s1 = astring1[j];
/*  78 */             map.put(spf.getName() + "." + s + "." + s1, new ShaderParameterIndexed(spf, i, j));
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean registerExpression(String name, IExpression expr) {
/*  87 */     if (this.mapExpressions.containsKey(name)) {
/*     */       
/*  89 */       SMCLog.warning("Expression already defined: " + name);
/*  90 */       return false;
/*     */     } 
/*     */ 
/*     */     
/*  94 */     this.mapExpressions.put(name, expr);
/*  95 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public IExpression getExpression(String name) {
/* 101 */     return this.mapExpressions.get(name);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasExpression(String name) {
/* 106 */     return this.mapExpressions.containsKey(name);
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\optifine\shader\\uniform\ShaderExpressionResolver.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */