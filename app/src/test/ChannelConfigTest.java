import com.ganji.android.build.channel.MultiChannelPublisher;
import com.ganji.android.build.common.entity.SigningConfig;
import com.ganji.android.build.common.KeystoreConfig;
import org.json.JSONObject;

import java.io.File;

/**
 * Created by huiyh on 2016/9/24.
 */
public class ChannelConfigTest {

    public static final String APK_PATH = "C:\\Users\\huiyh\\Desktop\\Ganji_online-release.apk";
    public static final String CHANNELS_PATH = "C:\\Projects\\V7.7.0-GanjiLife-Integrate\\GJAndroidClient15\\channels.txt";

    public static void main(String[] args) {
        File srcApkFile = new File(APK_PATH);
        File channelsFile = new File(CHANNELS_PATH);
        SigningConfig signingConfig = KeystoreConfig.loadSigning();
        JSONObject channelConfigs = MultiChannelPublisher.readJson(channelsFile);
        MultiChannelPublisher.create(srcApkFile,signingConfig, channelConfigs);
    }

}
