/*    */ package de.florianmichael.viamcp.fixes;
/*    */ 
/*    */ import com.viaversion.viaversion.api.protocol.version.ProtocolVersion;
/*    */ import de.florianmichael.vialoadingbase.ViaLoadingBase;
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.util.MovingObjectPosition;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class AttackOrder
/*    */ {
/* 29 */   private static final Minecraft mc = Minecraft.getMinecraft();
/*    */   
/*    */   public static void sendConditionalSwing(MovingObjectPosition mop) {
/* 32 */     if (mop != null && mop.typeOfHit != MovingObjectPosition.MovingObjectType.ENTITY) mc.thePlayer.swingItem(); 
/*    */   }
/*    */   
/*    */   public static void sendFixedAttack(EntityPlayer entityIn, Entity target) {
/* 36 */     if (ViaLoadingBase.getInstance().getTargetVersion().isOlderThanOrEqualTo(ProtocolVersion.v1_8)) {
/* 37 */       mc.thePlayer.swingItem();
/* 38 */       mc.playerController.attackEntity(entityIn, target);
/*    */     } else {
/* 40 */       mc.playerController.attackEntity(entityIn, target);
/* 41 */       mc.thePlayer.swingItem();
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\de\florianmichael\viamcp\fixes\AttackOrder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */