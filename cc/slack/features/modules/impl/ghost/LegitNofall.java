/*     */ package cc.slack.features.modules.impl.ghost;
/*     */ 
/*     */ import cc.slack.events.State;
/*     */ import cc.slack.events.impl.player.MotionEvent;
/*     */ import cc.slack.features.modules.api.Category;
/*     */ import cc.slack.features.modules.api.Module;
/*     */ import cc.slack.features.modules.api.ModuleInfo;
/*     */ import cc.slack.features.modules.api.settings.Value;
/*     */ import cc.slack.features.modules.api.settings.impl.BooleanValue;
/*     */ import cc.slack.features.modules.api.settings.impl.NumberValue;
/*     */ import cc.slack.utils.client.mc;
/*     */ import cc.slack.utils.other.BlockUtils;
/*     */ import io.github.nevalackin.radbus.Listen;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.entity.player.InventoryPlayer;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.network.Packet;
/*     */ import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.util.MovingObjectPosition;
/*     */ import net.minecraft.util.Vec3;
/*     */ 
/*     */ 
/*     */ 
/*     */ @ModuleInfo(name = "LegitNofall", category = Category.GHOST)
/*     */ public class LegitNofall
/*     */   extends Module
/*     */ {
/*  33 */   private final BooleanValue silentAim = new BooleanValue("Silent Aim", true);
/*  34 */   private final BooleanValue switchToItem = new BooleanValue("Switch to Item", true);
/*  35 */   private final NumberValue<Float> minFallDist = new NumberValue("Minimum fall dist", Float.valueOf(5.0F), Float.valueOf(3.0F), Float.valueOf(20.0F), Float.valueOf(0.5F));
/*     */   
/*     */   public LegitNofall() {
/*  38 */     addSettings(new Value[] { (Value)this.silentAim, (Value)this.switchToItem, (Value)this.minFallDist });
/*     */   }
/*     */   
/*     */   private float prevPitch;
/*     */   
/*     */   @Listen
/*     */   public void onMotion(MotionEvent e) {
/*  45 */     if (e.getState() == State.PRE) {
/*  46 */       MovingObjectPosition rayCast = wrayCast(mc.getPlayerController().getBlockReachDistance(), e.getYaw(), 90.0F);
/*  47 */       if ((inPosition() && rayCast != null && rayCast.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK && holdWaterBucket(((Boolean)this.switchToItem.getValue()).booleanValue())) || (inPosition() && rayCast != null && rayCast.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK && holdSpiderweb(((Boolean)this.switchToItem.getValue()).booleanValue()))) {
/*  48 */         if (((Boolean)this.silentAim.getValue()).booleanValue()) {
/*  49 */           e.setPitch(90.0F);
/*     */         } else {
/*  51 */           this.prevPitch = (mc.getPlayer()).rotationPitch;
/*  52 */           (mc.getPlayer()).rotationPitch = 90.0F;
/*     */         } 
/*  54 */         sendPlace();
/*     */       } 
/*  56 */       if ((mc.getPlayer()).onGround && mc.getPlayer().isInWater()) {
/*  57 */         sendPlace();
/*  58 */         if (!((Boolean)this.silentAim.getValue()).booleanValue())
/*  59 */           (mc.getPlayer()).rotationPitch = this.prevPitch; 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private boolean inPosition() {
/*  65 */     return (!(mc.getPlayer()).capabilities.isFlying && !(mc.getPlayer()).capabilities.isCreativeMode && !(mc.getPlayer()).onGround && (mc.getPlayer()).motionY < -0.6D && !mc.getPlayer().isInWater() && fallDistance() <= 2 && (mc.getPlayer()).fallDistance >= ((Float)this.minFallDist.getValue()).floatValue());
/*     */   }
/*     */   
/*     */   private boolean holdWaterBucket(boolean setSlot) {
/*  69 */     if (containsItem(mc.getPlayer().getHeldItem(), Item.getItemFromBlock(Blocks.web))) {
/*  70 */       return true;
/*     */     }
/*  72 */     for (int i = 0; i < InventoryPlayer.getHotbarSize(); i++) {
/*  73 */       if (containsItem((mc.getPlayer()).inventory.mainInventory[i], Item.getItemFromBlock(Blocks.web)) && setSlot) {
/*  74 */         (mc.getPlayer()).inventory.currentItem = i;
/*  75 */         return true;
/*     */       } 
/*     */     } 
/*     */     
/*  79 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean holdSpiderweb(boolean setSlot) {
/*  84 */     if (containsItem(mc.getPlayer().getHeldItem(), Items.water_bucket)) {
/*  85 */       return true;
/*     */     }
/*  87 */     for (int i = 0; i < InventoryPlayer.getHotbarSize(); i++) {
/*  88 */       if (containsItem((mc.getPlayer()).inventory.mainInventory[i], Items.water_bucket) && setSlot) {
/*  89 */         (mc.getPlayer()).inventory.currentItem = i;
/*  90 */         return true;
/*     */       } 
/*     */     } 
/*     */     
/*  94 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean containsItem(ItemStack itemStack, Item item) {
/*  99 */     return (itemStack != null && itemStack.getItem() == item);
/*     */   }
/*     */   
/*     */   private void sendPlace() {
/* 103 */     mc.getNetHandler().addToSendQueue((Packet)new C08PacketPlayerBlockPlacement(mc.getPlayer().getHeldItem()));
/*     */   }
/*     */   
/*     */   private int fallDistance() {
/* 107 */     int fallDist = -1;
/* 108 */     Vec3 pos = new Vec3((mc.getPlayer()).posX, (mc.getPlayer()).posY, (mc.getPlayer()).posZ);
/* 109 */     int y = (int)Math.floor(pos.yCoord);
/* 110 */     if (pos.yCoord % 1.0D == 0.0D) y--; 
/* 111 */     for (int i = y; i > -1; i--) {
/* 112 */       Block block = BlockUtils.getBlock(new BlockPos((int)Math.floor(pos.xCoord), i, (int)Math.floor(pos.zCoord)));
/* 113 */       if (!(block instanceof net.minecraft.block.BlockAir) && !(block instanceof net.minecraft.block.BlockSign)) {
/* 114 */         fallDist = y - i;
/*     */         break;
/*     */       } 
/*     */     } 
/* 118 */     return fallDist;
/*     */   }
/*     */   
/*     */   public static MovingObjectPosition wrayCast(double n, float n2, float n3) {
/* 122 */     Vec3 getPositionEyes = mc.getPlayer().getPositionEyes(1.0F);
/* 123 */     float n4 = -n2 * 0.017453292F;
/* 124 */     float n5 = -n3 * 0.017453292F;
/* 125 */     float cos = MathHelper.cos(n4 - 3.1415927F);
/* 126 */     float sin = MathHelper.sin(n4 - 3.1415927F);
/* 127 */     float n6 = -MathHelper.cos(n5);
/* 128 */     Vec3 vec3 = new Vec3((sin * n6), MathHelper.sin(n5), (cos * n6));
/* 129 */     return mc.getWorld().rayTraceBlocks(getPositionEyes, getPositionEyes.addVector(vec3.xCoord * n, vec3.yCoord * n, vec3.zCoord * n), false, false, false);
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\cc\slack\features\modules\impl\ghost\LegitNofall.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */