/*     */ package com.viaversion.viaversion.libs.gson.internal;
/*     */ 
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectStreamClass;
/*     */ import java.lang.reflect.Field;
/*     */ import java.lang.reflect.Method;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class UnsafeAllocator
/*     */ {
/*     */   private static void assertInstantiable(Class<?> c) {
/*  39 */     String exceptionMessage = ConstructorConstructor.checkInstantiable(c);
/*  40 */     if (exceptionMessage != null) {
/*  41 */       throw new AssertionError("UnsafeAllocator is used for non-instantiable type: " + exceptionMessage);
/*     */     }
/*     */   }
/*     */   
/*  45 */   public static final UnsafeAllocator INSTANCE = create();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static UnsafeAllocator create() {
/*     */     try {
/*  53 */       Class<?> unsafeClass = Class.forName("sun.misc.Unsafe");
/*  54 */       Field f = unsafeClass.getDeclaredField("theUnsafe");
/*  55 */       f.setAccessible(true);
/*  56 */       final Object unsafe = f.get(null);
/*  57 */       final Method allocateInstance = unsafeClass.getMethod("allocateInstance", new Class[] { Class.class });
/*  58 */       return new UnsafeAllocator()
/*     */         {
/*     */           public <T> T newInstance(Class<T> c) throws Exception
/*     */           {
/*  62 */             UnsafeAllocator.assertInstantiable(c);
/*  63 */             return (T)allocateInstance.invoke(unsafe, new Object[] { c });
/*     */           }
/*     */         };
/*  66 */     } catch (Exception exception) {
/*     */ 
/*     */       
/*     */       try {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*  76 */         Method getConstructorId = ObjectStreamClass.class.getDeclaredMethod("getConstructorId", new Class[] { Class.class });
/*  77 */         getConstructorId.setAccessible(true);
/*  78 */         final int constructorId = ((Integer)getConstructorId.invoke(null, new Object[] { Object.class })).intValue();
/*     */         
/*  80 */         final Method newInstance = ObjectStreamClass.class.getDeclaredMethod("newInstance", new Class[] { Class.class, int.class });
/*  81 */         newInstance.setAccessible(true);
/*  82 */         return new UnsafeAllocator()
/*     */           {
/*     */             public <T> T newInstance(Class<T> c) throws Exception
/*     */             {
/*  86 */               UnsafeAllocator.assertInstantiable(c);
/*  87 */               return (T)newInstance.invoke(null, new Object[] { c, Integer.valueOf(this.val$constructorId) });
/*     */             }
/*     */           };
/*  90 */       } catch (Exception exception1) {
/*     */ 
/*     */         
/*     */         try {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 100 */           final Method newInstance = ObjectInputStream.class.getDeclaredMethod("newInstance", new Class[] { Class.class, Class.class });
/* 101 */           newInstance.setAccessible(true);
/* 102 */           return new UnsafeAllocator()
/*     */             {
/*     */               public <T> T newInstance(Class<T> c) throws Exception
/*     */               {
/* 106 */                 UnsafeAllocator.assertInstantiable(c);
/* 107 */                 return (T)newInstance.invoke(null, new Object[] { c, Object.class });
/*     */               }
/*     */             };
/* 110 */         } catch (Exception exception2) {
/*     */ 
/*     */ 
/*     */           
/* 114 */           return new UnsafeAllocator()
/*     */             {
/*     */               public <T> T newInstance(Class<T> c) {
/* 117 */                 throw new UnsupportedOperationException("Cannot allocate " + c + ". Usage of JDK sun.misc.Unsafe is enabled, but it could not be used. Make sure your runtime is configured correctly.");
/*     */               }
/*     */             };
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public abstract <T> T newInstance(Class<T> paramClass) throws Exception;
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\libs\gson\internal\UnsafeAllocator.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */