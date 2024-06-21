/*    */ package net.optifine.entity.model;
/*    */ 
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.model.ModelBase;
/*    */ import net.minecraft.client.model.ModelOcelot;
/*    */ import net.minecraft.client.model.ModelRenderer;
/*    */ import net.minecraft.client.renderer.entity.RenderManager;
/*    */ import net.minecraft.client.renderer.entity.RenderOcelot;
/*    */ import net.minecraft.entity.passive.EntityOcelot;
/*    */ import net.optifine.reflect.Reflector;
/*    */ 
/*    */ public class ModelAdapterOcelot
/*    */   extends ModelAdapter {
/* 16 */   private static Map<String, Integer> mapPartFields = null;
/*    */ 
/*    */   
/*    */   public ModelAdapterOcelot() {
/* 20 */     super(EntityOcelot.class, "ocelot", 0.4F);
/*    */   }
/*    */ 
/*    */   
/*    */   public ModelBase makeModel() {
/* 25 */     return (ModelBase)new ModelOcelot();
/*    */   }
/*    */ 
/*    */   
/*    */   public ModelRenderer getModelRenderer(ModelBase model, String modelPart) {
/* 30 */     if (!(model instanceof ModelOcelot))
/*    */     {
/* 32 */       return null;
/*    */     }
/*    */ 
/*    */     
/* 36 */     ModelOcelot modelocelot = (ModelOcelot)model;
/* 37 */     Map<String, Integer> map = getMapPartFields();
/*    */     
/* 39 */     if (map.containsKey(modelPart)) {
/*    */       
/* 41 */       int i = ((Integer)map.get(modelPart)).intValue();
/* 42 */       return (ModelRenderer)Reflector.getFieldValue(modelocelot, Reflector.ModelOcelot_ModelRenderers, i);
/*    */     } 
/*    */ 
/*    */     
/* 46 */     return null;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String[] getModelRendererNames() {
/* 53 */     return new String[] { "back_left_leg", "back_right_leg", "front_left_leg", "front_right_leg", "tail", "tail2", "head", "body" };
/*    */   }
/*    */ 
/*    */   
/*    */   private static Map<String, Integer> getMapPartFields() {
/* 58 */     if (mapPartFields != null)
/*    */     {
/* 60 */       return mapPartFields;
/*    */     }
/*    */ 
/*    */     
/* 64 */     mapPartFields = new HashMap<>();
/* 65 */     mapPartFields.put("back_left_leg", Integer.valueOf(0));
/* 66 */     mapPartFields.put("back_right_leg", Integer.valueOf(1));
/* 67 */     mapPartFields.put("front_left_leg", Integer.valueOf(2));
/* 68 */     mapPartFields.put("front_right_leg", Integer.valueOf(3));
/* 69 */     mapPartFields.put("tail", Integer.valueOf(4));
/* 70 */     mapPartFields.put("tail2", Integer.valueOf(5));
/* 71 */     mapPartFields.put("head", Integer.valueOf(6));
/* 72 */     mapPartFields.put("body", Integer.valueOf(7));
/* 73 */     return mapPartFields;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public IEntityRenderer makeEntityRender(ModelBase modelBase, float shadowSize) {
/* 79 */     RenderManager rendermanager = Minecraft.getMinecraft().getRenderManager();
/* 80 */     return (IEntityRenderer)new RenderOcelot(rendermanager, modelBase, shadowSize);
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\optifine\entity\model\ModelAdapterOcelot.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */