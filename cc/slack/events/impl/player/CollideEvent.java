/*    */ package cc.slack.events.impl.player;
/*    */ 
/*    */ import cc.slack.events.Event;
/*    */ import net.minecraft.block.Block;
/*    */ import net.minecraft.util.AxisAlignedBB;
/*    */ 
/*    */ public class CollideEvent extends Event {
/*    */   public Block block;
/*    */   public AxisAlignedBB boundingBox;
/*    */   
/* 11 */   public void setBlock(Block block) { this.block = block; } public double x; public double y; public double z; public void setBoundingBox(AxisAlignedBB boundingBox) { this.boundingBox = boundingBox; } public void setX(double x) { this.x = x; } public void setY(double y) { this.y = y; } public void setZ(double z) { this.z = z; } public CollideEvent(Block block, AxisAlignedBB boundingBox, double x, double y, double z) {
/* 12 */     this.block = block; this.boundingBox = boundingBox; this.x = x; this.y = y; this.z = z;
/*    */   }
/* 14 */   public Block getBlock() { return this.block; }
/* 15 */   public AxisAlignedBB getBoundingBox() { return this.boundingBox; }
/* 16 */   public double getX() { return this.x; } public double getY() { return this.y; } public double getZ() { return this.z; }
/*    */ 
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\cc\slack\events\impl\player\CollideEvent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */