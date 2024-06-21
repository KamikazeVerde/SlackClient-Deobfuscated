/*     */ package com.viaversion.viabackwards.api.rewriters;
/*     */ 
/*     */ import com.viaversion.viabackwards.api.BackwardsProtocol;
/*     */ import com.viaversion.viabackwards.api.data.MappedItem;
/*     */ import com.viaversion.viaversion.api.minecraft.item.Item;
/*     */ import com.viaversion.viaversion.api.protocol.Protocol;
/*     */ import com.viaversion.viaversion.api.protocol.packet.ClientboundPacketType;
/*     */ import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
/*     */ import com.viaversion.viaversion.api.protocol.packet.ServerboundPacketType;
/*     */ import com.viaversion.viaversion.api.protocol.remapper.PacketHandler;
/*     */ import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
/*     */ import com.viaversion.viaversion.api.type.Type;
/*     */ import com.viaversion.viaversion.libs.gson.JsonElement;
/*     */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.ByteTag;
/*     */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
/*     */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.IntTag;
/*     */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.ListTag;
/*     */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.StringTag;
/*     */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.Tag;
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
/*     */ public class ItemRewriter<C extends ClientboundPacketType, S extends ServerboundPacketType, T extends BackwardsProtocol<C, ?, ?, S>>
/*     */   extends ItemRewriterBase<C, S, T>
/*     */ {
/*     */   public ItemRewriter(T protocol) {
/*  40 */     super(protocol, true);
/*     */   }
/*     */ 
/*     */   
/*     */   public Item handleItemToClient(Item item) {
/*  45 */     if (item == null) {
/*  46 */       return null;
/*     */     }
/*     */     
/*  49 */     CompoundTag display = (item.tag() != null) ? (CompoundTag)item.tag().get("display") : null;
/*  50 */     if (((BackwardsProtocol)this.protocol).getTranslatableRewriter() != null && display != null) {
/*     */       
/*  52 */       Tag name = display.get("Name");
/*  53 */       if (name instanceof StringTag) {
/*  54 */         StringTag nameStringTag = (StringTag)name;
/*  55 */         String newValue = ((BackwardsProtocol)this.protocol).getTranslatableRewriter().processText(nameStringTag.getValue()).toString();
/*  56 */         if (!newValue.equals(name.getValue())) {
/*  57 */           saveStringTag(display, nameStringTag, "Name");
/*     */         }
/*     */         
/*  60 */         nameStringTag.setValue(newValue);
/*     */       } 
/*     */       
/*  63 */       Tag lore = display.get("Lore");
/*  64 */       if (lore instanceof ListTag) {
/*  65 */         ListTag loreListTag = (ListTag)lore;
/*  66 */         boolean changed = false;
/*  67 */         for (Tag loreEntryTag : loreListTag) {
/*  68 */           if (!(loreEntryTag instanceof StringTag)) {
/*     */             continue;
/*     */           }
/*     */           
/*  72 */           StringTag loreEntry = (StringTag)loreEntryTag;
/*  73 */           String newValue = ((BackwardsProtocol)this.protocol).getTranslatableRewriter().processText(loreEntry.getValue()).toString();
/*  74 */           if (!changed && !newValue.equals(loreEntry.getValue())) {
/*     */             
/*  76 */             changed = true;
/*  77 */             saveListTag(display, loreListTag, "Lore");
/*     */           } 
/*     */           
/*  80 */           loreEntry.setValue(newValue);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/*  85 */     MappedItem data = ((BackwardsProtocol)this.protocol).getMappingData().getMappedItem(item.identifier());
/*  86 */     if (data == null)
/*     */     {
/*  88 */       return super.handleItemToClient(item);
/*     */     }
/*     */     
/*  91 */     if (item.tag() == null) {
/*  92 */       item.setTag(new CompoundTag());
/*     */     }
/*     */ 
/*     */     
/*  96 */     item.tag().put(this.nbtTagName + "|id", (Tag)new IntTag(item.identifier()));
/*  97 */     item.setIdentifier(data.getId());
/*     */ 
/*     */     
/* 100 */     if (data.customModelData() != null && !item.tag().contains("CustomModelData")) {
/* 101 */       item.tag().put("CustomModelData", (Tag)new IntTag(data.customModelData().intValue()));
/*     */     }
/*     */ 
/*     */     
/* 105 */     if (display == null) {
/* 106 */       item.tag().put("display", (Tag)(display = new CompoundTag()));
/*     */     }
/* 108 */     if (!display.contains("Name")) {
/* 109 */       display.put("Name", (Tag)new StringTag(data.getJsonName()));
/* 110 */       display.put(this.nbtTagName + "|customName", (Tag)new ByteTag());
/*     */     } 
/* 112 */     return item;
/*     */   }
/*     */ 
/*     */   
/*     */   public Item handleItemToServer(Item item) {
/* 117 */     if (item == null) return null;
/*     */     
/* 119 */     super.handleItemToServer(item);
/* 120 */     if (item.tag() != null) {
/* 121 */       IntTag originalId = (IntTag)item.tag().remove(this.nbtTagName + "|id");
/* 122 */       if (originalId != null) {
/* 123 */         item.setIdentifier(originalId.asInt());
/*     */       }
/*     */     } 
/* 126 */     return item;
/*     */   }
/*     */ 
/*     */   
/*     */   public void registerAdvancements(C packetType, final Type<Item> type) {
/* 131 */     ((BackwardsProtocol)this.protocol).registerClientbound((ClientboundPacketType)packetType, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/* 134 */             handler(wrapper -> {
/*     */                   wrapper.passthrough((Type)Type.BOOLEAN);
/*     */                   int size = ((Integer)wrapper.passthrough((Type)Type.VAR_INT)).intValue();
/*     */                   for (int i = 0; i < size; i++) {
/*     */                     wrapper.passthrough(Type.STRING);
/*     */                     if (((Boolean)wrapper.passthrough((Type)Type.BOOLEAN)).booleanValue())
/*     */                       wrapper.passthrough(Type.STRING); 
/*     */                     if (((Boolean)wrapper.passthrough((Type)Type.BOOLEAN)).booleanValue()) {
/*     */                       JsonElement title = (JsonElement)wrapper.passthrough(Type.COMPONENT);
/*     */                       JsonElement description = (JsonElement)wrapper.passthrough(Type.COMPONENT);
/*     */                       TranslatableRewriter<C> translatableRewriter = ((BackwardsProtocol)ItemRewriter.this.protocol).getTranslatableRewriter();
/*     */                       if (translatableRewriter != null) {
/*     */                         translatableRewriter.processText(title);
/*     */                         translatableRewriter.processText(description);
/*     */                       } 
/*     */                       ItemRewriter.this.handleItemToClient((Item)wrapper.passthrough(type));
/*     */                       wrapper.passthrough((Type)Type.VAR_INT);
/*     */                       int flags = ((Integer)wrapper.passthrough((Type)Type.INT)).intValue();
/*     */                       if ((flags & 0x1) != 0)
/*     */                         wrapper.passthrough(Type.STRING); 
/*     */                       wrapper.passthrough((Type)Type.FLOAT);
/*     */                       wrapper.passthrough((Type)Type.FLOAT);
/*     */                     } 
/*     */                     wrapper.passthrough(Type.STRING_ARRAY);
/*     */                     int arrayLength = ((Integer)wrapper.passthrough((Type)Type.VAR_INT)).intValue();
/*     */                     for (int array = 0; array < arrayLength; array++)
/*     */                       wrapper.passthrough(Type.STRING_ARRAY); 
/*     */                   } 
/*     */                 });
/*     */           }
/*     */         });
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viabackwards\api\rewriters\ItemRewriter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */