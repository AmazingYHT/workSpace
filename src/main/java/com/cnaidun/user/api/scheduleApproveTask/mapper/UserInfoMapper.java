package com.cnaidun.user.api.scheduleApproveTask.mapper;



import com.cnaidun.user.api.scheduleApproveTask.bean.IdInfo;
import org.apache.ibatis.annotations.Insert;
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

public interface UserInfoMapper {



    @Select("select * from id_info where  uid= #{uid}")
    List<IdInfo> authentication(IdInfo info);

    @Select("select distinct r.openid,i.name,r.uid,i.phone from id_info i,reg_info r where i.uid = r.uid")
    List<IdInfo> selectAll();

    //@Insert("insert into work_sheet (workno,worktypecode,unitcode,uid,reqcontent,createtime) VALUES (UUID(),#{worktypecode},#{unitcode},#{uid},#{reqcontent},null) ")
 //   @Insert("insert into work_sheet (workno,worktypecode,uid,reqcontent,createtime) VALUES (UUID(),#{workTypeCode},#{uid},#{reqContent},NOW() )")
  //  int updateDriverContacts(WorkSheet workSheet);

 //   @Select("select carno,cartype,brand,owner,citycode,address,usetype,carlicence,engineno,getdate,regdate from car_licence_info where uid= #{uid}")
//    List<CarLicenceInfo> findUserCarLicenceInfo(WorkSheet workSheet);

}
