/*    */ package net.optifine.reflect;
/*    */ 
/*    */ import java.lang.reflect.Field;
/*    */ import net.minecraft.src.Config;
/*    */ 
/*    */ public class FieldLocatorType
/*    */   implements IFieldLocator
/*    */ {
/*    */   private ReflectorClass reflectorClass;
/*    */   private Class targetFieldType;
/*    */   private int targetFieldIndex;
/*    */   
/*    */   public FieldLocatorType(ReflectorClass reflectorClass, Class targetFieldType) {
/* 14 */     this(reflectorClass, targetFieldType, 0);
/*    */   }
/*    */ 
/*    */   
/*    */   public FieldLocatorType(ReflectorClass reflectorClass, Class targetFieldType, int targetFieldIndex) {
/* 19 */     this.reflectorClass = null;
/* 20 */     this.targetFieldType = null;
/* 21 */     this.reflectorClass = reflectorClass;
/* 22 */     this.targetFieldType = targetFieldType;
/* 23 */     this.targetFieldIndex = targetFieldIndex;
/*    */   }
/*    */ 
/*    */   
/*    */   public Field getField() {
/* 28 */     Class oclass = this.reflectorClass.getTargetClass();
/*    */     
/* 30 */     if (oclass == null)
/*    */     {
/* 32 */       return null;
/*    */     }
/*    */ 
/*    */ 
/*    */     
/*    */     try {
/* 38 */       Field[] afield = oclass.getDeclaredFields();
/* 39 */       int i = 0;
/*    */       
/* 41 */       for (int j = 0; j < afield.length; j++) {
/*    */         
/* 43 */         Field field = afield[j];
/*    */         
/* 45 */         if (field.getType() == this.targetFieldType) {
/*    */           
/* 47 */           if (i == this.targetFieldIndex) {
/*    */             
/* 49 */             field.setAccessible(true);
/* 50 */             return field;
/*    */           } 
/*    */           
/* 53 */           i++;
/*    */         } 
/*    */       } 
/*    */       
/* 57 */       Config.log("(Reflector) Field not present: " + oclass.getName() + ".(type: " + this.targetFieldType + ", index: " + this.targetFieldIndex + ")");
/* 58 */       return null;
/*    */     }
/* 60 */     catch (SecurityException securityexception) {
/*    */       
/* 62 */       securityexception.printStackTrace();
/* 63 */       return null;
/*    */     }
/* 65 */     catch (Throwable throwable) {
/*    */       
/* 67 */       throwable.printStackTrace();
/* 68 */       return null;
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\optifine\reflect\FieldLocatorType.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */