# xc-service-manage-cms
## 学成在线CMS管理微服务

1. 为什么需要对页面进行管理？
> 本项目对cms的定位是对页面进行管理，对那些由于经营需要（首页的广告、门户页面版式）而需要快速上线的功能页面进行管理。
2. 如何进行页面管理？
> 通过人工开发来编写HTML页面可以实现，如采用CMS系统就可以实现由CMS自动化对页面进行更新，采用静态化技术自动生成HTML页面，快速上线。
3. 如何对页面进行静态化？
> 模版引擎（页面模版+数据=输出HTML页面）
4. 静态化的HTML页面存放在哪里？
> 生成的静态化页面，由CMS程序自动发布到服务器（门户服务器、其他）中，实现页面的快速上线。

## 页面静态化及页面发布流程图：
1. 编辑页面模版
2. 确定页面数据
3. 对页面进行静态化
4. 将静态化生成的HTML页面存放文件系统中
5. 将存放在文件系统的的HTML发布到服务器
```java
                                                                deploy    +---------+
                                                       +----------------> | server1 |
                                                       |                  +---------+
+-------------------+     +----------------+  save   +--------+  deploy   |         |
| pageTemplate+data | --> | page staticize | ------> | GridFS | --------> | server2 |
+-------------------+     +----------------+         +--------+           +---------+
                                                       |        deploy    |         |
                                                       +----------------> | server3 |
                                                                          +---------+
```

### 流程图生成代码：
```shell

echo '[pageTemplate+data]->[page staticize]
[page staticize]-- save -->[GridFS]
[GridFS]-- deploy -->[server1]{origin: GridFS; offset: 2, -1;}
[GridFS]-- deploy -->[server2]{origin: GridFS; offset: 2, 0;}
[GridFS]-- deploy -->[server3]{origin: GridFS; offset: 2, 1;}
' | graph-easy

```
## 页面静态化流程：
1. 静态化程序首先读取页面获取dataUrl（获取页面数据模型的URL）

2. 静态化程序远程请求dataUrl得到页面数据模型
3. 获取页面模版
4. 执行页面静态化

```java
                                            +----------------------+
                                            |         html         |
                                            +----------------------+
                                              ^
                                              | 4.page staticize
                                              |
+-------------------+  1.get page DataUrl   +----------------------+  2.http invoke DataUrl get dataModel   +---------+
| cms_page(MongoDB) | <-------------------- | pageStaticizeProgram | -------------------------------------> | DataUrl |
+-------------------+                       +----------------------+                                        +---------+
                                              |
                                              | 3.get page template
                                              v
                                            +----------------------+
                                            |        GridFS        |
                                            +----------------------+
```

### 流程图生成代码：
```shell
echo '[pageStaticizeProgram]-- 1.get page DataUrl -->[cms_page(MongoDB)]
[pageStaticizeProgram]-- 2.http invoke DataUrl get dataModel -->[DataUrl]
[pageStaticizeProgram]-- 3.get page template -->[GridFS]
[pageStaticizeProgram]-- 4.page staticize  -->[html]
' | graph-easy
```

## 页面发布流程：

业务流程：

1. 管理员进入管理界面点击``页面发布``，前端请求CMS页面发布接口。

2. CMS页面发布接口执行页面静态化，并将静态化后的HTML页面存储至GridFS中。

3. 静态化成功后，向消息队列发送页面发布的消息。

   （页面发布的最终目标是将页面发布到服务器），通过消息队列将页面发布的消息发送给各个服务器。

4. 消息队列负责将消息发送给各个服务器（比如门户网站服务器）上都部署的 CMS Client（CMS客户端程序）

     在每个服务器上部署 CMS Client（CMS客户端），客户端接收消息队列的通知

5. 每个接收到页面发布消息的CMS客户端从GridFS获取HTML页面文件，并将HTML文件存储在本地服务器。

         ```java
            +---------------------+  save html
       +--> |   page staticize    | --------------------------------------------------------------------------------------------+
       |    +---------------------+                                                                                             |
       |                                          routingKey(PC portal siteID)          +------------------------+              |
       |                             +------------------------------------------------> |    PC portal Server    | -+           |
       |                             |                                                  +------------------------+  |           v
     +-----+  routingKey(siteID)   +------------+  routingKey(lenrning center siteID)   |                        |  |         +--------+
     | CMS | --------------------> |     MQ     | ------------------------------------> | lenrning center Server |  +-------> | GridFS |
     +-----+                       +------------+                                       +------------------------+            +--------+
                                     |            routingKey(other portal siteID)       |                        |  getHtml     ^
                                     +------------------------------------------------> |  other portal Server   | -------------+
                                                                                        +------------------------+
         ```

     #### 技术方案说明：

     1. 平台包括多个站点，页面归属不同的站点

     2. 发布一个页面应该将该页面发布到所属站点的服务器上

     3. 每个站点服务器部署CMS Client程序，并与交换机绑定，绑定时指定站点ID为routingKey

        指定站点ID为routingkey就可以实现CMS Client只会接收所属站点的页面发布消息。

     

     流程图代码：

     ```shell
     echo '[CMS]->[page staticize]{origin:CMS; offset: 1,-2;}
     [page staticize]-- save html -->[GridFS]{offset: 7,1;}
     [CMS]-- routingKey(siteID) -->[MQ]
     [MQ]-- routingKey(PC portal siteID) -->[PC portal Server]{origin: MQ; offset: 2, -1;}-- getHtml -->[GridFS]{origin: MQ; offset: 4, 0;}
     [MQ]-- routingKey(lenrning center siteID) -->[lenrning center Server]{origin: MQ; offset: 2, 0;}-- getHtml -->[GridFS]{origin: MQ; offset: 4, 0;}
     [MQ]-- routingKey(other portal siteID) -->[other portal Server]{origin: MQ; offset: 2, 1;}-- getHtml -->[GridFS]{origin: MQ; offset: 4, 0;}
     ' | graph-easy
     ```

     

     ​    

## typora页面宽度调整

ascii流程图如果显示不正常，可以调整typora页面宽度

首先打开偏好设置 -> 打开主题文件夹 -> 新建 night.user.css 文件，填入如下内容。

```css
/* 调整视图正文宽度 */
#write{
    max-width: 90%;
}

/* 调整源码正文宽度 */
#typora-source .CodeMirror-lines {
    max-width: 90%;
}

/* 调整输出 PDF 文件宽度 */
@media print {
    #write{
        max-width: 95%;
    }
    @page {
        size: A3;
    }
}

/* 调整正文字体,字体需单独下载 */
body {
    font-family: IBM Plex Sans;
}
```

