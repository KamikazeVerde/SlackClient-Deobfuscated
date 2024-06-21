/*    */ package net.optifine.entity.model;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import net.minecraft.client.model.ModelBase;
/*    */ import net.minecraft.client.model.ModelRenderer;
/*    */ 
/*    */ 
/*    */ public abstract class ModelAdapter
/*    */ {
/*    */   private Class entityClass;
/*    */   private String name;
/*    */   private float shadowSize;
/*    */   private String[] aliases;
/*    */   
/*    */   public ModelAdapter(Class entityClass, String name, float shadowSize) {
/* 17 */     this.entityClass = entityClass;
/* 18 */     this.name = name;
/* 19 */     this.shadowSize = shadowSize;
/*    */   }
/*    */ 
/*    */   
/*    */   public ModelAdapter(Class entityClass, String name, float shadowSize, String[] aliases) {
/* 24 */     this.entityClass = entityClass;
/* 25 */     this.name = name;
/* 26 */     this.shadowSize = shadowSize;
/* 27 */     this.aliases = aliases;
/*    */   }
/*    */ 
/*    */   
/*    */   public Class getEntityClass() {
/* 32 */     return this.entityClass;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getName() {
/* 37 */     return this.name;
/*    */   }
/*    */ 
/*    */   
/*    */   public String[] getAliases() {
/* 42 */     return this.aliases;
/*    */   }
/*    */ 
/*    */   
/*    */   public float getShadowSize() {
/* 47 */     return this.shadowSize;
/*    */   }
/*    */ 
/*    */   
/*    */   public abstract ModelBase makeModel();
/*    */   
/*    */   public abstract ModelRenderer getModelRenderer(ModelBase paramModelBase, String paramString);
/*    */   
/*    */   public abstract String[] getModelRendererNames();
/*    */   
/*    */   public abstract IEntityRenderer makeEntityRender(ModelBase paramModelBase, float paramFloat);
/*    */   
/*    */   public ModelRenderer[] getModelRenderers(ModelBase model) {
/* 60 */     String[] astring = getModelRendererNames();
/* 61 */     List<ModelRenderer> list = new ArrayList<>();
/*    */     
/* 63 */     for (int i = 0; i < astring.length; i++) {
/*    */       
/* 65 */       String s = astring[i];
/* 66 */       ModelRenderer modelrenderer = getModelRenderer(model, s);
/*    */       
/* 68 */       if (modelrenderer != null)
/*    */       {
/* 70 */         list.add(modelrenderer);
/*    */       }
/*    */     } 
/*    */     
/* 74 */     ModelRenderer[] amodelrenderer = list.<ModelRenderer>toArray(new ModelRenderer[list.size()]);
/* 75 */     return amodelrenderer;
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\optifine\entity\model\ModelAdapter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */