package com.cnaidun.user.api.scheduleApproveTask.mapper;



import com.cnaidun.user.api.scheduleApproveTask.bean.WeixinSend;
import org.apache.ibatis.annotations.Select;

import java.util.List;

//import org.mapstruct.Mapper;

/**
 * Copyright (C), 2018-2018
 * FileName: WorkSheetMapper
 * Author:   cjle
 * Date:     2018/7/11 13:37
 * Description: 工单mapper
 * History:
 * <author>          <time>          <version>          <desc>
 * 修改者           修改时间           版本号              描述
 */

public interface WeixinSendMapper {



    @Select("select * from weixin_send where  dsend= 0 and to_days(createtime) = to_days(now())")
    List<WeixinSend> selectSmsData(WeixinSend info);

 //  @Select("select * from id_info order by createtime desc")
//    List<IdInfo> selectAll();
//    //@Insert("insert into work_sheet (workno,worktypecode,unitcode,uid,reqcontent,createtime) VALUES (UUID(),#{worktypecode},#{unitcode},#{uid},#{reqcontent},null) ")
// //   @Insert("insert into work_sheet (workno,worktypecode,uid,reqcontent,createtime) VALUES (UUID(),#{workTypeCode},#{uid},#{reqContent},NOW() )")
//  //  int updateDriverContacts(WorkSheet workSheet);
//
// //   @Select("select carno,cartype,brand,owner,citycode,address,usetype,carlicence,engineno,getdate,regdate from car_licence_info where uid= #{uid}")
////    List<CarLicenceInfo> findUserCarLicenceInfo(WorkSheet workSheet);

}
