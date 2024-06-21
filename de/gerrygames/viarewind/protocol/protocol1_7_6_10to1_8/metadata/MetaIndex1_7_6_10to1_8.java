/*    */ package de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.metadata;
/*    */ 
/*    */ import com.viaversion.viaversion.api.minecraft.entities.Entity1_10Types;
/*    */ import com.viaversion.viaversion.util.Pair;
/*    */ import de.gerrygames.viarewind.protocol.protocol1_8to1_7_6_10.metadata.MetaIndex1_8to1_7_6_10;
/*    */ import java.util.HashMap;
/*    */ import java.util.Optional;
/*    */ 
/*    */ 
/*    */ public class MetaIndex1_7_6_10to1_8
/*    */ {
/* 12 */   private static final HashMap<Pair<Entity1_10Types.EntityType, Integer>, MetaIndex1_8to1_7_6_10> metadataRewrites = new HashMap<>();
/*    */   
/*    */   static {
/* 15 */     for (MetaIndex1_8to1_7_6_10 index : MetaIndex1_8to1_7_6_10.values())
/* 16 */       metadataRewrites.put(new Pair(index.getClazz(), Integer.valueOf(index.getNewIndex())), index); 
/*    */   }
/*    */   
/*    */   private static Optional<MetaIndex1_8to1_7_6_10> getIndex(Entity1_10Types.EntityType type, int index) {
/* 20 */     Pair pair = new Pair(type, Integer.valueOf(index));
/* 21 */     if (metadataRewrites.containsKey(pair)) {
/* 22 */       return Optional.of(metadataRewrites.get(pair));
/*    */     }
/*    */     
/* 25 */     return Optional.empty();
/*    */   }
/*    */   
/*    */   public static MetaIndex1_8to1_7_6_10 searchIndex(Entity1_10Types.EntityType type, int index) {
/* 29 */     Entity1_10Types.EntityType currentType = type;
/*    */     do {
/* 31 */       Optional<MetaIndex1_8to1_7_6_10> optMeta = getIndex(currentType, index);
/*    */       
/* 33 */       if (optMeta.isPresent()) {
/* 34 */         return optMeta.get();
/*    */       }
/*    */       
/* 37 */       currentType = currentType.getParent();
/* 38 */     } while (currentType != null);
/*    */     
/* 40 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\de\gerrygames\viarewind\protocol\protocol1_7_6_10to1_8\metadata\MetaIndex1_7_6_10to1_8.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */