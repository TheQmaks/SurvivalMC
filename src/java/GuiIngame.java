package net.minecraft.src;

import java.awt.Color;
import java.util.List;
import java.util.Random;
import net.minecraft.client.Minecraft;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

public class GuiIngame extends Gui
{
    private int tickChum;
    private static final RenderItem itemRenderer = new RenderItem();
    private final Random rand = new Random();
    private final Minecraft mc;

    /** ChatGUI instance that retains all previous chat data */
    private final GuiNewChat persistantChatGUI;
    private int updateCounter = 0;

    /** The string specifying which record music is playing */
    private String recordPlaying = "";

    /** How many ticks the record playing message will be displayed */
    private int recordPlayingUpFor = 0;
    private boolean recordIsPlaying = false;

    /** Previous frame vignette brightness (slowly changes by 1% each frame) */
    public float prevVignetteBrightness = 1.0F;

    public GuiIngame(Minecraft par1Minecraft)
    {
        this.mc = par1Minecraft;
        this.persistantChatGUI = new GuiNewChat(par1Minecraft);
    }

    /**
     * Render the ingame overlay with quick icon bar, ...
     */
    public void renderGameOverlay(float par1, boolean par2, int par3, int par4)
    {
        ScaledResolution var5 = new ScaledResolution(this.mc.gameSettings, this.mc.displayWidth, this.mc.displayHeight);
        int var6 = var5.getScaledWidth();
        int var7 = var5.getScaledHeight();
        FontRenderer var8 = this.mc.fontRenderer;
        this.mc.entityRenderer.setupOverlayRendering();
        GL11.glEnable(GL11.GL_BLEND);

        if (Minecraft.isFancyGraphicsEnabled())
        {
            this.renderVignette(this.mc.thePlayer.getBrightness(par1), var6, var7);
        }
        else
        {
            GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        }

        ItemStack var9 = this.mc.thePlayer.inventory.armorItemInSlot(3);

        if (this.mc.gameSettings.thirdPersonView == 0 && var9 != null && var9.itemID == Block.pumpkin.blockID)
        {
            this.renderPumpkinBlur(var6, var7);
        }
        else if (this.mc.gameSettings.thirdPersonView == 0 && var9 != null && (var9.itemID == Item.helmetProtective.shiftedIndex || var9.itemID == Item.helmetAqualung.shiftedIndex || var9.itemID == Item.helmetOtAgressivnykhSred.shiftedIndex))
        {
            this.renderProtectiveBlur(var6, var7);
        }
        else if (this.mc.gameSettings.thirdPersonView == 0 && var9 != null && var9.itemID == Item.helmetPNV.shiftedIndex)
        {
            if (this.tickChum < 10)
            {
                this.renderChum(var6, var7, 0, 0.1F);
                ++this.tickChum;
            }
            else
            {
                this.renderChum(var6, var7, 1, 0.1F);
                ++this.tickChum;

                if (this.tickChum > 20)
                {
                    this.tickChum = 0;
                }
            }

            this.renderPNVBlur(var6, var7);
        }

        int var10 = this.mc.thePlayer.zarazaTick;

        if (var10 > 200 && !this.mc.thePlayer.capabilities.isCreativeMode)
        {
            if (var10 > 800)
            {
                var10 = 800;
            }

            float var11 = 2.0E-4F * (float)(var10 - 200);

            if (this.tickChum < 10)
            {
                this.renderChum(var6, var7, 0, var11);
                ++this.tickChum;
            }
            else
            {
                this.renderChum(var6, var7, 1, var11);
                ++this.tickChum;

                if (this.tickChum > 20)
                {
                    this.tickChum = 0;
                }
            }
        }

        ItemStack var36 = this.mc.thePlayer.inventory.getCurrentItem();
        int var13;
        int var14;
        int var15;
        int var16;

        if (var36 != null)
        {
            int var12 = var36.stat1;
            var13 = var36.getItem().shiftedIndex;

            if (randCh.oruzhiye(var13, 0))
            {
                var14 = var36.getItem().getMaxOboyma();
                var15 = var36.getItem().getMaxOboyma();
                var16 = (var36.getItem().getMaxDamage() - var36.getItemDamage()) % 1000;
                String var17;

                if (this.mc.thePlayer.zaderzhka > 0)
                {
                    var17 = "-------";
                    var8.drawString(var17, var6 - var8.getStringWidth(var17) - 32, var7 - 20, 16777215);
                }
                else if (var36.stat1 != 1 && !this.mc.thePlayer.zakliniloRasporka)
                {
                    if (this.mc.thePlayer.perezaryadka == 1)
                    {
                        var17 = "\u041f\u0435\u0440\u0435\u0437\u0430\u0440\u044f\u0434\u043a\u0430...";
                        var8.drawString(var17, var6 - var8.getStringWidth(var17) - 32, var7 - 20, 15110929);
                    }
                    else if (this.mc.thePlayer.perezaryadka == 2)
                    {
                        var17 = "\u0420\u0430\u0437\u0440\u044f\u0434\u043a\u0430...";
                        var8.drawString(var17, var6 - var8.getStringWidth(var17) - 32, var7 - 20, 15110929);
                    }
                    else if (this.mc.thePlayer.perezaryadka == 3)
                    {
                        var17 = "\u0420\u0430\u0441\u043a\u043b\u0438\u043d\u0438\u0432\u0430\u043d\u0438\u0435...";
                        var8.drawString(var17, var6 - var8.getStringWidth(var17) - 32, var7 - 20, 16720896);
                    }
                    else if (var16 <= var15)
                    {
                        var17 = var14 + " / " + var16;

                        if (var16 == 0)
                        {
                            var8.drawString(var17, var6 - var8.getStringWidth(var17) - 32, var7 - 20, 16720896);
                        }
                        else
                        {
                            var8.drawString(var17, var6 - var8.getStringWidth(var17) - 32, var7 - 20, 16777215);
                        }
                    }
                    else
                    {
                        var17 = var14 + " / " + "?";
                        var8.drawString(var17, var6 - var8.getStringWidth(var17) - 32, var7 - 20, 15110929);
                    }
                }
                else
                {
                    if (var16 <= var15)
                    {
                        var17 = "\u0417\u0430\u043a\u043b\u0438\u043d\u0438\u043b\u043e  " + var14 + " / " + var16;
                    }
                    else
                    {
                        var17 = "\u0417\u0430\u043a\u043b\u0438\u043d\u0438\u043b\u043e  " + var14 + " / " + "?";
                    }

                    var8.drawString(var17, var6 - var8.getStringWidth(var17) - 32, var7 - 20, 16720896);
                }
            }
            else if (var13 == Item.bow.shiftedIndex)
            {
                String var39;

                if (var12 == 0)
                {
                    var39 = "\u041e\u0431\u044b\u0447\u043d\u044b\u0435 \u0441\u0442\u0440\u0435\u043b\u044b";
                    var8.drawString(var39, var6 - var8.getStringWidth(var39) - 32, var7 - 20, 16777215);
                }
                else if (var12 == 1)
                {
                    var39 = "\u0416\u0435\u043b\u0435\u0437\u043d\u044b\u0435 \u0441\u0442\u0440\u0435\u043b\u044b";
                    var8.drawString(var39, var6 - var8.getStringWidth(var39) - 32, var7 - 20, 12632256);
                }
                else if (var12 == 2)
                {
                    var39 = "\u041e\u0442\u0440\u0430\u0432\u043b\u0435\u043d\u043d\u044b\u0435 \u0441\u0442\u0440\u0435\u043b\u044b";
                    var8.drawString(var39, var6 - var8.getStringWidth(var39) - 32, var7 - 20, 6136112);
                }
                else if (var12 == 3)
                {
                    var39 = "\u0417\u0430\u043c\u0435\u0434\u043b\u044f\u044e\u0449\u0438\u0435 \u0441\u0442\u0440\u0435\u043b\u044b";
                    var8.drawString(var39, var6 - var8.getStringWidth(var39) - 32, var7 - 20, 5263440);
                }
                else if (var12 == 4)
                {
                    var39 = "\u041e\u0441\u043b\u0430\u0431\u043b\u044f\u044e\u0449\u0438\u0435 \u0441\u0442\u0440\u0435\u043b\u044b";
                    var8.drawString(var39, var6 - var8.getStringWidth(var39) - 32, var7 - 20, 10058381);
                }
                else if (var12 == 5)
                {
                    var39 = "\u0421\u0442\u0440\u0435\u043b\u044b \u0433\u043e\u043b\u043e\u0432\u043e\u043a\u0440\u0443\u0436\u0435\u043d\u0438\u044f";
                    var8.drawString(var39, var6 - var8.getStringWidth(var39) - 32, var7 - 20, 11688352);
                }
                else if (var12 == 6)
                {
                    var39 = "\u0421\u0442\u0440\u0435\u043b\u044b \u0433\u043e\u043b\u043e\u0434\u0430";
                    var8.drawString(var39, var6 - var8.getStringWidth(var39) - 32, var7 - 20, 11181129);
                }
                else if (var12 == 7)
                {
                    var39 = "\u041e\u0441\u043b\u0435\u043f\u043b\u044f\u044e\u0449\u0438\u0435 \u0441\u0442\u0440\u0435\u043b\u044b";
                    var8.drawString(var39, var6 - var8.getStringWidth(var39) - 32, var7 - 20, 2621525);
                }
                else if (var12 == 8)
                {
                    var39 = "\u0421\u0442\u0440\u0435\u043b\u044b \u0438\u0441\u0441\u0443\u0448\u0435\u043d\u0438\u044f";
                    var8.drawString(var39, var6 - var8.getStringWidth(var39) - 32, var7 - 20, 11806755);
                }
                else if (var12 == 9)
                {
                    var39 = "\u0421\u0442\u0440\u0435\u043b\u044b \u0430\u043d\u0442\u0438-\u0437\u043e\u043c\u0431\u0438";
                    var8.drawString(var39, var6 - var8.getStringWidth(var39) - 32, var7 - 20, 5933485);
                }
                else if (var12 == 10)
                {
                    var39 = "\u0417\u0430\u0440\u0430\u0436\u0435\u043d\u043d\u044b\u0435 \u0441\u0442\u0440\u0435\u043b\u044b";
                    var8.drawString(var39, var6 - var8.getStringWidth(var39) - 32, var7 - 20, 11542784);
                }
                else if (var12 == 11)
                {
                    var39 = "\u041a\u043e\u0440\u0440\u043e\u0437\u0438\u043e\u043d\u043d\u044b\u0435 \u0441\u0442\u0440\u0435\u043b\u044b";
                    var8.drawString(var39, var6 - var8.getStringWidth(var39) - 32, var7 - 20, 65280);
                }
            }
        }

        if (!this.mc.thePlayer.isPotionActive(Potion.confusion))
        {
            float var37 = this.mc.thePlayer.prevTimeInPortal + (this.mc.thePlayer.timeInPortal - this.mc.thePlayer.prevTimeInPortal) * par1;

            if (var37 > 0.0F)
            {
                this.renderPortalOverlay(var37, var6, var7);
            }
        }

        int var18;
        int var19;
        int var20;
        int var21;
        int var22;
        byte var32;
        boolean var38;
        int var40;

        if (!this.mc.playerController.func_78747_a())
        {
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            GL11.glBindTexture(GL11.GL_TEXTURE_2D, this.mc.renderEngine.getTexture("/gui/gui.png"));
            InventoryPlayer var23 = this.mc.thePlayer.inventory;
            this.zLevel = -90.0F;
            this.drawTexturedModalRect(var6 / 2 - 91, var7 - 22, 0, 0, 182, 22);
            this.drawTexturedModalRect(var6 / 2 - 91 - 1 + var23.currentItem * 20, var7 - 22 - 1, 0, 22, 24, 22);
            GL11.glBindTexture(GL11.GL_TEXTURE_2D, this.mc.renderEngine.getTexture("/gui/icons.png"));
            GL11.glEnable(GL11.GL_BLEND);
            GL11.glBlendFunc(GL11.GL_ONE_MINUS_DST_COLOR, GL11.GL_ONE_MINUS_SRC_COLOR);
            this.drawTexturedModalRect(var6 / 2 - 7, var7 / 2 - 7, 0, 0, 16, 16);
            GL11.glDisable(GL11.GL_BLEND);
            var38 = this.mc.thePlayer.hurtResistantTime / 3 % 2 == 1;

            if (this.mc.thePlayer.hurtResistantTime < 10)
            {
                var38 = false;
            }

            var13 = this.mc.thePlayer.getHealth();
            var14 = this.mc.thePlayer.prevHealth;
            this.rand.setSeed((long)(this.updateCounter * 312871));
            boolean var24 = false;
            FoodStats var25 = this.mc.thePlayer.getFoodStats();
            var16 = var25.getFoodLevel();
            var15 = var25.getPrevFoodLevel();
            this.mc.mcProfiler.startSection("bossHealth");
            this.renderBossHealth();
            this.mc.mcProfiler.endSection();
            int var26;

            if (this.mc.playerController.shouldDrawHUD())
            {
                var26 = var6 / 2 - 91;
                var40 = var6 / 2 + 91;
                this.mc.mcProfiler.startSection("expBar");
                var18 = this.mc.thePlayer.xpBarCap();

                if (var18 > 0)
                {
                    short var27 = 182;
                    var20 = (int)(this.mc.thePlayer.experience * (float)(var27 + 1));
                    var19 = var7 - 32 + 3;
                    this.drawTexturedModalRect(var26, var19, 0, 64, var27, 5);

                    if (var20 > 0)
                    {
                        this.drawTexturedModalRect(var26, var19, 0, 69, var20, 5);
                    }
                }

                var22 = var7 - 39;
                var20 = var22 - 10;
                var19 = this.mc.thePlayer.getTotalArmorValue();
                var21 = -1;

                if (this.mc.thePlayer.isPotionActive(Potion.regeneration))
                {
                    var21 = this.updateCounter % 25;
                }

                this.mc.mcProfiler.endStartSection("healthArmor");
                int var28;
                int var29;
                int var30;
                int var45;

                for (var45 = 0; var45 < 10; ++var45)
                {
                    if (var19 > 0)
                    {
                        var28 = var26 + var45 * 8;

                        if (var45 * 2 + 1 < var19)
                        {
                            this.drawTexturedModalRect(var28, var20, 34, 9, 9, 9);
                        }

                        if (var45 * 2 + 1 == var19)
                        {
                            this.drawTexturedModalRect(var28, var20, 25, 9, 9, 9);
                        }

                        if (var45 * 2 + 1 > var19)
                        {
                            this.drawTexturedModalRect(var28, var20, 16, 9, 9, 9);
                        }
                    }

                    var28 = 16;

                    if (this.mc.thePlayer.isPotionActive(Potion.poison))
                    {
                        var28 += 36;
                    }
                    else if (this.mc.thePlayer.isPotionActive(Potion.wither))
                    {
                        var28 += 72;
                    }

                    byte var31 = 0;

                    if (var38)
                    {
                        var31 = 1;
                    }

                    var30 = var26 + var45 * 8;
                    var29 = var22;

                    if (var13 <= 4)
                    {
                        var29 = var22 + this.rand.nextInt(2);
                    }

                    if (var45 == var21)
                    {
                        var29 -= 2;
                    }

                    var32 = 0;

                    if (this.mc.theWorld.getWorldInfo().isHardcoreModeEnabled())
                    {
                        var32 = 5;
                    }

                    this.drawTexturedModalRect(var30, var29, 16 + var31 * 9, 9 * var32, 9, 9);

                    if (var38)
                    {
                        if (var45 * 2 + 1 < var14)
                        {
                            this.drawTexturedModalRect(var30, var29, var28 + 54, 9 * var32, 9, 9);
                        }

                        if (var45 * 2 + 1 == var14)
                        {
                            this.drawTexturedModalRect(var30, var29, var28 + 63, 9 * var32, 9, 9);
                        }
                    }

                    if (var45 * 2 + 1 < var13)
                    {
                        this.drawTexturedModalRect(var30, var29, var28 + 36, 9 * var32, 9, 9);
                    }

                    if (var45 * 2 + 1 == var13)
                    {
                        this.drawTexturedModalRect(var30, var29, var28 + 45, 9 * var32, 9, 9);
                    }
                }

                this.mc.mcProfiler.endStartSection("food");
                int var53;

                for (var45 = 0; var45 < 10; ++var45)
                {
                    var28 = var22;
                    var53 = 16;
                    var32 = 0;

                    if (this.mc.thePlayer.isPotionActive(Potion.hunger))
                    {
                        var53 += 36;
                        var32 = 13;
                    }

                    if (this.mc.thePlayer.getFoodStats().getSaturationLevel() <= 0.0F && this.updateCounter % (var16 * 3 + 1) == 0)
                    {
                        var28 = var22 + (this.rand.nextInt(3) - 1);
                    }

                    if (var24)
                    {
                        var32 = 1;
                    }

                    var29 = var40 - var45 * 8 - 9;
                    this.drawTexturedModalRect(var29, var28, 16 + var32 * 9, 27, 9, 9);

                    if (var24)
                    {
                        if (var45 * 2 + 1 < var15)
                        {
                            this.drawTexturedModalRect(var29, var28, var53 + 54, 27, 9, 9);
                        }

                        if (var45 * 2 + 1 == var15)
                        {
                            this.drawTexturedModalRect(var29, var28, var53 + 63, 27, 9, 9);
                        }
                    }

                    if (var45 * 2 + 1 < var16)
                    {
                        this.drawTexturedModalRect(var29, var28, var53 + 36, 27, 9, 9);
                    }

                    if (var45 * 2 + 1 == var16)
                    {
                        this.drawTexturedModalRect(var29, var28, var53 + 45, 27, 9, 9);
                    }
                }

                this.mc.mcProfiler.endStartSection("air");

                if (this.mc.thePlayer.isInsideOfMaterial(Material.water))
                {
                    var45 = this.mc.thePlayer.getAir();
                    var28 = MathHelper.ceiling_double_int((double)(var45 - 2) * 10.0D / 300.0D);
                    var53 = MathHelper.ceiling_double_int((double)var45 * 10.0D / 300.0D) - var28;

                    for (var30 = 0; var30 < var28 + var53; ++var30)
                    {
                        if (var30 < var28)
                        {
                            this.drawTexturedModalRect(var40 - var30 * 8 - 9, var20, 16, 18, 9, 9);
                        }
                        else
                        {
                            this.drawTexturedModalRect(var40 - var30 * 8 - 9, var20, 25, 18, 9, 9);
                        }
                    }
                }

                this.mc.mcProfiler.endSection();
            }

            GL11.glDisable(GL11.GL_BLEND);
            this.mc.mcProfiler.startSection("actionBar");
            GL11.glEnable(GL12.GL_RESCALE_NORMAL);
            RenderHelper.enableGUIStandardItemLighting();

            for (var26 = 0; var26 < 9; ++var26)
            {
                var40 = var6 / 2 - 90 + var26 * 20 + 2;
                var18 = var7 - 16 - 3;
                this.renderInventorySlot(var26, var40, var18, par1);
            }

            RenderHelper.disableStandardItemLighting();
            GL11.glDisable(GL12.GL_RESCALE_NORMAL);
            this.mc.mcProfiler.endSection();
        }

        float var41;
        int var42;

        if (this.mc.thePlayer.getSleepTimer() > 0)
        {
            this.mc.mcProfiler.startSection("sleep");
            GL11.glDisable(GL11.GL_DEPTH_TEST);
            GL11.glDisable(GL11.GL_ALPHA_TEST);
            var42 = this.mc.thePlayer.getSleepTimer();
            var41 = (float)var42 / 100.0F;

            if (var41 > 1.0F)
            {
                var41 = 1.0F - (float)(var42 - 100) / 10.0F;
            }

            var13 = (int)(220.0F * var41) << 24 | 1052704;
            drawRect(0, 0, var6, var7, var13);
            GL11.glEnable(GL11.GL_ALPHA_TEST);
            GL11.glEnable(GL11.GL_DEPTH_TEST);
            this.mc.mcProfiler.endSection();
        }

        int var43;
        String var44;

        if (this.mc.playerController.func_78763_f() && this.mc.thePlayer.experienceLevel != 0)
        {
            this.mc.mcProfiler.startSection("expLevel");
            var38 = false;
            var13 = var38 ? 16777215 : 8453920;

            if (this.mc.thePlayer.experienceLevel < 0)
            {
                var13 = 16711680;
            }

            var44 = "" + this.mc.thePlayer.experienceLevel;
            var43 = (var6 - var8.getStringWidth(var44)) / 2;
            var42 = var7 - 31 - 4;
            var8.drawString(var44, var43 + 1, var42, 0);
            var8.drawString(var44, var43 - 1, var42, 0);
            var8.drawString(var44, var43, var42 + 1, 0);
            var8.drawString(var44, var43, var42 - 1, 0);
            var8.drawString(var44, var43, var42, var13);
            this.mc.mcProfiler.endSection();
        }

        if (this.mc.isDemo())
        {
            this.mc.mcProfiler.startSection("demo");
            var44 = "";

            if (this.mc.theWorld.getTotalWorldTime() >= 120500L)
            {
                var44 = StatCollector.translateToLocal("demo.demoExpired");
            }
            else
            {
                var44 = String.format(StatCollector.translateToLocal("demo.remainingTime"), new Object[] {StringUtils.ticksToElapsedTime((int)(120500L - this.mc.theWorld.getTotalWorldTime()))});
            }

            var13 = var8.getStringWidth(var44);
            var8.drawStringWithShadow(var44, var6 - var13 - 10, 5, 16777215);
            this.mc.mcProfiler.endSection();
        }

        if (this.mc.gameSettings.showDebugInfo)
        {
            this.mc.mcProfiler.startSection("debug");
            GL11.glPushMatrix();
            var8.drawStringWithShadow("Minecraft 1.4.5 (" + this.mc.debug + ")", 2, 2, 16777215);
            var8.drawStringWithShadow(this.mc.debugInfoRenders(), 2, 12, 16777215);
            var8.drawStringWithShadow(this.mc.getEntityDebug(), 2, 22, 16777215);
            var8.drawStringWithShadow(this.mc.debugInfoEntities(), 2, 32, 16777215);
            var8.drawStringWithShadow(this.mc.getWorldProviderName(), 2, 42, 16777215);
            long var46 = Runtime.getRuntime().maxMemory();
            long var52 = Runtime.getRuntime().totalMemory();
            long var49 = Runtime.getRuntime().freeMemory();
            long var55 = var52 - var49;
            String var34 = "Used memory: " + var55 * 100L / var46 + "% (" + var55 / 1024L / 1024L + "MB) of " + var46 / 1024L / 1024L + "MB";
            this.drawString(var8, var34, var6 - var8.getStringWidth(var34) - 2, 2, 14737632);
            var34 = "Allocated memory: " + var52 * 100L / var46 + "% (" + var52 / 1024L / 1024L + "MB)";
            this.drawString(var8, var34, var6 - var8.getStringWidth(var34) - 2, 12, 14737632);
            var22 = MathHelper.floor_double(this.mc.thePlayer.posX);
            var20 = MathHelper.floor_double(this.mc.thePlayer.posY);
            var19 = MathHelper.floor_double(this.mc.thePlayer.posZ);
            this.drawString(var8, String.format("x: %.5f (%d) // c: %d (%d)", new Object[] {Double.valueOf(this.mc.thePlayer.posX), Integer.valueOf(var22), Integer.valueOf(var22 >> 4), Integer.valueOf(var22 & 15)}), 2, 64, 14737632);
            this.drawString(var8, String.format("y: %.3f (feet pos, %.3f eyes pos)", new Object[] {Double.valueOf(this.mc.thePlayer.boundingBox.minY), Double.valueOf(this.mc.thePlayer.posY)}), 2, 72, 14737632);
            this.drawString(var8, String.format("z: %.5f (%d) // c: %d (%d)", new Object[] {Double.valueOf(this.mc.thePlayer.posZ), Integer.valueOf(var19), Integer.valueOf(var19 >> 4), Integer.valueOf(var19 & 15)}), 2, 80, 14737632);
            var21 = MathHelper.floor_double((double)(this.mc.thePlayer.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
            this.drawString(var8, "f: " + var21 + " (" + Direction.directions[var21] + ") / " + MathHelper.wrapAngleTo180_float(this.mc.thePlayer.rotationYaw), 2, 88, 14737632);

            if (this.mc.theWorld != null && this.mc.theWorld.blockExists(var22, var20, var19))
            {
                Chunk var35 = this.mc.theWorld.getChunkFromBlockCoords(var22, var19);
                this.drawString(var8, "lc: " + (var35.getTopFilledSegment() + 15) + " bl: " + var35.getSavedLightValue(EnumSkyBlock.Block, var22 & 15, var20, var19 & 15) + " sl: " + var35.getSavedLightValue(EnumSkyBlock.Sky, var22 & 15, var20, var19 & 15) + " rl: " + var35.getBlockLightValue(var22 & 15, var20, var19 & 15, 0), 2, 96, 14737632);
            }

            this.drawString(var8, String.format("ws: %.3f, fs: %.3f, g: %b, fl: %d", new Object[] {Float.valueOf(this.mc.thePlayer.capabilities.getWalkSpeed()), Float.valueOf(this.mc.thePlayer.capabilities.getFlySpeed()), Boolean.valueOf(this.mc.thePlayer.onGround), Integer.valueOf(this.mc.theWorld.getHeightValue(var22, var19))}), 2, 104, 14737632);
            GL11.glPopMatrix();
            this.mc.mcProfiler.endSection();
        } else {
            H2Eng.renderCheat(var8, var6, var7);
        }

        if (this.recordPlayingUpFor > 0)
        {
            this.mc.mcProfiler.startSection("overlayMessage");
            var41 = (float)this.recordPlayingUpFor - par1;
            var13 = (int)(var41 * 256.0F / 20.0F);

            if (var13 > 255)
            {
                var13 = 255;
            }

            if (var13 > 0)
            {
                GL11.glPushMatrix();
                GL11.glTranslatef((float)(var6 / 2), (float)(var7 - 48), 0.0F);
                GL11.glEnable(GL11.GL_BLEND);
                GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
                var14 = 16777215;

                if (this.recordIsPlaying)
                {
                    var14 = Color.HSBtoRGB(var41 / 50.0F, 0.7F, 0.6F) & 16777215;
                }

                var8.drawString(this.recordPlaying, -var8.getStringWidth(this.recordPlaying) / 2, -4, var14 + (var13 << 24));
                GL11.glDisable(GL11.GL_BLEND);
                GL11.glPopMatrix();
            }

            this.mc.mcProfiler.endSection();
        }

        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glDisable(GL11.GL_ALPHA_TEST);
        GL11.glPushMatrix();
        GL11.glTranslatef(0.0F, (float)(var7 - 48), 0.0F);
        this.mc.mcProfiler.startSection("chat");
        this.persistantChatGUI.drawChat(this.updateCounter);
        this.mc.mcProfiler.endSection();
        GL11.glPopMatrix();

        if (this.mc.gameSettings.keyBindPlayerList.pressed && (!this.mc.isIntegratedServerRunning() || this.mc.thePlayer.sendQueue.playerInfoList.size() > 1))
        {
            this.mc.mcProfiler.startSection("playerList");
            NetClientHandler var47 = this.mc.thePlayer.sendQueue;
            List var48 = var47.playerInfoList;

            if (this.mc.isFullScreen())
            {
                var14 = 200;
                var43 = var14;

                for (var42 = 1; var43 > 40; var43 = (var14 + var42 - 1) / var42)
                {
                    ++var42;
                }

                var16 = 500 / var42;

                if (var16 > 250)
                {
                    var16 = 250;
                }
            }
            else
            {
                var14 = var47.currentServerMaxPlayers;
                var43 = var14;

                for (var42 = 1; var43 > 20; var43 = (var14 + var42 - 1) / var42)
                {
                    ++var42;
                }

                var16 = 300 / var42;

                if (var16 > 150)
                {
                    var16 = 150;
                }
            }

            var15 = (var6 - var42 * var16) / 2;
            byte var54 = 10;
            drawRect(var15 - 1, var54 - 1, var15 + var16 * var42, var54 + 9 * var43, Integer.MIN_VALUE);

            for (var40 = 0; var40 < var14; ++var40)
            {
                var18 = var15 + var40 % var42 * var16;
                var22 = var54 + var40 / var42 * 9;
                drawRect(var18, var22, var18 + var16 - 1, var22 + 8, 553648127);
                GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
                GL11.glEnable(GL11.GL_ALPHA_TEST);

                if (var40 < var48.size())
                {
                    GuiPlayerInfo var50 = (GuiPlayerInfo)var48.get(var40);
                    var8.drawStringWithShadow(var50.name, var18, var22, 16777215);
                    this.mc.renderEngine.bindTexture(this.mc.renderEngine.getTexture("/gui/icons.png"));
                    byte var51 = 0;
                    boolean var56 = false;

                    if (var50.responseTime < 0)
                    {
                        var32 = 5;
                    }
                    else if (var50.responseTime < 150)
                    {
                        var32 = 0;
                    }
                    else if (var50.responseTime < 300)
                    {
                        var32 = 1;
                    }
                    else if (var50.responseTime < 600)
                    {
                        var32 = 2;
                    }
                    else if (var50.responseTime < 1000)
                    {
                        var32 = 3;
                    }
                    else
                    {
                        var32 = 4;
                    }

                    this.zLevel += 100.0F;
                    this.drawTexturedModalRect(var18 + var16 - 12, var22, 0 + var51 * 10, 176 + var32 * 8, 10, 8);
                    this.zLevel -= 100.0F;
                }
            }
        }

        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glEnable(GL11.GL_ALPHA_TEST);
    }

    /**
     * Renders dragon's (boss) health on the HUD
     */
    private void renderBossHealth()
    {
        if (BossStatus.bossName != null && BossStatus.field_82826_b > 0)
        {
            --BossStatus.field_82826_b;
            FontRenderer var1 = this.mc.fontRenderer;
            ScaledResolution var2 = new ScaledResolution(this.mc.gameSettings, this.mc.displayWidth, this.mc.displayHeight);
            int var3 = var2.getScaledWidth();
            short var4 = 182;
            int var5 = var3 / 2 - var4 / 2;
            int var6 = (int)(BossStatus.healthScale * (float)(var4 + 1));
            byte var7 = 12;
            this.drawTexturedModalRect(var5, var7, 0, 74, var4, 5);
            this.drawTexturedModalRect(var5, var7, 0, 74, var4, 5);

            if (var6 > 0)
            {
                this.drawTexturedModalRect(var5, var7, 0, 79, var6, 5);
            }

            String var8 = BossStatus.bossName;
            var1.drawStringWithShadow(var8, var3 / 2 - var1.getStringWidth(var8) / 2, var7 - 10, 16777215);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            GL11.glBindTexture(GL11.GL_TEXTURE_2D, this.mc.renderEngine.getTexture("/gui/icons.png"));
        }
    }

    private void renderPumpkinBlur(int par1, int par2)
    {
        GL11.glDisable(GL11.GL_DEPTH_TEST);
        GL11.glDepthMask(false);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GL11.glDisable(GL11.GL_ALPHA_TEST);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, this.mc.renderEngine.getTexture("%blur%/misc/pumpkinblur.png"));
        Tessellator var3 = Tessellator.instance;
        var3.startDrawingQuads();
        var3.addVertexWithUV(0.0D, (double)par2, -90.0D, 0.0D, 1.0D);
        var3.addVertexWithUV((double)par1, (double)par2, -90.0D, 1.0D, 1.0D);
        var3.addVertexWithUV((double)par1, 0.0D, -90.0D, 1.0D, 0.0D);
        var3.addVertexWithUV(0.0D, 0.0D, -90.0D, 0.0D, 0.0D);
        var3.draw();
        GL11.glDepthMask(true);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glEnable(GL11.GL_ALPHA_TEST);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
    }

    private void renderProtectiveBlur(int var1, int var2)
    {
        GL11.glDisable(GL11.GL_DEPTH_TEST);
        GL11.glDepthMask(false);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GL11.glDisable(GL11.GL_ALPHA_TEST);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, this.mc.renderEngine.getTexture("%blur%/misc/protectiveblur.png"));
        Tessellator var3 = Tessellator.instance;
        var3.startDrawingQuads();
        var3.addVertexWithUV(0.0D, (double)var2, -90.0D, 0.0D, 1.0D);
        var3.addVertexWithUV((double)var1, (double)var2, -90.0D, 1.0D, 1.0D);
        var3.addVertexWithUV((double)var1, 0.0D, -90.0D, 1.0D, 0.0D);
        var3.addVertexWithUV(0.0D, 0.0D, -90.0D, 0.0D, 0.0D);
        var3.draw();
        GL11.glDepthMask(true);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glEnable(GL11.GL_ALPHA_TEST);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
    }

    private void renderPNVBlur(int var1, int var2)
    {
        GL11.glDisable(GL11.GL_DEPTH_TEST);
        GL11.glDepthMask(false);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GL11.glDisable(GL11.GL_ALPHA_TEST);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, this.mc.renderEngine.getTexture("%blur%/misc/PNVblur.png"));
        Tessellator var3 = Tessellator.instance;
        var3.startDrawingQuads();
        var3.addVertexWithUV(0.0D, (double)var2, -90.0D, 0.0D, 1.0D);
        var3.addVertexWithUV((double)var1, (double)var2, -90.0D, 1.0D, 1.0D);
        var3.addVertexWithUV((double)var1, 0.0D, -90.0D, 1.0D, 0.0D);
        var3.addVertexWithUV(0.0D, 0.0D, -90.0D, 0.0D, 0.0D);
        var3.draw();
        GL11.glDepthMask(true);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glEnable(GL11.GL_ALPHA_TEST);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
    }

    private void renderChum(int var1, int var2, int var3, float var4)
    {
        GL11.glDisable(GL11.GL_DEPTH_TEST);
        GL11.glDepthMask(false);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, var4);
        GL11.glDisable(GL11.GL_ALPHA_TEST);

        if (var3 == 0)
        {
            GL11.glBindTexture(GL11.GL_TEXTURE_2D, this.mc.renderEngine.getTexture("%blur%/misc/chum1.gif"));
        }
        else
        {
            GL11.glBindTexture(GL11.GL_TEXTURE_2D, this.mc.renderEngine.getTexture("%blur%/misc/chum2.gif"));
        }

        Tessellator var5 = Tessellator.instance;
        var5.startDrawingQuads();
        var5.addVertexWithUV(0.0D, (double)var2, -90.0D, 0.0D, 1.0D);
        var5.addVertexWithUV((double)var1, (double)var2, -90.0D, 1.0D, 1.0D);
        var5.addVertexWithUV((double)var1, 0.0D, -90.0D, 1.0D, 0.0D);
        var5.addVertexWithUV(0.0D, 0.0D, -90.0D, 0.0D, 0.0D);
        var5.draw();
        GL11.glDepthMask(true);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glEnable(GL11.GL_ALPHA_TEST);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, var4);
    }

    /**
     * Renders the vignette. Args: vignetteBrightness, width, height
     */
    private void renderVignette(float par1, int par2, int par3)
    {
        par1 = 1.0F - par1;

        if (par1 < 0.0F)
        {
            par1 = 0.0F;
        }

        if (par1 > 1.0F)
        {
            par1 = 1.0F;
        }

        this.prevVignetteBrightness = (float)((double)this.prevVignetteBrightness + (double)(par1 - this.prevVignetteBrightness) * 0.01D);
        GL11.glDisable(GL11.GL_DEPTH_TEST);
        GL11.glDepthMask(false);
        GL11.glBlendFunc(GL11.GL_ZERO, GL11.GL_ONE_MINUS_SRC_COLOR);
        GL11.glColor4f(this.prevVignetteBrightness, this.prevVignetteBrightness, this.prevVignetteBrightness, 1.0F);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, this.mc.renderEngine.getTexture("%blur%/misc/vignette.png"));
        Tessellator var4 = Tessellator.instance;
        var4.startDrawingQuads();
        var4.addVertexWithUV(0.0D, (double)par3, -90.0D, 0.0D, 1.0D);
        var4.addVertexWithUV((double)par2, (double)par3, -90.0D, 1.0D, 1.0D);
        var4.addVertexWithUV((double)par2, 0.0D, -90.0D, 1.0D, 0.0D);
        var4.addVertexWithUV(0.0D, 0.0D, -90.0D, 0.0D, 0.0D);
        var4.draw();
        GL11.glDepthMask(true);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
    }

    /**
     * Renders the portal overlay. Args: portalStrength, width, height
     */
    private void renderPortalOverlay(float par1, int par2, int par3)
    {
        if (par1 < 1.0F)
        {
            par1 *= par1;
            par1 *= par1;
            par1 = par1 * 0.8F + 0.2F;
        }

        GL11.glDisable(GL11.GL_ALPHA_TEST);
        GL11.glDisable(GL11.GL_DEPTH_TEST);
        GL11.glDepthMask(false);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, par1);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, this.mc.renderEngine.getTexture("/terrain.png"));
        float var4 = (float)(Block.portal.blockIndexInTexture % 16) / 16.0F;
        float var5 = (float)(Block.portal.blockIndexInTexture / 16) / 16.0F;
        float var6 = (float)(Block.portal.blockIndexInTexture % 16 + 1) / 16.0F;
        float var7 = (float)(Block.portal.blockIndexInTexture / 16 + 1) / 16.0F;
        Tessellator var8 = Tessellator.instance;
        var8.startDrawingQuads();
        var8.addVertexWithUV(0.0D, (double)par3, -90.0D, (double)var4, (double)var7);
        var8.addVertexWithUV((double)par2, (double)par3, -90.0D, (double)var6, (double)var7);
        var8.addVertexWithUV((double)par2, 0.0D, -90.0D, (double)var6, (double)var5);
        var8.addVertexWithUV(0.0D, 0.0D, -90.0D, (double)var4, (double)var5);
        var8.draw();
        GL11.glDepthMask(true);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glEnable(GL11.GL_ALPHA_TEST);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
    }

    /**
     * Renders the specified item of the inventory slot at the specified location. Args: slot, x, y, partialTick
     */
    private void renderInventorySlot(int par1, int par2, int par3, float par4)
    {
        ItemStack var5 = this.mc.thePlayer.inventory.mainInventory[par1];

        if (var5 != null)
        {
            float var6 = (float)var5.animationsToGo - par4;

            if (var6 > 0.0F)
            {
                GL11.glPushMatrix();
                float var7 = 1.0F + var6 / 5.0F;
                GL11.glTranslatef((float)(par2 + 8), (float)(par3 + 12), 0.0F);
                GL11.glScalef(1.0F / var7, (var7 + 1.0F) / 2.0F, 1.0F);
                GL11.glTranslatef((float)(-(par2 + 8)), (float)(-(par3 + 12)), 0.0F);
            }

            itemRenderer.renderItemAndEffectIntoGUI(this.mc.fontRenderer, this.mc.renderEngine, var5, par2, par3);

            if (var6 > 0.0F)
            {
                GL11.glPopMatrix();
            }

            itemRenderer.renderItemOverlayIntoGUI(this.mc.fontRenderer, this.mc.renderEngine, var5, par2, par3);
        }
    }

    /**
     * The update tick for the ingame UI
     */
    public void updateTick()
    {
        if (this.recordPlayingUpFor > 0)
        {
            --this.recordPlayingUpFor;
        }

        ++this.updateCounter;
    }

    public void setRecordPlayingMessage(String par1Str)
    {
        this.recordPlaying = "Now playing: " + par1Str;
        this.recordPlayingUpFor = 60;
        this.recordIsPlaying = true;
    }

    /**
     * returns a pointer to the persistant Chat GUI, containing all previous chat messages and such
     */
    public GuiNewChat getChatGUI()
    {
        return this.persistantChatGUI;
    }

    public int getUpdateCounter()
    {
        return this.updateCounter;
    }

    public static String gKclass()
    {
        return GuiIngame.class.getSimpleName();
    }
}
