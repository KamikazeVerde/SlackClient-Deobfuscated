/*     */ package com.viaversion.viabackwards.protocol.protocol1_17_1to1_18.packets;
/*     */ 
/*     */ import com.viaversion.viabackwards.api.BackwardsProtocol;
/*     */ import com.viaversion.viabackwards.api.rewriters.EntityRewriter;
/*     */ import com.viaversion.viabackwards.protocol.protocol1_17_1to1_18.Protocol1_17_1To1_18;
/*     */ import com.viaversion.viaversion.api.minecraft.entities.Entity1_17Types;
/*     */ import com.viaversion.viaversion.api.minecraft.entities.EntityType;
/*     */ import com.viaversion.viaversion.api.minecraft.metadata.MetaType;
/*     */ import com.viaversion.viaversion.api.minecraft.metadata.Metadata;
/*     */ import com.viaversion.viaversion.api.protocol.packet.ClientboundPacketType;
/*     */ import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
/*     */ import com.viaversion.viaversion.api.protocol.remapper.PacketHandler;
/*     */ import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
/*     */ import com.viaversion.viaversion.api.type.Type;
/*     */ import com.viaversion.viaversion.api.type.types.Particle;
/*     */ import com.viaversion.viaversion.api.type.types.version.Types1_17;
/*     */ import com.viaversion.viaversion.api.type.types.version.Types1_18;
/*     */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
/*     */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.FloatTag;
/*     */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.ListTag;
/*     */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.StringTag;
/*     */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.Tag;
/*     */ import com.viaversion.viaversion.protocols.protocol1_18to1_17_1.ClientboundPackets1_18;
/*     */ import com.viaversion.viaversion.rewriter.meta.MetaHandlerEvent;
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
/*     */ public final class EntityPackets1_18
/*     */   extends EntityRewriter<ClientboundPackets1_18, Protocol1_17_1To1_18>
/*     */ {
/*     */   public EntityPackets1_18(Protocol1_17_1To1_18 protocol) {
/*  40 */     super((BackwardsProtocol)protocol);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void registerPackets() {
/*  45 */     registerMetadataRewriter((ClientboundPacketType)ClientboundPackets1_18.ENTITY_METADATA, Types1_18.METADATA_LIST, Types1_17.METADATA_LIST);
/*     */     
/*  47 */     ((Protocol1_17_1To1_18)this.protocol).registerClientbound((ClientboundPacketType)ClientboundPackets1_18.JOIN_GAME, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/*  50 */             map((Type)Type.INT);
/*  51 */             map((Type)Type.BOOLEAN);
/*  52 */             map((Type)Type.UNSIGNED_BYTE);
/*  53 */             map((Type)Type.BYTE);
/*  54 */             map(Type.STRING_ARRAY);
/*  55 */             map(Type.NBT);
/*  56 */             map(Type.NBT);
/*  57 */             map(Type.STRING);
/*  58 */             map((Type)Type.LONG);
/*  59 */             map((Type)Type.VAR_INT);
/*  60 */             map((Type)Type.VAR_INT);
/*  61 */             read((Type)Type.VAR_INT);
/*  62 */             handler(EntityPackets1_18.this.worldDataTrackerHandler(1));
/*  63 */             handler(wrapper -> {
/*     */                   CompoundTag registry = (CompoundTag)wrapper.get(Type.NBT, 0);
/*     */                   
/*     */                   CompoundTag biomeRegistry = (CompoundTag)registry.get("minecraft:worldgen/biome");
/*     */                   
/*     */                   ListTag biomes = (ListTag)biomeRegistry.get("value");
/*     */                   
/*     */                   for (Tag biome : biomes.getValue()) {
/*     */                     CompoundTag biomeCompound = (CompoundTag)((CompoundTag)biome).get("element");
/*     */                     
/*     */                     StringTag category = (StringTag)biomeCompound.get("category");
/*     */                     
/*     */                     if (category.getValue().equals("mountain")) {
/*     */                       category.setValue("extreme_hills");
/*     */                     }
/*     */                     biomeCompound.put("depth", (Tag)new FloatTag(0.125F));
/*     */                     biomeCompound.put("scale", (Tag)new FloatTag(0.05F));
/*     */                   } 
/*     */                   EntityPackets1_18.this.tracker(wrapper.user()).setBiomesSent(biomes.size());
/*     */                 });
/*     */           }
/*     */         });
/*  85 */     ((Protocol1_17_1To1_18)this.protocol).registerClientbound((ClientboundPacketType)ClientboundPackets1_18.RESPAWN, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/*  88 */             map(Type.NBT);
/*  89 */             map(Type.STRING);
/*  90 */             handler(EntityPackets1_18.this.worldDataTrackerHandler(0));
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */   
/*     */   protected void registerRewrites() {
/*  97 */     filter().handler((event, meta) -> {
/*     */           meta.setMetaType(Types1_17.META_TYPES.byId(meta.metaType().typeId()));
/*     */           
/*     */           MetaType type = meta.metaType();
/*     */           
/*     */           if (type == Types1_17.META_TYPES.particleType) {
/*     */             Particle particle = (Particle)meta.getValue();
/*     */             
/*     */             if (particle.getId() == 3) {
/*     */               Particle.ParticleData data = particle.getArguments().remove(0);
/*     */               
/*     */               int blockState = ((Integer)data.getValue()).intValue();
/*     */               
/*     */               if (blockState == 7786) {
/*     */                 particle.setId(3);
/*     */               } else {
/*     */                 particle.setId(2);
/*     */               } 
/*     */               return;
/*     */             } 
/*     */             rewriteParticle(particle);
/*     */           } 
/*     */         });
/* 120 */     registerMetaTypeHandler(Types1_17.META_TYPES.itemType, null, null, null, Types1_17.META_TYPES.componentType, Types1_17.META_TYPES.optionalComponentType);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public EntityType typeFromId(int typeId) {
/* 126 */     return Entity1_17Types.getTypeFromId(typeId);
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viabackwards\protocol\protocol1_17_1to1_18\packets\EntityPackets1_18.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */