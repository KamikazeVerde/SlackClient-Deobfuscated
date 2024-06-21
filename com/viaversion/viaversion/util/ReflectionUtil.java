/*     */ package com.viaversion.viaversion.util;
/*     */ 
/*     */ import java.lang.reflect.Field;
/*     */ import java.lang.reflect.InvocationTargetException;
/*     */ import java.lang.reflect.Method;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.Map;
/*     */ import java.util.concurrent.ConcurrentHashMap;
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
/*     */ public class ReflectionUtil
/*     */ {
/*     */   public static Object invokeStatic(Class<?> clazz, String method) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
/*  31 */     Method m = clazz.getDeclaredMethod(method, new Class[0]);
/*  32 */     return m.invoke(null, new Object[0]);
/*     */   }
/*     */   
/*     */   public static Object invoke(Object o, String method) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
/*  36 */     Method m = o.getClass().getDeclaredMethod(method, new Class[0]);
/*  37 */     return m.invoke(o, new Object[0]);
/*     */   }
/*     */   
/*     */   public static <T> T getStatic(Class<?> clazz, String f, Class<T> type) throws NoSuchFieldException, IllegalAccessException {
/*  41 */     Field field = clazz.getDeclaredField(f);
/*  42 */     field.setAccessible(true);
/*  43 */     return type.cast(field.get(null));
/*     */   }
/*     */   
/*     */   public static void setStatic(Class<?> clazz, String f, Object value) throws NoSuchFieldException, IllegalAccessException {
/*  47 */     Field field = clazz.getDeclaredField(f);
/*  48 */     field.setAccessible(true);
/*  49 */     field.set(null, value);
/*     */   }
/*     */   
/*     */   public static <T> T getSuper(Object o, String f, Class<T> type) throws NoSuchFieldException, IllegalAccessException {
/*  53 */     Field field = o.getClass().getSuperclass().getDeclaredField(f);
/*  54 */     field.setAccessible(true);
/*  55 */     return type.cast(field.get(o));
/*     */   }
/*     */   
/*     */   public static <T> T get(Object instance, Class<?> clazz, String f, Class<T> type) throws NoSuchFieldException, IllegalAccessException {
/*  59 */     Field field = clazz.getDeclaredField(f);
/*  60 */     field.setAccessible(true);
/*  61 */     return type.cast(field.get(instance));
/*     */   }
/*     */   
/*     */   public static <T> T get(Object o, String f, Class<T> type) throws NoSuchFieldException, IllegalAccessException {
/*  65 */     Field field = o.getClass().getDeclaredField(f);
/*  66 */     field.setAccessible(true);
/*  67 */     return type.cast(field.get(o));
/*     */   }
/*     */   
/*     */   public static <T> T getPublic(Object o, String f, Class<T> type) throws NoSuchFieldException, IllegalAccessException {
/*  71 */     Field field = o.getClass().getField(f);
/*  72 */     field.setAccessible(true);
/*  73 */     return type.cast(field.get(o));
/*     */   }
/*     */   
/*     */   public static void set(Object o, String f, Object value) throws NoSuchFieldException, IllegalAccessException {
/*  77 */     Field field = o.getClass().getDeclaredField(f);
/*  78 */     field.setAccessible(true);
/*  79 */     field.set(o, value);
/*     */   }
/*     */   
/*     */   public static final class ClassReflection {
/*     */     private final Class<?> handle;
/*  84 */     private final Map<String, Field> fields = new ConcurrentHashMap<>();
/*  85 */     private final Map<String, Method> methods = new ConcurrentHashMap<>();
/*     */     
/*     */     public ClassReflection(Class<?> handle) {
/*  88 */       this(handle, true);
/*     */     }
/*     */     
/*     */     public ClassReflection(Class<?> handle, boolean recursive) {
/*  92 */       this.handle = handle;
/*  93 */       scanFields(handle, recursive);
/*  94 */       scanMethods(handle, recursive);
/*     */     }
/*     */     
/*     */     private void scanFields(Class<?> host, boolean recursive) {
/*  98 */       if (recursive && host.getSuperclass() != null && host.getSuperclass() != Object.class) {
/*  99 */         scanFields(host.getSuperclass(), true);
/*     */       }
/*     */       
/* 102 */       for (Field field : host.getDeclaredFields()) {
/* 103 */         field.setAccessible(true);
/* 104 */         this.fields.put(field.getName(), field);
/*     */       } 
/*     */     }
/*     */     
/*     */     private void scanMethods(Class<?> host, boolean recursive) {
/* 109 */       if (recursive && host.getSuperclass() != null && host.getSuperclass() != Object.class) {
/* 110 */         scanMethods(host.getSuperclass(), true);
/*     */       }
/*     */       
/* 113 */       for (Method method : host.getDeclaredMethods()) {
/* 114 */         method.setAccessible(true);
/* 115 */         this.methods.put(method.getName(), method);
/*     */       } 
/*     */     }
/*     */     
/*     */     public Object newInstance() throws ReflectiveOperationException {
/* 120 */       return this.handle.getConstructor(new Class[0]).newInstance(new Object[0]);
/*     */     }
/*     */     
/*     */     public Field getField(String name) {
/* 124 */       return this.fields.get(name);
/*     */     }
/*     */     
/*     */     public void setFieldValue(String fieldName, Object instance, Object value) throws IllegalAccessException {
/* 128 */       getField(fieldName).set(instance, value);
/*     */     }
/*     */     
/*     */     public <T> T getFieldValue(String fieldName, Object instance, Class<T> type) throws IllegalAccessException {
/* 132 */       return type.cast(getField(fieldName).get(instance));
/*     */     }
/*     */     
/*     */     public <T> T invokeMethod(Class<T> type, String methodName, Object instance, Object... args) throws InvocationTargetException, IllegalAccessException {
/* 136 */       return type.cast(getMethod(methodName).invoke(instance, args));
/*     */     }
/*     */     
/*     */     public Method getMethod(String name) {
/* 140 */       return this.methods.get(name);
/*     */     }
/*     */     
/*     */     public Collection<Field> getFields() {
/* 144 */       return Collections.unmodifiableCollection(this.fields.values());
/*     */     }
/*     */     
/*     */     public Collection<Method> getMethods() {
/* 148 */       return Collections.unmodifiableCollection(this.methods.values());
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversio\\util\ReflectionUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */