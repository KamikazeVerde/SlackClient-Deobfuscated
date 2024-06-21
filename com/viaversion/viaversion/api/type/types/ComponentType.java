/*    */ package com.viaversion.viaversion.api.type.types;
/*    */ 
/*    */ import com.viaversion.viaversion.api.Via;
/*    */ import com.viaversion.viaversion.api.type.OptionalType;
/*    */ import com.viaversion.viaversion.api.type.Type;
/*    */ import com.viaversion.viaversion.libs.gson.JsonElement;
/*    */ import com.viaversion.viaversion.libs.gson.JsonParser;
/*    */ import com.viaversion.viaversion.libs.gson.JsonSyntaxException;
/*    */ import io.netty.buffer.ByteBuf;
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
/*    */ public class ComponentType
/*    */   extends Type<JsonElement>
/*    */ {
/* 34 */   private static final StringType STRING_TAG = new StringType(262144);
/*    */   
/*    */   public ComponentType() {
/* 37 */     super(JsonElement.class);
/*    */   }
/*    */ 
/*    */   
/*    */   public JsonElement read(ByteBuf buffer) throws Exception {
/* 42 */     String s = STRING_TAG.read(buffer);
/*    */     try {
/* 44 */       return JsonParser.parseString(s);
/* 45 */     } catch (JsonSyntaxException e) {
/* 46 */       Via.getPlatform().getLogger().severe("Error when trying to parse json: " + s);
/* 47 */       throw e;
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public void write(ByteBuf buffer, JsonElement object) throws Exception {
/* 53 */     STRING_TAG.write(buffer, object.toString());
/*    */   }
/*    */   
/*    */   public static final class OptionalComponentType
/*    */     extends OptionalType<JsonElement> {
/*    */     public OptionalComponentType() {
/* 59 */       super(Type.COMPONENT);
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\api\type\types\ComponentType.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */