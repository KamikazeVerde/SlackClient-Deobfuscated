/*     */ package net.minecraft.item;
/*     */ 
/*     */ import com.google.common.collect.Multimap;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.creativetab.CreativeTabs;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.SharedMonsterAttributes;
/*     */ import net.minecraft.entity.ai.attributes.AttributeModifier;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class ItemSword
/*     */   extends Item
/*     */ {
/*     */   private float attackDamage;
/*     */   private final Item.ToolMaterial material;
/*     */   
/*     */   public ItemSword(Item.ToolMaterial material) {
/*  22 */     this.material = material;
/*  23 */     this.maxStackSize = 1;
/*  24 */     setMaxDamage(material.getMaxUses());
/*  25 */     setCreativeTab(CreativeTabs.tabCombat);
/*  26 */     this.attackDamage = 4.0F + material.getDamageVsEntity();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getDamageVsEntity() {
/*  34 */     return this.material.getDamageVsEntity();
/*     */   }
/*     */ 
/*     */   
/*     */   public float getStrVsBlock(ItemStack stack, Block block) {
/*  39 */     if (block == Blocks.web)
/*     */     {
/*  41 */       return 15.0F;
/*     */     }
/*     */ 
/*     */     
/*  45 */     Material material = block.getMaterial();
/*  46 */     return (material != Material.plants && material != Material.vine && material != Material.coral && material != Material.leaves && material != Material.gourd) ? 1.0F : 1.5F;
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
/*     */   
/*     */   public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker) {
/*  59 */     stack.damageItem(1, attacker);
/*  60 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onBlockDestroyed(ItemStack stack, World worldIn, Block blockIn, BlockPos pos, EntityLivingBase playerIn) {
/*  68 */     if (blockIn.getBlockHardness(worldIn, pos) != 0.0D)
/*     */     {
/*  70 */       stack.damageItem(2, playerIn);
/*     */     }
/*     */     
/*  73 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isFull3D() {
/*  81 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public EnumAction getItemUseAction(ItemStack stack) {
/*  89 */     return EnumAction.BLOCK;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMaxItemUseDuration(ItemStack stack) {
/*  97 */     return 72000;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn) {
/* 105 */     playerIn.setItemInUse(itemStackIn, getMaxItemUseDuration(itemStackIn));
/* 106 */     return itemStackIn;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canHarvestBlock(Block blockIn) {
/* 114 */     return (blockIn == Blocks.web);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getItemEnchantability() {
/* 122 */     return this.material.getEnchantability();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getToolMaterialName() {
/* 130 */     return this.material.toString();
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
/* 141 */     return (this.material.getRepairItem() == repair.getItem()) ? true : super.getIsRepairable(toRepair, repair);
/*     */   }
/*     */ 
/*     */   
/*     */   public Multimap<String, AttributeModifier> getItemAttributeModifiers() {
/* 146 */     Multimap<String, AttributeModifier> multimap = super.getItemAttributeModifiers();
/* 147 */     multimap.put(SharedMonsterAttributes.attackDamage.getAttributeUnlocalizedName(), new AttributeModifier(itemModifierUUID, "Weapon modifier", this.attackDamage, 0));
/* 148 */     return multimap;
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\minecraft\item\ItemSword.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */