package be.vn.logic;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import be.vn.constant.CommonConstant;
import be.vn.model.Schedule;
import be.vn.model.TblNews;
import be.vn.repository.ScheduleRepository;
import be.vn.repository.TblNewsRepository;
import be.vn.util.DateUtil;
import be.vn.util.LogUtil;
import be.vn.util.StringUtil;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * TVGuideLogic class
 *
 * @author TuanDV <tuandv@hospital.vn>
 */
@Service
public class TVGuideLogic {
    private static CloseableHttpClient client = new DefaultHttpClient();
    @Autowired
    private ScheduleRepository scheduleRepository;
    @Autowired
    private TblNewsRepository tblNewsRepository;

    public void updateSchedule() {
        try {
            scheduleRepository.deleteAll();
            for (TblNews news : tblNewsRepository.getTblNewsSchedule()) {
                if (StringUtil.isEmpty(news.getScheduleKey())) {
                    continue;
                }

                updateScheduleItem(news.getIdNews(), news.getScheduleKey() + DateUtil.toString(DateUtil.getCurrentDate(), DateUtil.FORMAT_YEAR_MONTH_DAY));
                updateScheduleItem(news.getIdNews(), news.getScheduleKey() + DateUtil.toString(DateUtil.getNextDay(DateUtil.getCurrentDate()), DateUtil.FORMAT_YEAR_MONTH_DAY));
//                updateScheduleItem(news.getIdNews(), news.getScheduleKey() + DateUtil.toString(DateUtil.getNextDay(DateUtil.getCurrentDate(), 2), DateUtil.FORMAT_YEAR_MONTH_DAY));
            }
        } catch (Exception ex) {
            LogUtil.error(ex);
        }

    }

    public String updateScheduleItem(Integer newsId, String scheduleKey) {
        try {
            HttpGet request = new HttpGet("https://tvguide.myjcom.jp/api/getEpgInfo/?channels=" + scheduleKey);

            // add request header
            request.addHeader(HttpHeaders.USER_AGENT, HttpHeaders.USER_AGENT);
            request.addHeader(HttpHeaders.CONTENT_TYPE, "application/json");
            HttpResponse response = client.execute(request);
            String body = IOUtils.toString(response.getEntity().getContent(), StandardCharsets.UTF_8);
            JSONArray data = new JSONObject(body).getJSONArray(scheduleKey);

            List<Schedule> schedules = new ArrayList<>();
            for (int i = 0; i < data.length(); i++) {
                JSONObject object = data.getJSONObject(i);
                Date programStartDate = DateUtil.getPreHour(DateUtil.toDate(String.valueOf(object.getLong("programStart")), DateUtil.FORMAT_YEAR_MONTH_DAY_SEC), 9);
                if (scheduleRepository.existsByNewsIdAndStartTime(newsId, programStartDate)) {
                    continue;
                }

                Schedule schedule = new Schedule();
                schedule.setTitle(object.getString("title"));
                schedule.setContent(object.getString("commentary"));
                schedule.setImageUrl(object.isNull("imgPath") ? null : "https://tvguide.myjcom.jp" + object.getString("imgPath"));
                schedule.setStartTime(programStartDate);
                schedule.setEndTime(DateUtil.getPreHour(DateUtil.toDate(String.valueOf(object.getLong("programEnd")), DateUtil.FORMAT_YEAR_MONTH_DAY_SEC), 9));
                schedule.setNewsId(newsId);
                schedules.add(schedule);
            }
            scheduleRepository.saveAll(schedules);
        } catch (Exception ex) {
            LogUtil.error(ex);
        }
        return CommonConstant.BLANK;
    }
}