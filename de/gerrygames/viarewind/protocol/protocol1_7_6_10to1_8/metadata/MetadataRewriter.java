/*    */ package de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.metadata;
/*    */ 
/*    */ import com.viaversion.viaversion.api.minecraft.entities.Entity1_10Types;
/*    */ import com.viaversion.viaversion.api.minecraft.item.Item;
/*    */ import com.viaversion.viaversion.api.minecraft.metadata.MetaType;
/*    */ import com.viaversion.viaversion.api.minecraft.metadata.Metadata;
/*    */ import com.viaversion.viaversion.api.minecraft.metadata.types.MetaType1_8;
/*    */ import de.gerrygames.viarewind.ViaRewind;
/*    */ import de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.items.ItemRewriter;
/*    */ import de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.types.MetaType1_7_6_10;
/*    */ import de.gerrygames.viarewind.protocol.protocol1_8to1_7_6_10.metadata.MetaIndex1_8to1_7_6_10;
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ 
/*    */ 
/*    */ public class MetadataRewriter
/*    */ {
/*    */   public static void transform(Entity1_10Types.EntityType type, List<Metadata> list) {
/* 19 */     for (Metadata entry : new ArrayList(list)) {
/* 20 */       MetaIndex1_8to1_7_6_10 metaIndex = MetaIndex1_7_6_10to1_8.searchIndex(type, entry.id());
/*    */       try {
/* 22 */         if (metaIndex == null) throw new Exception("Could not find valid metadata"); 
/* 23 */         if (metaIndex.getOldType() == MetaType1_7_6_10.NonExistent) {
/* 24 */           list.remove(entry);
/*    */           continue;
/*    */         } 
/* 27 */         Object value = entry.getValue();
/* 28 */         if (!metaIndex.getNewType().type().getOutputClass().isAssignableFrom(value.getClass())) {
/* 29 */           list.remove(entry);
/*    */           continue;
/*    */         } 
/* 32 */         entry.setMetaTypeUnsafe((MetaType)metaIndex.getOldType());
/* 33 */         entry.setId(metaIndex.getIndex());
/* 34 */         switch (metaIndex.getOldType()) {
/*    */           case Int:
/* 36 */             if (metaIndex.getNewType() == MetaType1_8.Byte) {
/* 37 */               entry.setValue(Integer.valueOf(((Byte)value).intValue()));
/* 38 */               if (metaIndex == MetaIndex1_8to1_7_6_10.ENTITY_AGEABLE_AGE && (
/* 39 */                 (Integer)entry.getValue()).intValue() < 0) {
/* 40 */                 entry.setValue(Integer.valueOf(-25000));
/*    */               }
/*    */             } 
/*    */             
/* 44 */             if (metaIndex.getNewType() == MetaType1_8.Short) {
/* 45 */               entry.setValue(Integer.valueOf(((Short)value).intValue()));
/*    */             }
/* 47 */             if (metaIndex.getNewType() == MetaType1_8.Int) {
/* 48 */               entry.setValue(value);
/*    */             }
/*    */             continue;
/*    */           case Byte:
/* 52 */             if (metaIndex.getNewType() == MetaType1_8.Int) {
/* 53 */               entry.setValue(Byte.valueOf(((Integer)value).byteValue()));
/*    */             }
/* 55 */             if (metaIndex.getNewType() == MetaType1_8.Byte) {
/* 56 */               if (metaIndex == MetaIndex1_8to1_7_6_10.ITEM_FRAME_ROTATION) {
/* 57 */                 value = Byte.valueOf(Integer.valueOf(((Byte)value).byteValue() / 2).byteValue());
/*    */               }
/* 59 */               entry.setValue(value);
/*    */             } 
/* 61 */             if (metaIndex == MetaIndex1_8to1_7_6_10.HUMAN_SKIN_FLAGS) {
/* 62 */               byte flags = ((Byte)value).byteValue();
/* 63 */               boolean cape = ((flags & 0x1) != 0);
/* 64 */               flags = (byte)(cape ? 0 : 2);
/* 65 */               entry.setValue(Byte.valueOf(flags));
/*    */             } 
/*    */             continue;
/*    */           case Slot:
/* 69 */             entry.setValue(ItemRewriter.toClient((Item)value));
/*    */             continue;
/*    */           case Float:
/* 72 */             entry.setValue(value);
/*    */             continue;
/*    */           case Short:
/* 75 */             entry.setValue(value);
/*    */             continue;
/*    */           case String:
/* 78 */             entry.setValue(value);
/*    */             continue;
/*    */           case Position:
/* 81 */             entry.setValue(value);
/*    */             continue;
/*    */         } 
/* 84 */         ViaRewind.getPlatform().getLogger().warning("[Out] Unhandled MetaDataType: " + metaIndex.getNewType());
/* 85 */         list.remove(entry);
/*    */       
/*    */       }
/* 88 */       catch (Exception e) {
/* 89 */         list.remove(entry);
/*    */       } 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\de\gerrygames\viarewind\protocol\protocol1_7_6_10to1_8\metadata\MetadataRewriter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */