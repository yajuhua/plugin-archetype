package ${package}.${artifactId}.main;

import io.github.yajuhua.download.manager.Request;
import io.github.yajuhua.podcast2API.Channel;
import io.github.yajuhua.podcast2API.Item;
import io.github.yajuhua.podcast2API.Podcast2;
import io.github.yajuhua.podcast2API.extension.build.ExtendList;
import io.github.yajuhua.podcast2API.setting.Setting;

import java.io.InputStream;
import java.util.*;

public class Main implements Podcast2 {
    @Override
    public List<Item> items() throws Exception {
        return Collections.emptyList();
    }

    public Item latestItem() throws Exception{
        return items().get(0);
    }

    @Override
    public Channel channel() throws Exception {
        return null;
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
    public Map getInfo() throws Exception{
        //读取插件属性文件plugin.properties
        InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream("plugin.properties");
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

    public List<Setting> settings() throws Exception{
        return Collections.emptyList();
    }

    public ExtendList getExtensions() throws Exception{
        return new ExtendList(Collections.emptyList(),Collections.emptyList());
    }
}
