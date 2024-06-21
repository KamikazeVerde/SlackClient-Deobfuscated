/*    */ package cc.slack.features.modules.impl.ghost;
/*    */ 
/*    */ import cc.slack.events.impl.render.RenderEvent;
/*    */ import cc.slack.features.modules.api.Category;
/*    */ import cc.slack.features.modules.api.Module;
/*    */ import cc.slack.features.modules.api.ModuleInfo;
/*    */ import cc.slack.features.modules.api.settings.Value;
/*    */ import cc.slack.features.modules.api.settings.impl.BooleanValue;
/*    */ import cc.slack.features.modules.api.settings.impl.ModeValue;
/*    */ import cc.slack.features.modules.api.settings.impl.NumberValue;
/*    */ import cc.slack.utils.client.mc;
/*    */ import cc.slack.utils.other.TimeUtil;
/*    */ import cc.slack.utils.player.AttackUtil;
/*    */ import io.github.nevalackin.radbus.Listen;
/*    */ import net.minecraft.client.settings.GameSettings;
/*    */ import net.minecraft.client.settings.KeyBinding;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @ModuleInfo(name = "Autoclicker", category = Category.GHOST)
/*    */ public class Autoclicker
/*    */   extends Module
/*    */ {
/* 26 */   public final NumberValue<Float> targetCPS = new NumberValue("Target CPS", Float.valueOf(11.0F), Float.valueOf(0.0F), Float.valueOf(30.0F), Float.valueOf(0.1F));
/*    */   
/* 28 */   public final NumberValue<Float> randomizeAmount = new NumberValue("Randomization Amount", Float.valueOf(1.5F), Float.valueOf(0.0F), Float.valueOf(4.0F), Float.valueOf(0.1F));
/* 29 */   public final ModeValue<String> randomizeMode = new ModeValue("Randomization Pattern", (Object[])new String[] { "NEW", "OLD", "EXTRA", "PATTERN1", "PATTERN2", "NONE" });
/*    */   
/* 31 */   public final BooleanValue onlySword = new BooleanValue("Only Sword", false);
/*    */   
/* 33 */   public final ModeValue<String> autoblockMode = new ModeValue("Autoblock", (Object[])new String[] { "Off", "Click", "Normal", "BlockHit" });
/* 34 */   public final BooleanValue autoblockOnClick = new BooleanValue("Block On Mouse Down", true);
/* 35 */   public final BooleanValue triggerBot = new BooleanValue("Trigger Bot", false);
/*    */   
/*    */   private final TimeUtil leftClickTimer;
/*    */   
/*    */   private long leftClickDelay;
/*    */   
/*    */   public Autoclicker() {
/* 42 */     this.leftClickTimer = new TimeUtil();
/* 43 */     this.leftClickDelay = 0L;
/*    */     addSettings(new Value[] { (Value)this.targetCPS, (Value)this.randomizeAmount, (Value)this.randomizeMode, (Value)this.onlySword, (Value)this.autoblockMode, (Value)this.autoblockOnClick, (Value)this.triggerBot });
/*    */   }
/*    */   
/*    */   @Listen
/*    */   public void onRender(RenderEvent event) {
/* 49 */     if ((GameSettings.isKeyDown((mc.getGameSettings()).keyBindAttack) || (((Boolean)this.triggerBot.getValue()).booleanValue() && (mc.getMinecraft()).objectMouseOver.entityHit != null)) && (
/* 50 */       !((Boolean)this.onlySword.getValue()).booleanValue() || (mc.getPlayer().getHeldItem() != null && mc.getPlayer().getHeldItem().getItem() instanceof net.minecraft.item.ItemSword)) && 
/* 51 */       !(mc.getPlayerController()).isHittingBlock) {
/*    */       
/* 53 */       if (this.leftClickTimer.hasReached(this.leftClickDelay)) {
/* 54 */         this.leftClickTimer.reset();
/* 55 */         this.leftClickDelay = updateDelay((Float)this.targetCPS.getValue(), (Float)this.randomizeAmount.getValue());
/* 56 */         KeyBinding.onTick((mc.getGameSettings()).keyBindAttack.getKeyCode());
/* 57 */         if (((String)this.autoblockMode.getValue()).equals("Click") && (!((Boolean)this.autoblockOnClick.getValue()).booleanValue() || GameSettings.isKeyDown((mc.getGameSettings()).keyBindUseItem))) KeyBinding.onTick((mc.getGameSettings()).keyBindUseItem.getKeyCode()); 
/*    */       } 
/* 59 */       boolean rightMouseDown = GameSettings.isKeyDown((mc.getGameSettings()).keyBindUseItem);
/* 60 */       switch (((String)this.autoblockMode.getValue()).toLowerCase()) {
/*    */ 
/*    */         
/*    */         case "click":
/* 64 */           rightMouseDown = false;
/*    */           break;
/*    */         case "normal":
/* 67 */           rightMouseDown = (this.leftClickTimer.elapsed() > 0.1D * this.leftClickDelay && this.leftClickTimer.elapsed() < 0.65D * this.leftClickDelay);
/*    */           break;
/*    */         case "blockhit":
/* 70 */           rightMouseDown = (this.leftClickTimer.elapsed() < 0.4D * this.leftClickDelay);
/*    */           break;
/*    */       } 
/* 73 */       (mc.getGameSettings()).keyBindUseItem.pressed = (rightMouseDown && (!((Boolean)this.autoblockOnClick.getValue()).booleanValue() || GameSettings.isKeyDown((mc.getGameSettings()).keyBindUseItem)));
/*    */     } 
/*    */   }
/*    */   
/*    */   private long updateDelay(Float cps, Float rand) {
/* 78 */     switch (((String)this.randomizeMode.getValue()).toLowerCase()) {
/*    */       case "none":
/* 80 */         return (long)(1000.0F / cps.floatValue());
/*    */       case "old":
/* 82 */         return (long)(1000.0D / AttackUtil.getOldRandomization(cps.floatValue(), rand.floatValue()));
/*    */       case "new":
/* 84 */         return (long)(1000.0D / AttackUtil.getNewRandomization(cps.floatValue(), rand.floatValue()));
/*    */       case "extra":
/* 86 */         return (long)(1000.0D / AttackUtil.getExtraRandomization(cps.floatValue(), rand.floatValue()));
/*    */     } 
/* 88 */     return 0L;
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\cc\slack\features\modules\impl\ghost\Autoclicker.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */