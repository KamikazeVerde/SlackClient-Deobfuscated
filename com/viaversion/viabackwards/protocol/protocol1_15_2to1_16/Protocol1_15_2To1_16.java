/*     */ package com.viaversion.viabackwards.protocol.protocol1_15_2to1_16;
/*     */ 
/*     */ import com.viaversion.viabackwards.api.BackwardsProtocol;
/*     */ import com.viaversion.viabackwards.api.data.BackwardsMappings;
/*     */ import com.viaversion.viabackwards.api.rewriters.SoundRewriter;
/*     */ import com.viaversion.viabackwards.api.rewriters.TranslatableRewriter;
/*     */ import com.viaversion.viabackwards.protocol.protocol1_15_2to1_16.chat.TranslatableRewriter1_16;
/*     */ import com.viaversion.viabackwards.protocol.protocol1_15_2to1_16.data.BackwardsMappings;
/*     */ import com.viaversion.viabackwards.protocol.protocol1_15_2to1_16.data.CommandRewriter1_16;
/*     */ import com.viaversion.viabackwards.protocol.protocol1_15_2to1_16.data.WorldNameTracker;
/*     */ import com.viaversion.viabackwards.protocol.protocol1_15_2to1_16.packets.BlockItemPackets1_16;
/*     */ import com.viaversion.viabackwards.protocol.protocol1_15_2to1_16.packets.EntityPackets1_16;
/*     */ import com.viaversion.viabackwards.protocol.protocol1_15_2to1_16.storage.PlayerSneakStorage;
/*     */ import com.viaversion.viaversion.api.connection.StorableObject;
/*     */ import com.viaversion.viaversion.api.connection.UserConnection;
/*     */ import com.viaversion.viaversion.api.data.MappingData;
/*     */ import com.viaversion.viaversion.api.data.entity.EntityTracker;
/*     */ import com.viaversion.viaversion.api.minecraft.RegistryType;
/*     */ import com.viaversion.viaversion.api.minecraft.entities.Entity1_16Types;
/*     */ import com.viaversion.viaversion.api.minecraft.entities.EntityType;
/*     */ import com.viaversion.viaversion.api.protocol.Protocol;
/*     */ import com.viaversion.viaversion.api.protocol.packet.ClientboundPacketType;
/*     */ import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
/*     */ import com.viaversion.viaversion.api.protocol.packet.ServerboundPacketType;
/*     */ import com.viaversion.viaversion.api.protocol.packet.State;
/*     */ import com.viaversion.viaversion.api.protocol.remapper.PacketHandler;
/*     */ import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
/*     */ import com.viaversion.viaversion.api.rewriter.EntityRewriter;
/*     */ import com.viaversion.viaversion.api.rewriter.ItemRewriter;
/*     */ import com.viaversion.viaversion.api.type.Type;
/*     */ import com.viaversion.viaversion.data.entity.EntityTrackerBase;
/*     */ import com.viaversion.viaversion.libs.gson.JsonElement;
/*     */ import com.viaversion.viaversion.libs.gson.JsonObject;
/*     */ import com.viaversion.viaversion.protocols.protocol1_14to1_13_2.ServerboundPackets1_14;
/*     */ import com.viaversion.viaversion.protocols.protocol1_15to1_14_4.ClientboundPackets1_15;
/*     */ import com.viaversion.viaversion.protocols.protocol1_16to1_15_2.ClientboundPackets1_16;
/*     */ import com.viaversion.viaversion.protocols.protocol1_16to1_15_2.ServerboundPackets1_16;
/*     */ import com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2.storage.ClientWorld;
/*     */ import com.viaversion.viaversion.rewriter.StatisticsRewriter;
/*     */ import com.viaversion.viaversion.rewriter.TagRewriter;
/*     */ import com.viaversion.viaversion.util.GsonUtil;
/*     */ import java.util.UUID;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Protocol1_15_2To1_16
/*     */   extends BackwardsProtocol<ClientboundPackets1_16, ClientboundPackets1_15, ServerboundPackets1_16, ServerboundPackets1_14>
/*     */ {
/*  50 */   public static final BackwardsMappings MAPPINGS = new BackwardsMappings();
/*  51 */   private final EntityPackets1_16 entityRewriter = new EntityPackets1_16(this);
/*  52 */   private final BlockItemPackets1_16 blockItemPackets = new BlockItemPackets1_16(this);
/*  53 */   private final TranslatableRewriter1_16 translatableRewriter = new TranslatableRewriter1_16(this);
/*     */   
/*     */   public Protocol1_15_2To1_16() {
/*  56 */     super(ClientboundPackets1_16.class, ClientboundPackets1_15.class, ServerboundPackets1_16.class, ServerboundPackets1_14.class);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void registerPackets() {
/*  61 */     super.registerPackets();
/*     */     
/*  63 */     this.translatableRewriter.registerBossBar((ClientboundPacketType)ClientboundPackets1_16.BOSSBAR);
/*  64 */     this.translatableRewriter.registerCombatEvent((ClientboundPacketType)ClientboundPackets1_16.COMBAT_EVENT);
/*  65 */     this.translatableRewriter.registerDisconnect((ClientboundPacketType)ClientboundPackets1_16.DISCONNECT);
/*  66 */     this.translatableRewriter.registerTabList((ClientboundPacketType)ClientboundPackets1_16.TAB_LIST);
/*  67 */     this.translatableRewriter.registerTitle((ClientboundPacketType)ClientboundPackets1_16.TITLE);
/*  68 */     this.translatableRewriter.registerPing();
/*     */     
/*  70 */     (new CommandRewriter1_16(this)).registerDeclareCommands((ClientboundPacketType)ClientboundPackets1_16.DECLARE_COMMANDS);
/*     */     
/*  72 */     registerClientbound(State.STATUS, 0, 0, wrapper -> {
/*     */           String original = (String)wrapper.passthrough(Type.STRING);
/*     */           JsonObject object = (JsonObject)GsonUtil.getGson().fromJson(original, JsonObject.class);
/*     */           JsonElement description = object.get("description");
/*     */           if (description == null) {
/*     */             return;
/*     */           }
/*     */           this.translatableRewriter.processText(description);
/*     */           wrapper.set(Type.STRING, 0, object.toString());
/*     */         });
/*  82 */     registerClientbound((ClientboundPacketType)ClientboundPackets1_16.CHAT_MESSAGE, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/*  85 */             handler(wrapper -> Protocol1_15_2To1_16.this.translatableRewriter.processText((JsonElement)wrapper.passthrough(Type.COMPONENT)));
/*  86 */             map((Type)Type.BYTE);
/*  87 */             map(Type.UUID, (Type)Type.NOTHING);
/*     */           }
/*     */         });
/*     */     
/*  91 */     registerClientbound((ClientboundPacketType)ClientboundPackets1_16.OPEN_WINDOW, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/*  94 */             map((Type)Type.VAR_INT);
/*  95 */             map((Type)Type.VAR_INT);
/*  96 */             handler(wrapper -> Protocol1_15_2To1_16.this.translatableRewriter.processText((JsonElement)wrapper.passthrough(Type.COMPONENT)));
/*  97 */             handler(wrapper -> {
/*     */                   int windowType = ((Integer)wrapper.get((Type)Type.VAR_INT, 1)).intValue();
/*     */                   
/*     */                   if (windowType == 20) {
/*     */                     wrapper.set((Type)Type.VAR_INT, 1, Integer.valueOf(7));
/*     */                   } else if (windowType > 20) {
/*     */                     wrapper.set((Type)Type.VAR_INT, 1, Integer.valueOf(--windowType));
/*     */                   } 
/*     */                 });
/*     */           }
/*     */         });
/* 108 */     SoundRewriter<ClientboundPackets1_16> soundRewriter = new SoundRewriter(this);
/* 109 */     soundRewriter.registerSound((ClientboundPacketType)ClientboundPackets1_16.SOUND);
/* 110 */     soundRewriter.registerSound((ClientboundPacketType)ClientboundPackets1_16.ENTITY_SOUND);
/* 111 */     soundRewriter.registerNamedSound((ClientboundPacketType)ClientboundPackets1_16.NAMED_SOUND);
/* 112 */     soundRewriter.registerStopSound((ClientboundPacketType)ClientboundPackets1_16.STOP_SOUND);
/*     */ 
/*     */     
/* 115 */     registerClientbound(State.LOGIN, 2, 2, wrapper -> {
/*     */           UUID uuid = (UUID)wrapper.read(Type.UUID);
/*     */           
/*     */           wrapper.write(Type.STRING, uuid.toString());
/*     */         });
/*     */     
/* 121 */     (new TagRewriter((Protocol)this)).register((ClientboundPacketType)ClientboundPackets1_16.TAGS, RegistryType.ENTITY);
/*     */     
/* 123 */     (new StatisticsRewriter((Protocol)this)).register((ClientboundPacketType)ClientboundPackets1_16.STATISTICS);
/*     */     
/* 125 */     registerServerbound((ServerboundPacketType)ServerboundPackets1_14.ENTITY_ACTION, wrapper -> {
/*     */           wrapper.passthrough((Type)Type.VAR_INT);
/*     */           
/*     */           int action = ((Integer)wrapper.passthrough((Type)Type.VAR_INT)).intValue();
/*     */           if (action == 0) {
/*     */             ((PlayerSneakStorage)wrapper.user().get(PlayerSneakStorage.class)).setSneaking(true);
/*     */           } else if (action == 1) {
/*     */             ((PlayerSneakStorage)wrapper.user().get(PlayerSneakStorage.class)).setSneaking(false);
/*     */           } 
/*     */         });
/* 135 */     registerServerbound((ServerboundPacketType)ServerboundPackets1_14.INTERACT_ENTITY, wrapper -> {
/*     */           wrapper.passthrough((Type)Type.VAR_INT);
/*     */           
/*     */           int action = ((Integer)wrapper.passthrough((Type)Type.VAR_INT)).intValue();
/*     */           
/*     */           if (action == 0 || action == 2) {
/*     */             if (action == 2) {
/*     */               wrapper.passthrough((Type)Type.FLOAT);
/*     */               
/*     */               wrapper.passthrough((Type)Type.FLOAT);
/*     */               
/*     */               wrapper.passthrough((Type)Type.FLOAT);
/*     */             } 
/*     */             
/*     */             wrapper.passthrough((Type)Type.VAR_INT);
/*     */           } 
/*     */           wrapper.write((Type)Type.BOOLEAN, Boolean.valueOf(((PlayerSneakStorage)wrapper.user().get(PlayerSneakStorage.class)).isSneaking()));
/*     */         });
/* 153 */     registerServerbound((ServerboundPacketType)ServerboundPackets1_14.PLAYER_ABILITIES, wrapper -> {
/*     */           byte flags = ((Byte)wrapper.read((Type)Type.BYTE)).byteValue();
/*     */           
/*     */           flags = (byte)(flags & 0x2);
/*     */           
/*     */           wrapper.write((Type)Type.BYTE, Byte.valueOf(flags));
/*     */           wrapper.read((Type)Type.FLOAT);
/*     */           wrapper.read((Type)Type.FLOAT);
/*     */         });
/* 162 */     cancelServerbound((ServerboundPacketType)ServerboundPackets1_14.UPDATE_JIGSAW_BLOCK);
/*     */   }
/*     */ 
/*     */   
/*     */   public void init(UserConnection user) {
/* 167 */     if (!user.has(ClientWorld.class)) {
/* 168 */       user.put((StorableObject)new ClientWorld(user));
/*     */     }
/*     */     
/* 171 */     user.put((StorableObject)new PlayerSneakStorage());
/* 172 */     user.put((StorableObject)new WorldNameTracker());
/* 173 */     user.addEntityTracker(getClass(), (EntityTracker)new EntityTrackerBase(user, (EntityType)Entity1_16Types.PLAYER));
/*     */   }
/*     */ 
/*     */   
/*     */   public TranslatableRewriter1_16 getTranslatableRewriter() {
/* 178 */     return this.translatableRewriter;
/*     */   }
/*     */ 
/*     */   
/*     */   public BackwardsMappings getMappingData() {
/* 183 */     return MAPPINGS;
/*     */   }
/*     */ 
/*     */   
/*     */   public EntityPackets1_16 getEntityRewriter() {
/* 188 */     return this.entityRewriter;
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockItemPackets1_16 getItemRewriter() {
/* 193 */     return this.blockItemPackets;
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viabackwards\protocol\protocol1_15_2to1_16\Protocol1_15_2To1_16.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */