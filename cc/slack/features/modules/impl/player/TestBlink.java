/*    */ package cc.slack.features.modules.impl.player;
/*    */ 
/*    */ import cc.slack.events.impl.player.UpdateEvent;
/*    */ import cc.slack.features.modules.api.Category;
/*    */ import cc.slack.features.modules.api.Module;
/*    */ import cc.slack.features.modules.api.ModuleInfo;
/*    */ import cc.slack.utils.client.mc;
/*    */ import cc.slack.utils.player.BlinkUtil;
/*    */ import io.github.nevalackin.radbus.Listen;
/*    */ import java.io.IOException;
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.settings.GameSettings;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @ModuleInfo(name = "TestBlink", category = Category.PLAYER)
/*    */ public class TestBlink
/*    */   extends Module
/*    */ {
/* 26 */   List<GameSettings> inputReplay = new ArrayList<>();
/* 27 */   private Minecraft startingMC = mc.getMinecraft();
/* 28 */   private int ticks = 0;
/*    */ 
/*    */   
/*    */   public void onEnable() {
/* 32 */     BlinkUtil.enable(true, true);
/* 33 */     this.inputReplay.clear();
/* 34 */     this.startingMC = mc.getMinecraft();
/* 35 */     this.ticks = 0;
/*    */   }
/*    */ 
/*    */   
/*    */   @Listen
/*    */   public void onUpdate(UpdateEvent event) {
/* 41 */     this.inputReplay.add(mc.getGameSettings());
/* 42 */     this.ticks++;
/*    */   }
/*    */ 
/*    */   
/*    */   public void onDisable() {
/* 47 */     BlinkUtil.clearPackets(false, true);
/* 48 */     BlinkUtil.setConfig(true, false);
/* 49 */     Minecraft.setMinecraft(this.startingMC);
/* 50 */     for (int i = 1; i <= this.ticks; i++) {
/*    */       try {
/* 52 */         (mc.getMinecraft()).gameSettings = this.inputReplay.get(i);
/* 53 */         mc.getMinecraft().runTick();
/* 54 */       } catch (IOException iOException) {}
/*    */     } 
/*    */ 
/*    */     
/* 58 */     BlinkUtil.disable(true);
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\cc\slack\features\modules\impl\player\TestBlink.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */