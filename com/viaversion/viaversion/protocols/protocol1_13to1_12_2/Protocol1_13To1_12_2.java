/*     */ package com.viaversion.viaversion.protocols.protocol1_13to1_12_2;
/*     */ 
/*     */ import com.google.common.collect.Sets;
/*     */ import com.google.common.primitives.Ints;
/*     */ import com.viaversion.viaversion.api.Via;
/*     */ import com.viaversion.viaversion.api.connection.StorableObject;
/*     */ import com.viaversion.viaversion.api.connection.UserConnection;
/*     */ import com.viaversion.viaversion.api.data.MappingData;
/*     */ import com.viaversion.viaversion.api.data.entity.EntityTracker;
/*     */ import com.viaversion.viaversion.api.minecraft.Position;
/*     */ import com.viaversion.viaversion.api.minecraft.entities.Entity1_13Types;
/*     */ import com.viaversion.viaversion.api.minecraft.entities.EntityType;
/*     */ import com.viaversion.viaversion.api.minecraft.item.DataItem;
/*     */ import com.viaversion.viaversion.api.minecraft.item.Item;
/*     */ import com.viaversion.viaversion.api.platform.providers.Provider;
/*     */ import com.viaversion.viaversion.api.platform.providers.ViaProviders;
/*     */ import com.viaversion.viaversion.api.protocol.AbstractProtocol;
/*     */ import com.viaversion.viaversion.api.protocol.Protocol;
/*     */ import com.viaversion.viaversion.api.protocol.packet.ClientboundPacketType;
/*     */ import com.viaversion.viaversion.api.protocol.packet.PacketType;
/*     */ import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
/*     */ import com.viaversion.viaversion.api.protocol.packet.ServerboundPacketType;
/*     */ import com.viaversion.viaversion.api.protocol.packet.State;
/*     */ import com.viaversion.viaversion.api.protocol.remapper.PacketHandler;
/*     */ import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
/*     */ import com.viaversion.viaversion.api.protocol.remapper.ValueTransformer;
/*     */ import com.viaversion.viaversion.api.rewriter.EntityRewriter;
/*     */ import com.viaversion.viaversion.api.rewriter.ItemRewriter;
/*     */ import com.viaversion.viaversion.api.type.Type;
/*     */ import com.viaversion.viaversion.api.type.types.minecraft.ParticleType;
/*     */ import com.viaversion.viaversion.api.type.types.version.Types1_13;
/*     */ import com.viaversion.viaversion.data.entity.EntityTrackerBase;
/*     */ import com.viaversion.viaversion.libs.gson.JsonElement;
/*     */ import com.viaversion.viaversion.libs.gson.JsonObject;
/*     */ import com.viaversion.viaversion.libs.gson.JsonParseException;
/*     */ import com.viaversion.viaversion.protocols.protocol1_12_1to1_12.ClientboundPackets1_12_1;
/*     */ import com.viaversion.viaversion.protocols.protocol1_12_1to1_12.ServerboundPackets1_12_1;
/*     */ import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.blockconnections.ConnectionData;
/*     */ import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.blockconnections.providers.BlockConnectionProvider;
/*     */ import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.data.BlockIdData;
/*     */ import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.data.ComponentRewriter1_13;
/*     */ import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.data.MappingData;
/*     */ import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.data.RecipeData;
/*     */ import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.data.StatisticData;
/*     */ import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.data.StatisticMappings;
/*     */ import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.metadata.MetadataRewriter1_13To1_12_2;
/*     */ import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.packets.EntityPackets;
/*     */ import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.packets.InventoryPackets;
/*     */ import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.packets.WorldPackets;
/*     */ import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.providers.BlockEntityProvider;
/*     */ import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.providers.PaintingProvider;
/*     */ import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.providers.PlayerLookTargetProvider;
/*     */ import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.storage.BlockConnectionStorage;
/*     */ import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.storage.BlockStorage;
/*     */ import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.storage.TabCompleteTracker;
/*     */ import com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2.storage.ClientWorld;
/*     */ import com.viaversion.viaversion.rewriter.SoundRewriter;
/*     */ import com.viaversion.viaversion.util.ChatColorUtil;
/*     */ import com.viaversion.viaversion.util.GsonUtil;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Protocol1_13To1_12_2
/*     */   extends AbstractProtocol<ClientboundPackets1_12_1, ClientboundPackets1_13, ServerboundPackets1_12_1, ServerboundPackets1_13>
/*     */ {
/*  74 */   public static final MappingData MAPPINGS = new MappingData();
/*     */   
/*  76 */   private static final Map<Character, Character> SCOREBOARD_TEAM_NAME_REWRITE = new HashMap<>();
/*  77 */   private static final Set<Character> FORMATTING_CODES = Sets.newHashSet((Object[])new Character[] { Character.valueOf('k'), Character.valueOf('l'), Character.valueOf('m'), Character.valueOf('n'), Character.valueOf('o'), Character.valueOf('r') });
/*  78 */   private final MetadataRewriter1_13To1_12_2 entityRewriter = new MetadataRewriter1_13To1_12_2(this);
/*  79 */   private final InventoryPackets itemRewriter = new InventoryPackets(this);
/*  80 */   private final ComponentRewriter1_13<ClientboundPackets1_12_1> componentRewriter = new ComponentRewriter1_13((Protocol)this);
/*     */   
/*     */   static {
/*  83 */     SCOREBOARD_TEAM_NAME_REWRITE.put(Character.valueOf('0'), Character.valueOf('g'));
/*  84 */     SCOREBOARD_TEAM_NAME_REWRITE.put(Character.valueOf('1'), Character.valueOf('h'));
/*  85 */     SCOREBOARD_TEAM_NAME_REWRITE.put(Character.valueOf('2'), Character.valueOf('i'));
/*  86 */     SCOREBOARD_TEAM_NAME_REWRITE.put(Character.valueOf('3'), Character.valueOf('j'));
/*  87 */     SCOREBOARD_TEAM_NAME_REWRITE.put(Character.valueOf('4'), Character.valueOf('p'));
/*  88 */     SCOREBOARD_TEAM_NAME_REWRITE.put(Character.valueOf('5'), Character.valueOf('q'));
/*  89 */     SCOREBOARD_TEAM_NAME_REWRITE.put(Character.valueOf('6'), Character.valueOf('s'));
/*  90 */     SCOREBOARD_TEAM_NAME_REWRITE.put(Character.valueOf('7'), Character.valueOf('t'));
/*  91 */     SCOREBOARD_TEAM_NAME_REWRITE.put(Character.valueOf('8'), Character.valueOf('u'));
/*  92 */     SCOREBOARD_TEAM_NAME_REWRITE.put(Character.valueOf('9'), Character.valueOf('v'));
/*  93 */     SCOREBOARD_TEAM_NAME_REWRITE.put(Character.valueOf('a'), Character.valueOf('w'));
/*  94 */     SCOREBOARD_TEAM_NAME_REWRITE.put(Character.valueOf('b'), Character.valueOf('x'));
/*  95 */     SCOREBOARD_TEAM_NAME_REWRITE.put(Character.valueOf('c'), Character.valueOf('y'));
/*  96 */     SCOREBOARD_TEAM_NAME_REWRITE.put(Character.valueOf('d'), Character.valueOf('z'));
/*  97 */     SCOREBOARD_TEAM_NAME_REWRITE.put(Character.valueOf('e'), Character.valueOf('!'));
/*  98 */     SCOREBOARD_TEAM_NAME_REWRITE.put(Character.valueOf('f'), Character.valueOf('?'));
/*  99 */     SCOREBOARD_TEAM_NAME_REWRITE.put(Character.valueOf('k'), Character.valueOf('#'));
/* 100 */     SCOREBOARD_TEAM_NAME_REWRITE.put(Character.valueOf('l'), Character.valueOf('('));
/* 101 */     SCOREBOARD_TEAM_NAME_REWRITE.put(Character.valueOf('m'), Character.valueOf(')'));
/* 102 */     SCOREBOARD_TEAM_NAME_REWRITE.put(Character.valueOf('n'), Character.valueOf(':'));
/* 103 */     SCOREBOARD_TEAM_NAME_REWRITE.put(Character.valueOf('o'), Character.valueOf(';'));
/* 104 */     SCOREBOARD_TEAM_NAME_REWRITE.put(Character.valueOf('r'), Character.valueOf('/'));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 111 */     POS_TO_3_INT = (wrapper -> {
/*     */         Position position = (Position)wrapper.read(Type.POSITION);
/*     */         
/*     */         wrapper.write((Type)Type.INT, Integer.valueOf(position.x()));
/*     */         wrapper.write((Type)Type.INT, Integer.valueOf(position.y()));
/*     */         wrapper.write((Type)Type.INT, Integer.valueOf(position.z()));
/*     */       });
/* 118 */     SEND_DECLARE_COMMANDS_AND_TAGS = (w -> {
/*     */         w.create((PacketType)ClientboundPackets1_13.DECLARE_COMMANDS, ()).scheduleSend(Protocol1_13To1_12_2.class);
/*     */         w.create((PacketType)ClientboundPackets1_13.TAGS, ()).scheduleSend(Protocol1_13To1_12_2.class);
/*     */       });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final PacketHandler POS_TO_3_INT;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final PacketHandler SEND_DECLARE_COMMANDS_AND_TAGS;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Protocol1_13To1_12_2() {
/*     */     super(ClientboundPackets1_12_1.class, ClientboundPackets1_13.class, ServerboundPackets1_12_1.class, ServerboundPackets1_13.class);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void registerPackets() {
/* 164 */     this.entityRewriter.register();
/* 165 */     this.itemRewriter.register();
/*     */     
/* 167 */     EntityPackets.register(this);
/* 168 */     WorldPackets.register(this);
/*     */     
/* 170 */     registerClientbound(State.LOGIN, 0, 0, wrapper -> this.componentRewriter.processText((JsonElement)wrapper.passthrough(Type.COMPONENT)));
/*     */     
/* 172 */     registerClientbound(State.STATUS, 0, 0, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/* 175 */             map(Type.STRING);
/* 176 */             handler(wrapper -> {
/*     */                   String response = (String)wrapper.get(Type.STRING, 0);
/*     */                   try {
/*     */                     JsonObject json = (JsonObject)GsonUtil.getGson().fromJson(response, JsonObject.class);
/*     */                     if (json.has("favicon")) {
/*     */                       json.addProperty("favicon", json.get("favicon").getAsString().replace("\n", ""));
/*     */                     }
/*     */                     wrapper.set(Type.STRING, 0, GsonUtil.getGson().toJson((JsonElement)json));
/* 184 */                   } catch (JsonParseException e) {
/*     */                     e.printStackTrace();
/*     */                   } 
/*     */                 });
/*     */           }
/*     */         });
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 194 */     registerClientbound((ClientboundPacketType)ClientboundPackets1_12_1.STATISTICS, wrapper -> {
/*     */           int size = ((Integer)wrapper.read((Type)Type.VAR_INT)).intValue();
/*     */           List<StatisticData> remappedStats = new ArrayList<>();
/*     */           for (int i = 0; i < size; i++) {
/*     */             String name = (String)wrapper.read(Type.STRING);
/*     */             String[] split = name.split("\\.");
/*     */             int categoryId = 0;
/*     */             int newId = -1;
/*     */             int value = ((Integer)wrapper.read((Type)Type.VAR_INT)).intValue();
/*     */             if (split.length == 2) {
/*     */               categoryId = 8;
/*     */               Integer newIdRaw = (Integer)StatisticMappings.CUSTOM_STATS.get(name);
/*     */               if (newIdRaw != null) {
/*     */                 newId = newIdRaw.intValue();
/*     */               } else {
/*     */                 Via.getPlatform().getLogger().warning("Could not find 1.13 -> 1.12.2 statistic mapping for " + name);
/*     */               } 
/*     */             } else if (split.length > 2) {
/*     */               String category = split[1];
/*     */               switch (category) {
/*     */                 case "mineBlock":
/*     */                   categoryId = 0;
/*     */                   break;
/*     */                 
/*     */                 case "craftItem":
/*     */                   categoryId = 1;
/*     */                   break;
/*     */                 
/*     */                 case "useItem":
/*     */                   categoryId = 2;
/*     */                   break;
/*     */                 
/*     */                 case "breakItem":
/*     */                   categoryId = 3;
/*     */                   break;
/*     */                 
/*     */                 case "pickup":
/*     */                   categoryId = 4;
/*     */                   break;
/*     */                 case "drop":
/*     */                   categoryId = 5;
/*     */                   break;
/*     */                 case "killEntity":
/*     */                   categoryId = 6;
/*     */                   break;
/*     */                 case "entityKilledBy":
/*     */                   categoryId = 7;
/*     */                   break;
/*     */               } 
/*     */             } 
/*     */             if (newId != -1) {
/*     */               remappedStats.add(new StatisticData(categoryId, newId, value));
/*     */             }
/*     */           } 
/*     */           wrapper.write((Type)Type.VAR_INT, Integer.valueOf(remappedStats.size()));
/*     */           for (StatisticData stat : remappedStats) {
/*     */             wrapper.write((Type)Type.VAR_INT, Integer.valueOf(stat.getCategoryId()));
/*     */             wrapper.write((Type)Type.VAR_INT, Integer.valueOf(stat.getNewId()));
/*     */             wrapper.write((Type)Type.VAR_INT, Integer.valueOf(stat.getValue()));
/*     */           } 
/*     */         });
/* 255 */     this.componentRewriter.registerBossBar((ClientboundPacketType)ClientboundPackets1_12_1.BOSSBAR);
/* 256 */     this.componentRewriter.registerComponentPacket((ClientboundPacketType)ClientboundPackets1_12_1.CHAT_MESSAGE);
/*     */     
/* 258 */     registerClientbound((ClientboundPacketType)ClientboundPackets1_12_1.TAB_COMPLETE, wrapper -> {
/*     */           int index;
/*     */           
/*     */           int length;
/*     */           
/*     */           wrapper.write((Type)Type.VAR_INT, Integer.valueOf(((TabCompleteTracker)wrapper.user().get(TabCompleteTracker.class)).getTransactionId()));
/*     */           
/*     */           String input = ((TabCompleteTracker)wrapper.user().get(TabCompleteTracker.class)).getInput();
/*     */           
/*     */           if (input.endsWith(" ") || input.isEmpty()) {
/*     */             index = input.length();
/*     */             
/*     */             length = 0;
/*     */           } else {
/*     */             int lastSpace = input.lastIndexOf(' ') + 1;
/*     */             
/*     */             index = lastSpace;
/*     */             
/*     */             length = input.length() - lastSpace;
/*     */           } 
/*     */           
/*     */           wrapper.write((Type)Type.VAR_INT, Integer.valueOf(index));
/*     */           wrapper.write((Type)Type.VAR_INT, Integer.valueOf(length));
/*     */           int count = ((Integer)wrapper.passthrough((Type)Type.VAR_INT)).intValue();
/*     */           for (int i = 0; i < count; i++) {
/*     */             String suggestion = (String)wrapper.read(Type.STRING);
/*     */             if (suggestion.startsWith("/") && index == 0) {
/*     */               suggestion = suggestion.substring(1);
/*     */             }
/*     */             wrapper.write(Type.STRING, suggestion);
/*     */             wrapper.write((Type)Type.BOOLEAN, Boolean.valueOf(false));
/*     */           } 
/*     */         });
/* 291 */     registerClientbound((ClientboundPacketType)ClientboundPackets1_12_1.OPEN_WINDOW, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/* 294 */             map((Type)Type.UNSIGNED_BYTE);
/* 295 */             map(Type.STRING);
/* 296 */             handler(wrapper -> Protocol1_13To1_12_2.this.componentRewriter.processText((JsonElement)wrapper.passthrough(Type.COMPONENT)));
/*     */           }
/*     */         });
/*     */     
/* 300 */     registerClientbound((ClientboundPacketType)ClientboundPackets1_12_1.COOLDOWN, wrapper -> {
/*     */           int item = ((Integer)wrapper.read((Type)Type.VAR_INT)).intValue();
/*     */           
/*     */           int ticks = ((Integer)wrapper.read((Type)Type.VAR_INT)).intValue();
/*     */           wrapper.cancel();
/*     */           if (item == 383) {
/*     */             int i = 0;
/*     */             while (i < 44) {
/*     */               int newItem = MAPPINGS.getItemMappings().getNewId(item << 16 | i);
/*     */               if (newItem != -1) {
/*     */                 PacketWrapper packet = wrapper.create((PacketType)ClientboundPackets1_13.COOLDOWN);
/*     */                 packet.write((Type)Type.VAR_INT, Integer.valueOf(newItem));
/*     */                 packet.write((Type)Type.VAR_INT, Integer.valueOf(ticks));
/*     */                 packet.send(Protocol1_13To1_12_2.class);
/*     */                 i++;
/*     */               } 
/*     */             } 
/*     */           } else {
/*     */             int i = 0;
/*     */             while (i < 16) {
/*     */               int newItem = MAPPINGS.getItemMappings().getNewId(item << 4 | i);
/*     */               if (newItem != -1) {
/*     */                 PacketWrapper packet = wrapper.create((PacketType)ClientboundPackets1_13.COOLDOWN);
/*     */                 packet.write((Type)Type.VAR_INT, Integer.valueOf(newItem));
/*     */                 packet.write((Type)Type.VAR_INT, Integer.valueOf(ticks));
/*     */                 packet.send(Protocol1_13To1_12_2.class);
/*     */                 i++;
/*     */               } 
/*     */             } 
/*     */           } 
/*     */         });
/* 331 */     this.componentRewriter.registerComponentPacket((ClientboundPacketType)ClientboundPackets1_12_1.DISCONNECT);
/*     */     
/* 333 */     registerClientbound((ClientboundPacketType)ClientboundPackets1_12_1.EFFECT, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/* 336 */             map((Type)Type.INT);
/* 337 */             map(Type.POSITION);
/* 338 */             map((Type)Type.INT);
/* 339 */             handler(wrapper -> {
/*     */                   int id = ((Integer)wrapper.get((Type)Type.INT, 0)).intValue();
/*     */                   
/*     */                   int data = ((Integer)wrapper.get((Type)Type.INT, 1)).intValue();
/*     */                   if (id == 1010) {
/*     */                     wrapper.set((Type)Type.INT, 1, Integer.valueOf(Protocol1_13To1_12_2.this.getMappingData().getItemMappings().getNewId(data << 4)));
/*     */                   } else if (id == 2001) {
/*     */                     int blockId = data & 0xFFF;
/*     */                     int blockData = data >> 12;
/*     */                     wrapper.set((Type)Type.INT, 1, Integer.valueOf(WorldPackets.toNewId(blockId << 4 | blockData)));
/*     */                   } 
/*     */                 });
/*     */           }
/*     */         });
/* 353 */     registerClientbound((ClientboundPacketType)ClientboundPackets1_12_1.CRAFT_RECIPE_RESPONSE, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/* 356 */             map((Type)Type.BYTE);
/* 357 */             handler(wrapper -> wrapper.write(Type.STRING, "viaversion:legacy/" + wrapper.read((Type)Type.VAR_INT)));
/*     */           }
/*     */         });
/*     */     
/* 361 */     this.componentRewriter.registerCombatEvent((ClientboundPacketType)ClientboundPackets1_12_1.COMBAT_EVENT);
/*     */     
/* 363 */     registerClientbound((ClientboundPacketType)ClientboundPackets1_12_1.MAP_DATA, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/* 366 */             map((Type)Type.VAR_INT);
/* 367 */             map((Type)Type.BYTE);
/* 368 */             map((Type)Type.BOOLEAN);
/* 369 */             handler(wrapper -> {
/*     */                   int iconCount = ((Integer)wrapper.passthrough((Type)Type.VAR_INT)).intValue();
/*     */                   for (int i = 0; i < iconCount; i++) {
/*     */                     byte directionAndType = ((Byte)wrapper.read((Type)Type.BYTE)).byteValue();
/*     */                     int type = (directionAndType & 0xF0) >> 4;
/*     */                     wrapper.write((Type)Type.VAR_INT, Integer.valueOf(type));
/*     */                     wrapper.passthrough((Type)Type.BYTE);
/*     */                     wrapper.passthrough((Type)Type.BYTE);
/*     */                     byte direction = (byte)(directionAndType & 0xF);
/*     */                     wrapper.write((Type)Type.BYTE, Byte.valueOf(direction));
/*     */                     wrapper.write(Type.OPTIONAL_COMPONENT, null);
/*     */                   } 
/*     */                 });
/*     */           }
/*     */         });
/* 384 */     registerClientbound((ClientboundPacketType)ClientboundPackets1_12_1.UNLOCK_RECIPES, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/* 387 */             map((Type)Type.VAR_INT);
/* 388 */             map((Type)Type.BOOLEAN);
/* 389 */             map((Type)Type.BOOLEAN);
/* 390 */             handler(wrapper -> {
/*     */                   wrapper.write((Type)Type.BOOLEAN, Boolean.valueOf(false));
/*     */                   wrapper.write((Type)Type.BOOLEAN, Boolean.valueOf(false));
/*     */                 });
/* 394 */             handler(wrapper -> {
/*     */                   int action = ((Integer)wrapper.get((Type)Type.VAR_INT, 0)).intValue();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */                   
/*     */                   for (int i = 0; i < ((action == 0) ? 2 : 1); i++) {
/*     */                     int[] ids = (int[])wrapper.read(Type.VAR_INT_ARRAY_PRIMITIVE);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */                     
/*     */                     String[] stringIds = new String[ids.length];
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */                     
/*     */                     for (int j = 0; j < ids.length; j++) {
/*     */                       stringIds[j] = "viaversion:legacy/" + ids[j];
/*     */                     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */                     
/*     */                     wrapper.write(Type.STRING_ARRAY, stringIds);
/*     */                   } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */                   
/*     */                   if (action == 0) {
/*     */                     wrapper.create((PacketType)ClientboundPackets1_13.DECLARE_RECIPES, ()).send(Protocol1_13To1_12_2.class);
/*     */                   }
/*     */                 });
/*     */           }
/*     */         });
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 461 */     registerClientbound((ClientboundPacketType)ClientboundPackets1_12_1.RESPAWN, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/* 464 */             map((Type)Type.INT);
/* 465 */             handler(wrapper -> {
/*     */                   ClientWorld clientWorld = (ClientWorld)wrapper.user().get(ClientWorld.class);
/*     */                   
/*     */                   int dimensionId = ((Integer)wrapper.get((Type)Type.INT, 0)).intValue();
/*     */                   clientWorld.setEnvironment(dimensionId);
/*     */                   if (Via.getConfig().isServersideBlockConnections()) {
/*     */                     ConnectionData.clearBlockStorage(wrapper.user());
/*     */                   }
/*     */                 });
/* 474 */             handler(Protocol1_13To1_12_2.SEND_DECLARE_COMMANDS_AND_TAGS);
/*     */           }
/*     */         });
/*     */     
/* 478 */     registerClientbound((ClientboundPacketType)ClientboundPackets1_12_1.SCOREBOARD_OBJECTIVE, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/* 481 */             map(Type.STRING);
/* 482 */             map((Type)Type.BYTE);
/* 483 */             handler(wrapper -> {
/*     */                   byte mode = ((Byte)wrapper.get((Type)Type.BYTE, 0)).byteValue();
/*     */                   
/*     */                   if (mode == 0 || mode == 2) {
/*     */                     String value = (String)wrapper.read(Type.STRING);
/*     */                     
/*     */                     wrapper.write(Type.COMPONENT, ChatRewriter.legacyTextToJson(value));
/*     */                     
/*     */                     String type = (String)wrapper.read(Type.STRING);
/*     */                     
/*     */                     wrapper.write((Type)Type.VAR_INT, Integer.valueOf(type.equals("integer") ? 0 : 1));
/*     */                   } 
/*     */                 });
/*     */           }
/*     */         });
/* 498 */     registerClientbound((ClientboundPacketType)ClientboundPackets1_12_1.TEAMS, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/* 501 */             map(Type.STRING);
/* 502 */             map((Type)Type.BYTE);
/*     */             
/* 504 */             handler(wrapper -> {
/*     */                   byte action = ((Byte)wrapper.get((Type)Type.BYTE, 0)).byteValue();
/*     */                   
/*     */                   if (action == 0 || action == 2) {
/*     */                     String displayName = (String)wrapper.read(Type.STRING);
/*     */                     
/*     */                     wrapper.write(Type.COMPONENT, ChatRewriter.legacyTextToJson(displayName));
/*     */                     
/*     */                     String prefix = (String)wrapper.read(Type.STRING);
/*     */                     
/*     */                     String suffix = (String)wrapper.read(Type.STRING);
/*     */                     
/*     */                     wrapper.passthrough((Type)Type.BYTE);
/*     */                     
/*     */                     wrapper.passthrough(Type.STRING);
/*     */                     
/*     */                     wrapper.passthrough(Type.STRING);
/*     */                     
/*     */                     int colour = ((Byte)wrapper.read((Type)Type.BYTE)).intValue();
/*     */                     
/*     */                     if (colour == -1) {
/*     */                       colour = 21;
/*     */                     }
/*     */                     
/*     */                     if (Via.getConfig().is1_13TeamColourFix()) {
/*     */                       char lastColorChar = Protocol1_13To1_12_2.this.getLastColorChar(prefix);
/*     */                       
/*     */                       colour = ChatColorUtil.getColorOrdinal(lastColorChar);
/*     */                       suffix = '§' + Character.toString(lastColorChar) + suffix;
/*     */                     } 
/*     */                     wrapper.write((Type)Type.VAR_INT, Integer.valueOf(colour));
/*     */                     wrapper.write(Type.COMPONENT, ChatRewriter.legacyTextToJson(prefix));
/*     */                     wrapper.write(Type.COMPONENT, ChatRewriter.legacyTextToJson(suffix));
/*     */                   } 
/*     */                   if (action == 0 || action == 3 || action == 4) {
/*     */                     String[] names = (String[])wrapper.read(Type.STRING_ARRAY);
/*     */                     for (int i = 0; i < names.length; i++) {
/*     */                       names[i] = Protocol1_13To1_12_2.this.rewriteTeamMemberName(names[i]);
/*     */                     }
/*     */                     wrapper.write(Type.STRING_ARRAY, names);
/*     */                   } 
/*     */                 });
/*     */           }
/*     */         });
/* 548 */     registerClientbound((ClientboundPacketType)ClientboundPackets1_12_1.UPDATE_SCORE, wrapper -> {
/*     */           String displayName = (String)wrapper.read(Type.STRING);
/*     */           
/*     */           displayName = rewriteTeamMemberName(displayName);
/*     */           
/*     */           wrapper.write(Type.STRING, displayName);
/*     */           byte action = ((Byte)wrapper.read((Type)Type.BYTE)).byteValue();
/*     */           wrapper.write((Type)Type.BYTE, Byte.valueOf(action));
/*     */           wrapper.passthrough(Type.STRING);
/*     */           if (action != 1) {
/*     */             wrapper.passthrough((Type)Type.VAR_INT);
/*     */           }
/*     */         });
/* 561 */     this.componentRewriter.registerTitle((ClientboundPacketType)ClientboundPackets1_12_1.TITLE);
/*     */ 
/*     */ 
/*     */     
/* 565 */     (new SoundRewriter((Protocol)this)).registerSound((ClientboundPacketType)ClientboundPackets1_12_1.SOUND);
/*     */     
/* 567 */     registerClientbound((ClientboundPacketType)ClientboundPackets1_12_1.TAB_LIST, wrapper -> {
/*     */           this.componentRewriter.processText((JsonElement)wrapper.passthrough(Type.COMPONENT));
/*     */           
/*     */           this.componentRewriter.processText((JsonElement)wrapper.passthrough(Type.COMPONENT));
/*     */         });
/* 572 */     registerClientbound((ClientboundPacketType)ClientboundPackets1_12_1.ADVANCEMENTS, wrapper -> {
/*     */           wrapper.passthrough((Type)Type.BOOLEAN);
/*     */           
/*     */           int size = ((Integer)wrapper.passthrough((Type)Type.VAR_INT)).intValue();
/*     */           
/*     */           for (int i = 0; i < size; i++) {
/*     */             wrapper.passthrough(Type.STRING);
/*     */             
/*     */             if (((Boolean)wrapper.passthrough((Type)Type.BOOLEAN)).booleanValue()) {
/*     */               wrapper.passthrough(Type.STRING);
/*     */             }
/*     */             
/*     */             if (((Boolean)wrapper.passthrough((Type)Type.BOOLEAN)).booleanValue()) {
/*     */               this.componentRewriter.processText((JsonElement)wrapper.passthrough(Type.COMPONENT));
/*     */               
/*     */               this.componentRewriter.processText((JsonElement)wrapper.passthrough(Type.COMPONENT));
/*     */               
/*     */               Item icon = (Item)wrapper.read(Type.ITEM);
/*     */               
/*     */               this.itemRewriter.handleItemToClient(icon);
/*     */               
/*     */               wrapper.write(Type.FLAT_ITEM, icon);
/*     */               
/*     */               wrapper.passthrough((Type)Type.VAR_INT);
/*     */               
/*     */               int flags = ((Integer)wrapper.passthrough((Type)Type.INT)).intValue();
/*     */               if ((flags & 0x1) != 0) {
/*     */                 wrapper.passthrough(Type.STRING);
/*     */               }
/*     */               wrapper.passthrough((Type)Type.FLOAT);
/*     */               wrapper.passthrough((Type)Type.FLOAT);
/*     */             } 
/*     */             wrapper.passthrough(Type.STRING_ARRAY);
/*     */             int arrayLength = ((Integer)wrapper.passthrough((Type)Type.VAR_INT)).intValue();
/*     */             for (int array = 0; array < arrayLength; array++) {
/*     */               wrapper.passthrough(Type.STRING_ARRAY);
/*     */             }
/*     */           } 
/*     */         });
/* 611 */     cancelServerbound(State.LOGIN, 2);
/*     */ 
/*     */     
/* 614 */     cancelServerbound(ServerboundPackets1_13.QUERY_BLOCK_NBT);
/*     */ 
/*     */     
/* 617 */     registerServerbound(ServerboundPackets1_13.TAB_COMPLETE, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/* 620 */             handler(wrapper -> {
/*     */                   if (Via.getConfig().isDisable1_13AutoComplete()) {
/*     */                     wrapper.cancel();
/*     */                   }
/*     */                   
/*     */                   int tid = ((Integer)wrapper.read((Type)Type.VAR_INT)).intValue();
/*     */                   
/*     */                   ((TabCompleteTracker)wrapper.user().get(TabCompleteTracker.class)).setTransactionId(tid);
/*     */                 });
/*     */             
/* 630 */             map(Type.STRING, new ValueTransformer<String, String>(Type.STRING)
/*     */                 {
/*     */                   public String transform(PacketWrapper wrapper, String inputValue) {
/* 633 */                     ((TabCompleteTracker)wrapper.user().get(TabCompleteTracker.class)).setInput(inputValue);
/* 634 */                     return "/" + inputValue;
/*     */                   }
/*     */                 });
/*     */             
/* 638 */             handler(wrapper -> {
/*     */                   wrapper.write((Type)Type.BOOLEAN, Boolean.valueOf(false));
/*     */                   
/*     */                   Position playerLookTarget = ((PlayerLookTargetProvider)Via.getManager().getProviders().get(PlayerLookTargetProvider.class)).getPlayerLookTarget(wrapper.user());
/*     */                   
/*     */                   wrapper.write(Type.OPTIONAL_POSITION, playerLookTarget);
/*     */                   if (!wrapper.isCancelled() && Via.getConfig().get1_13TabCompleteDelay() > 0) {
/*     */                     TabCompleteTracker tracker = (TabCompleteTracker)wrapper.user().get(TabCompleteTracker.class);
/*     */                     wrapper.cancel();
/*     */                     tracker.setTimeToSend(System.currentTimeMillis() + Via.getConfig().get1_13TabCompleteDelay() * 50L);
/*     */                     tracker.setLastTabComplete((String)wrapper.get(Type.STRING, 0));
/*     */                   } 
/*     */                 });
/*     */           }
/*     */         });
/* 653 */     registerServerbound(ServerboundPackets1_13.EDIT_BOOK, (ServerboundPacketType)ServerboundPackets1_12_1.PLUGIN_MESSAGE, wrapper -> {
/*     */           Item item = (Item)wrapper.read(Type.FLAT_ITEM);
/*     */           
/*     */           boolean isSigning = ((Boolean)wrapper.read((Type)Type.BOOLEAN)).booleanValue();
/*     */           
/*     */           this.itemRewriter.handleItemToServer(item);
/*     */           
/*     */           wrapper.write(Type.STRING, isSigning ? "MC|BSign" : "MC|BEdit");
/*     */           
/*     */           wrapper.write(Type.ITEM, item);
/*     */         });
/* 664 */     cancelServerbound(ServerboundPackets1_13.ENTITY_NBT_REQUEST);
/*     */ 
/*     */     
/* 667 */     registerServerbound(ServerboundPackets1_13.PICK_ITEM, (ServerboundPacketType)ServerboundPackets1_12_1.PLUGIN_MESSAGE, wrapper -> wrapper.write(Type.STRING, "MC|PickItem"));
/*     */ 
/*     */ 
/*     */     
/* 671 */     registerServerbound(ServerboundPackets1_13.CRAFT_RECIPE_REQUEST, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/* 674 */             map((Type)Type.BYTE);
/*     */             
/* 676 */             handler(wrapper -> {
/*     */                   String s = (String)wrapper.read(Type.STRING);
/*     */                   
/*     */                   Integer id;
/*     */                   
/*     */                   if (s.length() < 19 || (id = Ints.tryParse(s.substring(18))) == null) {
/*     */                     wrapper.cancel();
/*     */                     return;
/*     */                   } 
/*     */                   wrapper.write((Type)Type.VAR_INT, id);
/*     */                 });
/*     */           }
/*     */         });
/* 689 */     registerServerbound(ServerboundPackets1_13.RECIPE_BOOK_DATA, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/* 692 */             map((Type)Type.VAR_INT);
/*     */             
/* 694 */             handler(wrapper -> {
/*     */                   int type = ((Integer)wrapper.get((Type)Type.VAR_INT, 0)).intValue();
/*     */                   
/*     */                   if (type == 0) {
/*     */                     String s = (String)wrapper.read(Type.STRING);
/*     */                     
/*     */                     Integer id;
/*     */                     
/*     */                     if (s.length() < 19 || (id = Ints.tryParse(s.substring(18))) == null) {
/*     */                       wrapper.cancel();
/*     */                       
/*     */                       return;
/*     */                     } 
/*     */                     
/*     */                     wrapper.write((Type)Type.INT, id);
/*     */                   } 
/*     */                   if (type == 1) {
/*     */                     wrapper.passthrough((Type)Type.BOOLEAN);
/*     */                     wrapper.passthrough((Type)Type.BOOLEAN);
/*     */                     wrapper.read((Type)Type.BOOLEAN);
/*     */                     wrapper.read((Type)Type.BOOLEAN);
/*     */                   } 
/*     */                 });
/*     */           }
/*     */         });
/* 719 */     registerServerbound(ServerboundPackets1_13.RENAME_ITEM, (ServerboundPacketType)ServerboundPackets1_12_1.PLUGIN_MESSAGE, wrapper -> wrapper.write(Type.STRING, "MC|ItemName"));
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 724 */     registerServerbound(ServerboundPackets1_13.SELECT_TRADE, (ServerboundPacketType)ServerboundPackets1_12_1.PLUGIN_MESSAGE, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/* 727 */             handler(wrapper -> wrapper.write(Type.STRING, "MC|TrSel"));
/*     */ 
/*     */             
/* 730 */             map((Type)Type.VAR_INT, (Type)Type.INT);
/*     */           }
/*     */         });
/*     */ 
/*     */     
/* 735 */     registerServerbound(ServerboundPackets1_13.SET_BEACON_EFFECT, (ServerboundPacketType)ServerboundPackets1_12_1.PLUGIN_MESSAGE, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/* 738 */             handler(wrapper -> wrapper.write(Type.STRING, "MC|Beacon"));
/*     */ 
/*     */             
/* 741 */             map((Type)Type.VAR_INT, (Type)Type.INT);
/* 742 */             map((Type)Type.VAR_INT, (Type)Type.INT);
/*     */           }
/*     */         });
/*     */ 
/*     */     
/* 747 */     registerServerbound(ServerboundPackets1_13.UPDATE_COMMAND_BLOCK, (ServerboundPacketType)ServerboundPackets1_12_1.PLUGIN_MESSAGE, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/* 750 */             handler(wrapper -> wrapper.write(Type.STRING, "MC|AutoCmd"));
/* 751 */             handler(Protocol1_13To1_12_2.POS_TO_3_INT);
/* 752 */             map(Type.STRING);
/* 753 */             handler(wrapper -> {
/*     */                   int mode = ((Integer)wrapper.read((Type)Type.VAR_INT)).intValue();
/*     */                   
/*     */                   byte flags = ((Byte)wrapper.read((Type)Type.BYTE)).byteValue();
/*     */                   
/*     */                   String stringMode = (mode == 0) ? "SEQUENCE" : ((mode == 1) ? "AUTO" : "REDSTONE");
/*     */                   
/*     */                   wrapper.write((Type)Type.BOOLEAN, Boolean.valueOf(((flags & 0x1) != 0)));
/*     */                   
/*     */                   wrapper.write(Type.STRING, stringMode);
/*     */                   
/*     */                   wrapper.write((Type)Type.BOOLEAN, Boolean.valueOf(((flags & 0x2) != 0)));
/*     */                   
/*     */                   wrapper.write((Type)Type.BOOLEAN, Boolean.valueOf(((flags & 0x4) != 0)));
/*     */                 });
/*     */           }
/*     */         });
/* 770 */     registerServerbound(ServerboundPackets1_13.UPDATE_COMMAND_BLOCK_MINECART, (ServerboundPacketType)ServerboundPackets1_12_1.PLUGIN_MESSAGE, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/* 773 */             handler(wrapper -> {
/*     */                   wrapper.write(Type.STRING, "MC|AdvCmd");
/*     */                   wrapper.write((Type)Type.BYTE, Byte.valueOf((byte)1));
/*     */                 });
/* 777 */             map((Type)Type.VAR_INT, (Type)Type.INT);
/*     */           }
/*     */         });
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 784 */     registerServerbound(ServerboundPackets1_13.UPDATE_STRUCTURE_BLOCK, (ServerboundPacketType)ServerboundPackets1_12_1.PLUGIN_MESSAGE, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/* 787 */             handler(wrapper -> wrapper.write(Type.STRING, "MC|Struct"));
/*     */ 
/*     */             
/* 790 */             handler(Protocol1_13To1_12_2.POS_TO_3_INT);
/* 791 */             map((Type)Type.VAR_INT, new ValueTransformer<Integer, Byte>((Type)Type.BYTE)
/*     */                 {
/*     */                   public Byte transform(PacketWrapper wrapper, Integer action) throws Exception {
/* 794 */                     return Byte.valueOf((byte)(action.intValue() + 1));
/*     */                   }
/*     */                 });
/* 797 */             map((Type)Type.VAR_INT, new ValueTransformer<Integer, String>(Type.STRING)
/*     */                 {
/*     */                   public String transform(PacketWrapper wrapper, Integer mode) throws Exception {
/* 800 */                     return (mode.intValue() == 0) ? "SAVE" : (
/* 801 */                       (mode.intValue() == 1) ? "LOAD" : (
/* 802 */                       (mode.intValue() == 2) ? "CORNER" : "DATA"));
/*     */                   }
/*     */                 });
/*     */             
/* 806 */             map(Type.STRING);
/* 807 */             map((Type)Type.BYTE, (Type)Type.INT);
/* 808 */             map((Type)Type.BYTE, (Type)Type.INT);
/* 809 */             map((Type)Type.BYTE, (Type)Type.INT);
/* 810 */             map((Type)Type.BYTE, (Type)Type.INT);
/* 811 */             map((Type)Type.BYTE, (Type)Type.INT);
/* 812 */             map((Type)Type.BYTE, (Type)Type.INT);
/* 813 */             map((Type)Type.VAR_INT, new ValueTransformer<Integer, String>(Type.STRING)
/*     */                 {
/*     */                   public String transform(PacketWrapper wrapper, Integer mirror) throws Exception {
/* 816 */                     return (mirror.intValue() == 0) ? "NONE" : (
/* 817 */                       (mirror.intValue() == 1) ? "LEFT_RIGHT" : "FRONT_BACK");
/*     */                   }
/*     */                 });
/*     */             
/* 821 */             map((Type)Type.VAR_INT, new ValueTransformer<Integer, String>(Type.STRING)
/*     */                 {
/*     */                   public String transform(PacketWrapper wrapper, Integer rotation) throws Exception {
/* 824 */                     return (rotation.intValue() == 0) ? "NONE" : (
/* 825 */                       (rotation.intValue() == 1) ? "CLOCKWISE_90" : (
/* 826 */                       (rotation.intValue() == 2) ? "CLOCKWISE_180" : "COUNTERCLOCKWISE_90"));
/*     */                   }
/*     */                 });
/*     */             
/* 830 */             map(Type.STRING);
/* 831 */             handler(wrapper -> {
/*     */                   float integrity = ((Float)wrapper.read((Type)Type.FLOAT)).floatValue();
/*     */                   long seed = ((Long)wrapper.read((Type)Type.VAR_LONG)).longValue();
/*     */                   byte flags = ((Byte)wrapper.read((Type)Type.BYTE)).byteValue();
/*     */                   wrapper.write((Type)Type.BOOLEAN, Boolean.valueOf(((flags & 0x1) != 0)));
/*     */                   wrapper.write((Type)Type.BOOLEAN, Boolean.valueOf(((flags & 0x2) != 0)));
/*     */                   wrapper.write((Type)Type.BOOLEAN, Boolean.valueOf(((flags & 0x4) != 0)));
/*     */                   wrapper.write((Type)Type.FLOAT, Float.valueOf(integrity));
/*     */                   wrapper.write((Type)Type.VAR_LONG, Long.valueOf(seed));
/*     */                 });
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void onMappingDataLoaded() {
/* 848 */     ConnectionData.init();
/* 849 */     RecipeData.init();
/* 850 */     BlockIdData.init();
/*     */     
/* 852 */     Types1_13.PARTICLE.filler((Protocol)this)
/* 853 */       .reader(3, ParticleType.Readers.BLOCK)
/* 854 */       .reader(20, ParticleType.Readers.DUST)
/* 855 */       .reader(11, ParticleType.Readers.DUST)
/* 856 */       .reader(27, ParticleType.Readers.ITEM);
/*     */     
/* 858 */     if (Via.getConfig().isServersideBlockConnections() && Via.getManager().getProviders().get(BlockConnectionProvider.class) instanceof com.viaversion.viaversion.protocols.protocol1_13to1_12_2.blockconnections.providers.PacketBlockConnectionProvider) {
/* 859 */       BlockConnectionStorage.init();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void init(UserConnection userConnection) {
/* 865 */     userConnection.addEntityTracker(getClass(), (EntityTracker)new EntityTrackerBase(userConnection, (EntityType)Entity1_13Types.EntityType.PLAYER));
/* 866 */     userConnection.put((StorableObject)new TabCompleteTracker());
/* 867 */     if (!userConnection.has(ClientWorld.class))
/* 868 */       userConnection.put((StorableObject)new ClientWorld(userConnection)); 
/* 869 */     userConnection.put((StorableObject)new BlockStorage());
/* 870 */     if (Via.getConfig().isServersideBlockConnections() && 
/* 871 */       Via.getManager().getProviders().get(BlockConnectionProvider.class) instanceof com.viaversion.viaversion.protocols.protocol1_13to1_12_2.blockconnections.providers.PacketBlockConnectionProvider) {
/* 872 */       userConnection.put((StorableObject)new BlockConnectionStorage());
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void register(ViaProviders providers) {
/* 879 */     providers.register(BlockEntityProvider.class, (Provider)new BlockEntityProvider());
/* 880 */     providers.register(PaintingProvider.class, (Provider)new PaintingProvider());
/* 881 */     providers.register(PlayerLookTargetProvider.class, (Provider)new PlayerLookTargetProvider());
/*     */   }
/*     */ 
/*     */   
/*     */   public char getLastColorChar(String input) {
/* 886 */     int length = input.length();
/* 887 */     for (int index = length - 1; index > -1; index--) {
/* 888 */       char section = input.charAt(index);
/* 889 */       if (section == '§' && index < length - 1) {
/* 890 */         char c = input.charAt(index + 1);
/* 891 */         if (ChatColorUtil.isColorCode(c) && !FORMATTING_CODES.contains(Character.valueOf(c))) {
/* 892 */           return c;
/*     */         }
/*     */       } 
/*     */     } 
/* 896 */     return 'r';
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String rewriteTeamMemberName(String name) {
/* 903 */     if (ChatColorUtil.stripColor(name).isEmpty()) {
/* 904 */       StringBuilder newName = new StringBuilder();
/* 905 */       for (int i = 1; i < name.length(); i += 2) {
/* 906 */         char colorChar = name.charAt(i);
/* 907 */         Character rewrite = SCOREBOARD_TEAM_NAME_REWRITE.get(Character.valueOf(colorChar));
/* 908 */         if (rewrite == null) {
/* 909 */           rewrite = Character.valueOf(colorChar);
/*     */         }
/* 911 */         newName.append('§').append(rewrite);
/*     */       } 
/* 913 */       name = newName.toString();
/*     */     } 
/* 915 */     return name;
/*     */   }
/*     */ 
/*     */   
/*     */   public MappingData getMappingData() {
/* 920 */     return MAPPINGS;
/*     */   }
/*     */ 
/*     */   
/*     */   public MetadataRewriter1_13To1_12_2 getEntityRewriter() {
/* 925 */     return this.entityRewriter;
/*     */   }
/*     */ 
/*     */   
/*     */   public InventoryPackets getItemRewriter() {
/* 930 */     return this.itemRewriter;
/*     */   }
/*     */   
/*     */   public ComponentRewriter1_13 getComponentRewriter() {
/* 934 */     return this.componentRewriter;
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\protocols\protocol1_13to1_12_2\Protocol1_13To1_12_2.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */