/*    */ package cc.slack.features.modules.impl.player;
/*    */ 
/*    */ import cc.slack.events.impl.player.UpdateEvent;
/*    */ import cc.slack.features.modules.api.Category;
/*    */ import cc.slack.features.modules.api.Module;
/*    */ import cc.slack.features.modules.api.ModuleInfo;
/*    */ import cc.slack.features.modules.api.settings.Value;
/*    */ import cc.slack.features.modules.api.settings.impl.ModeValue;
/*    */ import cc.slack.utils.client.mc;
/*    */ import cc.slack.utils.network.PacketUtil;
/*    */ import cc.slack.utils.player.MovementUtil;
/*    */ import io.github.nevalackin.radbus.Listen;
/*    */ import net.minecraft.network.Packet;
/*    */ import net.minecraft.network.play.client.C03PacketPlayer;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @ModuleInfo(name = "FastEat", category = Category.PLAYER)
/*    */ public class FastEat
/*    */   extends Module
/*    */ {
/* 25 */   private final ModeValue<String> mode = new ModeValue((Object[])new String[] { "Instant", "Clip" });
/*    */   
/*    */   public FastEat() {
/* 28 */     addSettings(new Value[] { (Value)this.mode });
/*    */   }
/*    */ 
/*    */   
/*    */   double startY;
/*    */   
/*    */   @Listen
/*    */   public void onUpdate(UpdateEvent event) {
/* 36 */     if (mc.getPlayer().isUsingItem() && (mc.getPlayer().getItemInUse().getItem() instanceof net.minecraft.item.ItemFood || mc.getPlayer().getItemInUse().getItem() instanceof net.minecraft.item.ItemPotion || mc.getPlayer().getItemInUse().getItem() instanceof net.minecraft.item.ItemBucketMilk))
/* 37 */       switch ((String)this.mode.getValue()) {
/*    */         case "Instant":
/* 39 */           PacketUtil.sendNoEvent((Packet)new C03PacketPlayer((mc.getPlayer()).onGround), 30);
/*    */           break;
/*    */         case "Clip":
/* 42 */           this.startY = (mc.getPlayer()).posY;
/* 43 */           MovementUtil.resetMotion(false);
/* 44 */           if ((mc.getPlayer()).onGround) {
/* 45 */             if ((mc.getPlayer()).posY <= this.startY)
/* 46 */               mc.getPlayer().setPosition((mc.getPlayer()).posX, (mc.getPlayer()).posY - 1.0E-8D, (mc.getPlayer()).posZ);  break;
/*    */           } 
/* 48 */           if ((mc.getPlayer()).posY <= this.startY) PacketUtil.sendNoEvent((Packet)new C03PacketPlayer(false), 3); 
/*    */           break;
/*    */       }  
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\cc\slack\features\modules\impl\player\FastEat.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */