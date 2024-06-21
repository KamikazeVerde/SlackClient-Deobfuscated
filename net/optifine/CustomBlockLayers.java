/*     */ package net.optifine;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.Properties;
/*     */ import net.minecraft.block.state.BlockStateBase;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.src.Config;
/*     */ import net.minecraft.util.EnumWorldBlockLayer;
/*     */ import net.optifine.config.ConnectedParser;
/*     */ import net.optifine.config.MatchBlock;
/*     */ import net.optifine.shaders.BlockAliases;
/*     */ import net.optifine.util.PropertiesOrdered;
/*     */ import net.optifine.util.ResUtils;
/*     */ 
/*     */ public class CustomBlockLayers
/*     */ {
/*  18 */   private static EnumWorldBlockLayer[] renderLayers = null;
/*     */   
/*     */   public static boolean active = false;
/*     */   
/*     */   public static EnumWorldBlockLayer getRenderLayer(IBlockState blockState) {
/*  23 */     if (renderLayers == null)
/*     */     {
/*  25 */       return null;
/*     */     }
/*  27 */     if (blockState.getBlock().isOpaqueCube())
/*     */     {
/*  29 */       return null;
/*     */     }
/*  31 */     if (!(blockState instanceof BlockStateBase))
/*     */     {
/*  33 */       return null;
/*     */     }
/*     */ 
/*     */     
/*  37 */     BlockStateBase blockstatebase = (BlockStateBase)blockState;
/*  38 */     int i = blockstatebase.getBlockId();
/*  39 */     return (i > 0 && i < renderLayers.length) ? renderLayers[i] : null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static void update() {
/*  45 */     renderLayers = null;
/*  46 */     active = false;
/*  47 */     List<EnumWorldBlockLayer> list = new ArrayList<>();
/*  48 */     String s = "optifine/block.properties";
/*  49 */     Properties properties = ResUtils.readProperties(s, "CustomBlockLayers");
/*     */     
/*  51 */     if (properties != null)
/*     */     {
/*  53 */       readLayers(s, properties, list);
/*     */     }
/*     */     
/*  56 */     if (Config.isShaders()) {
/*     */       
/*  58 */       PropertiesOrdered propertiesordered = BlockAliases.getBlockLayerPropertes();
/*     */       
/*  60 */       if (propertiesordered != null) {
/*     */         
/*  62 */         String s1 = "shaders/block.properties";
/*  63 */         readLayers(s1, (Properties)propertiesordered, list);
/*     */       } 
/*     */     } 
/*     */     
/*  67 */     if (!list.isEmpty()) {
/*     */       
/*  69 */       renderLayers = list.<EnumWorldBlockLayer>toArray(new EnumWorldBlockLayer[list.size()]);
/*  70 */       active = true;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private static void readLayers(String pathProps, Properties props, List<EnumWorldBlockLayer> list) {
/*  76 */     Config.dbg("CustomBlockLayers: " + pathProps);
/*  77 */     readLayer("solid", EnumWorldBlockLayer.SOLID, props, list);
/*  78 */     readLayer("cutout", EnumWorldBlockLayer.CUTOUT, props, list);
/*  79 */     readLayer("cutout_mipped", EnumWorldBlockLayer.CUTOUT_MIPPED, props, list);
/*  80 */     readLayer("translucent", EnumWorldBlockLayer.TRANSLUCENT, props, list);
/*     */   }
/*     */ 
/*     */   
/*     */   private static void readLayer(String name, EnumWorldBlockLayer layer, Properties props, List<EnumWorldBlockLayer> listLayers) {
/*  85 */     String s = "layer." + name;
/*  86 */     String s1 = props.getProperty(s);
/*     */     
/*  88 */     if (s1 != null) {
/*     */       
/*  90 */       ConnectedParser connectedparser = new ConnectedParser("CustomBlockLayers");
/*  91 */       MatchBlock[] amatchblock = connectedparser.parseMatchBlocks(s1);
/*     */       
/*  93 */       if (amatchblock != null)
/*     */       {
/*  95 */         for (int i = 0; i < amatchblock.length; i++) {
/*     */           
/*  97 */           MatchBlock matchblock = amatchblock[i];
/*  98 */           int j = matchblock.getBlockId();
/*     */           
/* 100 */           if (j > 0) {
/*     */             
/* 102 */             while (listLayers.size() < j + 1)
/*     */             {
/* 104 */               listLayers.add(null);
/*     */             }
/*     */             
/* 107 */             if (listLayers.get(j) != null)
/*     */             {
/* 109 */               Config.warn("CustomBlockLayers: Block layer is already set, block: " + j + ", layer: " + name);
/*     */             }
/*     */             
/* 112 */             listLayers.set(j, layer);
/*     */           } 
/*     */         } 
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean isActive() {
/* 121 */     return active;
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\optifine\CustomBlockLayers.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */