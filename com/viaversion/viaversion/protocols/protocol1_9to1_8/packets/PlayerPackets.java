/*     */ package com.viaversion.viaversion.protocols.protocol1_9to1_8.packets;
/*     */ 
/*     */ import com.viaversion.viaversion.api.Via;
/*     */ import com.viaversion.viaversion.api.minecraft.entities.Entity1_10Types;
/*     */ import com.viaversion.viaversion.api.minecraft.entities.EntityType;
/*     */ import com.viaversion.viaversion.api.minecraft.item.Item;
/*     */ import com.viaversion.viaversion.api.protocol.packet.ClientboundPacketType;
/*     */ import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
/*     */ import com.viaversion.viaversion.api.protocol.packet.ServerboundPacketType;
/*     */ import com.viaversion.viaversion.api.protocol.remapper.PacketHandler;
/*     */ import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
/*     */ import com.viaversion.viaversion.api.type.Type;
/*     */ import com.viaversion.viaversion.libs.gson.JsonObject;
/*     */ import com.viaversion.viaversion.protocols.protocol1_8.ClientboundPackets1_8;
/*     */ import com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2.storage.ClientWorld;
/*     */ import com.viaversion.viaversion.protocols.protocol1_9to1_8.ItemRewriter;
/*     */ import com.viaversion.viaversion.protocols.protocol1_9to1_8.PlayerMovementMapper;
/*     */ import com.viaversion.viaversion.protocols.protocol1_9to1_8.Protocol1_9To1_8;
/*     */ import com.viaversion.viaversion.protocols.protocol1_9to1_8.ServerboundPackets1_9;
/*     */ import com.viaversion.viaversion.protocols.protocol1_9to1_8.chat.ChatRewriter;
/*     */ import com.viaversion.viaversion.protocols.protocol1_9to1_8.chat.GameMode;
/*     */ import com.viaversion.viaversion.protocols.protocol1_9to1_8.providers.CommandBlockProvider;
/*     */ import com.viaversion.viaversion.protocols.protocol1_9to1_8.providers.CompressionProvider;
/*     */ import com.viaversion.viaversion.protocols.protocol1_9to1_8.providers.MainHandProvider;
/*     */ import com.viaversion.viaversion.protocols.protocol1_9to1_8.storage.ClientChunks;
/*     */ import com.viaversion.viaversion.protocols.protocol1_9to1_8.storage.EntityTracker1_9;
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
/*     */ public class PlayerPackets
/*     */ {
/*     */   public static void register(Protocol1_9To1_8 protocol) {
/*  42 */     protocol.registerClientbound((ClientboundPacketType)ClientboundPackets1_8.CHAT_MESSAGE, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/*  45 */             map(Type.STRING, Protocol1_9To1_8.FIX_JSON);
/*  46 */             map((Type)Type.BYTE);
/*     */             
/*  48 */             handler(wrapper -> {
/*     */                   try {
/*     */                     JsonObject obj = (JsonObject)wrapper.get(Type.COMPONENT, 0);
/*     */                     ChatRewriter.toClient(obj, wrapper.user());
/*  52 */                   } catch (Exception e) {
/*     */                     e.printStackTrace();
/*     */                   } 
/*     */                 });
/*     */           }
/*     */         });
/*     */     
/*  59 */     protocol.registerClientbound((ClientboundPacketType)ClientboundPackets1_8.TAB_LIST, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/*  62 */             map(Type.STRING, Protocol1_9To1_8.FIX_JSON);
/*  63 */             map(Type.STRING, Protocol1_9To1_8.FIX_JSON);
/*     */           }
/*     */         });
/*     */     
/*  67 */     protocol.registerClientbound((ClientboundPacketType)ClientboundPackets1_8.DISCONNECT, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/*  70 */             map(Type.STRING, Protocol1_9To1_8.FIX_JSON);
/*     */           }
/*     */         });
/*     */     
/*  74 */     protocol.registerClientbound((ClientboundPacketType)ClientboundPackets1_8.TITLE, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/*  77 */             map((Type)Type.VAR_INT);
/*     */             
/*  79 */             handler(wrapper -> {
/*     */                   int action = ((Integer)wrapper.get((Type)Type.VAR_INT, 0)).intValue();
/*     */                   
/*     */                   if (action == 0 || action == 1) {
/*     */                     Protocol1_9To1_8.FIX_JSON.write(wrapper, wrapper.read(Type.STRING));
/*     */                   }
/*     */                 });
/*     */           }
/*     */         });
/*     */     
/*  89 */     protocol.registerClientbound((ClientboundPacketType)ClientboundPackets1_8.PLAYER_POSITION, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/*  92 */             map((Type)Type.DOUBLE);
/*  93 */             map((Type)Type.DOUBLE);
/*  94 */             map((Type)Type.DOUBLE);
/*     */             
/*  96 */             map((Type)Type.FLOAT);
/*  97 */             map((Type)Type.FLOAT);
/*     */             
/*  99 */             map((Type)Type.BYTE);
/*     */             
/* 101 */             handler(wrapper -> wrapper.write((Type)Type.VAR_INT, Integer.valueOf(0)));
/*     */           }
/*     */         });
/*     */ 
/*     */ 
/*     */     
/* 107 */     protocol.registerClientbound((ClientboundPacketType)ClientboundPackets1_8.TEAMS, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/* 110 */             map(Type.STRING);
/* 111 */             map((Type)Type.BYTE);
/* 112 */             handler(wrapper -> {
/*     */                   byte mode = ((Byte)wrapper.get((Type)Type.BYTE, 0)).byteValue();
/*     */                   
/*     */                   if (mode == 0 || mode == 2) {
/*     */                     wrapper.passthrough(Type.STRING);
/*     */                     
/*     */                     wrapper.passthrough(Type.STRING);
/*     */                     
/*     */                     wrapper.passthrough(Type.STRING);
/*     */                     
/*     */                     wrapper.passthrough((Type)Type.BYTE);
/*     */                     
/*     */                     wrapper.passthrough(Type.STRING);
/*     */                     
/*     */                     wrapper.write(Type.STRING, Via.getConfig().isPreventCollision() ? "never" : "");
/*     */                     
/*     */                     wrapper.passthrough((Type)Type.BYTE);
/*     */                   } 
/*     */                   
/*     */                   if (mode == 0 || mode == 3 || mode == 4) {
/*     */                     String[] players = (String[])wrapper.passthrough(Type.STRING_ARRAY);
/*     */                     
/*     */                     EntityTracker1_9 entityTracker = (EntityTracker1_9)wrapper.user().getEntityTracker(Protocol1_9To1_8.class);
/*     */                     
/*     */                     String myName = wrapper.user().getProtocolInfo().getUsername();
/*     */                     
/*     */                     String teamName = (String)wrapper.get(Type.STRING, 0);
/*     */                     
/*     */                     for (String player : players) {
/*     */                       if (entityTracker.isAutoTeam() && player.equalsIgnoreCase(myName)) {
/*     */                         if (mode == 4) {
/*     */                           wrapper.send(Protocol1_9To1_8.class);
/*     */                           
/*     */                           wrapper.cancel();
/*     */                           entityTracker.sendTeamPacket(true, true);
/*     */                           entityTracker.setCurrentTeam("viaversion");
/*     */                         } else {
/*     */                           entityTracker.sendTeamPacket(false, true);
/*     */                           entityTracker.setCurrentTeam(teamName);
/*     */                         } 
/*     */                       }
/*     */                     } 
/*     */                   } 
/*     */                   if (mode == 1) {
/*     */                     EntityTracker1_9 entityTracker = (EntityTracker1_9)wrapper.user().getEntityTracker(Protocol1_9To1_8.class);
/*     */                     String teamName = (String)wrapper.get(Type.STRING, 0);
/*     */                     if (entityTracker.isAutoTeam() && teamName.equals(entityTracker.getCurrentTeam())) {
/*     */                       wrapper.send(Protocol1_9To1_8.class);
/*     */                       wrapper.cancel();
/*     */                       entityTracker.sendTeamPacket(true, true);
/*     */                       entityTracker.setCurrentTeam("viaversion");
/*     */                     } 
/*     */                   } 
/*     */                 });
/*     */           }
/*     */         });
/* 168 */     protocol.registerClientbound((ClientboundPacketType)ClientboundPackets1_8.JOIN_GAME, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/* 171 */             map((Type)Type.INT);
/*     */             
/* 173 */             handler(wrapper -> {
/*     */                   int entityId = ((Integer)wrapper.get((Type)Type.INT, 0)).intValue();
/*     */                   EntityTracker1_9 tracker = (EntityTracker1_9)wrapper.user().getEntityTracker(Protocol1_9To1_8.class);
/*     */                   tracker.addEntity(entityId, (EntityType)Entity1_10Types.EntityType.PLAYER);
/*     */                   tracker.setClientEntityId(entityId);
/*     */                 });
/* 179 */             map((Type)Type.UNSIGNED_BYTE);
/* 180 */             map((Type)Type.BYTE);
/* 181 */             map((Type)Type.UNSIGNED_BYTE);
/* 182 */             map((Type)Type.UNSIGNED_BYTE);
/* 183 */             map(Type.STRING);
/* 184 */             map((Type)Type.BOOLEAN);
/*     */             
/* 186 */             handler(wrapper -> {
/*     */                   EntityTracker1_9 tracker = (EntityTracker1_9)wrapper.user().getEntityTracker(Protocol1_9To1_8.class);
/*     */                   
/*     */                   tracker.setGameMode(GameMode.getById(((Short)wrapper.get((Type)Type.UNSIGNED_BYTE, 0)).shortValue()));
/*     */                 });
/*     */             
/* 192 */             handler(wrapper -> {
/*     */                   ClientWorld clientWorld = (ClientWorld)wrapper.user().get(ClientWorld.class);
/*     */                   
/*     */                   int dimensionId = ((Byte)wrapper.get((Type)Type.BYTE, 0)).byteValue();
/*     */                   
/*     */                   clientWorld.setEnvironment(dimensionId);
/*     */                 });
/* 199 */             handler(wrapper -> {
/*     */                   CommandBlockProvider provider = (CommandBlockProvider)Via.getManager().getProviders().get(CommandBlockProvider.class);
/*     */                   
/*     */                   provider.sendPermission(wrapper.user());
/*     */                 });
/*     */             
/* 205 */             handler(wrapper -> {
/*     */                   EntityTracker1_9 entityTracker = (EntityTracker1_9)wrapper.user().getEntityTracker(Protocol1_9To1_8.class);
/*     */                   
/*     */                   if (Via.getConfig().isAutoTeam()) {
/*     */                     entityTracker.setAutoTeam(true);
/*     */                     
/*     */                     wrapper.send(Protocol1_9To1_8.class);
/*     */                     wrapper.cancel();
/*     */                     entityTracker.sendTeamPacket(true, true);
/*     */                     entityTracker.setCurrentTeam("viaversion");
/*     */                   } else {
/*     */                     entityTracker.setAutoTeam(false);
/*     */                   } 
/*     */                 });
/*     */           }
/*     */         });
/* 221 */     protocol.registerClientbound((ClientboundPacketType)ClientboundPackets1_8.PLAYER_INFO, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/* 224 */             map((Type)Type.VAR_INT);
/* 225 */             map((Type)Type.VAR_INT);
/*     */ 
/*     */             
/* 228 */             handler(wrapper -> {
/*     */                   int action = ((Integer)wrapper.get((Type)Type.VAR_INT, 0)).intValue();
/*     */                   
/*     */                   int count = ((Integer)wrapper.get((Type)Type.VAR_INT, 1)).intValue();
/*     */                   
/*     */                   for (int i = 0; i < count; i++) {
/*     */                     wrapper.passthrough(Type.UUID);
/*     */                     
/*     */                     if (action == 0) {
/*     */                       wrapper.passthrough(Type.STRING);
/*     */                       
/*     */                       int properties = ((Integer)wrapper.passthrough((Type)Type.VAR_INT)).intValue();
/*     */                       
/*     */                       for (int j = 0; j < properties; j++) {
/*     */                         wrapper.passthrough(Type.STRING);
/*     */                         
/*     */                         wrapper.passthrough(Type.STRING);
/*     */                         
/*     */                         wrapper.passthrough(Type.OPTIONAL_STRING);
/*     */                       } 
/*     */                       wrapper.passthrough((Type)Type.VAR_INT);
/*     */                       wrapper.passthrough((Type)Type.VAR_INT);
/*     */                       String displayName = (String)wrapper.read(Type.OPTIONAL_STRING);
/*     */                       wrapper.write(Type.OPTIONAL_COMPONENT, (displayName != null) ? Protocol1_9To1_8.FIX_JSON.transform(wrapper, displayName) : null);
/*     */                     } else if (action == 1 || action == 2) {
/*     */                       wrapper.passthrough((Type)Type.VAR_INT);
/*     */                     } else if (action == 3) {
/*     */                       String displayName = (String)wrapper.read(Type.OPTIONAL_STRING);
/*     */                       wrapper.write(Type.OPTIONAL_COMPONENT, (displayName != null) ? Protocol1_9To1_8.FIX_JSON.transform(wrapper, displayName) : null);
/*     */                     } else if (action == 4) {
/*     */                     
/*     */                     } 
/*     */                   } 
/*     */                 });
/*     */           }
/*     */         });
/* 264 */     protocol.registerClientbound((ClientboundPacketType)ClientboundPackets1_8.PLUGIN_MESSAGE, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/* 267 */             map(Type.STRING);
/* 268 */             handler(wrapper -> {
/*     */                   String name = (String)wrapper.get(Type.STRING, 0);
/*     */                   
/*     */                   if (name.equalsIgnoreCase("MC|BOpen")) {
/*     */                     wrapper.read(Type.REMAINING_BYTES);
/*     */                     
/*     */                     wrapper.write((Type)Type.VAR_INT, Integer.valueOf(0));
/*     */                   } 
/*     */                   
/*     */                   if (name.equalsIgnoreCase("MC|TrList")) {
/*     */                     wrapper.passthrough((Type)Type.INT);
/*     */                     
/*     */                     Short size = (Short)wrapper.passthrough((Type)Type.UNSIGNED_BYTE);
/*     */                     
/*     */                     for (int i = 0; i < size.shortValue(); i++) {
/*     */                       Item item1 = (Item)wrapper.passthrough(Type.ITEM);
/*     */                       
/*     */                       ItemRewriter.toClient(item1);
/*     */                       
/*     */                       Item item2 = (Item)wrapper.passthrough(Type.ITEM);
/*     */                       
/*     */                       ItemRewriter.toClient(item2);
/*     */                       boolean present = ((Boolean)wrapper.passthrough((Type)Type.BOOLEAN)).booleanValue();
/*     */                       if (present) {
/*     */                         Item item3 = (Item)wrapper.passthrough(Type.ITEM);
/*     */                         ItemRewriter.toClient(item3);
/*     */                       } 
/*     */                       wrapper.passthrough((Type)Type.BOOLEAN);
/*     */                       wrapper.passthrough((Type)Type.INT);
/*     */                       wrapper.passthrough((Type)Type.INT);
/*     */                     } 
/*     */                   } 
/*     */                 });
/*     */           }
/*     */         });
/* 303 */     protocol.registerClientbound((ClientboundPacketType)ClientboundPackets1_8.RESPAWN, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/* 306 */             map((Type)Type.INT);
/* 307 */             map((Type)Type.UNSIGNED_BYTE);
/* 308 */             map((Type)Type.UNSIGNED_BYTE);
/* 309 */             map(Type.STRING);
/*     */ 
/*     */             
/* 312 */             handler(wrapper -> {
/*     */                   ClientWorld clientWorld = (ClientWorld)wrapper.user().get(ClientWorld.class);
/*     */                   
/*     */                   int dimensionId = ((Integer)wrapper.get((Type)Type.INT, 0)).intValue();
/*     */                   clientWorld.setEnvironment(dimensionId);
/*     */                 });
/* 318 */             handler(wrapper -> {
/*     */                   ((ClientChunks)wrapper.user().get(ClientChunks.class)).getLoadedChunks().clear();
/*     */                   
/*     */                   int gamemode = ((Short)wrapper.get((Type)Type.UNSIGNED_BYTE, 0)).shortValue();
/*     */                   
/*     */                   EntityTracker1_9 tracker = (EntityTracker1_9)wrapper.user().getEntityTracker(Protocol1_9To1_8.class);
/*     */                   
/*     */                   tracker.setGameMode(GameMode.getById(gamemode));
/*     */                 });
/*     */             
/* 328 */             handler(wrapper -> {
/*     */                   CommandBlockProvider provider = (CommandBlockProvider)Via.getManager().getProviders().get(CommandBlockProvider.class);
/*     */                   
/*     */                   provider.sendPermission(wrapper.user());
/*     */                   provider.unloadChunks(wrapper.user());
/*     */                 });
/*     */           }
/*     */         });
/* 336 */     protocol.registerClientbound((ClientboundPacketType)ClientboundPackets1_8.GAME_EVENT, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/* 339 */             map((Type)Type.UNSIGNED_BYTE);
/* 340 */             map((Type)Type.FLOAT);
/*     */             
/* 342 */             handler(wrapper -> {
/*     */                   short reason = ((Short)wrapper.get((Type)Type.UNSIGNED_BYTE, 0)).shortValue();
/*     */                   
/*     */                   if (reason == 3) {
/*     */                     int gamemode = ((Float)wrapper.get((Type)Type.FLOAT, 0)).intValue();
/*     */                     
/*     */                     EntityTracker1_9 tracker = (EntityTracker1_9)wrapper.user().getEntityTracker(Protocol1_9To1_8.class);
/*     */                     tracker.setGameMode(GameMode.getById(gamemode));
/*     */                   } else if (reason == 4) {
/*     */                     wrapper.set((Type)Type.FLOAT, 0, Float.valueOf(1.0F));
/*     */                   } 
/*     */                 });
/*     */           }
/*     */         });
/* 356 */     protocol.registerClientbound((ClientboundPacketType)ClientboundPackets1_8.SET_COMPRESSION, null, wrapper -> {
/*     */           wrapper.cancel();
/*     */ 
/*     */           
/*     */           CompressionProvider provider = (CompressionProvider)Via.getManager().getProviders().get(CompressionProvider.class);
/*     */           
/*     */           provider.handlePlayCompression(wrapper.user(), ((Integer)wrapper.read((Type)Type.VAR_INT)).intValue());
/*     */         });
/*     */     
/* 365 */     protocol.registerServerbound((ServerboundPacketType)ServerboundPackets1_9.TAB_COMPLETE, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/* 368 */             map(Type.STRING);
/* 369 */             map((Type)Type.BOOLEAN, (Type)Type.NOTHING);
/*     */           }
/*     */         });
/*     */     
/* 373 */     protocol.registerServerbound((ServerboundPacketType)ServerboundPackets1_9.CLIENT_SETTINGS, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/* 376 */             map(Type.STRING);
/* 377 */             map((Type)Type.BYTE);
/* 378 */             map((Type)Type.VAR_INT, (Type)Type.BYTE);
/* 379 */             map((Type)Type.BOOLEAN);
/* 380 */             map((Type)Type.UNSIGNED_BYTE);
/*     */             
/* 382 */             handler(wrapper -> {
/*     */                   int hand = ((Integer)wrapper.read((Type)Type.VAR_INT)).intValue();
/*     */                   
/*     */                   if (Via.getConfig().isLeftHandedHandling()) {
/*     */                     if (hand == 0) {
/*     */                       wrapper.set((Type)Type.UNSIGNED_BYTE, 0, Short.valueOf((short)(((Short)wrapper.get((Type)Type.UNSIGNED_BYTE, 0)).intValue() | 0x80)));
/*     */                     }
/*     */                   }
/*     */                   
/*     */                   wrapper.sendToServer(Protocol1_9To1_8.class);
/*     */                   
/*     */                   wrapper.cancel();
/*     */                   ((MainHandProvider)Via.getManager().getProviders().get(MainHandProvider.class)).setMainHand(wrapper.user(), hand);
/*     */                 });
/*     */           }
/*     */         });
/* 398 */     protocol.registerServerbound((ServerboundPacketType)ServerboundPackets1_9.ANIMATION, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/* 401 */             map((Type)Type.VAR_INT, (Type)Type.NOTHING);
/*     */           }
/*     */         });
/*     */     
/* 405 */     protocol.cancelServerbound((ServerboundPacketType)ServerboundPackets1_9.TELEPORT_CONFIRM);
/* 406 */     protocol.cancelServerbound((ServerboundPacketType)ServerboundPackets1_9.VEHICLE_MOVE);
/* 407 */     protocol.cancelServerbound((ServerboundPacketType)ServerboundPackets1_9.STEER_BOAT);
/*     */     
/* 409 */     protocol.registerServerbound((ServerboundPacketType)ServerboundPackets1_9.PLUGIN_MESSAGE, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/* 412 */             map(Type.STRING);
/* 413 */             handler(wrapper -> {
/*     */                   String name = (String)wrapper.get(Type.STRING, 0);
/*     */                   
/*     */                   if (name.equalsIgnoreCase("MC|BSign")) {
/*     */                     Item item = (Item)wrapper.passthrough(Type.ITEM);
/*     */                     if (item != null) {
/*     */                       item.setIdentifier(387);
/*     */                       ItemRewriter.rewriteBookToServer(item);
/*     */                     } 
/*     */                   } 
/*     */                   if (name.equalsIgnoreCase("MC|AutoCmd")) {
/*     */                     wrapper.set(Type.STRING, 0, "MC|AdvCdm");
/*     */                     wrapper.write((Type)Type.BYTE, Byte.valueOf((byte)0));
/*     */                     wrapper.passthrough((Type)Type.INT);
/*     */                     wrapper.passthrough((Type)Type.INT);
/*     */                     wrapper.passthrough((Type)Type.INT);
/*     */                     wrapper.passthrough(Type.STRING);
/*     */                     wrapper.passthrough((Type)Type.BOOLEAN);
/*     */                     wrapper.clearInputBuffer();
/*     */                   } 
/*     */                   if (name.equalsIgnoreCase("MC|AdvCmd")) {
/*     */                     wrapper.set(Type.STRING, 0, "MC|AdvCdm");
/*     */                   }
/*     */                 });
/*     */           }
/*     */         });
/* 439 */     protocol.registerServerbound((ServerboundPacketType)ServerboundPackets1_9.CLIENT_STATUS, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/* 442 */             map((Type)Type.VAR_INT);
/* 443 */             handler(wrapper -> {
/*     */                   int action = ((Integer)wrapper.get((Type)Type.VAR_INT, 0)).intValue();
/*     */                   
/*     */                   if (action == 2) {
/*     */                     EntityTracker1_9 tracker = (EntityTracker1_9)wrapper.user().getEntityTracker(Protocol1_9To1_8.class);
/*     */                     
/*     */                     if (tracker.isBlocking()) {
/*     */                       if (!Via.getConfig().isShowShieldWhenSwordInHand()) {
/*     */                         tracker.setSecondHand(null);
/*     */                       }
/*     */                       tracker.setBlocking(false);
/*     */                     } 
/*     */                   } 
/*     */                 });
/*     */           }
/*     */         });
/* 459 */     protocol.registerServerbound((ServerboundPacketType)ServerboundPackets1_9.PLAYER_POSITION, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/* 462 */             map((Type)Type.DOUBLE);
/* 463 */             map((Type)Type.DOUBLE);
/* 464 */             map((Type)Type.DOUBLE);
/* 465 */             map((Type)Type.BOOLEAN);
/* 466 */             handler((PacketHandler)new PlayerMovementMapper());
/*     */           }
/*     */         });
/* 469 */     protocol.registerServerbound((ServerboundPacketType)ServerboundPackets1_9.PLAYER_POSITION_AND_ROTATION, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/* 472 */             map((Type)Type.DOUBLE);
/* 473 */             map((Type)Type.DOUBLE);
/* 474 */             map((Type)Type.DOUBLE);
/* 475 */             map((Type)Type.FLOAT);
/* 476 */             map((Type)Type.FLOAT);
/* 477 */             map((Type)Type.BOOLEAN);
/* 478 */             handler((PacketHandler)new PlayerMovementMapper());
/*     */           }
/*     */         });
/* 481 */     protocol.registerServerbound((ServerboundPacketType)ServerboundPackets1_9.PLAYER_ROTATION, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/* 484 */             map((Type)Type.FLOAT);
/* 485 */             map((Type)Type.FLOAT);
/* 486 */             map((Type)Type.BOOLEAN);
/* 487 */             handler((PacketHandler)new PlayerMovementMapper());
/*     */           }
/*     */         });
/* 490 */     protocol.registerServerbound((ServerboundPacketType)ServerboundPackets1_9.PLAYER_MOVEMENT, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/* 493 */             map((Type)Type.BOOLEAN);
/* 494 */             handler((PacketHandler)new PlayerMovementMapper());
/*     */           }
/*     */         });
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\protocols\protocol1_9to1_8\packets\PlayerPackets.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */