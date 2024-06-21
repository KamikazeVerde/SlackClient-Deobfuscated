/*     */ package net.minecraft.command;
/*     */ 
/*     */ import com.google.common.base.Functions;
/*     */ import com.google.common.collect.Iterables;
/*     */ import com.google.common.collect.Lists;
/*     */ import com.google.common.primitives.Doubles;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import java.util.UUID;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.player.EntityPlayerMP;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.server.MinecraftServer;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.ChatComponentText;
/*     */ import net.minecraft.util.IChatComponent;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class CommandBase
/*     */   implements ICommand
/*     */ {
/*     */   private static IAdminCommand theAdmin;
/*     */   
/*     */   public int getRequiredPermissionLevel() {
/*  31 */     return 4;
/*     */   }
/*     */ 
/*     */   
/*     */   public List<String> getCommandAliases() {
/*  36 */     return Collections.emptyList();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canCommandSenderUseCommand(ICommandSender sender) {
/*  46 */     return sender.canCommandSenderUseCommand(getRequiredPermissionLevel(), getCommandName());
/*     */   }
/*     */ 
/*     */   
/*     */   public List<String> addTabCompletionOptions(ICommandSender sender, String[] args, BlockPos pos) {
/*  51 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static int parseInt(String input) throws NumberInvalidException {
/*     */     try {
/*  58 */       return Integer.parseInt(input);
/*     */     }
/*  60 */     catch (NumberFormatException var2) {
/*     */       
/*  62 */       throw new NumberInvalidException("commands.generic.num.invalid", new Object[] { input });
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static int parseInt(String input, int min) throws NumberInvalidException {
/*  68 */     return parseInt(input, min, 2147483647);
/*     */   }
/*     */ 
/*     */   
/*     */   public static int parseInt(String input, int min, int max) throws NumberInvalidException {
/*  73 */     int i = parseInt(input);
/*     */     
/*  75 */     if (i < min)
/*     */     {
/*  77 */       throw new NumberInvalidException("commands.generic.num.tooSmall", new Object[] { Integer.valueOf(i), Integer.valueOf(min) });
/*     */     }
/*  79 */     if (i > max)
/*     */     {
/*  81 */       throw new NumberInvalidException("commands.generic.num.tooBig", new Object[] { Integer.valueOf(i), Integer.valueOf(max) });
/*     */     }
/*     */ 
/*     */     
/*  85 */     return i;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static long parseLong(String input) throws NumberInvalidException {
/*     */     try {
/*  93 */       return Long.parseLong(input);
/*     */     }
/*  95 */     catch (NumberFormatException var2) {
/*     */       
/*  97 */       throw new NumberInvalidException("commands.generic.num.invalid", new Object[] { input });
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static long parseLong(String input, long min, long max) throws NumberInvalidException {
/* 103 */     long i = parseLong(input);
/*     */     
/* 105 */     if (i < min)
/*     */     {
/* 107 */       throw new NumberInvalidException("commands.generic.num.tooSmall", new Object[] { Long.valueOf(i), Long.valueOf(min) });
/*     */     }
/* 109 */     if (i > max)
/*     */     {
/* 111 */       throw new NumberInvalidException("commands.generic.num.tooBig", new Object[] { Long.valueOf(i), Long.valueOf(max) });
/*     */     }
/*     */ 
/*     */     
/* 115 */     return i;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static BlockPos parseBlockPos(ICommandSender sender, String[] args, int startIndex, boolean centerBlock) throws NumberInvalidException {
/* 121 */     BlockPos blockpos = sender.getPosition();
/* 122 */     return new BlockPos(parseDouble(blockpos.getX(), args[startIndex], -30000000, 30000000, centerBlock), parseDouble(blockpos.getY(), args[startIndex + 1], 0, 256, false), parseDouble(blockpos.getZ(), args[startIndex + 2], -30000000, 30000000, centerBlock));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static double parseDouble(String input) throws NumberInvalidException {
/*     */     try {
/* 129 */       double d0 = Double.parseDouble(input);
/*     */       
/* 131 */       if (!Doubles.isFinite(d0))
/*     */       {
/* 133 */         throw new NumberInvalidException("commands.generic.num.invalid", new Object[] { input });
/*     */       }
/*     */ 
/*     */       
/* 137 */       return d0;
/*     */     
/*     */     }
/* 140 */     catch (NumberFormatException var3) {
/*     */       
/* 142 */       throw new NumberInvalidException("commands.generic.num.invalid", new Object[] { input });
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static double parseDouble(String input, double min) throws NumberInvalidException {
/* 148 */     return parseDouble(input, min, Double.MAX_VALUE);
/*     */   }
/*     */ 
/*     */   
/*     */   public static double parseDouble(String input, double min, double max) throws NumberInvalidException {
/* 153 */     double d0 = parseDouble(input);
/*     */     
/* 155 */     if (d0 < min)
/*     */     {
/* 157 */       throw new NumberInvalidException("commands.generic.double.tooSmall", new Object[] { Double.valueOf(d0), Double.valueOf(min) });
/*     */     }
/* 159 */     if (d0 > max)
/*     */     {
/* 161 */       throw new NumberInvalidException("commands.generic.double.tooBig", new Object[] { Double.valueOf(d0), Double.valueOf(max) });
/*     */     }
/*     */ 
/*     */     
/* 165 */     return d0;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean parseBoolean(String input) throws CommandException {
/* 171 */     if (!input.equals("true") && !input.equals("1")) {
/*     */       
/* 173 */       if (!input.equals("false") && !input.equals("0"))
/*     */       {
/* 175 */         throw new CommandException("commands.generic.boolean.invalid", new Object[] { input });
/*     */       }
/*     */ 
/*     */       
/* 179 */       return false;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 184 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static EntityPlayerMP getCommandSenderAsPlayer(ICommandSender sender) throws PlayerNotFoundException {
/* 193 */     if (sender instanceof EntityPlayerMP)
/*     */     {
/* 195 */       return (EntityPlayerMP)sender;
/*     */     }
/*     */ 
/*     */     
/* 199 */     throw new PlayerNotFoundException("You must specify which player you wish to perform this action on.", new Object[0]);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static EntityPlayerMP getPlayer(ICommandSender sender, String username) throws PlayerNotFoundException {
/* 205 */     EntityPlayerMP entityplayermp = PlayerSelector.matchOnePlayer(sender, username);
/*     */     
/* 207 */     if (entityplayermp == null) {
/*     */       
/*     */       try {
/*     */         
/* 211 */         entityplayermp = MinecraftServer.getServer().getConfigurationManager().getPlayerByUUID(UUID.fromString(username));
/*     */       }
/* 213 */       catch (IllegalArgumentException illegalArgumentException) {}
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 219 */     if (entityplayermp == null)
/*     */     {
/* 221 */       entityplayermp = MinecraftServer.getServer().getConfigurationManager().getPlayerByUsername(username);
/*     */     }
/*     */     
/* 224 */     if (entityplayermp == null)
/*     */     {
/* 226 */       throw new PlayerNotFoundException();
/*     */     }
/*     */ 
/*     */     
/* 230 */     return entityplayermp;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static Entity func_175768_b(ICommandSender p_175768_0_, String p_175768_1_) throws EntityNotFoundException {
/* 236 */     return getEntity(p_175768_0_, p_175768_1_, Entity.class);
/*     */   }
/*     */   
/*     */   public static <T extends Entity> T getEntity(ICommandSender commandSender, String p_175759_1_, Class<? extends T> p_175759_2_) throws EntityNotFoundException {
/*     */     EntityPlayerMP entityPlayerMP;
/* 241 */     Entity entity = PlayerSelector.matchOneEntity(commandSender, p_175759_1_, p_175759_2_);
/* 242 */     MinecraftServer minecraftserver = MinecraftServer.getServer();
/*     */     
/* 244 */     if (entity == null)
/*     */     {
/* 246 */       entityPlayerMP = minecraftserver.getConfigurationManager().getPlayerByUsername(p_175759_1_);
/*     */     }
/*     */     
/* 249 */     if (entityPlayerMP == null) {
/*     */       
/*     */       try {
/*     */         
/* 253 */         UUID uuid = UUID.fromString(p_175759_1_);
/* 254 */         Entity entity1 = minecraftserver.getEntityFromUuid(uuid);
/*     */         
/* 256 */         if (entity1 == null)
/*     */         {
/* 258 */           entityPlayerMP = minecraftserver.getConfigurationManager().getPlayerByUUID(uuid);
/*     */         }
/*     */       }
/* 261 */       catch (IllegalArgumentException var6) {
/*     */         
/* 263 */         throw new EntityNotFoundException("commands.generic.entity.invalidUuid", new Object[0]);
/*     */       } 
/*     */     }
/*     */     
/* 267 */     if (entityPlayerMP != null && p_175759_2_.isAssignableFrom(entityPlayerMP.getClass()))
/*     */     {
/* 269 */       return (T)entityPlayerMP;
/*     */     }
/*     */ 
/*     */     
/* 273 */     throw new EntityNotFoundException();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static List<Entity> func_175763_c(ICommandSender p_175763_0_, String p_175763_1_) throws EntityNotFoundException {
/* 279 */     return PlayerSelector.hasArguments(p_175763_1_) ? PlayerSelector.<Entity>matchEntities(p_175763_0_, p_175763_1_, Entity.class) : Lists.newArrayList((Object[])new Entity[] { func_175768_b(p_175763_0_, p_175763_1_) });
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getPlayerName(ICommandSender sender, String query) throws PlayerNotFoundException {
/*     */     try {
/* 286 */       return getPlayer(sender, query).getCommandSenderName();
/*     */     }
/* 288 */     catch (PlayerNotFoundException playernotfoundexception) {
/*     */       
/* 290 */       if (PlayerSelector.hasArguments(query))
/*     */       {
/* 292 */         throw playernotfoundexception;
/*     */       }
/*     */ 
/*     */       
/* 296 */       return query;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getEntityName(ICommandSender p_175758_0_, String p_175758_1_) throws EntityNotFoundException {
/*     */     try {
/* 309 */       return getPlayer(p_175758_0_, p_175758_1_).getCommandSenderName();
/*     */     }
/* 311 */     catch (PlayerNotFoundException var5) {
/*     */ 
/*     */       
/*     */       try {
/* 315 */         return func_175768_b(p_175758_0_, p_175758_1_).getUniqueID().toString();
/*     */       }
/* 317 */       catch (EntityNotFoundException entitynotfoundexception) {
/*     */         
/* 319 */         if (PlayerSelector.hasArguments(p_175758_1_))
/*     */         {
/* 321 */           throw entitynotfoundexception;
/*     */         }
/*     */ 
/*     */         
/* 325 */         return p_175758_1_;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static IChatComponent getChatComponentFromNthArg(ICommandSender sender, String[] args, int p_147178_2_) throws CommandException, PlayerNotFoundException {
/* 333 */     return getChatComponentFromNthArg(sender, args, p_147178_2_, false);
/*     */   }
/*     */ 
/*     */   
/*     */   public static IChatComponent getChatComponentFromNthArg(ICommandSender sender, String[] args, int index, boolean p_147176_3_) throws PlayerNotFoundException {
/* 338 */     ChatComponentText chatComponentText = new ChatComponentText("");
/*     */     
/* 340 */     for (int i = index; i < args.length; i++) {
/*     */       IChatComponent iChatComponent;
/* 342 */       if (i > index)
/*     */       {
/* 344 */         chatComponentText.appendText(" ");
/*     */       }
/*     */       
/* 347 */       ChatComponentText chatComponentText1 = new ChatComponentText(args[i]);
/*     */       
/* 349 */       if (p_147176_3_) {
/*     */         
/* 351 */         IChatComponent ichatcomponent2 = PlayerSelector.matchEntitiesToChatComponent(sender, args[i]);
/*     */         
/* 353 */         if (ichatcomponent2 == null) {
/*     */           
/* 355 */           if (PlayerSelector.hasArguments(args[i]))
/*     */           {
/* 357 */             throw new PlayerNotFoundException();
/*     */           }
/*     */         }
/*     */         else {
/*     */           
/* 362 */           iChatComponent = ichatcomponent2;
/*     */         } 
/*     */       } 
/*     */       
/* 366 */       chatComponentText.appendSibling(iChatComponent);
/*     */     } 
/*     */     
/* 369 */     return (IChatComponent)chatComponentText;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String buildString(String[] args, int startPos) {
/* 377 */     StringBuilder stringbuilder = new StringBuilder();
/*     */     
/* 379 */     for (int i = startPos; i < args.length; i++) {
/*     */       
/* 381 */       if (i > startPos)
/*     */       {
/* 383 */         stringbuilder.append(" ");
/*     */       }
/*     */       
/* 386 */       String s = args[i];
/* 387 */       stringbuilder.append(s);
/*     */     } 
/*     */     
/* 390 */     return stringbuilder.toString();
/*     */   }
/*     */ 
/*     */   
/*     */   public static CoordinateArg parseCoordinate(double base, String p_175770_2_, boolean centerBlock) throws NumberInvalidException {
/* 395 */     return parseCoordinate(base, p_175770_2_, -30000000, 30000000, centerBlock);
/*     */   }
/*     */ 
/*     */   
/*     */   public static CoordinateArg parseCoordinate(double p_175767_0_, String p_175767_2_, int min, int max, boolean centerBlock) throws NumberInvalidException {
/* 400 */     boolean flag = p_175767_2_.startsWith("~");
/*     */     
/* 402 */     if (flag && Double.isNaN(p_175767_0_))
/*     */     {
/* 404 */       throw new NumberInvalidException("commands.generic.num.invalid", new Object[] { Double.valueOf(p_175767_0_) });
/*     */     }
/*     */ 
/*     */     
/* 408 */     double d0 = 0.0D;
/*     */     
/* 410 */     if (!flag || p_175767_2_.length() > 1) {
/*     */       
/* 412 */       boolean flag1 = p_175767_2_.contains(".");
/*     */       
/* 414 */       if (flag)
/*     */       {
/* 416 */         p_175767_2_ = p_175767_2_.substring(1);
/*     */       }
/*     */       
/* 419 */       d0 += parseDouble(p_175767_2_);
/*     */       
/* 421 */       if (!flag1 && !flag && centerBlock)
/*     */       {
/* 423 */         d0 += 0.5D;
/*     */       }
/*     */     } 
/*     */     
/* 427 */     if (min != 0 || max != 0) {
/*     */       
/* 429 */       if (d0 < min)
/*     */       {
/* 431 */         throw new NumberInvalidException("commands.generic.double.tooSmall", new Object[] { Double.valueOf(d0), Integer.valueOf(min) });
/*     */       }
/*     */       
/* 434 */       if (d0 > max)
/*     */       {
/* 436 */         throw new NumberInvalidException("commands.generic.double.tooBig", new Object[] { Double.valueOf(d0), Integer.valueOf(max) });
/*     */       }
/*     */     } 
/*     */     
/* 440 */     return new CoordinateArg(d0 + (flag ? p_175767_0_ : 0.0D), d0, flag);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static double parseDouble(double base, String input, boolean centerBlock) throws NumberInvalidException {
/* 446 */     return parseDouble(base, input, -30000000, 30000000, centerBlock);
/*     */   }
/*     */ 
/*     */   
/*     */   public static double parseDouble(double base, String input, int min, int max, boolean centerBlock) throws NumberInvalidException {
/* 451 */     boolean flag = input.startsWith("~");
/*     */     
/* 453 */     if (flag && Double.isNaN(base))
/*     */     {
/* 455 */       throw new NumberInvalidException("commands.generic.num.invalid", new Object[] { Double.valueOf(base) });
/*     */     }
/*     */ 
/*     */     
/* 459 */     double d0 = flag ? base : 0.0D;
/*     */     
/* 461 */     if (!flag || input.length() > 1) {
/*     */       
/* 463 */       boolean flag1 = input.contains(".");
/*     */       
/* 465 */       if (flag)
/*     */       {
/* 467 */         input = input.substring(1);
/*     */       }
/*     */       
/* 470 */       d0 += parseDouble(input);
/*     */       
/* 472 */       if (!flag1 && !flag && centerBlock)
/*     */       {
/* 474 */         d0 += 0.5D;
/*     */       }
/*     */     } 
/*     */     
/* 478 */     if (min != 0 || max != 0) {
/*     */       
/* 480 */       if (d0 < min)
/*     */       {
/* 482 */         throw new NumberInvalidException("commands.generic.double.tooSmall", new Object[] { Double.valueOf(d0), Integer.valueOf(min) });
/*     */       }
/*     */       
/* 485 */       if (d0 > max)
/*     */       {
/* 487 */         throw new NumberInvalidException("commands.generic.double.tooBig", new Object[] { Double.valueOf(d0), Integer.valueOf(max) });
/*     */       }
/*     */     } 
/*     */     
/* 491 */     return d0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Item getItemByText(ICommandSender sender, String id) throws NumberInvalidException {
/* 502 */     ResourceLocation resourcelocation = new ResourceLocation(id);
/* 503 */     Item item = (Item)Item.itemRegistry.getObject(resourcelocation);
/*     */     
/* 505 */     if (item == null)
/*     */     {
/* 507 */       throw new NumberInvalidException("commands.give.item.notFound", new Object[] { resourcelocation });
/*     */     }
/*     */ 
/*     */     
/* 511 */     return item;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Block getBlockByText(ICommandSender sender, String id) throws NumberInvalidException {
/* 522 */     ResourceLocation resourcelocation = new ResourceLocation(id);
/*     */     
/* 524 */     if (!Block.blockRegistry.containsKey(resourcelocation))
/*     */     {
/* 526 */       throw new NumberInvalidException("commands.give.block.notFound", new Object[] { resourcelocation });
/*     */     }
/*     */ 
/*     */     
/* 530 */     Block block = (Block)Block.blockRegistry.getObject(resourcelocation);
/*     */     
/* 532 */     if (block == null)
/*     */     {
/* 534 */       throw new NumberInvalidException("commands.give.block.notFound", new Object[] { resourcelocation });
/*     */     }
/*     */ 
/*     */     
/* 538 */     return block;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String joinNiceString(Object[] elements) {
/* 549 */     StringBuilder stringbuilder = new StringBuilder();
/*     */     
/* 551 */     for (int i = 0; i < elements.length; i++) {
/*     */       
/* 553 */       String s = elements[i].toString();
/*     */       
/* 555 */       if (i > 0)
/*     */       {
/* 557 */         if (i == elements.length - 1) {
/*     */           
/* 559 */           stringbuilder.append(" and ");
/*     */         }
/*     */         else {
/*     */           
/* 563 */           stringbuilder.append(", ");
/*     */         } 
/*     */       }
/*     */       
/* 567 */       stringbuilder.append(s);
/*     */     } 
/*     */     
/* 570 */     return stringbuilder.toString();
/*     */   }
/*     */ 
/*     */   
/*     */   public static IChatComponent join(List<IChatComponent> components) {
/* 575 */     ChatComponentText chatComponentText = new ChatComponentText("");
/*     */     
/* 577 */     for (int i = 0; i < components.size(); i++) {
/*     */       
/* 579 */       if (i > 0)
/*     */       {
/* 581 */         if (i == components.size() - 1) {
/*     */           
/* 583 */           chatComponentText.appendText(" and ");
/*     */         }
/* 585 */         else if (i > 0) {
/*     */           
/* 587 */           chatComponentText.appendText(", ");
/*     */         } 
/*     */       }
/*     */       
/* 591 */       chatComponentText.appendSibling(components.get(i));
/*     */     } 
/*     */     
/* 594 */     return (IChatComponent)chatComponentText;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String joinNiceStringFromCollection(Collection<String> strings) {
/* 604 */     return joinNiceString(strings.toArray((Object[])new String[strings.size()]));
/*     */   }
/*     */   
/*     */   public static List<String> func_175771_a(String[] p_175771_0_, int p_175771_1_, BlockPos p_175771_2_) {
/*     */     String s;
/* 609 */     if (p_175771_2_ == null)
/*     */     {
/* 611 */       return null;
/*     */     }
/*     */ 
/*     */     
/* 615 */     int i = p_175771_0_.length - 1;
/*     */ 
/*     */     
/* 618 */     if (i == p_175771_1_) {
/*     */       
/* 620 */       s = Integer.toString(p_175771_2_.getX());
/*     */     }
/* 622 */     else if (i == p_175771_1_ + 1) {
/*     */       
/* 624 */       s = Integer.toString(p_175771_2_.getY());
/*     */     }
/*     */     else {
/*     */       
/* 628 */       if (i != p_175771_1_ + 2)
/*     */       {
/* 630 */         return null;
/*     */       }
/*     */       
/* 633 */       s = Integer.toString(p_175771_2_.getZ());
/*     */     } 
/*     */     
/* 636 */     return Lists.newArrayList((Object[])new String[] { s });
/*     */   }
/*     */ 
/*     */   
/*     */   public static List<String> func_181043_b(String[] p_181043_0_, int p_181043_1_, BlockPos p_181043_2_) {
/*     */     String s;
/* 642 */     if (p_181043_2_ == null)
/*     */     {
/* 644 */       return null;
/*     */     }
/*     */ 
/*     */     
/* 648 */     int i = p_181043_0_.length - 1;
/*     */ 
/*     */     
/* 651 */     if (i == p_181043_1_) {
/*     */       
/* 653 */       s = Integer.toString(p_181043_2_.getX());
/*     */     }
/*     */     else {
/*     */       
/* 657 */       if (i != p_181043_1_ + 1)
/*     */       {
/* 659 */         return null;
/*     */       }
/*     */       
/* 662 */       s = Integer.toString(p_181043_2_.getZ());
/*     */     } 
/*     */     
/* 665 */     return Lists.newArrayList((Object[])new String[] { s });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean doesStringStartWith(String original, String region) {
/* 674 */     return region.regionMatches(true, 0, original, 0, original.length());
/*     */   }
/*     */ 
/*     */   
/*     */   public static List<String> getListOfStringsMatchingLastWord(String[] args, String... possibilities) {
/* 679 */     return getListOfStringsMatchingLastWord(args, Arrays.asList((Object[])possibilities));
/*     */   }
/*     */ 
/*     */   
/*     */   public static List<String> getListOfStringsMatchingLastWord(String[] p_175762_0_, Collection<?> p_175762_1_) {
/* 684 */     String s = p_175762_0_[p_175762_0_.length - 1];
/* 685 */     List<String> list = Lists.newArrayList();
/*     */     
/* 687 */     if (!p_175762_1_.isEmpty()) {
/*     */       
/* 689 */       for (String s1 : Iterables.transform(p_175762_1_, Functions.toStringFunction())) {
/*     */         
/* 691 */         if (doesStringStartWith(s, s1))
/*     */         {
/* 693 */           list.add(s1);
/*     */         }
/*     */       } 
/*     */       
/* 697 */       if (list.isEmpty())
/*     */       {
/* 699 */         for (Object object : p_175762_1_) {
/*     */           
/* 701 */           if (object instanceof ResourceLocation && doesStringStartWith(s, ((ResourceLocation)object).getResourcePath()))
/*     */           {
/* 703 */             list.add(String.valueOf(object));
/*     */           }
/*     */         } 
/*     */       }
/*     */     } 
/*     */     
/* 709 */     return list;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isUsernameIndex(String[] args, int index) {
/* 720 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public static void notifyOperators(ICommandSender sender, ICommand command, String msgFormat, Object... msgParams) {
/* 725 */     notifyOperators(sender, command, 0, msgFormat, msgParams);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void notifyOperators(ICommandSender sender, ICommand command, int p_152374_2_, String msgFormat, Object... msgParams) {
/* 730 */     if (theAdmin != null)
/*     */     {
/* 732 */       theAdmin.notifyOperators(sender, command, p_152374_2_, msgFormat, msgParams);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void setAdminCommander(IAdminCommand command) {
/* 741 */     theAdmin = command;
/*     */   }
/*     */ 
/*     */   
/*     */   public int compareTo(ICommand p_compareTo_1_) {
/* 746 */     return getCommandName().compareTo(p_compareTo_1_.getCommandName());
/*     */   }
/*     */ 
/*     */   
/*     */   public static class CoordinateArg
/*     */   {
/*     */     private final double field_179633_a;
/*     */     private final double field_179631_b;
/*     */     private final boolean field_179632_c;
/*     */     
/*     */     protected CoordinateArg(double p_i46051_1_, double p_i46051_3_, boolean p_i46051_5_) {
/* 757 */       this.field_179633_a = p_i46051_1_;
/* 758 */       this.field_179631_b = p_i46051_3_;
/* 759 */       this.field_179632_c = p_i46051_5_;
/*     */     }
/*     */ 
/*     */     
/*     */     public double func_179628_a() {
/* 764 */       return this.field_179633_a;
/*     */     }
/*     */ 
/*     */     
/*     */     public double func_179629_b() {
/* 769 */       return this.field_179631_b;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean func_179630_c() {
/* 774 */       return this.field_179632_c;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\minecraft\command\CommandBase.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */