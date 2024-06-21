/*    */ package com.viaversion.viaversion.libs.kyori.adventure.text.renderer;
/*    */ 
/*    */ import com.viaversion.viaversion.libs.kyori.adventure.text.BlockNBTComponent;
/*    */ import com.viaversion.viaversion.libs.kyori.adventure.text.Component;
/*    */ import com.viaversion.viaversion.libs.kyori.adventure.text.EntityNBTComponent;
/*    */ import com.viaversion.viaversion.libs.kyori.adventure.text.KeybindComponent;
/*    */ import com.viaversion.viaversion.libs.kyori.adventure.text.ScoreComponent;
/*    */ import com.viaversion.viaversion.libs.kyori.adventure.text.SelectorComponent;
/*    */ import com.viaversion.viaversion.libs.kyori.adventure.text.StorageNBTComponent;
/*    */ import com.viaversion.viaversion.libs.kyori.adventure.text.TextComponent;
/*    */ import com.viaversion.viaversion.libs.kyori.adventure.text.TranslatableComponent;
/*    */ import org.jetbrains.annotations.NotNull;
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
/*    */ public abstract class AbstractComponentRenderer<C>
/*    */   implements ComponentRenderer<C>
/*    */ {
/*    */   @NotNull
/*    */   public Component render(@NotNull Component component, @NotNull C context) {
/* 47 */     if (component instanceof TextComponent)
/* 48 */       return renderText((TextComponent)component, context); 
/* 49 */     if (component instanceof TranslatableComponent)
/* 50 */       return renderTranslatable((TranslatableComponent)component, context); 
/* 51 */     if (component instanceof KeybindComponent)
/* 52 */       return renderKeybind((KeybindComponent)component, context); 
/* 53 */     if (component instanceof ScoreComponent)
/* 54 */       return renderScore((ScoreComponent)component, context); 
/* 55 */     if (component instanceof SelectorComponent)
/* 56 */       return renderSelector((SelectorComponent)component, context); 
/* 57 */     if (component instanceof com.viaversion.viaversion.libs.kyori.adventure.text.NBTComponent) {
/* 58 */       if (component instanceof BlockNBTComponent)
/* 59 */         return renderBlockNbt((BlockNBTComponent)component, context); 
/* 60 */       if (component instanceof EntityNBTComponent)
/* 61 */         return renderEntityNbt((EntityNBTComponent)component, context); 
/* 62 */       if (component instanceof StorageNBTComponent) {
/* 63 */         return renderStorageNbt((StorageNBTComponent)component, context);
/*    */       }
/*    */     } 
/* 66 */     return component;
/*    */   }
/*    */   
/*    */   @NotNull
/*    */   protected abstract Component renderBlockNbt(@NotNull BlockNBTComponent paramBlockNBTComponent, @NotNull C paramC);
/*    */   
/*    */   @NotNull
/*    */   protected abstract Component renderEntityNbt(@NotNull EntityNBTComponent paramEntityNBTComponent, @NotNull C paramC);
/*    */   
/*    */   @NotNull
/*    */   protected abstract Component renderStorageNbt(@NotNull StorageNBTComponent paramStorageNBTComponent, @NotNull C paramC);
/*    */   
/*    */   @NotNull
/*    */   protected abstract Component renderKeybind(@NotNull KeybindComponent paramKeybindComponent, @NotNull C paramC);
/*    */   
/*    */   @NotNull
/*    */   protected abstract Component renderScore(@NotNull ScoreComponent paramScoreComponent, @NotNull C paramC);
/*    */   
/*    */   @NotNull
/*    */   protected abstract Component renderSelector(@NotNull SelectorComponent paramSelectorComponent, @NotNull C paramC);
/*    */   
/*    */   @NotNull
/*    */   protected abstract Component renderText(@NotNull TextComponent paramTextComponent, @NotNull C paramC);
/*    */   
/*    */   @NotNull
/*    */   protected abstract Component renderTranslatable(@NotNull TranslatableComponent paramTranslatableComponent, @NotNull C paramC);
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\libs\kyori\adventure\text\renderer\AbstractComponentRenderer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */