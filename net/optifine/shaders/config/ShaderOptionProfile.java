/*     */ package net.optifine.shaders.config;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import net.optifine.Lang;
/*     */ import net.optifine.shaders.ShaderUtils;
/*     */ import net.optifine.shaders.Shaders;
/*     */ 
/*     */ public class ShaderOptionProfile
/*     */   extends ShaderOption {
/*  11 */   private ShaderProfile[] profiles = null;
/*  12 */   private ShaderOption[] options = null;
/*     */   
/*     */   private static final String NAME_PROFILE = "<profile>";
/*     */   private static final String VALUE_CUSTOM = "<custom>";
/*     */   
/*     */   public ShaderOptionProfile(ShaderProfile[] profiles, ShaderOption[] options) {
/*  18 */     super("<profile>", "", detectProfileName(profiles, options), getProfileNames(profiles), detectProfileName(profiles, options, true), null);
/*  19 */     this.profiles = profiles;
/*  20 */     this.options = options;
/*     */   }
/*     */ 
/*     */   
/*     */   public void nextValue() {
/*  25 */     super.nextValue();
/*     */     
/*  27 */     if (getValue().equals("<custom>"))
/*     */     {
/*  29 */       super.nextValue();
/*     */     }
/*     */     
/*  32 */     applyProfileOptions();
/*     */   }
/*     */ 
/*     */   
/*     */   public void updateProfile() {
/*  37 */     ShaderProfile shaderprofile = getProfile(getValue());
/*     */     
/*  39 */     if (shaderprofile == null || !ShaderUtils.matchProfile(shaderprofile, this.options, false)) {
/*     */       
/*  41 */       String s = detectProfileName(this.profiles, this.options);
/*  42 */       setValue(s);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void applyProfileOptions() {
/*  48 */     ShaderProfile shaderprofile = getProfile(getValue());
/*     */     
/*  50 */     if (shaderprofile != null) {
/*     */       
/*  52 */       String[] astring = shaderprofile.getOptions();
/*     */       
/*  54 */       for (int i = 0; i < astring.length; i++) {
/*     */         
/*  56 */         String s = astring[i];
/*  57 */         ShaderOption shaderoption = getOption(s);
/*     */         
/*  59 */         if (shaderoption != null) {
/*     */           
/*  61 */           String s1 = shaderprofile.getValue(s);
/*  62 */           shaderoption.setValue(s1);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private ShaderOption getOption(String name) {
/*  70 */     for (int i = 0; i < this.options.length; i++) {
/*     */       
/*  72 */       ShaderOption shaderoption = this.options[i];
/*     */       
/*  74 */       if (shaderoption.getName().equals(name))
/*     */       {
/*  76 */         return shaderoption;
/*     */       }
/*     */     } 
/*     */     
/*  80 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   private ShaderProfile getProfile(String name) {
/*  85 */     for (int i = 0; i < this.profiles.length; i++) {
/*     */       
/*  87 */       ShaderProfile shaderprofile = this.profiles[i];
/*     */       
/*  89 */       if (shaderprofile.getName().equals(name))
/*     */       {
/*  91 */         return shaderprofile;
/*     */       }
/*     */     } 
/*     */     
/*  95 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getNameText() {
/* 100 */     return Lang.get("of.shaders.profile");
/*     */   }
/*     */ 
/*     */   
/*     */   public String getValueText(String val) {
/* 105 */     return val.equals("<custom>") ? Lang.get("of.general.custom", "<custom>") : Shaders.translate("profile." + val, val);
/*     */   }
/*     */ 
/*     */   
/*     */   public String getValueColor(String val) {
/* 110 */     return val.equals("<custom>") ? "§c" : "§a";
/*     */   }
/*     */ 
/*     */   
/*     */   public String getDescriptionText() {
/* 115 */     String s = Shaders.translate("profile.comment", null);
/*     */     
/* 117 */     if (s != null)
/*     */     {
/* 119 */       return s;
/*     */     }
/*     */ 
/*     */     
/* 123 */     StringBuffer stringbuffer = new StringBuffer();
/*     */     
/* 125 */     for (int i = 0; i < this.profiles.length; i++) {
/*     */       
/* 127 */       String s1 = this.profiles[i].getName();
/*     */       
/* 129 */       if (s1 != null) {
/*     */         
/* 131 */         String s2 = Shaders.translate("profile." + s1 + ".comment", null);
/*     */         
/* 133 */         if (s2 != null) {
/*     */           
/* 135 */           stringbuffer.append(s2);
/*     */           
/* 137 */           if (!s2.endsWith(". "))
/*     */           {
/* 139 */             stringbuffer.append(". ");
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 145 */     return stringbuffer.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static String detectProfileName(ShaderProfile[] profs, ShaderOption[] opts) {
/* 151 */     return detectProfileName(profs, opts, false);
/*     */   }
/*     */ 
/*     */   
/*     */   private static String detectProfileName(ShaderProfile[] profs, ShaderOption[] opts, boolean def) {
/* 156 */     ShaderProfile shaderprofile = ShaderUtils.detectProfile(profs, opts, def);
/* 157 */     return (shaderprofile == null) ? "<custom>" : shaderprofile.getName();
/*     */   }
/*     */ 
/*     */   
/*     */   private static String[] getProfileNames(ShaderProfile[] profs) {
/* 162 */     List<String> list = new ArrayList<>();
/*     */     
/* 164 */     for (int i = 0; i < profs.length; i++) {
/*     */       
/* 166 */       ShaderProfile shaderprofile = profs[i];
/* 167 */       list.add(shaderprofile.getName());
/*     */     } 
/*     */     
/* 170 */     list.add("<custom>");
/* 171 */     String[] astring = list.<String>toArray(new String[list.size()]);
/* 172 */     return astring;
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\optifine\shaders\config\ShaderOptionProfile.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */