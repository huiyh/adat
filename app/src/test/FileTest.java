import com.ganji.android.build.common.FileAdapter;
import com.ganji.android.build.common.FileFuncs;
import com.ganji.android.build.common.LogConfig;
import org.apache.log4j.Logger;

import java.io.File;

/**
 * Created by huiyh on 2016/3/15.
 */
public class FileTest {

    public static final Logger LOGGER = LogConfig.getLogger(FileTest.class);

    public static void main(String[] args){

        File file = new File("C:\\Projects\\V7.3.0-GanjiLife-Integrate-Git");
        FileFuncs.eachFile(file, new FileAdapter() {
            @Override
            public boolean isOpenDir(File dir) {
                String name = dir.getName();
                if(".idea".equals(name) || ".git".equals(name) || ".gradle".equals(name)){
                    return false;
                }
                return true;
            }

            @Override
            public void onHasDir(File dir, boolean isOpen) {
                log((isOpen ? "Open: " : "Close: ") + dir.getAbsolutePath());
            }

            @Override
            public void onHasFile(File file) {
                log("    " + file.getName());
            }
        });
    }

    private static void log(String msg) {
        LOGGER.info(msg);
    }
}
