/*     */ package com.viaversion.viaversion.libs.gson.internal;
/*     */ 
/*     */ import com.viaversion.viaversion.libs.gson.ExclusionStrategy;
/*     */ import com.viaversion.viaversion.libs.gson.FieldAttributes;
/*     */ import com.viaversion.viaversion.libs.gson.Gson;
/*     */ import com.viaversion.viaversion.libs.gson.TypeAdapter;
/*     */ import com.viaversion.viaversion.libs.gson.TypeAdapterFactory;
/*     */ import com.viaversion.viaversion.libs.gson.annotations.Expose;
/*     */ import com.viaversion.viaversion.libs.gson.annotations.Since;
/*     */ import com.viaversion.viaversion.libs.gson.annotations.Until;
/*     */ import com.viaversion.viaversion.libs.gson.reflect.TypeToken;
/*     */ import com.viaversion.viaversion.libs.gson.stream.JsonReader;
/*     */ import com.viaversion.viaversion.libs.gson.stream.JsonWriter;
/*     */ import java.io.IOException;
/*     */ import java.lang.reflect.Field;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
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
/*     */ public final class Excluder
/*     */   implements TypeAdapterFactory, Cloneable
/*     */ {
/*     */   private static final double IGNORE_VERSIONS = -1.0D;
/*  52 */   public static final Excluder DEFAULT = new Excluder();
/*     */   
/*  54 */   private double version = -1.0D;
/*  55 */   private int modifiers = 136;
/*     */   private boolean serializeInnerClasses = true;
/*     */   private boolean requireExpose;
/*  58 */   private List<ExclusionStrategy> serializationStrategies = Collections.emptyList();
/*  59 */   private List<ExclusionStrategy> deserializationStrategies = Collections.emptyList();
/*     */   
/*     */   protected Excluder clone() {
/*     */     try {
/*  63 */       return (Excluder)super.clone();
/*  64 */     } catch (CloneNotSupportedException e) {
/*  65 */       throw new AssertionError(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   public Excluder withVersion(double ignoreVersionsAfter) {
/*  70 */     Excluder result = clone();
/*  71 */     result.version = ignoreVersionsAfter;
/*  72 */     return result;
/*     */   }
/*     */   
/*     */   public Excluder withModifiers(int... modifiers) {
/*  76 */     Excluder result = clone();
/*  77 */     result.modifiers = 0;
/*  78 */     for (int modifier : modifiers) {
/*  79 */       result.modifiers |= modifier;
/*     */     }
/*  81 */     return result;
/*     */   }
/*     */   
/*     */   public Excluder disableInnerClassSerialization() {
/*  85 */     Excluder result = clone();
/*  86 */     result.serializeInnerClasses = false;
/*  87 */     return result;
/*     */   }
/*     */   
/*     */   public Excluder excludeFieldsWithoutExposeAnnotation() {
/*  91 */     Excluder result = clone();
/*  92 */     result.requireExpose = true;
/*  93 */     return result;
/*     */   }
/*     */ 
/*     */   
/*     */   public Excluder withExclusionStrategy(ExclusionStrategy exclusionStrategy, boolean serialization, boolean deserialization) {
/*  98 */     Excluder result = clone();
/*  99 */     if (serialization) {
/* 100 */       result.serializationStrategies = new ArrayList<>(this.serializationStrategies);
/* 101 */       result.serializationStrategies.add(exclusionStrategy);
/*     */     } 
/* 103 */     if (deserialization) {
/* 104 */       result.deserializationStrategies = new ArrayList<>(this.deserializationStrategies);
/* 105 */       result.deserializationStrategies.add(exclusionStrategy);
/*     */     } 
/* 107 */     return result;
/*     */   }
/*     */   
/*     */   public <T> TypeAdapter<T> create(final Gson gson, final TypeToken<T> type) {
/* 111 */     Class<?> rawType = type.getRawType();
/* 112 */     boolean excludeClass = excludeClassChecks(rawType);
/*     */     
/* 114 */     final boolean skipSerialize = (excludeClass || excludeClassInStrategy(rawType, true));
/* 115 */     final boolean skipDeserialize = (excludeClass || excludeClassInStrategy(rawType, false));
/*     */     
/* 117 */     if (!skipSerialize && !skipDeserialize) {
/* 118 */       return null;
/*     */     }
/*     */     
/* 121 */     return new TypeAdapter<T>()
/*     */       {
/*     */         private TypeAdapter<T> delegate;
/*     */         
/*     */         public T read(JsonReader in) throws IOException {
/* 126 */           if (skipDeserialize) {
/* 127 */             in.skipValue();
/* 128 */             return null;
/*     */           } 
/* 130 */           return (T)delegate().read(in);
/*     */         }
/*     */         
/*     */         public void write(JsonWriter out, T value) throws IOException {
/* 134 */           if (skipSerialize) {
/* 135 */             out.nullValue();
/*     */             return;
/*     */           } 
/* 138 */           delegate().write(out, value);
/*     */         }
/*     */         
/*     */         private TypeAdapter<T> delegate() {
/* 142 */           TypeAdapter<T> d = this.delegate;
/* 143 */           return (d != null) ? 
/* 144 */             d : (
/* 145 */             this.delegate = gson.getDelegateAdapter(Excluder.this, type));
/*     */         }
/*     */       };
/*     */   }
/*     */   
/*     */   public boolean excludeField(Field field, boolean serialize) {
/* 151 */     if ((this.modifiers & field.getModifiers()) != 0) {
/* 152 */       return true;
/*     */     }
/*     */     
/* 155 */     if (this.version != -1.0D && 
/* 156 */       !isValidVersion(field.<Since>getAnnotation(Since.class), field.<Until>getAnnotation(Until.class))) {
/* 157 */       return true;
/*     */     }
/*     */     
/* 160 */     if (field.isSynthetic()) {
/* 161 */       return true;
/*     */     }
/*     */     
/* 164 */     if (this.requireExpose) {
/* 165 */       Expose annotation = field.<Expose>getAnnotation(Expose.class);
/* 166 */       if (annotation == null || (serialize ? !annotation.serialize() : !annotation.deserialize())) {
/* 167 */         return true;
/*     */       }
/*     */     } 
/*     */     
/* 171 */     if (!this.serializeInnerClasses && isInnerClass(field.getType())) {
/* 172 */       return true;
/*     */     }
/*     */     
/* 175 */     if (isAnonymousOrNonStaticLocal(field.getType())) {
/* 176 */       return true;
/*     */     }
/*     */     
/* 179 */     List<ExclusionStrategy> list = serialize ? this.serializationStrategies : this.deserializationStrategies;
/* 180 */     if (!list.isEmpty()) {
/* 181 */       FieldAttributes fieldAttributes = new FieldAttributes(field);
/* 182 */       for (ExclusionStrategy exclusionStrategy : list) {
/* 183 */         if (exclusionStrategy.shouldSkipField(fieldAttributes)) {
/* 184 */           return true;
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 189 */     return false;
/*     */   }
/*     */   
/*     */   private boolean excludeClassChecks(Class<?> clazz) {
/* 193 */     if (this.version != -1.0D && !isValidVersion(clazz.<Since>getAnnotation(Since.class), clazz.<Until>getAnnotation(Until.class))) {
/* 194 */       return true;
/*     */     }
/*     */     
/* 197 */     if (!this.serializeInnerClasses && isInnerClass(clazz)) {
/* 198 */       return true;
/*     */     }
/*     */     
/* 201 */     return isAnonymousOrNonStaticLocal(clazz);
/*     */   }
/*     */   
/*     */   public boolean excludeClass(Class<?> clazz, boolean serialize) {
/* 205 */     return (excludeClassChecks(clazz) || 
/* 206 */       excludeClassInStrategy(clazz, serialize));
/*     */   }
/*     */   
/*     */   private boolean excludeClassInStrategy(Class<?> clazz, boolean serialize) {
/* 210 */     List<ExclusionStrategy> list = serialize ? this.serializationStrategies : this.deserializationStrategies;
/* 211 */     for (ExclusionStrategy exclusionStrategy : list) {
/* 212 */       if (exclusionStrategy.shouldSkipClass(clazz)) {
/* 213 */         return true;
/*     */       }
/*     */     } 
/* 216 */     return false;
/*     */   }
/*     */   
/*     */   private boolean isAnonymousOrNonStaticLocal(Class<?> clazz) {
/* 220 */     return (!Enum.class.isAssignableFrom(clazz) && !isStatic(clazz) && (clazz
/* 221 */       .isAnonymousClass() || clazz.isLocalClass()));
/*     */   }
/*     */   
/*     */   private boolean isInnerClass(Class<?> clazz) {
/* 225 */     return (clazz.isMemberClass() && !isStatic(clazz));
/*     */   }
/*     */   
/*     */   private boolean isStatic(Class<?> clazz) {
/* 229 */     return ((clazz.getModifiers() & 0x8) != 0);
/*     */   }
/*     */   
/*     */   private boolean isValidVersion(Since since, Until until) {
/* 233 */     return (isValidSince(since) && isValidUntil(until));
/*     */   }
/*     */   
/*     */   private boolean isValidSince(Since annotation) {
/* 237 */     if (annotation != null) {
/* 238 */       double annotationVersion = annotation.value();
/* 239 */       return (this.version >= annotationVersion);
/*     */     } 
/* 241 */     return true;
/*     */   }
/*     */   
/*     */   private boolean isValidUntil(Until annotation) {
/* 245 */     if (annotation != null) {
/* 246 */       double annotationVersion = annotation.value();
/* 247 */       return (this.version < annotationVersion);
/*     */     } 
/* 249 */     return true;
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\libs\gson\internal\Excluder.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */