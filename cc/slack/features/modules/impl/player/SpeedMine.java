/*    */ package cc.slack.features.modules.impl.player;
/*    */ 
/*    */ import cc.slack.events.impl.player.UpdateEvent;
/*    */ import cc.slack.features.modules.api.Category;
/*    */ import cc.slack.features.modules.api.Module;
/*    */ import cc.slack.features.modules.api.ModuleInfo;
/*    */ import cc.slack.features.modules.api.settings.Value;
/*    */ import cc.slack.features.modules.api.settings.impl.ModeValue;
/*    */ import cc.slack.features.modules.api.settings.impl.NumberValue;
/*    */ import cc.slack.utils.client.mc;
/*    */ import cc.slack.utils.other.MathUtil;
/*    */ import io.github.nevalackin.radbus.Listen;
/*    */ import net.minecraft.util.MovingObjectPosition;
/*    */ 
/*    */ 
/*    */ 
/*    */ @ModuleInfo(name = "SpeedMine", category = Category.PLAYER)
/*    */ public class SpeedMine
/*    */   extends Module
/*    */ {
/* 21 */   private final ModeValue<String> mode = new ModeValue((Object[])new String[] { "Vanilla", "Percent", "Instant", "NCP" });
/* 22 */   private final NumberValue<Double> percent = new NumberValue("Percent", Double.valueOf(0.8D), Double.valueOf(0.0D), Double.valueOf(1.0D), Double.valueOf(0.05D));
/* 23 */   private final NumberValue<Double> speed = new NumberValue("Speed", Double.valueOf(1.0D), Double.valueOf(0.1D), Double.valueOf(2.0D), Double.valueOf(0.1D));
/*    */   
/*    */   public SpeedMine() {
/* 26 */     addSettings(new Value[] { (Value)this.mode, (Value)this.percent, (Value)this.speed });
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   @Listen
/*    */   public void onUpdate(UpdateEvent event) {
/* 33 */     boolean isValid = ((mc.getGameSettings()).keyBindAttack.pressed && (mc.getMinecraft()).objectMouseOver != null && (mc.getMinecraft()).objectMouseOver.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK && (mc.getMinecraft()).objectMouseOver.getBlockPos() != null);
/* 34 */     switch ((String)this.mode.getValue()) {
/*    */       case "Vanilla":
/* 36 */         if ((mc.getPlayerController()).curBlockDamageMP * ((Double)this.speed.getValue()).doubleValue() > 1.0D) {
/* 37 */           (mc.getPlayerController()).curBlockDamageMP = 1.0F;
/*    */         }
/*    */         break;
/*    */       case "Instant":
/* 41 */         if (isValid) {
/* 42 */           (mc.getPlayerController()).curBlockDamageMP = 1.0F;
/*    */         }
/*    */         break;
/*    */       case "Percent":
/* 46 */         if ((mc.getPlayerController()).curBlockDamageMP >= ((Double)this.percent.getValue()).doubleValue()) {
/* 47 */           (mc.getPlayerController()).curBlockDamageMP = 1.0F;
/*    */         }
/*    */         break;
/*    */       case "NCP":
/* 51 */         if (isValid && 
/* 52 */           (mc.getPlayerController()).curBlockDamageMP >= 0.5F && !(mc.getPlayer()).isDead)
/* 53 */           (mc.getPlayerController()).curBlockDamageMP += MathUtil.getDifference((mc.getPlayerController()).curBlockDamageMP, 1.0F) * 0.7F; 
/*    */         break;
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\cc\slack\features\modules\impl\player\SpeedMine.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */