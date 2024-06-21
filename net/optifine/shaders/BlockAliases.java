/*     */ package net.optifine.shaders;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.src.Config;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.optifine.config.ConnectedParser;
/*     */ import net.optifine.config.MatchBlock;
/*     */ import net.optifine.reflect.Reflector;
/*     */ import net.optifine.reflect.ReflectorForge;
/*     */ import net.optifine.shaders.config.MacroProcessor;
/*     */ import net.optifine.util.PropertiesOrdered;
/*     */ import net.optifine.util.StrUtils;
/*     */ 
/*     */ 
/*     */ public class BlockAliases
/*     */ {
/*  21 */   private static BlockAlias[][] blockAliases = (BlockAlias[][])null;
/*  22 */   private static PropertiesOrdered blockLayerPropertes = null;
/*     */   
/*     */   private static boolean updateOnResourcesReloaded;
/*     */   
/*     */   public static int getBlockAliasId(int blockId, int metadata) {
/*  27 */     if (blockAliases == null)
/*     */     {
/*  29 */       return blockId;
/*     */     }
/*  31 */     if (blockId >= 0 && blockId < blockAliases.length) {
/*     */       
/*  33 */       BlockAlias[] ablockalias = blockAliases[blockId];
/*     */       
/*  35 */       if (ablockalias == null)
/*     */       {
/*  37 */         return blockId;
/*     */       }
/*     */ 
/*     */       
/*  41 */       for (int i = 0; i < ablockalias.length; i++) {
/*     */         
/*  43 */         BlockAlias blockalias = ablockalias[i];
/*     */         
/*  45 */         if (blockalias.matches(blockId, metadata))
/*     */         {
/*  47 */           return blockalias.getBlockAliasId();
/*     */         }
/*     */       } 
/*     */       
/*  51 */       return blockId;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/*  56 */     return blockId;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static void resourcesReloaded() {
/*  62 */     if (updateOnResourcesReloaded) {
/*     */       
/*  64 */       updateOnResourcesReloaded = false;
/*  65 */       update(Shaders.getShaderPack());
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static void update(IShaderPack shaderPack) {
/*  71 */     reset();
/*     */     
/*  73 */     if (shaderPack != null)
/*     */     {
/*  75 */       if (Reflector.Loader_getActiveModList.exists() && Minecraft.getMinecraft().getResourcePackRepository() == null) {
/*     */         
/*  77 */         Config.dbg("[Shaders] Delayed loading of block mappings after resources are loaded");
/*  78 */         updateOnResourcesReloaded = true;
/*     */       }
/*     */       else {
/*     */         
/*  82 */         List<List<BlockAlias>> list = new ArrayList<>();
/*  83 */         String s = "/shaders/block.properties";
/*  84 */         InputStream inputstream = shaderPack.getResourceAsStream(s);
/*     */         
/*  86 */         if (inputstream != null)
/*     */         {
/*  88 */           loadBlockAliases(inputstream, s, list);
/*     */         }
/*     */         
/*  91 */         loadModBlockAliases(list);
/*     */         
/*  93 */         if (list.size() > 0)
/*     */         {
/*  95 */           blockAliases = toArrays(list);
/*     */         }
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private static void loadModBlockAliases(List<List<BlockAlias>> listBlockAliases) {
/* 103 */     String[] astring = ReflectorForge.getForgeModIds();
/*     */     
/* 105 */     for (int i = 0; i < astring.length; i++) {
/*     */       
/* 107 */       String s = astring[i];
/*     */ 
/*     */       
/*     */       try {
/* 111 */         ResourceLocation resourcelocation = new ResourceLocation(s, "shaders/block.properties");
/* 112 */         InputStream inputstream = Config.getResourceStream(resourcelocation);
/* 113 */         loadBlockAliases(inputstream, resourcelocation.toString(), listBlockAliases);
/*     */       }
/* 115 */       catch (IOException iOException) {}
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void loadBlockAliases(InputStream in, String path, List<List<BlockAlias>> listBlockAliases) {
/* 124 */     if (in != null) {
/*     */       
/*     */       try {
/*     */         
/* 128 */         in = MacroProcessor.process(in, path);
/* 129 */         PropertiesOrdered propertiesOrdered = new PropertiesOrdered();
/* 130 */         propertiesOrdered.load(in);
/* 131 */         in.close();
/* 132 */         Config.dbg("[Shaders] Parsing block mappings: " + path);
/* 133 */         ConnectedParser connectedparser = new ConnectedParser("Shaders");
/*     */         
/* 135 */         for (Object e : propertiesOrdered.keySet())
/*     */         {
/* 137 */           String s = (String)e;
/* 138 */           String s1 = propertiesOrdered.getProperty(s);
/*     */           
/* 140 */           if (s.startsWith("layer.")) {
/*     */             
/* 142 */             if (blockLayerPropertes == null)
/*     */             {
/* 144 */               blockLayerPropertes = new PropertiesOrdered();
/*     */             }
/*     */             
/* 147 */             blockLayerPropertes.put(s, s1);
/*     */             
/*     */             continue;
/*     */           } 
/* 151 */           String s2 = "block.";
/*     */           
/* 153 */           if (!s.startsWith(s2)) {
/*     */             
/* 155 */             Config.warn("[Shaders] Invalid block ID: " + s);
/*     */             
/*     */             continue;
/*     */           } 
/* 159 */           String s3 = StrUtils.removePrefix(s, s2);
/* 160 */           int i = Config.parseInt(s3, -1);
/*     */           
/* 162 */           if (i < 0) {
/*     */             
/* 164 */             Config.warn("[Shaders] Invalid block ID: " + s);
/*     */             
/*     */             continue;
/*     */           } 
/* 168 */           MatchBlock[] amatchblock = connectedparser.parseMatchBlocks(s1);
/*     */           
/* 170 */           if (amatchblock != null && amatchblock.length >= 1) {
/*     */             
/* 172 */             BlockAlias blockalias = new BlockAlias(i, amatchblock);
/* 173 */             addToList(listBlockAliases, blockalias);
/*     */             
/*     */             continue;
/*     */           } 
/* 177 */           Config.warn("[Shaders] Invalid block ID mapping: " + s + "=" + s1);
/*     */ 
/*     */         
/*     */         }
/*     */ 
/*     */       
/*     */       }
/* 184 */       catch (IOException var14) {
/*     */         
/* 186 */         Config.warn("[Shaders] Error reading: " + path);
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private static void addToList(List<List<BlockAlias>> blocksAliases, BlockAlias ba) {
/* 193 */     int[] aint = ba.getMatchBlockIds();
/*     */     
/* 195 */     for (int i = 0; i < aint.length; i++) {
/*     */       
/* 197 */       int j = aint[i];
/*     */       
/* 199 */       while (j >= blocksAliases.size())
/*     */       {
/* 201 */         blocksAliases.add(null);
/*     */       }
/*     */       
/* 204 */       List<BlockAlias> list = blocksAliases.get(j);
/*     */       
/* 206 */       if (list == null) {
/*     */         
/* 208 */         list = new ArrayList<>();
/* 209 */         blocksAliases.set(j, list);
/*     */       } 
/*     */       
/* 212 */       BlockAlias blockalias = new BlockAlias(ba.getBlockAliasId(), ba.getMatchBlocks(j));
/* 213 */       list.add(blockalias);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private static BlockAlias[][] toArrays(List<List<BlockAlias>> listBlocksAliases) {
/* 219 */     BlockAlias[][] ablockalias = new BlockAlias[listBlocksAliases.size()][];
/*     */     
/* 221 */     for (int i = 0; i < ablockalias.length; i++) {
/*     */       
/* 223 */       List<BlockAlias> list = listBlocksAliases.get(i);
/*     */       
/* 225 */       if (list != null)
/*     */       {
/* 227 */         ablockalias[i] = list.<BlockAlias>toArray(new BlockAlias[list.size()]);
/*     */       }
/*     */     } 
/*     */     
/* 231 */     return ablockalias;
/*     */   }
/*     */ 
/*     */   
/*     */   public static PropertiesOrdered getBlockLayerPropertes() {
/* 236 */     return blockLayerPropertes;
/*     */   }
/*     */ 
/*     */   
/*     */   public static void reset() {
/* 241 */     blockAliases = (BlockAlias[][])null;
/* 242 */     blockLayerPropertes = null;
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\optifine\shaders\BlockAliases.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */