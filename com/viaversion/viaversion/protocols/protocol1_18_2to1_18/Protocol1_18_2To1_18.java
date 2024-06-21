/*    */ package com.viaversion.viaversion.protocols.protocol1_18_2to1_18;
/*    */ 
/*    */ import com.viaversion.viaversion.api.minecraft.RegistryType;
/*    */ import com.viaversion.viaversion.api.protocol.AbstractProtocol;
/*    */ import com.viaversion.viaversion.api.protocol.Protocol;
/*    */ import com.viaversion.viaversion.api.protocol.packet.ClientboundPacketType;
/*    */ import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
/*    */ import com.viaversion.viaversion.api.protocol.remapper.PacketHandler;
/*    */ import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
/*    */ import com.viaversion.viaversion.api.type.Type;
/*    */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
/*    */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.ListTag;
/*    */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.StringTag;
/*    */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.Tag;
/*    */ import com.viaversion.viaversion.protocols.protocol1_17to1_16_4.ServerboundPackets1_17;
/*    */ import com.viaversion.viaversion.protocols.protocol1_18to1_17_1.ClientboundPackets1_18;
/*    */ import com.viaversion.viaversion.rewriter.TagRewriter;
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
/*    */ public final class Protocol1_18_2To1_18
/*    */   extends AbstractProtocol<ClientboundPackets1_18, ClientboundPackets1_18, ServerboundPackets1_17, ServerboundPackets1_17>
/*    */ {
/*    */   public Protocol1_18_2To1_18() {
/* 35 */     super(ClientboundPackets1_18.class, ClientboundPackets1_18.class, ServerboundPackets1_17.class, ServerboundPackets1_17.class);
/*    */   }
/*    */ 
/*    */   
/*    */   protected void registerPackets() {
/* 40 */     TagRewriter<ClientboundPackets1_18> tagRewriter = new TagRewriter((Protocol)this);
/* 41 */     tagRewriter.addEmptyTag(RegistryType.BLOCK, "minecraft:fall_damage_resetting");
/* 42 */     tagRewriter.registerGeneric((ClientboundPacketType)ClientboundPackets1_18.TAGS);
/*    */     
/* 44 */     registerClientbound((ClientboundPacketType)ClientboundPackets1_18.ENTITY_EFFECT, (PacketHandler)new PacketHandlers()
/*    */         {
/*    */           public void register() {
/* 47 */             map((Type)Type.VAR_INT);
/* 48 */             map((Type)Type.BYTE, (Type)Type.VAR_INT);
/*    */           }
/*    */         });
/*    */     
/* 52 */     registerClientbound((ClientboundPacketType)ClientboundPackets1_18.REMOVE_ENTITY_EFFECT, (PacketHandler)new PacketHandlers()
/*    */         {
/*    */           public void register() {
/* 55 */             map((Type)Type.VAR_INT);
/* 56 */             map((Type)Type.BYTE, (Type)Type.VAR_INT);
/*    */           }
/*    */         });
/*    */     
/* 60 */     registerClientbound((ClientboundPacketType)ClientboundPackets1_18.JOIN_GAME, (PacketHandler)new PacketHandlers()
/*    */         {
/*    */           public void register() {
/* 63 */             map((Type)Type.INT);
/* 64 */             map((Type)Type.BOOLEAN);
/* 65 */             map((Type)Type.UNSIGNED_BYTE);
/* 66 */             map((Type)Type.BYTE);
/* 67 */             map(Type.STRING_ARRAY);
/* 68 */             map(Type.NBT);
/* 69 */             map(Type.NBT);
/* 70 */             handler(wrapper -> {
/*    */                   CompoundTag registry = (CompoundTag)wrapper.get(Type.NBT, 0);
/*    */                   
/*    */                   CompoundTag dimensionsHolder = (CompoundTag)registry.get("minecraft:dimension_type");
/*    */                   
/*    */                   ListTag dimensions = (ListTag)dimensionsHolder.get("value");
/*    */                   for (Tag dimension : dimensions) {
/*    */                     Protocol1_18_2To1_18.this.addTagPrefix((CompoundTag)((CompoundTag)dimension).get("element"));
/*    */                   }
/*    */                   Protocol1_18_2To1_18.this.addTagPrefix((CompoundTag)wrapper.get(Type.NBT, 1));
/*    */                 });
/*    */           }
/*    */         });
/* 83 */     registerClientbound((ClientboundPacketType)ClientboundPackets1_18.RESPAWN, wrapper -> addTagPrefix((CompoundTag)wrapper.passthrough(Type.NBT)));
/*    */   }
/*    */   
/*    */   private void addTagPrefix(CompoundTag tag) {
/* 87 */     Tag infiniburnTag = tag.get("infiniburn");
/* 88 */     if (infiniburnTag instanceof StringTag) {
/* 89 */       StringTag infiniburn = (StringTag)infiniburnTag;
/* 90 */       infiniburn.setValue("#" + infiniburn.getValue());
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\protocols\protocol1_18_2to1_18\Protocol1_18_2To1_18.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */