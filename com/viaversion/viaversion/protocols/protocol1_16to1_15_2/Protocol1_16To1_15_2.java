/*     */ package com.viaversion.viaversion.protocols.protocol1_16to1_15_2;
/*     */ 
/*     */ import com.google.common.base.Joiner;
/*     */ import com.viaversion.viaversion.api.Via;
/*     */ import com.viaversion.viaversion.api.connection.StorableObject;
/*     */ import com.viaversion.viaversion.api.connection.UserConnection;
/*     */ import com.viaversion.viaversion.api.data.MappingData;
/*     */ import com.viaversion.viaversion.api.data.entity.EntityTracker;
/*     */ import com.viaversion.viaversion.api.minecraft.RegistryType;
/*     */ import com.viaversion.viaversion.api.minecraft.entities.Entity1_16Types;
/*     */ import com.viaversion.viaversion.api.minecraft.entities.EntityType;
/*     */ import com.viaversion.viaversion.api.platform.providers.Provider;
/*     */ import com.viaversion.viaversion.api.platform.providers.ViaProviders;
/*     */ import com.viaversion.viaversion.api.protocol.AbstractProtocol;
/*     */ import com.viaversion.viaversion.api.protocol.Protocol;
/*     */ import com.viaversion.viaversion.api.protocol.packet.ClientboundPacketType;
/*     */ import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
/*     */ import com.viaversion.viaversion.api.protocol.packet.State;
/*     */ import com.viaversion.viaversion.api.protocol.remapper.PacketHandler;
/*     */ import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
/*     */ import com.viaversion.viaversion.api.rewriter.EntityRewriter;
/*     */ import com.viaversion.viaversion.api.rewriter.ItemRewriter;
/*     */ import com.viaversion.viaversion.api.type.Type;
/*     */ import com.viaversion.viaversion.api.type.types.minecraft.ParticleType;
/*     */ import com.viaversion.viaversion.api.type.types.version.Types1_16;
/*     */ import com.viaversion.viaversion.data.entity.EntityTrackerBase;
/*     */ import com.viaversion.viaversion.libs.gson.JsonArray;
/*     */ import com.viaversion.viaversion.libs.gson.JsonElement;
/*     */ import com.viaversion.viaversion.libs.gson.JsonObject;
/*     */ import com.viaversion.viaversion.protocols.protocol1_14to1_13_2.ServerboundPackets1_14;
/*     */ import com.viaversion.viaversion.protocols.protocol1_15to1_14_4.ClientboundPackets1_15;
/*     */ import com.viaversion.viaversion.protocols.protocol1_16to1_15_2.data.MappingData;
/*     */ import com.viaversion.viaversion.protocols.protocol1_16to1_15_2.data.TranslationMappings;
/*     */ import com.viaversion.viaversion.protocols.protocol1_16to1_15_2.metadata.MetadataRewriter1_16To1_15_2;
/*     */ import com.viaversion.viaversion.protocols.protocol1_16to1_15_2.packets.EntityPackets;
/*     */ import com.viaversion.viaversion.protocols.protocol1_16to1_15_2.packets.InventoryPackets;
/*     */ import com.viaversion.viaversion.protocols.protocol1_16to1_15_2.packets.WorldPackets;
/*     */ import com.viaversion.viaversion.protocols.protocol1_16to1_15_2.provider.PlayerAbilitiesProvider;
/*     */ import com.viaversion.viaversion.protocols.protocol1_16to1_15_2.storage.InventoryTracker1_16;
/*     */ import com.viaversion.viaversion.rewriter.SoundRewriter;
/*     */ import com.viaversion.viaversion.rewriter.StatisticsRewriter;
/*     */ import com.viaversion.viaversion.rewriter.TagRewriter;
/*     */ import com.viaversion.viaversion.util.GsonUtil;
/*     */ import java.nio.charset.StandardCharsets;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.UUID;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Protocol1_16To1_15_2
/*     */   extends AbstractProtocol<ClientboundPackets1_15, ClientboundPackets1_16, ServerboundPackets1_14, ServerboundPackets1_16>
/*     */ {
/*  57 */   private static final UUID ZERO_UUID = new UUID(0L, 0L);
/*  58 */   public static final MappingData MAPPINGS = new MappingData();
/*  59 */   private final MetadataRewriter1_16To1_15_2 metadataRewriter = new MetadataRewriter1_16To1_15_2(this);
/*  60 */   private final InventoryPackets itemRewriter = new InventoryPackets(this);
/*  61 */   private final TranslationMappings componentRewriter = new TranslationMappings(this);
/*     */   private TagRewriter<ClientboundPackets1_15> tagRewriter;
/*     */   
/*     */   public Protocol1_16To1_15_2() {
/*  65 */     super(ClientboundPackets1_15.class, ClientboundPackets1_16.class, ServerboundPackets1_14.class, ServerboundPackets1_16.class);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void registerPackets() {
/*  70 */     this.metadataRewriter.register();
/*  71 */     this.itemRewriter.register();
/*     */     
/*  73 */     EntityPackets.register(this);
/*  74 */     WorldPackets.register(this);
/*     */     
/*  76 */     this.tagRewriter = new TagRewriter((Protocol)this);
/*  77 */     this.tagRewriter.register((ClientboundPacketType)ClientboundPackets1_15.TAGS, RegistryType.ENTITY);
/*     */     
/*  79 */     (new StatisticsRewriter((Protocol)this)).register((ClientboundPacketType)ClientboundPackets1_15.STATISTICS);
/*     */ 
/*     */     
/*  82 */     registerClientbound(State.LOGIN, 2, 2, wrapper -> {
/*     */           UUID uuid = UUID.fromString((String)wrapper.read(Type.STRING));
/*     */ 
/*     */           
/*     */           wrapper.write(Type.UUID, uuid);
/*     */         });
/*     */     
/*  89 */     registerClientbound(State.STATUS, 0, 0, wrapper -> {
/*     */           String original = (String)wrapper.passthrough(Type.STRING);
/*     */           
/*     */           JsonObject object = (JsonObject)GsonUtil.getGson().fromJson(original, JsonObject.class);
/*     */           
/*     */           JsonObject players = object.getAsJsonObject("players");
/*     */           
/*     */           if (players == null) {
/*     */             return;
/*     */           }
/*     */           JsonArray sample = players.getAsJsonArray("sample");
/*     */           if (sample == null) {
/*     */             return;
/*     */           }
/*     */           JsonArray splitSamples = new JsonArray();
/*     */           for (JsonElement element : sample) {
/*     */             JsonObject playerInfo = element.getAsJsonObject();
/*     */             String name = playerInfo.getAsJsonPrimitive("name").getAsString();
/*     */             if (name.indexOf('\n') == -1) {
/*     */               splitSamples.add((JsonElement)playerInfo);
/*     */               continue;
/*     */             } 
/*     */             String id = playerInfo.getAsJsonPrimitive("id").getAsString();
/*     */             for (String s : name.split("\n")) {
/*     */               JsonObject newSample = new JsonObject();
/*     */               newSample.addProperty("name", s);
/*     */               newSample.addProperty("id", id);
/*     */               splitSamples.add((JsonElement)newSample);
/*     */             } 
/*     */           } 
/*     */           if (splitSamples.size() != sample.size()) {
/*     */             players.add("sample", (JsonElement)splitSamples);
/*     */             wrapper.set(Type.STRING, 0, object.toString());
/*     */           } 
/*     */         });
/* 124 */     registerClientbound((ClientboundPacketType)ClientboundPackets1_15.CHAT_MESSAGE, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/* 127 */             map(Type.COMPONENT);
/* 128 */             map((Type)Type.BYTE);
/* 129 */             handler(wrapper -> {
/*     */                   Protocol1_16To1_15_2.this.componentRewriter.processText((JsonElement)wrapper.get(Type.COMPONENT, 0));
/*     */                   wrapper.write(Type.UUID, Protocol1_16To1_15_2.ZERO_UUID);
/*     */                 });
/*     */           }
/*     */         });
/* 135 */     this.componentRewriter.registerBossBar((ClientboundPacketType)ClientboundPackets1_15.BOSSBAR);
/* 136 */     this.componentRewriter.registerTitle((ClientboundPacketType)ClientboundPackets1_15.TITLE);
/* 137 */     this.componentRewriter.registerCombatEvent((ClientboundPacketType)ClientboundPackets1_15.COMBAT_EVENT);
/*     */     
/* 139 */     SoundRewriter<ClientboundPackets1_15> soundRewriter = new SoundRewriter((Protocol)this);
/* 140 */     soundRewriter.registerSound((ClientboundPacketType)ClientboundPackets1_15.SOUND);
/* 141 */     soundRewriter.registerSound((ClientboundPacketType)ClientboundPackets1_15.ENTITY_SOUND);
/*     */     
/* 143 */     registerServerbound(ServerboundPackets1_16.INTERACT_ENTITY, wrapper -> {
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
/*     */           wrapper.read((Type)Type.BOOLEAN);
/*     */         });
/* 161 */     if (Via.getConfig().isIgnoreLong1_16ChannelNames()) {
/* 162 */       registerServerbound(ServerboundPackets1_16.PLUGIN_MESSAGE, (PacketHandler)new PacketHandlers()
/*     */           {
/*     */             public void register() {
/* 165 */               handler(wrapper -> {
/*     */                     String channel = (String)wrapper.passthrough(Type.STRING);
/*     */                     
/*     */                     if (channel.length() > 32) {
/*     */                       if (!Via.getConfig().isSuppressConversionWarnings()) {
/*     */                         Via.getPlatform().getLogger().warning("Ignoring incoming plugin channel, as it is longer than 32 characters: " + channel);
/*     */                       }
/*     */                       
/*     */                       wrapper.cancel();
/*     */                     } else if (channel.equals("minecraft:register") || channel.equals("minecraft:unregister")) {
/*     */                       String[] channels = (new String((byte[])wrapper.read(Type.REMAINING_BYTES), StandardCharsets.UTF_8)).split("\000");
/*     */                       
/*     */                       List<String> checkedChannels = new ArrayList<>(channels.length);
/*     */                       
/*     */                       for (String registeredChannel : channels) {
/*     */                         if (registeredChannel.length() > 32) {
/*     */                           if (!Via.getConfig().isSuppressConversionWarnings()) {
/*     */                             Via.getPlatform().getLogger().warning("Ignoring incoming plugin channel register of '" + registeredChannel + "', as it is longer than 32 characters");
/*     */                           }
/*     */                         } else {
/*     */                           checkedChannels.add(registeredChannel);
/*     */                         } 
/*     */                       } 
/*     */                       
/*     */                       if (checkedChannels.isEmpty()) {
/*     */                         wrapper.cancel();
/*     */                         return;
/*     */                       } 
/*     */                       wrapper.write(Type.REMAINING_BYTES, Joiner.on(false).join(checkedChannels).getBytes(StandardCharsets.UTF_8));
/*     */                     } 
/*     */                   });
/*     */             }
/*     */           });
/*     */     }
/* 199 */     registerServerbound(ServerboundPackets1_16.PLAYER_ABILITIES, wrapper -> {
/*     */           wrapper.passthrough((Type)Type.BYTE);
/*     */           
/*     */           PlayerAbilitiesProvider playerAbilities = (PlayerAbilitiesProvider)Via.getManager().getProviders().get(PlayerAbilitiesProvider.class);
/*     */           
/*     */           wrapper.write((Type)Type.FLOAT, Float.valueOf(playerAbilities.getFlyingSpeed(wrapper.user())));
/*     */           wrapper.write((Type)Type.FLOAT, Float.valueOf(playerAbilities.getWalkingSpeed(wrapper.user())));
/*     */         });
/* 207 */     cancelServerbound(ServerboundPackets1_16.GENERATE_JIGSAW);
/* 208 */     cancelServerbound(ServerboundPackets1_16.UPDATE_JIGSAW_BLOCK);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void onMappingDataLoaded() {
/* 213 */     int[] wallPostOverrideTag = new int[47];
/* 214 */     int arrayIndex = 0;
/* 215 */     wallPostOverrideTag[arrayIndex++] = 140;
/* 216 */     wallPostOverrideTag[arrayIndex++] = 179;
/* 217 */     wallPostOverrideTag[arrayIndex++] = 264; int i;
/* 218 */     for (i = 153; i <= 158; i++) {
/* 219 */       wallPostOverrideTag[arrayIndex++] = i;
/*     */     }
/* 221 */     for (i = 163; i <= 168; i++) {
/* 222 */       wallPostOverrideTag[arrayIndex++] = i;
/*     */     }
/* 224 */     for (i = 408; i <= 439; i++) {
/* 225 */       wallPostOverrideTag[arrayIndex++] = i;
/*     */     }
/*     */     
/* 228 */     this.tagRewriter.addTag(RegistryType.BLOCK, "minecraft:wall_post_override", wallPostOverrideTag);
/* 229 */     this.tagRewriter.addTag(RegistryType.BLOCK, "minecraft:beacon_base_blocks", new int[] { 133, 134, 148, 265 });
/* 230 */     this.tagRewriter.addTag(RegistryType.BLOCK, "minecraft:climbable", new int[] { 160, 241, 658 });
/* 231 */     this.tagRewriter.addTag(RegistryType.BLOCK, "minecraft:fire", new int[] { 142 });
/* 232 */     this.tagRewriter.addTag(RegistryType.BLOCK, "minecraft:campfires", new int[] { 679 });
/* 233 */     this.tagRewriter.addTag(RegistryType.BLOCK, "minecraft:fence_gates", new int[] { 242, 467, 468, 469, 470, 471 });
/* 234 */     this.tagRewriter.addTag(RegistryType.BLOCK, "minecraft:unstable_bottom_center", new int[] { 242, 467, 468, 469, 470, 471 });
/* 235 */     this.tagRewriter.addTag(RegistryType.BLOCK, "minecraft:wooden_trapdoors", new int[] { 193, 194, 195, 196, 197, 198 });
/* 236 */     this.tagRewriter.addTag(RegistryType.ITEM, "minecraft:wooden_trapdoors", new int[] { 215, 216, 217, 218, 219, 220 });
/* 237 */     this.tagRewriter.addTag(RegistryType.ITEM, "minecraft:beacon_payment_items", new int[] { 529, 530, 531, 760 });
/* 238 */     this.tagRewriter.addTag(RegistryType.ENTITY, "minecraft:impact_projectiles", new int[] { 2, 72, 71, 37, 69, 79, 83, 15, 93 });
/*     */ 
/*     */     
/* 241 */     this.tagRewriter.addEmptyTag(RegistryType.BLOCK, "minecraft:guarded_by_piglins");
/* 242 */     this.tagRewriter.addEmptyTag(RegistryType.BLOCK, "minecraft:soul_speed_blocks");
/* 243 */     this.tagRewriter.addEmptyTag(RegistryType.BLOCK, "minecraft:soul_fire_base_blocks");
/* 244 */     this.tagRewriter.addEmptyTag(RegistryType.BLOCK, "minecraft:non_flammable_wood");
/* 245 */     this.tagRewriter.addEmptyTag(RegistryType.ITEM, "minecraft:non_flammable_wood");
/*     */ 
/*     */     
/* 248 */     this.tagRewriter.addEmptyTags(RegistryType.BLOCK, new String[] { "minecraft:bamboo_plantable_on", "minecraft:beds", "minecraft:bee_growables", "minecraft:beehives", "minecraft:coral_plants", "minecraft:crops", "minecraft:dragon_immune", "minecraft:flowers", "minecraft:portals", "minecraft:shulker_boxes", "minecraft:small_flowers", "minecraft:tall_flowers", "minecraft:trapdoors", "minecraft:underwater_bonemeals", "minecraft:wither_immune", "minecraft:wooden_fences", "minecraft:wooden_trapdoors" });
/*     */ 
/*     */ 
/*     */     
/* 252 */     this.tagRewriter.addEmptyTags(RegistryType.ENTITY, new String[] { "minecraft:arrows", "minecraft:beehive_inhabitors", "minecraft:raiders", "minecraft:skeletons" });
/* 253 */     this.tagRewriter.addEmptyTags(RegistryType.ITEM, new String[] { "minecraft:beds", "minecraft:coals", "minecraft:fences", "minecraft:flowers", "minecraft:lectern_books", "minecraft:music_discs", "minecraft:small_flowers", "minecraft:tall_flowers", "minecraft:trapdoors", "minecraft:walls", "minecraft:wooden_fences" });
/*     */ 
/*     */     
/* 256 */     Types1_16.PARTICLE.filler((Protocol)this)
/* 257 */       .reader("block", ParticleType.Readers.BLOCK)
/* 258 */       .reader("dust", ParticleType.Readers.DUST)
/* 259 */       .reader("falling_dust", ParticleType.Readers.BLOCK)
/* 260 */       .reader("item", ParticleType.Readers.VAR_INT_ITEM);
/*     */   }
/*     */ 
/*     */   
/*     */   public void register(ViaProviders providers) {
/* 265 */     providers.register(PlayerAbilitiesProvider.class, (Provider)new PlayerAbilitiesProvider());
/*     */   }
/*     */ 
/*     */   
/*     */   public void init(UserConnection userConnection) {
/* 270 */     userConnection.addEntityTracker(getClass(), (EntityTracker)new EntityTrackerBase(userConnection, (EntityType)Entity1_16Types.PLAYER));
/* 271 */     userConnection.put((StorableObject)new InventoryTracker1_16());
/*     */   }
/*     */ 
/*     */   
/*     */   public MappingData getMappingData() {
/* 276 */     return MAPPINGS;
/*     */   }
/*     */ 
/*     */   
/*     */   public MetadataRewriter1_16To1_15_2 getEntityRewriter() {
/* 281 */     return this.metadataRewriter;
/*     */   }
/*     */ 
/*     */   
/*     */   public InventoryPackets getItemRewriter() {
/* 286 */     return this.itemRewriter;
/*     */   }
/*     */   
/*     */   public TranslationMappings getComponentRewriter() {
/* 290 */     return this.componentRewriter;
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\protocols\protocol1_16to1_15_2\Protocol1_16To1_15_2.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */