/*     */ package com.viaversion.viaversion.protocols.protocol1_13to1_12_2.storage;
/*     */ 
/*     */ import com.viaversion.viaversion.api.connection.StorableObject;
/*     */ import com.viaversion.viaversion.api.minecraft.Position;
/*     */ import com.viaversion.viaversion.libs.fastutil.ints.IntOpenHashSet;
/*     */ import com.viaversion.viaversion.libs.fastutil.ints.IntSet;
/*     */ import com.viaversion.viaversion.libs.flare.SyncMap;
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
/*     */ public class BlockStorage
/*     */   implements StorableObject
/*     */ {
/*  29 */   private static final IntSet WHITELIST = (IntSet)new IntOpenHashSet(46, 0.99F);
/*  30 */   private final Map<Position, ReplacementData> blocks = (Map<Position, ReplacementData>)SyncMap.hashmap();
/*     */ 
/*     */   
/*     */   static {
/*  34 */     WHITELIST.add(5266);
/*     */     
/*     */     int i;
/*  37 */     for (i = 0; i < 16; i++) {
/*  38 */       WHITELIST.add(972 + i);
/*     */     }
/*     */ 
/*     */     
/*  42 */     for (i = 0; i < 20; i++) {
/*  43 */       WHITELIST.add(6854 + i);
/*     */     }
/*     */ 
/*     */     
/*  47 */     for (i = 0; i < 4; i++) {
/*  48 */       WHITELIST.add(7110 + i);
/*     */     }
/*     */ 
/*     */     
/*  52 */     for (i = 0; i < 5; i++) {
/*  53 */       WHITELIST.add(5447 + i);
/*     */     }
/*     */   }
/*     */   
/*     */   public void store(Position position, int block) {
/*  58 */     store(position, block, -1);
/*     */   }
/*     */   
/*     */   public void store(Position position, int block, int replacementId) {
/*  62 */     if (!WHITELIST.contains(block)) {
/*     */       return;
/*     */     }
/*  65 */     this.blocks.put(position, new ReplacementData(block, replacementId));
/*     */   }
/*     */   
/*     */   public boolean isWelcome(int block) {
/*  69 */     return WHITELIST.contains(block);
/*     */   }
/*     */   
/*     */   public boolean contains(Position position) {
/*  73 */     return this.blocks.containsKey(position);
/*     */   }
/*     */   
/*     */   public ReplacementData get(Position position) {
/*  77 */     return this.blocks.get(position);
/*     */   }
/*     */   
/*     */   public ReplacementData remove(Position position) {
/*  81 */     return this.blocks.remove(position);
/*     */   }
/*     */   
/*     */   public static final class ReplacementData {
/*     */     private final int original;
/*     */     private int replacement;
/*     */     
/*     */     public ReplacementData(int original, int replacement) {
/*  89 */       this.original = original;
/*  90 */       this.replacement = replacement;
/*     */     }
/*     */     
/*     */     public int getOriginal() {
/*  94 */       return this.original;
/*     */     }
/*     */     
/*     */     public int getReplacement() {
/*  98 */       return this.replacement;
/*     */     }
/*     */     
/*     */     public void setReplacement(int replacement) {
/* 102 */       this.replacement = replacement;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\protocols\protocol1_13to1_12_2\storage\BlockStorage.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */