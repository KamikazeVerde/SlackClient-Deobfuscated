/*     */ package de.gerrygames.viarewind.protocol.protocol1_8to1_9.items;
/*     */ 
/*     */ import com.viaversion.viaversion.api.minecraft.item.Item;
/*     */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.ByteTag;
/*     */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
/*     */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.ListTag;
/*     */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.NumberTag;
/*     */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.ShortTag;
/*     */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.StringTag;
/*     */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.Tag;
/*     */ import de.gerrygames.viarewind.protocol.protocol1_8to1_9.Protocol1_8TO1_9;
/*     */ import de.gerrygames.viarewind.utils.Enchantments;
/*     */ import java.lang.reflect.Field;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ 
/*     */ public class ItemRewriter {
/*     */   private static Map<String, Integer> ENTTIY_NAME_TO_ID;
/*     */   private static Map<Integer, String> ENTTIY_ID_TO_NAME;
/*  22 */   private static final Map<String, String> POTION_NAME_INDEX = new HashMap<>(); private static Map<String, Integer> POTION_NAME_TO_ID; private static Map<Integer, String> POTION_ID_TO_NAME;
/*     */   
/*     */   static {
/*  25 */     for (Field field : ItemRewriter.class.getDeclaredFields()) {
/*     */       try {
/*  27 */         Field other = com.viaversion.viaversion.protocols.protocol1_9to1_8.ItemRewriter.class.getDeclaredField(field.getName());
/*  28 */         other.setAccessible(true);
/*  29 */         field.setAccessible(true);
/*  30 */         field.set(null, other.get(null));
/*  31 */       } catch (Exception exception) {}
/*     */     } 
/*     */     
/*  34 */     POTION_NAME_TO_ID.put("luck", Integer.valueOf(8203));
/*     */     
/*  36 */     POTION_NAME_INDEX.put("water", "§rWater Bottle");
/*  37 */     POTION_NAME_INDEX.put("mundane", "§rMundane Potion");
/*  38 */     POTION_NAME_INDEX.put("thick", "§rThick Potion");
/*  39 */     POTION_NAME_INDEX.put("awkward", "§rAwkward Potion");
/*  40 */     POTION_NAME_INDEX.put("water_splash", "§rSplash Water Bottle");
/*  41 */     POTION_NAME_INDEX.put("mundane_splash", "§rMundane Splash Potion");
/*  42 */     POTION_NAME_INDEX.put("thick_splash", "§rThick Splash Potion");
/*  43 */     POTION_NAME_INDEX.put("awkward_splash", "§rAwkward Splash Potion");
/*  44 */     POTION_NAME_INDEX.put("water_lingering", "§rLingering Water Bottle");
/*  45 */     POTION_NAME_INDEX.put("mundane_lingering", "§rMundane Lingering Potion");
/*  46 */     POTION_NAME_INDEX.put("thick_lingering", "§rThick Lingering Potion");
/*  47 */     POTION_NAME_INDEX.put("awkward_lingering", "§rAwkward Lingering Potion");
/*  48 */     POTION_NAME_INDEX.put("night_vision_lingering", "§rLingering Potion of Night Vision");
/*  49 */     POTION_NAME_INDEX.put("long_night_vision_lingering", "§rLingering Potion of Night Vision");
/*  50 */     POTION_NAME_INDEX.put("invisibility_lingering", "§rLingering Potion of Invisibility");
/*  51 */     POTION_NAME_INDEX.put("long_invisibility_lingering", "§rLingering Potion of Invisibility");
/*  52 */     POTION_NAME_INDEX.put("leaping_lingering", "§rLingering Potion of Leaping");
/*  53 */     POTION_NAME_INDEX.put("long_leaping_lingering", "§rLingering Potion of Leaping");
/*  54 */     POTION_NAME_INDEX.put("strong_leaping_lingering", "§rLingering Potion of Leaping");
/*  55 */     POTION_NAME_INDEX.put("fire_resistance_lingering", "§rLingering Potion of Fire Resistance");
/*  56 */     POTION_NAME_INDEX.put("long_fire_resistance_lingering", "§rLingering Potion of Fire Resistance");
/*  57 */     POTION_NAME_INDEX.put("swiftness_lingering", "§rLingering Potion of Swiftness");
/*  58 */     POTION_NAME_INDEX.put("long_swiftness_lingering", "§rLingering Potion of Swiftness");
/*  59 */     POTION_NAME_INDEX.put("strong_swiftness_lingering", "§rLingering Potion of Swiftness");
/*  60 */     POTION_NAME_INDEX.put("slowness_lingering", "§rLingering Potion of Slowness");
/*  61 */     POTION_NAME_INDEX.put("long_slowness_lingering", "§rLingering Potion of Slowness");
/*  62 */     POTION_NAME_INDEX.put("water_breathing_lingering", "§rLingering Potion of Water Breathing");
/*  63 */     POTION_NAME_INDEX.put("long_water_breathing_lingering", "§rLingering Potion of Water Breathing");
/*  64 */     POTION_NAME_INDEX.put("healing_lingering", "§rLingering Potion of Healing");
/*  65 */     POTION_NAME_INDEX.put("strong_healing_lingering", "§rLingering Potion of Healing");
/*  66 */     POTION_NAME_INDEX.put("harming_lingering", "§rLingering Potion of Harming");
/*  67 */     POTION_NAME_INDEX.put("strong_harming_lingering", "§rLingering Potion of Harming");
/*  68 */     POTION_NAME_INDEX.put("poison_lingering", "§rLingering Potion of Poisen");
/*  69 */     POTION_NAME_INDEX.put("long_poison_lingering", "§rLingering Potion of Poisen");
/*  70 */     POTION_NAME_INDEX.put("strong_poison_lingering", "§rLingering Potion of Poisen");
/*  71 */     POTION_NAME_INDEX.put("regeneration_lingering", "§rLingering Potion of Regeneration");
/*  72 */     POTION_NAME_INDEX.put("long_regeneration_lingering", "§rLingering Potion of Regeneration");
/*  73 */     POTION_NAME_INDEX.put("strong_regeneration_lingering", "§rLingering Potion of Regeneration");
/*  74 */     POTION_NAME_INDEX.put("strength_lingering", "§rLingering Potion of Strength");
/*  75 */     POTION_NAME_INDEX.put("long_strength_lingering", "§rLingering Potion of Strength");
/*  76 */     POTION_NAME_INDEX.put("strong_strength_lingering", "§rLingering Potion of Strength");
/*  77 */     POTION_NAME_INDEX.put("weakness_lingering", "§rLingering Potion of Weakness");
/*  78 */     POTION_NAME_INDEX.put("long_weakness_lingering", "§rLingering Potion of Weakness");
/*  79 */     POTION_NAME_INDEX.put("luck_lingering", "§rLingering Potion of Luck");
/*  80 */     POTION_NAME_INDEX.put("luck", "§rPotion of Luck");
/*  81 */     POTION_NAME_INDEX.put("luck_splash", "§rSplash Potion of Luck");
/*     */   }
/*     */   
/*     */   public static Item toClient(Item item) {
/*  85 */     if (item == null) return null;
/*     */     
/*  87 */     CompoundTag tag = item.tag();
/*  88 */     if (tag == null) item.setTag(tag = new CompoundTag());
/*     */     
/*  90 */     CompoundTag viaVersionTag = new CompoundTag();
/*  91 */     tag.put("ViaRewind1_8to1_9", (Tag)viaVersionTag);
/*     */     
/*  93 */     viaVersionTag.put("id", (Tag)new ShortTag((short)item.identifier()));
/*  94 */     viaVersionTag.put("data", (Tag)new ShortTag(item.data()));
/*     */     
/*  96 */     CompoundTag display = (CompoundTag)tag.get("display");
/*  97 */     if (display != null && display.contains("Name")) {
/*  98 */       viaVersionTag.put("displayName", (Tag)new StringTag((String)display.get("Name").getValue()));
/*     */     }
/*     */     
/* 101 */     if (display != null && display.contains("Lore")) {
/* 102 */       viaVersionTag.put("lore", (Tag)new ListTag(((ListTag)display.get("Lore")).getValue()));
/*     */     }
/*     */     
/* 105 */     if (tag.contains("ench") || tag.contains("StoredEnchantments")) {
/* 106 */       ListTag enchTag = tag.contains("ench") ? (ListTag)tag.get("ench") : (ListTag)tag.get("StoredEnchantments");
/* 107 */       List<Tag> lore = new ArrayList<>();
/* 108 */       for (Tag ench : new ArrayList(enchTag.getValue())) {
/* 109 */         short id = ((NumberTag)((CompoundTag)ench).get("id")).asShort();
/* 110 */         short lvl = ((NumberTag)((CompoundTag)ench).get("lvl")).asShort();
/*     */         
/* 112 */         if (id == 70) {
/* 113 */           s = "§r§7Mending ";
/* 114 */         } else if (id == 9) {
/* 115 */           s = "§r§7Frost Walker ";
/*     */         } else {
/*     */           continue;
/*     */         } 
/* 119 */         enchTag.remove(ench);
/* 120 */         String s = s + (String)Enchantments.ENCHANTMENTS.getOrDefault(Short.valueOf(lvl), "enchantment.level." + lvl);
/* 121 */         lore.add(new StringTag(s));
/*     */       } 
/* 123 */       if (!lore.isEmpty()) {
/* 124 */         if (display == null) {
/* 125 */           tag.put("display", (Tag)(display = new CompoundTag()));
/* 126 */           viaVersionTag.put("noDisplay", (Tag)new ByteTag());
/*     */         } 
/* 128 */         ListTag loreTag = (ListTag)display.get("Lore");
/* 129 */         if (loreTag == null) display.put("Lore", (Tag)(loreTag = new ListTag(StringTag.class))); 
/* 130 */         lore.addAll(loreTag.getValue());
/* 131 */         loreTag.setValue(lore);
/*     */       } 
/*     */     } 
/*     */     
/* 135 */     if (item.data() != 0 && tag.contains("Unbreakable")) {
/* 136 */       ByteTag unbreakable = (ByteTag)tag.get("Unbreakable");
/* 137 */       if (unbreakable.asByte() != 0) {
/* 138 */         viaVersionTag.put("Unbreakable", (Tag)new ByteTag(unbreakable.asByte()));
/* 139 */         tag.remove("Unbreakable");
/*     */         
/* 141 */         if (display == null) {
/* 142 */           tag.put("display", (Tag)(display = new CompoundTag()));
/* 143 */           viaVersionTag.put("noDisplay", (Tag)new ByteTag());
/*     */         } 
/* 145 */         ListTag loreTag = (ListTag)display.get("Lore");
/* 146 */         if (loreTag == null) display.put("Lore", (Tag)(loreTag = new ListTag(StringTag.class))); 
/* 147 */         loreTag.add((Tag)new StringTag("§9Unbreakable"));
/*     */       } 
/*     */     } 
/*     */     
/* 151 */     if (tag.contains("AttributeModifiers")) {
/* 152 */       viaVersionTag.put("AttributeModifiers", tag.get("AttributeModifiers").clone());
/*     */     }
/*     */     
/* 155 */     if (item.identifier() == 383 && item.data() == 0) {
/* 156 */       int data = 0;
/* 157 */       if (tag.contains("EntityTag")) {
/* 158 */         CompoundTag entityTag = (CompoundTag)tag.get("EntityTag");
/* 159 */         if (entityTag.contains("id")) {
/* 160 */           StringTag id = (StringTag)entityTag.get("id");
/* 161 */           if (ENTTIY_NAME_TO_ID.containsKey(id.getValue())) {
/* 162 */             data = ((Integer)ENTTIY_NAME_TO_ID.get(id.getValue())).intValue();
/* 163 */           } else if (display == null) {
/* 164 */             tag.put("display", (Tag)(display = new CompoundTag()));
/* 165 */             viaVersionTag.put("noDisplay", (Tag)new ByteTag());
/* 166 */             display.put("Name", (Tag)new StringTag("§rSpawn " + id.getValue()));
/*     */           } 
/*     */         } 
/*     */       } 
/*     */       
/* 171 */       item.setData((short)data);
/*     */     } 
/*     */     
/* 174 */     ReplacementRegistry1_8to1_9.replace(item);
/*     */     
/* 176 */     if (item.identifier() == 373 || item.identifier() == 438 || item.identifier() == 441) {
/* 177 */       int data = 0;
/* 178 */       if (tag.contains("Potion")) {
/* 179 */         StringTag potion = (StringTag)tag.get("Potion");
/* 180 */         String potionName = potion.getValue().replace("minecraft:", "");
/* 181 */         if (POTION_NAME_TO_ID.containsKey(potionName)) {
/* 182 */           data = ((Integer)POTION_NAME_TO_ID.get(potionName)).intValue();
/*     */         }
/* 184 */         if (item.identifier() == 438) { potionName = potionName + "_splash"; }
/* 185 */         else if (item.identifier() == 441) { potionName = potionName + "_lingering"; }
/* 186 */          if ((display == null || !display.contains("Name")) && POTION_NAME_INDEX.containsKey(potionName)) {
/* 187 */           if (display == null) {
/* 188 */             tag.put("display", (Tag)(display = new CompoundTag()));
/* 189 */             viaVersionTag.put("noDisplay", (Tag)new ByteTag());
/*     */           } 
/* 191 */           display.put("Name", (Tag)new StringTag(POTION_NAME_INDEX.get(potionName)));
/*     */         } 
/*     */       } 
/*     */       
/* 195 */       if (item.identifier() == 438 || item.identifier() == 441) {
/* 196 */         item.setIdentifier(373);
/* 197 */         data += 8192;
/*     */       } 
/*     */       
/* 200 */       item.setData((short)data);
/*     */     } 
/*     */     
/* 203 */     if (tag.contains("AttributeModifiers")) {
/* 204 */       ListTag attributes = (ListTag)tag.get("AttributeModifiers");
/* 205 */       for (int i = 0; i < attributes.size(); i++) {
/* 206 */         CompoundTag attribute = (CompoundTag)attributes.get(i);
/* 207 */         String name = (String)attribute.get("AttributeName").getValue();
/* 208 */         if (!Protocol1_8TO1_9.VALID_ATTRIBUTES.contains(name)) {
/* 209 */           attributes.remove((Tag)attribute);
/* 210 */           i--;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 215 */     if (viaVersionTag.size() == 2 && ((Short)viaVersionTag.get("id").getValue()).shortValue() == item.identifier() && ((Short)viaVersionTag.get("data").getValue()).shortValue() == item.data()) {
/* 216 */       item.tag().remove("ViaRewind1_8to1_9");
/* 217 */       if (item.tag().isEmpty()) item.setTag(null);
/*     */     
/*     */     } 
/* 220 */     return item;
/*     */   }
/*     */   
/*     */   public static Item toServer(Item item) {
/* 224 */     if (item == null) return null;
/*     */     
/* 226 */     CompoundTag tag = item.tag();
/*     */     
/* 228 */     if (item.identifier() == 383 && item.data() != 0) {
/* 229 */       if (tag == null) item.setTag(tag = new CompoundTag()); 
/* 230 */       if (!tag.contains("EntityTag") && ENTTIY_ID_TO_NAME.containsKey(Integer.valueOf(item.data()))) {
/* 231 */         CompoundTag entityTag = new CompoundTag();
/* 232 */         entityTag.put("id", (Tag)new StringTag(ENTTIY_ID_TO_NAME.get(Integer.valueOf(item.data()))));
/* 233 */         tag.put("EntityTag", (Tag)entityTag);
/*     */       } 
/*     */       
/* 236 */       item.setData((short)0);
/*     */     } 
/*     */     
/* 239 */     if (item.identifier() == 373 && (tag == null || !tag.contains("Potion"))) {
/* 240 */       if (tag == null) item.setTag(tag = new CompoundTag());
/*     */       
/* 242 */       if (item.data() >= 16384) {
/* 243 */         item.setIdentifier(438);
/* 244 */         item.setData((short)(item.data() - 8192));
/*     */       } 
/*     */       
/* 247 */       String name = (item.data() == 8192) ? "water" : com.viaversion.viaversion.protocols.protocol1_9to1_8.ItemRewriter.potionNameFromDamage(item.data());
/* 248 */       tag.put("Potion", (Tag)new StringTag("minecraft:" + name));
/* 249 */       item.setData((short)0);
/*     */     } 
/*     */     
/* 252 */     if (tag == null || !item.tag().contains("ViaRewind1_8to1_9")) return item;
/*     */     
/* 254 */     CompoundTag viaVersionTag = (CompoundTag)tag.remove("ViaRewind1_8to1_9");
/*     */     
/* 256 */     item.setIdentifier(((Short)viaVersionTag.get("id").getValue()).shortValue());
/* 257 */     item.setData(((Short)viaVersionTag.get("data").getValue()).shortValue());
/*     */     
/* 259 */     if (viaVersionTag.contains("noDisplay")) tag.remove("display");
/*     */     
/* 261 */     if (viaVersionTag.contains("Unbreakable")) {
/* 262 */       tag.put("Unbreakable", viaVersionTag.get("Unbreakable").clone());
/*     */     }
/*     */     
/* 265 */     if (viaVersionTag.contains("displayName")) {
/* 266 */       CompoundTag display = (CompoundTag)tag.get("display");
/* 267 */       if (display == null) tag.put("display", (Tag)(display = new CompoundTag())); 
/* 268 */       StringTag name = (StringTag)display.get("Name");
/* 269 */       if (name == null) { display.put("Name", (Tag)new StringTag((String)viaVersionTag.get("displayName").getValue())); }
/* 270 */       else { name.setValue((String)viaVersionTag.get("displayName").getValue()); } 
/* 271 */     } else if (tag.contains("display")) {
/* 272 */       ((CompoundTag)tag.get("display")).remove("Name");
/*     */     } 
/*     */     
/* 275 */     if (viaVersionTag.contains("lore")) {
/* 276 */       CompoundTag display = (CompoundTag)tag.get("display");
/* 277 */       if (display == null) tag.put("display", (Tag)(display = new CompoundTag())); 
/* 278 */       ListTag lore = (ListTag)display.get("Lore");
/* 279 */       if (lore == null) { display.put("Lore", (Tag)new ListTag((List)viaVersionTag.get("lore").getValue())); }
/* 280 */       else { lore.setValue((List)viaVersionTag.get("lore").getValue()); } 
/* 281 */     } else if (tag.contains("display")) {
/* 282 */       ((CompoundTag)tag.get("display")).remove("Lore");
/*     */     } 
/*     */     
/* 285 */     tag.remove("AttributeModifiers");
/* 286 */     if (viaVersionTag.contains("AttributeModifiers")) {
/* 287 */       tag.put("AttributeModifiers", viaVersionTag.get("AttributeModifiers"));
/*     */     }
/*     */     
/* 290 */     return item;
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\de\gerrygames\viarewind\protocol\protocol1_8to1_9\items\ItemRewriter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */