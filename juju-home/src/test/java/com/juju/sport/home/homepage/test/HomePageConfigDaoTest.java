package com.juju.sport.home.homepage.test;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.juju.sport.base.dao.HomePageConfigDao;
import com.juju.sport.base.dto.PageData;
import com.juju.sport.base.pojo.HomePageConfig;
import com.juju.sport.common.util.JsonUtil;
import com.juju.sport.home.test.BaseTestCase;

public class HomePageConfigDaoTest extends BaseTestCase {
	
	@Autowired
	private HomePageConfigDao pageDao;

    // 聚喜大家庭
    @Test
    public void prepareFamily() {
        int i = 0;
        for(int index = 0;index<20;index++) {
            HomePageConfig config  = new HomePageConfig();
            config.setDataKey("family");
            PageData pd = new PageData("纤夫的爱", "../img/pic_132x126.jpg", "");
            pd.setScore(3.0f);
            pd.setDate("2013-12-11");
            config.setDataValue(JsonUtil.getJsonStr(pd));
            config.setOrderTag(index);
            pageDao.insert(config);
        }

    }

    /**
     * 六画面
     */
    @Test
    public void prepareJxRecommend() {
        int i = 0;
        for(int index = 0;index<6;index++) {
            HomePageConfig config = new HomePageConfig();
            config.setDataKey("jx_recommend");
            PageData pd1 = new PageData("花嫁丽舍私人婚礼会所"+index, "../img/pic_260x175_01.jpg", "5888-7888元");
            config.setDataValue(JsonUtil.getJsonStr(pd1));
            config.setOrderTag(index);
            pageDao.insert(config);
        }

    }

    /**
     * 品味婚典
     */
    @Test
    public void preparePinWei() {
        int i = 0;
        for(int index = 0;index<4;index++) {
            HomePageConfig config = new HomePageConfig();
            config.setDataKey("wed_style");
            PageData pd1 = new PageData("中式婚典标题占位符"+index, "../img/pic_380x168.jpg", "上海市普陀区金沙江路999号");
            pd1.getSubImage().add("../img/pic_184x168.jpg");
            pd1.getSubImage().add("../img/pic_184x168.jpg");
            pd1.getSubImage().add("../img/pic_184x168.jpg");
            pd1.getChildren().add(new PageData("就进入会员\"天使的爱\"主页查看详情", "../img/pic_43x43.jpg", ""));
            pd1.getChildren().add(new PageData("就进入会员\"天使的爱\"主页查看详情", "../img/pic_43x43.jpg", ""));
            config.setDataValue(JsonUtil.getJsonStr(pd1));
            config.setOrderTag(index);
            pageDao.insert(config);
        }

    }

    // 首页轮播(大)
    @Test
    public void prepareBigSlide() {
        int i = 0;
        HomePageConfig config  = new HomePageConfig();
        config.setDataKey("big_slide");
        config.setDataValue(JsonUtil.getJsonStr(new PageData("", "../img/pic_668x480_01.jpg", "")));
        config.setOrderTag(i++);
        pageDao.insert(config);

        config  = new HomePageConfig();
        config.setDataKey("big_slide");
        config.setDataValue(JsonUtil.getJsonStr(new PageData("", "../img/pic_668x480_02.jpg", "")));
        config.setOrderTag(i++);
        pageDao.insert(config);
    }

    // 首页轮播(小)
    @Test
    public void prepareSmallSlide() {
        int i = 0;
        HomePageConfig config  = new HomePageConfig();
        config.setDataKey("small_slide");
        config.setDataValue(JsonUtil.getJsonStr(new PageData("", "../img/pic_265x392.jpg", "")));
        config.setOrderTag(i++);
        pageDao.insert(config);

        config  = new HomePageConfig();
        config.setDataKey("small_slide");
        config.setDataValue(JsonUtil.getJsonStr(new PageData("", "../img/pic_265x392.jpg", "")));
        pageDao.insert(config);
    }

	@Test
	public void prepareHotSaleData() {
		int i = 0;
		HomePageConfig config  = new HomePageConfig();
		config.setDataKey("hot_sale");
		config.setDataValue(JsonUtil.getJsonStr(new PageData("拉薇达一站式婚礼会馆（法国馆）", "image", "婚宴价格：¥4688 - 16888")));
		config.setOrderTag(i++);
		pageDao.insert(config);
		
		config  = new HomePageConfig();
		config.setDataKey("hot_sale");
		config.setDataValue(JsonUtil.getJsonStr(new PageData("拉薇达一站式婚礼会馆（法国馆）", "image", "婚宴价格：¥4688 - 16888")));
		config.setOrderTag(i++);
		pageDao.insert(config);
		
		config  = new HomePageConfig();
		config.setDataKey("hot_sale");
		config.setDataValue(JsonUtil.getJsonStr(new PageData("拉薇达一站式婚礼会馆（法国馆）", "image", "婚宴价格：¥4688 - 16888")));
		config.setOrderTag(i++);
		pageDao.insert(config);
	}


    @Test
    public void prepareTop10() {
        int i = 0;
        HomePageConfig config  = new HomePageConfig();
        config.setDataKey("top10");
        config.setDataValue(JsonUtil.getJsonStr(new PageData("拉薇达一站式婚礼会馆（法国馆）", "image", "婚宴价格：¥4688 - 16888")));
        config.setOrderTag(i++);
        pageDao.insert(config);

        config  = new HomePageConfig();
        config.setDataKey("top10");
        config.setDataValue(JsonUtil.getJsonStr(new PageData("拉薇达一站式婚礼会馆（法国馆）", "image", "婚宴价格：¥4688 - 16888")));
        config.setOrderTag(i++);
        pageDao.insert(config);

        config  = new HomePageConfig();
        config.setDataKey("top10");
        config.setDataValue(JsonUtil.getJsonStr(new PageData("拉薇达一站式婚礼会馆（法国馆）", "image", "婚宴价格：¥4688 - 16888")));
        config.setOrderTag(i++);
        pageDao.insert(config);

        config  = new HomePageConfig();
        config.setDataKey("top10");
        config.setDataValue(JsonUtil.getJsonStr(new PageData("拉薇达一站式婚礼会馆（法国馆）", "image", "婚宴价格：¥4688 - 16888")));
        config.setOrderTag(i++);
        pageDao.insert(config);

        config  = new HomePageConfig();
        config.setDataKey("top10");
        config.setDataValue(JsonUtil.getJsonStr(new PageData("拉薇达一站式婚礼会馆（法国馆）", "image", "婚宴价格：¥4688 - 16888")));
        config.setOrderTag(i++);
        pageDao.insert(config);

        config  = new HomePageConfig();
        config.setDataKey("top10");
        config.setDataValue(JsonUtil.getJsonStr(new PageData("拉薇达一站式婚礼会馆（法国馆）", "image", "婚宴价格：¥4688 - 16888")));
        config.setOrderTag(i++);
        pageDao.insert(config);

        config  = new HomePageConfig();
        config.setDataKey("top10");
        config.setDataValue(JsonUtil.getJsonStr(new PageData("拉薇达一站式婚礼会馆（法国馆）", "image", "婚宴价格：¥4688 - 16888")));
        config.setOrderTag(i++);
        pageDao.insert(config);

        config  = new HomePageConfig();
        config.setDataKey("top10");
        config.setDataValue(JsonUtil.getJsonStr(new PageData("拉薇达一站式婚礼会馆（法国馆）", "image", "婚宴价格：¥4688 - 16888")));
        config.setOrderTag(i++);
        pageDao.insert(config);

        config  = new HomePageConfig();
        config.setDataKey("top10");
        config.setDataValue(JsonUtil.getJsonStr(new PageData("拉薇达一站式婚礼会馆（法国馆）", "image", "婚宴价格：¥4688 - 16888")));
        config.setOrderTag(i++);
        pageDao.insert(config);

        config  = new HomePageConfig();
        config.setDataKey("top10");
        config.setDataValue(JsonUtil.getJsonStr(new PageData("拉薇达一站式婚礼会馆（法国馆）", "image", "婚宴价格：¥4688 - 16888")));
        config.setOrderTag(i++);
        pageDao.insert(config);
    }
}
