/*     */ package com.viaversion.viabackwards.api.rewriters;
/*     */ 
/*     */ import com.viaversion.viabackwards.api.BackwardsProtocol;
/*     */ import com.viaversion.viabackwards.api.data.MappedLegacyBlockItem;
/*     */ import com.viaversion.viabackwards.api.data.VBMappingDataLoader;
/*     */ import com.viaversion.viabackwards.protocol.protocol1_11_1to1_12.data.BlockColors;
/*     */ import com.viaversion.viabackwards.utils.Block;
/*     */ import com.viaversion.viaversion.api.minecraft.chunks.Chunk;
/*     */ import com.viaversion.viaversion.api.minecraft.chunks.ChunkSection;
/*     */ import com.viaversion.viaversion.api.minecraft.chunks.DataPalette;
/*     */ import com.viaversion.viaversion.api.minecraft.chunks.PaletteType;
/*     */ import com.viaversion.viaversion.api.minecraft.item.Item;
/*     */ import com.viaversion.viaversion.api.protocol.packet.ClientboundPacketType;
/*     */ import com.viaversion.viaversion.api.protocol.packet.ServerboundPacketType;
/*     */ import com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectMap;
/*     */ import com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectOpenHashMap;
/*     */ import com.viaversion.viaversion.libs.gson.JsonElement;
/*     */ import com.viaversion.viaversion.libs.gson.JsonObject;
/*     */ import com.viaversion.viaversion.libs.gson.JsonPrimitive;
/*     */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.ByteTag;
/*     */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
/*     */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.IntTag;
/*     */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.NumberTag;
/*     */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.StringTag;
/*     */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.Tag;
/*     */ import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.ChatRewriter;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class LegacyBlockItemRewriter<C extends ClientboundPacketType, S extends ServerboundPacketType, T extends BackwardsProtocol<C, ?, ?, S>>
/*     */   extends ItemRewriterBase<C, S, T>
/*     */ {
/*  52 */   private static final Map<String, Int2ObjectMap<MappedLegacyBlockItem>> LEGACY_MAPPINGS = new HashMap<>();
/*     */   protected final Int2ObjectMap<MappedLegacyBlockItem> replacementData;
/*     */   
/*     */   static {
/*  56 */     JsonObject jsonObject = VBMappingDataLoader.loadFromDataDir("legacy-mappings.json");
/*  57 */     for (Map.Entry<String, JsonElement> entry : (Iterable<Map.Entry<String, JsonElement>>)jsonObject.entrySet()) {
/*  58 */       Int2ObjectOpenHashMap int2ObjectOpenHashMap = new Int2ObjectOpenHashMap(8);
/*  59 */       LEGACY_MAPPINGS.put(entry.getKey(), int2ObjectOpenHashMap);
/*  60 */       for (Map.Entry<String, JsonElement> dataEntry : (Iterable<Map.Entry<String, JsonElement>>)((JsonElement)entry.getValue()).getAsJsonObject().entrySet()) {
/*  61 */         JsonObject object = ((JsonElement)dataEntry.getValue()).getAsJsonObject();
/*  62 */         int id = object.getAsJsonPrimitive("id").getAsInt();
/*  63 */         JsonPrimitive jsonData = object.getAsJsonPrimitive("data");
/*  64 */         short data = (jsonData != null) ? jsonData.getAsShort() : 0;
/*  65 */         String name = object.getAsJsonPrimitive("name").getAsString();
/*  66 */         JsonPrimitive blockField = object.getAsJsonPrimitive("block");
/*  67 */         boolean block = (blockField != null && blockField.getAsBoolean());
/*     */         
/*  69 */         if (((String)dataEntry.getKey()).indexOf('-') != -1) {
/*     */           
/*  71 */           String[] split = ((String)dataEntry.getKey()).split("-", 2);
/*  72 */           int from = Integer.parseInt(split[0]);
/*  73 */           int to = Integer.parseInt(split[1]);
/*     */ 
/*     */           
/*  76 */           if (name.contains("%color%")) {
/*  77 */             for (int j = from; j <= to; j++)
/*  78 */               int2ObjectOpenHashMap.put(j, new MappedLegacyBlockItem(id, data, name.replace("%color%", BlockColors.get(j - from)), block)); 
/*     */             continue;
/*     */           } 
/*  81 */           MappedLegacyBlockItem mappedBlockItem = new MappedLegacyBlockItem(id, data, name, block);
/*  82 */           for (int i = from; i <= to; i++) {
/*  83 */             int2ObjectOpenHashMap.put(i, mappedBlockItem);
/*     */           }
/*     */           continue;
/*     */         } 
/*  87 */         int2ObjectOpenHashMap.put(Integer.parseInt(dataEntry.getKey()), new MappedLegacyBlockItem(id, data, name, block));
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected LegacyBlockItemRewriter(T protocol) {
/*  94 */     super(protocol, false);
/*  95 */     this.replacementData = LEGACY_MAPPINGS.get(protocol.getClass().getSimpleName().split("To")[1].replace("_", "."));
/*     */   }
/*     */ 
/*     */   
/*     */   public Item handleItemToClient(Item item) {
/* 100 */     if (item == null) return null;
/*     */     
/* 102 */     MappedLegacyBlockItem data = (MappedLegacyBlockItem)this.replacementData.get(item.identifier());
/* 103 */     if (data == null)
/*     */     {
/* 105 */       return super.handleItemToClient(item);
/*     */     }
/*     */     
/* 108 */     short originalData = item.data();
/* 109 */     item.setIdentifier(data.getId());
/*     */     
/* 111 */     if (data.getData() != -1) {
/* 112 */       item.setData(data.getData());
/*     */     }
/*     */ 
/*     */     
/* 116 */     if (data.getName() != null) {
/* 117 */       if (item.tag() == null) {
/* 118 */         item.setTag(new CompoundTag());
/*     */       }
/*     */       
/* 121 */       CompoundTag display = (CompoundTag)item.tag().get("display");
/* 122 */       if (display == null) {
/* 123 */         item.tag().put("display", (Tag)(display = new CompoundTag()));
/*     */       }
/*     */       
/* 126 */       StringTag nameTag = (StringTag)display.get("Name");
/* 127 */       if (nameTag == null) {
/* 128 */         display.put("Name", (Tag)(nameTag = new StringTag(data.getName())));
/* 129 */         display.put(this.nbtTagName + "|customName", (Tag)new ByteTag());
/*     */       } 
/*     */ 
/*     */       
/* 133 */       String value = nameTag.getValue();
/* 134 */       if (value.contains("%vb_color%")) {
/* 135 */         display.put("Name", (Tag)new StringTag(value.replace("%vb_color%", BlockColors.get(originalData))));
/*     */       }
/*     */     } 
/* 138 */     return item;
/*     */   }
/*     */   
/*     */   public int handleBlockID(int idx) {
/* 142 */     int type = idx >> 4;
/* 143 */     int meta = idx & 0xF;
/*     */     
/* 145 */     Block b = handleBlock(type, meta);
/* 146 */     if (b == null) return idx;
/*     */     
/* 148 */     return b.getId() << 4 | b.getData() & 0xF;
/*     */   }
/*     */   
/*     */   public Block handleBlock(int blockId, int data) {
/* 152 */     MappedLegacyBlockItem settings = (MappedLegacyBlockItem)this.replacementData.get(blockId);
/* 153 */     if (settings == null || !settings.isBlock()) return null;
/*     */     
/* 155 */     Block block = settings.getBlock();
/*     */     
/* 157 */     if (block.getData() == -1) {
/* 158 */       return block.withData(data);
/*     */     }
/* 160 */     return block;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void handleChunk(Chunk chunk) {
/* 165 */     Map<Pos, CompoundTag> tags = new HashMap<>();
/* 166 */     for (CompoundTag tag : chunk.getBlockEntities()) {
/*     */       Tag xTag, yTag, zTag;
/*     */ 
/*     */       
/* 170 */       if ((xTag = tag.get("x")) == null || (yTag = tag.get("y")) == null || (zTag = tag.get("z")) == null) {
/*     */         continue;
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 177 */       Pos pos = new Pos(((NumberTag)xTag).asInt() & 0xF, ((NumberTag)yTag).asInt(), ((NumberTag)zTag).asInt() & 0xF);
/* 178 */       tags.put(pos, tag);
/*     */ 
/*     */       
/* 181 */       if (pos.getY() < 0 || pos.getY() > 255)
/*     */         continue; 
/* 183 */       ChunkSection section = chunk.getSections()[pos.getY() >> 4];
/* 184 */       if (section == null)
/*     */         continue; 
/* 186 */       int block = section.palette(PaletteType.BLOCKS).idAt(pos.getX(), pos.getY() & 0xF, pos.getZ());
/* 187 */       int btype = block >> 4;
/*     */       
/* 189 */       MappedLegacyBlockItem settings = (MappedLegacyBlockItem)this.replacementData.get(btype);
/* 190 */       if (settings != null && settings.hasBlockEntityHandler()) {
/* 191 */         settings.getBlockEntityHandler().handleOrNewCompoundTag(block, tag);
/*     */       }
/*     */     } 
/*     */     
/* 195 */     for (int i = 0; i < (chunk.getSections()).length; i++) {
/* 196 */       ChunkSection section = chunk.getSections()[i];
/* 197 */       if (section != null) {
/*     */ 
/*     */ 
/*     */         
/* 201 */         boolean hasBlockEntityHandler = false;
/*     */ 
/*     */         
/* 204 */         DataPalette palette = section.palette(PaletteType.BLOCKS);
/* 205 */         for (int j = 0; j < palette.size(); j++) {
/* 206 */           int block = palette.idByIndex(j);
/* 207 */           int btype = block >> 4;
/* 208 */           int meta = block & 0xF;
/*     */           
/* 210 */           Block b = handleBlock(btype, meta);
/* 211 */           if (b != null) {
/* 212 */             palette.setIdByIndex(j, b.getId() << 4 | b.getData() & 0xF);
/*     */           }
/*     */ 
/*     */           
/* 216 */           if (!hasBlockEntityHandler) {
/*     */             
/* 218 */             MappedLegacyBlockItem settings = (MappedLegacyBlockItem)this.replacementData.get(btype);
/* 219 */             if (settings != null && settings.hasBlockEntityHandler()) {
/* 220 */               hasBlockEntityHandler = true;
/*     */             }
/*     */           } 
/*     */         } 
/* 224 */         if (hasBlockEntityHandler)
/*     */         {
/*     */           
/* 227 */           for (int x = 0; x < 16; x++) {
/* 228 */             for (int y = 0; y < 16; y++) {
/* 229 */               for (int z = 0; z < 16; z++) {
/* 230 */                 int block = palette.idAt(x, y, z);
/* 231 */                 int btype = block >> 4;
/* 232 */                 int meta = block & 0xF;
/*     */                 
/* 234 */                 MappedLegacyBlockItem settings = (MappedLegacyBlockItem)this.replacementData.get(btype);
/* 235 */                 if (settings != null && settings.hasBlockEntityHandler()) {
/*     */                   
/* 237 */                   Pos pos = new Pos(x, y + (i << 4), z);
/*     */ 
/*     */                   
/* 240 */                   if (!tags.containsKey(pos)) {
/*     */                     
/* 242 */                     CompoundTag tag = new CompoundTag();
/* 243 */                     tag.put("x", (Tag)new IntTag(x + (chunk.getX() << 4)));
/* 244 */                     tag.put("y", (Tag)new IntTag(y + (i << 4)));
/* 245 */                     tag.put("z", (Tag)new IntTag(z + (chunk.getZ() << 4)));
/*     */                     
/* 247 */                     settings.getBlockEntityHandler().handleOrNewCompoundTag(block, tag);
/* 248 */                     chunk.getBlockEntities().add(tag);
/*     */                   } 
/*     */                 } 
/*     */               } 
/*     */             } 
/*     */           }  } 
/*     */       } 
/*     */     }  } protected CompoundTag getNamedTag(String text) {
/* 256 */     CompoundTag tag = new CompoundTag();
/* 257 */     tag.put("display", (Tag)new CompoundTag());
/* 258 */     text = "§r" + text;
/* 259 */     ((CompoundTag)tag.get("display")).put("Name", (Tag)new StringTag(this.jsonNameFormat ? ChatRewriter.legacyTextToJsonString(text) : text));
/* 260 */     return tag;
/*     */   }
/*     */   
/*     */   private static final class Pos
/*     */   {
/*     */     private final int x;
/*     */     private final short y;
/*     */     private final int z;
/*     */     
/*     */     private Pos(int x, int y, int z) {
/* 270 */       this.x = x;
/* 271 */       this.y = (short)y;
/* 272 */       this.z = z;
/*     */     }
/*     */     
/*     */     public int getX() {
/* 276 */       return this.x;
/*     */     }
/*     */     
/*     */     public int getY() {
/* 280 */       return this.y;
/*     */     }
/*     */     
/*     */     public int getZ() {
/* 284 */       return this.z;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean equals(Object o) {
/* 289 */       if (this == o) return true; 
/* 290 */       if (o == null || getClass() != o.getClass()) return false; 
/* 291 */       Pos pos = (Pos)o;
/* 292 */       if (this.x != pos.x) return false; 
/* 293 */       if (this.y != pos.y) return false; 
/* 294 */       return (this.z == pos.z);
/*     */     }
/*     */ 
/*     */     
/*     */     public int hashCode() {
/* 299 */       int result = this.x;
/* 300 */       result = 31 * result + this.y;
/* 301 */       result = 31 * result + this.z;
/* 302 */       return result;
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/* 307 */       return "Pos{x=" + this.x + ", y=" + this.y + ", z=" + this.z + '}';
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viabackwards\api\rewriters\LegacyBlockItemRewriter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */