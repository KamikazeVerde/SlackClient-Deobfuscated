/*     */ package com.viaversion.viaversion.rewriter;
/*     */ 
/*     */ import com.viaversion.viaversion.api.Via;
/*     */ import com.viaversion.viaversion.api.protocol.Protocol;
/*     */ import com.viaversion.viaversion.api.protocol.packet.ClientboundPacketType;
/*     */ import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
/*     */ import com.viaversion.viaversion.api.protocol.remapper.PacketHandler;
/*     */ import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
/*     */ import com.viaversion.viaversion.api.type.Type;
/*     */ import com.viaversion.viaversion.libs.gson.JsonElement;
/*     */ import com.viaversion.viaversion.libs.gson.JsonObject;
/*     */ import com.viaversion.viaversion.libs.gson.JsonParser;
/*     */ import com.viaversion.viaversion.libs.gson.JsonPrimitive;
/*     */ import com.viaversion.viaversion.libs.gson.JsonSyntaxException;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ComponentRewriter<C extends ClientboundPacketType>
/*     */ {
/*     */   protected final Protocol<C, ?, ?, ?> protocol;
/*     */   
/*     */   public ComponentRewriter(Protocol<C, ?, ?, ?> protocol) {
/*  39 */     this.protocol = protocol;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ComponentRewriter() {
/*  46 */     this.protocol = null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void registerComponentPacket(C packetType) {
/*  56 */     this.protocol.registerClientbound((ClientboundPacketType)packetType, wrapper -> processText((JsonElement)wrapper.passthrough(Type.COMPONENT)));
/*     */   }
/*     */   
/*     */   @Deprecated
/*     */   public void registerChatMessage(C packetType) {
/*  61 */     registerComponentPacket(packetType);
/*     */   }
/*     */   
/*     */   public void registerBossBar(C packetType) {
/*  65 */     this.protocol.registerClientbound((ClientboundPacketType)packetType, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/*  68 */             map(Type.UUID);
/*  69 */             map((Type)Type.VAR_INT);
/*  70 */             handler(wrapper -> {
/*     */                   int action = ((Integer)wrapper.get((Type)Type.VAR_INT, 0)).intValue();
/*     */                   if (action == 0 || action == 3) {
/*     */                     ComponentRewriter.this.processText((JsonElement)wrapper.passthrough(Type.COMPONENT));
/*     */                   }
/*     */                 });
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void registerCombatEvent(C packetType) {
/*  84 */     this.protocol.registerClientbound((ClientboundPacketType)packetType, wrapper -> {
/*     */           if (((Integer)wrapper.passthrough((Type)Type.VAR_INT)).intValue() == 2) {
/*     */             wrapper.passthrough((Type)Type.VAR_INT);
/*     */             wrapper.passthrough((Type)Type.INT);
/*     */             processText((JsonElement)wrapper.passthrough(Type.COMPONENT));
/*     */           } 
/*     */         });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void registerTitle(C packetType) {
/*  97 */     this.protocol.registerClientbound((ClientboundPacketType)packetType, wrapper -> {
/*     */           int action = ((Integer)wrapper.passthrough((Type)Type.VAR_INT)).intValue();
/*     */           if (action >= 0 && action <= 2) {
/*     */             processText((JsonElement)wrapper.passthrough(Type.COMPONENT));
/*     */           }
/*     */         });
/*     */   }
/*     */   
/*     */   public JsonElement processText(String value) {
/*     */     try {
/* 107 */       JsonElement root = JsonParser.parseString(value);
/* 108 */       processText(root);
/* 109 */       return root;
/* 110 */     } catch (JsonSyntaxException e) {
/* 111 */       if (Via.getManager().isDebug()) {
/* 112 */         Via.getPlatform().getLogger().severe("Error when trying to parse json: " + value);
/* 113 */         throw e;
/*     */       } 
/*     */       
/* 116 */       return (JsonElement)new JsonPrimitive(value);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void processText(JsonElement element) {
/* 121 */     if (element == null || element.isJsonNull())
/* 122 */       return;  if (element.isJsonArray()) {
/* 123 */       processAsArray(element);
/*     */       return;
/*     */     } 
/* 126 */     if (element.isJsonPrimitive()) {
/* 127 */       handleText(element.getAsJsonPrimitive());
/*     */       
/*     */       return;
/*     */     } 
/* 131 */     JsonObject object = element.getAsJsonObject();
/* 132 */     JsonPrimitive text = object.getAsJsonPrimitive("text");
/* 133 */     if (text != null) {
/* 134 */       handleText(text);
/*     */     }
/*     */     
/* 137 */     JsonElement translate = object.get("translate");
/* 138 */     if (translate != null) {
/* 139 */       handleTranslate(object, translate.getAsString());
/*     */       
/* 141 */       JsonElement with = object.get("with");
/* 142 */       if (with != null) {
/* 143 */         processAsArray(with);
/*     */       }
/*     */     } 
/*     */     
/* 147 */     JsonElement extra = object.get("extra");
/* 148 */     if (extra != null) {
/* 149 */       processAsArray(extra);
/*     */     }
/*     */     
/* 152 */     JsonObject hoverEvent = object.getAsJsonObject("hoverEvent");
/* 153 */     if (hoverEvent != null) {
/* 154 */       handleHoverEvent(hoverEvent);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void handleText(JsonPrimitive text) {}
/*     */ 
/*     */ 
/*     */   
/*     */   protected void handleTranslate(JsonObject object, String translate) {}
/*     */ 
/*     */   
/*     */   protected void handleHoverEvent(JsonObject hoverEvent) {
/* 168 */     String action = hoverEvent.getAsJsonPrimitive("action").getAsString();
/* 169 */     if (action.equals("show_text")) {
/* 170 */       JsonElement value = hoverEvent.get("value");
/* 171 */       processText((value != null) ? value : hoverEvent.get("contents"));
/* 172 */     } else if (action.equals("show_entity")) {
/* 173 */       JsonObject contents = hoverEvent.getAsJsonObject("contents");
/* 174 */       if (contents != null) {
/* 175 */         processText(contents.get("name"));
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   private void processAsArray(JsonElement element) {
/* 181 */     for (JsonElement jsonElement : element.getAsJsonArray()) {
/* 182 */       processText(jsonElement);
/*     */     }
/*     */   }
/*     */   
/*     */   public <T extends Protocol<C, ?, ?, ?>> T getProtocol() {
/* 187 */     return (T)this.protocol;
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\rewriter\ComponentRewriter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */