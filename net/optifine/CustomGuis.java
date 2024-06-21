/*     */ package net.optifine;
/*     */ 
/*     */ import java.io.FileNotFoundException;
/*     */ import java.io.InputStream;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.Calendar;
/*     */ import java.util.List;
/*     */ import java.util.Properties;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.gui.GuiScreen;
/*     */ import net.minecraft.client.multiplayer.WorldClient;
/*     */ import net.minecraft.client.resources.IResourcePack;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.src.Config;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.optifine.override.PlayerControllerOF;
/*     */ import net.optifine.util.PropertiesOrdered;
/*     */ import net.optifine.util.ResUtils;
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
/*     */ public class CustomGuis
/*     */ {
/*  38 */   private static Minecraft mc = Config.getMinecraft();
/*  39 */   private static PlayerControllerOF playerControllerOF = null;
/*  40 */   private static CustomGuiProperties[][] guiProperties = (CustomGuiProperties[][])null;
/*  41 */   public static boolean isChristmas = isChristmas();
/*     */ 
/*     */   
/*     */   public static ResourceLocation getTextureLocation(ResourceLocation loc) {
/*  45 */     if (guiProperties == null)
/*     */     {
/*  47 */       return loc;
/*     */     }
/*     */ 
/*     */     
/*  51 */     GuiScreen guiscreen = mc.currentScreen;
/*     */     
/*  53 */     if (!(guiscreen instanceof net.minecraft.client.gui.inventory.GuiContainer))
/*     */     {
/*  55 */       return loc;
/*     */     }
/*  57 */     if (loc.getResourceDomain().equals("minecraft") && loc.getResourcePath().startsWith("textures/gui/")) {
/*     */       
/*  59 */       if (playerControllerOF == null)
/*     */       {
/*  61 */         return loc;
/*     */       }
/*     */ 
/*     */       
/*  65 */       WorldClient worldClient = mc.theWorld;
/*     */       
/*  67 */       if (worldClient == null)
/*     */       {
/*  69 */         return loc;
/*     */       }
/*  71 */       if (guiscreen instanceof net.minecraft.client.gui.inventory.GuiContainerCreative)
/*     */       {
/*  73 */         return getTexturePos(CustomGuiProperties.EnumContainer.CREATIVE, mc.thePlayer.getPosition(), (IBlockAccess)worldClient, loc, guiscreen);
/*     */       }
/*  75 */       if (guiscreen instanceof net.minecraft.client.gui.inventory.GuiInventory)
/*     */       {
/*  77 */         return getTexturePos(CustomGuiProperties.EnumContainer.INVENTORY, mc.thePlayer.getPosition(), (IBlockAccess)worldClient, loc, guiscreen);
/*     */       }
/*     */ 
/*     */       
/*  81 */       BlockPos blockpos = playerControllerOF.getLastClickBlockPos();
/*     */       
/*  83 */       if (blockpos != null) {
/*     */         
/*  85 */         if (guiscreen instanceof net.minecraft.client.gui.GuiRepair)
/*     */         {
/*  87 */           return getTexturePos(CustomGuiProperties.EnumContainer.ANVIL, blockpos, (IBlockAccess)worldClient, loc, guiscreen);
/*     */         }
/*     */         
/*  90 */         if (guiscreen instanceof net.minecraft.client.gui.inventory.GuiBeacon)
/*     */         {
/*  92 */           return getTexturePos(CustomGuiProperties.EnumContainer.BEACON, blockpos, (IBlockAccess)worldClient, loc, guiscreen);
/*     */         }
/*     */         
/*  95 */         if (guiscreen instanceof net.minecraft.client.gui.inventory.GuiBrewingStand)
/*     */         {
/*  97 */           return getTexturePos(CustomGuiProperties.EnumContainer.BREWING_STAND, blockpos, (IBlockAccess)worldClient, loc, guiscreen);
/*     */         }
/*     */         
/* 100 */         if (guiscreen instanceof net.minecraft.client.gui.inventory.GuiChest)
/*     */         {
/* 102 */           return getTexturePos(CustomGuiProperties.EnumContainer.CHEST, blockpos, (IBlockAccess)worldClient, loc, guiscreen);
/*     */         }
/*     */         
/* 105 */         if (guiscreen instanceof net.minecraft.client.gui.inventory.GuiCrafting)
/*     */         {
/* 107 */           return getTexturePos(CustomGuiProperties.EnumContainer.CRAFTING, blockpos, (IBlockAccess)worldClient, loc, guiscreen);
/*     */         }
/*     */         
/* 110 */         if (guiscreen instanceof net.minecraft.client.gui.inventory.GuiDispenser)
/*     */         {
/* 112 */           return getTexturePos(CustomGuiProperties.EnumContainer.DISPENSER, blockpos, (IBlockAccess)worldClient, loc, guiscreen);
/*     */         }
/*     */         
/* 115 */         if (guiscreen instanceof net.minecraft.client.gui.GuiEnchantment)
/*     */         {
/* 117 */           return getTexturePos(CustomGuiProperties.EnumContainer.ENCHANTMENT, blockpos, (IBlockAccess)worldClient, loc, guiscreen);
/*     */         }
/*     */         
/* 120 */         if (guiscreen instanceof net.minecraft.client.gui.inventory.GuiFurnace)
/*     */         {
/* 122 */           return getTexturePos(CustomGuiProperties.EnumContainer.FURNACE, blockpos, (IBlockAccess)worldClient, loc, guiscreen);
/*     */         }
/*     */         
/* 125 */         if (guiscreen instanceof net.minecraft.client.gui.GuiHopper)
/*     */         {
/* 127 */           return getTexturePos(CustomGuiProperties.EnumContainer.HOPPER, blockpos, (IBlockAccess)worldClient, loc, guiscreen);
/*     */         }
/*     */       } 
/*     */       
/* 131 */       Entity entity = playerControllerOF.getLastClickEntity();
/*     */       
/* 133 */       if (entity != null) {
/*     */         
/* 135 */         if (guiscreen instanceof net.minecraft.client.gui.inventory.GuiScreenHorseInventory)
/*     */         {
/* 137 */           return getTextureEntity(CustomGuiProperties.EnumContainer.HORSE, entity, (IBlockAccess)worldClient, loc);
/*     */         }
/*     */         
/* 140 */         if (guiscreen instanceof net.minecraft.client.gui.GuiMerchant)
/*     */         {
/* 142 */           return getTextureEntity(CustomGuiProperties.EnumContainer.VILLAGER, entity, (IBlockAccess)worldClient, loc);
/*     */         }
/*     */       } 
/*     */       
/* 146 */       return loc;
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 152 */     return loc;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static ResourceLocation getTexturePos(CustomGuiProperties.EnumContainer container, BlockPos pos, IBlockAccess blockAccess, ResourceLocation loc, GuiScreen screen) {
/* 159 */     CustomGuiProperties[] acustomguiproperties = guiProperties[container.ordinal()];
/*     */     
/* 161 */     if (acustomguiproperties == null)
/*     */     {
/* 163 */       return loc;
/*     */     }
/*     */ 
/*     */     
/* 167 */     for (int i = 0; i < acustomguiproperties.length; i++) {
/*     */       
/* 169 */       CustomGuiProperties customguiproperties = acustomguiproperties[i];
/*     */       
/* 171 */       if (customguiproperties.matchesPos(container, pos, blockAccess, screen))
/*     */       {
/* 173 */         return customguiproperties.getTextureLocation(loc);
/*     */       }
/*     */     } 
/*     */     
/* 177 */     return loc;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static ResourceLocation getTextureEntity(CustomGuiProperties.EnumContainer container, Entity entity, IBlockAccess blockAccess, ResourceLocation loc) {
/* 183 */     CustomGuiProperties[] acustomguiproperties = guiProperties[container.ordinal()];
/*     */     
/* 185 */     if (acustomguiproperties == null)
/*     */     {
/* 187 */       return loc;
/*     */     }
/*     */ 
/*     */     
/* 191 */     for (int i = 0; i < acustomguiproperties.length; i++) {
/*     */       
/* 193 */       CustomGuiProperties customguiproperties = acustomguiproperties[i];
/*     */       
/* 195 */       if (customguiproperties.matchesEntity(container, entity, blockAccess))
/*     */       {
/* 197 */         return customguiproperties.getTextureLocation(loc);
/*     */       }
/*     */     } 
/*     */     
/* 201 */     return loc;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static void update() {
/* 207 */     guiProperties = (CustomGuiProperties[][])null;
/*     */     
/* 209 */     if (Config.isCustomGuis()) {
/*     */       
/* 211 */       List<List<CustomGuiProperties>> list = new ArrayList<>();
/* 212 */       IResourcePack[] airesourcepack = Config.getResourcePacks();
/*     */       
/* 214 */       for (int i = airesourcepack.length - 1; i >= 0; i--) {
/*     */         
/* 216 */         IResourcePack iresourcepack = airesourcepack[i];
/* 217 */         update(iresourcepack, list);
/*     */       } 
/*     */       
/* 220 */       guiProperties = propertyListToArray(list);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private static CustomGuiProperties[][] propertyListToArray(List<List<CustomGuiProperties>> listProps) {
/* 226 */     if (listProps.isEmpty())
/*     */     {
/* 228 */       return (CustomGuiProperties[][])null;
/*     */     }
/*     */ 
/*     */     
/* 232 */     CustomGuiProperties[][] acustomguiproperties = new CustomGuiProperties[CustomGuiProperties.EnumContainer.VALUES.length][];
/*     */     
/* 234 */     for (int i = 0; i < acustomguiproperties.length; i++) {
/*     */       
/* 236 */       if (listProps.size() > i) {
/*     */         
/* 238 */         List<CustomGuiProperties> list = listProps.get(i);
/*     */         
/* 240 */         if (list != null) {
/*     */           
/* 242 */           CustomGuiProperties[] acustomguiproperties1 = list.<CustomGuiProperties>toArray(new CustomGuiProperties[list.size()]);
/* 243 */           acustomguiproperties[i] = acustomguiproperties1;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 248 */     return acustomguiproperties;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static void update(IResourcePack rp, List<List<CustomGuiProperties>> listProps) {
/* 254 */     String[] astring = ResUtils.collectFiles(rp, "optifine/gui/container/", ".properties", null);
/* 255 */     Arrays.sort((Object[])astring);
/*     */     
/* 257 */     for (int i = 0; i < astring.length; i++) {
/*     */       
/* 259 */       String s = astring[i];
/* 260 */       Config.dbg("CustomGuis: " + s);
/*     */ 
/*     */       
/*     */       try {
/* 264 */         ResourceLocation resourcelocation = new ResourceLocation(s);
/* 265 */         InputStream inputstream = rp.getInputStream(resourcelocation);
/*     */         
/* 267 */         if (inputstream == null)
/*     */         {
/* 269 */           Config.warn("CustomGuis file not found: " + s);
/*     */         }
/*     */         else
/*     */         {
/* 273 */           PropertiesOrdered propertiesOrdered = new PropertiesOrdered();
/* 274 */           propertiesOrdered.load(inputstream);
/* 275 */           inputstream.close();
/* 276 */           CustomGuiProperties customguiproperties = new CustomGuiProperties((Properties)propertiesOrdered, s);
/*     */           
/* 278 */           if (customguiproperties.isValid(s))
/*     */           {
/* 280 */             addToList(customguiproperties, listProps);
/*     */           }
/*     */         }
/*     */       
/* 284 */       } catch (FileNotFoundException var9) {
/*     */         
/* 286 */         Config.warn("CustomGuis file not found: " + s);
/*     */       }
/* 288 */       catch (Exception exception) {
/*     */         
/* 290 */         exception.printStackTrace();
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private static void addToList(CustomGuiProperties cgp, List<List<CustomGuiProperties>> listProps) {
/* 297 */     if (cgp.getContainer() == null) {
/*     */       
/* 299 */       warn("Invalid container: " + cgp.getContainer());
/*     */     }
/*     */     else {
/*     */       
/* 303 */       int i = cgp.getContainer().ordinal();
/*     */       
/* 305 */       while (listProps.size() <= i)
/*     */       {
/* 307 */         listProps.add(null);
/*     */       }
/*     */       
/* 310 */       List<CustomGuiProperties> list = listProps.get(i);
/*     */       
/* 312 */       if (list == null) {
/*     */         
/* 314 */         list = new ArrayList<>();
/* 315 */         listProps.set(i, list);
/*     */       } 
/*     */       
/* 318 */       list.add(cgp);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static PlayerControllerOF getPlayerControllerOF() {
/* 324 */     return playerControllerOF;
/*     */   }
/*     */ 
/*     */   
/*     */   public static void setPlayerControllerOF(PlayerControllerOF playerControllerOF) {
/* 329 */     playerControllerOF = playerControllerOF;
/*     */   }
/*     */ 
/*     */   
/*     */   private static boolean isChristmas() {
/* 334 */     Calendar calendar = Calendar.getInstance();
/* 335 */     return (calendar.get(2) + 1 == 12 && calendar.get(5) >= 24 && calendar.get(5) <= 26);
/*     */   }
/*     */ 
/*     */   
/*     */   private static void warn(String str) {
/* 340 */     Config.warn("[CustomGuis] " + str);
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\optifine\CustomGuis.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */