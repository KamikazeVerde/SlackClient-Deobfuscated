/*     */ package com.viaversion.viaversion.protocols.protocol1_18to1_17_1.packets;
/*     */ 
/*     */ import com.viaversion.viaversion.api.data.entity.EntityTracker;
/*     */ import com.viaversion.viaversion.api.minecraft.entities.Entity1_17Types;
/*     */ import com.viaversion.viaversion.api.minecraft.entities.EntityType;
/*     */ import com.viaversion.viaversion.api.minecraft.metadata.Metadata;
/*     */ import com.viaversion.viaversion.api.protocol.Protocol;
/*     */ import com.viaversion.viaversion.api.protocol.packet.ClientboundPacketType;
/*     */ import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
/*     */ import com.viaversion.viaversion.api.protocol.remapper.PacketHandler;
/*     */ import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
/*     */ import com.viaversion.viaversion.api.type.Type;
/*     */ import com.viaversion.viaversion.api.type.types.Particle;
/*     */ import com.viaversion.viaversion.api.type.types.version.Types1_17;
/*     */ import com.viaversion.viaversion.api.type.types.version.Types1_18;
/*     */ import com.viaversion.viaversion.protocols.protocol1_17_1to1_17.ClientboundPackets1_17_1;
/*     */ import com.viaversion.viaversion.protocols.protocol1_18to1_17_1.Protocol1_18To1_17_1;
/*     */ import com.viaversion.viaversion.protocols.protocol1_18to1_17_1.storage.ChunkLightStorage;
/*     */ import com.viaversion.viaversion.rewriter.EntityRewriter;
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
/*     */ public final class EntityPackets
/*     */   extends EntityRewriter<ClientboundPackets1_17_1, Protocol1_18To1_17_1>
/*     */ {
/*     */   public EntityPackets(Protocol1_18To1_17_1 protocol) {
/*  36 */     super((Protocol)protocol);
/*     */   }
/*     */ 
/*     */   
/*     */   public void registerPackets() {
/*  41 */     registerMetadataRewriter((ClientboundPacketType)ClientboundPackets1_17_1.ENTITY_METADATA, Types1_17.METADATA_LIST, Types1_18.METADATA_LIST);
/*     */     
/*  43 */     ((Protocol1_18To1_17_1)this.protocol).registerClientbound((ClientboundPacketType)ClientboundPackets1_17_1.JOIN_GAME, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/*  46 */             map((Type)Type.INT);
/*  47 */             map((Type)Type.BOOLEAN);
/*  48 */             map((Type)Type.UNSIGNED_BYTE);
/*  49 */             map((Type)Type.BYTE);
/*  50 */             map(Type.STRING_ARRAY);
/*  51 */             map(Type.NBT);
/*  52 */             map(Type.NBT);
/*  53 */             map(Type.STRING);
/*  54 */             map((Type)Type.LONG);
/*  55 */             map((Type)Type.VAR_INT);
/*  56 */             handler(wrapper -> {
/*     */                   int chunkRadius = ((Integer)wrapper.passthrough((Type)Type.VAR_INT)).intValue();
/*     */                   wrapper.write((Type)Type.VAR_INT, Integer.valueOf(chunkRadius));
/*     */                 });
/*  60 */             handler(EntityPackets.this.worldDataTrackerHandler(1));
/*  61 */             handler(EntityPackets.this.biomeSizeTracker());
/*     */           }
/*     */         });
/*     */     
/*  65 */     ((Protocol1_18To1_17_1)this.protocol).registerClientbound((ClientboundPacketType)ClientboundPackets1_17_1.RESPAWN, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/*  68 */             map(Type.NBT);
/*  69 */             map(Type.STRING);
/*  70 */             handler(wrapper -> {
/*     */                   String world = (String)wrapper.get(Type.STRING, 0);
/*     */                   EntityTracker tracker = EntityPackets.this.tracker(wrapper.user());
/*     */                   if (!world.equals(tracker.currentWorld())) {
/*     */                     ((ChunkLightStorage)wrapper.user().get(ChunkLightStorage.class)).clear();
/*     */                   }
/*     */                 });
/*  77 */             handler(EntityPackets.this.worldDataTrackerHandler(0));
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */   
/*     */   protected void registerRewrites() {
/*  84 */     filter().handler((event, meta) -> {
/*     */           meta.setMetaType(Types1_18.META_TYPES.byId(meta.metaType().typeId()));
/*     */           
/*     */           if (meta.metaType() == Types1_18.META_TYPES.particleType) {
/*     */             Particle particle = (Particle)meta.getValue();
/*     */             if (particle.getId() == 2) {
/*     */               particle.setId(3);
/*     */               particle.getArguments().add(new Particle.ParticleData((Type)Type.VAR_INT, Integer.valueOf(7754)));
/*     */             } else if (particle.getId() == 3) {
/*     */               particle.getArguments().add(new Particle.ParticleData((Type)Type.VAR_INT, Integer.valueOf(7786)));
/*     */             } else {
/*     */               rewriteParticle(particle);
/*     */             } 
/*     */           } 
/*     */         });
/*  99 */     registerMetaTypeHandler(Types1_18.META_TYPES.itemType, null, null, null);
/*     */   }
/*     */ 
/*     */   
/*     */   public EntityType typeFromId(int type) {
/* 104 */     return Entity1_17Types.getTypeFromId(type);
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\protocols\protocol1_18to1_17_1\packets\EntityPackets.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */