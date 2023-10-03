package be.vn.logic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import be.vn.model.LinkView;
import be.vn.model.TblNews;
import be.vn.repository.LinkViewRepository;
import be.vn.repository.TblNewsRepository;
import be.vn.util.HttpClientUtil;
import be.vn.util.LogUtil;
import be.vn.util.StringUtil;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * VideoBSYLogic class
 *
 * @author TuanDV <tuandv@hospital.vn>
 */
@Service
public class VideoBSYLogic {
    @Autowired
    private TblNewsRepository tblNewsRepository;
    @Autowired
    private LinkViewRepository linkViewRepository;

    public void updateLink() {
        try {
            TblNews news = tblNewsRepository.findFirstByIdNews(108);

            List<LinkView> lsLinkView = new ArrayList<>();
            for (int i = 0; i < 3; i++) {
                String response = HttpClientUtil.sendGet("https://video.bsy.co.jp/");
                Pattern p = Pattern.compile("(?<=loadSource\\(').*?(?=')");
                Matcher m = p.matcher(response);
                if (!m.find()) {
                    return;
                }
                LinkView linkView = new LinkView();
                linkView.setNewsId(news.getIdNews());
                linkView.setLink(m.group());
                linkView.setStatus(1);
                linkView.setTime(0);
                linkView.setCount(0);
                linkView.setActive(1);
                linkView.setType(1);
                lsLinkView.add(linkView);
                Thread.sleep(100);
            }
            if (lsLinkView.size() > 0) {
                linkViewRepository.deleteAll(linkViewRepository.findByNewsIdAndType(news.getIdNews(), 1));
            }
            linkViewRepository.saveAll(lsLinkView);
            HttpClientUtil.sendGet("https://duytt.animehd.club/app_tv_rv/api/news/updateM3u");
        } catch (Exception ex) {
            LogUtil.error(ex);
        }
    }
}