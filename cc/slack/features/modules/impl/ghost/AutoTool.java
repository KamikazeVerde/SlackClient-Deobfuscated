/*     */ package cc.slack.features.modules.impl.ghost;
/*     */ 
/*     */ import cc.slack.events.impl.network.PacketEvent;
/*     */ import cc.slack.events.impl.render.RenderEvent;
/*     */ import cc.slack.features.modules.api.Category;
/*     */ import cc.slack.features.modules.api.Module;
/*     */ import cc.slack.features.modules.api.ModuleInfo;
/*     */ import cc.slack.features.modules.api.settings.Value;
/*     */ import cc.slack.features.modules.api.settings.impl.BooleanValue;
/*     */ import cc.slack.features.modules.api.settings.impl.NumberValue;
/*     */ import cc.slack.utils.client.mc;
/*     */ import cc.slack.utils.other.TimeUtil;
/*     */ import cc.slack.utils.player.AttackUtil;
/*     */ import cc.slack.utils.player.ItemSpoofUtil;
/*     */ import io.github.nevalackin.radbus.Listen;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.network.play.client.C02PacketUseEntity;
/*     */ import net.minecraft.util.MovingObjectPosition;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @ModuleInfo(name = "AutoTool", category = Category.GHOST)
/*     */ public class AutoTool
/*     */   extends Module
/*     */ {
/*  28 */   private final BooleanValue noCombat = new BooleanValue("No Combat", true);
/*  29 */   private final NumberValue<Integer> delay = new NumberValue("Switch Delay", Integer.valueOf(90), Integer.valueOf(0), Integer.valueOf(500), Integer.valueOf(10)); private final TimeUtil switchTimer; private int prevItem;
/*  30 */   private final BooleanValue spoof = new BooleanValue("Spoof Item", false);
/*     */   
/*     */   private boolean isMining;
/*     */   private int bestSlot;
/*     */   
/*     */   public AutoTool() {
/*  36 */     this.switchTimer = new TimeUtil();
/*     */     
/*  38 */     this.prevItem = 0;
/*     */     
/*  40 */     this.isMining = false;
/*  41 */     this.bestSlot = 0;
/*     */     addSettings(new Value[] { (Value)this.noCombat, (Value)this.delay, (Value)this.spoof });
/*     */   }
/*     */   @Listen
/*     */   public void onRender(RenderEvent event) {
/*  46 */     if (event.getState() != RenderEvent.State.RENDER_3D)
/*  47 */       return;  if (!(mc.getGameSettings()).keyBindUseItem.isKeyDown() && (mc.getGameSettings()).keyBindAttack.isKeyDown() && 
/*  48 */       (mc.getMinecraft()).objectMouseOver != null && 
/*  49 */       (mc.getMinecraft()).objectMouseOver.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK && (!AttackUtil.inCombat || 
/*  50 */       !((Boolean)this.noCombat.getValue()).booleanValue())) {
/*  51 */       getTool(Boolean.valueOf(true), mc.getWorld().getBlockState((mc.getMinecraft()).objectMouseOver.getBlockPos()).getBlock(), (Integer)this.delay.getValue(), (Boolean)this.spoof.getValue());
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   @Listen
/*     */   public void onPacket(PacketEvent event) {
/*  58 */     if (event.getPacket() instanceof C02PacketUseEntity) {
/*  59 */       C02PacketUseEntity C02 = (C02PacketUseEntity)event.getPacket();
/*  60 */       if (C02.getAction() == C02PacketUseEntity.Action.ATTACK)
/*  61 */         this.isMining = false; 
/*     */     } 
/*     */   }
/*     */   
/*     */   public void getTool(Boolean enabled, Block block, Integer delay, Boolean spoof) {
/*  66 */     if (enabled.booleanValue()) {
/*  67 */       if (this.switchTimer.hasReached(delay.intValue())) {
/*     */         
/*  69 */         this.bestSlot = -1;
/*     */         
/*  71 */         if (!this.isMining) {
/*  72 */           this.prevItem = (mc.getPlayer()).inventory.currentItem;
/*     */         }
/*     */         
/*  75 */         float bestSpeed = (mc.getPlayer()).inventory.getStackInSlot((mc.getPlayer()).inventory.currentItem).getStrVsBlock(block);
/*  76 */         for (int i = 0; i <= 8; i++) {
/*  77 */           ItemStack item = (mc.getPlayer()).inventory.getStackInSlot(i);
/*  78 */           if (item != null) {
/*     */ 
/*     */             
/*  81 */             float speed = item.getStrVsBlock(block);
/*     */             
/*  83 */             if (speed > bestSpeed) {
/*  84 */               bestSpeed = speed;
/*  85 */               this.bestSlot = i;
/*     */             } 
/*     */             
/*  88 */             if (this.bestSlot != -1)
/*  89 */               if (spoof.booleanValue()) {
/*  90 */                 ItemSpoofUtil.startSpoofing(this.bestSlot);
/*     */               } else {
/*  92 */                 (mc.getPlayer()).inventory.currentItem = this.bestSlot;
/*     */               }  
/*     */           } 
/*     */         } 
/*  96 */         this.isMining = true;
/*     */       } 
/*     */     } else {
/*  99 */       this.switchTimer.reset();
/* 100 */       if (this.isMining) {
/* 101 */         this.isMining = false;
/* 102 */         if (spoof.booleanValue()) {
/* 103 */           ItemSpoofUtil.stopSpoofing();
/*     */         }
/* 105 */         (mc.getPlayer()).inventory.currentItem = this.prevItem;
/*     */       } else {
/* 107 */         this.prevItem = (mc.getPlayer()).inventory.currentItem;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\cc\slack\features\modules\impl\ghost\AutoTool.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */