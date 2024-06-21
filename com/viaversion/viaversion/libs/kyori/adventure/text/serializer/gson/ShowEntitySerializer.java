/*    */ package com.viaversion.viaversion.libs.kyori.adventure.text.serializer.gson;
/*    */ 
/*    */ import com.viaversion.viaversion.libs.gson.Gson;
/*    */ import com.viaversion.viaversion.libs.gson.JsonParseException;
/*    */ import com.viaversion.viaversion.libs.gson.TypeAdapter;
/*    */ import com.viaversion.viaversion.libs.gson.stream.JsonReader;
/*    */ import com.viaversion.viaversion.libs.gson.stream.JsonWriter;
/*    */ import com.viaversion.viaversion.libs.kyori.adventure.key.Key;
/*    */ import com.viaversion.viaversion.libs.kyori.adventure.text.Component;
/*    */ import com.viaversion.viaversion.libs.kyori.adventure.text.event.HoverEvent;
/*    */ import java.io.IOException;
/*    */ import java.util.UUID;
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
/*    */ final class ShowEntitySerializer
/*    */   extends TypeAdapter<HoverEvent.ShowEntity>
/*    */ {
/*    */   static final String TYPE = "type";
/*    */   static final String ID = "id";
/*    */   static final String NAME = "name";
/*    */   private final Gson gson;
/*    */   
/*    */   static TypeAdapter<HoverEvent.ShowEntity> create(Gson gson) {
/* 44 */     return (new ShowEntitySerializer(gson)).nullSafe();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   private ShowEntitySerializer(Gson gson) {
/* 50 */     this.gson = gson;
/*    */   }
/*    */ 
/*    */   
/*    */   public HoverEvent.ShowEntity read(JsonReader in) throws IOException {
/* 55 */     in.beginObject();
/*    */     
/* 57 */     Key type = null;
/* 58 */     UUID id = null;
/* 59 */     Component name = null;
/*    */     
/* 61 */     while (in.hasNext()) {
/* 62 */       String fieldName = in.nextName();
/* 63 */       if (fieldName.equals("type")) {
/* 64 */         type = (Key)this.gson.fromJson(in, SerializerFactory.KEY_TYPE); continue;
/* 65 */       }  if (fieldName.equals("id")) {
/* 66 */         id = UUID.fromString(in.nextString()); continue;
/* 67 */       }  if (fieldName.equals("name")) {
/* 68 */         name = (Component)this.gson.fromJson(in, SerializerFactory.COMPONENT_TYPE); continue;
/*    */       } 
/* 70 */       in.skipValue();
/*    */     } 
/*    */ 
/*    */     
/* 74 */     if (type == null || id == null) {
/* 75 */       throw new JsonParseException("A show entity hover event needs type and id fields to be deserialized");
/*    */     }
/* 77 */     in.endObject();
/*    */     
/* 79 */     return HoverEvent.ShowEntity.of(type, id, name);
/*    */   }
/*    */ 
/*    */   
/*    */   public void write(JsonWriter out, HoverEvent.ShowEntity value) throws IOException {
/* 84 */     out.beginObject();
/*    */     
/* 86 */     out.name("type");
/* 87 */     this.gson.toJson(value.type(), SerializerFactory.KEY_TYPE, out);
/*    */     
/* 89 */     out.name("id");
/* 90 */     out.value(value.id().toString());
/*    */     
/* 92 */     Component name = value.name();
/* 93 */     if (name != null) {
/* 94 */       out.name("name");
/* 95 */       this.gson.toJson(name, SerializerFactory.COMPONENT_TYPE, out);
/*    */     } 
/*    */     
/* 98 */     out.endObject();
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\libs\kyori\adventure\text\serializer\gson\ShowEntitySerializer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */