/*    */ package com.viaversion.viaversion.api.minecraft;
/*    */ 
/*    */ import com.viaversion.viaversion.api.connection.StorableObject;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class WorldIdentifiers
/*    */   implements StorableObject
/*    */ {
/*    */   public static final String OVERWORLD_DEFAULT = "minecraft:overworld";
/*    */   public static final String NETHER_DEFAULT = "minecraft:the_nether";
/*    */   public static final String END_DEFAULT = "minecraft:the_end";
/*    */   private final String overworld;
/*    */   private final String nether;
/*    */   private final String end;
/*    */   
/*    */   public WorldIdentifiers(String overworld) {
/* 37 */     this(overworld, "minecraft:the_nether", "minecraft:the_end");
/*    */   }
/*    */   
/*    */   public WorldIdentifiers(String overworld, String nether, String end) {
/* 41 */     this.overworld = overworld;
/* 42 */     this.nether = nether;
/* 43 */     this.end = end;
/*    */   }
/*    */   
/*    */   public String overworld() {
/* 47 */     return this.overworld;
/*    */   }
/*    */   
/*    */   public String nether() {
/* 51 */     return this.nether;
/*    */   }
/*    */   
/*    */   public String end() {
/* 55 */     return this.end;
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\api\minecraft\WorldIdentifiers.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */