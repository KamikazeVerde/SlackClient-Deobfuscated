/*     */ package com.viaversion.viaversion.protocols.protocol1_9to1_8;
/*     */ 
/*     */ import com.viaversion.viaversion.api.Via;
/*     */ import com.viaversion.viaversion.api.connection.StorableObject;
/*     */ import com.viaversion.viaversion.api.connection.UserConnection;
/*     */ import com.viaversion.viaversion.api.data.entity.EntityTracker;
/*     */ import com.viaversion.viaversion.api.minecraft.item.Item;
/*     */ import com.viaversion.viaversion.api.platform.providers.Provider;
/*     */ import com.viaversion.viaversion.api.platform.providers.ViaProviders;
/*     */ import com.viaversion.viaversion.api.protocol.AbstractProtocol;
/*     */ import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
/*     */ import com.viaversion.viaversion.api.protocol.packet.State;
/*     */ import com.viaversion.viaversion.api.protocol.remapper.ValueTransformer;
/*     */ import com.viaversion.viaversion.api.rewriter.EntityRewriter;
/*     */ import com.viaversion.viaversion.api.type.Type;
/*     */ import com.viaversion.viaversion.libs.gson.JsonElement;
/*     */ import com.viaversion.viaversion.libs.gson.JsonObject;
/*     */ import com.viaversion.viaversion.protocols.protocol1_8.ClientboundPackets1_8;
/*     */ import com.viaversion.viaversion.protocols.protocol1_8.ServerboundPackets1_8;
/*     */ import com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2.storage.ClientWorld;
/*     */ import com.viaversion.viaversion.protocols.protocol1_9to1_8.metadata.MetadataRewriter1_9To1_8;
/*     */ import com.viaversion.viaversion.protocols.protocol1_9to1_8.packets.EntityPackets;
/*     */ import com.viaversion.viaversion.protocols.protocol1_9to1_8.packets.InventoryPackets;
/*     */ import com.viaversion.viaversion.protocols.protocol1_9to1_8.packets.PlayerPackets;
/*     */ import com.viaversion.viaversion.protocols.protocol1_9to1_8.packets.SpawnPackets;
/*     */ import com.viaversion.viaversion.protocols.protocol1_9to1_8.packets.WorldPackets;
/*     */ import com.viaversion.viaversion.protocols.protocol1_9to1_8.providers.BossBarProvider;
/*     */ import com.viaversion.viaversion.protocols.protocol1_9to1_8.providers.CommandBlockProvider;
/*     */ import com.viaversion.viaversion.protocols.protocol1_9to1_8.providers.CompressionProvider;
/*     */ import com.viaversion.viaversion.protocols.protocol1_9to1_8.providers.EntityIdProvider;
/*     */ import com.viaversion.viaversion.protocols.protocol1_9to1_8.providers.HandItemProvider;
/*     */ import com.viaversion.viaversion.protocols.protocol1_9to1_8.providers.MainHandProvider;
/*     */ import com.viaversion.viaversion.protocols.protocol1_9to1_8.providers.MovementTransmitterProvider;
/*     */ import com.viaversion.viaversion.protocols.protocol1_9to1_8.storage.ClientChunks;
/*     */ import com.viaversion.viaversion.protocols.protocol1_9to1_8.storage.CommandBlockStorage;
/*     */ import com.viaversion.viaversion.protocols.protocol1_9to1_8.storage.EntityTracker1_9;
/*     */ import com.viaversion.viaversion.protocols.protocol1_9to1_8.storage.InventoryTracker;
/*     */ import com.viaversion.viaversion.protocols.protocol1_9to1_8.storage.MovementTracker;
/*     */ import com.viaversion.viaversion.util.GsonUtil;
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
/*     */ public class Protocol1_9To1_8
/*     */   extends AbstractProtocol<ClientboundPackets1_8, ClientboundPackets1_9, ServerboundPackets1_8, ServerboundPackets1_9>
/*     */ {
/*  55 */   public static final ValueTransformer<String, JsonElement> FIX_JSON = new ValueTransformer<String, JsonElement>(Type.COMPONENT)
/*     */     {
/*     */       public JsonElement transform(PacketWrapper wrapper, String line) {
/*  58 */         return Protocol1_9To1_8.fixJson(line);
/*     */       }
/*     */     };
/*  61 */   private final MetadataRewriter1_9To1_8 metadataRewriter = new MetadataRewriter1_9To1_8(this);
/*     */   
/*     */   public Protocol1_9To1_8() {
/*  64 */     super(ClientboundPackets1_8.class, ClientboundPackets1_9.class, ServerboundPackets1_8.class, ServerboundPackets1_9.class);
/*     */   }
/*     */   
/*     */   public static JsonElement fixJson(String line) {
/*  68 */     if (line == null || line.equalsIgnoreCase("null")) {
/*  69 */       line = "{\"text\":\"\"}";
/*     */     } else {
/*  71 */       if ((!line.startsWith("\"") || !line.endsWith("\"")) && (!line.startsWith("{") || !line.endsWith("}"))) {
/*  72 */         return constructJson(line);
/*     */       }
/*  74 */       if (line.startsWith("\"") && line.endsWith("\"")) {
/*  75 */         line = "{\"text\":" + line + "}";
/*     */       }
/*     */     } 
/*     */     try {
/*  79 */       return (JsonElement)GsonUtil.getGson().fromJson(line, JsonObject.class);
/*  80 */     } catch (Exception e) {
/*  81 */       if (Via.getConfig().isForceJsonTransform()) {
/*  82 */         return constructJson(line);
/*     */       }
/*  84 */       Via.getPlatform().getLogger().warning("Invalid JSON String: \"" + line + "\" Please report this issue to the ViaVersion Github: " + e.getMessage());
/*  85 */       return (JsonElement)GsonUtil.getGson().fromJson("{\"text\":\"\"}", JsonObject.class);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private static JsonElement constructJson(String text) {
/*  91 */     JsonObject jsonObject = new JsonObject();
/*  92 */     jsonObject.addProperty("text", text);
/*  93 */     return (JsonElement)jsonObject;
/*     */   }
/*     */   
/*     */   public static Item getHandItem(UserConnection info) {
/*  97 */     return ((HandItemProvider)Via.getManager().getProviders().get(HandItemProvider.class)).getHandItem(info);
/*     */   }
/*     */   
/*     */   public static boolean isSword(int id) {
/* 101 */     if (id == 267) return true; 
/* 102 */     if (id == 268) return true; 
/* 103 */     if (id == 272) return true; 
/* 104 */     if (id == 276) return true; 
/* 105 */     if (id == 283) return true;
/*     */     
/* 107 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void registerPackets() {
/* 112 */     this.metadataRewriter.register();
/*     */ 
/*     */     
/* 115 */     registerClientbound(State.LOGIN, 0, 0, wrapper -> {
/*     */           if (wrapper.isReadable(Type.COMPONENT, 0)) {
/*     */             return;
/*     */           }
/*     */ 
/*     */           
/*     */           wrapper.write(Type.COMPONENT, fixJson((String)wrapper.read(Type.STRING)));
/*     */         });
/*     */ 
/*     */     
/* 125 */     SpawnPackets.register(this);
/* 126 */     InventoryPackets.register(this);
/* 127 */     EntityPackets.register(this);
/* 128 */     PlayerPackets.register(this);
/* 129 */     WorldPackets.register(this);
/*     */   }
/*     */ 
/*     */   
/*     */   public void register(ViaProviders providers) {
/* 134 */     providers.register(HandItemProvider.class, (Provider)new HandItemProvider());
/* 135 */     providers.register(CommandBlockProvider.class, (Provider)new CommandBlockProvider());
/* 136 */     providers.register(EntityIdProvider.class, (Provider)new EntityIdProvider());
/* 137 */     providers.register(BossBarProvider.class, (Provider)new BossBarProvider());
/* 138 */     providers.register(MainHandProvider.class, (Provider)new MainHandProvider());
/* 139 */     providers.register(CompressionProvider.class, (Provider)new CompressionProvider());
/* 140 */     providers.require(MovementTransmitterProvider.class);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void init(UserConnection userConnection) {
/* 146 */     userConnection.addEntityTracker(getClass(), (EntityTracker)new EntityTracker1_9(userConnection));
/*     */     
/* 148 */     userConnection.put((StorableObject)new ClientChunks(userConnection));
/*     */     
/* 150 */     userConnection.put((StorableObject)new MovementTracker());
/*     */     
/* 152 */     userConnection.put((StorableObject)new InventoryTracker());
/*     */     
/* 154 */     userConnection.put((StorableObject)new CommandBlockStorage());
/*     */     
/* 156 */     if (!userConnection.has(ClientWorld.class)) {
/* 157 */       userConnection.put((StorableObject)new ClientWorld(userConnection));
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public MetadataRewriter1_9To1_8 getEntityRewriter() {
/* 163 */     return this.metadataRewriter;
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\protocols\protocol1_9to1_8\Protocol1_9To1_8.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */