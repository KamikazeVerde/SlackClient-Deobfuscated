/*     */ package de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.items;
/*     */ import com.viaversion.viaversion.api.minecraft.item.Item;
/*     */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
/*     */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.ListTag;
/*     */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.NumberTag;
/*     */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.ShortTag;
/*     */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.StringTag;
/*     */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.Tag;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ 
/*     */ public class ItemRewriter {
/*     */   public static Item toClient(Item item) {
/*  14 */     if (item == null) return null;
/*     */     
/*  16 */     CompoundTag tag = item.tag();
/*  17 */     if (tag == null) item.setTag(tag = new CompoundTag());
/*     */     
/*  19 */     CompoundTag viaVersionTag = new CompoundTag();
/*  20 */     tag.put("ViaRewind1_7_6_10to1_8", (Tag)viaVersionTag);
/*     */     
/*  22 */     viaVersionTag.put("id", (Tag)new ShortTag((short)item.identifier()));
/*  23 */     viaVersionTag.put("data", (Tag)new ShortTag(item.data()));
/*     */     
/*  25 */     CompoundTag display = (CompoundTag)tag.get("display");
/*  26 */     if (display != null && display.contains("Name")) {
/*  27 */       viaVersionTag.put("displayName", (Tag)new StringTag((String)display.get("Name").getValue()));
/*     */     }
/*     */     
/*  30 */     if (display != null && display.contains("Lore")) {
/*  31 */       viaVersionTag.put("lore", (Tag)new ListTag(((ListTag)display.get("Lore")).getValue()));
/*     */     }
/*     */     
/*  34 */     if (tag.contains("ench") || tag.contains("StoredEnchantments")) {
/*  35 */       ListTag enchTag = tag.contains("ench") ? (ListTag)tag.get("ench") : (ListTag)tag.get("StoredEnchantments");
/*  36 */       List<Tag> lore = new ArrayList<>();
/*  37 */       for (Tag ench : new ArrayList(enchTag.getValue())) {
/*  38 */         short id = ((NumberTag)((CompoundTag)ench).get("id")).asShort();
/*  39 */         short lvl = ((NumberTag)((CompoundTag)ench).get("lvl")).asShort();
/*     */         
/*  41 */         if (id == 8) {
/*  42 */           String s = "§r§7Depth Strider ";
/*     */ 
/*     */ 
/*     */           
/*  46 */           enchTag.remove(ench);
/*  47 */           s = s + (String)Enchantments.ENCHANTMENTS.getOrDefault(Short.valueOf(lvl), "enchantment.level." + lvl);
/*  48 */           lore.add(new StringTag(s));
/*     */         } 
/*  50 */       }  if (!lore.isEmpty()) {
/*  51 */         if (display == null) {
/*  52 */           tag.put("display", (Tag)(display = new CompoundTag()));
/*  53 */           viaVersionTag.put("noDisplay", (Tag)new ByteTag());
/*     */         } 
/*  55 */         ListTag loreTag = (ListTag)display.get("Lore");
/*  56 */         if (loreTag == null) display.put("Lore", (Tag)(loreTag = new ListTag(StringTag.class))); 
/*  57 */         lore.addAll(loreTag.getValue());
/*  58 */         loreTag.setValue(lore);
/*     */       } 
/*     */     } 
/*     */     
/*  62 */     if (item.identifier() == 387 && tag.contains("pages")) {
/*  63 */       ListTag pages = (ListTag)tag.get("pages");
/*  64 */       ListTag oldPages = new ListTag(StringTag.class);
/*  65 */       viaVersionTag.put("pages", (Tag)oldPages);
/*     */       
/*  67 */       for (int i = 0; i < pages.size(); i++) {
/*  68 */         StringTag page = (StringTag)pages.get(i);
/*  69 */         String value = page.getValue();
/*  70 */         oldPages.add((Tag)new StringTag(value));
/*  71 */         value = ChatUtil.jsonToLegacy(value);
/*  72 */         page.setValue(value);
/*     */       } 
/*     */     } 
/*     */     
/*  76 */     ReplacementRegistry1_7_6_10to1_8.replace(item);
/*     */     
/*  78 */     if (viaVersionTag.size() == 2 && ((Short)viaVersionTag.get("id").getValue()).shortValue() == item.identifier() && ((Short)viaVersionTag.get("data").getValue()).shortValue() == item.data()) {
/*  79 */       item.tag().remove("ViaRewind1_7_6_10to1_8");
/*  80 */       if (item.tag().isEmpty()) item.setTag(null);
/*     */     
/*     */     } 
/*  83 */     return item;
/*     */   }
/*     */   
/*     */   public static Item toServer(Item item) {
/*  87 */     if (item == null) return null;
/*     */     
/*  89 */     CompoundTag tag = item.tag();
/*     */     
/*  91 */     if (tag == null || !item.tag().contains("ViaRewind1_7_6_10to1_8")) return item;
/*     */     
/*  93 */     CompoundTag viaVersionTag = (CompoundTag)tag.remove("ViaRewind1_7_6_10to1_8");
/*     */     
/*  95 */     item.setIdentifier(((Short)viaVersionTag.get("id").getValue()).shortValue());
/*  96 */     item.setData(((Short)viaVersionTag.get("data").getValue()).shortValue());
/*     */     
/*  98 */     if (viaVersionTag.contains("noDisplay")) tag.remove("display");
/*     */     
/* 100 */     if (viaVersionTag.contains("displayName")) {
/* 101 */       CompoundTag display = (CompoundTag)tag.get("display");
/* 102 */       if (display == null) tag.put("display", (Tag)(display = new CompoundTag())); 
/* 103 */       StringTag name = (StringTag)display.get("Name");
/* 104 */       if (name == null) { display.put("Name", (Tag)new StringTag((String)viaVersionTag.get("displayName").getValue())); }
/* 105 */       else { name.setValue((String)viaVersionTag.get("displayName").getValue()); } 
/* 106 */     } else if (tag.contains("display")) {
/* 107 */       ((CompoundTag)tag.get("display")).remove("Name");
/*     */     } 
/*     */     
/* 110 */     if (item.identifier() == 387) {
/* 111 */       ListTag oldPages = (ListTag)viaVersionTag.get("pages");
/* 112 */       tag.remove("pages");
/* 113 */       tag.put("pages", (Tag)oldPages);
/*     */     } 
/*     */     
/* 116 */     return item;
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\de\gerrygames\viarewind\protocol\protocol1_7_6_10to1_8\items\ItemRewriter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */