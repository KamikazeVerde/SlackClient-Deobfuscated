/*     */ package net.optifine.reflect;
/*     */ 
/*     */ import java.lang.reflect.Method;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import net.minecraft.src.Config;
/*     */ 
/*     */ 
/*     */ public class ReflectorMethod
/*     */ {
/*     */   private ReflectorClass reflectorClass;
/*     */   private String targetMethodName;
/*     */   private Class[] targetMethodParameterTypes;
/*     */   private boolean checked;
/*     */   private Method targetMethod;
/*     */   
/*     */   public ReflectorMethod(ReflectorClass reflectorClass, String targetMethodName) {
/*  18 */     this(reflectorClass, targetMethodName, null, false);
/*     */   }
/*     */ 
/*     */   
/*     */   public ReflectorMethod(ReflectorClass reflectorClass, String targetMethodName, Class[] targetMethodParameterTypes) {
/*  23 */     this(reflectorClass, targetMethodName, targetMethodParameterTypes, false);
/*     */   }
/*     */ 
/*     */   
/*     */   public ReflectorMethod(ReflectorClass reflectorClass, String targetMethodName, Class[] targetMethodParameterTypes, boolean lazyResolve) {
/*  28 */     this.reflectorClass = null;
/*  29 */     this.targetMethodName = null;
/*  30 */     this.targetMethodParameterTypes = null;
/*  31 */     this.checked = false;
/*  32 */     this.targetMethod = null;
/*  33 */     this.reflectorClass = reflectorClass;
/*  34 */     this.targetMethodName = targetMethodName;
/*  35 */     this.targetMethodParameterTypes = targetMethodParameterTypes;
/*     */     
/*  37 */     if (!lazyResolve)
/*     */     {
/*  39 */       Method method = getTargetMethod();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public Method getTargetMethod() {
/*  45 */     if (this.checked)
/*     */     {
/*  47 */       return this.targetMethod;
/*     */     }
/*     */ 
/*     */     
/*  51 */     this.checked = true;
/*  52 */     Class oclass = this.reflectorClass.getTargetClass();
/*     */     
/*  54 */     if (oclass == null)
/*     */     {
/*  56 */       return null;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/*  62 */       if (this.targetMethodParameterTypes == null) {
/*     */         
/*  64 */         Method[] amethod = getMethods(oclass, this.targetMethodName);
/*     */         
/*  66 */         if (amethod.length <= 0) {
/*     */           
/*  68 */           Config.log("(Reflector) Method not present: " + oclass.getName() + "." + this.targetMethodName);
/*  69 */           return null;
/*     */         } 
/*     */         
/*  72 */         if (amethod.length > 1) {
/*     */           
/*  74 */           Config.warn("(Reflector) More than one method found: " + oclass.getName() + "." + this.targetMethodName);
/*     */           
/*  76 */           for (int i = 0; i < amethod.length; i++) {
/*     */             
/*  78 */             Method method = amethod[i];
/*  79 */             Config.warn("(Reflector)  - " + method);
/*     */           } 
/*     */           
/*  82 */           return null;
/*     */         } 
/*     */         
/*  85 */         this.targetMethod = amethod[0];
/*     */       }
/*     */       else {
/*     */         
/*  89 */         this.targetMethod = getMethod(oclass, this.targetMethodName, this.targetMethodParameterTypes);
/*     */       } 
/*     */       
/*  92 */       if (this.targetMethod == null) {
/*     */         
/*  94 */         Config.log("(Reflector) Method not present: " + oclass.getName() + "." + this.targetMethodName);
/*  95 */         return null;
/*     */       } 
/*     */ 
/*     */       
/*  99 */       this.targetMethod.setAccessible(true);
/* 100 */       return this.targetMethod;
/*     */     
/*     */     }
/* 103 */     catch (Throwable throwable) {
/*     */       
/* 105 */       throwable.printStackTrace();
/* 106 */       return null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean exists() {
/* 114 */     return this.checked ? ((this.targetMethod != null)) : ((getTargetMethod() != null));
/*     */   }
/*     */ 
/*     */   
/*     */   public Class getReturnType() {
/* 119 */     Method method = getTargetMethod();
/* 120 */     return (method == null) ? null : method.getReturnType();
/*     */   }
/*     */ 
/*     */   
/*     */   public void deactivate() {
/* 125 */     this.checked = true;
/* 126 */     this.targetMethod = null;
/*     */   }
/*     */ 
/*     */   
/*     */   public static Method getMethod(Class cls, String methodName, Class[] paramTypes) {
/* 131 */     Method[] amethod = cls.getDeclaredMethods();
/*     */     
/* 133 */     for (int i = 0; i < amethod.length; i++) {
/*     */       
/* 135 */       Method method = amethod[i];
/*     */       
/* 137 */       if (method.getName().equals(methodName)) {
/*     */         
/* 139 */         Class[] aclass = method.getParameterTypes();
/*     */         
/* 141 */         if (Reflector.matchesTypes(paramTypes, aclass))
/*     */         {
/* 143 */           return method;
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 148 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public static Method[] getMethods(Class cls, String methodName) {
/* 153 */     List<Method> list = new ArrayList();
/* 154 */     Method[] amethod = cls.getDeclaredMethods();
/*     */     
/* 156 */     for (int i = 0; i < amethod.length; i++) {
/*     */       
/* 158 */       Method method = amethod[i];
/*     */       
/* 160 */       if (method.getName().equals(methodName))
/*     */       {
/* 162 */         list.add(method);
/*     */       }
/*     */     } 
/*     */     
/* 166 */     Method[] amethod1 = list.<Method>toArray(new Method[list.size()]);
/* 167 */     return amethod1;
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\optifine\reflect\ReflectorMethod.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */