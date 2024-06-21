/*     */ package net.minecraft.command;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import com.google.common.collect.Maps;
/*     */ import com.google.common.collect.Sets;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Random;
/*     */ import java.util.Set;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.entity.player.EntityPlayerMP;
/*     */ import net.minecraft.scoreboard.Team;
/*     */ import net.minecraft.server.MinecraftServer;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.ChatComponentTranslation;
/*     */ import net.minecraft.util.IChatComponent;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ 
/*     */ public class CommandSpreadPlayers
/*     */   extends CommandBase
/*     */ {
/*     */   public String getCommandName() {
/*  27 */     return "spreadplayers";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getRequiredPermissionLevel() {
/*  35 */     return 2;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getCommandUsage(ICommandSender sender) {
/*  45 */     return "commands.spreadplayers.usage";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void processCommand(ICommandSender sender, String[] args) throws CommandException {
/*  56 */     if (args.length < 6)
/*     */     {
/*  58 */       throw new WrongUsageException("commands.spreadplayers.usage", new Object[0]);
/*     */     }
/*     */ 
/*     */     
/*  62 */     int i = 0;
/*  63 */     BlockPos blockpos = sender.getPosition();
/*  64 */     double d0 = parseDouble(blockpos.getX(), args[i++], true);
/*  65 */     double d1 = parseDouble(blockpos.getZ(), args[i++], true);
/*  66 */     double d2 = parseDouble(args[i++], 0.0D);
/*  67 */     double d3 = parseDouble(args[i++], d2 + 1.0D);
/*  68 */     boolean flag = parseBoolean(args[i++]);
/*  69 */     List<Entity> list = Lists.newArrayList();
/*     */     
/*  71 */     while (i < args.length) {
/*     */       
/*  73 */       String s = args[i++];
/*     */       
/*  75 */       if (PlayerSelector.hasArguments(s)) {
/*     */         
/*  77 */         List<Entity> list1 = PlayerSelector.matchEntities(sender, s, Entity.class);
/*     */         
/*  79 */         if (list1.size() == 0)
/*     */         {
/*  81 */           throw new EntityNotFoundException();
/*     */         }
/*     */         
/*  84 */         list.addAll(list1);
/*     */         
/*     */         continue;
/*     */       } 
/*  88 */       EntityPlayerMP entityPlayerMP = MinecraftServer.getServer().getConfigurationManager().getPlayerByUsername(s);
/*     */       
/*  90 */       if (entityPlayerMP == null)
/*     */       {
/*  92 */         throw new PlayerNotFoundException();
/*     */       }
/*     */       
/*  95 */       list.add(entityPlayerMP);
/*     */     } 
/*     */ 
/*     */     
/*  99 */     sender.setCommandStat(CommandResultStats.Type.AFFECTED_ENTITIES, list.size());
/*     */     
/* 101 */     if (list.isEmpty())
/*     */     {
/* 103 */       throw new EntityNotFoundException();
/*     */     }
/*     */ 
/*     */     
/* 107 */     sender.addChatMessage((IChatComponent)new ChatComponentTranslation("commands.spreadplayers.spreading." + (flag ? "teams" : "players"), new Object[] { Integer.valueOf(list.size()), Double.valueOf(d3), Double.valueOf(d0), Double.valueOf(d1), Double.valueOf(d2) }));
/* 108 */     func_110669_a(sender, list, new Position(d0, d1), d2, d3, ((Entity)list.get(0)).worldObj, flag);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void func_110669_a(ICommandSender p_110669_1_, List<Entity> p_110669_2_, Position p_110669_3_, double p_110669_4_, double p_110669_6_, World worldIn, boolean p_110669_9_) throws CommandException {
/* 115 */     Random random = new Random();
/* 116 */     double d0 = p_110669_3_.field_111101_a - p_110669_6_;
/* 117 */     double d1 = p_110669_3_.field_111100_b - p_110669_6_;
/* 118 */     double d2 = p_110669_3_.field_111101_a + p_110669_6_;
/* 119 */     double d3 = p_110669_3_.field_111100_b + p_110669_6_;
/* 120 */     Position[] acommandspreadplayers$position = func_110670_a(random, p_110669_9_ ? func_110667_a(p_110669_2_) : p_110669_2_.size(), d0, d1, d2, d3);
/* 121 */     int i = func_110668_a(p_110669_3_, p_110669_4_, worldIn, random, d0, d1, d2, d3, acommandspreadplayers$position, p_110669_9_);
/* 122 */     double d4 = func_110671_a(p_110669_2_, worldIn, acommandspreadplayers$position, p_110669_9_);
/* 123 */     notifyOperators(p_110669_1_, this, "commands.spreadplayers.success." + (p_110669_9_ ? "teams" : "players"), new Object[] { Integer.valueOf(acommandspreadplayers$position.length), Double.valueOf(p_110669_3_.field_111101_a), Double.valueOf(p_110669_3_.field_111100_b) });
/*     */     
/* 125 */     if (acommandspreadplayers$position.length > 1)
/*     */     {
/* 127 */       p_110669_1_.addChatMessage((IChatComponent)new ChatComponentTranslation("commands.spreadplayers.info." + (p_110669_9_ ? "teams" : "players"), new Object[] { String.format("%.2f", new Object[] { Double.valueOf(d4) }), Integer.valueOf(i) }));
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private int func_110667_a(List<Entity> p_110667_1_) {
/* 133 */     Set<Team> set = Sets.newHashSet();
/*     */     
/* 135 */     for (Entity entity : p_110667_1_) {
/*     */       
/* 137 */       if (entity instanceof EntityPlayer) {
/*     */         
/* 139 */         set.add(((EntityPlayer)entity).getTeam());
/*     */         
/*     */         continue;
/*     */       } 
/* 143 */       set.add(null);
/*     */     } 
/*     */ 
/*     */     
/* 147 */     return set.size();
/*     */   }
/*     */ 
/*     */   
/*     */   private int func_110668_a(Position p_110668_1_, double p_110668_2_, World worldIn, Random p_110668_5_, double p_110668_6_, double p_110668_8_, double p_110668_10_, double p_110668_12_, Position[] p_110668_14_, boolean p_110668_15_) throws CommandException {
/* 152 */     boolean flag = true;
/* 153 */     double d0 = 3.4028234663852886E38D;
/*     */     
/*     */     int i;
/* 156 */     for (i = 0; i < 10000 && flag; i++) {
/*     */       
/* 158 */       flag = false;
/* 159 */       d0 = 3.4028234663852886E38D;
/*     */       
/* 161 */       for (int j = 0; j < p_110668_14_.length; j++) {
/*     */         
/* 163 */         Position commandspreadplayers$position = p_110668_14_[j];
/* 164 */         int k = 0;
/* 165 */         Position commandspreadplayers$position1 = new Position();
/*     */         
/* 167 */         for (int l = 0; l < p_110668_14_.length; l++) {
/*     */           
/* 169 */           if (j != l) {
/*     */             
/* 171 */             Position commandspreadplayers$position2 = p_110668_14_[l];
/* 172 */             double d1 = commandspreadplayers$position.func_111099_a(commandspreadplayers$position2);
/* 173 */             d0 = Math.min(d1, d0);
/*     */             
/* 175 */             if (d1 < p_110668_2_) {
/*     */               
/* 177 */               k++;
/* 178 */               commandspreadplayers$position1.field_111101_a += commandspreadplayers$position2.field_111101_a - commandspreadplayers$position.field_111101_a;
/* 179 */               commandspreadplayers$position1.field_111100_b += commandspreadplayers$position2.field_111100_b - commandspreadplayers$position.field_111100_b;
/*     */             } 
/*     */           } 
/*     */         } 
/*     */         
/* 184 */         if (k > 0) {
/*     */           
/* 186 */           commandspreadplayers$position1.field_111101_a /= k;
/* 187 */           commandspreadplayers$position1.field_111100_b /= k;
/* 188 */           double d2 = commandspreadplayers$position1.func_111096_b();
/*     */           
/* 190 */           if (d2 > 0.0D) {
/*     */             
/* 192 */             commandspreadplayers$position1.func_111095_a();
/* 193 */             commandspreadplayers$position.func_111094_b(commandspreadplayers$position1);
/*     */           }
/*     */           else {
/*     */             
/* 197 */             commandspreadplayers$position.func_111097_a(p_110668_5_, p_110668_6_, p_110668_8_, p_110668_10_, p_110668_12_);
/*     */           } 
/*     */           
/* 200 */           flag = true;
/*     */         } 
/*     */         
/* 203 */         if (commandspreadplayers$position.func_111093_a(p_110668_6_, p_110668_8_, p_110668_10_, p_110668_12_))
/*     */         {
/* 205 */           flag = true;
/*     */         }
/*     */       } 
/*     */       
/* 209 */       if (!flag)
/*     */       {
/* 211 */         for (Position commandspreadplayers$position3 : p_110668_14_) {
/*     */           
/* 213 */           if (!commandspreadplayers$position3.func_111098_b(worldIn)) {
/*     */             
/* 215 */             commandspreadplayers$position3.func_111097_a(p_110668_5_, p_110668_6_, p_110668_8_, p_110668_10_, p_110668_12_);
/* 216 */             flag = true;
/*     */           } 
/*     */         } 
/*     */       }
/*     */     } 
/*     */     
/* 222 */     if (i >= 10000)
/*     */     {
/* 224 */       throw new CommandException("commands.spreadplayers.failure." + (p_110668_15_ ? "teams" : "players"), new Object[] { Integer.valueOf(p_110668_14_.length), Double.valueOf(p_110668_1_.field_111101_a), Double.valueOf(p_110668_1_.field_111100_b), String.format("%.2f", new Object[] { Double.valueOf(d0) }) });
/*     */     }
/*     */ 
/*     */     
/* 228 */     return i;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private double func_110671_a(List<Entity> p_110671_1_, World worldIn, Position[] p_110671_3_, boolean p_110671_4_) {
/* 234 */     double d0 = 0.0D;
/* 235 */     int i = 0;
/* 236 */     Map<Team, Position> map = Maps.newHashMap();
/*     */     
/* 238 */     for (int j = 0; j < p_110671_1_.size(); j++) {
/*     */       Position commandspreadplayers$position;
/* 240 */       Entity entity = p_110671_1_.get(j);
/*     */ 
/*     */       
/* 243 */       if (p_110671_4_) {
/*     */         
/* 245 */         Team team = (entity instanceof EntityPlayer) ? ((EntityPlayer)entity).getTeam() : null;
/*     */         
/* 247 */         if (!map.containsKey(team))
/*     */         {
/* 249 */           map.put(team, p_110671_3_[i++]);
/*     */         }
/*     */         
/* 252 */         commandspreadplayers$position = map.get(team);
/*     */       }
/*     */       else {
/*     */         
/* 256 */         commandspreadplayers$position = p_110671_3_[i++];
/*     */       } 
/*     */       
/* 259 */       entity.setPositionAndUpdate((MathHelper.floor_double(commandspreadplayers$position.field_111101_a) + 0.5F), commandspreadplayers$position.func_111092_a(worldIn), MathHelper.floor_double(commandspreadplayers$position.field_111100_b) + 0.5D);
/* 260 */       double d2 = Double.MAX_VALUE;
/*     */       
/* 262 */       for (int k = 0; k < p_110671_3_.length; k++) {
/*     */         
/* 264 */         if (commandspreadplayers$position != p_110671_3_[k]) {
/*     */           
/* 266 */           double d1 = commandspreadplayers$position.func_111099_a(p_110671_3_[k]);
/* 267 */           d2 = Math.min(d1, d2);
/*     */         } 
/*     */       } 
/*     */       
/* 271 */       d0 += d2;
/*     */     } 
/*     */     
/* 274 */     d0 /= p_110671_1_.size();
/* 275 */     return d0;
/*     */   }
/*     */ 
/*     */   
/*     */   private Position[] func_110670_a(Random p_110670_1_, int p_110670_2_, double p_110670_3_, double p_110670_5_, double p_110670_7_, double p_110670_9_) {
/* 280 */     Position[] acommandspreadplayers$position = new Position[p_110670_2_];
/*     */     
/* 282 */     for (int i = 0; i < acommandspreadplayers$position.length; i++) {
/*     */       
/* 284 */       Position commandspreadplayers$position = new Position();
/* 285 */       commandspreadplayers$position.func_111097_a(p_110670_1_, p_110670_3_, p_110670_5_, p_110670_7_, p_110670_9_);
/* 286 */       acommandspreadplayers$position[i] = commandspreadplayers$position;
/*     */     } 
/*     */     
/* 289 */     return acommandspreadplayers$position;
/*     */   }
/*     */ 
/*     */   
/*     */   public List<String> addTabCompletionOptions(ICommandSender sender, String[] args, BlockPos pos) {
/* 294 */     return (args.length >= 1 && args.length <= 2) ? func_181043_b(args, 0, pos) : null;
/*     */   }
/*     */ 
/*     */   
/*     */   static class Position
/*     */   {
/*     */     double field_111101_a;
/*     */     
/*     */     double field_111100_b;
/*     */ 
/*     */     
/*     */     Position() {}
/*     */     
/*     */     Position(double p_i1358_1_, double p_i1358_3_) {
/* 308 */       this.field_111101_a = p_i1358_1_;
/* 309 */       this.field_111100_b = p_i1358_3_;
/*     */     }
/*     */ 
/*     */     
/*     */     double func_111099_a(Position p_111099_1_) {
/* 314 */       double d0 = this.field_111101_a - p_111099_1_.field_111101_a;
/* 315 */       double d1 = this.field_111100_b - p_111099_1_.field_111100_b;
/* 316 */       return Math.sqrt(d0 * d0 + d1 * d1);
/*     */     }
/*     */ 
/*     */     
/*     */     void func_111095_a() {
/* 321 */       double d0 = func_111096_b();
/* 322 */       this.field_111101_a /= d0;
/* 323 */       this.field_111100_b /= d0;
/*     */     }
/*     */ 
/*     */     
/*     */     float func_111096_b() {
/* 328 */       return MathHelper.sqrt_double(this.field_111101_a * this.field_111101_a + this.field_111100_b * this.field_111100_b);
/*     */     }
/*     */ 
/*     */     
/*     */     public void func_111094_b(Position p_111094_1_) {
/* 333 */       this.field_111101_a -= p_111094_1_.field_111101_a;
/* 334 */       this.field_111100_b -= p_111094_1_.field_111100_b;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean func_111093_a(double p_111093_1_, double p_111093_3_, double p_111093_5_, double p_111093_7_) {
/* 339 */       boolean flag = false;
/*     */       
/* 341 */       if (this.field_111101_a < p_111093_1_) {
/*     */         
/* 343 */         this.field_111101_a = p_111093_1_;
/* 344 */         flag = true;
/*     */       }
/* 346 */       else if (this.field_111101_a > p_111093_5_) {
/*     */         
/* 348 */         this.field_111101_a = p_111093_5_;
/* 349 */         flag = true;
/*     */       } 
/*     */       
/* 352 */       if (this.field_111100_b < p_111093_3_) {
/*     */         
/* 354 */         this.field_111100_b = p_111093_3_;
/* 355 */         flag = true;
/*     */       }
/* 357 */       else if (this.field_111100_b > p_111093_7_) {
/*     */         
/* 359 */         this.field_111100_b = p_111093_7_;
/* 360 */         flag = true;
/*     */       } 
/*     */       
/* 363 */       return flag;
/*     */     }
/*     */ 
/*     */     
/*     */     public int func_111092_a(World worldIn) {
/* 368 */       BlockPos blockpos = new BlockPos(this.field_111101_a, 256.0D, this.field_111100_b);
/*     */       
/* 370 */       while (blockpos.getY() > 0) {
/*     */         
/* 372 */         blockpos = blockpos.down();
/*     */         
/* 374 */         if (worldIn.getBlockState(blockpos).getBlock().getMaterial() != Material.air)
/*     */         {
/* 376 */           return blockpos.getY() + 1;
/*     */         }
/*     */       } 
/*     */       
/* 380 */       return 257;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean func_111098_b(World worldIn) {
/* 385 */       BlockPos blockpos = new BlockPos(this.field_111101_a, 256.0D, this.field_111100_b);
/*     */       
/* 387 */       while (blockpos.getY() > 0) {
/*     */         
/* 389 */         blockpos = blockpos.down();
/* 390 */         Material material = worldIn.getBlockState(blockpos).getBlock().getMaterial();
/*     */         
/* 392 */         if (material != Material.air)
/*     */         {
/* 394 */           return (!material.isLiquid() && material != Material.fire);
/*     */         }
/*     */       } 
/*     */       
/* 398 */       return false;
/*     */     }
/*     */ 
/*     */     
/*     */     public void func_111097_a(Random p_111097_1_, double p_111097_2_, double p_111097_4_, double p_111097_6_, double p_111097_8_) {
/* 403 */       this.field_111101_a = MathHelper.getRandomDoubleInRange(p_111097_1_, p_111097_2_, p_111097_6_);
/* 404 */       this.field_111100_b = MathHelper.getRandomDoubleInRange(p_111097_1_, p_111097_4_, p_111097_8_);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\minecraft\command\CommandSpreadPlayers.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */