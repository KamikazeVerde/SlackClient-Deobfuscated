/*    */ package com.viaversion.viabackwards.protocol.protocol1_10to1_11.packets;
/*    */ 
/*    */ import com.viaversion.viabackwards.protocol.protocol1_10to1_11.Protocol1_10To1_11;
/*    */ import com.viaversion.viaversion.api.protocol.packet.ClientboundPacketType;
/*    */ import com.viaversion.viaversion.api.protocol.packet.PacketType;
/*    */ import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
/*    */ import com.viaversion.viaversion.api.protocol.packet.ServerboundPacketType;
/*    */ import com.viaversion.viaversion.api.protocol.remapper.PacketHandler;
/*    */ import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
/*    */ import com.viaversion.viaversion.api.protocol.remapper.ValueTransformer;
/*    */ import com.viaversion.viaversion.api.type.Type;
/*    */ import com.viaversion.viaversion.libs.gson.JsonElement;
/*    */ import com.viaversion.viaversion.libs.gson.JsonObject;
/*    */ import com.viaversion.viaversion.libs.kyori.adventure.text.serializer.gson.GsonComponentSerializer;
/*    */ import com.viaversion.viaversion.libs.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
/*    */ import com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2.ClientboundPackets1_9_3;
/*    */ import com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2.ServerboundPackets1_9_3;
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
/*    */ public class PlayerPackets1_11
/*    */ {
/* 34 */   private static final ValueTransformer<Short, Float> TO_NEW_FLOAT = new ValueTransformer<Short, Float>((Type)Type.FLOAT)
/*    */     {
/*    */       public Float transform(PacketWrapper wrapper, Short inputValue) throws Exception {
/* 37 */         return Float.valueOf(inputValue.shortValue() / 16.0F);
/*    */       }
/*    */     };
/*    */   
/*    */   public void register(Protocol1_10To1_11 protocol) {
/* 42 */     protocol.registerClientbound((ClientboundPacketType)ClientboundPackets1_9_3.TITLE, (PacketHandler)new PacketHandlers()
/*    */         {
/*    */           public void register() {
/* 45 */             map((Type)Type.VAR_INT);
/*    */             
/* 47 */             handler(wrapper -> {
/*    */                   int action = ((Integer)wrapper.get((Type)Type.VAR_INT, 0)).intValue();
/*    */                   
/*    */                   if (action == 2) {
/*    */                     JsonElement message = (JsonElement)wrapper.read(Type.COMPONENT);
/*    */                     
/*    */                     wrapper.clearPacket();
/*    */                     
/*    */                     wrapper.setPacketType((PacketType)ClientboundPackets1_9_3.CHAT_MESSAGE);
/*    */                     
/*    */                     String legacy = LegacyComponentSerializer.legacySection().serialize(GsonComponentSerializer.gson().deserialize(message.toString()));
/*    */                     
/*    */                     JsonObject jsonObject = new JsonObject();
/*    */                     
/*    */                     jsonObject.getAsJsonObject().addProperty("text", legacy);
/*    */                     wrapper.write(Type.COMPONENT, jsonObject);
/*    */                     wrapper.write((Type)Type.BYTE, Byte.valueOf((byte)2));
/*    */                   } else if (action > 2) {
/*    */                     wrapper.set((Type)Type.VAR_INT, 0, Integer.valueOf(action - 1));
/*    */                   } 
/*    */                 });
/*    */           }
/*    */         });
/* 70 */     protocol.registerClientbound((ClientboundPacketType)ClientboundPackets1_9_3.COLLECT_ITEM, (PacketHandler)new PacketHandlers()
/*    */         {
/*    */           public void register() {
/* 73 */             map((Type)Type.VAR_INT);
/* 74 */             map((Type)Type.VAR_INT);
/*    */             
/* 76 */             handler(wrapper -> wrapper.read((Type)Type.VAR_INT));
/*    */           }
/*    */         });
/*    */ 
/*    */     
/* 81 */     protocol.registerServerbound((ServerboundPacketType)ServerboundPackets1_9_3.PLAYER_BLOCK_PLACEMENT, (PacketHandler)new PacketHandlers()
/*    */         {
/*    */           public void register() {
/* 84 */             map(Type.POSITION);
/* 85 */             map((Type)Type.VAR_INT);
/* 86 */             map((Type)Type.VAR_INT);
/*    */             
/* 88 */             map((Type)Type.UNSIGNED_BYTE, PlayerPackets1_11.TO_NEW_FLOAT);
/* 89 */             map((Type)Type.UNSIGNED_BYTE, PlayerPackets1_11.TO_NEW_FLOAT);
/* 90 */             map((Type)Type.UNSIGNED_BYTE, PlayerPackets1_11.TO_NEW_FLOAT);
/*    */           }
/*    */         });
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viabackwards\protocol\protocol1_10to1_11\packets\PlayerPackets1_11.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */