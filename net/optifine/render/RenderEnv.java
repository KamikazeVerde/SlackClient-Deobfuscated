/*     */ package net.optifine.render;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.BitSet;
/*     */ import java.util.List;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.state.BlockStateBase;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.client.renderer.BlockModelRenderer;
/*     */ import net.minecraft.client.renderer.RegionRenderCacheBuilder;
/*     */ import net.minecraft.client.renderer.block.model.BakedQuad;
/*     */ import net.minecraft.src.Config;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.EnumWorldBlockLayer;
/*     */ import net.optifine.BlockPosM;
/*     */ import net.optifine.model.ListQuadsOverlay;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class RenderEnv
/*     */ {
/*     */   private IBlockState blockState;
/*     */   private BlockPos blockPos;
/*  25 */   private int blockId = -1;
/*  26 */   private int metadata = -1;
/*  27 */   private int breakingAnimation = -1;
/*  28 */   private int smartLeaves = -1;
/*  29 */   private float[] quadBounds = new float[EnumFacing.VALUES.length * 2];
/*  30 */   private BitSet boundsFlags = new BitSet(3);
/*  31 */   private BlockModelRenderer.AmbientOcclusionFace aoFace = new BlockModelRenderer.AmbientOcclusionFace();
/*  32 */   private BlockPosM colorizerBlockPosM = null;
/*  33 */   private boolean[] borderFlags = null;
/*  34 */   private boolean[] borderFlags2 = null;
/*  35 */   private boolean[] borderFlags3 = null;
/*  36 */   private EnumFacing[] borderDirections = null;
/*  37 */   private List<BakedQuad> listQuadsCustomizer = new ArrayList<>();
/*  38 */   private List<BakedQuad> listQuadsCtmMultipass = new ArrayList<>();
/*  39 */   private BakedQuad[] arrayQuadsCtm1 = new BakedQuad[1];
/*  40 */   private BakedQuad[] arrayQuadsCtm2 = new BakedQuad[2];
/*  41 */   private BakedQuad[] arrayQuadsCtm3 = new BakedQuad[3];
/*  42 */   private BakedQuad[] arrayQuadsCtm4 = new BakedQuad[4];
/*  43 */   private RegionRenderCacheBuilder regionRenderCacheBuilder = null;
/*  44 */   private ListQuadsOverlay[] listsQuadsOverlay = new ListQuadsOverlay[(EnumWorldBlockLayer.values()).length];
/*     */   
/*     */   private boolean overlaysRendered = false;
/*     */   private static final int UNKNOWN = -1;
/*     */   private static final int FALSE = 0;
/*     */   private static final int TRUE = 1;
/*     */   
/*     */   public RenderEnv(IBlockState blockState, BlockPos blockPos) {
/*  52 */     this.blockState = blockState;
/*  53 */     this.blockPos = blockPos;
/*     */   }
/*     */ 
/*     */   
/*     */   public void reset(IBlockState blockStateIn, BlockPos blockPosIn) {
/*  58 */     if (this.blockState != blockStateIn || this.blockPos != blockPosIn) {
/*     */       
/*  60 */       this.blockState = blockStateIn;
/*  61 */       this.blockPos = blockPosIn;
/*  62 */       this.blockId = -1;
/*  63 */       this.metadata = -1;
/*  64 */       this.breakingAnimation = -1;
/*  65 */       this.smartLeaves = -1;
/*  66 */       this.boundsFlags.clear();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public int getBlockId() {
/*  72 */     if (this.blockId < 0)
/*     */     {
/*  74 */       if (this.blockState instanceof BlockStateBase) {
/*     */         
/*  76 */         BlockStateBase blockstatebase = (BlockStateBase)this.blockState;
/*  77 */         this.blockId = blockstatebase.getBlockId();
/*     */       }
/*     */       else {
/*     */         
/*  81 */         this.blockId = Block.getIdFromBlock(this.blockState.getBlock());
/*     */       } 
/*     */     }
/*     */     
/*  85 */     return this.blockId;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getMetadata() {
/*  90 */     if (this.metadata < 0)
/*     */     {
/*  92 */       if (this.blockState instanceof BlockStateBase) {
/*     */         
/*  94 */         BlockStateBase blockstatebase = (BlockStateBase)this.blockState;
/*  95 */         this.metadata = blockstatebase.getMetadata();
/*     */       }
/*     */       else {
/*     */         
/*  99 */         this.metadata = this.blockState.getBlock().getMetaFromState(this.blockState);
/*     */       } 
/*     */     }
/*     */     
/* 103 */     return this.metadata;
/*     */   }
/*     */ 
/*     */   
/*     */   public float[] getQuadBounds() {
/* 108 */     return this.quadBounds;
/*     */   }
/*     */ 
/*     */   
/*     */   public BitSet getBoundsFlags() {
/* 113 */     return this.boundsFlags;
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockModelRenderer.AmbientOcclusionFace getAoFace() {
/* 118 */     return this.aoFace;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isBreakingAnimation(List listQuads) {
/* 123 */     if (this.breakingAnimation == -1 && listQuads.size() > 0)
/*     */     {
/* 125 */       if (listQuads.get(0) instanceof net.minecraft.client.renderer.block.model.BreakingFour) {
/*     */         
/* 127 */         this.breakingAnimation = 1;
/*     */       }
/*     */       else {
/*     */         
/* 131 */         this.breakingAnimation = 0;
/*     */       } 
/*     */     }
/*     */     
/* 135 */     return (this.breakingAnimation == 1);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isBreakingAnimation(BakedQuad quad) {
/* 140 */     if (this.breakingAnimation < 0)
/*     */     {
/* 142 */       if (quad instanceof net.minecraft.client.renderer.block.model.BreakingFour) {
/*     */         
/* 144 */         this.breakingAnimation = 1;
/*     */       }
/*     */       else {
/*     */         
/* 148 */         this.breakingAnimation = 0;
/*     */       } 
/*     */     }
/*     */     
/* 152 */     return (this.breakingAnimation == 1);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isBreakingAnimation() {
/* 157 */     return (this.breakingAnimation == 1);
/*     */   }
/*     */ 
/*     */   
/*     */   public IBlockState getBlockState() {
/* 162 */     return this.blockState;
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockPosM getColorizerBlockPosM() {
/* 167 */     if (this.colorizerBlockPosM == null)
/*     */     {
/* 169 */       this.colorizerBlockPosM = new BlockPosM(0, 0, 0);
/*     */     }
/*     */     
/* 172 */     return this.colorizerBlockPosM;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean[] getBorderFlags() {
/* 177 */     if (this.borderFlags == null)
/*     */     {
/* 179 */       this.borderFlags = new boolean[4];
/*     */     }
/*     */     
/* 182 */     return this.borderFlags;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean[] getBorderFlags2() {
/* 187 */     if (this.borderFlags2 == null)
/*     */     {
/* 189 */       this.borderFlags2 = new boolean[4];
/*     */     }
/*     */     
/* 192 */     return this.borderFlags2;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean[] getBorderFlags3() {
/* 197 */     if (this.borderFlags3 == null)
/*     */     {
/* 199 */       this.borderFlags3 = new boolean[4];
/*     */     }
/*     */     
/* 202 */     return this.borderFlags3;
/*     */   }
/*     */ 
/*     */   
/*     */   public EnumFacing[] getBorderDirections() {
/* 207 */     if (this.borderDirections == null)
/*     */     {
/* 209 */       this.borderDirections = new EnumFacing[4];
/*     */     }
/*     */     
/* 212 */     return this.borderDirections;
/*     */   }
/*     */ 
/*     */   
/*     */   public EnumFacing[] getBorderDirections(EnumFacing dir0, EnumFacing dir1, EnumFacing dir2, EnumFacing dir3) {
/* 217 */     EnumFacing[] aenumfacing = getBorderDirections();
/* 218 */     aenumfacing[0] = dir0;
/* 219 */     aenumfacing[1] = dir1;
/* 220 */     aenumfacing[2] = dir2;
/* 221 */     aenumfacing[3] = dir3;
/* 222 */     return aenumfacing;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isSmartLeaves() {
/* 227 */     if (this.smartLeaves == -1)
/*     */     {
/* 229 */       if (Config.isTreesSmart() && this.blockState.getBlock() instanceof net.minecraft.block.BlockLeaves) {
/*     */         
/* 231 */         this.smartLeaves = 1;
/*     */       }
/*     */       else {
/*     */         
/* 235 */         this.smartLeaves = 0;
/*     */       } 
/*     */     }
/*     */     
/* 239 */     return (this.smartLeaves == 1);
/*     */   }
/*     */ 
/*     */   
/*     */   public List<BakedQuad> getListQuadsCustomizer() {
/* 244 */     return this.listQuadsCustomizer;
/*     */   }
/*     */ 
/*     */   
/*     */   public BakedQuad[] getArrayQuadsCtm(BakedQuad quad) {
/* 249 */     this.arrayQuadsCtm1[0] = quad;
/* 250 */     return this.arrayQuadsCtm1;
/*     */   }
/*     */ 
/*     */   
/*     */   public BakedQuad[] getArrayQuadsCtm(BakedQuad quad0, BakedQuad quad1) {
/* 255 */     this.arrayQuadsCtm2[0] = quad0;
/* 256 */     this.arrayQuadsCtm2[1] = quad1;
/* 257 */     return this.arrayQuadsCtm2;
/*     */   }
/*     */ 
/*     */   
/*     */   public BakedQuad[] getArrayQuadsCtm(BakedQuad quad0, BakedQuad quad1, BakedQuad quad2) {
/* 262 */     this.arrayQuadsCtm3[0] = quad0;
/* 263 */     this.arrayQuadsCtm3[1] = quad1;
/* 264 */     this.arrayQuadsCtm3[2] = quad2;
/* 265 */     return this.arrayQuadsCtm3;
/*     */   }
/*     */ 
/*     */   
/*     */   public BakedQuad[] getArrayQuadsCtm(BakedQuad quad0, BakedQuad quad1, BakedQuad quad2, BakedQuad quad3) {
/* 270 */     this.arrayQuadsCtm4[0] = quad0;
/* 271 */     this.arrayQuadsCtm4[1] = quad1;
/* 272 */     this.arrayQuadsCtm4[2] = quad2;
/* 273 */     this.arrayQuadsCtm4[3] = quad3;
/* 274 */     return this.arrayQuadsCtm4;
/*     */   }
/*     */ 
/*     */   
/*     */   public List<BakedQuad> getListQuadsCtmMultipass(BakedQuad[] quads) {
/* 279 */     this.listQuadsCtmMultipass.clear();
/*     */     
/* 281 */     if (quads != null)
/*     */     {
/* 283 */       for (int i = 0; i < quads.length; i++) {
/*     */         
/* 285 */         BakedQuad bakedquad = quads[i];
/* 286 */         this.listQuadsCtmMultipass.add(bakedquad);
/*     */       } 
/*     */     }
/*     */     
/* 290 */     return this.listQuadsCtmMultipass;
/*     */   }
/*     */ 
/*     */   
/*     */   public RegionRenderCacheBuilder getRegionRenderCacheBuilder() {
/* 295 */     return this.regionRenderCacheBuilder;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setRegionRenderCacheBuilder(RegionRenderCacheBuilder regionRenderCacheBuilder) {
/* 300 */     this.regionRenderCacheBuilder = regionRenderCacheBuilder;
/*     */   }
/*     */ 
/*     */   
/*     */   public ListQuadsOverlay getListQuadsOverlay(EnumWorldBlockLayer layer) {
/* 305 */     ListQuadsOverlay listquadsoverlay = this.listsQuadsOverlay[layer.ordinal()];
/*     */     
/* 307 */     if (listquadsoverlay == null) {
/*     */       
/* 309 */       listquadsoverlay = new ListQuadsOverlay();
/* 310 */       this.listsQuadsOverlay[layer.ordinal()] = listquadsoverlay;
/*     */     } 
/*     */     
/* 313 */     return listquadsoverlay;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isOverlaysRendered() {
/* 318 */     return this.overlaysRendered;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setOverlaysRendered(boolean overlaysRendered) {
/* 323 */     this.overlaysRendered = overlaysRendered;
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\optifine\render\RenderEnv.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */