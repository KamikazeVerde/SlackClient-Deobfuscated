/*     */ package net.minecraft.client.renderer.chunk;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import java.util.BitSet;
/*     */ import java.util.List;
/*     */ import net.minecraft.client.renderer.WorldRenderer;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.EnumWorldBlockLayer;
/*     */ 
/*     */ public class CompiledChunk
/*     */ {
/*  13 */   public static final CompiledChunk DUMMY = new CompiledChunk()
/*     */     {
/*     */       protected void setLayerUsed(EnumWorldBlockLayer layer)
/*     */       {
/*  17 */         throw new UnsupportedOperationException();
/*     */       }
/*     */       
/*     */       public void setLayerStarted(EnumWorldBlockLayer layer) {
/*  21 */         throw new UnsupportedOperationException();
/*     */       }
/*     */       
/*     */       public boolean isVisible(EnumFacing facing, EnumFacing facing2) {
/*  25 */         return false;
/*     */       }
/*     */       
/*     */       public void setAnimatedSprites(EnumWorldBlockLayer p_setAnimatedSprites_1_, BitSet p_setAnimatedSprites_2_) {
/*  29 */         throw new UnsupportedOperationException();
/*     */       }
/*     */     };
/*  32 */   private final boolean[] layersUsed = new boolean[RenderChunk.ENUM_WORLD_BLOCK_LAYERS.length];
/*  33 */   private final boolean[] layersStarted = new boolean[RenderChunk.ENUM_WORLD_BLOCK_LAYERS.length];
/*     */   private boolean empty = true;
/*  35 */   private final List<TileEntity> tileEntities = Lists.newArrayList();
/*  36 */   private SetVisibility setVisibility = new SetVisibility();
/*     */   private WorldRenderer.State state;
/*  38 */   private BitSet[] animatedSprites = new BitSet[RenderChunk.ENUM_WORLD_BLOCK_LAYERS.length];
/*     */ 
/*     */   
/*     */   public boolean isEmpty() {
/*  42 */     return this.empty;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void setLayerUsed(EnumWorldBlockLayer layer) {
/*  47 */     this.empty = false;
/*  48 */     this.layersUsed[layer.ordinal()] = true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isLayerEmpty(EnumWorldBlockLayer layer) {
/*  53 */     return !this.layersUsed[layer.ordinal()];
/*     */   }
/*     */ 
/*     */   
/*     */   public void setLayerStarted(EnumWorldBlockLayer layer) {
/*  58 */     this.layersStarted[layer.ordinal()] = true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isLayerStarted(EnumWorldBlockLayer layer) {
/*  63 */     return this.layersStarted[layer.ordinal()];
/*     */   }
/*     */ 
/*     */   
/*     */   public List<TileEntity> getTileEntities() {
/*  68 */     return this.tileEntities;
/*     */   }
/*     */ 
/*     */   
/*     */   public void addTileEntity(TileEntity tileEntityIn) {
/*  73 */     this.tileEntities.add(tileEntityIn);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isVisible(EnumFacing facing, EnumFacing facing2) {
/*  78 */     return this.setVisibility.isVisible(facing, facing2);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setVisibility(SetVisibility visibility) {
/*  83 */     this.setVisibility = visibility;
/*     */   }
/*     */ 
/*     */   
/*     */   public WorldRenderer.State getState() {
/*  88 */     return this.state;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setState(WorldRenderer.State stateIn) {
/*  93 */     this.state = stateIn;
/*     */   }
/*     */ 
/*     */   
/*     */   public BitSet getAnimatedSprites(EnumWorldBlockLayer p_getAnimatedSprites_1_) {
/*  98 */     return this.animatedSprites[p_getAnimatedSprites_1_.ordinal()];
/*     */   }
/*     */ 
/*     */   
/*     */   public void setAnimatedSprites(EnumWorldBlockLayer p_setAnimatedSprites_1_, BitSet p_setAnimatedSprites_2_) {
/* 103 */     this.animatedSprites[p_setAnimatedSprites_1_.ordinal()] = p_setAnimatedSprites_2_;
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\minecraft\client\renderer\chunk\CompiledChunk.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */