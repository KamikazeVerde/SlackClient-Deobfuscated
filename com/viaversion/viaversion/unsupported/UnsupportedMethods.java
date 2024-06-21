/*    */ package com.viaversion.viaversion.unsupported;
/*    */ 
/*    */ import java.lang.reflect.Method;
/*    */ import java.util.Collections;
/*    */ import java.util.Set;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class UnsupportedMethods
/*    */ {
/*    */   private final String className;
/*    */   private final Set<String> methodNames;
/*    */   
/*    */   public UnsupportedMethods(String className, Set<String> methodNames) {
/* 30 */     this.className = className;
/* 31 */     this.methodNames = Collections.unmodifiableSet(methodNames);
/*    */   }
/*    */   
/*    */   public String getClassName() {
/* 35 */     return this.className;
/*    */   }
/*    */   
/*    */   public final boolean findMatch() {
/*    */     try {
/* 40 */       for (Method method : Class.forName(this.className).getDeclaredMethods()) {
/* 41 */         if (this.methodNames.contains(method.getName())) {
/* 42 */           return true;
/*    */         }
/*    */       } 
/* 45 */     } catch (ClassNotFoundException classNotFoundException) {}
/*    */     
/* 47 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversio\\unsupported\UnsupportedMethods.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */