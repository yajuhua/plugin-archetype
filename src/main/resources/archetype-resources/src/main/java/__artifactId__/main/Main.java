package ${package}.${artifactId}.main;

import com.google.gson.Gson;
import io.github.yajuhua.download.commons.Operation;
import io.github.yajuhua.download.commons.Type;
import io.github.yajuhua.download.manager.DownloadManager;
import io.github.yajuhua.download.manager.Request;
import io.github.yajuhua.podcast2API.Channel;
import io.github.yajuhua.podcast2API.Item;
import io.github.yajuhua.podcast2API.Params;
import io.github.yajuhua.podcast2API.Podcast2;
import io.github.yajuhua.podcast2API.extension.build.ExtendList;
import io.github.yajuhua.podcast2API.setting.Setting;
import lombok.extern.slf4j.Slf4j;

import java.io.InputStream;
import java.util.*;

@Slf4j
public class Main implements Podcast2 {

    private Params params;
    private Gson gson = new Gson();

    public Main() {
    }

    public Main(String paramsStr) {
        this.params = gson.fromJson(paramsStr, Params.class);
    }

    public Main(Params params) {
        this.params = params;
    }

    /**
     * 获取频道信息
     * @return Channel
     * @throws Exception
     */
    @Override
    public Channel channel() throws Exception {
        return Channel.builder()
                .title("频道名称")
                .status(1)
                .image("频道封面")
                .link("频道链接")
                .description("频道描述")
                .author("频道作者")
                .category("分类")
                .build();
    }

    @Override
    public Request getRequest(String link) throws Exception {
        return null;
    }

    @Override
    public Item getItem(String link) throws Exception {
        return null;
    }

    @Override
    public List<Item> items() throws Exception {
        return Collections.emptyList();
    }

    @Override
    public Item latestItem() throws Exception{
        params.setEpisodes(Arrays.asList(0));
        return items().get(0);
    }

    @Override
    public Map getInfo() throws Exception{
        //读取插件属性文件plugin.properties
        InputStream is = this.getClass().getClassLoader().getResourceAsStream("plugin.properties");
        Properties properties = new Properties();
        properties.load(is);

        //封装信息
        Map map = new HashMap();
        map.put("版本",properties.getProperty("version"));
        map.put("名称",properties.getProperty("name"));
        map.put("更新时间",properties.getProperty("update"));
        map.put("uuid",properties.getProperty("uuid"));
        map.put("domainNames",properties.getProperty("name"));

        return map;
    }

    @Override
    public List<Setting> settings() throws Exception{
        return Collections.emptyList();
    }

    @Override
    public ExtendList getExtensions() throws Exception{
        return new ExtendList(Collections.emptyList(),Collections.emptyList());
    }
}