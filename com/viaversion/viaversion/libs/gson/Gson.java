/*      */ package com.viaversion.viaversion.libs.gson;
/*      */ 
/*      */ import com.viaversion.viaversion.libs.gson.internal.ConstructorConstructor;
/*      */ import com.viaversion.viaversion.libs.gson.internal.Excluder;
/*      */ import com.viaversion.viaversion.libs.gson.internal.LazilyParsedNumber;
/*      */ import com.viaversion.viaversion.libs.gson.internal.Primitives;
/*      */ import com.viaversion.viaversion.libs.gson.internal.Streams;
/*      */ import com.viaversion.viaversion.libs.gson.internal.bind.ArrayTypeAdapter;
/*      */ import com.viaversion.viaversion.libs.gson.internal.bind.CollectionTypeAdapterFactory;
/*      */ import com.viaversion.viaversion.libs.gson.internal.bind.DateTypeAdapter;
/*      */ import com.viaversion.viaversion.libs.gson.internal.bind.JsonAdapterAnnotationTypeAdapterFactory;
/*      */ import com.viaversion.viaversion.libs.gson.internal.bind.JsonTreeReader;
/*      */ import com.viaversion.viaversion.libs.gson.internal.bind.JsonTreeWriter;
/*      */ import com.viaversion.viaversion.libs.gson.internal.bind.MapTypeAdapterFactory;
/*      */ import com.viaversion.viaversion.libs.gson.internal.bind.NumberTypeAdapter;
/*      */ import com.viaversion.viaversion.libs.gson.internal.bind.ObjectTypeAdapter;
/*      */ import com.viaversion.viaversion.libs.gson.internal.bind.ReflectiveTypeAdapterFactory;
/*      */ import com.viaversion.viaversion.libs.gson.internal.bind.SerializationDelegatingTypeAdapter;
/*      */ import com.viaversion.viaversion.libs.gson.internal.bind.TypeAdapters;
/*      */ import com.viaversion.viaversion.libs.gson.internal.sql.SqlTypesSupport;
/*      */ import com.viaversion.viaversion.libs.gson.reflect.TypeToken;
/*      */ import com.viaversion.viaversion.libs.gson.stream.JsonReader;
/*      */ import com.viaversion.viaversion.libs.gson.stream.JsonToken;
/*      */ import com.viaversion.viaversion.libs.gson.stream.JsonWriter;
/*      */ import com.viaversion.viaversion.libs.gson.stream.MalformedJsonException;
/*      */ import java.io.EOFException;
/*      */ import java.io.IOException;
/*      */ import java.io.Reader;
/*      */ import java.io.StringReader;
/*      */ import java.io.StringWriter;
/*      */ import java.io.Writer;
/*      */ import java.lang.reflect.Type;
/*      */ import java.math.BigDecimal;
/*      */ import java.math.BigInteger;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Collections;
/*      */ import java.util.HashMap;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.Objects;
/*      */ import java.util.concurrent.ConcurrentHashMap;
/*      */ import java.util.concurrent.ConcurrentMap;
/*      */ import java.util.concurrent.atomic.AtomicLong;
/*      */ import java.util.concurrent.atomic.AtomicLongArray;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public final class Gson
/*      */ {
/*      */   static final boolean DEFAULT_JSON_NON_EXECUTABLE = false;
/*      */   static final boolean DEFAULT_LENIENT = false;
/*      */   static final boolean DEFAULT_PRETTY_PRINT = false;
/*      */   static final boolean DEFAULT_ESCAPE_HTML = true;
/*      */   static final boolean DEFAULT_SERIALIZE_NULLS = false;
/*      */   static final boolean DEFAULT_COMPLEX_MAP_KEYS = false;
/*      */   static final boolean DEFAULT_SPECIALIZE_FLOAT_VALUES = false;
/*      */   static final boolean DEFAULT_USE_JDK_UNSAFE = true;
/*  150 */   static final String DEFAULT_DATE_PATTERN = null;
/*  151 */   static final FieldNamingStrategy DEFAULT_FIELD_NAMING_STRATEGY = FieldNamingPolicy.IDENTITY;
/*  152 */   static final ToNumberStrategy DEFAULT_OBJECT_TO_NUMBER_STRATEGY = ToNumberPolicy.DOUBLE;
/*  153 */   static final ToNumberStrategy DEFAULT_NUMBER_TO_NUMBER_STRATEGY = ToNumberPolicy.LAZILY_PARSED_NUMBER;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static final String JSON_NON_EXECUTABLE_PREFIX = ")]}'\n";
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  169 */   private final ThreadLocal<Map<TypeToken<?>, TypeAdapter<?>>> threadLocalAdapterResults = new ThreadLocal<>();
/*      */   
/*  171 */   private final ConcurrentMap<TypeToken<?>, TypeAdapter<?>> typeTokenCache = new ConcurrentHashMap<>();
/*      */ 
/*      */   
/*      */   private final ConstructorConstructor constructorConstructor;
/*      */ 
/*      */   
/*      */   private final JsonAdapterAnnotationTypeAdapterFactory jsonAdapterFactory;
/*      */ 
/*      */   
/*      */   final List<TypeAdapterFactory> factories;
/*      */ 
/*      */   
/*      */   final Excluder excluder;
/*      */ 
/*      */   
/*      */   final FieldNamingStrategy fieldNamingStrategy;
/*      */ 
/*      */   
/*      */   final Map<Type, InstanceCreator<?>> instanceCreators;
/*      */ 
/*      */   
/*      */   final boolean serializeNulls;
/*      */ 
/*      */   
/*      */   final boolean complexMapKeySerialization;
/*      */ 
/*      */   
/*      */   final boolean generateNonExecutableJson;
/*      */ 
/*      */   
/*      */   final boolean htmlSafe;
/*      */ 
/*      */   
/*      */   final boolean prettyPrinting;
/*      */ 
/*      */   
/*      */   final boolean lenient;
/*      */ 
/*      */   
/*      */   final boolean serializeSpecialFloatingPointValues;
/*      */   
/*      */   final boolean useJdkUnsafe;
/*      */   
/*      */   final String datePattern;
/*      */   
/*      */   final int dateStyle;
/*      */   
/*      */   final int timeStyle;
/*      */   
/*      */   final LongSerializationPolicy longSerializationPolicy;
/*      */   
/*      */   final List<TypeAdapterFactory> builderFactories;
/*      */   
/*      */   final List<TypeAdapterFactory> builderHierarchyFactories;
/*      */   
/*      */   final ToNumberStrategy objectToNumberStrategy;
/*      */   
/*      */   final ToNumberStrategy numberToNumberStrategy;
/*      */   
/*      */   final List<ReflectionAccessFilter> reflectionFilters;
/*      */ 
/*      */   
/*      */   public Gson() {
/*  234 */     this(Excluder.DEFAULT, DEFAULT_FIELD_NAMING_STRATEGY, 
/*  235 */         Collections.emptyMap(), false, false, false, true, false, false, false, true, LongSerializationPolicy.DEFAULT, DEFAULT_DATE_PATTERN, 2, 2, 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  240 */         Collections.emptyList(), Collections.emptyList(), 
/*  241 */         Collections.emptyList(), DEFAULT_OBJECT_TO_NUMBER_STRATEGY, DEFAULT_NUMBER_TO_NUMBER_STRATEGY, 
/*  242 */         Collections.emptyList());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   Gson(Excluder excluder, FieldNamingStrategy fieldNamingStrategy, Map<Type, InstanceCreator<?>> instanceCreators, boolean serializeNulls, boolean complexMapKeySerialization, boolean generateNonExecutableGson, boolean htmlSafe, boolean prettyPrinting, boolean lenient, boolean serializeSpecialFloatingPointValues, boolean useJdkUnsafe, LongSerializationPolicy longSerializationPolicy, String datePattern, int dateStyle, int timeStyle, List<TypeAdapterFactory> builderFactories, List<TypeAdapterFactory> builderHierarchyFactories, List<TypeAdapterFactory> factoriesToBeAdded, ToNumberStrategy objectToNumberStrategy, ToNumberStrategy numberToNumberStrategy, List<ReflectionAccessFilter> reflectionFilters) {
/*  256 */     this.excluder = excluder;
/*  257 */     this.fieldNamingStrategy = fieldNamingStrategy;
/*  258 */     this.instanceCreators = instanceCreators;
/*  259 */     this.constructorConstructor = new ConstructorConstructor(instanceCreators, useJdkUnsafe, reflectionFilters);
/*  260 */     this.serializeNulls = serializeNulls;
/*  261 */     this.complexMapKeySerialization = complexMapKeySerialization;
/*  262 */     this.generateNonExecutableJson = generateNonExecutableGson;
/*  263 */     this.htmlSafe = htmlSafe;
/*  264 */     this.prettyPrinting = prettyPrinting;
/*  265 */     this.lenient = lenient;
/*  266 */     this.serializeSpecialFloatingPointValues = serializeSpecialFloatingPointValues;
/*  267 */     this.useJdkUnsafe = useJdkUnsafe;
/*  268 */     this.longSerializationPolicy = longSerializationPolicy;
/*  269 */     this.datePattern = datePattern;
/*  270 */     this.dateStyle = dateStyle;
/*  271 */     this.timeStyle = timeStyle;
/*  272 */     this.builderFactories = builderFactories;
/*  273 */     this.builderHierarchyFactories = builderHierarchyFactories;
/*  274 */     this.objectToNumberStrategy = objectToNumberStrategy;
/*  275 */     this.numberToNumberStrategy = numberToNumberStrategy;
/*  276 */     this.reflectionFilters = reflectionFilters;
/*      */     
/*  278 */     List<TypeAdapterFactory> factories = new ArrayList<>();
/*      */ 
/*      */     
/*  281 */     factories.add(TypeAdapters.JSON_ELEMENT_FACTORY);
/*  282 */     factories.add(ObjectTypeAdapter.getFactory(objectToNumberStrategy));
/*      */ 
/*      */     
/*  285 */     factories.add(excluder);
/*      */ 
/*      */     
/*  288 */     factories.addAll(factoriesToBeAdded);
/*      */ 
/*      */     
/*  291 */     factories.add(TypeAdapters.STRING_FACTORY);
/*  292 */     factories.add(TypeAdapters.INTEGER_FACTORY);
/*  293 */     factories.add(TypeAdapters.BOOLEAN_FACTORY);
/*  294 */     factories.add(TypeAdapters.BYTE_FACTORY);
/*  295 */     factories.add(TypeAdapters.SHORT_FACTORY);
/*  296 */     TypeAdapter<Number> longAdapter = longAdapter(longSerializationPolicy);
/*  297 */     factories.add(TypeAdapters.newFactory(long.class, Long.class, longAdapter));
/*  298 */     factories.add(TypeAdapters.newFactory(double.class, Double.class, 
/*  299 */           doubleAdapter(serializeSpecialFloatingPointValues)));
/*  300 */     factories.add(TypeAdapters.newFactory(float.class, Float.class, 
/*  301 */           floatAdapter(serializeSpecialFloatingPointValues)));
/*  302 */     factories.add(NumberTypeAdapter.getFactory(numberToNumberStrategy));
/*  303 */     factories.add(TypeAdapters.ATOMIC_INTEGER_FACTORY);
/*  304 */     factories.add(TypeAdapters.ATOMIC_BOOLEAN_FACTORY);
/*  305 */     factories.add(TypeAdapters.newFactory(AtomicLong.class, atomicLongAdapter(longAdapter)));
/*  306 */     factories.add(TypeAdapters.newFactory(AtomicLongArray.class, atomicLongArrayAdapter(longAdapter)));
/*  307 */     factories.add(TypeAdapters.ATOMIC_INTEGER_ARRAY_FACTORY);
/*  308 */     factories.add(TypeAdapters.CHARACTER_FACTORY);
/*  309 */     factories.add(TypeAdapters.STRING_BUILDER_FACTORY);
/*  310 */     factories.add(TypeAdapters.STRING_BUFFER_FACTORY);
/*  311 */     factories.add(TypeAdapters.newFactory(BigDecimal.class, TypeAdapters.BIG_DECIMAL));
/*  312 */     factories.add(TypeAdapters.newFactory(BigInteger.class, TypeAdapters.BIG_INTEGER));
/*      */     
/*  314 */     factories.add(TypeAdapters.newFactory(LazilyParsedNumber.class, TypeAdapters.LAZILY_PARSED_NUMBER));
/*  315 */     factories.add(TypeAdapters.URL_FACTORY);
/*  316 */     factories.add(TypeAdapters.URI_FACTORY);
/*  317 */     factories.add(TypeAdapters.UUID_FACTORY);
/*  318 */     factories.add(TypeAdapters.CURRENCY_FACTORY);
/*  319 */     factories.add(TypeAdapters.LOCALE_FACTORY);
/*  320 */     factories.add(TypeAdapters.INET_ADDRESS_FACTORY);
/*  321 */     factories.add(TypeAdapters.BIT_SET_FACTORY);
/*  322 */     factories.add(DateTypeAdapter.FACTORY);
/*  323 */     factories.add(TypeAdapters.CALENDAR_FACTORY);
/*      */     
/*  325 */     if (SqlTypesSupport.SUPPORTS_SQL_TYPES) {
/*  326 */       factories.add(SqlTypesSupport.TIME_FACTORY);
/*  327 */       factories.add(SqlTypesSupport.DATE_FACTORY);
/*  328 */       factories.add(SqlTypesSupport.TIMESTAMP_FACTORY);
/*      */     } 
/*      */     
/*  331 */     factories.add(ArrayTypeAdapter.FACTORY);
/*  332 */     factories.add(TypeAdapters.CLASS_FACTORY);
/*      */ 
/*      */     
/*  335 */     factories.add(new CollectionTypeAdapterFactory(this.constructorConstructor));
/*  336 */     factories.add(new MapTypeAdapterFactory(this.constructorConstructor, complexMapKeySerialization));
/*  337 */     this.jsonAdapterFactory = new JsonAdapterAnnotationTypeAdapterFactory(this.constructorConstructor);
/*  338 */     factories.add(this.jsonAdapterFactory);
/*  339 */     factories.add(TypeAdapters.ENUM_FACTORY);
/*  340 */     factories.add(new ReflectiveTypeAdapterFactory(this.constructorConstructor, fieldNamingStrategy, excluder, this.jsonAdapterFactory, reflectionFilters));
/*      */ 
/*      */     
/*  343 */     this.factories = Collections.unmodifiableList(factories);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public GsonBuilder newBuilder() {
/*  354 */     return new GsonBuilder(this);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public Excluder excluder() {
/*  363 */     return this.excluder;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public FieldNamingStrategy fieldNamingStrategy() {
/*  372 */     return this.fieldNamingStrategy;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean serializeNulls() {
/*  382 */     return this.serializeNulls;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean htmlSafe() {
/*  392 */     return this.htmlSafe;
/*      */   }
/*      */   
/*      */   private TypeAdapter<Number> doubleAdapter(boolean serializeSpecialFloatingPointValues) {
/*  396 */     if (serializeSpecialFloatingPointValues) {
/*  397 */       return TypeAdapters.DOUBLE;
/*      */     }
/*  399 */     return new TypeAdapter<Number>() {
/*      */         public Double read(JsonReader in) throws IOException {
/*  401 */           if (in.peek() == JsonToken.NULL) {
/*  402 */             in.nextNull();
/*  403 */             return null;
/*      */           } 
/*  405 */           return Double.valueOf(in.nextDouble());
/*      */         }
/*      */         public void write(JsonWriter out, Number value) throws IOException {
/*  408 */           if (value == null) {
/*  409 */             out.nullValue();
/*      */             return;
/*      */           } 
/*  412 */           double doubleValue = value.doubleValue();
/*  413 */           Gson.checkValidFloatingPoint(doubleValue);
/*  414 */           out.value(doubleValue);
/*      */         }
/*      */       };
/*      */   }
/*      */   
/*      */   private TypeAdapter<Number> floatAdapter(boolean serializeSpecialFloatingPointValues) {
/*  420 */     if (serializeSpecialFloatingPointValues) {
/*  421 */       return TypeAdapters.FLOAT;
/*      */     }
/*  423 */     return new TypeAdapter<Number>() {
/*      */         public Float read(JsonReader in) throws IOException {
/*  425 */           if (in.peek() == JsonToken.NULL) {
/*  426 */             in.nextNull();
/*  427 */             return null;
/*      */           } 
/*  429 */           return Float.valueOf((float)in.nextDouble());
/*      */         }
/*      */         public void write(JsonWriter out, Number value) throws IOException {
/*  432 */           if (value == null) {
/*  433 */             out.nullValue();
/*      */             return;
/*      */           } 
/*  436 */           float floatValue = value.floatValue();
/*  437 */           Gson.checkValidFloatingPoint(floatValue);
/*      */ 
/*      */           
/*  440 */           Number floatNumber = (value instanceof Float) ? value : Float.valueOf(floatValue);
/*  441 */           out.value(floatNumber);
/*      */         }
/*      */       };
/*      */   }
/*      */   
/*      */   static void checkValidFloatingPoint(double value) {
/*  447 */     if (Double.isNaN(value) || Double.isInfinite(value)) {
/*  448 */       throw new IllegalArgumentException(value + " is not a valid double value as per JSON specification. To override this behavior, use GsonBuilder.serializeSpecialFloatingPointValues() method.");
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static TypeAdapter<Number> longAdapter(LongSerializationPolicy longSerializationPolicy) {
/*  455 */     if (longSerializationPolicy == LongSerializationPolicy.DEFAULT) {
/*  456 */       return TypeAdapters.LONG;
/*      */     }
/*  458 */     return new TypeAdapter<Number>() {
/*      */         public Number read(JsonReader in) throws IOException {
/*  460 */           if (in.peek() == JsonToken.NULL) {
/*  461 */             in.nextNull();
/*  462 */             return null;
/*      */           } 
/*  464 */           return Long.valueOf(in.nextLong());
/*      */         }
/*      */         public void write(JsonWriter out, Number value) throws IOException {
/*  467 */           if (value == null) {
/*  468 */             out.nullValue();
/*      */             return;
/*      */           } 
/*  471 */           out.value(value.toString());
/*      */         }
/*      */       };
/*      */   }
/*      */   
/*      */   private static TypeAdapter<AtomicLong> atomicLongAdapter(final TypeAdapter<Number> longAdapter) {
/*  477 */     return (new TypeAdapter<AtomicLong>() {
/*      */         public void write(JsonWriter out, AtomicLong value) throws IOException {
/*  479 */           longAdapter.write(out, Long.valueOf(value.get()));
/*      */         }
/*      */         public AtomicLong read(JsonReader in) throws IOException {
/*  482 */           Number value = longAdapter.read(in);
/*  483 */           return new AtomicLong(value.longValue());
/*      */         }
/*  485 */       }).nullSafe();
/*      */   }
/*      */   
/*      */   private static TypeAdapter<AtomicLongArray> atomicLongArrayAdapter(final TypeAdapter<Number> longAdapter) {
/*  489 */     return (new TypeAdapter<AtomicLongArray>() {
/*      */         public void write(JsonWriter out, AtomicLongArray value) throws IOException {
/*  491 */           out.beginArray();
/*  492 */           for (int i = 0, length = value.length(); i < length; i++) {
/*  493 */             longAdapter.write(out, Long.valueOf(value.get(i)));
/*      */           }
/*  495 */           out.endArray();
/*      */         }
/*      */         public AtomicLongArray read(JsonReader in) throws IOException {
/*  498 */           List<Long> list = new ArrayList<>();
/*  499 */           in.beginArray();
/*  500 */           while (in.hasNext()) {
/*  501 */             long value = ((Number)longAdapter.read(in)).longValue();
/*  502 */             list.add(Long.valueOf(value));
/*      */           } 
/*  504 */           in.endArray();
/*  505 */           int length = list.size();
/*  506 */           AtomicLongArray array = new AtomicLongArray(length);
/*  507 */           for (int i = 0; i < length; i++) {
/*  508 */             array.set(i, ((Long)list.get(i)).longValue());
/*      */           }
/*  510 */           return array;
/*      */         }
/*  512 */       }).nullSafe();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public <T> TypeAdapter<T> getAdapter(TypeToken<T> type) {
/*  527 */     Objects.requireNonNull(type, "type must not be null");
/*  528 */     TypeAdapter<?> cached = this.typeTokenCache.get(type);
/*  529 */     if (cached != null) {
/*      */       
/*  531 */       TypeAdapter<T> adapter = (TypeAdapter)cached;
/*  532 */       return adapter;
/*      */     } 
/*      */     
/*  535 */     Map<TypeToken<?>, TypeAdapter<?>> threadCalls = this.threadLocalAdapterResults.get();
/*  536 */     boolean isInitialAdapterRequest = false;
/*  537 */     if (threadCalls == null) {
/*  538 */       threadCalls = new HashMap<>();
/*  539 */       this.threadLocalAdapterResults.set(threadCalls);
/*  540 */       isInitialAdapterRequest = true;
/*      */     }
/*      */     else {
/*      */       
/*  544 */       TypeAdapter<T> ongoingCall = (TypeAdapter<T>)threadCalls.get(type);
/*  545 */       if (ongoingCall != null) {
/*  546 */         return ongoingCall;
/*      */       }
/*      */     } 
/*      */     
/*  550 */     TypeAdapter<T> candidate = null;
/*      */     try {
/*  552 */       FutureTypeAdapter<T> call = new FutureTypeAdapter<>();
/*  553 */       threadCalls.put(type, call);
/*      */       
/*  555 */       for (TypeAdapterFactory factory : this.factories) {
/*  556 */         candidate = factory.create(this, type);
/*  557 */         if (candidate != null) {
/*  558 */           call.setDelegate(candidate);
/*      */           
/*  560 */           threadCalls.put(type, candidate);
/*      */           break;
/*      */         } 
/*      */       } 
/*      */     } finally {
/*  565 */       if (isInitialAdapterRequest) {
/*  566 */         this.threadLocalAdapterResults.remove();
/*      */       }
/*      */     } 
/*      */     
/*  570 */     if (candidate == null) {
/*  571 */       throw new IllegalArgumentException("GSON (2.10.1) cannot handle " + type);
/*      */     }
/*      */     
/*  574 */     if (isInitialAdapterRequest)
/*      */     {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  581 */       this.typeTokenCache.putAll(threadCalls);
/*      */     }
/*  583 */     return candidate;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public <T> TypeAdapter<T> getDelegateAdapter(TypeAdapterFactory skipPast, TypeToken<T> type) {
/*      */     JsonAdapterAnnotationTypeAdapterFactory jsonAdapterAnnotationTypeAdapterFactory;
/*  639 */     if (!this.factories.contains(skipPast)) {
/*  640 */       jsonAdapterAnnotationTypeAdapterFactory = this.jsonAdapterFactory;
/*      */     }
/*      */     
/*  643 */     boolean skipPastFound = false;
/*  644 */     for (TypeAdapterFactory factory : this.factories) {
/*  645 */       if (!skipPastFound) {
/*  646 */         if (factory == jsonAdapterAnnotationTypeAdapterFactory) {
/*  647 */           skipPastFound = true;
/*      */         }
/*      */         
/*      */         continue;
/*      */       } 
/*  652 */       TypeAdapter<T> candidate = factory.create(this, type);
/*  653 */       if (candidate != null) {
/*  654 */         return candidate;
/*      */       }
/*      */     } 
/*  657 */     throw new IllegalArgumentException("GSON cannot serialize " + type);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public <T> TypeAdapter<T> getAdapter(Class<T> type) {
/*  667 */     return getAdapter(TypeToken.get(type));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public JsonElement toJsonTree(Object src) {
/*  686 */     if (src == null) {
/*  687 */       return JsonNull.INSTANCE;
/*      */     }
/*  689 */     return toJsonTree(src, src.getClass());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public JsonElement toJsonTree(Object src, Type typeOfSrc) {
/*  711 */     JsonTreeWriter writer = new JsonTreeWriter();
/*  712 */     toJson(src, typeOfSrc, (JsonWriter)writer);
/*  713 */     return writer.get();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String toJson(Object src) {
/*  733 */     if (src == null) {
/*  734 */       return toJson(JsonNull.INSTANCE);
/*      */     }
/*  736 */     return toJson(src, src.getClass());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String toJson(Object src, Type typeOfSrc) {
/*  758 */     StringWriter writer = new StringWriter();
/*  759 */     toJson(src, typeOfSrc, writer);
/*  760 */     return writer.toString();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void toJson(Object src, Appendable writer) throws JsonIOException {
/*  782 */     if (src != null) {
/*  783 */       toJson(src, src.getClass(), writer);
/*      */     } else {
/*  785 */       toJson(JsonNull.INSTANCE, writer);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void toJson(Object src, Type typeOfSrc, Appendable writer) throws JsonIOException {
/*      */     try {
/*  811 */       JsonWriter jsonWriter = newJsonWriter(Streams.writerForAppendable(writer));
/*  812 */       toJson(src, typeOfSrc, jsonWriter);
/*  813 */     } catch (IOException e) {
/*  814 */       throw new JsonIOException(e);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void toJson(Object src, Type typeOfSrc, JsonWriter writer) throws JsonIOException {
/*  834 */     TypeAdapter<Object> adapter = getAdapter(TypeToken.get(typeOfSrc));
/*  835 */     boolean oldLenient = writer.isLenient();
/*  836 */     writer.setLenient(true);
/*  837 */     boolean oldHtmlSafe = writer.isHtmlSafe();
/*  838 */     writer.setHtmlSafe(this.htmlSafe);
/*  839 */     boolean oldSerializeNulls = writer.getSerializeNulls();
/*  840 */     writer.setSerializeNulls(this.serializeNulls);
/*      */     try {
/*  842 */       adapter.write(writer, src);
/*  843 */     } catch (IOException e) {
/*  844 */       throw new JsonIOException(e);
/*  845 */     } catch (AssertionError e) {
/*  846 */       throw new AssertionError("AssertionError (GSON 2.10.1): " + e.getMessage(), e);
/*      */     } finally {
/*  848 */       writer.setLenient(oldLenient);
/*  849 */       writer.setHtmlSafe(oldHtmlSafe);
/*  850 */       writer.setSerializeNulls(oldSerializeNulls);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String toJson(JsonElement jsonElement) {
/*  862 */     StringWriter writer = new StringWriter();
/*  863 */     toJson(jsonElement, writer);
/*  864 */     return writer.toString();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void toJson(JsonElement jsonElement, Appendable writer) throws JsonIOException {
/*      */     try {
/*  877 */       JsonWriter jsonWriter = newJsonWriter(Streams.writerForAppendable(writer));
/*  878 */       toJson(jsonElement, jsonWriter);
/*  879 */     } catch (IOException e) {
/*  880 */       throw new JsonIOException(e);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public JsonWriter newJsonWriter(Writer writer) throws IOException {
/*  897 */     if (this.generateNonExecutableJson) {
/*  898 */       writer.write(")]}'\n");
/*      */     }
/*  900 */     JsonWriter jsonWriter = new JsonWriter(writer);
/*  901 */     if (this.prettyPrinting) {
/*  902 */       jsonWriter.setIndent("  ");
/*      */     }
/*  904 */     jsonWriter.setHtmlSafe(this.htmlSafe);
/*  905 */     jsonWriter.setLenient(this.lenient);
/*  906 */     jsonWriter.setSerializeNulls(this.serializeNulls);
/*  907 */     return jsonWriter;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public JsonReader newJsonReader(Reader reader) {
/*  919 */     JsonReader jsonReader = new JsonReader(reader);
/*  920 */     jsonReader.setLenient(this.lenient);
/*  921 */     return jsonReader;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void toJson(JsonElement jsonElement, JsonWriter writer) throws JsonIOException {
/*  938 */     boolean oldLenient = writer.isLenient();
/*  939 */     writer.setLenient(true);
/*  940 */     boolean oldHtmlSafe = writer.isHtmlSafe();
/*  941 */     writer.setHtmlSafe(this.htmlSafe);
/*  942 */     boolean oldSerializeNulls = writer.getSerializeNulls();
/*  943 */     writer.setSerializeNulls(this.serializeNulls);
/*      */     try {
/*  945 */       Streams.write(jsonElement, writer);
/*  946 */     } catch (IOException e) {
/*  947 */       throw new JsonIOException(e);
/*  948 */     } catch (AssertionError e) {
/*  949 */       throw new AssertionError("AssertionError (GSON 2.10.1): " + e.getMessage(), e);
/*      */     } finally {
/*  951 */       writer.setLenient(oldLenient);
/*  952 */       writer.setHtmlSafe(oldHtmlSafe);
/*  953 */       writer.setSerializeNulls(oldSerializeNulls);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public <T> T fromJson(String json, Class<T> classOfT) throws JsonSyntaxException {
/*  982 */     T object = fromJson(json, TypeToken.get(classOfT));
/*  983 */     return Primitives.wrap(classOfT).cast(object);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public <T> T fromJson(String json, Type typeOfT) throws JsonSyntaxException {
/* 1014 */     return fromJson(json, TypeToken.get(typeOfT));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public <T> T fromJson(String json, TypeToken<T> typeOfT) throws JsonSyntaxException {
/* 1043 */     if (json == null) {
/* 1044 */       return null;
/*      */     }
/* 1046 */     StringReader reader = new StringReader(json);
/* 1047 */     return fromJson(reader, typeOfT);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public <T> T fromJson(Reader json, Class<T> classOfT) throws JsonSyntaxException, JsonIOException {
/* 1075 */     T object = fromJson(json, TypeToken.get(classOfT));
/* 1076 */     return Primitives.wrap(classOfT).cast(object);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public <T> T fromJson(Reader json, Type typeOfT) throws JsonIOException, JsonSyntaxException {
/* 1107 */     return fromJson(json, TypeToken.get(typeOfT));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public <T> T fromJson(Reader json, TypeToken<T> typeOfT) throws JsonIOException, JsonSyntaxException {
/* 1136 */     JsonReader jsonReader = newJsonReader(json);
/* 1137 */     T object = fromJson(jsonReader, typeOfT);
/* 1138 */     assertFullConsumption(object, jsonReader);
/* 1139 */     return object;
/*      */   }
/*      */   
/*      */   private static void assertFullConsumption(Object obj, JsonReader reader) {
/*      */     try {
/* 1144 */       if (obj != null && reader.peek() != JsonToken.END_DOCUMENT) {
/* 1145 */         throw new JsonSyntaxException("JSON document was not fully consumed.");
/*      */       }
/* 1147 */     } catch (MalformedJsonException e) {
/* 1148 */       throw new JsonSyntaxException(e);
/* 1149 */     } catch (IOException e) {
/* 1150 */       throw new JsonIOException(e);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public <T> T fromJson(JsonReader reader, Type typeOfT) throws JsonIOException, JsonSyntaxException {
/* 1186 */     return fromJson(reader, TypeToken.get(typeOfT));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public <T> T fromJson(JsonReader reader, TypeToken<T> typeOfT) throws JsonIOException, JsonSyntaxException {
/* 1220 */     boolean isEmpty = true;
/* 1221 */     boolean oldLenient = reader.isLenient();
/* 1222 */     reader.setLenient(true);
/*      */     try {
/* 1224 */       reader.peek();
/* 1225 */       isEmpty = false;
/* 1226 */       TypeAdapter<T> typeAdapter = getAdapter(typeOfT);
/* 1227 */       return typeAdapter.read(reader);
/* 1228 */     } catch (EOFException e) {
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1233 */       if (isEmpty) {
/* 1234 */         return null;
/*      */       }
/* 1236 */       throw new JsonSyntaxException(e);
/* 1237 */     } catch (IllegalStateException e) {
/* 1238 */       throw new JsonSyntaxException(e);
/* 1239 */     } catch (IOException e) {
/*      */       
/* 1241 */       throw new JsonSyntaxException(e);
/* 1242 */     } catch (AssertionError e) {
/* 1243 */       throw new AssertionError("AssertionError (GSON 2.10.1): " + e.getMessage(), e);
/*      */     } finally {
/* 1245 */       reader.setLenient(oldLenient);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public <T> T fromJson(JsonElement json, Class<T> classOfT) throws JsonSyntaxException {
/* 1271 */     T object = fromJson(json, TypeToken.get(classOfT));
/* 1272 */     return Primitives.wrap(classOfT).cast(object);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public <T> T fromJson(JsonElement json, Type typeOfT) throws JsonSyntaxException {
/* 1300 */     return fromJson(json, TypeToken.get(typeOfT));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public <T> T fromJson(JsonElement json, TypeToken<T> typeOfT) throws JsonSyntaxException {
/* 1326 */     if (json == null) {
/* 1327 */       return null;
/*      */     }
/* 1329 */     return fromJson((JsonReader)new JsonTreeReader(json), typeOfT);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static class FutureTypeAdapter<T>
/*      */     extends SerializationDelegatingTypeAdapter<T>
/*      */   {
/* 1342 */     private TypeAdapter<T> delegate = null;
/*      */     
/*      */     public void setDelegate(TypeAdapter<T> typeAdapter) {
/* 1345 */       if (this.delegate != null) {
/* 1346 */         throw new AssertionError("Delegate is already set");
/*      */       }
/* 1348 */       this.delegate = typeAdapter;
/*      */     }
/*      */     
/*      */     private TypeAdapter<T> delegate() {
/* 1352 */       TypeAdapter<T> delegate = this.delegate;
/* 1353 */       if (delegate == null)
/*      */       {
/*      */         
/* 1356 */         throw new IllegalStateException("Adapter for type with cyclic dependency has been used before dependency has been resolved");
/*      */       }
/*      */       
/* 1359 */       return delegate;
/*      */     }
/*      */     
/*      */     public TypeAdapter<T> getSerializationDelegate() {
/* 1363 */       return delegate();
/*      */     }
/*      */     
/*      */     public T read(JsonReader in) throws IOException {
/* 1367 */       return delegate().read(in);
/*      */     }
/*      */     
/*      */     public void write(JsonWriter out, T value) throws IOException {
/* 1371 */       delegate().write(out, value);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public String toString() {
/* 1377 */     return "{serializeNulls:" + this.serializeNulls + ",factories:" + this.factories + ",instanceCreators:" + this.constructorConstructor + "}";
/*      */   }
/*      */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\libs\gson\Gson.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */