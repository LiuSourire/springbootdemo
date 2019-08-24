package com.example.controller;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.entity.QueryEntity;
import com.example.entity.User;
import com.example.service.UserService;
import com.example.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Sourire
 */
@Controller
public class ViewController {
	
	@Autowired
	private UserService userService;
	
	@GetMapping("/toUserList")
	public String toUserList(Model model, QueryEntity qe) {
		QueryWrapper<User> wrapper = null;
		if(qe.getSize() == 10){
			qe.setSize(2);
		}
		if(null != qe.getDatemax() && null != qe.getDatemin() && null != qe.getQueryMsg()){
			try {
				wrapper = new QueryWrapper<User>().ge("create-time", DateUtil.stringtoDate(qe.getDatemin(),"yyyy-mm-dd"))
				.le("create-time",DateUtil.stringtoDate(qe.getDatemax(),"yyyy-mm-dd")).last("name||mobile||email like '"+qe.getQueryMsg()+"'");
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		IPage page = userService.page(new Page<>(qe.getCurrent(),qe.getSize()), wrapper);
		qe.setRecords(page.getRecords());
		qe.setPages(page.getPages());
		qe.setTotal(page.getTotal());
		System.out.println(qe.getCurrent()+"---"+qe.getPages());
		model.addAttribute("pageInfo",qe);
		return "userList";
	}

	@GetMapping("/toUserAdd")
	public String toUserAdd(){
		return "userAdd";
	}

    @GetMapping("/toChangePWD")
    public String toDelUser(String id,Model model){
		User user = userService.getById(id);
		model.addAttribute("user",user);
		return "changePWD";
    }

    @GetMapping("/toUserEdit")
    public String toUserEdit(String id, Model model) {
        User user = userService.getById(id);
        model.addAttribute("user",user);
        return "userEdit";
    }

}
