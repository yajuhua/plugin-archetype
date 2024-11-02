## Podcast2 插件开发脚手架

### 克隆仓库
```bash
git clone https://github.com/yajuhua/plugin-archetype.git
```
### 安装到本地maven仓库
```bash
cd plugin-archetype
mvn clean install
```
### 生成插件模板
```bash
mvn archetype:generate -DarchetypeGroupId=io.github.yajuhua -DarchetypeArtifactId=plugin-archetype -DarchetypeVersion=0.0.3 -DgroupId=io.github.yajuhua -DartifactId=metube
```