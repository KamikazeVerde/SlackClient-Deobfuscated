/*     */ package com.viaversion.viaversion.libs.gson.internal.reflect;
/*     */ 
/*     */ import com.viaversion.viaversion.libs.gson.JsonIOException;
/*     */ import java.lang.reflect.AccessibleObject;
/*     */ import java.lang.reflect.Constructor;
/*     */ import java.lang.reflect.Field;
/*     */ import java.lang.reflect.Method;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ReflectionHelper
/*     */ {
/*     */   private static final RecordHelper RECORD_HELPER;
/*     */   
/*     */   static {
/*     */     RecordHelper instance;
/*     */     try {
/*  18 */       instance = new RecordSupportedHelper();
/*  19 */     } catch (NoSuchMethodException e) {
/*  20 */       instance = new RecordNotSupportedHelper();
/*     */     } 
/*  22 */     RECORD_HELPER = instance;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void makeAccessible(AccessibleObject object) throws JsonIOException {
/*     */     try {
/*  35 */       object.setAccessible(true);
/*  36 */     } catch (Exception exception) {
/*  37 */       String description = getAccessibleObjectDescription(object, false);
/*  38 */       throw new JsonIOException("Failed making " + description + " accessible; either increase its visibility or write a custom TypeAdapter for its declaring type.", exception);
/*     */     } 
/*     */   }
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
/*     */   public static String getAccessibleObjectDescription(AccessibleObject object, boolean uppercaseFirstLetter) {
/*     */     String description;
/*  55 */     if (object instanceof Field) {
/*  56 */       description = "field '" + fieldToString((Field)object) + "'";
/*  57 */     } else if (object instanceof Method) {
/*  58 */       Method method = (Method)object;
/*     */       
/*  60 */       StringBuilder methodSignatureBuilder = new StringBuilder(method.getName());
/*  61 */       appendExecutableParameters(method, methodSignatureBuilder);
/*  62 */       String methodSignature = methodSignatureBuilder.toString();
/*     */       
/*  64 */       description = "method '" + method.getDeclaringClass().getName() + "#" + methodSignature + "'";
/*  65 */     } else if (object instanceof Constructor) {
/*  66 */       description = "constructor '" + constructorToString((Constructor)object) + "'";
/*     */     } else {
/*  68 */       description = "<unknown AccessibleObject> " + object.toString();
/*     */     } 
/*     */     
/*  71 */     if (uppercaseFirstLetter && Character.isLowerCase(description.charAt(0))) {
/*  72 */       description = Character.toUpperCase(description.charAt(0)) + description.substring(1);
/*     */     }
/*  74 */     return description;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String fieldToString(Field field) {
/*  82 */     return field.getDeclaringClass().getName() + "#" + field.getName();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String constructorToString(Constructor<?> constructor) {
/*  90 */     StringBuilder stringBuilder = new StringBuilder(constructor.getDeclaringClass().getName());
/*  91 */     appendExecutableParameters(constructor, stringBuilder);
/*     */     
/*  93 */     return stringBuilder.toString();
/*     */   }
/*     */ 
/*     */   
/*     */   private static void appendExecutableParameters(AccessibleObject executable, StringBuilder stringBuilder) {
/*  98 */     stringBuilder.append('(');
/*     */ 
/*     */ 
/*     */     
/* 102 */     Class<?>[] parameters = (executable instanceof Method) ? ((Method)executable).getParameterTypes() : ((Constructor)executable).getParameterTypes();
/* 103 */     for (int i = 0; i < parameters.length; i++) {
/* 104 */       if (i > 0) {
/* 105 */         stringBuilder.append(", ");
/*     */       }
/* 107 */       stringBuilder.append(parameters[i].getSimpleName());
/*     */     } 
/*     */     
/* 110 */     stringBuilder.append(')');
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String tryMakeAccessible(Constructor<?> constructor) {
/*     */     try {
/* 123 */       constructor.setAccessible(true);
/* 124 */       return null;
/* 125 */     } catch (Exception exception) {
/* 126 */       return "Failed making constructor '" + constructorToString(constructor) + "' accessible; either increase its visibility or write a custom InstanceCreator or TypeAdapter for its declaring type: " + exception
/*     */ 
/*     */         
/* 129 */         .getMessage();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean isRecord(Class<?> raw) {
/* 135 */     return RECORD_HELPER.isRecord(raw);
/*     */   }
/*     */   
/*     */   public static String[] getRecordComponentNames(Class<?> raw) {
/* 139 */     return RECORD_HELPER.getRecordComponentNames(raw);
/*     */   }
/*     */ 
/*     */   
/*     */   public static Method getAccessor(Class<?> raw, Field field) {
/* 144 */     return RECORD_HELPER.getAccessor(raw, field);
/*     */   }
/*     */   
/*     */   public static <T> Constructor<T> getCanonicalRecordConstructor(Class<T> raw) {
/* 148 */     return RECORD_HELPER.getCanonicalRecordConstructor(raw);
/*     */   }
/*     */ 
/*     */   
/*     */   public static RuntimeException createExceptionForUnexpectedIllegalAccess(IllegalAccessException exception) {
/* 153 */     throw new RuntimeException("Unexpected IllegalAccessException occurred (Gson 2.10.1). Certain ReflectionAccessFilter features require Java >= 9 to work correctly. If you are not using ReflectionAccessFilter, report this to the Gson maintainers.", exception);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static RuntimeException createExceptionForRecordReflectionException(ReflectiveOperationException exception) {
/* 162 */     throw new RuntimeException("Unexpected ReflectiveOperationException occurred (Gson 2.10.1). To support Java records, reflection is utilized to read out information about records. All these invocations happens after it is established that records exist in the JVM. This exception is unexpected behavior.", exception);
/*     */   }
/*     */ 
/*     */   
/*     */   private static abstract class RecordHelper
/*     */   {
/*     */     private RecordHelper() {}
/*     */ 
/*     */     
/*     */     abstract boolean isRecord(Class<?> param1Class);
/*     */ 
/*     */     
/*     */     abstract String[] getRecordComponentNames(Class<?> param1Class);
/*     */     
/*     */     abstract <T> Constructor<T> getCanonicalRecordConstructor(Class<T> param1Class);
/*     */     
/*     */     public abstract Method getAccessor(Class<?> param1Class, Field param1Field);
/*     */   }
/*     */   
/*     */   private static class RecordSupportedHelper
/*     */     extends RecordHelper
/*     */   {
/*     */     private final Method isRecord;
/*     */     private final Method getRecordComponents;
/*     */     private final Method getName;
/*     */     private final Method getType;
/*     */     
/*     */     private RecordSupportedHelper() throws NoSuchMethodException {
/* 190 */       this.isRecord = Class.class.getMethod("isRecord", new Class[0]);
/* 191 */       this.getRecordComponents = Class.class.getMethod("getRecordComponents", new Class[0]);
/*     */       
/* 193 */       Class<?> classRecordComponent = this.getRecordComponents.getReturnType().getComponentType();
/* 194 */       this.getName = classRecordComponent.getMethod("getName", new Class[0]);
/* 195 */       this.getType = classRecordComponent.getMethod("getType", new Class[0]);
/*     */     }
/*     */ 
/*     */     
/*     */     boolean isRecord(Class<?> raw) {
/*     */       try {
/* 201 */         return ((Boolean)this.isRecord.invoke(raw, new Object[0])).booleanValue();
/* 202 */       } catch (ReflectiveOperationException e) {
/* 203 */         throw ReflectionHelper.createExceptionForRecordReflectionException(e);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     String[] getRecordComponentNames(Class<?> raw) {
/*     */       try {
/* 210 */         Object[] recordComponents = (Object[])this.getRecordComponents.invoke(raw, new Object[0]);
/* 211 */         String[] componentNames = new String[recordComponents.length];
/* 212 */         for (int i = 0; i < recordComponents.length; i++) {
/* 213 */           componentNames[i] = (String)this.getName.invoke(recordComponents[i], new Object[0]);
/*     */         }
/* 215 */         return componentNames;
/* 216 */       } catch (ReflectiveOperationException e) {
/* 217 */         throw ReflectionHelper.createExceptionForRecordReflectionException(e);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public <T> Constructor<T> getCanonicalRecordConstructor(Class<T> raw) {
/*     */       try {
/* 224 */         Object[] recordComponents = (Object[])this.getRecordComponents.invoke(raw, new Object[0]);
/* 225 */         Class<?>[] recordComponentTypes = new Class[recordComponents.length];
/* 226 */         for (int i = 0; i < recordComponents.length; i++) {
/* 227 */           recordComponentTypes[i] = (Class)this.getType.invoke(recordComponents[i], new Object[0]);
/*     */         }
/*     */ 
/*     */         
/* 231 */         return raw.getDeclaredConstructor(recordComponentTypes);
/* 232 */       } catch (ReflectiveOperationException e) {
/* 233 */         throw ReflectionHelper.createExceptionForRecordReflectionException(e);
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Method getAccessor(Class<?> raw, Field field) {
/*     */       try {
/* 242 */         return raw.getMethod(field.getName(), new Class[0]);
/* 243 */       } catch (ReflectiveOperationException e) {
/* 244 */         throw ReflectionHelper.createExceptionForRecordReflectionException(e);
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private static class RecordNotSupportedHelper
/*     */     extends RecordHelper
/*     */   {
/*     */     private RecordNotSupportedHelper() {}
/*     */     
/*     */     boolean isRecord(Class<?> clazz) {
/* 256 */       return false;
/*     */     }
/*     */ 
/*     */     
/*     */     String[] getRecordComponentNames(Class<?> clazz) {
/* 261 */       throw new UnsupportedOperationException("Records are not supported on this JVM, this method should not be called");
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     <T> Constructor<T> getCanonicalRecordConstructor(Class<T> raw) {
/* 267 */       throw new UnsupportedOperationException("Records are not supported on this JVM, this method should not be called");
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public Method getAccessor(Class<?> raw, Field field) {
/* 273 */       throw new UnsupportedOperationException("Records are not supported on this JVM, this method should not be called");
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\libs\gson\internal\reflect\ReflectionHelper.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */