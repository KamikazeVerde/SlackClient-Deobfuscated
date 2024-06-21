/*     */ package com.viaversion.viaversion.libs.gson.internal;
/*     */ 
/*     */ import com.viaversion.viaversion.libs.gson.InstanceCreator;
/*     */ import com.viaversion.viaversion.libs.gson.JsonIOException;
/*     */ import com.viaversion.viaversion.libs.gson.ReflectionAccessFilter;
/*     */ import com.viaversion.viaversion.libs.gson.internal.reflect.ReflectionHelper;
/*     */ import com.viaversion.viaversion.libs.gson.reflect.TypeToken;
/*     */ import java.lang.reflect.Constructor;
/*     */ import java.lang.reflect.InvocationTargetException;
/*     */ import java.lang.reflect.Modifier;
/*     */ import java.lang.reflect.ParameterizedType;
/*     */ import java.lang.reflect.Type;
/*     */ import java.util.ArrayDeque;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.EnumMap;
/*     */ import java.util.EnumSet;
/*     */ import java.util.LinkedHashMap;
/*     */ import java.util.LinkedHashSet;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Queue;
/*     */ import java.util.Set;
/*     */ import java.util.SortedMap;
/*     */ import java.util.SortedSet;
/*     */ import java.util.TreeMap;
/*     */ import java.util.TreeSet;
/*     */ import java.util.concurrent.ConcurrentHashMap;
/*     */ import java.util.concurrent.ConcurrentMap;
/*     */ import java.util.concurrent.ConcurrentNavigableMap;
/*     */ import java.util.concurrent.ConcurrentSkipListMap;
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
/*     */ public final class ConstructorConstructor
/*     */ {
/*     */   private final Map<Type, InstanceCreator<?>> instanceCreators;
/*     */   private final boolean useJdkUnsafe;
/*     */   private final List<ReflectionAccessFilter> reflectionFilters;
/*     */   
/*     */   public ConstructorConstructor(Map<Type, InstanceCreator<?>> instanceCreators, boolean useJdkUnsafe, List<ReflectionAccessFilter> reflectionFilters) {
/*  59 */     this.instanceCreators = instanceCreators;
/*  60 */     this.useJdkUnsafe = useJdkUnsafe;
/*  61 */     this.reflectionFilters = reflectionFilters;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static String checkInstantiable(Class<?> c) {
/*  71 */     int modifiers = c.getModifiers();
/*  72 */     if (Modifier.isInterface(modifiers)) {
/*  73 */       return "Interfaces can't be instantiated! Register an InstanceCreator or a TypeAdapter for this type. Interface name: " + c
/*  74 */         .getName();
/*     */     }
/*  76 */     if (Modifier.isAbstract(modifiers)) {
/*  77 */       return "Abstract classes can't be instantiated! Register an InstanceCreator or a TypeAdapter for this type. Class name: " + c
/*  78 */         .getName();
/*     */     }
/*  80 */     return null;
/*     */   }
/*     */   
/*     */   public <T> ObjectConstructor<T> get(TypeToken<T> typeToken) {
/*  84 */     final Type type = typeToken.getType();
/*  85 */     Class<? super T> rawType = typeToken.getRawType();
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  90 */     final InstanceCreator<T> typeCreator = (InstanceCreator<T>)this.instanceCreators.get(type);
/*  91 */     if (typeCreator != null) {
/*  92 */       return new ObjectConstructor<T>() {
/*     */           public T construct() {
/*  94 */             return (T)typeCreator.createInstance(type);
/*     */           }
/*     */         };
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 102 */     final InstanceCreator<T> rawTypeCreator = (InstanceCreator<T>)this.instanceCreators.get(rawType);
/* 103 */     if (rawTypeCreator != null) {
/* 104 */       return new ObjectConstructor<T>() {
/*     */           public T construct() {
/* 106 */             return (T)rawTypeCreator.createInstance(type);
/*     */           }
/*     */         };
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 114 */     ObjectConstructor<T> specialConstructor = newSpecialCollectionConstructor(type, rawType);
/* 115 */     if (specialConstructor != null) {
/* 116 */       return specialConstructor;
/*     */     }
/*     */     
/* 119 */     ReflectionAccessFilter.FilterResult filterResult = ReflectionAccessFilterHelper.getFilterResult(this.reflectionFilters, rawType);
/* 120 */     ObjectConstructor<T> defaultConstructor = newDefaultConstructor(rawType, filterResult);
/* 121 */     if (defaultConstructor != null) {
/* 122 */       return defaultConstructor;
/*     */     }
/*     */     
/* 125 */     ObjectConstructor<T> defaultImplementation = newDefaultImplementationConstructor(type, rawType);
/* 126 */     if (defaultImplementation != null) {
/* 127 */       return defaultImplementation;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 132 */     final String exceptionMessage = checkInstantiable(rawType);
/* 133 */     if (exceptionMessage != null) {
/* 134 */       return new ObjectConstructor<T>() {
/*     */           public T construct() {
/* 136 */             throw new JsonIOException(exceptionMessage);
/*     */           }
/*     */         };
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 143 */     if (filterResult == ReflectionAccessFilter.FilterResult.ALLOW)
/*     */     {
/* 145 */       return newUnsafeAllocator(rawType);
/*     */     }
/* 147 */     final String message = "Unable to create instance of " + rawType + "; ReflectionAccessFilter does not permit using reflection or Unsafe. Register an InstanceCreator or a TypeAdapter for this type or adjust the access filter to allow using reflection.";
/*     */ 
/*     */     
/* 150 */     return new ObjectConstructor<T>() {
/*     */         public T construct() {
/* 152 */           throw new JsonIOException(message);
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static <T> ObjectConstructor<T> newSpecialCollectionConstructor(final Type type, Class<? super T> rawType) {
/* 162 */     if (EnumSet.class.isAssignableFrom(rawType)) {
/* 163 */       return new ObjectConstructor<T>() {
/*     */           public T construct() {
/* 165 */             if (type instanceof ParameterizedType) {
/* 166 */               Type elementType = ((ParameterizedType)type).getActualTypeArguments()[0];
/* 167 */               if (elementType instanceof Class)
/*     */               {
/* 169 */                 return (T)EnumSet.noneOf((Class<Enum>)elementType);
/*     */               }
/*     */               
/* 172 */               throw new JsonIOException("Invalid EnumSet type: " + type.toString());
/*     */             } 
/*     */             
/* 175 */             throw new JsonIOException("Invalid EnumSet type: " + type.toString());
/*     */           }
/*     */         };
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 182 */     if (rawType == EnumMap.class) {
/* 183 */       return new ObjectConstructor<T>() {
/*     */           public T construct() {
/* 185 */             if (type instanceof ParameterizedType) {
/* 186 */               Type elementType = ((ParameterizedType)type).getActualTypeArguments()[0];
/* 187 */               if (elementType instanceof Class)
/*     */               {
/* 189 */                 return (T)new EnumMap<>((Class<Enum>)elementType);
/*     */               }
/*     */               
/* 192 */               throw new JsonIOException("Invalid EnumMap type: " + type.toString());
/*     */             } 
/*     */             
/* 195 */             throw new JsonIOException("Invalid EnumMap type: " + type.toString());
/*     */           }
/*     */         };
/*     */     }
/*     */ 
/*     */     
/* 201 */     return null;
/*     */   }
/*     */   
/*     */   private static <T> ObjectConstructor<T> newDefaultConstructor(Class<? super T> rawType, ReflectionAccessFilter.FilterResult filterResult) {
/*     */     final Constructor<? super T> constructor;
/* 206 */     if (Modifier.isAbstract(rawType.getModifiers())) {
/* 207 */       return null;
/*     */     }
/*     */ 
/*     */     
/*     */     try {
/* 212 */       constructor = rawType.getDeclaredConstructor(new Class[0]);
/* 213 */     } catch (NoSuchMethodException e) {
/* 214 */       return null;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 219 */     boolean canAccess = (filterResult == ReflectionAccessFilter.FilterResult.ALLOW || (ReflectionAccessFilterHelper.canAccess(constructor, null) && (filterResult != ReflectionAccessFilter.FilterResult.BLOCK_ALL || Modifier.isPublic(constructor.getModifiers()))));
/*     */     
/* 221 */     if (!canAccess) {
/* 222 */       final String message = "Unable to invoke no-args constructor of " + rawType + "; constructor is not accessible and ReflectionAccessFilter does not permit making it accessible. Register an InstanceCreator or a TypeAdapter for this type, change the visibility of the constructor or adjust the access filter.";
/*     */ 
/*     */ 
/*     */       
/* 226 */       return new ObjectConstructor<T>() {
/*     */           public T construct() {
/* 228 */             throw new JsonIOException(message);
/*     */           }
/*     */         };
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 235 */     if (filterResult == ReflectionAccessFilter.FilterResult.ALLOW) {
/* 236 */       final String exceptionMessage = ReflectionHelper.tryMakeAccessible(constructor);
/* 237 */       if (exceptionMessage != null)
/*     */       {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 246 */         return new ObjectConstructor<T>()
/*     */           {
/*     */ 
/*     */             
/*     */             public T construct()
/*     */             {
/* 252 */               throw new JsonIOException(exceptionMessage);
/*     */             }
/*     */           };
/*     */       }
/*     */     } 
/*     */     
/* 258 */     return new ObjectConstructor<T>()
/*     */       {
/*     */         public T construct() {
/*     */           try {
/* 262 */             T newInstance = constructor.newInstance(new Object[0]);
/* 263 */             return newInstance;
/*     */ 
/*     */           
/*     */           }
/* 267 */           catch (InstantiationException e) {
/* 268 */             throw new RuntimeException("Failed to invoke constructor '" + ReflectionHelper.constructorToString(constructor) + "' with no args", e);
/*     */           }
/* 270 */           catch (InvocationTargetException e) {
/*     */ 
/*     */             
/* 273 */             throw new RuntimeException("Failed to invoke constructor '" + ReflectionHelper.constructorToString(constructor) + "' with no args", e
/* 274 */                 .getCause());
/* 275 */           } catch (IllegalAccessException e) {
/* 276 */             throw ReflectionHelper.createExceptionForUnexpectedIllegalAccess(e);
/*     */           } 
/*     */         }
/*     */       };
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
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static <T> ObjectConstructor<T> newDefaultImplementationConstructor(Type type, Class<? super T> rawType) {
/* 298 */     if (Collection.class.isAssignableFrom(rawType)) {
/* 299 */       if (SortedSet.class.isAssignableFrom(rawType))
/* 300 */         return new ObjectConstructor<T>() {
/*     */             public T construct() {
/* 302 */               return (T)new TreeSet();
/*     */             }
/*     */           }; 
/* 305 */       if (Set.class.isAssignableFrom(rawType))
/* 306 */         return new ObjectConstructor<T>() {
/*     */             public T construct() {
/* 308 */               return (T)new LinkedHashSet();
/*     */             }
/*     */           }; 
/* 311 */       if (Queue.class.isAssignableFrom(rawType)) {
/* 312 */         return new ObjectConstructor<T>() {
/*     */             public T construct() {
/* 314 */               return (T)new ArrayDeque();
/*     */             }
/*     */           };
/*     */       }
/* 318 */       return new ObjectConstructor<T>() {
/*     */           public T construct() {
/* 320 */             return (T)new ArrayList();
/*     */           }
/*     */         };
/*     */     } 
/*     */ 
/*     */     
/* 326 */     if (Map.class.isAssignableFrom(rawType)) {
/* 327 */       if (ConcurrentNavigableMap.class.isAssignableFrom(rawType))
/* 328 */         return new ObjectConstructor<T>() {
/*     */             public T construct() {
/* 330 */               return (T)new ConcurrentSkipListMap<>();
/*     */             }
/*     */           }; 
/* 333 */       if (ConcurrentMap.class.isAssignableFrom(rawType))
/* 334 */         return new ObjectConstructor<T>() {
/*     */             public T construct() {
/* 336 */               return (T)new ConcurrentHashMap<>();
/*     */             }
/*     */           }; 
/* 339 */       if (SortedMap.class.isAssignableFrom(rawType))
/* 340 */         return new ObjectConstructor<T>() {
/*     */             public T construct() {
/* 342 */               return (T)new TreeMap<>();
/*     */             }
/*     */           }; 
/* 345 */       if (type instanceof ParameterizedType && !String.class.isAssignableFrom(
/* 346 */           TypeToken.get(((ParameterizedType)type).getActualTypeArguments()[0]).getRawType())) {
/* 347 */         return new ObjectConstructor<T>() {
/*     */             public T construct() {
/* 349 */               return (T)new LinkedHashMap<>();
/*     */             }
/*     */           };
/*     */       }
/* 353 */       return new ObjectConstructor<T>() {
/*     */           public T construct() {
/* 355 */             return (T)new LinkedTreeMap<>();
/*     */           }
/*     */         };
/*     */     } 
/*     */ 
/*     */     
/* 361 */     return null;
/*     */   }
/*     */   
/*     */   private <T> ObjectConstructor<T> newUnsafeAllocator(final Class<? super T> rawType) {
/* 365 */     if (this.useJdkUnsafe) {
/* 366 */       return new ObjectConstructor<T>()
/*     */         {
/*     */           public T construct() {
/*     */             try {
/* 370 */               T newInstance = UnsafeAllocator.INSTANCE.newInstance(rawType);
/* 371 */               return newInstance;
/* 372 */             } catch (Exception e) {
/* 373 */               throw new RuntimeException("Unable to create instance of " + rawType + ". Registering an InstanceCreator or a TypeAdapter for this type, or adding a no-args constructor may fix this problem.", e);
/*     */             } 
/*     */           }
/*     */         };
/*     */     }
/*     */ 
/*     */     
/* 380 */     final String exceptionMessage = "Unable to create instance of " + rawType + "; usage of JDK Unsafe is disabled. Registering an InstanceCreator or a TypeAdapter for this type, adding a no-args constructor, or enabling usage of JDK Unsafe may fix this problem.";
/*     */ 
/*     */     
/* 383 */     return new ObjectConstructor<T>() {
/*     */         public T construct() {
/* 385 */           throw new JsonIOException(exceptionMessage);
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 392 */     return this.instanceCreators.toString();
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\libs\gson\internal\ConstructorConstructor.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */