package com.juju.sport.home.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.juju.sport.common.constants.DataStatus;
import com.juju.sport.common.constants.SessionConstants;
import com.juju.sport.common.dto.DateDto;
import com.juju.sport.common.model.PageQuery;
import com.juju.sport.common.model.PageResult;
import com.juju.sport.common.model.Response;
import com.juju.sport.common.util.DateUtils;
import com.juju.sport.common.util.StringUtils;
import com.juju.sport.order.dto.OrderItemsDto;
import com.juju.sport.order.service.IOrderItemsService;
import com.juju.sport.stadium.api.IStadiumService;
import com.juju.sport.stadium.dto.SpaceDto;
import com.juju.sport.stadium.dto.SpaceInfoDto;
import com.juju.sport.stadium.dto.SpaceListDto;
import com.juju.sport.stadium.dto.SpaceOpenTimeDto;
import com.juju.sport.stadium.service.ISpaceOpenTimeService;
import com.juju.sport.stadium.service.ISpaceService;
import com.juju.sport.user.dto.LoginUserDto;
import com.juju.sport.user.dto.VenusInfoDto;
import com.juju.sport.user.service.IVenuesInfoService;


/**
 * 
 * 此类描述的是：场地管理Controller
 * 
 * @author: cwftalus@163.com
 * @version: 2015年3月24日 上午10:21:58
 */

@Controller
@RequestMapping(value = "/space")
public class SpaceController {

	protected static final Log logger = LogFactory.getLog(SpaceController.class);

	@Autowired
	private ISpaceService iSpaceService;
	
	@Autowired
	private IOrderItemsService iOrderItemsService;
	
	@Autowired
	private ISpaceOpenTimeService iSpaceOpenTimeService;
	
	@Autowired
	private IVenuesInfoService iVenuesInfoService;
	
	@Autowired
	private IStadiumService iStadiumService;
	
	private static String FORMATSTR = "HH:mm";
	
	private static final String SPACEUPDATEINFO = "spaceupdateinfo";
	

	/**
	 * 
	 * 此方法描述的是：场馆用户对场地进行新增或者修改操作
	 * 
	 * @author: wangxiongdx@163.com
	 * @version: 2015年3月24日 上午11:59:32
	 */
	@RequestMapping(value = "/save.do")
	@ResponseBody
	public Response<String> save(SpaceDto spaceDto) {
		 Response<String> result = new  Response<String>();
		iSpaceService.saveOrUpdate(spaceDto);
		return result;
	}
	
	/**
	 * 
		 * 此方法描述的是：标记删除场地数据
		 * @author: alfredye
		 * @version: 2015年3月30日 下午1:03:32
	 */
	
	@RequestMapping(value = "/delete.do")
	@ResponseBody
	public Response<String> delete(HttpSession session,SpaceDto spaceDto) {
		 Response<String> result = new  Response<String>();
		 LoginUserDto loginUserDto =null;
		if(session.getAttribute(SessionConstants.LOGIN_USER_INFO)!=null){
			 loginUserDto = (LoginUserDto) session.getAttribute(SessionConstants.LOGIN_USER_INFO);
			if(loginUserDto.getType()!=2){
				result.setStatus(DataStatus.HTTP_FAILE);
				result.setMessage("401");
				return result;
			}
		}
		OrderItemsDto orderItemsDto = new OrderItemsDto();
		orderItemsDto.setSpaceId(spaceDto.getId());
		int count = iOrderItemsService.findCount(orderItemsDto);
		if(count==0){
			iSpaceService.deleteSpaceInfos(spaceDto);
			//删除时场馆场地数量-1
			iVenuesInfoService.updateSpaceNum(loginUserDto.getId(), false);
			
			result.setStatus(DataStatus.HTTP_SUCCESS);
			result.setMessage("200");
			return result;
		}else{
			iSpaceService.deleteSpaceInfos(spaceDto);
			result.setStatus(DataStatus.HTTP_FAILE);
			result.setMessage("404");
			return result;
		}
		
		
	}
	
	/**
	 * 此方法描述的是:修改场地主要信息
	 */
	@RequestMapping(value="/updateSpaceMaster.do")
	@ResponseBody
	public Response<String> updateMaster(SpaceDto spaceDto,HttpSession session){
		 Response<String> result = new  Response<String>();
		if(session.getAttribute(SessionConstants.LOGIN_USER_INFO)!=null){
			LoginUserDto loginUserDto = (LoginUserDto) session.getAttribute(SessionConstants.LOGIN_USER_INFO);
			if(loginUserDto.getType()!=2){
				result.setStatus(DataStatus.HTTP_FAILE);
				result.setMessage("401");
				return result;
			}
			spaceDto.setUserAccountId(loginUserDto.getId());
		}
		if(spaceDto.getMinNumber()==-1){
			spaceDto.setMinNumber(null);
		}
		
		
		if(!spaceDto.getSpaceName().equals(spaceDto.getResourceInfos())&&iSpaceService.findCountByName(spaceDto)>0){
				result.setStatus(DataStatus.HTTP_FAILE);
				result.setMessage("405");
				return result;
		}
		spaceDto.setStat(1);
		iSpaceService.updateSpaceInfos(spaceDto);
		result.setStatus(DataStatus.HTTP_SUCCESS);
		result.setMessage("200");
		return result;
	}
	
	
	
	
	/**
	 * 此方法描述的是:寻找修改场地信息数据
	 * @author alfredye
	 */
	@RequestMapping(value="/findUpdateInfo.do")
	@ResponseBody
	public  Response<SpaceListDto> findSpaceUpdateInfo(HttpSession session){
		 Response<SpaceListDto> results = new  Response<SpaceListDto>();
		SpaceDto spaceDto = new SpaceDto();
		if(session.getAttribute(SessionConstants.LOGIN_USER_INFO)!=null){
			LoginUserDto loginUserDto = (LoginUserDto) session.getAttribute(SessionConstants.LOGIN_USER_INFO);
			if(loginUserDto.getType()!=2){
				results.setStatus(DataStatus.HTTP_FAILE);
				results.setMessage("401");
				return results;
			}
		}
		if(session.getAttribute(SPACEUPDATEINFO)==null){
			results.setStatus(DataStatus.HTTP_FAILE);
			results.setMessage("401");
			return results;
		}
		spaceDto.setId(session.getAttribute(SPACEUPDATEINFO).toString());
		session.removeAttribute(SPACEUPDATEINFO);
		

		SpaceListDto result = iSpaceService.findUpdateInfos(spaceDto);
		results.setData(result);
		return results;
		
	}
	
	@RequestMapping(value="/jumpInfos.do")
	public void jumpInfos(SpaceDto spaceDto,HttpSession session,HttpServletRequest request,HttpServletResponse response){
		 
		session.removeAttribute(SPACEUPDATEINFO);
		session.setAttribute(SPACEUPDATEINFO, spaceDto.getId());
		
		try {
			response.sendRedirect("/app/site/updateSpaceList.html");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 此方法描述的是：新的修改场地数据
	 */
	
	@RequestMapping(value="/update.do")
	@ResponseBody
	public Response<String>updates(HttpSession session,SpaceDto spaceDto,@RequestParam("startDate")int startDate,@RequestParam("price")int price,@RequestParam("endDate")int endDate,@RequestParam("startTime")int startTime,@RequestParam("endTime")int endTime){
		Response<String>  resultType = new Response<String>();
		SpaceOpenTimeDto spaceOpenTimeDto = null;
		
		LoginUserDto loginUserDto = null;
		if(session.getAttribute(SessionConstants.LOGIN_USER_INFO)!=null){
			loginUserDto = (LoginUserDto) session.getAttribute(SessionConstants.LOGIN_USER_INFO);
			if(loginUserDto.getType()!=2){
				resultType.setStatus(DataStatus.HTTP_FAILE);
				resultType.setMessage("401");
				return resultType;
			}
		}
		List<VenusInfoDto>  venusList = iStadiumService.selectVenusInfoByUserAccountId(loginUserDto.getId());
		if(!CollectionUtils.isEmpty(venusList)){
			int charType = venusList.get(0).getChargeType();
			if(charType==0&&price>0){
				resultType.setStatus(DataStatus.HTTP_FAILE);
				resultType.setMessage("408");
				return resultType;
			}
		}

 		String resourceInfos = spaceDto.getResourceInfos();
		//原先数据
		String[] temp = resourceInfos.split(";");
		String tempId=temp[0].toString();
		int tempStartWeek=Integer.parseInt(temp[1].toString());
 		int tempEndWeek = Integer.parseInt(temp[2].toString());
		int tempStartTime=Integer.parseInt(temp[3].toString());
		int tempEndTime = Integer.parseInt(temp[4].toString());
		int tempPrice = Integer.parseInt(temp[5].toString());
		String tempA= "";
		
		//数据没变
		if(startDate==tempStartWeek&&endDate==tempEndWeek&&startTime==tempStartTime&&endTime==tempEndTime&&tempPrice==price){
 			resultType.setMessage("200");
			return resultType;
		}
		
		
		
		//比较时间
				if(startTime>tempStartTime){
					OrderItemsDto orderItemsDto = new OrderItemsDto();
					orderItemsDto.setSpaceId(tempId);
					orderItemsDto.setOrderTime(tempStartTime+"");
					orderItemsDto.setEndTime(startTime+"");
					orderItemsDto.setStat(900);
					int countOr = iOrderItemsService.findCount(orderItemsDto);
					orderItemsDto.setStat(901);
					int countOrd = iOrderItemsService.findCount(orderItemsDto);
					if(countOr!=0&&countOrd!=0){
						resultType.setStatus(DataStatus.HTTP_FAILE);
						resultType.setMessage("405");
						return resultType;
					}
				}
				
				if(endTime<tempEndTime){
					OrderItemsDto orderItemsDto = new OrderItemsDto();
					orderItemsDto.setSpaceId(tempId);
					orderItemsDto.setOrderTime(endTime+"");
					orderItemsDto.setEndTime(tempEndTime+"");
					orderItemsDto.setStat(900);
					int countOr = iOrderItemsService.findCount(orderItemsDto);
					orderItemsDto.setStat(901);
					int countOrd = iOrderItemsService.findCount(orderItemsDto);
					if(countOr!=0&&countOrd!=0){
						resultType.setStatus(DataStatus.HTTP_FAILE);
						resultType.setMessage("405");
						return resultType;
					}
				}
				
				
				
				//比较日期
				//开始日期与结束日期与数据库相同
				if(startDate==tempStartWeek&&endDate==tempEndWeek){
					for(int i=startDate;i<=endDate;i++){
						spaceOpenTimeDto = new SpaceOpenTimeDto();
						spaceOpenTimeDto.setStartTime(DateUtils.StringToDate(tempStartTime+":00:00", FORMATSTR));
						spaceOpenTimeDto.setEndTime(DateUtils.StringToDate(tempEndTime+":00:00", FORMATSTR));
						spaceOpenTimeDto.setStat(DataStatus.ENABLED);
						spaceOpenTimeDto.setSpaceId(tempId);
						spaceOpenTimeDto.setOpenWeek((short)i);
						List<SpaceOpenTimeDto> spaceTimeList = iSpaceOpenTimeService.findSpace(spaceOpenTimeDto);
						if(spaceTimeList!=null&&spaceTimeList.size()>0){
							spaceOpenTimeDto = new SpaceOpenTimeDto();
//							spaceOpenTimeDto.setSpaceId(tempId);
							spaceOpenTimeDto.setId(spaceTimeList.get(0).getId());
							spaceOpenTimeDto.setStartTime(DateUtils.StringToDate(startTime+":00:00", FORMATSTR));
							spaceOpenTimeDto.setEndTime(DateUtils.StringToDate(endTime+":00:00", FORMATSTR));
							spaceOpenTimeDto.setOpenWeek((short)i);
							spaceOpenTimeDto.setPrice(price);
							iSpaceOpenTimeService.updateOpenTimeInfoByWeek(spaceOpenTimeDto);

						}
					
					}
					resultType.setMessage("200");
					return resultType;
				}
					
				
				//日期如果小于开始日期或大于结束日期 则insert数据
				if(startDate<tempStartWeek&&endDate>tempEndWeek){
					
					//开始日期小于原先开始日期
					if(startDate<tempStartWeek){
					for(int i =startDate;i<tempStartWeek;i++){
						spaceOpenTimeDto = new SpaceOpenTimeDto();
						spaceOpenTimeDto.setStartTime(DateUtils.StringToDate(tempStartTime+":00:00", FORMATSTR));
						spaceOpenTimeDto.setEndTime(DateUtils.StringToDate(tempEndTime+":00:00", FORMATSTR));
						spaceOpenTimeDto.setStat(DataStatus.ENABLED);
						spaceOpenTimeDto.setSpaceId(tempId);
						spaceOpenTimeDto.setOpenWeek((short)i);
						List<SpaceOpenTimeDto> spaceTimeList = iSpaceOpenTimeService.findSpace(spaceOpenTimeDto);
						if(spaceTimeList!=null&&spaceTimeList.size()>0){
							spaceOpenTimeDto = new SpaceOpenTimeDto();
//							spaceOpenTimeDto.setSpaceId(tempId);
							spaceOpenTimeDto.setId(spaceTimeList.get(0).getId());
							spaceOpenTimeDto.setStartTime(DateUtils.StringToDate(startTime+":00:00", FORMATSTR));
							spaceOpenTimeDto.setEndTime(DateUtils.StringToDate(endTime+":00:00", FORMATSTR));
							spaceOpenTimeDto.setOpenWeek((short)i);
							spaceOpenTimeDto.setUserAccountId(loginUserDto.getId());
							spaceOpenTimeDto.setPrice(price);
							iSpaceOpenTimeService.saveOrUpdate(spaceOpenTimeDto);
						}
						
					}
				}

				//如果是结束日期大于原定结束日期
				if(endDate>tempEndWeek){
					for(int o =tempEndWeek;o<=endDate;o++){
						spaceOpenTimeDto = new SpaceOpenTimeDto();
						spaceOpenTimeDto.setOpenWeek((short)o);
						spaceOpenTimeDto.setSpaceId(tempId);
						int count2 = iSpaceOpenTimeService.findCount(spaceOpenTimeDto);
						if(count2==0){
							spaceOpenTimeDto = new SpaceOpenTimeDto();
							spaceOpenTimeDto.setSpaceId(tempId);
							spaceOpenTimeDto.setStartTime(DateUtils.StringToDate(startTime+":00:00", FORMATSTR));
							spaceOpenTimeDto.setEndTime(DateUtils.StringToDate(endTime+":00:00", FORMATSTR));
							spaceOpenTimeDto.setOpenWeek((short)o);
							spaceOpenTimeDto.setUserAccountId(loginUserDto.getId());
							spaceOpenTimeDto.setPrice(price);
							iSpaceOpenTimeService.saveOrUpdate(spaceOpenTimeDto);
						}else{
							spaceOpenTimeDto.setStartTime(DateUtils.StringToDate(tempStartTime+":00:00", FORMATSTR));
							spaceOpenTimeDto.setEndTime(DateUtils.StringToDate(tempEndTime+":00:00", FORMATSTR));
							spaceOpenTimeDto.setStat(DataStatus.ENABLED);
							List<SpaceOpenTimeDto> spaceTimeList = iSpaceOpenTimeService.findSpace(spaceOpenTimeDto);
							if(spaceTimeList!=null&&spaceTimeList.size()>0){
								spaceOpenTimeDto = new SpaceOpenTimeDto();
								spaceOpenTimeDto.setSpaceId(tempId);
								spaceOpenTimeDto.setId(spaceTimeList.get(0).getId());
								spaceOpenTimeDto.setStartTime(DateUtils.StringToDate(startTime+":00:00", FORMATSTR));
								spaceOpenTimeDto.setEndTime(DateUtils.StringToDate(endTime+":00:00", FORMATSTR));
								spaceOpenTimeDto.setOpenWeek((short)o);
								spaceOpenTimeDto.setPrice(price);
								iSpaceOpenTimeService.updateOpenTimeInfoByWeek(spaceOpenTimeDto);
							}
							
						}

						
					}

				}
				resultType.setMessage("200");
				return resultType;
		}	
				
				
				
				
				
				
				//日期如果大于开始日期且小于结束日期、日期大于开始日期且等于结束日期、日期等于开始日期且小于结束日期
				
				if((startDate>tempStartWeek&&endDate<tempEndWeek)||(startDate>=tempStartWeek&&endDate<tempEndWeek)||(startDate>tempStartWeek&&endDate<=tempEndWeek)){
					
					
					
					
					
					//开始日期不等于原先开始日期 且结束日期不等于结束日期
					if(startDate!=tempStartWeek&&endDate!=tempEndWeek){
					for(int k=tempStartWeek;k<=tempEndWeek;k++){
						boolean flag=false;
						for(int p=startDate;p<=endDate;p++){
							if(k==p){
								flag=true;
								break;
							}
							
						}
						if(flag==false){
						tempA+=k+";";
						}
						
					}
					String[] tempB= tempA.split(";");
					
					
					for(int u = 0;u<tempB.length;u++){
						if(startTime==tempStartTime&&endTime==tempEndTime){
						spaceOpenTimeDto = new SpaceOpenTimeDto();
						spaceOpenTimeDto.setOpenWeek((short)Integer.parseInt(tempB[u].toString()));
						spaceOpenTimeDto.setSpaceId(tempId);
						int count2 = iSpaceOpenTimeService.findCount(spaceOpenTimeDto);
						if(count2!=0){
							if(tempB[u]!=null&&!"".equals(tempB[u].toString())){
								
								OrderItemsDto orderItemsDto = new OrderItemsDto();
								orderItemsDto.setSpaceId(tempId);
								orderItemsDto.setWeek(Integer.parseInt(tempB[u]));
								int count = iOrderItemsService.findCount(orderItemsDto);
								if(count==0){
									spaceOpenTimeDto.setStartTime(DateUtils.StringToDate(tempStartTime+":00:00", FORMATSTR));
									spaceOpenTimeDto.setEndTime(DateUtils.StringToDate(tempEndTime+":00:00", FORMATSTR));
									spaceOpenTimeDto.setStat(DataStatus.ENABLED);
									List<SpaceOpenTimeDto> spaceTimeList = iSpaceOpenTimeService.findSpace(spaceOpenTimeDto);
									if(spaceTimeList!=null&&spaceTimeList.size()>0){
										spaceOpenTimeDto = new SpaceOpenTimeDto();
										spaceOpenTimeDto.setOpenWeek((short)Integer.parseInt(tempB[u]));
										spaceOpenTimeDto.setId(spaceTimeList.get(0).getId());
										iSpaceOpenTimeService.deleteOpenTimeInfoByWeek(spaceOpenTimeDto);
										
									}

									
								}else{
									resultType.setMessage("405");
									return resultType;
								}
						}
					}else{
						
					}
				}else{
					spaceOpenTimeDto = new SpaceOpenTimeDto();
					spaceOpenTimeDto.setOpenWeek((short)Integer.parseInt(tempB[u].toString()));
					spaceOpenTimeDto.setSpaceId(tempId);
					int count2 = iSpaceOpenTimeService.findCount(spaceOpenTimeDto);
					if(count2!=0){
						if(tempB[u]!=null&&!"".equals(tempB[u].toString())){
							
							OrderItemsDto orderItemsDto = new OrderItemsDto();
							orderItemsDto.setSpaceId(tempId);
							orderItemsDto.setWeek(Integer.parseInt(tempB[u]));
							int count = iOrderItemsService.findCount(orderItemsDto);
							if(count==0){
								spaceOpenTimeDto.setStartTime(DateUtils.StringToDate(tempStartTime+":00:00", FORMATSTR));
								spaceOpenTimeDto.setEndTime(DateUtils.StringToDate(tempEndTime+":00:00", FORMATSTR));
								spaceOpenTimeDto.setStat(DataStatus.ENABLED);
								List<SpaceOpenTimeDto> spaceTimeList = iSpaceOpenTimeService.findSpace(spaceOpenTimeDto);
								if(spaceTimeList!=null&&spaceTimeList.size()>0){
									spaceOpenTimeDto = new SpaceOpenTimeDto();
									spaceOpenTimeDto.setOpenWeek((short)Integer.parseInt(tempB[u]));
//									spaceOpenTimeDto.setSpaceId(tempId);
									spaceOpenTimeDto.setId(spaceTimeList.get(0).getId());
									spaceOpenTimeDto.setStartTime(DateUtils.StringToDate(startTime+":00:00", FORMATSTR));
									spaceOpenTimeDto.setEndTime(DateUtils.StringToDate(endTime+":00:00", FORMATSTR));
									spaceOpenTimeDto.setPrice(price);
									iSpaceOpenTimeService.updateOpenTimeInfoByWeek(spaceOpenTimeDto);
									
								}
					
								
							}else{
								resultType.setMessage("405");
								return resultType;
							}
					}
				}
			}	
						
					}
					resultType.setMessage("200");
					return resultType;
				}
					
					
					
					
					
					
					
					
					
					//大于开始日期且等于结束日期
				   if(startDate!=tempStartWeek&&endDate==tempEndWeek){
					   
					   for(int m=tempStartWeek;m<startDate;m++){
						   spaceOpenTimeDto = new SpaceOpenTimeDto();
							spaceOpenTimeDto.setOpenWeek((short)m);
							spaceOpenTimeDto.setSpaceId(tempId);
							int count2 = iSpaceOpenTimeService.findCount(spaceOpenTimeDto);
							if(count2!=0){
								OrderItemsDto orderItemsDto = new OrderItemsDto();
								   orderItemsDto.setSpaceId(tempId);
								   orderItemsDto.setWeek(m);
								   int count1 = iOrderItemsService.findCount(orderItemsDto);
								   if(count1==0){
									   spaceOpenTimeDto.setStartTime(DateUtils.StringToDate(tempStartTime+":00:00", FORMATSTR));
										spaceOpenTimeDto.setEndTime(DateUtils.StringToDate(tempEndTime+":00:00", FORMATSTR));
										spaceOpenTimeDto.setStat(DataStatus.ENABLED);
										List<SpaceOpenTimeDto> spaceTimeList = iSpaceOpenTimeService.findSpace(spaceOpenTimeDto);
										if(spaceTimeList!=null&&spaceTimeList.size()>0){
											   spaceOpenTimeDto = new SpaceOpenTimeDto();
												spaceOpenTimeDto.setOpenWeek((short)m);
//												spaceOpenTimeDto.setSpaceId(tempId);
												spaceOpenTimeDto.setId(spaceTimeList.get(0).getId());
												iSpaceOpenTimeService.deleteOpenTimeInfoByWeek(spaceOpenTimeDto);
										}

										
								   }else{
										resultType.setMessage("405");
										return resultType;
									}
							}else{
								resultType.setMessage("403");
								return resultType;
							}
					   }
					   if(startTime!=tempStartTime||endTime!=tempEndTime){
						   for(int b=startDate;b<=endDate;b++){
							   spaceOpenTimeDto = new SpaceOpenTimeDto();
							   spaceOpenTimeDto.setOpenWeek((short)b);
							   spaceOpenTimeDto.setSpaceId(tempId);
							   spaceOpenTimeDto.setStartTime(DateUtils.StringToDate(tempStartTime+":00:00", FORMATSTR));
								spaceOpenTimeDto.setEndTime(DateUtils.StringToDate(tempEndTime+":00:00", FORMATSTR));
								spaceOpenTimeDto.setStat(DataStatus.ENABLED);
								List<SpaceOpenTimeDto> spaceTimeList = iSpaceOpenTimeService.findSpace(spaceOpenTimeDto);
								if(spaceTimeList!=null&&spaceTimeList.size()>0){
									spaceOpenTimeDto = new SpaceOpenTimeDto();
									   spaceOpenTimeDto.setOpenWeek((short)b);
//									   spaceOpenTimeDto.setSpaceId(tempId);
									   spaceOpenTimeDto.setId(spaceTimeList.get(0).getId());
									   spaceOpenTimeDto.setStartTime(DateUtils.StringToDate(startTime+":00:00", FORMATSTR));
										spaceOpenTimeDto.setEndTime(DateUtils.StringToDate(endTime+":00:00", FORMATSTR));
										spaceOpenTimeDto.setPrice(price);
									   iSpaceOpenTimeService.updateOpenTimeInfoByWeek(spaceOpenTimeDto);
								}
							   
						   }
					   }
					   resultType.setMessage("200");
						return resultType;
				   }
				   
				   
				   
				   
				   //开始日期相等且结束日期不同
				   if(startDate==tempStartWeek&&endDate!=tempEndWeek){
					   
					   for(int n=endDate+1;n<=tempEndWeek;n++){
						   spaceOpenTimeDto = new SpaceOpenTimeDto();
							spaceOpenTimeDto.setOpenWeek((short)n);
							spaceOpenTimeDto.setSpaceId(tempId);
							int count2 = iSpaceOpenTimeService.findCount(spaceOpenTimeDto);
							if(count2!=0){
								OrderItemsDto orderItemsDto = new OrderItemsDto();
								   orderItemsDto.setSpaceId(tempId);
								   orderItemsDto.setWeek(n);
								   int count23 = iOrderItemsService.findCount(orderItemsDto);
								   if(count23==0){
									   spaceOpenTimeDto.setStartTime(DateUtils.StringToDate(tempStartTime+":00:00", FORMATSTR));
										spaceOpenTimeDto.setEndTime(DateUtils.StringToDate(tempEndTime+":00:00", FORMATSTR));
										spaceOpenTimeDto.setStat(DataStatus.ENABLED);
										List<SpaceOpenTimeDto> spaceTimeList = iSpaceOpenTimeService.findSpace(spaceOpenTimeDto);
										if(spaceTimeList!=null&&spaceTimeList.size()>0){
											spaceOpenTimeDto = new SpaceOpenTimeDto();
											spaceOpenTimeDto.setOpenWeek((short)n);
//											spaceOpenTimeDto.setSpaceId(tempId);
											spaceOpenTimeDto.setId(spaceTimeList.get(0).getId());
											iSpaceOpenTimeService.deleteOpenTimeInfoByWeek(spaceOpenTimeDto);
										}
									   
										
								   }else{
										resultType.setMessage("405");
										return resultType;
									}
							}else{
								resultType.setMessage("403");
								return resultType;
							}
							
							
					   }
					   
					   
					   //开始日期或结束日期不相同
					   if(startTime!=tempStartTime||endTime!=tempEndTime){
						   for(int b=startDate;b<=endDate;b++){
							   spaceOpenTimeDto = new SpaceOpenTimeDto();
							   spaceOpenTimeDto.setSpaceId(tempId);
							   spaceOpenTimeDto.setOpenWeek((short)b);
							   spaceOpenTimeDto.setStartTime(DateUtils.StringToDate(tempStartTime+":00:00", FORMATSTR));
								spaceOpenTimeDto.setEndTime(DateUtils.StringToDate(tempEndTime+":00:00", FORMATSTR));
								spaceOpenTimeDto.setStat(DataStatus.ENABLED);
								List<SpaceOpenTimeDto> spaceTimeList = iSpaceOpenTimeService.findSpace(spaceOpenTimeDto);
								if(spaceTimeList!=null&&spaceTimeList.size()>0){
									  spaceOpenTimeDto = new SpaceOpenTimeDto();
									   spaceOpenTimeDto.setOpenWeek((short)b);
//									   spaceOpenTimeDto.setSpaceId(tempId);
									   spaceOpenTimeDto.setId(spaceTimeList.get(0).getId());
									   spaceOpenTimeDto.setStartTime(DateUtils.StringToDate(startTime+":00:00", FORMATSTR));
										spaceOpenTimeDto.setEndTime(DateUtils.StringToDate(endTime+":00:00", FORMATSTR));
										spaceOpenTimeDto.setPrice(price);
									   iSpaceOpenTimeService.updateOpenTimeInfoByWeek(spaceOpenTimeDto);
								}
							 
						   }
					   }
					   resultType.setMessage("200");
						return resultType;
				   }
				   
				   //开始日期相等且结束日期不能相等
				   if(startDate==tempStartWeek&&endDate!=tempEndWeek){
					   
					   for(int n=endDate+1;n<=tempEndWeek;n++){
						   spaceOpenTimeDto = new SpaceOpenTimeDto();
							spaceOpenTimeDto.setOpenWeek((short)n);
							spaceOpenTimeDto.setSpaceId(tempId);
							int count2 = iSpaceOpenTimeService.findCount(spaceOpenTimeDto);
							if(count2!=0){
								OrderItemsDto orderItemsDto = new OrderItemsDto();
								   orderItemsDto.setSpaceId(tempId);
								   orderItemsDto.setWeek(n);
								   int count23 = iOrderItemsService.findCount(orderItemsDto);
								   if(count23==0){
									   spaceOpenTimeDto.setStartTime(DateUtils.StringToDate(tempStartTime+":00:00", FORMATSTR));
										spaceOpenTimeDto.setEndTime(DateUtils.StringToDate(tempEndTime+":00:00", FORMATSTR));
										spaceOpenTimeDto.setStat(DataStatus.ENABLED);
										spaceOpenTimeDto.setOpenWeek((short)n);
										List<SpaceOpenTimeDto> spaceTimeList = iSpaceOpenTimeService.findSpace(spaceOpenTimeDto);
										if(spaceTimeList!=null&&spaceTimeList.size()>0){
											 spaceOpenTimeDto = new SpaceOpenTimeDto();
												spaceOpenTimeDto.setOpenWeek((short)n);
//												spaceOpenTimeDto.setSpaceId(tempId);
												spaceOpenTimeDto.setId(spaceTimeList.get(0).getId());
												iSpaceOpenTimeService.deleteOpenTimeInfoByWeek(spaceOpenTimeDto); 
										}
									  
										
								   }else{
										resultType.setMessage("405");
										return resultType;
									}
							}else{
								resultType.setMessage("403");
								return resultType;
							}
							
							
					   }
					   if(startTime!=tempStartTime||endTime!=tempEndTime){
						   for(int b=startDate;b<=endDate;b++){
							   spaceOpenTimeDto = new SpaceOpenTimeDto();
							   spaceOpenTimeDto.setSpaceId(tempId);
							   spaceOpenTimeDto.setOpenWeek((short)b);
							   spaceOpenTimeDto.setStartTime(DateUtils.StringToDate(tempStartTime+":00:00", FORMATSTR));
								spaceOpenTimeDto.setEndTime(DateUtils.StringToDate(tempEndTime+":00:00", FORMATSTR));
								spaceOpenTimeDto.setStat(DataStatus.ENABLED);
								List<SpaceOpenTimeDto> spaceTimeList = iSpaceOpenTimeService.findSpace(spaceOpenTimeDto);
								if(spaceTimeList!=null&&spaceTimeList.size()>0){
									 spaceOpenTimeDto = new SpaceOpenTimeDto();
									   spaceOpenTimeDto.setOpenWeek((short)b);
//									   spaceOpenTimeDto.setSpaceId(tempId);
									   spaceOpenTimeDto.setId(spaceTimeList.get(0).getId());
									   spaceOpenTimeDto.setStartTime(DateUtils.StringToDate(startTime+":00:00", FORMATSTR));
										spaceOpenTimeDto.setEndTime(DateUtils.StringToDate(endTime+":00:00", FORMATSTR));
										spaceOpenTimeDto.setPrice(price);
									   iSpaceOpenTimeService.updateOpenTimeInfoByWeek(spaceOpenTimeDto);
								}
							   
							  
						   }
					   }
					   resultType.setMessage("200");
						return resultType;
				   }
					
				}		
				
				
				
				//日期如果小于开始日期且小于结束日期 则需要判断是否有订单且insert数据
				
				if(startDate<tempStartWeek&&endDate<=tempEndWeek){
					
					if(endDate>=tempStartWeek){
						for(int m=startDate;m<=endDate;m++){
							spaceOpenTimeDto = new SpaceOpenTimeDto();
							spaceOpenTimeDto.setOpenWeek((short)m);
								spaceOpenTimeDto.setSpaceId(tempId);
								spaceOpenTimeDto.setStartTime(DateUtils.StringToDate(tempStartTime+":00:00", FORMATSTR));
								spaceOpenTimeDto.setEndTime(DateUtils.StringToDate(tempEndTime+":00:00", FORMATSTR));
								spaceOpenTimeDto.setStat(DataStatus.ENABLED);
								int count2 = iSpaceOpenTimeService.findCount(spaceOpenTimeDto);
								if(m==startDate&&count2>0){
									resultType.setMessage("403");
									return resultType;
								}
								if(count2==0){
									spaceOpenTimeDto.setSpaceId(tempId);
									spaceOpenTimeDto.setStartTime(DateUtils.StringToDate(startTime+":00:00", FORMATSTR));
									spaceOpenTimeDto.setEndTime(DateUtils.StringToDate(endTime+":00:00", FORMATSTR));
									spaceOpenTimeDto.setOpenWeek((short)m);
									spaceOpenTimeDto.setUserAccountId(loginUserDto.getId());
									spaceOpenTimeDto.setPrice(price);
									iSpaceOpenTimeService.saveOrUpdate(spaceOpenTimeDto);
								}else{

									List<SpaceOpenTimeDto> spaceTimeList = iSpaceOpenTimeService.findSpace(spaceOpenTimeDto);
									if(spaceTimeList!=null&&spaceTimeList.size()>0){
//										spaceOpenTimeDto.setSpaceId(tempId);
										spaceOpenTimeDto.setId(spaceTimeList.get(0).getId());
										spaceOpenTimeDto.setStartTime(DateUtils.StringToDate(startTime+":00:00", FORMATSTR));
										spaceOpenTimeDto.setEndTime(DateUtils.StringToDate(endTime+":00:00", FORMATSTR));
										spaceOpenTimeDto.setOpenWeek((short)m);
										spaceOpenTimeDto.setUserAccountId(loginUserDto.getId());
										spaceOpenTimeDto.setPrice(price);
										iSpaceOpenTimeService.updateOpenTimeInfoByWeek(spaceOpenTimeDto);
									}
									
								}
						}
							resultType.setMessage("200");
							return resultType;

					}
					
					
					if(endDate<=tempEndWeek){
						for(int m=startDate;m<=tempStartWeek;m++){
							spaceOpenTimeDto = new SpaceOpenTimeDto();
							spaceOpenTimeDto.setOpenWeek((short)m);
								spaceOpenTimeDto.setSpaceId(tempId);
								spaceOpenTimeDto.setStartTime(DateUtils.StringToDate(tempStartTime+":00:00", FORMATSTR));
								spaceOpenTimeDto.setEndTime(DateUtils.StringToDate(tempEndTime+":00:00", FORMATSTR));
								spaceOpenTimeDto.setStat(DataStatus.ENABLED);
								int count2 = iSpaceOpenTimeService.findCount(spaceOpenTimeDto);
								if(count2==0){
									spaceOpenTimeDto.setSpaceId(tempId);
									spaceOpenTimeDto.setStartTime(DateUtils.StringToDate(startTime+":00:00", FORMATSTR));
									spaceOpenTimeDto.setEndTime(DateUtils.StringToDate(endTime+":00:00", FORMATSTR));
									spaceOpenTimeDto.setOpenWeek((short)m);
									spaceOpenTimeDto.setUserAccountId(loginUserDto.getId());
									spaceOpenTimeDto.setPrice(price);
									iSpaceOpenTimeService.saveOrUpdate(spaceOpenTimeDto);
								}else{
									List<SpaceOpenTimeDto> spaceTimeList = iSpaceOpenTimeService.findSpace(spaceOpenTimeDto);
									if(spaceTimeList!=null&&spaceTimeList.size()>0){
//										spaceOpenTimeDto.setSpaceId(tempId);
										spaceOpenTimeDto.setId(spaceTimeList.get(0).getId());
										spaceOpenTimeDto.setStartTime(DateUtils.StringToDate(startTime+":00:00", FORMATSTR));
										spaceOpenTimeDto.setEndTime(DateUtils.StringToDate(endTime+":00:00", FORMATSTR));
										spaceOpenTimeDto.setOpenWeek((short)m);
										spaceOpenTimeDto.setUserAccountId(loginUserDto.getId());
										spaceOpenTimeDto.setPrice(price);
										iSpaceOpenTimeService.updateOpenTimeInfoByWeek(spaceOpenTimeDto);
									}
									
								}
						}
						for(int n=endDate+1;n<=tempEndWeek;n++){
								   OrderItemsDto orderItemsDto = new OrderItemsDto();
								   orderItemsDto.setSpaceId(tempId);
								   orderItemsDto.setWeek(n);
								   int count23 = iOrderItemsService.findCount(orderItemsDto);
								   if(count23==0){
									   spaceOpenTimeDto.setStartTime(DateUtils.StringToDate(tempStartTime+":00:00", FORMATSTR));
										spaceOpenTimeDto.setEndTime(DateUtils.StringToDate(tempEndTime+":00:00", FORMATSTR));
										spaceOpenTimeDto.setStat(DataStatus.ENABLED);
										spaceOpenTimeDto.setOpenWeek((short)n);
										List<SpaceOpenTimeDto> spaceTimeList = iSpaceOpenTimeService.findSpace(spaceOpenTimeDto);
										if(spaceTimeList!=null&&spaceTimeList.size()>0){
											spaceOpenTimeDto = new SpaceOpenTimeDto();
											spaceOpenTimeDto.setOpenWeek((short)n);
//											spaceOpenTimeDto.setSpaceId(tempId);
											spaceOpenTimeDto.setId(spaceTimeList.get(0).getId());
											iSpaceOpenTimeService.deleteOpenTimeInfoByWeek(spaceOpenTimeDto);
										}
									   
										
								   }else{
										resultType.setMessage("405");
										return resultType;
									}
							
						}
					}
					
					 
				}	
				
				//大于开始日期 且大于结束日期
				if(startDate>=tempStartWeek&&endDate>tempEndWeek){
					 if(startDate!=tempStartWeek){
						 
						 for(int n =tempStartWeek;n<=startDate;n++){
							   spaceOpenTimeDto = new SpaceOpenTimeDto();
								spaceOpenTimeDto.setOpenWeek((short)n);
								spaceOpenTimeDto.setSpaceId(tempId);
								int count2 = iSpaceOpenTimeService.findCount(spaceOpenTimeDto);
								if(count2!=0){
									OrderItemsDto orderItemsDto = new OrderItemsDto();
									   orderItemsDto.setSpaceId(tempId);
									   orderItemsDto.setWeek(n);
									   int count23 = iOrderItemsService.findCount(orderItemsDto);
									   if(count23==0){
										   spaceOpenTimeDto.setStartTime(DateUtils.StringToDate(tempStartTime+":00:00", FORMATSTR));
											spaceOpenTimeDto.setEndTime(DateUtils.StringToDate(tempEndTime+":00:00", FORMATSTR));
											spaceOpenTimeDto.setStat(DataStatus.ENABLED);
											List<SpaceOpenTimeDto> spaceTimeList = iSpaceOpenTimeService.findSpace(spaceOpenTimeDto);
											if(spaceTimeList!=null&&spaceTimeList.size()>0){
												
												spaceOpenTimeDto = new SpaceOpenTimeDto();
												spaceOpenTimeDto.setOpenWeek((short)n);
//												spaceOpenTimeDto.setSpaceId(tempId);
												spaceOpenTimeDto.setId(spaceTimeList.get(0).getId());
												iSpaceOpenTimeService.deleteOpenTimeInfoByWeek(spaceOpenTimeDto);
											}
											
									   }else{
											resultType.setMessage("405");
											return resultType;
										}
								}else{
									
								}
						   }
						 
					 }
					 
					 for(int m=tempEndWeek;m<=endDate;m++){
						 spaceOpenTimeDto = new SpaceOpenTimeDto();
						 spaceOpenTimeDto.setOpenWeek((short)m);
							spaceOpenTimeDto.setSpaceId(tempId);
							int count2 = iSpaceOpenTimeService.findCount(spaceOpenTimeDto);
							if(count2==0){
								spaceOpenTimeDto.setSpaceId(tempId);
								spaceOpenTimeDto.setStartTime(DateUtils.StringToDate(startTime+":00:00", FORMATSTR));
								spaceOpenTimeDto.setEndTime(DateUtils.StringToDate(endTime+":00:00", FORMATSTR));
								spaceOpenTimeDto.setOpenWeek((short)m);
								spaceOpenTimeDto.setUserAccountId(loginUserDto.getId());
								spaceOpenTimeDto.setPrice(price);
								iSpaceOpenTimeService.saveOrUpdate(spaceOpenTimeDto);
							}else{
								 spaceOpenTimeDto.setStartTime(DateUtils.StringToDate(tempStartTime+":00:00", FORMATSTR));
									spaceOpenTimeDto.setEndTime(DateUtils.StringToDate(tempEndTime+":00:00", FORMATSTR));
									spaceOpenTimeDto.setStat(DataStatus.ENABLED);
									spaceOpenTimeDto.setOpenWeek((short)m);
									List<SpaceOpenTimeDto> spaceTimeList = iSpaceOpenTimeService.findSpace(spaceOpenTimeDto);
									if(spaceTimeList!=null&&spaceTimeList.size()>0){
//										spaceOpenTimeDto.setSpaceId(tempId);
										spaceOpenTimeDto.setId(spaceTimeList.get(0).getId());
										spaceOpenTimeDto.setStartTime(DateUtils.StringToDate(startTime+":00:00", FORMATSTR));
										spaceOpenTimeDto.setEndTime(DateUtils.StringToDate(endTime+":00:00", FORMATSTR));
										spaceOpenTimeDto.setOpenWeek((short)m);
										spaceOpenTimeDto.setPrice(price);
										iSpaceOpenTimeService.updateOpenTimeInfoByWeek(spaceOpenTimeDto);										
									}

							}
							for(int k=startDate;k<endDate;k++){
								spaceOpenTimeDto = new SpaceOpenTimeDto();
								spaceOpenTimeDto.setOpenWeek((short)k);
								spaceOpenTimeDto.setSpaceId(tempId);
								spaceOpenTimeDto.setStartTime(DateUtils.StringToDate(tempStartTime+":00:00", FORMATSTR));
								spaceOpenTimeDto.setEndTime(DateUtils.StringToDate(tempEndTime+":00:00", FORMATSTR));
								spaceOpenTimeDto.setStat(DataStatus.ENABLED);
								spaceOpenTimeDto.setOpenWeek((short)k);
								List<SpaceOpenTimeDto> spaceTimeList = iSpaceOpenTimeService.findSpace(spaceOpenTimeDto);
								if(spaceTimeList!=null&&spaceTimeList.size()>0){
//									spaceOpenTimeDto.setSpaceId(tempId);
									spaceOpenTimeDto.setId(spaceTimeList.get(0).getId());
									spaceOpenTimeDto.setStartTime(DateUtils.StringToDate(startTime+":00:00", FORMATSTR));
									spaceOpenTimeDto.setEndTime(DateUtils.StringToDate(endTime+":00:00", FORMATSTR));
									spaceOpenTimeDto.setOpenWeek((short)k);
									spaceOpenTimeDto.setPrice(price);
									iSpaceOpenTimeService.updateOpenTimeInfoByWeek(spaceOpenTimeDto);
								}
								
							}

							
					 } 
					 resultType.setMessage("200");
						return resultType;
				 }
				 resultType.setMessage("200");
					return resultType;
				
		
	}
	
	
	
	
	
	
	
	
	
	
	
	/**
	 * 此方法描述的是：旧标记修改场地数据 已废除 测试专用
	 * @author:alfredye
	 */
	@RequestMapping(value="/#.do")
	@ResponseBody
	public Response<String> update(HttpSession session,SpaceDto spaceDto,@RequestParam("startDate")int startDate,@RequestParam("price")int price,@RequestParam("endDate")int endDate,@RequestParam("startTime")int startTime,@RequestParam("endTime")int endTime){
		Response<String>  resultType = new Response<String>();
		LoginUserDto loginUserDto = null;
		if(session.getAttribute(SessionConstants.LOGIN_USER_INFO)!=null){
			loginUserDto = (LoginUserDto) session.getAttribute(SessionConstants.LOGIN_USER_INFO);
			if(loginUserDto.getType()!=2){
				resultType.setStatus(DataStatus.HTTP_FAILE);
				resultType.setMessage("401");
				return resultType;
			}
		}
		
 		String resourceInfos = spaceDto.getResourceInfos();
		//原先数据
		String[] temp = resourceInfos.split(";");
		String tempId=temp[0].toString();
		int tempStartWeek=Integer.parseInt(temp[1].toString());
 		int tempEndWeek = Integer.parseInt(temp[2].toString());
		int tempStartTime=Integer.parseInt(temp[3].toString());
		int tempEndTime = Integer.parseInt(temp[4].toString());
		int tempPrice = Integer.parseInt(temp[5].toString());
		String tempA= "";
		
 		if(startDate==tempStartWeek&&endDate==tempEndWeek&&startTime==tempStartTime&&endTime==tempEndTime&&tempPrice==price){
 			resultType.setMessage("200");
			return resultType;
		}
		
		SpaceOpenTimeDto spaceOpenTimeDto = null;
		
		
		//比较时间
		if(startTime>tempStartTime){
			OrderItemsDto orderItemsDto = new OrderItemsDto();
			orderItemsDto.setSpaceId(tempId);
			orderItemsDto.setOrderTime(tempStartTime+"");
			orderItemsDto.setEndTime(startTime+"");
			orderItemsDto.setStat(900);
			int countOr = iOrderItemsService.findCount(orderItemsDto);
			orderItemsDto.setStat(901);
			int countOrd = iOrderItemsService.findCount(orderItemsDto);
			if(countOr!=0&&countOrd!=0){
				resultType.setStatus(DataStatus.HTTP_FAILE);
				resultType.setMessage("405");
				return resultType;
			}
		}
		
		if(endTime<tempEndTime){
			OrderItemsDto orderItemsDto = new OrderItemsDto();
			orderItemsDto.setSpaceId(tempId);
			orderItemsDto.setOrderTime(endTime+"");
			orderItemsDto.setEndTime(tempEndTime+"");
			orderItemsDto.setStat(900);
			int countOr = iOrderItemsService.findCount(orderItemsDto);
			orderItemsDto.setStat(901);
			int countOrd = iOrderItemsService.findCount(orderItemsDto);
			if(countOr!=0&&countOrd!=0){
				resultType.setStatus(DataStatus.HTTP_FAILE);
				resultType.setMessage("405");
				return resultType;
			}
		}
					//比较日期
					//开始日期与结束日期与数据库相同
					if(startDate==tempStartWeek&&endDate==tempEndWeek){
						for(int i=startDate;i<=endDate;i++){
						spaceOpenTimeDto = new SpaceOpenTimeDto();
						spaceOpenTimeDto.setSpaceId(tempId);
						spaceOpenTimeDto.setStartTime(DateUtils.StringToDate(startTime+":00:00", FORMATSTR));
						spaceOpenTimeDto.setEndTime(DateUtils.StringToDate(endTime+":00:00", FORMATSTR));
						spaceOpenTimeDto.setOpenWeek((short)i);
						spaceOpenTimeDto.setPrice(price);
						iSpaceOpenTimeService.updateOpenTimeInfoByWeek(spaceOpenTimeDto);

						}
						resultType.setMessage("200");
						return resultType;
					}
						
		
		
		
		
					//日期如果小于开始日期或大于结束日期 则insert数据
					if(startDate<tempStartWeek&&endDate>tempEndWeek){
						if(startDate<tempStartWeek){
						for(int i =startDate;i<tempStartWeek;i++){
							spaceOpenTimeDto = new SpaceOpenTimeDto();
							spaceOpenTimeDto.setSpaceId(tempId);
							spaceOpenTimeDto.setStartTime(DateUtils.StringToDate(startTime+":00:00", FORMATSTR));
							spaceOpenTimeDto.setEndTime(DateUtils.StringToDate(endTime+":00:00", FORMATSTR));
							spaceOpenTimeDto.setOpenWeek((short)i);
							spaceOpenTimeDto.setUserAccountId(loginUserDto.getId());
							spaceOpenTimeDto.setPrice(price);
							iSpaceOpenTimeService.saveOrUpdate(spaceOpenTimeDto);
						}
					}
	
					
					if(endDate>tempEndWeek){
						for(int o =tempEndWeek;o<=endDate;o++){
							spaceOpenTimeDto = new SpaceOpenTimeDto();
							spaceOpenTimeDto.setOpenWeek((short)o);
							spaceOpenTimeDto.setSpaceId(tempId);
							int count2 = iSpaceOpenTimeService.findCount(spaceOpenTimeDto);
							if(count2==0){
								spaceOpenTimeDto = new SpaceOpenTimeDto();
								spaceOpenTimeDto.setSpaceId(tempId);
								spaceOpenTimeDto.setStartTime(DateUtils.StringToDate(startTime+":00:00", FORMATSTR));
								spaceOpenTimeDto.setEndTime(DateUtils.StringToDate(endTime+":00:00", FORMATSTR));
								spaceOpenTimeDto.setOpenWeek((short)o);
								spaceOpenTimeDto.setUserAccountId(loginUserDto.getId());
								spaceOpenTimeDto.setPrice(price);
								iSpaceOpenTimeService.saveOrUpdate(spaceOpenTimeDto);
							}else{
								spaceOpenTimeDto = new SpaceOpenTimeDto();
								spaceOpenTimeDto.setSpaceId(tempId);
								spaceOpenTimeDto.setStartTime(DateUtils.StringToDate(startTime+":00:00", FORMATSTR));
								spaceOpenTimeDto.setEndTime(DateUtils.StringToDate(endTime+":00:00", FORMATSTR));
								spaceOpenTimeDto.setOpenWeek((short)o);
								spaceOpenTimeDto.setPrice(price);
								iSpaceOpenTimeService.updateOpenTimeInfoByWeek(spaceOpenTimeDto);
							}
	
							
						}

					}
					resultType.setMessage("200");
					return resultType;
			}	
					
					
					//日期如果大于开始日期且小于结束日期、日期大于开始日期且等于结束日期、日期等于开始日期且小于结束日期
					//则需要判断是否有订单
					if((startDate>tempStartWeek&&endDate<tempEndWeek)||(startDate>=tempStartWeek&&endDate<tempEndWeek)||(startDate>tempStartWeek&&endDate<=tempEndWeek)){
						if(startDate!=tempStartWeek&&endDate!=tempEndWeek){
						for(int k=tempStartWeek;k<=tempEndWeek;k++){
							boolean flag=false;
							for(int p=startDate;p<=endDate;p++){
								if(k==p){
									flag=true;
									break;
								}
								
							}
							if(flag==false){
							tempA+=k+";";
							}
							
						}
						String[] tempB= tempA.split(";");
						
						
						for(int u = 0;u<tempB.length;u++){
							if(startTime==tempStartTime&&endTime==tempEndTime){
							spaceOpenTimeDto = new SpaceOpenTimeDto();
							spaceOpenTimeDto.setOpenWeek((short)Integer.parseInt(tempB[u].toString()));
							spaceOpenTimeDto.setSpaceId(tempId);
							int count2 = iSpaceOpenTimeService.findCount(spaceOpenTimeDto);
							if(count2!=0){
								if(tempB[u]!=null&&!"".equals(tempB[u].toString())){
									
									OrderItemsDto orderItemsDto = new OrderItemsDto();
									orderItemsDto.setSpaceId(tempId);
									orderItemsDto.setWeek(Integer.parseInt(tempB[u]));
									int count = iOrderItemsService.findCount(orderItemsDto);
									if(count==0){
										spaceOpenTimeDto = new SpaceOpenTimeDto();
										spaceOpenTimeDto.setOpenWeek((short)Integer.parseInt(tempB[u]));
										spaceOpenTimeDto.setSpaceId(tempId);
										iSpaceOpenTimeService.deleteOpenTimeInfoByWeek(spaceOpenTimeDto);
										
										
									}else{
										resultType.setMessage("405");
										return resultType;
									}
							}
						}else{
							
						}
					}else{
						spaceOpenTimeDto = new SpaceOpenTimeDto();
						spaceOpenTimeDto.setOpenWeek((short)Integer.parseInt(tempB[u].toString()));
						spaceOpenTimeDto.setSpaceId(tempId);
						int count2 = iSpaceOpenTimeService.findCount(spaceOpenTimeDto);
						if(count2!=0){
							if(tempB[u]!=null&&!"".equals(tempB[u].toString())){
								
								OrderItemsDto orderItemsDto = new OrderItemsDto();
								orderItemsDto.setSpaceId(tempId);
								orderItemsDto.setWeek(Integer.parseInt(tempB[u]));
								int count = iOrderItemsService.findCount(orderItemsDto);
								if(count==0){
									spaceOpenTimeDto = new SpaceOpenTimeDto();
									spaceOpenTimeDto.setOpenWeek((short)Integer.parseInt(tempB[u]));
									spaceOpenTimeDto.setSpaceId(tempId);
									spaceOpenTimeDto.setStartTime(DateUtils.StringToDate(startTime+":00:00", FORMATSTR));
									spaceOpenTimeDto.setEndTime(DateUtils.StringToDate(endTime+":00:00", FORMATSTR));
									spaceOpenTimeDto.setPrice(price);
									iSpaceOpenTimeService.updateOpenTimeInfoByWeek(spaceOpenTimeDto);
									
								}else{
									resultType.setMessage("405");
									return resultType;
								}
						}
					}
				}	
							
						}
						resultType.setMessage("200");
						return resultType;
					}
						
						//大于开始日期且等于结束日期
					   if(startDate!=tempStartWeek&&endDate==tempEndWeek){
						   
						   for(int m=tempStartWeek;m<startDate;m++){
							   spaceOpenTimeDto = new SpaceOpenTimeDto();
								spaceOpenTimeDto.setOpenWeek((short)m);
								spaceOpenTimeDto.setSpaceId(tempId);
								int count2 = iSpaceOpenTimeService.findCount(spaceOpenTimeDto);
								if(count2!=0){
									OrderItemsDto orderItemsDto = new OrderItemsDto();
									   orderItemsDto.setSpaceId(tempId);
									   orderItemsDto.setWeek(m);
									   int count1 = iOrderItemsService.findCount(orderItemsDto);
									   if(count1==0){
										   spaceOpenTimeDto = new SpaceOpenTimeDto();
											spaceOpenTimeDto.setOpenWeek((short)m);
											spaceOpenTimeDto.setSpaceId(tempId);
											iSpaceOpenTimeService.deleteOpenTimeInfoByWeek(spaceOpenTimeDto);
											
									   }else{
											resultType.setMessage("405");
											return resultType;
										}
								}else{
									resultType.setMessage("403");
									return resultType;
								}
						   }
						   if(startTime!=tempStartTime||endTime!=tempEndTime){
							   for(int b=startDate;b<=endDate;b++){
								   spaceOpenTimeDto = new SpaceOpenTimeDto();
								   spaceOpenTimeDto.setOpenWeek((short)b);
								   spaceOpenTimeDto.setSpaceId(tempId);
								   spaceOpenTimeDto.setStartTime(DateUtils.StringToDate(startTime+":00:00", FORMATSTR));
									spaceOpenTimeDto.setEndTime(DateUtils.StringToDate(endTime+":00:00", FORMATSTR));
									spaceOpenTimeDto.setPrice(price);
								   iSpaceOpenTimeService.updateOpenTimeInfoByWeek(spaceOpenTimeDto);
							   }
						   }
						   resultType.setMessage("200");
							return resultType;
					   }
					   
					   
					   if(startDate==tempStartWeek&&endDate!=tempEndWeek){
						   
						   for(int n=endDate+1;n<=tempEndWeek;n++){
							   spaceOpenTimeDto = new SpaceOpenTimeDto();
								spaceOpenTimeDto.setOpenWeek((short)n);
								spaceOpenTimeDto.setSpaceId(tempId);
								int count2 = iSpaceOpenTimeService.findCount(spaceOpenTimeDto);
								if(count2!=0){
									OrderItemsDto orderItemsDto = new OrderItemsDto();
									   orderItemsDto.setSpaceId(tempId);
									   orderItemsDto.setWeek(n);
									   int count23 = iOrderItemsService.findCount(orderItemsDto);
									   if(count23==0){
										   spaceOpenTimeDto = new SpaceOpenTimeDto();
											spaceOpenTimeDto.setOpenWeek((short)n);
											spaceOpenTimeDto.setSpaceId(tempId);
											iSpaceOpenTimeService.deleteOpenTimeInfoByWeek(spaceOpenTimeDto);
											
									   }else{
											resultType.setMessage("405");
											return resultType;
										}
								}else{
									resultType.setMessage("403");
									return resultType;
								}
								
								
						   }
						   if(startTime!=tempStartTime||endTime!=tempEndTime){
							   for(int b=startDate;b<=endDate;b++){
								   spaceOpenTimeDto = new SpaceOpenTimeDto();
								   spaceOpenTimeDto.setOpenWeek((short)b);
								   spaceOpenTimeDto.setSpaceId(tempId);
								   spaceOpenTimeDto.setStartTime(DateUtils.StringToDate(startTime+":00:00", FORMATSTR));
									spaceOpenTimeDto.setEndTime(DateUtils.StringToDate(endTime+":00:00", FORMATSTR));
									spaceOpenTimeDto.setPrice(price);
								   iSpaceOpenTimeService.updateOpenTimeInfoByWeek(spaceOpenTimeDto);
							   }
						   }
						   resultType.setMessage("200");
							return resultType;
					   }
						
						
					}
					
					//日期如果小于开始日期且小于结束日期 则需要判断是否有订单且insert数据
					
					if(startDate<tempStartWeek&&endDate<=tempEndWeek){
						
						if(endDate>=tempStartWeek){
							for(int m=startDate;m<=endDate;m++){
								spaceOpenTimeDto = new SpaceOpenTimeDto();
								spaceOpenTimeDto.setOpenWeek((short)m);
									spaceOpenTimeDto.setSpaceId(tempId);
									int count2 = iSpaceOpenTimeService.findCount(spaceOpenTimeDto);
									if(m==startDate&&count2>0){
										resultType.setMessage("403");
										return resultType;
									}
									if(count2==0){
										spaceOpenTimeDto.setSpaceId(tempId);
										spaceOpenTimeDto.setStartTime(DateUtils.StringToDate(startTime+":00:00", FORMATSTR));
										spaceOpenTimeDto.setEndTime(DateUtils.StringToDate(endTime+":00:00", FORMATSTR));
										spaceOpenTimeDto.setOpenWeek((short)m);
										spaceOpenTimeDto.setUserAccountId(loginUserDto.getId());
										spaceOpenTimeDto.setPrice(price);
										iSpaceOpenTimeService.saveOrUpdate(spaceOpenTimeDto);
									}else{
										spaceOpenTimeDto.setSpaceId(tempId);
										spaceOpenTimeDto.setStartTime(DateUtils.StringToDate(startTime+":00:00", FORMATSTR));
										spaceOpenTimeDto.setEndTime(DateUtils.StringToDate(endTime+":00:00", FORMATSTR));
										spaceOpenTimeDto.setOpenWeek((short)m);
										spaceOpenTimeDto.setUserAccountId(loginUserDto.getId());
										spaceOpenTimeDto.setPrice(price);
										iSpaceOpenTimeService.updateOpenTimeInfoByWeek(spaceOpenTimeDto);
//										iSpaceOpenTimeService.saveOrUpdate(spaceOpenTimeDto);
									}
							}
								resultType.setMessage("200");
								return resultType;

						}
						if(endDate<=tempEndWeek){
							for(int m=startDate;m<=tempStartWeek;m++){
								spaceOpenTimeDto = new SpaceOpenTimeDto();
								spaceOpenTimeDto.setOpenWeek((short)m);
									spaceOpenTimeDto.setSpaceId(tempId);
									int count2 = iSpaceOpenTimeService.findCount(spaceOpenTimeDto);
									if(count2==0){
										spaceOpenTimeDto.setSpaceId(tempId);
										spaceOpenTimeDto.setStartTime(DateUtils.StringToDate(startTime+":00:00", FORMATSTR));
										spaceOpenTimeDto.setEndTime(DateUtils.StringToDate(endTime+":00:00", FORMATSTR));
										spaceOpenTimeDto.setOpenWeek((short)m);
										spaceOpenTimeDto.setUserAccountId(loginUserDto.getId());
										spaceOpenTimeDto.setPrice(price);
										iSpaceOpenTimeService.saveOrUpdate(spaceOpenTimeDto);
									}else{
										spaceOpenTimeDto.setSpaceId(tempId);
										spaceOpenTimeDto.setStartTime(DateUtils.StringToDate(startTime+":00:00", FORMATSTR));
										spaceOpenTimeDto.setEndTime(DateUtils.StringToDate(endTime+":00:00", FORMATSTR));
										spaceOpenTimeDto.setOpenWeek((short)m);
										spaceOpenTimeDto.setUserAccountId(loginUserDto.getId());
										spaceOpenTimeDto.setPrice(price);
										iSpaceOpenTimeService.updateOpenTimeInfoByWeek(spaceOpenTimeDto);
									}
							}
							for(int n=endDate+1;n<=tempEndWeek;n++){
									   OrderItemsDto orderItemsDto = new OrderItemsDto();
									   orderItemsDto.setSpaceId(tempId);
									   orderItemsDto.setWeek(n);
									   int count23 = iOrderItemsService.findCount(orderItemsDto);
									   if(count23==0){
										   spaceOpenTimeDto = new SpaceOpenTimeDto();
											spaceOpenTimeDto.setOpenWeek((short)n);
											spaceOpenTimeDto.setSpaceId(tempId);
											iSpaceOpenTimeService.deleteOpenTimeInfoByWeek(spaceOpenTimeDto);
											
									   }else{
											resultType.setMessage("405");
											return resultType;
										}
								
							}
						}
						
//							   for(int n=endDate;n<=tempEndWeek;n++){
//								   spaceOpenTimeDto = new SpaceOpenTimeDto();
//									spaceOpenTimeDto.setOpenWeek((short)n);
//									spaceOpenTimeDto.setSpaceId(tempId);
//									int count2 = iSpaceOpenTimeService.findCount(spaceOpenTimeDto);
//									if(count2==0){
//										   OrderItemsDto orderItemsDto = new OrderItemsDto();
//										   orderItemsDto.setSpaceId(tempId);
//										   orderItemsDto.setWeek(n);
//										   int count23 = iOrderItemsService.findCount(orderItemsDto);
//										   if(count23==0){
//											   spaceOpenTimeDto = new SpaceOpenTimeDto();
//												spaceOpenTimeDto.setOpenWeek((short)n);
//												spaceOpenTimeDto.setId(tempId);
//												iSpaceOpenTimeService.deleteOpenTimeInfoByWeek(spaceOpenTimeDto);
//												
//										   }
//									}
//						 return "200";
//						}
//						 for(int m=startDate;m<=tempStartWeek;m++){
//							 spaceOpenTimeDto = new SpaceOpenTimeDto();
//							 spaceOpenTimeDto.setOpenWeek((short)m);
//								spaceOpenTimeDto.setSpaceId(tempId);
//								int count2 = iSpaceOpenTimeService.findCount(spaceOpenTimeDto);
//								if(count2==0){
//									spaceOpenTimeDto.setSpaceId(tempId);
//									spaceOpenTimeDto.setStartTime(DateUtils.StringToDate(startTime+":00:00", FORMATSTR));
//									spaceOpenTimeDto.setEndTime(DateUtils.StringToDate(endTime+":00:00", FORMATSTR));
//									spaceOpenTimeDto.setOpenWeek((short)m);
//									iSpaceOpenTimeService.saveOrUpdate(spaceOpenTimeDto);
//								}
//								
//
//						 }
//							return "200";
						 
					}
					
					
					
					 if(startDate>=tempStartWeek&&endDate>tempEndWeek){
						 if(startDate!=tempStartWeek){
							 
							 for(int n =tempStartWeek;n<=startDate;n++){
								   spaceOpenTimeDto = new SpaceOpenTimeDto();
									spaceOpenTimeDto.setOpenWeek((short)n);
									spaceOpenTimeDto.setSpaceId(tempId);
									int count2 = iSpaceOpenTimeService.findCount(spaceOpenTimeDto);
									if(count2!=0){
										OrderItemsDto orderItemsDto = new OrderItemsDto();
										   orderItemsDto.setSpaceId(tempId);
										   orderItemsDto.setWeek(n);
										   int count23 = iOrderItemsService.findCount(orderItemsDto);
										   if(count23==0){
											   spaceOpenTimeDto = new SpaceOpenTimeDto();
												spaceOpenTimeDto.setOpenWeek((short)n);
												spaceOpenTimeDto.setSpaceId(tempId);
												iSpaceOpenTimeService.deleteOpenTimeInfoByWeek(spaceOpenTimeDto);
												
										   }else{
												resultType.setMessage("405");
												return resultType;
											}
									}else{
										
									}
							   }
							 
						 }
						 
						 for(int m=tempEndWeek;m<=endDate;m++){
							 spaceOpenTimeDto = new SpaceOpenTimeDto();
							 spaceOpenTimeDto.setOpenWeek((short)m);
								spaceOpenTimeDto.setSpaceId(tempId);
								int count2 = iSpaceOpenTimeService.findCount(spaceOpenTimeDto);
								if(count2==0){
									spaceOpenTimeDto.setSpaceId(tempId);
									spaceOpenTimeDto.setStartTime(DateUtils.StringToDate(startTime+":00:00", FORMATSTR));
									spaceOpenTimeDto.setEndTime(DateUtils.StringToDate(endTime+":00:00", FORMATSTR));
									spaceOpenTimeDto.setOpenWeek((short)m);
									spaceOpenTimeDto.setUserAccountId(loginUserDto.getId());
									spaceOpenTimeDto.setPrice(price);
									iSpaceOpenTimeService.saveOrUpdate(spaceOpenTimeDto);
								}else{
									spaceOpenTimeDto.setSpaceId(tempId);
									spaceOpenTimeDto.setStartTime(DateUtils.StringToDate(startTime+":00:00", FORMATSTR));
									spaceOpenTimeDto.setEndTime(DateUtils.StringToDate(endTime+":00:00", FORMATSTR));
									spaceOpenTimeDto.setOpenWeek((short)m);
									spaceOpenTimeDto.setPrice(price);
									iSpaceOpenTimeService.updateOpenTimeInfoByWeek(spaceOpenTimeDto);
								}
								for(int k=startDate;k<endDate;k++){
									spaceOpenTimeDto.setSpaceId(tempId);
									spaceOpenTimeDto.setStartTime(DateUtils.StringToDate(startTime+":00:00", FORMATSTR));
									spaceOpenTimeDto.setEndTime(DateUtils.StringToDate(endTime+":00:00", FORMATSTR));
									spaceOpenTimeDto.setOpenWeek((short)m);
									spaceOpenTimeDto.setPrice(price);
									iSpaceOpenTimeService.updateOpenTimeInfoByWeek(spaceOpenTimeDto);
								}

								
						 } 
						 resultType.setMessage("200");
							return resultType;
					 }
					 resultType.setMessage("200");
						return resultType;
					 


		
	}
	
	

	/**
	 * 
	 * 此方法描述的是：查询场馆用户对应的场地列表
	 * 
	 * @author: wangxiongdx@163.com
	 * @version: 2015年3月24日 上午11:58:49
	 */
	@RequestMapping(value = "/list.do")
	@ResponseBody
	// 注册用户
	public Response<List<SpaceDto>> list(SpaceDto spaceDto) {
		Response<List<SpaceDto>> results = new Response<List<SpaceDto>>();
		List<SpaceDto> result = iSpaceService.findBy(spaceDto);
		results.setData(result);
		return results;
	}
	
	/**
	 * 
	 * 此方法描述是:查询所有场馆	
	 */
	@RequestMapping(value="/findAll.do")
	@ResponseBody
	public Response<PageResult<SpaceListDto>> findAll(HttpSession session,PageQuery page){
		Response<PageResult<SpaceListDto>> results = new Response<PageResult<SpaceListDto>>();
		SpaceDto spaceDto = new SpaceDto();
		LoginUserDto loginUserDto = null;
		if(session.getAttribute(SessionConstants.LOGIN_USER_INFO)!=null){
			loginUserDto = (LoginUserDto) session.getAttribute(SessionConstants.LOGIN_USER_INFO);
			if(loginUserDto.getType()!=2){
				results.setStatus(DataStatus.HTTP_FAILE);
				results.setMessage("401");
				return results;
			}
		}else{
			results.setStatus(DataStatus.HTTP_FAILE);
			results.setMessage("404");
			return results;
		}
		String userId=loginUserDto.getId();
		spaceDto.setUserAccountId(userId);
		 PageResult<SpaceListDto> result = iSpaceService.findAll(spaceDto,page);
		 results.setData(result);
		return results;
	}
	
	@RequestMapping(value="/findSearch.do")
	@ResponseBody
	public Response<PageResult<SpaceListDto>> findSearch(HttpSession session, SpaceDto spaceDto){
		Response<PageResult<SpaceListDto>> results = new Response<PageResult<SpaceListDto>>();
		LoginUserDto loginUserDto = null;
		if(session.getAttribute(SessionConstants.LOGIN_USER_INFO)!=null){
			loginUserDto = (LoginUserDto) session.getAttribute(SessionConstants.LOGIN_USER_INFO);
			if(loginUserDto.getType()!=2){
				results.setStatus(DataStatus.HTTP_FAILE);
				results.setMessage("401");
				return results;
			}
		}else{
			results.setStatus(DataStatus.HTTP_FAILE);
			results.setMessage("404");
			return results;
		}
		String userId=loginUserDto.getId();
		spaceDto.setUserAccountId(userId);
		if(!StringUtils.isEmpty(spaceDto.getSpaceName())){
			spaceDto.setSpaceName("%"+spaceDto.getSpaceName()+"%");
		}
		 PageResult<SpaceListDto> result = iSpaceService.findAll(spaceDto,null);
		 results.setData(result);
			return results;
	}
	
	/**
	 * 新增场地保存方法
	 */
	@RequestMapping(value="/insertSave.do")
	@ResponseBody
	public Response<String> insertSave(HttpSession session,@RequestParam("spaceName") String spaceName,@RequestParam("price") int price,@RequestParam("sportType") String sportType,@RequestParam("userImage") String userImage,@RequestParam("maxNumber") int maxNumber,@RequestParam("minNumber") int minNum){
		Response<String> resultType = new Response<String>();
		if(price<0||maxNumber<0){
			resultType.setStatus(DataStatus.HTTP_FAILE);
			resultType.setMessage("403");
			return resultType;
		}
		
		LoginUserDto loginUserDto = null;
		if(session.getAttribute(SessionConstants.LOGIN_USER_INFO)!=null){
			loginUserDto = (LoginUserDto) session.getAttribute(SessionConstants.LOGIN_USER_INFO);
			if(loginUserDto.getType()!=2){
				resultType.setStatus(DataStatus.HTTP_FAILE);
				resultType.setMessage("401");
				return resultType;
			}
		}
		String userId = loginUserDto.getId().toString();
		List<VenusInfoDto>  venusList = iStadiumService.selectVenusInfoByUserAccountId(userId);
		if(!CollectionUtils.isEmpty(venusList)){
			int charType = venusList.get(0).getChargeType();
			if(charType==0&&price>0){
				resultType.setStatus(DataStatus.HTTP_FAILE);
				resultType.setMessage("408");
				return resultType;
			}
		}
		SpaceDto spaceDto = new SpaceDto();
		
		spaceDto.setSpaceName(spaceName);
		spaceDto.setUserAccountId(userId);
		List<SpaceDto> result = iSpaceService.findBy(spaceDto);
		if(result!=null&&result.size()>0){
			resultType.setMessage("200");
			return resultType;
		}else{
			spaceDto.setSportId(sportType);
			spaceDto.setSpaceName(spaceName);
//			spaceDto.setPrice(price);
			spaceDto.setMaxNumber(maxNumber);
			spaceDto.setImages(userImage);
			if(minNum!=-1){
				spaceDto.setMinNumber(minNum);
			}
			String spaceId = iSpaceService.saveOrUpdate(spaceDto);
			resultType.setMessage("200");
			return resultType;
		}
		
		
	}
	
	
	/**
	 * 此方法查询space opentime
	 */
	@RequestMapping(value="/findOpen.do")
	@ResponseBody
	public Response<String>  findOpen(HttpSession session,@RequestParam("startDate") int startDate,@RequestParam("endDate") int endDate,@RequestParam("startTime") String startTime,@RequestParam("endTime") String endTime,@RequestParam("spaceName") String spaceName,@RequestParam("price") int price,@RequestParam("sportType") String sportType,@RequestParam("userImage") String userImage,@RequestParam("maxNumber") int maxNumber,@RequestParam("minNumber") int minNum,@RequestParam("nameStatus") int nameStatus){
		Response<String> resultType = new Response<String>();
		if(price<0||maxNumber<0){
			resultType.setStatus(DataStatus.HTTP_FAILE);
			resultType.setMessage("403");
			return resultType;
		}

		SpaceOpenTimeDto spaceOpenTimeDto = new SpaceOpenTimeDto();
		LoginUserDto loginUserDto = null;
		if(session.getAttribute(SessionConstants.LOGIN_USER_INFO)!=null){
			loginUserDto = (LoginUserDto) session.getAttribute(SessionConstants.LOGIN_USER_INFO);
			if(loginUserDto.getType()!=2){
				resultType.setStatus(DataStatus.HTTP_FAILE);
				resultType.setMessage("401");
				return resultType;
			}
		}else{
			resultType.setStatus(DataStatus.HTTP_FAILE);
			resultType.setMessage("404");
			return resultType;
		}
		String userId = loginUserDto.getId().toString();
		List<VenusInfoDto>  venusList = iStadiumService.selectVenusInfoByUserAccountId(userId);
		if(!CollectionUtils.isEmpty(venusList)){
			int charType = venusList.get(0).getChargeType();
			if(charType==0&&price>0){
				resultType.setStatus(DataStatus.HTTP_FAILE);
				resultType.setMessage("408");
				return resultType;
			}
		}
		SpaceDto spaceDto = new SpaceDto();
		spaceDto.setSpaceName(spaceName);
		spaceDto.setOwnerAccountId(userId);
		spaceDto.setUserAccountId(userId);
		//判断同个场馆不允许有相同名字的场地
		if(nameStatus==1){
			int countResult = iSpaceService.findCountByName(spaceDto);
			if(countResult>0){
				resultType.setStatus(DataStatus.HTTP_FAILE);
				resultType.setMessage("405");
				return resultType;
			}
		}
	
		List<SpaceDto> result = iSpaceService.findBy(spaceDto);

		if(result!=null&&result.size()>0){
			String spaceId = result.get(0).getId();
			spaceOpenTimeDto.setSpaceId(spaceId);
			//校验
			for(int i =startDate;i<=endDate;i++){
				spaceOpenTimeDto.setOpenWeek((short)i);
				spaceOpenTimeDto.setStartTime(DateUtils.StringToDate(startTime+":00:00", FORMATSTR));
				spaceOpenTimeDto.setEndTime(DateUtils.StringToDate(endTime+":00:00", FORMATSTR));
				int count = iSpaceService.findById(spaceOpenTimeDto);
				if(count>0){
					resultType.setStatus(DataStatus.HTTP_FAILE);
					resultType.setMessage("402");
					return resultType;
				}
				
				//一天只能加入一个时间点
//				int count2 = iSpaceService.findCheckDate(spaceOpenTimeDto);
//				if(count2>0){
//					resultType.setStatus(DataStatus.HTTP_FAILE);
//					resultType.setMessage("407");
//					return resultType;
//				}
			}
			//插入
			spaceDto = new SpaceDto();
			spaceDto.setId(result.get(0).getId());
			List<SpaceDto> list = iSpaceService.findBy(spaceDto);
			if(list!=null&&list.size()>0){
				String spaceIds = result.get(0).getId();

				for(int k =startDate;k<=endDate;k++){
					spaceOpenTimeDto = new SpaceOpenTimeDto();
					spaceOpenTimeDto.setPrice(price);
					spaceOpenTimeDto.setSpaceId(spaceIds);
					spaceOpenTimeDto.setOpenWeek((short)k);
					spaceOpenTimeDto.setStartTime(DateUtils.StringToDate(startTime+":00:00", FORMATSTR));
					spaceOpenTimeDto.setEndTime(DateUtils.StringToDate(endTime+":00:00", FORMATSTR));
					spaceOpenTimeDto.setUserAccountId(loginUserDto.getId());
					iSpaceService.saveOrUpdateOpenTime(spaceOpenTimeDto);
					
				}

				resultType.setMessage("200");
				return resultType;
			}
			
			
			
			
		}else{
			spaceDto.setSportId(sportType);
			spaceDto.setSpaceName(spaceName);
//			spaceDto.setPrice(price);
			spaceDto.setImages(userImage);
			spaceDto.setMaxNumber(maxNumber);
			if(minNum!=-1){
				spaceDto.setMinNumber(minNum);
			}
			String spaceId = iSpaceService.saveOrUpdate(spaceDto);
			for(int k =startDate;k<=endDate;k++){
				spaceOpenTimeDto = new SpaceOpenTimeDto();
				spaceOpenTimeDto.setSpaceId(spaceId);
				spaceOpenTimeDto.setPrice(price);
				spaceOpenTimeDto.setOpenWeek((short)k);
				spaceOpenTimeDto.setStartTime(DateUtils.StringToDate(startTime+":00:00", FORMATSTR));
				spaceOpenTimeDto.setEndTime(DateUtils.StringToDate(endTime+":00:00", FORMATSTR));
				spaceOpenTimeDto.setUserAccountId(loginUserDto.getId());
				iSpaceService.saveOrUpdateOpenTime(spaceOpenTimeDto);
				
			}
			//修改场地数量+1
			String tempUpdateNum = iVenuesInfoService.updateSpaceNum(userId,true);
			
			resultType.setMessage("200");
			return resultType;
		}
		resultType.setStatus(DataStatus.HTTP_FAILE);
		resultType.setMessage("405");
		return resultType;
	}

	
}
