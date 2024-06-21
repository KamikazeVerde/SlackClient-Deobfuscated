/*    */ package com.viaversion.viabackwards.protocol.protocol1_11_1to1_12.packets;
/*    */ 
/*    */ import com.viaversion.viabackwards.protocol.protocol1_11_1to1_12.Protocol1_11_1To1_12;
/*    */ import com.viaversion.viabackwards.protocol.protocol1_11_1to1_12.data.AdvancementTranslations;
/*    */ import com.viaversion.viaversion.api.protocol.Protocol;
/*    */ import com.viaversion.viaversion.api.protocol.packet.ClientboundPacketType;
/*    */ import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
/*    */ import com.viaversion.viaversion.api.rewriter.RewriterBase;
/*    */ import com.viaversion.viaversion.api.type.Type;
/*    */ import com.viaversion.viaversion.libs.gson.JsonElement;
/*    */ import com.viaversion.viaversion.libs.gson.JsonObject;
/*    */ import com.viaversion.viaversion.protocols.protocol1_12to1_11_1.ClientboundPackets1_12;
/*    */ import com.viaversion.viaversion.rewriter.ComponentRewriter;
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
/*    */ public class ChatPackets1_12
/*    */   extends RewriterBase<Protocol1_11_1To1_12>
/*    */ {
/* 32 */   public static final ComponentRewriter<ClientboundPackets1_12> COMPONENT_REWRITER = new ComponentRewriter<ClientboundPackets1_12>()
/*    */     {
/*    */       public void processText(JsonElement element) {
/* 35 */         super.processText(element);
/* 36 */         if (element == null || !element.isJsonObject()) {
/*    */           return;
/*    */         }
/*    */         
/* 40 */         JsonObject object = element.getAsJsonObject();
/* 41 */         JsonElement keybind = object.remove("keybind");
/* 42 */         if (keybind == null) {
/*    */           return;
/*    */         }
/*    */ 
/*    */         
/* 47 */         object.addProperty("text", keybind.getAsString());
/*    */       }
/*    */ 
/*    */       
/*    */       protected void handleTranslate(JsonObject object, String translate) {
/* 52 */         String text = AdvancementTranslations.get(translate);
/* 53 */         if (text != null) {
/* 54 */           object.addProperty("translate", text);
/*    */         }
/*    */       }
/*    */     };
/*    */   
/*    */   public ChatPackets1_12(Protocol1_11_1To1_12 protocol) {
/* 60 */     super((Protocol)protocol);
/*    */   }
/*    */ 
/*    */   
/*    */   protected void registerPackets() {
/* 65 */     ((Protocol1_11_1To1_12)this.protocol).registerClientbound((ClientboundPacketType)ClientboundPackets1_12.CHAT_MESSAGE, wrapper -> {
/*    */           JsonElement element = (JsonElement)wrapper.passthrough(Type.COMPONENT);
/*    */           COMPONENT_REWRITER.processText(element);
/*    */         });
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viabackwards\protocol\protocol1_11_1to1_12\packets\ChatPackets1_12.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */