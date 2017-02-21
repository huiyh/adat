import com.ganji.android.build.common.VerifyUtils;
import com.ganji.android.build.jar.PatchBuilder;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import sun.security.tools.JarSigner;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * Created by huiyh on 2016/2/17.
 */
public class BuildTestMain {

    private static final Logger logger = LogManager.getLogger(BuildTestMain.class);

    private static String[] classNames = new String[]{
            "com.ganji.android.fragment.aq",
            "com.ganji.android.fragment.ar",
            "com.ganji.android.fragment.as",
            "com.ganji.android.fragment.at",
            "com.ganji.android.fragment.au",
            "com.ganji.android.fragment.av",
            "com.ganji.android.fragment.aw",
            "com.ganji.android.fragment.ax",
            "com.ganji.android.fragment.ay",
            "com.ganji.android.fragment.az",
            "com.ganji.android.fragment.ba",
            "com.ganji.android.fragment.bb",
            "com.ganji.android.fragment.bc",
            "com.ganji.android.fragment.bd",
            "com.ganji.android.fragment.be",
            "com.ganji.android.fragment.bf",
            "com.ganji.android.fragment.bg",
            "com.ganji.android.fragment.bh",
            "com.ganji.android.fragment.bi",
            "com.ganji.android.fragment.bj",
            "com.ganji.android.fragment.bk",
            "com.ganji.android.fragment.bl",
            "com.ganji.android.fragment.bm",
            "com.ganji.android.fragment.bn",
            "com.ganji.android.fragment.bo",
            "com.ganji.android.fragment.bp",
            "com.ganji.android.fragment.bq",
            "com.ganji.android.fragment.br",
            "com.ganji.android.fragment.bs",
            "com.ganji.android.fragment.bt",
            "com.ganji.android.fragment.bu",
            "com.ganji.android.fragment.bv",
            "com.ganji.android.fragment.bw",
            "com.ganji.android.fragment.aq$a",
            "com.ganji.android.fragment.aq$b",
            "com.ganji.android.fragment.aq$c"
    };
    //    private static String[] patchClassArray = new String[]{
//            "com.ganji.android.html5.GJJsonRpcServer",
//            "com.ganji.android.gui.InformationView",
//            "com.ganji.android.comp.html5.Html5Activity"};
    private static String[] patchClassArray = new String[]{
            "com.ganji.android.fragment.MyinfoFragment",
            "com.ganji.android.myinfo.control.SettingActivity"};

//    private static String[] patchClassArray = new String[]{
//            "com.ganji.android.gui.InformationView"};

//    private static String[] patchClassArray = new String[]{
//            "com.ganji.android.comp.html5.Html5Activity"};

    public static void noproguard(){

    }


    public static void main(String[] args) throws Exception {
        // 从传入参数获取数据,如果没有传,使用默认数据
//        String currentPath = new File("").getAbsolutePath();
//        String ROOT_PATH = currentPath.substring(0,currentPath.lastIndexOf(File.separator));
//        String ROOT_DIR_NAME = ROOT_PATH.substring(ROOT_PATH.lastIndexOf(File.separator) + 1,ROOT_PATH.length());
//        System.out.println(currentPath);
//        System.out.println(ROOT_PATH);
//        System.out.println(ROOT_DIR_NAME);
        PatchBuilder builder = createBuilder();
        File build = builder.build();
//        File build = new File("opt/myinfo_favorite.jar");
        String fileMd5 = VerifyUtils.getFileMd5(build);

        File file = new File(build.getParent(), "MD5="+fileMd5);
        if(!file.exists()){
            file.createNewFile();
        }
        System.out.println(build.getName()+" md5="+fileMd5);


    }

    private static PatchBuilder createBuilder() {
        PatchBuilder builder = new PatchBuilder();

        File sourceFile = new File("opt/classes.jar");
        boolean exists = sourceFile.exists();
        builder.setSourceFile(sourceFile);

        File mapping = new File("opt/mapping.txt");
        boolean exists1 = mapping.exists();
        List<String> patchClasses = getPatchClasses();
        builder.setPatchClassList(mapping,patchClasses);

        File key = new File("GanjiAndroid.key");
        builder.setSignature(key,"ganji2010","ganji2010","ganji");
        return builder;
    }

    /**
     * Step:0 获取补丁Jar的class列表(混淆前)
     * 可以是手动配置,也可以是
     * @return
     */
    private static List<String> getPatchClasses() {
        List<String> patchClassList = Arrays.asList(patchClassArray);
        return patchClassList;
    }

    /**
     * Step:4 为jar签名
     */
    public static void testJarSigner2() {
        File key = new File("res/GanjiAndroid.key");
        File source = new File("res/gjlife_patch_dex.jar");
        File dest = new File("res/gjlife_patch_dex_signed.jar");
        try {
            // Step:4 为jar签名
            PatchBuilder.jarSigner(key,"ganji2010","ganji2010","ganji",source,dest);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Step:4 为jar签名
     */
    public static void testJarSigner1() {
//        sun.security.tools.JarSigner jarSigner = new JarSigner();
//        jarSigner.
//        String[] signerArgs = new String[]{"--output=res/gjlife_patch_dex.jar","res/gjlife_patch.jar",};
        String argStr = "-verbose -sigalg MD5withRSA -digestalg SHA1 -keystore res/GanjiAndroid.key -storepass ganji2010 -keypass ganji2010 -signedjar res/gjlife_patch_dex_signed.jar res/gjlife_patch_dex.jar ganji";
        String[] signerArgs = argStr.split(" ");
        System.out.println(Arrays.toString(signerArgs));
        try {
            JarSigner.main(signerArgs);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * Step:3 将jar转为dexJar
     */
    public static void testConvertDex() {
//        String[] args = new String[]{"--dex","--output=gjlife_patch_dex.jar","gjlife_patch.jar",};
//        com.android.dx.command.Main.main(args);

        String[] dexerArgs = new String[]{"--output=res/gjlife_patch_dex.jar","res/gjlife_patch.jar",};
        com.android.dx.command.dexer.Main.Arguments arguments = new com.android.dx.command.dexer.Main.Arguments();
        arguments.parse(dexerArgs);
        try {
            com.android.dx.command.dexer.Main.run(arguments);
        } catch (IOException e) {
            e.printStackTrace();
            logger.error(e);
        }
    }

    /**
     * Step:2 从原始jar包中获取到对应的文件,将需要的".class"文件打包为新的jar
     */
    public static void testCreatePatchJar() {
        try {
            File sourceFile = new File("res/gjlife_classes.jar");
            File patchFile = new File("res/gjlife_patch.jar");
            PatchBuilder.createPatchJar(sourceFile, patchFile,Arrays.asList(classNames));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Step:1 从mapping文件中获取混淆后的文件名
     */
    public static void testParseProguardMapping() {
        List<String> progardNames = PatchBuilder.getProguardedClasses(new File("res/mapping.txt"), Arrays.asList(classNames));
        System.out.println(progardNames.toString());
    }

    //    public static final String LOG_FILE_NAME = "class_list_%s.txt";
//    public static final String LOG_FILE_FORMAT = "yyyy-MM-dd.HH.mm";

//    public static File getLogFile(){
//        SimpleDateFormat format = new SimpleDateFormat(LOG_FILE_FORMAT);
//        File file = new File(String.format(LOG_FILE_NAME,format.format(new Date())));
//        return file;
//    }

//    public static void initLogger(){
//        File file = getLogFile();
//        LogConfig.initLogConfig(file);
//        logger = LogManager.getLogger(Main.class);
//    }
}
