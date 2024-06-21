/*     */ package com.viaversion.viaversion.libs.gson.internal.bind;
/*     */ 
/*     */ import com.viaversion.viaversion.libs.gson.Gson;
/*     */ import com.viaversion.viaversion.libs.gson.JsonSyntaxException;
/*     */ import com.viaversion.viaversion.libs.gson.TypeAdapter;
/*     */ import com.viaversion.viaversion.libs.gson.TypeAdapterFactory;
/*     */ import com.viaversion.viaversion.libs.gson.internal.JavaVersion;
/*     */ import com.viaversion.viaversion.libs.gson.internal.PreJava9DateFormatProvider;
/*     */ import com.viaversion.viaversion.libs.gson.internal.bind.util.ISO8601Utils;
/*     */ import com.viaversion.viaversion.libs.gson.reflect.TypeToken;
/*     */ import com.viaversion.viaversion.libs.gson.stream.JsonReader;
/*     */ import com.viaversion.viaversion.libs.gson.stream.JsonToken;
/*     */ import com.viaversion.viaversion.libs.gson.stream.JsonWriter;
/*     */ import java.io.IOException;
/*     */ import java.text.DateFormat;
/*     */ import java.text.ParseException;
/*     */ import java.text.ParsePosition;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Date;
/*     */ import java.util.List;
/*     */ import java.util.Locale;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class DateTypeAdapter
/*     */   extends TypeAdapter<Date>
/*     */ {
/*  47 */   public static final TypeAdapterFactory FACTORY = new TypeAdapterFactory()
/*     */     {
/*     */       public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> typeToken) {
/*  50 */         return (typeToken.getRawType() == Date.class) ? new DateTypeAdapter() : null;
/*     */       }
/*     */     };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  58 */   private final List<DateFormat> dateFormats = new ArrayList<>();
/*     */   
/*     */   public DateTypeAdapter() {
/*  61 */     this.dateFormats.add(DateFormat.getDateTimeInstance(2, 2, Locale.US));
/*  62 */     if (!Locale.getDefault().equals(Locale.US)) {
/*  63 */       this.dateFormats.add(DateFormat.getDateTimeInstance(2, 2));
/*     */     }
/*  65 */     if (JavaVersion.isJava9OrLater()) {
/*  66 */       this.dateFormats.add(PreJava9DateFormatProvider.getUSDateTimeFormat(2, 2));
/*     */     }
/*     */   }
/*     */   
/*     */   public Date read(JsonReader in) throws IOException {
/*  71 */     if (in.peek() == JsonToken.NULL) {
/*  72 */       in.nextNull();
/*  73 */       return null;
/*     */     } 
/*  75 */     return deserializeToDate(in);
/*     */   }
/*     */   
/*     */   private Date deserializeToDate(JsonReader in) throws IOException {
/*  79 */     String s = in.nextString();
/*  80 */     synchronized (this.dateFormats) {
/*  81 */       for (DateFormat dateFormat : this.dateFormats) {
/*     */         try {
/*  83 */           return dateFormat.parse(s);
/*  84 */         } catch (ParseException parseException) {}
/*     */       } 
/*     */     } 
/*     */     try {
/*  88 */       return ISO8601Utils.parse(s, new ParsePosition(0));
/*  89 */     } catch (ParseException e) {
/*  90 */       throw new JsonSyntaxException("Failed parsing '" + s + "' as Date; at path " + in.getPreviousPath(), e);
/*     */     } 
/*     */   }
/*     */   public void write(JsonWriter out, Date value) throws IOException {
/*     */     String dateFormatAsString;
/*  95 */     if (value == null) {
/*  96 */       out.nullValue();
/*     */       
/*     */       return;
/*     */     } 
/* 100 */     DateFormat dateFormat = this.dateFormats.get(0);
/*     */     
/* 102 */     synchronized (this.dateFormats) {
/* 103 */       dateFormatAsString = dateFormat.format(value);
/*     */     } 
/* 105 */     out.value(dateFormatAsString);
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\libs\gson\internal\bind\DateTypeAdapter.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */