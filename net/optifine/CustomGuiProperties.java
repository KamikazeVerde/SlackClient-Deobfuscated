/*     */ package net.optifine;
/*     */ 
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import java.util.Properties;
/*     */ import net.minecraft.client.gui.GuiScreen;
/*     */ import net.minecraft.client.gui.inventory.GuiDispenser;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.passive.EntityHorse;
/*     */ import net.minecraft.entity.passive.EntityVillager;
/*     */ import net.minecraft.item.EnumDyeColor;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.src.Config;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.tileentity.TileEntityBeacon;
/*     */ import net.minecraft.tileentity.TileEntityChest;
/*     */ import net.minecraft.tileentity.TileEntityDispenser;
/*     */ import net.minecraft.tileentity.TileEntityEnderChest;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.IWorldNameable;
/*     */ import net.minecraft.world.biome.BiomeGenBase;
/*     */ import net.optifine.config.ConnectedParser;
/*     */ import net.optifine.config.Matches;
/*     */ import net.optifine.config.NbtTagValue;
/*     */ import net.optifine.config.RangeListInt;
/*     */ import net.optifine.config.VillagerProfession;
/*     */ import net.optifine.reflect.Reflector;
/*     */ import net.optifine.reflect.ReflectorField;
/*     */ import net.optifine.util.StrUtils;
/*     */ import net.optifine.util.TextureUtils;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CustomGuiProperties
/*     */ {
/*  43 */   private String fileName = null;
/*  44 */   private String basePath = null;
/*  45 */   private EnumContainer container = null;
/*  46 */   private Map<ResourceLocation, ResourceLocation> textureLocations = null;
/*  47 */   private NbtTagValue nbtName = null;
/*  48 */   private BiomeGenBase[] biomes = null;
/*  49 */   private RangeListInt heights = null;
/*  50 */   private Boolean large = null;
/*  51 */   private Boolean trapped = null;
/*  52 */   private Boolean christmas = null;
/*  53 */   private Boolean ender = null;
/*  54 */   private RangeListInt levels = null;
/*  55 */   private VillagerProfession[] professions = null;
/*  56 */   private EnumVariant[] variants = null;
/*  57 */   private EnumDyeColor[] colors = null;
/*  58 */   private static final EnumVariant[] VARIANTS_HORSE = new EnumVariant[] { EnumVariant.HORSE, EnumVariant.DONKEY, EnumVariant.MULE, EnumVariant.LLAMA };
/*  59 */   private static final EnumVariant[] VARIANTS_DISPENSER = new EnumVariant[] { EnumVariant.DISPENSER, EnumVariant.DROPPER };
/*  60 */   private static final EnumVariant[] VARIANTS_INVALID = new EnumVariant[0];
/*  61 */   private static final EnumDyeColor[] COLORS_INVALID = new EnumDyeColor[0];
/*  62 */   private static final ResourceLocation ANVIL_GUI_TEXTURE = new ResourceLocation("textures/gui/container/anvil.png");
/*  63 */   private static final ResourceLocation BEACON_GUI_TEXTURE = new ResourceLocation("textures/gui/container/beacon.png");
/*  64 */   private static final ResourceLocation BREWING_STAND_GUI_TEXTURE = new ResourceLocation("textures/gui/container/brewing_stand.png");
/*  65 */   private static final ResourceLocation CHEST_GUI_TEXTURE = new ResourceLocation("textures/gui/container/generic_54.png");
/*  66 */   private static final ResourceLocation CRAFTING_TABLE_GUI_TEXTURE = new ResourceLocation("textures/gui/container/crafting_table.png");
/*  67 */   private static final ResourceLocation HORSE_GUI_TEXTURE = new ResourceLocation("textures/gui/container/horse.png");
/*  68 */   private static final ResourceLocation DISPENSER_GUI_TEXTURE = new ResourceLocation("textures/gui/container/dispenser.png");
/*  69 */   private static final ResourceLocation ENCHANTMENT_TABLE_GUI_TEXTURE = new ResourceLocation("textures/gui/container/enchanting_table.png");
/*  70 */   private static final ResourceLocation FURNACE_GUI_TEXTURE = new ResourceLocation("textures/gui/container/furnace.png");
/*  71 */   private static final ResourceLocation HOPPER_GUI_TEXTURE = new ResourceLocation("textures/gui/container/hopper.png");
/*  72 */   private static final ResourceLocation INVENTORY_GUI_TEXTURE = new ResourceLocation("textures/gui/container/inventory.png");
/*  73 */   private static final ResourceLocation SHULKER_BOX_GUI_TEXTURE = new ResourceLocation("textures/gui/container/shulker_box.png");
/*  74 */   private static final ResourceLocation VILLAGER_GUI_TEXTURE = new ResourceLocation("textures/gui/container/villager.png");
/*     */ 
/*     */   
/*     */   public CustomGuiProperties(Properties props, String path) {
/*  78 */     ConnectedParser connectedparser = new ConnectedParser("CustomGuis");
/*  79 */     this.fileName = connectedparser.parseName(path);
/*  80 */     this.basePath = connectedparser.parseBasePath(path);
/*  81 */     this.container = (EnumContainer)connectedparser.parseEnum(props.getProperty("container"), (Enum[])EnumContainer.values(), "container");
/*  82 */     this.textureLocations = parseTextureLocations(props, "texture", this.container, "textures/gui/", this.basePath);
/*  83 */     this.nbtName = connectedparser.parseNbtTagValue("name", props.getProperty("name"));
/*  84 */     this.biomes = connectedparser.parseBiomes(props.getProperty("biomes"));
/*  85 */     this.heights = connectedparser.parseRangeListInt(props.getProperty("heights"));
/*  86 */     this.large = connectedparser.parseBooleanObject(props.getProperty("large"));
/*  87 */     this.trapped = connectedparser.parseBooleanObject(props.getProperty("trapped"));
/*  88 */     this.christmas = connectedparser.parseBooleanObject(props.getProperty("christmas"));
/*  89 */     this.ender = connectedparser.parseBooleanObject(props.getProperty("ender"));
/*  90 */     this.levels = connectedparser.parseRangeListInt(props.getProperty("levels"));
/*  91 */     this.professions = connectedparser.parseProfessions(props.getProperty("professions"));
/*  92 */     EnumVariant[] acustomguiproperties$enumvariant = getContainerVariants(this.container);
/*  93 */     this.variants = (EnumVariant[])connectedparser.parseEnums(props.getProperty("variants"), (Enum[])acustomguiproperties$enumvariant, "variants", (Enum[])VARIANTS_INVALID);
/*  94 */     this.colors = parseEnumDyeColors(props.getProperty("colors"));
/*     */   }
/*     */ 
/*     */   
/*     */   private static EnumVariant[] getContainerVariants(EnumContainer cont) {
/*  99 */     return (cont == EnumContainer.HORSE) ? VARIANTS_HORSE : ((cont == EnumContainer.DISPENSER) ? VARIANTS_DISPENSER : new EnumVariant[0]);
/*     */   }
/*     */ 
/*     */   
/*     */   private static EnumDyeColor[] parseEnumDyeColors(String str) {
/* 104 */     if (str == null)
/*     */     {
/* 106 */       return null;
/*     */     }
/*     */ 
/*     */     
/* 110 */     str = str.toLowerCase();
/* 111 */     String[] astring = Config.tokenize(str, " ");
/* 112 */     EnumDyeColor[] aenumdyecolor = new EnumDyeColor[astring.length];
/*     */     
/* 114 */     for (int i = 0; i < astring.length; i++) {
/*     */       
/* 116 */       String s = astring[i];
/* 117 */       EnumDyeColor enumdyecolor = parseEnumDyeColor(s);
/*     */       
/* 119 */       if (enumdyecolor == null) {
/*     */         
/* 121 */         warn("Invalid color: " + s);
/* 122 */         return COLORS_INVALID;
/*     */       } 
/*     */       
/* 125 */       aenumdyecolor[i] = enumdyecolor;
/*     */     } 
/*     */     
/* 128 */     return aenumdyecolor;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static EnumDyeColor parseEnumDyeColor(String str) {
/* 134 */     if (str == null)
/*     */     {
/* 136 */       return null;
/*     */     }
/*     */ 
/*     */     
/* 140 */     EnumDyeColor[] aenumdyecolor = EnumDyeColor.values();
/*     */     
/* 142 */     for (int i = 0; i < aenumdyecolor.length; i++) {
/*     */       
/* 144 */       EnumDyeColor enumdyecolor = aenumdyecolor[i];
/*     */       
/* 146 */       if (enumdyecolor.getName().equals(str))
/*     */       {
/* 148 */         return enumdyecolor;
/*     */       }
/*     */       
/* 151 */       if (enumdyecolor.getUnlocalizedName().equals(str))
/*     */       {
/* 153 */         return enumdyecolor;
/*     */       }
/*     */     } 
/*     */     
/* 157 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static ResourceLocation parseTextureLocation(String str, String basePath) {
/* 163 */     if (str == null)
/*     */     {
/* 165 */       return null;
/*     */     }
/*     */ 
/*     */     
/* 169 */     str = str.trim();
/* 170 */     String s = TextureUtils.fixResourcePath(str, basePath);
/*     */     
/* 172 */     if (!s.endsWith(".png"))
/*     */     {
/* 174 */       s = s + ".png";
/*     */     }
/*     */     
/* 177 */     return new ResourceLocation(basePath + "/" + s);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static Map<ResourceLocation, ResourceLocation> parseTextureLocations(Properties props, String property, EnumContainer container, String pathPrefix, String basePath) {
/* 183 */     Map<ResourceLocation, ResourceLocation> map = new HashMap<>();
/* 184 */     String s = props.getProperty(property);
/*     */     
/* 186 */     if (s != null) {
/*     */       
/* 188 */       ResourceLocation resourcelocation = getGuiTextureLocation(container);
/* 189 */       ResourceLocation resourcelocation1 = parseTextureLocation(s, basePath);
/*     */       
/* 191 */       if (resourcelocation != null && resourcelocation1 != null)
/*     */       {
/* 193 */         map.put(resourcelocation, resourcelocation1);
/*     */       }
/*     */     } 
/*     */     
/* 197 */     String s5 = property + ".";
/*     */     
/* 199 */     for (Object e : props.keySet()) {
/*     */       
/* 201 */       String s1 = (String)e;
/* 202 */       if (s1.startsWith(s5)) {
/*     */         
/* 204 */         String s2 = s1.substring(s5.length());
/* 205 */         s2 = s2.replace('\\', '/');
/* 206 */         s2 = StrUtils.removePrefixSuffix(s2, "/", ".png");
/* 207 */         String s3 = pathPrefix + s2 + ".png";
/* 208 */         String s4 = props.getProperty(s1);
/* 209 */         ResourceLocation resourcelocation2 = new ResourceLocation(s3);
/* 210 */         ResourceLocation resourcelocation3 = parseTextureLocation(s4, basePath);
/* 211 */         map.put(resourcelocation2, resourcelocation3);
/*     */       } 
/*     */     } 
/*     */     
/* 215 */     return map;
/*     */   }
/*     */ 
/*     */   
/*     */   private static ResourceLocation getGuiTextureLocation(EnumContainer container) {
/* 220 */     switch (container) {
/*     */       
/*     */       case ANVIL:
/* 223 */         return ANVIL_GUI_TEXTURE;
/*     */       
/*     */       case BEACON:
/* 226 */         return BEACON_GUI_TEXTURE;
/*     */       
/*     */       case BREWING_STAND:
/* 229 */         return BREWING_STAND_GUI_TEXTURE;
/*     */       
/*     */       case CHEST:
/* 232 */         return CHEST_GUI_TEXTURE;
/*     */       
/*     */       case CRAFTING:
/* 235 */         return CRAFTING_TABLE_GUI_TEXTURE;
/*     */       
/*     */       case CREATIVE:
/* 238 */         return null;
/*     */       
/*     */       case DISPENSER:
/* 241 */         return DISPENSER_GUI_TEXTURE;
/*     */       
/*     */       case ENCHANTMENT:
/* 244 */         return ENCHANTMENT_TABLE_GUI_TEXTURE;
/*     */       
/*     */       case FURNACE:
/* 247 */         return FURNACE_GUI_TEXTURE;
/*     */       
/*     */       case HOPPER:
/* 250 */         return HOPPER_GUI_TEXTURE;
/*     */       
/*     */       case HORSE:
/* 253 */         return HORSE_GUI_TEXTURE;
/*     */       
/*     */       case INVENTORY:
/* 256 */         return INVENTORY_GUI_TEXTURE;
/*     */       
/*     */       case SHULKER_BOX:
/* 259 */         return SHULKER_BOX_GUI_TEXTURE;
/*     */       
/*     */       case VILLAGER:
/* 262 */         return VILLAGER_GUI_TEXTURE;
/*     */     } 
/*     */     
/* 265 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isValid(String path) {
/* 271 */     if (this.fileName != null && this.fileName.length() > 0) {
/*     */       
/* 273 */       if (this.basePath == null) {
/*     */         
/* 275 */         warn("No base path found: " + path);
/* 276 */         return false;
/*     */       } 
/* 278 */       if (this.container == null) {
/*     */         
/* 280 */         warn("No container found: " + path);
/* 281 */         return false;
/*     */       } 
/* 283 */       if (this.textureLocations.isEmpty()) {
/*     */         
/* 285 */         warn("No texture found: " + path);
/* 286 */         return false;
/*     */       } 
/* 288 */       if (this.professions == ConnectedParser.PROFESSIONS_INVALID) {
/*     */         
/* 290 */         warn("Invalid professions or careers: " + path);
/* 291 */         return false;
/*     */       } 
/* 293 */       if (this.variants == VARIANTS_INVALID) {
/*     */         
/* 295 */         warn("Invalid variants: " + path);
/* 296 */         return false;
/*     */       } 
/* 298 */       if (this.colors == COLORS_INVALID) {
/*     */         
/* 300 */         warn("Invalid colors: " + path);
/* 301 */         return false;
/*     */       } 
/*     */ 
/*     */       
/* 305 */       return true;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 310 */     warn("No name found: " + path);
/* 311 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static void warn(String str) {
/* 317 */     Config.warn("[CustomGuis] " + str);
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean matchesGeneral(EnumContainer ec, BlockPos pos, IBlockAccess blockAccess) {
/* 322 */     if (this.container != ec)
/*     */     {
/* 324 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 328 */     if (this.biomes != null) {
/*     */       
/* 330 */       BiomeGenBase biomegenbase = blockAccess.getBiomeGenForCoords(pos);
/*     */       
/* 332 */       if (!Matches.biome(biomegenbase, this.biomes))
/*     */       {
/* 334 */         return false;
/*     */       }
/*     */     } 
/*     */     
/* 338 */     return (this.heights == null || this.heights.isInRange(pos.getY()));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean matchesPos(EnumContainer ec, BlockPos pos, IBlockAccess blockAccess, GuiScreen screen) {
/* 344 */     if (!matchesGeneral(ec, pos, blockAccess))
/*     */     {
/* 346 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 350 */     if (this.nbtName != null) {
/*     */       
/* 352 */       String s = getName(screen);
/*     */       
/* 354 */       if (!this.nbtName.matchesValue(s))
/*     */       {
/* 356 */         return false;
/*     */       }
/*     */     } 
/*     */     
/* 360 */     switch (ec) {
/*     */       
/*     */       case BEACON:
/* 363 */         return matchesBeacon(pos, blockAccess);
/*     */       
/*     */       case CHEST:
/* 366 */         return matchesChest(pos, blockAccess);
/*     */       
/*     */       case DISPENSER:
/* 369 */         return matchesDispenser(pos, blockAccess);
/*     */     } 
/*     */     
/* 372 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getName(GuiScreen screen) {
/* 379 */     IWorldNameable iworldnameable = getWorldNameable(screen);
/* 380 */     return (iworldnameable == null) ? null : iworldnameable.getDisplayName().getUnformattedText();
/*     */   }
/*     */ 
/*     */   
/*     */   private static IWorldNameable getWorldNameable(GuiScreen screen) {
/* 385 */     return (screen instanceof net.minecraft.client.gui.inventory.GuiBeacon) ? getWorldNameable(screen, Reflector.GuiBeacon_tileBeacon) : ((screen instanceof net.minecraft.client.gui.inventory.GuiBrewingStand) ? getWorldNameable(screen, Reflector.GuiBrewingStand_tileBrewingStand) : ((screen instanceof net.minecraft.client.gui.inventory.GuiChest) ? getWorldNameable(screen, Reflector.GuiChest_lowerChestInventory) : ((screen instanceof GuiDispenser) ? (IWorldNameable)((GuiDispenser)screen).dispenserInventory : ((screen instanceof net.minecraft.client.gui.GuiEnchantment) ? getWorldNameable(screen, Reflector.GuiEnchantment_nameable) : ((screen instanceof net.minecraft.client.gui.inventory.GuiFurnace) ? getWorldNameable(screen, Reflector.GuiFurnace_tileFurnace) : ((screen instanceof net.minecraft.client.gui.GuiHopper) ? getWorldNameable(screen, Reflector.GuiHopper_hopperInventory) : null))))));
/*     */   }
/*     */ 
/*     */   
/*     */   private static IWorldNameable getWorldNameable(GuiScreen screen, ReflectorField fieldInventory) {
/* 390 */     Object object = Reflector.getFieldValue(screen, fieldInventory);
/* 391 */     return !(object instanceof IWorldNameable) ? null : (IWorldNameable)object;
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean matchesBeacon(BlockPos pos, IBlockAccess blockAccess) {
/* 396 */     TileEntity tileentity = blockAccess.getTileEntity(pos);
/*     */     
/* 398 */     if (!(tileentity instanceof TileEntityBeacon))
/*     */     {
/* 400 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 404 */     TileEntityBeacon tileentitybeacon = (TileEntityBeacon)tileentity;
/*     */     
/* 406 */     if (this.levels != null) {
/*     */       
/* 408 */       NBTTagCompound nbttagcompound = new NBTTagCompound();
/* 409 */       tileentitybeacon.writeToNBT(nbttagcompound);
/* 410 */       int i = nbttagcompound.getInteger("Levels");
/*     */       
/* 412 */       if (!this.levels.isInRange(i))
/*     */       {
/* 414 */         return false;
/*     */       }
/*     */     } 
/*     */     
/* 418 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean matchesChest(BlockPos pos, IBlockAccess blockAccess) {
/* 424 */     TileEntity tileentity = blockAccess.getTileEntity(pos);
/*     */     
/* 426 */     if (tileentity instanceof TileEntityChest) {
/*     */       
/* 428 */       TileEntityChest tileentitychest = (TileEntityChest)tileentity;
/* 429 */       return matchesChest(tileentitychest, pos, blockAccess);
/*     */     } 
/* 431 */     if (tileentity instanceof TileEntityEnderChest) {
/*     */       
/* 433 */       TileEntityEnderChest tileentityenderchest = (TileEntityEnderChest)tileentity;
/* 434 */       return matchesEnderChest(tileentityenderchest, pos, blockAccess);
/*     */     } 
/*     */ 
/*     */     
/* 438 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean matchesChest(TileEntityChest tec, BlockPos pos, IBlockAccess blockAccess) {
/* 444 */     boolean flag = (tec.adjacentChestXNeg != null || tec.adjacentChestXPos != null || tec.adjacentChestZNeg != null || tec.adjacentChestZPos != null);
/* 445 */     boolean flag1 = (tec.getChestType() == 1);
/* 446 */     boolean flag2 = CustomGuis.isChristmas;
/* 447 */     boolean flag3 = false;
/* 448 */     return matchesChest(flag, flag1, flag2, flag3);
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean matchesEnderChest(TileEntityEnderChest teec, BlockPos pos, IBlockAccess blockAccess) {
/* 453 */     return matchesChest(false, false, false, true);
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean matchesChest(boolean isLarge, boolean isTrapped, boolean isChristmas, boolean isEnder) {
/* 458 */     return (this.large != null && this.large.booleanValue() != isLarge) ? false : ((this.trapped != null && this.trapped.booleanValue() != isTrapped) ? false : ((this.christmas != null && this.christmas.booleanValue() != isChristmas) ? false : ((this.ender == null || this.ender.booleanValue() == isEnder))));
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean matchesDispenser(BlockPos pos, IBlockAccess blockAccess) {
/* 463 */     TileEntity tileentity = blockAccess.getTileEntity(pos);
/*     */     
/* 465 */     if (!(tileentity instanceof TileEntityDispenser))
/*     */     {
/* 467 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 471 */     TileEntityDispenser tileentitydispenser = (TileEntityDispenser)tileentity;
/*     */     
/* 473 */     if (this.variants != null) {
/*     */       
/* 475 */       EnumVariant customguiproperties$enumvariant = getDispenserVariant(tileentitydispenser);
/*     */       
/* 477 */       if (!Config.equalsOne(customguiproperties$enumvariant, (Object[])this.variants))
/*     */       {
/* 479 */         return false;
/*     */       }
/*     */     } 
/*     */     
/* 483 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private EnumVariant getDispenserVariant(TileEntityDispenser ted) {
/* 489 */     return (ted instanceof net.minecraft.tileentity.TileEntityDropper) ? EnumVariant.DROPPER : EnumVariant.DISPENSER;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean matchesEntity(EnumContainer ec, Entity entity, IBlockAccess blockAccess) {
/* 494 */     if (!matchesGeneral(ec, entity.getPosition(), blockAccess))
/*     */     {
/* 496 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 500 */     if (this.nbtName != null) {
/*     */       
/* 502 */       String s = entity.getCommandSenderName();
/*     */       
/* 504 */       if (!this.nbtName.matchesValue(s))
/*     */       {
/* 506 */         return false;
/*     */       }
/*     */     } 
/*     */     
/* 510 */     switch (ec) {
/*     */       
/*     */       case HORSE:
/* 513 */         return matchesHorse(entity, blockAccess);
/*     */       
/*     */       case VILLAGER:
/* 516 */         return matchesVillager(entity, blockAccess);
/*     */     } 
/*     */     
/* 519 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean matchesVillager(Entity entity, IBlockAccess blockAccess) {
/* 526 */     if (!(entity instanceof EntityVillager))
/*     */     {
/* 528 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 532 */     EntityVillager entityvillager = (EntityVillager)entity;
/*     */     
/* 534 */     if (this.professions != null) {
/*     */       
/* 536 */       int i = entityvillager.getProfession();
/* 537 */       int j = Reflector.getFieldValueInt(entityvillager, Reflector.EntityVillager_careerId, -1);
/*     */       
/* 539 */       if (j < 0)
/*     */       {
/* 541 */         return false;
/*     */       }
/*     */       
/* 544 */       boolean flag = false;
/*     */       
/* 546 */       for (int k = 0; k < this.professions.length; k++) {
/*     */         
/* 548 */         VillagerProfession villagerprofession = this.professions[k];
/*     */         
/* 550 */         if (villagerprofession.matches(i, j)) {
/*     */           
/* 552 */           flag = true;
/*     */           
/*     */           break;
/*     */         } 
/*     */       } 
/* 557 */       if (!flag)
/*     */       {
/* 559 */         return false;
/*     */       }
/*     */     } 
/*     */     
/* 563 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean matchesHorse(Entity entity, IBlockAccess blockAccess) {
/* 569 */     if (!(entity instanceof EntityHorse))
/*     */     {
/* 571 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 575 */     EntityHorse entityhorse = (EntityHorse)entity;
/*     */     
/* 577 */     if (this.variants != null) {
/*     */       
/* 579 */       EnumVariant customguiproperties$enumvariant = getHorseVariant(entityhorse);
/*     */       
/* 581 */       if (!Config.equalsOne(customguiproperties$enumvariant, (Object[])this.variants))
/*     */       {
/* 583 */         return false;
/*     */       }
/*     */     } 
/*     */     
/* 587 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private EnumVariant getHorseVariant(EntityHorse entity) {
/* 593 */     int i = entity.getHorseType();
/*     */     
/* 595 */     switch (i) {
/*     */       
/*     */       case 0:
/* 598 */         return EnumVariant.HORSE;
/*     */       
/*     */       case 1:
/* 601 */         return EnumVariant.DONKEY;
/*     */       
/*     */       case 2:
/* 604 */         return EnumVariant.MULE;
/*     */     } 
/*     */     
/* 607 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public EnumContainer getContainer() {
/* 613 */     return this.container;
/*     */   }
/*     */ 
/*     */   
/*     */   public ResourceLocation getTextureLocation(ResourceLocation loc) {
/* 618 */     ResourceLocation resourcelocation = this.textureLocations.get(loc);
/* 619 */     return (resourcelocation == null) ? loc : resourcelocation;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 624 */     return "name: " + this.fileName + ", container: " + this.container + ", textures: " + this.textureLocations;
/*     */   }
/*     */   
/*     */   public enum EnumContainer
/*     */   {
/* 629 */     ANVIL,
/* 630 */     BEACON,
/* 631 */     BREWING_STAND,
/* 632 */     CHEST,
/* 633 */     CRAFTING,
/* 634 */     DISPENSER,
/* 635 */     ENCHANTMENT,
/* 636 */     FURNACE,
/* 637 */     HOPPER,
/* 638 */     HORSE,
/* 639 */     VILLAGER,
/* 640 */     SHULKER_BOX,
/* 641 */     CREATIVE,
/* 642 */     INVENTORY; static {
/*     */     
/* 644 */     } public static final EnumContainer[] VALUES = values();
/*     */   }
/*     */   
/*     */   private enum EnumVariant
/*     */   {
/* 649 */     HORSE,
/* 650 */     DONKEY,
/* 651 */     MULE,
/* 652 */     LLAMA,
/* 653 */     DISPENSER,
/* 654 */     DROPPER;
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\optifine\CustomGuiProperties.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */