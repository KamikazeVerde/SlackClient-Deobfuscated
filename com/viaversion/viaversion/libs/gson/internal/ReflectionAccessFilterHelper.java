/*    */ package com.viaversion.viaversion.libs.gson.internal;
/*    */ 
/*    */ import com.viaversion.viaversion.libs.gson.ReflectionAccessFilter;
/*    */ import java.lang.reflect.AccessibleObject;
/*    */ import java.lang.reflect.Method;
/*    */ import java.util.List;
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
/*    */ public class ReflectionAccessFilterHelper
/*    */ {
/*    */   public static boolean isJavaType(Class<?> c) {
/* 20 */     return isJavaType(c.getName());
/*    */   }
/*    */   
/*    */   private static boolean isJavaType(String className) {
/* 24 */     return (className.startsWith("java.") || className.startsWith("javax."));
/*    */   }
/*    */   
/*    */   public static boolean isAndroidType(Class<?> c) {
/* 28 */     return isAndroidType(c.getName());
/*    */   }
/*    */   
/*    */   private static boolean isAndroidType(String className) {
/* 32 */     return (className.startsWith("android.") || className
/* 33 */       .startsWith("androidx.") || 
/* 34 */       isJavaType(className));
/*    */   }
/*    */   
/*    */   public static boolean isAnyPlatformType(Class<?> c) {
/* 38 */     String className = c.getName();
/* 39 */     return (isAndroidType(className) || className
/* 40 */       .startsWith("kotlin.") || className
/* 41 */       .startsWith("kotlinx.") || className
/* 42 */       .startsWith("scala."));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static ReflectionAccessFilter.FilterResult getFilterResult(List<ReflectionAccessFilter> reflectionFilters, Class<?> c) {
/* 51 */     for (ReflectionAccessFilter filter : reflectionFilters) {
/* 52 */       ReflectionAccessFilter.FilterResult result = filter.check(c);
/* 53 */       if (result != ReflectionAccessFilter.FilterResult.INDECISIVE) {
/* 54 */         return result;
/*    */       }
/*    */     } 
/* 57 */     return ReflectionAccessFilter.FilterResult.ALLOW;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static boolean canAccess(AccessibleObject accessibleObject, Object object) {
/* 64 */     return AccessChecker.INSTANCE.canAccess(accessibleObject, object);
/*    */   }
/*    */   
/*    */   private static abstract class AccessChecker
/*    */   {
/*    */     static {
/* 70 */       AccessChecker accessChecker = null;
/*    */       
/* 72 */       if (JavaVersion.isJava9OrLater()) {
/*    */         try {
/* 74 */           final Method canAccessMethod = AccessibleObject.class.getDeclaredMethod("canAccess", new Class[] { Object.class });
/* 75 */           accessChecker = new AccessChecker() {
/*    */               public boolean canAccess(AccessibleObject accessibleObject, Object object) {
/*    */                 try {
/* 78 */                   return ((Boolean)canAccessMethod.invoke(accessibleObject, new Object[] { object })).booleanValue();
/* 79 */                 } catch (Exception e) {
/* 80 */                   throw new RuntimeException("Failed invoking canAccess", e);
/*    */                 } 
/*    */               }
/*    */             };
/* 84 */         } catch (NoSuchMethodException noSuchMethodException) {}
/*    */       }
/*    */ 
/*    */       
/* 88 */       if (accessChecker == null) {
/* 89 */         accessChecker = new AccessChecker()
/*    */           {
/*    */             public boolean canAccess(AccessibleObject accessibleObject, Object object) {
/* 92 */               return true;
/*    */             }
/*    */           };
/*    */       }
/* 96 */       INSTANCE = accessChecker;
/*    */     }
/*    */     
/*    */     public static final AccessChecker INSTANCE;
/*    */     
/*    */     private AccessChecker() {}
/*    */     
/*    */     public abstract boolean canAccess(AccessibleObject param1AccessibleObject, Object param1Object);
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\libs\gson\internal\ReflectionAccessFilterHelper.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */