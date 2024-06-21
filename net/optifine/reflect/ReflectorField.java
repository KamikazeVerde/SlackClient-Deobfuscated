/*    */ package net.optifine.reflect;
/*    */ 
/*    */ import java.lang.reflect.Field;
/*    */ 
/*    */ 
/*    */ public class ReflectorField
/*    */ {
/*    */   private IFieldLocator fieldLocator;
/*    */   private boolean checked;
/*    */   private Field targetField;
/*    */   
/*    */   public ReflectorField(ReflectorClass reflectorClass, String targetFieldName) {
/* 13 */     this(new FieldLocatorName(reflectorClass, targetFieldName));
/*    */   }
/*    */ 
/*    */   
/*    */   public ReflectorField(ReflectorClass reflectorClass, String targetFieldName, boolean lazyResolve) {
/* 18 */     this(new FieldLocatorName(reflectorClass, targetFieldName), lazyResolve);
/*    */   }
/*    */ 
/*    */   
/*    */   public ReflectorField(ReflectorClass reflectorClass, Class targetFieldType) {
/* 23 */     this(reflectorClass, targetFieldType, 0);
/*    */   }
/*    */ 
/*    */   
/*    */   public ReflectorField(ReflectorClass reflectorClass, Class targetFieldType, int targetFieldIndex) {
/* 28 */     this(new FieldLocatorType(reflectorClass, targetFieldType, targetFieldIndex));
/*    */   }
/*    */ 
/*    */   
/*    */   public ReflectorField(Field field) {
/* 33 */     this(new FieldLocatorFixed(field));
/*    */   }
/*    */ 
/*    */   
/*    */   public ReflectorField(IFieldLocator fieldLocator) {
/* 38 */     this(fieldLocator, false);
/*    */   }
/*    */ 
/*    */   
/*    */   public ReflectorField(IFieldLocator fieldLocator, boolean lazyResolve) {
/* 43 */     this.fieldLocator = null;
/* 44 */     this.checked = false;
/* 45 */     this.targetField = null;
/* 46 */     this.fieldLocator = fieldLocator;
/*    */     
/* 48 */     if (!lazyResolve)
/*    */     {
/* 50 */       getTargetField();
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public Field getTargetField() {
/* 56 */     if (this.checked)
/*    */     {
/* 58 */       return this.targetField;
/*    */     }
/*    */ 
/*    */     
/* 62 */     this.checked = true;
/* 63 */     this.targetField = this.fieldLocator.getField();
/*    */     
/* 65 */     if (this.targetField != null)
/*    */     {
/* 67 */       this.targetField.setAccessible(true);
/*    */     }
/*    */     
/* 70 */     return this.targetField;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public Object getValue() {
/* 76 */     return Reflector.getFieldValue((Object)null, this);
/*    */   }
/*    */ 
/*    */   
/*    */   public void setValue(Object value) {
/* 81 */     Reflector.setFieldValue(null, this, value);
/*    */   }
/*    */ 
/*    */   
/*    */   public void setValue(Object obj, Object value) {
/* 86 */     Reflector.setFieldValue(obj, this, value);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean exists() {
/* 91 */     return (getTargetField() != null);
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\optifine\reflect\ReflectorField.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */