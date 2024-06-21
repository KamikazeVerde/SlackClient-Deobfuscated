/*    */ package com.viaversion.viaversion.libs.kyori.adventure.text.serializer.gson;
/*    */ 
/*    */ import com.viaversion.viaversion.libs.gson.JsonParseException;
/*    */ import com.viaversion.viaversion.libs.gson.TypeAdapter;
/*    */ import com.viaversion.viaversion.libs.gson.stream.JsonReader;
/*    */ import com.viaversion.viaversion.libs.gson.stream.JsonWriter;
/*    */ import com.viaversion.viaversion.libs.kyori.adventure.text.BlockNBTComponent;
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
/*    */ final class BlockNBTComponentPosSerializer
/*    */   extends TypeAdapter<BlockNBTComponent.Pos>
/*    */ {
/* 34 */   static final TypeAdapter<BlockNBTComponent.Pos> INSTANCE = (new BlockNBTComponentPosSerializer()).nullSafe();
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public BlockNBTComponent.Pos read(JsonReader in) throws IOException {
/* 41 */     String string = in.nextString();
/*    */     try {
/* 43 */       return BlockNBTComponent.Pos.fromString(string);
/* 44 */     } catch (IllegalArgumentException ex) {
/* 45 */       throw new JsonParseException("Don't know how to turn " + string + " into a Position");
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public void write(JsonWriter out, BlockNBTComponent.Pos value) throws IOException {
/* 51 */     out.value(value.asString());
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\libs\kyori\adventure\text\serializer\gson\BlockNBTComponentPosSerializer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */