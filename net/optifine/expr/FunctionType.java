/*     */ package net.optifine.expr;
/*     */ 
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.multiplayer.WorldClient;
/*     */ import net.minecraft.src.Config;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.optifine.shaders.uniform.Smoother;
/*     */ import net.optifine.util.MathUtils;
/*     */ 
/*     */ public enum FunctionType
/*     */ {
/*  14 */   PLUS(10, ExpressionType.FLOAT, "+", new ExpressionType[] { ExpressionType.FLOAT, ExpressionType.FLOAT }),
/*  15 */   MINUS(10, ExpressionType.FLOAT, "-", new ExpressionType[] { ExpressionType.FLOAT, ExpressionType.FLOAT }),
/*  16 */   MUL(11, ExpressionType.FLOAT, "*", new ExpressionType[] { ExpressionType.FLOAT, ExpressionType.FLOAT }),
/*  17 */   DIV(11, ExpressionType.FLOAT, "/", new ExpressionType[] { ExpressionType.FLOAT, ExpressionType.FLOAT }),
/*  18 */   MOD(11, ExpressionType.FLOAT, "%", new ExpressionType[] { ExpressionType.FLOAT, ExpressionType.FLOAT }),
/*  19 */   NEG(12, ExpressionType.FLOAT, "neg", new ExpressionType[] { ExpressionType.FLOAT }),
/*  20 */   PI(ExpressionType.FLOAT, "pi", new ExpressionType[0]),
/*  21 */   SIN(ExpressionType.FLOAT, "sin", new ExpressionType[] { ExpressionType.FLOAT }),
/*  22 */   COS(ExpressionType.FLOAT, "cos", new ExpressionType[] { ExpressionType.FLOAT }),
/*  23 */   ASIN(ExpressionType.FLOAT, "asin", new ExpressionType[] { ExpressionType.FLOAT }),
/*  24 */   ACOS(ExpressionType.FLOAT, "acos", new ExpressionType[] { ExpressionType.FLOAT }),
/*  25 */   TAN(ExpressionType.FLOAT, "tan", new ExpressionType[] { ExpressionType.FLOAT }),
/*  26 */   ATAN(ExpressionType.FLOAT, "atan", new ExpressionType[] { ExpressionType.FLOAT }),
/*  27 */   ATAN2(ExpressionType.FLOAT, "atan2", new ExpressionType[] { ExpressionType.FLOAT, ExpressionType.FLOAT }),
/*  28 */   TORAD(ExpressionType.FLOAT, "torad", new ExpressionType[] { ExpressionType.FLOAT }),
/*  29 */   TODEG(ExpressionType.FLOAT, "todeg", new ExpressionType[] { ExpressionType.FLOAT }),
/*  30 */   MIN(ExpressionType.FLOAT, "min", (new ParametersVariable()).first(new ExpressionType[] { ExpressionType.FLOAT }).repeat(new ExpressionType[] { ExpressionType.FLOAT })),
/*  31 */   MAX(ExpressionType.FLOAT, "max", (new ParametersVariable()).first(new ExpressionType[] { ExpressionType.FLOAT }).repeat(new ExpressionType[] { ExpressionType.FLOAT })),
/*  32 */   CLAMP(ExpressionType.FLOAT, "clamp", new ExpressionType[] { ExpressionType.FLOAT, ExpressionType.FLOAT, ExpressionType.FLOAT }),
/*  33 */   ABS(ExpressionType.FLOAT, "abs", new ExpressionType[] { ExpressionType.FLOAT }),
/*  34 */   FLOOR(ExpressionType.FLOAT, "floor", new ExpressionType[] { ExpressionType.FLOAT }),
/*  35 */   CEIL(ExpressionType.FLOAT, "ceil", new ExpressionType[] { ExpressionType.FLOAT }),
/*  36 */   EXP(ExpressionType.FLOAT, "exp", new ExpressionType[] { ExpressionType.FLOAT }),
/*  37 */   FRAC(ExpressionType.FLOAT, "frac", new ExpressionType[] { ExpressionType.FLOAT }),
/*  38 */   LOG(ExpressionType.FLOAT, "log", new ExpressionType[] { ExpressionType.FLOAT }),
/*  39 */   POW(ExpressionType.FLOAT, "pow", new ExpressionType[] { ExpressionType.FLOAT, ExpressionType.FLOAT }),
/*  40 */   RANDOM(ExpressionType.FLOAT, "random", new ExpressionType[0]),
/*  41 */   ROUND(ExpressionType.FLOAT, "round", new ExpressionType[] { ExpressionType.FLOAT }),
/*  42 */   SIGNUM(ExpressionType.FLOAT, "signum", new ExpressionType[] { ExpressionType.FLOAT }),
/*  43 */   SQRT(ExpressionType.FLOAT, "sqrt", new ExpressionType[] { ExpressionType.FLOAT }),
/*  44 */   FMOD(ExpressionType.FLOAT, "fmod", new ExpressionType[] { ExpressionType.FLOAT, ExpressionType.FLOAT }),
/*  45 */   TIME(ExpressionType.FLOAT, "time", new ExpressionType[0]),
/*  46 */   IF(ExpressionType.FLOAT, "if", (new ParametersVariable()).first(new ExpressionType[] { ExpressionType.BOOL, ExpressionType.FLOAT }).repeat(new ExpressionType[] { ExpressionType.BOOL, ExpressionType.FLOAT }).last(new ExpressionType[] { ExpressionType.FLOAT })),
/*  47 */   NOT(12, ExpressionType.BOOL, "!", new ExpressionType[] { ExpressionType.BOOL }),
/*  48 */   AND(3, ExpressionType.BOOL, "&&", new ExpressionType[] { ExpressionType.BOOL, ExpressionType.BOOL }),
/*  49 */   OR(2, ExpressionType.BOOL, "||", new ExpressionType[] { ExpressionType.BOOL, ExpressionType.BOOL }),
/*  50 */   GREATER(8, ExpressionType.BOOL, ">", new ExpressionType[] { ExpressionType.FLOAT, ExpressionType.FLOAT }),
/*  51 */   GREATER_OR_EQUAL(8, ExpressionType.BOOL, ">=", new ExpressionType[] { ExpressionType.FLOAT, ExpressionType.FLOAT }),
/*  52 */   SMALLER(8, ExpressionType.BOOL, "<", new ExpressionType[] { ExpressionType.FLOAT, ExpressionType.FLOAT }),
/*  53 */   SMALLER_OR_EQUAL(8, ExpressionType.BOOL, "<=", new ExpressionType[] { ExpressionType.FLOAT, ExpressionType.FLOAT }),
/*  54 */   EQUAL(7, ExpressionType.BOOL, "==", new ExpressionType[] { ExpressionType.FLOAT, ExpressionType.FLOAT }),
/*  55 */   NOT_EQUAL(7, ExpressionType.BOOL, "!=", new ExpressionType[] { ExpressionType.FLOAT, ExpressionType.FLOAT }),
/*  56 */   BETWEEN(7, ExpressionType.BOOL, "between", new ExpressionType[] { ExpressionType.FLOAT, ExpressionType.FLOAT, ExpressionType.FLOAT }),
/*  57 */   EQUALS(7, ExpressionType.BOOL, "equals", new ExpressionType[] { ExpressionType.FLOAT, ExpressionType.FLOAT, ExpressionType.FLOAT }),
/*  58 */   IN(ExpressionType.BOOL, "in", (new ParametersVariable()).first(new ExpressionType[] { ExpressionType.FLOAT }).repeat(new ExpressionType[] { ExpressionType.FLOAT }).last(new ExpressionType[] { ExpressionType.FLOAT })),
/*  59 */   SMOOTH(ExpressionType.FLOAT, "smooth", (new ParametersVariable()).first(new ExpressionType[] { ExpressionType.FLOAT }).repeat(new ExpressionType[] { ExpressionType.FLOAT }).maxCount(4)),
/*  60 */   TRUE(ExpressionType.BOOL, "true", new ExpressionType[0]),
/*  61 */   FALSE(ExpressionType.BOOL, "false", new ExpressionType[0]),
/*  62 */   VEC2(ExpressionType.FLOAT_ARRAY, "vec2", new ExpressionType[] { ExpressionType.FLOAT, ExpressionType.FLOAT }),
/*  63 */   VEC3(ExpressionType.FLOAT_ARRAY, "vec3", new ExpressionType[] { ExpressionType.FLOAT, ExpressionType.FLOAT, ExpressionType.FLOAT }),
/*  64 */   VEC4(ExpressionType.FLOAT_ARRAY, "vec4", new ExpressionType[] { ExpressionType.FLOAT, ExpressionType.FLOAT, ExpressionType.FLOAT, ExpressionType.FLOAT }); private int precedence; private ExpressionType expressionType; private String name;
/*     */   private IParameters parameters;
/*     */   public static FunctionType[] VALUES;
/*     */   private static final Map<Integer, Float> mapSmooth;
/*     */   
/*     */   static {
/*  70 */     VALUES = values();
/*  71 */     mapSmooth = new HashMap<>();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   FunctionType(int precedence, ExpressionType expressionType, String name, IParameters parameters) {
/*  87 */     this.precedence = precedence;
/*  88 */     this.expressionType = expressionType;
/*  89 */     this.name = name;
/*  90 */     this.parameters = parameters;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getName() {
/*  95 */     return this.name;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getPrecedence() {
/* 100 */     return this.precedence;
/*     */   }
/*     */ 
/*     */   
/*     */   public ExpressionType getExpressionType() {
/* 105 */     return this.expressionType;
/*     */   }
/*     */ 
/*     */   
/*     */   public IParameters getParameters() {
/* 110 */     return this.parameters;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getParameterCount(IExpression[] arguments) {
/* 115 */     return (this.parameters.getParameterTypes(arguments)).length;
/*     */   }
/*     */ 
/*     */   
/*     */   public ExpressionType[] getParameterTypes(IExpression[] arguments) {
/* 120 */     return this.parameters.getParameterTypes(arguments); } public float evalFloat(IExpression[] args) { float f, f1, f2, f3;
/*     */     Minecraft minecraft;
/*     */     WorldClient worldClient;
/*     */     int i, k, j;
/*     */     float f4, f5, f6, f7;
/* 125 */     switch (this) {
/*     */       
/*     */       case PLUS:
/* 128 */         return evalFloat(args, 0) + evalFloat(args, 1);
/*     */       
/*     */       case MINUS:
/* 131 */         return evalFloat(args, 0) - evalFloat(args, 1);
/*     */       
/*     */       case MUL:
/* 134 */         return evalFloat(args, 0) * evalFloat(args, 1);
/*     */       
/*     */       case DIV:
/* 137 */         return evalFloat(args, 0) / evalFloat(args, 1);
/*     */       
/*     */       case MOD:
/* 140 */         f = evalFloat(args, 0);
/* 141 */         f1 = evalFloat(args, 1);
/* 142 */         return f - f1 * (int)(f / f1);
/*     */       
/*     */       case NEG:
/* 145 */         return -evalFloat(args, 0);
/*     */       
/*     */       case PI:
/* 148 */         return MathHelper.PI;
/*     */       
/*     */       case SIN:
/* 151 */         return MathHelper.sin(evalFloat(args, 0));
/*     */       
/*     */       case COS:
/* 154 */         return MathHelper.cos(evalFloat(args, 0));
/*     */       
/*     */       case ASIN:
/* 157 */         return MathUtils.asin(evalFloat(args, 0));
/*     */       
/*     */       case ACOS:
/* 160 */         return MathUtils.acos(evalFloat(args, 0));
/*     */       
/*     */       case TAN:
/* 163 */         return (float)Math.tan(evalFloat(args, 0));
/*     */       
/*     */       case ATAN:
/* 166 */         return (float)Math.atan(evalFloat(args, 0));
/*     */       
/*     */       case ATAN2:
/* 169 */         return (float)MathHelper.func_181159_b(evalFloat(args, 0), evalFloat(args, 1));
/*     */       
/*     */       case TORAD:
/* 172 */         return MathUtils.toRad(evalFloat(args, 0));
/*     */       
/*     */       case TODEG:
/* 175 */         return MathUtils.toDeg(evalFloat(args, 0));
/*     */       
/*     */       case MIN:
/* 178 */         return getMin(args);
/*     */       
/*     */       case MAX:
/* 181 */         return getMax(args);
/*     */       
/*     */       case CLAMP:
/* 184 */         return MathHelper.clamp_float(evalFloat(args, 0), evalFloat(args, 1), evalFloat(args, 2));
/*     */       
/*     */       case ABS:
/* 187 */         return MathHelper.abs(evalFloat(args, 0));
/*     */       
/*     */       case EXP:
/* 190 */         return (float)Math.exp(evalFloat(args, 0));
/*     */       
/*     */       case FLOOR:
/* 193 */         return MathHelper.floor_float(evalFloat(args, 0));
/*     */       
/*     */       case CEIL:
/* 196 */         return MathHelper.ceiling_float_int(evalFloat(args, 0));
/*     */       
/*     */       case FRAC:
/* 199 */         return (float)MathHelper.func_181162_h(evalFloat(args, 0));
/*     */       
/*     */       case LOG:
/* 202 */         return (float)Math.log(evalFloat(args, 0));
/*     */       
/*     */       case POW:
/* 205 */         return (float)Math.pow(evalFloat(args, 0), evalFloat(args, 1));
/*     */       
/*     */       case RANDOM:
/* 208 */         return (float)Math.random();
/*     */       
/*     */       case ROUND:
/* 211 */         return Math.round(evalFloat(args, 0));
/*     */       
/*     */       case SIGNUM:
/* 214 */         return Math.signum(evalFloat(args, 0));
/*     */       
/*     */       case SQRT:
/* 217 */         return MathHelper.sqrt_float(evalFloat(args, 0));
/*     */       
/*     */       case FMOD:
/* 220 */         f2 = evalFloat(args, 0);
/* 221 */         f3 = evalFloat(args, 1);
/* 222 */         return f2 - f3 * MathHelper.floor_float(f2 / f3);
/*     */       
/*     */       case TIME:
/* 225 */         minecraft = Minecraft.getMinecraft();
/* 226 */         worldClient = minecraft.theWorld;
/*     */         
/* 228 */         if (worldClient == null)
/*     */         {
/* 230 */           return 0.0F;
/*     */         }
/*     */         
/* 233 */         return (float)(worldClient.getTotalWorldTime() % 24000L) + Config.renderPartialTicks;
/*     */       
/*     */       case IF:
/* 236 */         i = (args.length - 1) / 2;
/*     */         
/* 238 */         for (k = 0; k < i; k++) {
/*     */           
/* 240 */           int l = k * 2;
/*     */           
/* 242 */           if (evalBool(args, l))
/*     */           {
/* 244 */             return evalFloat(args, l + 1);
/*     */           }
/*     */         } 
/*     */         
/* 248 */         return evalFloat(args, i * 2);
/*     */       
/*     */       case SMOOTH:
/* 251 */         j = (int)evalFloat(args, 0);
/* 252 */         f4 = evalFloat(args, 1);
/* 253 */         f5 = (args.length > 2) ? evalFloat(args, 2) : 1.0F;
/* 254 */         f6 = (args.length > 3) ? evalFloat(args, 3) : f5;
/* 255 */         f7 = Smoother.getSmoothValue(j, f4, f5, f6);
/* 256 */         return f7;
/*     */     } 
/*     */     
/* 259 */     Config.warn("Unknown function type: " + this);
/* 260 */     return 0.0F; }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private float getMin(IExpression[] exprs) {
/* 266 */     if (exprs.length == 2)
/*     */     {
/* 268 */       return Math.min(evalFloat(exprs, 0), evalFloat(exprs, 1));
/*     */     }
/*     */ 
/*     */     
/* 272 */     float f = evalFloat(exprs, 0);
/*     */     
/* 274 */     for (int i = 1; i < exprs.length; i++) {
/*     */       
/* 276 */       float f1 = evalFloat(exprs, i);
/*     */       
/* 278 */       if (f1 < f)
/*     */       {
/* 280 */         f = f1;
/*     */       }
/*     */     } 
/*     */     
/* 284 */     return f;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private float getMax(IExpression[] exprs) {
/* 290 */     if (exprs.length == 2)
/*     */     {
/* 292 */       return Math.max(evalFloat(exprs, 0), evalFloat(exprs, 1));
/*     */     }
/*     */ 
/*     */     
/* 296 */     float f = evalFloat(exprs, 0);
/*     */     
/* 298 */     for (int i = 1; i < exprs.length; i++) {
/*     */       
/* 300 */       float f1 = evalFloat(exprs, i);
/*     */       
/* 302 */       if (f1 > f)
/*     */       {
/* 304 */         f = f1;
/*     */       }
/*     */     } 
/*     */     
/* 308 */     return f;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static float evalFloat(IExpression[] exprs, int index) {
/* 314 */     IExpressionFloat iexpressionfloat = (IExpressionFloat)exprs[index];
/* 315 */     float f = iexpressionfloat.eval();
/* 316 */     return f;
/*     */   }
/*     */   public boolean evalBool(IExpression[] args) {
/*     */     float f, f1, f2, f3;
/*     */     int i;
/* 321 */     switch (this) {
/*     */       
/*     */       case TRUE:
/* 324 */         return true;
/*     */       
/*     */       case FALSE:
/* 327 */         return false;
/*     */       
/*     */       case NOT:
/* 330 */         return !evalBool(args, 0);
/*     */       
/*     */       case AND:
/* 333 */         return (evalBool(args, 0) && evalBool(args, 1));
/*     */       
/*     */       case OR:
/* 336 */         return (evalBool(args, 0) || evalBool(args, 1));
/*     */       
/*     */       case GREATER:
/* 339 */         return (evalFloat(args, 0) > evalFloat(args, 1));
/*     */       
/*     */       case GREATER_OR_EQUAL:
/* 342 */         return (evalFloat(args, 0) >= evalFloat(args, 1));
/*     */       
/*     */       case SMALLER:
/* 345 */         return (evalFloat(args, 0) < evalFloat(args, 1));
/*     */       
/*     */       case SMALLER_OR_EQUAL:
/* 348 */         return (evalFloat(args, 0) <= evalFloat(args, 1));
/*     */       
/*     */       case EQUAL:
/* 351 */         return (evalFloat(args, 0) == evalFloat(args, 1));
/*     */       
/*     */       case NOT_EQUAL:
/* 354 */         return (evalFloat(args, 0) != evalFloat(args, 1));
/*     */       
/*     */       case BETWEEN:
/* 357 */         f = evalFloat(args, 0);
/* 358 */         return (f >= evalFloat(args, 1) && f <= evalFloat(args, 2));
/*     */       
/*     */       case EQUALS:
/* 361 */         f1 = evalFloat(args, 0) - evalFloat(args, 1);
/* 362 */         f2 = evalFloat(args, 2);
/* 363 */         return (Math.abs(f1) <= f2);
/*     */       
/*     */       case IN:
/* 366 */         f3 = evalFloat(args, 0);
/*     */         
/* 368 */         for (i = 1; i < args.length; i++) {
/*     */           
/* 370 */           float f4 = evalFloat(args, i);
/*     */           
/* 372 */           if (f3 == f4)
/*     */           {
/* 374 */             return true;
/*     */           }
/*     */         } 
/*     */         
/* 378 */         return false;
/*     */     } 
/*     */     
/* 381 */     Config.warn("Unknown function type: " + this);
/* 382 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static boolean evalBool(IExpression[] exprs, int index) {
/* 388 */     IExpressionBool iexpressionbool = (IExpressionBool)exprs[index];
/* 389 */     boolean flag = iexpressionbool.eval();
/* 390 */     return flag;
/*     */   }
/*     */ 
/*     */   
/*     */   public float[] evalFloatArray(IExpression[] args) {
/* 395 */     switch (this) {
/*     */       
/*     */       case VEC2:
/* 398 */         return new float[] { evalFloat(args, 0), evalFloat(args, 1) };
/*     */       case VEC3:
/* 400 */         return new float[] { evalFloat(args, 0), evalFloat(args, 1), evalFloat(args, 2) };
/*     */       case VEC4:
/* 402 */         return new float[] { evalFloat(args, 0), evalFloat(args, 1), evalFloat(args, 2), evalFloat(args, 3) };
/*     */     } 
/* 404 */     Config.warn("Unknown function type: " + this);
/* 405 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static FunctionType parse(String str) {
/* 411 */     for (int i = 0; i < VALUES.length; i++) {
/*     */       
/* 413 */       FunctionType functiontype = VALUES[i];
/*     */       
/* 415 */       if (functiontype.getName().equals(str))
/*     */       {
/* 417 */         return functiontype;
/*     */       }
/*     */     } 
/*     */     
/* 421 */     return null;
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\optifine\expr\FunctionType.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */