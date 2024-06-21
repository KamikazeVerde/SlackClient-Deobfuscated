/*    */ package com.viaversion.viaversion.libs.kyori.adventure.text.serializer.gson;
/*    */ 
/*    */ import com.viaversion.viaversion.libs.gson.JsonParseException;
/*    */ import com.viaversion.viaversion.libs.gson.JsonSyntaxException;
/*    */ import com.viaversion.viaversion.libs.gson.TypeAdapter;
/*    */ import com.viaversion.viaversion.libs.gson.stream.JsonReader;
/*    */ import com.viaversion.viaversion.libs.gson.stream.JsonWriter;
/*    */ import com.viaversion.viaversion.libs.kyori.adventure.text.format.TextColor;
/*    */ import com.viaversion.viaversion.libs.kyori.adventure.text.format.TextDecoration;
/*    */ import java.io.IOException;
/*    */ import org.jetbrains.annotations.Nullable;
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
/*    */ 
/*    */ 
/*    */ final class TextColorWrapper
/*    */ {
/*    */   @Nullable
/*    */   final TextColor color;
/*    */   @Nullable
/*    */   final TextDecoration decoration;
/*    */   final boolean reset;
/*    */   
/*    */   TextColorWrapper(@Nullable TextColor color, @Nullable TextDecoration decoration, boolean reset) {
/* 45 */     this.color = color;
/* 46 */     this.decoration = decoration;
/* 47 */     this.reset = reset;
/*    */   }
/*    */   
/*    */   static final class Serializer extends TypeAdapter<TextColorWrapper> {
/* 51 */     static final Serializer INSTANCE = new Serializer();
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/*    */     public void write(JsonWriter out, TextColorWrapper value) {
/* 58 */       throw new JsonSyntaxException("Cannot write TextColorWrapper instances");
/*    */     }
/*    */ 
/*    */     
/*    */     public TextColorWrapper read(JsonReader in) throws IOException {
/* 63 */       String input = in.nextString();
/* 64 */       TextColor color = TextColorSerializer.fromString(input);
/* 65 */       TextDecoration decoration = (TextDecoration)TextDecoration.NAMES.value(input);
/* 66 */       boolean reset = (decoration == null && input.equals("reset"));
/* 67 */       if (color == null && decoration == null && !reset) {
/* 68 */         throw new JsonParseException("Don't know how to parse " + input + " at " + in.getPath());
/*    */       }
/* 70 */       return new TextColorWrapper(color, decoration, reset);
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\libs\kyori\adventure\text\serializer\gson\TextColorWrapper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */