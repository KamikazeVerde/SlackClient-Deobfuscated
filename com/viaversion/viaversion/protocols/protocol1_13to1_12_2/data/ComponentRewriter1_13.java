/*     */ package com.viaversion.viaversion.protocols.protocol1_13to1_12_2.data;
/*     */ 
/*     */ import com.viaversion.viaversion.api.Via;
/*     */ import com.viaversion.viaversion.api.minecraft.item.DataItem;
/*     */ import com.viaversion.viaversion.api.minecraft.item.Item;
/*     */ import com.viaversion.viaversion.api.minecraft.nbt.BinaryTagIO;
/*     */ import com.viaversion.viaversion.api.protocol.Protocol;
/*     */ import com.viaversion.viaversion.api.protocol.packet.ClientboundPacketType;
/*     */ import com.viaversion.viaversion.libs.gson.JsonArray;
/*     */ import com.viaversion.viaversion.libs.gson.JsonElement;
/*     */ import com.viaversion.viaversion.libs.gson.JsonObject;
/*     */ import com.viaversion.viaversion.libs.gson.JsonPrimitive;
/*     */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
/*     */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.NumberTag;
/*     */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.ShortTag;
/*     */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.Tag;
/*     */ import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.Protocol1_13To1_12_2;
/*     */ import com.viaversion.viaversion.rewriter.ComponentRewriter;
/*     */ import java.io.IOException;
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
/*     */ public class ComponentRewriter1_13<C extends ClientboundPacketType>
/*     */   extends ComponentRewriter<C>
/*     */ {
/*     */   public ComponentRewriter1_13(Protocol<C, ?, ?, ?> protocol) {
/*  40 */     super(protocol);
/*     */   }
/*     */   
/*     */   protected void handleHoverEvent(JsonObject hoverEvent) {
/*     */     CompoundTag tag;
/*  45 */     super.handleHoverEvent(hoverEvent);
/*  46 */     String action = hoverEvent.getAsJsonPrimitive("action").getAsString();
/*  47 */     if (!action.equals("show_item"))
/*     */       return; 
/*  49 */     JsonElement value = hoverEvent.get("value");
/*  50 */     if (value == null)
/*     */       return; 
/*  52 */     String text = findItemNBT(value);
/*  53 */     if (text == null) {
/*     */       return;
/*     */     }
/*     */     try {
/*  57 */       tag = BinaryTagIO.readString(text);
/*  58 */     } catch (Exception e) {
/*  59 */       if (!Via.getConfig().isSuppressConversionWarnings() || Via.getManager().isDebug()) {
/*  60 */         Via.getPlatform().getLogger().warning("Error reading NBT in show_item:" + text);
/*  61 */         e.printStackTrace();
/*     */       } 
/*     */       
/*     */       return;
/*     */     } 
/*  66 */     CompoundTag itemTag = (CompoundTag)tag.get("tag");
/*  67 */     NumberTag damageTag = (NumberTag)tag.get("Damage");
/*     */ 
/*     */     
/*  70 */     short damage = (damageTag != null) ? damageTag.asShort() : 0;
/*  71 */     DataItem dataItem = new DataItem();
/*  72 */     dataItem.setData(damage);
/*  73 */     dataItem.setTag(itemTag);
/*  74 */     this.protocol.getItemRewriter().handleItemToClient((Item)dataItem);
/*     */ 
/*     */     
/*  77 */     if (damage != dataItem.data()) {
/*  78 */       tag.put("Damage", (Tag)new ShortTag(dataItem.data()));
/*     */     }
/*  80 */     if (itemTag != null) {
/*  81 */       tag.put("tag", (Tag)itemTag);
/*     */     }
/*     */     
/*  84 */     JsonArray array = new JsonArray();
/*  85 */     JsonObject object = new JsonObject();
/*  86 */     array.add((JsonElement)object);
/*     */     
/*     */     try {
/*  89 */       String serializedNBT = BinaryTagIO.writeString(tag);
/*  90 */       object.addProperty("text", serializedNBT);
/*  91 */       hoverEvent.add("value", (JsonElement)array);
/*  92 */     } catch (IOException e) {
/*  93 */       Via.getPlatform().getLogger().warning("Error writing NBT in show_item:" + text);
/*  94 */       e.printStackTrace();
/*     */     } 
/*     */   }
/*     */   
/*     */   protected String findItemNBT(JsonElement element) {
/*  99 */     if (element.isJsonArray()) {
/* 100 */       for (JsonElement jsonElement : element.getAsJsonArray()) {
/* 101 */         String value = findItemNBT(jsonElement);
/* 102 */         if (value != null) {
/* 103 */           return value;
/*     */         }
/*     */       } 
/* 106 */     } else if (element.isJsonObject()) {
/* 107 */       JsonPrimitive text = element.getAsJsonObject().getAsJsonPrimitive("text");
/* 108 */       if (text != null) {
/* 109 */         return text.getAsString();
/*     */       }
/* 111 */     } else if (element.isJsonPrimitive()) {
/* 112 */       return element.getAsJsonPrimitive().getAsString();
/*     */     } 
/* 114 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void handleTranslate(JsonObject object, String translate) {
/* 119 */     super.handleTranslate(object, translate);
/*     */     
/* 121 */     String newTranslate = Protocol1_13To1_12_2.MAPPINGS.getTranslateMapping().get(translate);
/* 122 */     if (newTranslate == null) {
/* 123 */       newTranslate = Protocol1_13To1_12_2.MAPPINGS.getMojangTranslation().get(translate);
/*     */     }
/* 125 */     if (newTranslate != null)
/* 126 */       object.addProperty("translate", newTranslate); 
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\protocols\protocol1_13to1_12_2\data\ComponentRewriter1_13.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */