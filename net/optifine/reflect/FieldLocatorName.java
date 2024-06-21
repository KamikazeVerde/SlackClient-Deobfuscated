/*    */ package net.optifine.reflect;
/*    */ 
/*    */ import java.lang.reflect.Field;
/*    */ import net.minecraft.src.Config;
/*    */ 
/*    */ public class FieldLocatorName
/*    */   implements IFieldLocator {
/*  8 */   private ReflectorClass reflectorClass = null;
/*  9 */   private String targetFieldName = null;
/*    */ 
/*    */   
/*    */   public FieldLocatorName(ReflectorClass reflectorClass, String targetFieldName) {
/* 13 */     this.reflectorClass = reflectorClass;
/* 14 */     this.targetFieldName = targetFieldName;
/*    */   }
/*    */ 
/*    */   
/*    */   public Field getField() {
/* 19 */     Class oclass = this.reflectorClass.getTargetClass();
/*    */     
/* 21 */     if (oclass == null)
/*    */     {
/* 23 */       return null;
/*    */     }
/*    */ 
/*    */ 
/*    */     
/*    */     try {
/* 29 */       Field field = getDeclaredField(oclass, this.targetFieldName);
/* 30 */       field.setAccessible(true);
/* 31 */       return field;
/*    */     }
/* 33 */     catch (NoSuchFieldException var3) {
/*    */       
/* 35 */       Config.log("(Reflector) Field not present: " + oclass.getName() + "." + this.targetFieldName);
/* 36 */       return null;
/*    */     }
/* 38 */     catch (SecurityException securityexception) {
/*    */       
/* 40 */       securityexception.printStackTrace();
/* 41 */       return null;
/*    */     }
/* 43 */     catch (Throwable throwable) {
/*    */       
/* 45 */       throwable.printStackTrace();
/* 46 */       return null;
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   private Field getDeclaredField(Class<Object> cls, String name) throws NoSuchFieldException {
/* 53 */     Field[] afield = cls.getDeclaredFields();
/*    */     
/* 55 */     for (int i = 0; i < afield.length; i++) {
/*    */       
/* 57 */       Field field = afield[i];
/*    */       
/* 59 */       if (field.getName().equals(name))
/*    */       {
/* 61 */         return field;
/*    */       }
/*    */     } 
/*    */     
/* 65 */     if (cls == Object.class)
/*    */     {
/* 67 */       throw new NoSuchFieldException(name);
/*    */     }
/*    */ 
/*    */     
/* 71 */     return getDeclaredField(cls.getSuperclass(), name);
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\optifine\reflect\FieldLocatorName.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */