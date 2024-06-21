/*     */ package cc.slack.features.modules.impl.world;
/*     */ 
/*     */ import cc.slack.events.State;
/*     */ import cc.slack.events.impl.player.MotionEvent;
/*     */ import cc.slack.events.impl.player.MoveEvent;
/*     */ import cc.slack.events.impl.player.UpdateEvent;
/*     */ import cc.slack.features.modules.api.Category;
/*     */ import cc.slack.features.modules.api.Module;
/*     */ import cc.slack.features.modules.api.ModuleInfo;
/*     */ import cc.slack.features.modules.api.settings.Value;
/*     */ import cc.slack.features.modules.api.settings.impl.BooleanValue;
/*     */ import cc.slack.features.modules.api.settings.impl.ModeValue;
/*     */ import cc.slack.features.modules.api.settings.impl.NumberValue;
/*     */ import cc.slack.utils.client.mc;
/*     */ import cc.slack.utils.other.BlockUtils;
/*     */ import cc.slack.utils.player.InventoryUtil;
/*     */ import cc.slack.utils.player.ItemSpoofUtil;
/*     */ import cc.slack.utils.player.MovementUtil;
/*     */ import cc.slack.utils.player.PlayerUtil;
/*     */ import cc.slack.utils.player.RotationUtil;
/*     */ import io.github.nevalackin.radbus.Listen;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import net.minecraft.client.settings.GameSettings;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.MovingObjectPosition;
/*     */ import net.minecraft.util.Vec3;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @ModuleInfo(name = "Scaffold", category = Category.WORLD, key = 47)
/*     */ public class Scaffold
/*     */   extends Module
/*     */ {
/*  39 */   private final ModeValue<String> rotationMode = new ModeValue("Rotation Mode", (Object[])new String[] { "Vanilla", "Hypixel" });
/*  40 */   private final NumberValue<Integer> keepRotationTicks = new NumberValue("Keep Rotation Length", Integer.valueOf(1), Integer.valueOf(0), Integer.valueOf(10), Integer.valueOf(1));
/*     */   
/*  42 */   private final ModeValue<String> raycastMode = new ModeValue("Placement Check", (Object[])new String[] { "Normal", "Strict", "Off" });
/*  43 */   private final ModeValue<String> placeTiming = new ModeValue("Placement Timing", (Object[])new String[] { "Legit", "Pre", "Post" });
/*     */   
/*  45 */   private final ModeValue<String> sprintMode = new ModeValue("Sprint Mode", (Object[])new String[] { "Always", "No Packet", "Hypixel Safe", "Off" });
/*  46 */   private final NumberValue<Double> speedModifier = new NumberValue("Speed Modifier", Double.valueOf(1.0D), Double.valueOf(0.0D), Double.valueOf(2.0D), Double.valueOf(0.01D));
/*     */   
/*  48 */   private final ModeValue<String> safewalkMode = new ModeValue("Safewalk", (Object[])new String[] { "Ground", "Always", "Sneak", "Off" });
/*     */   
/*  50 */   private final BooleanValue strafeFix = new BooleanValue("Movement Correction", true);
/*     */   
/*  52 */   private final ModeValue<String> towerMode = new ModeValue("Tower Mode", (Object[])new String[] { "Vanilla", "Vulcan", "Static", "Off" });
/*  53 */   private final BooleanValue towerNoMove = new BooleanValue("Tower No Move", false);
/*     */   
/*  55 */   private final BooleanValue spoofSlot = new BooleanValue("Spoof Item Slot", false);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   float yaw;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   boolean hasBlock = false;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  78 */   float[] blockRotation = new float[] { 0.0F, 0.0F };
/*  79 */   BlockPos blockPlace = new BlockPos(0, 0, 0);
/*  80 */   BlockPos blockPlacement = new BlockPos(0, 0, 0);
/*  81 */   EnumFacing blockPlacementFace = EnumFacing.DOWN;
/*  82 */   double jumpGround = 0.0D;
/*     */ 
/*     */   
/*     */   public Scaffold() {
/*  86 */     addSettings(new Value[] { (Value)this.rotationMode, (Value)this.keepRotationTicks, (Value)this.raycastMode, (Value)this.placeTiming, (Value)this.sprintMode, (Value)this.speedModifier, (Value)this.safewalkMode, (Value)this.strafeFix, (Value)this.towerMode, (Value)this.towerNoMove, (Value)this.spoofSlot });
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onEnable() {}
/*     */ 
/*     */ 
/*     */   
/*     */   public void onDisable() {
/*  96 */     ItemSpoofUtil.stopSpoofing();
/*     */   }
/*     */   
/*     */   @Listen
/*     */   public void onMotion(MotionEvent event) {
/* 101 */     if (event.getState() == State.PRE)
/* 102 */     { if (this.placeTiming.getValue() == "Pre") placeBlock();
/*     */        }
/* 104 */     else if (this.placeTiming.getValue() == "Post") { placeBlock(); }
/*     */   
/*     */   }
/*     */   
/*     */   @Listen
/*     */   public void onMove(MoveEvent event) {
/* 110 */     switch (((String)this.safewalkMode.getValue()).toLowerCase()) {
/*     */       case "ground":
/* 112 */         event.safewalk = (event.safewalk || (mc.getPlayer()).onGround);
/*     */         break;
/*     */       case "always":
/* 115 */         event.safewalk = true;
/*     */         break;
/*     */       case "sneak":
/* 118 */         (mc.getGameSettings()).keyBindSneak.pressed = PlayerUtil.isOverAir();
/*     */         break;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Listen
/*     */   public void onUpdate(UpdateEvent event) {
/* 129 */     if (!pickBlock()) {
/* 130 */       RotationUtil.disable();
/*     */       return;
/*     */     } 
/* 133 */     setSprint();
/* 134 */     setMovementCorrection();
/* 135 */     runTowerMove();
/* 136 */     updatePlayerRotations();
/* 137 */     startSearch();
/* 138 */     if (this.placeTiming.getValue() == "Legit") placeBlock();
/*     */   
/*     */   }
/*     */   
/*     */   private boolean pickBlock() {
/* 143 */     if (InventoryUtil.pickHotarBlock(true) != -1) {
/* 144 */       if (((Boolean)this.spoofSlot.getValue()).booleanValue()) {
/* 145 */         ItemSpoofUtil.startSpoofing(InventoryUtil.pickHotarBlock(true));
/*     */       } else {
/* 147 */         (mc.getPlayer()).inventory.currentItem = InventoryUtil.pickHotarBlock(true);
/*     */       } 
/* 149 */       return true;
/*     */     } 
/* 151 */     ItemSpoofUtil.stopSpoofing();
/* 152 */     return false;
/*     */   }
/*     */   
/*     */   private void setSprint() {
/* 156 */     switch (((String)this.sprintMode.getValue()).toLowerCase()) {
/*     */       case "always":
/*     */       case "no packet":
/* 159 */         mc.getPlayer().setSprinting(true);
/*     */         break;
/*     */       case "hypixel safe":
/* 162 */         mc.getPlayer().setSprinting(false);
/* 163 */         (mc.getPlayer()).motionX *= 0.95D;
/* 164 */         (mc.getPlayer()).motionZ *= 0.95D;
/*     */         break;
/*     */       case "off":
/* 167 */         mc.getPlayer().setSprinting(false);
/*     */         break;
/*     */     } 
/*     */   }
/*     */   
/*     */   private void setMovementCorrection() {
/* 173 */     if (((Boolean)this.strafeFix.getValue()).booleanValue()) {
/* 174 */       RotationUtil.setStrafeFix(true, false);
/*     */     } else {
/* 176 */       RotationUtil.setStrafeFix(false, false);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void updatePlayerRotations() {
/* 181 */     switch (((String)this.rotationMode.getValue()).toLowerCase()) {
/*     */       case "hypixel":
/* 183 */         RotationUtil.setClientRotation(new float[] { (mc.getPlayer()).rotationYaw + 180.0F, 78.0F }, ((Integer)this.keepRotationTicks.getValue()).intValue());
/*     */         break;
/*     */       case "vanilla":
/* 186 */         RotationUtil.setClientRotation(this.blockRotation, ((Integer)this.keepRotationTicks.getValue()).intValue());
/*     */         break;
/*     */     } 
/*     */     
/* 190 */     BlockPos below = new BlockPos((mc.getPlayer()).posX, (mc.getPlayer()).posY - 1.0D, (mc.getPlayer()).posZ);
/* 191 */     if (!BlockUtils.isReplaceable(below) && (
/* 192 */       (Integer)this.keepRotationTicks.getValue()).intValue() == 0) {
/* 193 */       RotationUtil.disable();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private void runTowerMove() {
/* 199 */     if (GameSettings.isKeyDown((mc.getGameSettings()).keyBindJump) && (!((Boolean)this.towerNoMove.getValue()).booleanValue() || !MovementUtil.isMoving())) {
/* 200 */       switch (((String)this.towerMode.getValue()).toLowerCase()) {
/*     */         case "static":
/* 202 */           (mc.getPlayer()).motionY = 0.42D;
/*     */           break;
/*     */         case "vanilla":
/* 205 */           if ((mc.getPlayer()).onGround) {
/* 206 */             this.jumpGround = (mc.getPlayer()).posY;
/* 207 */             (mc.getPlayer()).motionY = 0.42D;
/*     */           } 
/*     */           
/* 210 */           switch ((int)Math.round(((mc.getPlayer()).posY - this.jumpGround) * 100.0D)) {
/*     */             case 42:
/* 212 */               (mc.getPlayer()).motionY = 0.33D;
/*     */               break;
/*     */             case 75:
/* 215 */               (mc.getPlayer()).motionY = 0.25D;
/*     */               break;
/*     */             case 100:
/* 218 */               this.jumpGround = (mc.getPlayer()).posY;
/* 219 */               (mc.getPlayer()).motionY = 0.42D;
/* 220 */               (mc.getPlayer()).onGround = true;
/*     */               break;
/*     */           } 
/*     */           break;
/*     */         case "vulcan":
/* 225 */           if ((mc.getPlayer()).onGround) {
/* 226 */             this.jumpGround = (mc.getPlayer()).posY;
/* 227 */             (mc.getPlayer()).motionY = PlayerUtil.getJumpHeight();
/*     */           } 
/* 229 */           if ((mc.getPlayer()).posY > this.jumpGround + 0.65D && MovementUtil.isMoving()) {
/* 230 */             (mc.getPlayer()).motionY = 0.36D;
/* 231 */             this.jumpGround = (mc.getPlayer()).posY;
/*     */           } 
/*     */           break;
/*     */         case "off":
/* 235 */           if ((mc.getPlayer()).onGround) {
/* 236 */             (mc.getPlayer()).motionY = PlayerUtil.getJumpHeight();
/*     */           }
/*     */           break;
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   private void startSearch() {
/* 244 */     BlockPos below = new BlockPos((mc.getPlayer()).posX, (mc.getPlayer()).posY - 1.0D, (mc.getPlayer()).posZ);
/* 245 */     if (!BlockUtils.isReplaceable(below))
/*     */       return; 
/* 247 */     List<BlockPos> searchQueue = new ArrayList<>();
/*     */     
/* 249 */     searchQueue.add(below.down());
/*     */     
/* 251 */     searchQueue.add(below.north());
/* 252 */     searchQueue.add(below.east());
/* 253 */     searchQueue.add(below.south());
/* 254 */     searchQueue.add(below.west());
/*     */     
/* 256 */     searchQueue.add(below.north().east());
/* 257 */     searchQueue.add(below.north().west());
/* 258 */     searchQueue.add(below.south().east());
/* 259 */     searchQueue.add(below.south().west());
/*     */     int i;
/* 261 */     for (i = 0; i < searchQueue.size(); i++) {
/*     */       
/* 263 */       if (searchBlock(searchQueue.get(i))) {
/* 264 */         this.hasBlock = true;
/*     */         
/*     */         return;
/*     */       } 
/*     */     } 
/* 269 */     for (i = 0; i < searchQueue.size(); i++) {
/*     */       
/* 271 */       if (searchBlock(((BlockPos)searchQueue.get(i)).down())) {
/* 272 */         this.hasBlock = true;
/*     */         return;
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private boolean searchBlock(BlockPos block) {
/* 279 */     if (BlockUtils.isFullBlock(block)) {
/* 280 */       EnumFacing placeFace = BlockUtils.getHorizontalFacingEnum(block);
/* 281 */       if (block.getY() <= (mc.getPlayer()).posY - 2.0D) {
/* 282 */         placeFace = EnumFacing.UP;
/*     */       }
/* 284 */       this.blockRotation = BlockUtils.getFaceRotation(placeFace, block);
/* 285 */       this.blockPlace = block;
/* 286 */       this.blockPlacement = block.add(placeFace.getDirectionVec());
/* 287 */       if (!BlockUtils.isReplaceable(this.blockPlacement)) {
/* 288 */         return false;
/*     */       }
/* 290 */       this.blockPlacementFace = placeFace;
/* 291 */       return true;
/*     */     } 
/* 293 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   private void placeBlock() {
/* 298 */     if (!this.hasBlock)
/* 299 */       return;  boolean canContinue = true;
/* 300 */     MovingObjectPosition raytraced = mc.getWorld().rayTraceBlocks(
/* 301 */         mc.getPlayer().getPositionEyes(1.0F), 
/* 302 */         mc.getPlayer().getPositionEyes(1.0F).add(RotationUtil.getNormalRotVector(RotationUtil.clientRotation).multiply(4.0D)), false, true, false);
/*     */     
/* 304 */     switch (((String)this.raycastMode.getValue()).toLowerCase()) {
/*     */       case "normal":
/* 306 */         if (raytraced == null) {
/* 307 */           canContinue = false;
/*     */           break;
/*     */         } 
/* 310 */         if (raytraced.typeOfHit != MovingObjectPosition.MovingObjectType.BLOCK) {
/* 311 */           canContinue = false; break;
/*     */         } 
/* 313 */         canContinue = (raytraced.getBlockPos() == this.blockPlacement);
/*     */         break;
/*     */       
/*     */       case "strict":
/* 317 */         if (raytraced == null) {
/* 318 */           canContinue = false;
/*     */           break;
/*     */         } 
/* 321 */         if (raytraced.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {
/* 322 */           canContinue = (raytraced.getBlockPos() == this.blockPlacement && raytraced.sideHit == this.blockPlacementFace);
/*     */         }
/*     */         break;
/*     */     } 
/*     */ 
/*     */     
/* 328 */     if (!canContinue)
/*     */       return; 
/* 330 */     BlockPos below = new BlockPos((mc.getPlayer()).posX, (mc.getPlayer()).posY - 1.0D, (mc.getPlayer()).posZ);
/* 331 */     if (!BlockUtils.isReplaceable(below))
/*     */       return; 
/* 333 */     Vec3 hitVec = (new Vec3(this.blockPlacementFace.getDirectionVec())).multiply(0.5D).add(new Vec3(0.5D, 0.5D, 0.5D));
/*     */     
/* 335 */     if (mc.getPlayerController().onPlayerRightClick(mc.getPlayer(), mc.getWorld(), mc.getPlayer().getHeldItem(), this.blockPlace, this.blockPlacementFace, hitVec)) {
/* 336 */       mc.getPlayer().swingItem();
/*     */       
/* 338 */       (mc.getPlayer()).motionX *= ((Double)this.speedModifier.getValue()).doubleValue();
/* 339 */       (mc.getPlayer()).motionZ *= ((Double)this.speedModifier.getValue()).doubleValue();
/* 340 */       this.hasBlock = false;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\cc\slack\features\modules\impl\world\Scaffold.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */