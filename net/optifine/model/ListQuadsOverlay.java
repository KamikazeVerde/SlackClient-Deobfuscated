/*    */ package net.optifine.model;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.Arrays;
/*    */ import java.util.List;
/*    */ import net.minecraft.block.state.IBlockState;
/*    */ import net.minecraft.client.renderer.block.model.BakedQuad;
/*    */ import net.minecraft.init.Blocks;
/*    */ 
/*    */ public class ListQuadsOverlay
/*    */ {
/* 12 */   private List<BakedQuad> listQuads = new ArrayList<>();
/* 13 */   private List<IBlockState> listBlockStates = new ArrayList<>();
/* 14 */   private List<BakedQuad> listQuadsSingle = Arrays.asList(new BakedQuad[1]);
/*    */ 
/*    */   
/*    */   public void addQuad(BakedQuad quad, IBlockState blockState) {
/* 18 */     if (quad != null) {
/*    */       
/* 20 */       this.listQuads.add(quad);
/* 21 */       this.listBlockStates.add(blockState);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public int size() {
/* 27 */     return this.listQuads.size();
/*    */   }
/*    */ 
/*    */   
/*    */   public BakedQuad getQuad(int index) {
/* 32 */     return this.listQuads.get(index);
/*    */   }
/*    */ 
/*    */   
/*    */   public IBlockState getBlockState(int index) {
/* 37 */     return (index >= 0 && index < this.listBlockStates.size()) ? this.listBlockStates.get(index) : Blocks.air.getDefaultState();
/*    */   }
/*    */ 
/*    */   
/*    */   public List<BakedQuad> getListQuadsSingle(BakedQuad quad) {
/* 42 */     this.listQuadsSingle.set(0, quad);
/* 43 */     return this.listQuadsSingle;
/*    */   }
/*    */ 
/*    */   
/*    */   public void clear() {
/* 48 */     this.listQuads.clear();
/* 49 */     this.listBlockStates.clear();
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\optifine\model\ListQuadsOverlay.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */