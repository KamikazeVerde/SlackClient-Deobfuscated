/*    */ package net.minecraft.block.material;
/*    */ 
/*    */ public class MaterialTransparent
/*    */   extends Material
/*    */ {
/*    */   public MaterialTransparent(MapColor color) {
/*  7 */     super(color);
/*  8 */     setReplaceable();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isSolid() {
/* 16 */     return false;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean blocksLight() {
/* 24 */     return false;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean blocksMovement() {
/* 32 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\minecraft\block\material\MaterialTransparent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */