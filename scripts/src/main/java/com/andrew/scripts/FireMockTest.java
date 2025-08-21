package com.andrew.scripts;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;

/**
 * @author tongwenjin
 * @since 2025-8-18
 */
public class FireMockTest {

    public static void main(String[] args) {

        mockCameraData();

        if (1==1){
            return;
        }

        long[] ids = new long[]{
                205051589981536L,
                205051731508320L,
                205051800383840L,
                205051800394336L,
                205051800401760L,
                205051800409696L,
                205051800418656L,
                205051800428128L,
                205051800436320L,
                205051800441952L,
                205051800447840L,
                205051800453728L,
                205051800459360L,
                205051800465248L,
                205051800471136L,
                205051800478816L,
                205051800485984L,
                205051800491616L,
                205051800497760L,
                205051800503136L,
                205051800508000L,
                205051800512608L,
                205051800517216L,
                205051800522336L,
                205051800528224L,
                205051800534368L,
                205051800541536L,
                205051800546912L,
                205051800553312L,
                205051800557664L,
                205051800562016L,
                205051800566368L,
                205051800572256L,
                205051800578400L,
                205051800583008L,
                205051800588640L,
                205051800594016L,
                205051800597856L,
                205051800602976L,
                205051800608352L,
                205051800611936L,
                205051800616288L,
                205051800620128L,
                205051800623968L,
                205051800628320L,
                205051800632672L,
                205051800637024L,
                205051800642400L,
                205051800647520L,
                205051800651360L,
                205051800655200L,
                205051800659296L,
                205051800663136L,
                205051800666720L,
                205051800670304L,
                205051800673632L,
                205051800676960L,
                205051800681056L,
                205051800684896L,
                205051800688736L,
                205051800692832L,
                205051800696416L,
                205051800701536L,
                205051800706144L,
                205051800709984L,
                205051800715104L,
                205051800719200L,
                205051800725088L,
                205051800730720L,
                205051800736352L,
                205051800741728L,
                205051800748640L,
                205051800754528L,
                205051800770400L,
                205051800782688L,
                205051800791904L,
                205051800800096L,
                205051800807776L,
                205051800815456L,
                205051800822112L,
                205051800827744L,
                205051800833632L,
                205051800839008L,
                205051800844384L,
                205051800851040L,
                205051800858464L,
                205051800864352L,
                205051800869728L,
                205051800877152L,
                205051800886624L,
                205051800896864L,
                205051800904032L,
                205051800912224L,
                205051800920672L,
                205051800928352L,
                205051800936032L,
                205051800945760L,
                205051800956512L,
                205051800962912L,
                205051800972640L,
                205051800979552L,
                205051800986976L,
                205051800994912L,
                205051801001824L,
                205051801008736L,
                205051801020512L,
                205051801029728L,
                205051801038432L,
                205051801054560L,
                205051801064032L,
                205051801072992L,
                205051801083232L,
                205051801093472L,
                205051801107296L,
                205051801116512L,
                205051801126496L,
                205051801136736L,
                205051801146208L,
                205051801170784L,
                205051801181280L,
                205051801193824L,
                205051801202016L,
                205051801214304L,
                205051801228896L,
                205051801240416L,
                205051801253472L,
                205051801261152L,
                205051801269856L,
                205051801276768L,
                205051801282912L,
                205051801287776L,
                205051801292384L,
                205051801297248L,
                205051801301856L,
                205051801306464L,
                205051801315168L,
                205051801322336L,
                205051801327968L,
                205051801333344L,
                205051801343584L,
                205051801348704L,
                205051801356128L,
                205051801363808L,
                205051801369184L,
                205051801374816L,
                205051801381984L,
                205051801390944L,
                205051801401696L,
                205051801408608L,
                205051801415264L,
                205051801423968L,
                205051801433952L,
                205051801443168L,
                205051801453152L,
                205051801464160L,
                205051801472864L,
                205051801479776L,
                205051801486688L,
                205051801493600L,
                205051801500256L,
                205051801507936L,
                205051801517664L,
                205051801525344L,
                205051801532000L,
                205051801538912L,
                205051801548640L,
                205051801555040L,
                205051801563744L,
                205051801570912L,
                205051801578848L,
                205051801589600L,
                205051801597024L,
                205051801609824L,
                205051801620320L,
                205051801637728L,
                205051801649504L,
                205051801659488L,
                205051801666912L,
                205051801674080L,
                205051801679968L,
                205051801686368L,
                205051801696352L,
                205051801702752L,
                205051801709664L,
                205051801714272L,
                205051801720672L,
                205051801726560L,
                205051801732448L,
                205051801740384L,
                205051801749600L,
                205051801758048L,
                205051801767520L,
                205051801780576L,
                205051801792096L,
                205051801805408L,
                205051801817440L,
                205051801829728L,
                205051801838944L,
                205051801845600L,
                205051801852512L
        };

        try {
            Thread.sleep(10000L);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        for (long id : ids) {
            String post = HttpUtil.post("http://192.168.2.65/api/test/mock/alarm",
                    "{\"facilityType\":\"\",\"facilityId\":\""+id+"\",\"alarmEvent\":\"1\",\"auth\":\"yepanpan\"}");

        }
    }

    public static void mockData() {
        HttpRequest req = HttpUtil.createPost("http://192.168.2.65/api/facility/facility");
        for (int i = 0; i < 50000; i++) {
            req.header("Authorization","Bearer eyJhbGciOiJIUzUxMiJ9.eyJsb2dpbl91c2VyX2tleSI6IjE4Mzk5YWQ1LTlmNDMtNDE0Zi05YzcyLWNkMTk0ZmQyOGFkMCJ9.k2jAWeQgodatCwF809veR4xVsfEmWEut8DCkGhcxL8I--6nTdtXuvWukD4IYVIsWlAVK5liJlCLqDdM8mpLOBw");
            req.body("{\"id\":null,\"deptId\":196374575865856,\"typeId\":126,\"sysTypeId\":null,\"modelId\":null,\"facilityCode\":\"117机-"+i+"\",\"extCode\":null,\"position\":\"17机-1\",\"positionType\":\"1\",\"longitude\":null,\"latitude\":null,\"buildingId\":196228596064832,\"floorId\":196228596072512,\"roomId\":196228596075584,\"pointX\":null,\"pointY\":null,\"pointZ\":null,\"propLoop\":null,\"propChannel\":null,\"propIp\":null,\"propPort\":null,\"propUsername\":null,\"propPassword\":null,\"propRtsp\":null,\"propFlv\":null,\"propNetId\":null,\"propParentId\":null,\"propControllerId\":null,\"propNetMode\":\"0\",\"parentFacilityId\":null,\"runStatus\":\"0\",\"useStatus\":\"0\",\"scrapStatus\":\"0\",\"scrapDate\":36,\"cameraIds\":null,\"remark\":null,\"createBy\":null,\"createTime\":null,\"updateBy\":null,\"updateTime\":null,\"belong\":\"A区 / 校史馆1 / 1层 / 公共区域\"}");
            HttpResponse response = req.execute();
            System.out.println(response.body());
        }
    }

    public static void mockCameraData() {
        HttpRequest req = HttpUtil.createPost("http://192.168.2.65/api/facility/facility");
        for (int i = 0; i < 1000; i++) {
            req.header("Authorization","Bearer eyJhbGciOiJIUzUxMiJ9.eyJsb2dpbl91c2VyX2tleSI6IjE4Mzk5YWQ1LTlmNDMtNDE0Zi05YzcyLWNkMTk0ZmQyOGFkMCJ9.k2jAWeQgodatCwF809veR4xVsfEmWEut8DCkGhcxL8I--6nTdtXuvWukD4IYVIsWlAVK5liJlCLqDdM8mpLOBw");
            req.body("{\"id\":null,\"deptId\":196374575865856,\"typeId\":84,\"sysTypeId\":null,\"modelId\":null,\"facilityCode\":\"test_camare"+i+"\",\"extCode\":null,\"position\":\"test_camare\",\"positionType\":\"1\",\"longitude\":null,\"latitude\":null,\"buildingId\":196228596064832,\"floorId\":196228596072512,\"roomId\":196228596075584,\"pointX\":null,\"pointY\":null,\"pointZ\":null,\"propLoop\":null,\"propChannel\":null,\"propIp\":null,\"propPort\":null,\"propUsername\":null,\"propPassword\":null,\"propRtsp\":null,\"propFlv\":null,\"propNetId\":null,\"propParentId\":null,\"propControllerId\":null,\"propNetMode\":\"0\",\"parentFacilityId\":null,\"runStatus\":\"0\",\"useStatus\":\"0\",\"scrapStatus\":\"0\",\"scrapDate\":36,\"cameraIds\":null,\"remark\":null,\"createBy\":null,\"createTime\":null,\"updateBy\":null,\"updateTime\":null,\"belong\":\"A区 / 校史馆1 / 1层 / 公共区域\"}");
            HttpResponse response = req.execute();
            System.out.println(response.body());
        }
    }
}
