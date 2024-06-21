/*    */ package cc.slack.features.modules.impl.other;
/*    */ 
/*    */ import cc.slack.events.impl.player.MotionEvent;
/*    */ import cc.slack.events.impl.player.UpdateEvent;
/*    */ import cc.slack.features.modules.api.Category;
/*    */ import cc.slack.features.modules.api.Module;
/*    */ import cc.slack.features.modules.api.ModuleInfo;
/*    */ import cc.slack.features.modules.api.settings.Value;
/*    */ import cc.slack.features.modules.api.settings.impl.BooleanValue;
/*    */ import cc.slack.features.modules.api.settings.impl.NumberValue;
/*    */ import cc.slack.utils.client.mc;
/*    */ import cc.slack.utils.player.MovementUtil;
/*    */ import io.github.nevalackin.radbus.Listen;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @ModuleInfo(name = "Tweaks", category = Category.OTHER)
/*    */ public class Tweaks
/*    */   extends Module
/*    */ {
/* 23 */   public final BooleanValue noachievement = new BooleanValue("No Achievement", true);
/* 24 */   public final BooleanValue noblockhitdelay = new BooleanValue("No Block Hit Delay", false);
/* 25 */   public final BooleanValue noclickdelay = new BooleanValue("No Click Delay", true);
/* 26 */   public final BooleanValue nojumpdelay = new BooleanValue("No Jump Delay", false);
/* 27 */   public final NumberValue<Integer> noJumpDelayTicks = new NumberValue("Jump Delay Ticks", Integer.valueOf(0), Integer.valueOf(0), Integer.valueOf(10), Integer.valueOf(1));
/* 28 */   public final BooleanValue nobosshealth = new BooleanValue("No Boss Health", false);
/* 29 */   public final BooleanValue nohurtcam = new BooleanValue("No Hurt Cam", false);
/* 30 */   private final BooleanValue fullbright = new BooleanValue("FullBright", true);
/* 31 */   private final BooleanValue exitGUIFix = new BooleanValue("Exit Gui Fix", true);
/* 32 */   public final BooleanValue noPumpkin = new BooleanValue("No Pumpkin", true);
/* 33 */   public final BooleanValue customTitle = new BooleanValue("Custom Title", false);
/*    */   
/* 35 */   float prevGamma = -1.0F;
/*    */   
/*    */   boolean wasGUI = false;
/*    */   
/*    */   public Tweaks() {
/* 40 */     addSettings(new Value[] { (Value)this.noachievement, (Value)this.noblockhitdelay, (Value)this.noclickdelay, (Value)this.nohurtcam, (Value)this.fullbright, (Value)this.nobosshealth, (Value)this.nojumpdelay, (Value)this.noJumpDelayTicks, (Value)this.exitGUIFix, (Value)this.noPumpkin, (Value)this.customTitle });
/*    */   }
/*    */   
/*    */   public void onEnable() {
/* 44 */     this.prevGamma = (mc.getGameSettings()).gammaSetting;
/*    */   }
/*    */   
/*    */   @Listen
/*    */   public void onUpdate(UpdateEvent event) {
/* 49 */     if (((Boolean)this.fullbright.getValue()).booleanValue()) {
/* 50 */       if ((mc.getGameSettings()).gammaSetting <= 100.0F) (mc.getGameSettings()).gammaSetting++; 
/* 51 */     } else if (this.prevGamma != -1.0F) {
/* 52 */       (mc.getGameSettings()).gammaSetting = this.prevGamma;
/* 53 */       this.prevGamma = -1.0F;
/*    */     } 
/*    */     
/* 56 */     if (((Boolean)this.exitGUIFix.getValue()).booleanValue()) {
/* 57 */       if (mc.getCurrentScreen() == null) {
/* 58 */         if (this.wasGUI) {
/* 59 */           MovementUtil.updateBinds();
/*    */         }
/* 61 */         this.wasGUI = false;
/*    */       } else {
/* 63 */         this.wasGUI = true;
/*    */       } 
/*    */     }
/*    */     
/* 67 */     if (((Boolean)this.noclickdelay.getValue()).booleanValue()) (mc.getMinecraft()).leftClickCounter = 0; 
/*    */   }
/*    */   
/*    */   @Listen
/*    */   public void onMotion(MotionEvent event) {
/* 72 */     if (((Boolean)this.noblockhitdelay.getValue()).booleanValue()) (mc.getPlayerController()).blockHitDelay = 0; 
/*    */   }
/*    */   
/*    */   public void onDisable() {
/* 76 */     if (this.prevGamma == -1.0F)
/* 77 */       return;  (mc.getGameSettings()).gammaSetting = this.prevGamma;
/* 78 */     this.prevGamma = -1.0F;
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\cc\slack\features\modules\impl\other\Tweaks.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */