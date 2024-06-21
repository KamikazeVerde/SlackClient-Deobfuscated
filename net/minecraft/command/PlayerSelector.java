/*     */ package net.minecraft.command;
/*     */ 
/*     */ import com.google.common.base.Predicate;
/*     */ import com.google.common.base.Predicates;
/*     */ import com.google.common.collect.ComparisonChain;
/*     */ import com.google.common.collect.Lists;
/*     */ import com.google.common.collect.Maps;
/*     */ import com.google.common.collect.Sets;
/*     */ import java.util.Collections;
/*     */ import java.util.Comparator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import java.util.regex.Matcher;
/*     */ import java.util.regex.Pattern;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityList;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.player.EntityPlayerMP;
/*     */ import net.minecraft.scoreboard.Score;
/*     */ import net.minecraft.scoreboard.ScoreObjective;
/*     */ import net.minecraft.scoreboard.Scoreboard;
/*     */ import net.minecraft.scoreboard.Team;
/*     */ import net.minecraft.server.MinecraftServer;
/*     */ import net.minecraft.util.AxisAlignedBB;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.ChatComponentTranslation;
/*     */ import net.minecraft.util.ChatFormatting;
/*     */ import net.minecraft.util.EntitySelectors;
/*     */ import net.minecraft.util.IChatComponent;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraft.world.WorldSettings;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PlayerSelector
/*     */ {
/*  42 */   private static final Pattern tokenPattern = Pattern.compile("^@([pare])(?:\\[([\\w=,!-]*)\\])?$");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  47 */   private static final Pattern intListPattern = Pattern.compile("\\G([-!]?[\\w-]*)(?:$|,)");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  52 */   private static final Pattern keyValueListPattern = Pattern.compile("\\G(\\w+)=([-!]?[\\w-]*)(?:$|,)");
/*  53 */   private static final Set<String> WORLD_BINDING_ARGS = Sets.newHashSet((Object[])new String[] { "x", "y", "z", "dx", "dy", "dz", "rm", "r" });
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static EntityPlayerMP matchOnePlayer(ICommandSender sender, String token) {
/*  63 */     return matchOneEntity(sender, token, EntityPlayerMP.class);
/*     */   }
/*     */ 
/*     */   
/*     */   public static <T extends Entity> T matchOneEntity(ICommandSender sender, String token, Class<? extends T> targetClass) {
/*  68 */     List<T> list = matchEntities(sender, token, targetClass);
/*  69 */     return (list.size() == 1) ? list.get(0) : null;
/*     */   }
/*     */ 
/*     */   
/*     */   public static IChatComponent matchEntitiesToChatComponent(ICommandSender sender, String token) {
/*  74 */     List<Entity> list = matchEntities(sender, token, Entity.class);
/*     */     
/*  76 */     if (list.isEmpty())
/*     */     {
/*  78 */       return null;
/*     */     }
/*     */ 
/*     */     
/*  82 */     List<IChatComponent> list1 = Lists.newArrayList();
/*     */     
/*  84 */     for (Entity entity : list)
/*     */     {
/*  86 */       list1.add(entity.getDisplayName());
/*     */     }
/*     */     
/*  89 */     return CommandBase.join(list1);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static <T extends Entity> List<T> matchEntities(ICommandSender sender, String token, Class<? extends T> targetClass) {
/*  95 */     Matcher matcher = tokenPattern.matcher(token);
/*     */     
/*  97 */     if (matcher.matches() && sender.canCommandSenderUseCommand(1, "@")) {
/*     */       
/*  99 */       Map<String, String> map = getArgumentMap(matcher.group(2));
/*     */       
/* 101 */       if (!isEntityTypeValid(sender, map))
/*     */       {
/* 103 */         return Collections.emptyList();
/*     */       }
/*     */ 
/*     */       
/* 107 */       String s = matcher.group(1);
/* 108 */       BlockPos blockpos = func_179664_b(map, sender.getPosition());
/* 109 */       List<World> list = getWorlds(sender, map);
/* 110 */       List<T> list1 = Lists.newArrayList();
/*     */       
/* 112 */       for (World world : list) {
/*     */         
/* 114 */         if (world != null) {
/*     */           
/* 116 */           List<Predicate<Entity>> list2 = Lists.newArrayList();
/* 117 */           list2.addAll(func_179663_a(map, s));
/* 118 */           list2.addAll(func_179648_b(map));
/* 119 */           list2.addAll(func_179649_c(map));
/* 120 */           list2.addAll(func_179659_d(map));
/* 121 */           list2.addAll(func_179657_e(map));
/* 122 */           list2.addAll(func_179647_f(map));
/* 123 */           list2.addAll(func_180698_a(map, blockpos));
/* 124 */           list2.addAll(func_179662_g(map));
/* 125 */           list1.addAll(filterResults(map, targetClass, list2, s, world, blockpos));
/*     */         } 
/*     */       } 
/*     */       
/* 129 */       return func_179658_a(list1, map, sender, targetClass, s, blockpos);
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 134 */     return Collections.emptyList();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static List<World> getWorlds(ICommandSender sender, Map<String, String> argumentMap) {
/* 140 */     List<World> list = Lists.newArrayList();
/*     */     
/* 142 */     if (func_179665_h(argumentMap)) {
/*     */       
/* 144 */       list.add(sender.getEntityWorld());
/*     */     }
/*     */     else {
/*     */       
/* 148 */       Collections.addAll(list, (Object[])(MinecraftServer.getServer()).worldServers);
/*     */     } 
/*     */     
/* 151 */     return list;
/*     */   }
/*     */ 
/*     */   
/*     */   private static <T extends Entity> boolean isEntityTypeValid(ICommandSender commandSender, Map<String, String> params) {
/* 156 */     String s = func_179651_b(params, "type");
/* 157 */     s = (s != null && s.startsWith("!")) ? s.substring(1) : s;
/*     */     
/* 159 */     if (s != null && !EntityList.isStringValidEntityName(s)) {
/*     */       
/* 161 */       ChatComponentTranslation chatcomponenttranslation = new ChatComponentTranslation("commands.generic.entity.invalidType", new Object[] { s });
/* 162 */       chatcomponenttranslation.getChatStyle().setColor(ChatFormatting.RED);
/* 163 */       commandSender.addChatMessage((IChatComponent)chatcomponenttranslation);
/* 164 */       return false;
/*     */     } 
/*     */ 
/*     */     
/* 168 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static List<Predicate<Entity>> func_179663_a(Map<String, String> p_179663_0_, String p_179663_1_) {
/* 174 */     List<Predicate<Entity>> list = Lists.newArrayList();
/* 175 */     String s = func_179651_b(p_179663_0_, "type");
/* 176 */     final boolean flag = (s != null && s.startsWith("!"));
/*     */     
/* 178 */     if (flag)
/*     */     {
/* 180 */       s = s.substring(1);
/*     */     }
/*     */     
/* 183 */     boolean flag1 = !p_179663_1_.equals("e");
/* 184 */     boolean flag2 = (p_179663_1_.equals("r") && s != null);
/*     */     
/* 186 */     if ((s == null || !p_179663_1_.equals("e")) && !flag2) {
/*     */       
/* 188 */       if (flag1)
/*     */       {
/* 190 */         list.add(new Predicate<Entity>()
/*     */             {
/*     */               public boolean apply(Entity p_apply_1_)
/*     */               {
/* 194 */                 return p_apply_1_ instanceof net.minecraft.entity.player.EntityPlayer;
/*     */               }
/*     */             });
/*     */       }
/*     */     }
/*     */     else {
/*     */       
/* 201 */       final String s_f = s;
/* 202 */       list.add(new Predicate<Entity>()
/*     */           {
/*     */             public boolean apply(Entity p_apply_1_)
/*     */             {
/* 206 */               return (EntityList.isStringEntityName(p_apply_1_, s_f) != flag);
/*     */             }
/*     */           });
/*     */     } 
/*     */     
/* 211 */     return list;
/*     */   }
/*     */ 
/*     */   
/*     */   private static List<Predicate<Entity>> func_179648_b(Map<String, String> p_179648_0_) {
/* 216 */     List<Predicate<Entity>> list = Lists.newArrayList();
/* 217 */     final int i = parseIntWithDefault(p_179648_0_, "lm", -1);
/* 218 */     final int j = parseIntWithDefault(p_179648_0_, "l", -1);
/*     */     
/* 220 */     if (i > -1 || j > -1)
/*     */     {
/* 222 */       list.add(new Predicate<Entity>()
/*     */           {
/*     */             public boolean apply(Entity p_apply_1_)
/*     */             {
/* 226 */               if (!(p_apply_1_ instanceof EntityPlayerMP))
/*     */               {
/* 228 */                 return false;
/*     */               }
/*     */ 
/*     */               
/* 232 */               EntityPlayerMP entityplayermp = (EntityPlayerMP)p_apply_1_;
/* 233 */               return ((i <= -1 || entityplayermp.experienceLevel >= i) && (j <= -1 || entityplayermp.experienceLevel <= j));
/*     */             }
/*     */           });
/*     */     }
/*     */ 
/*     */     
/* 239 */     return list;
/*     */   }
/*     */ 
/*     */   
/*     */   private static List<Predicate<Entity>> func_179649_c(Map<String, String> p_179649_0_) {
/* 244 */     List<Predicate<Entity>> list = Lists.newArrayList();
/* 245 */     final int i = parseIntWithDefault(p_179649_0_, "m", WorldSettings.GameType.NOT_SET.getID());
/*     */     
/* 247 */     if (i != WorldSettings.GameType.NOT_SET.getID())
/*     */     {
/* 249 */       list.add(new Predicate<Entity>()
/*     */           {
/*     */             public boolean apply(Entity p_apply_1_)
/*     */             {
/* 253 */               if (!(p_apply_1_ instanceof EntityPlayerMP))
/*     */               {
/* 255 */                 return false;
/*     */               }
/*     */ 
/*     */               
/* 259 */               EntityPlayerMP entityplayermp = (EntityPlayerMP)p_apply_1_;
/* 260 */               return (entityplayermp.theItemInWorldManager.getGameType().getID() == i);
/*     */             }
/*     */           });
/*     */     }
/*     */ 
/*     */     
/* 266 */     return list;
/*     */   }
/*     */ 
/*     */   
/*     */   private static List<Predicate<Entity>> func_179659_d(Map<String, String> p_179659_0_) {
/* 271 */     List<Predicate<Entity>> list = Lists.newArrayList();
/* 272 */     String s = func_179651_b(p_179659_0_, "team");
/* 273 */     final boolean flag = (s != null && s.startsWith("!"));
/*     */     
/* 275 */     if (flag)
/*     */     {
/* 277 */       s = s.substring(1);
/*     */     }
/*     */     
/* 280 */     if (s != null) {
/*     */       
/* 282 */       final String s_f = s;
/* 283 */       list.add(new Predicate<Entity>()
/*     */           {
/*     */             public boolean apply(Entity p_apply_1_)
/*     */             {
/* 287 */               if (!(p_apply_1_ instanceof EntityLivingBase))
/*     */               {
/* 289 */                 return false;
/*     */               }
/*     */ 
/*     */               
/* 293 */               EntityLivingBase entitylivingbase = (EntityLivingBase)p_apply_1_;
/* 294 */               Team team = entitylivingbase.getTeam();
/* 295 */               String s1 = (team == null) ? "" : team.getRegisteredName();
/* 296 */               return (s1.equals(s_f) != flag);
/*     */             }
/*     */           });
/*     */     } 
/*     */ 
/*     */     
/* 302 */     return list;
/*     */   }
/*     */ 
/*     */   
/*     */   private static List<Predicate<Entity>> func_179657_e(Map<String, String> p_179657_0_) {
/* 307 */     List<Predicate<Entity>> list = Lists.newArrayList();
/* 308 */     final Map<String, Integer> map = func_96560_a(p_179657_0_);
/*     */     
/* 310 */     if (map != null && map.size() > 0)
/*     */     {
/* 312 */       list.add(new Predicate<Entity>()
/*     */           {
/*     */             public boolean apply(Entity p_apply_1_)
/*     */             {
/* 316 */               Scoreboard scoreboard = MinecraftServer.getServer().worldServerForDimension(0).getScoreboard();
/*     */               
/* 318 */               for (Map.Entry<String, Integer> entry : (Iterable<Map.Entry<String, Integer>>)map.entrySet()) {
/*     */                 
/* 320 */                 String s = entry.getKey();
/* 321 */                 boolean flag = false;
/*     */                 
/* 323 */                 if (s.endsWith("_min") && s.length() > 4) {
/*     */                   
/* 325 */                   flag = true;
/* 326 */                   s = s.substring(0, s.length() - 4);
/*     */                 } 
/*     */                 
/* 329 */                 ScoreObjective scoreobjective = scoreboard.getObjective(s);
/*     */                 
/* 331 */                 if (scoreobjective == null)
/*     */                 {
/* 333 */                   return false;
/*     */                 }
/*     */                 
/* 336 */                 String s1 = (p_apply_1_ instanceof EntityPlayerMP) ? p_apply_1_.getCommandSenderName() : p_apply_1_.getUniqueID().toString();
/*     */                 
/* 338 */                 if (!scoreboard.entityHasObjective(s1, scoreobjective))
/*     */                 {
/* 340 */                   return false;
/*     */                 }
/*     */                 
/* 343 */                 Score score = scoreboard.getValueFromObjective(s1, scoreobjective);
/* 344 */                 int i = score.getScorePoints();
/*     */                 
/* 346 */                 if (i < ((Integer)entry.getValue()).intValue() && flag)
/*     */                 {
/* 348 */                   return false;
/*     */                 }
/*     */                 
/* 351 */                 if (i > ((Integer)entry.getValue()).intValue() && !flag)
/*     */                 {
/* 353 */                   return false;
/*     */                 }
/*     */               } 
/*     */               
/* 357 */               return true;
/*     */             }
/*     */           });
/*     */     }
/*     */     
/* 362 */     return list;
/*     */   }
/*     */ 
/*     */   
/*     */   private static List<Predicate<Entity>> func_179647_f(Map<String, String> p_179647_0_) {
/* 367 */     List<Predicate<Entity>> list = Lists.newArrayList();
/* 368 */     String s = func_179651_b(p_179647_0_, "name");
/* 369 */     final boolean flag = (s != null && s.startsWith("!"));
/*     */     
/* 371 */     if (flag)
/*     */     {
/* 373 */       s = s.substring(1);
/*     */     }
/*     */     
/* 376 */     if (s != null) {
/*     */       
/* 378 */       final String s_f = s;
/* 379 */       list.add(new Predicate<Entity>()
/*     */           {
/*     */             public boolean apply(Entity p_apply_1_)
/*     */             {
/* 383 */               return (p_apply_1_.getCommandSenderName().equals(s_f) != flag);
/*     */             }
/*     */           });
/*     */     } 
/*     */     
/* 388 */     return list;
/*     */   }
/*     */ 
/*     */   
/*     */   private static List<Predicate<Entity>> func_180698_a(Map<String, String> p_180698_0_, final BlockPos p_180698_1_) {
/* 393 */     List<Predicate<Entity>> list = Lists.newArrayList();
/* 394 */     final int i = parseIntWithDefault(p_180698_0_, "rm", -1);
/* 395 */     final int j = parseIntWithDefault(p_180698_0_, "r", -1);
/*     */     
/* 397 */     if (p_180698_1_ != null && (i >= 0 || j >= 0)) {
/*     */       
/* 399 */       final int k = i * i;
/* 400 */       final int l = j * j;
/* 401 */       list.add(new Predicate<Entity>()
/*     */           {
/*     */             public boolean apply(Entity p_apply_1_)
/*     */             {
/* 405 */               int i1 = (int)p_apply_1_.getDistanceSqToCenter(p_180698_1_);
/* 406 */               return ((i < 0 || i1 >= k) && (j < 0 || i1 <= l));
/*     */             }
/*     */           });
/*     */     } 
/*     */     
/* 411 */     return list;
/*     */   }
/*     */ 
/*     */   
/*     */   private static List<Predicate<Entity>> func_179662_g(Map<String, String> p_179662_0_) {
/* 416 */     List<Predicate<Entity>> list = Lists.newArrayList();
/*     */     
/* 418 */     if (p_179662_0_.containsKey("rym") || p_179662_0_.containsKey("ry")) {
/*     */       
/* 420 */       final int i = func_179650_a(parseIntWithDefault(p_179662_0_, "rym", 0));
/* 421 */       final int j = func_179650_a(parseIntWithDefault(p_179662_0_, "ry", 359));
/* 422 */       list.add(new Predicate<Entity>()
/*     */           {
/*     */             public boolean apply(Entity p_apply_1_)
/*     */             {
/* 426 */               int i1 = PlayerSelector.func_179650_a((int)Math.floor(p_apply_1_.rotationYaw));
/* 427 */               return (i > j) ? ((i1 >= i || i1 <= j)) : ((i1 >= i && i1 <= j));
/*     */             }
/*     */           });
/*     */     } 
/*     */     
/* 432 */     if (p_179662_0_.containsKey("rxm") || p_179662_0_.containsKey("rx")) {
/*     */       
/* 434 */       final int k = func_179650_a(parseIntWithDefault(p_179662_0_, "rxm", 0));
/* 435 */       final int l = func_179650_a(parseIntWithDefault(p_179662_0_, "rx", 359));
/* 436 */       list.add(new Predicate<Entity>()
/*     */           {
/*     */             public boolean apply(Entity p_apply_1_)
/*     */             {
/* 440 */               int i1 = PlayerSelector.func_179650_a((int)Math.floor(p_apply_1_.rotationPitch));
/* 441 */               return (k > l) ? ((i1 >= k || i1 <= l)) : ((i1 >= k && i1 <= l));
/*     */             }
/*     */           });
/*     */     } 
/*     */     
/* 446 */     return list;
/*     */   }
/*     */ 
/*     */   
/*     */   private static <T extends Entity> List<T> filterResults(Map<String, String> params, Class<? extends T> entityClass, List<Predicate<Entity>> inputList, String type, World worldIn, BlockPos position) {
/* 451 */     List<T> list = Lists.newArrayList();
/* 452 */     String s = func_179651_b(params, "type");
/* 453 */     s = (s != null && s.startsWith("!")) ? s.substring(1) : s;
/* 454 */     boolean flag = !type.equals("e");
/* 455 */     boolean flag1 = (type.equals("r") && s != null);
/* 456 */     int i = parseIntWithDefault(params, "dx", 0);
/* 457 */     int j = parseIntWithDefault(params, "dy", 0);
/* 458 */     int k = parseIntWithDefault(params, "dz", 0);
/* 459 */     int l = parseIntWithDefault(params, "r", -1);
/* 460 */     Predicate<Entity> predicate = Predicates.and(inputList);
/* 461 */     Predicate<Entity> predicate1 = Predicates.and(EntitySelectors.selectAnything, predicate);
/*     */     
/* 463 */     if (position != null) {
/*     */       
/* 465 */       int i1 = worldIn.playerEntities.size();
/* 466 */       int j1 = worldIn.loadedEntityList.size();
/* 467 */       boolean flag2 = (i1 < j1 / 16);
/*     */       
/* 469 */       if (!params.containsKey("dx") && !params.containsKey("dy") && !params.containsKey("dz")) {
/*     */         
/* 471 */         if (l >= 0) {
/*     */           
/* 473 */           AxisAlignedBB axisalignedbb1 = new AxisAlignedBB((position.getX() - l), (position.getY() - l), (position.getZ() - l), (position.getX() + l + 1), (position.getY() + l + 1), (position.getZ() + l + 1));
/*     */           
/* 475 */           if (flag && flag2 && !flag1)
/*     */           {
/* 477 */             list.addAll(worldIn.getPlayers(entityClass, predicate1));
/*     */           }
/*     */           else
/*     */           {
/* 481 */             list.addAll(worldIn.getEntitiesWithinAABB(entityClass, axisalignedbb1, predicate1));
/*     */           }
/*     */         
/* 484 */         } else if (type.equals("a")) {
/*     */           
/* 486 */           list.addAll(worldIn.getPlayers(entityClass, predicate));
/*     */         }
/* 488 */         else if (!type.equals("p") && (!type.equals("r") || flag1)) {
/*     */           
/* 490 */           list.addAll(worldIn.getEntities(entityClass, predicate1));
/*     */         }
/*     */         else {
/*     */           
/* 494 */           list.addAll(worldIn.getPlayers(entityClass, predicate1));
/*     */         }
/*     */       
/*     */       } else {
/*     */         
/* 499 */         final AxisAlignedBB axisalignedbb = func_179661_a(position, i, j, k);
/*     */         
/* 501 */         if (flag && flag2 && !flag1)
/*     */         {
/* 503 */           Predicate<Entity> predicate2 = new Predicate<Entity>()
/*     */             {
/*     */               public boolean apply(Entity p_apply_1_)
/*     */               {
/* 507 */                 return (p_apply_1_.posX >= axisalignedbb.minX && p_apply_1_.posY >= axisalignedbb.minY && p_apply_1_.posZ >= axisalignedbb.minZ) ? ((p_apply_1_.posX < axisalignedbb.maxX && p_apply_1_.posY < axisalignedbb.maxY && p_apply_1_.posZ < axisalignedbb.maxZ)) : false;
/*     */               }
/*     */             };
/* 510 */           list.addAll(worldIn.getPlayers(entityClass, Predicates.and(predicate1, predicate2)));
/*     */         }
/*     */         else
/*     */         {
/* 514 */           list.addAll(worldIn.getEntitiesWithinAABB(entityClass, axisalignedbb, predicate1));
/*     */         }
/*     */       
/*     */       } 
/* 518 */     } else if (type.equals("a")) {
/*     */       
/* 520 */       list.addAll(worldIn.getPlayers(entityClass, predicate));
/*     */     }
/* 522 */     else if (!type.equals("p") && (!type.equals("r") || flag1)) {
/*     */       
/* 524 */       list.addAll(worldIn.getEntities(entityClass, predicate1));
/*     */     }
/*     */     else {
/*     */       
/* 528 */       list.addAll(worldIn.getPlayers(entityClass, predicate1));
/*     */     } 
/*     */     
/* 531 */     return list;
/*     */   }
/*     */ 
/*     */   
/*     */   private static <T extends Entity> List<T> func_179658_a(List<T> p_179658_0_, Map<String, String> p_179658_1_, ICommandSender p_179658_2_, Class<? extends T> p_179658_3_, String p_179658_4_, final BlockPos p_179658_5_) {
/* 536 */     int i = parseIntWithDefault(p_179658_1_, "c", (!p_179658_4_.equals("a") && !p_179658_4_.equals("e")) ? 1 : 0);
/*     */     
/* 538 */     if (!p_179658_4_.equals("p") && !p_179658_4_.equals("a") && !p_179658_4_.equals("e")) {
/*     */       
/* 540 */       if (p_179658_4_.equals("r"))
/*     */       {
/* 542 */         Collections.shuffle(p_179658_0_);
/*     */       }
/*     */     }
/* 545 */     else if (p_179658_5_ != null) {
/*     */       
/* 547 */       Collections.sort(p_179658_0_, (Comparator)new Comparator<Entity>()
/*     */           {
/*     */             public int compare(Entity p_compare_1_, Entity p_compare_2_)
/*     */             {
/* 551 */               return ComparisonChain.start().compare(p_compare_1_.getDistanceSq(p_179658_5_), p_compare_2_.getDistanceSq(p_179658_5_)).result();
/*     */             }
/*     */           });
/*     */     } 
/*     */     
/* 556 */     Entity entity = p_179658_2_.getCommandSenderEntity();
/*     */     
/* 558 */     if (entity != null && p_179658_3_.isAssignableFrom(entity.getClass()) && i == 1 && p_179658_0_.contains(entity) && !"r".equals(p_179658_4_))
/*     */     {
/* 560 */       p_179658_0_ = Lists.newArrayList((Object[])new Entity[] { entity });
/*     */     }
/*     */     
/* 563 */     if (i != 0) {
/*     */       
/* 565 */       if (i < 0)
/*     */       {
/* 567 */         Collections.reverse(p_179658_0_);
/*     */       }
/*     */       
/* 570 */       p_179658_0_ = p_179658_0_.subList(0, Math.min(Math.abs(i), p_179658_0_.size()));
/*     */     } 
/*     */     
/* 573 */     return p_179658_0_;
/*     */   }
/*     */ 
/*     */   
/*     */   private static AxisAlignedBB func_179661_a(BlockPos p_179661_0_, int p_179661_1_, int p_179661_2_, int p_179661_3_) {
/* 578 */     boolean flag = (p_179661_1_ < 0);
/* 579 */     boolean flag1 = (p_179661_2_ < 0);
/* 580 */     boolean flag2 = (p_179661_3_ < 0);
/* 581 */     int i = p_179661_0_.getX() + (flag ? p_179661_1_ : 0);
/* 582 */     int j = p_179661_0_.getY() + (flag1 ? p_179661_2_ : 0);
/* 583 */     int k = p_179661_0_.getZ() + (flag2 ? p_179661_3_ : 0);
/* 584 */     int l = p_179661_0_.getX() + (flag ? 0 : p_179661_1_) + 1;
/* 585 */     int i1 = p_179661_0_.getY() + (flag1 ? 0 : p_179661_2_) + 1;
/* 586 */     int j1 = p_179661_0_.getZ() + (flag2 ? 0 : p_179661_3_) + 1;
/* 587 */     return new AxisAlignedBB(i, j, k, l, i1, j1);
/*     */   }
/*     */ 
/*     */   
/*     */   public static int func_179650_a(int p_179650_0_) {
/* 592 */     p_179650_0_ %= 360;
/*     */     
/* 594 */     if (p_179650_0_ >= 160)
/*     */     {
/* 596 */       p_179650_0_ -= 360;
/*     */     }
/*     */     
/* 599 */     if (p_179650_0_ < 0)
/*     */     {
/* 601 */       p_179650_0_ += 360;
/*     */     }
/*     */     
/* 604 */     return p_179650_0_;
/*     */   }
/*     */ 
/*     */   
/*     */   private static BlockPos func_179664_b(Map<String, String> p_179664_0_, BlockPos p_179664_1_) {
/* 609 */     return new BlockPos(parseIntWithDefault(p_179664_0_, "x", p_179664_1_.getX()), parseIntWithDefault(p_179664_0_, "y", p_179664_1_.getY()), parseIntWithDefault(p_179664_0_, "z", p_179664_1_.getZ()));
/*     */   }
/*     */ 
/*     */   
/*     */   private static boolean func_179665_h(Map<String, String> p_179665_0_) {
/* 614 */     for (String s : WORLD_BINDING_ARGS) {
/*     */       
/* 616 */       if (p_179665_0_.containsKey(s))
/*     */       {
/* 618 */         return true;
/*     */       }
/*     */     } 
/*     */     
/* 622 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   private static int parseIntWithDefault(Map<String, String> p_179653_0_, String p_179653_1_, int p_179653_2_) {
/* 627 */     return p_179653_0_.containsKey(p_179653_1_) ? MathHelper.parseIntWithDefault(p_179653_0_.get(p_179653_1_), p_179653_2_) : p_179653_2_;
/*     */   }
/*     */ 
/*     */   
/*     */   private static String func_179651_b(Map<String, String> p_179651_0_, String p_179651_1_) {
/* 632 */     return p_179651_0_.get(p_179651_1_);
/*     */   }
/*     */ 
/*     */   
/*     */   public static Map<String, Integer> func_96560_a(Map<String, String> p_96560_0_) {
/* 637 */     Map<String, Integer> map = Maps.newHashMap();
/*     */     
/* 639 */     for (String s : p_96560_0_.keySet()) {
/*     */       
/* 641 */       if (s.startsWith("score_") && s.length() > "score_".length())
/*     */       {
/* 643 */         map.put(s.substring("score_".length()), Integer.valueOf(MathHelper.parseIntWithDefault(p_96560_0_.get(s), 1)));
/*     */       }
/*     */     } 
/*     */     
/* 647 */     return map;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean matchesMultiplePlayers(String p_82377_0_) {
/* 655 */     Matcher matcher = tokenPattern.matcher(p_82377_0_);
/*     */     
/* 657 */     if (!matcher.matches())
/*     */     {
/* 659 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 663 */     Map<String, String> map = getArgumentMap(matcher.group(2));
/* 664 */     String s = matcher.group(1);
/* 665 */     int i = (!"a".equals(s) && !"e".equals(s)) ? 1 : 0;
/* 666 */     return (parseIntWithDefault(map, "c", i) != 1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean hasArguments(String p_82378_0_) {
/* 675 */     return tokenPattern.matcher(p_82378_0_).matches();
/*     */   }
/*     */ 
/*     */   
/*     */   private static Map<String, String> getArgumentMap(String argumentString) {
/* 680 */     Map<String, String> map = Maps.newHashMap();
/*     */     
/* 682 */     if (argumentString == null)
/*     */     {
/* 684 */       return map;
/*     */     }
/*     */ 
/*     */     
/* 688 */     int i = 0;
/* 689 */     int j = -1;
/*     */     
/* 691 */     for (Matcher matcher = intListPattern.matcher(argumentString); matcher.find(); j = matcher.end()) {
/*     */       
/* 693 */       String s = null;
/*     */       
/* 695 */       switch (i++) {
/*     */         
/*     */         case 0:
/* 698 */           s = "x";
/*     */           break;
/*     */         
/*     */         case 1:
/* 702 */           s = "y";
/*     */           break;
/*     */         
/*     */         case 2:
/* 706 */           s = "z";
/*     */           break;
/*     */         
/*     */         case 3:
/* 710 */           s = "r";
/*     */           break;
/*     */       } 
/* 713 */       if (s != null && matcher.group(1).length() > 0)
/*     */       {
/* 715 */         map.put(s, matcher.group(1));
/*     */       }
/*     */     } 
/*     */     
/* 719 */     if (j < argumentString.length()) {
/*     */       
/* 721 */       Matcher matcher1 = keyValueListPattern.matcher((j == -1) ? argumentString : argumentString.substring(j));
/*     */       
/* 723 */       while (matcher1.find())
/*     */       {
/* 725 */         map.put(matcher1.group(1), matcher1.group(2));
/*     */       }
/*     */     } 
/*     */     
/* 729 */     return map;
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\minecraft\command\PlayerSelector.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */