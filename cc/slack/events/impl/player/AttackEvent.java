/*    */ package cc.slack.events.impl.player;
/*    */ 
/*    */ import cc.slack.events.Event;
/*    */ import net.minecraft.entity.Entity;
/*    */ 
/*    */ public class AttackEvent extends Event {
/*    */   private Entity targetEntity;
/*    */   
/*    */   public void setTargetEntity(Entity targetEntity) {
/* 10 */     this.targetEntity = targetEntity; } public AttackEvent(Entity targetEntity) {
/* 11 */     this.targetEntity = targetEntity;
/*    */   } public Entity getTargetEntity() {
/* 13 */     return this.targetEntity;
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\cc\slack\events\impl\player\AttackEvent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */