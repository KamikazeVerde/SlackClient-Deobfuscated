/*    */ package net.optifine.entity.model;
/*    */ 
/*    */ import java.util.Iterator;
/*    */ import java.util.List;
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.model.ModelBase;
/*    */ import net.minecraft.client.model.ModelSheep1;
/*    */ import net.minecraft.client.model.ModelSheep2;
/*    */ import net.minecraft.client.renderer.entity.Render;
/*    */ import net.minecraft.client.renderer.entity.RenderManager;
/*    */ import net.minecraft.client.renderer.entity.RenderSheep;
/*    */ import net.minecraft.client.renderer.entity.layers.LayerRenderer;
/*    */ import net.minecraft.client.renderer.entity.layers.LayerSheepWool;
/*    */ import net.minecraft.entity.passive.EntitySheep;
/*    */ import net.minecraft.src.Config;
/*    */ 
/*    */ public class ModelAdapterSheepWool
/*    */   extends ModelAdapterQuadruped
/*    */ {
/*    */   public ModelAdapterSheepWool() {
/* 21 */     super(EntitySheep.class, "sheep_wool", 0.7F);
/*    */   }
/*    */ 
/*    */   
/*    */   public ModelBase makeModel() {
/* 26 */     return (ModelBase)new ModelSheep1();
/*    */   }
/*    */   
/*    */   public IEntityRenderer makeEntityRender(ModelBase modelBase, float shadowSize) {
/*    */     RenderSheep renderSheep1;
/* 31 */     RenderManager rendermanager = Minecraft.getMinecraft().getRenderManager();
/* 32 */     Render render = (Render)rendermanager.getEntityRenderMap().get(EntitySheep.class);
/*    */     
/* 34 */     if (!(render instanceof RenderSheep)) {
/*    */       
/* 36 */       Config.warn("Not a RenderSheep: " + render);
/* 37 */       return null;
/*    */     } 
/*    */ 
/*    */     
/* 41 */     if (render.getEntityClass() == null)
/*    */     {
/* 43 */       renderSheep1 = new RenderSheep(rendermanager, (ModelBase)new ModelSheep2(), 0.7F);
/*    */     }
/*    */     
/* 46 */     RenderSheep rendersheep = renderSheep1;
/* 47 */     List<LayerRenderer<EntitySheep>> list = rendersheep.getLayerRenderers();
/* 48 */     Iterator<LayerRenderer<EntitySheep>> iterator = list.iterator();
/*    */     
/* 50 */     while (iterator.hasNext()) {
/*    */       
/* 52 */       LayerRenderer layerrenderer = iterator.next();
/*    */       
/* 54 */       if (layerrenderer instanceof LayerSheepWool)
/*    */       {
/* 56 */         iterator.remove();
/*    */       }
/*    */     } 
/*    */     
/* 60 */     LayerSheepWool layersheepwool = new LayerSheepWool(rendersheep);
/* 61 */     layersheepwool.sheepModel = (ModelSheep1)modelBase;
/* 62 */     rendersheep.addLayer((LayerRenderer)layersheepwool);
/* 63 */     return (IEntityRenderer)rendersheep;
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\optifine\entity\model\ModelAdapterSheepWool.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */