/*    */ package cc.slack.features.modules.impl.render;
/*    */ 
/*    */ import cc.slack.features.modules.api.Category;
/*    */ import cc.slack.features.modules.api.Module;
/*    */ import cc.slack.features.modules.api.ModuleInfo;
/*    */ import cc.slack.utils.client.mc;
/*    */ import net.minecraft.block.Block;
/*    */ import net.minecraft.client.Minecraft;
/*    */ 
/*    */ 
/*    */ 
/*    */ @ModuleInfo(name = "XRay", category = Category.RENDER)
/*    */ public class XRay
/*    */   extends Module
/*    */ {
/*    */   private float oldgammavalue;
/* 17 */   private final int[] blockIds = new int[] { 14, 15, 56, 129 };
/*    */ 
/*    */ 
/*    */   
/*    */   public void onEnable() {
/* 22 */     this.oldgammavalue = (mc.getGameSettings()).gammaSetting;
/* 23 */     (mc.getGameSettings()).gammaSetting = 10.0F;
/* 24 */     (mc.getGameSettings()).ambientOcclusion = 0;
/* 25 */     (Minecraft.getMinecraft()).renderGlobal.loadRenderers();
/*    */   }
/*    */ 
/*    */   
/*    */   public void onDisable() {
/* 30 */     (mc.getGameSettings()).gammaSetting = this.oldgammavalue;
/* 31 */     (mc.getGameSettings()).ambientOcclusion = 1;
/* 32 */     (Minecraft.getMinecraft()).renderGlobal.loadRenderers(); } public boolean shouldRenderBlock(Block block) {
/*    */     int[] arrayOfInt;
/*    */     int i;
/*    */     byte b;
/* 36 */     for (arrayOfInt = this.blockIds, i = arrayOfInt.length, b = 0; b < i; ) { int id = arrayOfInt[b];
/* 37 */       if (block != Block.getBlockById(id)) { b++; continue; }
/* 38 */        return true; }
/*    */     
/* 40 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\cc\slack\features\modules\impl\render\XRay.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */