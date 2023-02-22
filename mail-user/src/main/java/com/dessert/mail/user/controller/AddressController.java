package com.dessert.mail.user.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.dessert.common.entity.common.Result;
import com.dessert.common.entity.pms.ProductVo;
import com.dessert.common.entity.ums.AddressInfo;
import com.dessert.common.entity.ums.AddressInfoVo;
import com.dessert.common.entity.ums.User;
import com.dessert.mail.user.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("address")
public class AddressController extends BaseController{

    @Autowired
    private AddressService addressService;

    @GetMapping("/page")
    public Result page(HttpSession session , AddressInfoVo vo){
        vo.setUserId(this.getLoginUserId(session));
        return addressService.getPage(vo);
    }

    @PutMapping("/update")
    public Result update(@RequestBody AddressInfo addressInfo,HttpSession session){
        addressInfo.setUserId(this.getLoginUserId(session));
        return Result.success(addressService.updateById(addressInfo));
    }
    @PostMapping("/insert")
    public Result insert(@RequestBody AddressInfo addressInfo,HttpSession session){
        addressInfo.setUserId(this.getLoginUserId(session));
        return Result.success(addressService.save(addressInfo));
    }
    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable("id") Long id){
        return Result.success(addressService.removeById(id));
    }

    @GetMapping("/getDefault")
    public Result getDefault(HttpSession session){
        LambdaQueryWrapper<AddressInfo> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(AddressInfo::getUserId,getLoginUserId(session));
        wrapper.eq(AddressInfo::getIsDefault,1);
        return Result.success(addressService.getOne(wrapper));
    }
}
