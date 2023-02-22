package com.dessert.mail.user.controller;

import com.dessert.common.entity.common.Result;
import com.dessert.common.entity.ums.Collection;
import com.dessert.common.entity.ums.CollectionVo;
import com.dessert.mail.user.service.CollectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("collection")
public class CollectionController extends BaseController{
    @Autowired
    private CollectionService collectionService;

    @PostMapping("insert")
    public Result insert(@RequestBody Collection collection,HttpSession session){
        collection.setUserId(getLoginUserId(session));
        return collectionService.insert(collection);
    }
    @DeleteMapping("/deleteByProductId/{id}")
    public Result deleteByProductId(@PathVariable("id") Long productId,HttpSession session){
        return collectionService.deleteByProductId(productId,getLoginUserId(session));
    }
    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable("id") Long id){
        return Result.success(collectionService.removeById(id));
    }
    @GetMapping("/page")
    public Result page(CollectionVo vo,HttpSession session){
        vo.setUserId(getLoginUserId(session));
        return collectionService.getPage(vo);
    }

}
