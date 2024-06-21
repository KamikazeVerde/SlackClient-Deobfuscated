/*     */ package com.viaversion.viabackwards.api.rewriters;
/*     */ 
/*     */ import com.viaversion.viaversion.api.minecraft.item.Item;
/*     */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
/*     */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.ListTag;
/*     */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.NumberTag;
/*     */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.ShortTag;
/*     */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.StringTag;
/*     */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.Tag;
/*     */ import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.ChatRewriter;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class EnchantmentRewriter
/*     */ {
/*  39 */   private final Map<String, String> enchantmentMappings = new HashMap<>();
/*     */   private final ItemRewriter<?, ?, ?> itemRewriter;
/*     */   private final boolean jsonFormat;
/*     */   
/*     */   public EnchantmentRewriter(ItemRewriter<?, ?, ?> itemRewriter, boolean jsonFormat) {
/*  44 */     this.itemRewriter = itemRewriter;
/*  45 */     this.jsonFormat = jsonFormat;
/*     */   }
/*     */   
/*     */   public EnchantmentRewriter(ItemRewriter<?, ?, ?> itemRewriter) {
/*  49 */     this(itemRewriter, true);
/*     */   }
/*     */   
/*     */   public void registerEnchantment(String key, String replacementLore) {
/*  53 */     this.enchantmentMappings.put(key, replacementLore);
/*     */   }
/*     */   
/*     */   public void handleToClient(Item item) {
/*  57 */     CompoundTag tag = item.tag();
/*  58 */     if (tag == null)
/*     */       return; 
/*  60 */     if (tag.get("Enchantments") instanceof ListTag) {
/*  61 */       rewriteEnchantmentsToClient(tag, false);
/*     */     }
/*  63 */     if (tag.get("StoredEnchantments") instanceof ListTag) {
/*  64 */       rewriteEnchantmentsToClient(tag, true);
/*     */     }
/*     */   }
/*     */   
/*     */   public void handleToServer(Item item) {
/*  69 */     CompoundTag tag = item.tag();
/*  70 */     if (tag == null)
/*     */       return; 
/*  72 */     if (tag.contains(this.itemRewriter.getNbtTagName() + "|Enchantments")) {
/*  73 */       rewriteEnchantmentsToServer(tag, false);
/*     */     }
/*  75 */     if (tag.contains(this.itemRewriter.getNbtTagName() + "|StoredEnchantments")) {
/*  76 */       rewriteEnchantmentsToServer(tag, true);
/*     */     }
/*     */   }
/*     */   
/*     */   public void rewriteEnchantmentsToClient(CompoundTag tag, boolean storedEnchant) {
/*  81 */     String key = storedEnchant ? "StoredEnchantments" : "Enchantments";
/*  82 */     ListTag enchantments = (ListTag)tag.get(key);
/*  83 */     List<Tag> loreToAdd = new ArrayList<>();
/*  84 */     boolean changed = false;
/*     */     
/*  86 */     Iterator<Tag> iterator = enchantments.iterator();
/*  87 */     while (iterator.hasNext()) {
/*  88 */       CompoundTag enchantmentEntry = (CompoundTag)iterator.next();
/*  89 */       Tag idTag = enchantmentEntry.get("id");
/*  90 */       if (!(idTag instanceof StringTag))
/*     */         continue; 
/*  92 */       String enchantmentId = ((StringTag)idTag).getValue();
/*  93 */       String remappedName = this.enchantmentMappings.get(enchantmentId);
/*  94 */       if (remappedName != null) {
/*  95 */         if (!changed) {
/*     */           
/*  97 */           this.itemRewriter.saveListTag(tag, enchantments, key);
/*  98 */           changed = true;
/*     */         } 
/*     */         
/* 101 */         iterator.remove();
/*     */         
/* 103 */         int level = ((NumberTag)enchantmentEntry.get("lvl")).asInt();
/* 104 */         String loreValue = remappedName + " " + getRomanNumber(level);
/* 105 */         if (this.jsonFormat) {
/* 106 */           loreValue = ChatRewriter.legacyTextToJsonString(loreValue);
/*     */         }
/*     */         
/* 109 */         loreToAdd.add(new StringTag(loreValue));
/*     */       } 
/*     */     } 
/*     */     
/* 113 */     if (!loreToAdd.isEmpty()) {
/*     */       
/* 115 */       if (!storedEnchant && enchantments.size() == 0) {
/* 116 */         CompoundTag dummyEnchantment = new CompoundTag();
/* 117 */         dummyEnchantment.put("id", (Tag)new StringTag());
/* 118 */         dummyEnchantment.put("lvl", (Tag)new ShortTag((short)0));
/* 119 */         enchantments.add((Tag)dummyEnchantment);
/*     */       } 
/*     */       
/* 122 */       CompoundTag display = (CompoundTag)tag.get("display");
/* 123 */       if (display == null) {
/* 124 */         tag.put("display", (Tag)(display = new CompoundTag()));
/*     */       }
/*     */       
/* 127 */       ListTag loreTag = (ListTag)display.get("Lore");
/* 128 */       if (loreTag == null) {
/* 129 */         display.put("Lore", (Tag)(loreTag = new ListTag(StringTag.class)));
/*     */       } else {
/*     */         
/* 132 */         this.itemRewriter.saveListTag(display, loreTag, "Lore");
/*     */       } 
/*     */       
/* 135 */       loreToAdd.addAll(loreTag.getValue());
/* 136 */       loreTag.setValue(loreToAdd);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void rewriteEnchantmentsToServer(CompoundTag tag, boolean storedEnchant) {
/* 142 */     String key = storedEnchant ? "StoredEnchantments" : "Enchantments";
/* 143 */     this.itemRewriter.restoreListTag(tag, key);
/*     */   }
/*     */   
/*     */   public static String getRomanNumber(int number) {
/* 147 */     switch (number) {
/*     */       case 1:
/* 149 */         return "I";
/*     */       case 2:
/* 151 */         return "II";
/*     */       case 3:
/* 153 */         return "III";
/*     */       case 4:
/* 155 */         return "IV";
/*     */       case 5:
/* 157 */         return "V";
/*     */       case 6:
/* 159 */         return "VI";
/*     */       case 7:
/* 161 */         return "VII";
/*     */       case 8:
/* 163 */         return "VIII";
/*     */       case 9:
/* 165 */         return "IX";
/*     */       case 10:
/* 167 */         return "X";
/*     */     } 
/* 169 */     return Integer.toString(number);
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viabackwards\api\rewriters\EnchantmentRewriter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */