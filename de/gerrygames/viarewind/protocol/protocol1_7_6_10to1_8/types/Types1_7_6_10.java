/*    */ package de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.types;
/*    */ 
/*    */ import com.viaversion.viaversion.api.minecraft.item.Item;
/*    */ import com.viaversion.viaversion.api.minecraft.metadata.Metadata;
/*    */ import com.viaversion.viaversion.api.type.Type;
/*    */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
/*    */ import java.util.List;
/*    */ 
/*    */ public class Types1_7_6_10
/*    */ {
/* 11 */   public static final Type<CompoundTag> COMPRESSED_NBT = new CompressedNBTType();
/* 12 */   public static final Type<Item[]> ITEM_ARRAY = new ItemArrayType(false);
/* 13 */   public static final Type<Item[]> COMPRESSED_NBT_ITEM_ARRAY = new ItemArrayType(true);
/* 14 */   public static final Type<Item> ITEM = new ItemType(false);
/* 15 */   public static final Type<Item> COMPRESSED_NBT_ITEM = new ItemType(true);
/* 16 */   public static final Type<List<Metadata>> METADATA_LIST = (Type<List<Metadata>>)new MetadataListType();
/* 17 */   public static final Type<Metadata> METADATA = (Type<Metadata>)new MetadataType();
/* 18 */   public static final Type<CompoundTag> NBT = new NBTType();
/*    */ 
/*    */ 
/*    */   
/* 22 */   public static final Type<int[]> INT_ARRAY = new IntArrayType();
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\de\gerrygames\viarewind\protocol\protocol1_7_6_10to1_8\types\Types1_7_6_10.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */