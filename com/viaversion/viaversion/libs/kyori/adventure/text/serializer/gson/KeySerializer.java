/*    */ package com.viaversion.viaversion.libs.kyori.adventure.text.serializer.gson;
/*    */ 
/*    */ import com.viaversion.viaversion.libs.gson.TypeAdapter;
/*    */ import com.viaversion.viaversion.libs.gson.stream.JsonReader;
/*    */ import com.viaversion.viaversion.libs.gson.stream.JsonWriter;
/*    */ import com.viaversion.viaversion.libs.kyori.adventure.key.Key;
/*    */ import java.io.IOException;
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
/*    */ 
/*    */ final class KeySerializer
/*    */   extends TypeAdapter<Key>
/*    */ {
/* 33 */   static final TypeAdapter<Key> INSTANCE = (new KeySerializer()).nullSafe();
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void write(JsonWriter out, Key value) throws IOException {
/* 40 */     out.value(value.asString());
/*    */   }
/*    */ 
/*    */   
/*    */   public Key read(JsonReader in) throws IOException {
/* 45 */     return Key.key(in.nextString());
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\libs\kyori\adventure\text\serializer\gson\KeySerializer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */