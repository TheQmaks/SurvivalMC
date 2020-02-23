package net.minecraft.src;

import java.util.*;
import net.minecraft.client.Minecraft;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

public class H2Eng {
    /*
    TODO:
    //Blink - packets saving and sending
    //fly/spider
    //speedhack
    //nofall
     */

    public static Minecraft mc = Minecraft.getMinecraft();

    public static boolean esp = false;
    public static boolean xray = false;
    public static boolean blink = false;
    public static boolean regen = false;
    public static boolean antikb = false;
    public static boolean sprint = false;
    public static boolean noslow = false;
    public static boolean tracers = false;
    public static boolean freecam = false;
    public static boolean fastuse = false;
    public static boolean chestesp = false;
    public static boolean killaura = false;
    public static boolean waterwalk = false; //recode to ncp version
    public static boolean fastbreak = false;
    public static boolean fullbright = false;

    private static double posX = 0.0D;
    private static double posY = 0.0D;
    private static double posZ = 0.0D;
    private static float rotationYaw = 0.0F;
    private static float rotationPitch = 0.0F;

    private static double deathX = 0.0D;
    private static double deathY = 0.0D;
    private static double deathZ = 0.0D;

    public static ArrayList<Packet> packets = new ArrayList<Packet>();

    private static boolean[] KeyStates = new boolean[256];
    private static ArrayList<Integer> xray_blocks = new ArrayList<Integer>();

    public static void addBlock(int i) {
        xray_blocks.add(i);
    }

    public static void delBlock(int i) {
        Iterator<Integer> iter = xray_blocks.iterator();

        while (iter.hasNext()) {
            int id = iter.next();

            if (id == i)
                iter.remove();
        }
    }

    public static boolean isBlockExists(int i) {
        return xray_blocks.contains(i);
    }

    private static String getBlocks() {
        return "&6XRay block's: " + (xray_blocks.toString().equals("[]") ? "&cXRay list is empty."
                : "&a" + xray_blocks.toString().replace("[", "").replace("]", ""));
    }

    private static ArrayList<String> friends = new ArrayList<String>();

    public static void addFriend(String name) {
        friends.add(name.toLowerCase());
    }

    public static void removeFriend(String name) {
        Iterator<String> iter = friends.iterator();

        while (iter.hasNext()) {
            String iName = iter.next();

            if (iName.equals(name.toLowerCase()))
                iter.remove();
        }
    }

    public static boolean isFriend(String name) {
        return friends.contains(name.toLowerCase());
    }

    public static void msg(String s, boolean prefix) {
        s = (prefix ? "&f&l[&3H2Eng&f&l] &r" : "") + s;
        mc.thePlayer.addChatMessage(s.replace("&", "\u00a7"));
    }

    private static void drawText(FontRenderer fr, String text, int x, int y) {
        fr.drawStringWithShadow(text.replace("&", "\u00a7"), x, y, 1);
    }

    private static boolean checkKey(int key) {
        return mc.currentScreen != null ? false
                : (Keyboard.isKeyDown(key) != KeyStates[key] ? (KeyStates[key] = !KeyStates[key]) : false);
    }

    public static String genInt(int length) {
        String result = "";
        for (int i = 0; i < length; i++) {
            result = result + new Random().nextInt(10);
        }
        return result;
    }

    private static void binds() {
        if(Keyboard.isKeyDown(Keyboard.KEY_Z)) {
            mc.gameSettings.fovSetting = 8.0F;
        } else {
            mc.gameSettings.fovSetting = 0.0F;
        }
        if (checkKey(Keyboard.KEY_V)) {
            mc.thePlayer.sendChatMessage("\u00a7");
        }
        if (checkKey(Keyboard.KEY_C)) {
            esp = !esp;
        }
        if (checkKey(Keyboard.KEY_X)) {
            xray = !xray;
            mc.renderGlobal.loadRenderers();
        }
        if (checkKey(Keyboard.KEY_F)) {
            blink = !blink;

            if(!blink) {
                for(Packet packet : packets) {
                    mc.thePlayer.sendQueue.addToSendQueue(packet);
                }

                packets.clear();
            }
        }
        if (checkKey(Keyboard.KEY_H)) {
            regen = !regen;
        }
        if (checkKey(Keyboard.KEY_K)) {
            antikb = !antikb;
        }
        if(checkKey(Keyboard.KEY_M)) {
            sprint = !sprint;
        }
        if (checkKey(Keyboard.KEY_L)) {
            noslow = !noslow;
        }
        if(checkKey(Keyboard.KEY_I)) {
            fastuse = !fastuse;
        }
        if (checkKey(Keyboard.KEY_U)) {
            tracers = !tracers;
        }
        if (checkKey(Keyboard.KEY_G)) {
            freecam = !freecam;
            if (freecam) {
                posX = mc.thePlayer.posX;
                posY = mc.thePlayer.posY;
                posZ = mc.thePlayer.posZ;
                rotationYaw = mc.thePlayer.rotationYaw;
                rotationPitch = mc.thePlayer.rotationPitch;
                EntityOtherPlayerMP entity = new EntityOtherPlayerMP(mc.theWorld, mc.thePlayer.getCommandSenderName());
                entity.setPositionAndRotation(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ,
                        mc.thePlayer.rotationYaw, mc.thePlayer.rotationPitch);
                mc.theWorld.addEntityToWorld(-1, entity);
            } else {
                mc.thePlayer.setPositionAndRotation(posX, posY, posZ, rotationYaw, rotationPitch);
                mc.theWorld.removeEntityFromWorld(-1);
            }
        }
        if (checkKey(Keyboard.KEY_R)) {
            killaura = !killaura;
        }
        if (checkKey(Keyboard.KEY_B)) {
            chestesp = !chestesp;
        }
        if(checkKey(Keyboard.KEY_N)) {
            waterwalk = !waterwalk;
        }
        if (checkKey(Keyboard.KEY_J)) {
            fastbreak = !fastbreak;
        }
        if (checkKey(Keyboard.KEY_O)) {
            fullbright = !fullbright;

            if (fullbright) {
                float[] bright = mc.theWorld.provider.lightBrightnessTable;
                for (int i = 0; i < bright.length; i++) {
                    bright[i] = 1.0F;
                }
            } else {
                mc.theWorld.provider.registerWorld(mc.theWorld);
            }
        }
    }

    private static boolean overwater;
    private static int delay;

    public static void onUpdate() {
        binds();

        if(waterwalk) {
            if (isOnLiquid(mc.thePlayer.boundingBox))
            {
                delay += 1;

                if (delay == 4)
                {
                    mc.thePlayer.sendQueue.addToSendQueue(new Packet11PlayerPosition(mc.thePlayer.posX, mc.thePlayer.boundingBox.minY - 0.02D, mc.thePlayer.posY - 0.02D, mc.thePlayer.posZ, false));
                    delay = 0;
                }

            }

            if (isInLiquid())
            {
                mc.thePlayer.motionY = 0.08500000000000001D;
            }

            Block Blocks = Block.blocksList[mc.theWorld.getBlockId((int) mc.thePlayer.posX, (int) mc.thePlayer.posY - 2, (int) mc.thePlayer.posZ)];
            Block lel = Block.blocksList[mc.theWorld.getBlockId((int) mc.thePlayer.posX, (int) mc.thePlayer.posY + 1, (int) mc.thePlayer.posZ)];
            if ((Blocks instanceof BlockFluid))
                overwater = true;
            else if (!(Blocks instanceof BlockFluid))
            {
                overwater = false;
            } else if (((lel instanceof BlockFluid)) ||
                    ((Blocks instanceof BlockFluid)) || (!(Blocks instanceof BlockFluid)));
        }
        if(fastuse) {
            ItemStack currentItem = mc.thePlayer.getHeldItem();
            if (mc.thePlayer.isUsingItem() && (currentItem.getItem() instanceof ItemBow || currentItem.getItem() instanceof ItemFood) && Minecraft.getMinecraft().thePlayer.onGround && mc.thePlayer.ticksExisted % 4 == 0) {
                try {
                    if (mc.thePlayer.getHeldItem().getItem() instanceof ItemBow) { //if item is bow mc.playerController.sendUseItem(mc.thePlayer, mc.theWorld, mc.thePlayer.getHeldItem(), 1);
                        mc.thePlayer.sendQueue.addToSendQueue(new Packet15Place(-1, -1, -1, 255, currentItem, 1.0f, 1.0f, 1.0f));
                    } else { //if item is food mc.playerController.syncCurrentPlayItem();
                        mc.thePlayer.sendQueue.addToSendQueue(new Packet16BlockItemSwitch(mc.thePlayer.inventory.currentItem));
                    }
                    for (int i = 0; i < 2; ++i) {
                            mc.thePlayer.sendQueue.addToSendQueue(new Packet13PlayerLookMove(mc.thePlayer.posX, mc.thePlayer.posY + 1.0E-9, mc.thePlayer.posY + 1.6200000047683716D, mc.thePlayer.posZ, mc.thePlayer.rotationYaw, mc.thePlayer.rotationPitch, true));
                    }
                    /*for (int i = 0; i < 1000; i++) {
                        //mc.thePlayer.sendMotionUpdates();
                        Packet10Flying packet = new Packet10Flying(mc.thePlayer.onGround);
                        packet.moving = false;
                        mc.thePlayer.sendQueue.addToSendQueue(packet);
                    }*/
                    mc.thePlayer.sendQueue.addToSendQueue(new Packet14BlockDig(5, 0, 0, 0, 255)); //mc.playerController.onStoppedUsingItem(mc.thePlayer);
                } catch (Exception ex) {}
            }
        }

        if (mc.thePlayer.isDead) {
            deathX = mc.thePlayer.posX;
            deathY = mc.thePlayer.posY;
            deathZ = mc.thePlayer.posZ;
        }

        if (regen && mc.thePlayer.getFoodStats().getFoodLevel() > 16 && mc.thePlayer.getHealth() < 20.0F && mc.thePlayer.onGround && mc.thePlayer.ticksExisted % 3 == 0) {
            for (int i = 0; i < 20; i++) {
                mc.thePlayer.sendQueue.addToSendQueue(new Packet10Flying(false));
                //mc.thePlayer.sendQueue.addToSendQueue(
                //new Packet11PlayerPosition(mc.thePlayer.posX, -999.0D, -999.0D, mc.thePlayer.posZ, true));
                //mc.thePlayer.sendQueue.addToSendQueue(new Packet13PlayerLookMove(mc.thePlayer.posX, -999.0D,
                //-999.0D, mc.thePlayer.posZ, mc.thePlayer.rotationYaw, mc.thePlayer.rotationPitch, true));
            }
        }

        if (killaura) {
            for (Object o : mc.theWorld.loadedEntityList) {
                Entity e = (Entity) o;
                if (e instanceof EntityLiving) {
                    if (e.getDistanceToEntity(mc.thePlayer) <= 6 && e != mc.thePlayer && e.isEntityAlive()) {
                        if(e instanceof EntityPlayer) {
                            if(isFriend(((EntityPlayer)e).getCommandSenderName())) continue;
                        }
                        //mc.playerController.attackEntity(mc.thePlayer, e);
                        /*if (mc.thePlayer.boundingBox.intersectsWith(e.boundingBox)) {
                            for (int i = 0; i < 250; i++) {
                                Packet10Flying packet = new Packet10Flying(mc.thePlayer.onGround);
                                packet.moving = false;
                                mc.thePlayer.sendQueue.addToSendQueue(packet);
                            }
                        }*/
                        mc.thePlayer.sendQueue.addToSendQueue(new Packet7UseEntity(mc.thePlayer.entityId, e.entityId, 1));
                        mc.thePlayer.swingItem();
                    }
                }
            }
        }

        if(sprint) {
            mc.thePlayer.setSprinting(true);
        }
    }

    private static int y = 2;

    public static void renderCheat(FontRenderer fr, int width, int height) {
        drawText(fr, "&6SurvivalHack [vk.com/h2eng]", 2, 2);
        drawText(fr, (esp ? "&e" : "&c") + "[C] ESP", 2, 12);
        drawText(fr, (xray ? "&e" : "&c") + "[X] XRAY", 2, 22);
        drawText(fr, (blink ? "&e" : "&c") + "[F] BLINK", 2, 32);
        drawText(fr, (regen ? "&e" : "&c") + "[H] REGEN", 2, 42);
        drawText(fr, (sprint ? "&e" : "&c") + "[M] SPRINT", 2, 52);
        drawText(fr, (antikb ? "&e" : "&c") + "[K] ANTIKB", 2, 62);
        drawText(fr, (noslow ? "&e" : "&c") + "[L] NOSLOW", 2, 72);
        drawText(fr, (fastuse ? "&e" : "&c") + "[I] FASTUSE", 2, 82);
        drawText(fr, (tracers ? "&e" : "&c") + "[U] TRACERS", 2, 92);
        drawText(fr, (freecam ? "&e" : "&c") + "[G] FREECAM", 2, 102);
        drawText(fr, (killaura ? "&e" : "&c") + "[R] KILLAURA", 2, 112);
        drawText(fr, (chestesp ? "&e" : "&c") + "[B] CHESTESP", 2, 122);
        drawText(fr, (waterwalk ? "&e" : "&c") + "[N] WATERWALK", 2, 132);
        drawText(fr, (fastbreak ? "&e" : "&c") + "[J] FASTBREAK", 2, 142);
        drawText(fr, (fullbright ? "&e" : "&c") + "[O] FULLBRIGHT", 2, 152);

        drawText(fr, "&6[Z] ZOOM", 2, height - 30);
        drawText(fr, "&6[V] DISCONNECT", 2, height - 20);
        drawText(fr, getBlocks(), 2, height - 10);

        String currentX = String.format("%.1f", mc.thePlayer.posX);
        String currentY = String.format("%.1f", mc.thePlayer.posY);
        String currentZ = String.format("%.1f", mc.thePlayer.posZ);
        String currentCoords = "&6X: &c" + currentX + " &f| &6Y: &c" + currentY + " &f| &6Z: &c" + currentZ;
        int indent = 1;
        int borderWidth = fr.getStringWidth("---------------------------");
        drawText(fr, "&6CURRENT COORDS", width - indent - borderWidth / 2 - fr.getStringWidth("CURRENT COORDS") / 2,
                height - 20);
        drawText(fr, currentCoords, width - indent - borderWidth / 2 - fr.getStringWidth("X: " + currentX + " | Y: " + currentY + " | Z: " + currentZ) / 2,
                height - 10);
        if (deathX != 0.0D && deathY != 0.0D && deathZ != 0.0D) {
            String posX = String.format("%.1f", deathX);
            String posY = String.format("%.1f", deathY);
            String posZ = String.format("%.1f", deathZ);
            String text = "&6DEATH COORDS";
            drawText(fr, text,
                    width - indent
                            - borderWidth / 2
                            - fr.getStringWidth("DEATH COORDS") / 2,
                    height - 50);
            String coords = "&6X: &c" + posX + " &f| &6Y: &c" + posY + " &f| &6Z: &c" + posZ;
            drawText(fr, coords, width - indent
                            - borderWidth / 2
                            - fr.getStringWidth("X: " + posX + " | Y: " + posY + " | Z: " + posZ) / 2,
                    height - 40);
            drawText(fr, "&f---------------------------", width - borderWidth - 1,
                    height - 30);
        }

        for (EntityPlayer e : (List<EntityPlayer>) mc.theWorld.playerEntities) {
            if (e != mc.renderViewEntity) {
                double distance = mc.thePlayer.getDistanceToEntity(e);
                String color = "&" + (distance < 16.0D ? (distance < 5.0D ? "c" : "e") : "a");
                String str = e.getCommandSenderName() + " [" + String.format("%.1f", distance) + "]";
                String s = "&6" + e.getCommandSenderName() + " &f[" + color + String.format("%.1f", distance) + "&f]";
                drawText(fr, s, width - fr.getStringWidth(str) - 1, y);
                y += 10;
            }
        }
        y = 2;
    }

    public static boolean isOnLiquid(AxisAlignedBB boundingBox)
    {
        boundingBox = boundingBox.contract(0.01D, 0.0D, 0.01D).offset(0.0D, -0.01D, 0.0D);
        boolean onLiquid = false;
        int y = (int)boundingBox.minY;
        for (int x = MathHelper.floor_double(boundingBox.minX); x < MathHelper.floor_double(boundingBox.maxX + 1.0D); x++) {
            for (int z = MathHelper.floor_double(boundingBox.minZ); z < MathHelper.floor_double(boundingBox.maxZ + 1.0D); z++) {
                int id = mc.theWorld.getBlockId(x, y, z);
                Block block = Block.blocksList[id];
                if (id == 0) // if is air
                {
                    continue;
                }
                if (!(block instanceof BlockFluid)) {
                    return false;
                }

                onLiquid = true;
            }
        }

        return onLiquid;
    }

    private static boolean isInLiquid()
    {
        AxisAlignedBB par1AxisAlignedBB = mc.thePlayer.boundingBox.contract(0.001D, 0.001D, 0.001D);
        int minX = MathHelper.floor_double(par1AxisAlignedBB.minX);
        int maxX = MathHelper.floor_double(par1AxisAlignedBB.maxX + 1.0D);
        int minY = MathHelper.floor_double(par1AxisAlignedBB.minY);
        int maxY = MathHelper.floor_double(par1AxisAlignedBB.maxY + 1.0D);
        int minZ = MathHelper.floor_double(par1AxisAlignedBB.minZ);
        int maxZ = MathHelper.floor_double(par1AxisAlignedBB.maxZ + 1.0D);

        if (!mc.theWorld.checkChunksExist(minX, minY, minZ, maxX, maxY, maxZ)) {
            return false;
        }
        Vec3 vec = Vec3.createVectorHelper(0.0D, 0.0D, 0.0D);
        for (int X = minX; X < maxX; X++) {
            for (int Y = minY; Y < maxY; Y++) {
                for (int Z = minZ; Z < maxZ; Z++) {
                    Block block = Block.blocksList[mc.theWorld.getBlockId(X, Y, Z)];
                    if ((block instanceof BlockFluid)) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    public static void drawBorderedRect(float x, float y, float x2, float y2, float l1, int col1, int col2)
    {
        drawRect(x, y, x2, y2, col2);

        float f = (float) (col1 >> 24 & 0xFF) / 255F;
        float f1 = (float) (col1 >> 16 & 0xFF) / 255F;
        float f2 = (float) (col1 >> 8 & 0xFF) / 255F;
        float f3 = (float) (col1 & 0xFF) / 255F;

        GL11.glEnable(GL11.GL_BLEND);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glEnable(GL11.GL_LINE_SMOOTH);

        GL11.glPushMatrix();
        GL11.glColor4f(f1, f2, f3, f);
        GL11.glLineWidth(l1);
        GL11.glBegin(GL11.GL_LINES);
        GL11.glVertex2f(x, y);
        GL11.glVertex2f(x, y2);
        GL11.glVertex2f(x2, y2);
        GL11.glVertex2f(x2, y);
        GL11.glVertex2f(x, y);
        GL11.glVertex2f(x2, y);
        GL11.glVertex2f(x, y2);
        GL11.glVertex2f(x2, y2);
        GL11.glEnd();
        GL11.glPopMatrix();

        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glDisable(GL11.GL_LINE_SMOOTH);
    }


    public static void drawRect(float g, float h, float i, float j, int col1)
    {
        float f = (float) (col1 >> 24 & 0xFF) / 255F;
        float f1 = (float) (col1 >> 16 & 0xFF) / 255F;
        float f2 = (float) (col1 >> 8 & 0xFF) / 255F;
        float f3 = (float) (col1 & 0xFF) / 255F;

        GL11.glEnable(GL11.GL_BLEND);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glEnable(GL11.GL_LINE_SMOOTH);

        GL11.glPushMatrix();
        GL11.glColor4f(f1, f2, f3, f);
        GL11.glBegin(GL11.GL_QUADS);
        GL11.glVertex2d(i, h);
        GL11.glVertex2d(g, h);
        GL11.glVertex2d(g, j);
        GL11.glVertex2d(i, j);
        GL11.glEnd();
        GL11.glPopMatrix();

        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glDisable(GL11.GL_LINE_SMOOTH);
    }
}
