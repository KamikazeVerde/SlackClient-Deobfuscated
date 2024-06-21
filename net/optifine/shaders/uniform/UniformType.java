/*     */ package net.optifine.shaders.uniform;
/*     */ 
/*     */ import net.optifine.expr.ExpressionType;
/*     */ import net.optifine.expr.IExpression;
/*     */ import net.optifine.expr.IExpressionBool;
/*     */ import net.optifine.expr.IExpressionFloat;
/*     */ import net.optifine.expr.IExpressionFloatArray;
/*     */ 
/*     */ public enum UniformType
/*     */ {
/*  11 */   BOOL,
/*  12 */   INT,
/*  13 */   FLOAT,
/*  14 */   VEC2,
/*  15 */   VEC3,
/*  16 */   VEC4;
/*     */ 
/*     */   
/*     */   public ShaderUniformBase makeShaderUniform(String name) {
/*  20 */     switch (this) {
/*     */       
/*     */       case BOOL:
/*  23 */         return new ShaderUniform1i(name);
/*     */       
/*     */       case INT:
/*  26 */         return new ShaderUniform1i(name);
/*     */       
/*     */       case FLOAT:
/*  29 */         return new ShaderUniform1f(name);
/*     */       
/*     */       case VEC2:
/*  32 */         return new ShaderUniform2f(name);
/*     */       
/*     */       case VEC3:
/*  35 */         return new ShaderUniform3f(name);
/*     */       
/*     */       case VEC4:
/*  38 */         return new ShaderUniform4f(name);
/*     */     } 
/*     */     
/*  41 */     throw new RuntimeException("Unknown uniform type: " + this);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateUniform(IExpression expression, ShaderUniformBase uniform) {
/*  47 */     switch (this) {
/*     */       
/*     */       case BOOL:
/*  50 */         updateUniformBool((IExpressionBool)expression, (ShaderUniform1i)uniform);
/*     */         return;
/*     */       
/*     */       case INT:
/*  54 */         updateUniformInt((IExpressionFloat)expression, (ShaderUniform1i)uniform);
/*     */         return;
/*     */       
/*     */       case FLOAT:
/*  58 */         updateUniformFloat((IExpressionFloat)expression, (ShaderUniform1f)uniform);
/*     */         return;
/*     */       
/*     */       case VEC2:
/*  62 */         updateUniformFloat2((IExpressionFloatArray)expression, (ShaderUniform2f)uniform);
/*     */         return;
/*     */       
/*     */       case VEC3:
/*  66 */         updateUniformFloat3((IExpressionFloatArray)expression, (ShaderUniform3f)uniform);
/*     */         return;
/*     */       
/*     */       case VEC4:
/*  70 */         updateUniformFloat4((IExpressionFloatArray)expression, (ShaderUniform4f)uniform);
/*     */         return;
/*     */     } 
/*     */     
/*  74 */     throw new RuntimeException("Unknown uniform type: " + this);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void updateUniformBool(IExpressionBool expression, ShaderUniform1i uniform) {
/*  80 */     boolean flag = expression.eval();
/*  81 */     int i = flag ? 1 : 0;
/*  82 */     uniform.setValue(i);
/*     */   }
/*     */ 
/*     */   
/*     */   private void updateUniformInt(IExpressionFloat expression, ShaderUniform1i uniform) {
/*  87 */     int i = (int)expression.eval();
/*  88 */     uniform.setValue(i);
/*     */   }
/*     */ 
/*     */   
/*     */   private void updateUniformFloat(IExpressionFloat expression, ShaderUniform1f uniform) {
/*  93 */     float f = expression.eval();
/*  94 */     uniform.setValue(f);
/*     */   }
/*     */ 
/*     */   
/*     */   private void updateUniformFloat2(IExpressionFloatArray expression, ShaderUniform2f uniform) {
/*  99 */     float[] afloat = expression.eval();
/*     */     
/* 101 */     if (afloat.length != 2)
/*     */     {
/* 103 */       throw new RuntimeException("Value length is not 2, length: " + afloat.length);
/*     */     }
/*     */ 
/*     */     
/* 107 */     uniform.setValue(afloat[0], afloat[1]);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void updateUniformFloat3(IExpressionFloatArray expression, ShaderUniform3f uniform) {
/* 113 */     float[] afloat = expression.eval();
/*     */     
/* 115 */     if (afloat.length != 3)
/*     */     {
/* 117 */       throw new RuntimeException("Value length is not 3, length: " + afloat.length);
/*     */     }
/*     */ 
/*     */     
/* 121 */     uniform.setValue(afloat[0], afloat[1], afloat[2]);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void updateUniformFloat4(IExpressionFloatArray expression, ShaderUniform4f uniform) {
/* 127 */     float[] afloat = expression.eval();
/*     */     
/* 129 */     if (afloat.length != 4)
/*     */     {
/* 131 */       throw new RuntimeException("Value length is not 4, length: " + afloat.length);
/*     */     }
/*     */ 
/*     */     
/* 135 */     uniform.setValue(afloat[0], afloat[1], afloat[2], afloat[3]);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean matchesExpressionType(ExpressionType expressionType) {
/* 141 */     switch (this) {
/*     */       
/*     */       case BOOL:
/* 144 */         return (expressionType == ExpressionType.BOOL);
/*     */       
/*     */       case INT:
/* 147 */         return (expressionType == ExpressionType.FLOAT);
/*     */       
/*     */       case FLOAT:
/* 150 */         return (expressionType == ExpressionType.FLOAT);
/*     */       
/*     */       case VEC2:
/*     */       case VEC3:
/*     */       case VEC4:
/* 155 */         return (expressionType == ExpressionType.FLOAT_ARRAY);
/*     */     } 
/*     */     
/* 158 */     throw new RuntimeException("Unknown uniform type: " + this);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static UniformType parse(String type) {
/* 164 */     UniformType[] auniformtype = values();
/*     */     
/* 166 */     for (int i = 0; i < auniformtype.length; i++) {
/*     */       
/* 168 */       UniformType uniformtype = auniformtype[i];
/*     */       
/* 170 */       if (uniformtype.name().toLowerCase().equals(type))
/*     */       {
/* 172 */         return uniformtype;
/*     */       }
/*     */     } 
/*     */     
/* 176 */     return null;
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\optifine\shader\\uniform\UniformType.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */