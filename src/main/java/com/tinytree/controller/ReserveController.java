package com.tinytree.controller;

import com.tinytree.entity.Doctor;
import com.tinytree.entity.ReserveInfo;
import com.tinytree.entity.ReserveSetting;
import com.tinytree.service.DoctorService;
import com.tinytree.service.ReserveInfoService;
import com.tinytree.service.ReserveSettingService;
import com.tinytree.service.UserService;
import com.tinytree.util.DateUtils;
import com.tinytree.util.GlobalUtil;
import com.tinytree.bean.PagerEx;
import com.tinytree.bean.PagerParam;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Description:预约挂号接口
 * @ClassName: ReserveController
 * @Author：tangyang
 * @Date 2016-1-13
 * (变更历史)
 * 如：eric	2015/01/08 修改了删除功
 */
@Controller
public class ReserveController extends BaseController {
    private static final Logger logger = LoggerFactory.getLogger(ReserveController.class);

    @Autowired
    private ReserveInfoService reserveInfoService;

    @Autowired
    private ReserveSettingService reserveSettingService;

    @Autowired
    private DoctorService doctorService;

    @Autowired
    private UserService userService;

    /**
     * 预约设置
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/reserve/{accountId}/registerSetting", method = {RequestMethod.POST}, produces = "text/plain;charset=UTF-8")
    public String registerSetting(@PathVariable("accountId") String accountId, HttpServletRequest request) {
        String weekDays = request.getParameter("weekDays");
        String startTime = request.getParameter("startTime");
        String endTime = request.getParameter("endTime");
        String repeat = request.getParameter("repeat");
        String count = request.getParameter("count");

        String status = "";
        JSONObject jsonObject = new JSONObject();

        try {
            do {
                if (StringUtils.isAnyBlank(weekDays, startTime, endTime, repeat, count)) {
                    logger.warn("[weekDays,startTime,endTime,repeat,count]");
                    logger.warn("$:" + weekDays);
                    logger.warn("$:" + startTime);
                    logger.warn("$:" + endTime);
                    logger.warn("$:" + repeat);
                    logger.warn("$:" + count);
                    status = GlobalUtil.PARAM_ERROR;
                    break;
                }

                int iRepeat = Integer.parseInt(repeat);
                if (iRepeat != 0 && iRepeat != 1) {
                    logger.warn("param error iRepeat:" + iRepeat);
                    status = GlobalUtil.PARAM_ERROR;
                    break;
                }

                int iCount = Integer.parseInt(count);

                int iWeekDays = Integer.parseInt(weekDays);
                List<Integer> weekDayList = DateUtils.parseWeekDays(iWeekDays);
                if (weekDayList == null || weekDayList.isEmpty()) {
                    logger.warn("param error weekDays:" + iWeekDays);
                    status = GlobalUtil.PARAM_ERROR;
                    break;
                }

                Date dStartTime = DateUtils.format2Date(startTime, GlobalUtil.TIME_PATTERN);
                Date dEndTime = DateUtils.format2Date(endTime, GlobalUtil.TIME_PATTERN);
                Doctor doctor = doctorService.getByAccountId(accountId);
                if (doctor == null) {
                    status = GlobalUtil.DOCTOR_ERROR;
                    break;
                }
                String doctorId = doctor.getId();

                List<ReserveSetting> entitys = new ArrayList<ReserveSetting>();
                for (Integer weekDay : weekDayList) {
                    ReserveSetting reserveSetting = new ReserveSetting();
                    reserveSetting.setCount(iCount);
                    reserveSetting.setDoctorId(doctorId);
                    reserveSetting.setStartTime(dStartTime);
                    reserveSetting.setEndTime(dEndTime);
                    reserveSetting.setRepeatStatus(iRepeat);
                    reserveSetting.setWeekDay(weekDay);
                    reserveSetting.setStatus(0);
                    entitys.add(reserveSetting);
                }
                reserveSettingService.saveAndUpdate(entitys);
                status = GlobalUtil.SUCCESS;

            } while (false);

        } catch (Exception e) {
            status = GlobalUtil.SYS_ERROR;
            e.printStackTrace();
            logger.error(e.getMessage());
        }

        jsonObject.put("status", status);
        return json(jsonObject);
    }

    /**
     * 医生的预约列表
     *
     * @param physicianId
     * @param request
     * @return
     */
    @RequestMapping(value = "/reserve/{accountId}/reserveList", method = {RequestMethod.GET}, produces = "text/plain;charset=UTF-8")
    public String reserveList(@PathVariable String accountId, HttpServletRequest request) {
        String startPage = request.getParameter("startPage");
        String pageSize = request.getParameter("pageSize");
        String startDate = request.getParameter("startDate");
        String datePatten = request.getParameter("datePatten");

        String status = GlobalUtil.SUCCESS;
        JSONObject jsonObject = new JSONObject();

        try {
            do {
                //暂时删除了,startDate,endDate
                if (StringUtils.isAnyBlank(startPage, pageSize, accountId)) {
                    logger.warn("[startPage,pageSize,startDate,endDate]");
                    logger.warn("$:" + startPage);
                    logger.warn("$:" + pageSize);
                    logger.warn("$:" + startDate);
                    logger.warn("$:" + accountId);
                    status = GlobalUtil.PARAM_ERROR;
                    break;
                }

                if (StringUtils.isBlank(datePatten)) {
                    datePatten = GlobalUtil.DATETIME_PATTERN;
                }

                int iStartPage = Integer.parseInt(startPage);
                int iPageSize = Integer.parseInt(pageSize);

                PagerEx pager = new PagerEx();
                pager.setPageNumber(iStartPage);
                pager.setPageSize(iPageSize);

                Doctor doctor = doctorService.getByAccountId(accountId);
                if (doctor == null) {
                    status = GlobalUtil.DOCTOR_ERROR;
                    break;
                }
                String doctorId = doctor.getId();
                List<Map<String, String>> result = reserveInfoService.findReserveList(doctorId, startPage, pageSize, startDate, null);
                JSONArray reserveList = JSONArray.fromObject(result);
                jsonObject.put("reserveList", reserveList);
            } while (false);

        } catch (Exception e) {
            e.printStackTrace();
            status = GlobalUtil.SYS_ERROR;
            logger.error(e.getMessage());
        }
        jsonObject.put("status", status);
        return json(jsonObject);
    }

    /**
     * 医生的预约列表bydate
     *
     * @param physicianId
     * @param request
     * @return
     */
    @RequestMapping(value = "/reserve/{accountId}/reserveListByDate", method = {RequestMethod.GET}, produces = "text/plain;charset=UTF-8")
    public String reserveListByDate(@PathVariable String accountId, HttpServletRequest request) {
        String startPage = request.getParameter("startPage");
        String pageSize = request.getParameter("pageSize");
        String startDate = request.getParameter("startDate");
        String datePatten = request.getParameter("datePatten");

        String status = GlobalUtil.SUCCESS;
        JSONObject jsonObject = new JSONObject();

        try {
            do {
                //暂时删除了,startDate,endDate
                if (StringUtils.isAnyBlank(startPage, pageSize, accountId)) {
                    logger.warn("[startPage,pageSize,startDate,endDate]");
                    logger.warn("$:" + startPage);
                    logger.warn("$:" + pageSize);
                    logger.warn("$:" + startDate);
                    logger.warn("$:" + accountId);
                    status = GlobalUtil.PARAM_ERROR;
                    break;
                }

                if (StringUtils.isBlank(datePatten)) {
                    datePatten = GlobalUtil.DATE_PATTERN;
                }

                Doctor doctor = doctorService.getByAccountId(accountId);
                if (doctor == null) {
                    status = GlobalUtil.DOCTOR_ERROR;
                    break;
                }
                String doctorId = doctor.getId();
                List<Map<String, String>> result = reserveInfoService.findReserveListByDate(doctorId, startPage, pageSize, startDate);
                JSONArray reserveList = JSONArray.fromObject(result);
                jsonObject.put("reserveList", reserveList);
            } while (false);
        } catch (Exception e) {
            status = GlobalUtil.SYS_ERROR;
            logger.error(e.getMessage());
        }
        jsonObject.put("status", status);
        return json(jsonObject);
    }

    /**
     * 患者的预约列表
     *
     * @param userId
     * @param request
     * @return
     */
    @RequestMapping(value = "/reserve/{accountId}/userReserve", method = {RequestMethod.GET}, produces = "text/plain;charset=UTF-8")
    public String reserveListByUser(@PathVariable String accountId, HttpServletRequest request) {
        String startPage = request.getParameter("startPage");
        String pageSize = request.getParameter("pageSize");
        String startDate = request.getParameter("startDate");
        String endDate = request.getParameter("endDate");
        String datePatten = request.getParameter("datePatten");

        String status = GlobalUtil.SUCCESS;
        JSONObject jsonObject = new JSONObject();

        try {
            do {
                if (StringUtils.isAnyBlank(startPage, pageSize, accountId)) {
                    logger.warn("[startPage,pageSize,startDate,endDate,accountId]");
                    logger.warn("$:" + startPage);
                    logger.warn("$:" + pageSize);
                    logger.warn("$:" + startDate);
                    logger.warn("$:" + endDate);
                    logger.warn("$:" + accountId);
                    status = GlobalUtil.PARAM_ERROR;
                    break;
                }

                if (StringUtils.isBlank(datePatten)) {
                    datePatten = GlobalUtil.DATE_PATTERN;
                }
                Date dStartDate = null;
                if (StringUtils.isNotBlank(startDate)) {
                    dStartDate = DateUtils.format2Date(startDate, datePatten);
                }
                Date dEndDate = null;
                if (StringUtils.isNotBlank(endDate)) {
                    dEndDate = DateUtils.format2Date(endDate, datePatten);
                }

                int iStartPage = Integer.parseInt(startPage);
                int iPageSize = Integer.parseInt(pageSize);

                PagerEx pager = new PagerEx();
                pager.setPageNumber(iStartPage);
                pager.setPageSize(iPageSize);

                List<Map<String, String>> result = reserveInfoService.findReserveListUser(accountId, startPage, pageSize, startDate, endDate);

                JSONArray json = JSONArray.fromObject(result);
                jsonObject.put("reserveList", json);
            } while (false);

        } catch (Exception e) {
            status = GlobalUtil.SYS_ERROR;
            logger.error(e.getMessage());
        }

        jsonObject.put("status", status);
        return json(jsonObject);
    }

    /**
     * 预约详情
     *
     * @param reserveId
     * @param request
     * @return
     */
    @RequestMapping(value = "/reserve/reserve/{reserveId}", method = {RequestMethod.GET}, produces = "text/plain;charset=UTF-8")
    public String reserveDetail(@PathVariable String reserveId, HttpServletRequest request) {
        String status = GlobalUtil.SUCCESS;
        JSONObject jsonObject = new JSONObject();

        try {
            do {
                if (StringUtils.isBlank(reserveId)) {
                    logger.warn("param is error reserveId is null");
                    status = GlobalUtil.PARAM_ERROR;
                    break;
                }

                ReserveInfo reserveInfo = reserveInfoService.get(reserveId);
                if (reserveInfo == null) {
                    logger.warn("reserveInfo is null,reserveId:" + reserveId);
                    break;
                }
                Map<String, Object> result = reserveInfoService.findReserveDetail(reserveId);
                Date birthdayDate = (Date) result.get("birthday");
                String birthday = DateUtils.format2String(birthdayDate, GlobalUtil.DATE_PATTERN);
                result.remove("birthday");
                result.put("birthday", birthday);
                String startTime = (String) result.get("startTime");
                String endTime = (String) result.get("endTime");
                String todayDate = startTime.substring(0, 9);
                startTime = startTime.substring(9, 17);
                endTime = endTime.substring(9, 17);
                result.put("todayDate", todayDate);
                result.put("startTime", startTime);
                result.put("endTime", endTime);
                result.put("age", DateUtils.getAge(birthday));
                String shortDesc = (String) result.get("shortDescribe");
                //result.put("shortDescribe", shortDesc);
                if (StringUtils.isBlank(shortDesc)) {
                    result.put("shortDescribe", "");
                }
                JSONObject json = JSONObject.fromObject(result);
                jsonObject.put("reserveDetail", json);

            } while (false);
        } catch (Exception e) {
            status = GlobalUtil.SYS_ERROR;
            logger.error(e.getMessage());
        }

        jsonObject.put("status", status);
        return json(jsonObject);
    }

    /**
     * 医师接受预约的时间
     *
     * @param physicianId
     * @param request
     * @return
     */
    @RequestMapping(value = "/reserve/{accountId}/physicianTime", method = {RequestMethod.GET}, produces = "text/plain;charset=UTF-8")
    public String physicianTime(@PathVariable String accountId, HttpServletRequest request) {
        String status = GlobalUtil.SUCCESS;
        JSONObject jsonObject = new JSONObject();

        try {
            do {
                if (StringUtils.isBlank(accountId)) {
                    logger.warn("param is error physicianId is null");
                    status = GlobalUtil.PARAM_ERROR;
                    break;
                }

                PagerEx pager = new PagerEx();

                List<PagerParam> params = new ArrayList<PagerParam>();

                PagerParam doctorIdParam = new PagerParam();
                Doctor doctor = doctorService.getByAccountId(accountId);
                if (doctor == null) {
                    status = GlobalUtil.DOCTOR_ERROR;
                    break;
                }
                String doctorId = doctor.getId();
                doctorIdParam.setParam(PagerParam.ConditionType.equal, "doctor_id", doctorId);
                params.add(doctorIdParam);
                PagerParam statusParam = new PagerParam();
                statusParam.setParam(PagerParam.ConditionType.equal, "status", 0);
                params.add(statusParam);

                pager.setParams(params);

                pager.setOrderBy("week_day");
                pager = reserveSettingService.findByPager(pager);

                List<ReserveSetting> datas = pager.getList();
                if (datas == null || datas.isEmpty()) {
                    logger.warn("datas is empty,physicianId:" + accountId);
                    break;
                }
                JSONArray jsonArray = new JSONArray();
                for (ReserveSetting reserveSetting : datas) {
                    int weekDay = reserveSetting.getWeekDay();
                    String newDate = DateUtils.format2String(DateUtils.getDateByWeekDay(weekDay), GlobalUtil.DATE_PATTERN);
                    String startTime = DateUtils.format2String(reserveSetting.getStartTime(), GlobalUtil.TIME_PATTERN);
                    String endTime = DateUtils.format2String(reserveSetting.getEndTime(), GlobalUtil.TIME_PATTERN);
                    JSONObject object = new JSONObject();
                    int count = reserveSetting.getCount();
                    String reserveId = reserveSetting.getId();
                    int flag = reserveSetting.getRepeatStatus();
                    String repeatStatus = null;
                    if (flag == 1) {
                        repeatStatus = "重复";
                    }
                    if (flag == 0) {
                        repeatStatus = " ";
                    }
                    object.put("startDate", startTime);
                    object.put("endDate", endTime);
                    object.put("todayDate", newDate);
                    object.put("weekDay", DateUtils.parseWeekDayToName(weekDay));
                    object.put("repeatStatus", repeatStatus);
                    object.put("count", count);
                    object.put("reserveId", reserveId);
                    jsonArray.add(object);
                }
                jsonObject.put("timeList", jsonArray);
            } while (false);
        } catch (Exception e) {
            status = GlobalUtil.SYS_ERROR;
            logger.error(e.getMessage());
        }

        jsonObject.put("status", status);
        return json(jsonObject);
    }

    /**
     * 用户预约
     *
     * @param userId
     * @param request
     * @return
     */
    @RequestMapping(value = "/reserve/{accountId}/reserve", method = {RequestMethod.POST}, produces = "text/plain;charset=UTF-8")
    public String reserve(@PathVariable String accountId, HttpServletRequest request) {
        String reserveId = request.getParameter("reserveId");
        String startTime = request.getParameter("startTime");
        String endTime = request.getParameter("endTime");
        String desc = request.getParameter("desc");

        String status = GlobalUtil.SUCCESS;
        JSONObject jsonObject = new JSONObject();

        try {
            do {
                if (StringUtils.isAnyBlank(reserveId, startTime, endTime, accountId)) {
                    logger.warn("[reserveId,startTime,endTime]");
                    logger.warn("$:" + reserveId);
                    logger.warn("$:" + startTime);
                    logger.warn("$:" + endTime);
                    logger.warn("$:" + accountId);
                    status = GlobalUtil.PARAM_ERROR;
                    break;
                }

                Date dStartDate = DateUtils.format2Date(startTime, GlobalUtil.DATETIME_PATTERN);
                Date dEndDate = DateUtils.format2Date(endTime, GlobalUtil.DATETIME_PATTERN);

                ReserveSetting reserveSetting = reserveSettingService.get(reserveId);
                if (reserveSetting == null || reserveSetting.getStatus() == 1) {
                    status = GlobalUtil.RESERVE_OVER;
                    break;
                }

                int s1 = DateUtils.getWeekDay(dStartDate);
                int s2 = DateUtils.getWeekDay(dEndDate);
                if (s1 != reserveSetting.getWeekDay() || s2 != reserveSetting.getWeekDay()) {
                    status = GlobalUtil.RESERVE_TIME_ERROR;
                    break;
                }

                int count = reserveSetting.getCount();

                int usedCount = reserveInfoService.getReserveInfoCount(reserveSetting.getDoctorId(), dStartDate);
                if (count <= usedCount) {
                    status = GlobalUtil.RESERVE_FULL;
                    break;
                }

                ReserveInfo reserveInfo = new ReserveInfo();
                reserveInfo.setDesc(desc);
                reserveInfo.setDoctorId(reserveSetting.getDoctorId());
                reserveInfo.setStartTime(dStartDate);
                reserveInfo.setEndTime(dEndDate);
                reserveInfo.setUserId(accountId);
                reserveInfoService.save(reserveInfo);
                JSONObject json = JSONObject.fromObject(reserveInfo);
                jsonObject.put("reserveInfo", json);
                status = GlobalUtil.SUCCESS;
            } while (false);


        } catch (Exception e) {
            status = GlobalUtil.SYS_ERROR;
            logger.error(e.getMessage());
        }

        jsonObject.put("status", status);
        return json(jsonObject);
    }

}
