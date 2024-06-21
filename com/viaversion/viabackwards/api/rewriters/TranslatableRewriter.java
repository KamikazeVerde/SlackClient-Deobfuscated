/*     */ package com.viaversion.viabackwards.api.rewriters;
/*     */ 
/*     */ import com.viaversion.viabackwards.ViaBackwards;
/*     */ import com.viaversion.viabackwards.api.BackwardsProtocol;
/*     */ import com.viaversion.viabackwards.api.data.VBMappingDataLoader;
/*     */ import com.viaversion.viaversion.api.protocol.Protocol;
/*     */ import com.viaversion.viaversion.api.protocol.packet.ClientboundPacketType;
/*     */ import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
/*     */ import com.viaversion.viaversion.api.protocol.packet.State;
/*     */ import com.viaversion.viaversion.api.protocol.remapper.PacketHandler;
/*     */ import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
/*     */ import com.viaversion.viaversion.api.type.Type;
/*     */ import com.viaversion.viaversion.libs.gson.JsonElement;
/*     */ import com.viaversion.viaversion.libs.gson.JsonObject;
/*     */ import com.viaversion.viaversion.rewriter.ComponentRewriter;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
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
/*     */ public class TranslatableRewriter<C extends ClientboundPacketType>
/*     */   extends ComponentRewriter<C>
/*     */ {
/*  36 */   private static final Map<String, Map<String, String>> TRANSLATABLES = new HashMap<>();
/*     */   private final Map<String, String> newTranslatables;
/*     */   
/*     */   public static void loadTranslatables() {
/*  40 */     JsonObject jsonObject = VBMappingDataLoader.loadFromDataDir("translation-mappings.json");
/*  41 */     for (Map.Entry<String, JsonElement> entry : (Iterable<Map.Entry<String, JsonElement>>)jsonObject.entrySet()) {
/*  42 */       Map<String, String> versionMappings = new HashMap<>();
/*  43 */       TRANSLATABLES.put(entry.getKey(), versionMappings);
/*  44 */       for (Map.Entry<String, JsonElement> translationEntry : (Iterable<Map.Entry<String, JsonElement>>)((JsonElement)entry.getValue()).getAsJsonObject().entrySet()) {
/*  45 */         versionMappings.put(translationEntry.getKey(), ((JsonElement)translationEntry.getValue()).getAsString());
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public TranslatableRewriter(BackwardsProtocol<C, ?, ?, ?> protocol) {
/*  51 */     this(protocol, protocol.getClass().getSimpleName().split("To")[1].replace("_", "."));
/*     */   }
/*     */   
/*     */   public TranslatableRewriter(BackwardsProtocol<C, ?, ?, ?> protocol, String sectionIdentifier) {
/*  55 */     super((Protocol)protocol);
/*  56 */     Map<String, String> newTranslatables = TRANSLATABLES.get(sectionIdentifier);
/*  57 */     if (newTranslatables == null) {
/*  58 */       ViaBackwards.getPlatform().getLogger().warning("Error loading " + sectionIdentifier + " translatables!");
/*  59 */       this.newTranslatables = new HashMap<>();
/*     */     } else {
/*  61 */       this.newTranslatables = newTranslatables;
/*     */     } 
/*     */   }
/*     */   
/*     */   public void registerPing() {
/*  66 */     this.protocol.registerClientbound(State.LOGIN, 0, 0, wrapper -> processText((JsonElement)wrapper.passthrough(Type.COMPONENT)));
/*     */   }
/*     */   
/*     */   public void registerDisconnect(C packetType) {
/*  70 */     this.protocol.registerClientbound((ClientboundPacketType)packetType, wrapper -> processText((JsonElement)wrapper.passthrough(Type.COMPONENT)));
/*     */   }
/*     */   
/*     */   public void registerLegacyOpenWindow(C packetType) {
/*  74 */     this.protocol.registerClientbound((ClientboundPacketType)packetType, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/*  77 */             map((Type)Type.UNSIGNED_BYTE);
/*  78 */             map(Type.STRING);
/*  79 */             handler(wrapper -> TranslatableRewriter.this.processText((JsonElement)wrapper.passthrough(Type.COMPONENT)));
/*     */           }
/*     */         });
/*     */   }
/*     */   
/*     */   public void registerOpenWindow(C packetType) {
/*  85 */     this.protocol.registerClientbound((ClientboundPacketType)packetType, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/*  88 */             map((Type)Type.VAR_INT);
/*  89 */             map((Type)Type.VAR_INT);
/*  90 */             handler(wrapper -> TranslatableRewriter.this.processText((JsonElement)wrapper.passthrough(Type.COMPONENT)));
/*     */           }
/*     */         });
/*     */   }
/*     */   
/*     */   public void registerTabList(C packetType) {
/*  96 */     this.protocol.registerClientbound((ClientboundPacketType)packetType, wrapper -> {
/*     */           processText((JsonElement)wrapper.passthrough(Type.COMPONENT));
/*     */           processText((JsonElement)wrapper.passthrough(Type.COMPONENT));
/*     */         });
/*     */   }
/*     */   
/*     */   public void registerCombatKill(C packetType) {
/* 103 */     this.protocol.registerClientbound((ClientboundPacketType)packetType, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/* 106 */             map((Type)Type.VAR_INT);
/* 107 */             map((Type)Type.INT);
/* 108 */             handler(wrapper -> TranslatableRewriter.this.processText((JsonElement)wrapper.passthrough(Type.COMPONENT)));
/*     */           }
/*     */         });
/*     */   }
/*     */   
/*     */   public void registerCombatKill1_20(C packetType) {
/* 114 */     this.protocol.registerClientbound((ClientboundPacketType)packetType, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/* 117 */             map((Type)Type.VAR_INT);
/* 118 */             handler(wrapper -> TranslatableRewriter.this.processText((JsonElement)wrapper.passthrough(Type.COMPONENT)));
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */   
/*     */   protected void handleTranslate(JsonObject root, String translate) {
/* 125 */     String newTranslate = mappedTranslationKey(translate);
/* 126 */     if (newTranslate != null) {
/* 127 */       root.addProperty("translate", newTranslate);
/*     */     }
/*     */   }
/*     */   
/*     */   public String mappedTranslationKey(String translationKey) {
/* 132 */     return this.newTranslatables.get(translationKey);
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viabackwards\api\rewriters\TranslatableRewriter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */