/*    */ package net.optifine.reflect;
/*    */ 
/*    */ import java.lang.reflect.Field;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Arrays;
/*    */ import java.util.Collections;
/*    */ import java.util.List;
/*    */ import net.minecraft.src.Config;
/*    */ 
/*    */ public class FieldLocatorTypes
/*    */   implements IFieldLocator {
/* 12 */   private Field field = null;
/*    */ 
/*    */   
/*    */   public FieldLocatorTypes(Class cls, Class[] preTypes, Class<?> type, Class[] postTypes, String errorName) {
/* 16 */     Field[] afield = cls.getDeclaredFields();
/* 17 */     List<Class<?>> list = new ArrayList<>();
/*    */     
/* 19 */     for (int i = 0; i < afield.length; i++) {
/*    */       
/* 21 */       Field field = afield[i];
/* 22 */       list.add(field.getType());
/*    */     } 
/*    */     
/* 25 */     List<Class<?>> list1 = new ArrayList<>();
/* 26 */     list1.addAll(Arrays.asList(preTypes));
/* 27 */     list1.add(type);
/* 28 */     list1.addAll(Arrays.asList(postTypes));
/* 29 */     int l = Collections.indexOfSubList(list, list1);
/*    */     
/* 31 */     if (l < 0) {
/*    */       
/* 33 */       Config.log("(Reflector) Field not found: " + errorName);
/*    */     }
/*    */     else {
/*    */       
/* 37 */       int j = Collections.indexOfSubList(list.subList(l + 1, list.size()), list1);
/*    */       
/* 39 */       if (j >= 0) {
/*    */         
/* 41 */         Config.log("(Reflector) More than one match found for field: " + errorName);
/*    */       }
/*    */       else {
/*    */         
/* 45 */         int k = l + preTypes.length;
/* 46 */         this.field = afield[k];
/*    */       } 
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public Field getField() {
/* 53 */     return this.field;
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\optifine\reflect\FieldLocatorTypes.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */