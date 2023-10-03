package be.vn.logic;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import be.vn.model.TblShow;
import be.vn.repository.TblShowRepository;
import be.vn.util.DateUtil;
import be.vn.util.LogUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * MiomioLogic class
 *
 * @author TuanDV <tuandv@hospital.vn>
 */
@Service
public class MiomioLogic {
    @Autowired
    private TblShowRepository tblShowRepository;

    public void updateLinks() {
        try {
            Document doc = Jsoup.connect("https://miomio.guru/").get();
            List<TblShow> lsTblShow = new ArrayList<>();
            for (Element element : doc.selectXpath("//div[contains(@class, 'scrol')]/div[@class='vc_column-inner ']/div[@class='wpb_wrapper']//div[contains(@class, 'post-title')]/p/a")) {
                String linkWeb = element.attr("href");
                Document subDoc = Jsoup.connect(element.attr("href")).get();

                String title = element.text().trim();
                String src = "https:" + subDoc.selectXpath("//div[@id='player-embed']/iframe").first().attr("src");
                String imageUrl = subDoc.selectXpath("//meta[@property='og:image']").first().attr("content");
                if (tblShowRepository.existsByTitle(title)) {
                    break;
                }
                TblShow tblShow = new TblShow();
                tblShow.setNationId(108);
                tblShow.setView(0);
                tblShow.setTitle(title);
                tblShow.setLinkVideo(src);
                String[] arr = title.trim().split("2023");
                tblShow.setDateLive(arr.length > 1 ? DateUtil.toDate("2023" + arr[arr.length - 1], "yyyy年MM月dd日") : DateUtil.getCurrentDateUTC());
                tblShow.setLinkImg(imageUrl);
                tblShow.setLinkWeb(linkWeb);
                lsTblShow.add(tblShow);
            }
            tblShowRepository.saveAll(lsTblShow);
        } catch (Exception ex) {
            LogUtil.error(ex);
        }

    }
}