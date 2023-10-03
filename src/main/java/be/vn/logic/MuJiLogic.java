package be.vn.logic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import be.vn.constant.CommonConstant;
import be.vn.model.TblNews;
import be.vn.repository.TblNewsRepository;
import be.vn.util.HttpClientUtil;
import be.vn.util.LogUtil;
import be.vn.util.StringUtil;

import java.util.*;

/**
 * MuJiLogic class
 *
 * @author TuanDV <tuandv@hospital.vn>
 */
@Service
public class MuJiLogic {
    @Autowired
    private TblNewsRepository tblNewsRepository;

    public String updateLink() {
        try {
            for (TblNews news : tblNewsRepository.getLsMuji()) {
                if (StringUtil.isEmpty(news.getMujiParam())) {
                    continue;
                }
                Set<String> links = new HashSet<>();
                links.add(news.getLinkM3u());
                links.addAll(Arrays.asList(news.getNote().split("\n")));
                String link = getLink(links);
                if (!StringUtil.isEmpty(link)) {
                    news.setLinkM3u(link);
                    tblNewsRepository.save(news);
                    LogUtil.warn(String.format("update id %s: %s", news.getIdNews(), link));
                }
            }
        } catch (Exception ex) {
            LogUtil.error(ex);
        }
        return CommonConstant.SUCCESS;
    }

    private String getLink(Set<String> links) {
        try {
            Map<Long, String> map = new TreeMap<>();
            for (String link : links) {
                long start = System.currentTimeMillis();
                String body = HttpClientUtil.sendGet(link);
                if (!link.endsWith("m3u8") || isNotLive(body)) {
                    continue;
                }
                long end = System.currentTimeMillis();
                long millis = (end - start);
                if (millis < 1001) {
                    return link;
                }
                map.put(millis, link);
            }
            if (map.size() > 0) {
                return map.get(map.keySet().stream().findFirst().get());
            }
        } catch (Exception ex) {
            LogUtil.error(ex);
        }
        return null;
    }

    private boolean isNotLive(String body) {
        return StringUtil.isEmpty(body) || body.contains("404 Not Found");
    }
}