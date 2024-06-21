/*    */ package cc.slack.features.modules.impl.render;
/*    */ 
/*    */ import cc.slack.events.impl.render.RenderEvent;
/*    */ import cc.slack.features.modules.api.Category;
/*    */ import cc.slack.features.modules.api.Module;
/*    */ import cc.slack.features.modules.api.ModuleInfo;
/*    */ import cc.slack.features.modules.api.settings.Value;
/*    */ import cc.slack.features.modules.api.settings.impl.NumberValue;
/*    */ import cc.slack.utils.client.mc;
/*    */ import cc.slack.utils.render.Render3DUtil;
/*    */ import io.github.nevalackin.radbus.Listen;
/*    */ import net.minecraft.client.renderer.GlStateManager;
/*    */ import net.minecraft.client.renderer.entity.RenderManager;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.util.AxisAlignedBB;
/*    */ import net.minecraft.util.Timer;
/*    */ import org.lwjgl.opengl.GL11;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @ModuleInfo(name = "ESP", category = Category.RENDER)
/*    */ public class ESP
/*    */   extends Module
/*    */ {
/* 27 */   private final NumberValue<Float> lineWidth = new NumberValue("Line Width", Float.valueOf(1.0F), Float.valueOf(1.0F), Float.valueOf(3.0F), Float.valueOf(0.1F));
/*    */   
/*    */   public ESP() {
/* 30 */     addSettings(new Value[] { (Value)this.lineWidth });
/*    */   }
/*    */   
/*    */   @Listen
/*    */   public void onRender(RenderEvent event) {
/* 35 */     if (event.getState() != RenderEvent.State.RENDER_3D)
/*    */       return; 
/* 37 */     for (Entity entity : (mc.getWorld()).loadedEntityList) {
/* 38 */       if (entity.getEntityId() == mc.getPlayer().getEntityId())
/* 39 */         continue;  RenderManager renderManager = mc.getRenderManager();
/* 40 */       Timer timer = mc.getTimer();
/*    */       
/* 42 */       GL11.glBlendFunc(770, 771);
/* 43 */       Render3DUtil.enableGlCap(3042);
/* 44 */       Render3DUtil.disableGlCap(new int[] { 3553, 2929 });
/* 45 */       GL11.glDepthMask(false);
/*    */       
/* 47 */       double x = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * timer.renderPartialTicks - renderManager.renderPosX;
/*    */       
/* 49 */       double y = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * timer.renderPartialTicks - renderManager.renderPosY;
/*    */       
/* 51 */       double z = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * timer.renderPartialTicks - renderManager.renderPosZ;
/*    */ 
/*    */       
/* 54 */       AxisAlignedBB entityBox = entity.getEntityBoundingBox();
/* 55 */       AxisAlignedBB axisAlignedBB = new AxisAlignedBB(entityBox.minX - entity.posX + x - 0.05D, entityBox.minY - entity.posY + y, entityBox.minZ - entity.posZ + z - 0.05D, entityBox.maxX - entity.posX + x + 0.05D, entityBox.maxY - entity.posY + y + 0.15D, entityBox.maxZ - entity.posZ + z + 0.05D);
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */       
/* 64 */       GL11.glLineWidth(((Float)this.lineWidth.getValue()).floatValue());
/* 65 */       Render3DUtil.enableGlCap(2848);
/* 66 */       if (entity.hurtResistantTime > 1) {
/* 67 */         glColor(255, 10, 10, 95);
/*    */       } else {
/* 69 */         glColor(255, 255, 255, 95);
/*    */       } 
/* 71 */       Render3DUtil.drawSelectionBoundingBox(axisAlignedBB);
/*    */       
/* 73 */       GlStateManager.resetColor();
/* 74 */       GL11.glDepthMask(true);
/* 75 */       Render3DUtil.resetCaps();
/*    */     } 
/*    */   }
/*    */   
/*    */   public static void glColor(int red, int green, int blue, int alpha) {
/* 80 */     GlStateManager.color(red / 255.0F, green / 255.0F, blue / 255.0F, alpha / 255.0F);
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\cc\slack\features\modules\impl\render\ESP.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */