/*     */ package com.viaversion.viabackwards.protocol.protocol1_18to1_18_2;
/*     */ 
/*     */ import com.viaversion.viabackwards.ViaBackwards;
/*     */ import com.viaversion.viabackwards.api.BackwardsProtocol;
/*     */ import com.viaversion.viabackwards.protocol.protocol1_18to1_18_2.data.CommandRewriter1_18_2;
/*     */ import com.viaversion.viaversion.api.Via;
/*     */ import com.viaversion.viaversion.api.protocol.packet.ClientboundPacketType;
/*     */ import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
/*     */ import com.viaversion.viaversion.api.protocol.remapper.PacketHandler;
/*     */ import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
/*     */ import com.viaversion.viaversion.api.type.Type;
/*     */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
/*     */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.ListTag;
/*     */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.StringTag;
/*     */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.Tag;
/*     */ import com.viaversion.viaversion.protocols.protocol1_17to1_16_4.ServerboundPackets1_17;
/*     */ import com.viaversion.viaversion.protocols.protocol1_18to1_17_1.ClientboundPackets1_18;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class Protocol1_18To1_18_2
/*     */   extends BackwardsProtocol<ClientboundPackets1_18, ClientboundPackets1_18, ServerboundPackets1_17, ServerboundPackets1_17>
/*     */ {
/*     */   public Protocol1_18To1_18_2() {
/*  37 */     super(ClientboundPackets1_18.class, ClientboundPackets1_18.class, ServerboundPackets1_17.class, ServerboundPackets1_17.class);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void registerPackets() {
/*  42 */     (new CommandRewriter1_18_2(this)).registerDeclareCommands((ClientboundPacketType)ClientboundPackets1_18.DECLARE_COMMANDS);
/*     */     
/*  44 */     final PacketHandler entityEffectIdHandler = wrapper -> {
/*     */         int id = ((Integer)wrapper.read((Type)Type.VAR_INT)).intValue();
/*     */         
/*     */         if ((byte)id != id) {
/*     */           if (!Via.getConfig().isSuppressConversionWarnings()) {
/*     */             ViaBackwards.getPlatform().getLogger().warning("Cannot send entity effect id " + id + " to old client");
/*     */           }
/*     */           wrapper.cancel();
/*     */           return;
/*     */         } 
/*     */         wrapper.write((Type)Type.BYTE, Byte.valueOf((byte)id));
/*     */       };
/*  56 */     registerClientbound((ClientboundPacketType)ClientboundPackets1_18.ENTITY_EFFECT, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/*  59 */             map((Type)Type.VAR_INT);
/*  60 */             handler(entityEffectIdHandler);
/*     */           }
/*     */         });
/*     */     
/*  64 */     registerClientbound((ClientboundPacketType)ClientboundPackets1_18.REMOVE_ENTITY_EFFECT, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/*  67 */             map((Type)Type.VAR_INT);
/*  68 */             handler(entityEffectIdHandler);
/*     */           }
/*     */         });
/*     */     
/*  72 */     registerClientbound((ClientboundPacketType)ClientboundPackets1_18.JOIN_GAME, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/*  75 */             map((Type)Type.INT);
/*  76 */             map((Type)Type.BOOLEAN);
/*  77 */             map((Type)Type.UNSIGNED_BYTE);
/*  78 */             map((Type)Type.BYTE);
/*  79 */             map(Type.STRING_ARRAY);
/*  80 */             map(Type.NBT);
/*  81 */             map(Type.NBT);
/*  82 */             handler(wrapper -> {
/*     */                   CompoundTag registry = (CompoundTag)wrapper.get(Type.NBT, 0);
/*     */                   
/*     */                   CompoundTag dimensionsHolder = (CompoundTag)registry.get("minecraft:dimension_type");
/*     */                   
/*     */                   ListTag dimensions = (ListTag)dimensionsHolder.get("value");
/*     */                   for (Tag dimension : dimensions) {
/*     */                     Protocol1_18To1_18_2.this.removeTagPrefix((CompoundTag)((CompoundTag)dimension).get("element"));
/*     */                   }
/*     */                   Protocol1_18To1_18_2.this.removeTagPrefix((CompoundTag)wrapper.get(Type.NBT, 1));
/*     */                 });
/*     */           }
/*     */         });
/*  95 */     registerClientbound((ClientboundPacketType)ClientboundPackets1_18.RESPAWN, wrapper -> removeTagPrefix((CompoundTag)wrapper.passthrough(Type.NBT)));
/*     */   }
/*     */   
/*     */   private void removeTagPrefix(CompoundTag tag) {
/*  99 */     Tag infiniburnTag = tag.get("infiniburn");
/* 100 */     if (infiniburnTag instanceof StringTag) {
/* 101 */       StringTag infiniburn = (StringTag)infiniburnTag;
/* 102 */       infiniburn.setValue(infiniburn.getValue().substring(1));
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viabackwards\protocol\protocol1_18to1_18_2\Protocol1_18To1_18_2.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */