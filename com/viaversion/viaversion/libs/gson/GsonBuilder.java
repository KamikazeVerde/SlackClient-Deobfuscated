/*     */ package com.viaversion.viaversion.libs.gson;
/*     */ 
/*     */ import com.viaversion.viaversion.libs.gson.internal.;
/*     */ import com.viaversion.viaversion.libs.gson.internal.Excluder;
/*     */ import com.viaversion.viaversion.libs.gson.internal.bind.DefaultDateTypeAdapter;
/*     */ import com.viaversion.viaversion.libs.gson.internal.bind.TreeTypeAdapter;
/*     */ import com.viaversion.viaversion.libs.gson.internal.bind.TypeAdapters;
/*     */ import com.viaversion.viaversion.libs.gson.internal.sql.SqlTypesSupport;
/*     */ import com.viaversion.viaversion.libs.gson.reflect.TypeToken;
/*     */ import java.lang.reflect.Type;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.HashMap;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
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
/*     */ public final class GsonBuilder
/*     */ {
/*  87 */   private Excluder excluder = Excluder.DEFAULT;
/*  88 */   private LongSerializationPolicy longSerializationPolicy = LongSerializationPolicy.DEFAULT;
/*  89 */   private FieldNamingStrategy fieldNamingPolicy = FieldNamingPolicy.IDENTITY;
/*  90 */   private final Map<Type, InstanceCreator<?>> instanceCreators = new HashMap<>();
/*  91 */   private final List<TypeAdapterFactory> factories = new ArrayList<>();
/*     */   
/*  93 */   private final List<TypeAdapterFactory> hierarchyFactories = new ArrayList<>();
/*     */   private boolean serializeNulls = false;
/*  95 */   private String datePattern = Gson.DEFAULT_DATE_PATTERN;
/*  96 */   private int dateStyle = 2;
/*  97 */   private int timeStyle = 2;
/*     */   private boolean complexMapKeySerialization = false;
/*     */   private boolean serializeSpecialFloatingPointValues = false;
/*     */   private boolean escapeHtmlChars = true;
/*     */   private boolean prettyPrinting = false;
/*     */   private boolean generateNonExecutableJson = false;
/*     */   private boolean lenient = false;
/*     */   private boolean useJdkUnsafe = true;
/* 105 */   private ToNumberStrategy objectToNumberStrategy = Gson.DEFAULT_OBJECT_TO_NUMBER_STRATEGY;
/* 106 */   private ToNumberStrategy numberToNumberStrategy = Gson.DEFAULT_NUMBER_TO_NUMBER_STRATEGY;
/* 107 */   private final LinkedList<ReflectionAccessFilter> reflectionFilters = new LinkedList<>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public GsonBuilder() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   GsonBuilder(Gson gson) {
/* 125 */     this.excluder = gson.excluder;
/* 126 */     this.fieldNamingPolicy = gson.fieldNamingStrategy;
/* 127 */     this.instanceCreators.putAll(gson.instanceCreators);
/* 128 */     this.serializeNulls = gson.serializeNulls;
/* 129 */     this.complexMapKeySerialization = gson.complexMapKeySerialization;
/* 130 */     this.generateNonExecutableJson = gson.generateNonExecutableJson;
/* 131 */     this.escapeHtmlChars = gson.htmlSafe;
/* 132 */     this.prettyPrinting = gson.prettyPrinting;
/* 133 */     this.lenient = gson.lenient;
/* 134 */     this.serializeSpecialFloatingPointValues = gson.serializeSpecialFloatingPointValues;
/* 135 */     this.longSerializationPolicy = gson.longSerializationPolicy;
/* 136 */     this.datePattern = gson.datePattern;
/* 137 */     this.dateStyle = gson.dateStyle;
/* 138 */     this.timeStyle = gson.timeStyle;
/* 139 */     this.factories.addAll(gson.builderFactories);
/* 140 */     this.hierarchyFactories.addAll(gson.builderHierarchyFactories);
/* 141 */     this.useJdkUnsafe = gson.useJdkUnsafe;
/* 142 */     this.objectToNumberStrategy = gson.objectToNumberStrategy;
/* 143 */     this.numberToNumberStrategy = gson.numberToNumberStrategy;
/* 144 */     this.reflectionFilters.addAll(gson.reflectionFilters);
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
/*     */   public GsonBuilder setVersion(double version) {
/* 163 */     if (Double.isNaN(version) || version < 0.0D) {
/* 164 */       throw new IllegalArgumentException("Invalid version: " + version);
/*     */     }
/* 166 */     this.excluder = this.excluder.withVersion(version);
/* 167 */     return this;
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
/*     */   public GsonBuilder excludeFieldsWithModifiers(int... modifiers) {
/* 185 */     Objects.requireNonNull(modifiers);
/* 186 */     this.excluder = this.excluder.withModifiers(modifiers);
/* 187 */     return this;
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
/*     */   public GsonBuilder generateNonExecutableJson() {
/* 200 */     this.generateNonExecutableJson = true;
/* 201 */     return this;
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
/*     */   public GsonBuilder excludeFieldsWithoutExposeAnnotation() {
/* 214 */     this.excluder = this.excluder.excludeFieldsWithoutExposeAnnotation();
/* 215 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public GsonBuilder serializeNulls() {
/* 226 */     this.serializeNulls = true;
/* 227 */     return this;
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
/*     */   public GsonBuilder enableComplexMapKeySerialization() {
/* 310 */     this.complexMapKeySerialization = true;
/* 311 */     return this;
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
/*     */   public GsonBuilder disableInnerClassSerialization() {
/* 334 */     this.excluder = this.excluder.disableInnerClassSerialization();
/* 335 */     return this;
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
/*     */   public GsonBuilder setLongSerializationPolicy(LongSerializationPolicy serializationPolicy) {
/* 347 */     this.longSerializationPolicy = Objects.<LongSerializationPolicy>requireNonNull(serializationPolicy);
/* 348 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public GsonBuilder setFieldNamingPolicy(FieldNamingPolicy namingConvention) {
/* 358 */     return setFieldNamingStrategy(namingConvention);
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
/*     */   public GsonBuilder setFieldNamingStrategy(FieldNamingStrategy fieldNamingStrategy) {
/* 374 */     this.fieldNamingPolicy = Objects.<FieldNamingStrategy>requireNonNull(fieldNamingStrategy);
/* 375 */     return this;
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
/*     */   public GsonBuilder setObjectToNumberStrategy(ToNumberStrategy objectToNumberStrategy) {
/* 387 */     this.objectToNumberStrategy = Objects.<ToNumberStrategy>requireNonNull(objectToNumberStrategy);
/* 388 */     return this;
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
/*     */   public GsonBuilder setNumberToNumberStrategy(ToNumberStrategy numberToNumberStrategy) {
/* 400 */     this.numberToNumberStrategy = Objects.<ToNumberStrategy>requireNonNull(numberToNumberStrategy);
/* 401 */     return this;
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
/*     */   public GsonBuilder setExclusionStrategies(ExclusionStrategy... strategies) {
/* 431 */     Objects.requireNonNull(strategies);
/* 432 */     for (ExclusionStrategy strategy : strategies) {
/* 433 */       this.excluder = this.excluder.withExclusionStrategy(strategy, true, true);
/*     */     }
/* 435 */     return this;
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
/*     */   public GsonBuilder addSerializationExclusionStrategy(ExclusionStrategy strategy) {
/* 454 */     Objects.requireNonNull(strategy);
/* 455 */     this.excluder = this.excluder.withExclusionStrategy(strategy, true, false);
/* 456 */     return this;
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
/*     */   public GsonBuilder addDeserializationExclusionStrategy(ExclusionStrategy strategy) {
/* 475 */     Objects.requireNonNull(strategy);
/* 476 */     this.excluder = this.excluder.withExclusionStrategy(strategy, false, true);
/* 477 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public GsonBuilder setPrettyPrinting() {
/* 487 */     this.prettyPrinting = true;
/* 488 */     return this;
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
/*     */   public GsonBuilder setLenient() {
/* 502 */     this.lenient = true;
/* 503 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public GsonBuilder disableHtmlEscaping() {
/* 514 */     this.escapeHtmlChars = false;
/* 515 */     return this;
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
/*     */   public GsonBuilder setDateFormat(String pattern) {
/* 536 */     this.datePattern = pattern;
/* 537 */     return this;
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
/*     */   public GsonBuilder setDateFormat(int style) {
/* 555 */     this.dateStyle = style;
/* 556 */     this.datePattern = null;
/* 557 */     return this;
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
/*     */   public GsonBuilder setDateFormat(int dateStyle, int timeStyle) {
/* 576 */     this.dateStyle = dateStyle;
/* 577 */     this.timeStyle = timeStyle;
/* 578 */     this.datePattern = null;
/* 579 */     return this;
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
/*     */   public GsonBuilder registerTypeAdapter(Type type, Object typeAdapter) {
/* 605 */     Objects.requireNonNull(type);
/* 606 */     .Gson.Preconditions.checkArgument((typeAdapter instanceof JsonSerializer || typeAdapter instanceof JsonDeserializer || typeAdapter instanceof InstanceCreator || typeAdapter instanceof TypeAdapter));
/*     */ 
/*     */ 
/*     */     
/* 610 */     if (typeAdapter instanceof InstanceCreator) {
/* 611 */       this.instanceCreators.put(type, (InstanceCreator)typeAdapter);
/*     */     }
/* 613 */     if (typeAdapter instanceof JsonSerializer || typeAdapter instanceof JsonDeserializer) {
/* 614 */       TypeToken<?> typeToken = TypeToken.get(type);
/* 615 */       this.factories.add(TreeTypeAdapter.newFactoryWithMatchRawType(typeToken, typeAdapter));
/*     */     } 
/* 617 */     if (typeAdapter instanceof TypeAdapter) {
/*     */       
/* 619 */       TypeAdapterFactory factory = TypeAdapters.newFactory(TypeToken.get(type), (TypeAdapter)typeAdapter);
/* 620 */       this.factories.add(factory);
/*     */     } 
/* 622 */     return this;
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
/*     */   public GsonBuilder registerTypeAdapterFactory(TypeAdapterFactory factory) {
/* 638 */     Objects.requireNonNull(factory);
/* 639 */     this.factories.add(factory);
/* 640 */     return this;
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
/*     */   public GsonBuilder registerTypeHierarchyAdapter(Class<?> baseType, Object typeAdapter) {
/* 658 */     Objects.requireNonNull(baseType);
/* 659 */     .Gson.Preconditions.checkArgument((typeAdapter instanceof JsonSerializer || typeAdapter instanceof JsonDeserializer || typeAdapter instanceof TypeAdapter));
/*     */ 
/*     */     
/* 662 */     if (typeAdapter instanceof JsonDeserializer || typeAdapter instanceof JsonSerializer) {
/* 663 */       this.hierarchyFactories.add(TreeTypeAdapter.newTypeHierarchyFactory(baseType, typeAdapter));
/*     */     }
/* 665 */     if (typeAdapter instanceof TypeAdapter) {
/*     */       
/* 667 */       TypeAdapterFactory factory = TypeAdapters.newTypeHierarchyFactory(baseType, (TypeAdapter)typeAdapter);
/* 668 */       this.factories.add(factory);
/*     */     } 
/* 670 */     return this;
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
/*     */   public GsonBuilder serializeSpecialFloatingPointValues() {
/* 694 */     this.serializeSpecialFloatingPointValues = true;
/* 695 */     return this;
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
/*     */   public GsonBuilder disableJdkUnsafe() {
/* 715 */     this.useJdkUnsafe = false;
/* 716 */     return this;
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
/*     */   public GsonBuilder addReflectionAccessFilter(ReflectionAccessFilter filter) {
/* 740 */     Objects.requireNonNull(filter);
/* 741 */     this.reflectionFilters.addFirst(filter);
/* 742 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Gson create() {
/* 752 */     List<TypeAdapterFactory> factories = new ArrayList<>(this.factories.size() + this.hierarchyFactories.size() + 3);
/* 753 */     factories.addAll(this.factories);
/* 754 */     Collections.reverse(factories);
/*     */     
/* 756 */     List<TypeAdapterFactory> hierarchyFactories = new ArrayList<>(this.hierarchyFactories);
/* 757 */     Collections.reverse(hierarchyFactories);
/* 758 */     factories.addAll(hierarchyFactories);
/*     */     
/* 760 */     addTypeAdaptersForDate(this.datePattern, this.dateStyle, this.timeStyle, factories);
/*     */     
/* 762 */     return new Gson(this.excluder, this.fieldNamingPolicy, new HashMap<>(this.instanceCreators), this.serializeNulls, this.complexMapKeySerialization, this.generateNonExecutableJson, this.escapeHtmlChars, this.prettyPrinting, this.lenient, this.serializeSpecialFloatingPointValues, this.useJdkUnsafe, this.longSerializationPolicy, this.datePattern, this.dateStyle, this.timeStyle, new ArrayList<>(this.factories), new ArrayList<>(this.hierarchyFactories), factories, this.objectToNumberStrategy, this.numberToNumberStrategy, new ArrayList<>(this.reflectionFilters));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void addTypeAdaptersForDate(String datePattern, int dateStyle, int timeStyle, List<TypeAdapterFactory> factories) {
/*     */     TypeAdapterFactory dateAdapterFactory;
/* 774 */     boolean sqlTypesSupported = SqlTypesSupport.SUPPORTS_SQL_TYPES;
/* 775 */     TypeAdapterFactory sqlTimestampAdapterFactory = null;
/* 776 */     TypeAdapterFactory sqlDateAdapterFactory = null;
/*     */     
/* 778 */     if (datePattern != null && !datePattern.trim().isEmpty()) {
/* 779 */       dateAdapterFactory = DefaultDateTypeAdapter.DateType.DATE.createAdapterFactory(datePattern);
/*     */       
/* 781 */       if (sqlTypesSupported) {
/* 782 */         sqlTimestampAdapterFactory = SqlTypesSupport.TIMESTAMP_DATE_TYPE.createAdapterFactory(datePattern);
/* 783 */         sqlDateAdapterFactory = SqlTypesSupport.DATE_DATE_TYPE.createAdapterFactory(datePattern);
/*     */       } 
/* 785 */     } else if (dateStyle != 2 && timeStyle != 2) {
/* 786 */       dateAdapterFactory = DefaultDateTypeAdapter.DateType.DATE.createAdapterFactory(dateStyle, timeStyle);
/*     */       
/* 788 */       if (sqlTypesSupported) {
/* 789 */         sqlTimestampAdapterFactory = SqlTypesSupport.TIMESTAMP_DATE_TYPE.createAdapterFactory(dateStyle, timeStyle);
/* 790 */         sqlDateAdapterFactory = SqlTypesSupport.DATE_DATE_TYPE.createAdapterFactory(dateStyle, timeStyle);
/*     */       } 
/*     */     } else {
/*     */       return;
/*     */     } 
/*     */     
/* 796 */     factories.add(dateAdapterFactory);
/* 797 */     if (sqlTypesSupported) {
/* 798 */       factories.add(sqlTimestampAdapterFactory);
/* 799 */       factories.add(sqlDateAdapterFactory);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\libs\gson\GsonBuilder.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */