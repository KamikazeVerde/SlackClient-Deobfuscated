/*    */ package com.viaversion.viaversion.libs.gson.internal.sql;
/*    */ 
/*    */ import com.viaversion.viaversion.libs.gson.Gson;
/*    */ import com.viaversion.viaversion.libs.gson.TypeAdapter;
/*    */ import com.viaversion.viaversion.libs.gson.TypeAdapterFactory;
/*    */ import com.viaversion.viaversion.libs.gson.reflect.TypeToken;
/*    */ import com.viaversion.viaversion.libs.gson.stream.JsonReader;
/*    */ import com.viaversion.viaversion.libs.gson.stream.JsonWriter;
/*    */ import java.io.IOException;
/*    */ import java.sql.Timestamp;
/*    */ import java.util.Date;
/*    */ 
/*    */ class SqlTimestampTypeAdapter
/*    */   extends TypeAdapter<Timestamp> {
/* 15 */   static final TypeAdapterFactory FACTORY = new TypeAdapterFactory()
/*    */     {
/*    */       public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> typeToken) {
/* 18 */         if (typeToken.getRawType() == Timestamp.class) {
/* 19 */           TypeAdapter<Date> dateTypeAdapter = gson.getAdapter(Date.class);
/* 20 */           return new SqlTimestampTypeAdapter(dateTypeAdapter);
/*    */         } 
/* 22 */         return null;
/*    */       }
/*    */     };
/*    */ 
/*    */   
/*    */   private final TypeAdapter<Date> dateTypeAdapter;
/*    */   
/*    */   private SqlTimestampTypeAdapter(TypeAdapter<Date> dateTypeAdapter) {
/* 30 */     this.dateTypeAdapter = dateTypeAdapter;
/*    */   }
/*    */ 
/*    */   
/*    */   public Timestamp read(JsonReader in) throws IOException {
/* 35 */     Date date = (Date)this.dateTypeAdapter.read(in);
/* 36 */     return (date != null) ? new Timestamp(date.getTime()) : null;
/*    */   }
/*    */ 
/*    */   
/*    */   public void write(JsonWriter out, Timestamp value) throws IOException {
/* 41 */     this.dateTypeAdapter.write(out, value);
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\libs\gson\internal\sql\SqlTimestampTypeAdapter.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */