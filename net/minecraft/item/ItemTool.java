/*     */ package net.minecraft.item;
/*     */ 
/*     */ import com.google.common.collect.Multimap;
/*     */ import java.util.Set;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.creativetab.CreativeTabs;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.SharedMonsterAttributes;
/*     */ import net.minecraft.entity.ai.attributes.AttributeModifier;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class ItemTool
/*     */   extends Item {
/*     */   private Set<Block> effectiveBlocks;
/*  16 */   protected float efficiencyOnProperMaterial = 4.0F;
/*     */ 
/*     */   
/*     */   private float damageVsEntity;
/*     */ 
/*     */   
/*     */   protected Item.ToolMaterial toolMaterial;
/*     */ 
/*     */   
/*     */   protected ItemTool(float attackDamage, Item.ToolMaterial material, Set<Block> effectiveBlocks) {
/*  26 */     this.toolMaterial = material;
/*  27 */     this.effectiveBlocks = effectiveBlocks;
/*  28 */     this.maxStackSize = 1;
/*  29 */     setMaxDamage(material.getMaxUses());
/*  30 */     this.efficiencyOnProperMaterial = material.getEfficiencyOnProperMaterial();
/*  31 */     this.damageVsEntity = attackDamage + material.getDamageVsEntity();
/*  32 */     setCreativeTab(CreativeTabs.tabTools);
/*     */   }
/*     */ 
/*     */   
/*     */   public float getStrVsBlock(ItemStack stack, Block block) {
/*  37 */     return this.effectiveBlocks.contains(block) ? this.efficiencyOnProperMaterial : 1.0F;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker) {
/*  49 */     stack.damageItem(2, attacker);
/*  50 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onBlockDestroyed(ItemStack stack, World worldIn, Block blockIn, BlockPos pos, EntityLivingBase playerIn) {
/*  58 */     if (blockIn.getBlockHardness(worldIn, pos) != 0.0D)
/*     */     {
/*  60 */       stack.damageItem(1, playerIn);
/*     */     }
/*     */     
/*  63 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isFull3D() {
/*  71 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public Item.ToolMaterial getToolMaterial() {
/*  76 */     return this.toolMaterial;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getItemEnchantability() {
/*  84 */     return this.toolMaterial.getEnchantability();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getToolMaterialName() {
/*  92 */     return this.toolMaterial.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean getIsRepairable(ItemStack toRepair, ItemStack repair) {
/* 103 */     return (this.toolMaterial.getRepairItem() == repair.getItem()) ? true : super.getIsRepairable(toRepair, repair);
/*     */   }
/*     */ 
/*     */   
/*     */   public Multimap<String, AttributeModifier> getItemAttributeModifiers() {
/* 108 */     Multimap<String, AttributeModifier> multimap = super.getItemAttributeModifiers();
/* 109 */     multimap.put(SharedMonsterAttributes.attackDamage.getAttributeUnlocalizedName(), new AttributeModifier(itemModifierUUID, "Tool modifier", this.damageVsEntity, 0));
/* 110 */     return multimap;
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\minecraft\item\ItemTool.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */