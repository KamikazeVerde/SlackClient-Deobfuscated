/*    */ package net.minecraft.block;
/*    */ 
/*    */ import java.util.Random;
/*    */ import net.minecraft.block.material.MapColor;
/*    */ import net.minecraft.block.state.IBlockState;
/*    */ import net.minecraft.init.Items;
/*    */ import net.minecraft.item.Item;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class BlockGravel
/*    */   extends BlockFalling
/*    */ {
/*    */   public Item getItemDropped(IBlockState state, Random rand, int fortune) {
/* 18 */     if (fortune > 3)
/*    */     {
/* 20 */       fortune = 3;
/*    */     }
/*    */     
/* 23 */     return (rand.nextInt(10 - fortune * 3) == 0) ? Items.flint : Item.getItemFromBlock(this);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public MapColor getMapColor(IBlockState state) {
/* 31 */     return MapColor.stoneColor;
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\minecraft\block\BlockGravel.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */