/*     */ package com.viaversion.viaversion.libs.gson.reflect;
/*     */ 
/*     */ import com.viaversion.viaversion.libs.gson.internal.;
/*     */ import java.lang.reflect.GenericArrayType;
/*     */ import java.lang.reflect.ParameterizedType;
/*     */ import java.lang.reflect.Type;
/*     */ import java.lang.reflect.TypeVariable;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import java.util.Objects;
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
/*     */ public class TypeToken<T>
/*     */ {
/*     */   private final Class<? super T> rawType;
/*     */   private final Type type;
/*     */   private final int hashCode;
/*     */   
/*     */   protected TypeToken() {
/*  70 */     this.type = getTypeTokenTypeArgument();
/*  71 */     this.rawType = .Gson.Types.getRawType(this.type);
/*  72 */     this.hashCode = this.type.hashCode();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private TypeToken(Type type) {
/*  80 */     this.type = .Gson.Types.canonicalize(Objects.<Type>requireNonNull(type));
/*  81 */     this.rawType = .Gson.Types.getRawType(this.type);
/*  82 */     this.hashCode = this.type.hashCode();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Type getTypeTokenTypeArgument() {
/*  91 */     Type superclass = getClass().getGenericSuperclass();
/*  92 */     if (superclass instanceof ParameterizedType) {
/*  93 */       ParameterizedType parameterized = (ParameterizedType)superclass;
/*  94 */       if (parameterized.getRawType() == TypeToken.class) {
/*  95 */         return .Gson.Types.canonicalize(parameterized.getActualTypeArguments()[0]);
/*     */       
/*     */       }
/*     */     }
/*  99 */     else if (superclass == TypeToken.class) {
/* 100 */       throw new IllegalStateException("TypeToken must be created with a type argument: new TypeToken<...>() {}; When using code shrinkers (ProGuard, R8, ...) make sure that generic signatures are preserved.");
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 105 */     throw new IllegalStateException("Must only create direct subclasses of TypeToken");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final Class<? super T> getRawType() {
/* 112 */     return this.rawType;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final Type getType() {
/* 119 */     return this.type;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public boolean isAssignableFrom(Class<?> cls) {
/* 130 */     return isAssignableFrom(cls);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public boolean isAssignableFrom(Type from) {
/* 141 */     if (from == null) {
/* 142 */       return false;
/*     */     }
/*     */     
/* 145 */     if (this.type.equals(from)) {
/* 146 */       return true;
/*     */     }
/*     */     
/* 149 */     if (this.type instanceof Class)
/* 150 */       return this.rawType.isAssignableFrom(.Gson.Types.getRawType(from)); 
/* 151 */     if (this.type instanceof ParameterizedType) {
/* 152 */       return isAssignableFrom(from, (ParameterizedType)this.type, new HashMap<>());
/*     */     }
/* 154 */     if (this.type instanceof GenericArrayType) {
/* 155 */       return (this.rawType.isAssignableFrom(.Gson.Types.getRawType(from)) && 
/* 156 */         isAssignableFrom(from, (GenericArrayType)this.type));
/*     */     }
/* 158 */     throw buildUnexpectedTypeError(this.type, new Class[] { Class.class, ParameterizedType.class, GenericArrayType.class });
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
/*     */   @Deprecated
/*     */   public boolean isAssignableFrom(TypeToken<?> token) {
/* 171 */     return isAssignableFrom(token.getType());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static boolean isAssignableFrom(Type<?> from, GenericArrayType to) {
/* 179 */     Type toGenericComponentType = to.getGenericComponentType();
/* 180 */     if (toGenericComponentType instanceof ParameterizedType) {
/* 181 */       Type<?> t = from;
/* 182 */       if (from instanceof GenericArrayType) {
/* 183 */         t = ((GenericArrayType)from).getGenericComponentType();
/* 184 */       } else if (from instanceof Class) {
/* 185 */         Class<?> classType = (Class)from;
/* 186 */         while (classType.isArray()) {
/* 187 */           classType = classType.getComponentType();
/*     */         }
/* 189 */         t = classType;
/*     */       } 
/* 191 */       return isAssignableFrom(t, (ParameterizedType)toGenericComponentType, new HashMap<>());
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 196 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static boolean isAssignableFrom(Type from, ParameterizedType to, Map<String, Type> typeVarMap) {
/* 206 */     if (from == null) {
/* 207 */       return false;
/*     */     }
/*     */     
/* 210 */     if (to.equals(from)) {
/* 211 */       return true;
/*     */     }
/*     */ 
/*     */     
/* 215 */     Class<?> clazz = .Gson.Types.getRawType(from);
/* 216 */     ParameterizedType ptype = null;
/* 217 */     if (from instanceof ParameterizedType) {
/* 218 */       ptype = (ParameterizedType)from;
/*     */     }
/*     */ 
/*     */     
/* 222 */     if (ptype != null) {
/* 223 */       Type[] tArgs = ptype.getActualTypeArguments();
/* 224 */       TypeVariable[] arrayOfTypeVariable = (TypeVariable[])clazz.getTypeParameters();
/* 225 */       for (int i = 0; i < tArgs.length; i++) {
/* 226 */         Type arg = tArgs[i];
/* 227 */         TypeVariable<?> var = arrayOfTypeVariable[i];
/* 228 */         while (arg instanceof TypeVariable) {
/* 229 */           TypeVariable<?> v = (TypeVariable)arg;
/* 230 */           arg = typeVarMap.get(v.getName());
/*     */         } 
/* 232 */         typeVarMap.put(var.getName(), arg);
/*     */       } 
/*     */ 
/*     */       
/* 236 */       if (typeEquals(ptype, to, typeVarMap)) {
/* 237 */         return true;
/*     */       }
/*     */     } 
/*     */     
/* 241 */     for (Type itype : clazz.getGenericInterfaces()) {
/* 242 */       if (isAssignableFrom(itype, to, new HashMap<>(typeVarMap))) {
/* 243 */         return true;
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 248 */     Type sType = clazz.getGenericSuperclass();
/* 249 */     return isAssignableFrom(sType, to, new HashMap<>(typeVarMap));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static boolean typeEquals(ParameterizedType from, ParameterizedType to, Map<String, Type> typeVarMap) {
/* 258 */     if (from.getRawType().equals(to.getRawType())) {
/* 259 */       Type[] fromArgs = from.getActualTypeArguments();
/* 260 */       Type[] toArgs = to.getActualTypeArguments();
/* 261 */       for (int i = 0; i < fromArgs.length; i++) {
/* 262 */         if (!matches(fromArgs[i], toArgs[i], typeVarMap)) {
/* 263 */           return false;
/*     */         }
/*     */       } 
/* 266 */       return true;
/*     */     } 
/* 268 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static AssertionError buildUnexpectedTypeError(Type token, Class<?>... expected) {
/* 275 */     StringBuilder exceptionMessage = new StringBuilder("Unexpected type. Expected one of: ");
/*     */     
/* 277 */     for (Class<?> clazz : expected) {
/* 278 */       exceptionMessage.append(clazz.getName()).append(", ");
/*     */     }
/* 280 */     exceptionMessage.append("but got: ").append(token.getClass().getName())
/* 281 */       .append(", for type token: ").append(token.toString()).append('.');
/*     */     
/* 283 */     return new AssertionError(exceptionMessage.toString());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static boolean matches(Type from, Type to, Map<String, Type> typeMap) {
/* 291 */     return (to.equals(from) || (from instanceof TypeVariable && to
/*     */       
/* 293 */       .equals(typeMap.get(((TypeVariable)from).getName()))));
/*     */   }
/*     */ 
/*     */   
/*     */   public final int hashCode() {
/* 298 */     return this.hashCode;
/*     */   }
/*     */   
/*     */   public final boolean equals(Object o) {
/* 302 */     return (o instanceof TypeToken && 
/* 303 */       .Gson.Types.equals(this.type, ((TypeToken)o).type));
/*     */   }
/*     */   
/*     */   public final String toString() {
/* 307 */     return .Gson.Types.typeToString(this.type);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static TypeToken<?> get(Type type) {
/* 314 */     return new TypeToken(type);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <T> TypeToken<T> get(Class<T> type) {
/* 321 */     return new TypeToken<>(type);
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
/*     */ 
/*     */   
/*     */   public static TypeToken<?> getParameterized(Type rawType, Type... typeArguments) {
/* 342 */     Objects.requireNonNull(rawType);
/* 343 */     Objects.requireNonNull(typeArguments);
/*     */ 
/*     */ 
/*     */     
/* 347 */     if (!(rawType instanceof Class))
/*     */     {
/* 349 */       throw new IllegalArgumentException("rawType must be of type Class, but was " + rawType);
/*     */     }
/* 351 */     Class<?> rawClass = (Class)rawType;
/* 352 */     TypeVariable[] arrayOfTypeVariable = (TypeVariable[])rawClass.getTypeParameters();
/*     */     
/* 354 */     int expectedArgsCount = arrayOfTypeVariable.length;
/* 355 */     int actualArgsCount = typeArguments.length;
/* 356 */     if (actualArgsCount != expectedArgsCount) {
/* 357 */       throw new IllegalArgumentException(rawClass.getName() + " requires " + expectedArgsCount + " type arguments, but got " + actualArgsCount);
/*     */     }
/*     */ 
/*     */     
/* 361 */     for (int i = 0; i < expectedArgsCount; i++) {
/* 362 */       Type typeArgument = typeArguments[i];
/* 363 */       Class<?> rawTypeArgument = .Gson.Types.getRawType(typeArgument);
/* 364 */       TypeVariable<?> typeVariable = arrayOfTypeVariable[i];
/*     */       
/* 366 */       for (Type bound : typeVariable.getBounds()) {
/* 367 */         Class<?> rawBound = .Gson.Types.getRawType(bound);
/*     */         
/* 369 */         if (!rawBound.isAssignableFrom(rawTypeArgument)) {
/* 370 */           throw new IllegalArgumentException("Type argument " + typeArgument + " does not satisfy bounds for type variable " + typeVariable + " declared by " + rawType);
/*     */         }
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 376 */     return new TypeToken(.Gson.Types.newParameterizedTypeWithOwner(null, rawType, typeArguments));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static TypeToken<?> getArray(Type componentType) {
/* 383 */     return new TypeToken(.Gson.Types.arrayOf(componentType));
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\libs\gson\reflect\TypeToken.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */