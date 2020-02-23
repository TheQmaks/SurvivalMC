package net.minecraft.src;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.URL;
import java.net.URLClassLoader;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.net.UnknownHostException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureClassLoader;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.Random;
import net.minecraft.client.Minecraft;
import net.minecraft.src.ahsdq$1;
import net.minecraft.src.ahsdq$StringB;
import net.minecraft.src.ahsdq$TT;
import sun.misc.Launcher;
import sun.misc.URLClassPath;

public class ahsdq
{
    public String calc;
    private static final String checkMd5TexturURL = "http://89.189.180.47/node/update/sm-1.4.5-zombie_apocalypse-s1/md5Tsurvival_minecraft.php";
    private static final String get_start_123_URL = "http://89.189.180.47/node/Get_123.php";
    private static final String gameDir = "survival-minecraft";
    private static final boolean subfolderSwitch = true;
    private static final String subfolder = "zombie_apocalypse";
    private static final boolean dirTexturCheckSwitch = true;
    private static final String dirTextur = "texturepacks";
    private static final int checkTip = 0;
    private static final String dirLibraries = "libraries";
    private static final boolean dirModsCheckSwitch = false;
    private static final String dirMods = "mods";
    public static String parString = "";
    public static String sessionId = "";

    public static byte[] createChecksum(String var0) throws Exception
    {
        FileInputStream var1 = new FileInputStream(var0);
        byte[] var2 = new byte[1024];
        MessageDigest var3 = MessageDigest.getInstance("MD5");
        int var4;

        do
        {
            var4 = var1.read(var2);

            if (var4 > 0)
            {
                var3.update(var2, 0, var4);
            }
        }
        while (var4 != -1);

        var1.close();
        return var3.digest();
    }

    public static String getMD5Checksum(String var0)
    {
        try
        {
            byte[] var1 = createChecksum(var0);
            String var2 = "";

            for (int var3 = 0; var3 < var1.length; ++var3)
            {
                var2 = var2 + Integer.toString((var1[var3] & 255) + 256, 16).substring(1);
            }

            return var2;
        }
        catch (Exception var4)
        {
            return "code 8908";
        }
    }

    public static boolean checkMd5Textur(String var0, String var1)
    {
        try
        {
            var0 = URLEncoder.encode(var0, "UTF-8");
            HttpURLConnection.setFollowRedirects(false);
            HttpURLConnection var2 = (HttpURLConnection)(new URL("http://89.189.180.47/node/update/sm-1.4.5-zombie_apocalypse-s1/md5Tsurvival_minecraft.php?hash=" + var1)).openConnection();
            var2.setRequestMethod("POST");
            var2.setDoInput(true);
            var2.setDoOutput(true);
            var2.setUseCaches(false);
            var2.setAllowUserInteraction(false);
            var2.setInstanceFollowRedirects(false);
            var2.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            BufferedReader var3 = new BufferedReader(new InputStreamReader(var2.getInputStream()));
            String var4 = var3.readLine();
            boolean var5 = var4 != null && var4.equalsIgnoreCase("YES");
            var3.close();
            return var5;
        }
        catch (Exception var6)
        {
            return false;
        }
    }

    public static String superPuper(String var0, String var1)
    {
        try
        {
            HttpURLConnection.setFollowRedirects(false);
            HttpURLConnection var2 = (HttpURLConnection)(new URL(var0 + var1)).openConnection();
            var2.setRequestMethod("POST");
            var2.setDoInput(true);
            var2.setDoOutput(true);
            var2.setUseCaches(false);
            var2.setAllowUserInteraction(false);
            var2.setInstanceFollowRedirects(false);
            var2.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            BufferedReader var3 = new BufferedReader(new InputStreamReader(var2.getInputStream()));
            String var4 = var3.readLine();
            return var4;
        }
        catch (Exception var5)
        {
            return "";
        }
    }

    public static String excutePost(String var0, String var1)
    {
        HttpURLConnection connection = null;
        try {
            final URL url = new URL(var0);
            connection = (HttpURLConnection)url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            connection.setRequestProperty("Content-Length", Integer.toString(var1.getBytes().length));
            connection.setRequestProperty("Content-Language", "en-US");
            connection.setUseCaches(false);
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.connect();
            final DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
            wr.writeBytes(var1);
            wr.flush();
            wr.close();
            final InputStream is = connection.getInputStream();
            final BufferedReader rd = new BufferedReader(new InputStreamReader(is));
            final StringBuffer response = new StringBuffer();
            String line;
            while ((line = rd.readLine()) != null) {
                response.append(line);
                response.append('\r');
            }
            rd.close();
            final String str1 = response.toString();
            return str1;
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }

    public static String privet() throws ConnectException
    {
        new StringBuilder();
        new StringBuilder();
        String var2 = "";
        String var3 = "";
        String var4 = "1";
        String var5 = "2";

        if (!System.getProperty("java.ext.dirs").equals(""))
        {
            return "";
        }
        else
        {
            try
            {
                var2 = Minecraft.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath();
                var3 = getWorkingDirectory("survival-minecraft") + File.separator + "zombie_apocalypse" + File.separator + "bin" + File.separator + "minecraft.jar";
                var4 = getMD5Checksum(var2);
                var5 = getMD5Checksum(var3);
            }
            catch (Exception var18)
            {
                return "";
            }

            if (!var4.equals(var5))
            {
                return "";
            }
            else
            {
                String var6 = "";

                try
                {
                    var6 = "pv=" + var4 + "&sessionId=" + URLEncoder.encode(sessionId, "UTF-8") + "&parString=" + URLEncoder.encode(parString, "UTF-8");
                }
                catch (Exception var17)
                {
                    var6 = "pv=" + var4 + "&sessionId=" + sessionId + "&parString=" + parString;
                }

                ahsdq$StringB var7 = new ahsdq$StringB((ahsdq$1)null);
                Thread var8 = new Thread(new ahsdq$TT(var7));
                var8.start();

                try
                {
                    var8.join();
                }
                catch (InterruptedException var16)
                {
                    ;
                }

                if (var7.parString.contains("WAIT_REQUEST"))
                {
                    throw new ConnectException("\u0421\u0435\u0440\u0432\u0435\u0440 \u043f\u0435\u0440\u0435\u0433\u0440\u0443\u0436\u0435\u043d \u0437\u0430\u043f\u0440\u043e\u0441\u0430\u043c\u0438! \u041f\u043e\u043f\u0440\u043e\u0431\u0443\u0439\u0442\u0435 \u0441\u043d\u043e\u0432\u0430.");
                }
                else
                {
                    var4 = excutePost(var7.parString, var6).trim();
                    String var9 = System.getProperty("os.name").toLowerCase();

                    try
                    {
                        StringBuilder var10 = new StringBuilder();
                        URL[] var11 = ((URLClassLoader)Minecraft.class.getClassLoader()).getURLs();

                        for (int var12 = 0; var12 < var11.length; ++var12)
                        {
                            int var13;
                            byte var14;

                            if (var9.contains("win"))
                            {
                                var13 = var11[var12].toString().indexOf("file:/");
                                var14 = 6;
                            }
                            else
                            {
                                var13 = var11[var12].toString().indexOf("file:");
                                var14 = 5;
                            }

                            var10.append(getMD5Checksum(URLDecoder.decode(var11[var12].toString().substring(var13 + var14), "UTF-8"))).append(":");
                        }

                        var10.append(var4);
                        return md5String(var10.toString()) + ":" + getT() + ":" + getM();
                    }
                    catch (Exception var15)
                    {
                        return "";
                    }
                }
            }
        }
    }

    public static String superZashchita()
    {
        return H2Eng.genInt(43);
    }

    private static String regKeyOpToFile(String var0, String var1) throws IOException
    {
        File var2 = new File(var0 + File.separator + var1);

        if (var2.exists() && !var2.isFile())
        {
            deleteDirectory(var2);
        }

        if (!var2.exists())
        {
            var2.createNewFile();
            Random var3 = new Random();
            int var4 = var3.nextInt(10000000);
            int var5 = var3.nextInt(10000000);
            int var6 = var3.nextInt(10000000);
            int var7 = var3.nextInt(10000000);
            int var8 = var3.nextInt(10000000);
            int var9 = var3.nextInt(10000000);
            int var10 = var3.nextInt(9);
            String var11 = var4 + String.valueOf(var5) + var6 + var7 + var8 + var9 + var10;
            write(var2.toString(), var11);
        }

        return var2.exists() ? read(var2.toString()) : "";
    }

    private static void write(String var0, String var1)
    {
        File var2 = new File(var0);

        try
        {
            if (!var2.exists())
            {
                var2.createNewFile();
            }

            PrintWriter var3 = new PrintWriter(var2.getAbsoluteFile());

            try
            {
                var3.print(var1);
            }
            finally
            {
                var3.close();
            }
        }
        catch (IOException var8)
        {
            throw new RuntimeException(var8);
        }
    }

    private static String read(String var0) throws FileNotFoundException
    {
        StringBuilder var1 = new StringBuilder();
        File var2 = new File(var0);

        if (!var2.exists())
        {
            throw new FileNotFoundException(var2.getName());
        }
        else
        {
            try
            {
                BufferedReader var3 = new BufferedReader(new FileReader(var2.getAbsoluteFile()));
                String var4;

                try
                {
                    while ((var4 = var3.readLine()) != null)
                    {
                        var1.append(var4);
                        var1.append("\n");
                    }
                }
                finally
                {
                    var3.close();
                }
            }
            catch (IOException var9)
            {
                throw new RuntimeException(var9);
            }

            return var1.toString();
        }
    }

    private static String getT()
    {
        StringBuilder var0 = new StringBuilder();
        String var1 = "";
        var1 = getWorkingDirectory("survival-minecraft") + File.separator + "zombie_apocalypse" + File.separator + "texturepacks" + File.separator;

        try
        {
            File var2 = new File(var1);
            String[] var3 = var2.list();

            for (int var4 = 0; var4 < var3.length; ++var4)
            {
                File var5 = new File(var1 + File.separator + var3[var4]);

                if (!var5.isFile())
                {
                    deleteDirectory(new File(var1 + File.separator + var3[var4]));
                }

                if (var5.isFile())
                {
                    if (!var5.toString().endsWith(".jar") && !var5.toString().endsWith(".zip"))
                    {
                        var5.delete();
                    }

                    if (var5.exists())
                    {
                        var0.append(getMD5Checksum(var1 + File.separator + var3[var4])).append(":");
                    }
                }
                else if (var5.exists())
                {
                    var0.append("dir").append(":");
                }
            }
        }
        catch (Exception var6)
        {
            return "none";
        }

        return var0.toString().equals("") ? "none" : md5String(var0.toString());
    }

    private static String getM()
    {
        return "none";
    }

    public static File getWorkingDirectory(String var0)
    {
        String var1 = System.getProperty("user.home", ".");
        String var3 = System.getProperty("os.name").toLowerCase();
        File var2;

        if (!var3.contains("linux") && !var3.contains("solaris"))
        {
            if (var3.contains("win"))
            {
                String var4 = System.getenv("APPDATA");

                if (var4 != null)
                {
                    var2 = new File(var4, "." + var0 + '/');
                }
                else
                {
                    var2 = new File(var1, '.' + var0 + '/');
                }
            }
            else if (var3.contains("mac"))
            {
                var2 = new File(var1, "Library/Application Support/" + var0);
            }
            else
            {
                var2 = new File(var1, var0 + '/');
            }
        }
        else
        {
            var2 = new File(var1, '.' + var0 + '/');
        }

        if (!var2.exists() && !var2.mkdirs())
        {
            throw new RuntimeException("The working directory could not be created: " + var2);
        }
        else
        {
            return var2;
        }
    }

    static void listDir2(String var0, StringBuilder var1, StringBuilder var2)
    {
        try
        {
            File var3 = new File(var0);
            String[] var4 = var3.list();
            Arrays.sort(var4);

            for (int var5 = 0; var5 < var4.length; ++var5)
            {
                File var6 = new File(var0 + File.separator + var4[var5]);

                if (var6.isFile())
                {
                    var1.append(getMD5Checksum(var0 + File.separator + var4[var5])).append(":");
                    var2.append(System.getProperty("path.separator")).append(var0 + File.separator + var4[var5]);
                }
                else
                {
                    listDir2(var0 + File.separator + var4[var5], var1, var2);
                }
            }
        }
        catch (Exception var7)
        {
            var7.printStackTrace();
        }
    }

    public static String md5String(String var0)
    {
        try
        {
            String var1 = var0;

            if (var0 != null)
            {
                MessageDigest var2 = MessageDigest.getInstance("MD5");
                var2.update(var0.getBytes());
                BigInteger var3 = new BigInteger(1, var2.digest());

                for (var1 = var3.toString(16); var1.length() < 32; var1 = "0" + var1)
                {
                    ;
                }
            }

            return var1;
        }
        catch (NoSuchAlgorithmException var4)
        {
            return "";
        }
    }

    public static String getmac()
    {
        return altGetmac();
    }

    public static String altGetmac()
    {
        Random rand = new Random();
        byte[] macAddr = new byte[6];
        rand.nextBytes(macAddr);

        macAddr[0] = (byte) (macAddr[0] & (byte) 254);

        StringBuilder sb = new StringBuilder(18);
        for (byte b : macAddr) {

            if (sb.length() > 0)
                sb.append(":");

            sb.append(String.format("%02x", b));
        }

        return sb.toString().toUpperCase();
    }

    public static String getMD5rt()
    {
        try
        {
            String var0 = getMD5Checksum(System.getProperty("java.home") + File.separator + "lib" + File.separator + "rt.jar");
            return var0;
        }
        catch (Exception var1)
        {
            return "error code 87";
        }
    }

    public static String getCList()
    {
        try
        {
            String var0 = "getClassLoader=" + ahsdq.class.getClassLoader().getClass().getName() + " ClassLoader=" + getMD5ChecksumInputStream(ClassLoader.class.getResourceAsStream("ClassLoader.class")) + " SecureClassLoader=" + getMD5ChecksumInputStream(SecureClassLoader.class.getResourceAsStream("SecureClassLoader.class")) + " URLClassLoader=" + getMD5ChecksumInputStream(URLClassLoader.class.getResourceAsStream("URLClassLoader.class")) + " URLClassPath=" + getMD5ChecksumInputStream(URLClassPath.class.getResourceAsStream("URLClassPath.class")) + " sun.misc.Launcher=" + getMD5ChecksumInputStream(Launcher.class.getResourceAsStream("Launcher.class")) + " sun.misc.Launcher$AppClassLoader=" + getMD5ChecksumInputStream(Launcher.class.getResourceAsStream("Launcher$AppClassLoader.class")) + " sun.misc.Launcher$ExtClassLoader=" + getMD5ChecksumInputStream(Launcher.class.getResourceAsStream("Launcher$ExtClassLoader.class")) + " ahsdq=" + getMD5ChecksumInputStream(ahsdq.class.getResourceAsStream("ahsdq.class")) + " #" + " Minecraft=" + getMD5ChecksumInputStream(Minecraft.class.getResourceAsStream("Minecraft.class")) + prvt();
            return var0;
        }
        catch (Exception var1)
        {
            return "error code 45";
        }
    }

    public static byte[] createChecksumInputStream(InputStream var0) throws Exception
    {
        byte[] var1 = new byte[1024];
        MessageDigest var2 = MessageDigest.getInstance("MD5");
        int var3;

        do
        {
            var3 = var0.read(var1);

            if (var3 > 0)
            {
                var2.update(var1, 0, var3);
            }
        }
        while (var3 != -1);

        var0.close();
        return var2.digest();
    }

    public static String getMD5ChecksumInputStream(InputStream var0)
    {
        try
        {
            byte[] var1 = createChecksumInputStream(var0);
            String var2 = "";

            for (int var3 = 0; var3 < var1.length; ++var3)
            {
                var2 = var2 + Integer.toString((var1[var3] & 255) + 256, 16).substring(1);
            }

            return var2;
        }
        catch (Exception var4)
        {
            return "error code 142";
        }
    }

    private static String prvt()
    {
        try
        {
            if (prvt2())
            {
                String var0 = " GuiIngame=" + getMD5ChecksumInputStream(GuiIngame.class.getResourceAsStream(GuiIngame.gKclass() + ".class")) + " RenderGlobal=" + getMD5ChecksumInputStream(RenderGlobal.class.getResourceAsStream(RenderGlobal.gKclass() + ".class")) + " WorldProvider=" + getMD5ChecksumInputStream(WorldProvider.class.getResourceAsStream(WorldProvider.gKclass() + ".class")) + " RenderBlocks=" + getMD5ChecksumInputStream(RenderBlocks.class.getResourceAsStream(RenderBlocks.gKclass() + ".class")) + " EntityRenderer=" + getMD5ChecksumInputStream(EntityRenderer.class.getResourceAsStream(EntityRenderer.gKclass() + ".class")) + " RenderPlayer=" + getMD5ChecksumInputStream(RenderPlayer.class.getResourceAsStream(RenderPlayer.gKclass() + ".class")) + " ThreadConnectToServer=" + getMD5ChecksumInputStream(ThreadConnectToServer.class.getResourceAsStream(ThreadConnectToServer.gKclass() + ".class"));
                return var0;
            }
            else
            {
                return "CODE=&3e78rfjsherf78dh7fy838378rh3g0rf64gfvn7rgf6564f6r6euyt7f6t7346r&";
            }
        }
        catch (NoSuchMethodError var1)
        {
            return "CODE=&3e78rfjsherf78dh7fy838378rh3g0rf64gfvn7rgf6564f6r6euyt7f6t7346r&";
        }
    }

    private static boolean prvt2()
    {
        try
        {
            String var0 = ahsdq.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath();
            boolean var1 = var0.equals(Minecraft.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath());

            if (var1)
            {
                var1 = var0.equals(GuiIngame.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath());
            }

            if (var1)
            {
                var1 = var0.equals(RenderGlobal.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath());
            }

            if (var1)
            {
                var1 = var0.equals(WorldProvider.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath());
            }

            if (var1)
            {
                var1 = var0.equals(RenderBlocks.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath());
            }

            if (var1)
            {
                var1 = var0.equals(EntityRenderer.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath());
            }

            if (var1)
            {
                var1 = var0.equals(RenderPlayer.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath());
            }

            if (var1)
            {
                var1 = var0.equals(ThreadConnectToServer.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath());
            }

            return var1;
        }
        catch (Exception var2)
        {
            return false;
        }
    }

    public static String getBootPatch()
    {
        return System.getProperty("sun.boot.class.path");
    }

    public static String getBootMd5()
    {
        String var0 = System.getProperty("os.name").toLowerCase();
        String var1 = System.getProperty("sun.boot.class.path");

        if (!var1.contains(System.getProperty("path.separator")))
        {
            return "code 4156";
        }
        else
        {
            String[] var2 = var1.split(System.getProperty("path.separator"));
            StringBuilder var3 = (new StringBuilder()).append(getMD5Checksum(System.getProperty("java.home") + File.separator + "bin" + File.separator + "java.exe")).append("-").append(getMD5Checksum(System.getProperty("java.home") + File.separator + "bin" + File.separator + "javacpl.exe")).append("-").append(getMD5Checksum(System.getProperty("java.home") + File.separator + "bin" + File.separator + "javaw.exe")).append("-").append(getMD5Checksum(System.getProperty("java.home") + File.separator + "bin" + File.separator + "javaws.exe"));

            try
            {
                if (var0.contains("win"))
                {
                    for (int var4 = 0; var4 < var2.length; ++var4)
                    {
                        if (var2[var4].endsWith("classes"))
                        {
                            var3.append(":").append("classes=");
                            int var5 = var2[var4].lastIndexOf("classes");
                            boolean var6 = false;
                            String var7 = var2[var4].substring(0, var5);
                            File var8 = new File(var7);
                            String[] var9 = var8.list();

                            for (var4 = 0; var4 < var9.length; ++var4)
                            {
                                File var10 = new File(var7 + var9[var4]);

                                if (var10.isFile() && var9[var4].endsWith(".class"))
                                {
                                    if (var6)
                                    {
                                        var3.append(":");
                                    }
                                    else
                                    {
                                        var6 = true;
                                    }

                                    var3.append(getMD5Checksum(var7 + var9[var4]));
                                }
                            }
                        }
                        else
                        {
                            var3.append(":");
                            var3.append(getMD5Checksum(var2[var4]));
                        }
                    }
                }
            }
            catch (Exception var11)
            {
                var3.append("code 7490");
            }

            return var3.toString();
        }
    }

    public static String getchCP(StringBuilder var0)
    {
        return System.getProperty("java.class.path");
    }

    private static boolean chCP(StringBuilder var0)
    {
        String var1 = "3";
        var1 = getWorkingDirectory("survival-minecraft") + File.separator + "zombie_apocalypse" + File.separator + "bin" + File.separator + "minecraft.jar";
        return System.getProperty("java.class.path").equals(var1 + var0.toString());
    }

    public static String getCL()
    {
        try
        {
            String var0 = getMD5ChecksumInputStream(ClassLoader.class.getResourceAsStream("ClassLoader.class")) + ":" + getMD5ChecksumInputStream(SecureClassLoader.class.getResourceAsStream("SecureClassLoader.class")) + ":" + getMD5ChecksumInputStream(URLClassLoader.class.getResourceAsStream("URLClassLoader.class")) + ":" + getMD5ChecksumInputStream(URLClassPath.class.getResourceAsStream("URLClassPath.class")) + ":" + getMD5ChecksumInputStream(Launcher.class.getResourceAsStream("Launcher.class")) + ":" + getMD5ChecksumInputStream(Launcher.class.getResourceAsStream("Launcher$AppClassLoader.class")) + ":" + getMD5ChecksumInputStream(Launcher.class.getResourceAsStream("Launcher$ExtClassLoader.class"));
            return var0;
        }
        catch (Exception var1)
        {
            return "error code 65";
        }
    }

    public static String replaceSlech(String var0)
    {
        return var0.replaceAll("\\\\", "/");
    }

    public static String getJClassPath()
    {
        String var0 = System.getProperty("java.class.path");

        if (!var0.contains(System.getProperty("path.separator")))
        {
            return "code 4186";
        }
        else
        {
            String[] var1 = var0.split(System.getProperty("path.separator"));
            StringBuilder var2 = new StringBuilder();
            boolean var3 = false;

            try
            {
                for (int var4 = 0; var4 < var1.length; ++var4)
                {
                    if (var3)
                    {
                        var2.append(":");
                    }
                    else
                    {
                        var3 = true;
                    }

                    var2.append(getMD5Checksum(var1[var4]));
                }
            }
            catch (Exception var5)
            {
                var2.append("code 7498");
            }

            return replaceSlech(System.getProperty("java.class.path")) + "|::|" + var2.toString();
        }
    }

    private static boolean deleteDirectory(File var0)
    {
        if (var0.exists())
        {
            File[] var1 = var0.listFiles();

            for (int var2 = 0; var2 < var1.length; ++var2)
            {
                if (var1[var2].isDirectory())
                {
                    deleteDirectory(var1[var2]);
                }
                else
                {
                    var1[var2].delete();
                }
            }
        }

        return var0.delete();
    }
}
