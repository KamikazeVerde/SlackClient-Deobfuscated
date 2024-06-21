/*     */ package com.viaversion.viaversion.libs.gson.internal;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import java.lang.reflect.Array;
/*     */ import java.lang.reflect.GenericArrayType;
/*     */ import java.lang.reflect.GenericDeclaration;
/*     */ import java.lang.reflect.Modifier;
/*     */ import java.lang.reflect.ParameterizedType;
/*     */ import java.lang.reflect.Type;
/*     */ import java.lang.reflect.TypeVariable;
/*     */ import java.lang.reflect.WildcardType;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collection;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import java.util.NoSuchElementException;
/*     */ import java.util.Objects;
/*     */ import java.util.Properties;
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
/*     */ public final class $Gson$Types
/*     */ {
/*  46 */   static final Type[] EMPTY_TYPE_ARRAY = new Type[0];
/*     */   
/*     */   private $Gson$Types() {
/*  49 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ParameterizedType newParameterizedTypeWithOwner(Type ownerType, Type rawType, Type... typeArguments) {
/*  60 */     return new ParameterizedTypeImpl(ownerType, rawType, typeArguments);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static GenericArrayType arrayOf(Type componentType) {
/*  70 */     return new GenericArrayTypeImpl(componentType);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static WildcardType subtypeOf(Type bound) {
/*     */     Type[] upperBounds;
/*  81 */     if (bound instanceof WildcardType) {
/*  82 */       upperBounds = ((WildcardType)bound).getUpperBounds();
/*     */     } else {
/*  84 */       upperBounds = new Type[] { bound };
/*     */     } 
/*  86 */     return new WildcardTypeImpl(upperBounds, EMPTY_TYPE_ARRAY);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static WildcardType supertypeOf(Type bound) {
/*     */     Type[] lowerBounds;
/*  96 */     if (bound instanceof WildcardType) {
/*  97 */       lowerBounds = ((WildcardType)bound).getLowerBounds();
/*     */     } else {
/*  99 */       lowerBounds = new Type[] { bound };
/*     */     } 
/* 101 */     return new WildcardTypeImpl(new Type[] { Object.class }, lowerBounds);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Type canonicalize(Type type) {
/* 110 */     if (type instanceof Class) {
/* 111 */       Class<?> c = (Class)type;
/* 112 */       return c.isArray() ? new GenericArrayTypeImpl(canonicalize(c.getComponentType())) : c;
/*     */     } 
/* 114 */     if (type instanceof ParameterizedType) {
/* 115 */       ParameterizedType p = (ParameterizedType)type;
/* 116 */       return new ParameterizedTypeImpl(p.getOwnerType(), p
/* 117 */           .getRawType(), p.getActualTypeArguments());
/*     */     } 
/* 119 */     if (type instanceof GenericArrayType) {
/* 120 */       GenericArrayType g = (GenericArrayType)type;
/* 121 */       return new GenericArrayTypeImpl(g.getGenericComponentType());
/*     */     } 
/* 123 */     if (type instanceof WildcardType) {
/* 124 */       WildcardType w = (WildcardType)type;
/* 125 */       return new WildcardTypeImpl(w.getUpperBounds(), w.getLowerBounds());
/*     */     } 
/*     */ 
/*     */     
/* 129 */     return type;
/*     */   }
/*     */ 
/*     */   
/*     */   public static Class<?> getRawType(Type type) {
/* 134 */     if (type instanceof Class)
/*     */     {
/* 136 */       return (Class)type;
/*     */     }
/* 138 */     if (type instanceof ParameterizedType) {
/* 139 */       ParameterizedType parameterizedType = (ParameterizedType)type;
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 144 */       Type rawType = parameterizedType.getRawType();
/* 145 */       $Gson$Preconditions.checkArgument(rawType instanceof Class);
/* 146 */       return (Class)rawType;
/*     */     } 
/* 148 */     if (type instanceof GenericArrayType) {
/* 149 */       Type componentType = ((GenericArrayType)type).getGenericComponentType();
/* 150 */       return Array.newInstance(getRawType(componentType), 0).getClass();
/*     */     } 
/* 152 */     if (type instanceof TypeVariable)
/*     */     {
/*     */       
/* 155 */       return Object.class;
/*     */     }
/* 157 */     if (type instanceof WildcardType) {
/* 158 */       Type[] bounds = ((WildcardType)type).getUpperBounds();
/*     */       
/* 160 */       assert bounds.length == 1;
/* 161 */       return getRawType(bounds[0]);
/*     */     } 
/*     */     
/* 164 */     String className = (type == null) ? "null" : type.getClass().getName();
/* 165 */     throw new IllegalArgumentException("Expected a Class, ParameterizedType, or GenericArrayType, but <" + type + "> is of type " + className);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static boolean equal(Object a, Object b) {
/* 171 */     return Objects.equals(a, b);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean equals(Type a, Type b) {
/* 178 */     if (a == b)
/*     */     {
/* 180 */       return true;
/*     */     }
/* 182 */     if (a instanceof Class)
/*     */     {
/* 184 */       return a.equals(b);
/*     */     }
/* 186 */     if (a instanceof ParameterizedType) {
/* 187 */       if (!(b instanceof ParameterizedType)) {
/* 188 */         return false;
/*     */       }
/*     */ 
/*     */       
/* 192 */       ParameterizedType pa = (ParameterizedType)a;
/* 193 */       ParameterizedType pb = (ParameterizedType)b;
/* 194 */       return (equal(pa.getOwnerType(), pb.getOwnerType()) && pa
/* 195 */         .getRawType().equals(pb.getRawType()) && 
/* 196 */         Arrays.equals((Object[])pa.getActualTypeArguments(), (Object[])pb.getActualTypeArguments()));
/*     */     } 
/* 198 */     if (a instanceof GenericArrayType) {
/* 199 */       if (!(b instanceof GenericArrayType)) {
/* 200 */         return false;
/*     */       }
/*     */       
/* 203 */       GenericArrayType ga = (GenericArrayType)a;
/* 204 */       GenericArrayType gb = (GenericArrayType)b;
/* 205 */       return equals(ga.getGenericComponentType(), gb.getGenericComponentType());
/*     */     } 
/* 207 */     if (a instanceof WildcardType) {
/* 208 */       if (!(b instanceof WildcardType)) {
/* 209 */         return false;
/*     */       }
/*     */       
/* 212 */       WildcardType wa = (WildcardType)a;
/* 213 */       WildcardType wb = (WildcardType)b;
/* 214 */       return (Arrays.equals((Object[])wa.getUpperBounds(), (Object[])wb.getUpperBounds()) && 
/* 215 */         Arrays.equals((Object[])wa.getLowerBounds(), (Object[])wb.getLowerBounds()));
/*     */     } 
/* 217 */     if (a instanceof TypeVariable) {
/* 218 */       if (!(b instanceof TypeVariable)) {
/* 219 */         return false;
/*     */       }
/* 221 */       TypeVariable<?> va = (TypeVariable)a;
/* 222 */       TypeVariable<?> vb = (TypeVariable)b;
/* 223 */       return (va.getGenericDeclaration() == vb.getGenericDeclaration() && va
/* 224 */         .getName().equals(vb.getName()));
/*     */     } 
/*     */ 
/*     */     
/* 228 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public static String typeToString(Type type) {
/* 233 */     return (type instanceof Class) ? ((Class)type).getName() : type.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static Type getGenericSupertype(Type context, Class<?> rawType, Class<?> supertype) {
/* 242 */     if (supertype == rawType) {
/* 243 */       return context;
/*     */     }
/*     */ 
/*     */     
/* 247 */     if (supertype.isInterface()) {
/* 248 */       Class<?>[] interfaces = rawType.getInterfaces();
/* 249 */       for (int i = 0, length = interfaces.length; i < length; i++) {
/* 250 */         if (interfaces[i] == supertype)
/* 251 */           return rawType.getGenericInterfaces()[i]; 
/* 252 */         if (supertype.isAssignableFrom(interfaces[i])) {
/* 253 */           return getGenericSupertype(rawType.getGenericInterfaces()[i], interfaces[i], supertype);
/*     */         }
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 259 */     if (!rawType.isInterface()) {
/* 260 */       while (rawType != Object.class) {
/* 261 */         Class<?> rawSupertype = rawType.getSuperclass();
/* 262 */         if (rawSupertype == supertype)
/* 263 */           return rawType.getGenericSuperclass(); 
/* 264 */         if (supertype.isAssignableFrom(rawSupertype)) {
/* 265 */           return getGenericSupertype(rawType.getGenericSuperclass(), rawSupertype, supertype);
/*     */         }
/* 267 */         rawType = rawSupertype;
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/* 272 */     return supertype;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static Type getSupertype(Type context, Class<?> contextRawType, Class<?> supertype) {
/* 283 */     if (context instanceof WildcardType) {
/*     */       
/* 285 */       Type[] bounds = ((WildcardType)context).getUpperBounds();
/*     */       
/* 287 */       assert bounds.length == 1;
/* 288 */       context = bounds[0];
/*     */     } 
/* 290 */     $Gson$Preconditions.checkArgument(supertype.isAssignableFrom(contextRawType));
/* 291 */     return resolve(context, contextRawType, 
/* 292 */         getGenericSupertype(context, contextRawType, supertype));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Type getArrayComponentType(Type array) {
/* 300 */     return (array instanceof GenericArrayType) ? (
/* 301 */       (GenericArrayType)array).getGenericComponentType() : (
/* 302 */       (Class)array).getComponentType();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Type getCollectionElementType(Type context, Class<?> contextRawType) {
/* 310 */     Type collectionType = getSupertype(context, contextRawType, Collection.class);
/*     */     
/* 312 */     if (collectionType instanceof ParameterizedType) {
/* 313 */       return ((ParameterizedType)collectionType).getActualTypeArguments()[0];
/*     */     }
/* 315 */     return Object.class;
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
/*     */   public static Type[] getMapKeyAndValueTypes(Type context, Class<?> contextRawType) {
/* 328 */     if (context == Properties.class) {
/* 329 */       return new Type[] { String.class, String.class };
/*     */     }
/*     */     
/* 332 */     Type mapType = getSupertype(context, contextRawType, Map.class);
/*     */     
/* 334 */     if (mapType instanceof ParameterizedType) {
/* 335 */       ParameterizedType mapParameterizedType = (ParameterizedType)mapType;
/* 336 */       return mapParameterizedType.getActualTypeArguments();
/*     */     } 
/* 338 */     return new Type[] { Object.class, Object.class };
/*     */   }
/*     */ 
/*     */   
/*     */   public static Type resolve(Type context, Class<?> contextRawType, Type toResolve) {
/* 343 */     return resolve(context, contextRawType, toResolve, new HashMap<>());
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
/*     */   private static Type resolve(Type context, Class<?> contextRawType, Type toResolve, Map<TypeVariable<?>, Type> visitedTypeVariables) {
/*     */     // Byte code:
/*     */     //   0: aconst_null
/*     */     //   1: astore #4
/*     */     //   3: aload_2
/*     */     //   4: instanceof java/lang/reflect/TypeVariable
/*     */     //   7: ifeq -> 90
/*     */     //   10: aload_2
/*     */     //   11: checkcast java/lang/reflect/TypeVariable
/*     */     //   14: astore #5
/*     */     //   16: aload_3
/*     */     //   17: aload #5
/*     */     //   19: invokeinterface get : (Ljava/lang/Object;)Ljava/lang/Object;
/*     */     //   24: checkcast java/lang/reflect/Type
/*     */     //   27: astore #6
/*     */     //   29: aload #6
/*     */     //   31: ifnull -> 49
/*     */     //   34: aload #6
/*     */     //   36: getstatic java/lang/Void.TYPE : Ljava/lang/Class;
/*     */     //   39: if_acmpne -> 46
/*     */     //   42: aload_2
/*     */     //   43: goto -> 48
/*     */     //   46: aload #6
/*     */     //   48: areturn
/*     */     //   49: aload_3
/*     */     //   50: aload #5
/*     */     //   52: getstatic java/lang/Void.TYPE : Ljava/lang/Class;
/*     */     //   55: invokeinterface put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
/*     */     //   60: pop
/*     */     //   61: aload #4
/*     */     //   63: ifnonnull -> 70
/*     */     //   66: aload #5
/*     */     //   68: astore #4
/*     */     //   70: aload_0
/*     */     //   71: aload_1
/*     */     //   72: aload #5
/*     */     //   74: invokestatic resolveTypeVariable : (Ljava/lang/reflect/Type;Ljava/lang/Class;Ljava/lang/reflect/TypeVariable;)Ljava/lang/reflect/Type;
/*     */     //   77: astore_2
/*     */     //   78: aload_2
/*     */     //   79: aload #5
/*     */     //   81: if_acmpne -> 87
/*     */     //   84: goto -> 482
/*     */     //   87: goto -> 3
/*     */     //   90: aload_2
/*     */     //   91: instanceof java/lang/Class
/*     */     //   94: ifeq -> 154
/*     */     //   97: aload_2
/*     */     //   98: checkcast java/lang/Class
/*     */     //   101: invokevirtual isArray : ()Z
/*     */     //   104: ifeq -> 154
/*     */     //   107: aload_2
/*     */     //   108: checkcast java/lang/Class
/*     */     //   111: astore #5
/*     */     //   113: aload #5
/*     */     //   115: invokevirtual getComponentType : ()Ljava/lang/Class;
/*     */     //   118: astore #6
/*     */     //   120: aload_0
/*     */     //   121: aload_1
/*     */     //   122: aload #6
/*     */     //   124: aload_3
/*     */     //   125: invokestatic resolve : (Ljava/lang/reflect/Type;Ljava/lang/Class;Ljava/lang/reflect/Type;Ljava/util/Map;)Ljava/lang/reflect/Type;
/*     */     //   128: astore #7
/*     */     //   130: aload #6
/*     */     //   132: aload #7
/*     */     //   134: invokestatic equal : (Ljava/lang/Object;Ljava/lang/Object;)Z
/*     */     //   137: ifeq -> 145
/*     */     //   140: aload #5
/*     */     //   142: goto -> 150
/*     */     //   145: aload #7
/*     */     //   147: invokestatic arrayOf : (Ljava/lang/reflect/Type;)Ljava/lang/reflect/GenericArrayType;
/*     */     //   150: astore_2
/*     */     //   151: goto -> 482
/*     */     //   154: aload_2
/*     */     //   155: instanceof java/lang/reflect/GenericArrayType
/*     */     //   158: ifeq -> 210
/*     */     //   161: aload_2
/*     */     //   162: checkcast java/lang/reflect/GenericArrayType
/*     */     //   165: astore #5
/*     */     //   167: aload #5
/*     */     //   169: invokeinterface getGenericComponentType : ()Ljava/lang/reflect/Type;
/*     */     //   174: astore #6
/*     */     //   176: aload_0
/*     */     //   177: aload_1
/*     */     //   178: aload #6
/*     */     //   180: aload_3
/*     */     //   181: invokestatic resolve : (Ljava/lang/reflect/Type;Ljava/lang/Class;Ljava/lang/reflect/Type;Ljava/util/Map;)Ljava/lang/reflect/Type;
/*     */     //   184: astore #7
/*     */     //   186: aload #6
/*     */     //   188: aload #7
/*     */     //   190: invokestatic equal : (Ljava/lang/Object;Ljava/lang/Object;)Z
/*     */     //   193: ifeq -> 201
/*     */     //   196: aload #5
/*     */     //   198: goto -> 206
/*     */     //   201: aload #7
/*     */     //   203: invokestatic arrayOf : (Ljava/lang/reflect/Type;)Ljava/lang/reflect/GenericArrayType;
/*     */     //   206: astore_2
/*     */     //   207: goto -> 482
/*     */     //   210: aload_2
/*     */     //   211: instanceof java/lang/reflect/ParameterizedType
/*     */     //   214: ifeq -> 368
/*     */     //   217: aload_2
/*     */     //   218: checkcast java/lang/reflect/ParameterizedType
/*     */     //   221: astore #5
/*     */     //   223: aload #5
/*     */     //   225: invokeinterface getOwnerType : ()Ljava/lang/reflect/Type;
/*     */     //   230: astore #6
/*     */     //   232: aload_0
/*     */     //   233: aload_1
/*     */     //   234: aload #6
/*     */     //   236: aload_3
/*     */     //   237: invokestatic resolve : (Ljava/lang/reflect/Type;Ljava/lang/Class;Ljava/lang/reflect/Type;Ljava/util/Map;)Ljava/lang/reflect/Type;
/*     */     //   240: astore #7
/*     */     //   242: aload #7
/*     */     //   244: aload #6
/*     */     //   246: invokestatic equal : (Ljava/lang/Object;Ljava/lang/Object;)Z
/*     */     //   249: ifne -> 256
/*     */     //   252: iconst_1
/*     */     //   253: goto -> 257
/*     */     //   256: iconst_0
/*     */     //   257: istore #8
/*     */     //   259: aload #5
/*     */     //   261: invokeinterface getActualTypeArguments : ()[Ljava/lang/reflect/Type;
/*     */     //   266: astore #9
/*     */     //   268: iconst_0
/*     */     //   269: istore #10
/*     */     //   271: aload #9
/*     */     //   273: arraylength
/*     */     //   274: istore #11
/*     */     //   276: iload #10
/*     */     //   278: iload #11
/*     */     //   280: if_icmpge -> 340
/*     */     //   283: aload_0
/*     */     //   284: aload_1
/*     */     //   285: aload #9
/*     */     //   287: iload #10
/*     */     //   289: aaload
/*     */     //   290: aload_3
/*     */     //   291: invokestatic resolve : (Ljava/lang/reflect/Type;Ljava/lang/Class;Ljava/lang/reflect/Type;Ljava/util/Map;)Ljava/lang/reflect/Type;
/*     */     //   294: astore #12
/*     */     //   296: aload #12
/*     */     //   298: aload #9
/*     */     //   300: iload #10
/*     */     //   302: aaload
/*     */     //   303: invokestatic equal : (Ljava/lang/Object;Ljava/lang/Object;)Z
/*     */     //   306: ifne -> 334
/*     */     //   309: iload #8
/*     */     //   311: ifne -> 327
/*     */     //   314: aload #9
/*     */     //   316: invokevirtual clone : ()Ljava/lang/Object;
/*     */     //   319: checkcast [Ljava/lang/reflect/Type;
/*     */     //   322: astore #9
/*     */     //   324: iconst_1
/*     */     //   325: istore #8
/*     */     //   327: aload #9
/*     */     //   329: iload #10
/*     */     //   331: aload #12
/*     */     //   333: aastore
/*     */     //   334: iinc #10, 1
/*     */     //   337: goto -> 276
/*     */     //   340: iload #8
/*     */     //   342: ifeq -> 362
/*     */     //   345: aload #7
/*     */     //   347: aload #5
/*     */     //   349: invokeinterface getRawType : ()Ljava/lang/reflect/Type;
/*     */     //   354: aload #9
/*     */     //   356: invokestatic newParameterizedTypeWithOwner : (Ljava/lang/reflect/Type;Ljava/lang/reflect/Type;[Ljava/lang/reflect/Type;)Ljava/lang/reflect/ParameterizedType;
/*     */     //   359: goto -> 364
/*     */     //   362: aload #5
/*     */     //   364: astore_2
/*     */     //   365: goto -> 482
/*     */     //   368: aload_2
/*     */     //   369: instanceof java/lang/reflect/WildcardType
/*     */     //   372: ifeq -> 482
/*     */     //   375: aload_2
/*     */     //   376: checkcast java/lang/reflect/WildcardType
/*     */     //   379: astore #5
/*     */     //   381: aload #5
/*     */     //   383: invokeinterface getLowerBounds : ()[Ljava/lang/reflect/Type;
/*     */     //   388: astore #6
/*     */     //   390: aload #5
/*     */     //   392: invokeinterface getUpperBounds : ()[Ljava/lang/reflect/Type;
/*     */     //   397: astore #7
/*     */     //   399: aload #6
/*     */     //   401: arraylength
/*     */     //   402: iconst_1
/*     */     //   403: if_icmpne -> 439
/*     */     //   406: aload_0
/*     */     //   407: aload_1
/*     */     //   408: aload #6
/*     */     //   410: iconst_0
/*     */     //   411: aaload
/*     */     //   412: aload_3
/*     */     //   413: invokestatic resolve : (Ljava/lang/reflect/Type;Ljava/lang/Class;Ljava/lang/reflect/Type;Ljava/util/Map;)Ljava/lang/reflect/Type;
/*     */     //   416: astore #8
/*     */     //   418: aload #8
/*     */     //   420: aload #6
/*     */     //   422: iconst_0
/*     */     //   423: aaload
/*     */     //   424: if_acmpeq -> 436
/*     */     //   427: aload #8
/*     */     //   429: invokestatic supertypeOf : (Ljava/lang/reflect/Type;)Ljava/lang/reflect/WildcardType;
/*     */     //   432: astore_2
/*     */     //   433: goto -> 482
/*     */     //   436: goto -> 476
/*     */     //   439: aload #7
/*     */     //   441: arraylength
/*     */     //   442: iconst_1
/*     */     //   443: if_icmpne -> 476
/*     */     //   446: aload_0
/*     */     //   447: aload_1
/*     */     //   448: aload #7
/*     */     //   450: iconst_0
/*     */     //   451: aaload
/*     */     //   452: aload_3
/*     */     //   453: invokestatic resolve : (Ljava/lang/reflect/Type;Ljava/lang/Class;Ljava/lang/reflect/Type;Ljava/util/Map;)Ljava/lang/reflect/Type;
/*     */     //   456: astore #8
/*     */     //   458: aload #8
/*     */     //   460: aload #7
/*     */     //   462: iconst_0
/*     */     //   463: aaload
/*     */     //   464: if_acmpeq -> 476
/*     */     //   467: aload #8
/*     */     //   469: invokestatic subtypeOf : (Ljava/lang/reflect/Type;)Ljava/lang/reflect/WildcardType;
/*     */     //   472: astore_2
/*     */     //   473: goto -> 482
/*     */     //   476: aload #5
/*     */     //   478: astore_2
/*     */     //   479: goto -> 482
/*     */     //   482: aload #4
/*     */     //   484: ifnull -> 497
/*     */     //   487: aload_3
/*     */     //   488: aload #4
/*     */     //   490: aload_2
/*     */     //   491: invokeinterface put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
/*     */     //   496: pop
/*     */     //   497: aload_2
/*     */     //   498: areturn
/*     */     // Line number table:
/*     */     //   Java source line number -> byte code offset
/*     */     //   #349	-> 0
/*     */     //   #351	-> 3
/*     */     //   #352	-> 10
/*     */     //   #353	-> 16
/*     */     //   #354	-> 29
/*     */     //   #356	-> 34
/*     */     //   #360	-> 49
/*     */     //   #361	-> 61
/*     */     //   #362	-> 66
/*     */     //   #365	-> 70
/*     */     //   #366	-> 78
/*     */     //   #367	-> 84
/*     */     //   #370	-> 87
/*     */     //   #371	-> 107
/*     */     //   #372	-> 113
/*     */     //   #373	-> 120
/*     */     //   #374	-> 130
/*     */     //   #375	-> 140
/*     */     //   #376	-> 145
/*     */     //   #377	-> 151
/*     */     //   #379	-> 154
/*     */     //   #380	-> 161
/*     */     //   #381	-> 167
/*     */     //   #382	-> 176
/*     */     //   #383	-> 186
/*     */     //   #384	-> 196
/*     */     //   #385	-> 201
/*     */     //   #386	-> 207
/*     */     //   #388	-> 210
/*     */     //   #389	-> 217
/*     */     //   #390	-> 223
/*     */     //   #391	-> 232
/*     */     //   #392	-> 242
/*     */     //   #394	-> 259
/*     */     //   #395	-> 268
/*     */     //   #396	-> 283
/*     */     //   #397	-> 296
/*     */     //   #398	-> 309
/*     */     //   #399	-> 314
/*     */     //   #400	-> 324
/*     */     //   #402	-> 327
/*     */     //   #395	-> 334
/*     */     //   #406	-> 340
/*     */     //   #407	-> 345
/*     */     //   #408	-> 362
/*     */     //   #409	-> 365
/*     */     //   #411	-> 368
/*     */     //   #412	-> 375
/*     */     //   #413	-> 381
/*     */     //   #414	-> 390
/*     */     //   #416	-> 399
/*     */     //   #417	-> 406
/*     */     //   #418	-> 418
/*     */     //   #419	-> 427
/*     */     //   #420	-> 433
/*     */     //   #422	-> 436
/*     */     //   #423	-> 446
/*     */     //   #424	-> 458
/*     */     //   #425	-> 467
/*     */     //   #426	-> 473
/*     */     //   #429	-> 476
/*     */     //   #430	-> 479
/*     */     //   #437	-> 482
/*     */     //   #438	-> 487
/*     */     //   #440	-> 497
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	descriptor
/*     */     //   16	71	5	typeVariable	Ljava/lang/reflect/TypeVariable;
/*     */     //   29	58	6	previouslyResolved	Ljava/lang/reflect/Type;
/*     */     //   113	41	5	original	Ljava/lang/Class;
/*     */     //   120	34	6	componentType	Ljava/lang/reflect/Type;
/*     */     //   130	24	7	newComponentType	Ljava/lang/reflect/Type;
/*     */     //   167	43	5	original	Ljava/lang/reflect/GenericArrayType;
/*     */     //   176	34	6	componentType	Ljava/lang/reflect/Type;
/*     */     //   186	24	7	newComponentType	Ljava/lang/reflect/Type;
/*     */     //   296	38	12	resolvedTypeArgument	Ljava/lang/reflect/Type;
/*     */     //   271	69	10	t	I
/*     */     //   276	64	11	length	I
/*     */     //   223	145	5	original	Ljava/lang/reflect/ParameterizedType;
/*     */     //   232	136	6	ownerType	Ljava/lang/reflect/Type;
/*     */     //   242	126	7	newOwnerType	Ljava/lang/reflect/Type;
/*     */     //   259	109	8	changed	Z
/*     */     //   268	100	9	args	[Ljava/lang/reflect/Type;
/*     */     //   418	18	8	lowerBound	Ljava/lang/reflect/Type;
/*     */     //   458	18	8	upperBound	Ljava/lang/reflect/Type;
/*     */     //   381	101	5	original	Ljava/lang/reflect/WildcardType;
/*     */     //   390	92	6	originalLowerBound	[Ljava/lang/reflect/Type;
/*     */     //   399	83	7	originalUpperBound	[Ljava/lang/reflect/Type;
/*     */     //   0	499	0	context	Ljava/lang/reflect/Type;
/*     */     //   0	499	1	contextRawType	Ljava/lang/Class;
/*     */     //   0	499	2	toResolve	Ljava/lang/reflect/Type;
/*     */     //   0	499	3	visitedTypeVariables	Ljava/util/Map;
/*     */     //   3	496	4	resolving	Ljava/lang/reflect/TypeVariable;
/*     */     // Local variable type table:
/*     */     //   start	length	slot	name	signature
/*     */     //   16	71	5	typeVariable	Ljava/lang/reflect/TypeVariable<*>;
/*     */     //   113	41	5	original	Ljava/lang/Class<*>;
/*     */     //   0	499	1	contextRawType	Ljava/lang/Class<*>;
/*     */     //   0	499	3	visitedTypeVariables	Ljava/util/Map<Ljava/lang/reflect/TypeVariable<*>;Ljava/lang/reflect/Type;>;
/*     */     //   3	496	4	resolving	Ljava/lang/reflect/TypeVariable<*>;
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
/*     */   private static Type resolveTypeVariable(Type context, Class<?> contextRawType, TypeVariable<?> unknown) {
/* 444 */     Class<?> declaredByRaw = declaringClassOf(unknown);
/*     */ 
/*     */     
/* 447 */     if (declaredByRaw == null) {
/* 448 */       return unknown;
/*     */     }
/*     */     
/* 451 */     Type declaredBy = getGenericSupertype(context, contextRawType, declaredByRaw);
/* 452 */     if (declaredBy instanceof ParameterizedType) {
/* 453 */       int index = indexOf((Object[])declaredByRaw.getTypeParameters(), unknown);
/* 454 */       return ((ParameterizedType)declaredBy).getActualTypeArguments()[index];
/*     */     } 
/*     */     
/* 457 */     return unknown;
/*     */   }
/*     */   
/*     */   private static int indexOf(Object[] array, Object toFind) {
/* 461 */     for (int i = 0, length = array.length; i < length; i++) {
/* 462 */       if (toFind.equals(array[i])) {
/* 463 */         return i;
/*     */       }
/*     */     } 
/* 466 */     throw new NoSuchElementException();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static Class<?> declaringClassOf(TypeVariable<?> typeVariable) {
/* 474 */     GenericDeclaration genericDeclaration = (GenericDeclaration)typeVariable.getGenericDeclaration();
/* 475 */     return (genericDeclaration instanceof Class) ? 
/* 476 */       (Class)genericDeclaration : 
/* 477 */       null;
/*     */   }
/*     */   
/*     */   static void checkNotPrimitive(Type type) {
/* 481 */     $Gson$Preconditions.checkArgument((!(type instanceof Class) || !((Class)type).isPrimitive()));
/*     */   }
/*     */   
/*     */   private static final class ParameterizedTypeImpl
/*     */     implements ParameterizedType, Serializable {
/*     */     private final Type ownerType;
/*     */     private final Type rawType;
/*     */     
/*     */     public ParameterizedTypeImpl(Type ownerType, Type rawType, Type... typeArguments) {
/* 490 */       Objects.requireNonNull(rawType);
/*     */       
/* 492 */       if (rawType instanceof Class) {
/* 493 */         Class<?> rawTypeAsClass = (Class)rawType;
/*     */         
/* 495 */         boolean isStaticOrTopLevelClass = (Modifier.isStatic(rawTypeAsClass.getModifiers()) || rawTypeAsClass.getEnclosingClass() == null);
/* 496 */         $Gson$Preconditions.checkArgument((ownerType != null || isStaticOrTopLevelClass));
/*     */       } 
/*     */       
/* 499 */       this.ownerType = (ownerType == null) ? null : $Gson$Types.canonicalize(ownerType);
/* 500 */       this.rawType = $Gson$Types.canonicalize(rawType);
/* 501 */       this.typeArguments = (Type[])typeArguments.clone();
/* 502 */       for (int t = 0, length = this.typeArguments.length; t < length; t++) {
/* 503 */         Objects.requireNonNull(this.typeArguments[t]);
/* 504 */         $Gson$Types.checkNotPrimitive(this.typeArguments[t]);
/* 505 */         this.typeArguments[t] = $Gson$Types.canonicalize(this.typeArguments[t]);
/*     */       } 
/*     */     }
/*     */     private final Type[] typeArguments; private static final long serialVersionUID = 0L;
/*     */     public Type[] getActualTypeArguments() {
/* 510 */       return (Type[])this.typeArguments.clone();
/*     */     }
/*     */     
/*     */     public Type getRawType() {
/* 514 */       return this.rawType;
/*     */     }
/*     */     
/*     */     public Type getOwnerType() {
/* 518 */       return this.ownerType;
/*     */     }
/*     */     
/*     */     public boolean equals(Object other) {
/* 522 */       return (other instanceof ParameterizedType && 
/* 523 */         $Gson$Types.equals(this, (ParameterizedType)other));
/*     */     }
/*     */     
/*     */     private static int hashCodeOrZero(Object o) {
/* 527 */       return (o != null) ? o.hashCode() : 0;
/*     */     }
/*     */     
/*     */     public int hashCode() {
/* 531 */       return Arrays.hashCode((Object[])this.typeArguments) ^ this.rawType
/* 532 */         .hashCode() ^ 
/* 533 */         hashCodeOrZero(this.ownerType);
/*     */     }
/*     */     
/*     */     public String toString() {
/* 537 */       int length = this.typeArguments.length;
/* 538 */       if (length == 0) {
/* 539 */         return $Gson$Types.typeToString(this.rawType);
/*     */       }
/*     */       
/* 542 */       StringBuilder stringBuilder = new StringBuilder(30 * (length + 1));
/* 543 */       stringBuilder.append($Gson$Types.typeToString(this.rawType)).append("<").append($Gson$Types.typeToString(this.typeArguments[0]));
/* 544 */       for (int i = 1; i < length; i++) {
/* 545 */         stringBuilder.append(", ").append($Gson$Types.typeToString(this.typeArguments[i]));
/*     */       }
/* 547 */       return stringBuilder.append(">").toString();
/*     */     }
/*     */   }
/*     */   
/*     */   private static final class GenericArrayTypeImpl
/*     */     implements GenericArrayType, Serializable {
/*     */     private final Type componentType;
/*     */     private static final long serialVersionUID = 0L;
/*     */     
/*     */     public GenericArrayTypeImpl(Type componentType) {
/* 557 */       Objects.requireNonNull(componentType);
/* 558 */       this.componentType = $Gson$Types.canonicalize(componentType);
/*     */     }
/*     */     
/*     */     public Type getGenericComponentType() {
/* 562 */       return this.componentType;
/*     */     }
/*     */     
/*     */     public boolean equals(Object o) {
/* 566 */       return (o instanceof GenericArrayType && 
/* 567 */         $Gson$Types.equals(this, (GenericArrayType)o));
/*     */     }
/*     */     
/*     */     public int hashCode() {
/* 571 */       return this.componentType.hashCode();
/*     */     }
/*     */     
/*     */     public String toString() {
/* 575 */       return $Gson$Types.typeToString(this.componentType) + "[]";
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static final class WildcardTypeImpl
/*     */     implements WildcardType, Serializable
/*     */   {
/*     */     private final Type upperBound;
/*     */     
/*     */     private final Type lowerBound;
/*     */     
/*     */     private static final long serialVersionUID = 0L;
/*     */ 
/*     */     
/*     */     public WildcardTypeImpl(Type[] upperBounds, Type[] lowerBounds) {
/* 592 */       $Gson$Preconditions.checkArgument((lowerBounds.length <= 1));
/* 593 */       $Gson$Preconditions.checkArgument((upperBounds.length == 1));
/*     */       
/* 595 */       if (lowerBounds.length == 1) {
/* 596 */         Objects.requireNonNull(lowerBounds[0]);
/* 597 */         $Gson$Types.checkNotPrimitive(lowerBounds[0]);
/* 598 */         $Gson$Preconditions.checkArgument((upperBounds[0] == Object.class));
/* 599 */         this.lowerBound = $Gson$Types.canonicalize(lowerBounds[0]);
/* 600 */         this.upperBound = Object.class;
/*     */       } else {
/*     */         
/* 603 */         Objects.requireNonNull(upperBounds[0]);
/* 604 */         $Gson$Types.checkNotPrimitive(upperBounds[0]);
/* 605 */         this.lowerBound = null;
/* 606 */         this.upperBound = $Gson$Types.canonicalize(upperBounds[0]);
/*     */       } 
/*     */     }
/*     */     
/*     */     public Type[] getUpperBounds() {
/* 611 */       return new Type[] { this.upperBound };
/*     */     }
/*     */     
/*     */     public Type[] getLowerBounds() {
/* 615 */       (new Type[1])[0] = this.lowerBound; return (this.lowerBound != null) ? new Type[1] : $Gson$Types.EMPTY_TYPE_ARRAY;
/*     */     }
/*     */     
/*     */     public boolean equals(Object other) {
/* 619 */       return (other instanceof WildcardType && 
/* 620 */         $Gson$Types.equals(this, (WildcardType)other));
/*     */     }
/*     */ 
/*     */     
/*     */     public int hashCode() {
/* 625 */       return ((this.lowerBound != null) ? (31 + this.lowerBound.hashCode()) : 1) ^ 31 + this.upperBound
/* 626 */         .hashCode();
/*     */     }
/*     */     
/*     */     public String toString() {
/* 630 */       if (this.lowerBound != null)
/* 631 */         return "? super " + $Gson$Types.typeToString(this.lowerBound); 
/* 632 */       if (this.upperBound == Object.class) {
/* 633 */         return "?";
/*     */       }
/* 635 */       return "? extends " + $Gson$Types.typeToString(this.upperBound);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\libs\gson\internal\$Gson$Types.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */