/*     */ package de.gerrygames.viarewind.protocol.protocol1_8to1_9.metadata;
/*     */ 
/*     */ import com.viaversion.viaversion.api.minecraft.EulerAngle;
/*     */ import com.viaversion.viaversion.api.minecraft.Vector;
/*     */ import com.viaversion.viaversion.api.minecraft.entities.Entity1_10Types;
/*     */ import com.viaversion.viaversion.api.minecraft.item.Item;
/*     */ import com.viaversion.viaversion.api.minecraft.metadata.MetaType;
/*     */ import com.viaversion.viaversion.api.minecraft.metadata.Metadata;
/*     */ import com.viaversion.viaversion.api.minecraft.metadata.types.MetaType1_8;
/*     */ import com.viaversion.viaversion.api.minecraft.metadata.types.MetaType1_9;
/*     */ import com.viaversion.viaversion.protocols.protocol1_9to1_8.metadata.MetaIndex;
/*     */ import de.gerrygames.viarewind.ViaRewind;
/*     */ import de.gerrygames.viarewind.protocol.protocol1_8to1_9.items.ItemRewriter;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.UUID;
/*     */ 
/*     */ public class MetadataRewriter {
/*     */   public static void transform(Entity1_10Types.EntityType type, List<Metadata> list) {
/*  20 */     for (Metadata entry : new ArrayList(list)) {
/*  21 */       MetaIndex metaIndex = MetaIndex1_8to1_9.searchIndex(type, entry.id());
/*     */       try {
/*  23 */         if (metaIndex != null) {
/*  24 */           UUID owner; Vector vector; EulerAngle angle; if (metaIndex.getOldType() == MetaType1_8.NonExistent || metaIndex.getNewType() == null) {
/*  25 */             list.remove(entry);
/*     */             continue;
/*     */           } 
/*  28 */           Object value = entry.getValue();
/*  29 */           entry.setMetaTypeUnsafe((MetaType)metaIndex.getOldType());
/*  30 */           entry.setId(metaIndex.getIndex());
/*  31 */           switch (metaIndex.getNewType()) {
/*     */             case Byte:
/*  33 */               if (metaIndex.getOldType() == MetaType1_8.Byte) {
/*  34 */                 entry.setValue(value);
/*     */               }
/*  36 */               if (metaIndex.getOldType() == MetaType1_8.Int) {
/*  37 */                 entry.setValue(Integer.valueOf(((Byte)value).intValue()));
/*     */               }
/*     */               break;
/*     */             case OptUUID:
/*  41 */               if (metaIndex.getOldType() != MetaType1_8.String) {
/*  42 */                 list.remove(entry);
/*     */                 break;
/*     */               } 
/*  45 */               owner = (UUID)value;
/*  46 */               if (owner == null) { entry.setValue(""); break; }
/*  47 */                entry.setValue(owner.toString());
/*     */               break;
/*     */             case BlockID:
/*  50 */               list.remove(entry);
/*  51 */               list.add(new Metadata(metaIndex.getIndex(), (MetaType)MetaType1_8.Short, Short.valueOf(((Integer)value).shortValue())));
/*     */               break;
/*     */             case VarInt:
/*  54 */               if (metaIndex.getOldType() == MetaType1_8.Byte) {
/*  55 */                 entry.setValue(Byte.valueOf(((Integer)value).byteValue()));
/*     */               }
/*  57 */               if (metaIndex.getOldType() == MetaType1_8.Short) {
/*  58 */                 entry.setValue(Short.valueOf(((Integer)value).shortValue()));
/*     */               }
/*  60 */               if (metaIndex.getOldType() == MetaType1_8.Int) {
/*  61 */                 entry.setValue(value);
/*     */               }
/*     */               break;
/*     */             case Float:
/*  65 */               entry.setValue(value);
/*     */               break;
/*     */             case String:
/*  68 */               entry.setValue(value);
/*     */               break;
/*     */             case Boolean:
/*  71 */               if (metaIndex == MetaIndex.AGEABLE_AGE) { entry.setValue(Byte.valueOf((byte)(((Boolean)value).booleanValue() ? -1 : 0))); break; }
/*  72 */                entry.setValue(Byte.valueOf((byte)(((Boolean)value).booleanValue() ? 1 : 0)));
/*     */               break;
/*     */             case Slot:
/*  75 */               entry.setValue(ItemRewriter.toClient((Item)value));
/*     */               break;
/*     */             case Position:
/*  78 */               vector = (Vector)value;
/*  79 */               entry.setValue(vector);
/*     */               break;
/*     */             case Vector3F:
/*  82 */               angle = (EulerAngle)value;
/*  83 */               entry.setValue(angle);
/*     */               break;
/*     */             case Chat:
/*  86 */               entry.setValue(value);
/*     */               break;
/*     */             default:
/*  89 */               ViaRewind.getPlatform().getLogger().warning("[Out] Unhandled MetaDataType: " + metaIndex.getNewType());
/*  90 */               list.remove(entry);
/*     */               break;
/*     */           } 
/*     */           
/*  94 */           if (!metaIndex.getOldType().type().getOutputClass().isAssignableFrom(entry.getValue().getClass())) {
/*  95 */             list.remove(entry);
/*     */           }
/*     */           continue;
/*     */         } 
/*  99 */         throw new Exception("Could not find valid metadata");
/*     */       }
/* 101 */       catch (Exception e) {
/* 102 */         list.remove(entry);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\de\gerrygames\viarewind\protocol\protocol1_8to1_9\metadata\MetadataRewriter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */