/*    */ package net.minecraft.util;
/*    */ 
/*    */ import java.util.Collection;
/*    */ import java.util.Random;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class WeightedRandom
/*    */ {
/*    */   public static int getTotalWeight(Collection<? extends Item> collection) {
/* 15 */     int i = 0;
/*    */     
/* 17 */     for (Item weightedrandom$item : collection)
/*    */     {
/* 19 */       i += weightedrandom$item.itemWeight;
/*    */     }
/*    */     
/* 22 */     return i;
/*    */   }
/*    */ 
/*    */   
/*    */   public static <T extends Item> T getRandomItem(Random random, Collection<T> collection, int totalWeight) {
/* 27 */     if (totalWeight <= 0)
/*    */     {
/* 29 */       throw new IllegalArgumentException();
/*    */     }
/*    */ 
/*    */     
/* 33 */     int i = random.nextInt(totalWeight);
/* 34 */     return getRandomItem(collection, i);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public static <T extends Item> T getRandomItem(Collection<T> collection, int weight) {
/* 40 */     for (Item item : collection) {
/*    */       
/* 42 */       weight -= item.itemWeight;
/*    */       
/* 44 */       if (weight < 0)
/*    */       {
/* 46 */         return (T)item;
/*    */       }
/*    */     } 
/*    */     
/* 50 */     return null;
/*    */   }
/*    */ 
/*    */   
/*    */   public static <T extends Item> T getRandomItem(Random random, Collection<T> collection) {
/* 55 */     return getRandomItem(random, collection, getTotalWeight(collection));
/*    */   }
/*    */ 
/*    */   
/*    */   public static class Item
/*    */   {
/*    */     protected int itemWeight;
/*    */     
/*    */     public Item(int itemWeightIn) {
/* 64 */       this.itemWeight = itemWeightIn;
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\minecraf\\util\WeightedRandom.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */