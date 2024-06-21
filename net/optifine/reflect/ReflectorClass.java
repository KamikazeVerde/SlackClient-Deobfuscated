/*    */ package net.optifine.reflect;
/*    */ 
/*    */ import net.minecraft.src.Config;
/*    */ 
/*    */ 
/*    */ public class ReflectorClass
/*    */ {
/*    */   private String targetClassName;
/*    */   private boolean checked;
/*    */   private Class targetClass;
/*    */   
/*    */   public ReflectorClass(String targetClassName) {
/* 13 */     this(targetClassName, false);
/*    */   }
/*    */ 
/*    */   
/*    */   public ReflectorClass(String targetClassName, boolean lazyResolve) {
/* 18 */     this.targetClassName = null;
/* 19 */     this.checked = false;
/* 20 */     this.targetClass = null;
/* 21 */     this.targetClassName = targetClassName;
/*    */     
/* 23 */     if (!lazyResolve)
/*    */     {
/* 25 */       Class clazz = getTargetClass();
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public ReflectorClass(Class targetClass) {
/* 31 */     this.targetClassName = null;
/* 32 */     this.checked = false;
/* 33 */     this.targetClass = null;
/* 34 */     this.targetClass = targetClass;
/* 35 */     this.targetClassName = targetClass.getName();
/* 36 */     this.checked = true;
/*    */   }
/*    */ 
/*    */   
/*    */   public Class getTargetClass() {
/* 41 */     if (this.checked)
/*    */     {
/* 43 */       return this.targetClass;
/*    */     }
/*    */ 
/*    */     
/* 47 */     this.checked = true;
/*    */ 
/*    */     
/*    */     try {
/* 51 */       this.targetClass = Class.forName(this.targetClassName);
/*    */     }
/* 53 */     catch (ClassNotFoundException var2) {
/*    */       
/* 55 */       Config.log("(Reflector) Class not present: " + this.targetClassName);
/*    */     }
/* 57 */     catch (Throwable throwable) {
/*    */       
/* 59 */       throwable.printStackTrace();
/*    */     } 
/*    */     
/* 62 */     return this.targetClass;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean exists() {
/* 68 */     return (getTargetClass() != null);
/*    */   }
/*    */ 
/*    */   
/*    */   public String getTargetClassName() {
/* 73 */     return this.targetClassName;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isInstance(Object obj) {
/* 78 */     return (getTargetClass() == null) ? false : getTargetClass().isInstance(obj);
/*    */   }
/*    */ 
/*    */   
/*    */   public ReflectorField makeField(String name) {
/* 83 */     return new ReflectorField(this, name);
/*    */   }
/*    */ 
/*    */   
/*    */   public ReflectorMethod makeMethod(String name) {
/* 88 */     return new ReflectorMethod(this, name);
/*    */   }
/*    */ 
/*    */   
/*    */   public ReflectorMethod makeMethod(String name, Class[] paramTypes) {
/* 93 */     return new ReflectorMethod(this, name, paramTypes);
/*    */   }
/*    */ 
/*    */   
/*    */   public ReflectorMethod makeMethod(String name, Class[] paramTypes, boolean lazyResolve) {
/* 98 */     return new ReflectorMethod(this, name, paramTypes, lazyResolve);
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\optifine\reflect\ReflectorClass.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */