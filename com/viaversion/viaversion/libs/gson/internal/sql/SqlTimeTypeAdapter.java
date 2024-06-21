/*    */ package com.viaversion.viaversion.libs.gson.internal.sql;
/*    */ 
/*    */ import com.viaversion.viaversion.libs.gson.Gson;
/*    */ import com.viaversion.viaversion.libs.gson.JsonSyntaxException;
/*    */ import com.viaversion.viaversion.libs.gson.TypeAdapter;
/*    */ import com.viaversion.viaversion.libs.gson.TypeAdapterFactory;
/*    */ import com.viaversion.viaversion.libs.gson.reflect.TypeToken;
/*    */ import com.viaversion.viaversion.libs.gson.stream.JsonReader;
/*    */ import com.viaversion.viaversion.libs.gson.stream.JsonToken;
/*    */ import com.viaversion.viaversion.libs.gson.stream.JsonWriter;
/*    */ import java.io.IOException;
/*    */ import java.sql.Time;
/*    */ import java.text.DateFormat;
/*    */ import java.text.ParseException;
/*    */ import java.text.SimpleDateFormat;
/*    */ import java.util.Date;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ final class SqlTimeTypeAdapter
/*    */   extends TypeAdapter<Time>
/*    */ {
/* 41 */   static final TypeAdapterFactory FACTORY = new TypeAdapterFactory()
/*    */     {
/*    */       public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> typeToken) {
/* 44 */         return (typeToken.getRawType() == Time.class) ? new SqlTimeTypeAdapter() : null;
/*    */       }
/*    */     };
/*    */   
/* 48 */   private final DateFormat format = new SimpleDateFormat("hh:mm:ss a");
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Time read(JsonReader in) throws IOException {
/* 54 */     if (in.peek() == JsonToken.NULL) {
/* 55 */       in.nextNull();
/* 56 */       return null;
/*    */     } 
/* 58 */     String s = in.nextString();
/*    */     try {
/* 60 */       synchronized (this) {
/* 61 */         Date date = this.format.parse(s);
/* 62 */         return new Time(date.getTime());
/*    */       } 
/* 64 */     } catch (ParseException e) {
/* 65 */       throw new JsonSyntaxException("Failed parsing '" + s + "' as SQL Time; at path " + in.getPreviousPath(), e);
/*    */     } 
/*    */   }
/*    */   public void write(JsonWriter out, Time value) throws IOException {
/*    */     String timeString;
/* 70 */     if (value == null) {
/* 71 */       out.nullValue();
/*    */       
/*    */       return;
/*    */     } 
/* 75 */     synchronized (this) {
/* 76 */       timeString = this.format.format(value);
/*    */     } 
/* 78 */     out.value(timeString);
/*    */   }
/*    */   
/*    */   private SqlTimeTypeAdapter() {}
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\libs\gson\internal\sql\SqlTimeTypeAdapter.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */