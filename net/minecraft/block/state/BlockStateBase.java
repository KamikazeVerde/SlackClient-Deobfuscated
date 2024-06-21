/*     */ package net.minecraft.block.state;
/*     */ 
/*     */ import com.google.common.base.Function;
/*     */ import com.google.common.base.Joiner;
/*     */ import com.google.common.collect.ImmutableTable;
/*     */ import com.google.common.collect.Iterables;
/*     */ import java.util.Collection;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ 
/*     */ public abstract class BlockStateBase
/*     */   implements IBlockState {
/*  16 */   private static final Joiner COMMA_JOINER = Joiner.on(',');
/*  17 */   private static final Function<Map.Entry<IProperty, Comparable>, String> MAP_ENTRY_TO_STRING = new Function<Map.Entry<IProperty, Comparable>, String>()
/*     */     {
/*     */       public String apply(Map.Entry<IProperty, Comparable> p_apply_1_)
/*     */       {
/*  21 */         if (p_apply_1_ == null)
/*     */         {
/*  23 */           return "<NULL>";
/*     */         }
/*     */ 
/*     */         
/*  27 */         IProperty iproperty = p_apply_1_.getKey();
/*  28 */         return iproperty.getName() + "=" + iproperty.getName(p_apply_1_.getValue());
/*     */       }
/*     */     };
/*     */   
/*  32 */   private int blockId = -1;
/*  33 */   private int blockStateId = -1;
/*  34 */   private int metadata = -1;
/*  35 */   private ResourceLocation blockLocation = null;
/*     */ 
/*     */   
/*     */   public int getBlockId() {
/*  39 */     if (this.blockId < 0)
/*     */     {
/*  41 */       this.blockId = Block.getIdFromBlock(getBlock());
/*     */     }
/*     */     
/*  44 */     return this.blockId;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getBlockStateId() {
/*  49 */     if (this.blockStateId < 0)
/*     */     {
/*  51 */       this.blockStateId = Block.getStateId(this);
/*     */     }
/*     */     
/*  54 */     return this.blockStateId;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getMetadata() {
/*  59 */     if (this.metadata < 0)
/*     */     {
/*  61 */       this.metadata = getBlock().getMetaFromState(this);
/*     */     }
/*     */     
/*  64 */     return this.metadata;
/*     */   }
/*     */ 
/*     */   
/*     */   public ResourceLocation getBlockLocation() {
/*  69 */     if (this.blockLocation == null)
/*     */     {
/*  71 */       this.blockLocation = (ResourceLocation)Block.blockRegistry.getNameForObject(getBlock());
/*     */     }
/*     */     
/*  74 */     return this.blockLocation;
/*     */   }
/*     */ 
/*     */   
/*     */   public ImmutableTable<IProperty<?>, Comparable<?>, IBlockState> getPropertyValueTable() {
/*  79 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public <T extends Comparable<T>> IBlockState cycleProperty(IProperty<T> property) {
/*  84 */     return withProperty(property, cyclePropertyValue(property.getAllowedValues(), getValue(property)));
/*     */   }
/*     */ 
/*     */   
/*     */   protected static <T> T cyclePropertyValue(Collection<T> values, T currentValue) {
/*  89 */     Iterator<T> iterator = values.iterator();
/*     */     
/*  91 */     while (iterator.hasNext()) {
/*     */       
/*  93 */       if (iterator.next().equals(currentValue)) {
/*     */         
/*  95 */         if (iterator.hasNext())
/*     */         {
/*  97 */           return iterator.next();
/*     */         }
/*     */         
/* 100 */         return values.iterator().next();
/*     */       } 
/*     */     } 
/*     */     
/* 104 */     return iterator.next();
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 109 */     StringBuilder stringbuilder = new StringBuilder();
/* 110 */     stringbuilder.append(Block.blockRegistry.getNameForObject(getBlock()));
/*     */     
/* 112 */     if (!getProperties().isEmpty()) {
/*     */       
/* 114 */       stringbuilder.append("[");
/* 115 */       COMMA_JOINER.appendTo(stringbuilder, Iterables.transform((Iterable)getProperties().entrySet(), MAP_ENTRY_TO_STRING));
/* 116 */       stringbuilder.append("]");
/*     */     } 
/*     */     
/* 119 */     return stringbuilder.toString();
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\minecraft\block\state\BlockStateBase.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */