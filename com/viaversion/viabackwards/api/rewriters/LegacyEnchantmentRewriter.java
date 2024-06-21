/*     */ package com.viaversion.viabackwards.api.rewriters;
/*     */ 
/*     */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.ByteTag;
/*     */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
/*     */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.IntTag;
/*     */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.ListTag;
/*     */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.NumberTag;
/*     */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.ShortTag;
/*     */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.StringTag;
/*     */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.Tag;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
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
/*     */ public class LegacyEnchantmentRewriter
/*     */ {
/*  37 */   private final Map<Short, String> enchantmentMappings = new HashMap<>();
/*     */   private final String nbtTagName;
/*     */   private Set<Short> hideLevelForEnchants;
/*     */   
/*     */   public LegacyEnchantmentRewriter(String nbtTagName) {
/*  42 */     this.nbtTagName = nbtTagName;
/*     */   }
/*     */   
/*     */   public void registerEnchantment(int id, String replacementLore) {
/*  46 */     this.enchantmentMappings.put(Short.valueOf((short)id), replacementLore);
/*     */   }
/*     */   
/*     */   public void rewriteEnchantmentsToClient(CompoundTag tag, boolean storedEnchant) {
/*  50 */     String key = storedEnchant ? "StoredEnchantments" : "ench";
/*  51 */     ListTag enchantments = (ListTag)tag.get(key);
/*  52 */     ListTag remappedEnchantments = new ListTag(CompoundTag.class);
/*  53 */     List<Tag> lore = new ArrayList<>();
/*  54 */     for (Tag enchantmentEntry : enchantments.clone()) {
/*  55 */       Tag idTag = ((CompoundTag)enchantmentEntry).get("id");
/*  56 */       if (idTag == null)
/*     */         continue; 
/*  58 */       short newId = ((NumberTag)idTag).asShort();
/*  59 */       String enchantmentName = this.enchantmentMappings.get(Short.valueOf(newId));
/*  60 */       if (enchantmentName != null) {
/*  61 */         enchantments.remove(enchantmentEntry);
/*  62 */         short level = ((NumberTag)((CompoundTag)enchantmentEntry).get("lvl")).asShort();
/*  63 */         if (this.hideLevelForEnchants != null && this.hideLevelForEnchants.contains(Short.valueOf(newId))) {
/*  64 */           lore.add(new StringTag(enchantmentName));
/*     */         } else {
/*  66 */           lore.add(new StringTag(enchantmentName + " " + EnchantmentRewriter.getRomanNumber(level)));
/*     */         } 
/*  68 */         remappedEnchantments.add(enchantmentEntry);
/*     */       } 
/*     */     } 
/*  71 */     if (!lore.isEmpty()) {
/*  72 */       if (!storedEnchant && enchantments.size() == 0) {
/*  73 */         CompoundTag dummyEnchantment = new CompoundTag();
/*  74 */         dummyEnchantment.put("id", (Tag)new ShortTag((short)0));
/*  75 */         dummyEnchantment.put("lvl", (Tag)new ShortTag((short)0));
/*  76 */         enchantments.add((Tag)dummyEnchantment);
/*     */         
/*  78 */         tag.put(this.nbtTagName + "|dummyEnchant", (Tag)new ByteTag());
/*     */         
/*  80 */         IntTag hideFlags = (IntTag)tag.get("HideFlags");
/*  81 */         if (hideFlags == null) {
/*  82 */           hideFlags = new IntTag();
/*     */         } else {
/*  84 */           tag.put(this.nbtTagName + "|oldHideFlags", (Tag)new IntTag(hideFlags.asByte()));
/*     */         } 
/*     */         
/*  87 */         int flags = hideFlags.asByte() | 0x1;
/*  88 */         hideFlags.setValue(flags);
/*  89 */         tag.put("HideFlags", (Tag)hideFlags);
/*     */       } 
/*     */       
/*  92 */       tag.put(this.nbtTagName + "|" + key, (Tag)remappedEnchantments);
/*     */       
/*  94 */       CompoundTag display = (CompoundTag)tag.get("display");
/*  95 */       if (display == null) {
/*  96 */         tag.put("display", (Tag)(display = new CompoundTag()));
/*     */       }
/*  98 */       ListTag loreTag = (ListTag)display.get("Lore");
/*  99 */       if (loreTag == null) {
/* 100 */         display.put("Lore", (Tag)(loreTag = new ListTag(StringTag.class)));
/*     */       }
/*     */       
/* 103 */       lore.addAll(loreTag.getValue());
/* 104 */       loreTag.setValue(lore);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void rewriteEnchantmentsToServer(CompoundTag tag, boolean storedEnchant) {
/* 109 */     String key = storedEnchant ? "StoredEnchantments" : "ench";
/* 110 */     ListTag remappedEnchantments = (ListTag)tag.remove(this.nbtTagName + "|" + key);
/* 111 */     ListTag enchantments = (ListTag)tag.get(key);
/* 112 */     if (enchantments == null) {
/* 113 */       enchantments = new ListTag(CompoundTag.class);
/*     */     }
/*     */     
/* 116 */     if (!storedEnchant && tag.remove(this.nbtTagName + "|dummyEnchant") != null) {
/* 117 */       for (Tag enchantment : enchantments.clone()) {
/* 118 */         short id = ((NumberTag)((CompoundTag)enchantment).get("id")).asShort();
/* 119 */         short level = ((NumberTag)((CompoundTag)enchantment).get("lvl")).asShort();
/* 120 */         if (id == 0 && level == 0) {
/* 121 */           enchantments.remove(enchantment);
/*     */         }
/*     */       } 
/*     */       
/* 125 */       IntTag hideFlags = (IntTag)tag.remove(this.nbtTagName + "|oldHideFlags");
/* 126 */       if (hideFlags != null) {
/* 127 */         tag.put("HideFlags", (Tag)new IntTag(hideFlags.asByte()));
/*     */       } else {
/* 129 */         tag.remove("HideFlags");
/*     */       } 
/*     */     } 
/*     */     
/* 133 */     CompoundTag display = (CompoundTag)tag.get("display");
/*     */     
/* 135 */     ListTag lore = (display != null) ? (ListTag)display.get("Lore") : null;
/* 136 */     for (Tag enchantment : remappedEnchantments.clone()) {
/* 137 */       enchantments.add(enchantment);
/* 138 */       if (lore != null && lore.size() != 0) {
/* 139 */         lore.remove(lore.get(0));
/*     */       }
/*     */     } 
/* 142 */     if (lore != null && lore.size() == 0) {
/* 143 */       display.remove("Lore");
/* 144 */       if (display.isEmpty()) {
/* 145 */         tag.remove("display");
/*     */       }
/*     */     } 
/*     */     
/* 149 */     tag.put(key, (Tag)enchantments);
/*     */   }
/*     */   
/*     */   public void setHideLevelForEnchants(int... enchants) {
/* 153 */     this.hideLevelForEnchants = new HashSet<>();
/* 154 */     for (int enchant : enchants)
/* 155 */       this.hideLevelForEnchants.add(Short.valueOf((short)enchant)); 
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viabackwards\api\rewriters\LegacyEnchantmentRewriter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */