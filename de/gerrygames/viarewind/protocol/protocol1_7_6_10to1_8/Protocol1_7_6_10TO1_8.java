/*     */ package de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8;
/*     */ import com.viaversion.viaversion.api.Via;
/*     */ import com.viaversion.viaversion.api.connection.StorableObject;
/*     */ import com.viaversion.viaversion.api.connection.UserConnection;
/*     */ import com.viaversion.viaversion.api.platform.providers.ViaProviders;
/*     */ import com.viaversion.viaversion.api.protocol.packet.ClientboundPacketType;
/*     */ import com.viaversion.viaversion.api.protocol.packet.Direction;
/*     */ import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
/*     */ import com.viaversion.viaversion.api.protocol.packet.State;
/*     */ import com.viaversion.viaversion.api.protocol.remapper.PacketHandler;
/*     */ import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
/*     */ import com.viaversion.viaversion.api.type.Type;
/*     */ import com.viaversion.viaversion.protocols.protocol1_8.ClientboundPackets1_8;
/*     */ import com.viaversion.viaversion.protocols.protocol1_8.ServerboundPackets1_8;
/*     */ import de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.packets.EntityPackets;
/*     */ import de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.packets.SpawnPackets;
/*     */ import de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.packets.WorldPackets;
/*     */ import de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.provider.CompressionHandlerProvider;
/*     */ import de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.storage.EntityTracker;
/*     */ import de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.storage.PlayerAbilities;
/*     */ 
/*     */ public class Protocol1_7_6_10TO1_8 extends AbstractProtocol<ClientboundPackets1_8, ClientboundPackets1_7, ServerboundPackets1_8, ServerboundPackets1_7> {
/*     */   public Protocol1_7_6_10TO1_8() {
/*  24 */     super(ClientboundPackets1_8.class, ClientboundPackets1_7.class, ServerboundPackets1_8.class, ServerboundPackets1_7.class);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void registerPackets() {
/*  29 */     EntityPackets.register(this);
/*  30 */     InventoryPackets.register(this);
/*  31 */     PlayerPackets.register(this);
/*  32 */     ScoreboardPackets.register(this);
/*  33 */     SpawnPackets.register(this);
/*  34 */     WorldPackets.register(this);
/*     */     
/*  36 */     registerClientbound((ClientboundPacketType)ClientboundPackets1_8.KEEP_ALIVE, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/*  39 */             map((Type)Type.VAR_INT, (Type)Type.INT);
/*     */           }
/*     */         });
/*     */     
/*  43 */     cancelClientbound((ClientboundPacketType)ClientboundPackets1_8.SET_COMPRESSION);
/*     */     
/*  45 */     registerServerbound(ServerboundPackets1_7.KEEP_ALIVE, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/*  48 */             map((Type)Type.INT, (Type)Type.VAR_INT);
/*     */           }
/*     */         });
/*     */ 
/*     */     
/*  53 */     registerClientbound(State.LOGIN, 1, 1, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/*  56 */             map(Type.STRING);
/*  57 */             map(Type.BYTE_ARRAY_PRIMITIVE, Type.SHORT_BYTE_ARRAY);
/*  58 */             map(Type.BYTE_ARRAY_PRIMITIVE, Type.SHORT_BYTE_ARRAY);
/*     */           }
/*     */         });
/*     */ 
/*     */     
/*  63 */     registerClientbound(State.LOGIN, 3, 3, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/*  66 */             handler(packetWrapper -> {
/*     */                   ((CompressionHandlerProvider)Via.getManager().getProviders().get(CompressionHandlerProvider.class)).handleSetCompression(packetWrapper.user(), ((Integer)packetWrapper.read((Type)Type.VAR_INT)).intValue());
/*     */ 
/*     */                   
/*     */                   packetWrapper.cancel();
/*     */                 });
/*     */           }
/*     */         });
/*     */     
/*  75 */     registerServerbound(State.LOGIN, 1, 1, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/*  78 */             map(Type.SHORT_BYTE_ARRAY, Type.BYTE_ARRAY_PRIMITIVE);
/*  79 */             map(Type.SHORT_BYTE_ARRAY, Type.BYTE_ARRAY_PRIMITIVE);
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */   
/*     */   public void transform(Direction direction, State state, PacketWrapper packetWrapper) throws Exception {
/*  86 */     ((CompressionHandlerProvider)Via.getManager().getProviders().get(CompressionHandlerProvider.class))
/*  87 */       .handleTransform(packetWrapper.user());
/*     */     
/*  89 */     super.transform(direction, state, packetWrapper);
/*     */   }
/*     */ 
/*     */   
/*     */   public void init(UserConnection userConnection) {
/*  94 */     Ticker.init();
/*     */     
/*  96 */     userConnection.put((StorableObject)new Windows(userConnection));
/*  97 */     userConnection.put((StorableObject)new EntityTracker(userConnection));
/*  98 */     userConnection.put((StorableObject)new PlayerPosition(userConnection));
/*  99 */     userConnection.put((StorableObject)new GameProfileStorage(userConnection));
/* 100 */     userConnection.put((StorableObject)new Scoreboard(userConnection));
/* 101 */     userConnection.put((StorableObject)new CompressionSendStorage(userConnection));
/* 102 */     userConnection.put((StorableObject)new WorldBorder(userConnection));
/* 103 */     userConnection.put((StorableObject)new PlayerAbilities(userConnection));
/* 104 */     userConnection.put((StorableObject)new ClientWorld(userConnection));
/*     */   }
/*     */ 
/*     */   
/*     */   public void register(ViaProviders providers) {
/* 109 */     providers.register(CompressionHandlerProvider.class, (Provider)new CompressionHandlerProvider());
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\de\gerrygames\viarewind\protocol\protocol1_7_6_10to1_8\Protocol1_7_6_10TO1_8.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */