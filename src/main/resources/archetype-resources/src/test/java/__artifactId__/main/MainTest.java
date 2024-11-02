package ${package}.${artifactId}.main;

import io.github.yajuhua.download.commons.Context;
import io.github.yajuhua.download.commons.progress.DownloadProgress;
import io.github.yajuhua.download.manager.DownloadManager;
import io.github.yajuhua.download.manager.Request;
import io.github.yajuhua.podcast2API.Channel;
import io.github.yajuhua.podcast2API.Item;
import io.github.yajuhua.podcast2API.Params;
import io.github.yajuhua.podcast2API.Type;
import io.github.yajuhua.podcast2API.extension.reception.InputAndSelectData;
import io.github.yajuhua.podcast2API.setting.Setting;
import lombok.extern.slf4j.Slf4j;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import ${groupId}.${artifactId}.main.Main;
import static org.testng.AssertJUnit.assertNotNull;

@Slf4j
public class MainTest {

    private static Params params;
    private static String channelLink = "频道链接";
    private static String singleLink = "单集节目链接";

    /**
     * 设置参数
     */
    @BeforeTest
    public void setParams(){
        params = new Params();
        List<Setting> settingList = new ArrayList<>();
        params.setSettings(settingList);
        params.setUrl(channelLink);
        params.setType(Type.Video);
        List<InputAndSelectData> inputAndSelectDataList = new ArrayList<>();
        params.setInputAndSelectDataList(inputAndSelectDataList);
    }

    /**
     * 获取频道信息
     * @throws IOException
     */
    @Test
    public void channel() throws Exception {
        Main main = new Main(params);
        Channel channel = main.channel();
        assertNotNull("无法获取频道信息",channel);
    }

    /**
     * 获取最新一集
     */
    @Test
    public void getLatest() throws Exception {
        Main main = new Main(params);
        Item item = main.latestItem();
        assertNotNull("无法获取最新一集",item);
    }

    /**
     * 测试下载，尽量选择较小的
     * @throws Exception
     */
    @Test
    public void download() throws Exception {
        Main main = new Main(params);
        Request request = main.getRequest(singleLink);
        DownloadManager downloadManager = new DownloadManager(1,1,1);
        String uuid = UUID.randomUUID().toString();
        request.setChannelUuid("channelUUId");
        request.setUuid(uuid);
        request.setDir(new File(System.getProperty("java.io.tmpdir")));
        downloadManager.add(request);
        downloadManager.startDownload();
        log.info("开始下载...");
        while (true){
            Thread.sleep(2000);
            String process = downloadManager.allDownloadProgress().stream()
                    .map(DownloadProgress::getDownloadProgress).map(String::valueOf).collect(Collectors.joining(","));
            log.info("下载进度: {}",process);
            List<Integer> statusList = downloadManager.allDownloadProgress().stream()
                    .map(DownloadProgress::getStatus)
                    .collect(Collectors.toList());
            Integer status = statusList.get(0);
            if (status == Context.COMPLETED){
                log.info("下载完成...");
                return;
            }
            if (errorStatusCode().contains(status)){
                log.error("下载异常: {}",downloadStatusDescription(status));
                throw new Exception();
            }
            //删除下载文件
            File[] tempFiles = new File(System.getProperty("java.io.tmpdir")).listFiles();
            for (File tempFile : tempFiles) {
                if (tempFile.getName().contains(uuid)){
                    tempFile.delete();
                }
            }
        }
    }

    /**
     * 获取下载状态码描述
     * @return
     */
    public static String downloadStatusDescription(Integer code){
        Map<Integer,String> map = new HashMap<>();
        map.put(-1,"未知错误");
        map.put(1,"下载完成");
        map.put(2,"合并完成");
        map.put(3,"转换mp4成功");
        map.put(4,"转换m4a成功");
        map.put(5,"完成");
        map.put(6,"下载错误");
        map.put(7,"合并错误");
        map.put(8,"转换mp4错误");
        map.put(9,"转换m4a错误");
        map.put(10,"下载中");
        map.put(11,"合并中");
        map.put(12,"转换mp4中");
        map.put(13,"转换m4a中");
        map.put(14,"下载第一部分中");
        map.put(15,"下载第二部分中");
        map.put(16,"第一部分下载完成");
        map.put(17,"第二部分下载完成");
        map.put(18,"第一部分下载错误");
        map.put(19,"第二部分下载错误");
        map.put(20,"移除下载");
        map.put(21,"alist正在上传");
        map.put(22,"alist上传失败");
        map.put(23,"alist连接失败");
        map.put(24,"alist上传成功");
        map.put(25,"alist找不到文件");
        map.put(26,"alist删除文件错误");
        map.put(27,"alist取消文件上传");
        map.put(28,"内存过小，无法上传到alist");
        return map.get(code);
    }

    /**
     * 错误下载的状态码
     * @return
     */
    public static List<Integer> errorStatusCode(){
        List<Integer> errorStatusCode = new ArrayList<>();
        Collections.addAll(errorStatusCode, Context.UNKNOWN,Context.DOWNLOAD_ERR,Context.MERGE_ERR,
                Context.TO_M4A_ERR,Context.TO_MP4_ERR,Context.DOWNLOAD_PATH1_ERR,Context.DOWNLOAD_PATH2_ERR);
        return errorStatusCode;
    }
}
