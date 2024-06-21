/*     */ package com.viaversion.viabackwards.api.rewriters;
/*     */ 
/*     */ import com.viaversion.viabackwards.api.BackwardsProtocol;
/*     */ import com.viaversion.viaversion.api.minecraft.item.Item;
/*     */ import com.viaversion.viaversion.api.protocol.Protocol;
/*     */ import com.viaversion.viaversion.api.protocol.packet.ClientboundPacketType;
/*     */ import com.viaversion.viaversion.api.protocol.packet.ServerboundPacketType;
/*     */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
/*     */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.ListTag;
/*     */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.StringTag;
/*     */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.Tag;
/*     */ import com.viaversion.viaversion.rewriter.ItemRewriter;
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
/*     */ public abstract class ItemRewriterBase<C extends ClientboundPacketType, S extends ServerboundPacketType, T extends BackwardsProtocol<C, ?, ?, S>>
/*     */   extends ItemRewriter<C, S, T>
/*     */ {
/*     */   protected final String nbtTagName;
/*     */   protected final boolean jsonNameFormat;
/*     */   
/*     */   protected ItemRewriterBase(T protocol, boolean jsonNameFormat) {
/*  38 */     super((Protocol)protocol);
/*  39 */     this.jsonNameFormat = jsonNameFormat;
/*  40 */     this.nbtTagName = "VB|" + protocol.getClass().getSimpleName();
/*     */   }
/*     */ 
/*     */   
/*     */   public Item handleItemToServer(Item item) {
/*  45 */     if (item == null) return null; 
/*  46 */     super.handleItemToServer(item);
/*     */     
/*  48 */     restoreDisplayTag(item);
/*  49 */     return item;
/*     */   }
/*     */   
/*     */   protected boolean hasBackupTag(CompoundTag displayTag, String tagName) {
/*  53 */     return displayTag.contains(this.nbtTagName + "|o" + tagName);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void saveStringTag(CompoundTag displayTag, StringTag original, String name) {
/*  58 */     String backupName = this.nbtTagName + "|o" + name;
/*  59 */     if (!displayTag.contains(backupName)) {
/*  60 */       displayTag.put(backupName, (Tag)new StringTag(original.getValue()));
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   protected void saveListTag(CompoundTag displayTag, ListTag original, String name) {
/*  66 */     String backupName = this.nbtTagName + "|o" + name;
/*  67 */     if (!displayTag.contains(backupName)) {
/*     */       
/*  69 */       ListTag listTag = new ListTag();
/*  70 */       for (Tag tag : original.getValue()) {
/*  71 */         listTag.add(tag.clone());
/*     */       }
/*     */       
/*  74 */       displayTag.put(backupName, (Tag)listTag);
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void restoreDisplayTag(Item item) {
/*  79 */     if (item.tag() == null)
/*     */       return; 
/*  81 */     CompoundTag display = (CompoundTag)item.tag().get("display");
/*  82 */     if (display != null) {
/*     */       
/*  84 */       if (display.remove(this.nbtTagName + "|customName") != null) {
/*  85 */         display.remove("Name");
/*     */       } else {
/*  87 */         restoreStringTag(display, "Name");
/*     */       } 
/*     */ 
/*     */       
/*  91 */       restoreListTag(display, "Lore");
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void restoreStringTag(CompoundTag tag, String tagName) {
/*  96 */     StringTag original = (StringTag)tag.remove(this.nbtTagName + "|o" + tagName);
/*  97 */     if (original != null) {
/*  98 */       tag.put(tagName, (Tag)new StringTag(original.getValue()));
/*     */     }
/*     */   }
/*     */   
/*     */   protected void restoreListTag(CompoundTag tag, String tagName) {
/* 103 */     ListTag original = (ListTag)tag.remove(this.nbtTagName + "|o" + tagName);
/* 104 */     if (original != null) {
/* 105 */       tag.put(tagName, (Tag)new ListTag(original.getValue()));
/*     */     }
/*     */   }
/*     */   
/*     */   public String getNbtTagName() {
/* 110 */     return this.nbtTagName;
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viabackwards\api\rewriters\ItemRewriterBase.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */