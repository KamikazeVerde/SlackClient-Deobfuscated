/*     */ package com.viaversion.viaversion.libs.gson.internal.bind;
/*     */ 
/*     */ import com.viaversion.viaversion.libs.gson.JsonSyntaxException;
/*     */ import com.viaversion.viaversion.libs.gson.TypeAdapter;
/*     */ import com.viaversion.viaversion.libs.gson.TypeAdapterFactory;
/*     */ import com.viaversion.viaversion.libs.gson.internal.JavaVersion;
/*     */ import com.viaversion.viaversion.libs.gson.internal.PreJava9DateFormatProvider;
/*     */ import com.viaversion.viaversion.libs.gson.internal.bind.util.ISO8601Utils;
/*     */ import com.viaversion.viaversion.libs.gson.stream.JsonReader;
/*     */ import com.viaversion.viaversion.libs.gson.stream.JsonToken;
/*     */ import com.viaversion.viaversion.libs.gson.stream.JsonWriter;
/*     */ import java.io.IOException;
/*     */ import java.text.DateFormat;
/*     */ import java.text.ParseException;
/*     */ import java.text.ParsePosition;
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Date;
/*     */ import java.util.List;
/*     */ import java.util.Locale;
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
/*     */ public final class DefaultDateTypeAdapter<T extends Date>
/*     */   extends TypeAdapter<T>
/*     */ {
/*     */   private static final String SIMPLE_NAME = "DefaultDateTypeAdapter";
/*     */   private final DateType<T> dateType;
/*     */   
/*     */   public static abstract class DateType<T extends Date>
/*     */   {
/*  51 */     public static final DateType<Date> DATE = new DateType<Date>(Date.class) {
/*     */         protected Date deserialize(Date date) {
/*  53 */           return date;
/*     */         }
/*     */       };
/*     */     
/*     */     private final Class<T> dateClass;
/*     */     
/*     */     protected DateType(Class<T> dateClass) {
/*  60 */       this.dateClass = dateClass;
/*     */     }
/*     */     
/*     */     protected abstract T deserialize(Date param1Date);
/*     */     
/*     */     private TypeAdapterFactory createFactory(DefaultDateTypeAdapter<T> adapter) {
/*  66 */       return TypeAdapters.newFactory(this.dateClass, adapter);
/*     */     }
/*     */     
/*     */     public final TypeAdapterFactory createAdapterFactory(String datePattern) {
/*  70 */       return createFactory(new DefaultDateTypeAdapter<>(this, datePattern));
/*     */     }
/*     */     
/*     */     public final TypeAdapterFactory createAdapterFactory(int style) {
/*  74 */       return createFactory(new DefaultDateTypeAdapter<>(this, style));
/*     */     }
/*     */     
/*     */     public final TypeAdapterFactory createAdapterFactory(int dateStyle, int timeStyle) {
/*  78 */       return createFactory(new DefaultDateTypeAdapter<>(this, dateStyle, timeStyle));
/*     */     }
/*     */     
/*     */     public final TypeAdapterFactory createDefaultsAdapterFactory() {
/*  82 */       return createFactory(new DefaultDateTypeAdapter<>(this, 2, 2));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  92 */   private final List<DateFormat> dateFormats = new ArrayList<>();
/*     */   
/*     */   private DefaultDateTypeAdapter(DateType<T> dateType, String datePattern) {
/*  95 */     this.dateType = Objects.<DateType<T>>requireNonNull(dateType);
/*  96 */     this.dateFormats.add(new SimpleDateFormat(datePattern, Locale.US));
/*  97 */     if (!Locale.getDefault().equals(Locale.US)) {
/*  98 */       this.dateFormats.add(new SimpleDateFormat(datePattern));
/*     */     }
/*     */   }
/*     */   
/*     */   private DefaultDateTypeAdapter(DateType<T> dateType, int style) {
/* 103 */     this.dateType = Objects.<DateType<T>>requireNonNull(dateType);
/* 104 */     this.dateFormats.add(DateFormat.getDateInstance(style, Locale.US));
/* 105 */     if (!Locale.getDefault().equals(Locale.US)) {
/* 106 */       this.dateFormats.add(DateFormat.getDateInstance(style));
/*     */     }
/* 108 */     if (JavaVersion.isJava9OrLater()) {
/* 109 */       this.dateFormats.add(PreJava9DateFormatProvider.getUSDateFormat(style));
/*     */     }
/*     */   }
/*     */   
/*     */   private DefaultDateTypeAdapter(DateType<T> dateType, int dateStyle, int timeStyle) {
/* 114 */     this.dateType = Objects.<DateType<T>>requireNonNull(dateType);
/* 115 */     this.dateFormats.add(DateFormat.getDateTimeInstance(dateStyle, timeStyle, Locale.US));
/* 116 */     if (!Locale.getDefault().equals(Locale.US)) {
/* 117 */       this.dateFormats.add(DateFormat.getDateTimeInstance(dateStyle, timeStyle));
/*     */     }
/* 119 */     if (JavaVersion.isJava9OrLater()) {
/* 120 */       this.dateFormats.add(PreJava9DateFormatProvider.getUSDateTimeFormat(dateStyle, timeStyle));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void write(JsonWriter out, Date value) throws IOException {
/*     */     String dateFormatAsString;
/* 128 */     if (value == null) {
/* 129 */       out.nullValue();
/*     */       
/*     */       return;
/*     */     } 
/* 133 */     DateFormat dateFormat = this.dateFormats.get(0);
/*     */     
/* 135 */     synchronized (this.dateFormats) {
/* 136 */       dateFormatAsString = dateFormat.format(value);
/*     */     } 
/* 138 */     out.value(dateFormatAsString);
/*     */   }
/*     */ 
/*     */   
/*     */   public T read(JsonReader in) throws IOException {
/* 143 */     if (in.peek() == JsonToken.NULL) {
/* 144 */       in.nextNull();
/* 145 */       return null;
/*     */     } 
/* 147 */     Date date = deserializeToDate(in);
/* 148 */     return this.dateType.deserialize(date);
/*     */   }
/*     */   
/*     */   private Date deserializeToDate(JsonReader in) throws IOException {
/* 152 */     String s = in.nextString();
/* 153 */     synchronized (this.dateFormats) {
/* 154 */       for (DateFormat dateFormat : this.dateFormats) {
/*     */         try {
/* 156 */           return dateFormat.parse(s);
/* 157 */         } catch (ParseException parseException) {}
/*     */       } 
/*     */     } 
/*     */     
/*     */     try {
/* 162 */       return ISO8601Utils.parse(s, new ParsePosition(0));
/* 163 */     } catch (ParseException e) {
/* 164 */       throw new JsonSyntaxException("Failed parsing '" + s + "' as Date; at path " + in.getPreviousPath(), e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 170 */     DateFormat defaultFormat = this.dateFormats.get(0);
/* 171 */     if (defaultFormat instanceof SimpleDateFormat) {
/* 172 */       return "DefaultDateTypeAdapter(" + ((SimpleDateFormat)defaultFormat).toPattern() + ')';
/*     */     }
/* 174 */     return "DefaultDateTypeAdapter(" + defaultFormat.getClass().getSimpleName() + ')';
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\libs\gson\internal\bind\DefaultDateTypeAdapter.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */