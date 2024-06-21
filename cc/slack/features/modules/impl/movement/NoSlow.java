/*    */ package cc.slack.features.modules.impl.movement;
/*    */ 
/*    */ import cc.slack.Slack;
/*    */ import cc.slack.events.impl.player.UpdateEvent;
/*    */ import cc.slack.features.modules.api.Category;
/*    */ import cc.slack.features.modules.api.Module;
/*    */ import cc.slack.features.modules.api.ModuleInfo;
/*    */ import cc.slack.features.modules.api.settings.Value;
/*    */ import cc.slack.features.modules.api.settings.impl.BooleanValue;
/*    */ import cc.slack.features.modules.api.settings.impl.ModeValue;
/*    */ import cc.slack.features.modules.api.settings.impl.NumberValue;
/*    */ import cc.slack.features.modules.impl.combat.KillAura;
/*    */ import cc.slack.utils.client.mc;
/*    */ import cc.slack.utils.network.PacketUtil;
/*    */ import io.github.nevalackin.radbus.Listen;
/*    */ import net.minecraft.network.Packet;
/*    */ import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
/*    */ import net.minecraft.network.play.client.C09PacketHeldItemChange;
/*    */ import net.minecraft.util.BlockPos;
/*    */ import net.minecraft.util.EnumFacing;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @ModuleInfo(name = "NoSlow", category = Category.MOVEMENT)
/*    */ public class NoSlow
/*    */   extends Module
/*    */ {
/* 32 */   public final ModeValue<String> mode = new ModeValue("Bypass Mode", (Object[])new String[] { "Vanilla", "Vulcan", "NCP Latest", "Hypixel", "Switch", "Place", "C08 Tick" });
/*    */   
/* 34 */   public final NumberValue<Float> forwardMultiplier = new NumberValue("Forward Multiplier", Float.valueOf(1.0F), Float.valueOf(0.2F), Float.valueOf(1.0F), Float.valueOf(0.05F));
/* 35 */   public final NumberValue<Float> strafeMultiplier = new NumberValue("Strafe Multiplier", Float.valueOf(1.0F), Float.valueOf(0.2F), Float.valueOf(1.0F), Float.valueOf(0.05F));
/*    */   
/* 37 */   public final BooleanValue onEat = new BooleanValue("Eating NoSlow", true);
/*    */ 
/*    */   
/*    */   public NoSlow() {
/* 41 */     addSettings(new Value[] { (Value)this.mode, (Value)this.forwardMultiplier, (Value)this.strafeMultiplier, (Value)this.onEat });
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   @Listen
/*    */   public void onUpdate(UpdateEvent event) {
/* 48 */     if (mc.getPlayer() == null)
/* 49 */       return;  boolean usingItem = (mc.getPlayer().isUsingItem() || ((KillAura)Slack.getInstance().getModuleManager().getInstance(KillAura.class)).isToggle() || ((KillAura)Slack.getInstance().getModuleManager().getInstance(KillAura.class)).isBlocking);
/*    */     
/* 51 */     if ((usingItem && mc.getPlayer().getHeldItem() != null && (mc.getPlayer().getHeldItem()).item instanceof net.minecraft.item.ItemSword) || (mc.getPlayer().isUsingItem() && (mc.getPlayer().getHeldItem()).item instanceof net.minecraft.item.ItemFood && ((Boolean)this.onEat.getValue()).booleanValue()))
/* 52 */       switch (((String)this.mode.getValue()).toLowerCase()) {
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */         
/*    */         case "switch":
/* 59 */           mc.getNetHandler().addToSendQueue((Packet)new C09PacketHeldItemChange((mc.getPlayer()).inventory.currentItem % 8 + 1));
/* 60 */           mc.getNetHandler().addToSendQueue((Packet)new C09PacketHeldItemChange((mc.getPlayer()).inventory.currentItem));
/*    */           break;
/*    */         case "place":
/* 63 */           mc.getNetHandler().addToSendQueue((Packet)new C08PacketPlayerBlockPlacement((mc.getPlayer()).inventory.getCurrentItem()));
/*    */           break;
/*    */         case "c08 tick":
/* 66 */           if ((mc.getPlayer()).ticksExisted % 3 == 0) {
/* 67 */             mc.getNetHandler().addToSendQueue((Packet)new C08PacketPlayerBlockPlacement(mc.getPlayer().getHeldItem()));
/*    */           }
/*    */           break;
/*    */         case "hypixel":
/* 71 */           if (mc.getPlayer().isUsingItem() && mc.getPlayer().getHeldItem() != null && mc.getPlayer().getHeldItem().getItem() instanceof net.minecraft.item.ItemSword) {
/* 72 */             PacketUtil.sendBlocking(true, false);
/* 73 */             if (mc.getPlayer().isUsingItem() || mc.getPlayer().isBlocking() || (mc.getPlayer()).ticksExisted % 3 != 0)
/*    */               break; 
/* 75 */             PacketUtil.sendNoEvent((Packet)new C08PacketPlayerBlockPlacement(new BlockPos(-1, -1, -1), EnumFacing.UP.getIndex(), null, 0.0F, 0.0F, 0.0F));
/*    */           } 
/*    */           break;
/*    */       }  
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\cc\slack\features\modules\impl\movement\NoSlow.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */