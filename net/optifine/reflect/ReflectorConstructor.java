/*    */ package net.optifine.reflect;
/*    */ 
/*    */ import java.lang.reflect.Constructor;
/*    */ import net.minecraft.src.Config;
/*    */ 
/*    */ public class ReflectorConstructor
/*    */ {
/*  8 */   private ReflectorClass reflectorClass = null;
/*  9 */   private Class[] parameterTypes = null;
/*    */   private boolean checked = false;
/* 11 */   private Constructor targetConstructor = null;
/*    */ 
/*    */   
/*    */   public ReflectorConstructor(ReflectorClass reflectorClass, Class[] parameterTypes) {
/* 15 */     this.reflectorClass = reflectorClass;
/* 16 */     this.parameterTypes = parameterTypes;
/* 17 */     Constructor constructor = getTargetConstructor();
/*    */   }
/*    */ 
/*    */   
/*    */   public Constructor getTargetConstructor() {
/* 22 */     if (this.checked)
/*    */     {
/* 24 */       return this.targetConstructor;
/*    */     }
/*    */ 
/*    */     
/* 28 */     this.checked = true;
/* 29 */     Class oclass = this.reflectorClass.getTargetClass();
/*    */     
/* 31 */     if (oclass == null)
/*    */     {
/* 33 */       return null;
/*    */     }
/*    */ 
/*    */ 
/*    */     
/*    */     try {
/* 39 */       this.targetConstructor = findConstructor(oclass, this.parameterTypes);
/*    */       
/* 41 */       if (this.targetConstructor == null)
/*    */       {
/* 43 */         Config.dbg("(Reflector) Constructor not present: " + oclass.getName() + ", params: " + Config.arrayToString((Object[])this.parameterTypes));
/*    */       }
/*    */       
/* 46 */       if (this.targetConstructor != null)
/*    */       {
/* 48 */         this.targetConstructor.setAccessible(true);
/*    */       }
/*    */     }
/* 51 */     catch (Throwable throwable) {
/*    */       
/* 53 */       throwable.printStackTrace();
/*    */     } 
/*    */     
/* 56 */     return this.targetConstructor;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private static Constructor findConstructor(Class cls, Class[] paramTypes) {
/* 63 */     Constructor[] aconstructor = (Constructor[])cls.getDeclaredConstructors();
/*    */     
/* 65 */     for (int i = 0; i < aconstructor.length; i++) {
/*    */       
/* 67 */       Constructor constructor = aconstructor[i];
/* 68 */       Class[] aclass = constructor.getParameterTypes();
/*    */       
/* 70 */       if (Reflector.matchesTypes(paramTypes, aclass))
/*    */       {
/* 72 */         return constructor;
/*    */       }
/*    */     } 
/*    */     
/* 76 */     return null;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean exists() {
/* 81 */     return this.checked ? ((this.targetConstructor != null)) : ((getTargetConstructor() != null));
/*    */   }
/*    */ 
/*    */   
/*    */   public void deactivate() {
/* 86 */     this.checked = true;
/* 87 */     this.targetConstructor = null;
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\optifine\reflect\ReflectorConstructor.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */