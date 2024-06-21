/*    */ package cc.slack.features.modules.impl.render;
/*    */ 
/*    */ import cc.slack.events.impl.render.RenderEvent;
/*    */ import cc.slack.features.modules.api.Category;
/*    */ import cc.slack.features.modules.api.Module;
/*    */ import cc.slack.features.modules.api.ModuleInfo;
/*    */ import cc.slack.utils.render.Render3DUtil;
/*    */ import io.github.nevalackin.radbus.Listen;
/*    */ import java.util.ArrayList;
/*    */ import net.minecraft.block.Block;
/*    */ import net.minecraft.util.AxisAlignedBB;
/*    */ import net.minecraft.util.BlockPos;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @ModuleInfo(name = "ChestESP", category = Category.RENDER)
/*    */ public class ChestESP
/*    */   extends Module
/*    */ {
/* 22 */   public ArrayList<BlockPos> chestBoundingBoxes = new ArrayList<>();
/*    */   
/* 24 */   private final Integer[] chestIDS = new Integer[] { Integer.valueOf(54), Integer.valueOf(130), Integer.valueOf(146) };
/*    */   
/*    */   @Listen
/*    */   public void onRender(RenderEvent event) {
/* 28 */     if (event.getState() != RenderEvent.State.RENDER_3D)
/*    */       return; 
/* 30 */     for (BlockPos bp : this.chestBoundingBoxes) {
/* 31 */       Render3DUtil.drawAABB(AxisAlignedBB.fromBounds(bp.getX(), bp.getY(), bp.getZ(), (bp.getX() + 1), (bp.getY() + 1), (bp.getZ() + 1)));
/*    */     }
/*    */     
/* 34 */     this.chestBoundingBoxes.clear(); } public boolean isChest(Block block) {
/*    */     Integer[] arrayOfInteger;
/*    */     int i;
/*    */     byte b;
/* 38 */     for (arrayOfInteger = this.chestIDS, i = arrayOfInteger.length, b = 0; b < i; ) { int id = arrayOfInteger[b].intValue();
/* 39 */       if (block != Block.getBlockById(id)) { b++; continue; }
/* 40 */        return true; }
/*    */     
/* 42 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\cc\slack\features\modules\impl\render\ChestESP.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */