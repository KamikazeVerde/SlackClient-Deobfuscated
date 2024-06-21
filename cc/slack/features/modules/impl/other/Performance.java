/*    */ package cc.slack.features.modules.impl.other;
/*    */ 
/*    */ import cc.slack.events.impl.player.UpdateEvent;
/*    */ import cc.slack.features.modules.api.Category;
/*    */ import cc.slack.features.modules.api.Module;
/*    */ import cc.slack.features.modules.api.ModuleInfo;
/*    */ import cc.slack.features.modules.api.settings.Value;
/*    */ import cc.slack.features.modules.api.settings.impl.BooleanValue;
/*    */ import cc.slack.features.modules.api.settings.impl.ModeValue;
/*    */ import cc.slack.features.modules.api.settings.impl.NumberValue;
/*    */ import cc.slack.utils.client.mc;
/*    */ import cc.slack.utils.other.PrintUtil;
/*    */ import io.github.nevalackin.radbus.Listen;
/*    */ import net.minecraft.client.settings.GameSettings;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @ModuleInfo(name = "Performance", category = Category.OTHER)
/*    */ public class Performance
/*    */   extends Module
/*    */ {
/* 23 */   public ModeValue<String> performancemode = new ModeValue((Object[])new String[] { "None", "Simple", "Extreme" });
/* 24 */   public NumberValue<Integer> chunkupdates = new NumberValue("Chunk Updates", Integer.valueOf(1), Integer.valueOf(1), Integer.valueOf(10), Integer.valueOf(1));
/* 25 */   public BooleanValue garbagevalue = new BooleanValue("MemoryFix", false);
/*    */   
/*    */   public Performance() {
/* 28 */     addSettings(new Value[] { (Value)this.performancemode, (Value)this.chunkupdates, (Value)this.garbagevalue });
/*    */   }
/*    */ 
/*    */   
/*    */   public void onEnable() {
/* 33 */     switch ((String)this.performancemode.getValue()) {
/*    */       case "Simple":
/* 35 */         PrintUtil.message("Performance Mode Simple is enabled successfully");
/*    */         break;
/*    */       case "Extreme":
/* 38 */         PrintUtil.message("Performance Mode EXTREME is enabled successfully");
/*    */         break;
/*    */     } 
/*    */   }
/*    */   
/*    */   @Listen
/*    */   public void onUpdate(UpdateEvent event) {
/* 45 */     if (((Boolean)this.garbagevalue.getValue()).booleanValue() && (mc.getPlayer()).ticksExisted % 5000 == 0) {
/* 46 */       System.gc();
/*    */     }
/* 48 */     configureGSettings((String)this.performancemode.getValue());
/*    */   }
/*    */   
/*    */   private void configureGSettings(String mode) {
/* 52 */     GameSettings settings = (mc.getMinecraft()).gameSettings;
/*    */     
/* 54 */     settings.ofChunkUpdates = ((Integer)this.chunkupdates.getValue()).intValue();
/*    */     
/* 56 */     switch (mode) {
/*    */       case "Simple":
/* 58 */         settings.fancyGraphics = false;
/*    */         break;
/*    */       case "Extreme":
/* 61 */         settings.clouds = 0;
/* 62 */         settings.ofCloudsHeight = 0.0F;
/* 63 */         settings.fancyGraphics = false;
/* 64 */         settings.mipmapLevels = 0;
/* 65 */         settings.particleSetting = 2;
/* 66 */         settings.ofDynamicLights = 0;
/* 67 */         settings.ofSmoothBiomes = false;
/* 68 */         settings.field_181151_V = false;
/* 69 */         settings.ofTranslucentBlocks = 1;
/* 70 */         settings.ofDroppedItems = 1;
/*    */         break;
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\cc\slack\features\modules\impl\other\Performance.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */