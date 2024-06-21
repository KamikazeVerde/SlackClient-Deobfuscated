/*     */ package cc.slack.utils.player;
/*     */ 
/*     */ import cc.slack.events.impl.player.MoveEvent;
/*     */ import cc.slack.utils.client.mc;
/*     */ import cc.slack.utils.network.PacketUtil;
/*     */ import cc.slack.utils.other.BlockUtils;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.enchantment.Enchantment;
/*     */ import net.minecraft.enchantment.EnchantmentHelper;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.item.ItemSword;
/*     */ import net.minecraft.network.Packet;
/*     */ import net.minecraft.network.play.client.C03PacketPlayer;
/*     */ import net.minecraft.potion.Potion;
/*     */ import net.minecraft.util.AxisAlignedBB;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.MathHelper;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PlayerUtil
/*     */   extends mc
/*     */ {
/*     */   public static final double BASE_MOTION = 0.21585904623731839D;
/*     */   public static final double BASE_MOTION_SPRINT = 0.28060730580133125D;
/*     */   public static final double BASE_MOTION_WATER = 0.09989148404308008D;
/*     */   public static final double MAX_MOTION_SPRINT = 0.28623662093593094D;
/*     */   public static final double BASE_GROUND_BOOST = 1.9561839658913562D;
/*     */   public static final double BASE_GROUND_FRICTION = 0.587619839258055D;
/*     */   public static final double SPEED_GROUND_BOOST = 2.016843005849186D;
/*     */   public static final double MOVE_FRICTION = 0.9800000190734863D;
/*     */   public static final double BASE_JUMP_HEIGHT = 0.41999998688698D;
/*     */   public static final double HEAD_HITTER_MOTIONY = -0.07840000152D;
/*     */   
/*     */   public static double getJumpHeight() {
/*  41 */     return getJumpHeight(0.41999998688698D);
/*     */   }
/*     */   
/*     */   public static double getJumpHeight(double height) {
/*  45 */     if (getPlayer().isPotionActive(Potion.jump)) {
/*  46 */       return height + (getPlayer().getActivePotionEffect(Potion.jump).getAmplifier() + 1) * 0.1D;
/*     */     }
/*  48 */     return height;
/*     */   }
/*     */   
/*     */   public static double getSpeed() {
/*  52 */     return Math.hypot((getPlayer()).motionX, (getPlayer()).motionZ);
/*     */   }
/*     */   
/*     */   public static double getSpeed(MoveEvent event) {
/*  56 */     return Math.hypot(event.getX(), event.getZ());
/*     */   }
/*     */   
/*     */   public static double getBaseMoveSpeed() {
/*  60 */     double baseSpeed = 0.2873D;
/*     */     
/*  62 */     if (getPlayer().isPotionActive(Potion.moveSpeed)) {
/*  63 */       double amplifier = getPlayer().getActivePotionEffect(Potion.moveSpeed).getAmplifier();
/*  64 */       baseSpeed *= 1.0D + 0.2D * (amplifier + 1.0D);
/*     */     } 
/*     */     
/*  67 */     return baseSpeed;
/*     */   }
/*     */   
/*     */   public static boolean isOverAir(double x, double y, double z) {
/*  71 */     return BlockUtils.isAir(new BlockPos(x, y - 1.0D, z));
/*     */   }
/*     */   
/*     */   public static boolean isOverAir() {
/*  75 */     return isOverAir((mc.getPlayer()).posX, (mc.getPlayer()).posY, (mc.getPlayer()).posZ);
/*     */   }
/*     */   
/*     */   public static boolean isOnSameTeam(EntityPlayer entity) {
/*  79 */     if (entity.getTeam() != null && getPlayer().getTeam() != null) {
/*  80 */       return (entity.getDisplayName().getFormattedText().charAt(1) == getPlayer().getDisplayName().getFormattedText().charAt(1));
/*     */     }
/*  82 */     return false;
/*     */   }
/*     */   
/*     */   public static boolean isBlockUnder() {
/*  86 */     for (int y = (int)(getPlayer()).posY; y >= 0; y--) {
/*     */       
/*  88 */       if (!(getWorld().getBlockState(new BlockPos((getPlayer()).posX, y, (getPlayer()).posZ)).getBlock() instanceof net.minecraft.block.BlockAir)) {
/*  89 */         return true;
/*     */       }
/*     */     } 
/*  92 */     return false;
/*     */   }
/*     */   
/*     */   public static boolean isBlockUnderP(int offset) {
/*  96 */     for (int i = (int)((mc.getPlayer()).posY - offset); i > 0; i--) {
/*  97 */       BlockPos pos = new BlockPos((mc.getPlayer()).posX, i, (mc.getPlayer()).posZ);
/*  98 */       if (!(mc.getWorld().getBlockState(pos).getBlock() instanceof net.minecraft.block.BlockAir))
/*  99 */         return true; 
/*     */     } 
/* 101 */     return false;
/*     */   }
/*     */   
/*     */   public static boolean isOnLiquid() {
/* 105 */     boolean onLiquid = false;
/* 106 */     AxisAlignedBB playerBB = getPlayer().getEntityBoundingBox();
/* 107 */     double y = (int)(playerBB.offset(0.0D, -0.01D, 0.0D)).minY;
/*     */     double x;
/* 109 */     for (x = MathHelper.floor_double(playerBB.minX); x < (MathHelper.floor_double(playerBB.maxX) + 1); x++) {
/* 110 */       double z; for (z = MathHelper.floor_double(playerBB.minZ); z < (MathHelper.floor_double(playerBB.maxZ) + 1); z++) {
/* 111 */         Block block = getWorld().getBlockState(new BlockPos(x, y, z)).getBlock();
/*     */         
/* 113 */         if (block != null && !(block instanceof net.minecraft.block.BlockAir)) {
/* 114 */           if (!(block instanceof net.minecraft.block.BlockLiquid)) return false;
/*     */           
/* 116 */           onLiquid = true;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 121 */     return onLiquid;
/*     */   }
/*     */   
/*     */   public static boolean isOverVoid() {
/* 125 */     for (double posY = (getPlayer()).posY; posY > 0.0D; posY--) {
/*     */       
/* 127 */       if (!(getWorld().getBlockState(new BlockPos((getPlayer()).posX, posY, (getPlayer()).posZ)).getBlock() instanceof net.minecraft.block.BlockAir)) {
/* 128 */         return false;
/*     */       }
/*     */     } 
/* 131 */     return true;
/*     */   }
/*     */   
/*     */   public static double getMaxFallDist() {
/* 135 */     double fallDistanceReq = 3.1D;
/*     */     
/* 137 */     if (getPlayer().isPotionActive(Potion.jump)) {
/* 138 */       int amplifier = getPlayer().getActivePotionEffect(Potion.jump).getAmplifier();
/* 139 */       fallDistanceReq += (amplifier + 1);
/*     */     } 
/*     */     
/* 142 */     return fallDistanceReq;
/*     */   }
/*     */   
/*     */   public static void damagePlayer(double value, boolean groundCheck) {
/* 146 */     if (!groundCheck || (getPlayer()).onGround) {
/* 147 */       for (int i = 0; i < Math.ceil(getMaxFallDist() / value); i++) {
/* 148 */         PacketUtil.sendNoEvent((Packet)new C03PacketPlayer.C04PacketPlayerPosition((getPlayer()).posX, (getPlayer()).posY + value, (getPlayer()).posZ, false));
/* 149 */         PacketUtil.sendNoEvent((Packet)new C03PacketPlayer.C04PacketPlayerPosition((getPlayer()).posX, (getPlayer()).posY, (getPlayer()).posZ, false));
/*     */       } 
/*     */       
/* 152 */       PacketUtil.sendNoEvent((Packet)new C03PacketPlayer(true));
/*     */     } 
/*     */   }
/*     */   
/*     */   public static ItemStack getBestSword() {
/* 157 */     int size = (mc.getPlayer()).inventoryContainer.getInventory().size();
/* 158 */     ItemStack lastSword = null;
/* 159 */     for (int i = 0; i < size; i++) {
/* 160 */       ItemStack stack = (mc.getPlayer()).inventoryContainer.getInventory().get(i);
/* 161 */       if (stack != null && stack.getItem() instanceof ItemSword)
/* 162 */         if (lastSword == null) {
/* 163 */           lastSword = stack;
/* 164 */         } else if (isBetterSword(stack, lastSword)) {
/* 165 */           lastSword = stack;
/*     */         }  
/*     */     } 
/* 168 */     return lastSword;
/*     */   }
/*     */ 
/*     */   
/*     */   public static ItemStack getBestAxe() {
/* 173 */     int size = (mc.getPlayer()).inventoryContainer.getInventory().size();
/* 174 */     ItemStack lastAxe = null;
/* 175 */     for (int i = 0; i < size; i++) {
/* 176 */       ItemStack stack = (mc.getPlayer()).inventoryContainer.getInventory().get(i);
/* 177 */       if (stack != null && stack.getItem() instanceof net.minecraft.item.ItemAxe)
/* 178 */         if (lastAxe == null) {
/* 179 */           lastAxe = stack;
/* 180 */         } else if (isBetterTool(stack, lastAxe, Blocks.planks)) {
/* 181 */           lastAxe = stack;
/*     */         }  
/*     */     } 
/* 184 */     return lastAxe;
/*     */   }
/*     */   
/*     */   public static int getBestHotbarTool(Block target) {
/* 188 */     int bestTool = (mc.getPlayer()).inventory.currentItem;
/* 189 */     for (int i = 36; i < 45; i++) {
/* 190 */       ItemStack itemStack = (mc.getPlayer()).inventoryContainer.getSlot(i).getStack();
/* 191 */       if (itemStack != null && (mc.getPlayer()).inventoryContainer.getSlot(bestTool).getStack() != null && 
/* 192 */         isBetterTool(itemStack, (mc.getPlayer()).inventoryContainer.getSlot(bestTool).getStack(), target)) {
/* 193 */         bestTool = i;
/*     */       }
/*     */     } 
/*     */     
/* 197 */     return bestTool;
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean isBetterTool(ItemStack better, ItemStack than, Block versus) {
/* 202 */     return (getToolDigEfficiency(better, versus) > getToolDigEfficiency(than, versus));
/*     */   }
/*     */   
/*     */   public static boolean isBetterSword(ItemStack better, ItemStack than) {
/* 206 */     return (getSwordDamage((ItemSword)better.getItem(), better) > getSwordDamage((ItemSword)than.getItem(), than));
/*     */   }
/*     */ 
/*     */   
/*     */   public static float getSwordDamage(ItemSword sword, ItemStack stack) {
/* 211 */     float base = sword.getMaxDamage();
/* 212 */     return base + EnchantmentHelper.getEnchantmentLevel(Enchantment.sharpness.effectId, stack) * 1.25F;
/*     */   }
/*     */   
/*     */   public static float getToolDigEfficiency(ItemStack stack, Block block) {
/* 216 */     float f = stack.getStrVsBlock(block);
/* 217 */     if (f > 1.0F) {
/* 218 */       int i = EnchantmentHelper.getEnchantmentLevel(Enchantment.efficiency.effectId, stack);
/* 219 */       if (i > 0)
/* 220 */         f += (i * i + 1); 
/*     */     } 
/* 222 */     return f;
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\cc\slac\\utils\player\PlayerUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */