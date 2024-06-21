/*     */ package net.minecraft.client.gui;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.net.URI;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import net.minecraft.client.resources.I18n;
/*     */ import net.minecraft.client.resources.ResourcePackListEntry;
/*     */ import net.minecraft.client.resources.ResourcePackListEntryDefault;
/*     */ import net.minecraft.client.resources.ResourcePackListEntryFound;
/*     */ import net.minecraft.client.resources.ResourcePackRepository;
/*     */ import net.minecraft.util.Util;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ import org.lwjgl.Sys;
/*     */ 
/*     */ public class GuiScreenResourcePacks
/*     */   extends GuiScreen {
/*  21 */   private static final Logger logger = LogManager.getLogger();
/*     */   
/*     */   private final GuiScreen parentScreen;
/*     */   
/*     */   private List<ResourcePackListEntry> availableResourcePacks;
/*     */   
/*     */   private List<ResourcePackListEntry> selectedResourcePacks;
/*     */   
/*     */   private GuiResourcePackAvailable availableResourcePacksList;
/*     */   
/*     */   private GuiResourcePackSelected selectedResourcePacksList;
/*     */   private boolean changed = false;
/*     */   
/*     */   public GuiScreenResourcePacks(GuiScreen parentScreenIn) {
/*  35 */     this.parentScreen = parentScreenIn;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void initGui() {
/*  44 */     this.buttonList.add(new GuiOptionButton(2, this.width / 2 - 154, this.height - 48, I18n.format("resourcePack.openFolder", new Object[0])));
/*  45 */     this.buttonList.add(new GuiOptionButton(1, this.width / 2 + 4, this.height - 48, I18n.format("gui.done", new Object[0])));
/*     */     
/*  47 */     if (!this.changed) {
/*     */       
/*  49 */       this.availableResourcePacks = Lists.newArrayList();
/*  50 */       this.selectedResourcePacks = Lists.newArrayList();
/*  51 */       ResourcePackRepository resourcepackrepository = this.mc.getResourcePackRepository();
/*  52 */       resourcepackrepository.updateRepositoryEntriesAll();
/*  53 */       List<ResourcePackRepository.Entry> list = Lists.newArrayList(resourcepackrepository.getRepositoryEntriesAll());
/*  54 */       list.removeAll(resourcepackrepository.getRepositoryEntries());
/*     */       
/*  56 */       for (ResourcePackRepository.Entry resourcepackrepository$entry : list)
/*     */       {
/*  58 */         this.availableResourcePacks.add(new ResourcePackListEntryFound(this, resourcepackrepository$entry));
/*     */       }
/*     */       
/*  61 */       for (ResourcePackRepository.Entry resourcepackrepository$entry1 : Lists.reverse(resourcepackrepository.getRepositoryEntries()))
/*     */       {
/*  63 */         this.selectedResourcePacks.add(new ResourcePackListEntryFound(this, resourcepackrepository$entry1));
/*     */       }
/*     */       
/*  66 */       this.selectedResourcePacks.add(new ResourcePackListEntryDefault(this));
/*     */     } 
/*     */     
/*  69 */     this.availableResourcePacksList = new GuiResourcePackAvailable(this.mc, 200, this.height, this.availableResourcePacks);
/*  70 */     this.availableResourcePacksList.setSlotXBoundsFromLeft(this.width / 2 - 4 - 200);
/*  71 */     this.availableResourcePacksList.registerScrollButtons(7, 8);
/*  72 */     this.selectedResourcePacksList = new GuiResourcePackSelected(this.mc, 200, this.height, this.selectedResourcePacks);
/*  73 */     this.selectedResourcePacksList.setSlotXBoundsFromLeft(this.width / 2 + 4);
/*  74 */     this.selectedResourcePacksList.registerScrollButtons(7, 8);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void handleMouseInput() throws IOException {
/*  82 */     super.handleMouseInput();
/*  83 */     this.selectedResourcePacksList.handleMouseInput();
/*  84 */     this.availableResourcePacksList.handleMouseInput();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasResourcePackEntry(ResourcePackListEntry p_146961_1_) {
/*  89 */     return this.selectedResourcePacks.contains(p_146961_1_);
/*     */   }
/*     */ 
/*     */   
/*     */   public List<ResourcePackListEntry> getListContaining(ResourcePackListEntry p_146962_1_) {
/*  94 */     return hasResourcePackEntry(p_146962_1_) ? this.selectedResourcePacks : this.availableResourcePacks;
/*     */   }
/*     */ 
/*     */   
/*     */   public List<ResourcePackListEntry> getAvailableResourcePacks() {
/*  99 */     return this.availableResourcePacks;
/*     */   }
/*     */ 
/*     */   
/*     */   public List<ResourcePackListEntry> getSelectedResourcePacks() {
/* 104 */     return this.selectedResourcePacks;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void actionPerformed(GuiButton button) throws IOException {
/* 112 */     if (button.enabled)
/*     */     {
/* 114 */       if (button.id == 2) {
/*     */         
/* 116 */         File file1 = this.mc.getResourcePackRepository().getDirResourcepacks();
/* 117 */         String s = file1.getAbsolutePath();
/*     */         
/* 119 */         if (Util.getOSType() == Util.EnumOS.OSX) {
/*     */ 
/*     */           
/*     */           try {
/* 123 */             logger.info(s);
/* 124 */             Runtime.getRuntime().exec(new String[] { "/usr/bin/open", s });
/*     */             
/*     */             return;
/* 127 */           } catch (IOException ioexception1) {
/*     */             
/* 129 */             logger.error("Couldn't open file", ioexception1);
/*     */           }
/*     */         
/* 132 */         } else if (Util.getOSType() == Util.EnumOS.WINDOWS) {
/*     */           
/* 134 */           String s1 = String.format("cmd.exe /C start \"Open file\" \"%s\"", new Object[] { s });
/*     */ 
/*     */           
/*     */           try {
/* 138 */             Runtime.getRuntime().exec(s1);
/*     */             
/*     */             return;
/* 141 */           } catch (IOException ioexception) {
/*     */             
/* 143 */             logger.error("Couldn't open file", ioexception);
/*     */           } 
/*     */         } 
/*     */         
/* 147 */         boolean flag = false;
/*     */ 
/*     */         
/*     */         try {
/* 151 */           Class<?> oclass = Class.forName("java.awt.Desktop");
/* 152 */           Object object = oclass.getMethod("getDesktop", new Class[0]).invoke(null, new Object[0]);
/* 153 */           oclass.getMethod("browse", new Class[] { URI.class }).invoke(object, new Object[] { file1.toURI() });
/*     */         }
/* 155 */         catch (Throwable throwable) {
/*     */           
/* 157 */           logger.error("Couldn't open link", throwable);
/* 158 */           flag = true;
/*     */         } 
/*     */         
/* 161 */         if (flag)
/*     */         {
/* 163 */           logger.info("Opening via system class!");
/* 164 */           Sys.openURL("file://" + s);
/*     */         }
/*     */       
/* 167 */       } else if (button.id == 1) {
/*     */         
/* 169 */         if (this.changed) {
/*     */           
/* 171 */           List<ResourcePackRepository.Entry> list = Lists.newArrayList();
/*     */           
/* 173 */           for (ResourcePackListEntry resourcepacklistentry : this.selectedResourcePacks) {
/*     */             
/* 175 */             if (resourcepacklistentry instanceof ResourcePackListEntryFound)
/*     */             {
/* 177 */               list.add(((ResourcePackListEntryFound)resourcepacklistentry).func_148318_i());
/*     */             }
/*     */           } 
/*     */           
/* 181 */           Collections.reverse(list);
/* 182 */           this.mc.getResourcePackRepository().setRepositories(list);
/* 183 */           this.mc.gameSettings.resourcePacks.clear();
/* 184 */           this.mc.gameSettings.field_183018_l.clear();
/*     */           
/* 186 */           for (ResourcePackRepository.Entry resourcepackrepository$entry : list) {
/*     */             
/* 188 */             this.mc.gameSettings.resourcePacks.add(resourcepackrepository$entry.getResourcePackName());
/*     */             
/* 190 */             if (resourcepackrepository$entry.func_183027_f() != 1)
/*     */             {
/* 192 */               this.mc.gameSettings.field_183018_l.add(resourcepackrepository$entry.getResourcePackName());
/*     */             }
/*     */           } 
/*     */           
/* 196 */           this.mc.gameSettings.saveOptions();
/* 197 */           this.mc.refreshResources();
/*     */         } 
/*     */         
/* 200 */         this.mc.displayGuiScreen(this.parentScreen);
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
/* 210 */     super.mouseClicked(mouseX, mouseY, mouseButton);
/* 211 */     this.availableResourcePacksList.mouseClicked(mouseX, mouseY, mouseButton);
/* 212 */     this.selectedResourcePacksList.mouseClicked(mouseX, mouseY, mouseButton);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void mouseReleased(int mouseX, int mouseY, int state) {
/* 220 */     super.mouseReleased(mouseX, mouseY, state);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawScreen(int mouseX, int mouseY, float partialTicks) {
/* 228 */     drawBackground(0);
/* 229 */     this.availableResourcePacksList.drawScreen(mouseX, mouseY, partialTicks);
/* 230 */     this.selectedResourcePacksList.drawScreen(mouseX, mouseY, partialTicks);
/* 231 */     drawCenteredString(this.fontRendererObj, I18n.format("resourcePack.title", new Object[0]), this.width / 2, 16, 16777215);
/* 232 */     drawCenteredString(this.fontRendererObj, I18n.format("resourcePack.folderInfo", new Object[0]), this.width / 2 - 77, this.height - 26, 8421504);
/* 233 */     super.drawScreen(mouseX, mouseY, partialTicks);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void markChanged() {
/* 241 */     this.changed = true;
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\minecraft\client\gui\GuiScreenResourcePacks.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */