/*     */ package com.viaversion.viabackwards.protocol.protocol1_9_4to1_10;
/*     */ 
/*     */ import com.viaversion.viabackwards.api.BackwardsProtocol;
/*     */ import com.viaversion.viabackwards.api.data.BackwardsMappings;
/*     */ import com.viaversion.viabackwards.api.rewriters.SoundRewriter;
/*     */ import com.viaversion.viabackwards.protocol.protocol1_9_4to1_10.packets.BlockItemPackets1_10;
/*     */ import com.viaversion.viabackwards.protocol.protocol1_9_4to1_10.packets.EntityPackets1_10;
/*     */ import com.viaversion.viaversion.api.connection.StorableObject;
/*     */ import com.viaversion.viaversion.api.connection.UserConnection;
/*     */ import com.viaversion.viaversion.api.data.MappingData;
/*     */ import com.viaversion.viaversion.api.data.entity.EntityTracker;
/*     */ import com.viaversion.viaversion.api.minecraft.entities.Entity1_10Types;
/*     */ import com.viaversion.viaversion.api.minecraft.entities.EntityType;
/*     */ import com.viaversion.viaversion.api.protocol.packet.ClientboundPacketType;
/*     */ import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
/*     */ import com.viaversion.viaversion.api.protocol.packet.ServerboundPacketType;
/*     */ import com.viaversion.viaversion.api.protocol.remapper.PacketHandler;
/*     */ import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
/*     */ import com.viaversion.viaversion.api.protocol.remapper.ValueTransformer;
/*     */ import com.viaversion.viaversion.api.rewriter.EntityRewriter;
/*     */ import com.viaversion.viaversion.api.rewriter.ItemRewriter;
/*     */ import com.viaversion.viaversion.api.type.Type;
/*     */ import com.viaversion.viaversion.data.entity.EntityTrackerBase;
/*     */ import com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2.ClientboundPackets1_9_3;
/*     */ import com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2.ServerboundPackets1_9_3;
/*     */ import com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2.storage.ClientWorld;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Protocol1_9_4To1_10
/*     */   extends BackwardsProtocol<ClientboundPackets1_9_3, ClientboundPackets1_9_3, ServerboundPackets1_9_3, ServerboundPackets1_9_3>
/*     */ {
/*  39 */   public static final BackwardsMappings MAPPINGS = new BackwardsMappings("1.10", "1.9.4");
/*  40 */   private static final ValueTransformer<Float, Short> TO_OLD_PITCH = new ValueTransformer<Float, Short>((Type)Type.UNSIGNED_BYTE) {
/*     */       public Short transform(PacketWrapper packetWrapper, Float inputValue) throws Exception {
/*  42 */         return Short.valueOf((short)Math.round(inputValue.floatValue() * 63.5F));
/*     */       }
/*     */     };
/*  45 */   private final EntityPackets1_10 entityPackets = new EntityPackets1_10(this);
/*  46 */   private final BlockItemPackets1_10 blockItemPackets = new BlockItemPackets1_10(this);
/*     */   
/*     */   public Protocol1_9_4To1_10() {
/*  49 */     super(ClientboundPackets1_9_3.class, ClientboundPackets1_9_3.class, ServerboundPackets1_9_3.class, ServerboundPackets1_9_3.class);
/*     */   }
/*     */   
/*     */   protected void registerPackets() {
/*  53 */     this.entityPackets.register();
/*  54 */     this.blockItemPackets.register();
/*     */     
/*  56 */     final SoundRewriter<ClientboundPackets1_9_3> soundRewriter = new SoundRewriter(this);
/*  57 */     registerClientbound((ClientboundPacketType)ClientboundPackets1_9_3.NAMED_SOUND, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/*  60 */             map(Type.STRING);
/*  61 */             map((Type)Type.VAR_INT);
/*  62 */             map((Type)Type.INT);
/*  63 */             map((Type)Type.INT);
/*  64 */             map((Type)Type.INT);
/*  65 */             map((Type)Type.FLOAT);
/*  66 */             map((Type)Type.FLOAT, Protocol1_9_4To1_10.TO_OLD_PITCH);
/*  67 */             handler(soundRewriter.getNamedSoundHandler());
/*     */           }
/*     */         });
/*  70 */     registerClientbound((ClientboundPacketType)ClientboundPackets1_9_3.SOUND, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/*  73 */             map((Type)Type.VAR_INT);
/*  74 */             map((Type)Type.VAR_INT);
/*  75 */             map((Type)Type.INT);
/*  76 */             map((Type)Type.INT);
/*  77 */             map((Type)Type.INT);
/*  78 */             map((Type)Type.FLOAT);
/*  79 */             map((Type)Type.FLOAT, Protocol1_9_4To1_10.TO_OLD_PITCH);
/*  80 */             handler(soundRewriter.getSoundHandler());
/*     */           }
/*     */         });
/*     */     
/*  84 */     registerServerbound((ServerboundPacketType)ServerboundPackets1_9_3.RESOURCE_PACK_STATUS, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/*  87 */             map(Type.STRING, (Type)Type.NOTHING);
/*  88 */             map((Type)Type.VAR_INT);
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */   
/*     */   public void init(UserConnection user) {
/*  95 */     if (!user.has(ClientWorld.class)) {
/*  96 */       user.put((StorableObject)new ClientWorld(user));
/*     */     }
/*     */     
/*  99 */     user.addEntityTracker(getClass(), (EntityTracker)new EntityTrackerBase(user, (EntityType)Entity1_10Types.EntityType.PLAYER));
/*     */   }
/*     */ 
/*     */   
/*     */   public BackwardsMappings getMappingData() {
/* 104 */     return MAPPINGS;
/*     */   }
/*     */ 
/*     */   
/*     */   public EntityPackets1_10 getEntityRewriter() {
/* 109 */     return this.entityPackets;
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockItemPackets1_10 getItemRewriter() {
/* 114 */     return this.blockItemPackets;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasMappingDataToLoad() {
/* 119 */     return true;
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viabackwards\protocol\protocol1_9_4to1_10\Protocol1_9_4To1_10.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */