/*    */ package net.minecraft.tileentity;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class TileEntityDropper
/*    */   extends TileEntityDispenser
/*    */ {
/*    */   public String getCommandSenderName() {
/* 10 */     return hasCustomName() ? this.customName : "container.dropper";
/*    */   }
/*    */ 
/*    */   
/*    */   public String getGuiID() {
/* 15 */     return "minecraft:dropper";
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\minecraft\tileentity\TileEntityDropper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */