/*    */ package cc.slack.features.modules.impl.other;
/*    */ 
/*    */ import cc.slack.events.impl.player.UpdateEvent;
/*    */ import cc.slack.features.modules.api.Category;
/*    */ import cc.slack.features.modules.api.Module;
/*    */ import cc.slack.features.modules.api.ModuleInfo;
/*    */ import cc.slack.features.modules.api.settings.Value;
/*    */ import cc.slack.features.modules.api.settings.impl.BooleanValue;
/*    */ import cc.slack.utils.client.mc;
/*    */ import io.github.nevalackin.radbus.Listen;
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import net.minecraft.potion.Potion;
/*    */ 
/*    */ 
/*    */ 
/*    */ @ModuleInfo(name = "RemoveEffect", category = Category.OTHER)
/*    */ public class RemoveEffect
/*    */   extends Module
/*    */ {
/* 21 */   private final BooleanValue shouldRemoveSlowness = new BooleanValue("Slowness", false);
/* 22 */   private final BooleanValue shouldRemoveMiningFatigue = new BooleanValue("MiningFatigue", false);
/* 23 */   private final BooleanValue shouldRemoveBlindness = new BooleanValue("Blindness", false);
/* 24 */   private final BooleanValue shouldRemoveWeakness = new BooleanValue("Weakness", false);
/* 25 */   private final BooleanValue shouldRemoveWither = new BooleanValue("Wither", false);
/* 26 */   private final BooleanValue shouldRemovePoison = new BooleanValue("Poison", false);
/* 27 */   private final BooleanValue shouldRemoveWaterBreathing = new BooleanValue("WaterBreathing", false);
/*    */ 
/*    */   
/*    */   public RemoveEffect() {
/* 31 */     addSettings(new Value[] { (Value)this.shouldRemoveSlowness, (Value)this.shouldRemoveMiningFatigue, (Value)this.shouldRemoveBlindness, (Value)this.shouldRemoveWeakness, (Value)this.shouldRemoveWither, (Value)this.shouldRemovePoison, (Value)this.shouldRemoveWaterBreathing });
/*    */   }
/*    */ 
/*    */   
/*    */   @Listen
/*    */   public void onUpdate(UpdateEvent event) {
/* 37 */     if (mc.getPlayer() != null) {
/* 38 */       List<Integer> effectIdsToRemove = new ArrayList<>();
/* 39 */       if (((Boolean)this.shouldRemoveSlowness.getValue()).booleanValue()) mc.getPlayer().removePotionEffectClient(Potion.moveSlowdown.id); 
/* 40 */       if (((Boolean)this.shouldRemoveMiningFatigue.getValue()).booleanValue()) mc.getPlayer().removePotionEffectClient(Potion.digSlowdown.id); 
/* 41 */       if (((Boolean)this.shouldRemoveBlindness.getValue()).booleanValue()) mc.getPlayer().removePotionEffectClient(Potion.blindness.id); 
/* 42 */       if (((Boolean)this.shouldRemoveWeakness.getValue()).booleanValue()) mc.getPlayer().removePotionEffectClient(Potion.weakness.id); 
/* 43 */       if (((Boolean)this.shouldRemoveWither.getValue()).booleanValue()) effectIdsToRemove.add(Integer.valueOf(Potion.wither.id)); 
/* 44 */       if (((Boolean)this.shouldRemovePoison.getValue()).booleanValue()) effectIdsToRemove.add(Integer.valueOf(Potion.poison.id)); 
/* 45 */       if (((Boolean)this.shouldRemoveWaterBreathing.getValue()).booleanValue()) effectIdsToRemove.add(Integer.valueOf(Potion.waterBreathing.id));
/*    */       
/* 47 */       for (Integer effectId : effectIdsToRemove)
/* 48 */         mc.getPlayer().removePotionEffectClient(effectId.intValue()); 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\cc\slack\features\modules\impl\other\RemoveEffect.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */