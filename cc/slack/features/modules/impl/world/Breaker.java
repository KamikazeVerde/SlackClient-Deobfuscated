/*     */ package cc.slack.features.modules.impl.world;
/*     */ 
/*     */ import cc.slack.Slack;
/*     */ import cc.slack.events.impl.player.UpdateEvent;
/*     */ import cc.slack.events.impl.render.RenderEvent;
/*     */ import cc.slack.features.modules.api.Category;
/*     */ import cc.slack.features.modules.api.Module;
/*     */ import cc.slack.features.modules.api.ModuleInfo;
/*     */ import cc.slack.features.modules.api.settings.Value;
/*     */ import cc.slack.features.modules.api.settings.impl.ModeValue;
/*     */ import cc.slack.features.modules.api.settings.impl.NumberValue;
/*     */ import cc.slack.features.modules.impl.ghost.AutoTool;
/*     */ import cc.slack.utils.client.mc;
/*     */ import cc.slack.utils.other.BlockUtils;
/*     */ import cc.slack.utils.other.TimeUtil;
/*     */ import cc.slack.utils.player.RotationUtil;
/*     */ import cc.slack.utils.render.Render3DUtil;
/*     */ import io.github.nevalackin.radbus.Listen;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.network.Packet;
/*     */ import net.minecraft.network.play.client.C07PacketPlayerDigging;
/*     */ import net.minecraft.util.AxisAlignedBB;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @ModuleInfo(name = "Breaker", category = Category.WORLD)
/*     */ public class Breaker
/*     */   extends Module
/*     */ {
/*  36 */   public final ModeValue<String> mode = new ModeValue("Bypass", (Object[])new String[] { "Hypixel", "None" });
/*  37 */   public final NumberValue<Double> radiusDist = new NumberValue("Radius", Double.valueOf(4.5D), Double.valueOf(1.0D), Double.valueOf(7.0D), Double.valueOf(0.5D));
/*  38 */   public final ModeValue<String> sortMode = new ModeValue("Sort", (Object[])new String[] { "Distance", "Absolute" });
/*  39 */   public final NumberValue<Integer> switchDelay = new NumberValue("Switch Delay", Integer.valueOf(50), Integer.valueOf(0), Integer.valueOf(500), Integer.valueOf(10));
/*  40 */   public final NumberValue<Integer> targetSwitchDelay = new NumberValue("Target Switch Delay", Integer.valueOf(50), Integer.valueOf(0), Integer.valueOf(500), Integer.valueOf(10));
/*     */   
/*     */   private BlockPos targetBlock;
/*     */   
/*     */   private BlockPos currentBlock;
/*     */   
/*     */   private float breakingProgress;
/*     */   
/*     */   private TimeUtil switchTimer;
/*     */   
/*     */   public Breaker() {
/*  51 */     this.switchTimer = new TimeUtil();
/*     */     addSettings(new Value[] { (Value)this.mode, (Value)this.radiusDist, (Value)this.sortMode, (Value)this.switchDelay, (Value)this.targetSwitchDelay });
/*     */   }
/*     */   public void onEnable() {
/*  55 */     this.targetBlock = null;
/*  56 */     this.currentBlock = null;
/*  57 */     this.breakingProgress = 0.0F;
/*     */   }
/*     */   
/*     */   @Listen
/*     */   public void onUpdate(UpdateEvent event) {
/*  62 */     if (this.targetBlock == null) {
/*  63 */       if (this.switchTimer.hasReached(((Integer)this.targetSwitchDelay.getValue()).intValue())) {
/*  64 */         findTargetBlock();
/*     */       }
/*     */     } else {
/*  67 */       if (this.currentBlock == null && 
/*  68 */         this.switchTimer.hasReached(((Integer)this.switchDelay.getValue()).intValue())) {
/*  69 */         findBreakBlock();
/*  70 */         this.breakingProgress = 0.0F;
/*  71 */         mc.getNetHandler().addToSendQueue((Packet)new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.START_DESTROY_BLOCK, this.currentBlock, EnumFacing.DOWN));
/*     */       } 
/*     */ 
/*     */       
/*  75 */       if (this.currentBlock != null) {
/*  76 */         ((AutoTool)Slack.getInstance().getModuleManager().getInstance(AutoTool.class)).getTool(Boolean.valueOf(true), BlockUtils.getBlock(this.currentBlock), Integer.valueOf(0), Boolean.valueOf(false));
/*     */         
/*  78 */         this.breakingProgress += BlockUtils.getHardness(this.currentBlock);
/*  79 */         mc.getWorld().sendBlockBreakProgress(mc.getPlayer().getEntityId(), this.currentBlock, (int)(this.breakingProgress * 10.0F) - 1);
/*  80 */         mc.getPlayer().swingItem();
/*     */         
/*  82 */         RotationUtil.setClientRotation(BlockUtils.getCenterRotation(this.currentBlock));
/*  83 */         RotationUtil.setStrafeFix(true, false);
/*     */         
/*  85 */         if (this.breakingProgress > 1.0F) {
/*  86 */           mc.getNetHandler().addToSendQueue((Packet)new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.STOP_DESTROY_BLOCK, this.currentBlock, EnumFacing.DOWN));
/*  87 */           mc.getPlayerController().onPlayerDestroyBlock(this.currentBlock, EnumFacing.DOWN);
/*  88 */           mc.getWorld().setBlockState(this.currentBlock, Blocks.air.getDefaultState(), 11);
/*  89 */           ((AutoTool)Slack.getInstance().getModuleManager().getInstance(AutoTool.class)).getTool(Boolean.valueOf(false), BlockUtils.getBlock(this.currentBlock), Integer.valueOf(0), Boolean.valueOf(false));
/*  90 */           this.currentBlock = null;
/*  91 */           this.switchTimer.reset();
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   @Listen
/*     */   public void onRender(RenderEvent event) {
/*  99 */     if (event.getState() != RenderEvent.State.RENDER_3D)
/* 100 */       return;  if (this.currentBlock == null)
/* 101 */       return;  BlockPos bp = this.currentBlock;
/*     */     
/* 103 */     Render3DUtil.drawAABB(AxisAlignedBB.fromBounds(bp.getX(), bp.getY(), bp.getZ(), (bp.getX() + 1), (bp.getY() + this.breakingProgress), (bp.getZ() + 1)));
/*     */   }
/*     */   
/*     */   private void findTargetBlock() {
/* 107 */     int radius = (int)Math.ceil(((Double)this.radiusDist.getValue()).doubleValue());
/* 108 */     BlockPos bestBlock = null;
/* 109 */     double bestDist = -1.0D;
/* 110 */     int bestAbs = -1;
/*     */     
/* 112 */     for (int x = radius; x >= -radius + 1; x--) {
/* 113 */       for (int y = radius; y >= -radius + 1; y--) {
/* 114 */         for (int z = radius; z >= -radius + 1; z--) {
/* 115 */           BlockPos blockPos = new BlockPos((mc.getPlayer()).posX + x, (mc.getPlayer()).posY + y, (mc.getPlayer()).posZ + z);
/* 116 */           Block block = BlockUtils.getBlock(blockPos);
/* 117 */           if (block != null && 
/* 118 */             block instanceof net.minecraft.block.BlockBed) {
/* 119 */             switch (((String)this.sortMode.getValue()).toLowerCase()) {
/*     */               case "distance":
/* 121 */                 if (bestDist == -1.0D || BlockUtils.getCenterDistance(blockPos) < bestDist) {
/* 122 */                   bestBlock = blockPos;
/* 123 */                   bestDist = BlockUtils.getCenterDistance(blockPos);
/*     */                 } 
/*     */                 break;
/*     */               case "absolute":
/* 127 */                 if (bestAbs == -1 || BlockUtils.getAbsoluteValue(blockPos) < bestDist) {
/* 128 */                   bestBlock = blockPos;
/* 129 */                   bestAbs = BlockUtils.getAbsoluteValue(blockPos);
/*     */                 } 
/*     */                 break;
/*     */             } 
/*     */           
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/* 138 */     if (bestBlock != null) {
/* 139 */       this.targetBlock = bestBlock;
/* 140 */       this.switchTimer.reset();
/*     */     } 
/*     */   }
/*     */   
/*     */   private void findBreakBlock() {
/* 145 */     if (this.targetBlock == null)
/*     */       return; 
/* 147 */     switch (((String)this.mode.getValue()).toLowerCase()) {
/*     */       case "hypixel":
/* 149 */         if (BlockUtils.isReplaceableNotBed(this.targetBlock.east()) || 
/* 150 */           BlockUtils.isReplaceableNotBed(this.targetBlock.north()) || 
/* 151 */           BlockUtils.isReplaceableNotBed(this.targetBlock.west()) || 
/* 152 */           BlockUtils.isReplaceableNotBed(this.targetBlock.south()) || 
/* 153 */           BlockUtils.isReplaceableNotBed(this.targetBlock.up())) {
/* 154 */           this.currentBlock = this.targetBlock; break;
/*     */         } 
/* 156 */         this.currentBlock = this.targetBlock.up();
/*     */         break;
/*     */       
/*     */       case "none":
/* 160 */         this.currentBlock = this.targetBlock;
/*     */         break;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\cc\slack\features\modules\impl\world\Breaker.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */